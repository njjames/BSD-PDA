package org.aotu.publics.eneity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table; 
/**
 * 
 * title:Sm_wxxmlbEntity
 * @Description:<维修项目类别>
 * @author Zhang Yalong
 * @date 2017-4-18 上午10:12:47
 * @version: V1.0
 */
@Table(value="sm_wxxmlb")
public class Sm_wxxmlbEntity{
	 @Column
	private String wxxm_lbdm;
	 @Column
	private String wxxm_lbmc;
	 @Column
	private String wxxm_lbtop;
	 @Column
	private String wxxm_lbqz;
	 @Column
	private int wxxm_lblen;
	public void setWxxm_lbdm(String wxxm_lbdm){
	this.wxxm_lbdm=wxxm_lbdm;
	}
	public String getWxxm_lbdm(){
		return wxxm_lbdm;
	}
	public void setWxxm_lbmc(String wxxm_lbmc){
	this.wxxm_lbmc=wxxm_lbmc;
	}
	public String getWxxm_lbmc(){
		return wxxm_lbmc;
	}
	public void setWxxm_lbtop(String wxxm_lbtop){
	this.wxxm_lbtop=wxxm_lbtop;
	}
	public String getWxxm_lbtop(){
		return wxxm_lbtop;
	}
	public void setWxxm_lbqz(String wxxm_lbqz){
	this.wxxm_lbqz=wxxm_lbqz;
	}
	public String getWxxm_lbqz(){
		return wxxm_lbqz;
	}
	public void setWxxm_lblen(int wxxm_lblen){
	this.wxxm_lblen=wxxm_lblen;
	}
	public int getWxxm_lblen(){
		return wxxm_lblen;
	}
}

