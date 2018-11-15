package org.aotu.kuixiu.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//


//
import org.aotu.Jsons;
import org.aotu.offsetPager;
import org.aotu.VIPcard.entity.Kehu_CardEntity;
import org.aotu.VIPcard.module.card;
import org.aotu.offer.entity.feilvEntity;
import org.aotu.order.entity.Work_ll_gzEntity;
import org.aotu.order.entity.Work_mx_gzEntity;
import org.aotu.order.entity.Work_pz_gzEntity;
import org.aotu.publics.eneity.KehuEntity;
import org.aotu.publics.eneity.Work_cheliang_smEntity;
import org.aotu.publics.module.publicModule;
import org.aotu.user.entity.userEntity;
import org.aotu.util.BsdUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

/**
 * @author 刘宏德
 */
@IocBean
@At("/kuaixiu")
public class KuaixiuModule {
    @Inject
    Dao dao;
    @Inject
    Jsons jsons;
    @Inject
    card card;
    @Inject
    publicModule pu;

    /**
     * 美容快修的添加基本信息
     *
     * @param gongsiNo
     * @return
     */
    @At
    @Ok("raw:json")
    public String addMrkxJbxx(@Param("gongsiNo") String gongsiNo,
                              @Param("caozuoyuan_xm") final String caozuoyuan_xm,
                              @Param("..") Work_pz_gzEntity pz) {
        if (pz.getGongSiNo() == null || "".equals(pz.getGongSiNo())) {
            Sql sql = Sqls
                    .queryRecord(" select gongsino from sm_caozuoyuan where caozuoyuan_xm='" + caozuoyuan_xm + "'");
            dao.execute(sql);
            List<Record> res = sql.getList(Record.class);
            pz.setGongSiNo(res.get(0).getString("gongsino"));
        }
        pz.setXche_jsrq(new Date());
        dao.update(pz, "^che_no|che_cx|che_vin|kehu_no|kehu_mc|kehu_dh|xche_sfbz|xche_bz|xche_jsr|xche_lc|xche_jsrq|card_no|GongSiNo|cangk_dm$");
        Work_cheliang_smEntity che = pu.saveCheInfo(pz.getChe_no(), pz.getGcsj(), pz.getChe_cx(),
                pz.getChe_vin(), pz.getGongSiNo());
        pu.saveKeHu(che.getKehu_no(), pz.getKehu_mc(), pz.getKehu_dh());

        dao.update(Work_ll_gzEntity.class, Chain.make("cangk_dm", pz.getCangk_dm()), Cnd.where("Work_no", "=", pz.getWork_no()));
        Sql sql0_8 = Sqls
                .create("update work_ll_gz set danwei_bs_ls =  1 where work_no = '" + pz.getWork_no() + "' and  danwei_bs_ls = 0 ");
        dao.execute(sql0_8);
        Sql sql0_1 = Sqls.queryRecord("select Card_JifenType from cardsysset");
        dao.execute(sql0_1);
        List<Record> res = sql0_1.getList(Record.class);
        String Card_JifenType = res.get(0).getString("Card_JifenType");
        if ("false".equals(Card_JifenType)) {
            Sql sql0_2 = Sqls
                    .create("update b set b.peij_jifenlv=isnull(a.peij_shlv,0) from kucshp_info a ,work_ll_gz b where work_no='" + pz.getWork_no() + "' and a.peij_no=b.peij_no");
            dao.execute(sql0_2);
            Sql sql0_3 = Sqls
                    .create("update work_ll_gz set peij_jifen=isnull(peij_jifenlv,0)*isnull(peij_je,0) where work_no='" + pz.getWork_no() + "'");
            dao.execute(sql0_3);
            BigDecimal pjJF = (BigDecimal) dao.func2(Work_ll_gzEntity.class, "sum", "peij_jifen",
                    Cnd.where("work_no", "=", pz.getWork_no()).and("peij_zt", "=", "正常"));
            Sql sql0_4 = Sqls
                    .create("update b set b.wxxm_jifenlv=isnull(a.wxxm_jifenlv,0) from work_weixiu_sm a ,work_mx_gz b where work_no='" + pz.getWork_no() + "' and a.wxxm_no=b.wxxm_no");
            dao.execute(sql0_4);
            Sql sql0_5 = Sqls
                    .create("update work_mx_gz set wxxm_jifen=isnull(wxxm_jifenlv,0)*isnull(wxxm_je,0) where work_no='" + pz.getWork_no() + "'");
            dao.execute(sql0_5);
            BigDecimal xmJF = (BigDecimal) dao.func2(Work_mx_gzEntity.class, "sum", "wxxm_jifen",
                    Cnd.where("work_no", "=", pz.getWork_no()).and("wxxm_zt", "=", "正常"));
            if (pjJF != null && xmJF != null) {
                dao.execute(Sqls.create("update work_pz_gz set work_jifen_sum= " + pjJF.add(xmJF) + " where work_no='" + pz.getWork_no() + "'"));
            } else if (pjJF != null) {
                dao.execute(Sqls.create("update work_pz_gz set work_jifen_sum= " + pjJF + " where work_no='" + pz.getWork_no() + "'"));
            } else if (xmJF != null) {
                dao.execute(Sqls.create("update work_pz_gz set work_jifen_sum= " + xmJF + " where work_no='" + pz.getWork_no() + "'"));
            }
        }
        BsdUtils.updateWorkPzGz(dao, pz.getWork_no());
        return "success";
    }

