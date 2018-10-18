package org.aotu.publics.eneity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table; 


/**
 * title:Work_weixiu_smEntity
 * @Description:<维修内容>
 * @author Zhang Yalong
 * @date 2017-4-18 上午10:27:59
 * @version: V1.0
 */
@Table(value="work_weixiu_sm")
public class Work_weixiu_smEntity{ 
	 @Column 
	private double id;
	 @Name
	 @Column
	private String wxxm_no;
	 @Column
	private String wxxm_mc;
	 @Column
	private String wxxm_py;
	 @Column 
	private double wxxm_gs;
	 @Column
	private double wxxm_khgs;
	 @Column
	private int wxxm_byzq;
	 @Column
	private String wxxm_bz;
	 @Column
	private boolean wxxm_by;
	 @Column
	private double wxxm_zgdj;
	 @Column
	private double wxxm_zddj;
	 @Column
	private double wxxm_dj;
	 @Column
	private String wxxm_jffs;
	 @Column
	private String wxxm_lbdm;
	 @Column
	private String wxxm_lbmc;
	 @Column
	private String wxxm_cx;
	 @Column
	private String wxxm_cxpy;
	 @Column
	private String wxxm_wb;
	 @Column
	private String wxxm_cxwb;
	 @Column
	private double wxxm_cb;
	 @Column
	private double wxxm_tc;
	 @Column
	private int wxxm_tcfs;
	 @Column
	private int wxxm_zztcfs;
	 @Column
	private double wxxm_zztc;
	 @Column
	private boolean flag_wjg;
	public double getId() {
		return id;
	}
	public void setId(double id) {
		this.id = id;
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
	public String getWxxm_py() {
		return wxxm_py;
	}
	public void setWxxm_py(String wxxm_py) {
		this.wxxm_py = wxxm_py;
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
	public int getWxxm_byzq() {
		return wxxm_byzq;
	}
	public void setWxxm_byzq(int wxxm_byzq) {
		this.wxxm_byzq = wxxm_byzq;
	}
	public String getWxxm_bz() {
		return wxxm_bz;
	}
	public void setWxxm_bz(String wxxm_bz) {
		this.wxxm_bz = wxxm_bz;
	}
	public boolean isWxxm_by() {
		return wxxm_by;
	}
	public void setWxxm_by(boolean wxxm_by) {
		this.wxxm_by = wxxm_by;
	}
	public double getWxxm_zgdj() {
		return wxxm_zgdj;
	}
	public void setWxxm_zgdj(double wxxm_zgdj) {
		this.wxxm_zgdj = wxxm_zgdj;
	}
	public double getWxxm_zddj() {
		return wxxm_zddj;
	}
	public void setWxxm_zddj(double wxxm_zddj) {
		this.wxxm_zddj = wxxm_zddj;
	}
	public double getWxxm_dj() {
		return wxxm_dj;
	}
	public void setWxxm_dj(double wxxm_dj) {
		this.wxxm_dj = wxxm_dj;
	}
	public String getWxxm_jffs() {
		return wxxm_jffs;
	}
	public void setWxxm_jffs(String wxxm_jffs) {
		this.wxxm_jffs = wxxm_jffs;
	}
	public String getWxxm_lbdm() {
		return wxxm_lbdm;
	}
	public void setWxxm_lbdm(String wxxm_lbdm) {
		this.wxxm_lbdm = wxxm_lbdm;
	}
	public String getWxxm_lbmc() {
		return wxxm_lbmc;
	}
	public void setWxxm_lbmc(String wxxm_lbmc) {
		this.wxxm_lbmc = wxxm_lbmc;
	}
	public String getWxxm_cx() {
		return wxxm_cx;
	}
	public void setWxxm_cx(String wxxm_cx) {
		this.wxxm_cx = wxxm_cx;
	}
	public String getWxxm_cxpy() {
		return wxxm_cxpy;
	}
	public void setWxxm_cxpy(String wxxm_cxpy) {
		this.wxxm_cxpy = wxxm_cxpy;
	}
	public String getWxxm_wb() {
		return wxxm_wb;
	}
	public void setWxxm_wb(String wxxm_wb) {
		this.wxxm_wb = wxxm_wb;
	}
	public String getWxxm_cxwb() {
		return wxxm_cxwb;
	}
	public void setWxxm_cxwb(String wxxm_cxwb) {
		this.wxxm_cxwb = wxxm_cxwb;
	}
	public double getWxxm_cb() {
		return wxxm_cb;
	}
	public void setWxxm_cb(double wxxm_cb) {
		this.wxxm_cb = wxxm_cb;
	}
	public double getWxxm_tc() {
		return wxxm_tc;
	}
	public void setWxxm_tc(double wxxm_tc) {
		this.wxxm_tc = wxxm_tc;
	}
	public int getWxxm_tcfs() {
		return wxxm_tcfs;
	}
	public void setWxxm_tcfs(int wxxm_tcfs) {
		this.wxxm_tcfs = wxxm_tcfs;
	}
	public int getWxxm_zztcfs() {
		return wxxm_zztcfs;
	}
	public void setWxxm_zztcfs(int wxxm_zztcfs) {
		this.wxxm_zztcfs = wxxm_zztcfs;
	}
	public double getWxxm_zztc() {
		return wxxm_zztc;
	}
	public void setWxxm_zztc(double wxxm_zztc) {
		this.wxxm_zztc = wxxm_zztc;
	}
	public boolean isFlag_wjg() {
		return flag_wjg;
	}
	public void setFlag_wjg(boolean flag_wjg) {
		this.flag_wjg = flag_wjg;
	}
	 
}

