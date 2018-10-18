package org.aotu.order.entity;

import java.sql.Blob;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "V_work_pz_gz")
public class Work_pz_gz_VEntity {
	@Name
	@Column
	private String work_no;
	@Column
	private String kehu_no;
	@Column
	private String kehu_mc;
	@Column
	private String kehu_xm;
	@Column
	private String kehu_dz;
	@Column
	private String kehu_yb;
	@Column
	private String kehu_dh;
	@Column
	private String kehu_sj;
	@Column
	private String che_no;
	@Column
	private String che_vin;
	@Column
	private String che_fd;
	@Column
	private String che_cx;
	@Column
	private String che_wxys;
	@Column
	private String xche_sfbz;
	@Column
	private double xche_sffl;
	@Column
	private int xche_lc;
	@Column
	private String xche_cy;
	@Column
	private Date xche_jdrq;
	@Column
	private double xche_gj;
	@Column
	private String xche_wxjd;
	@Column
	private Date xche_wgrq;
	@Column
	private String xche_pgcz;
	@Column
	private String xche_jlr;
	@Column
	private Date xche_jlrq;
	@Column
	private String xche_jsr;
	@Column
	private Date xche_jsrq;
	@Column
	private String xche_fpfs;
	@Column
	private double xche_fplv;
	@Column
	private double xche_rgf;
	@Column
	private double xche_rgsl;
	@Column
	private double xche_rgse;
	@Column
	private double xche_rgbh;
	@Column
	private double xche_clf;
	@Column
	private double xche_clsl;
	@Column
	private double xche_clse;
	@Column
	private double xche_clbh;
	@Column
	private double xche_glf;
	@Column
	private double xche_wjgf;
	@Column
	private double xche_wjgcb;
	@Column
	private double xche_qtf;
	@Column
	private double xche_yhje;
	@Column
	private double xche_ysje;
	@Column
	private double xche_rgcb;
	@Column
	private double xche_cb;
	@Column
	private String xche_zxr;
	@Column
	private String xche_qtxm;
	@Column
	private String xche_wjgx;
	@Column
	private String xche_yhyy;
	@Column
	private String xche_gdfl;
	@Column
	private String xche_wxfl;
	 @Column
	 private String xche_bz;
	public String getXche_bz() {
		return xche_bz;
	}

	public void setXche_bz(String xche_bz) {
		this.xche_bz = xche_bz;
	}

