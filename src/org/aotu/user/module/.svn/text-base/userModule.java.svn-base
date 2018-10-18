package org.aotu.user.module;

import java.util.List;

import org.aotu.Jsons;
import org.aotu.user.entity.userEntity;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * Title   :userModule 
 * Package :org.aotu.user.module
 * @Description: 用户
 * @author ZhangYaLong
 * @data 2017年4月12日上午8:19:50
 * @version V1.0
 */
@IocBean
@At("/user")
public class userModule {
	
	@Inject
	Dao dao;
	
	@Inject
	Jsons jsons;
	
	
	/**
	 * 用户列表
	 * @param Nu
	 * @return
	 */

	@At
	@Ok("raw:json")
	public String userList(String GongSiNo){
		List<userEntity> result= dao.query(userEntity.class, Cnd.where("GongSiNo","=",GongSiNo));
		String json = Json.toJson(result, JsonFormat.compact());
		if(result.size() != 0){
			return jsons.json(1, result.size(), 1, json);
		}
		return jsons.json(1, result.size(), 0, json);
	}
	
	/**
	 * 修改密码
	 * @param id
	 * @return
	 */
	@At
	public boolean pswdEdit(Integer id,String newPsd){
		System.out.println("======="+id);
		 userEntity user = dao.fetch(userEntity.class, id);
    	if(user!=null){
    		user.setPsd(newPsd);
    		int num = dao.update(user);
    		if(num>0){
    			return true;
    		}
    	}
		return false;
	} 
	
	/**
	 * 取默认的价格
	 * @param price_id
	 * @param price_name
	 * @return
	 */
	@At
	@Ok("raw:json")
	public String jiag(String price_id,String price_name){ 
		Sql sql = Sqls.queryRecord("select price_id from sm_app_defprice");
		dao.execute(sql);
		List<Record> res = sql.getList(Record.class);
		if(res == null){
			Sql sql1 = Sqls.queryRecord("insert into sm_app_defprice (price_id,price_name) values ('"+price_id+"','"+price_name+"');");
			dao.execute(sql);
			List<Record> res1 = sql1.getList(Record.class);
			String json = Json.toJson(res1, JsonFormat.full()); 
			return jsons.json(1, res1.size(), 1, json); 
		}else{
			
			String json = Json.toJson(res, JsonFormat.full());
			return jsons.json(1, res.size(), 1, json); 
		}
		
	}
	
	
}
