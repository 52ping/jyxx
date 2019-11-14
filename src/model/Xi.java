package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_Xi")
public class Xi implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;

	private int xilock;
	
	private String name;
	

 	
	
	
	

	

	

	

	public int getXilock() {
		return xilock;
	}

	public void setXilock(int xilock) {
		this.xilock = xilock;
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
