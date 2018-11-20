package org.aotu.VIPcard.module;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aotu.Jsons;
import org.aotu.VIPcard.entity.CardKindEntity;
import org.aotu.VIPcard.entity.CardKindPeij;
import org.aotu.VIPcard.entity.Cardservice;
import org.aotu.VIPcard.entity.KehuCardCheEntity;
import org.aotu.VIPcard.entity.KehuCarddetail;
import org.aotu.VIPcard.entity.KehuCarddetailpeij;
import org.aotu.VIPcard.entity.Kehu_CardEntity;
import org.aotu.lswx.entity.Work_pz_sjEntity;
import org.aotu.order.entity.Work_pz_gzEntity;
import org.aotu.publics.eneity.KehuEntity;
import org.aotu.publics.eneity.Work_cheliang_smEntity;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.random.StringGenerator;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/card")
public class card {
	@Inject
	Dao dao;

	@Inject
	Jsons jsons;

    /**
     * 已收款会员
     * @param pageNumber
     * @param card_no
     * @param cardkind
     * @return
     */
	@At
	@Ok("raw:json")
	public String CardXxy(int pageNumber, String card_no, String cardkind) {
		Pager pager = dao.createPager(pageNumber, 20);
		Cnd cnd = Cnd.where("flag_shoukuan", "=", "1");
		if (card_no != null && card_no.length() > 0){
			SqlExpressionGroup c = Cnd.exps("card_no", "like", "%" + card_no + "%").or("kehu_sj", "like", "%" + card_no + "%");
			cnd.and(c);
		}
		if (cardkind != null && cardkind.length() > 0) {
			cnd.and("card_kind", "like", "%" + cardkind + "%");
		}
        List<Kehu_CardEntity> result = dao.query(Kehu_CardEntity.class, cnd.desc("id"), pager);
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}

    /**
     * 未收款会员
     * @param pageNumber
     * @param card_no
     * @param cardkind
     * @return
     */
	@At
	@Ok("raw:json")
	public String CardXxn(int pageNumber, String card_no, String cardkind) {
		Pager pager = dao.createPager(pageNumber, 20);
		Cnd cnd = Cnd.where("flag_shoukuan", "=", "0");
		if (card_no != null  && card_no.length() > 0){
			SqlExpressionGroup c = Cnd.exps("card_no", "like", "%" + card_no + "%").or("kehu_sj", "like", "%" + card_no + "%");
			cnd.and(c);
		}
		if (cardkind != null && cardkind.length() > 0) {
            cnd.and("card_kind", "like", "%" + cardkind + "%");
        }
        List<Kehu_CardEntity> result = dao.query(Kehu_CardEntity.class, cnd.desc("id"), pager);
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}

	public Kehu_CardEntity getVipCard(String card_no, String pass) {
		return dao.fetch(
				Kehu_CardEntity.class,
				Cnd.where("card_no", "=", card_no).and("card_password", "=",
						pass));
	}

