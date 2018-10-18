package org.aotu.car.module;

import java.util.List;

import org.aotu.Jsons;
import org.aotu.lj.FileUpload;
import org.aotu.publics.eneity.Work_cheliang_smEntity;
import org.aotu.publics.eneity.Work_cheliang_sm_vEntity;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
/**
 * title:carModule
 * @Description:<车辆信息>
 * @author Zhang Yalong
 * @date 2017-5-3 下午4:18:46
 * @version: V1.0
 */
@IocBean
@At("/car")
public class carModule {
	
	
	@Inject
	Dao dao;
	
	@Inject
	Jsons jsons;
	
	
	/**
	 * 查询车辆信息
	 * @param pageNumber
	 * @param che_no
	 * @param che_sj
	 * @param kehu_mc
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String clxx(int pageNumber,@Param("che_no")String che_no,@Param("che_sj")String che_sj,String kehu_mc){
		System.out.println("================="+kehu_mc);
		Cnd cb = Cnd.where("1","=","1");
		if(che_no!=null && che_no!=""){
			cb = cb.and("che_no","like","%"+che_no+"%");
		}
//		if(che_sj!=null && che_sj!=""){
//			cb = cb.and("kehu_mc","like","%"+che_sj+"%");
//		}
		if(kehu_mc!=null&& kehu_mc!=""){
			cb.and("kehu_mc","like","%"+kehu_mc+"%");
		}
		//cb.and("che_xingzhi","!=","");
		Pager pager = dao.createPager(pageNumber, 20);
		List<Work_cheliang_sm_vEntity> result;
			result = dao.query(Work_cheliang_sm_vEntity.class, cb.desc("id"),pager);
		
		String json = Json.toJson(result, JsonFormat.full());
		if (result.size() != 0) {
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);

	  }	
	
}
