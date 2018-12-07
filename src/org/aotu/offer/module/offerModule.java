package org.aotu.offer.module;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.aotu.Jsons;
import org.aotu.offsetPager;
import org.aotu.appointment.entity.Work_yuyue_pzEntity;
import org.aotu.offer.entity.LLEntity;
import org.aotu.offer.entity.baoJiaEntity;
import org.aotu.offer.entity.feilvEntity;
import org.aotu.offer.entity.offerEntity;
import org.aotu.offer.entity.wxxmEntity;
import org.aotu.order.entity.Work_ll_gzEntity;
import org.aotu.order.entity.Work_mx_gzEntity;
import org.aotu.order.entity.Work_pz_gzEntity;
import org.aotu.publics.eneity.KehuEntity;
import org.aotu.publics.eneity.Work_cheliang_smEntity;
import org.aotu.publics.module.publicModule;
import org.aotu.util.BsdUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.trans.Atom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.aotu.order.module.ordersModule;
import org.nutz.trans.Trans;

/**
 * 
 * title:offerModuleJ
 * 
 * @Description:<描述>
 * @author Zhang Yalong
 * @date 2017-4-17 下午1:28:51
 * @version: V1.0
 */

@IocBean
@At("/offer")
public class offerModule {

	@Inject
	Dao dao;

	@Inject
	Jsons jsons;

	@Inject
	ordersModule ordersModule;
	
	@Inject
	publicModule pu;

