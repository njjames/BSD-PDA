package org.aotu.VIPcard.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author 刘宏德
 *
 */
@Table(value="kehu_carddetail")
public class KehuCarddetail {
	@Id
	@Column
	private Integer id;
	@Column
	private String che_no;
	@Column
	private String card_no;
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
	@Column
	private Integer wxxm_yqcs;
	@Column
	private Date date_create;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChe_no() {
		return che_no;
	}
	public void setChe_no(String che_no) {
		this.che_no = che_no;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
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

	public Date getDate_create() {
		return date_create;
	}
	public void setDate_create(Date date_create) {
		this.date_create = date_create;
	}
	public Integer getWxxm_yqcs() {
		return wxxm_yqcs;
	}
	public void setWxxm_yqcs(Integer wxxm_yqcs) {
		this.wxxm_yqcs = wxxm_yqcs;
	}
	
}