	@Column
	private String xche_cz;
	@Column
	private String xche_jb;
	@Column
	private double xche_hjje;
	@Column
	private double xche_ssje;
	@Column
	private double xche_yeje;
	@Column
	private String xche_jsfs;
	@Column
	private String dept_mc;
	@Column
	private String xche_fpno;
	@Column
	private double xche_kpje;
	@Column
	private double xche_kpse;
	@Column
	private boolean flag_fast;
	@Column
	private String card_no;
	@Column
	private String card_kind;
	@Column
	private boolean flag_cardjs;
	@Column
	private String zhifu_card_no;
	@Column
	private double zhifu_card_je;
	@Column
	private double xche_gj_wx;
	@Column
	private double xche_gj_ll;
	@Column
	private double zhifu_card_xj;
	@Column
	private double card_itemrate;
	@Column
	private double card_peijrate;
	@Column
	private int xche_state;
	@Column
	private double xche_dinge;
	@Column
	private String kehu_bxno;
	@Column
	private String kehu_bxmc;
	@Column
	private Date xche_sgsj;
	@Column
	private String xche_sgdd;
	@Column
	private String xche_sgyy;
	@Column
	private String GongSiNo;
	@Column
	private String GongSiMc;
	@Column
	private String xche_jcr;
	@Column
	private String xche_zgr;
	@Column
	private String xche_ywlx;
	@Column
	private Date yuyue_date;
	@Column
	private double xche_sjhk;
	@Column
	private double xche_wxxmlv;
	@Column
	private double xche_peijlv;
	@Column
	private double xche_wxxmglf;
	@Column
	private double xche_peijglf;
	@Column
	private Date yuyue_edate;
	@Column
	private String yuyue_gw;
	@Column
	private String yuyue_ry;
	@Column
	private String xche_wxjy;
	@Column
	private String xche_jcjg;
	@Column
	private String xche_jcyj;
	@Column
	private String che_fd_xh;
	@Column
	private String che_dp_xh;
	@Column
	private String che_pp;
	@Column
	private String che_pl;
	@Column
	private String che_pd;
	@Column
	private String che_nf;
	@Column
	private String che_bs_no;
	@Column
	private String che_bs;
	@Column
	private boolean Flag_UfPz;
	@Column
	private String xche_selected;
	@Column
	private String xche_wancheng;
	@Column
	private String yh_zhanghao;
	@Column
	private double xche_last_lc;
	@Column
	private Date xche_last_jdrq;
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
	private boolean Flag_UfPzcb;
	@Column
	private double xche_sbclf;
	@Column
	private double xche_sbgss;
	@Column
	private double xche_sbgsf;
	@Column
	private double xche_sbclcb;
	@Column
	private double xche_sbgsff;
	@Column
	private double xche_bxclf;
	@Column
	private double xche_bxgss;
	@Column
	private double xche_bxgsf;
	@Column
	private double xche_bxclcb;
	@Column
	private double xche_bxgscb;
	@Column
	private double temp_hk;
	@Column
	private double xche_yqhk;
	@Column
	private boolean flag_hk;
	@Column
	private double xche_xzrgf_je;
	@Column
	private double xche_xzclf_je;
	@Column
	private String cangk_dm;
	@Column
	private String che_zjno;
	@Column
	private double xche_jianyi_lc;
	@Column
	private String Using_Czy;
	@Column
	private int mainstate;
	@Column
	private String substate;
	@Column
	private boolean flag_IsCheck;
	@Column
	private String xche_CheckRen;
	@Column
	private boolean flag_CheckOK;
	@Column
	private Date xche_zjkgrq;
	@Column
	private Date xche_zjwgrq;
	@Column
	private Date xche_xckgrq;
	@Column
	private Date xche_xcwgrq;
	@Column
	private Date xche_kgrq;
	@Column
	private boolean flag_isxiche;
	@Column
	private int xche_wxsj;
	@Column
	private String yuyue_no;
	@Column
	private String gujia_no;
	@Column
	private Date gujia_imptrq;
	@Column
	private Blob che_biaoshi;
	@Column
	private double xche_haocai_je;
	@Column
	private double xche_haocai_cb;
	@Column
	private String xche_yjwgrq;
	@Column
	private double xche_cclc;
	@Column
	private double wxxm_tcje_sum;
	@Column
	private double peij_tcje_sum;
	@Column
	private double wxxm_zztcje_sum;
	@Column
	private double xche_saobmianshou;
	@Column
	private double xche_bxmianshou;
	@Column
	private double xche_zengsgsf;
	@Column
	private double xche_zengsclf;
	@Column
	private double xche_zengsmianshou;
	@Column
	private double xche_wxxm_yhje;
	@Column
	private double xche_peij_yhje;
	@Column
	private double work_jifen_sum;
	@Column
	private boolean flag_pad;
	@Column
	private Date gcsj;

	public void setWork_no(String work_no) {
		this.work_no = work_no;
	}

	public String getWork_no() {
		return work_no;
	}

	public void setKehu_no(String kehu_no) {
		this.kehu_no = kehu_no;
	}

	public String getKehu_no() {
		return kehu_no;
	}

	public void setKehu_mc(String kehu_mc) {
		this.kehu_mc = kehu_mc;
	}

	public String getKehu_mc() {
		return kehu_mc;
	}

	public void setKehu_xm(String kehu_xm) {
		this.kehu_xm = kehu_xm;
	}

	public String getKehu_xm() {
		return kehu_xm;
	}

	public void setKehu_dz(String kehu_dz) {
		this.kehu_dz = kehu_dz;
	}

	public String getKehu_dz() {
		return kehu_dz;
	}

	public void setKehu_yb(String kehu_yb) {
		this.kehu_yb = kehu_yb;
	}

	public String getKehu_yb() {
		return kehu_yb;
	}

	public void setKehu_dh(String kehu_dh) {
		this.kehu_dh = kehu_dh;
	}

	public String getKehu_dh() {
		return kehu_dh;
	}

	public void setKehu_sj(String kehu_sj) {
		this.kehu_sj = kehu_sj;
	}

	public String getKehu_sj() {
		return kehu_sj;
	}

	public void setChe_no(String che_no) {
		this.che_no = che_no;
	}

	public String getChe_no() {
		return che_no;
	}

	public void setChe_vin(String che_vin) {
		this.che_vin = che_vin;
	}

	public String getChe_vin() {
		return che_vin;
	}

	public void setChe_fd(String che_fd) {
		this.che_fd = che_fd;
	}

	public String getChe_fd() {
		return che_fd;
	}

	public void setChe_cx(String che_cx) {
		this.che_cx = che_cx;
	}

	public String getChe_cx() {
		return che_cx;
	}

	public void setChe_wxys(String che_wxys) {
		this.che_wxys = che_wxys;
	}

	public String getChe_wxys() {
		return che_wxys;
	}

	public void setXche_sfbz(String xche_sfbz) {
		this.xche_sfbz = xche_sfbz;
	}

	public String getXche_sfbz() {
		return xche_sfbz;
	}

