package org.aotu.publics.eneity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;
 
/**
 * 
 * title:Work_cheliang_smEntity
 * @Description:<车辆档案表>
 * @author Zhang Yalong
 * @date 2017-5-2 下午2:08:07
 * @version: V1.0
 */
@Table(value="work_cheliang_sm")
public class Work_cheliang_smEntity{
	@Id
	 @Column
	private Integer id;
	@Name
	 @Column
	private String che_no;
	 @Column
	private String kehu_no;
	 @Column
	private String che_vin;
	 @Column
	private String che_fd;
	 @Column
	private String che_bs;
	 @Column
	private String che_cx;
	 @Column
	private Date che_ccrq;
	 @Column
	private Date che_gcrq;
	 @Column
	private String che_wxys;
	 @Column
	private String che_fdjxh;
	 @Column
	private String che_sj;
	 @Column
	private String che_zjno;
	 @Column
	private Date che_djrq;
	 @Column
	private String che_sfbz;
	 @Column
	private double che_sffl;
	 @Column
	private String che_bz;
	 @Column
	private String card_no;
	 @Column
	private String card_kind;
	 @Column
	private String kehu_bxno;
	 @Column
	private String kehu_bxmc;
	 @Column
	private String GongSiNo;
	 @Column
	private String GongSiMc;
	 @Column
	private String che_cxpy;
	 @Column
	private String che_cxwb;
	 @Column
	private String che_py;
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
	private double che_rjlc;
	 @Column
	private Date che_next_byrq;
	 @Column
	private String chex_dm;
	 @Column
	private String che_wxxm_jffs;
	 @Column
	private String che_peij_jffs;
	 @Column
	private String che_jiyouge_xh;
	 @Column
	private String weixin_OpenId;
	 @Column
	private Date ns_dqrq;
	 @Column
	private Date bx_dqrq;
	 @Column
	private String che_jiyou_xh;
	 @Column
	private Date che_jianche_dqrq;
	 @Column
	private Date che_jiaoqx_dqrq;
	 @Column
	private Date che_shangyex_dqrq;
	 @Column
	private Date che_erjiwh_rq;
	 @Column
	private Date che_dengjipd_rq;
	 @Column
	private Date che_yingyz_rq;
	 @Column
	private String che_chex_fl;
	 @Column
	private String che_songxiuren;
	 @Column
	private String che_songxiuren_sj;
	 @Column
	private boolean che_flag_yunying;
	 @Column
	private String che_xingzhi;
	 @Column
	private double che_baoyanglicheng;
	 @Column
	private Date che_prior_byrq;
	 @Column
	private double che_prior_licheng;
	 @Column
	private double che_next_licheng;
	 @Column
	private boolean flag_notsendmsg;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setChe_no(String che_no){
	this.che_no=che_no;
	}
	public String getChe_no(){
		return che_no;
	}
	public void setKehu_no(String kehu_no){
	this.kehu_no=kehu_no;
	}
	public String getKehu_no(){
		return kehu_no;
	}
	public void setChe_vin(String che_vin){
	this.che_vin=che_vin;
	}
	public String getChe_vin(){
		return che_vin;
	}
	public void setChe_fd(String che_fd){
	this.che_fd=che_fd;
	}
	public String getChe_fd(){
		return che_fd;
	}
	public void setChe_bs(String che_bs){
	this.che_bs=che_bs;
	}
	public String getChe_bs(){
		return che_bs;
	}
	public void setChe_cx(String che_cx){
	this.che_cx=che_cx;
	}
	public String getChe_cx(){
		return che_cx;
	}
	public void setChe_ccrq(Date che_ccrq){
	this.che_ccrq=che_ccrq;
	}
	public Date getChe_ccrq(){
		return che_ccrq;
	}
	public void setChe_gcrq(Date che_gcrq){
	this.che_gcrq=che_gcrq;
	}
	public Date getChe_gcrq(){
		return che_gcrq;
	}
	public void setChe_wxys(String che_wxys){
	this.che_wxys=che_wxys;
	}
	public String getChe_wxys(){
		return che_wxys;
	}
	public void setChe_fdjxh(String che_fdjxh){
	this.che_fdjxh=che_fdjxh;
	}
	public String getChe_fdjxh(){
		return che_fdjxh;
	}
	public void setChe_sj(String che_sj){
	this.che_sj=che_sj;
	}
	public String getChe_sj(){
		return che_sj;
	}
	public void setChe_zjno(String che_zjno){
	this.che_zjno=che_zjno;
	}
	public String getChe_zjno(){
		return che_zjno;
	}
	public void setChe_djrq(Date che_djrq){
	this.che_djrq=che_djrq;
	}
	public Date getChe_djrq(){
		return che_djrq;
	}
	public void setChe_sfbz(String che_sfbz){
	this.che_sfbz=che_sfbz;
	}
	public String getChe_sfbz(){
		return che_sfbz;
	}
	public void setChe_sffl(double che_sffl){
	this.che_sffl=che_sffl;
	}
	public double getChe_sffl(){
		return che_sffl;
	}
	public void setChe_bz(String che_bz){
	this.che_bz=che_bz;
	}
	public String getChe_bz(){
		return che_bz;
	}
	public void setCard_no(String card_no){
	this.card_no=card_no;
	}
	public String getCard_no(){
		return card_no;
	}
	public void setCard_kind(String card_kind){
	this.card_kind=card_kind;
	}
	public String getCard_kind(){
		return card_kind;
	}
	public void setKehu_bxno(String kehu_bxno){
	this.kehu_bxno=kehu_bxno;
	}
	public String getKehu_bxno(){
		return kehu_bxno;
	}
	public void setKehu_bxmc(String kehu_bxmc){
	this.kehu_bxmc=kehu_bxmc;
	}
	public String getKehu_bxmc(){
		return kehu_bxmc;
	}
	public void setGongSiNo(String GongSiNo){
	this.GongSiNo=GongSiNo;
	}
	public String getGongSiNo(){
		return GongSiNo;
	}
	public void setGongSiMc(String GongSiMc){
	this.GongSiMc=GongSiMc;
	}
	public String getGongSiMc(){
		return GongSiMc;
	}
	public void setChe_cxpy(String che_cxpy){
	this.che_cxpy=che_cxpy;
	}
	public String getChe_cxpy(){
		return che_cxpy;
	}
	public void setChe_cxwb(String che_cxwb){
	this.che_cxwb=che_cxwb;
	}
	public String getChe_cxwb(){
		return che_cxwb;
	}
	public void setChe_py(String che_py){
	this.che_py=che_py;
	}
	public String getChe_py(){
		return che_py;
	}
	public void setChe_fd_xh(String che_fd_xh){
	this.che_fd_xh=che_fd_xh;
	}
	public String getChe_fd_xh(){
		return che_fd_xh;
	}
	public void setChe_dp_xh(String che_dp_xh){
	this.che_dp_xh=che_dp_xh;
	}
	public String getChe_dp_xh(){
		return che_dp_xh;
	}
	public void setChe_pp(String che_pp){
	this.che_pp=che_pp;
	}
	public String getChe_pp(){
		return che_pp;
	}
	public void setChe_pl(String che_pl){
	this.che_pl=che_pl;
	}
	public String getChe_pl(){
		return che_pl;
	}
	public void setChe_pd(String che_pd){
	this.che_pd=che_pd;
	}
	public String getChe_pd(){
		return che_pd;
	}
	public void setChe_nf(String che_nf){
	this.che_nf=che_nf;
	}
	public String getChe_nf(){
		return che_nf;
	}
	public void setChe_bs_no(String che_bs_no){
	this.che_bs_no=che_bs_no;
	}
	public String getChe_bs_no(){
		return che_bs_no;
	}
	public void setChe_rjlc(double che_rjlc){
	this.che_rjlc=che_rjlc;
	}
	public double getChe_rjlc(){
		return che_rjlc;
	}
	public void setChe_next_byrq(Date che_next_byrq){
	this.che_next_byrq=che_next_byrq;
	}
	public Date getChe_next_byrq(){
		return che_next_byrq;
	}
	public void setChex_dm(String chex_dm){
	this.chex_dm=chex_dm;
	}
	public String getChex_dm(){
		return chex_dm;
	}
	public void setChe_wxxm_jffs(String che_wxxm_jffs){
	this.che_wxxm_jffs=che_wxxm_jffs;
	}
	public String getChe_wxxm_jffs(){
		return che_wxxm_jffs;
	}
	public void setChe_peij_jffs(String che_peij_jffs){
	this.che_peij_jffs=che_peij_jffs;
	}
	public String getChe_peij_jffs(){
		return che_peij_jffs;
	}
	public void setChe_jiyouge_xh(String che_jiyouge_xh){
	this.che_jiyouge_xh=che_jiyouge_xh;
	}
	public String getChe_jiyouge_xh(){
		return che_jiyouge_xh;
	}
	public void setWeixin_OpenId(String weixin_OpenId){
	this.weixin_OpenId=weixin_OpenId;
	}
	public String getWeixin_OpenId(){
		return weixin_OpenId;
	}
	public void setNs_dqrq(Date ns_dqrq){
	this.ns_dqrq=ns_dqrq;
	}
	public Date getNs_dqrq(){
		return ns_dqrq;
	}
	public void setBx_dqrq(Date bx_dqrq){
	this.bx_dqrq=bx_dqrq;
	}
	public Date getBx_dqrq(){
		return bx_dqrq;
	}
	public void setChe_jiyou_xh(String che_jiyou_xh){
	this.che_jiyou_xh=che_jiyou_xh;
	}
	public String getChe_jiyou_xh(){
		return che_jiyou_xh;
	}
	public void setChe_jianche_dqrq(Date che_jianche_dqrq){
	this.che_jianche_dqrq=che_jianche_dqrq;
	}
	public Date getChe_jianche_dqrq(){
		return che_jianche_dqrq;
	}
	public void setChe_jiaoqx_dqrq(Date che_jiaoqx_dqrq){
	this.che_jiaoqx_dqrq=che_jiaoqx_dqrq;
	}
	public Date getChe_jiaoqx_dqrq(){
		return che_jiaoqx_dqrq;
	}
	public void setChe_shangyex_dqrq(Date che_shangyex_dqrq){
	this.che_shangyex_dqrq=che_shangyex_dqrq;
	}
	public Date getChe_shangyex_dqrq(){
		return che_shangyex_dqrq;
	}
	public void setChe_erjiwh_rq(Date che_erjiwh_rq){
	this.che_erjiwh_rq=che_erjiwh_rq;
	}
	public Date getChe_erjiwh_rq(){
		return che_erjiwh_rq;
	}
	public void setChe_dengjipd_rq(Date che_dengjipd_rq){
	this.che_dengjipd_rq=che_dengjipd_rq;
	}
	public Date getChe_dengjipd_rq(){
		return che_dengjipd_rq;
	}
	public void setChe_yingyz_rq(Date che_yingyz_rq){
	this.che_yingyz_rq=che_yingyz_rq;
	}
	public Date getChe_yingyz_rq(){
		return che_yingyz_rq;
	}
	public void setChe_chex_fl(String che_chex_fl){
	this.che_chex_fl=che_chex_fl;
	}
	public String getChe_chex_fl(){
		return che_chex_fl;
	}
	public void setChe_songxiuren(String che_songxiuren){
	this.che_songxiuren=che_songxiuren;
	}
	public String getChe_songxiuren(){
		return che_songxiuren;
	}
	public void setChe_songxiuren_sj(String che_songxiuren_sj){
	this.che_songxiuren_sj=che_songxiuren_sj;
	}
	public String getChe_songxiuren_sj(){
		return che_songxiuren_sj;
	}
	public void setChe_flag_yunying(boolean che_flag_yunying){
	this.che_flag_yunying=che_flag_yunying;
	}
	public boolean getChe_flag_yunying(){
		return che_flag_yunying;
	}
	public void setChe_xingzhi(String che_xingzhi){
	this.che_xingzhi=che_xingzhi;
	}
	public String getChe_xingzhi(){
		return che_xingzhi;
	}
	public void setChe_baoyanglicheng(double che_baoyanglicheng){
	this.che_baoyanglicheng=che_baoyanglicheng;
	}
	public double getChe_baoyanglicheng(){
		return che_baoyanglicheng;
	}
	public void setChe_prior_byrq(Date che_prior_byrq){
	this.che_prior_byrq=che_prior_byrq;
	}
	public Date getChe_prior_byrq(){
		return che_prior_byrq;
	}
	public void setChe_prior_licheng(double che_prior_licheng){
	this.che_prior_licheng=che_prior_licheng;
	}
	public double getChe_prior_licheng(){
		return che_prior_licheng;
	}
	public void setChe_next_licheng(double che_next_licheng){
	this.che_next_licheng=che_next_licheng;
	}
	public double getChe_next_licheng(){
		return che_next_licheng;
	}
	public void setFlag_notsendmsg(boolean flag_notsendmsg){
	this.flag_notsendmsg=flag_notsendmsg;
	}
	public boolean getFlag_notsendmsg(){
		return flag_notsendmsg;
	}

	
}

