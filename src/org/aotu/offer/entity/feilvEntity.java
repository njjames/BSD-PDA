package org.aotu.offer.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

   /**
    * 
    * title:feilvEntity
    * @Description:<描述>
    * @author Zhang Yalong
    * @date 2017-4-15 下午6:12:48
    * @version: V1.0
    */

@Table(value = "work_feilv_sm")
public class feilvEntity{
	 @Column
	private double id;
	 @Column
	private String feil_mc;
	 @Column
	private double feil_fl;
	 @Column
	private boolean feil_sy;
	public void setId(double id){
	this.id=id;
	}
	public double getId(){
		return id;
	}
	public void setFeil_mc(String feil_mc){
	this.feil_mc=feil_mc;
	}
	public String getFeil_mc(){
		return feil_mc;
	}
	public void setFeil_fl(double feil_fl){
	this.feil_fl=feil_fl;
	}
	public double getFeil_fl(){
		return feil_fl;
	}
	public void setFeil_sy(boolean feil_sy){
	this.feil_sy=feil_sy;
	}
	public boolean getFeil_sy(){
		return feil_sy;
	}
}

