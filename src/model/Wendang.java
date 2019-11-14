package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_Wendang")
public class Wendang implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;
	
	private int wendanglock;
	
	private String title;
	
	private String path;//简历路径
	
	private Date createtime;
	
	
	public Date getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	
	

	

	public int getWendanglock() {
		return wendanglock;
	}


	public void setWendanglock(int wendanglock) {
		this.wendanglock = wendanglock;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	

	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


}
