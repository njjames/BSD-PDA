package org.aotu.offer.module;

import java.util.List;

import org.aotu.Jsons;
import org.aotu.offer.entity.baoJiaEntity;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;


/**
 * 
 * title:lsOfferModule
 * @Description:<历史报价>
 * @author Zhang Yalong
 * @date 2017-4-26 下午3:59:43
 * @version: V1.0
 */
@IocBean
@At("/lsbj")
public class lsOfferModule {
	
	@Inject
	Dao dao;
	
	@Inject
	Jsons jsons;
	
	
	@At("/")
	@Ok("raw:json")
	public String lsbj(int pageNumber,String che_no,String kehu_mc){
		  Pager pager = dao.createPager(pageNumber, 20);
		  Cnd cnd = Cnd.where("list_state", "=", 1);
		  if(che_no!=null && che_no.length()>0)
			  cnd.and("che_no", "like", "%"+che_no+"%");
		  if(kehu_mc!=null  && kehu_mc.length()>0)
			  cnd.and("kehu_mc", "like", "%"+kehu_mc+"%");
		List<baoJiaEntity> result  = dao.query(baoJiaEntity.class, cnd.desc("reco_no"),pager);
		String json = Json.toJson(result, JsonFormat.full());
		if(result.size() != 0){
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json); 
		
	}
	
//
//	@At("/")
//	@Ok("raw:json")
//	public String lsbj(int pageNumber,String s){
//		  Pager pager = dao.createPager(pageNumber, 20);
//		List<baoJiaEntity> result  = dao.query(baoJiaEntity.class, null,pager);
//		String json = Json.toJson(result, JsonFormat.compact());
//		if(result.size() != 0){
//			return jsons.json(1, result.size(), 1, json);
//		}
//		return jsons.json(1, result.size(), 0, json); 
//		
//	}
}
