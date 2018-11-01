package org.aotu.lswx.module;

import org.aotu.Jsons;
import org.aotu.lswx.entity.Work_mx_sjEntity;
import org.aotu.lswx.entity.Work_pz_sjEntity;
import org.aotu.user.entity.userEntity;
import org.aotu.util.BsdUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


@IocBean
@At("/lswx")
public class lswxModule {
	
	@Inject
	Dao dao;
	
	@Inject
	Jsons jsons;

    /**
     * 历史报价
     *
     * @param che_no
     * @param pageNumber
     * @param type       0所有，1，快修
     * @return
     */
    @At("/")
    @Ok("raw:json")
    public String lswx(String che_no, int pageNumber, int type, int caozuoyuanid) {
        userEntity fetch = dao.fetch(userEntity.class, caozuoyuanid);
        if (fetch == null) {
            return jsons.json(1, 0, 0, "没有此操作员");
        } else {
            String gongsiGndm = fetch.getGongsi_gndm();
            String gongsiGndmQuote = BsdUtils.gn2quote(gongsiGndm);
            Pager pager = dao.createPager(pageNumber, 20);
            List<Work_pz_sjEntity> result;
            String sql_ = "select pz.Xche_jsrq,pz.Card_no,pz.Zhifu_card_no,pz.Zhifu_card_je,pz.Zhifu_card_xj,pz.Flag_cardjs,kh.kehu_mc,kh.kehu_xm,kh.kehu_dh," +
                    "pz.id,pz.work_no,kh.kehu_no,kh.kehulb_mc,cl.che_no,(xche_hjje - xche_xzrgf_je - xche_xzclf_je) as xche_hjje,pz.xche_jdrq,pz.xche_jcr " +
                    "from work_pz_sj pz,kehu kh,work_cheliang_sm cl where pz.kehu_no = kh.kehu_no and pz.che_no = cl.che_no";
            if (che_no != null && !"".equals(che_no)) {
                if (type == -1) {
                    sql_ += " and pz.che_no='" + che_no + "'";
                } else {
                    sql_ += " and pz.che_no like '%" + che_no + "%'";
                }
            }
            if (type == 1) {
                sql_ += " and pz.flag_fast=1";
            } else if (type == 0) {
                sql_ += " and pz.flag_fast=0";
            }
            sql_ += " and (pz.GongSino in(" + gongsiGndmQuote + ") or isnull(pz.GongSiNo,'')='')";
            sql_ += "  order by pz.Xche_jsrq desc";
            Sql sql = Sqls.queryRecord(sql_);
            sql.setPager(pager);
            dao.execute(sql);
            List<Record> res = sql.getList(Record.class);
            String json = Json.toJson(res, JsonFormat.full());
            if (res.size() != 0) {
                return jsons.json(1, res.size(), 1, json);
            }
            return jsons.json(1, res.size(), 0, json);
        }
    }
	
	/**
	 * 维修项目明细
	 * @param work_no
	 * @param pageNumber
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String wxxm(String work_no,int pageNumber){
		Pager pager = dao.createPager(pageNumber, 20);
		List<Work_mx_sjEntity> result;
		if(work_no==null){
			 result = dao.query(Work_mx_sjEntity.class, null,pager);
		}else{
			result = dao.query(Work_mx_sjEntity.class, Cnd.where("work_no","=",work_no),pager);
		}
		
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}
	
	
	/**
	 * 材料明细
	 * @param work_no
	 * @param pageNumber
	 * @return
	 */

	@At
	@Ok("raw:json")
	public String cail(String work_no,int pageNumber){
		Sql sql;
		sql = Sqls.queryRecord("select b.reco_no,b.work_no,a.peij_pp,a.peij_mc,b.peij_no,b.peij_sl,b.peij_dj,b.peij_je,a.peij_th,a.peij_th " +
				"from work_ll_sj b left join kucshp_info a on a.peij_no=b.peij_no where b.work_no='"+work_no+"'");
		dao.execute(sql);
		List<Record> result = sql.getList(Record.class);
		
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}
	/**
	 * 历史维修信息
	 * 历史维修主表信息
	 * @param che_no
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@At
	@Ok("raw:json")
	public String lswxxx(String che_no, String caozuoyuan_xm){
        if (caozuoyuan_xm == null || caozuoyuan_xm.equals("")) {
            return jsons.json(1, 1, 0, "操作员姓名不能为空");
        }
		if (che_no == null || che_no.equals("")) {
			return jsons.json(1, 1, 0, "车牌号无效，请输入正确车牌号。");
		}
        // 查询操作员拥有的公司权限
        Sql sqlgndm = Sqls
                .queryRecord("select gongsi_gndm from sm_caozuoyuan where caozuoyuan_xm='" + caozuoyuan_xm + "'");
        dao.execute(sqlgndm);
        List<Record> resgndm = sqlgndm.getList(Record.class);
        String gongsi_gndm = resgndm.get(0).getString("gongsi_gndm");
        // 转化为可以在in中使用的字符串
        String gndmStr = BsdUtils.gn2quote(gongsi_gndm);
        StringBuffer sql_ = new StringBuffer();
		sql_.append("select pz.work_no,pz.Xche_jsrq,kh.kehu_mc,kh.kehu_sj,pz.Card_no,pz.Zhifu_card_no,pz.Zhifu_card_je,pz.Zhifu_card_xj,"
				+ "pz.Flag_cardjs,kh.kehu_xm,kh.kehu_dh,pz.id,kh.kehu_no,kh.kehulb_mc,"
				+ "pz.xche_hjje,pz.xche_jdrq,pz.xche_jcr,pz.xche_cclc,pz.xche_ysje  "
				+ "from work_pz_sj pz,kehu kh,work_cheliang_sm s where pz.kehu_no = kh.kehu_no and pz.che_no = s.che_no and pz.che_no = '"
				+ che_no + "' and ( pz.GongSiNo in(" + gndmStr + ") or isnull(pz.GongSiNo,'') = '')");

		sql_.append("  order by pz.Xche_jsrq desc");
		Sql sql = Sqls.queryRecord(sql_.toString());
		dao.execute(sql);
		List<Record> res = sql.getList(Record.class);
		String json = Json.toJson(res, JsonFormat.full());
		if (res.size() != 0) {
			return jsons.json(1, res.size(), 1, json);
		}
		return jsons.json(1, res.size(), 0, json);
	}

	/**
	 * 历史维修项目明细
	 * 
	 * @param work_no
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@At
	@Ok("raw:json")
	public String lswxxmxx(String work_no){
		if (work_no == null || work_no.equals("")) {
			return jsons.json(1, 1, 0, "历史维修单号不能为空，请检查数据。");
		}
		List<Work_mx_sjEntity> result = dao.query(Work_mx_sjEntity.class, Cnd
				.where("work_no", "=", work_no).asc("reco_no"));

		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}
	/**
	 * 历史维修用料信息
	 * 
	 * @param work_no
	 * @param pageNumber
	 * @return
	 */

