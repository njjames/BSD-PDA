package org.aotu.offer.service;

import java.util.List;

import org.aotu.offsetPager;
import org.aotu.offer.entity.feilvEntity;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;

public class offerService {
	@Inject
	Dao dao;
	
	public List<feilvEntity>  queryfei(String q,String  w,String e){
		 List<feilvEntity>	list= dao.query(feilvEntity.class, Cnd.where(q,w,e));
		return list;
	}
	
	public List<feilvEntity>  queryfei(String q,String  w,String e,int a,int b){
		List<feilvEntity> list= dao.query(feilvEntity.class, Cnd.where(q,w,e),new offsetPager(a, b));
		return list;
	}
	
	
}
