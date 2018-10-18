package org.aotu.publics.eneity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table; 

@Table(value="sm_app_defprice")
public class Sm_app_defpriceEntity{
	@Id
	@Column
	private int id;
	@Column
	private int price_id;
	@Column
	private String price_name;
	public void setId(int id){
	this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setPrice_id(int price_id){
	this.price_id=price_id;
	}
	public int getPrice_id(){
		return price_id;
	}
	public void setPrice_name(String price_name){
	this.price_name=price_name;
	}
	public String getPrice_name(){
		return price_name;
	}
}

