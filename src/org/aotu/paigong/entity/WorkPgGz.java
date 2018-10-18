package org.aotu.paigong.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value="work_pg_gz")
public class WorkPgGz {
	@Id
	@Column
	private Integer reco_no;
	@Name
	@Column
	private String work_no;
	@Column
	private String wxxm_no;	
	@Column
	private String reny_no;
	@Column
	private String reny_mc;
	@Column
	private String wxry_bm;
	@Column
	private String wxry_cj;
	@Column
	private String wxry_bz;
	@Column
	private double paig_khgs;
	@Column
	private double paig_khje;
	/*//修改项目名称没有字段 新加字段
	@Column
	private String wxxm_mc;*/
	@Column
	private Date paig_pgsj;
	@Column
	private String paig_bz;
	@Column
	private String wxry_pg;
	/*public String getWxxm_mc() {
		return wxxm_mc;
	}
	public void setWxxm_mc(String wxxm_mc) {
		this.wxxm_mc = wxxm_mc;
	}*/
	public Integer getReco_no() {
		return reco_no;
	}
	public void setReco_no(Integer reco_no) {
		this.reco_no = reco_no;
	}
	public String getWork_no() {
		return work_no;
	}
	public void setWork_no(String work_no) {
		this.work_no = work_no;
	}
	public String getWxxm_no() {
		return wxxm_no;
	}
	public void setWxxm_no(String wxxm_no) {
		this.wxxm_no = wxxm_no;
	}
	public String getReny_no() {
		return reny_no;
	}
	public void setReny_no(String reny_no) {
		this.reny_no = reny_no;
	}
	public String getReny_mc() {
		return reny_mc;
	}
	public void setReny_mc(String reny_mc) {
		this.reny_mc = reny_mc;
	}
	public String getWxry_bm() {
		return wxry_bm;
	}
	public void setWxry_bm(String wxry_bm) {
		this.wxry_bm = wxry_bm;
	}
	public String getWxry_cj() {
		return wxry_cj;
	}
	public void setWxry_cj(String wxry_cj) {
		this.wxry_cj = wxry_cj;
	}
	public String getWxry_bz() {
		return wxry_bz;
	}
	public void setWxry_bz(String wxry_bz) {
		this.wxry_bz = wxry_bz;
	}

	public double getPaig_khgs() {
		return paig_khgs;
	}
	public void setPaig_khgs(double paig_khgs) {
		this.paig_khgs = paig_khgs;
	}
	public double getPaig_khje() {
		return paig_khje;
	}
	public void setPaig_khje(double paig_khje) {
		this.paig_khje = paig_khje;
	}
	public String getPaig_bz() {
		return paig_bz;
	}
	public void setPaig_bz(String paig_bz) {
		this.paig_bz = paig_bz;
	}
	public String getWxry_pg() {
		return wxry_pg;
	}
	public void setWxry_pg(String wxry_pg) {
		this.wxry_pg = wxry_pg;
	}
	public Date getPaig_pgsj() {
		return paig_pgsj;
	}
	public void setPaig_pgsj(Date paig_pgsj) {
		this.paig_pgsj = paig_pgsj;
	}
	
	
}
