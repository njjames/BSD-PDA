package org.aotu.offer.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 
 * Title:wxxmEntity
 * Package:org.aotu.offer.entity 
 * @Description: 报价维修项目
 * @author ZhangYaLong
 * @date 2017年4月14日下午1:10:57
 * @version V1.0
 */

@Table(value="work_baojia_wxxm")
public class wxxmEntity{
	@Id
	 @Column
	private Integer reco_no;
	 @Column
	private String list_no;
	 @Column
	private String wxxm_no;
	 @Column
	private String wxxm_mc;
	 @Column
	private double wxxm_gs;
	 @Column
	private double wxxm_khgs;
	 @Column
	private double wxxm_cb;
	 @Column
	private double wxxm_je;
	 @Column
	private double wxxm_yje;
	 @Column
	private String wxxm_mxcx;
	 @Column
	private String wxxm_zt;
	 @Column
	private String wxxm_bz;
	 @Column
	private double wxxm_dj;

	public Integer getReco_no() {
		return reco_no;
	}
	public void setReco_no(Integer reco_no) {
		this.reco_no = reco_no;
	}
	public void setList_no(String list_no){
	this.list_no=list_no;
	}
	public String getList_no(){
		return list_no;
	}
	public void setWxxm_no(String wxxm_no){
	this.wxxm_no=wxxm_no;
	}
	public String getWxxm_no(){
		return wxxm_no;
	}
	public void setWxxm_mc(String wxxm_mc){
	this.wxxm_mc=wxxm_mc;
	}
	public String getWxxm_mc(){
		return wxxm_mc;
	}
	public void setWxxm_gs(double wxxm_gs){
	this.wxxm_gs=wxxm_gs;
	}
	public double getWxxm_gs(){
		return wxxm_gs;
	}
	public void setWxxm_khgs(double wxxm_khgs){
	this.wxxm_khgs=wxxm_khgs;
	}
	public double getWxxm_khgs(){
		return wxxm_khgs;
	}
	public void setWxxm_cb(double wxxm_cb){
	this.wxxm_cb=wxxm_cb;
	}
	public double getWxxm_cb(){
		return wxxm_cb;
	}
	public void setWxxm_je(double wxxm_je){
	this.wxxm_je=wxxm_je;
	}
	public double getWxxm_je(){
		return wxxm_je;
	}
	public void setWxxm_yje(double wxxm_yje){
	this.wxxm_yje=wxxm_yje;
	}
	public double getWxxm_yje(){
		return wxxm_yje;
	}
	public void setWxxm_mxcx(String wxxm_mxcx){
	this.wxxm_mxcx=wxxm_mxcx;
	}
	public String getWxxm_mxcx(){
		return wxxm_mxcx;
	}
	public void setWxxm_zt(String wxxm_zt){
	this.wxxm_zt=wxxm_zt;
	}
	public String getWxxm_zt(){
		return wxxm_zt;
	}
	public void setWxxm_bz(String wxxm_bz){
	this.wxxm_bz=wxxm_bz;
	}
	public String getWxxm_bz(){
		return wxxm_bz;
	}
	public void setWxxm_dj(double wxxm_dj){
	this.wxxm_dj=wxxm_dj;
	}
	public double getWxxm_dj(){
		return wxxm_dj;
	}
}

