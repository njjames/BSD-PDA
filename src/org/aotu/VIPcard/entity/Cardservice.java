package org.aotu.VIPcard.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author Administrator
 *
 */
@Table(value="cardservice")
public class Cardservice {
	@Id
	@Column
	private Integer id;
	@Column
	private String cardkind;
	@Column
	private String wxxm_no;
	@Column
	private String wxxm_mc;
	@Column
	private String wxxm_gs;
	@Column
	private String wxxm_cs;
	@Column
	private String wxxm_je;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCardkind() {
		return cardkind;
	}
	public void setCardkind(String cardkind) {
		this.cardkind = cardkind;
	}
	public String getWxxm_no() {
		return wxxm_no;
	}
	public void setWxxm_no(String wxxm_no) {
		this.wxxm_no = wxxm_no;
	}
	public String getWxxm_mc() {
		return wxxm_mc;
	}
	public void setWxxm_mc(String wxxm_mc) {
		this.wxxm_mc = wxxm_mc;
	}
	public String getWxxm_gs() {
		return wxxm_gs;
	}
	public void setWxxm_gs(String wxxm_gs) {
		this.wxxm_gs = wxxm_gs;
	}
	public String getWxxm_cs() {
		return wxxm_cs;
	}
	public void setWxxm_cs(String wxxm_cs) {
		this.wxxm_cs = wxxm_cs;
	}
	public String getWxxm_je() {
		return wxxm_je;
	}
	public void setWxxm_je(String wxxm_je) {
		this.wxxm_je = wxxm_je;
	}
	
	
}
