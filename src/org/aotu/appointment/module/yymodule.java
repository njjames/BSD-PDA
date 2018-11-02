package org.aotu.appointment.module;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.aotu.Jsons;
import org.aotu.offsetPager;
import org.aotu.appointment.entity.Work_yuyue_llEntity;
import org.aotu.appointment.entity.Work_yuyue_pzEntity;
import org.aotu.appointment.entity.Work_yuyue_wxxmEntity;
import org.aotu.offer.entity.baoJiaEntity;
import org.aotu.offer.entity.feilvEntity;
import org.aotu.offer.entity.offerEntity;
import org.aotu.order.entity.Work_ll_gzEntity;
import org.aotu.order.entity.Work_mx_gzEntity;
import org.aotu.order.entity.Work_pz_gzEntity;
import org.aotu.order.module.ordersModule;
import org.aotu.publics.eneity.KehuEntity;
import org.aotu.publics.eneity.Work_cheliang_smEntity;
import org.aotu.publics.module.publicModule;
import org.nutz.dao.Cnd;
import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@IocBean
@At("/yuyue")
public class yymodule {
	@Inject
	Dao dao;

	@Inject
	Jsons jsons;

	@Inject
	ordersModule ordersModule;
	@Inject
	publicModule pu;

	/**
	 * 新建预约
	 * 
	 * @param pai
	 * @param gongsiNo
	 *            公司
	 * @param caozuoyuan_xm
	 *            操作员
	 * @return 2.0版本 刘宏德维护 第一版本是输入车牌号码，如果没有，返回一个空， 我修改为： 如果没有车牌的快速报价，直接创建一个，
	 */
	@At
	@Ok("raw:json")
	public String jbxx(String pai, String gongsiNo, String caozuoyuan_xm, String yuyue_no) {
		Work_yuyue_pzEntity pz = new Work_yuyue_pzEntity();
		if (!"".equals(yuyue_no)) {
			pz = dao.fetch(Work_yuyue_pzEntity.class, yuyue_no);
		} else {
			java.util.Calendar rightNow = java.util.Calendar.getInstance();
			java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 如果是后退几天，就写 -天数 例如：
			rightNow.add(java.util.Calendar.DAY_OF_MONTH, -20);
			// 进行时间转换
			String date = sim.format(rightNow.getTime());
			List<Work_yuyue_pzEntity> list = dao.query(Work_yuyue_pzEntity.class,
					Cnd.where("che_no", "=", pai)
                            .and("yuyue_progress", "<>", "已进店")
                            .and("yuyue_progress", "<>", "已离店")
                            .and("yuyue_progress", "<>", "已取消")
                            .and("yuyue_jlrq", ">", date)
							.desc("yuyue_jlrq"));
			if (list.size() == 0) {
				String num = add(gongsiNo, caozuoyuan_xm);
				if (num != null) {
                    pz.setYuyue_no(num);
                    pz.setChe_no(pai);
                    pz.setKehu_no(pai);
                    pz.setWork_no("");
                    pz.setYuyue_scjcrq(new Date());
                    pz.setYuyue_jlrq(new Date());
					// 查询建表需要的内容
					List<feilvEntity> fei = dao.query(feilvEntity.class,
							Cnd.where("feil_sy", "=", true));
					if (fei.size() < 0) {
						fei = dao.query(feilvEntity.class, Cnd.where("feil_mc", "=", "一级标准"));
					}
					String mc = fei.get(0).getFeil_mc();
					double fl = fei.get(0).getFeil_fl();
                    pz.setYuyue_sfbz(mc);
                    pz.setYuyue_sffl(fl);
                    pz.setYuyue_state(0);
                    pz.setYuyue_progress("");
					Work_cheliang_smEntity che = dao.fetch(Work_cheliang_smEntity.class, pai);
					if (che != null) {
                        pz.setChe_cx(che.getChe_cx());
                        pz.setChe_vin(che.getChe_vin());
                        pz.setGcsj(che.getChe_gcrq());
                        pz.setYuyue_yjjclc(che.getChe_next_licheng());
						KehuEntity kehu = dao.fetch(KehuEntity.class, che.getKehu_no());
						if (kehu != null) {
                            pz.setKehu_mc(kehu.getKehu_mc());
                            pz.setKehu_dh(kehu.getKehu_dh());
                            pz.setKehu_no(kehu.getKehu_no());
						}
					}
					dao.updateIgnoreNull(pz);
				}
			} else {
                pz = list.get(0);
            }
		}
        String json = Json.toJson(pz, JsonFormat.full());
        return jsons.json(1, 1, 1, json);
	}

