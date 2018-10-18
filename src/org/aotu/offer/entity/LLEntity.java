package org.aotu.offer.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 
 * Title:LLEntity
 * Package:org.aotu.offer.entity 
 * @Description: 报价材料
 * @author ZhangYaLong
 * @date 2017年4月14日下午1:10:31
 * @version V1.0
 */

@Table(value="work_baojia_ll")
public class LLEntity{
	@Id
	 @Column
	private Integer reco_no;
	 @Column
	private String list_no;
	 @Column
	private String peij_no;
	 @Column
	private String peij_th;
	 @Column
	private String peij_mc;
	 @Column
	private String peij_dw;
	 @Column
	private double peij_sl;
	 @Column
	private double peij_dj;
	 @Column
	private double peij_je;
	 @Column
	private double peij_cb;
	 @Column
	private double peij_cbje;
	 @Column
	private String cangk_dm;
	 @Column
	private String peij_cx;
	 @Column
	private String peij_cd;
	 @Column
	private String peij_pp;
	 @Column
	private String peij_jk;
	 @Column
	private String peij_hw;
	 @Column
	private String peij_zt;
	 @Column
	private String peij_bz;

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
	public void setPeij_no(String peij_no){
	this.peij_no=peij_no;
	}
	public String getPeij_no(){
		return peij_no;
	}
	public void setPeij_th(String peij_th){
	this.peij_th=peij_th;
	}
	public String getPeij_th(){
		return peij_th;
	}
	public void setPeij_mc(String peij_mc){
	this.peij_mc=peij_mc;
	}
	public String getPeij_mc(){
		return peij_mc;
	}
	public void setPeij_dw(String peij_dw){
	this.peij_dw=peij_dw;
	}
	public String getPeij_dw(){
		return peij_dw;
	}
	public void setPeij_sl(double peij_sl){
	this.peij_sl=peij_sl;
	}
	public double getPeij_sl(){
		return peij_sl;
	}
	public void setPeij_dj(double peij_dj){
	this.peij_dj=peij_dj;
	}
	public double getPeij_dj(){
		return peij_dj;
	}
	public void setPeij_je(double peij_je){
	this.peij_je=peij_je;
	}
	public double getPeij_je(){
		return peij_je;
	}
	public void setPeij_cb(double peij_cb){
	this.peij_cb=peij_cb;
	}
	public double getPeij_cb(){
		return peij_cb;
	}
	public void setPeij_cbje(double peij_cbje){
	this.peij_cbje=peij_cbje;
	}
	public double getPeij_cbje(){
		return peij_cbje;
	}
	public void setCangk_dm(String cangk_dm){
	this.cangk_dm=cangk_dm;
	}
	public String getCangk_dm(){
		return cangk_dm;
	}
	public void setPeij_cx(String peij_cx){
	this.peij_cx=peij_cx;
	}
	public String getPeij_cx(){
		return peij_cx;
	}
	public void setPeij_cd(String peij_cd){
	this.peij_cd=peij_cd;
	}
	public String getPeij_cd(){
		return peij_cd;
	}
	public void setPeij_pp(String peij_pp){
	this.peij_pp=peij_pp;
	}
	public String getPeij_pp(){
		return peij_pp;
	}
	public void setPeij_jk(String peij_jk){
	this.peij_jk=peij_jk;
	}
	public String getPeij_jk(){
		return peij_jk;
	}
	public void setPeij_hw(String peij_hw){
	this.peij_hw=peij_hw;
	}
	public String getPeij_hw(){
		return peij_hw;
	}
	public void setPeij_zt(String peij_zt){
	this.peij_zt=peij_zt;
	}
	public String getPeij_zt(){
		return peij_zt;
	}
	public void setPeij_bz(String peij_bz){
	this.peij_bz=peij_bz;
	}
	public String getPeij_bz(){
		return peij_bz;
	}
}