    /**
     * 基本信息
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String mrkxJbxx(String che_no, String gongsiNo, String caozuoyuan_xm, String work_no) {
        Work_cheliang_smEntity che = dao.fetch(Work_cheliang_smEntity.class, che_no);
        Work_pz_gzEntity pz = new Work_pz_gzEntity();
        if (!"".equals(work_no)) {
            pz = dao.fetch(Work_pz_gzEntity.class, work_no);
            pz.setGcsj(che.getChe_gcrq());
        } else {
            if (gongsiNo == null || caozuoyuan_xm == null) {
                return jsons.json(1, 1, 0, "公司编号或操作员不能为空");
            }
            java.util.Calendar rightNow = java.util.Calendar.getInstance();
            java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 如果是后退几天，就写 -天数 例如：
            rightNow.add(java.util.Calendar.DAY_OF_MONTH, -20);
            // 进行时间转换
            String date = sim.format(rightNow.getTime());
            // 查询这个车一段时间内的快修草稿单，按时间倒叙
            List<Work_pz_gzEntity> result = dao.query(Work_pz_gzEntity.class,
                    Cnd.where("che_no", "=", che_no)
                            .and("xche_jdrq", ">", date)
                            .and("flag_fast", "=", 1)
                            .and("mainstate", "=", -1)
                            .desc("xche_jdrq"));
            // 如果没有，则新建单号
            if (result.size() == 0) {
                String num = add(gongsiNo, caozuoyuan_xm);
                pz.setWork_no(num);
                pz.setChe_no(che_no);
                pz.setXche_jdrq(new Date());
                // 查询建表需要的内容
                List<feilvEntity> fei = dao.query(feilvEntity.class,
                        Cnd.where("feil_sy", "=", true));
                if (fei.size() < 0) {
                    fei = dao.query(feilvEntity.class,
                            Cnd.where("feil_mc", "=", "一级标准"));
                }
                String mc = fei.get(0).getFeil_mc();
                double fl = fei.get(0).getFeil_fl();
                pz.setXche_sfbz(mc);
                pz.setXche_sffl(fl);
                pz.setFlag_pad(true);
                pz.setFlag_fast(true);
                // 设置草稿单上车辆和客户的信息
                if (che != null) {
                    pz.setChe_cx(che.getChe_cx());
                    pz.setChe_vin(che.getChe_vin());
                    pz.setGcsj(che.getChe_gcrq());
                    pz.setXche_lc(che.getChe_next_licheng());
                    KehuEntity kehu = dao.fetch(KehuEntity.class, che.getKehu_no());
                    if (kehu != null) {
                        pz.setKehu_mc(kehu.getKehu_mc());
                        pz.setKehu_dh(kehu.getKehu_dh());
                        pz.setKehu_no(kehu.getKehu_no());
                    }
                }
                pz.setMainstate(-1);
                dao.updateIgnoreNull(pz);
            } else {
                pz = result.get(0);
                pz.setGcsj(che.getChe_gcrq());
            }
            // 查询仓库，准备获取名字
            Sql sql1 = Sqls
                    .queryRecord("select * from sm_cangk where isnull(cangk_hide,0) = 0 ");
            dao.execute(sql1);
            List<Record> res = sql1.getList(Record.class);
            for (Record record : res) {
                if (pz.getCangk_dm() != null && !pz.getCangk_dm().equals("")) {
                    // 如果草稿单中的仓库代码和仓库中的代码相同，则把仓库名称赋给草稿单中的内容
                    if (record.getString("cangk_dm").equals(pz.getCangk_dm())) {
                        pz.setCangk_mc(record.getString("cangk_mc"));
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        String json = Json.toJson(pz, JsonFormat.full());
        return jsons.json(1, 1, 1, json);
    }

    String number = "";

    /**
     * 创建work单号
     *
     * @param gongsiNo
     * @param caozuoyuan_xm
     * @return
     */
    @At
    @Ok("raw:json")
    public String add(final String gongsiNo, final String caozuoyuan_xm) {
        if (gongsiNo == "" || caozuoyuan_xm == "") {
            return jsons.json(1, 0, 0, "");
        }
        // 新增单号
        dao.run(new ConnCallback() {
            @Override
            public void invoke(java.sql.Connection conn) throws Exception {
                CallableStatement cs = conn
                        .prepareCall("{call sp_bslistnew (2007,?,?,?)}");
                cs.setString(1, gongsiNo);
                cs.setString(2, caozuoyuan_xm);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.executeUpdate();
                String orderNo = cs.getString(3);
                number = orderNo;
            }
        });
        if (number == "") {
            return jsons.json(1, 0, 0, "");
        }
        // 查询建表需要的内容
        List<feilvEntity> fe;
        List<feilvEntity> fei1 = dao.query(feilvEntity.class,
                Cnd.where("feil_mc", "=", "一级标准"));
        List<feilvEntity> fei = dao.query(feilvEntity.class,
                Cnd.where("feil_sy", "=", "1"));
        if (fei.size() != 0) {
            fe = dao.query(feilvEntity.class, Cnd.where("feil_sy", "=", "1"));
        } else if (fei1.size() != 0) {
            fe = dao.query(feilvEntity.class, Cnd.where("feil_mc", "=", "一级标准"));
        } else {
            fe = dao.query(feilvEntity.class,
                    Cnd.where("feil_mc", "=", "一级标准"), new offsetPager(1, 1));
        }
        String mc = fe.get(0).getFeil_mc();
        double fl = fe.get(0).getFeil_fl();

        List list_ = sm();
        double dos1 = 1;
        double dos2 = 1;
        if (list_.size() > 0) {
            dos1 = Double.parseDouble(list_.get(0).toString());
            dos2 = Double.parseDouble(list_.get(1).toString());
        }
        Work_pz_gzEntity result = dao.fetch(Work_pz_gzEntity.class,
                Cnd.where("work_no", "=", number));
        // for(Work_pz_gzEntity re :result){
        result.setXche_cz(caozuoyuan_xm);
        result.setDept_mc(gongsiNo);
        result.setXche_jdrq(new Date());
        result.setXche_sfbz(mc);
        result.setXche_sffl(fl);
        result.setXche_yjwgrq(time());
        result.setXche_wxjd("登记");
        result.setFlag_fast(false);
        result.setXche_ywlx("正常维修");
        result.setXche_wxxmlv(dos1 * 100);
        result.setXche_peijlv(dos2 * 100);
        result.setFlag_pad(true);
        result.setFlag_fast(true);
        result.setFlag_IsCheck(zhijian());
        result.setFlag_isxiche(xiche());

        List<Record> list = pu.getCangKuListByCaoZuoYuan(caozuoyuan_xm);
        if (list.size() > 0) {
            Record ee = list.get(0);
            result.setCangk_dm(ee.getString("cangk_dm"));
            result.setCangk_mc(ee.getString("cangk_mc"));
        }
        int num = dao.update(result);

        if (num < 1) {
            return jsons.json(1, 0, 0, "");

            // }
        }

        List<Work_pz_gzEntity> result1 = dao.query(Work_pz_gzEntity.class,
                Cnd.where("work_no", "=", number));
        // String json = Json.toJson(result1, JsonFormat.full());
        // return jsons.json(1, result1.size(), 0, json);
        return number;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private List sm() {
        Sql sql = Sqls.queryRecord("select wxxm_lv,peij_lv,* from sm_glf");
        dao.execute(sql);
        ArrayList list = new ArrayList();
        List<Record> res = sql.getList(Record.class);
        for (Record re : res) {
            list.add(re.getString("wxxm_lv"));
            list.add(re.getString("peij_lv"));
        }

        return list;

    }

    /**
     * @param xche_hjje      合计金额
     * @param xche_ssje      实收金额
     * @param xche_wxxm_yhje 项目优惠金额
     * @param xche_peij_yhje 材料优惠金额
     * @param xche_ysje      应收金额 合计金额-项目优惠金额-材料优惠金额=应收金额（优惠后金额）
     */
    private void savejsJe(String work_no, double xche_hjje, double xche_ssje,
                          double xche_wxxm_yhje, double xche_peij_yhje, double xche_ysje, boolean flag_cardjs, String zhifu_card_no, double Zhifu_card_xj, double zhifu_card_je) {
        Work_pz_gzEntity pz = dao.fetch(Work_pz_gzEntity.class, work_no);
        pz.setXche_hjje(xche_hjje);
        pz.setXche_ssje(xche_ssje);
        pz.setXche_wxxm_yhje(xche_wxxm_yhje);
        pz.setXche_peij_yhje(xche_peij_yhje);
        pz.setXche_ysje(xche_ysje);
        pz.setFlag_cardjs(flag_cardjs);
        if (flag_cardjs)
            pz.setZhifu_card_no(zhifu_card_no);
        pz.setZhifu_card_je(zhifu_card_je);
        if (!flag_cardjs)
            pz.setZhifu_card_xj(0);
        else
            pz.setZhifu_card_xj(Zhifu_card_xj);
        dao.update(pz, "^xche_hjje|xche_ssje|xche_wxxm_yhje|xche_peij_yhje|xche_ysje|flag_cardjs|zhifu_card_no|zhifu_card_je|zhifu_card_xj$");
    }

    /**
     * 2017年12月18日14:40:11
     */
    @At
    @Ok("raw:json")
    public String check(String che_no, String kehu_no) {
        if (che_no != null && kehu_no != null) {
            Sql sql = Sqls
                    .queryRecord("select kehu_no from work_cheliang_sm where kehu_no='" + kehu_no + " ' and che_no='" + che_no + "'");
            dao.execute(sql);
            List<Record> res = sql.getList(Record.class);

            if (res.size() == 0) {
                Sql sql1 = Sqls
                        .queryRecord("select che_no from work_cheliang_sm where che_no='" + che_no + "'");
                dao.execute(sql1);
                List<Record> res1 = sql1.getList(Record.class);
                if (res1.size() > 0) {
                    return jsons.json(1, 1, 0, "\"此车非此客户所有，请重新打开该单据！\"");
                } else {
                    return jsons.json(1, 1, 0, "\"此车不存在，请重新打开该单据！\"");
                }
            } else {
                return jsons.json(1, 1, 1, "\"已搜索到匹配成功\"");
            }
        }
        return jsons.json(1, 1, 0, "\"车牌号或者客户编码不能为空\"");
    }

    /**
     * 结算之前的检测
     *
     * @param work_no
     * @param caozuoyuanID
     * @return
     */
    @At
    @Ok("raw:json")
    public String checkBeforeJiesuan(String work_no, int caozuoyuanID) {
        userEntity user = dao.fetch(userEntity.class, caozuoyuanID);
        // 判断是否有美容快修单的权限
        if (!BsdUtils.isHasQx(user.getCaozuoyuan_Gndm(), "2425")) {
            return jsons.json(1, 1, 0, "您没有【快修单结算】的权限！");
        }
        List<String> tipLists = new ArrayList<>();
        Sql sql1 = Sqls.queryRecord("select count(*) as cnt from work_ckpz_gz where work_no= '" + work_no + "'");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        int cnt = res1.get(0).getInt("cnt");
        if (cnt > 0) {
            return jsons.json(1, 1, 0, "不能结算，该车尚有耗材未出库，请库房确认出库");
        }
        // 下面这个权限的情况是：有权限就不用查询了，没有权限就查询；所以先判断权限在查询
        // 判断是否有出库的权限
        if (!BsdUtils.isHasQx(user.getCaozuoyuan_Gndm(), "2408")) {
            Sql sql = Sqls.queryRecord("select count(*) as cnt from work_ll_gz where work_no='" + work_no + "' and flag_chuku=0 and isnull(flag_xz,0)=0");
            dao.execute(sql);
            List<Record> res2 = sql.getList(Record.class);
            int cnt3 = res2.get(0).getInt("cnt");
            if (cnt3 > 0) {
                return jsons.json(1, 1, 0, "您没有【配件出库】的权限！");
            }
        }
        // 下面这个权限的情况是：不管有没有权限都要查询，也就是有权限给提示，没有权限给拒绝；所以先查询再判断权限
        // 判断是否有负库存出库的权限
        Sql sql2 = Sqls
                .queryRecord("select count(*) as cnt from work_ll_gz where peij_sl>peij_kc and flag_chuku = 0 and isnull(flag_xz,0)=0 and work_no= '" + work_no + "'");
        dao.execute(sql2);
        List<Record> res2 = sql2.getList(Record.class);
        int cnt2 = res2.get(0).getInt("cnt");
        if (cnt2 > 0) {
            // 判断是否有负库存出库的权限
            if (BsdUtils.isHasQx(user.getCaozuoyuan_Gndm(), "2410")) {
                tipLists.add("配件出库数量大于库存数量");
            } else {
                return jsons.json(1, 1, 0, "您没有【负库存出库】的权限！");
            }
        }
        // 判断是否有允许维修出库价低于库存均价
        Sql sql3 = Sqls.queryRecord("select count(*) as cnt from work_ll_gz a ,kucshp_fl b where a.work_no = '"
                + work_no + "' and a.peij_no = b.peij_no and a.cangk_dm = b.cangk_dm  and a.peij_dj<b.jiag_jp");
        dao.execute(sql3);
        List<Record> res3 = sql3.getList(Record.class);
        int cnt3 = res3.get(0).getInt("cnt");
        if (cnt3 > 0) {
            if (BsdUtils.isHasQx(user.getCaozuoyuan_Gndm(), "2414")) {
                tipLists.add("有配件单价小于库存平均价");
            } else {
                return jsons.json(1, 1, 0, "您没有【允许维修出库价低于库存均价】的权限！");
            }
        }
        // 判断销价小于最低售价的配件
        Sql sql4 = Sqls.queryRecord("select sys_jiag_low,sys_SetPrice from sm_system_info");
        dao.execute(sql4);
        List<Record> res4 = sql4.getList(Record.class);
        int sys_jiag_low = res4.get(0).getInt("sys_jiag_low");
        int sys_SetPrice = res4.get(0).getInt("sys_SetPrice");
        if (sys_jiag_low == 0) { // 没有权限才判断
            Sql sql5;
            if (sys_SetPrice == 0) { // 统一定价
                sql5 = Sqls.queryRecord("select count(*) as cnt from work_ll_gz a,kucshp_zk b " +
                        "where a.peij_no = b.peij_no and isnull(a.peij_dj,0)<isnull(b.jiag_low,0) " +
                        "and a.work_no='" + work_no + "' and b.gongsino =(select gongsino from sm_gongsi where gongsi_xz = 1)");
            } else {  // 分公司设定价格
                sql5 = Sqls.queryRecord("select count(*) as cnt from work_ll_gz a,kucshp_zk b " +
                        "where a.peij_no = b.peij_no and isnull(a.peij_dj,0)<isnull(b.jiag_low,0) " +
                        "and a.work_no='" + work_no + "' and b.gongsino ='" + user.getGongSiNo() + "'");
            }
            dao.execute(sql5);
            List<Record> res5 = sql5.getList(Record.class);
            int cnt5 = res5.get(0).getInt("cnt");
            if (cnt5 > 0) {
                return jsons.json(1, 1, 0, "您没有【销价小于最低售价】的权限！");
            }
        }
        if (tipLists.size() > 0) {
            String json = Json.toJson(tipLists);
            return jsons.json(1, 1, 1, json);
        }
//        return jsons.json(1, 0, 1, ""); // 特别注意，最后一个参数永远不能为空字符，否则最后拼出来的字符串就不是json格式了
        return jsons.json(1, 0, 1, "检测成功");
    }

    /**
     * 检测优惠金额是否在优惠率之内
     * 不是，则返回fail
     * 是，就把优惠金额更新到库里面
     *
     * @param work_no
     * @param xm_yhje
     * @param cl_yhje
     * @param caozuoyuanID
     * @return
     */
    @At
    @Ok("raw:json")
    public String checkYhlv(String work_no, String xm_yhje, String cl_yhje, int caozuoyuanID) {
        userEntity user = dao.fetch(userEntity.class, caozuoyuanID);
        BigDecimal xiaoYhlv = new BigDecimal(1 - user.getXiao_yhlv() * 0.01);
        BigDecimal xmYhje = new BigDecimal(xm_yhje);
        BigDecimal clYhje = new BigDecimal(cl_yhje);
        BigDecimal totalYhje = xmYhje.add(clYhje);
        // 获取总金额，平板只用到了项目优惠和材料优惠
        Sql sql = Sqls.queryRecord("select isnull(xche_rgf,0)+isnull(xche_clf,0)+isnull(xche_wjgf,0)+isnull(xche_qtf,0)+isnull(xche_glf,0) as totalje from work_pz_gz" +
                " where work_no= '" + work_no + "'");
        dao.execute(sql);
        List<Record> res1 = sql.getList(Record.class);
        BigDecimal totalje = new BigDecimal(res1.get(0).getString("totalje"));
        // 如果优惠金额大于最大的优惠金额，则返回fail
        if (totalYhje.subtract(totalje.multiply(xiaoYhlv)).compareTo(BigDecimal.ZERO) > 0) {
            return "fail";
        }
        Sql sql1 = Sqls.create("update work_pz_gz set xche_wxxm_yhje=" + xmYhje.toString() + ",xche_peij_yhje=" + clYhje.toString() + ",xche_yhje=" + totalYhje.toString() +
                " where work_no= '" + work_no + "'");
        dao.execute(sql1);
        return "success";
    }

    @At
    @Ok("raw:json")
    public String realJiesuan(final String work_no, final String card_no, final int iscard, final String xche_ssje, String zhifu_card_je,
                              final String zhifu_card_xj, String xche_jsfs, String yh_zhanghao, final String caozuoyuan_xm,
                              final int sys_baoyang_che_fs, final int isNextTx, final String next_bylc, final String next_byrq, final String che_no) {
        // 如果没有设置账号，默认是现金
        if (yh_zhanghao == null || yh_zhanghao.equals("")) {
            yh_zhanghao = "现金";
        }
        // 更新一下pz表中的金额
        Sql sql1;
        if (iscard == 1) {
            sql1 = Sqls.create("update work_pz_gz set flag_cardjs=1,zhifu_card_no='" + card_no +
                    "',zhifu_card_je=" + zhifu_card_je + ",zhifu_card_xj=" + zhifu_card_xj +
                    ",xche_ysje=xche_hjje-xche_yhje" +
                    ",xche_ssje=" + xche_ssje +
                    ",xche_yeje=xche_hjje-xche_yhje-" + xche_ssje +
                    ",xche_jsfs='" + xche_jsfs + "'" +
                    ",yh_zhanghao='" + yh_zhanghao + "'" +
                    " where work_no = '" + work_no + "' ");
        } else {
            sql1 = Sqls.create("update work_pz_gz set flag_cardjs=0,zhifu_card_no='',zhifu_card_je=0,zhifu_card_xj=0" +
                    ",xche_ysje=xche_hjje-xche_yhje" +
                    ",xche_ssje=" + xche_ssje +
                    ",xche_yeje=xche_hjje-xche_yhje-" + xche_ssje +
                    ",xche_jsfs='" + xche_jsfs + "'" +
                    ",yh_zhanghao='" + yh_zhanghao + "'" +
                    " where work_no = '" + work_no + "' ");
        }
        dao.execute(sql1);
        // 更新付款单位
        updateFkdw(work_no);
        try {
            Trans.exec(new Atom() {
                @Override
                public void run() {
                    // 更新下次保养信息
                    updateNextTx(work_no, che_no, sys_baoyang_che_fs, isNextTx, next_bylc, next_byrq);
                    // 插入work_pz_sj
                    insertWorkPzSj(work_no);
                    // 插入work_pz_sj_ls
                    insertWorkPzSjLs(work_no);
                    // 插入work_mx_sj
                    insertWorkMxSj(work_no);
                    // 插入work_mx_sj_ls
                    insertWorkMxSjLs(work_no);
                    // 插入work_wjg_sj
                    insertWorkWjgSj(work_no);
                    // 插入check_mx_sj
                    insertCheckMxSj(work_no);
                    // 插入work_ll_sj
                    insertWorkLlSj(work_no);
                    // 插入work_ll_sj_ls
                    insertWorkLlSjLs(work_no);
                    // 插入Work_Pg_Sj
                    insertWorkPgSj(work_no);
                    // 执行Wx_lingliao_chuku的存储过程
                    if (callReturnValueProc("Wx_lingliao_chuku('" + work_no + "')") != 0) {
                        throw new RuntimeException("结算失败，存储过程--出库出错！");
                    }
                    // 执行wx_weixiu_kuan的存储过程
                    if (callReturnValueProc("wx_weixiu_kuan('" + work_no + "')") != 0) {
                        throw new RuntimeException("结算失败，存储过程--收款出错！");
                    }
                    // 执行wx_weixiu_piao的存储过程
                    if (callReturnValueProc("wx_weixiu_piao('" + work_no + "')") != 0) {
                        throw new RuntimeException("结算失败，存储过程--开票出错！");
                    }
                    dao.execute(Sqls.create("update work_yuyue_pz set yuyue_progress='已离店' where work_no='" + work_no + "'"));
                    // 处理日记账
                    updateRiJizhang(work_no, iscard, xche_ssje, zhifu_card_xj);
                    // 处理会员卡积分，如果是储值卡结算，则积分到储值卡
                    if (iscard == 1) {
                        updateCardJifen(work_no, card_no, xche_ssje, caozuoyuan_xm);
                    }
                    // 减去会员卡（单据中的会员卡）中的特殊项目
                    Sql sql8 = Sqls.queryRecord("select card_no from work_pz_sj where work_no='" + work_no + "'");
                    dao.execute(sql8);
                    List<Record> res1 = sql8.getList(Record.class);
                    String cardNo = res1.get(0).getString("card_no");
                    if (cardNo != null && !"".equals(card_no)) {
                        updateCardSpecialCS(work_no, cardNo);
                        if (iscard == 0) {
                            updateCardJifen(work_no, cardNo, xche_ssje, caozuoyuan_xm);
                        }
                    }
                }
            });
        } catch (Exception e) {
            return jsons.json(1, 1, 0, e.getMessage());
        }
        return jsons.json(1, 1, 1, "结算成功");
    }

    private void updateFkdw(String work_no) {
        dao.execute(Sqls.create("delete from work_fkdw_list where work_no = '" + work_no + "'"));
        dao.execute(Sqls.create("insert into work_fkdw_list(work_no,kehu_no,che_no,xche_fkdw_no,xche_fkdw_mc,xche_ysje,xche_ssje,xche_yhje,xche_yeje)" +
                "select work_no,kehu_no,che_no,kehu_no,kehu_mc,xche_ysje,xche_ssje,xche_yhje,xche_yeje " +
                "from work_pz_gz where work_no='" + work_no + "'"));
    }

    private void updateRiJizhang(String work_no, int iscard, String xche_ssje, String zhifu_card_xj) {
        // 处理日记账
        BigDecimal ssje = new BigDecimal(xche_ssje);
        // 如果不是会员卡支付，并且实收金额不为0
        if (iscard == 0 && ssje.compareTo(BigDecimal.ZERO) != 0) {
            Sql sql4 = Sqls.create(
                    "insert into jizhang_xjmx(" +
                            "kehu_no,yewu_jsfs,jizhang_name,jizhang_pz,jizhang_km,jizhang_jf,jizhang_rq,jizhang_jb,jizhang_cz,jizhang_bz,yewu_dh,yewu_xz,GongSiNo,GongSiMc)" +
                            "select kehu_no,xche_jsfs,yh_zhanghao,work_no,case yh_zhanghao when '现金' then '101' else '102' end,xche_ssje,getdate(),xche_jb,xche_cz,'收维修款（PAD）',work_no,'2007',gongsino,gongsimc " +
                            "from work_pz_sj where work_no='" + work_no + "'"
            );
            dao.execute(sql4);
        }
        // 如果补现金不为0，则插入日记账
        BigDecimal buxj = new BigDecimal(zhifu_card_xj);
        if (buxj.compareTo(BigDecimal.ZERO) != 0) {
            Sql sql5 = Sqls.create(
                    "insert into jizhang_xjmx(" +
                            "kehu_no,yewu_jsfs,jizhang_name,jizhang_pz,jizhang_km,jizhang_jf,jizhang_rq,jizhang_jb,jizhang_cz,jizhang_bz,yewu_dh,yewu_xz,GongSiNo,GongSiMc)" +
                            "select kehu_no,xche_jsfs,yh_zhanghao,work_no,case yh_zhanghao when '现金' then '101' else '102' end,zhifu_card_xj,getdate(),xche_jb,xche_cz,'收维修款补现金（PAD）',work_no,'2007',gongsino,gongsimc " +
                            "from work_pz_sj where work_no='" + work_no + "'"
            );
            dao.execute(sql5);
        }
    }

    private void updateNextTx(String work_no, String che_no, int sys_baoyang_che_fs, int isNextTx, String next_bylc, String next_byrq) {
        // 处理下次保养信息
        if (sys_baoyang_che_fs == 1) {
            if (isNextTx == 1) {
                if (next_bylc != null && !"".equals(next_bylc)) {
                    dao.execute(Sqls.create("update work_cheliang_sm set che_prior_licheng=che_next_licheng,che_next_licheng=" + next_bylc +
                            " where che_no ='" + che_no + "'"));
                }
                if (next_byrq != null && !"".equals(next_byrq)) {
                    dao.execute(Sqls.create("update work_cheliang_sm set flag_notsendmsg=0,che_prior_byrq=che_next_byrq,che_next_byrq='" + next_byrq + "'" +
                            " where che_no ='" + che_no + "'"));
                }
            }
        } else {
            Sql sql11 = Sqls.queryRecord("select count(*) as cnt from (" +
                    "select 1 as a from work_mx_sj where work_no='" + work_no + "' and wxxm_no in (select wxxm_no from work_weixiu_sm where isnull(wxxm_by,0)=1) " +
                    "union select 1 as a from work_mx_gz where work_no='" + work_no + "' and wxxm_no in (select wxxm_no from work_weixiu_sm where isnull(wxxm_by,0)=1)) a");
            dao.execute(sql11);
            List<Record> res11 = sql11.getList(Record.class);
            int cnt = res11.get(0).getInt("cnt");
            if (cnt > 0) {
                Sql sql12 = Sqls.queryRecord("select convert(varchar(10),isnull(che_next_byrq,getdate()),120) che_next_byrq,che_baoyanglicheng,che_next_licheng,che_rjlc " +
                        "from work_cheliang_sm where che_no='" + che_no + "'");
                dao.execute(sql12);
                List<Record> res12 = sql12.getList(Record.class);
                if (res12.size() > 0) {
                    String cheNextByrq = res12.get(0).getString("che_next_byrq");
                    BigDecimal cheBaoyanglicheng = new BigDecimal(res12.get(0).getString("che_baoyanglicheng"));
                    BigDecimal cheNextLicheng = new BigDecimal(res12.get(0).getString("che_next_licheng"));
                    BigDecimal cheRjlc = new BigDecimal(res12.get(0).getString("che_rjlc"));
                    if (cheNextByrq != null && !"".equals(cheNextByrq)
                            && cheRjlc.compareTo(BigDecimal.ZERO) != 0
                            && cheBaoyanglicheng.compareTo(BigDecimal.ZERO) != 0) {
                        int cheNextDays = cheBaoyanglicheng.divide(cheRjlc, 2, BigDecimal.ROUND_HALF_UP).intValue();
                        dao.execute(Sqls.create("update work_cheliang_sm set flag_notsendmsg=0,che_prior_byrq=che_next_byrq,che_prior_licheng=" + cheNextLicheng.toString() +
                                ",che_next_byrq=DATEADD(day," + cheNextDays + ",getdate()),che_next_licheng=isnull(che_prior_licheng,0)+" + cheBaoyanglicheng.toString() +
                                " where che_no='" + che_no + "'"));
                    }
                }
            }
        }
    }

    /**
     * 更新会员卡积分信息
     *
     * @param work_no
     * @param card_no
     * @param xche_ssje
     * @param caozuoyuan_xm
     */
    private void updateCardJifen(String work_no, String card_no, String xche_ssje, String caozuoyuan_xm) {
        // 先修改积分
        dao.execute(Sqls.create("update kehu_card set card_xiaozje=card_xiaozje+" + xche_ssje + " where card_no='" + card_no + "'"));
        Sql sql6 = Sqls.queryRecord("select Card_JifenType,Card_JifenLv from cardsysset");
        dao.execute(sql6);
        List<Record> res = sql6.getList(Record.class);
        int Card_JifenType = res.get(0).getInt("Card_JifenType");
        BigDecimal cardJifenLv = new BigDecimal(res.get(0).getString("Card_JifenLv"));
        if (Card_JifenType == 0) { // 按商品积分率
            Sql sql7 = Sqls.queryRecord("select work_jifen_sum from work_pz_sj where work_no='" + work_no + "'");
            dao.execute(sql7);
            List<Record> res1 = sql7.getList(Record.class);
            BigDecimal workJifenSum = new BigDecimal(res1.get(0).getString("work_jifen_sum"));
            dao.execute(Sqls.create("update kehu_card set card_jifen=Card_jifen+" + workJifenSum.toString() + ",card_leftjf=card_leftjf+" + workJifenSum.toString() +
                    " where card_no='" + card_no + "'"));
        } else { // 按会员卡积分率
            if (cardJifenLv.compareTo(BigDecimal.ZERO) != 0) {
                dao.execute(Sqls.create("update kehu_card set card_jifen=Card_jifen+" + xche_ssje + "*" + cardJifenLv.toString() +
                        ",card_leftjf=card_leftjf+" + xche_ssje + "*" + cardJifenLv.toString() +
                        " where card_no='" + card_no + "'"));
            }
        }
        // 在处理积分升级
        Kehu_CardEntity kehuCardEntity = dao.fetch(Kehu_CardEntity.class, card_no);
        double cardJifen = kehuCardEntity.getCard_jifen();
        boolean flagUpdate = kehuCardEntity.getFlag_update();
        String nowCardKind = kehuCardEntity.getCard_kind();
        Sql sql8 = Sqls.queryRecord("select top 1 cardkind from cardkind where card_jfmin<=" + cardJifen + " order by card_jfmin desc");
        dao.execute(sql8);
        List<Record> res2 = sql8.getList(Record.class);
        String updateCardkind = res2.get(0).getString("cardkind");
        if (flagUpdate && !"".equals(updateCardkind) && !nowCardKind.equals(updateCardkind)) {
            Sqls.create("insert into cardkindrename(card_no,oldcard_kind,newcard_kind,czy,ddate,demo)" +
                    "values('" + card_no + "','" + nowCardKind + "','" + updateCardkind + "','" + caozuoyuan_xm + "',getdate(),'会员积分升级')");
            Sqls.create("update kehu_card set card_kind='" + updateCardkind + "' where card_no='" + card_no + "'");
        }
    }

    /**
     * 更新特殊项目次数
     *
     * @param work_no
     * @param cardNo
     */
    private void updateCardSpecialCS(String work_no, String cardNo) {
        // 处理特殊维修项目
        Sql sql9 = Sqls.queryRecord("select wxxm_no from work_mx_sj where work_no='" + work_no + "'");
        dao.execute(sql9);
        List<Record> list = sql9.getList(Record.class);
        for (Record record : list) {
            String wxxm_no = record.getString("wxxm_no");
            Sql sql10 = Sqls
                    .queryRecord("select count(*) as cnt from kehu_carddetail where card_no='" + cardNo + "' and wxxm_no='" + wxxm_no + "' and wxxm_cs>wxxm_yqcs");
            dao.execute(sql10);
            List<Record> res = sql10.getList(Record.class);
            if (res.size() > 0) {
                dao.execute(Sqls.create("update kehu_carddetail set wxxm_yqcs=wxxm_yqcs+1 where card_no='" + cardNo + "' and wxxm_no='" + wxxm_no + "'"));
                dao.execute(Sqls.create("update work_mx_sj set Flag_WxxcCs = 1 where work_no ='" + work_no + "' and wxxm_no='" + wxxm_no + "'"));
            }
        }
        // 处理特殊配件项目
        Sql sql11 = Sqls.queryRecord("select peij_no,peij_sl from work_ll_sj where work_no='" + work_no + "'");
        dao.execute(sql11);
        List<Record> list1 = sql11.getList(Record.class);
        for (Record record : list1) {
            String peij_no = record.getString("peij_no");
            BigDecimal peijSl = new BigDecimal(record.getString("peij_sl"));
            Sql sql12 = Sqls
                    .queryRecord("select isnull(peij_card_sl,0)-isnull(peij_card_yqsl,0) as canusesl " +
                            "from kehu_carddetailpeij where card_no='" + cardNo + "' and peij_no='" + peij_no + "' and peij_card_sl>peij_card_yqsl");
            dao.execute(sql12);
            List<Record> res2 = sql12.getList(Record.class);
            if (res2.size() > 0) {
                BigDecimal canusesl = new BigDecimal(res2.get(0).getString("canusesl"));
                if (canusesl.subtract(peijSl).compareTo(BigDecimal.ZERO) >= 0) {
                    dao.execute(Sqls.create("update kehu_carddetailpeij set peij_card_yqsl=peij_card_yqsl+" + peijSl.toString() + " where card_no='" + cardNo + "' and peij_no='" + peij_no + "'"));
                    dao.execute(Sqls.create("update work_ll_sj set Flag_peijcardSL=" + peijSl.toString() + " where work_no ='" + work_no + "' and peij_no='" + peij_no + "'"));
                } else {
                    dao.execute(Sqls.create("update kehu_carddetailpeij set peij_card_yqsl=peij_card_sl where card_no='" + cardNo + "' and peij_no='" + peij_no + "'"));
                    dao.execute(Sqls.create("update work_ll_sj set Flag_peijcardSL=" + canusesl.toString() + " where work_no ='" + work_no + "' and peij_no='" + peij_no + "'"));
                }
            }

        }
    }


    @At
    @Ok("raw:json")
    public int execWxLingliaoChuku(String work_no) {
//        runCall("Wx_lingliao_chuku('" + work_no + "')");
        // 尝试1：
//        Sql sql = Sqls.queryRecord("declare @@rerturn int exec @@rerturn=Wx_lingliao_chuku '" + work_no + "' select @@rerturn as result");
//        dao.execute(sql);
//        List<Record> list = sql.getList(Record.class); // 这里的list的size永远是0，失败
//        int result = list.get(0).getInt("result");
        // 尝试2：
//        Sql sql = Sqls.create("{call Wx_lingliao_chuku(@work_no)}");
//        sql.params().set("work_no", work_no);
//        dao.execute(sql);
//        Record outParams = sql.getOutParams(); // 这个方法获取的一直null， 失败
//        System.out.println(outParams);
        // 尝试3：
//        Sql sql = Sqls.create("{call $returnValue=Wx_lingliao_chuku(@work_no)}");
//        sql.vars().set("returnValue", "@value");  // 这种方式运行直接报错
//        sql.params().set("work_no", work_no);
//        dao.execute(sql);

        int i = callReturnValueProc("Wx_lingliao_chuku('" + work_no + "')");

//        runCall();
        return i;
    }

    /**
     * 调用存储过程，并返回存储过程的return值，此值是int类型
     *
     * @param procSql 存储过程和其参数的sql语句
     * @return 存储过程的返回值
     */
    private int callReturnValueProc(final String procSql) {
        final int[] returnValue = new int[1];
        dao.run(new ConnCallback() {
            @Override
            public void invoke(java.sql.Connection conn) throws Exception {
                CallableStatement cs = conn.prepareCall("{? = call " + procSql + "}");
                cs.registerOutParameter(1, Types.INTEGER);
                cs.execute();
                returnValue[0] = cs.getInt(1);
            }
        });
        return returnValue[0];
    }

    private void insertWorkPgSj(String work_no) {
        Sql sql = Sqls.create(
                "insert into Work_Pg_Sj(work_no, wxxm_no, reny_no, reny_mc, wxry_bm, wxry_cj," +
                        "wxry_bz, paig_khgs, paig_khje, paig_pgsj, paig_bz, wxry_pg)" +
                        "select work_no, wxxm_no, reny_no, reny_mc, wxry_bm, wxry_cj," +
                        "wxry_bz, paig_khgs, paig_khje, paig_pgsj, paig_bz, wxry_pg " +
                        "from Work_Pg_Gz where work_no='" + work_no + "' order by reco_no");
        dao.execute(sql);
    }

    private void insertWorkLlSjLs(String work_no) {
        Sql sql = Sqls.create(
                "insert into work_ll_sj_ls(" +
                        "work_no, peij_no, peij_sl, peij_dj, peij_je, peij_cb, cangk_dm, peij_zt," +
                        "peij_hw, peij_ry, peij_bz,peij_ydj, peij_yje, peij_zk," +
                        "peij_sl_ls, peij_dw_ls, peij_dj_ls, danwei_bs_ls, reco_no1, flag_xz,peij_tcfs,peij_tc,peij_tcje,peij_jifen,peij_jifenlv," +
                        "peij_mc,peij_th,peij_dw,peij_cx,peij_pp,peij_cd)" +
                        "select work_no,peij_no,peij_sl,peij_dj,peij_je,peij_cb,cangk_dm,peij_zt," +
                        "peij_hw,peij_ry,peij_bz,peij_ydj, peij_yje, peij_zk," +
                        "peij_sl_ls, peij_dw, peij_dj_ls, danwei_bs_ls, reco_no, flag_xz,peij_tcfs,peij_tc,peij_tcje,peij_jifen,peij_jifenlv," +
                        "peij_mc,peij_th,peij_dw,peij_cx,peij_pp,peij_cd " +
                        "from work_ll_gz where work_no='" + work_no + "' order by reco_no");
        dao.execute(sql);
    }

    private void insertWorkLlSj(String work_no) {
        Sql sql = Sqls.create(
                "insert into work_ll_sj(" +
                        "work_no, peij_no, peij_sl, peij_dj, peij_je, peij_cb, peij_cbje, cangk_dm, peij_zt," +
                        "peij_hw, peij_ry, peij_bz, flag_import, have_import, flag_chuku, peij_ydj, peij_yje, peij_zk," +
                        "peij_sl_ls, peij_dw_ls, peij_dj_ls, danwei_bs_ls, reco_no1, flag_xz,peij_tcfs,peij_tc,peij_tcje,peij_jifen,peij_jifenlv)" +
                        "select work_no,peij_no,peij_sl,peij_dj,peij_je,peij_cb,peij_cbje,cangk_dm,peij_zt,peij_hw,peij_ry,peij_bz," +
                        "flag_import, have_import, flag_chuku, peij_ydj, peij_yje, peij_zk," +
                        "peij_sl_ls, peij_dw, peij_dj_ls, danwei_bs_ls, reco_no, flag_xz,peij_tcfs,peij_tc,peij_tcje,peij_jifen,peij_jifenlv " +
                        "from work_ll_gz where work_no='" + work_no + "' order by reco_no");
        dao.execute(sql);
    }

    private void insertCheckMxSj(String work_no) {
        Sql sql = Sqls.create(
                "insert into check_mx_sj(work_no,check_project,flag_normal,flag_adjust,flag_suggest_replace,kehu_view)" +
                        "select work_no,check_project,flag_normal,flag_adjust,flag_suggest_replace,kehu_view " +
                        "from check_mx_gz where work_no='" + work_no + "' order by reco_no");
        dao.execute(sql);
    }

    private void insertWorkWjgSj(String work_no) {
        Sql sql = Sqls.create(
                "insert into work_wjg_sj(work_no, wxxm_no, wxxm_mc, wxxm_cb, wxxm_je, wxxm_bz)" +
                        "select work_no, wxxm_no, wxxm_mc, wxxm_cb, wxxm_je, wxxm_bz " +
                        "from work_wjg_gz where work_no='" + work_no + "' order by reco_no");
        dao.execute(sql);
    }

    private void insertWorkMxSjLs(String work_no) {
        Sql sql = Sqls.create(
                "insert into work_mx_sj_ls(work_no, wxxm_no, wxxm_mc, wxxm_gs, wxxm_khgs, wxxm_cb, wxxm_je, wxxm_ry," +
                        "wxxm_zt, wxxm_bz, wxxm_zk,wxxm_yje,flag_xz,wxxm_mxcx,wxxm_dj,wxxm_tcfs,wxxm_tc,wxxm_tcje, wxxm_bjrq,wxxm_jifen,wxxm_jifenlv)" +
                        "select work_no, wxxm_no, wxxm_mc, wxxm_gs, wxxm_khgs, wxxm_cb, wxxm_je, wxxm_ry," +
                        "wxxm_zt, wxxm_bz, wxxm_zk,wxxm_yje,flag_xz,wxxm_mxcx,wxxm_dj,wxxm_tcfs,wxxm_tc,wxxm_tcje, wxxm_bjrq,wxxm_jifen,wxxm_jifenlv " +
                        "from work_mx_gz where work_no='" + work_no + "' order by reco_no");
        dao.execute(sql);
    }

    private void insertWorkMxSj(String work_no) {
        Sql sql = Sqls.create(
                "insert into work_mx_sj(" + "work_no, wxxm_no, wxxm_mc, wxxm_gs, wxxm_khgs, wxxm_cb, wxxm_je, wxxm_ry," +
                        "wxxm_zt, wxxm_bz, wxxm_zk,wxxm_yje,flag_xz,wxxm_mxcx,wxxm_dj,wxxm_tcfs,wxxm_tc,wxxm_tcje, wxxm_bjrq,wxxm_jifen,wxxm_jifenlv)" +
                        "select work_no, wxxm_no, wxxm_mc, wxxm_gs, wxxm_khgs, wxxm_cb, wxxm_je, wxxm_ry," +
                        "wxxm_zt, wxxm_bz, wxxm_zk,wxxm_yje,flag_xz,wxxm_mxcx,wxxm_dj,wxxm_tcfs,wxxm_tc,wxxm_tcje, wxxm_bjrq,wxxm_jifen,wxxm_jifenlv " +
                        "from work_mx_gz where work_no='" + work_no + "' order by reco_no");
        dao.execute(sql);
    }

    private void insertWorkPzSjLs(String work_no) {
        Sql sql = Sqls.create(
                "insert into work_pz_sj_ls (" +
                        "work_no, kehu_no, kehu_sj, che_no, xche_sfbz, xche_sffl, xche_lc, xche_cy," +
                        "xche_cclc, xche_last_lc,xche_last_jdrq,xche_jdrq, xche_gj, xche_wxjd,xche_yjwgrq, xche_wgrq, xche_pgcz, xche_jlr, xche_jlrq, xche_jsr, xche_jsrq," +
                        "xche_fpfs, xche_fplv, xche_rgf, xche_rgsl, xche_rgse, xche_rgbh, xche_clf, xche_clsl, xche_clse," +
                        "xche_clbh, xche_glf, xche_wjgf, xche_wjgcb, xche_qtf, xche_yhje, xche_ysje, xche_rgcb, xche_cb," +
                        "xche_zxr, xche_qtxm, xche_wjgx, xche_yhyy, xche_gdfl, xche_wxfl, xche_bz, xche_cz, xche_jb, xche_jcr," +
                        "xche_hjje, xche_ssje, xche_yeje, xche_jsfs, dept_mc, flag_fast, card_no, card_kind, flag_cardjs," +
                        "zhifu_card_no, zhifu_card_je,xche_kpje,xche_kpse,xche_fpno,zhifu_card_xj," +
                        "xche_sbclf,xche_sbgsf,xche_sbgss,xche_sbgsff,xche_sbclcb,xche_bxclf,xche_bxclcb,xche_bxgsf,xche_bxgss,xche_bxgscb," +
                        "card_itemrate,card_peijrate, gongsino, gongsimc, xche_wxxmlv, xche_peijlv,xche_sjhk,xche_xzrgf_je,xche_xzclf_je," +
                        "wxxm_tcje_sum,wxxm_zztcje_sum,peij_tcje_sum,che_zjno,xche_wxxm_yhje,xche_peij_yhje,work_jifen_sum,cangk_dm,xche_saobmianshou,xche_bxmianshou,xche_zengsmianshou," +
                        "kehu_mc,kehu_xm,kehu_dz,kehu_dh,che_vin,che_fd,che_wxys,che_cx)" +
                        "select work_no,kehu_no,kehu_sj,che_no,xche_sfbz,xche_sffl,xche_lc,xche_cy," +
                        "isnull(xche_lc,0), isnull(xche_last_lc,0),xche_last_jdrq,xche_jdrq,xche_gj,xche_wxjd, xche_yjwgrq,xche_jsrq," +
                        "xche_pgcz,xche_jlr,xche_jlrq,xche_jsr, xche_jsrq,xche_fpfs,xche_fplv,xche_rgf,xche_rgsl,xche_rgse,xche_rgbh," +
                        "xche_clf,xche_clsl,xche_clse,xche_clbh,xche_glf,xche_wjgf,xche_wjgcb,xche_qtf,xche_yhje,xche_ysje,xche_rgcb,xche_cb," +
                        "xche_zxr,xche_qtxm,xche_wjgx,xche_yhyy,xche_gdfl,xche_wxfl,xche_bz,xche_cz,xche_jb,xche_jcr,xche_hjje,xche_ssje," +
                        "xche_yeje,xche_jsfs,dept_mc,isnull(flag_fast,0),card_no,card_kind,flag_cardjs,zhifu_card_no," +
                        "zhifu_card_je,xche_kpje,xche_kpse,xche_fpno,zhifu_card_xj," +
                        "xche_sbclf,xche_sbgsf,xche_sbgss,xche_sbgsff,xche_sbclcb,xche_bxclf,xche_bxclcb,xche_bxgsf,xche_bxgss,xche_bxgscb," +
                        "card_itemrate,card_peijrate,gongsino,gongsimc," +
                        "xche_wxxmlv,xche_peijlv,xche_sjhk,xche_xzrgf_je,xche_xzclf_je,wxxm_tcje_sum,wxxm_zztcje_sum,peij_tcje_sum,che_zjno," +
                        "xche_wxxm_yhje,xche_peij_yhje,work_jifen_sum,cangk_dm,xche_saobmianshou,xche_bxmianshou,xche_zengsmianshou," +
                        "kehu_mc,kehu_xm,kehu_dz,kehu_dh,che_vin,che_fd,che_wxys,che_cx " +
                        "from work_pz_gz where work_no='" + work_no + "'"
        );
        dao.execute(sql);
    }

    /**
     * 插入work_pz_sj
     *
     * @param work_no
     */
    private void insertWorkPzSj(String work_no) {
        Sql sql = Sqls.create(
                "insert into work_pz_sj (" +
                        "work_no, kehu_no, kehu_sj, che_no, xche_sfbz, xche_sffl, xche_lc, xche_cy," +
                        "xche_cclc, xche_last_lc,xche_last_jdrq,xche_jdrq, xche_gj, xche_wxjd,xche_yjwgrq, xche_wgrq, xche_pgcz, xche_jlr, xche_jlrq, xche_jsr, xche_jsrq," +
                        "xche_fpfs, xche_fplv, xche_rgf, xche_rgsl, xche_rgse, xche_rgbh, xche_clf, xche_clsl, xche_clse," +
                        "xche_clbh, xche_glf, xche_wjgf, xche_wjgcb, xche_qtf, xche_yhje, xche_ysje, xche_rgcb, xche_cb," +
                        "xche_zxr, xche_qtxm, xche_wjgx, xche_yhyy, xche_gdfl, xche_wxfl, xche_bz, xche_cz, xche_jb, xche_jcr," +
                        "xche_hjje, xche_ssje, xche_yeje, xche_jsfs, dept_mc, flag_fast, card_no, card_kind, flag_cardjs," +
                        "zhifu_card_no, zhifu_card_je,xche_kpje,xche_kpse,xche_fpno,zhifu_card_xj," +
                        "xche_sbclf,xche_sbgsf,xche_sbgss,xche_sbgsff,xche_sbclcb,xche_bxclf,xche_bxclcb,xche_bxgsf,xche_bxgss,xche_bxgscb," +
                        "card_itemrate,card_peijrate, gongsino, gongsimc, xche_wxxmlv, xche_peijlv,yh_zhanghao,xche_sjhk,xche_xzrgf_je,xche_xzclf_je," +
                        "wxxm_tcje_sum,wxxm_zztcje_sum,peij_tcje_sum,che_zjno,xche_wxxm_yhje,xche_peij_yhje,work_jifen_sum,cangk_dm,xche_saobmianshou,xche_bxmianshou,xche_zengsmianshou)" +
                        "select work_no,kehu_no,kehu_sj,che_no,xche_sfbz,xche_sffl,xche_lc,xche_cy," +
                        "isnull(xche_lc,0), isnull(xche_last_lc,0),xche_last_jdrq,xche_jdrq,xche_gj,xche_wxjd, xche_yjwgrq,xche_jsrq," +
                        "xche_pgcz,xche_jlr,xche_jlrq,xche_jsr, xche_jsrq,xche_fpfs,xche_fplv,xche_rgf,xche_rgsl,xche_rgse,xche_rgbh," +
                        "xche_clf,xche_clsl,xche_clse,xche_clbh,xche_glf,xche_wjgf,xche_wjgcb,xche_qtf,xche_yhje,xche_ysje,xche_rgcb,xche_cb," +
                        "xche_zxr,xche_qtxm,xche_wjgx,xche_yhyy,xche_gdfl,xche_wxfl,xche_bz,xche_cz,xche_jb,xche_jcr,xche_hjje,xche_ssje," +
                        "xche_yeje,xche_jsfs,dept_mc,isnull(flag_fast,0),card_no,card_kind,flag_cardjs,zhifu_card_no," +
                        "zhifu_card_je,xche_kpje,xche_kpse,xche_fpno,zhifu_card_xj," +
                        "xche_sbclf,xche_sbgsf,xche_sbgss,xche_sbgsff,xche_sbclcb,xche_bxclf,xche_bxclcb,xche_bxgsf,xche_bxgss,xche_bxgscb," +
                        "card_itemrate,card_peijrate,gongsino,gongsimc," +
                        "xche_wxxmlv,xche_peijlv,yh_zhanghao,xche_sjhk,xche_xzrgf_je,xche_xzclf_je,wxxm_tcje_sum,wxxm_zztcje_sum,peij_tcje_sum,che_zjno," +
                        "xche_wxxm_yhje,xche_peij_yhje,work_jifen_sum,cangk_dm,xche_saobmianshou,xche_bxmianshou,xche_zengsmianshou " +
                        "from work_pz_gz where work_no='" + work_no + "'"
        );
        dao.execute(sql);
    }

    /**
     * 美容快修的结算 1验证密码，0不验证密码
     *
     * @param caozuoyuanid
     * @param work_no
     * @param che_no
     * @param xche_hjje      合计金额
     * @param xche_ssje      实收金额
     * @param xche_wxxm_yhje 项目优惠金额
     * @param xche_peij_yhje 材料优惠金额
     * @param xche_ysje      应收金额
     * @return
     */
    @At
    @Ok("raw:json")
    public String jiesuan_1(String caozuoyuanid, String work_no, String che_no,
                            double xche_hjje, double xche_ssje, double xche_wxxm_yhje,
                            double xche_peij_yhje, double xche_ysje, String card_no,
                            String pass, int iscard, double Zhifu_card_xj, double zhifu_card_je) {
        List<String> tipLists = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        if (pass == null)
            pass = "";
        //=====================================修改cjn===================================================================
        Sql sqlcjn1 = Sqls
                .queryRecord("update b set b.xche_rgf = a.xche_rgf,b.xche_rgbh=a.xche_rgf,b.xche_rgsl=0,b.xche_rgse=0 from work_pz_gz b,"
                        + "(select work_no,sum(wxxm_je) xche_rgf from work_mx_gz where work_no='" + work_no + "' and wxxm_zt in ('正常','保险')"
                        + " group by work_no) a   where b.work_no = '" + work_no + "' and a.work_no = b.work_no");
        dao.execute(sqlcjn1);

        Sql sqlcjn2 = Sqls
                .queryRecord("update b set b.xche_clf = a.xche_clf,b.xche_clbh=a.xche_clf,b.xche_clsl=0,b.xche_clse=0 from work_pz_gz b,(select work_no,sum(peij_je) "
                        + "xche_clf from work_ll_gz where work_no='" + work_no + "' and peij_zt in ('正常','保险')group by work_no ) a "
                        + "  where b.work_no = '" + work_no + "' and a.work_no = b.work_no");
        dao.execute(sqlcjn2);
        Sql sqlcjn3 = Sqls
                .queryRecord("update work_pz_gz set xche_yhje = isnull(xche_wxxm_yhje,0)+isnull(xche_peij_yhje,0) where work_no = '" + work_no + "'");
        dao.execute(sqlcjn3);
        //=====================================修改cjn===================================================================
        if (1 == iscard) {
            Kehu_CardEntity kehuCard = card.getVipCard(card_no);
            if (kehuCard == null) {
                return jsons.json(1, 1, 0, "卡不存在");
            } else if (!pass.equals(kehuCard.getCard_password())) {
                return jsons.json(1, 1, 0, "储值卡密码错误");
            }
            if (Zhifu_card_xj < 0) {
                return jsons.json(1, 1, 0, "金额错误");
            }
            if (Zhifu_card_xj + zhifu_card_je != xche_ysje) {
                return jsons.json(1, 1, 0, "金额错误");
            }
            if (kehuCard.getCard_leftje() < zhifu_card_je) {
                return jsons.json(1, 1, 0, "实收金额不能大于储值卡剩余金额！");
            }
        }
        savejsJe(work_no, xche_hjje, xche_ssje, xche_wxxm_yhje, xche_peij_yhje,
                xche_ysje, iscard == 0 ? false : true, card_no, Zhifu_card_xj, zhifu_card_je);
        // 1.//
        Sql sql1 = Sqls.queryRecord("select count(*) as cnt  from work_ckpz_gz where work_no= '" + work_no + "'");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        int cnt = res1.get(0).getInt("cnt");
        if (cnt > 0)
            return jsons.json(1, 1, 0, "\"不能结算，该车尚有耗材未出库，请库房确认出库\"");
        // 2.//当前操作员
        List<userEntity> list_user = dao.query(
                userEntity.class,
                Cnd.where("name", "=", caozuoyuanid).and("caozuoyuan_gndm",
                        "like", "%2425%"));
        if (list_user.size() > 0) {

            userEntity user = list_user.get(0);
            double Xiao_Yhlv = user.getXiao_yhlv() / 100;
            Work_pz_gzEntity work_pz = dao.fetch(Work_pz_gzEntity.class,
                    work_no);
            if (work_pz.getXche_rgf() >= 0) {
                if (work_pz.getXche_yhje() > (work_pz.getXche_rgf() + 0.1)) {
                    return jsons.json(1, 1, 0,
                            "\"不能结算，手工优惠金额超出了本操作员的“整单折扣率”范围，请检查！\"");
                }
            } else {
                if (work_pz.getXche_yhje() < (work_pz.getXche_rgf() - 0.1)) {
                    return jsons.json(1, 1, 0,
                            "\"不能结算，手工优惠金额超出了本操作员的“整单折扣率”范围，请检查！\"");
                }
            }
            // 3.//
            // 判断库存
            Sql sql2 = Sqls
                    .queryRecord("select count(*) as cnt from work_ll_gz where peij_sl>peij_kc and flag_chuku = 0 and isnull(flag_xz,0)=0 and work_no= '" + work_no + "'");
            dao.execute(sql2);
            List<Record> res2 = sql2.getList(Record.class);
            int cnt2 = res2.get(0).getInt("cnt");

            // 负库存出库的权限
            int Flag_FuKucunXiaoShou = 0;
            if (user.getCaozuoyuan_Gndm().indexOf("2410") > 0) {
                Flag_FuKucunXiaoShou = 1;
            }
            // 操作员权限判断
            // 是否有低于库存平均价出库权限
            int Flag_WXPriceUnderJp = 0;
            if (user.getCaozuoyuan_Gndm().indexOf("2414") > 0) {
                Flag_WXPriceUnderJp = 1;
            }

            if (cnt2 > 0) {
                if (Flag_FuKucunXiaoShou == 1) {
                    tipLists.add("出库数量大于库存数量，是否继续结算");
                    sb.append("存在出库数量大于库存数量的配件！").append("\n");
                } else {
                    return jsons.json(1, 1, 0, "\"您没有负库存销售的权限，不能结算\"");
                }
            }
            Sql sql5 = Sqls
                    .queryRecord("select top 1 a.peij_dj,b.jiag_jp, *  from work_ll_gz a ,kucshp_fl b where a.work_no = '"
                            + work_no + "'  and a.peij_no = b.peij_no and a.cangk_dm = b.cangk_dm  and a.peij_dj < b.jiag_jp ");
            dao.execute(sql5);
            List<Record> res5 = sql5.getList(Record.class);
            if (res5.size() > 0) {
                if (Flag_WXPriceUnderJp == 1) {
                    tipLists.add("有配件单价小于库存平均价,是否结算");
                    sb.append("存在单价小于库存平均价的配件!").append("\n");
                } else {
                    return jsons.json(1, 1, 0, "\"您没有低于库存平均价出库权限，不能结算\"");
                }
            }
            // 真正的结算，结算之前需要吧tips返回去，让客户端确定是否继续
            if (!sb.toString().equals("")) {  // 如果有提示，则返回，让客户端确定
                return jsons.json(2, 1, 0, sb.toString());
            } else { // 没有提示，就直接结算了
                jiesuan_3(caozuoyuanid, work_no, che_no, 0);
            }

        }
        return jsons.json(1, 1, 0, "\"操作员不存在\"");
    }

    /**
     * 美容快修的结算
     *
     * @param caozuoyuanid
     * @param work_no
     * @param che_no
     * @return
     */
    @At
    @Ok("raw:json")
    public String jiesuan_2(String caozuoyuanid, String work_no, String che_no) {
        // 当前操作员
        List<userEntity> list_user = dao.query(userEntity.class,
                Cnd.where("name", "=", caozuoyuanid));
        if (list_user.size() > 0) {
            userEntity user = list_user.get(0);
            // 操作员权限判断
            int Flag_WXPriceUnderJp = 0;
            if (user.getCaozuoyuan_Gndm().indexOf("2414") > 0) // 是否有低于库存平均价出库权限
                Flag_WXPriceUnderJp = 1;
            else
                Flag_WXPriceUnderJp = 0;
            // 4.//
            int Flag_WeiXiuChuKu = 0;
            if (user.getCaozuoyuan_Gndm().indexOf("2488") > 0)// --如果有负库存出库的
                Flag_WeiXiuChuKu = 1;
            else
                Flag_WeiXiuChuKu = 0;

            // if (Flag_WeiXiuChuKu == 0) // --如果没有配件出库的权限，结算时如果有配件没出库，则不让结算
            // {
            // Sql sql3 = Sqls
            // .queryRecord("select count(*) as cnt  from work_ll_gz where work_no='"
            // + work_no
            // + "' and flag_chuku=0 and isnull(flag_xz,0)=0 ");
            // dao.execute(sql3);
            // List<Record> res3 = sql3.getList(Record.class);
            // int cnt3 = res3.get(0).getInt("cnt");
            //
            // if (cnt3 > 0) {
            // return jsons.json(1, 1, 0, "\"有配件还没有出库，请出库后再进行结算！\"");
            // }
            //
            // }
            // 5.//
            Sql sql5 = Sqls
                    .queryRecord("select top 1 a.peij_dj,b.jiag_jp, *  from work_ll_gz a ,kucshp_fl b where a.work_no = '"
                            + work_no
                            + "'  and a.peij_no = b.peij_no and a.cangk_dm = b.cangk_dm  and a.peij_dj < b.jiag_jp ");
            dao.execute(sql5);
            List<Record> res5 = sql5.getList(Record.class);
            if (res5.size() > 0) {
                if (Flag_WXPriceUnderJp == 1)
                    return jsons.json(4, 1, 1, "\"有配件单价小于库存平均价,是否继续！\"");
                else
                    return jsons.json(1, 1, 0, "\"有配件单价小于库存平均价,不能结算！\"");
            } else {
                return jsons.json(3, 1, 1, "\"是否继续？\"");
            }
        }
        return jsons.json(1, 1, 0, "\"操作员不存在\"");
    }

    /**
     * 美容快修的结算
     *
     * @param caozuoyuanid
     * @param work_no
     * @param che_no
     * @param isPrint
     * @return
     */
    @At
    @Ok("raw:json")
    public String jiesuan_3(String caozuoyuanid, String work_no, String che_no, int isPrint) {
        // 当前操作员
        List<userEntity> list_user = dao.query(userEntity.class,
                Cnd.where("name", "=", caozuoyuanid));
        if (list_user.size() > 0) {
            userEntity user = list_user.get(0);
            // 6.//
            Sql sql6 = Sqls
                    .queryRecord("select sys_jiag_low,sys_SetPrice from sm_system_info");
            dao.execute(sql6);
            List<Record> res6 = sql6.getList(Record.class);
            int sys_jiag_low = res6.get(0).getInt("sys_jiag_low");
            int sys_SetPrice = res6.get(0).getInt("sys_SetPrice");
            if (sys_jiag_low == 0) {
                Sql sql61 = Sqls
                        .queryRecord("select gongsino from work_pz_gz where work_no = '" + work_no + "'");
                dao.execute(sql61);
                List<Record> res61 = sql61.getList(Record.class);
                if (res61 != null && res61.size() > 0) {
                    String gongsino = res61.get(0).getString("gongsino");
                    if (sys_SetPrice == 0) {
                        Sql sql62 = Sqls.queryRecord("select  gongsino from sm_gongsi where gongsi_xz = 1");
                        dao.execute(sql62);
                        List<Record> res62 = sql62.getList(Record.class);
                        gongsino = res62.get(0).getString("gongsino");
                    }

                    Sql sql62 = Sqls
                            .queryRecord("select count(*) as cnt from work_ll_gz a,kucshp_zk b where a.peij_no = b.peij_no and isnull(a.peij_dj,0) < isnull(b.jiag_low,0) and a.work_no = '"
                                    + work_no + "' and  b.gongsino = " + gongsino);
                    dao.execute(sql62);
                    List<Record> res62 = sql62.getList(Record.class);
                    int cnt62 = res62.get(0).getInt("cnt");
                    if (cnt62 > 0)
                        return jsons.json(1, 1, 0, "\"本单据中有销价小于最低售价的配件，不能完成结算！\"");
                }
            }
            // 7.//

            Sql sql71 = Sqls.queryRecord("select Card_JifenType,Card_JiFenlv from cardsysset");
            dao.execute(sql71);
            List<Record> res71 = sql71.getList(Record.class);
            String Card_JifenType = res71.get(0).getString("Card_JifenType");
            String Card_JiFenlv = res71.get(0).getString("Card_JiFenlv");

            Sql sql72 = Sqls
                    .queryRecord("update work_pz_gz set work_jifen_sum= (select isnull(xche_ssje,0) from work_pz_gz where work_no = '"
                            + work_no + "') * " + Card_JiFenlv + " where Work_No='" + work_no + "'");
            dao.execute(sql72);
            // 8.//
            Sql sql81 = Sqls
                    .queryRecord("select count(*) as cnt from ( select 1 as a  from work_mx_sj where work_no = '"
                            + work_no
                            + "' and  wxxm_no in (select wxxm_no from work_weixiu_sm where  isnull(wxxm_by,0)  = 1 ) union   select 1 as a  from work_mx_gz where work_no = '"
                            + work_no
                            + "' and  wxxm_no in (select wxxm_no from work_weixiu_sm where  isnull(wxxm_by,0)  = 1 ) )  a");
            dao.execute(sql81);
            List<Record> res81 = sql81.getList(Record.class);
            int cnt81 = res81.get(0).getInt("cnt");

            if (cnt81 > 0) {
                Sql sql82 = Sqls
                        .queryRecord("select convert(varchar(10),isnull(che_next_byrq,getdate()),120) as Che_Next_byrq ,isnull(che_baoyanglicheng,0) as che_baoyanglicheng,isnull(che_next_licheng,0) as che_next_licheng from work_cheliang_sm  where che_no = '"
                                + che_no + "'");
                dao.execute(sql82);
                List<Record> res82 = sql82.getList(Record.class);
                String Che_Next_byrq = res82.get(0).getString("Che_Next_byrq");
                String che_baoyanglicheng = res82.get(0).getString(
                        "che_baoyanglicheng");
                String che_next_licheng = res82.get(0).getString(
                        "che_next_licheng");
                if (!"".equals(Che_Next_byrq)) {
                    Sql sql83 = Sqls
                            .queryRecord("select isnull(che_rjlc,0) as che_rjlc,isnull(che_baoyanglicheng,0) as Che_ByZq from work_cheliang_sm  where che_no = '"
                                    + che_no + "'");
                    dao.execute(sql83);
                    List<Record> res83 = sql83.getList(Record.class);
                    int che_rjlc = res83.get(0).getInt("che_rjlc");
                    int Che_ByZq = res83.get(0).getInt("Che_ByZq");
                    if ((che_rjlc != 0) && (Che_ByZq != 0)) {
                        double Che_NextDays = Che_ByZq / che_rjlc;
                        Sql sql84 = Sqls
                                .queryRecord("update work_cheliang_sm set flag_notsendmsg = 0, che_prior_byrq = che_next_byrq ,che_prior_licheng = "
                                        + che_next_licheng
                                        + " where  che_no = '" + che_no + "'");
                        dao.execute(sql84);
                        Sql sql85 = Sqls
                                .queryRecord("update work_cheliang_sm set  che_next_byrq =  DATEADD(day, "
                                        + Che_NextDays
                                        + ", getdate()), che_next_licheng = isnull(che_prior_licheng,0)+ "
                                        + che_baoyanglicheng
                                        + " where che_no = '" + che_no + "'");
                        dao.execute(sql85);
                    }
                }
            }
            // 9.//
            if (isPrint == 1) {
                Sql sql90 = Sqls
                        .queryRecord("select printpc from PrintServerset where billkind = '快修单'");
                dao.execute(sql90);
                List<Record> res190 = sql90.getList(Record.class);
                if (res190 != null && res190.size() > 0) {
                    String jgss190 = res190.get(0).getString("printpc");
                    Sql sql9 = Sqls
                            .queryRecord("insert into PrintServerLog(printdate,billkind,billno,printczy,printpc,printsource,flag_print )  values (getdate(),'快修单','"
                                    + work_no
                                    + "','"
                                    + caozuoyuanid
                                    + "','"
                                    + jgss190
                                    + "',1,0)");
                    dao.execute(sql9);
                }

            }

            Sql sqlcjn3_1 = Sqls.create("update work_pz_gz set xche_yeje = xche_ysje - xche_ssje where work_no = '" + work_no + "' ");
            dao.execute(sqlcjn3_1);
            // 10.//
            Sql sql101 = Sqls
                    .queryRecord("delete from work_fkdw_list where work_no = '"
                            + work_no + "'");
            dao.execute(sql101);
            Sql sql102 = Sqls
                    .queryRecord("insert into work_fkdw_list(work_no,kehu_no,che_no, xche_fkdw_no, xche_fkdw_mc, xche_ysje, xche_ssje, xche_yhje, xche_yeje)     select work_no,kehu_no,che_no, kehu_no, kehu_mc, xche_ysje, xche_ssje, xche_yhje, xche_yeje    from work_pz_gz where work_no='"
                            + work_no + "'");
            dao.execute(sql102);
            Sql sql103 = Sqls
                    .queryRecord("select isnull(flag_cardjs,0) as card_js ,isnull(xche_ssje,0) as jzje,isnull(zhifu_card_xj,0) as card_zhifu_xj , isnull(yh_zhanghao,'现金') as yh_zhanghao,kehu_no,Card_no ,ZhiFu_Card_No,   isnull(xche_ssje,0)  as Card_Ssje  from work_pz_gz where work_no = '"
                            + work_no + "'");
            dao.execute(sql103);
            List<Record> res103 = sql103.getList(Record.class);
            int card_js = res103.get(0).getInt("card_js");
            double jzje = Double.parseDouble(res103.get(0).getString("jzje"));
            int card_zhifu_xj = res103.get(0).getInt("card_zhifu_xj");
            String yh_zhanghao = res103.get(0).getString("yh_zhanghao");
            String kehu_no = res103.get(0).getString("kehu_no");
            String Card_no = res103.get(0).getString("Card_no");
            String ZhiFu_Card_No = res103.get(0).getString("ZhiFu_Card_No");
//			if(card_js==1){
//				ZhiFu_Card_No = Card_no;
//			}
            int Card_Ssje = res103.get(0).getInt("Card_Ssje");
            Sql sql104 = Sqls
                    .queryRecord("insert into work_pz_sj (work_no, kehu_no, kehu_sj, che_no, xche_sfbz, xche_sffl, xche_lc, xche_cy, xche_cclc, xche_last_lc,xche_last_jdrq,xche_jdrq, xche_gj, xche_wxjd,xche_yjwgrq, xche_wgrq, xche_pgcz, xche_jlr, xche_jlrq, xche_jsr, xche_jsrq,xche_fpfs, xche_fplv, xche_rgf, xche_rgsl, xche_rgse, xche_rgbh, xche_clf, xche_clsl, xche_clse, xche_clbh, xche_glf, xche_wjgf, xche_wjgcb, xche_qtf, xche_yhje, xche_ysje, xche_rgcb, xche_cb, xche_zxr, xche_qtxm, xche_wjgx, xche_yhyy, xche_gdfl, xche_wxfl, xche_bz, xche_cz, xche_jb, xche_jcr, xche_hjje, xche_ssje, xche_yeje, xche_jsfs, dept_mc, flag_fast, card_no, card_kind, flag_cardjs,zhifu_card_no, zhifu_card_je,xche_kpje,xche_kpse,xche_fpno,zhifu_card_xj, xche_sbclf,xche_sbgsf,xche_sbgss,xche_sbgsff,xche_sbclcb,xche_bxclf,xche_bxclcb,xche_bxgsf,xche_bxgss,xche_bxgscb, card_itemrate,card_peijrate, gongsino, gongsimc, xche_wxxmlv, xche_peijlv,yh_zhanghao,xche_sjhk,xche_xzrgf_je,xche_xzclf_je,wxxm_tcje_sum,wxxm_zztcje_sum,peij_tcje_sum,che_zjno,xche_wxxm_yhje,xche_peij_yhje,work_jifen_sum,cangk_dm,flag_pad)  select work_no,kehu_no,kehu_sj,che_no,xche_sfbz,xche_sffl,xche_lc,xche_cy,isnull(xche_lc,0), isnull(xche_last_lc,0),xche_last_jdrq,xche_jdrq,xche_gj,xche_wxjd, xche_yjwgrq,xche_jsrq,  xche_pgcz,xche_jlr,xche_jlrq,xche_jsr, xche_jsrq,xche_fpfs,xche_fplv,xche_rgf,xche_rgsl,xche_rgse,xche_rgbh, xche_clf,xche_clsl,xche_clse,xche_clbh,xche_glf,xche_wjgf,xche_wjgcb,xche_qtf,xche_yhje,xche_ysje,xche_rgcb,xche_cb, xche_zxr,xche_qtxm,xche_wjgx,xche_yhyy,xche_gdfl,xche_wxfl,xche_bz,xche_cz,xche_jb,xche_jcr,xche_hjje,xche_ssje, xche_yeje,xche_jsfs,dept_mc,isnull(flag_fast,0),card_no,card_kind, "
                            + card_js
                            + " ,zhifu_card_no,zhifu_card_je,xche_kpje,xche_kpse,xche_fpno,zhifu_card_xj, xche_sbclf,xche_sbgsf,xche_sbgss,xche_sbgsff,xche_sbclcb,xche_bxclf,xche_bxclcb,xche_bxgsf,xche_bxgss,xche_bxgscb, card_itemrate,card_peijrate,gongsino,gongsimc,xche_wxxmlv,xche_peijlv,yh_zhanghao,xche_sjhk,xche_xzrgf_je,xche_xzclf_je,wxxm_tcje_sum,wxxm_zztcje_sum,peij_tcje_sum,che_zjno, xche_wxxm_yhje,xche_peij_yhje,work_jifen_sum,cangk_dm ,1 from work_pz_gz where work_no = '"
                            + work_no + "'");
            dao.execute(sql104);
            // 插入维修项目数据
            Sql sql105 = Sqls
                    .queryRecord("insert into work_mx_sj(work_no, wxxm_no, wxxm_mc, wxxm_gs, wxxm_khgs, wxxm_cb, wxxm_je, wxxm_ry,wxxm_zt, wxxm_bz, wxxm_zk,wxxm_yje,flag_xz,wxxm_mxcx,wxxm_dj,wxxm_tcfs,wxxm_tc,wxxm_tcje, wxxm_bjrq) select work_no, wxxm_no, wxxm_mc, wxxm_gs, wxxm_khgs, wxxm_cb, wxxm_je, wxxm_ry,wxxm_zt, wxxm_bz, wxxm_zk,wxxm_yje,flag_xz,wxxm_mxcx,wxxm_dj,wxxm_tcfs,wxxm_tc,wxxm_tcje, wxxm_bjrq from work_mx_gz where work_no= '"
                            + work_no + "'");
            dao.execute(sql105);
            // 插入外加工数据
            Sql sql106 = Sqls
                    .queryRecord("insert into work_wjg_sj(work_no, wxxm_no, wxxm_mc, wxxm_cb, wxxm_je, wxxm_bz) select work_no, wxxm_no, wxxm_mc, wxxm_cb, wxxm_je, wxxm_bz from work_wjg_gz where work_no= '"
                            + work_no + "'");
            dao.execute(sql106);
            // 插入检测项目数据
            Sql sql107 = Sqls
                    .queryRecord("insert into check_mx_sj(work_no,check_project,flag_normal,flag_adjust,flag_suggest_replace,kehu_view) select work_no,check_project,flag_normal,flag_adjust,flag_suggest_replace,kehu_view from check_mx_gz where work_no= '"
                            + work_no + "'");
            dao.execute(sql107);
            // 插入检测项目数据
            Sql sql108 = Sqls
                    .queryRecord("insert into work_ll_sj(work_no, peij_no, peij_sl, peij_dj, peij_je, peij_cb, peij_cbje, cangk_dm, peij_zt, peij_hw, peij_ry, peij_bz, flag_import, have_import, flag_chuku, peij_ydj, peij_yje, peij_zk, peij_sl_ls, peij_dw_ls, peij_dj_ls, danwei_bs_ls, reco_no1, flag_xz,peij_tcfs,peij_tc,peij_tcje,peij_jifen,peij_jifenlv ) select work_no,peij_no,peij_sl,peij_dj,peij_je,peij_cb,peij_cbje,cangk_dm,peij_zt,peij_hw,peij_ry,peij_bz,flag_import, have_import, flag_chuku, peij_ydj, peij_yje, peij_zk, peij_sl_ls, peij_dw, peij_dj_ls, danwei_bs_ls, reco_no, flag_xz,peij_tcfs,peij_tc,peij_tcje,peij_jifen,peij_jifenlv  from work_ll_gz where work_no= '"
                            + work_no + "'");
            dao.execute(sql108);
            // 插入派工数据
            Sql sql109 = Sqls
                    .queryRecord("insert into Work_Pg_Sj(work_no, wxxm_no, reny_no, reny_mc, wxry_bm, wxry_cj, wxry_bz, paig_khgs, paig_khje, paig_pgsj, paig_bz, wxry_pg) select work_no, wxxm_no, reny_no, reny_mc, wxry_bm, wxry_cj, wxry_bz, paig_khgs, paig_khje, paig_pgsj, paig_bz, wxry_pg from Work_Pg_Gz where work_no= '"
                            + work_no + "'");
            dao.execute(sql109);

            Sql sql109_1 = Sqls
                    .queryRecord("update work_pz_sj set xche_yhje = isnull(xche_peij_yhje,0)+ isnull(xche_wxxm_yhje,0)  where work_no = '" + work_no + "'");
            dao.execute(sql109_1);

            runCall("Wx_lingliao_chuku('" + work_no + "')");
            runCall("wx_weixiu_kuan('" + work_no + "')");
            runCall("wx_weixiu_piao('" + work_no + "')");
            runCall("Wx_Kuaixiu_JiesuanLs('" + work_no + "')");
            Sql sql110 = Sqls
                    .queryRecord("update work_yuyue_pz set yuyue_progress='已离店' where work_no='"
                            + work_no + "'");
            dao.execute(sql110);
            if (jzje != 0 && card_js == 0) {
                if ("现金".equals(yh_zhanghao)) {
                    Sql sql111 = Sqls
                            .queryRecord("insert into jizhang_xjmx(kehu_no,yewu_jsfs,jizhang_name,jizhang_pz,jizhang_km,jizhang_jf,jizhang_rq,jizhang_jb,jizhang_cz,jizhang_bz,yewu_dh,yewu_xz,GongSiNo,GongSiMc) values('"
                                    + kehu_no
                                    + "','"
                                    + yh_zhanghao
                                    + "', '现金','" + work_no + "' ,'101' , '"
                                    + jzje
                                    + "',getdate() , '" + user.getName() + "' ,'"
                                    + caozuoyuanid
                                    + "' ,'收维修款' ,'"
                                    + work_no
                                    + "' ,2007 ,'" + user.getGongSiNo() + "' , '" + user.getGongSiMc() + "' )");
                    dao.execute(sql111);
                } else {
                    Sql sql112 = Sqls
                            .queryRecord("insert into jizhang_xjmx(kehu_no,yewu_jsfs,jizhang_name,jizhang_pz,jizhang_km,jizhang_jf,jizhang_rq,jizhang_jb,jizhang_cz,jizhang_bz,yewu_dh,yewu_xz,GongSiNo,GongSiMc) values('"
                                    + kehu_no
                                    + "','"
                                    + yh_zhanghao
                                    + "','"
                                    + yh_zhanghao
                                    + "','"
                                    + work_no
                                    + "' ,'101' ,@jzje,getdate() , '" + user.getName() + "' ,'"
                                    + caozuoyuanid
                                    + "' ,'收维修款' ,'"
                                    + work_no
                                    + "' ,2007 ,'" + user.getGongSiNo() + "' ,'" + user.getGongSiMc() + "' )");
                    dao.execute(sql112);
                }
            }
            if (card_zhifu_xj != 0) {
                if ("现金".equals(yh_zhanghao)) {
                    Sql sql113 = Sqls
                            .queryRecord("insert into jizhang_xjmx(kehu_no,yewu_jsfs,jizhang_name,jizhang_pz,jizhang_km,jizhang_jf,jizhang_rq,jizhang_jb,jizhang_cz,jizhang_bz,yewu_dh,yewu_xz,GongSiNo,GongSiMc) values('"
                                    + kehu_no
                                    + "','"
                                    + yh_zhanghao
                                    + "', '现金','"
                                    + work_no
                                    + "' ,'1' ,'"
                                    + card_zhifu_xj
                                    + "', getdate() , '" + user.getName() + "' , '"
                                    + caozuoyuanid
                                    + "' , '收维修款'  ,'"
                                    + work_no
                                    + "' , 2007 ,'" + user.getGongSiNo() + "' ,  '" + user.getGongSiMc() + "' )");
                    dao.execute(sql113);
                } else {
                    Sql sql114 = Sqls
                            .queryRecord("insert into jizhang_xjmx(kehu_no,yewu_jsfs,jizhang_name,jizhang_pz,jizhang_km,jizhang_jf,jizhang_rq,jizhang_jb,jizhang_cz,jizhang_bz,yewu_dh,yewu_xz,GongSiNo,GongSiMc) values('"
                                    + kehu_no
                                    + "','"
                                    + yh_zhanghao
                                    + "','"
                                    + yh_zhanghao
                                    + "','"
                                    + work_no
                                    + "' ,'101' ,'"
                                    + card_zhifu_xj
                                    + "',getdate() , '" + user.getName() + "' ,'"
                                    + caozuoyuanid
                                    + "' ,'收维修款'  ,'"
                                    + work_no
                                    + "' ,2007 ,'" + user.getGongSiNo() + "' ,  '" + user.getGongSiMc() + "' )");
                    dao.execute(sql114);
                }
            }
            Sql sql116 = Sqls
                    .queryRecord("select sum(peij_jifen) as cardjifen  from work_ll_sj where work_no= '"
                            + work_no + "' and peij_zt='正常'");
            dao.execute(sql116);
            List<Record> res116 = sql116.getList(Record.class);
            int cardjifen = res116.get(0).getInt("cardjifen");
            // 会员积分等处理
            if (card_js == 1) {
                Sql sql114 = Sqls
                        .queryRecord("update kehu_card set card_xiaozje= card_xiaozje + "
                                + Card_Ssje
                                + "   where card_no= '"
                                + ZhiFu_Card_No + "'");
                dao.execute(sql114);
                Sql sql115 = Sqls
                        .queryRecord("select Card_JifenType,Card_JiFenlv from cardsysset");
                dao.execute(sql115);
                List<Record> res115 = sql115.getList(Record.class);
                Card_JifenType = res115.get(0).getString("Card_JifenType");
                Card_JiFenlv = res115.get(0).getString("Card_JiFenlv");
                if (!"".equals(ZhiFu_Card_No)) {
                    if ("true".equals(Card_JifenType)) {

                        System.out.println("111************************" + "update kehu_card set card_jifen=Card_jifen+"
                                + Card_Ssje
                                + "*"
                                + Card_JiFenlv
                                + "	 ,card_leftjf=card_leftjf+"
                                + Card_Ssje
                                + "*"
                                + Card_JiFenlv
                                + " where  card_no='"
                                + ZhiFu_Card_No + "'");
                        Sql sql = Sqls
                                .queryRecord("update kehu_card set card_jifen=Card_jifen+"
                                        + Card_Ssje
                                        + "*"
                                        + Card_JiFenlv
                                        + "	 ,card_leftjf=card_leftjf+"
                                        + Card_Ssje
                                        + "*"
                                        + Card_JiFenlv
                                        + " where  card_no='"
                                        + ZhiFu_Card_No + "'");
                        dao.execute(sql);
                    } else {
                        System.out.println("222************************" + "update kehu_card set card_jifen=Card_jifen+"
                                + cardjifen
                                + " , card_leftjf=card_leftjf+"
                                + cardjifen
                                + "   where card_no='"
                                + ZhiFu_Card_No + "'");
                        Sql sql = Sqls
                                .queryRecord("update kehu_card set card_jifen=Card_jifen+"
                                        + cardjifen
                                        + " , card_leftjf=card_leftjf+"
                                        + cardjifen
                                        + "   where card_no='"
                                        + ZhiFu_Card_No + "'");
                        dao.execute(sql);
                        Sql sql117 = Sqls
                                .queryRecord("select Card_jifen,Flag_Update,Card_Kind from kehu_card where card_no ='"
                                        + ZhiFu_Card_No + "'");
                        dao.execute(sql117);
                        List<Record> res117 = sql117.getList(Record.class);
                        if (res117.size() > 0) {
                            String Card_Jf = res117.get(0).getString("Card_jifen");
                            String Flag_Update = res117.get(0).getString(
                                    "Flag_Update");
                            String OldCard_Kind = res117.get(0).getString(
                                    "Card_Kind");

                            //
                            Sql sql118 = Sqls
                                    .queryRecord("select top 1 cardkind from cardkind where card_jfmin<="
                                            + Card_Jf + " order by card_jfmin desc");
                            dao.execute(sql118);
                            List<Record> res118 = sql118.getList(Record.class);
                            String NewCardKind = res118.get(0)
                                    .getString("cardkind");
                            if ("1".equals(Flag_Update) && "".equals(NewCardKind)
                                    && OldCard_Kind.equals(NewCardKind)
                                    && Card_Ssje > 0) {
                                Sql sql119 = Sqls
                                        .queryRecord("insert into cardkindrename(card_no,oldcard_kind,newcard_kind,czy,ddate,demo)values("
                                                + Card_no
                                                + ","
                                                + OldCard_Kind
                                                + ","
                                                + NewCardKind
                                                + ",'"
                                                + caozuoyuanid
                                                + "',getdate(),'会员积分升级')");
                                dao.execute(sql119);
                                Sql sql120 = Sqls
                                        .queryRecord("update kehu_card set card_kind="
                                                + NewCardKind
                                                + " where card_no= '"
                                                + ZhiFu_Card_No + "'");
                                dao.execute(sql120);
                            }
                        }
                    }
                }
            }


            // 积分，消费的记录
            if (!"".equals(Card_no)) {
                Sql sql121 = Sqls
                        .queryRecord("select  wxxm_no from work_mx_sj where work_no = '"
                                + work_no + "'");
                dao.execute(sql121);
                List<Record> res121 = sql121.getList(Record.class);
                for (Record rec : res121) {
                    String wxxm_no = rec.getString("wxxm_no");
                    Sql sql122 = Sqls
                            .queryRecord("select isnull(wxxm_cs,0)-isnull(wxxm_yqcs,0) as jg from kehu_carddetail where card_no='"
                                    + Card_no
                                    + "' and wxxm_no='"
                                    + wxxm_no
                                    + "'");
                    dao.execute(sql122);
                    List<Record> res122 = sql122.getList(Record.class);
                    if (res122.size() > 0) {
                        int jg = res122.get(0).getInt("jg");
                        if (jg > 0) {
                            Sql sql123 = Sqls
                                    .queryRecord("update kehu_carddetail set wxxm_yqcs=wxxm_yqcs+1  where card_no='"
                                            + Card_no
                                            + "' and wxxm_no='"
                                            + wxxm_no + "'");
                            dao.execute(sql123);
                            Sql sql124 = Sqls
                                    .queryRecord("update work_mx_sj set Flag_WxxcCs = 1 where work_no = '"
                                            + work_no
                                            + "' and  wxxm_no='"
                                            + wxxm_no + "'");
                            dao.execute(sql124);
                        }
                    }
                }
            }
            Sql sql125 = Sqls
                    .queryRecord("select * from work_ll_sj where work_no = '"
                            + work_no + "'");
            dao.execute(sql125);
            List<Record> res125 = sql125.getList(Record.class);
            if (res125.size() > 0) {
                String peij_no = res125.get(0).getString("peij_no");
                int peij_sl = res125.get(0).getInt("peij_sl");
                String sql_126 = "select isnull(peij_card_sl,0)-isnull(peij_card_yqsl,0) as peij_sysl from kehu_carddetailpeij where peij_no ='"
                        + peij_no + "'";
                if (Card_no != null && Card_no.length() > 2) {
                    sql_126 += " and Card_no='" + Card_no + "'";
                }
                Sql sql126 = Sqls.queryRecord(sql_126);
                dao.execute(sql126);
                List<Record> res126 = sql126.getList(Record.class);
                if (res126.size() > 0) {
                    int peij_sysl = res126.get(0).getInt("peij_sysl");
                    if (peij_sysl > 0) {
                        if (peij_sysl - peij_sl >= 0) {
                            Sql sql127 = Sqls
                                    .queryRecord("update kehu_carddetailpeij set peij_card_yqsl=isnull(peij_card_yqsl,0) + "
                                            + peij_sl
                                            + "	where card_no='"
                                            + Card_no
                                            + "' and peij_no='"
                                            + peij_no + "'");
                            dao.execute(sql127);
                            // --更新work_ll_sj表中的Flag_peijcardSL(使用的会员卡中的数量)，用于判断反结算时是否增加特殊配件数量
                            Sql sql128 = Sqls
                                    .queryRecord(" update work_ll_sj set Flag_peijcardSL = "
                                            + peij_sl
                                            + " where work_no = '"
                                            + work_no
                                            + "' and peij_no = '"
                                            + peij_no + "'");
                            dao.execute(sql128);
                        } else if (peij_sysl - peij_sl < 0) {
                            Sql sql129 = Sqls
                                    .queryRecord("update work_ll_sj set Flag_peijcardSL = "
                                            + peij_sysl
                                            + " where work_no = '"
                                            + work_no
                                            + "' and peij_no = '"
                                            + peij_no + "'");
                            dao.execute(sql129);
                            Sql sql130 = Sqls
                                    .queryRecord("update kehu_carddetailpeij set peij_card_yqsl=peij_card_yqsl + "
                                            + peij_sysl
                                            + " where card_no='"
                                            + Card_no
                                            + "' and peij_no='"
                                            + peij_no + "'");
                            dao.execute(sql130);
                        }
                    }
                }
            }
            if (card_js == 0) {
                Sql sql130 = Sqls
                        .queryRecord("update kehu_card set card_xiaozje= card_xiaozje + "
                                + Card_Ssje
                                + "   where card_no= '"
                                + Card_no + "'");
                dao.execute(sql130);
                if ("true".equals(Card_JifenType)) {
                    System.out.println("3******************************" + "update kehu_card set card_jifen=Card_jifen+"
                            + Card_Ssje
                            + "*"
                            + Card_JiFenlv
                            + " ,card_leftjf=card_leftjf+"
                            + Card_Ssje
                            + "*"
                            + Card_JiFenlv
                            + " where  card_no='"
                            + Card_no + "'");
                    Sql sql131 = Sqls
                            .queryRecord("update kehu_card set card_jifen=Card_jifen+"
                                    + Card_Ssje
                                    + "*"
                                    + Card_JiFenlv
                                    + " ,card_leftjf=card_leftjf+"
                                    + Card_Ssje
                                    + "*"
                                    + Card_JiFenlv
                                    + " where  card_no='"
                                    + Card_no + "'");
                    dao.execute(sql131);

                } else {
                    System.out.println("4******************************" + "update kehu_card set card_jifen=Card_jifen+"
                            + cardjifen
                            + ",  card_leftjf=card_leftjf+"
                            + cardjifen
                            + "  where card_no='"
                            + Card_no
                            + "'");
                    Sql sql131 = Sqls
                            .queryRecord("update kehu_card set card_jifen=Card_jifen+"
                                    + cardjifen
                                    + ",  card_leftjf=card_leftjf+"
                                    + cardjifen
                                    + "  where card_no='"
                                    + Card_no
                                    + "'");
                    dao.execute(sql131);

                }
            }
            return jsons.json(1, 1, 1, "\"结算完成\"");

        }
        return jsons.json(1, 1, 0, "\"操作员不存在\"");
    }

    private void runCall(final String sql) {
        dao.run(new ConnCallback() {
            @Override
            public void invoke(java.sql.Connection conn) throws Exception {
                CallableStatement cs = conn.prepareCall("{call " + sql + "}");
                cs.executeUpdate();
            }
        });
    }

    private String time() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Sql sql = Sqls.queryRecord("select dateadd(hour,3,'"
                + sdf.format(new Date()) + "')");
        dao.execute(sql);
        String sd = null;
        List<Record> res = sql.getList(Record.class);
        for (Record re : res) {
            sd = re.getString("");

        }

        return sd;

    }

