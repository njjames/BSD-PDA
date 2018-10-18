package org.aotu.publics.eneity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table; 
/**
 * 
 * title:Kucshp_zkEntity
 * @Description:<库存商品总库>
 * @author Zhang Yalong
 * @date 2017-4-24 上午11:40:28
 * @version: V1.0
 */
@Table(value="kucshp_zk")
public class Kucshp_zkEntity{
	 @Column
	private String peij_no;
	 @Column
	private double jiag_j1;
	 @Column
	private double jiag_j2;
	 @Column
	private double jiag_j3;
	 @Column
	private double jiag_x1;
	 @Column
	private double jiag_x2;
	 @Column
	private double jiag_x3;
	 @Column
	private double jiag_wb;
	 @Column
	private String waib_dw;
	 @Column
	private double peij_kc;
	 @Column
	private double jiag_jp;
	 @Column
	private double peij_je;
	 @Column
	private double jiag_low;
	 @Column
	private String gongsino;
	public void setPeij_no(String peij_no){
	this.peij_no=peij_no;
	}
	public String getPeij_no(){
		return peij_no;
	}
	public void setJiag_j1(double jiag_j1){
	this.jiag_j1=jiag_j1;
	}
	public double getJiag_j1(){
		return jiag_j1;
	}
	public void setJiag_j2(double jiag_j2){
	this.jiag_j2=jiag_j2;
	}
	public double getJiag_j2(){
		return jiag_j2;
	}
	public void setJiag_j3(double jiag_j3){
	this.jiag_j3=jiag_j3;
	}
	public double getJiag_j3(){
		return jiag_j3;
	}
	public void setJiag_x1(double jiag_x1){
	this.jiag_x1=jiag_x1;
	}
	public double getJiag_x1(){
		return jiag_x1;
	}
	public void setJiag_x2(double jiag_x2){
	this.jiag_x2=jiag_x2;
	}
	public double getJiag_x2(){
		return jiag_x2;
	}
	public void setJiag_x3(double jiag_x3){
	this.jiag_x3=jiag_x3;
	}
	public double getJiag_x3(){
		return jiag_x3;
	}
	public void setJiag_wb(double jiag_wb){
	this.jiag_wb=jiag_wb;
	}
	public double getJiag_wb(){
		return jiag_wb;
	}
	public void setWaib_dw(String waib_dw){
	this.waib_dw=waib_dw;
	}
	public String getWaib_dw(){
		return waib_dw;
	}
	public void setPeij_kc(double peij_kc){
	this.peij_kc=peij_kc;
	}
	public double getPeij_kc(){
		return peij_kc;
	}
	public void setJiag_jp(double jiag_jp){
	this.jiag_jp=jiag_jp;
	}
	public double getJiag_jp(){
		return jiag_jp;
	}
	public void setPeij_je(double peij_je){
	this.peij_je=peij_je;
	}
	public double getPeij_je(){
		return peij_je;
	}
	public void setJiag_low(double jiag_low){
	this.jiag_low=jiag_low;
	}
	public double getJiag_low(){
		return jiag_low;
	}
	public void setGongsino(String gongsino){
	this.gongsino=gongsino;
	}
	public String getGongsino(){
		return gongsino;
	}
}

