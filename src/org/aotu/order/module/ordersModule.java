package org.aotu.order.module;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aotu.Jsons;
import org.aotu.offsetPager;
import org.aotu.lswx.entity.Work_mx_sjEntity;
import org.aotu.offer.entity.feilvEntity;
import org.aotu.order.entity.Work_ll_gzEntity;
import org.aotu.order.entity.Work_mx_gzEntity;
import org.aotu.order.entity.Work_pz_gzEntity;
import org.aotu.publics.eneity.KehuEntity;
import org.aotu.publics.eneity.Work_cheliang_smEntity;
import org.aotu.publics.module.publicModule;
import org.aotu.util.BsdUtils;
import org.nutz.dao.*;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * title:ordersModule
 *
 * @author Zhang Yalong
 * @Description:<维修接单>
 * @date 2017-5-2 下午3:16:01
 * @version: V1.0
 */
@IocBean
@At("/order")
public class ordersModule {

    @Inject
    Dao dao;

    @Inject
    Jsons jsons;

    @Inject
    publicModule pu;

    /**
     * @param pai
     * @param gongsiNo
     * @param caozuoyuan_xm
     * @return
     */
    @At
    @Ok("raw:json")
    public String jbxxXiangXi(String pai, String gongsiNo, String caozuoyuan_xm) {
        java.util.Calendar rightNow = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        // 如果是后退几天，就写 -天数 例如：
        rightNow.add(java.util.Calendar.DAY_OF_MONTH, -20);
        // 进行时间转换
        String date = sim.format(rightNow.getTime());
        List<Work_pz_gzEntity> result = dao.query(
                Work_pz_gzEntity.class,
                Cnd.where("che_no", "=", pai).and("xche_jdrq", ">", date)
                        .and("flag_fast", "=", 0).and("mainstate", "=", 0)
                        .desc("xche_jdrq"));
        for (Work_pz_gzEntity o : result) {
            Work_cheliang_smEntity ss = dao.fetch(Work_cheliang_smEntity.class,
                    o.getChe_no());
            if (ss != null)
                o.setGcsj(ss.getChe_gcrq());
        }
        String json = Json.toJson(result, JsonFormat.full());
        if (result.size() != 0) {
            return jsons.json(1, result.size(), 1, json);
        }
        return jsons.json(1, result.size(), 0, json);


    }


    /**
     * 基本信息
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String jbxx(String che_no, String gongsiNo, String caozuoyuan_xm, String work_no) {
        Work_pz_gzEntity pz = new Work_pz_gzEntity();
        if (!"".equals(work_no)) {
            pz = dao.fetch(Work_pz_gzEntity.class, work_no);
        } else {
            java.util.Calendar rightNow = java.util.Calendar.getInstance();
            java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 如果是后退几天，就写 -天数 例如：
            rightNow.add(java.util.Calendar.DAY_OF_MONTH, -20);
            // 进行时间转换
            String date = sim.format(rightNow.getTime());
            List<Work_pz_gzEntity> result = dao.query(Work_pz_gzEntity.class,
                    Cnd.where("che_no", "=", che_no)
                            .and("xche_jdrq", ">", date)
                            .and("flag_fast", "=", 0)
                            .and("mainstate", "=", -1)
                            .desc("xche_jdrq"));
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
                pz.setFlag_fast(false);
                pz.setMainstate(-1);
                Work_cheliang_smEntity che = dao.fetch(Work_cheliang_smEntity.class, che_no);
                if (che != null) {
                    //添加颜色和年份字段。
                    pz.setChe_cx(che.getChe_cx());
                    pz.setChe_vin(che.getChe_vin());
                    pz.setGcsj(che.getChe_gcrq());
                    pz.setChe_wxys(che.getChe_wxys());
                    pz.setChe_nf(che.getChe_nf());
                    pz.setXche_lc(che.getChe_next_licheng());
                    KehuEntity kehu = dao.fetch(KehuEntity.class, che.getKehu_no());
                    if (kehu != null) {
                        pz.setKehu_no(kehu.getKehu_no());
                        pz.setKehu_mc(kehu.getKehu_mc());
                        pz.setKehu_dh(kehu.getKehu_dh());
                    }
                }

                dao.updateIgnoreNull(pz);
            } else {
                pz = result.get(0);
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

    /**
     * 历史接单
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String lsjd(String kehu_mc, String che_no, String gongsino) {
        StringBuilder _sql = new StringBuilder("select a.*,k.kehu_mc,b.che_baoyanglicheng,b.che_bs,b.che_ccrq,b.che_gcrq,k.kehu_dh,b.che_cx,b.che_vin from work_pz_gz a left join work_cheliang_sm b on a.che_no=b.che_no left join kehu k on a.kehu_no = k.kehu_no where a.flag_fast=0 and a.mainstate=-1 ");
        if (che_no != null && che_no.length() > 0) {
            _sql.append(" and a.che_no like '%").append(che_no).append("%'");
        }
        if (kehu_mc != null && kehu_mc.length() > 0) {
            _sql.append(" and kehu_mc like '%").append(kehu_mc).append("%'");
        }
        _sql.append(" order by xche_jdrq desc");
        Sql sql = Sqls.queryRecord(_sql.toString());
        dao.execute(sql);
        List<Record> res = sql.getList(Record.class);
        String json = Json.toJson(res, JsonFormat.full());
        if (res.size() != 0) {
            return jsons.json(1, res.size(), 1, json);
        }
        return jsons.json(1, res.size(), 0, json);
    }

    /**
     * 历史维修项目
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String lswxxm(String work_no) {
        String json = "";
        List<Work_mx_sjEntity> result = dao.query(Work_mx_sjEntity.class,
                Cnd.where("work_no", "=", work_no));
        //加入判断语句 否则会有下标越界的错误
        if (result.size() > 0) {
            json = Json.toJson(result, JsonFormat.full());
            if (result.size() != 0) {
                return jsons.json(1, result.size(), 1, json);
            }
        }
        return jsons.json(1, 0, 0, json);
    }

    /**
     * 历史接单
     *
     * @param pai
     * @return
     */
    @At
    @Ok("raw:json")
    public String lsjd2(int pageNumber, String kehu_mc, String che_no) {
        StringBuffer _sql = new StringBuffer("select a.*,k.kehu_mc,b.che_baoyanglicheng,b.che_bs,b.che_ccrq,b.che_gcrq,k.kehu_dh from work_pz_sj  a left join work_cheliang_sm b on a.che_no=b.che_no left join kehu k on a.kehu_no = k.kehu_no  where  a.flag_fast=1  and   a.mainstate>=-1 and  a.mainstate<=4 ");
        if (che_no != null && che_no.length() > 0)
            _sql.append(" and a.che_no like '%" + che_no + "%'");
        if (kehu_mc != null && kehu_mc.length() > 0)
            _sql.append(" and kehu_mc like '%" + kehu_mc + "%'");
        _sql.append(" order by id desc ");
        Sql sql = Sqls.queryRecord(_sql.toString());
        dao.execute(sql);
        List<Record> res = sql.getList(Record.class);
        String json = Json.toJson(res, JsonFormat.full());
        if (res.size() != 0) {
            return jsons.json(1, res.size(), 1, json);
        }
        return jsons.json(1, res.size(), 0, json);
    }

