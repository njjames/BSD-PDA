/**
 * @author LHW
 *@time 2018年1月8日上午11:07:45
 * 
 */
package org.aotu.xiche.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author LHW
 *
 */
@Table(value="work_xiche_wxxm_sj")
public class Work_xiche_wxxm_sjEntity {
	@Column
	private long reco_no;
	@Column
	private String xc_no;
	@Column
	private String wxxm_no;
	@Column
	private String wxxm_mc;
	@Column
	private double wxxm_gs;
	@Column
	private double wxxm_khgs;
	@Column
	private double wxxm_cb;
	@Column
	private double wxxm_je;
	@Column
	private String wxxm_ry;
	@Column
	private String wxxm_zt;
	@Column
	private double wxxm_yje;
	@Column
	private double wxxm_zk;
	@Column
	private String wxxm_bz;
	@Column
	private double wxxm_dj;
	@Column
	private int wxxm_tcfs;
	@Column
	private double wxxm_tc;
	@Column
	private double wxxm_tcje;
	public long getReco_no() {
		return reco_no;
	}
	public void setReco_no(long reco_no) {
		this.reco_no = reco_no;
	}
	public String getXc_no() {
		return xc_no;
	}
	public void setXc_no(String xc_no) {
		this.xc_no = xc_no;
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
	public double getWxxm_gs() {
		return wxxm_gs;
	}
	public void setWxxm_gs(double wxxm_gs) {
		this.wxxm_gs = wxxm_gs;
	}
	public double getWxxm_khgs() {
		return wxxm_khgs;
	}
	public void setWxxm_khgs(double wxxm_khgs) {
		this.wxxm_khgs = wxxm_khgs;
	}
	public double getWxxm_cb() {
		return wxxm_cb;
	}
	public void setWxxm_cb(double wxxm_cb) {
		this.wxxm_cb = wxxm_cb;
	}
	public double getWxxm_je() {
		return wxxm_je;
	}
	public void setWxxm_je(double wxxm_je) {
		this.wxxm_je = wxxm_je;
	}
	public String getWxxm_ry() {
		return wxxm_ry;
	}
	public void setWxxm_ry(String wxxm_ry) {
		this.wxxm_ry = wxxm_ry;
	}
	public String getWxxm_zt() {
		return wxxm_zt;
	}
	public void setWxxm_zt(String wxxm_zt) {
		this.wxxm_zt = wxxm_zt;
	}
	public double getWxxm_yje() {
		return wxxm_yje;
	}
	public void setWxxm_yje(double wxxm_yje) {
		this.wxxm_yje = wxxm_yje;
	}
	public double getWxxm_zk() {
		return wxxm_zk;
	}
	public void setWxxm_zk(double wxxm_zk) {
		this.wxxm_zk = wxxm_zk;
	}
	public String getWxxm_bz() {
		return wxxm_bz;
	}
	public void setWxxm_bz(String wxxm_bz) {
		this.wxxm_bz = wxxm_bz;
	}
	public double getWxxm_dj() {
		return wxxm_dj;
	}
	public void setWxxm_dj(double wxxm_dj) {
		this.wxxm_dj = wxxm_dj;
	}
	public int getWxxm_tcfs() {
		return wxxm_tcfs;
	}
	public void setWxxm_tcfs(int wxxm_tcfs) {
		this.wxxm_tcfs = wxxm_tcfs;
	}
	public double getWxxm_tc() {
		return wxxm_tc;
	}
	public void setWxxm_tc(double wxxm_tc) {
		this.wxxm_tc = wxxm_tc;
	}
	public double getWxxm_tcje() {
		return wxxm_tcje;
	}
	public void setWxxm_tcje(double wxxm_tcje) {
		this.wxxm_tcje = wxxm_tcje;
	}
	
}
