/**
 * @author LHW
 *@time 2018年1月8日上午11:20:35
 * 
 */
package org.aotu.xiche.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.aotu.Jsons;
import org.aotu.VIPcard.entity.Kehu_CardEntity;
import org.aotu.VIPcard.module.card;
import org.aotu.lswx.entity.Work_mx_sjEntity;
import org.aotu.offer.entity.LLEntity;
import org.aotu.offer.entity.baoJiaEntity;
import org.aotu.offer.entity.feilvEntity;
import org.aotu.offer.entity.wxxmEntity;
import org.aotu.order.entity.Work_ll_gzEntity;
import org.aotu.order.entity.Work_mx_gzEntity;
import org.aotu.order.entity.Work_pz_gzEntity;
import org.aotu.paigong.entity.WorkPgGz;
import org.aotu.publics.eneity.KehuEntity;
import org.aotu.publics.eneity.Work_cheliang_smEntity;
import org.aotu.publics.module.publicModule;
import org.aotu.user.entity.userEntity;
import org.aotu.xiche.entity.Work_weixiu_xiche_renyEntity;
import org.aotu.xiche.entity.Work_xiche_mx_gzEntity;
import org.aotu.xiche.entity.Work_xiche_pz_gzEntity;
import org.aotu.xiche.entity.Work_xiche_pz_sjEntity;
import org.aotu.xiche.entity.Work_xiche_wxxm_gzEntity;
import org.aotu.xiche.entity.Work_xiche_wxxm_sjEntity;
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
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * @author LHW
 *
 */
@IocBean
@At("/xiche")
public class XicheModule {
	@Inject
	Dao dao;
	@Inject
	Jsons jsons;
	@Inject
	card card;
	@Inject
	publicModule pu;
	
	
	