	public void setXche_sffl(double xche_sffl) {
		this.xche_sffl = xche_sffl;
	}

	public double getXche_sffl() {
		return xche_sffl;
	}

	public int getXche_lc() {
		return xche_lc;
	}

	public void setXche_lc(int xche_lc) {
		this.xche_lc = xche_lc;
	}

	public void setXche_cy(String xche_cy) {
		this.xche_cy = xche_cy;
	}

	public String getXche_cy() {
		return xche_cy;
	}

	public void setXche_jdrq(Date xche_jdrq) {
		this.xche_jdrq = xche_jdrq;
	}

	public Date getXche_jdrq() {
		return xche_jdrq;
	}

	public void setXche_gj(double xche_gj) {
		this.xche_gj = xche_gj;
	}

	public double getXche_gj() {
		return xche_gj;
	}

	public void setXche_wxjd(String xche_wxjd) {
		this.xche_wxjd = xche_wxjd;
	}

	public String getXche_wxjd() {
		return xche_wxjd;
	}

	public void setXche_wgrq(Date xche_wgrq) {
		this.xche_wgrq = xche_wgrq;
	}

	public Date getXche_wgrq() {
		return xche_wgrq;
	}

	public void setXche_pgcz(String xche_pgcz) {
		this.xche_pgcz = xche_pgcz;
	}

	public String getXche_pgcz() {
		return xche_pgcz;
	}

	public void setXche_jlr(String xche_jlr) {
		this.xche_jlr = xche_jlr;
	}

	public String getXche_jlr() {
		return xche_jlr;
	}

	public void setXche_jlrq(Date xche_jlrq) {
		this.xche_jlrq = xche_jlrq;
	}

	public Date getXche_jlrq() {
		return xche_jlrq;
	}

	public void setXche_jsr(String xche_jsr) {
		this.xche_jsr = xche_jsr;
	}

	public String getXche_jsr() {
		return xche_jsr;
	}

	public void setXche_jsrq(Date xche_jsrq) {
		this.xche_jsrq = xche_jsrq;
	}

	public Date getXche_jsrq() {
		return xche_jsrq;
	}

	public void setXche_fpfs(String xche_fpfs) {
		this.xche_fpfs = xche_fpfs;
	}

	public String getXche_fpfs() {
		return xche_fpfs;
	}

	public void setXche_fplv(double xche_fplv) {
		this.xche_fplv = xche_fplv;
	}

	public double getXche_fplv() {
		return xche_fplv;
	}

	public void setXche_rgf(double xche_rgf) {
		this.xche_rgf = xche_rgf;
	}

	public double getXche_rgf() {
		return xche_rgf;
	}

	public void setXche_rgsl(double xche_rgsl) {
		this.xche_rgsl = xche_rgsl;
	}

	public double getXche_rgsl() {
		return xche_rgsl;
	}

	public void setXche_rgse(double xche_rgse) {
		this.xche_rgse = xche_rgse;
	}

	public double getXche_rgse() {
		return xche_rgse;
	}

	public void setXche_rgbh(double xche_rgbh) {
		this.xche_rgbh = xche_rgbh;
	}

	public double getXche_rgbh() {
		return xche_rgbh;
	}

	public void setXche_clf(double xche_clf) {
		this.xche_clf = xche_clf;
	}

	public double getXche_clf() {
		return xche_clf;
	}

	public void setXche_clsl(double xche_clsl) {
		this.xche_clsl = xche_clsl;
	}

	public double getXche_clsl() {
		return xche_clsl;
	}

	public void setXche_clse(double xche_clse) {
		this.xche_clse = xche_clse;
	}

	public double getXche_clse() {
		return xche_clse;
	}

	public void setXche_clbh(double xche_clbh) {
		this.xche_clbh = xche_clbh;
	}

	public double getXche_clbh() {
		return xche_clbh;
	}

	public void setXche_glf(double xche_glf) {
		this.xche_glf = xche_glf;
	}

	public double getXche_glf() {
		return xche_glf;
	}

	public void setXche_wjgf(double xche_wjgf) {
		this.xche_wjgf = xche_wjgf;
	}

	public double getXche_wjgf() {
		return xche_wjgf;
	}

	public void setXche_wjgcb(double xche_wjgcb) {
		this.xche_wjgcb = xche_wjgcb;
	}

	public double getXche_wjgcb() {
		return xche_wjgcb;
	}

	public void setXche_qtf(double xche_qtf) {
		this.xche_qtf = xche_qtf;
	}

	public double getXche_qtf() {
		return xche_qtf;
	}

	public void setXche_yhje(double xche_yhje) {
		this.xche_yhje = xche_yhje;
	}

	public double getXche_yhje() {
		return xche_yhje;
	}

