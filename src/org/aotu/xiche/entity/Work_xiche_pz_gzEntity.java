/**
 * @author LHW
 *@time 2018年1月8日上午8:54:10
 * 
 */
package org.aotu.xiche.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author LHW
 *
 */
@Table(value="work_xiche_pz_gz")
public class Work_xiche_pz_gzEntity {
	@Name
	@Column
	private String xc_no;
	@Column
	private Date xc_rq;
	@Column
	private String xc_lx;
	@Column
	private String che_no;
	@Column
	private String kehu_no;
	@Column
	private String kehu_mc;
	@Column
	private String xc_kno;
	@Column
	private double xc_ysje;
	@Column
	private String xc_jsfs;
	@Column
	private String xc_czy;
	@Column
	private String xc_bz;
	@Column
	private double xc_ssje;
	@Column
	private String card_no;
	@Column
	private String card_kind;
	@Column
	private boolean flag_cardjs;
	@Column
	private String zhifu_card_no;
	@Column
	private double zhifu_card_xj;
	@Column
	private double zhifu_card_je;
	@Column
	private int xc_usecard;
	@Column
	private String GongSiNo;
	@Column
	private String GongSiMc;
	@Column
	private String xc_fs;
	@Column
	private String xc_jbr;
	@Column
	private String reny_no;
	@Column
	private String reny_mc;
	@Column
	private Date gcsj;
	@Column
	private String che_cx;
	@Column
	private String che_vin;
	@Column
	private String kehu_dh;
	@Column
	private double xc_xcysje;
	@Column
	private double xc_xmysje;
	@Column
	private double card_itemrate;
	@Column
	private double wxxm_tcje_sum;
	
	
	public Date getGcsj() {
		return gcsj;
	}
	public void setGcsj(Date gcsj) {
		this.gcsj = gcsj;
	}
	public String getChe_cx() {
		return che_cx;
	}
	public void setChe_cx(String che_cx) {
		this.che_cx = che_cx;
	}
	public String getChe_vin() {
		return che_vin;
	}
	public void setChe_vin(String che_vin) {
		this.che_vin = che_vin;
	}
	public String getKehu_dh() {
		return kehu_dh;
	}
	public void setKehu_dh(String kehu_dh) {
		this.kehu_dh = kehu_dh;
	}
	public String getXc_no() {
		return xc_no;
	}
	public void setXc_no(String xc_no) {
		this.xc_no = xc_no;
	}
	public Date getXc_rq() {
		return xc_rq;
	}
	public void setXc_rq(Date xc_rq) {
		this.xc_rq = xc_rq;
	}
	public String getXc_lx() {
		return xc_lx;
	}
	public void setXc_lx(String xc_lx) {
		this.xc_lx = xc_lx;
	}
	public String getChe_no() {
		return che_no;
	}
	public void setChe_no(String che_no) {
		this.che_no = che_no;
	}
	public String getKehu_no() {
		return kehu_no;
	}
	public void setKehu_no(String kehu_no) {
		this.kehu_no = kehu_no;
	}
	public String getKehu_mc() {
		return kehu_mc;
	}
	public void setKehu_mc(String kehu_mc) {
		this.kehu_mc = kehu_mc;
	}
	public String getXc_kno() {
		return xc_kno;
	}
	public void setXc_kno(String xc_kno) {
		this.xc_kno = xc_kno;
	}
	public double getXc_ysje() {
		return xc_ysje;
	}
	public void setXc_ysje(double xc_ysje) {
		this.xc_ysje = xc_ysje;
	}
	public String getXc_jsfs() {
		return xc_jsfs;
	}
	public void setXc_jsfs(String xc_jsfs) {
		this.xc_jsfs = xc_jsfs;
	}
	public String getXc_czy() {
		return xc_czy;
	}
	public void setXc_czy(String xc_czy) {
		this.xc_czy = xc_czy;
	}
	public String getXc_bz() {
		return xc_bz;
	}
	public void setXc_bz(String xc_bz) {
		this.xc_bz = xc_bz;
	}
	public double getXc_ssje() {
		return xc_ssje;
	}
	public void setXc_ssje(double xc_ssje) {
		this.xc_ssje = xc_ssje;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCard_kind() {
		return card_kind;
	}
	public void setCard_kind(String card_kind) {
		this.card_kind = card_kind;
	}
	
	public boolean isFlag_cardjs() {
		return flag_cardjs;
	}
	public void setFlag_cardjs(boolean flag_cardjs) {
		this.flag_cardjs = flag_cardjs;
	}
	public String getZhifu_card_no() {
		return zhifu_card_no;
	}
	public void setZhifu_card_no(String zhifu_card_no) {
		this.zhifu_card_no = zhifu_card_no;
	}
	public double getZhifu_card_xj() {
		return zhifu_card_xj;
	}
	public void setZhifu_card_xj(double zhifu_card_xj) {
		this.zhifu_card_xj = zhifu_card_xj;
	}
	public double getZhifu_card_je() {
		return zhifu_card_je;
	}
	public void setZhifu_card_je(double zhifu_card_je) {
		this.zhifu_card_je = zhifu_card_je;
	}
	public int getXc_usecard() {
		return xc_usecard;
	}
	public void setXc_usecard(int xc_usecard) {
		this.xc_usecard = xc_usecard;
	}
	public String getGongSiNo() {
		return GongSiNo;
	}
	public void setGongSiNo(String gongSiNo) {
		GongSiNo = gongSiNo;
	}
	public String getGongSiMc() {
		return GongSiMc;
	}
	public void setGongSiMc(String gongSiMc) {
		GongSiMc = gongSiMc;
	}
	public String getXc_fs() {
		return xc_fs;
	}
	public void setXc_fs(String xc_fs) {
		this.xc_fs = xc_fs;
	}
	public String getXc_jbr() {
		return xc_jbr;
	}
	public void setXc_jbr(String xc_jbr) {
		this.xc_jbr = xc_jbr;
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
	
	public double getXc_xcysje() {
		return xc_xcysje;
	}
	public void setXc_xcysje(double xc_xcysje) {
		this.xc_xcysje = xc_xcysje;
	}
	public double getXc_xmysje() {
		return xc_xmysje;
	}
	public void setXc_xmysje(double xc_xmysje) {
		this.xc_xmysje = xc_xmysje;
	}
	public double getCard_itemrate() {
		return card_itemrate;
	}
	public void setCard_itemrate(double card_itemrate) {
		this.card_itemrate = card_itemrate;
	}
	public double getWxxm_tcje_sum() {
		return wxxm_tcje_sum;
	}
	public void setWxxm_tcje_sum(double wxxm_tcje_sum) {
		this.wxxm_tcje_sum = wxxm_tcje_sum;
	}
	
}