	/**
	 * 根据会员卡号得到洗车剩余次数
	 */
	@At
	@Ok("raw:json")
	public String xcsycs(String card_no){
		if(card_no.equals("")){	
			return jsons.json(1, 1, 0, "输入卡号有误或没有输入卡号");
		}
		Sql sql = Sqls
				.queryRecord("select card_leftcs_px, card_leftcs_jx from kehu_card where card_no = '"+card_no+"'");
		dao.execute(sql);
		List<Kehu_CardEntity> res = sql.getList(Kehu_CardEntity.class);
		
		String json = Json.toJson(res, JsonFormat.full());
		if (res.size() != 0) {
			return jsons.json(1, res.size(), 1, json);
		}
		return jsons.json(1, res.size(), 0, json);
	}
	/**
	 *删除维修项目 
	 * 
	 */
	@At
	@Ok("raw:json")
	public String delxm(String xc_no,String wxxm_no){
		if(xc_no!=""&&wxxm_no!=""){
			Sql sql = Sqls
					.queryRecord("delete  work_xiche_wxxm_gz where xc_no='"+xc_no+"' and wxxm_no= '"+wxxm_no+"'");
			dao.execute(sql);
			 return jsons.json(1, 1, 0, "删除成功");
		}
		return jsons.json(1, 1, 0, "输入有误");
	}
	/**
	 *添加维修项目 
	 * 
	 */
	@At
	@Ok("raw:json")
	public String addxcxm(String json){
		System.out.println("=============="+json);
		JsonParser parse = new JsonParser(); // 创建json解析器
		try {
			JsonObject js = (JsonObject) parse.parse(json); // 创建jsonObject对象
			JsonArray array = js.get("data").getAsJsonArray();
			for (int i = 0; i < array.size(); i++) {
				Work_xiche_wxxm_gzEntity offer = new Work_xiche_wxxm_gzEntity();
				JsonObject subObject = array.get(i).getAsJsonObject();
				String nu = subObject.get("xc_no").getAsString();
				offer.setXc_no(subObject.get("xc_no").getAsString());
				if (i == 0) {
					Work_xiche_wxxm_gzEntity ks = dao.fetch(Work_xiche_wxxm_gzEntity.class,
							Cnd.where("xc_no", "=", nu));
					if (ks != null) {
						// dao.query(Work_yuyue_wxxmEntity.class,
						// Cnd.where("yuyue_no","=",nu));
						dao.clear(Work_xiche_wxxm_gzEntity.class,
								Cnd.where("xc_no", "=", nu));
					}
				}
				offer.setWxxm_yje(subObject.get("wxxm_je").getAsDouble());
				offer.setWxxm_no(subObject.get("wxxm_no").getAsString());
				offer.setWxxm_mc(subObject.get("wxxm_mc").getAsString());
				offer.setWxxm_gs(subObject.get("wxxm_gs").getAsDouble());
				offer.setWxxm_je(subObject.get("wxxm_je").getAsDouble());
				if(offer.getWxxm_gs()==0){
					offer.setWxxm_dj(offer.getWxxm_je());
				}else{
					offer.setWxxm_dj(offer.getWxxm_je()/offer.getWxxm_gs());
				}
				//try catch不好使会出现失败时间2017年10月31日14:53:27 @author LHW
				/*try{
					offer.setWxxm_dj(offer.getWxxm_je()/offer.getWxxm_gs());
				}catch(java.lang.Exception e){
					
				}*/
				offer.setWxxm_zt(subObject.get("wxxm_zt").getAsString());
				offer.setWxxm_khgs(subObject.get("wxxm_gs").getAsDouble());
				Work_xiche_wxxm_gzEntity dd = dao.insert(offer);
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
	 * 洗车派工
	 */
	@At
	@Ok("raw:json")
	public String savePg(@Param("..") Work_xiche_mx_gzEntity pg){
		if(pg.getXc_no()!=""&&pg.getXc_no()!=null){		
			Work_xiche_mx_gzEntity dd = dao.insert(pg);
			if(dd!=null){
				return jsons.json(1, 1, 0, "派工失败");
			}
			return jsons.json(1, 1, 1, "派工成功");
		}
		 return jsons.json(1, 1, 0, "洗车编号为空");
	}
	
	/**
	 * 删除派工
	 */
	@At
	@Ok("raw:json")
	public String delPg(String xc_no , String reny_no){
		if(xc_no!=null&&reny_no!=null){	
		Sql sql = Sqls
				.queryRecord("delete  work_xiche_mx_gz where xc_no='"+xc_no+"' and reny_no='"+reny_no+"' ");
		
		
		dao.execute(sql);
		 return jsons.json(1, 1, 1, "删除成功");
		}
		return jsons.json(1, 1, 1, "有编号为空，请输入。");
		
	}

	/**
	 * 洗车单的添加基本信息
	 * 
	 * @param 
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String addjbxx(@Param("gongsiNo") final String gongsiNo,
			@Param("caozuoyuan_xm") final String caozuoyuan_xm,
			@Param("..") Work_xiche_pz_gzEntity pz) {
		Work_cheliang_smEntity che = pu.saveCheInfo(pz.getChe_no(), pz.getGcsj(), pz.getChe_cx(),
				pz.getChe_vin(),pz.getGongSiNo());
		
		if (pz.getXc_no() == null) {
			String num = add(gongsiNo, caozuoyuan_xm);
			pz.setXc_no(num);
		}
		if(pz.getChe_no()==""||pz.getChe_no()==null){
			return jsons.json(1, 1, 0, "请输入车牌号!");
		}
		if(pz.getXc_lx()=="卡洗"&&pz.getXc_lx()==""){
			return jsons.json(1, 1, 0, "请输入会员卡号!");
		}
		Sql sql = Sqls
				.queryRecord("select kehu_No,kehu_mc from work_cheliang_sm  where che_no = '"+pz.getChe_no()+"'"); 
		dao.execute(sql);
		List<Record> res1 = sql.getList(Record.class);
    	String kehu_no = res1.get(0).getString("kehu_no");
    	String kehu_mc = res1.get(0).getString("kehu_mc");
		if(kehu_no==""&&pz.getKehu_no()==""){
			return jsons.json(1, 1, 0, "请输入客户，或者请到本站点参数设置中设置默认洗车客户！");
		}else if(kehu_no==""&&pz.getKehu_no()!=""){
			kehu_no=pz.getKehu_no();
			kehu_mc=pz.getKehu_mc();
			pu.saveKeHu(kehu_no,kehu_mc);
		}else{
			return jsons.json(1, 1, 0, "选择了否，就不继续进行了。！");
		}
		
		pz.setXc_rq(new Date());
		
		
		Work_xiche_pz_gzEntity pp = dao.fetch(Work_xiche_pz_gzEntity.class, pz.getXc_no());
		
		////////////////////合计金额计算///////////////////////////////////
		if (pz.getXc_lx()=="普洗"){ //  --从平板的我的管理中读取这个变量
			Sql sql3 = Sqls
					.queryRecord("update work_xiche_pz_gz set xc_lx='零洗',xc_fs='普洗',xc_xcysje='"+pz.getXc_xcysje()+"' where xc_no='"+pz.getXc_no()+"' "); //--@list_no就是洗车单号 // @Def_XiChe_Je是我的管理中去读取的普洗金额"
			dao.execute(sql3);	  	
		}  
		else if (pz.getXc_lx() == "精洗"){
			Sql sql4 = Sqls
					.queryRecord("update work_xiche_pz_gz set xc_lx='零洗',xc_fs='精洗',xc_xcysje='"+pz.getXc_xcysje()+"' where xc_no='"+pz.getXc_no()+"'");  //--@Def_XiCheJX_Je是我的管理中读取的洗车金额
			dao.execute(sql4);	  
		}
		Sql sql5 = Sqls
				.queryRecord("select sum(wxxm_je) as wxxm_zje from work_xiche_wxxm_gz where xc_no = '"+pz.getXc_no()+"' and wxxm_zt = '正常'"); 
		dao.execute(sql5);
		List<Record> res5 = sql.getList(Record.class);
    	String wxxm_zje = res5.get(0).getString("wxxm_zje");
    	Double xc_xmysje = Double.parseDouble(wxxm_zje);
    	pz.setXc_xmysje(xc_xmysje);
    	pz.setXc_ysje(xc_xmysje+pz.getXc_xcysje());
    	Sql sql6 = Sqls
				.queryRecord("select sum(isnull(wxxm_tcje,0)) wxxm_tcje from work_xiche_wxxm_gz where xc_no='"+pz.getXc_no()+"'"); 
		dao.execute(sql6);
		List<Record> res6 = sql.getList(Record.class);
    	String wxxm_tc = res6.get(0).getString("wxxm_tcje");
    	Double wxxm_tcje = Double.parseDouble(wxxm_tc);
    	pz.setWxxm_tcje_sum(wxxm_tcje);
    	dao.update(pz,"^gongsiNo|xc_bz|caozuoyuan_xm|che_no|kehu_mc|card_no|kehu_no|xc_rq|wxxm_tcje_sum|xc_ysje|xc_xmysje$");
		///////////////////////////////////////////////////////////////
		
		return jsons.json(1, 1, 1, "保存成功");
	}
	
	/**
	 * 获取当前单号派工信息
	 * 
	 * @param pai
	 * @return
	 */
		
	@At
	@Ok("raw:json")
	public String getpgxx(String xc_no) {
		if(xc_no!=""&&xc_no!=null){	
		List<Work_xiche_mx_gzEntity> result = dao.query(Work_xiche_mx_gzEntity.class,
				Cnd.where("xc_no", "=", xc_no));
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
		}
		return jsons.json(1, 1, 1, "输入单号查询派工信息。");
	}
		
	/**
	 * 获取当前单号添加的维修项目
	 * 
	 * @param pai
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String xcxm(String xc_no) {
		if(xc_no!=""&&xc_no!=null){	
		List<Work_xiche_wxxm_gzEntity> result = dao.query(Work_xiche_wxxm_gzEntity.class,
				Cnd.where("xc_no", "=", xc_no));
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
		}
		return jsons.json(1, 1, 1, "输入单号获取维修项目");
	}
	
	/**
	 * 获取洗车单基本信息没有新建
	 * @param pai
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String mrkxJbxx(String che_no, String gongsiNo, String caozuoyuan_xm) {
		if (gongsiNo == null || caozuoyuan_xm == null) {
			return jsons.json(1, 1, 0, "公司编号或操作员不能为空");
		}
		java.util.Calendar rightNow = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// 如果是后退几天，就写 -天数 例如：
		rightNow.add(java.util.Calendar.DAY_OF_MONTH, -20);
		// 进行时间转换
		String date = sim.format(rightNow.getTime());
		List<Work_xiche_pz_gzEntity> result = dao.query(Work_xiche_pz_gzEntity.class,
				Cnd.where("che_no", "=", che_no).and("xc_rq", ">", date)
				.desc("xc_rq"));
		if (result.size() == 0){
			String num = add(gongsiNo, caozuoyuan_xm);
			Work_xiche_pz_gzEntity pz = new Work_xiche_pz_gzEntity();
			pz.setXc_no(num);
			pz.setChe_no(che_no);
			pz.setXc_rq(new Date());
			Work_cheliang_smEntity che = dao.fetch(Work_cheliang_smEntity.class, che_no);
			if(che!=null){
				KehuEntity kehu = dao.fetch(KehuEntity.class,che.getKehu_no());
				if(kehu!=null){
					pz.setKehu_mc(kehu.getKehu_mc());
					pz.setKehu_no(kehu.getKehu_no());
				}
			}
			dao.updateIgnoreNull(pz);
			result = dao.query(Work_xiche_pz_gzEntity.class,
					Cnd.where("che_no", "=", che_no)
							.and("xc_rq", ">", date).desc("xc_rq"));
		}
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}
	
	/**
	 * 查看所有历史洗车信息
	 * @param xc_no
	 * @param pageNumber
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String lsxc(String xc_no,int pageNumber){
		System.out.println(pageNumber);
		Pager pager = dao.createPager(pageNumber, 20);
		List<Work_xiche_pz_sjEntity> result;
		//字段过多注意添加字段的时候有重复字段
		String sql_ = "select pz.xc_rq,pz.xc_no,pz.Zhifu_card_no,pz.Zhifu_card_je,pz.Zhifu_card_xj,pz.Flag_cardjs,kh.kehu_mc,kh.kehu_xm,kh.kehu_dh,pz.reco_no,kh.kehu_no,kh.kehulb_mc,cl.che_no,pz.xc_xmysje,pz.xc_jbr from work_xiche_pz_sj pz,kehu kh,work_cheliang_sm cl where pz.kehu_no = kh.kehu_no and pz.che_no = cl.che_no";
		
		if(xc_no!=null||xc_no!=""){
			   sql_ = "select pz.Card_no,pz.Zhifu_card_no,pz.Zhifu_card_je,pz.Zhifu_card_xj,pz.Flag_cardjs,kh.kehu_mc,kh.kehu_xm,kh.kehu_dh,pz.xc_no,kh.kehu_no,kh.kehulb_mc,cl.che_no,pz.xc_xmysje,pz.xc_jbr from work_xiche_pz_sj pz,kehu kh,work_cheliang_sm cl where  pz.xc_no like '%"+xc_no+"%' and pz.kehu_no = kh.kehu_no and pz.che_no = cl.che_no";
		}
		
		sql_+="  order by pz.xc_no desc";
		Sql sql = Sqls.queryEntity(sql_);
		sql.setPager(pager);
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, java.sql.ResultSet rs, Sql sql)
			throws SQLException {
			List<Work_xiche_pz_sjEntity> list = new LinkedList<Work_xiche_pz_sjEntity>();
			while (rs.next()){
				Work_xiche_pz_sjEntity pz = new Work_xiche_pz_sjEntity();
				pz.setXc_no(rs.getString("xc_no"));
				pz.setKehu_no(rs.getString("kehu_no"));
				pz.setChe_no(rs.getString("che_no"));
				pz.setXc_jbr(rs.getString("xc_jbr"));
				pz.setKehu_mc(rs.getString("kehu_mc"));
				pz.setCard_no(rs.getString("Card_no"));
				pz.setZhifu_card_no(rs.getString("Zhifu_card_no"));
				pz.setZhifu_card_je(rs.getDouble("Zhifu_card_je"));
				pz.setZhifu_card_xj(rs.getDouble("Zhifu_card_xj"));
				
				list.add(pz);
			}
			return list;
			}
		});
		dao.execute(sql);
		result =  sql.getList(Work_xiche_pz_sjEntity.class);
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}
	/**
	 * 洗车单历史维修项目明细
	 * 
	 * @param work_no
	 * @param pageNumber
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@At
	@Ok("raw:json")
	public String lsxcxmxx(String xc_no,int pageNumber){
		
			Pager pager = dao.createPager(pageNumber, 20);
			List<Work_xiche_wxxm_sjEntity> result;
			if(xc_no==null||xc_no.equals("")){
				 result = dao.query(Work_xiche_wxxm_sjEntity.class, null,pager);
			}else{
				result = dao.query(Work_xiche_wxxm_sjEntity.class, Cnd.where("xc_no","=",xc_no),pager);
			}
			
			String json = Json.toJson(result, JsonFormat.full());
			if (result.size() != 0) {
				return jsons.json(1, result.size(), 1, json);
			}
			return jsons.json(1, result.size(), 0, json);
		}
		
	
	String number="";
	/** 
	 * 创建洗车单号
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
						.prepareCall("{call sp_bslistnew (8001,?,?,?)}");
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
		Sql sql2 = Sqls
				.queryRecord("update work_xiche_pz_gz set xc_czy='"+caozuoyuan_xm+"',GongSiNo='"+gongsiNo+"' where xc_no= '"
						+ number + "'");
		dao.execute(sql2);
		

		return number;
	}
	/**
	 * 洗车单的结算 1验证密码，0不验证密码
	 * 
	 * @param caozuoyuanid
	 * @param work_no
	 * @param che_no
	 * @param xche_hjje
	 *            合计金额
	 * @param xche_ssje
	 *            实收金额
	 * @param xche_wxxm_yhje
	 *            项目优惠金额
	 * @param xche_peij_yhje
	 *            材料优惠金额
	 * @param xche_ysje
	 *    应收金额
	 * 洗车单的结算
	 * 
	 * @param caozuoyuanid
	 * @param work_no
	 * @param che_no
	 * @param isPrint
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String jiesuan(String caozuoyuanid, String xc_no, String che_no,int isPrint,String yh_zhanghao,
			 double xc_ssje,
			 double xc_ysje, String card_no,
			String pass, int iscard,double Zhifu_card_xj,double zhifu_card_je) {
		if(pass==null)
			 pass="";
			//=====================================修改cjn===================================================================
			Sql sqlcjn1 = Sqls
					.queryRecord("update b set b.wxxm_je = a.wxxm_je,b.wxxm_tcje= a.wxxm_tcje from work_xiche_wxxm_sj b,"
							+ "(select xc_no,sum(wxxm_je) wxxm_je,sum(isnull(wxxm_tcje,0)) wxxm_tcje from work_xiche_wxxm_gz where xc_no='"+xc_no+"' and wxxm_zt = '正常'"
							+ " group by xc_no) a   where b.xc_no = '"+xc_no+"' and a.xc_no = b.xc_no");
			
			dao.execute(sqlcjn1);
			
			//=====================================修改cjn===================================================================
			if (1 == iscard) {
				Kehu_CardEntity kehuCard = card.getVipCard(card_no);
				if (kehuCard == null) {
					return jsons.json(1, 1, 0, "卡不存在");
				}else if(!pass.equals(kehuCard.getCard_password())){
					return jsons.json(1, 1, 0, "储值卡密码错误");
				}
				if(Zhifu_card_xj<0)
					return jsons.json(1, 1, 0, "金额错误");
				if(Zhifu_card_xj+zhifu_card_je!=xc_ysje)
					return jsons.json(1, 1, 0, "金额错误");
				if(kehuCard.getCard_leftje()< zhifu_card_je)
					return jsons.json(1, 1, 0, "实收金额不能大于储值卡剩余金额！");
			}
			savejsJe(xc_no, xc_ssje,xc_ysje,iscard==0?false:true,card_no,Zhifu_card_xj,zhifu_card_je);
		    //判断会员卡是否可以使用
			Sql sql1 = Sqls
					.queryRecord("SELECT flag_single  FROM cardsysset");
			dao.execute(sql1);
			List<Record> res1 = sql1.getList(Record.class);
			int flag_single = res1.get(0).getInt("flag_single");
			 if (flag_single>0)     
		     {
				 Sql sql2 = Sqls
							.queryRecord("select count(*) as cnt  from kehu_card a, kehu_card_che b where a.card_no = b.card_no  and  flag_use = 0 and flag_guashi = 0 and flag_enddate = 0 and isnull(flag_shoukuan,0) = 1 and b.card_no = "+card_no+" and b.che_no = "+che_no+"");
					dao.execute(sql2);
					List<Record> res2 = sql2.getList(Record.class);
					int cnt = res2.get(0).getInt("cnt");
		         if(cnt==0){
		        	 return jsons.json(1, 1, 0, "系统设置为“关联式会员制度”，此会员卡非该辆车所有,不符合条件。是否取消使用该会员卡，继续进行结算？");
		         }
		     }
		// 当前操作员
		List<userEntity> list_user = dao.query(userEntity.class,
				Cnd.where("name", "=", caozuoyuanid));
		if (list_user.size() > 0) {
			userEntity user = list_user.get(0);			
			// 7.//
			Sql sql71 = Sqls
					.queryRecord("select Card_JifenType,Card_JiFenlv from cardsysset");
			dao.execute(sql71);
			List<Record> res71 = sql71.getList(Record.class);
			double Card_JifenType = res71.get(0).getInt("Card_JifenType");
			String Card_JiFen = res71.get(0).getString("Card_JiFenlv");
			double Card_JiFenlv = Double.parseDouble(Card_JiFen);
			double cardjifen=xc_ssje*Card_JiFenlv;
			// 8.//
			Sql sql81 = Sqls
					.queryRecord("select count(*) as cnt from ( select 1 as a  from work_xiche_wxxm_sj where xc_no = '"
							+ xc_no
							+ "' and  wxxm_no in (select wxxm_no from work_weixiu_sm where  isnull(wxxm_by,0)  = 1 ) union   select 1 as a  from work_xiche_wxxm_gz where xc_no = '"
							+ xc_no
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
			if(isPrint==1){
				Sql sql90 = Sqls
						.queryRecord("select printpc from PrintServerset where billkind = '洗车单'");
				dao.execute(sql90);
				List<Record> res190 = sql90.getList(Record.class);
				if(res190!=null && res190.size()>0){
				String jgss190 = res190.get(0).getString("printpc");
				Sql sql9 = Sqls
						.queryRecord("insert into PrintServerLog(printdate,billkind,billno,printczy,printpc,printsource,flag_print )  values (getdate(),'洗车单','"
								+ xc_no
								+ "','"
								+ caozuoyuanid
								+ "','"
								+ jgss190
								+ "',1,0)");
				dao.execute(sql9);
				
				}
				
			}

			// 10.//
			
			Sql sql103 = Sqls
					.queryRecord("select isnull(flag_cardjs,0) as card_js ,isnull(xc_ssje,0) as jzje,isnull(zhifu_card_xj,0) as card_zhifu_xj , kehu_no,Card_no ,ZhiFu_Card_No,   isnull(xc_ssje,0)  as Card_Ssje  from work_xiche_pz_gz where xc_no = '"
							+ xc_no + "'");
			dao.execute(sql103);
			List<Record> res103 = sql103.getList(Record.class);
			int card_js = res103.get(0).getInt("card_js");
			double jzje = Double.parseDouble(res103.get(0).getString("jzje"));
			int card_zhifu_xj = res103.get(0).getInt("card_zhifu_xj");
			String kehu_no = res103.get(0).getString("kehu_no");
			String Card_no = res103.get(0).getString("Card_no");
			String ZhiFu_Card_No = res103.get(0).getString("ZhiFu_Card_No");
//			if(card_js==1){
//				ZhiFu_Card_No = Card_no;
//			}
			int Card_Ssje = res103.get(0).getInt("Card_Ssje");
			Sql sql104 = Sqls                                                                                                 																								  																
					.queryRecord("insert into work_xiche_pz_sj (xc_no, kehu_no, che_no, card_no, card_kind, flag_cardjs,zhifu_card_no, zhifu_card_je,zhifu_card_xj, card_itemrate, gongsino, gongsimc,wxxm_tcje_sum)  select xc_no, kehu_no, che_no ,card_no,card_kind, "
							+ card_js 	
							+ " ,zhifu_card_no,zhifu_card_je,zhifu_card_xj, card_itemrate,gongsino,gongsimc,wxxm_tcje_sum,1 from work_xiche_pz_gz where xc_no = '"
							+ xc_no + "'");
			dao.execute(sql104);
			// 插入维修项目数据
			Sql sql105 = Sqls
					.queryRecord("insert into work_xiche_wxxm_sj(xc_no, wxxm_no, wxxm_mc, wxxm_gs, wxxm_khgs, wxxm_cb, wxxm_je, wxxm_ry,wxxm_zt, wxxm_bz, wxxm_zk,wxxm_yje,wxxm_dj,wxxm_tcfs,wxxm_tc,wxxm_tcje) select xc_no, wxxm_no, wxxm_mc, wxxm_gs, wxxm_khgs, wxxm_cb, wxxm_je, wxxm_ry,wxxm_zt, wxxm_bz, wxxm_zk,wxxm_yje,wxxm_dj,wxxm_tcfs,wxxm_tc,wxxm_tcje from work_xiche_wxxm_gz where xc_no= '"
							+ xc_no + "'");
			dao.execute(sql105);
			// 插入派工数据
			Sql sql109 = Sqls
					.queryRecord("insert into work_xiche_mx_sj(xc_no, reny_no, reny_mc, reny_fe) select c_no, reny_no, reny_mc, reny_fe from work_xiche_mx_gz where xc_no= '"
							+ xc_no + "'");
			dao.execute(sql109);
			
			//调用存储过程结算
			runCall("Wx_XiChe_JieSuan('" + xc_no + "')");
			Sql sql110 = Sqls
					.queryRecord("update work_yuyue_pz set yuyue_progress='已离店' where xc_no='"
			+ xc_no + "'");
			dao.execute(sql110);
			if (jzje != 0 && card_js == 0) {
				if ("现金".equals(yh_zhanghao)) {
			Sql sql111 = Sqls
					.queryRecord("insert into jizhang_xjmx(kehu_no,yewu_jsfs,jizhang_name,jizhang_pz,jizhang_km,jizhang_jf,jizhang_rq,jizhang_jb,jizhang_cz,jizhang_bz,yewu_dh,yewu_xz,GongSiNo,GongSiMc) values('"
							+ kehu_no
							+ "','"
							+ yh_zhanghao
							+ "', '现金','"+xc_no+"' ,'101' , '"
							+ jzje
							+ "',getdate() , '"+user.getName()+"' ,'"
							+ caozuoyuanid
							+ "' ,'收维修款' ,'"
							+ xc_no
							+ "' ,2007 ,'"+user.getGongSiNo()+"' , '"+user.getGongSiMc()+"' )");
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
								+ xc_no
								+ "' ,'101' ,@jzje,getdate() , '"+user.getName()+"' ,'"
								+ caozuoyuanid
								+ "' ,'收维修款' ,'"
								+ xc_no
								+ "' ,2007 ,'"+user.getGongSiNo()+"' ,'"+user.getGongSiMc()+"' )");
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
							+ xc_no
							+ "' ,'101' ,'"
							+ card_zhifu_xj
							+ "', getdate() , '"+user.getName()+"' , '"
							+ caozuoyuanid
							+ "' , '收维修款'  ,'"
							+ xc_no
							+ "' , 2007 ,'"+user.getGongSiNo()+"' ,  '"+user.getGongSiMc()+"' )");
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
								+ xc_no
								+ "' ,'101' ,'"
								+ card_zhifu_xj
								+ "',getdate() , '"+user.getName()+"' ,'"
								+ caozuoyuanid
								+ "' ,'收维修款'  ,'"
								+ xc_no
								+ "' ,2007 ,'"+user.getGongSiNo()+"' ,  '"+user.getGongSiMc()+"' )");
					dao.execute(sql114);
				}
			}			
			
			// 会员积分等处理
			if (card_js == 1) {
				Sql sql114 = Sqls
						.queryRecord("update kehu_card set card_xiaozje= card_xiaozje + "
								+ Card_Ssje
								+ "   where card_no= '"
								+ ZhiFu_Card_No + "'");
			dao.execute(sql114);
			if (!"".equals(ZhiFu_Card_No)) {
			if ("true".equals(Card_JifenType)) {
			
			System.out.println("111************************"+"update kehu_card set card_jifen=Card_jifen+"
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
			} else {System.out.println("222************************"+"update kehu_card set card_jifen=Card_jifen+"
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
					.queryRecord("select  wxxm_no from work_xiche_wxxm_sj where xc_no = '"
			+ xc_no + "'");
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
				System.out.println("3******************************"+"update kehu_card set card_jifen=Card_jifen+"
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
					System.out.println("4******************************"+"update kehu_card set card_jifen=Card_jifen+"
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
	
	private void savejsJe(String xc_no, double xc_ssje,
			  double xc_ysje,boolean flag_cardjs,String zhifu_card_no,double Zhifu_card_xj,double zhifu_card_je) {
		Work_xiche_pz_gzEntity pz = dao.fetch(Work_xiche_pz_gzEntity.class, xc_no);
		pz.setXc_ssje(xc_ssje);
		pz.setXc_ysje(xc_ysje);
		pz.setFlag_cardjs(flag_cardjs);
		if(flag_cardjs)
			pz.setZhifu_card_no(zhifu_card_no);
		pz.setZhifu_card_je(zhifu_card_je);
		if(!flag_cardjs)
			pz.setZhifu_card_xj(0);
		else
			pz.setZhifu_card_xj(Zhifu_card_xj);
		dao.update(pz,"^xc_ssje|xc_ysje|flag_cardjs|zhifu_card_no|zhifu_card_je|zhifu_card_xj$");
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
	/*
	 * 洗车完工微信
	 * @time 2017年9月9日08:47:15
	 * 
	 */
	@At
	@Ok("raw:json")
	public void xcweixin(String xc_no) throws Exception{
			Sql sql126 = Sqls
					.queryRecord("select Option_Value from Option_List where Option_NO = 3025");
			dao.execute(sql126);
			List<Record> res = sql126.getList(Record.class);
			String Option_Value = res.get(0).getString("Option_Value");
			
			if("1".equals(Option_Value)){
				String json = pu.getWeXinDiZhi();
				JsonParser parse = new JsonParser(); // 创建json解析器
				
				JsonObject js = (JsonObject) parse.parse(json); // 创建jsonObject对象
				JsonArray array = js.get("data").getAsJsonArray();
				
				
					JsonObject subObject = array.get(0).getAsJsonObject();
					String sys_weixindizhi = subObject.get("sys_weixindizhi").getAsString();

					System.out.println(sys_weixindizhi);
				if(sys_weixindizhi!=null&&sys_weixindizhi!=""){
					sys_weixindizhi+="/WS/ws_pub.asmx/GetDogDaoQi";
					HttpClient httpCLient = new DefaultHttpClient();  
					HttpGet httpget = new HttpGet(sys_weixindizhi); 
					
				        // 配置请求信息（请求时间）
				        RequestConfig rc = RequestConfig.custom().setSocketTimeout(5000)
				                .setConnectTimeout(5000).build();
				        // 获取使用DefaultHttpClient对象
				        CloseableHttpClient httpclient = HttpClients.createDefault();
				        // 返回结果
				        String result ="";
				        try {
				            if (sys_weixindizhi != null) {
				                // 创建HttpGet对象，将URL通过构造方法传入HttpGet对象
				                HttpPost httpget1 = new HttpPost(sys_weixindizhi);
				                // 将配置好请求信息附加到http请求中;
				                 httpget.setConfig(rc);
				                // 执行DefaultHttpClient对象的execute方法发送GET请求，通过CloseableHttpResponse接口的实例，可以获取服务器返回的信息
				               CloseableHttpResponse response = httpclient.execute(httpget1);
				             
				                try {
				                    // 得到返回对象
				                    HttpEntity entity = response.getEntity();
				                    if (entity != null) {
				                        // 获取返回结果
				                        result = EntityUtils.toString(entity);
				                        int i = result.length();
				                        result=result.substring(result.length()-19,i-9);//截取两个数字之间的部分
				                        System.out.println(result);
				                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				                        Date de = new Date();
				                        String string = date.format(de);
				                        Date date2 = date.parse(result);
				                        Date date3 = date.parse(string);
				                        System.out.println(date2+"===="+date3);
				                        if (date2.getTime() > date3.getTime()) {
				                        	Sql sql121 = Sqls
				            						.queryRecord("select  * from work_xiche_pz_sj where xc_no = '"
				            								+ xc_no + "'");
				                        	dao.execute(sql121);
				                        	List<Record> res126 = sql121.getList(Record.class);
				                        	System.out.println(res126.size());
				            				if (res126.size() > 0) {
				            					String che_no = res126.get(0).getString("che_no");
				            					String gongsino = res126.get(0).getString("gongsino");
				            					String zhifu_card_no = res126.get(0).getString("zhifu_card_no");
				            					String xc_jbr = res126.get(0).getString("xc_jbr");
				            					String card_no = res126.get(0).getString("card_no");
				            					String xc_ysje = res126.get(0).getString("xc_ysje");
				            					//String xche_jcr = res126.get(0).getString("xche_jcr");
				            					Map<String, Object> map = new HashMap<String, Object>();
				            					map.put("list_code", "102");
				            					map.put("tm_type", "wt_XiaoShouJieSuan");
				            					map.put("gongsino", gongsino);
				            					map.put("che_no", che_no);
				            					map.put("card_no", card_no);
				            					map.put("zhifu_card_no", zhifu_card_no);
				            					map.put("cardinfo", "");
				            					map.put("xc_no", xc_no);
				            					map.put("xc_ysje", xc_ysje);
				            					map.put("xc_jbr", xc_jbr);
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
				            					
				            					json1="strJson="+json1;
				            					String lujing=sys_weixindizhi+"/PadWeiXin/CssWeiXinAction.ashx";
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
				            			  
//				            			        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");  
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
				                } finally {
				                    // 关闭到客户端的连接
				                    response.close();
				                }
				            }
				        } catch (ClientProtocolException e) {
				            e.printStackTrace();
				        } catch (IOException e) {
				            e.printStackTrace();
				        } finally {
				            try {
				                // 关闭http请求
				                httpclient.close();
				            } catch (IOException e) {
				                e.printStackTrace();
				            }
				        }     
				    }
			    }  
			}
	
}