package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_Toudijilu")
public class Toudijilu implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;
	
	private Zhiwei zhiwei;
	
	private Jianli jianli;
	
	private Date createtime;
	
	private String readzhuangtai;//未阅读  已阅读
	
	private String yingpinzhuangtai;//应聘状态
	
	
	
	


	public String getYingpinzhuangtai() {
		return yingpinzhuangtai;
	}


	public void setYingpinzhuangtai(String yingpinzhuangtai) {
		this.yingpinzhuangtai = yingpinzhuangtai;
	}


	public String getReadzhuangtai() {
		return readzhuangtai;
	}


	public void setReadzhuangtai(String readzhuangtai) {
		this.readzhuangtai = readzhuangtai;
	}


	public Date getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}




	

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}





	@ManyToOne
	@JoinColumn(name="zhiweiid")
	public Zhiwei getZhiwei() {
		return zhiwei;
	}


	public void setZhiwei(Zhiwei zhiwei) {
		this.zhiwei = zhiwei;
	}

	@ManyToOne
	@JoinColumn(name="jianliid")
	public Jianli getJianli() {
		return jianli;
	}


	public void setJianli(Jianli jianli) {
		this.jianli = jianli;
	}

	

}
