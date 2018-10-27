package org.aotu.paigong.module;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.aotu.Jsons;
import org.aotu.order.entity.Work_ll_gzEntity;
import org.aotu.order.entity.Work_mx_gzEntity;
import org.aotu.order.entity.Work_pz_gzEntity;
import org.aotu.paigong.entity.WorkPgGz;
import org.aotu.publics.eneity.Sm_app_defpriceEntity;
import org.aotu.publics.eneity.Work_cheliang_smEntity;
import org.nutz.dao.Cnd;
import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/paigong")
public class PaiGongModule {
	@Inject
	Dao dao;

	@Inject
	Jsons jsons;

	/**
	 * 获取派工列表
	 * 
	 * @param che_no
	 * @return
	 * 
	 */
	@At
	@Ok("raw:json")
	public String paiGongList(String che_no, String kehu_mc, String work_no,int pageNumber,String gongsino) {
		Pager pager = dao.createPager(pageNumber, 20);
		Cnd cnd = Cnd.where("mainstate", ">=", "0").and("mainstate", "<=", "1").and("flag_fast", "=", "0");
		//判断公司编码不等于空进入下面判断@时间2017-9-23 10:16:16
		if(gongsino!=null&&gongsino.length()>0){
			cnd.and("GongSiNo", "=", ""+gongsino+"");
		}
		System.out.println("kehu_mc=========="+kehu_mc);
		if (che_no != null && che_no.length() > 0) {
			cnd.and("che_no", "like", "%" + che_no + "%");
		}
		if (kehu_mc != null && kehu_mc.length() > 0) {
			cnd.and("kehu_mc", "like", "%" + kehu_mc + "%");
		}
		if (work_no != null && work_no.length() > 0) {
			cnd.and("work_no", "like", "%" + work_no+ "%");
		}
		List<Work_pz_gzEntity> list = dao.query(Work_pz_gzEntity.class,
				cnd.orderBy("xche_jdrq", "desc"),pager);
		String json = Json.toJson(list, JsonFormat.full());
		//没有判断
		return jsons.json(1, list.size(), 1, json);
	}

	@At
	@Ok("raw:json")
	public String getListInfoByNo(String work_no) {
        Cnd cnd = Cnd.where("mainstate", ">=", "0").and("mainstate", "<=", "1").and("flag_fast", "=", "0")
            .and("work_no", "=", work_no);
		Work_pz_gzEntity workPzGzEntity = dao.fetch(Work_pz_gzEntity.class, work_no);
		if (workPzGzEntity != null) {
            if (workPzGzEntity.getMainstate() == 0) {
                String json = Json.toJson(workPzGzEntity, JsonFormat.full());
                return jsons.json(1, 1, 1, json);
            } else {
                return jsons.json(0, 1, 1, workPzGzEntity.getMainstate() + "");
            }
        } else {
            return jsons.json(1, 0, 0, "不存在");
        }
	}

