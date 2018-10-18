package org.aotu.VIPcard.entity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table; 
/**
 * 
 * title:CardKindEntity
 * @Description:<卡类型>
 * @author Zhang Yalong
 * @date 2017-5-6 下午3:04:51
 * @version: V1.0
 */
@Table(value="CardKind")
public class CardKindEntity{
	@Id
	@Column
	private int id;
	@Name
	@Column
	private String CardKind;
	@Column
	private double ItemRate;
	@Column
	private double PeijRate;
	@Column
	private int CardFlag;
	@Column
	private double Card_JfMin;
	@Column
	private double Card_JfMax;
	@Column
	private double XiChe_Je;
	@Column
	private String Remark;
	@Column
	private String GongSiNo;
	@Column
	private String GongSiMc;
	@Column
	private double XiChe_Je_jx;
	@Column
	private boolean Flag_hide;
	@Column
	private double card_khje;
	@Column
	private double card_addje;
	@Column
	private double card_cs_jx;
	@Column
	private double card_cs_px;
	@Column
	private int card_yxq;
	public void setId(int id){
	this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setCardKind(String CardKind){
	this.CardKind=CardKind;
	}
	public String getCardKind(){
		return CardKind;
	}
	public void setItemRate(double ItemRate){
	this.ItemRate=ItemRate;
	}
	public double getItemRate(){
		return ItemRate;
	}
	public void setPeijRate(double PeijRate){
	this.PeijRate=PeijRate;
	}
	public double getPeijRate(){
		return PeijRate;
	}
	public void setCardFlag(int CardFlag){
	this.CardFlag=CardFlag;
	}
	public int getCardFlag(){
		return CardFlag;
	}
	public void setCard_JfMin(double Card_JfMin){
	this.Card_JfMin=Card_JfMin;
	}
	public double getCard_JfMin(){
		return Card_JfMin;
	}
	public void setCard_JfMax(double Card_JfMax){
	this.Card_JfMax=Card_JfMax;
	}
	public double getCard_JfMax(){
		return Card_JfMax;
	}
	public void setXiChe_Je(double XiChe_Je){
	this.XiChe_Je=XiChe_Je;
	}
	public double getXiChe_Je(){
		return XiChe_Je;
	}
	public void setRemark(String Remark){
	this.Remark=Remark;
	}
	public String getRemark(){
		return Remark;
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
	public void setXiChe_Je_jx(double XiChe_Je_jx){
	this.XiChe_Je_jx=XiChe_Je_jx;
	}
	public double getXiChe_Je_jx(){
		return XiChe_Je_jx;
	}
	public void setFlag_hide(boolean Flag_hide){
	this.Flag_hide=Flag_hide;
	}
	public boolean getFlag_hide(){
		return Flag_hide;
	}
	public void setCard_khje(double card_khje){
	this.card_khje=card_khje;
	}
	public double getCard_khje(){
		return card_khje;
	}
	public void setCard_addje(double card_addje){
	this.card_addje=card_addje;
	}
	public double getCard_addje(){
		return card_addje;
	}
	public void setCard_cs_jx(double card_cs_jx){
	this.card_cs_jx=card_cs_jx;
	}
	public double getCard_cs_jx(){
		return card_cs_jx;
	}
	public void setCard_cs_px(double card_cs_px){
	this.card_cs_px=card_cs_px;
	}
	public double getCard_cs_px(){
		return card_cs_px;
	}
	public void setCard_yxq(int card_yxq){
	this.card_yxq=card_yxq;
	}
	public int getCard_yxq(){
		return card_yxq;
	}
}

