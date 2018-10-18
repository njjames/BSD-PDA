package org.aotu.offer.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * title:baoJiaEntity
 *
 * @author Zhang Yalong
 * @Description:<描述>
 * @date 2017-4-17 下午1:28:31
 * @version: V1.0
 */

@Table(value = "Work_BaoJia_pz")
public class baoJiaEntity {
    @Id
    @Column
    private int reco_no;
    @Name
    @Column
    private String list_no;
    @Column
    private Date List_jlrq;
    @Column
    private String List_sfbz;
    @Column
    private double List_sffl;
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
    private String List_jcr;
    @Column
    private String List_yjjclc;
    @Column
    private String List_gdfl;
    @Column
    private String List_wxfl;
    @Column
    private String List_GuZhangMiaoShu;
    @Column
    private String dept_mc;
    @Column
    private String List_czy;
    @Column
    private String List_jbr;
    @Column
    private double List_gj_wx;
    @Column
    private double List_gj_ll;
    @Column
    private double List_hjje;
    @Column
    private double List_hjje_yh;
    @Column
    private String GongSiNo;
    @Column
    private String GongSiMc;
    @Column
    private int List_state;
    @Column
    private String List_progress;
    @Column
    private double List_IsImpt;
    @Column
    private String work_no;
    @Column
    private String List_bz;
    @Column
    private double List_lc;

    private Date gcsj;

    public int getReco_no() {
        return reco_no;
    }

    public void setReco_no(int reco_no) {
        this.reco_no = reco_no;
    }

    public void setList_no(String list_no) {
        this.list_no = list_no;
    }

    public String getList_no() {
        return list_no;
    }

    public void setList_jlrq(Date List_jlrq) {
        this.List_jlrq = List_jlrq;
    }

    public Date getList_jlrq() {
        return List_jlrq;
    }

    public void setList_sfbz(String List_sfbz) {
        this.List_sfbz = List_sfbz;
    }

    public String getList_sfbz() {
        return List_sfbz;
    }

    public void setList_sffl(double List_sffl) {
        this.List_sffl = List_sffl;
    }

    public double getList_sffl() {
        return List_sffl;
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

    public void setList_jcr(String List_jcr) {
        this.List_jcr = List_jcr;
    }

    public String getList_jcr() {
        return List_jcr;
    }

    public String getList_yjjclc() {
        return List_yjjclc;
    }

    public void setList_yjjclc(String list_yjjclc) {
        List_yjjclc = list_yjjclc;
    }

    public void setList_gdfl(String List_gdfl) {
        this.List_gdfl = List_gdfl;
    }

    public String getList_gdfl() {
        return List_gdfl;
    }

    public void setList_wxfl(String List_wxfl) {
        this.List_wxfl = List_wxfl;
    }

    public String getList_wxfl() {
        return List_wxfl;
    }

    public void setList_GuZhangMiaoShu(String List_GuZhangMiaoShu) {
        this.List_GuZhangMiaoShu = List_GuZhangMiaoShu;
    }

    public String getList_GuZhangMiaoShu() {
        return List_GuZhangMiaoShu;
    }

    public void setDept_mc(String dept_mc) {
        this.dept_mc = dept_mc;
    }

    public String getDept_mc() {
        return dept_mc;
    }

    public void setList_czy(String List_czy) {
        this.List_czy = List_czy;
    }

    public String getList_czy() {
        return List_czy;
    }

    public void setList_jbr(String List_jbr) {
        this.List_jbr = List_jbr;
    }

    public String getList_jbr() {
        return List_jbr;
    }

    public void setList_gj_wx(double List_gj_wx) {
        this.List_gj_wx = List_gj_wx;
    }

    public double getList_gj_wx() {
        return List_gj_wx;
    }

    public void setList_gj_ll(double List_gj_ll) {
        this.List_gj_ll = List_gj_ll;
    }

    public double getList_gj_ll() {
        return List_gj_ll;
    }

    public void setList_hjje(double List_hjje) {
        this.List_hjje = List_hjje;
    }

    public double getList_hjje() {
        return List_hjje;
    }

    public void setList_hjje_yh(double List_hjje_yh) {
        this.List_hjje_yh = List_hjje_yh;
    }

    public double getList_hjje_yh() {
        return List_hjje_yh;
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

    public int getList_state() {
        return List_state;
    }

    public void setList_state(int list_state) {
        List_state = list_state;
    }

    public void setList_progress(String List_progress) {
        this.List_progress = List_progress;
    }

    public String getList_progress() {
        return List_progress;
    }

    public void setList_IsImpt(double List_IsImpt) {
        this.List_IsImpt = List_IsImpt;
    }

    public double getList_IsImpt() {
        return List_IsImpt;
    }

    public void setWork_no(String work_no) {
        this.work_no = work_no;
    }

    public String getWork_no() {
        return work_no;
    }

    public void setList_bz(String List_bz) {
        this.List_bz = List_bz;
    }

    public String getList_bz() {
        return List_bz;
    }

    public void setList_lc(double List_lc) {
        this.List_lc = List_lc;
    }

    public double getList_lc() {
        return List_lc;
    }

    public Date getGcsj() {
        return gcsj;
    }

    public void setGcsj(Date gcsj) {
        this.gcsj = gcsj;
    }

}

