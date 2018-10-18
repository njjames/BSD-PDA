package org.aotu.publics.eneity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table; 


/**
 * 
 * title:Sm_peijlbEntity
 * @Description:<配件（商品）类别说明表>
 * @author Zhang Yalong
 * @date 2017-4-24 上午11:12:30
 * @version: V1.0
 */
@Table(value="sm_peijlb")
public class Sm_peijlbEntity{
	 @Column
	private String  peijlb_dm;
	 @Column
	private String peijlb_top;
	 @Column
	private String peijlb_mc;
	 @Column
	private String peijlb_qz;
	 @Column
	private int peijlb_len;
	public void setPeijlb_dm(String peijlb_dm){
	this.peijlb_dm=peijlb_dm;
	}
	public String getPeijlb_dm(){
		return peijlb_dm;
	}
	public void setPeijlb_top(String peijlb_top){
	this.peijlb_top=peijlb_top;
	}
	public String getPeijlb_top(){
		return peijlb_top;
	}
	public void setPeijlb_mc(String peijlb_mc){
	this.peijlb_mc=peijlb_mc;
	}
	public String getPeijlb_mc(){
		return peijlb_mc;
	}
	public void setPeijlb_qz(String peijlb_qz){
	this.peijlb_qz=peijlb_qz;
	}
	public String getPeijlb_qz(){
		return peijlb_qz;
	}
	public void setPeijlb_len(int peijlb_len){
	this.peijlb_len=peijlb_len;
	}
	public int getPeijlb_len(){
		return peijlb_len;
	}
}

