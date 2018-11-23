package org.aotu.appointment.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * title:Work_yuyue_pzEntity
 *
 * @author Zhang Yalong
 * @Description:<预约单主表>
 * @date 2017-4-28 上午11:18:58
 * @version: V1.0
 */
@Table(value = "work_yuyue_pz")
public class Work_yuyue_pzEntity {
    @Id
    @Column
    private int reco_no;
    @Name
    @Column
    private String yuyue_no;
    @Column
    private Date yuyue_jlrq;
    @Column
    private Date yuyue_scjcrq;
    @Column
    private String yuyue_sfbz;
    @Column
    private double yuyue_sffl;
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
    private String che_zjno;
    @Column
    private String che_no;
    @Column
    private String che_vin;
    @Column
    private String che_fd;
    @Column
    private String che_cx;
    @Column
    private String che_fd_xh;
    @Column
    private String che_dp_xh;
    @Column
    private String che_pp;
    @Column
    private String che_wxys;
    @Column
    private String yuyue_ren;
    @Column
    private String yuyue_ren_tel;
    @Column
    private String yuyue_ren_dh;
    @Column
    private String yuyue_jcr;
    @Column
    private Date yuyue_yjjcrq;
    @Column
    private double yuyue_yjjclc;
    @Column
    private Date yuyue_sjjcrq;
    @Column
    private String yuyue_gdfl;
    @Column
    private String yuyue_wxfl;
    @Column
    private String yuyue_gw;
    @Column
    private String yuyue_ry;
    @Column
    private int yuyue_wxsj;
    @Column
    private boolean yuyue_IsJieChe;
    @Column
    private String yuyue_jcAddress;
    @Column
    private String yuyue_GuZhangMiaoShu;
    @Column
    private String dept_mc;
    @Column
    private String yuyue_czy;
    @Column
    private String yuyue_jbr;
    @Column
    private double yuyue_gj_wx;
    @Column
    private double yuyue_gj_ll;
    @Column
    private double yuyue_hjje;
    @Column
    private String GongSiNo;
    @Column
    private String GongSiMc;
    @Column
    private int yuyue_state;
    @Column
    private String yuyue_progress;
    @Column
    private double yuyue_IsImpt;

    @Column
    private String work_no;
    @Column
    private String yuyue_bz;
    @Column
    private double yuyue_lc;

    private Date gcsj;

    public int getReco_no() {
        return reco_no;
    }

    public void setReco_no(int reco_no) {
        this.reco_no = reco_no;
    }

    public void setYuyue_no(String yuyue_no) {
        this.yuyue_no = yuyue_no;
    }

    public String getYuyue_no() {
        return yuyue_no;
    }

    public void setYuyue_jlrq(Date yuyue_jlrq) {
        this.yuyue_jlrq = yuyue_jlrq;
    }

    public Date getYuyue_jlrq() {
        return yuyue_jlrq;
    }

    public void setYuyue_scjcrq(Date yuyue_scjcrq) {
        this.yuyue_scjcrq = yuyue_scjcrq;
    }

    public Date getYuyue_scjcrq() {
        return yuyue_scjcrq;
    }

    public void setYuyue_sfbz(String yuyue_sfbz) {
        this.yuyue_sfbz = yuyue_sfbz;
    }

    public String getYuyue_sfbz() {
        return yuyue_sfbz;
    }

    public void setYuyue_sffl(double yuyue_sffl) {
        this.yuyue_sffl = yuyue_sffl;
    }