    /**
     * 维修项目
     * @return
     */
    @At
    @Ok("raw:json")
    public String wxxm(String work_no) {
        List<Work_mx_gzEntity> result = dao.query(Work_mx_gzEntity.class,
                Cnd.where("work_no", "=", work_no));
        String json = Json.toJson(result, JsonFormat.full());
        if (result.size() != 0) {
            return jsons.json(1, result.size(), 1, json);
        }
        return jsons.json(1, result.size(), 0, json);
    }


    /**
     * 材料
     *
     * @param pai
     * @return
     */
    @At
    @Ok("raw:json")
    public String cail(String work_no) {
        List<Work_ll_gzEntity> result = dao.query(Work_ll_gzEntity.class,
                Cnd.where("work_no", "=", work_no));
        String json = Json.toJson(result, JsonFormat.full());
        System.out.println(json);
        if (result.size() != 0) {
            return jsons.json(1, result.size(), 1, json);
        }
        return jsons.json(1, result.size(), 0, json);
    }

    /**
     * 历史材料
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String lscail(String work_no) {
        //List<Work_ll_sjEntity> result = dao.query(Work_ll_sjEntity.class,
        //		Cnd.where("work_no", "=", work_no));
        String json = "";
        Sql sql1 = Sqls
                .queryRecord("select  a.work_no,a.reco_no,a.peij_no,b.peij_th,b.peij_mc,b.peij_dw,a.peij_sl,a.peij_dj,a.peij_je,a.peij_zt from work_ll_sj a ,kucshp_info b where work_no = '" + work_no + "' and  a.peij_no = b.peij_no ");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        //加入判断语句 否则会有下标越界的错误
        if (res1.size() > 0) {
            ArrayList<Object> arrayList = new ArrayList<>();
            for (Record record : res1) {
                arrayList.add(record);
            }
            json = Json.toJson(arrayList, JsonFormat.full());
            System.out.println(json);
            if (res1.get(0).size() != 0) {
                return jsons.json(1, res1.get(0).size(), 1, json);
            }
        }
        return jsons.json(1, 0, 0, json);
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
        System.out.println("===============" + number + "=================");
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

        double dos1 = 1;
        double dos2 = 1;
        List list_ = sm();
        if (list_.size() > 0) {
            dos1 = Double.parseDouble(list_.get(0).toString());
            dos2 = Double.parseDouble(list_.get(1).toString());
        }
        System.out.println("====" + number);
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
        result.setFlag_fast(true);
        result.setXche_ywlx("正常维修");
        result.setXche_wxxmlv(dos1 * 100);
        result.setXche_peijlv(dos2 * 100);
        result.setFlag_pad(true);
        result.setFlag_IsCheck(zhijian());
        result.setFlag_isxiche(xiche());
        System.out.println("==========" + result.getWork_no());
        int num = dao.update(result);

        if (num < 1) {
            return jsons.json(1, 0, 0, "");
        }

        List<Work_pz_gzEntity> result1 = dao.query(Work_pz_gzEntity.class,
                Cnd.where("work_no", "=", number));
        // String json = Json.toJson(result1, JsonFormat.full());
        // return jsons.json(1, result1.size(), 0, json);
        return number;
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
     * select sys_ischeckwork from sm_system_info --是否质检 flag_ischeck;
     *
     * select sys_isxiche from sm_system_info --是否洗车lag_isxiche;
     */

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

