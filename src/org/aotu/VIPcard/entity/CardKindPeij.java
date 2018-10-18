package org.aotu.VIPcard.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table(value="CardKindPeij")
public class CardKindPeij {
	@Id
	@Column
	private Integer reco_no;
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
	private String cardkind;
	public Integer getReco_no() {
		return reco_no;
	}
	public void setReco_no(Integer reco_no) {
		this.reco_no = reco_no;
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
	public String getCardkind() {
		return cardkind;
	}
	public void setCardkind(String cardkind) {
		this.cardkind = cardkind;
	}
	
	
}