    public double getYuyue_sffl() {
        return yuyue_sffl;
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

    public void setChe_zjno(String che_zjno) {
        this.che_zjno = che_zjno;
    }

    public String getChe_zjno() {
        return che_zjno;
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

    public void setChe_wxys(String che_wxys) {
        this.che_wxys = che_wxys;
    }

    public String getChe_wxys() {
        return che_wxys;
    }

    public void setYuyue_ren(String yuyue_ren) {
        this.yuyue_ren = yuyue_ren;
    }

    public String getYuyue_ren() {
        return yuyue_ren;
    }

    public void setYuyue_ren_tel(String yuyue_ren_tel) {
        this.yuyue_ren_tel = yuyue_ren_tel;
    }

    public String getYuyue_ren_tel() {
        return yuyue_ren_tel;
    }

    public void setYuyue_ren_dh(String yuyue_ren_dh) {
        this.yuyue_ren_dh = yuyue_ren_dh;
    }

    public String getYuyue_ren_dh() {
        return yuyue_ren_dh;
    }

    public void setYuyue_jcr(String yuyue_jcr) {
        this.yuyue_jcr = yuyue_jcr;
    }

    public String getYuyue_jcr() {
        return yuyue_jcr;
    }

    public void setYuyue_yjjcrq(Date yuyue_yjjcrq) {
        this.yuyue_yjjcrq = yuyue_yjjcrq;
    }

    public Date getYuyue_yjjcrq() {
        return yuyue_yjjcrq;
    }


    public double getYuyue_yjjclc() {
        return yuyue_yjjclc;
    }

    public void setYuyue_yjjclc(double yuyue_yjjclc) {
        this.yuyue_yjjclc = yuyue_yjjclc;
    }

    public void setYuyue_sjjcrq(Date yuyue_sjjcrq) {
        this.yuyue_sjjcrq = yuyue_sjjcrq;
    }

    public Date getYuyue_sjjcrq() {
        return yuyue_sjjcrq;
    }

    public void setYuyue_gdfl(String yuyue_gdfl) {
        this.yuyue_gdfl = yuyue_gdfl;
    }

    public String getYuyue_gdfl() {
        return yuyue_gdfl;
    }

    public void setYuyue_wxfl(String yuyue_wxfl) {
        this.yuyue_wxfl = yuyue_wxfl;
    }

    public String getYuyue_wxfl() {
        return yuyue_wxfl;
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

    public void setYuyue_wxsj(int yuyue_wxsj) {
        this.yuyue_wxsj = yuyue_wxsj;
    }

    public int getYuyue_wxsj() {
        return yuyue_wxsj;
    }

    public void setYuyue_IsJieChe(boolean yuyue_IsJieChe) {
        this.yuyue_IsJieChe = yuyue_IsJieChe;
    }

    public boolean getYuyue_IsJieChe() {
        return yuyue_IsJieChe;
    }

    public void setYuyue_jcAddress(String yuyue_jcAddress) {
        this.yuyue_jcAddress = yuyue_jcAddress;
    }

    public String getYuyue_jcAddress() {
        return yuyue_jcAddress;
    }

    public void setYuyue_GuZhangMiaoShu(String yuyue_GuZhangMiaoShu) {
        this.yuyue_GuZhangMiaoShu = yuyue_GuZhangMiaoShu;
    }

    public String getYuyue_GuZhangMiaoShu() {
        return yuyue_GuZhangMiaoShu;
    }

    public void setDept_mc(String dept_mc) {
        this.dept_mc = dept_mc;
    }

    public String getDept_mc() {
        return dept_mc;
    }

    public void setYuyue_czy(String yuyue_czy) {
        this.yuyue_czy = yuyue_czy;
    }

    public String getYuyue_czy() {
        return yuyue_czy;
    }

    public void setYuyue_jbr(String yuyue_jbr) {
        this.yuyue_jbr = yuyue_jbr;
    }

    public String getYuyue_jbr() {
        return yuyue_jbr;
    }

    public void setYuyue_gj_wx(double yuyue_gj_wx) {
        this.yuyue_gj_wx = yuyue_gj_wx;
    }

    public double getYuyue_gj_wx() {
        return yuyue_gj_wx;
    }

    public void setYuyue_gj_ll(double yuyue_gj_ll) {
        this.yuyue_gj_ll = yuyue_gj_ll;
    }

    public double getYuyue_gj_ll() {
        return yuyue_gj_ll;
    }

    public void setYuyue_hjje(double yuyue_hjje) {
        this.yuyue_hjje = yuyue_hjje;
    }

    public double getYuyue_hjje() {
        return yuyue_hjje;
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

    public int getYuyue_state() {
        return yuyue_state;
    }

    public void setYuyue_state(int yuyue_state) {
        this.yuyue_state = yuyue_state;
    }

    public void setYuyue_progress(String yuyue_progress) {
        this.yuyue_progress = yuyue_progress;
    }

    public String getYuyue_progress() {
        return yuyue_progress;
    }

    public void setYuyue_IsImpt(double yuyue_IsImpt) {
        this.yuyue_IsImpt = yuyue_IsImpt;
    }

    public double getYuyue_IsImpt() {
        return yuyue_IsImpt;
    }

    public void setWork_no(String work_no) {
        this.work_no = work_no;
    }

    public String getWork_no() {
        return work_no;
    }

    public void setYuyue_bz(String yuyue_bz) {
        this.yuyue_bz = yuyue_bz;
    }

    public String getYuyue_bz() {
        return yuyue_bz;
    }

    public void setYuyue_lc(double yuyue_lc) {
        this.yuyue_lc = yuyue_lc;
    }

    public double getYuyue_lc() {
        return yuyue_lc;
    }

    public Date getGcsj() {
        return gcsj;
    }

    public void setGcsj(Date gcsj) {
        this.gcsj = gcsj;
    }

}