    /**
     * 添加基本信息
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public int addjbxx(@Param("gongsiNo") final String gongsiNo,
                       @Param("caozuoyuan_xm") final String caozuoyuan_xm,
                       @Param("..") Work_pz_gzEntity pz) {
        String num = add(gongsiNo, caozuoyuan_xm);
        pz.setWork_no(num);
        pz.setFlag_pad(true);
        int nu = dao.updateIgnoreNull(pz);
        return nu;
    }

    /**
     * @return
     * @sj修改时间 2017-8-30 16:05:05
     * 主表上增加年款，颜色，存油，里程等信息，增加故障描述字段。
     */
    @At
    @Ok("raw:json")
    public String addjb(@Param("..") Work_pz_gzEntity gzEntity) {
        gzEntity.setMainstate(-1);
        dao.update(gzEntity, "^card_no|che_wxys|che_nf|xche_bz|xche_cy|xche_lc|che_cx|che_vin|xche_lc|kehu_mc|kehu_dh|xche_sfbz|xche_sffl|xche_gj|mainstate$");
        if (gzEntity.getChe_no() != null) {
            Work_cheliang_smEntity che = pu.saveCheInfose(gzEntity.getXche_bz(), gzEntity.getChe_no(), gzEntity.getGcsj(),
                    gzEntity.getChe_cx(), gzEntity.getChe_vin(), gzEntity.getChe_nf(), gzEntity.getChe_wxys());
            pu.saveKeHu(che.getKehu_no(), gzEntity.getKehu_mc(), gzEntity.getKehu_dh());
            Sql sql = Sqls.create("update Work_BaoJia_pz set List_state = '1', List_progress ='未进店' where list_no='"
                    + gzEntity.getWork_no() + "'");
            dao.execute(sql);
        }
        return "success";
    }

