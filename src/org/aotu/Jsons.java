package org.aotu;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;


@IocBean
public class Jsons {
	public String json(int status,int total,int message,String data){
		if(data!=null && data.length()>0){
			data.replaceAll("\"","\'");
		}
		String mes = "查询失败";
		if(message == 1){
			mes = "查询成功";
		}
		String title = "\"status\": "+status+",\"total\": "+total+",\"message\": \""+mes+"\"";
		if(message == 0){
			data = "{"+title+",\"data\":\""+data+"\"}";
		}else{
			if(!data.equals("null"))
				data = "{"+title+",\"data\":"+data+"}";
			else
				data = "{"+title+"}";
		}
		System.out.println(data);
		return data.replaceAll(": null", ":\"\" ").replaceAll("null", "");
	}
}