	public Kehu_CardEntity getVipCard(String card_no) {
		return dao.fetch(Kehu_CardEntity.class, card_no);
	}
	/**
	 * 获取开卡金额 本次消费金额 会员卡余额和剩余卡次数
	 * 
	 * @param card_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String getVipCardInfo(String card_no,String work_no) {
		Map<String, Object> map= new HashMap<String, Object>();
		if(card_no != null && card_no != ""){
			Sql sql1 = Sqls
					.queryRecord("select * from kehu_carddetail where card_no='"+card_no+"'");
			dao.execute(sql1);
			List<Record> tsxm = sql1.getList(Record.class);
			
			Sql sql2 = Sqls
					.queryRecord("select * from kehu_carddetailpeij where card_no='"+card_no+"'");
			dao.execute(sql2);
			List<Record> tspj = sql2.getList(Record.class);
			for (int i = 0; i < tspj.size(); i++) {
				if(tspj.get(i).getString("peij_card_yqsl").equals("")||tspj.get(i).getString("peij_card_yqsl")==null){
					tspj.get(i).set("peij_card_yqsl", 0);
				}
				System.out.println(tspj.get(i).getString("peij_card_sl"));
				if(tspj.get(i).getString("peij_card_sl")==null){
					tspj.get(i).set("peij_card_sl", 0);
				}
			}
			
			Kehu_CardEntity kehuCard = getVipCard(card_no);
			if(kehuCard != null){
				map.put("card_ssje", kehuCard.getCard_ssje());
				map.put("card_leftcs_jx", kehuCard.getCard_leftcs_jx());
				map.put("card_leftcs_px", kehuCard.getCard_leftcs_px());
				map.put("card_syje", kehuCard.getCard_leftje());
			}
			if(tsxm.size() > 0){
				map.put("tsxm", tsxm);
			}
			if(tspj.size() > 0){
				map.put("tspj", tspj);
			}
		}else{
			return jsons.json(1, 1, 0,
					"会员卡号不能空，请输入卡号");
		}
		if(work_no != null&&work_no != ""){
			Sql sql2 = Sqls
					.queryRecord("select * from work_pz_sj where work_no='"+work_no+"'");
			dao.execute(sql2);
			List<Record> sj = sql2.getList(Record.class);
			if(sj != null){
				String xche_hjje = sj.get(0).getString("xche_hjje");
				map.put("xche_hjje",xche_hjje);
			}
			
		}else{
			return jsons.json(1, 1, 0,
					"维修单号不能空，请输入维修单号");
		}
		String json = Json.toJson(map, JsonFormat.full());
		if(map.size() > 0){
			return jsons.json(1, 1, 1, json);
		}
		return jsons.json(1, 1, 0, json);
		
	}
	/**
	 * 根据卡号获取卡信息
	 * 
	 * @param card_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String getVipCardByCardNo(String card_no, String che_no) {
		Kehu_CardEntity kehuCard = getVipCard(card_no);
		if (kehuCard != null) {
			if (kehuCard.isFlag_use()) {
				return jsons.json(1, 1, 0, "此卡号已经禁用！");
			}
			if (kehuCard.isFlag_guashi()) {
				return jsons.json(1, 1, 0, "此卡号已经挂失！");
			}
			if (kehuCard.isFlag_enddate()) {
				return jsons.json(1, 1, 0, "此卡号已经到期！");
			}
			if (card_no != null && card_no.length() > 1) {
				Sql sql_ = Sqls.queryRecord("SELECT flag_single FROM cardsysset ");
				dao.execute(sql_);
				List<Record> res_1 = sql_.getList(Record.class);
				int flag_single = res_1.get(0).getInt("flag_single");
				if(flag_single != 0) {
					Sql sql1 = Sqls.queryRecord("select count(*) as cnt from kehu_card a, kehu_card_che b " +
							"where a.card_no = b.card_no and flag_use  = 0 and flag_guashi = 0 and flag_enddate = 0 and isnull(flag_shoukuan,0) = 1 " +
                            "and b.card_no = '" + card_no + "' and b.che_no ='" + che_no + "'");
					dao.execute(sql1);
					List<Record> res1 = sql1.getList(Record.class);
					int cnt = res1.get(0).getInt("cnt");
					if (cnt == 0)  
						return jsons.json(1, 1, 0, "系统设置为“关联式会员制度”，此会员卡非该辆车所有，不能使用！");
				}
			}
            // 把che_no和kehu_no也返回去
            Sql sql1 = Sqls.queryRecord("select a.che_no,a.kehu_no,b.ItemRate,b.PeijRate,a.card_leftje " +
                    "from kehu_card a,CardKind b where a.card_kind = b.cardkind and a.card_no ='" + card_no + "'");
            dao.execute(sql1);
            List<Record> res1 = sql1.getList(Record.class);
            String json = Json.toJson(res1.get(0), JsonFormat.full());
            return jsons.json(1, 1, 1, json);
		} else {
            return jsons.json(1, 1, 0, "不存在此会员卡！");
        }
	}

    /**
     * 根据卡号获取卡信息
     *
     * @param card_no
     * @return
     */
    @At
    @Ok("raw:json")
    public String wxjdReadCardNo(String card_no, String che_no, String work_no) {
        Kehu_CardEntity kehuCard = getVipCard(card_no);
        if (kehuCard != null) {
            if (kehuCard.isFlag_use()) {
                return jsons.json(1, 1, 0, "此卡号已经禁用！");
            }
            if (kehuCard.isFlag_guashi()) {
                return jsons.json(1, 1, 0, "此卡号已经挂失！");
            }
            if (kehuCard.isFlag_enddate()) {
                return jsons.json(1, 1, 0, "此卡号已经到期！");
            }
            if (card_no != null && card_no.length() > 1) {
                Sql sql_ = Sqls.queryRecord("SELECT flag_single FROM cardsysset ");
                dao.execute(sql_);
                List<Record> res_1 = sql_.getList(Record.class);
                int flag_single = res_1.get(0).getInt("flag_single");
                if(flag_single != 0) {
                    Sql sql1 = Sqls.queryRecord("select count(*) as cnt from kehu_card a, kehu_card_che b " +
                            "where a.card_no = b.card_no and flag_use  = 0 and flag_guashi = 0 and flag_enddate = 0 and isnull(flag_shoukuan,0) = 1 " +
                            "and b.card_no = '" + card_no + "' and b.che_no ='" + che_no + "'");
                    dao.execute(sql1);
                    List<Record> res1 = sql1.getList(Record.class);
                    int cnt = res1.get(0).getInt("cnt");
                    if (cnt == 0)
                        return jsons.json(1, 1, 0, "系统设置为“关联式会员制度”，此会员卡非该辆车所有，不能使用！");
                }
            }
            String itemRate = "1";
            String peijRate = "1";
            String card_kind = "";
            Sql sql1 = Sqls.queryRecord("select a.card_kind,b.ItemRate,b.PeijRate from kehu_card a,CardKind b where a.card_kind=b.cardkind and a.card_no ='" + card_no + "'");
            dao.execute(sql1);
            List<Record> res1 = sql1.getList(Record.class);
            if (res1.size() > 0) {
                card_kind = res1.get(0).getString("card_kind");
                itemRate = res1.get(0).getString("ItemRate");
                peijRate = res1.get(0).getString("PeijRate");
            }
            // 把卡的信息更新到工作表中
            dao.execute(Sqls.create("update work_pz_gz set card_no='" + card_no + "',card_kind='" + card_kind +
                    "',card_itemrate=" + itemRate + ",card_peijrate=" + peijRate + " where work_no='" + work_no + "'"));
            return jsons.json(1, 1, 1, card_no);
        } else {
            return jsons.json(1, 1, 0, "不存在此会员卡！");
        }
    }

