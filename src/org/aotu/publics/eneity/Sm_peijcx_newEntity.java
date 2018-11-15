package org.aotu.publics.eneity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
 * title:Sm_peijcx_newEntity
 *
 * @author Zhang Yalong
 * @Description:<车联动>
 * @date 2017-4-18 下午2:59:14
 * @version: V1.0
 */
@Table(value = "sm_peijcx_new")
public class Sm_peijcx_newEntity {
    private String chex_dm;
    @Column
    private String chex_mc;
    @Column
    private String chex_top;
    @Column
    private String chex_qz;
    @Column
    private String chex_order;
    @Column
    private int chex_len;
    @Column
    private int che_level;
    @Column
    private String chex_cj;
    @Column
    private String chex_py;
    @Column
    private String chex_wb;
    @Column("chex_dm")
    private String chex_bz;
    @Column
    private String chex_mc_std;

    public void setChex_dm(String chex_dm) {
        this.chex_dm = chex_dm;
    }

    public String getChex_dm() {
        return chex_mc;
    }

    public void setChex_mc(String chex_mc) {
        this.chex_mc = chex_mc;
    }

    public String getChex_mc() {
        return chex_mc;
    }

    public void setChex_top(String chex_top) {
        this.chex_top = chex_top;
    }

    public String getChex_top() {
        return chex_top;
    }

    public void setChex_qz(String chex_qz) {
        this.chex_qz = chex_qz;
    }

    public String getChex_qz() {
        return chex_qz;
    }

    public void setChex_order(String chex_order) {
        this.chex_order = chex_order;
    }

    public String getChex_order() {
        return chex_order;
    }

    public void setChex_len(int chex_len) {
        this.chex_len = chex_len;
    }

    public int getChex_len() {
        return chex_len;
    }

    public void setChe_level(int che_level) {
        this.che_level = che_level;
    }

    public int getChe_level() {
        return che_level;
    }

    public void setChex_cj(String chex_cj) {
        this.chex_cj = chex_cj;
    }

    public String getChex_cj() {
        return chex_cj;
    }

    public void setChex_py(String chex_py) {
        this.chex_py = chex_py;
    }

    public String getChex_py() {
        return chex_py;
    }

    public void setChex_wb(String chex_wb) {
        this.chex_wb = chex_wb;
    }

    public String getChex_wb() {
        return chex_wb;
    }

    public void setChex_bz(String chex_bz) {
        this.chex_bz = chex_bz;
    }

    public String getChex_bz() {
        return chex_bz;
    }

    public String getChex_mc_std() {
        return chex_mc_std;
    }

    public void setChex_mc_std(String chex_mc_std) {
        this.chex_mc_std = chex_mc_std;
    }

}