    /**
     * 添加维修项目
     *
     * @param yuyue_no
     * @return
     */
    @At
    @Ok("raw:json")
    public String addwxxm(String json) {
        JsonParser parse = new JsonParser(); // 创建json解析器
        try {
            JsonObject js = (JsonObject) parse.parse(json); // 创建jsonObject对象
            JsonArray array = js.get("data").getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                Work_mx_gzEntity yuyue = new Work_mx_gzEntity();
                JsonObject subObject = array.get(i).getAsJsonObject();
                String nu = subObject.get("work_no").getAsString();

                yuyue.setWork_no(subObject.get("work_no").getAsString());
                if (i == 0) {
                    Work_mx_gzEntity ks = dao.fetch(Work_mx_gzEntity.class,
                            Cnd.where("work_no", "=", nu));
                    if (ks != null) {
                        // dao.query(Work_yuyue_wxxmEntity.class,
                        // Cnd.where("yuyue_no","=",nu));
                        dao.clear(Work_mx_gzEntity.class,
                                Cnd.where("work_no", "=", nu));
                    }
                }
                yuyue.setWxxm_yje(subObject.get("wxxm_je").getAsDouble());
                yuyue.setWxxm_no(subObject.get("wxxm_no").getAsString());
                yuyue.setWxxm_mc(subObject.get("wxxm_mc").getAsString());
                yuyue.setWxxm_gs(subObject.get("wxxm_gs").getAsDouble());
                yuyue.setWxxm_je(subObject.get("wxxm_je").getAsDouble());
                yuyue.setWxxm_tpye(subObject.get("wxxm_tpye").getAsString());
                yuyue.setWxxm_zt(subObject.get("wxxm_zt").getAsString());
                yuyue.setWxxm_khgs(subObject.get("wxxm_gs").getAsDouble());
                if (subObject.get("wxxm_gs") != null)
                    yuyue.setWxxm_gs(subObject.get("wxxm_gs").getAsDouble()); // --标准工时
                if (subObject.get("wxxm_dj") != null)
                    yuyue.setWxxm_dj(subObject.get("wxxm_dj").getAsDouble()); // --单价
                if (subObject.get("wxxm_yje") != null)
                    yuyue.setWxxm_yje(subObject.get("wxxm_yje").getAsDouble());// --原工时费，
                // 默认价格要取到这个字段了
                if (subObject.get("wxxm_zk") != null)
                    yuyue.setWxxm_zk(subObject.get("wxxm_zk").getAsDouble()); // --会员折扣
                if (subObject.get("wxxm_je") != null)
                    yuyue.setWxxm_je(subObject.get("wxxm_je").getAsDouble()); // --会员工时费
                if (yuyue.getWxxm_gs() == 0) {
                    yuyue.setWxxm_dj(yuyue.getWxxm_je());
                } else {
                    yuyue.setWxxm_dj(yuyue.getWxxm_je() / yuyue.getWxxm_gs());
                }
                //try catch不好使会出现失败时间2017年10月31日14:53:27 @author LHW
				/*try{
						offer.setWxxm_dj(offer.getWxxm_je()/offer.getWxxm_gs());
					}catch(java.lang.Exception e){
				
				}*/
                yuyue.setWxxm_Print(true);
                Work_mx_gzEntity dd = dao.insert(yuyue);

                ////////////////////合计金额计算///////////////////////////////////
                Sql sql1_1 = Sqls
                        .create("update b set b.xche_rgf = a.xche_rgf,b.xche_rgbh=a.xche_rgf,b.xche_rgsl=0,xche_rgse=0 from work_pz_gz b,(select work_no,sum(wxxm_je) xche_rgf from work_mx_gz where work_no='" + nu + "' and wxxm_zt in ('正常','保险') group by work_no) a   where b.work_no = '" + nu + "' and a.work_no = b.work_no");
                dao.execute(sql1_1);
                Sql sql1_2 = Sqls
                        .create("update b set b.xche_clf = a.xche_clf,b.xche_clbh=a.xche_clf,b.xche_clsl=0,xche_clse=0 from work_pz_gz b,(select work_no,sum(peij_je) xche_clf from work_ll_gz where work_no='" + nu + "' and peij_zt in ('正常','保险')group by work_no ) a   where b.work_no = '" + nu + "' and a.work_no = b.work_no");
                dao.execute(sql1_2);
                Sql sql1_3 = Sqls
                        .create("update work_pz_gz set xche_yhje = isnull(xche_wxxm_yhje,0)+isnull(xche_peij_yhje,0) where work_no = '" + nu + "'");
                dao.execute(sql1_3);
                Sql sql1_4 = Sqls
                        .create("update work_pz_gz set xche_hjje= isnull(xche_rgf,0) + isnull(xche_clf,0) where work_no = '" + nu + "'");
                dao.execute(sql1_4);
                ///////////////////////////////////////////////////////////////


                if (dd == null) {
                    return "失败";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }

        return "存档成功";
    }

    /**
     * 维修进厂
     *
     * @param work_no
     * @return
     */
    @At
    @Ok("raw:json")
    public String wx_jinchang(String work_no) {
        Sql sql1 = Sqls
                .create("update work_pz_gz set mainstate=0,substate='未派车辆',xche_wxjd='在修',xche_jdrq=getdate() where work_no= '" + work_no + "'");
        dao.execute(sql1);

        Sql sql2 = Sqls
                .create("update work_pz_gz set xche_wxsj = isnull(DATEDIFF(minute, xche_jdrq, xche_yjwgrq),0) where work_no= '" + work_no + "'");
        dao.execute(sql2);

        Sql sql3 = Sqls
                .create("update work_mx_gz set wxxm_jd='未派工' where work_no= '" + work_no + "'");
        dao.execute(sql3);

//		Work_pz_gzEntity pz = dao.fetch(Work_pz_gzEntity.class, work_no);
        FieldFilter filter = FieldFilter.create(Work_pz_gzEntity.class, "^che_no|yuyue_no$");
        Work_pz_gzEntity pz = Daos.ext(dao, filter).fetch(Work_pz_gzEntity.class, work_no);

        Sql sql4 = Sqls
                .create("update work_yuyue_pz set yuyue_progress='已进店',yuyue_sjjcrq=getdate() where yuyue_no= '" + pz.getYuyue_no() + "'");
        dao.execute(sql4);

        Sql sql5 = Sqls
                .create("update work_baojia_pz set list_progress='已进店' where list_no=  '" + pz.getYuyue_no() + "'");
        dao.execute(sql5);

        Sql sql6 = Sqls
                .create("update work_cheliang_sm set che_rjlc = (xche_lc-xche_last_lc)/ case isnull(convert(numeric(18,2), xche_jdrq - xche_last_jdrq),0) when 0 then 1 else isnull(convert(numeric(18,2), xche_jdrq - xche_last_jdrq),0) end from work_cheliang_sm a,work_pz_gz b"
                        + " where a.che_no = b.che_no and a.che_no = '" + pz.getChe_no() + "' and work_no='" + work_no + "'");
        dao.execute(sql6);

        BsdUtils.updateWorkPzGz(dao, work_no);
        return jsons.json(1, 5, 1, "进厂成功");
    }

    /**
     * 添加材料
     *
     * @param json
     * @return
     */
    @At
    @Ok("raw:json")
    public String addcail(String json) {
        JsonParser parse = new JsonParser(); // 创建json解析器
        try {
            JsonObject js = (JsonObject) parse.parse(json); // 创建jsonObject对象
            JsonArray array = js.get("data").getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                Work_ll_gzEntity yuyue = new Work_ll_gzEntity();
                JsonObject subObject = array.get(i).getAsJsonObject();
                String nu = subObject.get("work_no").getAsString();
                yuyue.setWork_no(subObject.get("work_no").getAsString());
                if (i == 0) {
                    // dao.query(Work_yuyue_wxxmEntity.class,
                    // Cnd.where("yuyue_no","=",nu));
                    Work_ll_gzEntity ks = dao.fetch(Work_ll_gzEntity.class,
                            Cnd.where("work_no", "=", nu));
                    if (ks != null) {
                        dao.clear(Work_ll_gzEntity.class,
                                Cnd.where("work_no", "=", nu));
                    }
                }
                yuyue.setPeij_no(subObject.get("peij_no").getAsString());// 配件编码
                yuyue.setPeij_mc(subObject.get("peij_mc").getAsString());// 配件名称
                yuyue.setPeij_sl(subObject.get("peij_sl").getAsDouble());// 配件数量
                yuyue.setPeij_dj(subObject.get("peij_dj").getAsDouble());// 配件单价
                yuyue.setPeij_zt(subObject.get("peij_zt").getAsString());// 配件状态
                yuyue.setPeij_je(subObject.get("peij_je").getAsDouble());// 配件金额
                yuyue.setPeij_th(subObject.get("peij_th").getAsString());// 配件图号
                yuyue.setPeij_dw(subObject.get("peij_dw").getAsString());// 配件单位
                //yuyue.setPeij_ry(subObject.get("peij_ry").getAsString());//配件领料人
                if (subObject.get("peij_ydj") != null)
                    yuyue.setPeij_ydj(subObject.get("peij_ydj").getAsDouble()); // --原单价
                // 默认价格到这字个字段上
                if (subObject.get("peij_zk") != null)
                    yuyue.setPeij_zk(subObject.get("peij_zk").getAsDouble()); // --会员折扣
                if (subObject.get("peij_dj") != null)
                    yuyue.setPeij_dj(subObject.get("peij_dj").getAsDouble()); // --单价
                if (subObject.get("peij_je") != null)
                    yuyue.setPeij_je(subObject.get("peij_je").getAsDouble()); // --金额

                Work_ll_gzEntity dd = dao.insert(yuyue);
                ////////////////////合计金额计算///////////////////////////////////
                Sql sql1_1 = Sqls
                        .create("update b set b.xche_rgf = a.xche_rgf,b.xche_rgbh=a.xche_rgf,b.xche_rgsl=0,xche_rgse=0 from work_pz_gz b,(select work_no,sum(wxxm_je) xche_rgf from work_mx_gz where work_no='" + nu + "' and wxxm_zt in ('正常','保险') group by work_no) a   where b.work_no = '" + nu + "' and a.work_no = b.work_no");
                dao.execute(sql1_1);
                Sql sql1_2 = Sqls
                        .create("update b set b.xche_clf = a.xche_clf,b.xche_clbh=a.xche_clf,b.xche_clsl=0,xche_clse=0 from work_pz_gz b,(select work_no,sum(peij_je) xche_clf from work_ll_gz where work_no='" + nu + "' and peij_zt in ('正常','保险')group by work_no ) a   where b.work_no = '" + nu + "' and a.work_no = b.work_no");
                dao.execute(sql1_2);
                Sql sql1_3 = Sqls
                        .create("update work_pz_gz set xche_yhje = isnull(xche_wxxm_yhje,0)+isnull(xche_peij_yhje,0) where work_no = '" + nu + "'");
                dao.execute(sql1_3);
                Sql sql1_4 = Sqls
                        .create("update work_pz_gz set xche_hjje= isnull(xche_rgf,0) + isnull(xche_clf,0) where work_no = '" + nu + "'");
                dao.execute(sql1_4);
                ///////////////////////////////////////////////////////////////
                if (dd == null) {
                    return "失败";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }

        return "存档成功";
    }

    /**
     * @param work_no    维修单号
     * @throws UnsupportedEncodingException
     * @author LHW
     * @time 2017年8月29日9:13:24
     */
    @At
    @Ok("raw:json")
    public String dayin(String work_no) {
        //没有判断work_no是否为空
        String xche_cz = "";
        Sql sql = Sqls.queryRecord("select xche_cz from work_pz_gz where work_no = '" + work_no + "'");
        dao.execute(sql);
        List<Record> res3 = sql.getList(Record.class);
        if (res3.size() > 0) {
            xche_cz = res3.get(0).getString("xche_cz");
        }
        Sql sql2 = Sqls.queryRecord("select printpc from PrintServerset where billkind = '接待登记单'");
        dao.execute(sql2);
        List<Record> res2 = sql2.getList(Record.class);
        if (res2.size() > 0) {
            String printpc = res2.get(0).getString("printpc");
            Sql sql1 = Sqls
                    .queryRecord("insert into PrintServerLog(printdate,billkind,billno,printczy,printpc,printsource,flag_print ) "
                            + "values (getdate(),'接待登记单','" + work_no + "','" + xche_cz + "','" + printpc + "',1,0)");
            dao.execute(sql1);
            return jsons.json(1, 1, 1, "打印成功");
        }
        return jsons.json(1, 1, 0, "接待登记单没有找到！");
    }

    @At
    @Ok("raw:json")
    public String jbxxx(String pai, String gongsiNo, String caozuoyuan_xm) throws UnsupportedEncodingException {
        //pai = new String(pai .getBytes("iso8859-1"),"utf-8");
        //caozuoyuan_xm = new String(caozuoyuan_xm .getBytes("iso8859-1"),"utf-8");
        java.util.Calendar rightNow = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        // 如果是后退几天，就写 -天数 例如：
        rightNow.add(java.util.Calendar.DAY_OF_MONTH, -20);
        // 进行时间转换
        String date = sim.format(rightNow.getTime());
        List<Work_pz_gzEntity> result = dao.query(
                Work_pz_gzEntity.class,
                Cnd.where("che_no", "=", pai).and("xche_jdrq", ">", date)
                        .and("flag_fast", "=", 0).and("mainstate", "=", -1)
                        .desc("xche_jdrq"));
        System.out.println("=====jbxxjbxxjbxx===========jbxxjbxxjbxx==============");
        if (result.size() == 0) {
            String num = add(gongsiNo, caozuoyuan_xm);
            Work_pz_gzEntity pz = new Work_pz_gzEntity();
            pz.setWork_no(num);
            pz.setChe_no(pai);
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
            pz.setFlag_fast(false);
            pz.setMainstate(-1);
            pz.setFlag_pad(true);
            Work_cheliang_smEntity che = dao.fetch(Work_cheliang_smEntity.class, pai);
            if (che != null) {
                pz.setChe_cx(che.getChe_cx());
                pz.setChe_vin(che.getChe_vin());
                pz.setGcsj(che.getChe_gcrq());
                pz.setXche_lc(che.getChe_next_licheng());
                KehuEntity kehu = dao.fetch(KehuEntity.class, che.getKehu_no());
                if (kehu != null) {
                    pz.setKehu_mc(kehu.getKehu_mc());
                    pz.setKehu_dh(kehu.getKehu_dh());
                }
            } else {
                Work_cheliang_smEntity che_new = new Work_cheliang_smEntity();
                che_new.setChe_no(pai);
                che_new.setKehu_no(pai);
                dao.insert(che_new);
                KehuEntity kehu = new KehuEntity();
                kehu.setKehu_no(pai);
                kehu.setLastModifyTime(new Date());
                dao.insert(kehu);
            }

            int nu = dao.updateIgnoreNull(pz);
            result = dao.query(Work_pz_gzEntity.class,
                    Cnd.where("che_no", "=", pai).and("xche_jdrq", ">", date)
                            .desc("xche_jdrq"));
        }
        for (Work_pz_gzEntity o : result) {
            Work_cheliang_smEntity ss = dao.fetch(Work_cheliang_smEntity.class,
                    o.getChe_no());
            if (ss != null)
                o.setGcsj(ss.getChe_gcrq());
        }
        String json = Json.toJson(result, JsonFormat.full());
        if (result.size() != 0) {
            return jsons.json(1, result.size(), 1, json);
        }
        return jsons.json(1, result.size(), 0, json);
    }

    /**
     * 添加新的维修项目
     *
     * @param addLists
     * @return
     */
    @At
    @Ok("raw:json")
    public String addNewWxxm(String addLists) {
        JsonParser parse = new JsonParser(); // 创建json解析器
        try {
            String workNo = "";
            JsonArray array = (JsonArray) parse.parse(addLists);
            for (int i = 0; i < array.size(); i++) {
                Work_mx_gzEntity workMxGzEntity = new Work_mx_gzEntity();
                JsonObject item = array.get(i).getAsJsonObject();
                workNo = item.get("work_no").getAsString();
                workMxGzEntity.setWork_no(workNo);
                workMxGzEntity.setWxxm_yje(item.get("wxxm_je").getAsDouble());
                workMxGzEntity.setWxxm_no(item.get("wxxm_no").getAsString());
                workMxGzEntity.setWxxm_mc(item.get("wxxm_mc").getAsString());
                workMxGzEntity.setWxxm_gs(item.get("wxxm_gs").getAsDouble());
                workMxGzEntity.setWxxm_je(item.get("wxxm_je").getAsDouble());
                workMxGzEntity.setWxxm_tpye(item.get("wxxm_Tpye").getAsString());
                workMxGzEntity.setWxxm_zt(item.get("wxxm_zt").getAsString());
                workMxGzEntity.setWxxm_khgs(item.get("wxxm_gs").getAsDouble());
                if (item.get("wxxm_gs") != null)
                    workMxGzEntity.setWxxm_gs(item.get("wxxm_gs").getAsDouble()); // --标准工时
                if (item.get("wxxm_dj") != null)
                    workMxGzEntity.setWxxm_dj(item.get("wxxm_dj").getAsDouble()); // --单价
                if (item.get("wxxm_yje") != null)
                    workMxGzEntity.setWxxm_yje(item.get("wxxm_yje").getAsDouble());// --原工时费，
                // 默认价格要取到这个字段了
                if (item.get("wxxm_zk") != null)
                    workMxGzEntity.setWxxm_zk(item.get("wxxm_zk").getAsDouble()); // --会员折扣
                if (item.get("wxxm_je") != null)
                    workMxGzEntity.setWxxm_je(item.get("wxxm_je").getAsDouble()); // --会员工时费
                if (workMxGzEntity.getWxxm_gs() == 0) {
                    workMxGzEntity.setWxxm_dj(workMxGzEntity.getWxxm_je());
                } else {
                    workMxGzEntity.setWxxm_dj(workMxGzEntity.getWxxm_je() / workMxGzEntity.getWxxm_gs());
                }
                workMxGzEntity.setWxxm_Print(true);
                Work_mx_gzEntity newWxxm = dao.insert(workMxGzEntity);
                if (newWxxm == null) {
                    return "fail";
                }
            }
            ////////////////////合计金额计算///////////////////////////////////
            Sql sql1_1 = Sqls
                    .create("update b set b.xche_rgf = a.xche_rgf,b.xche_rgbh=a.xche_rgf,b.xche_rgsl=0,xche_rgse=0 from work_pz_gz b,(select work_no,sum(wxxm_je) xche_rgf from work_mx_gz where work_no='" + workNo + "' and wxxm_zt in ('正常','保险') group by work_no) a   where b.work_no = '" + workNo + "' and a.work_no = b.work_no");
            dao.execute(sql1_1);
            Sql sql1_2 = Sqls
                    .create("update b set b.xche_clf = a.xche_clf,b.xche_clbh=a.xche_clf,b.xche_clsl=0,xche_clse=0 from work_pz_gz b,(select work_no,sum(peij_je) xche_clf from work_ll_gz where work_no='" + workNo + "' and peij_zt in ('正常','保险')group by work_no ) a   where b.work_no = '" + workNo + "' and a.work_no = b.work_no");
            dao.execute(sql1_2);
            Sql sql1_3 = Sqls
                    .create("update work_pz_gz set xche_yhje = isnull(xche_wxxm_yhje,0)+isnull(xche_peij_yhje,0) where work_no = '" + workNo + "'");
            dao.execute(sql1_3);
            Sql sql1_4 = Sqls
                    .create("update work_pz_gz set xche_hjje= isnull(xche_rgf,0) + isnull(xche_clf,0) where work_no = '" + workNo + "'");
            dao.execute(sql1_4);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    /**
     * 添加材料
     *
     * @param json
     * @return
     */
    @At
    @Ok("raw:json")
    public String addNewWxcl(String addLists) {
        JsonParser parse = new JsonParser(); // 创建json解析器
        try {
            String workNo = "";
            JsonArray array = (JsonArray) parse.parse(addLists);
            for (int i = 0; i < array.size(); i++) {
                Work_ll_gzEntity workLlGzEntity = new Work_ll_gzEntity();
                JsonObject item = array.get(i).getAsJsonObject();
                workNo = item.get("work_no").getAsString();
                String peijNo = item.get("peij_no").getAsString();
                Work_ll_gzEntity ks = dao.fetch(Work_ll_gzEntity.class,
                        Cnd.where("work_no", "=", workNo).and("peij_no", "=", peijNo));
                // 如果为null，说明需要新加这个配件
                if (ks == null) {
                    workLlGzEntity.setWork_no(item.get("work_no").getAsString());
                    workLlGzEntity.setPeij_no(item.get("peij_no").getAsString());// 配件编码
                    workLlGzEntity.setPeij_mc(item.get("peij_mc").getAsString());// 配件名称
                    workLlGzEntity.setPeij_sl(item.get("peij_sl").getAsDouble());// 配件数量
                    workLlGzEntity.setPeij_dj(item.get("peij_dj").getAsDouble());// 配件单价
                    workLlGzEntity.setPeij_zt(item.get("peij_zt").getAsString());// 配件状态
                    workLlGzEntity.setPeij_je(item.get("peij_je").getAsDouble());// 配件金额
                    workLlGzEntity.setPeij_th(item.get("peij_th").getAsString());// 配件图号
                    workLlGzEntity.setPeij_dw(item.get("peij_dw").getAsString());// 配件单位
                    //yuyue.setPeij_ry(subObject.get("peij_ry").getAsString());//配件领料人
                    if (item.get("peij_ydj") != null)
                        workLlGzEntity.setPeij_ydj(item.get("peij_ydj").getAsDouble()); // --原单价
                    // 默认价格到这字个字段上
                    if (item.get("peij_zk") != null)
                        workLlGzEntity.setPeij_zk(item.get("peij_zk").getAsDouble()); // --会员折扣
                    if (item.get("peij_dj") != null)
                        workLlGzEntity.setPeij_dj(item.get("peij_dj").getAsDouble()); // --单价
                    if (item.get("peij_je") != null)
                        workLlGzEntity.setPeij_je(item.get("peij_je").getAsDouble()); // --金额
                    Work_ll_gzEntity dd = dao.insert(workLlGzEntity);
                    if (dd == null) {
                        return "fail";
                    }
                } else { // 否则的话说明存在，则需要更新
                    if (ks.getPeij_sl() + item.get("peij_sl").getAsDouble() != 0) {
                        Sql sqlUpdate = Sqls
                                .create("update work_ll_gz set peij_sl = peij_sl + " + item.get("peij_sl").getAsDouble() + ",peij_je = peij_je + " + item.get("peij_je").getAsDouble() + " where work_no='" + workNo + "' and peij_no ='" + peijNo + "'");
                        dao.execute(sqlUpdate);
                    } else {
                        Sql sqlDelete = Sqls
                                .create("delete from work_ll_gz where work_no='" + workNo + "' and peij_no ='" + peijNo + "'");
                        dao.execute(sqlDelete);
                    }
                }
            }
            ////////////////////合计金额计算///////////////////////////////////
            Sql sql1_1 = Sqls
                    .create("update b set b.xche_rgf = a.xche_rgf,b.xche_rgbh=a.xche_rgf,b.xche_rgsl=0,xche_rgse=0 from work_pz_gz b,(select work_no,sum(wxxm_je) xche_rgf from work_mx_gz where work_no='" + workNo + "' and wxxm_zt in ('正常','保险') group by work_no) a   where b.work_no = '" + workNo + "' and a.work_no = b.work_no");
            dao.execute(sql1_1);
            Sql sql1_2 = Sqls
                    .create("update b set b.xche_clf = a.xche_clf,b.xche_clbh=a.xche_clf,b.xche_clsl=0,xche_clse=0 from work_pz_gz b,(select work_no,sum(peij_je) xche_clf from work_ll_gz where work_no='" + workNo + "' and peij_zt in ('正常','保险')group by work_no ) a   where b.work_no = '" + workNo + "' and a.work_no = b.work_no");
            dao.execute(sql1_2);
            Sql sql1_3 = Sqls
                    .create("update work_pz_gz set xche_yhje = isnull(xche_wxxm_yhje,0)+isnull(xche_peij_yhje,0) where work_no = '" + workNo + "'");
            dao.execute(sql1_3);
            Sql sql1_4 = Sqls
                    .create("update work_pz_gz set xche_hjje= isnull(xche_rgf,0) + isnull(xche_clf,0) where work_no = '" + workNo + "'");
            dao.execute(sql1_4);
            ///////////////////////////////////////////////////////////////
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    /**
     * 更改维修项目折扣
     * @param work_no
     * @param wxxm_zk
     * @return
     */
    @At
    @Ok("raw:json")
    public String updateWxxmZk(String work_no, String wxxm_zk) {
        Sql sql = Sqls
                .create("update work_mx_gz set wxxm_zk=" + wxxm_zk + ",wxxm_je=wxxm_yje*" + wxxm_zk + ",wxxm_dj=wxxm_yje*" + wxxm_zk + "/(case wxxm_gs when 0 then 1 else wxxm_gs end) where work_no = '" + work_no + "'");
        dao.execute(sql);
        BsdUtils.updateWorkPzGz(dao, work_no);
        return "success";
    }

    /**
     * 更改维修材料折扣
     * @param work_no
     * @param wxcl_zk
     * @return
     */
    @At
    @Ok("raw:json")
    public String updateWxclZk(String work_no, String wxcl_zk) {
        Sql sql = Sqls
                .create("update work_ll_gz set peij_zk=" + wxcl_zk + ",peij_je=peij_yje*" + wxcl_zk + ",peij_dj=peij_yje*" + wxcl_zk + "/peij_sl where work_no = '" + work_no + "'");
        dao.execute(sql);
        BsdUtils.updateWorkPzGz(dao, work_no);
        return "success";
    }
}