    /**
     * 是否质检 flag_ischeck
     *
     * @return
     */
    private boolean zhijian() {
        Sql sql = Sqls
                .queryRecord("select sys_ischeckwork from sm_system_info");
        dao.execute(sql);
        String s = "";
        List<Record> res = sql.getList(Record.class);
        for (Record re : res) {
            s = re.getString("sys_ischeckwork");
        }
        if (s == "0") {
            return false;
        }
        return true;

    }

    /**
     * 是否洗车lag_isxiche
     *
     * @return
     */
    private boolean xiche() {
        Sql sql = Sqls.queryRecord("select sys_isxiche  from sm_system_info");
        dao.execute(sql);
        String s = "";
        List<Record> res = sql.getList(Record.class);
        for (Record re : res) {
            s = re.getString("lag_isxiche");
        }

        if (s == "0") {
            return false;
        }
        return true;

    }

    /*
     * 快修微信
     * @time 2017年9月9日08:47:15
     *
     */
    @At
    @Ok("raw:json")
    public void kxweixin(String work_no) throws Exception {
        Sql sql126 = Sqls.queryRecord("select Option_Value from Option_List where Option_NO = 3025");
        dao.execute(sql126);
        List<Record> res = sql126.getList(Record.class);
        String Option_Value = res.get(0).getString("Option_Value");
        if ("1".equals(Option_Value)) {
            String json = pu.getWeXinDiZhi();
            JsonParser parse = new JsonParser(); // 创建json解析器
            JsonObject js = (JsonObject) parse.parse(json); // 创建jsonObject对象
            JsonArray array = js.get("data").getAsJsonArray();
            JsonObject subObject = array.get(0).getAsJsonObject();
            String sys_weixindizhi = subObject.get("sys_weixindizhi").getAsString();
            if (sys_weixindizhi != null && !"".equals(sys_weixindizhi)) {
                sys_weixindizhi += "/WS/ws_pub.asmx/GetDogDaoQi";
                HttpGet httpget = new HttpGet(sys_weixindizhi);
                // 配置请求信息（请求时间）
                RequestConfig rc = RequestConfig.custom().setSocketTimeout(5000)
                        .setConnectTimeout(5000).build();
                httpget.setConfig(rc);
                // 获取使用DefaultHttpClient对象
                CloseableHttpClient httpclient = HttpClients.createDefault();
                // 返回结果
                String result = "";
                CloseableHttpResponse response = null;
                try {
                    // 执行DefaultHttpClient对象的execute方法发送GET请求，通过CloseableHttpResponse接口的实例，可以获取服务器返回的信息
                    response = httpclient.execute(httpget);
                    // 得到返回对象
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // 获取返回结果
                        result = EntityUtils.toString(entity);
                        int i = result.length();
                        result = result.substring(result.length() - 19, i - 9);//截取两个数字之间的部分
                        System.out.println(result);
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                        Date de = new Date();
                        String string = date.format(de);
                        Date date2 = date.parse(result);
                        Date date3 = date.parse(string);
                        System.out.println(date2 + "====" + date3);
                        if (date2.getTime() > date3.getTime()) {
                            Sql sql121 = Sqls
                                    .queryRecord("select  * from work_pz_sj where work_no = '" + work_no + "'");
                            dao.execute(sql121);
                            List<Record> res126 = sql121.getList(Record.class);
                            System.out.println(res126.size());
                            if (res126.size() > 0) {
                                String che_no = res126.get(0).getString("che_no");
                                String gongsino = res126.get(0).getString("gongsino");
                                String xche_jsrq = res126.get(0).getString("xche_jsrq");
                                String zhifu_card_no = res126.get(0).getString("zhifu_card_no");
                                String xche_jb = res126.get(0).getString("xche_jb");
                                String card_no = res126.get(0).getString("card_no");
                                String xche_ysje = res126.get(0).getString("xche_ysje");
                                //String xche_jcr = res126.get(0).getString("xche_jcr");
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("list_code", "105");
                                map.put("tm_type", "wt_WeiXiuJieSuan");
                                map.put("gongsino", gongsino);
                                map.put("che_no", che_no);
                                map.put("card_no", card_no);
                                map.put("zhifu_card_no", zhifu_card_no);
                                map.put("cardinfo", "");
                                map.put("xche_jsrq", xche_jsrq);
                                map.put("xche_jb", xche_jb);
                                map.put("xche_ysje", xche_ysje);
                                map.put("work_no", work_no);
			            					/*map.put("list_code", "105");
			            					map.put("tm_type", "wt_WeiXiuJieSuan");
			            					map.put("gongsino", "01");
			            					map.put("che_no", "冀Au2d30");
			            					map.put("card_no", "318111");
			            					map.put("zhifu_card_no", "");
			            					map.put("cardinfo", "");
			            					map.put("xche_jsrq", "2017-09-01 14:38:26");
			            					map.put("xche_jb", "");
			            					map.put("xche_ysje", "100");
			            					map.put("work_no", "WX0120170900007");*/
                                String json1 = new Gson().toJson(map);
                                json1 = "strJson=" + json1;
                                String lujing = sys_weixindizhi + "/PadWeiXin/CssWeiXinAction.ashx";
                                System.out.println(json1);
                                System.out.println(lujing);
                                byte[] data = json1.getBytes();
                                java.net.URL url = new java.net.URL(lujing);
                                System.out.println(url);
                                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                conn.setConnectTimeout(5 * 1000);// 设置连接超时时间为5秒
                                conn.setReadTimeout(20 * 1000);// 设置读取超时时间为20秒
                                // 使用 URL 连接进行输出，则将 DoOutput标志设置为 true
                                conn.setDoOutput(true);
//			            			        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");  
                                // conn.setRequestProperty("Content-Encoding","gzip");
                                conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                                OutputStream outStream = conn.getOutputStream();// 返回写入到此连接的输出流
                                outStream.write(data);
                                outStream.close();// 关闭流
                                String msg = "";// 保存调用http服务后的响应信息
                                // 如果请求响应码是200，则表示成功
                                if (conn.getResponseCode() == 200) {
                                    // HTTP服务端返回的编码是UTF-8,故必须设置为UTF-8,保持编码统一,否则会出现中文乱码
                                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                                    msg = in.readLine();
                                    in.close();
                                }
                                conn.disconnect();// 断开连接
                                System.out.println(msg);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // 关闭到客户端的连接
                        if (response != null) {
                            response.close();
                        }
                        // 关闭http请求
                        httpclient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /*
     * 维修接单微信
     * @time 2017年9月9日08:47:15
     *
     */
    @At
    @Ok("raw:json")
    public void wxjdweixin(String work_no) throws Exception {
        work_no = new String(work_no.getBytes("iso8859-1"), "utf-8");
        Sql sql126 = Sqls
                .queryRecord("select Option_Value from Option_List where Option_NO = 3025");
        dao.execute(sql126);
        List<Record> res = sql126.getList(Record.class);
        String Option_Value = res.get(0).getString("Option_Value");
        if ("1".equals(Option_Value)) {
            String json = pu.getWeXinDiZhi();
            JsonParser parse = new JsonParser(); // 创建json解析器
            JsonObject js = (JsonObject) parse.parse(json); // 创建jsonObject对象
            JsonArray array = js.get("data").getAsJsonArray();
            JsonObject subObject = array.get(0).getAsJsonObject();
            String sys_weixindizhi = subObject.get("sys_weixindizhi").getAsString();
            if (sys_weixindizhi != null && !"".equals(sys_weixindizhi)) {
                String result = "";
                sys_weixindizhi += "/WS/ws_pub.asmx/GetDogDaoQi";
                CloseableHttpClient httpclient = null;
                CloseableHttpResponse response = null;
                try {
                    // 获取使用DefaultHttpClient对象
                    httpclient = HttpClients.createDefault();
                    // 配置请求信息（请求时间）
                    RequestConfig rc = RequestConfig.custom().setSocketTimeout(5000)
                            .setConnectTimeout(5000).build();
                    HttpGet httpget = new HttpGet(sys_weixindizhi);
                    // 将配置好请求信息附加到http请求中;
                    httpget.setConfig(rc);
                    // 执行DefaultHttpClient对象的execute方法发送GET请求，通过CloseableHttpResponse接口的实例，可以获取服务器返回的信息
                    response = httpclient.execute(httpget);
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // 获取返回结果
                        result = EntityUtils.toString(entity);
                        int i = result.length();
                        result = result.substring(result.length() - 19, i - 9);//截取两个数字之间的部分
                        System.out.println(result);
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                        Date de = new Date();
                        String string = date.format(de);
                        Date date2 = date.parse(result);
                        Date date3 = date.parse(string);
                        System.out.println(date2 + "====" + date3);
                        if (date2.getTime() > date3.getTime()) {
                            Sql sql121 = Sqls
                                    .queryRecord("select  * from work_pz_gz where work_no = '"
                                            + work_no + "'");
                            dao.execute(sql121);
                            List<Record> res126 = sql121.getList(Record.class);
                            System.out.println(res126.size());
                            if (res126.size() > 0) {
                                String che_no = res126.get(0).getString("che_no");
                                String gongsino = res126.get(0).getString("gongsino");
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("list_code", "106");
                                map.put("tm_type", "wt_WeiXiuJinDu");
                                map.put("gongsino", gongsino);
                                map.put("che_no", che_no);
                                map.put("xche_wxjd", "已进厂");
                                map.put("xche_bz", "欢迎您来到本店！");
                                String json1 = new Gson().toJson(map);
                                json1 = "strJson=" + json1;
                                String lujing = sys_weixindizhi + "/PadWeiXin/CssWeiXinAction.ashx";
                                byte[] data = json1.getBytes();
                                java.net.URL url = new java.net.URL(lujing);
                                System.out.println(url);
                                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                conn.setConnectTimeout(5 * 1000);// 设置连接超时时间为5秒
                                conn.setReadTimeout(20 * 1000);// 设置读取超时时间为20秒
                                // 使用 URL 连接进行输出，则将 DoOutput标志设置为 true
                                conn.setDoOutput(true);
//			            			    conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
                                // conn.setRequestProperty("Content-Encoding","gzip");
                                conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                                OutputStream outStream = conn.getOutputStream();// 返回写入到此连接的输出流
                                outStream.write(data);
                                outStream.close();// 关闭流
                                String msg = "";// 保存调用http服务后的响应信息
                                // 如果请求响应码是200，则表示成功
                                if (conn.getResponseCode() == 200) {
                                    // HTTP服务端返回的编码是UTF-8,故必须设置为UTF-8,保持编码统一,否则会出现中文乱码
                                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                                    msg = in.readLine();
                                    in.close();
                                    System.out.println(msg);
                                }
                                conn.disconnect();// 断开连接
                                System.out.println(msg);
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    weixinClose(httpclient, response);
                }
            }
        }
    }

    private void weixinClose(CloseableHttpClient httpclient, CloseableHttpResponse response) {
        try {
            if (response != null) {
                response.close();
            }
            if (httpclient != null) {
                httpclient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 完工微信
     * @time 2017年9月9日08:47:15
     *
     */
    @At
    @Ok("raw:json")
    public void wgweixin(String work_no) throws Exception {
        Sql sql126 = Sqls
                .queryRecord("select Option_Value from Option_List where Option_NO = 3025");
        dao.execute(sql126);
        List<Record> res = sql126.getList(Record.class);
        String Option_Value = res.get(0).getString("Option_Value");
        if ("1".equals(Option_Value)) {
            String json = pu.getWeXinDiZhi();
            JsonParser parse = new JsonParser(); // 创建json解析器

            JsonObject js = (JsonObject) parse.parse(json); // 创建jsonObject对象
            JsonArray array = js.get("data").getAsJsonArray();
            JsonObject subObject = array.get(0).getAsJsonObject();
            String sys_weixindizhi = subObject.get("sys_weixindizhi").getAsString();
            if (sys_weixindizhi != null && !"".equals(sys_weixindizhi)) {
                // 返回结果
                String result = "";
                sys_weixindizhi += "/WS/ws_pub.asmx/GetDogDaoQi";
                CloseableHttpClient httpclient = null;
                CloseableHttpResponse response = null;
                try {
                    // 获取使用DefaultHttpClient对象
                    httpclient = HttpClients.createDefault();
                    // 配置请求信息（请求时间）
                    RequestConfig rc = RequestConfig.custom().setSocketTimeout(5000)
                            .setConnectTimeout(5000).build();
                    // 创建HttpGet对象，将URL通过构造方法传入HttpGet对象
                    HttpGet httpget = new HttpGet(sys_weixindizhi);
                    // 将配置好请求信息附加到http请求中;
                    httpget.setConfig(rc);
                    // 执行DefaultHttpClient对象的execute方法发送GET请求，通过CloseableHttpResponse接口的实例，可以获取服务器返回的信息
                    response = httpclient.execute(httpget);
                    // 得到返回对象
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // 获取返回结果
                        result = EntityUtils.toString(entity);
                        int i = result.length();
                        result = result.substring(result.length() - 19, i - 9);//截取两个数字之间的部分
                        System.out.println(result);
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                        Date de = new Date();
                        String string = date.format(de);
                        Date date2 = date.parse(result);
                        Date date3 = date.parse(string);
                        System.out.println(date2 + "====" + date3);
                        if (date2.getTime() > date3.getTime()) {
                            Sql sql121 = Sqls
                                    .queryRecord("select  * from work_pz_gz where work_no = '" + work_no + "'");
                            dao.execute(sql121);
                            List<Record> res126 = sql121.getList(Record.class);
                            System.out.println(res126.size());
                            if (res126.size() > 0) {
                                String che_no = res126.get(0).getString("che_no");
                                String gongsino = res126.get(0).getString("gongsino");
                                //String xche_jcr = res126.get(0).getString("xche_jcr");
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("list_code", "113");
                                map.put("tm_type", "wt_WeiXiuJinDu");
                                map.put("gongsino", gongsino);
                                map.put("che_no", che_no);
                                map.put("xche_wxjd", "已完工");
                                map.put("xche_bz", "请您到前台进行费用结算！");
			            					/*map.put("list_code", "105");
			            					map.put("tm_type", "wt_WeiXiuJieSuan");
			            					map.put("gongsino", "01");
			            					map.put("che_no", "冀Au2d30");
			            					map.put("card_no", "318111");
			            					map.put("zhifu_card_no", "");
			            					map.put("cardinfo", "");
			            					map.put("xche_jsrq", "2017-09-01 14:38:26");
			            					map.put("xche_jb", "");
			            					map.put("xche_ysje", "100");
			            					map.put("work_no", "WX0120170900007");*/
                                String json1 = new Gson().toJson(map);
                                String lujing = "";
                                json1 = "strJson=" + json1;
                                lujing = sys_weixindizhi + "/PadWeiXin/CssWeiXinAction.ashx";
                                //lujing="http://css8new.weixin.comtg.cn/PadWeiXin/CssWeiXinAction.ashx";
                                //lujing="http://wdwx84.weixin.comtg.cn/PadWeiXin/CssWeiXinAction.ashx";
                                System.out.println(json1);
                                System.out.println(lujing);

                                byte[] data = json1.getBytes();
                                java.net.URL url = new java.net.URL(lujing);
                                System.out.println(url);
                                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                conn.setConnectTimeout(5 * 1000);// 设置连接超时时间为5秒
                                conn.setReadTimeout(20 * 1000);// 设置读取超时时间为20秒
                                // 使用 URL 连接进行输出，则将 DoOutput标志设置为 true
                                conn.setDoOutput(true);

//			            			        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");  
                                // conn.setRequestProperty("Content-Encoding","gzip");
                                conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                                OutputStream outStream = conn.getOutputStream();// 返回写入到此连接的输出流
                                outStream.write(data);
                                outStream.close();// 关闭流
                                String msg = "";// 保存调用http服务后的响应信息
                                // 如果请求响应码是200，则表示成功
                                if (conn.getResponseCode() == 200) {
                                    // HTTP服务端返回的编码是UTF-8,故必须设置为UTF-8,保持编码统一,否则会出现中文乱码
                                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                                    msg = in.readLine();
                                    in.close();
                                    System.out.println(msg);
                                }
                                conn.disconnect();// 断开连接
                                System.out.println(msg);
                            }

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    weixinClose(httpclient, response);
                }
            }
        }
    }

    /**
     * 获取结算信息
     * 会员卡剩余金额，维修优惠金额，材料优惠金额
     */
    @At
    @Ok("raw:json")
    public String getBillJieSuanInfo(String card_no, String work_no, String che_no) {
        HashMap<String, String> map = new HashMap<>();
        Sql sql = Sqls.queryRecord("select xche_wxxm_yhje,xche_peij_yhje,xche_hjje from work_pz_gz where work_no='" + work_no + "'");
        dao.execute(sql);
        List<Record> list = sql.getList(Record.class);
        if (list.size() > 0) {
            map.put("xche_wxxm_yhje", list.get(0).getString("xche_wxxm_yhje"));
            map.put("xche_peij_yhje", list.get(0).getString("xche_peij_yhje"));
            map.put("xche_hjje", list.get(0).getString("xche_hjje"));
        }
        if (card_no != null && card_no.length() > 0) {
            Sql sql1 = Sqls.queryRecord("select card_leftje from kehu_card where card_no='" + card_no + "'");
            dao.execute(sql1);
            List<Record> list1 = sql1.getList(Record.class);
            if (list1.size() > 0) {
                map.put("card_leftje", list1.get(0).getString("card_leftje"));
            }
        } else {
            map.put("card_leftje", "0");
        }
        Sql sql2 = Sqls.queryRecord("select sys_baoyang_che_fs from sm_system_info");
        dao.execute(sql2);
        List<Record> list2 = sql2.getList(Record.class);
        int sysBaoyangCheFs = list2.get(0).getInt("sys_baoyang_che_fs");
        map.put("sys_baoyang_che_fs", "" + sysBaoyangCheFs);

        Sql sql3 = Sqls.queryRecord("select isnull(che_next_licheng,0) as che_next_licheng,isnull(che_rjlc,0) as che_rjlc,isnull(che_baoyanglicheng,0) as che_baoyanglicheng " +
                "from work_cheliang_sm where che_no='" + che_no + "'");
        dao.execute(sql3);
        List<Record> list3 = sql3.getList(Record.class);
        String cheNextLicheng = list3.get(0).getString("che_next_licheng");
        String cheRjlc = list3.get(0).getString("che_rjlc");
        String cheBaoyanglicheng = list3.get(0).getString("che_baoyanglicheng");
        map.put("che_next_licheng", cheNextLicheng);
        map.put("che_rjlc", cheRjlc);
        map.put("che_baoyanglicheng", cheBaoyanglicheng);
        if (map.size() > 0) {
            String json = Json.toJson(map);
            return jsons.json(1, 1, 1, json);
        }
        return jsons.json(1, 1, 0, "");
    }
}
		
	

