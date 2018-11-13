/**
 * @author LHW
 *@time 2018年4月2日下午4:02:06
 * 
 */
package org.aotu.publics.eneity;

import org.nutz.dao.entity.annotation.Table;

/**
 * @author LHW
 *
 */
@Table(value="jizhang_yhdy")
public class Jizhang_yhdyEntity {
	private int id;
	private String yh_name;
	private int yh_qcye;
	private String yh_bz;
	private String GongSiNo;
	private String GongSiMc;
	private String shouxufei_lv;
	@Override
	public String toString() {
		return "Jizhang_yhdy [id=" + id + ", yh_name=" + yh_name + ", yh_qcye="
				+ yh_qcye + ", yh_bz=" + yh_bz + ", GongSiNo=" + GongSiNo
				+ ", GongSiMc=" + GongSiMc + ", shouxufei_lv=" + shouxufei_lv
				+ "]";
	}
	public Jizhang_yhdyEntity(int id, String yh_name, int yh_qcye, String yh_bz,
			String gongSiNo, String gongSiMc, String shouxufei_lv) {
		super();
		this.id = id;
		this.yh_name = yh_name;
		this.yh_qcye = yh_qcye;
		this.yh_bz = yh_bz;
		GongSiNo = gongSiNo;
		GongSiMc = gongSiMc;
		this.shouxufei_lv = shouxufei_lv;
	}
	public Jizhang_yhdyEntity() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getYh_name() {
		return yh_name;
	}
	public void setYh_name(String yh_name) {
		this.yh_name = yh_name;
	}
	public int getYh_qcye() {
		return yh_qcye;
	}
	public void setYh_qcye(int yh_qcye) {
		this.yh_qcye = yh_qcye;
	}
	public String getYh_bz() {
		return yh_bz;
	}
	public void setYh_bz(String yh_bz) {
		this.yh_bz = yh_bz;
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
	public String getShouxufei_lv() {
		return shouxufei_lv;
	}
	public void setShouxufei_lv(String shouxufei_lv) {
		this.shouxufei_lv = shouxufei_lv;
	}
	
}