	/**
	 * 快速报价
	 * 
	 * @param pai
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String story(String pai) {
		List<offerEntity> list = dao.query(offerEntity.class, null);
		String json = Json.toJson(list, JsonFormat.full());
		if (list.size() != 0) {
			return jsons.json(1, list.size(), 1, json);
		}
		return jsons.json(1, list.size(), 0, json);

	}

	private String getCatByPai(String pai) {
		Date date = new Date();
		short d = 1;
		Work_cheliang_smEntity res = dao.fetch(Work_cheliang_smEntity.class,
				Cnd.where("che_no", "=", pai));
		if (res == null) {
			res = new Work_cheliang_smEntity();
			res.setChe_no(pai);
			res.setKehu_no(pai);
			Work_cheliang_smEntity tr = dao.insert(res);
			KehuEntity ke = new KehuEntity();
			ke.setKehu_no(pai);
			ke.setLastModifyTime(date);
			ke.setKehu_xz(d);
			dao.insert(ke);
		}
		return res.getKehu_no();
	}

	/**
	 * 快速报价信息
	 * 
	 * @param pai
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String xinxi(String pai, String gongsiNo, String caozuoyuan_xm, String list_no) {
		Work_cheliang_smEntity che = dao.fetch(Work_cheliang_smEntity.class, pai);
        if (list_no != null && !"".equals(list_no)) {
            offerEntity offer = dao.fetch(offerEntity.class, list_no);
			if (offer != null) {
				offer.setGcsj(che.getChe_gcrq());
				String json = Json.toJson(offer, JsonFormat.full());
				return jsons.json(1, 1, 1, json);
			}
			return jsons.json(1, 1, 0, "此单已经不存在！");
		}
        return jsons.json(1, 1, 0, "单号不能为空！");
	}

	/**
	 * 项目
	 * 
	 * @param list_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String xiangm(String list_no) {
		List<wxxmEntity> list = dao.query(wxxmEntity.class,
				Cnd.where("list_no", "=", list_no));
		String json = Json.toJson(list, JsonFormat.full());
		if (list.size() != 0) {
			return jsons.json(1, list.size(), 1, json);
		}
		return jsons.json(1, list.size(), 0, json);

	}

	/**
	 * 材料
	 * 
	 * @param list_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String cail(String list_no) {
		List<LLEntity> list = dao.query(LLEntity.class,
				Cnd.where("list_no", "=", list_no));
		String json = Json.toJson(list, JsonFormat.full());
		if (list.size() != 0) {
			return jsons.json(1, list.size(), 1, json);
		}
		return jsons.json(1, list.size(), 0, json);
	}

	String number = null;

	@At
	@Ok("raw:json")
	public String jinchang(final String list_no, final String gongsiNo, final String caozuoyuan_xm) {
        final String[] num = new String[1];
        try {
            Trans.exec(new Atom() {
                @Override
                public void run() {
                    baoJiaEntity baoJia = dao.fetch(baoJiaEntity.class, list_no);
                    // 新建一个接待登记单号
                    try {
                        num[0] = ordersModule.add(gongsiNo, caozuoyuan_xm);
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                    Work_pz_gzEntity pz_new = new Work_pz_gzEntity();
                    pz_new.setWork_no(num[0]);
                    pz_new.setChe_no(baoJia.getChe_no());
                    pz_new.setKehu_no(baoJia.getKehu_no());
                    pz_new.setXche_jdrq(new Date());
                    pz_new.setYuyue_no(baoJia.getList_no());
                    pz_new.setKehu_no(baoJia.getKehu_no());
                    pz_new.setKehu_mc(baoJia.getKehu_mc());
                    pz_new.setKehu_xm(baoJia.getKehu_xm());
                    pz_new.setKehu_dz(baoJia.getKehu_dz());
                    pz_new.setKehu_dh(baoJia.getKehu_dh());
                    pz_new.setKehu_sj(baoJia.getKehu_sj());
                    pz_new.setChe_zjno(baoJia.getChe_zjno());
                    pz_new.setXche_gj(baoJia.getList_hjje());
                    pz_new.setKehu_yb(baoJia.getKehu_yb());
                    pz_new.setChe_vin(baoJia.getChe_vin());
                    pz_new.setChe_fd(baoJia.getChe_fd());
                    pz_new.setChe_cx(baoJia.getChe_cx());
                    pz_new.setXche_jcr(baoJia.getList_jcr());
                    pz_new.setXche_lc(Double.parseDouble(baoJia.getList_yjjclc()));
                    pz_new.setChe_wxys(baoJia.getChe_wxys());
                    pz_new.setXche_gdfl(baoJia.getList_gdfl());
                    pz_new.setXche_wxfl(baoJia.getList_wxfl());
                    pz_new.setXche_sfbz(baoJia.getList_sfbz());
                    pz_new.setXche_ywlx("报价转维修");
                    pz_new.setFlag_pad(true);
                    pz_new.setGongSiNo(baoJia.getGongSiNo());
                    pz_new.setGongSiMc(baoJia.getGongSiMc());
                    pz_new.setMainstate(0);
                    pz_new.setSubstate("未派车辆");
                    pz_new.setXche_wxjd("在修");
                    dao.update(pz_new, "^work_no|che_no|kehu_no|xche_jdrq|yuyue_no|kehu_no|kehu_mc|kehu_dz|kehu_dh|kehu_sj|che_zjno" +
							"|xche_gj|kehu_yb|che_vin|che_fd|che_cx|xche_jcr|xche_lc|setChe_wxys|xche_gdfl|xche_wxfl|xche_sfbz" +
                            "|xche_ywlx|flag_pad|GongSiNo|GongSiMc|mainstate|substate|xche_wxjd&");
                    // 更新领料
                    Sql sql_ = Sqls
                            .create("select list_no,a.peij_no,a.peij_mc,a.peij_dw,a.peij_jk,a.peij_th,a.peij_cx,a.peij_pp,a.peij_cd,a.peij_bz,peij_sl,peij_dj,peij_je,Peij_zt,b.peij_tcfs ,b.peij_tc from work_baojia_ll a ,kucshp_info b where a.list_no = '"
                                    + list_no + "'and a.peij_no=b.peij_no");
                    sql_.setCallback(new SqlCallback() {
                        public Object invoke(Connection conn, java.sql.ResultSet rs, Sql sql) throws SQLException {
                            List<Work_ll_gzEntity> list = new ArrayList<>();
                            while (rs.next()) {
                                Work_ll_gzEntity ll = new Work_ll_gzEntity();
                                ll.setWork_no(num[0]);
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
                    dao.insert(list);
                    // 更新维修项目
                    Sql sql_2 = Sqls
                            .create("select a.wxxm_no,a.wxxm_mc,a.wxxm_gs,a.wxxm_dj,a.wxxm_je,a.wxxm_khgs,wxxm_zt,a.wxxm_bz,b.wxxm_tcfs,b.wxxm_tc from work_baojia_wxxm a,work_weixiu_sm b where a.list_no = '"
                                    + list_no + "' and a.wxxm_no= b.wxxm_no ");
                    sql_2.setCallback(new SqlCallback() {
                        public Object invoke(Connection conn, java.sql.ResultSet rs, Sql sql) throws SQLException {
                            List<Work_mx_gzEntity> list_gz = new LinkedList<Work_mx_gzEntity>();
                            while (rs.next()) {
                                Work_mx_gzEntity mxgz = new Work_mx_gzEntity();
                                mxgz.setWork_no(num[0]);
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
                                mxgz.setWxxm_jd("未派工");
                                list_gz.add(mxgz);
                            }
                            return list_gz;
                        }
                    });
                    dao.execute(sql_2);
                    List<Work_mx_gzEntity> list_mx = sql_2.getList(Work_mx_gzEntity.class);
                    dao.insert(list_mx);
                    dao.execute(Sqls.create("update work_pz_gz set xche_wxsj=isnull(DATEDIFF(minute, xche_jdrq, xche_yjwgrq),0) where work_no='" + num[0] + "'"));
                    dao.execute(Sqls.create("update work_yuyue_pz set yuyue_progress='已进店',yuyue_sjjcrq=getdate() where yuyue_no='" + list_no + "'"));
                    baoJia.setList_IsImpt(1);
                    baoJia.setWork_no(num[0]);
                    baoJia.setList_progress("已进店");
                    dao.update(baoJia, "^List_IsImpt|work_no|List_progress$");
                    // 合计金额计算
                    BsdUtils.updateWorkPzGz(dao, num[0]);
                }
            });
        } catch (Exception e) {
            return jsons.json(1, 1, 0, e.getMessage());
        }
		return jsons.json(1, 1, 1, num[0]);
	}

	/**
	 * 创建新的报价单
	 * 
	 * @param gongsiNo
	 *            公司 no
	 * @param caozuoyuan_xm
	 *            操作员姓名
	 * @return boolean
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
						.prepareCall("{call sp_bslistnew (2021,?,?,?)}");
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

		Sql sql = Sqls.create("update work_BaoJia_pz set list_czy = @name,"
				+ " dept_mc = @dept," + "list_jlrq = getdate(),"
				+ "list_sfbz = @feil_mc," + "List_sffl = @feil_fl  $condition");
		sql.params().set("name", caozuoyuan_xm);
		sql.params().set("dept", gongsiNo);
		sql.params().set("feil_mc", mc);
		sql.params().set("feil_fl", fl);
		sql.params().set("list_no", number);
		sql.setCondition(Cnd.wrap("list_no='" + number + "'"));
		dao.execute(sql);
		int sd = sql.getUpdateCount();
		if (sd != 0) {
			// List<baoJiaEntity> result = dao.query(baoJiaEntity.class,
			// Cnd.where("list_no", "=", number));
			return number;
			// String json = Json.toJson(result, JsonFormat.full());
			// if (result.size() != 0) {
			// return jsons.json(1, result.size(), 1, json);
			// }
			// return jsons.json(1, result.size(), 0, json);
			// } else {
			// return jsons.json(1, 0, 0, "");
		}
		return "";
	}

	/**
	 * 添加项目
	 * 
	 * @param xm
	 * @return
	 */
	@At
	public String tjxm(@Param("..") wxxmEntity xm) {
		wxxmEntity res = dao.insert(xm);
		res.toString();
		if (res.toString() != null) {
			return "成功！";
		}
		return "失败";
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
			@Param("..") baoJiaEntity baojia) {
		String num = add(gongsiNo, caozuoyuan_xm);
		baojia.setList_no(num);
		int nu = dao.updateIgnoreNull(baojia);
		return nu;
	}

