package org.aotu.publics.eneity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;import java.util.Date;
 
/**
 * 
 * title:Kucshp_infoEntity
 * @Description:<库存商品信息表>
 * @author Zhang Yalong
 * @date 2017-4-24 上午11:40:35
 * @version: V1.0
 */
@Table(value="kucshp_info")
public class Kucshp_infoEntity{
	 @Column
	private double reco_no1;
	 @Column
	private String peij_no;
	 @Column
	private String peij_th;
	 @Column
	private String peij_mc;
	 @Column
	private String peij_en;
	 @Column
	private String peijlb_mc;
	 @Column
	private String peijlb_dm;
	 @Column
	private String peij_py;
	 @Column
	private String peij_dw;
	 @Column
	private String peij_jk;
	 @Column
	private String peij_cx;
	 @Column
	private String peij_cd;
	 @Column
	private String peij_pp;
	 @Column
	private String peij_th_ty;
	 @Column
	private String peij_cx_ty;
	 @Column
	private String peij_zl;
	 @Column
	private String peij_mb;
	 @Column
	private String peij_sc;
	 @Column
	private String peij_yc;
	 @Column
	private String peij_xh;
	 @Column
	private String peij_sh;
	 @Column
	private double peij_shlv;
	 @Column
	private String peij_bz;
	 @Column
	private String peij_tiaoma;
	 @Column
	private double peij_yxdate;
	 @Column
	private boolean peij_hide;
	 @Column
	private double peij_zhekou;
	 @Column
	private String peij_cl;
	 @Column
	private String kehu_dm;
	 @Column
	private String kehu_name;
	 @Column
	private Date LastModifyTime;
	 @Column
	private double dhjifen_sl;
	 @Column
	private String peij_mcwb;
	 @Column
	private String peij_cxpy;
	 @Column
	private String peij_cxwb;
	 @Column
	private String PjCanUsedInGongSi;
	 @Column
	private String PjCanUsedInGongSiMc;
	 @Column
	private double peij_tiji;
	 @Column
	private boolean peij_jinque;
	 @Column
	private double peij_tc;
	 @Column
	private int peij_tcfs;
	 @Column
	private boolean IsJiaMeng;
	 @Column
	private String peij_cx_typy;
	 @Column
	private String weixin_imageFile;
	 @Column
	private String tmp_peij_hw;
	 @Column
	private boolean flag_weixin;
	public void setReco_no1(double reco_no1){
	this.reco_no1=reco_no1;
	}
	public double getReco_no1(){
		return reco_no1;
	}
	public void setPeij_no(String peij_no){
	this.peij_no=peij_no;
	}
	public String getPeij_no(){
		return peij_no;
	}
	public void setPeij_th(String peij_th){
	this.peij_th=peij_th;
	}
	public String getPeij_th(){
		return peij_th;
	}
	public void setPeij_mc(String peij_mc){
	this.peij_mc=peij_mc;
	}
	public String getPeij_mc(){
		return peij_mc;
	}
	public void setPeij_en(String peij_en){
	this.peij_en=peij_en;
	}
	public String getPeij_en(){
		return peij_en;
	}
	public void setPeijlb_mc(String peijlb_mc){
	this.peijlb_mc=peijlb_mc;
	}
	public String getPeijlb_mc(){
		return peijlb_mc;
	}
	public void setPeijlb_dm(String peijlb_dm){
	this.peijlb_dm=peijlb_dm;
	}
	public String getPeijlb_dm(){
		return peijlb_dm;
	}
	public void setPeij_py(String peij_py){
	this.peij_py=peij_py;
	}
	public String getPeij_py(){
		return peij_py;
	}
	public void setPeij_dw(String peij_dw){
	this.peij_dw=peij_dw;
	}
	public String getPeij_dw(){
		return peij_dw;
	}
	public void setPeij_jk(String peij_jk){
	this.peij_jk=peij_jk;
	}
	public String getPeij_jk(){
		return peij_jk;
	}
	public void setPeij_cx(String peij_cx){
	this.peij_cx=peij_cx;
	}
	public String getPeij_cx(){
		return peij_cx;
	}
	public void setPeij_cd(String peij_cd){
	this.peij_cd=peij_cd;
	}
	public String getPeij_cd(){
		return peij_cd;
	}
	public void setPeij_pp(String peij_pp){
	this.peij_pp=peij_pp;
	}
	public String getPeij_pp(){
		return peij_pp;
	}
	public void setPeij_th_ty(String peij_th_ty){
	this.peij_th_ty=peij_th_ty;
	}
	public String getPeij_th_ty(){
		return peij_th_ty;
	}
	public void setPeij_cx_ty(String peij_cx_ty){
	this.peij_cx_ty=peij_cx_ty;
	}
	public String getPeij_cx_ty(){
		return peij_cx_ty;
	}
	public void setPeij_zl(String peij_zl){
	this.peij_zl=peij_zl;
	}
	public String getPeij_zl(){
		return peij_zl;
	}
	public void setPeij_mb(String peij_mb){
	this.peij_mb=peij_mb;
	}
	public String getPeij_mb(){
		return peij_mb;
	}
	public void setPeij_sc(String peij_sc){
	this.peij_sc=peij_sc;
	}
	public String getPeij_sc(){
		return peij_sc;
	}
	public void setPeij_yc(String peij_yc){
	this.peij_yc=peij_yc;
	}
	public String getPeij_yc(){
		return peij_yc;
	}
	public void setPeij_xh(String peij_xh){
	this.peij_xh=peij_xh;
	}
	public String getPeij_xh(){
		return peij_xh;
	}
	public void setPeij_sh(String peij_sh){
	this.peij_sh=peij_sh;
	}
	public String getPeij_sh(){
		return peij_sh;
	}
	public void setPeij_shlv(double peij_shlv){
	this.peij_shlv=peij_shlv;
	}
	public double getPeij_shlv(){
		return peij_shlv;
	}
	public void setPeij_bz(String peij_bz){
	this.peij_bz=peij_bz;
	}
	public String getPeij_bz(){
		return peij_bz;
	}
	public void setPeij_tiaoma(String peij_tiaoma){
	this.peij_tiaoma=peij_tiaoma;
	}
	public String getPeij_tiaoma(){
		return peij_tiaoma;
	}
	public void setPeij_yxdate(double peij_yxdate){
	this.peij_yxdate=peij_yxdate;
	}
	public double getPeij_yxdate(){
		return peij_yxdate;
	}
	public void setPeij_hide(boolean peij_hide){
	this.peij_hide=peij_hide;
	}
	public boolean getPeij_hide(){
		return peij_hide;
	}
	public void setPeij_zhekou(double peij_zhekou){
	this.peij_zhekou=peij_zhekou;
	}
	public double getPeij_zhekou(){
		return peij_zhekou;
	}
	public void setPeij_cl(String peij_cl){
	this.peij_cl=peij_cl;
	}
	public String getPeij_cl(){
		return peij_cl;
	}
	public void setKehu_dm(String kehu_dm){
	this.kehu_dm=kehu_dm;
	}
	public String getKehu_dm(){
		return kehu_dm;
	}
	public void setKehu_name(String kehu_name){
	this.kehu_name=kehu_name;
	}
	public String getKehu_name(){
		return kehu_name;
	}
	public void setLastModifyTime(Date LastModifyTime){
	this.LastModifyTime=LastModifyTime;
	}
	public Date getLastModifyTime(){
		return LastModifyTime;
	}
	public void setDhjifen_sl(double dhjifen_sl){
	this.dhjifen_sl=dhjifen_sl;
	}
	public double getDhjifen_sl(){
		return dhjifen_sl;
	}
	public void setPeij_mcwb(String peij_mcwb){
	this.peij_mcwb=peij_mcwb;
	}
	public String getPeij_mcwb(){
		return peij_mcwb;
	}
	public void setPeij_cxpy(String peij_cxpy){
	this.peij_cxpy=peij_cxpy;
	}
	public String getPeij_cxpy(){
		return peij_cxpy;
	}
	public void setPeij_cxwb(String peij_cxwb){
	this.peij_cxwb=peij_cxwb;
	}
	public String getPeij_cxwb(){
		return peij_cxwb;
	}
	public void setPjCanUsedInGongSi(String PjCanUsedInGongSi){
	this.PjCanUsedInGongSi=PjCanUsedInGongSi;
	}
	public String getPjCanUsedInGongSi(){
		return PjCanUsedInGongSi;
	}
	public void setPjCanUsedInGongSiMc(String PjCanUsedInGongSiMc){
	this.PjCanUsedInGongSiMc=PjCanUsedInGongSiMc;
	}
	public String getPjCanUsedInGongSiMc(){
		return PjCanUsedInGongSiMc;
	}
	public void setPeij_tiji(double peij_tiji){
	this.peij_tiji=peij_tiji;
	}
	public double getPeij_tiji(){
		return peij_tiji;
	}
	public void setPeij_jinque(boolean peij_jinque){
	this.peij_jinque=peij_jinque;
	}
	public boolean getPeij_jinque(){
		return peij_jinque;
	}
	public void setPeij_tc(double peij_tc){
	this.peij_tc=peij_tc;
	}
	public double getPeij_tc(){
		return peij_tc;
	}
	public void setPeij_tcfs(int peij_tcfs){
	this.peij_tcfs=peij_tcfs;
	}
	public int getPeij_tcfs(){
		return peij_tcfs;
	}
	public void setIsJiaMeng(boolean IsJiaMeng){
	this.IsJiaMeng=IsJiaMeng;
	}
	public boolean getIsJiaMeng(){
		return IsJiaMeng;
	}
	public void setPeij_cx_typy(String peij_cx_typy){
	this.peij_cx_typy=peij_cx_typy;
	}
	public String getPeij_cx_typy(){
		return peij_cx_typy;
	}
	public void setWeixin_imageFile(String weixin_imageFile){
	this.weixin_imageFile=weixin_imageFile;
	}
	public String getWeixin_imageFile(){
		return weixin_imageFile;
	}
	public void setTmp_peij_hw(String tmp_peij_hw){
	this.tmp_peij_hw=tmp_peij_hw;
	}
	public String getTmp_peij_hw(){
		return tmp_peij_hw;
	}
	public void setFlag_weixin(boolean flag_weixin){
	this.flag_weixin=flag_weixin;
	}
	public boolean getFlag_weixin(){
		return flag_weixin;
	}
}