	/**
	 * 维修项目
	 * 
	 * @param yuyue_no
	 * @return
	 */

	@At
	@Ok("raw:json")
	public String wxxm(String yuyue_no) {
		List<Work_yuyue_wxxmEntity> result = dao.query(Work_yuyue_wxxmEntity.class,
				Cnd.where("yuyue_no", "=", yuyue_no));
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}

	/**
	 * 材料
	 * 
	 * @param yuyue_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String cail(String yuyue_no) {
		List<Work_yuyue_llEntity> result = dao.query(Work_yuyue_llEntity.class,
				Cnd.where("yuyue_no", "=", yuyue_no));
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}

	/**
	 * 历史预约
	 * 
	 * @param pageNumber
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String lsyy(int pageNumber, String che_no, String kehu_xm) {
		
		Pager pager = dao.createPager(pageNumber, 20);
		Cnd cnd = Cnd.where("yuyue_state", "=", 1);
		if (che_no != null && che_no.length()>0)
			cnd.and("che_no", "like", "%" + che_no + "%");
		if (kehu_xm != null && kehu_xm.length()>0)
			cnd.and("kehu_mc", "like", "%" + kehu_xm + "%");
		List<Work_yuyue_pzEntity> result = dao.query(Work_yuyue_pzEntity.class,
				cnd.orderBy("yuyue_jlrq", "desc"), pager);
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}

	String number;

	/**
	 * @param gongsiNo
	 *            公司号
	 * @param caozuoyuan_xm
	 *            操作员
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
						.prepareCall("{call sp_bslistnew (2019,?,?,?)}");
				cs.setString(1, gongsiNo);
				cs.setString(2, caozuoyuan_xm);
				cs.registerOutParameter(3, Types.VARCHAR);
				cs.executeUpdate();
				String orderNo = cs.getString(3);
				number = orderNo;
			}
		});
		if (number == "") {
			return null;
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

		Sql sql = Sqls.create("update work_yuyue_pz set yuyue_czy = @name,"
				+ " dept_mc = @dept," + "yuyue_jlrq = getdate(),"
				+ "yuyue_sfbz = @feil_mc,"
				+ "yuyue_sffl = @feil_fl  $condition");
		sql.params().set("name", caozuoyuan_xm);
		sql.params().set("dept", gongsiNo);
		sql.params().set("feil_mc", mc);
		sql.params().set("feil_fl", fl);
		sql.params().set("yuyue_no", number);
		sql.setCondition(Cnd.wrap("yuyue_no='" + number + "'"));
		dao.execute(sql);
		int sd = sql.getUpdateCount();
		if (sd != 0) {
			return number;
		}
		return null;

	}

	/**
	 * 添加基本信息
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String addjbxx(@Param("gongsiNo") final String gongsiNo,
			@Param("caozuoyuan_xm") final String caozuoyuan_xm,
			@Param("..") Work_yuyue_pzEntity yuyue) {

		String num = add(gongsiNo, caozuoyuan_xm);
		baoJiaEntity baojia = dao.fetch(baoJiaEntity.class,
				Cnd.where("list_no", "=", num));
		baojia.setList_state(1);
		baojia.setList_progress("未进店");
		dao.update(baojia);
		if (num != null) {
			yuyue.setYuyue_no(num);
			yuyue.setYuyue_scjcrq(new Date());
			int nu = dao.updateIgnoreNull(yuyue);
			return num;
		} else {
			return null;
		}
	}

	/**
	 * @param yuyue_no
	 * @param pz
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String editsj(String yuyue_no, Date pz) {
		Work_yuyue_pzEntity yuyue = dao.fetch(Work_yuyue_pzEntity.class,
				yuyue_no);
		yuyue.setYuyue_yjjcrq(pz);
		dao.update(yuyue);
		return jsons.json(1, 1, 1, "成功");
	}

	/**
	 * 添加基本信息
	 *
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String addjb(@Param("..") Work_yuyue_pzEntity yuyue) {

		System.out.println("=====================里程=============================="+yuyue.getYuyue_yjjclc());
		Work_cheliang_smEntity che = pu.saveCheInfo(yuyue.getChe_no(), yuyue.getGcsj(), yuyue.getChe_cx(), yuyue.getChe_vin(),yuyue.getGongSiNo());
		pu.saveKeHu(che.getKehu_no(), yuyue.getKehu_mc(), yuyue.getKehu_dh());
		
		yuyue.setYuyue_scjcrq(new Date());
		
		Work_yuyue_pzEntity yuyue_pz = dao.fetch(Work_yuyue_pzEntity.class,
				Cnd.where("yuyue_no", "=", yuyue.getYuyue_no()));
		yuyue.setReco_no(yuyue_pz.getReco_no());
		yuyue.setYuyue_state(1);
		yuyue.setYuyue_progress("未进店");
		
		dao.update(yuyue,"^che_cx|yuyue_progress|yuyue_state|che_vin|yuyue_yjjcrq|yuyue_yjjclc|yuyue_yjjclc|kehu_mc|kehu_dh|yuyue_sfbz|yuyue_sffl|kehu_no|yuyue_scjcrq$");

		return yuyue.getYuyue_no();
	}

	/**
	 * {"yuyue_no":YY0120160900002,data:[{wxxm_mc:电工,wxxm_gs:0.0,wxxm_dj:12.6,
	 * wxxm_zt:正常}, {wxxm_mc:发动机,wxxm_gs:1.0,wxxm_dj:9.0,wxxm_zt:正常},
	 * {wxxm_mc:修发动机,wxxm_gs:1.0,wxxm_dj:12.6,wxxm_zt:正常},
	 * {wxxm_mc:换转向助力泵皮带,wxxm_gs:1.5,wxxm_dj:18.9,wxxm_zt:正常},
	 * {wxxm_mc:修油表浮子,wxxm_gs:8.0,wxxm_dj:100.8,wxxm_zt:正常},
	 * {wxxm_mc:检修油泵电路故障,wxxm_gs:8.0,wxxm_dj:100.8,wxxm_zt:正常},
	 * {wxxm_mc:检修EGR阀系统故障,wxxm_gs:10.0,wxxm_dj:126.0,wxxm_zt:正常},
	 * {wxxm_mc:检修EGR阀系统故障,wxxm_gs:10.0,wxxm_dj:126.0,wxxm_zt:正常}]}
	 */