	public void setXche_ysje(double xche_ysje) {
		this.xche_ysje = xche_ysje;
	}

	public double getXche_ysje() {
		return xche_ysje;
	}

	public void setXche_rgcb(double xche_rgcb) {
		this.xche_rgcb = xche_rgcb;
	}

	public double getXche_rgcb() {
		return xche_rgcb;
	}

	public void setXche_cb(double xche_cb) {
		this.xche_cb = xche_cb;
	}

	public double getXche_cb() {
		return xche_cb;
	}

	public void setXche_zxr(String xche_zxr) {
		this.xche_zxr = xche_zxr;
	}

	public String getXche_zxr() {
		return xche_zxr;
	}

	public void setXche_qtxm(String xche_qtxm) {
		this.xche_qtxm = xche_qtxm;
	}

	public String getXche_qtxm() {
		return xche_qtxm;
	}

	public void setXche_wjgx(String xche_wjgx) {
		this.xche_wjgx = xche_wjgx;
	}

	public String getXche_wjgx() {
		return xche_wjgx;
	}

	public void setXche_yhyy(String xche_yhyy) {
		this.xche_yhyy = xche_yhyy;
	}

	public String getXche_yhyy() {
		return xche_yhyy;
	}

	public void setXche_gdfl(String xche_gdfl) {
		this.xche_gdfl = xche_gdfl;
	}

	public String getXche_gdfl() {
		return xche_gdfl;
	}

	public void setXche_wxfl(String xche_wxfl) {
		this.xche_wxfl = xche_wxfl;
	}

	public String getXche_wxfl() {
		return xche_wxfl;
	}

	// public void setXche_bz(String xche_bz){
	// this.xche_bz=xche_bz.replaceAll("\"", "")+"3";
	// }
	// public String getXche_bz(){
	// return xche_bz.replaceAll("\"", "")+"3";
	// }
	public void setXche_cz(String xche_cz) {
		this.xche_cz = xche_cz;
	}

	public String getXche_cz() {
		return xche_cz;
	}

	public void setXche_jb(String xche_jb) {
		this.xche_jb = xche_jb;
	}

	public String getXche_jb() {
		return xche_jb;
	}

	public void setXche_hjje(double xche_hjje) {
		this.xche_hjje = xche_hjje;
	}

	public double getXche_hjje() {
		return xche_hjje;
	}

	public void setXche_ssje(double xche_ssje) {
		this.xche_ssje = xche_ssje;
	}

	public double getXche_ssje() {
		return xche_ssje;
	}

	public void setXche_yeje(double xche_yeje) {
		this.xche_yeje = xche_yeje;
	}

	public double getXche_yeje() {
		return xche_yeje;
	}

	public void setXche_jsfs(String xche_jsfs) {
		this.xche_jsfs = xche_jsfs;
	}

	public String getXche_jsfs() {
		return xche_jsfs;
	}

	public void setDept_mc(String dept_mc) {
		this.dept_mc = dept_mc;
	}

	public String getDept_mc() {
		return dept_mc;
	}

	public void setXche_fpno(String xche_fpno) {
		this.xche_fpno = xche_fpno;
	}

	public String getXche_fpno() {
		return xche_fpno;
	}

	public void setXche_kpje(double xche_kpje) {
		this.xche_kpje = xche_kpje;
	}

	public double getXche_kpje() {
		return xche_kpje;
	}

	public void setXche_kpse(double xche_kpse) {
		this.xche_kpse = xche_kpse;
	}

	public double getXche_kpse() {
		return xche_kpse;
	}

	public void setFlag_fast(boolean flag_fast) {
		this.flag_fast = flag_fast;
	}

