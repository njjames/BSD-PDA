package org.aotu.appointment.module;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.sun.scenario.effect.impl.prism.ps.PPSZeroSamplerPeer;
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
import org.aotu.util.BsdUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
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
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

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
	 * @param che_no
	 * @param gongsiNo
	 *            公司
	 * @param caozuoyuan_xm
	 *            操作员
	 * @return 2.0版本 刘宏德维护 第一版本是输入车牌号码，如果没有，返回一个空， 我修改为： 如果没有车牌的快速报价，直接创建一个，
	 */
	@At
	@Ok("raw:json")
	public String jbxx(String che_no, String gongsiNo, String caozuoyuan_xm, String yuyue_no) {
		Work_cheliang_smEntity che = dao.fetch(Work_cheliang_smEntity.class, che_no);
		if (yuyue_no != null && !"".equals(yuyue_no)) {
			Work_yuyue_pzEntity pz = dao.fetch(Work_yuyue_pzEntity.class, yuyue_no);
            if (pz != null) {
                pz.setGcsj(che.getChe_gcrq());
                String json = Json.toJson(pz, JsonFormat.full());
                return jsons.json(1, 1, 1, json);
            }
            return jsons.json(1, 1, 0, "此单已经不存在！");
        }
		return jsons.json(1, 1, 0, "单号不能为空！");
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
	public String addjb(@Param("..") final Work_yuyue_pzEntity yuyue, final boolean justsave) {
        try {
            Trans.exec(new Atom() {
                @Override
                public void run() {
                    String yuyue_gj_wx = "0";
                    String yuyue_gj_ll = "0";
                    Sql sql = Sqls.queryRecord("select isnull(sum(wxxm_je),0) as je from work_yuyue_wxxm where yuyue_no='" + yuyue.getYuyue_no() + "'");
                    dao.execute(sql);
                    List<Record> list = sql.getList(Record.class);
                    if (list.size() > 0) {
                        yuyue_gj_wx = list.get(0).getString("je");
                    }
                    Sql sql2 = Sqls.queryRecord("select isnull(sum(peij_je),0) as je from work_yuyue_ll where yuyue_no='" + yuyue.getYuyue_no() + "'");
                    dao.execute(sql2);
                    List<Record> list2 = sql2.getList(Record.class);
                    if (list2.size() > 0) {
                        yuyue_gj_ll = list2.get(0).getString("je");
                    }
                    Work_yuyue_pzEntity pzEntity = dao.fetch(Work_yuyue_pzEntity.class, yuyue.getYuyue_no());
                    pzEntity.setChe_vin(yuyue.getChe_vin());
                    pzEntity.setChe_cx(yuyue.getChe_cx());
                    pzEntity.setKehu_mc(yuyue.getKehu_mc());
                    pzEntity.setKehu_dh(yuyue.getKehu_dh());
                    pzEntity.setYuyue_sfbz(yuyue.getYuyue_sfbz());
                    pzEntity.setYuyue_sffl(yuyue.getYuyue_sffl());
                    pzEntity.setYuyue_bz(yuyue.getYuyue_bz());
                    pzEntity.setYuyue_jbr(yuyue.getYuyue_jbr());
                    pzEntity.setYuyue_yjjclc(yuyue.getYuyue_yjjclc());
                    pzEntity.setYuyue_scjcrq(new Date());
                    pzEntity.setYuyue_gj_wx(new BigDecimal(yuyue_gj_wx).doubleValue());
                    pzEntity.setYuyue_gj_ll(new BigDecimal(yuyue_gj_ll).doubleValue());
                    pzEntity.setYuyue_hjje(new BigDecimal(yuyue_gj_wx).add(new BigDecimal(yuyue_gj_ll)).doubleValue());
                    if (!justsave) {
                        pzEntity.setYuyue_state(1);
                        pzEntity.setYuyue_progress("未进店");
                    }
                    dao.update(yuyue,"^che_cx|che_vin|yuyue_yjjclc|kehu_mc|kehu_dh|yuyue_sfbz|yuyue_sffl|yuyue_scjcrq|yuyue_bz|yuyue_jbr|yuyue_state|yuyue_progress$");
                    pu.saveCheInfo(yuyue.getChe_no(), yuyue.getGcsj(), yuyue.getChe_cx(), yuyue.getChe_vin(),yuyue.getGongSiNo());
                    pu.saveKeHu(yuyue.getKehu_no(), yuyue.getKehu_mc(), yuyue.getKehu_dh());
                }
            });
        } catch (Exception e) {
            return jsons.json(1, 1, 0, "保存单据失败！");
        }
        return jsons.json(1, 1, 1, "保存成功");
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
            String yuyueNo;
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
                        dao.execute(Sqls.create("delete from work_yuyue_ll where yuyue_no='" + yuyueNo + "' and peij_no='" + peijNo + "'"));
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
	 * @param yuyue_no
	 * @param gongsiNo
	 * @param caozuoyuan_xm
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String yuyyejinchang(final String yuyue_no, final String gongsiNo, final String caozuoyuan_xm) {
		final Work_yuyue_pzEntity yuyue = dao.fetch(Work_yuyue_pzEntity.class, yuyue_no);
		if (yuyue != null) {
            final Work_pz_gzEntity[] pz_new = new Work_pz_gzEntity[1];
            try {
                Trans.exec(new Atom() {
                    @Override
                    public void run() {
                        // 新建一个接待登记单号
                        String num = BsdUtils.createNewBill(dao, gongsiNo, caozuoyuan_xm, 2007, false);
                        pz_new[0] = dao.fetch(Work_pz_gzEntity.class, num);
                        pz_new[0].setYuyue_no(yuyue.getYuyue_no());
                        pz_new[0].setKehu_no(yuyue.getKehu_no());
                        pz_new[0].setKehu_mc(yuyue.getKehu_mc());
                        pz_new[0].setKehu_xm(yuyue.getKehu_xm());
                        pz_new[0].setKehu_dz(yuyue.getKehu_dz());
                        pz_new[0].setKehu_dh(yuyue.getKehu_dh());
                        pz_new[0].setKehu_sj(yuyue.getKehu_sj());
                        pz_new[0].setChe_zjno(yuyue.getChe_zjno());
                        pz_new[0].setKehu_yb(yuyue.getKehu_yb());
                        pz_new[0].setChe_no(yuyue.getChe_no());
                        pz_new[0].setChe_vin(yuyue.getChe_vin());
                        pz_new[0].setChe_fd(yuyue.getChe_fd());
                        pz_new[0].setChe_cx(yuyue.getChe_cx());
                        pz_new[0].setChe_wxys(yuyue.getChe_wxys());
                        pz_new[0].setXche_gdfl(yuyue.getYuyue_gdfl());
                        pz_new[0].setXche_wxfl(yuyue.getYuyue_wxfl());
                        pz_new[0].setXche_sfbz(yuyue.getYuyue_sfbz());
                        pz_new[0].setXche_gj(yuyue.getYuyue_hjje());
                        pz_new[0].setXche_jcr(yuyue.getYuyue_jcr());
                        pz_new[0].setXche_zxr(yuyue.getYuyue_ry());
                        pz_new[0].setXche_lc(yuyue.getYuyue_yjjclc());
                        pz_new[0].setFlag_IsCheck(false);
                        pz_new[0].setFlag_isxiche(false);
                        pz_new[0].setFlag_pad(true);
                        pz_new[0].setXche_ywlx("预约转维修");
                        Sql sql = Sqls.queryRecord("select kehu_bxno,kehu_bxmc from work_cheliang_sm where che_no='" + yuyue.getChe_no() + "'");
                        dao.execute(sql);
                        List<Record> list1 = sql.getList(Record.class);
                        if (list1.size() > 0) {
                            pz_new[0].setKehu_bxno(list1.get(0).getString("kehu_bxno"));
                            pz_new[0].setKehu_bxmc(list1.get(0).getString("kehu_bxmc"));
                        }
                        if (yuyue.getYuyue_wxsj() != 0) {
                            int day = yuyue.getYuyue_wxsj() / (24 * 60);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(pz_new[0].getXche_jdrq());
                            calendar.add(Calendar.DATE, day);
                            pz_new[0].setXche_yjwgrq(calendar.getTime());
                        }
                        // 进厂的状态
                        pz_new[0].setMainstate(0);
                        pz_new[0].setSubstate("未派车辆");
                        pz_new[0].setXche_wxjd("在修");
                        pz_new[0].setXche_jdrq(new Date());
                        dao.update(pz_new[0], "^kehu_no|xche_jdrq|yuyue_no|kehu_mc|kehu_xm|kehu_dz|kehu_dh|kehu_sj|che_zjno|kehu_yb|che_no|che_vin|che_fd|che_cx|" +
                                "che_wxys|xche_gdfl|xche_wxfl|xche_sfbz|xche_gj|xche_jcr|xche_lc|flag_IsCheck|flag_isxiche|flag_pad|xche_ywlx|kehu_bxno|kehu_bxmc|xche_yjwgrq|" +
                                "mainstate|substate|xche_wxjd|xche_jdrq$");
                        yuyue.setYuyue_state(1);
                        yuyue.setYuyue_progress("已进店");
                        yuyue.setYuyue_IsImpt(1);
                        yuyue.setWork_no(num);
                        yuyue.setYuyue_sjjcrq(new Date());
                        dao.update(yuyue, "^yuyue_state|yuyue_progress|yuyue_IsImpt|work_no|yuyue_sjjcrq$");
                        // 更新详情表
                        Sql sql_ = Sqls.create("select yuyue_no,a.peij_no,a.peij_mc,a.peij_dw,a.peij_jk,a.peij_th,a.peij_cx,a.peij_pp,a.peij_cd,a.peij_bz,peij_sl,peij_dj,peij_je,Peij_zt,b.peij_tcfs ,b.peij_tc   from work_yuyue_ll a ,kucshp_info b  where a.yuyue_no = '"
                                + yuyue_no + "'and a.peij_no=b.peij_no");
                        sql_.setCallback(new SqlCallback() {
                            public Object invoke(Connection conn, java.sql.ResultSet rs, Sql sql) throws SQLException {
                                List<Work_ll_gzEntity> list = new LinkedList<Work_ll_gzEntity>();
                                while (rs.next()) {
                                    Work_ll_gzEntity ll = new Work_ll_gzEntity();
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
                        // 更新详细表
                        Sql sql_2 = Sqls.create("select a.wxxm_no,a.wxxm_mc,a.wxxm_gs,a.wxxm_dj,a.wxxm_je,a.wxxm_khgs,wxxm_zt,a.wxxm_bz,'正常',b.wxxm_tcfs,b.wxxm_tc from work_yuyue_wxxm a ,work_weixiu_sm b where a.yuyue_no = '"
                                + yuyue_no + "' and a.wxxm_no= b.wxxm_no ");
                        sql_2.setCallback(new SqlCallback() {
                            public Object invoke(Connection conn, java.sql.ResultSet rs, Sql sql) throws SQLException {
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
                        List<Work_mx_gzEntity> list_mx = sql_2.getList(Work_mx_gzEntity.class);
                        for (Work_mx_gzEntity llgz : list_mx) {
                            llgz.setWork_no(num);
                            dao.insert(llgz);
                        }
                        dao.execute(Sqls.create("update work_pz_gz set xche_wxsj = isnull(DATEDIFF(minute, xche_jdrq, xche_yjwgrq),0) where work_no='" + num + "'"));
                        dao.execute(Sqls.create("update work_mx_gz set wxxm_jd='未派工' where work_no='" + num + "'"));
                        dao.execute(Sqls.create("update work_baojia_pz set list_progress='已进店' where list_no='" + yuyue_no + "'"));
                        dao.execute(Sqls.create("update work_cheliang_sm " +
                                "set che_rjlc = (xche_lc-xche_last_lc)/ case isnull(convert(numeric(18,2), xche_jdrq - xche_last_jdrq),0) when 0 then 1 else isnull(convert(numeric(18,2), xche_jdrq - xche_last_jdrq),0) end " +
                                "from work_cheliang_sm a,work_pz_gz b where a.che_no = b.che_no and a.che_no='" + yuyue.getChe_no() + "' and work_no='" + num + "'"));
                        BsdUtils.updateWorkPzGz(dao, num);
                    }
                });
            } catch (Exception e) {
                return jsons.json(1, 1, 0, e.getMessage());
            }
            String json = Json.toJson(pz_new[0], JsonFormat.full());
            return jsons.json(1, 1, 1, json);
		} else {
			return jsons.json(1, 1, 0, "进厂失败，订单不存在");
		}
	}

    @At
    @Ok("raw:json")
	public String deleteWxxm(String yuyue_no, String wxxm_no) {
        dao.execute(Sqls.create("delete from work_yuyue_wxxm where yuyue_no='" + yuyue_no + "' and wxxm_no='" + wxxm_no + "'"));
        return jsons.json(1, 1, 1, "删除成功");
    }

    @At
    @Ok("raw:json")
    public String updateXmJe(String yuyue_no, String wxxm_no, String ygsf) {
        dao.execute(Sqls.create("update work_yuyue_wxxm set wxxm_yje=" + ygsf + ",wxxm_je=" + ygsf + ",wxxm_dj=" + ygsf + "/(case wxxm_gs when 0 then 1 else wxxm_gs end) " +
                "where yuyue_no='" + yuyue_no + "' and wxxm_no='" + wxxm_no + "'"));
        return "success";
    }

    @At
    @Ok("raw:json")
    public String deleteWxcl(String yuyue_no, String peij_no) {
        dao.execute(Sqls.create("delete from work_yuyue_ll where yuyue_no='" + yuyue_no + "' and peij_no='" + peij_no + "'"));
        return jsons.json(1, 1, 1, "删除成功");
    }

    @At
    @Ok("raw:json")
    public String updateClSl(String yuyue_no, String peij_no, String sl) {
        dao.execute(Sqls.create("update work_yuyue_ll set peij_sl=" + sl + ",peij_je=peij_dj*" + sl + ",peij_cbje=peij_cb*" + sl
                + " where yuyue_no='" + yuyue_no + "' and peij_no='" + peij_no + "'"));
        return "success";
    }

    @At
    @Ok("raw:json")
    public String updateClDj(String yuyue_no, String peij_no, String ydj) {
        dao.execute(Sqls.create("update work_yuyue_ll set peij_dj=" + ydj + ",peij_je=peij_sl*" + ydj
                + " where yuyue_no='" + yuyue_no + "' and peij_no='" + peij_no + "'"));
        return "success";
    }
}