	/**
	 * 根据订单编号和维修项目编号获取派工人员信息
	 * 
	 * @param work_no
	 * @param wxxm_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String getPgxxByWxxm(String work_no, String wxxm_no) {
		List list = dao.query(WorkPgGz.class, Cnd
				.where("work_no", "=", work_no).and("wxxm_no", "=", wxxm_no));
		String json = Json.toJson(list, JsonFormat.full());
		//没有判断
		return jsons.json(1, list.size(), 1, json);
	}

	int jg;

	/**
	 * @param work_no 维修单号
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String wangGong(final String work_no) {
        Sql sql1 = Sqls
                .queryRecord("select count(*) as cnt  from work_ckpz_gz where work_no= '" + work_no + "'");
        dao.execute(sql1);
        List<Record> res1 = sql1.getList(Record.class);
        int cnt = res1.get(0).getInt("cnt");
        if (cnt > 0)
            return jsons.json(1, 1, 0, "不能完工，有未出库的用料");
        Sql sql2 = Sqls
                .queryRecord("select count(*) as cnt  from work_ll_gz where isnull(flag_chuku,0)=0 and isnull(flag_xz,0)=0 and work_no= '"
                        + work_no + "'");
        dao.execute(sql2);
        List<Record> res2 = sql2.getList(Record.class);
        cnt = res2.get(0).getInt("cnt");
        if (cnt > 0)
            return jsons.json(1, 1, 0, "不能完工，有未领料配件");
        dao.run(new ConnCallback() {
            @Override
            public void invoke(java.sql.Connection conn) throws Exception {
                Sql sql3 = Sqls
                        .queryRecord("select card_no from work_pz_gz where work_no = '" + work_no + "'");
                dao.execute(sql3);
                List<Record> res3 = sql3.getList(Record.class);
                String itemrate = "1";
                String peijrate = "1";
                if (res3 != null && !res3.equals("") && res3.size() > 0) {
                    String card_no = res3.get(0).getString("card_no");
                    Sql sql4 = Sqls
                            .queryRecord("select itemrate,peijrate from cardkind  where cardkind = (select card_kind  from kehu_card where card_no = '"
                                    + card_no + "' ) ");
                    dao.execute(sql4);
                    List<Record> res4 = sql4.getList(Record.class);
                    if (res4.size() > 0) {
                        itemrate = res4.get(0).getString("itemrate");
                        peijrate = res4.get(0).getString("peijrate");
                    }
                }
                CallableStatement cs = conn
                        .prepareCall("{call Wx_PaiGong (?,?,?)}");
                cs.setString(1, work_no);
                cs.setString(2, itemrate);
                cs.setString(3, peijrate);
                cs.execute();
            }
        });
        return jsons.json(1, 1, 1, "成功");
	}

	/**
	 * @param work_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String stop(final String work_no) {
		//没有判断work_no
		Sql sql1 = Sqls
				.queryRecord("select count(*) as cnt  from work_ckmx_sj mx,work_ckpz_sj pz where mx.ling_no=pz.ling_no and pz.work_no= '"
						+ work_no + "'");
		dao.execute(sql1);
		List<Record> res1 = sql1.getList(Record.class);
		int cnt = res1.get(0).getInt("cnt");
		if (cnt > 0)
			return jsons.json(1, 1, 0, "不能终止，已经领料不能作废,请走空单子。");

		Sql sql2 = Sqls
				.queryRecord("select count(*) as cnt  from work_ckpz_gz where work_no= '"
						+ work_no + "'");
		dao.execute(sql2);
		List<Record> res2 = sql2.getList(Record.class);
		cnt = res2.get(0).getInt("cnt");
		if (cnt > 0)
			return jsons.json(1, 1, 0, "不能终止，有领料单尚未出库,请先作废领料单。");

		dao.run(new ConnCallback() {
			@Override
			public void invoke(java.sql.Connection conn) throws Exception {
				CallableStatement cs = conn
						.prepareCall("{call Wx_Del_DengJi (?)}");
				cs.setString(1, work_no);
				cs.execute();
			}
		});

		return jsons.json(1, 1, 1, "终止成功");
	}

	/**
	 * @param work_no
	 *            维修单号
	 * @param caozuoyuan
	 *            操作员编号
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String print(String work_no, String caozuoyuan) {
		Sql sql2 = Sqls
				.queryRecord("select printpc from PrintServerset where billkind = '维修派工单'");
		dao.execute(sql2);
		List<Record> res2 = sql2.getList(Record.class);
		if (res2.size() > 0) {
			String printpc = res2.get(0).getString("printpc");
			Sql sql1 = Sqls
					.queryRecord("insert into PrintServerLog(printdate,billkind,billno,printczy,printpc,printsource,flag_print ) values (getdate(),'维修派工单','"
							+ work_no
							+ "','"
							+ caozuoyuan
							+ "','"
							+ printpc
							+ "',1,0)");
			dao.execute(sql1);
			return jsons.json(1, 1, 1, "打印成功");
		}
		return jsons.json(1, 1, 0, "维修派工单没有找到！");
	}

	/**
	 * 全部派工，参数为一个派工实体类
	 * 
	 * @param pg
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String saveAllPgxx(@Param("..") WorkPgGz pg) {
		List<Work_mx_gzEntity> list = dao.query(Work_mx_gzEntity.class,
				Cnd.where("work_no", "=", pg.getWork_no()));
		for (Work_mx_gzEntity xm : list) {
			WorkPgGz _pg = new WorkPgGz();
			_pg.setWork_no(xm.getWork_no());// //单号

			_pg.setWxxm_no(xm.getWxxm_no());// //维修项目编号
			_pg.setReny_no(pg.getReny_no());// //人员编号
			_pg.setReny_mc(pg.getReny_mc());// //人员名称
			_pg.setWxry_bm(pg.getWxry_bm());// //人员部门
			_pg.setWxry_cj(pg.getWxry_cj());// //车间
			_pg.setWxry_bz(pg.getWxry_bz());// //班组
			_pg.setPaig_khgs(pg.getPaig_khgs());// //考核工时
			_pg.setPaig_khje(pg.getPaig_khje());// //考核金额
			_pg.setPaig_pgsj(new Date());// 派工时间
			_pg.setPaig_bz("");// 派工备注
			_pg.setWxry_pg("1");// 是否派工
			WorkPgGz pp = dao.fetch(
					WorkPgGz.class,
					Cnd.where("wxxm_no", "=", xm.getWxxm_no())
							.and("work_no", "=", xm.getWork_no())
							.and("reny_no", "=", pg.getReny_no()));
			if (pp == null) {
				List<WorkPgGz> list2 = dao.query(
						WorkPgGz.class,
						Cnd.where("wxxm_no", "=", xm.getWxxm_no())
								.and("work_no", "=", xm.getWork_no()));
				System.out.println("===================================="+list2.size());
				if(list2.size()==0){
					Work_mx_gzEntity mx = dao.fetch(Work_mx_gzEntity.class,Cnd.where("work_no", "=", xm.getWork_no()).and("wxxm_no", "=",  xm.getWxxm_no()));
					if(mx!=null ){
					_pg.setPaig_khgs(mx.getWxxm_khgs());
					_pg.setPaig_khje(mx.getWxxm_je());
					}
				}
				_pg.setPaig_pgsj(new Date());// 派工时间
				_pg.setPaig_bz("");// 派工备注
				_pg.setWxry_pg("1");// 是否派工				
				dao.insert(_pg);
			} else {
				pp.setWxxm_no(xm.getWxxm_no());// //维修项目编号
				pp.setReny_no(pg.getReny_no());// //人员编号
				pp.setReny_mc(pg.getReny_mc());// //人员名称
				pp.setWxry_bm(pg.getWxry_bm());// //人员部门
				pp.setWxry_cj(pg.getWxry_cj());// //车间
				pp.setWxry_bz(pg.getWxry_bz());// //班组
				pp.setPaig_khgs(pg.getPaig_khgs());// //考核工时
				pp.setPaig_khje(pg.getPaig_khje());// //考核金额
				pp.setPaig_pgsj(new Date());// 派工时间
				pp.setPaig_bz("");// 派工备注
				pp.setWxry_pg("1");// 是否派工
				dao.update(pp);
			}
		}
		return jsons.json(1, list.size(), 1, "成功");
	}

	/**
	 * @param id
	 * @param je
	 * @param gs
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String savePgJgAndJe(int id, double je, double gs) {
		WorkPgGz pp = dao.fetch(WorkPgGz.class, id);
		pp.setPaig_khgs(gs);
		pp.setPaig_khje(je);
		dao.update(pp);
		return jsons.json(1, 1, 1, "成功");
	}
	
	/**
	 * @param pg
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String savePgxx(@Param("..") WorkPgGz pg) {
		WorkPgGz pp = dao.fetch(
				WorkPgGz.class,
				Cnd.where("wxxm_no", "=", pg.getWxxm_no())
						.and("work_no", "=", pg.getWork_no())
						.and("reny_no", "=", pg.getReny_no()));

		if (pp == null) {
			List<WorkPgGz> list = dao.query(
					WorkPgGz.class,
					Cnd.where("wxxm_no", "=", pg.getWxxm_no())
							.and("work_no", "=", pg.getWork_no()));
			System.out.println("===================================="+list.size());
			if(list.size()==0){
				Work_mx_gzEntity mx = dao.fetch(Work_mx_gzEntity.class,Cnd.where("work_no", "=", pg.getWork_no()).and("wxxm_no", "=",  pg.getWxxm_no()));
				if(mx!=null ){
					pg.setPaig_khgs(mx.getWxxm_khgs());
					pg.setPaig_khje(mx.getWxxm_je());
				}
			}
			pg.setPaig_pgsj(new Date());// 派工时间
			pg.setPaig_bz("");// 派工备注
			pg.setWxry_pg("1");// 是否派工
			dao.insert(pg);
		} else {
			pp.setWxxm_no(pg.getWxxm_no());// //维修项目编号
			pp.setReny_no(pg.getReny_no());// //人员编号
			pp.setReny_mc(pg.getReny_mc());// //人员名称
			pp.setWxry_bm(pg.getWxry_bm());// //人员部门
			pp.setWxry_cj(pg.getWxry_cj());// //车间
			pp.setWxry_bz(pg.getWxry_bz());// //班组
			pp.setPaig_khgs(pg.getPaig_khgs());// //考核工时
			pp.setPaig_khje(pg.getPaig_khje());// //考核金额
			dao.update(pp);
		}
		return jsons.json(1, 1, 1, "成功");
	}

	/**
	 * 根据派工项目，进行删除操作
	 * 
	 * @param xxNo
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String delPgxx(Integer xxNo) {
		WorkPgGz pg = dao.fetch(WorkPgGz.class, xxNo);
		dao.delete(WorkPgGz.class, xxNo);
		return jsons.json(1, 1, 1, "删除成功");
	}
	/**
	 * 删除所有派工
	 * @param work_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String delPGAllRx(String work_no){
		dao.delete(WorkPgGz.class, work_no);
		return jsons.json(1, 1, 1, "删除成功");
	}

	@At
	@Ok("raw:json")
	public String diaoduDelwxxm(String work_no, String wxxm_no) {
		Sql sql1_del = Sqls
				.create("delete from work_mx_gz where work_no='" + work_no + "' and wxxm_no='" + wxxm_no + "'");
		dao.execute(sql1_del);
		Sql sql1_0 = Sqls
				.create("delete from work_pg_gz where work_no='" + work_no + "' and wxxm_no='" + wxxm_no + "'");
		dao.execute(sql1_0);	
		////////////////////合计金额计算///////////////////////////////////
		Sql sql1_1 = Sqls
				.create("update b set b.xche_rgf = a.xche_rgf,b.xche_rgbh=a.xche_rgf,b.xche_rgsl=0,xche_rgse=0 from work_pz_gz b,(select work_no,sum(wxxm_je) xche_rgf from work_mx_gz where work_no='"+work_no+"' and wxxm_zt in ('正常','保险') group by work_no) a   where b.work_no = '"+work_no+"' and a.work_no = b.work_no");
		dao.execute(sql1_1);			
		Sql sql1_2 = Sqls
				.create("update b set b.xche_clf = a.xche_clf,b.xche_clbh=a.xche_clf,b.xche_clsl=0,xche_clse=0 from work_pz_gz b,(select work_no,sum(peij_je) xche_clf from work_ll_gz where work_no='"+work_no+"' and peij_zt in ('正常','保险')group by work_no ) a   where b.work_no = '"+work_no+"' and a.work_no = b.work_no");
		dao.execute(sql1_2);
		Sql sql1_3 = Sqls
				.create("update work_pz_gz set xche_yhje = isnull(xche_wxxm_yhje,0)+isnull(xche_peij_yhje,0) where work_no = '"+work_no+"'");
		dao.execute(sql1_3);
		Sql sql1_4 = Sqls
				.create("update work_pz_gz set xche_hjje= isnull(xche_rgf,0) + isnull(xche_clf,0) where work_no = '"+work_no+"'");
		dao.execute(sql1_4);
		///////////////////////////////////////////////////////////////
		return jsons.json(1, 1, 1, "删除成功");
	}

	@At
	@Ok("raw:json")
	public String diaoduDelcl(String work_no, String peij_no) {
		Sql sql1_del = Sqls
				.create("delete from work_ll_gz where work_no='" + work_no + "' and peij_no='" + peij_no + "'");
		dao.execute(sql1_del);
		////////////////////合计金额计算///////////////////////////////////
		Sql sql1_1 = Sqls
				.create("update b set b.xche_rgf = a.xche_rgf,b.xche_rgbh=a.xche_rgf,b.xche_rgsl=0,xche_rgse=0 from work_pz_gz b,(select work_no,sum(wxxm_je) xche_rgf from work_mx_gz where work_no='"+work_no+"' and wxxm_zt in ('正常','保险') group by work_no) a   where b.work_no = '"+work_no+"' and a.work_no = b.work_no");
		dao.execute(sql1_1);			
		Sql sql1_2 = Sqls
				.create("update b set b.xche_clf = a.xche_clf,b.xche_clbh=a.xche_clf,b.xche_clsl=0,xche_clse=0 from work_pz_gz b,(select work_no,sum(peij_je) xche_clf from work_ll_gz where work_no='"+work_no+"' and peij_zt in ('正常','保险')group by work_no ) a   where b.work_no = '"+work_no+"' and a.work_no = b.work_no");
		dao.execute(sql1_2);
		Sql sql1_3 = Sqls
				.create("update work_pz_gz set xche_yhje = isnull(xche_wxxm_yhje,0)+isnull(xche_peij_yhje,0) where work_no = '"+work_no+"'");
		dao.execute(sql1_3);
		Sql sql1_4 = Sqls
				.create("update work_pz_gz set xche_hjje= isnull(xche_rgf,0) + isnull(xche_clf,0) where work_no = '"+work_no+"'");
		dao.execute(sql1_4);
		///////////////////////////////////////////////////////////////
		return jsons.json(1, 1, 1, "删除成功");
	}

	/**
	 * @param id
	 * @param jg
	 * @param sl
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String editYl(int id, double jg, double sl) {
		Work_ll_gzEntity ll = dao.fetch(Work_ll_gzEntity.class, id);
		ll.setPeij_ydj(jg);
		ll.setPeij_sl(sl);
		dao.update(ll);
		return jsons.json(1, 1, 1, "删除成功");
	}
	/**
	 * @param id
	 * @param jg
	 * @param sl
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String editYl_zcduwxylxgdj(int id, double jg, double sl) {
		Work_ll_gzEntity ll = dao.fetch(Work_ll_gzEntity.class, id);
		ll.setPeij_dj(jg);
		ll.setPeij_sl(sl);
		dao.update(ll);
		return jsons.json(1, 1, 1, "删除成功");
	}

	/**
	 * @param sl
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String editYl_mrkx(String work_no, String peij_no, double sl, double zk) {
        Sql sql1 = Sqls
                .create("update work_ll_gz set peij_sl=" + sl + ",peij_dj=peij_ydj*" + zk + ",peij_je=peij_ydj*" + sl*zk + ",peij_yje=peij_ydj*" + sl + " where work_no='" + work_no + "' and peij_no='" + peij_no + "'");
        dao.execute(sql1);
		return jsons.json(1, 1, 1, "删除成功");
	}

    @At
    @Ok("raw:json")
    public String updatePeijYdj(String work_no, String peij_no, double ydj, double zk) {
        Sql sql1 = Sqls
                .create("update work_ll_gz set peij_ydj=" + ydj + ",peij_yje=peij_sl*" + ydj + ",peij_dj=" + ydj*zk + ",peij_je=peij_sl*" + ydj*zk + " where work_no='" + work_no + "' and peij_no='" + peij_no + "'");
        dao.execute(sql1);
        return jsons.json(1, 1, 1, "删除成功");
    }

    /**
     *
     * @param work_no
     * @param wxxm_no
     * @param jg         原工时费
     * @param hyzk       会员折扣
     * @return
     */
	@At
	@Ok("raw:json")
	public String editWxxmByhyzk(String work_no, String wxxm_no, double jg, double hyzk) {
        Work_mx_gzEntity entity = dao.fetch(Work_mx_gzEntity.class, Cnd.where("work_no", "=", work_no).and("wxxm_no", "=", wxxm_no));
        if (entity != null) {
            Sql sql1 = null;
            if (entity.getWxxm_gs() == 0) {
                sql1 = Sqls
                        .create("update work_mx_gz set wxxm_yje=" + jg + ",wxxm_je=" + jg*hyzk + ",wxxm_dj=" + jg + " where work_no='" + work_no + "' and wxxm_no='" + wxxm_no + "'");
            } else {
                sql1 = Sqls
                        .create("update work_mx_gz set wxxm_yje=" + jg + ",wxxm_je=" + jg*hyzk + ",wxxm_dj=" + jg*hyzk + "/wxxm_gs where work_no='" + work_no + "' and wxxm_no='" + wxxm_no + "'");
            }
            dao.execute(sql1);
            return jsons.json(1, 1, 1, "修改成功");
        }
        return jsons.json(1, 0, 0, "修改失败");
	}
	