	/**
	 * 添加维修项目
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String addwxxm(String addLists) {
		JsonParser parse = new JsonParser(); // 创建json解析器
		try {
            JsonArray array = (JsonArray) parse.parse(addLists);
			for (int i = 0; i < array.size(); i++) {
                String yuyueNo = "";
				Work_yuyue_wxxmEntity yuyueWxxmEntity = new Work_yuyue_wxxmEntity();
				JsonObject item = array.get(i).getAsJsonObject();
				yuyueNo = item.get("yuyue_no").getAsString();
				yuyueWxxmEntity.setYuyue_no(yuyueNo);
                yuyueWxxmEntity.setWxxm_no(item.get("wxxm_no").getAsString());
                yuyueWxxmEntity.setWxxm_mc(item.get("wxxm_mc").getAsString());
                yuyueWxxmEntity.setWxxm_gs(item.get("wxxm_gs").getAsDouble());
                yuyueWxxmEntity.setWxxm_yje(item.get("wxxm_je").getAsDouble());
				yuyueWxxmEntity.setWxxm_je(item.get("wxxm_je").getAsDouble());
				if(yuyueWxxmEntity.getWxxm_gs()==0){
					yuyueWxxmEntity.setWxxm_dj(yuyueWxxmEntity.getWxxm_je());
				}else{
                    yuyueWxxmEntity.setWxxm_dj(yuyueWxxmEntity.getWxxm_je() / yuyueWxxmEntity.getWxxm_gs());
				}
				yuyueWxxmEntity.setWxxm_zt(item.get("wxxm_zt").getAsString());
				yuyueWxxmEntity.setWxxm_khgs(item.get("wxxm_gs").getAsDouble());
				Work_yuyue_wxxmEntity newYuyue = dao.insert(yuyueWxxmEntity);
				if (newYuyue == null) {
                    return "fail";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
            return "fail";
		}
        return "success";
	}

	/**
	 * 添加材料
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String addcail(String addLists) {
		JsonParser parse = new JsonParser(); // 创建json解析器
		try {
            String yuyueNo = "";
            JsonArray array = (JsonArray) parse.parse(addLists);
			for (int i = 0; i < array.size(); i++) {
				Work_yuyue_llEntity yuyueLlEntity = new Work_yuyue_llEntity();
				JsonObject item = array.get(i).getAsJsonObject();
                yuyueNo = item.get("yuyue_no").getAsString();
                String peijNo = item.get("peij_no").getAsString();
                Work_yuyue_llEntity entity = dao.fetch(Work_yuyue_llEntity.class, Cnd.where("yuyue_no", "=", yuyueNo).and("peij_no", "=", peijNo));
                if (entity == null) {
                    yuyueLlEntity.setYuyue_no(item.get("yuyue_no").getAsString());
                    yuyueLlEntity.setPeij_no(item.get("peij_no").getAsString());// 配件编码
                    yuyueLlEntity.setPeij_mc(item.get("peij_mc").getAsString());// 配件名称
                    yuyueLlEntity.setPeij_sl(item.get("peij_sl").getAsDouble());// 配件数量
                    yuyueLlEntity.setPeij_dj(item.get("peij_dj").getAsDouble());// 配件单价
                    yuyueLlEntity.setPeij_zt(item.get("peij_zt").getAsString());// 配件状态
                    yuyueLlEntity.setPeij_je(item.get("peij_je").getAsDouble());// 配件金额
                    yuyueLlEntity.setPeij_th(item.get("peij_th").getAsString());// 配件图号
                    yuyueLlEntity.setPeij_dw(item.get("peij_dw").getAsString());// 配件单位
                    Work_yuyue_llEntity dd = dao.insert(yuyueLlEntity);
                    if (dd == null) {
                        return "fail";
                    }
                } else {
                    if (entity.getPeij_sl() + item.get("peij_sl").getAsDouble() != 0) {
                        Sql sqlUpdate = Sqls
                                .create("update work_yuyue_ll set peij_sl = peij_sl + " + item.get("peij_sl").getAsDouble() + ",peij_je = peij_je + " + item.get("peij_je").getAsDouble() + " where yuyue_no='" + yuyueNo + "' and peij_no ='" + peijNo + "'");
                        dao.execute(sqlUpdate);
                    } else {
                        Sql sqlDelete = Sqls
                                .create("delete from work_yuyue_ll where yuyue_no='" + yuyueNo + "' and peij_no ='" + peijNo + "'");
                        dao.execute(sqlDelete);
                    }
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
            return "fail";
		}
        return "success";
	}

	
	
	
	/**
	 * @param no
	 * @param gongsiNo
	 * @param caozuoyuan_xm
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String yuyyejinchang(String no, String gongsiNo, String caozuoyuan_xm) {
		Work_yuyue_pzEntity yuyue = dao.fetch(Work_yuyue_pzEntity.class, no);
		if (yuyue != null) {
			// 新建一个接待登记单号
			String num = ordersModule.add(gongsiNo, caozuoyuan_xm);
			yuyue.setYuyue_state(1);
			yuyue.setYuyue_progress("未进店");
			yuyue.setYuyue_IsImpt(1);
			yuyue.setWork_no(num);
			dao.update(yuyue);
			
			Work_pz_gzEntity pz_new = dao.fetch(Work_pz_gzEntity.class, num);
			pz_new.setWork_no(num);
			pz_new.setKehu_no(yuyue.getKehu_no());
			pz_new.setXche_jdrq(new Date());
			
			pz_new.setYuyue_no(yuyue.getYuyue_no());
			pz_new.setKehu_mc(yuyue.getKehu_mc());
			pz_new.setKehu_xm(yuyue.getKehu_xm());
			pz_new.setKehu_dz(yuyue.getKehu_dz());
			pz_new.setKehu_dh(yuyue.getKehu_dh());
			pz_new.setKehu_sj(yuyue.getKehu_sj());
			pz_new.setChe_zjno(yuyue.getChe_zjno());
			pz_new.setKehu_yb(yuyue.getKehu_yb());
			pz_new.setChe_no(yuyue.getChe_no());
			pz_new.setChe_vin(yuyue.getChe_vin());
			pz_new.setChe_fd(yuyue.getChe_fd());
			pz_new.setChe_cx(yuyue.getChe_cx());
			pz_new.setChe_wxys(yuyue.getChe_wxys());
			
			pz_new.setXche_gdfl(yuyue.getYuyue_gdfl());
			pz_new.setXche_wxfl(yuyue.getYuyue_wxfl());
			pz_new.setXche_sfbz(yuyue.getYuyue_sfbz());
			pz_new.setXche_gj(yuyue.getYuyue_hjje());
			pz_new.setXche_jcr(yuyue.getYuyue_jcr());
			pz_new.setXche_lc(yuyue.getYuyue_yjjclc());
			pz_new.setFlag_IsCheck(false);
			pz_new.setFlag_isxiche(false);
			pz_new.setFlag_pad(true);
			pz_new.setXche_ywlx("预约转维修");	
			pz_new.setFlag_fast(false);
			int nu = dao.updateIgnoreNull(pz_new);


			// 更新详情表
			Sql sql_ = Sqls
					.create("select yuyue_no,a.peij_no,a.peij_mc,a.peij_dw,a.peij_jk,a.peij_th,a.peij_cx,a.peij_pp,a.peij_cd,a.peij_bz,peij_sl,peij_dj,peij_je,Peij_zt,b.peij_tcfs ,b.peij_tc   from work_yuyue_ll a ,kucshp_info b  where a.yuyue_no = '"
							+ no + "'and a.peij_no=b.peij_no");
			sql_.setCallback(new SqlCallback() {
				public Object invoke(Connection conn, java.sql.ResultSet rs,
						Sql sql) throws SQLException {
					List<Work_ll_gzEntity> list = new LinkedList<Work_ll_gzEntity>();
					while (rs.next()) {
						Work_ll_gzEntity ll = new Work_ll_gzEntity();
						// ll.setwork_no(num);
						ll.setPeij_no(rs.getString("Peij_no"));
						ll.setPeij_mc(rs.getString("peij_mc"));
						ll.setPeij_dw(rs.getString("peij_dw"));
						ll.setPeij_jk(rs.getString("peij_jk"));
						ll.setPeij_th(rs.getString("peij_th"));
						ll.setPeij_cx(rs.getString("peij_cx"));
						ll.setPeij_pp(rs.getString("peij_pp"));
						ll.setPeij_cd(rs.getString("peij_cd"));
						ll.setPeij_bz(rs.getString("peij_bz"));
						ll.setPeij_sl(rs.getDouble("peij_sl"));
						ll.setPeij_dj(rs.getDouble("peij_dj"));
						ll.setPeij_je(rs.getDouble("peij_je"));
						ll.setPeij_zt(rs.getString("peij_zt"));
						ll.setPeij_tcfs(rs.getInt("peij_tcfs"));
						ll.setPeij_tc(rs.getDouble("peij_tc"));
						ll.setPeij_yje(rs.getDouble("peij_je"));
						list.add(ll);
					}
					return list;
				}
			});
			dao.execute(sql_);
			List<Work_ll_gzEntity> list = sql_.getList(Work_ll_gzEntity.class);
			for (Work_ll_gzEntity llgz : list) {
				llgz.setWork_no(num);
				dao.insert(llgz);
			}
			// 结束
			// 更新详细表
			Sql sql_2 = Sqls
					.create("select a.wxxm_no,a.wxxm_mc,a.wxxm_gs,a.wxxm_dj,a.wxxm_je,a.wxxm_khgs,wxxm_zt,a.wxxm_bz,'正常',b.wxxm_tcfs,b.wxxm_tc from work_yuyue_wxxm a ,work_weixiu_sm b where a.yuyue_no = '"
							+ no + "' and a.wxxm_no= b.wxxm_no ");
			sql_2.setCallback(new SqlCallback() {
				public Object invoke(Connection conn, java.sql.ResultSet rs,
						Sql sql) throws SQLException {
					List<Work_mx_gzEntity> list_gz = new LinkedList<Work_mx_gzEntity>();
					while (rs.next()) {
						Work_mx_gzEntity mxgz = new Work_mx_gzEntity();
						mxgz.setWxxm_no(rs.getString("wxxm_no"));
						mxgz.setWxxm_mc(rs.getString("wxxm_mc"));
						mxgz.setWxxm_gs(rs.getDouble("wxxm_gs"));
						mxgz.setWxxm_dj(rs.getDouble("wxxm_dj"));
						mxgz.setWxxm_je(rs.getDouble("wxxm_je"));
						mxgz.setWxxm_khgs(rs.getDouble("wxxm_khgs"));
						mxgz.setWxxm_zt(rs.getString("wxxm_zt"));
						mxgz.setWxxm_bz(rs.getString("wxxm_bz"));
						mxgz.setWxxm_tpye("正常");
						mxgz.setWxxm_tcfs(rs.getInt("wxxm_tcfs"));
						mxgz.setWxxm_tc(rs.getDouble("wxxm_tc"));
						mxgz.setWxxm_yje(rs.getDouble("wxxm_je"));
						mxgz.setWxxm_Print(true);
						list_gz.add(mxgz);
					}
					return list_gz;
				}
			});
			dao.execute(sql_2);
			List<Work_mx_gzEntity> list_mx = sql_2
					.getList(Work_mx_gzEntity.class);
			for (Work_mx_gzEntity llgz : list_mx) {
				llgz.setWork_no(num);
				dao.insert(llgz);
			}
			// 结束

			Sql sql1 = Sqls
					.create("update work_pz_gz set mainstate=0,substate='未派车辆',xche_wxjd='在修',xche_jdrq=getdate() where work_no= '"
							+ num + "'");
			dao.execute(sql1);

			Sql sql2 = Sqls
					.create("update work_pz_gz set xche_wxsj = isnull(DATEDIFF(minute, xche_jdrq, xche_yjwgrq),0) where work_no= '"
							+ num + "'");
			dao.execute(sql2);

			Sql sql3 = Sqls
					.create("update work_mx_gz set wxxm_jd='未派工' where work_no= '"
							+ num + "'");
			dao.execute(sql3);

			Work_pz_gzEntity pz_ = dao.fetch(Work_pz_gzEntity.class, num);

			Sql sql4 = Sqls
					.create("update work_yuyue_pz set yuyue_state=1,  yuyue_progress='已进店',yuyue_sjjcrq=getdate() where yuyue_no= '"
							+ pz_.getYuyue_no() + "'");
			dao.execute(sql4);

			Sql sql5 = Sqls
					.create("update work_baojia_pz set list_progress='已进店' where list_no=  '"
							+ pz_.getYuyue_no() + "'");
			dao.execute(sql5);

//			Sql sql6 = Sqls
//					.create("update work_cheliang_sm set che_rjlc = (xche_lc-xche_last_lc)/ case isnull(convert(numeric(18,2), xche_jdrq - xche_last_jdrq),0) when 0 then 1 else isnull(convert(numeric(18,2), xche_jdrq - xche_last_jdrq),0) end from work_cheliang_sm a,work_pz_gz b where a.che_no = b.che_no and a.che_no = '"
//							+ pz_.getChe_no() + "' and work_no='" + num + "'");
//			dao.execute(sql6);
			////////////////////合计金额计算///////////////////////////////////
			Sql sql1_1 = Sqls
					.create("update b set b.xche_rgf = a.xche_rgf,b.xche_rgbh=a.xche_rgf,b.xche_rgsl=0,xche_rgse=0 from work_pz_gz b,(select work_no,sum(wxxm_je) xche_rgf from work_mx_gz where work_no='"+num+"' and wxxm_zt in ('正常','保险') group by work_no) a   where b.work_no = '"+num+"' and a.work_no = b.work_no");
			dao.execute(sql1_1);			
			Sql sql1_2 = Sqls
					.create("update b set b.xche_clf = a.xche_clf,b.xche_clbh=a.xche_clf,b.xche_clsl=0,xche_clse=0 from work_pz_gz b,(select work_no,sum(peij_je) xche_clf from work_ll_gz where work_no='"+num+"' and peij_zt in ('正常','保险')group by work_no ) a   where b.work_no = '"+num+"' and a.work_no = b.work_no");
			dao.execute(sql1_2);
			Sql sql1_3 = Sqls
					.create("update work_pz_gz set xche_yhje = isnull(xche_wxxm_yhje,0)+isnull(xche_peij_yhje,0) where work_no = '"+num+"'");
			dao.execute(sql1_3);
			Sql sql1_4 = Sqls
					.create("update work_pz_gz set xche_hjje= isnull(xche_rgf,0) + isnull(xche_clf,0) where work_no = '"+num+"'");
			dao.execute(sql1_4);
			///////////////////////////////////////////////////////////////
			return jsons.json(1, 5, 1, "进厂成功");
		} else {
			return jsons.json(1, 5, 0, "进厂失败，订单不存在");
		}
	}
}