	/**
	 * 获取卡信息
	 * 
	 * @param card_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String getvipCard(String card_no, String che_no) {
		Kehu_CardEntity kehuCard = getVipCard(card_no);
		if (kehuCard != null) {
			if (kehuCard.isFlag_use()) {
				return jsons.json(1, 1, 0, "此卡号已经禁用！");
			}
			if (kehuCard.isFlag_guashi()) {
				return jsons.json(1, 1, 0, "此卡号已经挂失！");
			}
			if (kehuCard.isFlag_enddate()) {
				return jsons.json(1, 1, 0, "此卡号已经到期！");
			}
			if (che_no != null && che_no.length() > 1) {
				Sql sql1 = Sqls
						.queryRecord("select count(*) as cnt from kehu_card a, kehu_card_che b where a.card_no = b.card_no and flag_use  = 0 and flag_guashi = 0 and flag_enddate = 0 and isnull(flag_shoukuan,0) = 1 and b.card_no = '"
								+ card_no + "' and b.che_no ='" + che_no + "'");
				dao.execute(sql1);
				List<Record> res1 = sql1.getList(Record.class);
				int cnt = res1.get(0).getInt("cnt");
				if (cnt == 0)  
					return jsons.json(1, 1, 0,
							"系统设置为“关联式会员制度”，此会员卡非该辆车所有,不符合条件。不能结算");
			}
			String json = Json.toJson(kehuCard, JsonFormat.full());
			return jsons.json(1, 1, 1, json);
		}
		return jsons.json(1, 1, 0, "会员卡不存在");
	}

	/**
	 * 根据车牌查询会员信息
	 * 
	 * @param che_no
	 * @param card_no
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String getUserCardByCheNo(String che_no, String card_no) {
		Cnd cnd = Cnd.where("1", "=", "1");
		if (card_no != null)
			cnd.and("card_no", "like", "%" + card_no + "%");
		if (che_no != null)
			cnd.and("che_no", "=", che_no);
		Kehu_CardEntity chad = dao.fetch(Kehu_CardEntity.class, cnd);
		if (chad != null) {
			String json = Json.toJson(chad, JsonFormat.full());
			return jsons.json(1, 1, 1, json);
		} else {
			return jsons.json(1, 1, 0, "会员卡不存在！");
		}
	}

	@At
	@Ok("raw:json")
	public String getkehuByCheNo(String che_no){
		Sql sql1 = Sqls
				.queryRecord("select kehu_no,kehu_mc,kehu_birthday,kehu_dh,kehu_sj,kehu_xm,kehu_dz from kehu where kehu_no in(select kehu_no from work_cheliang_sm where che_no= '"+che_no+"')");
		dao.execute(sql1);
		List<Record> res1 = sql1.getList(Record.class);
        if (res1.size() == 0) {
            return jsons.json(1, 1, 0, "查询失败");
        } else {
            String json = Json.toJson(res1.get(0), JsonFormat.full());
            return jsons.json(1, 1, 1, json);
        }
	}
	/**
	 * 添加会员
	 * 
	 * @param card
	 * @return
	 */
	@At
	@Ok("raw:json")
	public boolean add(@Param("..") Kehu_CardEntity card, int isNewCar) {
		System.out.println("======================");
		System.out.println(card.getCard_ysje());//, //--开户金额
		System.out.println(card.getCard_ssje());// , // --实收金额
		System.out.println(card.getCard_addje());//, --可用金额
		System.out.println(card.getCard_cs());//,--普洗次数
		System.out.println(card.getCard_cs_jx());//,--精洗次数
		System.out.println("======================");
        if (isNewCar == 1) {
            Date date = new Date();
            Work_cheliang_smEntity workCheliangSmEntity = new Work_cheliang_smEntity();
            workCheliangSmEntity.setChe_no(card.getChe_no());
            workCheliangSmEntity.setKehu_no(card.getChe_no());
            dao.insert(workCheliangSmEntity);
            KehuEntity ke = new KehuEntity();
            ke.setKehu_no(card.getChe_no());
            ke.setLastModifyTime(date);
            ke.setKehu_xz((short) 1);
            dao.insert(ke);
        }
        Work_cheliang_smEntity che = dao.fetch(Work_cheliang_smEntity.class, card.getChe_no());
        card.setKehu_no(che.getKehu_no());
        // 生成一个卡号，后期修改
		StringGenerator sg = new StringGenerator(20, 20);
		String cardNo = sg.next();
		card.setCard_no(cardNo);
		Date date = new Date();
		card.setCard_jlrq(date);
		card.setFlag_use(false);
		card.setFlag_guashi(false);
		card.setFlag_enddate(false);
		card.setFlag_shoukuan(0);
        card.setChe_cx(che.getChe_cx());
        card.setChe_wxys(che.getChe_wxys());
		Kehu_CardEntity result = dao.insert(card);
        if (result != null) {
            // 插入客户车辆关联信息
            KehuCardCheEntity kehuCardChe = new KehuCardCheEntity();
            kehuCardChe.setChe_no(card.getChe_no());
            kehuCardChe.setChe_cx(card.getChe_cx());
            kehuCardChe.setChe_wxys(card.getChe_wxys());
            kehuCardChe.setCard_no(cardNo);
            dao.insert(kehuCardChe);
            // 更新材料表
            List<Cardservice> list_cardservice = dao.query(Cardservice.class,
                    Cnd.where("cardkind", "=", card.getCard_kind()));
            for (Cardservice cardservice : list_cardservice) {
                KehuCarddetail kehuCardDetail = new KehuCarddetail();
                kehuCardDetail.setChe_no(card.getChe_no());
                kehuCardDetail.setCard_no(cardNo);
                kehuCardDetail.setWxxm_no(cardservice.getWxxm_no());
                kehuCardDetail.setWxxm_mc(cardservice.getWxxm_mc());
                kehuCardDetail.setWxxm_gs(cardservice.getWxxm_gs());
                kehuCardDetail.setWxxm_cs(cardservice.getWxxm_cs());
                kehuCardDetail.setWxxm_je(cardservice.getWxxm_je());
                kehuCardDetail.setWxxm_yqcs(0);
                kehuCardDetail.setDate_create(new Date());
                dao.insert(kehuCardDetail);
            }
            // 更新项目表
            List<CardKindPeij> list_CardKindPeij = dao.query(CardKindPeij.class,
                    Cnd.where("cardkind", "=", card.getCard_kind()));
            for (CardKindPeij cardKindPeij : list_CardKindPeij) {
                KehuCarddetailpeij kehuCarddetailpeij = new KehuCarddetailpeij();
                kehuCarddetailpeij.setChe_no(card.getChe_no());
                kehuCarddetailpeij.setCard_no(cardNo);
                kehuCarddetailpeij.setPeij_no(cardKindPeij.getPeij_no());
                kehuCarddetailpeij.setPeij_th(cardKindPeij.getPeij_th());
                kehuCarddetailpeij.setPeij_mc(cardKindPeij.getPeij_mc());
                kehuCarddetailpeij.setPeij_dw(cardKindPeij.getPeij_dw());
                kehuCarddetailpeij.setPeij_cx(cardKindPeij.getPeij_cx());
                kehuCarddetailpeij.setPeij_pp(cardKindPeij.getPeij_pp());
                kehuCarddetailpeij.setPeij_jk(cardKindPeij.getPeij_jk());
                kehuCarddetailpeij.setPeij_cd(cardKindPeij.getPeij_cd());
                kehuCarddetailpeij.setPeij_dj(cardKindPeij.getPeij_dj());
                kehuCarddetailpeij.setPeij_remark(cardKindPeij.getPeij_remark());
                kehuCarddetailpeij.setPeij_card_sl(cardKindPeij.getPeij_card_sl());
                kehuCarddetailpeij.setDate_create(new Date());
                dao.insert(kehuCarddetailpeij);
            }
            Sql sql1 = Sqls
                    .create("update kehu_card set card_yhje= isnull(card_ysje,0)-isnull(card_ssje,0), card_useje=0,card_leftje= card_addje,card_usejf=0 ,card_leftjf = card_jifen,card_usecs_jx=0,card_leftcs_jx= isnull(card_cs_jx,0), card_usecs_px=0,card_leftcs_px= isnull(card_cs,0) where card_no = '"
                            + cardNo + "'");
            dao.execute(sql1);
            return true;
        } else {
			return false;
		}
	}

	/**
	 * 卡信息
	 * 
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String kxx() {
		List<CardKindEntity> result = dao.query(CardKindEntity.class, null);

		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);

	}

	/**
	 * 卡信息
	 *
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String checkCardPass(String card_no, String card_pass) {
        Sql sql = Sqls.queryRecord("select isnull(card_password,'') card_password from kehu_card where card_no='" + card_no + "'");
        dao.execute(sql);
        List<Record> list = sql.getList(Record.class);
        if (list.size() > 0) {
            if (list.get(0).getString("card_password").equals(card_pass)) {
                return "success";
            }
        }
		return "fail";

	}

}