	/**
	 * 保存快速报价单
	 * 
	 * @param yuyue
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String addjb(@Param("..") final baoJiaEntity yuyue) {
	    try {
            Trans.exec(new Atom() {
                @Override
                public void run() {
                    baoJiaEntity bao = dao.fetch(baoJiaEntity.class, yuyue.getList_no());
                    bao.setChe_cx(yuyue.getChe_cx());
                    bao.setChe_vin(yuyue.getChe_vin());
                    bao.setList_lc(yuyue.getList_lc());
                    bao.setList_yjjclc(yuyue.getList_yjjclc());
                    bao.setKehu_mc(yuyue.getKehu_mc());
                    bao.setKehu_dh(yuyue.getKehu_dh());
                    bao.setList_sffl(yuyue.getList_sffl());
                    bao.setList_sfbz(yuyue.getList_sfbz());
                    bao.setList_hjje(yuyue.getList_hjje());
                    bao.setList_gj_wx(yuyue.getList_gj_wx());
                    bao.setList_gj_ll(yuyue.getList_gj_ll());
                    bao.setList_state(yuyue.getList_state());
                    bao.setList_progress(yuyue.getList_progress());
                    dao.update(bao,"^che_no|che_cx|che_vin|List_lc|kehu_mc|kehu_dh|List_sfbz|List_yjjclc|List_sffl|kehu_no|List_hjje|List_state|List_progress|List_gj_wx|List_gj_ll$");
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
	 * 添加维修项目
	 *
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String addwxxm(String json) {
		System.out.println("=============="+json);
		JsonParser parse = new JsonParser(); // 创建json解析器
		try {
			JsonObject js = (JsonObject) parse.parse(json); // 创建jsonObject对象
			JsonArray array = js.get("data").getAsJsonArray();
			for (int i = 0; i < array.size(); i++) {
				wxxmEntity offer = new wxxmEntity();
				JsonObject subObject = array.get(i).getAsJsonObject();
				String nu = subObject.get("list_no").getAsString();
				offer.setList_no(subObject.get("list_no").getAsString());
				if (i == 0) {
					wxxmEntity ks = dao.fetch(wxxmEntity.class,
							Cnd.where("list_no", "=", nu));
					if (ks != null) {
						// dao.query(Work_yuyue_wxxmEntity.class,
						// Cnd.where("yuyue_no","=",nu));
						dao.clear(wxxmEntity.class,
								Cnd.where("list_no", "=", nu));
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
				wxxmEntity dd = dao.insert(offer);
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
     * 添加新的维修项目
     * @param addLists
     * @return
     */
    @At
    @Ok("raw:json")
    public String addNewWxxm(String addLists) {
        JsonParser parse = new JsonParser(); // 创建json解析器
        try {
            String listNo = "";
            JsonArray array = (JsonArray) parse.parse(addLists); // 创建jsonObject对象
            for (int i = 0; i < array.size(); i++) {
                wxxmEntity offer = new wxxmEntity();
                JsonObject item = array.get(i).getAsJsonObject();
                listNo = item.get("list_no").getAsString();
                offer.setList_no(listNo);
                offer.setWxxm_yje(item.get("wxxm_je").getAsDouble());
                offer.setWxxm_no(item.get("wxxm_no").getAsString());
                offer.setWxxm_mc(item.get("wxxm_mc").getAsString());
                offer.setWxxm_gs(item.get("wxxm_gs").getAsDouble());
                offer.setWxxm_je(item.get("wxxm_je").getAsDouble());
                if (offer.getWxxm_gs() == 0) {
                    offer.setWxxm_dj(offer.getWxxm_je());
                } else {
                    offer.setWxxm_dj(offer.getWxxm_je() / offer.getWxxm_gs());
                }
                offer.setWxxm_zt(item.get("wxxm_zt").getAsString());
                offer.setWxxm_khgs(item.get("wxxm_gs").getAsDouble());
                wxxmEntity dd = dao.insert(offer);
                if (dd == null) {
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
				LLEntity yuyue = new LLEntity();
				JsonObject subObject = array.get(i).getAsJsonObject();
				String nu = subObject.get("list_no").getAsString();
				yuyue.setList_no(subObject.get("list_no").getAsString());
				if (i == 0) {
					LLEntity ks = dao.fetch(LLEntity.class,
							Cnd.where("list_no", "=", nu));
					if (ks != null) {
						dao.clear(LLEntity.class, Cnd.where("list_no", "=", nu));
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
				LLEntity dd = dao.insert(yuyue);
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
     * 添加材料
     *
     * @param addLists
     * @return
     */
    @At
    @Ok("raw:json")
    public String addNewCail(String addLists) {
        JsonParser parse = new JsonParser(); // 创建json解析器
        try {
            String listNo = "";
            JsonArray array = (JsonArray) parse.parse(addLists); // 创建jsonObject对象
            for (int i = 0; i < array.size(); i++) {
                LLEntity yuyue = new LLEntity();
                JsonObject item = array.get(i).getAsJsonObject();
                listNo = item.get("list_no").getAsString();
                String peijNo = item.get("peij_no").getAsString();
                LLEntity entity = dao.fetch(LLEntity.class, Cnd.where("list_no", "=", listNo).and("peij_no", "=", peijNo));
                if (entity == null) {
                    yuyue.setList_no(listNo);
                    yuyue.setPeij_no(item.get("peij_no").getAsString());// 配件编码
                    yuyue.setPeij_mc(item.get("peij_mc").getAsString());// 配件名称
                    yuyue.setPeij_sl(item.get("peij_sl").getAsDouble());// 配件数量
                    yuyue.setPeij_dj(item.get("peij_dj").getAsDouble());// 配件单价
                    yuyue.setPeij_zt(item.get("peij_zt").getAsString());// 配件状态
                    yuyue.setPeij_je(item.get("peij_je").getAsDouble());// 配件金额
                    yuyue.setPeij_th(item.get("peij_th").getAsString());// 配件图号
                    yuyue.setPeij_dw(item.get("peij_dw").getAsString());// 配件单位
                    LLEntity dd = dao.insert(yuyue);
                    if (dd == null) {
                        return "fail";
                    }
                } else {
                    if (entity.getPeij_sl() + item.get("peij_sl").getAsDouble() != 0) {
                        Sql sqlUpdate = Sqls
                                .create("update work_baojia_ll set peij_sl = peij_sl + " + item.get("peij_sl").getAsDouble() + ",peij_je = peij_je + " + item.get("peij_je").getAsDouble() + " where work_no='" + listNo + "' and peij_no ='" + peijNo + "'");
                        dao.execute(sqlUpdate);
                    } else {
                        Sql sqlDelete = Sqls
                                .create("delete from work_ll_gz where work_no='" + listNo + "' and peij_no ='" + peijNo + "'");
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
     * 删除维修项目
     * @param list_no
     * @param wxxm_no
     * @return
     */
	@At
	@Ok("raw:json")
	public String deleteWxxm(String list_no, String wxxm_no) {
		Sql sql1_del = Sqls
				.create("delete from work_baojia_wxxm where list_no='" + list_no + "' and wxxm_no='" + wxxm_no + "'");
		dao.execute(sql1_del);
		return jsons.json(1, 1, 1, "删除成功");
	}


    @At
    @Ok("raw:json")
    public String updateWxxmGs(String list_no, String wxxm_no, double gs) {
        Sql sql1 = Sqls
                .create("update work_baojia_wxxm set wxxm_gs=" + gs + ",wxxm_je=wxxm_dj*" + gs + " where list_no='" + list_no + "' and wxxm_no='" + wxxm_no + "'");
        dao.execute(sql1);
        return jsons.json(1, 1, 1, "修改成功");
    }

    @At
    @Ok("raw:json")
    public String updateWxxmJe(String list_no, String wxxm_no, double je) {
        wxxmEntity entity = dao.fetch(wxxmEntity.class, Cnd.where("list_no", "=", list_no).and("wxxm_no", "=", wxxm_no));
        if (entity != null) {
            Sql sql1 = null;
            if (entity.getWxxm_gs() == 0) {
                sql1 = Sqls
                        .create("update work_baojia_wxxm set wxxm_je=" + je + ",wxxm_dj=" + je + " where list_no='" + list_no + "' and wxxm_no='" + wxxm_no + "'");
            } else {
                sql1 = Sqls
                        .create("update work_baojia_wxxm set wxxm_je=" + je + ",wxxm_dj=" + je + "/wxxm_gs where list_no='" + list_no + "' and wxxm_no='" + wxxm_no + "'");
            }
            dao.execute(sql1);
            return jsons.json(1, 1, 1, "修改成功");
        }
        return jsons.json(1, 0, 0, "修改失败");
    }

	@At
	@Ok("raw:json")
	public String deleteWxcl(String list_no, String peij_no) {
		Sql sql1_del = Sqls
				.create("delete from work_baojia_ll where list_no='" + list_no + "' and peij_no='" + peij_no + "'");
		dao.execute(sql1_del);
		return jsons.json(1, 1, 1, "删除成功");
	}

    @At
    @Ok("raw:json")
    public String updateWxclSl(String list_no, String peij_no, double sl) {
        Sql sql1 = Sqls
                .create("update work_baojia_ll set peij_sl=" + sl + ",peij_je=peij_dj*" + sl + " where list_no='" + list_no + "' and peij_no='" + peij_no + "'");
        dao.execute(sql1);
        return jsons.json(1, 1, 1, "修改成功");
    }

    @At
    @Ok("raw:json")
    public String updateWxclDj(String list_no, String peij_no, double dj) {
        Sql sql1 = Sqls
                .create("update work_baojia_ll set peij_dj=" + dj + ",peij_je=peij_sl*" + dj + " where list_no='" + list_no + "' and peij_no='" + peij_no + "'");
        dao.execute(sql1);
        return jsons.json(1, 1, 1, "修改成功");
    }

}
