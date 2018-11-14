package org.aotu.publics.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aotu.Jsons;
import org.aotu.VIPcard.entity.CardKindEntity;
import org.aotu.appointment.entity.Work_yuyue_pzEntity;
import org.aotu.offer.entity.baoJiaEntity;
import org.aotu.offer.entity.feilvEntity;
import org.aotu.order.entity.Work_pz_gzEntity;
import org.aotu.publics.eneity.GongzryEntity;
import org.aotu.publics.eneity.Jizhang_yhdyEntity;
import org.aotu.publics.eneity.KehuEntity;
import org.aotu.publics.eneity.Sm_app_defpriceEntity;
import org.aotu.publics.eneity.Sm_cangkEntity;
import org.aotu.publics.eneity.Sm_deptEntity;
import org.aotu.publics.eneity.Sm_fapiaoEntity;
import org.aotu.publics.eneity.Sm_jiesuanEntity;
import org.aotu.publics.eneity.Sm_peijcx_newEntity;
import org.aotu.publics.eneity.Sm_peijlbEntity;
import org.aotu.publics.eneity.Sm_wxxmlbEntity;
import org.aotu.publics.eneity.Work_cheliang_smEntity;
import org.aotu.publics.eneity.Work_feilv_smEntity;
import org.aotu.publics.eneity.Work_weixiu_cxmxEntity;
import org.aotu.publics.eneity.Work_weixiu_flEntity;
import org.aotu.publics.eneity.Work_weixiu_fsEntity;
import org.aotu.publics.eneity.Work_weixiu_smEntity;
import org.aotu.user.entity.userEntity;
import org.aotu.util.BsdUtils;
import org.aotu.util.UploadFileUtils;
import org.apache.commons.io.FileUtils;
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
import org.nutz.dao.Cnd;
import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * title:TItemType
 *
 * @author Zhang Yalong
 * @Description:<描述>
 * @date 2017-4-18 上午8:56:24
 * @version: V1.0
 */
enum TItemType {
    ITUnkonw, ITcangk, ITcang_dmmc, ITfapiao, ITjb, ITdept, ITCzy, ITchexing, ITDanw, IT_RYXueLi, IT_RYZhiwu, IT_RYBanZu, ITfeiyong, ITrukulb, ITchukulb, ITchuruKulb, ITDanw_lc, ITJiesuan, ITCaoZuoYuan, ITGongSiCaoZuoYuan, ITCaoZuoYuanGongSi, ITBaoZhuangfs, ITYunshufs, ITBank, ITCardKind, ITDanweizuMc, ITAllGongSi, ITGongSi, ITcangkDo, ITWxfs, ITSfbz, ITState, ITStateAll, ITYwlb, ITGongSiShare, ITGongSiShareAll, ITPathNo, ITFanXiuLb, ITJylx, ITFhFs, ITGdfl, ITChe_nf, ITChe_pl, ITChe_pp, ITChe_pd, ITChe_dp, ITChe_fd, ITChe_bs, ITWx_CaoZuo, ITMsgList, ITBanZuMc, ITGongWei, ITPause, ITStop, ITJcr, ITCheckRen, ITJiShi, ITDiaoDu, ITXiChe, ITPeijPp, ITChe_XingZhi, ITChe_Chex_Fl, ITall, ITGongShi
};

/**
 * title:publicModule
 *
 * @author Zhang Yalong
 * @Description:<描述>
 * @date 2017-4-18 上午8:56:02
 * @version: V1.0
 */
@IocBean
@At("/public")
public class publicModule {
    @Inject
    Dao dao;

    @Inject
    Jsons jsons;
    @Inject
    PropertiesProxy conf;
    @Inject
    publicModule pu;


    private static Map jiageDengji;

    static {
        jiageDengji = new HashMap<String, Integer>();

        jiageDengji.put("无", 0);
        jiageDengji.put("一级进价", 26);
        jiageDengji.put("二级进价", 27);
        jiageDengji.put("三级进价", 28);
        jiageDengji.put("一级销价", 16);
        jiageDengji.put("二级销价", 17);
        jiageDengji.put("三级销价", 18);
        jiageDengji.put("本仓库平均价", 2);
        jiageDengji.put("各仓库汇总平均价", 1);
        jiageDengji.put("本仓现存最早进货之进价", 22);
        jiageDengji.put("最近一次进货价格", 23);
        jiageDengji.put("最近一次销售价格", 13);
        jiageDengji.put("历史最高进价", 24);
        jiageDengji.put("历史最低进价", 25);
        jiageDengji.put("该供应商历史结算价", 21);
        jiageDengji.put("该客户上次用料价", 31);
        jiageDengji.put("该客户上次用料结算价", 32);
        jiageDengji.put("该客户历史结算价", 11);
        jiageDengji.put("历史最高售价", 14);
        jiageDengji.put("历史最低售价", 15);
        jiageDengji.put("该配件最低售价", 33);

    }

    @At
    @Ok("raw:json")
    public String getVersion() {
        String version = "{\"version\":\"" + conf.get("version")
                + "\",\"url\":\"http://wdpbjd.bsd268.com:8888/app-releaseV"
                + conf.get("version") + ".apk\"}";
        return jsons.json(0, 0, 0, version);
    }

    @At
    @Ok("raw:json")
    public String getImgUrl() {
        System.out.println(conf.get("url"));
        return jsons.json(0, 0, 0, conf.get("url"));
    }

    /**
     * @param kehu_no
     * @param kehu_mc
     * @param kehu_dh
     */
    public KehuEntity saveKeHujb(String kehu_no, String kehu_mc, String kehu_dh, String kehu_xm, String kehu_sj) {
        List<KehuEntity> list = dao.query(KehuEntity.class,
                Cnd.where("kehu_no", "=", kehu_no));
        if (list.size() > 0) {
            KehuEntity ke = list.get(0);
            if (kehu_mc != null && kehu_mc.length() > 0)
                ke.setKehu_mc(kehu_mc);
            if (kehu_dh != null && kehu_dh.length() > 0)
                ke.setKehu_dh(kehu_dh);
            if (kehu_xm != null && kehu_xm.length() > 0)
                ke.setKehu_xm(kehu_xm);
            if (kehu_sj != null && kehu_sj.length() > 0)
                ke.setKeHu_CanUsedInGongSi("全公司共享");
            ke.setKehu_sj(kehu_sj);
            dao.update(ke);
            return ke;
        } else {
            KehuEntity ke = new KehuEntity();
            ke.setKehu_mc(kehu_mc);
            ke.setKehu_dh(kehu_dh);
            ke.setKehu_no(kehu_no);
            ke.setKehu_dh(kehu_xm);
            ke.setKehu_no(kehu_sj);
            ke.setKeHu_CanUsedInGongSi("全公司共享");
            dao.insert(ke);
            return ke;
        }
    }

    /**
     * @param kehu_no
     * @param kehu_mc
     * @param kehu_dh
     */
    public void saveKeHu(String kehu_no, String kehu_mc, String kehu_dh) {
        List<KehuEntity> list = dao.query(KehuEntity.class,
                Cnd.where("kehu_no", "=", kehu_no));
        KehuEntity kehuEntity = dao.fetch(KehuEntity.class, kehu_no);
        if (kehuEntity != null) {
            kehuEntity.setKehu_mc(kehu_mc);
            kehuEntity.setKehu_dh(kehu_dh);
            dao.update(kehuEntity, "^kehu_mc|kehu_dh$");
        } else {
            kehuEntity = new KehuEntity();
            kehuEntity.setKehu_no(kehu_no);
            kehuEntity.setKehu_mc(kehu_mc);
            kehuEntity.setKehu_dh(kehu_dh);
            kehuEntity.setKeHu_CanUsedInGongSi("全公司共享");
            dao.insert(kehuEntity);
        }
    }

    public void saveKeHu(String kehu_no, String kehu_mc) {
        List<KehuEntity> list = dao.query(KehuEntity.class,
                Cnd.where("kehu_no", "=", kehu_no));
        if (list.size() > 0) {
            KehuEntity ke = list.get(0);
            if (kehu_mc != null && kehu_mc.length() > 0)
                ke.setKehu_mc(kehu_mc);
            ke.setKeHu_CanUsedInGongSi("全公司共享");
            dao.update(ke);
        } else {
            KehuEntity ke = new KehuEntity();
            ke.setKehu_mc(kehu_mc);
            ke.setKehu_no(kehu_no);
            ke.setKeHu_CanUsedInGongSi("全公司共享");
            dao.insert(ke);
        }
    }

    SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");

    /**
     * @param che_no
     * @param che_gcrq
     * @param che_cx
     * @param che_vin
     */
    public Work_cheliang_smEntity saveCheInfo(String che_no, Date che_gcrq, String che_cx, String che_vin, String gongsino) {
        Work_cheliang_smEntity cheliangSmEntity = dao.fetch(Work_cheliang_smEntity.class, che_no);
        if (cheliangSmEntity != null) {
            cheliangSmEntity.setChe_cx(che_cx);
            cheliangSmEntity.setGongSiNo(gongsino);
            cheliangSmEntity.setChe_vin(che_vin);
            cheliangSmEntity.setChe_gcrq(che_gcrq);
            dao.update(cheliangSmEntity, "^che_cx|gongsino|che_vin|che_gcrq$");
            return cheliangSmEntity;
        } else {
            cheliangSmEntity = new Work_cheliang_smEntity();
            cheliangSmEntity.setChe_cx(che_cx);
            cheliangSmEntity.setChe_vin(che_vin);
            cheliangSmEntity.setGongSiNo(gongsino);
            cheliangSmEntity.setChe_gcrq(che_gcrq);
            cheliangSmEntity.setKehu_no(che_no);
            dao.insert(cheliangSmEntity);
            return cheliangSmEntity;
        }
    }

    /**
     * @param che_no
     */
    public Work_cheliang_smEntity saveCheInfo(String che_no) {
        List<Work_cheliang_smEntity> list_che = dao.query(
                Work_cheliang_smEntity.class, Cnd.where("che_no", "=", che_no));
        if (list_che.size() > 0) {
            Work_cheliang_smEntity che = list_che.get(0);

            dao.update(che);
            return che;
        } else {
            Work_cheliang_smEntity che = new Work_cheliang_smEntity();
            che.setKehu_no(che_no);
            dao.insert(che);
            return che;
        }
    }

    /**
     * @author LHW
     * @time 2017年8月30日16:01:21
     * 车辆添加其他颜色信息
     */
    public Work_cheliang_smEntity saveCheInfose(String xche_bz, String che_no, Date che_gcrq, String che_cx,
                                                String che_vin, String che_nf, String che_wxys) {
        Work_cheliang_smEntity entity = dao.fetch(Work_cheliang_smEntity.class, che_no);
        if (entity != null) {
            entity.setChe_cx(che_cx);
            entity.setChe_vin(che_vin);
            entity.setChe_gcrq(che_gcrq);
            entity.setChe_nf(che_nf);
            entity.setChe_wxys(che_wxys);
            entity.setChe_bz(xche_bz);
            dao.update(entity, "^che_cx|che_vin|che_gcrq|che_nf|che_wxys|xche_bz$");
            return entity;
        } else {
            entity = new Work_cheliang_smEntity();
            entity.setKehu_no(che_no);
            entity.setChe_cx(che_cx);
            entity.setChe_bz(xche_bz);
            entity.setChe_vin(che_vin);
            entity.setChe_gcrq(che_gcrq);
            dao.insert(entity);
            return entity;
        }
    }

    /**
     * @author LHW
     * @time 2017年8月30日16:01:21
     * 车辆基本信息
     */
    public Work_cheliang_smEntity saveCheInfosejb(String che_no, Date che_gcrq, String che_cx, String che_bz,
                                                  String che_vin, String che_nf, String che_wxys, Date che_next_byrq, Date che_jianche_dqrq, Date che_jiaoqx_dqrq, Date che_shangyex_dqrq) {
        List<Work_cheliang_smEntity> list_che = dao.query(
                Work_cheliang_smEntity.class, Cnd.where("che_no", "=", che_no));
        if (list_che.size() > 0) {
            Work_cheliang_smEntity che = list_che.get(0);
            che.setChe_cx(che_cx);
            che.setChe_vin(che_vin);
            che.setChe_gcrq(che_gcrq);
            che.setChe_nf(che_nf);
            che.setChe_wxys(che_wxys);
            che.setChe_next_byrq(che_next_byrq);
            che.setChe_jianche_dqrq(che_jianche_dqrq);
            che.setChe_jiaoqx_dqrq(che_jiaoqx_dqrq);
            che.setChe_shangyex_dqrq(che_shangyex_dqrq);
            che.setChe_bz(che_bz);
            dao.update(che);
            return che;
        } else {
            Work_cheliang_smEntity che = new Work_cheliang_smEntity();
            che.setChe_cx(che_cx);
            che.setChe_vin(che_vin);
            che.setChe_bz(che_bz);
            che.setChe_gcrq(che_gcrq);
            che.setKehu_no(che_no);
            che.setChe_nf(che_nf);
            che.setChe_wxys(che_wxys);
            che.setChe_next_byrq(che_next_byrq);
            che.setChe_jianche_dqrq(che_jianche_dqrq);
            che.setChe_jiaoqx_dqrq(che_jiaoqx_dqrq);
            che.setChe_shangyex_dqrq(che_shangyex_dqrq);
            dao.insert(che);
            return che;
        }
    }