	@At
	@Ok("raw:json")
	public String lswxylxx(String work_no) {
		if (work_no == null || work_no.equals("")) {
			return jsons.json(1, 1, 0, "维修单号无效，请选择正确的维修单号。");
		}
		Sql sql = Sqls
				.queryRecord("select b.peij_no,a.peij_mc,a.peij_th,b.peij_sl,b.peij_dj,b.peij_je,b.reco_no,b.work_no "
						+ "from work_ll_sj b left join kucshp_info a on a.peij_no=b.peij_no where b.work_no='"
						+ work_no + "' order by reco_no");
		dao.execute(sql);
		List<Record> result = sql.getList(Record.class);
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}
	/**
	 * 历史维修建议主表信息
	 * 
	 * @param che_no
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@At
	@Ok("raw:json")
	public String lswxjyzb(String che_no){
		if (che_no == null || che_no.equals("")) {
			return jsons.json(1, 1, 0, "车牌号无效，请输入正确车牌号。");
		}
		StringBuffer sql_ = new StringBuffer();
		sql_.append("select work_no,xche_jsrq as rq ,kehu_mc,card_no,p.kehu_sj,xche_cclc,xche_ysje"
				+ " from work_pz_sj p ,kehu  k where p.che_no = '"
				+ che_no
				+ "' and p.kehu_no=k.kehu_no and work_no "
				+ "in (select work_no from work_mx_jianyi where che_no = '"
				+ che_no
				+ "'  union "
				+ " select work_no from work_ll_jianyi where che_no = '"
				+ che_no + "') order by xche_jsrq desc");
		Sql sql = Sqls.queryRecord(sql_.toString());
		dao.execute(sql);
		List<Record> res = sql.getList(Record.class);
		String json = Json.toJson(res, JsonFormat.full());
		if (res.size() != 0) {
			return jsons.json(1, res.size(), 1, json);
		}

		return jsons.json(1, res.size(), 0, json);

	}

	/**
	 * 历史维修项目建议
	 * 
	 * @param work_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String lswxxmjy(String work_no) {
		if (work_no == null || work_no.equals("")) {
			return jsons.json(1, 1, 0, "维修单号无效，请选择正确的维修单号。");
		}
		StringBuffer sql_ = new StringBuffer();
		sql_.append("select work_no,jianyi_rq, wxxm_no,wxxm_mc,wxxm_gs,wxxm_je,wxxm_dj,jianyi_state "
				+ "from work_mx_jianyi where work_no = '"
				+ work_no
				+ "'  order by reco_no");
		Sql sql = Sqls.queryRecord(sql_.toString());
		dao.execute(sql);
		List<Record> res = sql.getList(Record.class);
		String json = Json.toJson(res, JsonFormat.full());
		if (res.size() != 0) {
			return jsons.json(1, res.size(), 1, json);
		}

		return jsons.json(1, res.size(), 0, json);

	}

	/**
	 * 历史维修配件建议
	 * 
	 * @param work_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String lswxpjjy(String work_no) {
		if (work_no == null || work_no.equals("")) {
			return jsons.json(1, 1, 0, "维修单号无效，请选择正确的维修单号。");
		}
		StringBuffer sql_ = new StringBuffer();
		sql_.append("select work_no,jianyi_rq,peij_no,peij_th,peij_mc,peij_dw,peij_sl,peij_dj,peij_je,jianyi_state "
				+ "from work_ll_jianyi where  work_no='"
				+ work_no
				+ "' order by reco_no ");
		Sql sql = Sqls.queryRecord(sql_.toString());
		dao.execute(sql);
		List<Record> res = sql.getList(Record.class);
		String json = Json.toJson(res, JsonFormat.full());
		
		if (res.size() != 0) {
			return jsons.json(1, res.size(), 1, json);
		}

		return jsons.json(1, res.size(), 0, json);
	}
}
