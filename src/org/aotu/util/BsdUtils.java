package org.aotu.util;

import org.aotu.offer.entity.feilvEntity;
import org.aotu.order.entity.Work_pz_gzEntity;
import org.aotu.user.entity.userEntity;
import org.nutz.dao.Cnd;
import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import java.sql.CallableStatement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class BsdUtils {

    /**
     * 把权限的字符串转化为可以在in中使用的带单引号的字符串
     * 例如，公司权限为"01,02,", 转化之后就是 "'01','02'"
     * @param gnStr
     * @return
     */
    public static String gn2quote(String gnStr) {
        if (gnStr == null || gnStr.equals("")) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        String[] split = gnStr.split(",");
        for (int i = 0; i < split.length; i++) {
            sb.append("'").append(split[i]).append("'").append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    public static void updateWorkPzGz(Dao dao, String work_no) {
        double xche_rgf = 0;
        double xche_clf = 0;
        Sql sql1_11 = Sqls
                .queryRecord("select sum(wxxm_je) xche_rgf from work_mx_gz where work_no='" + work_no + "' and wxxm_zt in ('正常','保险')");
        dao.execute(sql1_11);
        List<Record> resRgf = sql1_11.getList(Record.class);
//        if (resRgf != null && resRgf.size() > 0) {
        // 只需判断下面的条件既可，因为查询结果resRgf肯定不为null，并且size肯定是1
        if (resRgf.get(0).getString("xche_rgf") != null ) {
            xche_rgf = Double.parseDouble(resRgf.get(0).getString("xche_rgf"));
        }
        Sql sql1_1 = Sqls
                .create("update work_pz_gz set xche_rgf = " + xche_rgf + ",xche_rgbh = " + xche_rgf + ",xche_rgsl=0,xche_rgse=0 where work_no='" + work_no + "'");
        dao.execute(sql1_1);

        Sql sql1_22 = Sqls
                .queryRecord("select sum(peij_je) xche_clf from work_ll_gz where work_no='" + work_no + "' and peij_zt in ('正常','保险')");
        dao.execute(sql1_22);
        List<Record> resClf = sql1_22.getList(Record.class);
        if (resClf.get(0).getString("xche_clf") != null) {
            xche_clf = Double.parseDouble(resClf.get(0).getString("xche_clf"));
        }
        Sql sql1_2 = Sqls
                .create("update work_pz_gz set xche_clf = " + xche_clf + ",xche_clbh = " + xche_clf + ",xche_clsl=0,xche_clse=0" +
                        ",xche_yhje = isnull(xche_wxxm_yhje,0)+isnull(xche_peij_yhje,0)" +
                        ",xche_hjje= isnull(xche_rgf,0)+isnull(xche_clf,0)" +
                        " where work_no='" + work_no + "'");
        dao.execute(sql1_2);
    }

    /**
     * 是否有某个权限
     * @param caozuoyuanGndm 权限列表
     * @param qxName 权限代码
     * @return true表示有，false表示没有
     */
    public static boolean isHasQx(String caozuoyuanGndm, String qxName) {
        String[] qxs = caozuoyuanGndm.split(",");
        for (String qx : qxs) {
            if (qx.equals(qxName)) {
                return true;
            }
        }
        return false;
    }

    public static String createNewBill(final Dao dao, final String gongsino, final String caozuoyuan_xm, final int billType) {
        final String[] billNo = new String[1];
        Trans.exec(new Atom() {
            @Override
            public void run() {
                final int[] returnValue = new int[1];
                if (gongsino == null || "".equals(gongsino) || caozuoyuan_xm == null || "".equals(caozuoyuan_xm)) {
                    throw new RuntimeException("公司编码或者操作员不能为空！");
                }
                dao.run(new ConnCallback() {
                    @Override
                    public void invoke(java.sql.Connection conn) throws Exception {
                        CallableStatement cs = conn.prepareCall("{? = call sp_bslistnew (?,?,?,?)}");
                        cs.registerOutParameter(1, Types.INTEGER);
                        cs.setInt(2, billType);
                        cs.setString(3, gongsino);
                        cs.setString(4, caozuoyuan_xm);
                        cs.registerOutParameter(5, Types.VARCHAR);
                        cs.execute();
                        returnValue[0] = cs.getInt(1);
                        billNo[0] = cs.getString(5);
                    }
                });
                if (returnValue[0] == 0) { // 等于0，表示存储过程执行成功
                    if (billNo[0] == null || "".equals(billNo[0])) {
                        throw new RuntimeException("新建单号失败！");
                    }
                    switch (billType) {
                        case 2007: // 维修单
                            try {
                                supplyWorkInfo(dao, billNo[0]);
                            } catch (Exception e) {
                                throw new RuntimeException(e.getMessage());
                            }
                            break;
                    }
                } else {
                    throw new RuntimeException("新建单号或者插入工作表出错！");
                }
            }
        });
        return billNo[0];
    }

    /**
     * 补充维修单信息
     * @param dao
     * @param number
     */
    private static void supplyWorkInfo(Dao dao, String number) {
        String feilvName = "";
        double feilv = 1;
        List<feilvEntity> feilvList = dao.query(feilvEntity.class, Cnd.where("feil_sy", "=", 1));
        if (feilvList.size() > 0) {
            feilvName = feilvList.get(0).getFeil_mc();
            feilv = feilvList.get(0).getFeil_fl();
        } else {
            List<feilvEntity> feiList1 = dao.query(feilvEntity.class, Cnd.where("feil_mc", "=", "一级标准"));
            if (feiList1.size() > 0) {
                feilvName = feiList1.get(0).getFeil_mc();
                feilv = feiList1.get(0).getFeil_fl();
            } else {
                Sql sql = Sqls.queryRecord("select top 1 feil_mc, feil_fl from work_feilv_sm");
                dao.execute(sql);
                List<Record> list = sql.getList(Record.class);
                if (list.size() > 0) {
                    feilvName = list.get(0).getString("feil_mc");
                    feilv = Double.parseDouble(list.get(0).getString("feil_fl"));
                }
            }
        }
        double wxxm_lv = 0;
        double peij_lv = 0;
        Sql sql = Sqls.queryRecord("select wxxm_lv,peij_lv from sm_glf");
        dao.execute(sql);
        List<Record> res = sql.getList(Record.class);
        if (res.size() > 0) {
            wxxm_lv = Double.parseDouble(res.get(0).getString("wxxm_lv"));
            peij_lv = Double.parseDouble(res.get(0).getString("peij_lv"));
        }
        Work_pz_gzEntity pz = dao.fetch(Work_pz_gzEntity.class, Cnd.where("work_no", "=", number));
        // 获取3天之后的日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        pz.setXche_yjwgrq(simpleDateFormat.format(calendar.getTime()));
        pz.setXche_jsrq(Calendar.getInstance().getTime());
        pz.setXche_sfbz(feilvName);
        pz.setXche_sffl(feilv);
        pz.setXche_wxjd("登记");
        pz.setFlag_fast(true);
        pz.setXche_ywlx("正常维修");
        pz.setXche_wxxmlv(wxxm_lv * 100);
        pz.setXche_peijlv(peij_lv * 100);
        pz.setCard_itemrate(1);
        pz.setCard_peijrate(1);
        pz.setFlag_pad(true);
        // 判断是否质检和洗车
        Sql sql1 = Sqls.queryRecord("select sys_ischeckwork,sys_isxiche from sm_system_info");
        dao.execute(sql1);
        List<Record> list = sql1.getList(Record.class);
        if (list.size() > 0) {
            if (list.get(0).getInt("sys_ischeckwork") == 0) {
                pz.setFlag_IsCheck(false);
            } else {
                pz.setFlag_IsCheck(true);
            }
            if (list.get(0).getInt("sys_isxiche") == 0) {
                pz.setFlag_isxiche(false);
            } else {
                pz.setFlag_isxiche(true);
            }
        } else {
            pz.setFlag_IsCheck(false);
            pz.setFlag_isxiche(false);
        }
        // 只要更新的对象是从库里面查出来的，更新的时候正则表达式就可以^xxx|yyy$ 这样写
        int num = dao.update(pz, "^xche_yjwgrq|xche_jsrq|xche_sfbz|xche_sffl|xche_wxjd|flag_fast|xche_ywlx|xche_wxxmlv|xche_peijlv|card_itemrate|card_peijrate|flag_pad|flag_IsCheck|flag_isxiche$");
        if (num < 1) {
            throw new RuntimeException("单据已经不存在！");
        }
    }
}