    /**
     * 下拉集合汇总
     *
     * @param type
     * @param name
     * @return
     * @throws UnsupportedEncodingException
     */
    @At
    @Ok("raw:json")
    public String dept(TItemType type, String reny_xm, String GongSiNo, String... name) {
        List<?> result;
        String json;
        try {
            switch (type) {
                //2018年4月2日16:08:24 洗车单结算用到银行账号
                case ITBank: // 银行
                    ArrayList<Object> list = new ArrayList<>();
                    List<Jizhang_yhdyEntity> ji = dao.query(Jizhang_yhdyEntity.class, Cnd.where("GongSiNo", "=", GongSiNo));
                    list.add("现金");
                    for (Jizhang_yhdyEntity jizhang_yhdyEntity : ji) {
                        list.add(jizhang_yhdyEntity.getYh_name());
                    }
                    if (!list.contains("微信支付")) {
                        list.add("微信支付");
                    }
                    json = Json.toJson(list, JsonFormat.full());
                    if (list.size() != 0) {
                        return jsons.json(1, list.size(), 1, json);
                    }
                    return jsons.json(1, list.size(), 0, json);

                case ITdept: // 部门
                    result = dao.query(Sm_deptEntity.class, null);
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);

                case ITjb: // 工作人员
                    if (reny_xm != null && reny_xm != "") {
                        //2017年12月13日16:30:59 修改模糊查询人员
                        result = dao.query(GongzryEntity.class, Cnd.where("reny_xm", "like", "%" + reny_xm + "%").or("reny_lp", "like", "%" + reny_xm + "%"));
                    } else {
                        result = dao.query(GongzryEntity.class, null);
                    }

                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);

                case ITJcr: // 服务顾问
                    result = dao.query(GongzryEntity.class,
                            Cnd.where("reny_role", "=", "服务顾问"));
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);

