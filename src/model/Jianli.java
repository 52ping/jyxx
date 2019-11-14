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
@Table(name="t_Jianli")
public class Jianli implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;
	
	private int jianlilock;
	
	private String jianlimingchen;//简历名称
	
	private String path;//简历路径
	
	private User qiuzhiren;//求职者
	
	private Date createtime;
	
	
	public Date getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	@ManyToOne
	@JoinColumn(name="qiuzhirenid")
	public User getQiuzhiren() {
		return qiuzhiren;
	}

	
	public void setQiuzhiren(User qiuzhiren) {
		this.qiuzhiren = qiuzhiren;
	}

	

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public int getJianlilock() {
		return jianlilock;
	}


	public void setJianlilock(int jianlilock) {
		this.jianlilock = jianlilock;
	}


	public String getJianlimingchen() {
		return jianlimingchen;
	}


	public void setJianlimingchen(String jianlimingchen) {
		this.jianlimingchen = jianlimingchen;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


}
