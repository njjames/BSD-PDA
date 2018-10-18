/**
 * @author LHW
 *@time 2018年1月8日上午8:52:35
 * 
 */
package org.aotu.xiche.entity;


import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author LHW
 *
 */
@Table(value="work_weixiu_xiche_reny")
public class Work_weixiu_xiche_renyEntity {
	
		
		@Name
		@Column
		private String work_no;
		
		@Column
		private String reny_no;
		@Column
		private String reny_mc;
		@Column
		private double reny_fe;
		@Column
		private int flag_state;
		public String getWork_no() {
			return work_no;
		}
		public void setWork_no(String work_no) {
			this.work_no = work_no;
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
		public int getFlag_state() {
			return flag_state;
		}
		public void setFlag_state(int flag_state) {
			this.flag_state = flag_state;
		}
		
		
	
}
