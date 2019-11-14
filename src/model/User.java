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
@Table(name="t_User")
public class User implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;
	
	private String username;
	
	private String password;
	
	private Date createtime;
	
	private String truename;

	private int role;//4表示系统管理员,3表示老师管理员,2表示毕业生用户,1表示企业用户
	
	private int userlock;
	
	private String qiyequyu;//企业区域  南方 北方
	
	private String qiyemingchen;//企业名称
	
	private String qiyelianxidianhua;//企业联系电话
	
	private String qiyedizhi;//企业地址
	
	private String congshihangye;//从事行业
	
	private String farendaibiao;//法人代表
	
	private String xuehao;//学号
	
	private String zhuanye;//专业
	
	private String banji;//班级
	
	private String nianji;//年级
	
	private String lianxifangshi;//联系方式
	
	private String lianxidizhi;//联系地址
	
	private String xingbie;//性别
	
	private Zhiwei zhiwei;//岗位
	
	private String xi;//系
	
	
	
	
	
	
	
	
	
	public String getXi() {
		return xi;
	}

	public void setXi(String xi) {
		this.xi = xi;
	}

	public String getQiyequyu() {
		return qiyequyu;
	}

	public void setQiyequyu(String qiyequyu) {
		this.qiyequyu = qiyequyu;
	}

	public String getXingbie() {
		return xingbie;
	}

	public void setXingbie(String xingbie) {
		this.xingbie = xingbie;
	}

	@ManyToOne
	@JoinColumn(name="zhiweiid")
	public Zhiwei getZhiwei() {
		return zhiwei;
	}

	public void setZhiwei(Zhiwei zhiwei) {
		this.zhiwei = zhiwei;
	}

	public String getXuehao() {
		return xuehao;
	}

	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}

	public String getZhuanye() {
		return zhuanye;
	}

	public void setZhuanye(String zhuanye) {
		this.zhuanye = zhuanye;
	}

	public String getBanji() {
		return banji;
	}

	public void setBanji(String banji) {
		this.banji = banji;
	}

	public String getNianji() {
		return nianji;
	}

	public void setNianji(String nianji) {
		this.nianji = nianji;
	}

	public String getLianxifangshi() {
		return lianxifangshi;
	}

	public void setLianxifangshi(String lianxifangshi) {
		this.lianxifangshi = lianxifangshi;
	}

	public String getLianxidizhi() {
		return lianxidizhi;
	}

	public void setLianxidizhi(String lianxidizhi) {
		this.lianxidizhi = lianxidizhi;
	}

	public String getQiyemingchen() {
		return qiyemingchen;
	}

	public void setQiyemingchen(String qiyemingchen) {
		this.qiyemingchen = qiyemingchen;
	}

	public String getQiyelianxidianhua() {
		return qiyelianxidianhua;
	}

	public void setQiyelianxidianhua(String qiyelianxidianhua) {
		this.qiyelianxidianhua = qiyelianxidianhua;
	}

	public String getQiyedizhi() {
		return qiyedizhi;
	}

	public void setQiyedizhi(String qiyedizhi) {
		this.qiyedizhi = qiyedizhi;
	}

	public String getCongshihangye() {
		return congshihangye;
	}

	public void setCongshihangye(String congshihangye) {
		this.congshihangye = congshihangye;
	}

	public String getFarendaibiao() {
		return farendaibiao;
	}

	public void setFarendaibiao(String farendaibiao) {
		this.farendaibiao = farendaibiao;
	}

	public int getUserlock() {
		return userlock;
	}

	public void setUserlock(int userlock) {
		this.userlock = userlock;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	
	
}
