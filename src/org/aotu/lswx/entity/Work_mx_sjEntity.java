package org.aotu.lswx.entity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;import java.util.Date;
 

/**
 * 
 * title:Work_mx_sjEntity
 * @Description:<项目数据表>
 * @author Zhang Yalong
 * @date 2017-5-8 上午11:50:27
 * @version: V1.0
 */
@Table(value="work_mx_sj")
public class Work_mx_sjEntity{
	@Column
	private double reco_no;
	@Column
	private String work_no;
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
	private String wxxm_bz;
	@Column
	private boolean flag_import;
	@Column
	private boolean have_import;
	@Column
	private double wxxm_yje;
	@Column
	private String wxxm_jd;
	@Column
	private Date wxxm_pgrq;
	@Column
	private double wxxm_zk;
	@Column
	private boolean flag_xz;
	@Column
	private String kemu_dm;
	@Column
	private String kemu_mc;
	@Column
	private String AccountNo;
	@Column
	private String kemu_dmcb;
	@Column
	private String kemu_mccb;
	@Column
	private boolean Flag_WxxcCs;
	@Column
	private String wxxm_mxcx;
	@Column
	private boolean flag_CheckOK;
	@Column
	private String gw_mc;
	@Column
	private Date xche_kgrq;
	@Column
	private Date xche_sjwgrq;
	@Column
	private Date xche_ztrq;
	@Column
	private Date xche_jxrq;
	@Column
	private int xche_ztsj;
	@Column
	private Date xche_zdrq;
	@Column
	private Date xche_zdkgrq;
	@Column
	private int xche_zdsj;
	@Column
	private String Pause_mc;
	@Column
	private String stop_mc;
	@Column
	private String wxxm_Tpye;
	@Column
	private double wxxm_dj;
	@Column
	private int wxxm_tcfs;
	@Column
	private double wxxm_tc;
	@Column
	private double wxxm_tcje;
	@Column
	private Date wxxm_bjrq;
	public void setReco_no(double reco_no){
	this.reco_no=reco_no;
	}
	public double getReco_no(){
		return reco_no;
	}
	public void setWork_no(String work_no){
	this.work_no=work_no;
	}
	public String getWork_no(){
		return work_no;
	}
	public void setWxxm_no(String wxxm_no){
	this.wxxm_no=wxxm_no;
	}
	public String getWxxm_no(){
		return wxxm_no;
	}
	public void setWxxm_mc(String wxxm_mc){
	this.wxxm_mc=wxxm_mc;
	}
	public String getWxxm_mc(){
		return wxxm_mc;
	}
	public void setWxxm_gs(double wxxm_gs){
	this.wxxm_gs=wxxm_gs;
	}
	public double getWxxm_gs(){
		return wxxm_gs;
	}
	public void setWxxm_khgs(double wxxm_khgs){
	this.wxxm_khgs=wxxm_khgs;
	}
	public double getWxxm_khgs(){
		return wxxm_khgs;
	}
	public void setWxxm_cb(double wxxm_cb){
	this.wxxm_cb=wxxm_cb;
	}
	public double getWxxm_cb(){
		return wxxm_cb;
	}
	public void setWxxm_je(double wxxm_je){
	this.wxxm_je=wxxm_je;
	}
	public double getWxxm_je(){
		return wxxm_je;
	}
	public void setWxxm_ry(String wxxm_ry){
	this.wxxm_ry=wxxm_ry;
	}
	public String getWxxm_ry(){
		return wxxm_ry;
	}
	public void setWxxm_zt(String wxxm_zt){
	this.wxxm_zt=wxxm_zt;
	}
	public String getWxxm_zt(){
		return wxxm_zt;
	}
	public void setWxxm_bz(String wxxm_bz){
	this.wxxm_bz=wxxm_bz;
	}
	public String getWxxm_bz(){
		return wxxm_bz;
	}
	public void setFlag_import(boolean flag_import){
	this.flag_import=flag_import;
	}
	public boolean getFlag_import(){
		return flag_import;
	}
	public void setHave_import(boolean have_import){
	this.have_import=have_import;
	}
	public boolean getHave_import(){
		return have_import;
	}
	public void setWxxm_yje(double wxxm_yje){
	this.wxxm_yje=wxxm_yje;
	}
	public double getWxxm_yje(){
		return wxxm_yje;
	}
	public void setWxxm_jd(String wxxm_jd){
	this.wxxm_jd=wxxm_jd;
	}
	public String getWxxm_jd(){
		return wxxm_jd;
	}
	public void setWxxm_pgrq(Date wxxm_pgrq){
	this.wxxm_pgrq=wxxm_pgrq;
	}
	public Date getWxxm_pgrq(){
		return wxxm_pgrq;
	}
	public void setWxxm_zk(double wxxm_zk){
	this.wxxm_zk=wxxm_zk;
	}
	public double getWxxm_zk(){
		return wxxm_zk;
	}
	public void setFlag_xz(boolean flag_xz){
	this.flag_xz=flag_xz;
	}
	public boolean getFlag_xz(){
		return flag_xz;
	}
	public void setKemu_dm(String kemu_dm){
	this.kemu_dm=kemu_dm;
	}
	public String getKemu_dm(){
		return kemu_dm;
	}
	public void setKemu_mc(String kemu_mc){
	this.kemu_mc=kemu_mc;
	}
	public String getKemu_mc(){
		return kemu_mc;
	}
	public void setAccountNo(String AccountNo){
	this.AccountNo=AccountNo;
	}
	public String getAccountNo(){
		return AccountNo;
	}
	public void setKemu_dmcb(String kemu_dmcb){
	this.kemu_dmcb=kemu_dmcb;
	}
	public String getKemu_dmcb(){
		return kemu_dmcb;
	}
	public void setKemu_mccb(String kemu_mccb){
	this.kemu_mccb=kemu_mccb;
	}
	public String getKemu_mccb(){
		return kemu_mccb;
	}
	public void setFlag_WxxcCs(boolean Flag_WxxcCs){
	this.Flag_WxxcCs=Flag_WxxcCs;
	}
	public boolean getFlag_WxxcCs(){
		return Flag_WxxcCs;
	}
	public void setWxxm_mxcx(String wxxm_mxcx){
	this.wxxm_mxcx=wxxm_mxcx;
	}
	public String getWxxm_mxcx(){
		return wxxm_mxcx;
	}
	public void setFlag_CheckOK(boolean flag_CheckOK){
	this.flag_CheckOK=flag_CheckOK;
	}
	public boolean getFlag_CheckOK(){
		return flag_CheckOK;
	}
	public void setGw_mc(String gw_mc){
	this.gw_mc=gw_mc;
	}
	public String getGw_mc(){
		return gw_mc;
	}
	public void setXche_kgrq(Date xche_kgrq){
	this.xche_kgrq=xche_kgrq;
	}
	public Date getXche_kgrq(){
		return xche_kgrq;
	}
	public void setXche_sjwgrq(Date xche_sjwgrq){
	this.xche_sjwgrq=xche_sjwgrq;
	}
	public Date getXche_sjwgrq(){
		return xche_sjwgrq;
	}
	public void setXche_ztrq(Date xche_ztrq){
	this.xche_ztrq=xche_ztrq;
	}
	public Date getXche_ztrq(){
		return xche_ztrq;
	}
	public void setXche_jxrq(Date xche_jxrq){
	this.xche_jxrq=xche_jxrq;
	}
	public Date getXche_jxrq(){
		return xche_jxrq;
	}
	public void setXche_ztsj(int xche_ztsj){
	this.xche_ztsj=xche_ztsj;
	}
	public int getXche_ztsj(){
		return xche_ztsj;
	}
	public void setXche_zdrq(Date xche_zdrq){
	this.xche_zdrq=xche_zdrq;
	}
	public Date getXche_zdrq(){
		return xche_zdrq;
	}
	public void setXche_zdkgrq(Date xche_zdkgrq){
	this.xche_zdkgrq=xche_zdkgrq;
	}
	public Date getXche_zdkgrq(){
		return xche_zdkgrq;
	}
	public void setXche_zdsj(int xche_zdsj){
	this.xche_zdsj=xche_zdsj;
	}
	public int getXche_zdsj(){
		return xche_zdsj;
	}
	public void setPause_mc(String Pause_mc){
	this.Pause_mc=Pause_mc;
	}
	public String getPause_mc(){
		return Pause_mc;
	}
	public void setStop_mc(String stop_mc){
	this.stop_mc=stop_mc;
	}
	public String getStop_mc(){
		return stop_mc;
	}
	public void setWxxm_Tpye(String wxxm_Tpye){
	this.wxxm_Tpye=wxxm_Tpye;
	}
	public String getWxxm_Tpye(){
		return wxxm_Tpye;
	}
	public void setWxxm_dj(double wxxm_dj){
	this.wxxm_dj=wxxm_dj;
	}
	public double getWxxm_dj(){
		return wxxm_dj;
	}
	public void setWxxm_tcfs(int wxxm_tcfs){
	this.wxxm_tcfs=wxxm_tcfs;
	}
	public int getWxxm_tcfs(){
		return wxxm_tcfs;
	}
	public void setWxxm_tc(double wxxm_tc){
	this.wxxm_tc=wxxm_tc;
	}
	public double getWxxm_tc(){
		return wxxm_tc;
	}
	public void setWxxm_tcje(double wxxm_tcje){
	this.wxxm_tcje=wxxm_tcje;
	}
	public double getWxxm_tcje(){
		return wxxm_tcje;
	}
	public void setWxxm_bjrq(Date wxxm_bjrq){
	this.wxxm_bjrq=wxxm_bjrq;
	}
	public Date getWxxm_bjrq(){
		return wxxm_bjrq;
	}
}

