package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_Gonggao")
public class Gonggao implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;
	
	
	
	private int gonggaolock;
	
	private String title;
	
	private String content;
	
	private Date createtime;
 	
	
	
	

	

	

	public int getGonggaolock() {
		return gonggaolock;
	}

	public void setGonggaolock(int gonggaolock) {
		this.gonggaolock = gonggaolock;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	@Column(name="content", columnDefinition="TEXT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	

	
	
}