                case ITDiaoDu: // 调度师
                    result = dao.query(GongzryEntity.class,
                            Cnd.where("reny_role", "=", "调度师"));
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);

                case ITXiChe: // 洗车人员
                    result = dao.query(GongzryEntity.class,
                            Cnd.where("reny_role", "=", "洗车人员"));
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);
                case ITall:// 所有人员，派工
                    //2017年12月13日16:30:59 修改模糊查询人员
                    if (reny_xm != null && reny_xm != "") {
                        result = dao.query(GongzryEntity.class, Cnd.where("reny_xm", "like", "%" + reny_xm + "%").or("reny_lp", "like", "%" + reny_xm + "%"));
                    } else {
                        result = dao.query(GongzryEntity.class, null);
                    }

                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);
                case ITJiShi: // 技师
                    result = dao.query(GongzryEntity.class,
                            Cnd.where("reny_role", "=", "技师"));
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);

                case ITfapiao: // 发票类型
                    result = dao.query(Sm_fapiaoEntity.class, null);
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);

                case ITJiesuan: // 结算方式
                    result = dao.query(Sm_jiesuanEntity.class, null);
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);

                case ITcangk: // 仓库----
                    if (null != name) {
                        ArrayList<String> arr = new ArrayList<String>();
                        List<userEntity> li = dao.query(userEntity.class,
                                Cnd.where("caozuoyuan_xm", "=", name[0]));
                        List<Sm_cangkEntity> res = dao.query(Sm_cangkEntity.class,
                                null);
                        String str = li.get(0).getCangku_gndm();
                        String[] s = str.split(",");
                        for (int i = 0; i < s.length; i++) {
                            for (Sm_cangkEntity re : res) {
                                int s1 = s[i].indexOf(re.getCangk_dm());
                                if (s1 > -1) {
                                    arr.add(re.getCangk_dm());
                                }
                            }
                        }
                        String sd1 = arr.toString().replace("[", "")
                                .replace("]", "");
                        List<Sm_cangkEntity> rst = dao.query(Sm_cangkEntity.class,
                                Cnd.where("cangk_dm", "in", sd1));
                        json = Json.toJson(rst, JsonFormat.full());
                        if (rst.size() != 0) {
                            return jsons.json(1, rst.size(), 1, json);
                        }
                        return jsons.json(1, rst.size(), 0, json);
                    }
                    return jsons.json(1, 0, 0, "");

                case ITWxfs: // 维修方式
                    result = dao.query(Work_weixiu_fsEntity.class, null);
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);

                case ITYwlb: // 维修类别
                    result = dao.query(Work_weixiu_flEntity.class, null);
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);
                case ITSfbz: // 收费标准
                    result = dao.query(Work_feilv_smEntity.class, null);
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);
                case ITCardKind:
                    result = dao.query(CardKindEntity.class, null);
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    }
                    return jsons.json(1, result.size(), 0, json);
                case ITGongShi:// 工时费
                    result = dao.query(feilvEntity.class, null);
                    json = Json.toJson(result, JsonFormat.full());
                    if (result.size() != 0) {
                        return jsons.json(1, result.size(), 1, json);
                    } else {
                        return jsons.json(1, result.size(), 0, json);
                    }
                default:
                    break;
            }
            json = Json.toJson("", JsonFormat.full());
            return jsons.json(1, 0, 0, json);
        } catch (NullPointerException e) {
            json = Json.toJson("", JsonFormat.full());
            return jsons.json(0, 0, 0, json);
        }

    }

    public List getCangKuListByCaoZuoYuan(String caozuoyuanxm) {
        List<Record> list = new ArrayList();
        String cangkudo_gndm = "";
        Sql sql2 = Sqls.queryRecord("select cangkudo_gndm from sm_caozuoyuan where caozuoyuan_xm = '" + caozuoyuanxm + "'");
        dao.execute(sql2);
        List<Record> res2 = sql2.getList(Record.class);
        if (res2.size() > 0) {
            cangkudo_gndm = res2.get(0).getString("cangkudo_gndm");
        }
        Sql sql1 = Sqls.queryRecord("select * from sm_cangk where isnull(cangk_hide,0) = 0 order by cangk_dm");
        dao.execute(sql1);
        List<Record> res = sql1.getList(Record.class);
        for (Record record : res) {
            if (cangkudo_gndm.contains(record.getString("cangk_dm")))
                list.add(record);
        }
        return list;
    }

    @At
    @Ok("raw:json")
    public String getCangKuByCaoZuoYuan(String caozuoyuanxm) {
        List list = getCangKuListByCaoZuoYuan(caozuoyuanxm);
        String json = Json.toJson(list, JsonFormat.full());
        return jsons.json(1, list.size(), 1, json);
    }

    /**
     * 根据车型全称，获取内码
     *
     * @param quanMing
     * @return
     */
    @At
    @Ok("raw:json")
    public String getNeiMa(String quanMing) {
        if (quanMing != null && quanMing.length() > 0) {
            if (quanMing.indexOf("【") > 0) {
                String xiangxi = quanMing.substring(quanMing.indexOf("【") + 1,
                        quanMing.indexOf("】"));
                Sql sql1 = Sqls
                        .queryRecord("select chex_dm from sm_peijcx_new where chex_mc_std = '"
                                + xiangxi + "'");
                dao.execute(sql1);
                List<Record> res = sql1.getList(Record.class);
                if (res.size() > 0) {
                    return jsons.json(1, 1, 1, res.get(0).getString("chex_dm"));
                } else {
                    return jsons.json(1, 1, 0, "没有查询到");
                }
            } else {
                Sql sql1 = Sqls
                        .queryRecord("select chex_dm from sm_peijcx_new where chex_mc = '"
                                + quanMing + "' and che_level = 4 ");
                dao.execute(sql1);
                List<Record> res = sql1.getList(Record.class);
                if (res.size() > 0) {
                    return jsons.json(1, 1, 1, res.get(0).getString("chex_dm"));
                } else {
                    return jsons.json(1, 1, 0, "没有查询到");
                }
            }
        } else {
            return jsons.json(1, 1, 0, "参数为空！");
        }
    }

    /**
     * @param car_no 车牌
     * @return
     */
    @At
    @Ok("raw:json")
    public String getCarImage(String car_no) {
        String path = conf.get("CarImagePath");
        String version = "[{\"img\":\"http://pic10.nipic.com/20101010/3970232_002237089628_2.jpg\"},{\"img\":\"http://img1.bitautoimg.com/autoalbum/files/20150318/778/07172777893715_3933947_3.JPG\"},{\"img\":\"http://img3.bitautoimg.com/autoalbum/files/20160929/541/15421454102786_5300962_4.JPG\"},{\"img\":\"http://img2.bitautoimg.com/autoalbum/files/20160929/469/15420146936124_5300945_4.JPG\"}]";
        return jsons.json(0, 1, 1, version);
    }

    /**
     * 底部
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @At
    @Ok("raw:json")
    public String bottom() {
        @SuppressWarnings("rawtypes")
        ArrayList list = new ArrayList();
        list.add(0, "3868686");
        list.add(1, "138888888");
        list.add(2, "139999999");
        String json = Json.toJson(list, JsonFormat.full());
        return jsons.json(1, list.size(), 1, json);

    }

    /**
     * 维修项目结构
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String wxxm() {
        List<Sm_wxxmlbEntity> result = dao.query(Sm_wxxmlbEntity.class, null);
        String json = Json.toJson(result, JsonFormat.full());
        if (result.size() != 0) {
            return jsons.json(1, result.size(), 1, json);
        }
        return jsons.json(1, result.size(), 0, json);

    }

    /**
     * 维修内容明细-车型明细
     *
     * @param wxxm_no
     * @return
     */
    @At
    @Ok("raw:json")
    public String wxnr_cx(String wxxm_no) {
        List<Work_weixiu_cxmxEntity> result = dao.query(
                Work_weixiu_cxmxEntity.class,
                Cnd.where("wxxm_no", "=", wxxm_no));
        return jsons.json(1, result.size(), 1,
                Json.toJson(result, JsonFormat.full()));
    }

    /**
     * 维修内容明细
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String wxnr(int lx, String lbdm, int pageNumber, String name) {
        Pager pager = dao.createPager(pageNumber, 20);
        List<Work_weixiu_smEntity> result;
        Cnd cb = Cnd.where("1", "=", 1);
        if (lx == 1) {
            cb = cb.and("wxxm_by", "=", true);
        } else if (lx == 3) {
            cb = cb.and("flag_wjg", "=", true);
        } else if (lx == 2) {
            cb = cb.and("isnull(flag_wjg,0)", "=", "0").and(
                    "isnull(wxxm_by,0)", "=", "0");
        }
        if (lbdm != null && !"".equals(lbdm)) {
            cb = cb.and("wxxm_lbdm", "like", lbdm + "%");
        }
        if (name != null && !"".equals(name)) {
            cb = cb.and("wxxm_mc", "like", "%" + name + "%").or("wxxm_py", "like",
                    "%" + name + "%");
        }
        result = dao.query(Work_weixiu_smEntity.class, cb, pager);
        String json = Json.toJson(result, JsonFormat.full());
        //下面这样写不对啊，如果一个类别下就是没有维修项目，那就显示查询失败了
//		if (result.size() != 0) {
//			return jsons.json(1, result.size(), 1, json);
//		}
//		return jsons.json(1, result.size(), 0, json);
        // 应该是，程序运行到这里，必定是查询成功了，即使是查询结果是0条记录，也是查询成功了，除非去捕捉异常，捕捉到才显示失败
        return jsons.json(1, 1, 1, json);
    }

    //修改时间2017年12月7日09:17:53
    //因为修改wxxmjg接口出现错误 添加参数 felv_mc
    public double getUserZkwxxmjg(String card_no, String wxxm_no,
                                  String che_cx, String che_no, int fenlei, String no, String feil_mc) {
        double wxxmjg = wxxmjg(wxxm_no, che_cx, che_no, fenlei, no, feil_mc);
        Sql sql1 = Sqls
                .queryRecord("select CardKind.ItemRate,CardKind.PeijRate from kehu_card ,CardKind where kehu_card.card_no ='"
                        + card_no + "' and card_kind = CardKind.cardkind;");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        if (res1.size() > 0) {
            String ItemRate = res1.get(0).getString("ItemRate");
            String PeijRate = res1.get(0).getString("PeijRate");
            double jsNumber = wxxmjg * Double.parseDouble(ItemRate);
            return jsNumber;
        }
        return wxxmjg;
    }

    /**
     * 首先看车型维修项目表 select * from work_weixiu_cxmx where wxxm_no = '1719' and
     * che_cx = '末级的车型名称' 如果有值就看 select wxxm_jffs ,* from work_weixiu_cxmx where
     * wxxm_no = '1719' and che_cx = '末级的车型名称'
     * 这个字值的是‘最高价’，‘最低价’，‘标准价’，‘按工时等’，然后取 work_weixiu_cxmx 表相应的字段 如果是按工时，则还要关联
     * select * from work_feilv_sm 取对应的工费的每个工时多少钱，也就是工时单价。
     * 如果没有对应的车型的维修项目，则取work_weixiu_sm 算法和上面一样。 你维修项目选择器上，选中的那一行的维修项目编码
     * 这个原理和用料的默认价格的差不多 看这个工时费率表 这里没有卡号 收费标准，例如是二级标准，则 select feil_fl from
     * work_feilv_sm where feil_mc = '二级标准' 然后维修项目中添加的就是默认1工时，工时单价就是10块
     */
    /**
     * @param wxxm_no
     * @param che_cx  车型
     * @param che_no  车牌
     * @param fenlei  1.快速报价 2，维修预约，3.维修接单
     * @param no
     * @return
     */

    @At
    @Ok("raw:json")
    public double wxxmjg(String wxxm_no, String che_cx, String che_no,
                         int fenlei, String no, String feil_mc) {
        String gs = "";
        // 首先判断车辆的价格取值条件，如果没有车辆，或者车辆取值为空，直接跳过
        if (!Strings.isEmpty(Strings.sNull(che_no))) {
            Work_cheliang_smEntity che = dao.fetch(
                    Work_cheliang_smEntity.class, Cnd.where("che_no", "=", che_no));
            if (che != null)
                gs = Strings.sNull(che.getChe_wxxm_jffs(), "");
        }
        //修改时间2017年12月7日09:17:53
        //因为feilv需要传入felv_mc来指定 所以废除原来的接口double f = feilv(1);是写死的
        double f = 0;
        if (feil_mc != null) {
            f = feilv(feil_mc);
        }

        double jg = 0;
        if (!gs.equals("上次维修结算价")) {
            Cnd cnd = Cnd.where("wxxm_no", "=", wxxm_no);
            if (che_cx != null) {
                cnd = cnd.and("che_cx", "=", che_cx);
            }
            Work_weixiu_cxmxEntity weixiu = dao.fetch(
                    Work_weixiu_cxmxEntity.class, cnd);
            if (weixiu != null && che_cx != null) {
                if (gs.length() == 0)
                    gs = weixiu.getWxxm_jffs();
                //时间2017年11月10日15:58:37  如果不做判断会出现空指针
                if (gs == null || gs == "") {
                    gs = "按工时";
                }
                switch (gs) {
                    case "按工时":
                        jg = weixiu.getWxxm_khgs();
                        jg = f * jg;
                        break;
                    case "最低价":
                        jg = weixiu.getWxxm_zddj();
                        break;
                    case "最高价":
                        jg = weixiu.getWxxm_zgdj();
                        break;
                    case "标准价":
                        jg = weixiu.getWxxm_dj();
                        break;
                    default:
                        break;
                }
                return jg;
            } else {
                Work_weixiu_smEntity wei = dao.fetch(
                        Work_weixiu_smEntity.class,
                        Cnd.where("wxxm_no", "=", wxxm_no));
                if (gs.length() == 0 && wei != null)
                    gs = wei.getWxxm_jffs();
                switch (gs) {
                    case "按工时":
                        jg = wei.getWxxm_khgs();
                        jg = f * jg;
                        break;
                    case "最低价":
                        jg = wei.getWxxm_zddj();
                        break;
                    case "最高价":
                        jg = wei.getWxxm_zgdj();
                        break;
                    case "标准价":
                        jg = wei.getWxxm_dj();
                        break;
                    default:
                        break;
                }
                return jg;
            }
        } else {
            // 上次维修结算价
            Double danjia = new Double(0);
            Sql sql_danjia = Sqls
                    .create("select top 1 isnull(mx.wxxm_dj,0) wxxm_dj from work_mx_sj mx,work_pz_sj pz where mx.work_no=pz.work_no and pz.che_no='"
                            + che_no
                            + "' and wxxm_no='"
                            + wxxm_no
                            + "' order by pz.xche_jsrq desc");
            sql_danjia.setCallback(new SqlCallback() {
                public Object invoke(Connection conn, java.sql.ResultSet rs,
                                     Sql sql) throws SQLException {
                    Double dj = new Double(0);
                    if (rs.next())
                        dj = rs.getDouble("wxxm_dj");
                    return dj;
                }
            });
            dao.execute(sql_danjia);
            danjia = sql_danjia.getDouble();
            if (danjia == 0) {
                Sql sql_danjia2 = Sqls
                        .create("select top 1 isnull(wxxm_dj,0) wxxm_dj from lishizl_wx where list_code=2007 and che_no='"
                                + che_no
                                + "' and wxxm_no='"
                                + wxxm_no
                                + "' order by list_rq desc");
                sql_danjia2.setCallback(new SqlCallback() {
                    public Object invoke(Connection conn,
                                         java.sql.ResultSet rs, Sql sql) throws SQLException {
                        Double dj = new Double(0);
                        if (rs.next())
                            dj = rs.getDouble("wxxm_dj");
                        return dj;
                    }
                });
                dao.execute(sql_danjia2);
                danjia = sql_danjia2.getDouble();
            }
            //
            double gongshi = 0;
            Work_weixiu_cxmxEntity cxmc = dao.fetch(
                    Work_weixiu_cxmxEntity.class, wxxm_no);
            if (cxmc == null) {
                Work_weixiu_smEntity sm = dao.fetch(Work_weixiu_smEntity.class,
                        wxxm_no);
                gongshi = sm.getWxxm_gs();
            } else {
                gongshi = cxmc.getWxxm_gs();
            }
            jg = danjia * gongshi;
            if (jg == 0) {
                double list_sffl = 0;
                switch (fenlei) {
                    case 1:
                        baoJiaEntity bj = dao.fetch(baoJiaEntity.class,
                                Cnd.where("list_no", "=", no));
                        list_sffl = bj.getList_sffl();
                        System.out.println(bj.getReco_no() + "===="
                                + bj.getList_sfbz());
                        break;
                    case 2:
                        Work_yuyue_pzEntity yuyue = dao.fetch(
                                Work_yuyue_pzEntity.class,
                                Cnd.where("yuyue_no", "=", no));
                        list_sffl = yuyue.getYuyue_sffl();
                        break;
                    case 3:
                        Work_pz_gzEntity pz = dao.fetch(Work_pz_gzEntity.class,
                                Cnd.where("work_no", "=", no));
                        list_sffl = pz.getXche_sffl();
                        break;
                    default:
                        break;
                }
                Sql sql_jisuan = Sqls
                        .create("select case when wxxm_jffs='标准价' then isnull(wxxm_dj,0)  when wxxm_jffs='最高价' then isnull(wxxm_zgdj,0)  when  wxxm_jffs='最低价' then isnull(wxxm_zddj,0) when  wxxm_jffs='按工时' then isnull(wxxm_gs,0)*"
                                + list_sffl
                                + " else 0 end wxxm_dj from work_weixiu_sm  where wxxm_no='"
                                + wxxm_no + "'");
                // System.out.println("select case when wxxm_jffs='标准价' then isnull(wxxm_dj,0)  when wxxm_jffs='最高价' then isnull(wxxm_zgdj,0)  when  wxxm_jffs='最低价' then isnull(wxxm_zddj,0) when  wxxm_jffs='按工时' then isnull(wxxm_gs,0)*"
                // + list_sffl
                // + " else 0 end wxxm_dj from work_weixiu_sm  where wxxm_no='"
                // + wxxm_no + "'");
                sql_jisuan.setCallback(new SqlCallback() {
                    public Object invoke(Connection conn,
                                         java.sql.ResultSet rs, Sql sql) throws SQLException {
                        Double dj = new Double(0);
                        if (rs.next())
                            dj = rs.getDouble(1);
                        return dj;
                    }
                });
                dao.execute(sql_jisuan);
                jg = sql_jisuan.getDouble();
                // System.out.println("==============="+jg+"============");
                if (che_cx != null && che_cx.length() > 2) {
                    Work_weixiu_cxmxEntity cxmx = dao.fetch(
                            Work_weixiu_cxmxEntity.class,
                            Cnd.where("che_cx", "=", che_cx));
                    jg = jg / (cxmx.getWxxm_gs() == 0 ? 1 : cxmx.getWxxm_gs());
                } else {
                    Work_weixiu_smEntity sm = dao.fetch(
                            Work_weixiu_smEntity.class,
                            Cnd.where("wxxm_no", "=", wxxm_no));
                    jg = jg / (sm.getWxxm_gs() == 0 ? 1 : sm.getWxxm_gs());
                }
                return jg;
            } else {
                return jg;
            }

        }
    }

    public double feilv(String feil_mc) {
        Work_feilv_smEntity fei = dao.fetch(Work_feilv_smEntity.class,
                Cnd.where("feil_mc", "=", feil_mc));
        if (fei != null) {
            return fei.getFeil_fl();
        }
        return 0;
    }

    /**
     * 材料菜单
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String cail() {
        List<Sm_peijlbEntity> result = dao.query(Sm_peijlbEntity.class, null);
        String json = Json.toJson(result, JsonFormat.full());
        if (result.size() != 0) {
            return jsons.json(1, result.size(), 1, json);
        }
        return jsons.json(1, result.size(), 0, json);

    }

    /**
     * 材料内容
     *
     * @param sid  用户id
     * @param fen  分类的id
     * @param clmc 配件名称
     * @return
     */
    @At
    @Ok("raw:json")
    public String cailnr(@Param("id") String sid, @Param("fen") String fen,
                         @Param("clmc") String clmc, int pageNumber) {
        userEntity user = dao
                .fetch(userEntity.class, Cnd.where("id", "=", sid));
        String Cangku_gndm = user.getCangku_gndm();
        String gsGndm = user.getGongsi_gndm();
        String pjlb = "";
        if (fen != null && fen.length() > 0) {

            //pjlb = " and peijlb_dm like '%" + fen + "%'";
            //时间 2017年11月2日11:50:12 会查询所有
            pjlb = " and peijlb_dm like '" + fen + "%'";
        }
        if (clmc != null && clmc.length() > 0) {
            //新加模糊字段配件车型  时间2017年11月22日10:45:40
            //pjlb = " and (peij_mc like '%" + clmc + "%' or peij_py like '" + clmc + "%' or peij_th like '%"+clmc+"%')";
            pjlb = " and (peij_mc like '%" + clmc + "%' or peij_py like '" + clmc + "%' or peij_th like '%" + clmc + "%' or peij_cx like '" + clmc + "%')";
        }
        String[] fn = gsGndm.split(",");
        String gn = "";
        for (int i = 0; i < fn.length; i++) {
            gn += "'" + fn[i] + "',";
        }
        gn = gn.substring(0, gn.length() - 1);
        String gongsiNo;
        Sql sql1 = Sqls.queryRecord("select sys_setprice from sm_system_info");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        String s = res1.get(0).getString("sys_setprice");
        if (s == "0") {
            Sql sql2 = Sqls
                    .queryRecord("select gongsino from sm_gongsi where gongsi_xz = 1");
            dao.execute(sql2);
            List<Record> res2 = sql2.getList(Record.class);
            gongsiNo = res2.get(0).getString("gongsino");
        } else {
            gongsiNo = user.getGongSiNo();
        }
        Sql sql = null;
        if (fen == "" || fen == null) {
            String _sql = "Select b.peij_kc ,a.reco_no1, a.peij_no,a.peij_mc,a.peij_th,a.peij_dw,isnull(b.peij_kc,0) as zkc,b.jiag_j1,"
                    + "b.jiag_j2,b.jiag_j3,b.jiag_x1,b.jiag_x2,b.jiag_x3,b.jiag_low,b.jiag_wb,b.jiag_jp,b.waib_dw,a.peij_cx_ty,a.peij_cx,a.peij_cd,"
                    + "a.peij_pp,a.peij_cl,a.peijlb_dm,a.peijlb_mc,a.peij_jk,a.peij_th_ty,a.peij_en,a.peij_py,peij_tiaoma,LastModifyTime,"
                    + "' ' as peij_asno,a.peij_bz , a.dhjifen_sl,a.peij_zl,a.peij_mb,a.peij_yc,a.peij_xh,a.peij_sh,a.peij_shlv,a.PjCanUsedInGongSi,"
                    + "a.PjCanUsedInGongSiMc,a.kehu_dm,kehu_name  ,a.peij_jinque,a.peij_tcfs,a.peij_tc,case when a.peij_tcfs=0 then '按金额提成'"
                    + " when a.peij_tcfs=1 then '按比例提成' else '' end tcfs , isnull(zs.peij_kc,0) as qxkc, isnull(zs.kykc,0) as kykc ,"
                    + " (case isnull(zs.peij_kc,0) when 0 then b.jiag_jp else zs.peij_je/zs.peij_kc end) as qxjp  "
                    + "from kucshp_info  a  inner join kucshp_zk b on b.peij_no=a.peij_no  and b.gongsino = '"
                    + gongsiNo
                    + "'"
                    + "LEFT JOIN dbo.FN_PeiJian_ZSl('"
                    + Cangku_gndm
                    + "') zs on zs.peij_no = a.peij_no  "
                    + "Where 1 = 1   and  peij_hide <> 1   and (a.PjCanUsedInGongSi in ("
                    + gn
                    + ") or a.PjCanUsedInGongSi = '')  "
                    + pjlb
                    + "order by len(peij_mc) ";
            System.out.println("======" + _sql);
            sql = Sqls
                    .queryRecord(_sql);
            System.out.println(_sql);
        } else {
			/*String _sql = "select top 500 a.reco_no1, b.peij_kc,a.peij_no,a.peij_mc,a.peij_th,a.peij_dw,isnull(b.peij_kc,0) as zkc,b.jiag_j1,b.jiag_j2,b.jiag_j3,b.jiag_x1,b.jiag_x2,"
					+ "b.jiag_x3,b.jiag_low,b.jiag_wb,b.jiag_jp,b.waib_dw,a.peij_cx_ty,a.peij_cx,a.peij_cd,a.peij_pp,a.peij_cl,a.peijlb_dm,a.peijlb_mc,a.peij_jk,a.peij_th_ty,"
					+ "a.peij_en,a.peij_py,peij_tiaoma,LastModifyTime,' ' as peij_asno,a.peij_bz , a.dhjifen_sl,a.peij_zl,a.peij_mb,a.peij_yc,a.peij_xh,a.peij_sh,a.peij_shlv,"
					+ "a.PjCanUsedInGongSi,a.PjCanUsedInGongSiMc,a.kehu_dm,kehu_name  ,a.peij_jinque,a.peij_tcfs,a.peij_tc,"
					+ "case when a.peij_tcfs=0 then '按金额提成' when a.peij_tcfs=1 then '按比例提成' else '' end tcfs , isnull(fl.peij_kc,0) as qxkc, isnull(zs.kykc,0) as kykc ,"
					+ "fl.jiag_jp as qxjp  from kucshp_info a  inner join kucshp_zk b on b.peij_no=a.peij_no  and b.gongsino = '"
					+ gongsiNo
					+ "'"
					+ "INNER JOIN kucshp_fl fl ON a.peij_no = fl.peij_no "
					+ "LEFT JOIN dbo.FN_PeiJian_CkZSl('"
					+ Cangku_gndm
					//+ "') zs on zs.peij_no = a.peij_no  Where fl.cangk_dm ='01'  and  peij_hide <> 1"
					+ "') zs on zs.peij_no = a.peij_no  Where  peij_hide <> 1"
					+ "and (a.PjCanUsedInGongSi in ("
					+ gn
					+ ") or a.PjCanUsedInGongSi = '') "
					+ pjlb
					+ " order by len(peij_mc) ";*/
            //时间 2017年11月2日11:50:12 上面注释的是查询所有配件匹配错误
            String _sql = "Select b.peij_kc ,a.reco_no1, a.peij_no,a.peij_mc,a.peij_th,a.peij_dw,isnull(b.peij_kc,0) as zkc,b.jiag_j1,"
                    + "b.jiag_j2,b.jiag_j3,b.jiag_x1,b.jiag_x2,b.jiag_x3,b.jiag_low,b.jiag_wb,b.jiag_jp,b.waib_dw,a.peij_cx_ty,a.peij_cx,a.peij_cd,"
                    + "a.peij_pp,a.peij_cl,a.peijlb_dm,a.peijlb_mc,a.peij_jk,a.peij_th_ty,a.peij_en,a.peij_py,peij_tiaoma,LastModifyTime,"
                    + "' ' as peij_asno,a.peij_bz , a.dhjifen_sl,a.peij_zl,a.peij_mb,a.peij_yc,a.peij_xh,a.peij_sh,a.peij_shlv,a.PjCanUsedInGongSi,"
                    + "a.PjCanUsedInGongSiMc,a.kehu_dm,kehu_name  ,a.peij_jinque,a.peij_tcfs,a.peij_tc,case when a.peij_tcfs=0 then '按金额提成'"
                    + " when a.peij_tcfs=1 then '按比例提成' else '' end tcfs , isnull(zs.peij_kc,0) as qxkc, isnull(zs.kykc,0) as kykc ,"
                    + " (case isnull(zs.peij_kc,0) when 0 then b.jiag_jp else zs.peij_je/zs.peij_kc end) as qxjp  "
                    + "from kucshp_info  a  inner join kucshp_zk b on b.peij_no=a.peij_no  and b.gongsino = '"
                    + gongsiNo
                    + "'"
                    + "LEFT JOIN dbo.FN_PeiJian_ZSl('"
                    + Cangku_gndm
                    + "') zs on zs.peij_no = a.peij_no  "
                    + "Where 1 = 1   and  peij_hide <> 1   and (a.PjCanUsedInGongSi in ("
                    + gn
                    + ") or a.PjCanUsedInGongSi = '')  "
                    + pjlb
                    + "order by len(peij_mc) ";
            System.out.println("======" + _sql);
            sql = Sqls
                    .queryRecord(_sql);
            System.out.println(_sql);
        }
        dao.execute(sql);
        List<Record> res = sql.getList(Record.class);

        int kaishi = 20 * (pageNumber - 1);
        int jieshu = 20 * pageNumber;
        List<Record> _page_res = new ArrayList();
        if (res.size() < kaishi) {
            return jsons.json(1, 0, 0, "查询失败");
        }
        if (res.size() > kaishi && res.size() < jieshu) {
            jieshu = res.size();
            _page_res = res.subList(kaishi, jieshu);
        } else {
            _page_res = res.subList(kaishi, jieshu);
        }
        String json = Json.toJson(_page_res, JsonFormat.full());
        return jsons.json(1, _page_res.size(), 1, json);
    }

    String number;

    /**
     * @param keh_no    ----客户编号
     * @param peij_bm   ---配件编号
     * @param che_no    ----车牌号
     * @param gongsi_no ----公司编号
     * @return
     */
    @At
    @Ok("raw:json")
    public String cailjg(final int id, final String keh_no, final String peij_bm,
                         final String che_no, final String gongsi_no) {
        System.out.println("==_id=" + id + "==keh_no=" + keh_no + "====peij_bm=" + peij_bm + "=====che_no=" + che_no + "============");
        dao.run(new ConnCallback() {
            @Override
            public void invoke(java.sql.Connection conn) throws Exception {
                Work_cheliang_smEntity work_cheliang_smEntity = dao.fetch(
                        Work_cheliang_smEntity.class, che_no);
                int _id = 0;
                System.out.println("*********" + work_cheliang_smEntity.getChe_peij_jffs());
                if (!Strings.sNull(work_cheliang_smEntity.getChe_peij_jffs(),
                        "").equals("")) {
                    System.out.println("第一个分支");
                    _id = (int) jiageDengji.get(work_cheliang_smEntity
                            .getChe_peij_jffs());
                } else {
                    _id = id;
//					Sm_app_defpriceEntity result = dao
//							.fetch(Sm_app_defpriceEntity.class);
                    System.out.println("第二个分支");
//					if(result!=null){
//						id = result.getPrice_id();
//					}
                }
                System.out.println("========================" + id);
                CallableStatement cs = conn
                        .prepareCall("{call sp_return_def_price (?,?,?,?,?,?,?,?,?)}");
                cs.setInt(1, _id);
                cs.setString(2, "");
                if (keh_no == null) {
                    cs.setString(3, "");
                } else {
                    cs.setString(3, keh_no);
                }

                cs.setString(4, peij_bm);
                cs.registerOutParameter(5, Types.NUMERIC);
                cs.setString(6, "");
                cs.setString(7, "");
                cs.setString(8, che_no);
                cs.setString(9, gongsi_no);
                // cs.registerOutParameter(3, Types.VARCHAR);
                cs.executeUpdate();
                // cs.registerOutParameter(5, test);
                // System.out.println(test);
                // number = oc.toString();
                String orderNo = cs.getString(5);
                number = orderNo;
            }
        });
        return number;
    }

    public String getUserZkCailjg(String card_no, final String keh_no,
                                  final String peij_bm, final String che_no, final String gongsi_no) {
        String number = cailjg(0, keh_no, peij_bm, che_no, gongsi_no);
        Sql sql1 = Sqls
                .queryRecord("select CardKind.ItemRate,CardKind.PeijRate from kehu_card ,CardKind where kehu_card.card_no ='"
                        + card_no + "' and card_kind = CardKind.cardkind;");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        if (res1.size() > 0) {
            String ItemRate = res1.get(0).getString("ItemRate");
            String PeijRate = res1.get(0).getString("PeijRate");
            String jsNumber = (Double.parseDouble(number) * Double
                    .parseDouble(PeijRate)) + "";
            return jsNumber;
        }
        return number;
    }

    /**
     * @param keh_no    ----客户编号
     * @param peij_bm   ---配件编号
     * @param che_no    ----车牌号
     * @param gongsi_no ----公司编号
     * @return
     */
    @At
    @Ok("raw:json")
    public String cailJgByCord(final String keh_no, final String peij_bm,
                               final String che_no, final String gongsi_no) {
        return "";
    }

    /**
     * 根据车牌查询客户信息如果没有新建
     */
    @At
    @Ok("raw:json")
    public String cheliang(String che_no) {
        Date date = new Date();
        String json = "";
        Work_cheliang_smEntity result = dao.fetch(Work_cheliang_smEntity.class, che_no);
        if (result == null) {
            Work_cheliang_smEntity res = new Work_cheliang_smEntity();
            res.setChe_no(che_no);
            res.setKehu_no(che_no);
            Work_cheliang_smEntity tr = dao.insert(res);
            KehuEntity ke = new KehuEntity();
            ke.setKehu_no(che_no);
            ke.setLastModifyTime(date);
            ke.setKehu_xz((short) 1);
            dao.insert(ke);
            json = Json.toJson(tr, JsonFormat.full());
        } else {
            json = Json.toJson(result, JsonFormat.full());
        }
        return jsons.json(1, 1, 1, json);

    }

    /**
     * 根据车牌查询客户信息如果没有新建
     */
    @At
    @Ok("raw:json")
    public String checkCar(String che_no) {
        Work_cheliang_smEntity result = dao.fetch(Work_cheliang_smEntity.class, che_no);
        if (result == null) {
            return "false";
        } else {
            return "true";
        }
    }

    /**
     * 根据车牌查询客户信息和车厢信息如果没有新建并返回车辆信息和客户信息
     *
     * @throws Exception
     * @author LHW
     * @time 2017年8月29日15:10:01
     */
    @At
    @Ok("raw:json")
    public String clandkh(String che_no) {
        Date date = new Date();
        short d = 1;
        Work_cheliang_smEntity result = dao.fetch(Work_cheliang_smEntity.class, Cnd.where("che_no", "=", che_no));
        // 如果没有此车，就新建车辆信息和客户信息
        if (result == null) {
            HashMap<Object, Object> map = new HashMap<>();
            Work_cheliang_smEntity res = new Work_cheliang_smEntity();
            res.setChe_no(che_no);
            res.setKehu_no(che_no);
            Work_cheliang_smEntity tr = dao.insert(res);
            map.put("cheliang", tr);
            KehuEntity ke = new KehuEntity();
            ke.setKehu_no(che_no);
            ke.setLastModifyTime(date);
            ke.setKehu_xz(d);
            ke.setKeHu_CanUsedInGongSi("全公司共享");
            // gongsino和gongsimc设置为空字符串，防止是null
            ke.setGongSiNo("");
            ke.setGongSiMc("全公司共享");
            dao.insert(ke);
            map.put("kehu", ke);
            map.put("isnew", true);
            String json1 = Json.toJson(map, JsonFormat.full());
            return jsons.json(1, 1, 1, json1);
        } else {
            KehuEntity fetch = dao.fetch(KehuEntity.class, Cnd.where("kehu_no", "=", result.getKehu_no()));
            Map map = new HashMap<>();
            map.put("kehu", fetch);
            map.put("cheliang", result);
            map.put("isnew", false);
            String json = Json.toJson(map, JsonFormat.full());
            return jsons.json(1, 1, 1, json);
        }

    }

    //根据客户名称模糊获得客户信息如果客户名称为空则查询所有数据
    //时间2018年5月11日16:10:36
    @At
    @Ok("raw:json")
    public String sekehu(String kehu_mc) {
        if (kehu_mc != null && kehu_mc != "") {
            Sql sql1 = Sqls
                    .queryRecord("select kehu_mc,kehu_dh,kehu_sj,kehu_xm from kehu where kehu_mc like '%" + kehu_mc + "%'");
            dao.execute(sql1);
            List<Record> res1 = sql1.getList(Record.class);
            String json = Json.toJson(res1, JsonFormat.full());
            return jsons.json(1, 1, 1, json);
        }
        Sql sql1 = Sqls
                .queryRecord("select kehu_mc,kehu_dh,kehu_sj,kehu_xm from kehu");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        String json = Json.toJson(res1, JsonFormat.full());
        return jsons.json(1, 1, 1, json);
    }

    /**
     * 根据车牌获取客户信息
     *
     * @author LHW
     * @time 2017年8月29日10:11:06
     */
    @At
    @Ok("raw:json")
    public String kehu(String che_no) {
        Sql sql1 = Sqls
                .queryRecord("select a.* from kehu  a  left join work_cheliang_sm  b on  a.kehu_no = b.kehu_no  where b.che_no = '" + che_no + "'");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        if (res1.size() < 1)
            return jsons.json(1, 1, 0, "查询失败");
        String json = Json.toJson(res1, JsonFormat.full());
        return jsons.json(1, 1, 1, json);
    }

    /**
     * 品牌
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String pinpai() {
        List<Sm_peijcx_newEntity> result = dao.query(Sm_peijcx_newEntity.class,
                Cnd.where("che_level", "=", "1").asc("chex_qz"));
        String json = Json.toJson(result, JsonFormat.full());
        if (result.size() != 0) {
            return jsons.json(1, result.size(), 1, json);
        }
        return jsons.json(1, result.size(), 0, json);

    }

    /**
     * 获取公司信息
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String getGongSiJs() {
        return jsons.json(1, 1, 0, "<div>关于我们</div>");
    }

    /**
     * 根据车型代码获取4个全称
     *
     * @param chex_dm
     * @return
     */
    @At
    @Ok("raw:json")
    public String get4JIjiegouMingcheng(String chex_dm) {
        String _re = "";
        if (chex_dm != null && chex_dm.length() > 0) {
            Sql sql0 = Sqls
                    .queryRecord("select chex_top,chex_mc+'【'+chex_mc_std+'】' as chex_mc  from sm_peijcx_New where   chex_dm = '"
                            + chex_dm + "'");
            dao.execute(sql0);
            List<Record> res0 = sql0.getList(Record.class);
            if (res0 != null && res0.size() > 0) {
                String chex_top = res0.get(0).getString("chex_top");
                String chex_mc = res0.get(0).getString("chex_mc");
                if (chex_top != null && chex_top.length() > 0) {
                    Sql sql1 = Sqls
                            .queryRecord("select chex_dm as chezu_dm ,chex_mc as chezu_mc,chex_top  from sm_peijcx_New  where chex_dm ='"
                                    + chex_top + "'");
                    dao.execute(sql1);
                    List<Record> res1 = sql1.getList(Record.class);
                    if (res1 != null && res1.size() > 0) {
                        String chezu_dm = res1.get(0).getString("chezu_dm");
                        String chezu_mc = res1.get(0).getString("chezu_mc");
                        chex_top = res1.get(0).getString("chex_top");
                        if (chex_dm != null && chex_dm.length() > 0) {
                            Sql sql2 = Sqls
                                    .queryRecord("select chex_dm as chexi_dm,chex_mc as chexi_mc,chex_top  from sm_peijcx_New  where chex_dm ='"
                                            + chex_top + "'");
                            dao.execute(sql2);
                            List<Record> res2 = sql2.getList(Record.class);
                            if (res2 != null && res2.size() > 0) {
                                String chexi_dm = res2.get(0).getString(
                                        "chexi_dm");
                                String chexi_mc = res2.get(0).getString(
                                        "chexi_mc");
                                chex_top = res2.get(0).getString("chex_top");
                                if (chexi_dm != null && chexi_dm.length() > 0) {
                                    Sql sql3 = Sqls
                                            .queryRecord("select chex_dm as pp_dm,chex_mc as pp_mc,chex_top  from sm_peijcx_New  where chex_dm ='"
                                                    + chex_top + "'");
                                    dao.execute(sql3);
                                    List<Record> res3 = sql3
                                            .getList(Record.class);
                                    if (res3 != null && res3.size() > 0) {
                                        String pp_dm = res3.get(0).getString(
                                                "pp_dm");
                                        String pp_mc = res3.get(0).getString(
                                                "pp_mc");
                                        chex_top = res3.get(0).getString(
                                                "chex_top");
                                        _re = pp_mc + '|' + chexi_mc + '|'
                                                + chezu_mc + '|' + chex_mc;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return jsons.json(1, 1, 0, _re);
    }

    /**
     * 根据车型名称获取车型4及名称
     *
     * @param chex_mc
     * @return
     */
    @At
    @Ok("raw:json")
    public String getcheIDBycheXingMingCheng(String chex_mc) {
        List<Sm_peijcx_newEntity> result = dao.query(Sm_peijcx_newEntity.class,
                Cnd.where("chex_mc", "=", chex_mc).and("che_level", "=", "4"));
        String json = Json.toJson(result, JsonFormat.full());
        return jsons.json(1, result.size(), 1, json);
    }

    /**
     * 车系
     *
     * @param dm 品牌
     * @return
     */
    @At
    @Ok("raw:json")
    public String chexi(String dm) {
        Sm_peijcx_newEntity ben = dao.fetch(Sm_peijcx_newEntity.class,
                Cnd.where("chex_mc", "=", dm));
        List<Sm_peijcx_newEntity> result = dao.query(
                Sm_peijcx_newEntity.class,
                Cnd.where("che_level", "=", "2").and("chex_top", "=",
                        ben.getChex_bz()));
        String json = Json.toJson(result, JsonFormat.full());
        if (result.size() != 0) {
            return jsons.json(1, result.size(), 1, json);
        }
        return jsons.json(1, result.size(), 0, json);
    }

    /**
     * 车组
     *
     * @param dm 车系
     * @return
     */
    @At
    @Ok("raw:json")
    public String chezu(String dm) {
        Sm_peijcx_newEntity ss = dao.fetch(Sm_peijcx_newEntity.class, Cnd
                .where("che_level", "=", "2").and("chex_mc", "=", dm));
        List<Sm_peijcx_newEntity> result = dao.query(
                Sm_peijcx_newEntity.class,
                Cnd.where("che_level", "=", "3").and("chex_top", "=",
                        ss.getChex_bz()));
        String json = Json.toJson(result, JsonFormat.full());
        if (result.size() != 0) {
            return jsons.json(1, result.size(), 1, json);
        }
        return jsons.json(1, result.size(), 0, json);
    }

    /**
     * 车型
     *
     * @param dm 车组
     * @return
     */
    @At
    @Ok("raw:json")
    public String chexing(String dm) {
        Sm_peijcx_newEntity gg = dao.fetch(Sm_peijcx_newEntity.class, Cnd
                .where("che_level", "=", "3").and("chex_mc", "=", dm));
        Cnd cnd = Cnd.where("che_level", "=", "4");
        if (gg != null)
            cnd.and("chex_top", "=", gg.getChex_bz());
        List<Sm_peijcx_newEntity> result = dao.query(
                Sm_peijcx_newEntity.class, cnd);
        String json = Json.toJson(result, JsonFormat.full());
        if (result.size() != 0) {
            return jsons.json(1, result.size(), 1, json);
        }
        return jsons.json(1, result.size(), 0, json);
    }

    /**
     * 获取精友账号密码
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String getJyUser() {
        Sql sql1 = Sqls
                .queryRecord("select sys_shuju_url,sys_shuju_user,sys_shuju_pwd from sm_system_info ");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        String json = Json.toJson(res1, JsonFormat.full());
        return jsons.json(1, res1.size(), 1, json);
    }

    /**
     * 得到发送微信消息接口的地址
     *
     * @return
     * @author LHW
     * @time 2017年8月29日16:32:16
     */
    @At("/getWeXinDiZhi")
    @Ok("raw:json")
    public String getWeXinDiZhi() {
        Sql sql1 = Sqls
                .queryRecord("select sys_weixindizhi from sm_system_info ");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        String json = Json.toJson(res1, JsonFormat.full());
        return jsons.json(1, res1.size(), 1, json);
    }

    /**
     * 价格id
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String jia() {
        Sm_app_defpriceEntity result = dao.fetch(Sm_app_defpriceEntity.class);
        String json = Json.toJson(result, JsonFormat.full());
        if (result != null) {
            return jsons.json(1, 1, 1, json);
        }
        return jsons.json(1, 0, 0, json);
    }

    /**
     * 保存材料价格等级
     *
     * @param price_id
     * @param price_name
     * @return
     */
    @At
    @Ok("raw:json")
    public String saveJia(Integer price_id, String price_name) {
        Sm_app_defpriceEntity result = dao.fetch(Sm_app_defpriceEntity.class);
        if (result == null) {
            Sql sql1 = Sqls
                    .queryRecord("insert into sm_app_defprice values(" + price_id + ",'" + price_name + "')");
            dao.execute(sql1);
        } else {
            result.setPrice_id(price_id);
            result.setPrice_name(price_name);
            dao.update(result);
        }
        return jsons.json(1, 0, 0, "保存价格成功");
    }

    /**
     * 获取价格等级
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String getJiaGeDengJi() {
        String json = Json.toJson(jiageDengji, JsonFormat.full());
        return jsons.json(1, jiageDengji.size(), 1, json);

    }

    /**
     * 图片上传
     *
     * @throws Exception
     * @author LHW
     * @time 2017年8月28日14:15:55
     */
    @At
    @AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
    @Ok("raw:json")
    public String uploadFile(@Param("file") TempFile[] file) throws Exception {
        String string = "";
        for (TempFile file1 : file) {
            File f = file1.getFile();  // 这个是保存的临时文件
            String path2 = f.getAbsolutePath();
            FieldMeta meta = file1.getMeta();               // 这个原本的文件信息
            String fileName = meta.getFileLocalName();      // 这个时原本的文件名称
            File file3 = new File(path2);
            InputStream is = new FileInputStream(file3);
            FileUtils.copyInputStreamToFile(is, new File(conf.get("path"), fileName));
            string = "上传成功";
        }
        return jsons.json(1, 1, 1, string);

    }

    /**
     * 删除照片信息
     *
     * @return
     * @author LHW
     * @time 2017年9月4日08:59:58
     */
    @At("/deletefile")
    @Ok("raw:json")
    public String deletefile(String photo) {
        if (photo != null && photo != "") {
            String[] strings = photo.split(",");
            boolean b = false;
            for (String string : strings) {
                photo = conf.get("path") + "/" + string + ".jpg";
                System.out.println(photo);
                File file = new File(photo);
                b = UploadFileUtils.removeFile(file);
            }
            if (b) {
                System.out.println("删除成功");
                return jsons.json(1, 1, 1, "删除成功");
            }
        }
        System.out.println("找不到相应图片");
        return jsons.json(1, 1, 1, "找不到相应图片！");
    }


    /**
     * @param chex_mc_std 模糊获取车型
     * @throws Exception
     * @author LHW
     * @time 2017年8月29日15:51:07
     */
    @At("/getchexing")
    @Ok("raw:json")
    public String getchexing(String chex_mc_std) throws Exception {
        List<Sm_peijcx_newEntity> ban = dao.query(Sm_peijcx_newEntity.class,
                Cnd.where("chex_mc_std", "like", "%" + chex_mc_std + "%"));
        String json = Json.toJson(ban, JsonFormat.full());
        if (ban.size() != 0) {
            return jsons.json(1, ban.size(), 1, json);
        }
        return jsons.json(1, ban.size(), 0, json);
    }

    /**
     * 修改车辆基本信息和客户基本信息
     *     对于日期，如果前台什么都没有输入，映射到对象中的值是null
     * @return
     * @author LHW
     * @time修改时间 2017年9月18日08:56:16
     */
    @At("/updatejb")
    @Ok("raw:json")
    public boolean updatejb(@Param("..") Work_cheliang_smEntity chel, @Param("..") KehuEntity kehu) {
        boolean ss = false;
        if (chel != null && kehu != null) {
            Work_cheliang_smEntity _workCheliangSmEntity = dao.fetch(Work_cheliang_smEntity.class, chel.getChe_no());
            _workCheliangSmEntity.setChe_vin(chel.getChe_vin());
            _workCheliangSmEntity.setChe_pp(chel.getChe_pp());
            _workCheliangSmEntity.setChe_wxys(chel.getChe_wxys());
            _workCheliangSmEntity.setChe_cx(chel.getChe_cx());
            _workCheliangSmEntity.setChe_nf(chel.getChe_nf());
            _workCheliangSmEntity.setChe_gcrq(chel.getChe_gcrq());
            _workCheliangSmEntity.setChe_next_byrq(chel.getChe_next_byrq());
            _workCheliangSmEntity.setChe_jianche_dqrq(chel.getChe_jianche_dqrq());
            _workCheliangSmEntity.setChe_jiaoqx_dqrq(chel.getChe_jiaoqx_dqrq());
            _workCheliangSmEntity.setChe_shangyex_dqrq(chel.getChe_shangyex_dqrq());
            dao.update(_workCheliangSmEntity, "^che_vin|che_pp|che_wxys|che_cx|che_nf|che_gcrq|che_next_byrq|che_jianche_dqrq|che_jiaoqx_dqrq|che_shangyex_dqrq$");
//            dao.updateIgnoreNull(chel); // 这个方法是按照id更新，所以不用这个方法了
            Sql sql1 = Sqls
                    .create("update kehu set kehu_mc='" + kehu.getKehu_mc() + "',kehu_xm='" + kehu.getKehu_xm() + "',kehu_sj='" + kehu.getKehu_sj() + "',kehu_dh='" + kehu.getKehu_dh() +
                            "' where kehu_no='" + kehu.getKehu_no() + "'");
            dao.execute(sql1);

            // 单据中的公司都不改了
            dao.execute(Sqls.create("update work_pz_gz set kehu_mc='" + kehu.getKehu_mc() + "',kehu_xm='" + kehu.getKehu_xm() + "',kehu_dh='" + kehu.getKehu_dh() +
                    "',che_vin='" + chel.getChe_vin() + "',che_wxys='" + chel.getChe_wxys() + "',che_cx='" + chel.getChe_cx() +
                    "' where che_no='" + chel.getChe_no() + "'"));
            dao.execute(Sqls.create("update work_yuyue_pz set kehu_mc='" + kehu.getKehu_mc() + "',kehu_xm='" + kehu.getKehu_xm() + "',kehu_dh='" + kehu.getKehu_dh() +
                    "' where kehu_no='" + kehu.getKehu_no() + "'"));
            dao.execute(Sqls.create("update work_yuyue_pz set che_vin='" + chel.getChe_vin() + "',che_wxys='" + chel.getChe_wxys() + "',che_cx='" + chel.getChe_cx() +
                    "' where che_no='" + chel.getChe_no() + "'"));
            dao.execute(Sqls.create("update work_baojia_pz set kehu_mc='" + kehu.getKehu_mc() + "',kehu_xm='" + kehu.getKehu_xm() + "',kehu_dh='" + kehu.getKehu_dh() +
                    "' where kehu_no='" + kehu.getKehu_no() + "'"));
            dao.execute(Sqls.create("update work_baojia_pz set che_vin='" + chel.getChe_vin() + "',che_wxys='" + chel.getChe_wxys() + "',che_cx='" + chel.getChe_cx() +
                    "' where che_no='" + chel.getChe_no() + "'"));
            //河北保定万通专用
			/*Sql sql11 = Sqls
					.queryRecord("update kehu set kehu_CanUsedInGongSi='01' where kehu_CanUsedInGongSi=''");
			dao.execute(sql11);
			Sql sql31 = Sqls
					.create("update work_pz_gz set  gongsino='01' where gongsino=''");
			dao.execute(sql31);*/
            ss = true;
        }
        return ss;
    }

    /**
     * 得到vin码的账号密码url
     *
     * @return
     * @throws Exception
     * @author LHW
     * @time 2017-9-11 10:44:38
     */
    @At("/getvin")
    @Ok("raw:json")
    public String getvin(String vinCode) throws Exception {
        if (vinCode != null && !"".equals(vinCode)) {
            Sql sql1 = Sqls
                    .queryRecord("select sys_shuju_url,sys_shuju_user,sys_shuju_pwd from sm_system_info ");
            dao.execute(sql1);
            List<Record> res1 = sql1.getList(Record.class);
            String sys_shuju_url = res1.get(0).getString("sys_shuju_url");
            String sys_shuju_user = res1.get(0).getString("sys_shuju_user");
            String sys_shuju_pwd = res1.get(0).getString("sys_shuju_pwd");
            System.out.println(sys_shuju_url);
            System.out.println(sys_shuju_user);
            System.out.println(sys_shuju_pwd);
            HashMap<Object, Object> map = new HashMap<>();
            HashMap<Object, Object> data1 = new HashMap<>();
            data1.put("vinCode", vinCode);
            map.put("operatorCode", sys_shuju_user);
            map.put("operatorPwd", sys_shuju_pwd);
            map.put("channelType", "01");
            map.put("requestCode", "100101");
            map.put("dtype", "json");
            map.put("data", data1);

            String json1 = new Gson().toJson(map);
            System.out.println(json1);
            System.out.println(sys_shuju_url);

            byte[] data = json1.getBytes();
            java.net.URL url = new java.net.URL(sys_shuju_url);
            System.out.println(url);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);// 设置连接超时时间为5秒
            conn.setReadTimeout(20 * 1000);// 设置读取超时时间为20秒
            // 使用 URL 连接进行输出，则将 DoOutput标志设置为 true
            conn.setDoOutput(true);
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
            JsonParser parse = new JsonParser(); // 创建json解析器
            JsonObject js = (JsonObject) parse.parse(msg); // 创建jsonObject对象
            Object result = "";
            result = js.get("result");
            if (result == null) {
                return jsons.json(1, 0, 0, "无查询结果！");
            }
            JsonObject object = js.get("result").getAsJsonObject();
            JsonArray array2 = object.get("vehicleList").getAsJsonArray();
            System.out.println(object + "==" + array2);

            ArrayList<Object> list = new ArrayList<>();
            for (int i = 0; i < array2.size(); i++) {
                JsonObject subObject = array2.get(i).getAsJsonObject();
                String chex_dm = subObject.get("vehicleCode").getAsString();
                String chex_mc = subObject.get("vehicleName").getAsString();
                Sql sql = Sqls
                        .queryRecord("select chex_mc_std from sm_peijcx_new where chex_dm='" + chex_dm + "'");
                dao.execute(sql);
                List<Record> res = sql.getList(Record.class);
                String chex_mc_std = res.get(0).getString("chex_mc_std");
                Map map1 = new HashMap();
                map1.put("chex_dm", chex_dm);
                map1.put("chex_mc_std", chex_mc_std);
                map1.put("chex_mc", chex_mc);
                list.add(map1);
            }
            String json = Json.toJson(list, JsonFormat.full());
            return jsons.json(1, res1.size(), 1, json);
        }
        return jsons.json(1, 0, 0, "vinCode不能为空");

    }

    /**
     * 获取车型四级结构
     *
     * @return
     * @author LHE
     * @time 2017年9月7日17:07:15
     */
    @At
    @Ok("raw:json")
    public String getche(String chex_dm) {
        if (chex_dm != null && chex_dm != "") {
            Sm_peijcx_newEntity chexing = dao.fetch(Sm_peijcx_newEntity.class,
                    Cnd.where("chex_dm", "=", chex_dm));
            String chex_mc = chexing.getChex_mc();
            String chex_top = chexing.getChex_top();
            String chex_mc_std = chexing.getChex_mc_std();
            Sm_peijcx_newEntity chezu = dao.fetch(Sm_peijcx_newEntity.class, Cnd
                    .where("che_level", "=", "3").and("chex_dm", "=", chex_top));
            String chex_mc1 = chezu.getChex_mc();
            String chex_top1 = chezu.getChex_top();
            Sm_peijcx_newEntity chexi = dao.fetch(Sm_peijcx_newEntity.class, Cnd
                    .where("che_level", "=", "2").and("chex_dm", "=", chex_top1));
            String chex_mc2 = chexi.getChex_mc();
            String chex_top2 = chexi.getChex_top();
            Sm_peijcx_newEntity pinpai = dao.fetch(Sm_peijcx_newEntity.class, Cnd
                    .where("che_level", "=", "1").and("chex_dm", "=", chex_top2));
            String chex_mc3 = pinpai.getChex_mc();
            chex_mc_std = "【" + chex_mc_std + "】";
            String che = chex_mc3 + "|" + chex_mc2 + "|" + chex_mc1 + "|" + chex_mc + chex_mc_std;
            String json = Json.toJson(che, JsonFormat.full());
            return jsons.json(1, 1, 1, json);
        }

        return jsons.json(1, 1, 0, "无数据");


    }

    /**
     * 获取端口路径
     *
     * @return
     * @author LHW
     * @time 2017年9月7日09:20:38
     */
    @At("/getBspicportno")
    @Ok("raw:json")
    public String getBspicportno() {
        Sql sql1 = Sqls
                .queryRecord("select sys_bspicportno from sm_system_info ");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        String json = Json.toJson(res1, JsonFormat.full());
        return jsons.json(1, res1.size(), 1, json);
    }

    /**
     * 更新维修项目中会员卡有特殊处理的价格（不包括会员卡初始折扣）
     */
    @At
    @Ok("raw:json")
    public String getcardwxxm_price(String card_no, String work_no) {
        // 获取会员卡类别
        Sql sql1 = Sqls.queryRecord("select card_kind from kehu_card where card_no='" + card_no + "'");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        String card_kind = res1.get(0).getString("card_kind");
        // 获取可用的特殊维修项目（项目编码，金额和剩余次数）
        Sql sql2 = Sqls
                .queryRecord("select wxxm_no,wxxm_je,(wxxm_cs - wxxm_yqcs) as wxxm_sycs from kehu_carddetail where card_no = '" + card_no + "' and wxxm_cs>wxxm_yqcs");
        dao.execute(sql2);
        List<Record> cardDetailList = sql2.getList(Record.class);
        boolean wxxmIncardDetail;
        String wxxm_no;
        // 获取当前的维修项目
        Sql sql = Sqls.queryRecord("select wxxm_no from work_mx_gz where work_no = '" + work_no + "'");
        dao.execute(sql);
        List<Record> wxxmList = sql.getList(Record.class);
        // 遍历当前的维修项目
        for (Record aWxxmList : wxxmList) {
            wxxmIncardDetail = false;
            String wxxm_je = "";
            wxxm_no = aWxxmList.getString("wxxm_no");
            // 判断当前维修项目是否是可用的特殊维修项目
            for (Record record : cardDetailList) {
                if (wxxm_no.equals(record.getString("wxxm_no"))) {
                    wxxmIncardDetail = true;
                    wxxm_je = record.getString("wxxm_je");
                    break;
                }
            }
            // 如果是特殊维修项目，则更新维修金额和维修折扣
            if (wxxmIncardDetail) {
                Sql sql11 = Sqls
                        .create("update work_mx_gz set wxxm_je=" + wxxm_je + ",wxxm_zk=(case when wxxm_yje=0 then 1 else " + wxxm_je + "/wxxm_yje end)" +
                                " where work_no='" + work_no + "' and wxxm_no = '" + wxxm_no + "'");
                dao.execute(sql11);
            } else { // 如果不是特殊的为项目，则查看会员卡制度，0是按折扣算,1是按照会员价
                Sql sql3 = Sqls.queryRecord("select Card_DiscountOrPrice from cardsysset");
                dao.execute(sql3);
                List<Record> res111 = sql3.getList(Record.class);
                int i_Card_DiscountOrPrice = res111.get(0).getInt("Card_DiscountOrPrice");
                if (i_Card_DiscountOrPrice == 0) {  // 按折扣算
                    // 获取此维修项目在此会员类型下的折扣
                    Sql sql4 = Sqls
                            .queryRecord("select wxxm_zk from CardWxDiscount where cardkind = '" + card_kind + "' and wxxm_no = '" + wxxm_no + "'");
                    dao.execute(sql4);
                    List<Record> res4 = sql4.getList(Record.class);
                    // 如果存在此折扣，则按此折扣计算
                    if (res4.size() > 0) {
                        String wxxm_zk = res4.get(0).getString("wxxm_zk");
                        Sql sql12 = Sqls
                                .create("update work_mx_gz set wxxm_zk=" + wxxm_zk + ",wxxm_je=isnull(wxxm_yje,0)*" + wxxm_zk +
                                        " where work_no='" + work_no + "' and wxxm_no = '" + wxxm_no + "'");
                        dao.execute(sql12);
                    } else {
                        Sql sql5 = Sqls
                                .queryRecord("select lb_zk from cardkindLB a, work_weixiu_sm b where a.lb_dm = b.wxxm_lbdm and lb_xz = 0  and cardkind = '" + card_kind + "' and wxxm_no = '" + wxxm_no + "'");
                        dao.execute(sql5);
                        List<Record> res5 = sql5.getList(Record.class);
                        if (res5.size() > 0) {
                            String wxxm_zk = res5.get(0).getString("lb_zk");
                            Sql sql12 = Sqls
                                    .create("update work_mx_gz set wxxm_zk=" + wxxm_zk + ",wxxm_je=isnull(wxxm_yje,0)*" + wxxm_zk +
                                            " where work_no='" + work_no + "'  and wxxm_no = '" + wxxm_no + "'");
                            dao.execute(sql12);
                        }
                    }
                } else { // 按会员价算
                    Sql sql6 = Sqls
                            .queryRecord("select wxxm_je from CardKindWeix where cardkind = '" + card_kind + "' and wxxm_no = '" + wxxm_no + "'");
                    dao.execute(sql6);
                    List<Record> res6 = sql6.getList(Record.class);
                    if (res6.size() > 0) {
                        String huiyuanje = res6.get(0).getString("wxxm_je");
                        Sql sql11 = Sqls
                                .create("update work_mx_gz set wxxm_je=" + huiyuanje + ",wxxm_zk=(case when wxxm_yje=0 then 1 else " + huiyuanje + "/wxxm_yje end)" +
                                        " where work_no='" + work_no + "' and wxxm_no = '" + wxxm_no + "'");
                        dao.execute(sql11);
                    }
                }
            }
        }
        return "success";
    }

    /**
     * @author LHW
     * 得到会员卡特殊配件价格
     * @time 2017年9月7日14:25:06
     */
    @At
    @Ok("raw:json")
    public String getcardpj_price(String card_no, String work_no) {
        String card_kind = "";
        boolean b_card_discountOrPrice = false;
        Sql sql = Sqls
                .queryRecord("select card_kind from kehu_card where card_no='" + card_no + "'");
        dao.execute(sql);
        List<Record> res = sql.getList(Record.class);
        card_kind = res.get(0).getString("card_kind");
        Sql sql1 = Sqls
                .queryRecord("select Card_DiscountOrPrice from cardsysset");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        int i_Card_DiscountOrPrice = res1.get(0).getInt("Card_DiscountOrPrice");
        if (i_Card_DiscountOrPrice == 0) {
            b_card_discountOrPrice = true;
        }
        String peij_no = "";
        if (b_card_discountOrPrice == true) {
            Sql sql2 = Sqls
                    .queryRecord("select reco_no,peij_no from work_ll_gz where work_no = '" + work_no + "'");
            dao.execute(sql2);
            String reco_no = "";
            List<Record> res2 = sql2.getList(Record.class);
            for (int i = 0; i < res2.size(); i++) {
                peij_no = res2.get(i).getString("peij_no");
                reco_no = res2.get(i).getString("reco_no");
                Sql sql3 = Sqls
                        .queryRecord("select peij_zk from CardPjDiscount where cardkind = '" + card_kind + "' and peij_no = '" + peij_no + "'");
                dao.execute(sql3);

                List<Record> res3 = sql3.getList(Record.class);
                if (res3.size() > 0) {
                    String peij_zk = res3.get(0).getString("peij_zk");
                    Sql sql4 = Sqls
                            .queryRecord("update work_ll_gz set peij_zk='" + peij_zk + "' where reco_no = '" + reco_no + "'");
                    dao.execute(sql4);
                    Sql sql5 = Sqls
                            .queryRecord("update work_ll_gz set peij_dj=isnull(peij_ydj,0)*isnull(peij_zk,1) where reco_no = '" + reco_no + "'");
                    dao.execute(sql5);
                    Sql sql11 = Sqls
                            .queryRecord("update work_ll_gz set peij_je=isnull(peij_dj,0)*isnull(peij_sl,0) where reco_no = '" + reco_no + "'");
                    dao.execute(sql11);

                } else {
                    Sql sql6 = Sqls
                            .queryRecord("select lb_zk from cardkindLB a, kucshp_info b where a.lb_dm = b.peijlb_dm and lb_xz = 1 and cardkind = '" + card_kind + "' and peij_no = '" + peij_no + "'");
                    dao.execute(sql6);

                    List<Record> res6 = sql6.getList(Record.class);
                    if (res6.size() > 0) {
                        String peij_zk = res6.get(0).getString("lb_zk");
                        Sql sql7 = Sqls
                                .queryRecord("update work_ll_gz set peij_zk='" + peij_zk + "' where reco_no = '" + reco_no + "'");
                        dao.execute(sql7);
                        Sql sql8 = Sqls
                                .queryRecord("update work_ll_gz set peij_dj=isnull(peij_ydj,0)*isnull(peij_zk,1) where reco_no = '" + reco_no + "'");
                        dao.execute(sql8);
                        Sql sql12 = Sqls
                                .queryRecord("update work_ll_gz set peij_je=isnull(peij_dj,0)*isnull(peij_sl,0) where reco_no = '" + reco_no + "'");
                        dao.execute(sql12);
                    }
                }
            }
        } else {
            Sql sql9 = Sqls
                    .queryRecord("select reco_no,peij_no from work_ll_gz where work_no = '" + work_no + "'");
            dao.execute(sql9);

            String reco_no = "";
            List<Record> res9 = sql9.getList(Record.class);
            for (int i = 0; i < res9.size(); i++) {
                peij_no = res9.get(i).getString("peij_no");
                reco_no = res9.get(i).getString("reco_no");
                Sql sql10 = Sqls
                        .queryRecord("select peij_dj,(peij_card_sl-peij_card_yqsl) as peij_card_sysl  from kehu_CardDetailPeij where card_no= '" + card_no + "' and peij_no = '" + peij_no + "'");
                dao.execute(sql10);
                List<Record> res10 = sql10.getList(Record.class);

                if (res10.size() > 0) {
                    String peij_dj = res10.get(0).getString("peij_dj");
                    int peij_card_sysl = res10.get(0).getInt("peij_card_sysl");
                    if (peij_card_sysl > 0) {
                        Sql sql11 = Sqls
                                .queryRecord("update work_ll_gz set peij_dj='" + peij_dj + "' where reco_no = '" + reco_no + "'");
                        dao.execute(sql11);
                        //lxp add 2017-09-12 如果原单价为空，把原单价赋值为单价
                        Sql sql111 = Sqls
                                .queryRecord("update work_ll_gz set peij_ydj= peij_dj  where reco_no = '" + reco_no + "' and isnull(peij_ydj,0)=0");
                        dao.execute(sql111);
                        Sql sql12 = Sqls
                                .queryRecord("update work_ll_gz set peij_zk=case when peij_ydj=0 then 1 else peij_dj/peij_ydj end  where reco_no = '" + reco_no + "'");
                        dao.execute(sql12);

                        Sql sql14 = Sqls
                                .queryRecord("update work_ll_gz set peij_je=isnull(peij_dj,0)*isnull(peij_sl,0) where reco_no = '" + reco_no + "'");
                        dao.execute(sql14);
                    }
                }
            }
        }
        return jsons.json(1, 1, 1, "调用成功");
    }

    /**
     * 品牌
     * 根据车型名称模糊查询品牌
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @At
    @Ok("raw:json")
    public String pinpai1(String pinpai) throws UnsupportedEncodingException {
        List<Sm_peijcx_newEntity> result;
        result = dao.query(Sm_peijcx_newEntity.class,
                Cnd.where("che_level", "=", "1").and("chex_mc", "like", "%" + pinpai + "%").asc("chex_qz"));
        String json = Json.toJson(result, JsonFormat.full());
        if (result.size() != 0) {
            return jsons.json(1, result.size(), 1, json);
        }
        return jsons.json(1, result.size(), 0, json);

    }

    /**
     * 获取配件信息和仓库信息
     *
     * @return
     * @throws UnsupportedEncodingException
     * @author LHW
     * @time 2017年11月6日17:22:54
     */
    @At
    @Ok("raw:json")
    public String peijandck(String peij_no, String caozuoyuanid) throws UnsupportedEncodingException {
        if ((peij_no == null || peij_no.equals("")) || (caozuoyuanid == null || caozuoyuanid.equals(""))) {
            return jsons.json(1, 0, 0, "输入参数有误，请重新输入！");
        }
        String scangKu_Gndm = "";
        Sql sql2 = Sqls
                .queryRecord("select cangku_gndm from sm_caozuoyuan where id = " + caozuoyuanid + "");
        dao.execute(sql2);
        List<Record> res2 = sql2.getList(Record.class);
        if (res2.size() > 0) {
            scangKu_Gndm = res2.get(0).getString("cangku_gndm");
        }
        System.out.println(scangKu_Gndm);
        String[] split = scangKu_Gndm.split(",");
        scangKu_Gndm = "";
        for (String string : split) {
            scangKu_Gndm += "'" + string + "'" + ",";
        }
        scangKu_Gndm = scangKu_Gndm.substring(0, scangKu_Gndm.length() - 1);
        System.out.println(scangKu_Gndm);
        HashMap<Object, Object> map = new HashMap<>();
        Sql sql = Sqls
                .queryRecord("Select peij_no,peij_mc,peij_th,peij_dw,peij_cx,peij_pp From kucshp_info Where peij_no='" + peij_no + "'");
        dao.execute(sql);
        List<Record> res = sql.getList(Record.class);
        Sql sql1 = Sqls
                .queryRecord("Select reco_no,peij_no,fl.cangk_dm,ck.cangk_mc,peij_kc,jiag_jp,peij_je,xiao_sl,xiaoth_sl,jinh_sl,jinhth_sl, (peij_kc - xiao_sl + xiaoth_sl + jinh_sl - jinhth_sl) as Adjust_sl,kuc_sx, kuc_xx, kuc_hw,kuc_dh FROM "
                        + "kucshp_fl fl,sm_cangk ck where fl.cangk_dm = ck.cangk_dm and fl.peij_no = "
                        + "'" + peij_no + "' and( 1<0  or fl.cangk_dm in(" + scangKu_Gndm + ") or isnull(fl.cangk_dm,'') = '' )");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        map.put("peij_info", res.get(0));
        map.put("cangk_info", res1);
        String json = Json.toJson(map, JsonFormat.full());
        if (res1.size() > 0) {
            return jsons.json(1, map.size(), 1, json);
        }
        return jsons.json(1, 0, 0, "没有查询到数据");
    }
    /**
     * 修改维修项目名称
     * 2017年12月13日11:59:09
     * @return
     */
	/*@At
	@Ok("raw:json")
	public String updatewxnrmc(Work_weixiu_smEntity work_weixiu_sm) {
		dao.update(work_weixiu_sm);
		return jsons.json(1, 1, 1, "已修改");
		
	}*/

    /**
     * @throws Exception
     * @author LHW
     * @time
     */
    @At
    @Ok("raw:json")
    public String getgongzry(String reny_xm) throws Exception {
        List<GongzryEntity> ban = dao.query(GongzryEntity.class,
                Cnd.where("reny_xm", "like", "%" + reny_xm + "%"));
        String json = Json.toJson(ban, JsonFormat.full());
        if (ban.size() != 0) {
            return jsons.json(1, ban.size(), 1, json);
        }
        return jsons.json(1, ban.size(), 0, json);
    }

    /**
     * 根据车牌号和查询公司权限，判断此车牌号是否可用
     */
    @At
    @Ok("raw:json")
    public String carCanUsed(String che_no, String caozuoyuanID) {
        boolean canUsedType = true;
        // 先查询是否有这个车牌号
        Work_cheliang_smEntity result = dao.fetch(Work_cheliang_smEntity.class, Cnd.where("che_no", "=", che_no));
        if (result != null) {
            Sql sql1 = Sqls
                    .queryRecord("select kehu_no from kehu where kehu_no=@kehu_no and (kehu_CanUsedInGongSi in(select gongsi_gndm from sm_caozuoyuan where id=@id) or isnull(kehu_CanUsedInGongSi,'全公司共享')='全公司共享')");
            sql1.params().set("kehu_no", result.getKehu_no()).set("id", caozuoyuanID);
            dao.execute(sql1);
            List<Record> res1 = sql1.getList(Record.class);
            if (res1.size() == 0) {  // 如果有这个车，并且还没有权限，就返回false
                canUsedType = false;
            }
        }
        return jsons.json(1, 1, 1, Json.toJson(canUsedType));

    }

    /**
     * 把会员卡的信息更新到单据上
     */
    @At
    @Ok("raw:json")
    public String updateBillByCard(String card_no, String work_no) {
        // 把会员卡更新到pz表上
        Sql sql = Sqls.create("update work_pz_gz set card_no='" + card_no + "' where work_no = '" + work_no + "'");
        dao.execute(sql);
        // 更新维修项目
        updateWxxmPriceByCard(card_no, work_no);
        // 更新维修用料
        updateWxclPriceByCard(card_no, work_no);
        return "success";
    }

    /**
     * 把会员卡的信息移除
     */
    @At
    @Ok("raw:json")
    public String removeBillByCard(String work_no) {
        // 把会员卡更新到pz表上
        Sql sql = Sqls.create("update work_pz_gz set card_no='',card_kind='',card_itemrate=0,card_peijrate=0 where work_no = '" + work_no + "'");
        dao.execute(sql);
        // 更新维修项目
        updateWxxmPriceByCard("", work_no);
        // 更新维修用料
        updateWxclPriceByCard("", work_no);
        return "success";
    }

    /**
     * 更新维修用料中会员卡有特殊处理的价格
     */
    @At
    @Ok("raw:json")
    private String updateWxclPriceByCard(String card_no, String work_no) {
        // 如果会员卡存在，则按照会员卡修改折扣和金额
        if (card_no != null && !card_no.equals("")) {
            // 获取当前的维修用料
            Sql sql = Sqls.queryRecord("select peij_no from work_ll_gz where work_no = '" + work_no + "'");
            dao.execute(sql);
            List<Record> wxclList = sql.getList(Record.class);
            // 遍历当前的维修项目
            for (Record aWxclList : wxclList) {
                String peij_no = aWxclList.getString("peij_no");
                updateOneWxclPriceByCard(peij_no, card_no, work_no);
            }
        } else { // 如果没有会员卡，则修改折扣为1
            Sql sql = Sqls
                    .create("update work_ll_gz set peij_zk=1,peij_je=peij_yje,peij_dj=peij_yje/(case peij_sl when 0 then 1 else peij_sl end) where work_no = '" + work_no + "'");
            dao.execute(sql);
            BsdUtils.updateWorkPzGz(dao, work_no);
        }
        return "success";
    }

    public void updateOneWxclPriceByCard(String peij_no, String card_no, String work_no) {
        boolean isUpdate = false;
        // 获取会员卡类别和项目折扣
        Sql sql1 = Sqls.queryRecord("select cardkind,peijrate from cardkind where cardkind=(select card_kind from kehu_card where card_no='" + card_no + "')");
        dao.execute(sql1);
        List<Record> res = sql1.getList(Record.class);
        String card_kind = res.get(0).getString("cardkind");
        String peijrate = res.get(0).getString("peijrate");
        Sql sql2 = Sqls
                .queryRecord("select peij_dj from kehu_CardDetailPeij where card_no= '" + card_no + "' and peij_no = '" + peij_no + "' and peij_card_sl>peij_card_yqsl");
        dao.execute(sql2);
        List<Record> cardDetailList = sql2.getList(Record.class);
        if (cardDetailList.size() > 0) {
            String peij_dj = cardDetailList.get(0).getString("peij_dj");
            Sql sql11 = Sqls
                    .create("update work_ll_gz set peij_dj=" + peij_dj + ",peij_zk=(case when peij_ydj=0 then 1 else " + peij_dj + "/peij_ydj end)" +
                            " where work_no='" + work_no + "' and peij_no = '" + peij_no + "'");
            dao.execute(sql11);
            isUpdate = true;
        } else {
            Sql sql3 = Sqls
                    .queryRecord("select Card_DiscountOrPrice from cardsysset");
            dao.execute(sql3);
            List<Record> res1 = sql3.getList(Record.class);
            int i_Card_DiscountOrPrice = res1.get(0).getInt("Card_DiscountOrPrice");
            if (i_Card_DiscountOrPrice == 0) {
                Sql sql4 = Sqls.queryRecord("select peij_zk from CardPjDiscount where cardkind = '" + card_kind + "' and peij_no = '" + peij_no + "'");
                dao.execute(sql4);
                List<Record> res3 = sql4.getList(Record.class);
                if (res3.size() > 0) {
                    String peij_zk = res3.get(0).getString("peij_zk");
                    Sql sql5 = Sqls
                            .create("update work_ll_gz set peij_zk=" + peij_zk + ",peij_dj=isnull(peij_ydj,0)*" + peij_zk +
                                    " where work_no='" + work_no + "' and peij_no = '" + peij_no + "'");
                    dao.execute(sql5);
                    isUpdate = true;
                } else {
                    Sql sql6 = Sqls
                            .queryRecord("select lb_zk from cardkindLB a, kucshp_info b where a.lb_dm = b.peijlb_dm and lb_xz = 1 and cardkind = '" + card_kind + "' and peij_no = '" + peij_no + "'");
                    dao.execute(sql6);
                    List<Record> res6 = sql6.getList(Record.class);
                    if (res6.size() > 0) {
                        String peij_zk = res6.get(0).getString("lb_zk");
                        Sql sql7 = Sqls
                                .queryRecord("update work_ll_gz set peij_zk=" + peij_zk + ",peij_dj=isnull(peij_ydj,0)*" + peij_zk +
                                        " where work_no='" + work_no + "' and peij_no = '" + peij_no + "'");
                        dao.execute(sql7);
                        isUpdate = true;
                    }
                }
            } else {
                Sql sql6 = Sqls
                        .queryRecord("select peij_dj from CardKindPeij where cardkind = '" + card_kind + "' and peij_no = '" + peij_no + "'");
                dao.execute(sql6);
                List<Record> res6 = sql6.getList(Record.class);
                if (res6.size() > 0) {
                    String huiyuanje = res6.get(0).getString("peij_dj");
                    Sql sql11 = Sqls
                            .create("update work_ll_gz set peij_dj=" + huiyuanje + ",peij_zk=(case when peij_ydj=0 then 1 else " + huiyuanje + "/peij_ydj end)" +
                                    " where work_no='" + work_no + "' and peij_no = '" + peij_no + "'");
                    dao.execute(sql11);
                    isUpdate = true;
                }
            }
        }
        // 如果没有更新过，则按照会员卡原始的折扣计算
        if (!isUpdate) {
            Sql sql12 = Sqls
                    .create("update work_ll_gz set peij_zk=" + peijrate + ",peij_dj=isnull(peij_ydj,0)*" + peijrate +
                            " where work_no='" + work_no + "' and peij_no = '" + peij_no + "'");
            dao.execute(sql12);
        }
        // 最后更新配件金额
        Sql sql13 = Sqls
                .create("update work_ll_gz set peij_je=peij_sl*peij_dj" +
                        " where work_no='" + work_no + "' and peij_no = '" + peij_no + "'");
        dao.execute(sql13);
    }

    /**
     * 更新维修项目价格
     */
    @At
    @Ok("raw:json")
    public String updateWxxmPriceByCard(String card_no, String work_no) {
        if (card_no != null && !card_no.equals("")) {
            // 获取当前的维修项目
            Sql sql = Sqls.queryRecord("select wxxm_no from work_mx_gz where work_no = '" + work_no + "'");
            dao.execute(sql);
            List<Record> wxxmList = sql.getList(Record.class);
            // 遍历当前的维修项目
            for (Record aWxxmList : wxxmList) {
                String wxxm_no = aWxxmList.getString("wxxm_no");
                updateOneWxxmPriceByCard(wxxm_no, card_no, work_no);
            }
        } else {
            Sql sql = Sqls
                    .create("update work_mx_gz set wxxm_zk=1,wxxm_je=wxxm_yje,wxxm_dj=wxxm_yje/(case wxxm_gs when 0 then 1 else wxxm_gs end) where work_no = '" + work_no + "'");
            dao.execute(sql);
            BsdUtils.updateWorkPzGz(dao, work_no);
        }
        return "success";
    }

    public void updateOneWxxmPriceByCard(String wxxm_no, String card_no, String work_no) {
        boolean isUpdate = false;
        // 获取会员卡类别和项目折扣
        Sql sql1 = Sqls.queryRecord("select cardkind,itemrate from cardkind where cardkind=(select card_kind from kehu_card where card_no='" + card_no + "')");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        String card_kind = res1.get(0).getString("cardkind");
        String itemrate = res1.get(0).getString("itemrate");
        // 获取可用的特殊维修项目（金额）
        Sql sql2 = Sqls
                .queryRecord("select wxxm_je from kehu_carddetail where card_no='" + card_no + "' and wxxm_no='" + wxxm_no + "' and wxxm_cs>wxxm_yqcs");
        dao.execute(sql2);
        List<Record> cardDetailList = sql2.getList(Record.class);
        // 如果存在特殊的维修项目，则按照此金额计算
        if (cardDetailList.size() > 0) {
            String wxxm_je = cardDetailList.get(0).getString("wxxm_je");
            Sql sql11 = Sqls
                    .create("update work_mx_gz set wxxm_je=" + wxxm_je + ",wxxm_zk=(case when wxxm_yje=0 then 1 else " + wxxm_je + "/wxxm_yje end)" +
                            " where work_no='" + work_no + "' and wxxm_no = '" + wxxm_no + "'");
            dao.execute(sql11);
            isUpdate = true;
        } else {  // 如果不是特殊的维修项目，则查看会员卡制度，0是按折扣算,1是按照会员价
            Sql sql3 = Sqls.queryRecord("select Card_DiscountOrPrice from cardsysset");
            dao.execute(sql3);
            List<Record> res111 = sql3.getList(Record.class);
            int i_Card_DiscountOrPrice = res111.get(0).getInt("Card_DiscountOrPrice");
            if (i_Card_DiscountOrPrice == 0) {  // 按折扣算
                // 获取此维修项目在此会员类型下的折扣
                Sql sql4 = Sqls
                        .queryRecord("select wxxm_zk from CardWxDiscount where cardkind = '" + card_kind + "' and wxxm_no = '" + wxxm_no + "'");
                dao.execute(sql4);
                List<Record> res4 = sql4.getList(Record.class);
                // 如果存在此折扣，则按此折扣计算
                if (res4.size() > 0) {
                    String wxxm_zk = res4.get(0).getString("wxxm_zk");
                    Sql sql12 = Sqls
                            .create("update work_mx_gz set wxxm_zk=" + wxxm_zk + ",wxxm_je=isnull(wxxm_yje,0)*" + wxxm_zk +
                                    " where work_no='" + work_no + "' and wxxm_no = '" + wxxm_no + "'");
                    dao.execute(sql12);
                    isUpdate = true;
                } else {
                    Sql sql5 = Sqls
                            .queryRecord("select lb_zk from cardkindLB a, work_weixiu_sm b where a.lb_dm = b.wxxm_lbdm and lb_xz = 0  and cardkind = '" + card_kind + "' and wxxm_no = '" + wxxm_no + "'");
                    dao.execute(sql5);
                    List<Record> res5 = sql5.getList(Record.class);
                    if (res5.size() > 0) {
                        String wxxm_zk = res5.get(0).getString("lb_zk");
                        Sql sql12 = Sqls
                                .create("update work_mx_gz set wxxm_zk=" + wxxm_zk + ",wxxm_je=isnull(wxxm_yje,0)*" + wxxm_zk +
                                        " where work_no='" + work_no + "'  and wxxm_no = '" + wxxm_no + "'");
                        dao.execute(sql12);
                        isUpdate = true;
                    }
                }
            } else { // 按会员价算
                Sql sql6 = Sqls
                        .queryRecord("select wxxm_je from CardKindWeix where cardkind = '" + card_kind + "' and wxxm_no = '" + wxxm_no + "'");
                dao.execute(sql6);
                List<Record> res6 = sql6.getList(Record.class);
                if (res6.size() > 0) {
                    String huiyuanje = res6.get(0).getString("wxxm_je");
                    Sql sql11 = Sqls
                            .create("update work_mx_gz set wxxm_je=" + huiyuanje + ",wxxm_zk=(case when wxxm_yje=0 then 1 else " + huiyuanje + "/wxxm_yje end)" +
                                    " where work_no='" + work_no + "' and wxxm_no = '" + wxxm_no + "'");
                    dao.execute(sql11);
                    isUpdate = true;
                }
            }
        }
        // 如果没有更新过，则按照会员卡原始的折扣计算
        if (!isUpdate) {
            Sql sql12 = Sqls
                    .create("update work_mx_gz set wxxm_zk=" + itemrate + ",wxxm_je=isnull(wxxm_yje,0)*" + itemrate +
                            " where work_no='" + work_no + "' and wxxm_no = '" + wxxm_no + "'");
            dao.execute(sql12);
        }
        // 最后更新维修单价
        Sql sql13 = Sqls
                .create("update work_mx_gz set wxxm_dj=wxxm_je/(case wxxm_gs when 0 then 1 else wxxm_gs end)" +
                        " where work_no='" + work_no + "' and wxxm_no = '" + wxxm_no + "'");
        dao.execute(sql13);
    }


}