	public boolean getFlag_fast() {
		return flag_fast;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_kind(String card_kind) {
		this.card_kind = card_kind;
	}

	public String getCard_kind() {
		return card_kind;
	}

	public void setFlag_cardjs(boolean flag_cardjs) {
		this.flag_cardjs = flag_cardjs;
	}

	public boolean getFlag_cardjs() {
		return flag_cardjs;
	}

	public void setZhifu_card_no(String zhifu_card_no) {
		this.zhifu_card_no = zhifu_card_no;
	}

	public String getZhifu_card_no() {
		return zhifu_card_no;
	}

	public void setZhifu_card_je(double zhifu_card_je) {
		this.zhifu_card_je = zhifu_card_je;
	}

	public double getZhifu_card_je() {
		return zhifu_card_je;
	}

	public void setXche_gj_wx(double xche_gj_wx) {
		this.xche_gj_wx = xche_gj_wx;
	}

	public double getXche_gj_wx() {
		return xche_gj_wx;
	}

	public void setXche_gj_ll(double xche_gj_ll) {
		this.xche_gj_ll = xche_gj_ll;
	}

	public double getXche_gj_ll() {
		return xche_gj_ll;
	}

	public void setZhifu_card_xj(double zhifu_card_xj) {
		this.zhifu_card_xj = zhifu_card_xj;
	}

	public double getZhifu_card_xj() {
		return zhifu_card_xj;
	}

	public void setCard_itemrate(double card_itemrate) {
		this.card_itemrate = card_itemrate;
	}

	public double getCard_itemrate() {
		return card_itemrate;
	}

	public void setCard_peijrate(double card_peijrate) {
		this.card_peijrate = card_peijrate;
	}

	public double getCard_peijrate() {
		return card_peijrate;
	}

	public void setXche_state(int xche_state) {
		this.xche_state = xche_state;
	}

	public int getXche_state() {
		return xche_state;
	}

	public void setXche_dinge(double xche_dinge) {
		this.xche_dinge = xche_dinge;
	}

	public double getXche_dinge() {
		return xche_dinge;
	}

	public void setKehu_bxno(String kehu_bxno) {
		this.kehu_bxno = kehu_bxno;
	}

	public String getKehu_bxno() {
		return kehu_bxno;
	}

	public void setKehu_bxmc(String kehu_bxmc) {
		this.kehu_bxmc = kehu_bxmc;
	}

	public String getKehu_bxmc() {
		return kehu_bxmc;
	}

	public void setXche_sgsj(Date xche_sgsj) {
		this.xche_sgsj = xche_sgsj;
	}

	public Date getXche_sgsj() {
		return xche_sgsj;
	}

	public void setXche_sgdd(String xche_sgdd) {
		this.xche_sgdd = xche_sgdd;
	}

	public String getXche_sgdd() {
		return xche_sgdd;
	}

	public void setXche_sgyy(String xche_sgyy) {
		this.xche_sgyy = xche_sgyy;
	}

	public String getXche_sgyy() {
		return xche_sgyy;
	}

	public void setGongSiNo(String GongSiNo) {
		this.GongSiNo = GongSiNo;
	}

	public String getGongSiNo() {
		return GongSiNo;
	}

	public void setGongSiMc(String GongSiMc) {
		this.GongSiMc = GongSiMc;
	}

	public String getGongSiMc() {
		return GongSiMc;
	}

	public void setXche_jcr(String xche_jcr) {
		this.xche_jcr = xche_jcr;
	}

	public String getXche_jcr() {
		return xche_jcr;
	}

	public void setXche_zgr(String xche_zgr) {
		this.xche_zgr = xche_zgr;
	}

	public String getXche_zgr() {
		return xche_zgr;
	}

	public void setXche_ywlx(String xche_ywlx) {
		this.xche_ywlx = xche_ywlx;
	}

	public String getXche_ywlx() {
		return xche_ywlx;
	}

	public void setYuyue_date(Date yuyue_date) {
		this.yuyue_date = yuyue_date;
	}

	public Date getYuyue_date() {
		return yuyue_date;
	}

	public void setXche_sjhk(double xche_sjhk) {
		this.xche_sjhk = xche_sjhk;
	}

	public double getXche_sjhk() {
		return xche_sjhk;
	}

	public void setXche_wxxmlv(double xche_wxxmlv) {
		this.xche_wxxmlv = xche_wxxmlv;
	}

	public double getXche_wxxmlv() {
		return xche_wxxmlv;
	}

	public void setXche_peijlv(double xche_peijlv) {
		this.xche_peijlv = xche_peijlv;
	}

	public double getXche_peijlv() {
		return xche_peijlv;
	}

	public void setXche_wxxmglf(double xche_wxxmglf) {
		this.xche_wxxmglf = xche_wxxmglf;
	}

	public double getXche_wxxmglf() {
		return xche_wxxmglf;
	}

	public void setXche_peijglf(double xche_peijglf) {
		this.xche_peijglf = xche_peijglf;
	}

	public double getXche_peijglf() {
		return xche_peijglf;
	}

	public void setYuyue_edate(Date yuyue_edate) {
		this.yuyue_edate = yuyue_edate;
	}

	public Date getYuyue_edate() {
		return yuyue_edate;
	}

	public void setYuyue_gw(String yuyue_gw) {
		this.yuyue_gw = yuyue_gw;
	}

	public String getYuyue_gw() {
		return yuyue_gw;
	}

	public void setYuyue_ry(String yuyue_ry) {
		this.yuyue_ry = yuyue_ry;
	}

	public String getYuyue_ry() {
		return yuyue_ry;
	}

	public void setXche_wxjy(String xche_wxjy) {
		this.xche_wxjy = xche_wxjy;
	}

	public String getXche_wxjy() {
		return xche_wxjy;
	}

	public void setXche_jcjg(String xche_jcjg) {
		this.xche_jcjg = xche_jcjg;
	}

	public String getXche_jcjg() {
		return xche_jcjg;
	}

	public void setXche_jcyj(String xche_jcyj) {
		this.xche_jcyj = xche_jcyj;
	}

	public String getXche_jcyj() {
		return xche_jcyj;
	}

	public void setChe_fd_xh(String che_fd_xh) {
		this.che_fd_xh = che_fd_xh;
	}

	public String getChe_fd_xh() {
		return che_fd_xh;
	}

	public void setChe_dp_xh(String che_dp_xh) {
		this.che_dp_xh = che_dp_xh;
	}

	public String getChe_dp_xh() {
		return che_dp_xh;
	}

	public void setChe_pp(String che_pp) {
		this.che_pp = che_pp;
	}

	public String getChe_pp() {
		return che_pp;
	}

	public void setChe_pl(String che_pl) {
		this.che_pl = che_pl;
	}

	public String getChe_pl() {
		return che_pl;
	}

	public void setChe_pd(String che_pd) {
		this.che_pd = che_pd;
	}

	public String getChe_pd() {
		return che_pd;
	}

	public void setChe_nf(String che_nf) {
		this.che_nf = che_nf;
	}

	public String getChe_nf() {
		return che_nf;
	}

	public void setChe_bs_no(String che_bs_no) {
		this.che_bs_no = che_bs_no;
	}

	public String getChe_bs_no() {
		return che_bs_no;
	}

	public void setChe_bs(String che_bs) {
		this.che_bs = che_bs;
	}

	public String getChe_bs() {
		return che_bs;
	}

	public void setFlag_UfPz(boolean Flag_UfPz) {
		this.Flag_UfPz = Flag_UfPz;
	}

	public boolean getFlag_UfPz() {
		return Flag_UfPz;
	}

	public void setXche_selected(String xche_selected) {
		this.xche_selected = xche_selected;
	}

	public String getXche_selected() {
		return xche_selected;
	}

	public void setXche_wancheng(String xche_wancheng) {
		this.xche_wancheng = xche_wancheng;
	}

	public String getXche_wancheng() {
		return xche_wancheng;
	}

	public void setYh_zhanghao(String yh_zhanghao) {
		this.yh_zhanghao = yh_zhanghao;
	}

	public String getYh_zhanghao() {
		return yh_zhanghao;
	}

	public void setXche_last_lc(double xche_last_lc) {
		this.xche_last_lc = xche_last_lc;
	}

	public double getXche_last_lc() {
		return xche_last_lc;
	}

	public void setXche_last_jdrq(Date xche_last_jdrq) {
		this.xche_last_jdrq = xche_last_jdrq;
	}

	public Date getXche_last_jdrq() {
		return xche_last_jdrq;
	}

	public void setKemu_dm(String kemu_dm) {
		this.kemu_dm = kemu_dm;
	}

	public String getKemu_dm() {
		return kemu_dm;
	}

	public void setKemu_mc(String kemu_mc) {
		this.kemu_mc = kemu_mc;
	}

	public String getKemu_mc() {
		return kemu_mc;
	}

	public void setAccountNo(String AccountNo) {
		this.AccountNo = AccountNo;
	}

	public String getAccountNo() {
		return AccountNo;
	}

	public void setKemu_dmcb(String kemu_dmcb) {
		this.kemu_dmcb = kemu_dmcb;
	}

	public String getKemu_dmcb() {
		return kemu_dmcb;
	}

	public void setKemu_mccb(String kemu_mccb) {
		this.kemu_mccb = kemu_mccb;
	}

	public String getKemu_mccb() {
		return kemu_mccb;
	}

	public void setFlag_UfPzcb(boolean Flag_UfPzcb) {
		this.Flag_UfPzcb = Flag_UfPzcb;
	}

	public boolean getFlag_UfPzcb() {
		return Flag_UfPzcb;
	}

	public void setXche_sbclf(double xche_sbclf) {
		this.xche_sbclf = xche_sbclf;
	}

	public double getXche_sbclf() {
		return xche_sbclf;
	}

	public void setXche_sbgss(double xche_sbgss) {
		this.xche_sbgss = xche_sbgss;
	}

	public double getXche_sbgss() {
		return xche_sbgss;
	}

	public void setXche_sbgsf(double xche_sbgsf) {
		this.xche_sbgsf = xche_sbgsf;
	}

	public double getXche_sbgsf() {
		return xche_sbgsf;
	}

	public void setXche_sbclcb(double xche_sbclcb) {
		this.xche_sbclcb = xche_sbclcb;
	}

	public double getXche_sbclcb() {
		return xche_sbclcb;
	}

	public void setXche_sbgsff(double xche_sbgsff) {
		this.xche_sbgsff = xche_sbgsff;
	}

	public double getXche_sbgsff() {
		return xche_sbgsff;
	}

	public void setXche_bxclf(double xche_bxclf) {
		this.xche_bxclf = xche_bxclf;
	}

	public double getXche_bxclf() {
		return xche_bxclf;
	}

	public void setXche_bxgss(double xche_bxgss) {
		this.xche_bxgss = xche_bxgss;
	}

	public double getXche_bxgss() {
		return xche_bxgss;
	}

	public void setXche_bxgsf(double xche_bxgsf) {
		this.xche_bxgsf = xche_bxgsf;
	}

	public double getXche_bxgsf() {
		return xche_bxgsf;
	}

	public void setXche_bxclcb(double xche_bxclcb) {
		this.xche_bxclcb = xche_bxclcb;
	}

	public double getXche_bxclcb() {
		return xche_bxclcb;
	}

	public void setXche_bxgscb(double xche_bxgscb) {
		this.xche_bxgscb = xche_bxgscb;
	}

	public double getXche_bxgscb() {
		return xche_bxgscb;
	}

	public void setTemp_hk(double temp_hk) {
		this.temp_hk = temp_hk;
	}

	public double getTemp_hk() {
		return temp_hk;
	}

	public void setXche_yqhk(double xche_yqhk) {
		this.xche_yqhk = xche_yqhk;
	}

	public double getXche_yqhk() {
		return xche_yqhk;
	}

	public void setFlag_hk(boolean flag_hk) {
		this.flag_hk = flag_hk;
	}

	public boolean getFlag_hk() {
		return flag_hk;
	}

	public void setXche_xzrgf_je(double xche_xzrgf_je) {
		this.xche_xzrgf_je = xche_xzrgf_je;
	}

	public double getXche_xzrgf_je() {
		return xche_xzrgf_je;
	}

	public void setXche_xzclf_je(double xche_xzclf_je) {
		this.xche_xzclf_je = xche_xzclf_je;
	}

	public double getXche_xzclf_je() {
		return xche_xzclf_je;
	}

	public void setCangk_dm(String cangk_dm) {
		this.cangk_dm = cangk_dm;
	}

	public String getCangk_dm() {
		return cangk_dm;
	}

	public void setChe_zjno(String che_zjno) {
		this.che_zjno = che_zjno;
	}

	public String getChe_zjno() {
		return che_zjno;
	}

	public void setXche_jianyi_lc(double xche_jianyi_lc) {
		this.xche_jianyi_lc = xche_jianyi_lc;
	}

	public double getXche_jianyi_lc() {
		return xche_jianyi_lc;
	}

	public void setUsing_Czy(String Using_Czy) {
		this.Using_Czy = Using_Czy;
	}

	public String getUsing_Czy() {
		return Using_Czy;
	}

	public void setMainstate(int mainstate) {
		this.mainstate = mainstate;
	}

	public int getMainstate() {
		return mainstate;
	}

	public void setSubstate(String substate) {
		this.substate = substate;
	}

	public String getSubstate() {
		return substate;
	}

	public void setFlag_IsCheck(boolean flag_IsCheck) {
		this.flag_IsCheck = flag_IsCheck;
	}

	public boolean getFlag_IsCheck() {
		return flag_IsCheck;
	}

	public void setXche_CheckRen(String xche_CheckRen) {
		this.xche_CheckRen = xche_CheckRen;
	}

	public String getXche_CheckRen() {
		return xche_CheckRen;
	}

	public void setFlag_CheckOK(boolean flag_CheckOK) {
		this.flag_CheckOK = flag_CheckOK;
	}

	public boolean getFlag_CheckOK() {
		return flag_CheckOK;
	}

	public void setXche_zjkgrq(Date xche_zjkgrq) {
		this.xche_zjkgrq = xche_zjkgrq;
	}

	public Date getXche_zjkgrq() {
		return xche_zjkgrq;
	}

	public void setXche_zjwgrq(Date xche_zjwgrq) {
		this.xche_zjwgrq = xche_zjwgrq;
	}

	public Date getXche_zjwgrq() {
		return xche_zjwgrq;
	}

	public void setXche_xckgrq(Date xche_xckgrq) {
		this.xche_xckgrq = xche_xckgrq;
	}

	public Date getXche_xckgrq() {
		return xche_xckgrq;
	}

	public void setXche_xcwgrq(Date xche_xcwgrq) {
		this.xche_xcwgrq = xche_xcwgrq;
	}

	public Date getXche_xcwgrq() {
		return xche_xcwgrq;
	}

	public void setXche_kgrq(Date xche_kgrq) {
		this.xche_kgrq = xche_kgrq;
	}

	public Date getXche_kgrq() {
		return xche_kgrq;
	}

	public void setFlag_isxiche(boolean flag_isxiche) {
		this.flag_isxiche = flag_isxiche;
	}

	public boolean getFlag_isxiche() {
		return flag_isxiche;
	}

	public void setXche_wxsj(int xche_wxsj) {
		this.xche_wxsj = xche_wxsj;
	}

	public int getXche_wxsj() {
		return xche_wxsj;
	}

	public void setYuyue_no(String yuyue_no) {
		this.yuyue_no = yuyue_no;
	}

	public String getYuyue_no() {
		return yuyue_no;
	}

	public void setGujia_no(String gujia_no) {
		this.gujia_no = gujia_no;
	}

	public String getGujia_no() {
		return gujia_no;
	}

	public void setGujia_imptrq(Date gujia_imptrq) {
		this.gujia_imptrq = gujia_imptrq;
	}

	public Date getGujia_imptrq() {
		return gujia_imptrq;
	}

	public void setChe_biaoshi(Blob che_biaoshi) {
		this.che_biaoshi = che_biaoshi;
	}

	public Blob getChe_biaoshi() {
		return che_biaoshi;
	}

	public void setXche_haocai_je(double xche_haocai_je) {
		this.xche_haocai_je = xche_haocai_je;
	}

	public double getXche_haocai_je() {
		return xche_haocai_je;
	}

	public void setXche_haocai_cb(double xche_haocai_cb) {
		this.xche_haocai_cb = xche_haocai_cb;
	}

	public double getXche_haocai_cb() {
		return xche_haocai_cb;
	}

	public void setXche_yjwgrq(String xche_yjwgrq) {
		this.xche_yjwgrq = xche_yjwgrq;
	}

	public String getXche_yjwgrq() {
		return xche_yjwgrq;
	}

	public void setXche_cclc(double xche_cclc) {
		this.xche_cclc = xche_cclc;
	}

	public double getXche_cclc() {
		return xche_cclc;
	}

	public void setWxxm_tcje_sum(double wxxm_tcje_sum) {
		this.wxxm_tcje_sum = wxxm_tcje_sum;
	}

	public double getWxxm_tcje_sum() {
		return wxxm_tcje_sum;
	}

	public void setPeij_tcje_sum(double peij_tcje_sum) {
		this.peij_tcje_sum = peij_tcje_sum;
	}

	public double getPeij_tcje_sum() {
		return peij_tcje_sum;
	}

	public void setWxxm_zztcje_sum(double wxxm_zztcje_sum) {
		this.wxxm_zztcje_sum = wxxm_zztcje_sum;
	}

	public double getWxxm_zztcje_sum() {
		return wxxm_zztcje_sum;
	}

	public void setXche_saobmianshou(double xche_saobmianshou) {
		this.xche_saobmianshou = xche_saobmianshou;
	}

	public double getXche_saobmianshou() {
		return xche_saobmianshou;
	}

	public void setXche_bxmianshou(double xche_bxmianshou) {
		this.xche_bxmianshou = xche_bxmianshou;
	}

	public double getXche_bxmianshou() {
		return xche_bxmianshou;
	}

	public void setXche_zengsgsf(double xche_zengsgsf) {
		this.xche_zengsgsf = xche_zengsgsf;
	}

	public double getXche_zengsgsf() {
		return xche_zengsgsf;
	}

	public void setXche_zengsclf(double xche_zengsclf) {
		this.xche_zengsclf = xche_zengsclf;
	}

	public double getXche_zengsclf() {
		return xche_zengsclf;
	}

	public void setXche_zengsmianshou(double xche_zengsmianshou) {
		this.xche_zengsmianshou = xche_zengsmianshou;
	}

	public double getXche_zengsmianshou() {
		return xche_zengsmianshou;
	}

	public void setXche_wxxm_yhje(double xche_wxxm_yhje) {
		this.xche_wxxm_yhje = xche_wxxm_yhje;
	}

	public double getXche_wxxm_yhje() {
		return xche_wxxm_yhje;
	}

	public void setXche_peij_yhje(double xche_peij_yhje) {
		this.xche_peij_yhje = xche_peij_yhje;
	}

	public double getXche_peij_yhje() {
		return xche_peij_yhje;
	}

	public void setWork_jifen_sum(double work_jifen_sum) {
		this.work_jifen_sum = work_jifen_sum;
	}

	public double getWork_jifen_sum() {
		return work_jifen_sum;
	}

	public void setFlag_pad(boolean flag_pad) {
		this.flag_pad = flag_pad;
	}

	public boolean getFlag_pad() {
		return flag_pad;
	}

	public Date getGcsj() {
		return gcsj;
	}

	public void setGcsj(Date gcsj) {
		this.gcsj = gcsj;
	}

}
