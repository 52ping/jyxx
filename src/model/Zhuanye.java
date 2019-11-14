package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_Zhuanye")
public class Zhuanye implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;

	private int zhuanyelock;
	
	private String name;
	
	private Xi xi;
	

 	


	
	@ManyToOne
	@JoinColumn(name="xiid")
	public Xi getXi() {
		return xi;
	}

	public void setXi(Xi xi) {
		this.xi = xi;
	}

	public int getZhuanyelock() {
		return zhuanyelock;
	}

	public void setZhuanyelock(int zhuanyelock) {
		this.zhuanyelock = zhuanyelock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
