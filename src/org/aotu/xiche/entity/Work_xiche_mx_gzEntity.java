/**
 * @author LHW
 *@time 2018年1月8日上午8:52:58
 * 
 */
package org.aotu.xiche.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author LHW
 *
 */
@Table(value="work_xiche_mx_gz")
public class Work_xiche_mx_gzEntity {
	
	@Column
	private String xc_no;
	
	@Column
	private String reny_no;
	@Column
	private String reny_mc;
	@Column
	private double reny_fe;
	public String getXc_no() {
		return xc_no;
	}
	public void setXc_no(String xc_no) {
		this.xc_no = xc_no;
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
	public double getReny_fe() {
		return reny_fe;
	}
	public void setReny_fe(double reny_fe) {
		this.reny_fe = reny_fe;
	}
	
	
}
