package org.aotu.publics.eneity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
 * 
 * title:Sm_cangkEntity
 * @Description:<仓库>
 * @author Zhang Yalong
 * @date 2017-4-17 下午3:22:59
 * @version: V1.0
 */

@Table(value="Sm_cangk")
public class Sm_cangkEntity{
	 @Column
	private String cangk_dm;
	 @Column
	private String cangk_mc;
	 @Column
	private String danw_dm;
	 @Column
	private String danw_mc;
	 @Column
	private short cangk_def;
	 @Column
	private String cangk_bz;
	 @Column
	private String cangk_CanUsedInGongSi;
	 @Column
	private boolean cangk_hide;
	public void setCangk_dm(String cangk_dm){
	this.cangk_dm=cangk_dm;
	}
	public String getCangk_dm(){
		return cangk_dm;
	}
	public void setCangk_mc(String cangk_mc){
	this.cangk_mc=cangk_mc;
	}
	public String getCangk_mc(){
		return cangk_mc;
	}
	public void setDanw_dm(String danw_dm){
	this.danw_dm=danw_dm;
	}
	public String getDanw_dm(){
		return danw_dm;
	}
	public void setDanw_mc(String danw_mc){
	this.danw_mc=danw_mc;
	}
	public String getDanw_mc(){
		return danw_mc;
	}
	public void setCangk_def(short cangk_def){
	this.cangk_def=cangk_def;
	}
	public short getCangk_def(){
		return cangk_def;
	}
	public void setCangk_bz(String cangk_bz){
	this.cangk_bz=cangk_bz;
	}
	public String getCangk_bz(){
		return cangk_bz;
	}
	public void setCangk_CanUsedInGongSi(String cangk_CanUsedInGongSi){
	this.cangk_CanUsedInGongSi=cangk_CanUsedInGongSi;
	}
	public String getCangk_CanUsedInGongSi(){
		return cangk_CanUsedInGongSi;
	}
	public void setCangk_hide(boolean cangk_hide){
	this.cangk_hide=cangk_hide;
	}
	public boolean getCangk_hide(){
		return cangk_hide;
	}
}

