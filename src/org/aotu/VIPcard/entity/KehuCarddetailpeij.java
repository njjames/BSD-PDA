package org.aotu.VIPcard.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author 刘宏德
 *
 */
@Table(value="kehu_carddetailpeij")
public class KehuCarddetailpeij {
	@Id
	@Column
	private Integer id;
	@Column
	private String che_no;
	@Column
	private String card_no;
	@Column
	private String peij_no;
	@Column
	private String peij_th;
	@Column
	private String peij_mc;
	@Column
	private String peij_dw;
	@Column
	private String peij_cx;
	@Column
	private String peij_pp;
	@Column
	private String peij_jk;
	@Column
	private String peij_cd;
	@Column
	private String peij_dj;
	@Column
	private String peij_remark;
	@Column
	private String peij_card_sl;
	@Column
	private String peij_card_yqsl;
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
	public String getPeij_no() {
		return peij_no;
	}
	public void setPeij_no(String peij_no) {
		this.peij_no = peij_no;
	}
	public String getPeij_th() {
		return peij_th;
	}
	public void setPeij_th(String peij_th) {
		this.peij_th = peij_th;
	}
	public String getPeij_mc() {
		return peij_mc;
	}
	public void setPeij_mc(String peij_mc) {
		this.peij_mc = peij_mc;
	}
	public String getPeij_dw() {
		return peij_dw;
	}
	public void setPeij_dw(String peij_dw) {
		this.peij_dw = peij_dw;
	}
	public String getPeij_cx() {
		return peij_cx;
	}
	public void setPeij_cx(String peij_cx) {
		this.peij_cx = peij_cx;
	}
	public String getPeij_pp() {
		return peij_pp;
	}
	public void setPeij_pp(String peij_pp) {
		this.peij_pp = peij_pp;
	}
	public String getPeij_jk() {
		return peij_jk;
	}
	public void setPeij_jk(String peij_jk) {
		this.peij_jk = peij_jk;
	}
	public String getPeij_cd() {
		return peij_cd;
	}
	public void setPeij_cd(String peij_cd) {
		this.peij_cd = peij_cd;
	}
	public String getPeij_dj() {
		return peij_dj;
	}
	public void setPeij_dj(String peij_dj) {
		this.peij_dj = peij_dj;
	}
	public String getPeij_remark() {
		return peij_remark;
	}
	public void setPeij_remark(String peij_remark) {
		this.peij_remark = peij_remark;
	}
	public String getPeij_card_sl() {
		return peij_card_sl;
	}
	public void setPeij_card_sl(String peij_card_sl) {
		this.peij_card_sl = peij_card_sl;
	}
	public Date getDate_create() {
		return date_create;
	}
	public void setDate_create(Date date_create) {
		this.date_create = date_create;
	}
	public String getPeij_card_yqsl() {
		return peij_card_yqsl;
	}
	public void setPeij_card_yqsl(String peij_card_yqsl) {
		this.peij_card_yqsl = peij_card_yqsl;
	}
	
}