	/**
	 * @param id
	 * @param jg
	 * @param gs
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String editWxxm(int id, double jg, double gs,String wxxm_mc) {
		Work_mx_gzEntity mx = dao.fetch(Work_mx_gzEntity.class, id);
		mx.setWxxm_je(jg);
		mx.setWxxm_gs(gs);
		//更新考核工时
		mx.setWxxm_khgs(gs);
		//新加参数wxxm_mc修改维修项目名称
		mx.setWxxm_mc(wxxm_mc);
		dao.update(mx);
		return jsons.json(1, 1, 1, "修改成功");
	}

    /**
     * 全部派工，参数为一个派工实体类
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String paiGongAll(@Param("work_no") String work_no, @Param("reny_nos") String[] reny_nos) {
        List<Work_mx_gzEntity> workMxGzList = dao.query(Work_mx_gzEntity.class,
                Cnd.where("work_no", "=", work_no));
        for (int i = 0; i < reny_nos.length; i++) {
            for (Work_mx_gzEntity workMxGzEntity : workMxGzList) {
                // 先查看是否这个人员派工过这个项目
                WorkPgGz workPgGz = dao.fetch(WorkPgGz.class,
                    Cnd.where("wxxm_no", "=", workMxGzEntity.getWxxm_no())
                            .and("work_no", "=", work_no)
                            .and("reny_no", "=", reny_nos[i]));
                // 如果没有派工过就插入记录，有的话就不操作了
                if (workPgGz == null) {
                    WorkPgGz _workPgGz = new WorkPgGz();
                    // 判断这个项目是否被其他人派工过
                    List<WorkPgGz> list2 = dao.query(WorkPgGz.class,
                        Cnd.where("wxxm_no", "=", workMxGzEntity.getWxxm_no())
                                .and("work_no", "=", work_no));
                    // 如果没有派工过则，把工时和金额都分配这个人，否则工时和金额就是0
                    if (list2.size() == 0) {
                        _workPgGz.setPaig_khgs(workMxGzEntity.getWxxm_khgs());
                        _workPgGz.setPaig_khje(workMxGzEntity.getWxxm_je());
                    }
                    _workPgGz.setWork_no(work_no);
                    _workPgGz.setWxxm_no(workMxGzEntity.getWxxm_no());
                    _workPgGz.setReny_no(reny_nos[i]);
                    _workPgGz.setPaig_pgsj(new Date());// 派工时间
                    _workPgGz.setPaig_bz("");// 派工备注
                    _workPgGz.setWxry_pg("1");// 是否派工
                    dao.insert(_workPgGz);
                }
            }
        }
        Sql sql1_1 = Sqls
                .create("update work_pg_gz set reny_mc = reny_xm,wxry_bm = dept_mc from work_pg_gz a,gongzry b where reny_no = reny_dm and work_no='" + work_no + "' and isnull(reny_mc,'''')=''''");
        dao.execute(sql1_1);
        return "success";
    }

    /**
     * 全部派工，参数为一个派工实体类
     *
     * @return
     */
    @At
    @Ok("raw:json")
    public String paiGongSingle(@Param("work_no") String work_no, @Param("wxxm_no") String wxxm_no, @Param("reny_nos") String[] reny_nos) {
        for (int i = 0; i < reny_nos.length; i++) {
			WorkPgGz workPgGz = dao.fetch(WorkPgGz.class,
					Cnd.where("wxxm_no", "=", wxxm_no)
							.and("work_no", "=", work_no)
							.and("reny_no", "=", reny_nos[i]));
            if (workPgGz == null) {
                WorkPgGz _workPgGz = new WorkPgGz();
                List<WorkPgGz> list2 = dao.query(WorkPgGz.class,
                        Cnd.where("wxxm_no", "=", wxxm_no)
                                .and("work_no", "=", work_no));
                // 如果没有派工过则，把工时和金额都分配这个人，否则工时和金额就是0
                if (list2.size() == 0) {
                    List<Work_mx_gzEntity> workMxGzList = dao.query(Work_mx_gzEntity.class,
                            Cnd.where("work_no", "=", work_no)
                                    .and("wxxm_no", "=", wxxm_no));
                    _workPgGz.setPaig_khgs(workMxGzList.get(0).getWxxm_khgs());
                    _workPgGz.setPaig_khje(workMxGzList.get(0).getWxxm_je());
                }
                _workPgGz.setWork_no(work_no);
                _workPgGz.setWxxm_no(wxxm_no);
                _workPgGz.setReny_no(reny_nos[i]);
                _workPgGz.setPaig_pgsj(new Date());// 派工时间
                _workPgGz.setPaig_bz("");// 派工备注
                _workPgGz.setWxry_pg("1");// 是否派工
                dao.insert(_workPgGz);
            }
        }
        Sql sql1_1 = Sqls
                .create("update work_pg_gz set reny_mc = reny_xm,wxry_bm = dept_mc from work_pg_gz a,gongzry b where reny_no = reny_dm and work_no='" + work_no + "' and isnull(reny_mc,'''')='''' and wxxm_no='" + wxxm_no + "'");
        dao.execute(sql1_1);
        return "success";
    }

    /**
     * 修改维修项目名称
     * @param work_no
     * @param wxxm_no
     * @param wxxm_mc
     * @return
     */
	@At
	@Ok("raw:json")
	public String updateWxxmMc(String work_no, String wxxm_no,  String wxxm_mc) {
		Sql sql1 = Sqls
				.queryRecord("update work_mx_gz set wxxm_mc='" + wxxm_mc + "' where work_no='" + work_no + "' and wxxm_no='" + wxxm_no + "'");
		dao.execute(sql1);
		return jsons.json(1, 1, 1, "修改成功");
	}
}
