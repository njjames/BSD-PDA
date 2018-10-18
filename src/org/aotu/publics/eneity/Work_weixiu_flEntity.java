package org.aotu.publics.eneity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table; 

/**
 * 
 * title:Work_weixiu_flEntity
 * @Description:<维修类别>
 * @author Zhang Yalong
 * @date 2017-4-17 下午3:29:15
 * @version: V1.0
 */
@Table(value="work_weixiu_fl")
public class Work_weixiu_flEntity{
	 @Column
	private double id;
	 @Column
	private String wxfl_mc;
	public void setId(double id){
	this.id=id;
	}
	public double getId(){
		return id;
	}
	public void setWxfl_mc(String wxfl_mc){
	this.wxfl_mc=wxfl_mc;
	}
	public String getWxfl_mc(){
		return wxfl_mc;
	}
}

