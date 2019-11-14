package action;


import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import model.Gonggao;
import model.Jianli;
import model.Jiuye;
import model.Liuyan;
import model.Toudijilu;
import model.User;
import model.Wendang;
import model.Xi;
import model.Xinwen;
import model.Zhiwei;
import model.Zhuanye;

import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import util.Pager;
import util.Util;


import com.opensymphony.xwork2.ActionSupport;

import dao.GonggaoDao;
import dao.JianliDao;
import dao.JiuyeDao;
import dao.LiuyanDao;
import dao.ToudijiluDao;
import dao.UserDao;
import dao.WendangDao;
import dao.XiDao;
import dao.XinwenDao;
import dao.ZhiweiDao;
import dao.ZhuanyeDao;

public class ManageAction extends ActionSupport{
	
	
	private static final long serialVersionUID = -4304509122548259589L;
	
	private UserDao userDao;
	
	private String url = "./";
	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	//程序入口界面
	public String index(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Util.init(request);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user==null){
			return "success2";
		}else{
			return "success1";
		}
	}
	//用户登录操作
	public String login() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		User user = userDao.selectBean(" where username = '"+username +"' and password= '"+password +"' and userlock=0  and role= "+role);
		if (user!=null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			this.setUrl("index");
			return "redirect";
		} else {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('用户名或者密码错误');window.location.href='login.jsp';</script>");
		}
		return null;
	}
	
	//用户退出操作
	public String loginout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		this.setUrl("login.jsp");
		return SUCCESS;
	}
	
	
	//跳转到修改密码页面
	public String changepwd() {
		this.setUrl("user/password.jsp");
		return SUCCESS;
	}
	
	//修改密码操作
	public void changepwd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		User bean = userDao.selectBean(" where username= '"+u.getUsername()+"' and password= '"+password1+"'");
		if(bean!=null){
			bean.setPassword(password2);
			userDao.updateBean(bean);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('修改成功');</script>");
		}else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('用户名或者密码错误');</script>");
		}
	}
	
	//老师管理员列表
	public String userlist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String truename = request.getParameter("truename");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		if (username != null && !"".equals(username)) {

			sb.append("username like '%" + username + "%'");
			sb.append(" and ");
			sb2.append("username like '%" + username + "%'");
			sb2.append(" and ");
			request.setAttribute("username", username);
		}

		if (truename != null && !"".equals(truename)) {
			sb.append("truename like '%" + truename + "%'");
			sb.append(" and ");
			sb2.append("truename like '%" + truename + "%'");
			sb2.append(" and ");
			request.setAttribute("truename", truename);
		}
		
		
		sb.append(" userlock=0 and role=3  order by id desc ");
		String where = sb.toString();
		
		sb2.append(" userlock=0 and role=3  ");
		String where2 = sb2.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where2);
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist");
		request.setAttribute("url2", "method!user");
		request.setAttribute("title", "老师管理员管理");
		this.setUrl("user/userlist.jsp");
		return SUCCESS;

	}
//跳转到添加老师管理员页面
	public String useradd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!useradd2");
		request.setAttribute("title", "老师管理员添加");
		this.setUrl("user/useradd.jsp");
		return SUCCESS;
	}
//添加老师管理员操作
	public void useradd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String truename = request.getParameter("truename");

		User bean = userDao.selectBean(" where username='"+username+"' and userlock=0  ");
		if(bean==null){
			bean = new User();
			bean.setCreatetime(new Date());
			bean.setPassword("111111");
			bean.setRole(3);
			bean.setTruename(truename);
			bean.setUsername(username);
			
			userDao.insertBean(bean);
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!userlist';</script>");
		}else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作失败，该用户名已经存在');window.location.href='method!userlist';</script>");
		}
	}
//跳转到更新老师管理员页面
	public String userupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!userupdate2");
		request.setAttribute("title", "老师管理员修改");
		this.setUrl("user/userupdate.jsp");
		return SUCCESS;
	}
//更新老师管理员操作
	public void userupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String truename = request.getParameter("truename");
		String password = request.getParameter("password");

		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setPassword(password);

		bean.setTruename(truename);
		userDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!userlist';</script>");
	}
//删除老师管理员操作
	public void userdelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setUserlock(1);
		userDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!userlist';</script>");
	}
	
	
	private XinwenDao xinwenDao;


	public XinwenDao getXinwenDao() {
		return xinwenDao;
	}

	public void setXinwenDao(XinwenDao xinwenDao) {
		this.xinwenDao = xinwenDao;
	}
	
	//站内新闻列表
	public String xinwenlist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String title = request.getParameter("title");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		if (title != null && !"".equals(title)) {

			sb.append("title like '%" + title + "%'");
			sb.append(" and ");
			
			sb2.append("title like '%" + title + "%'");
			sb2.append(" and ");
			request.setAttribute("title", title);
		}

		
		
		
		sb.append(" xinwenlock=0   order by id desc ");
		String where = sb.toString();
		
		sb2.append(" xinwenlock=0   ");
		String where2 = sb2.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = xinwenDao.selectBeanCount(where2);
		request.setAttribute("list", xinwenDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!xinwenlist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!xinwenlist");
		request.setAttribute("url2", "method!xinwen");
	
		this.setUrl("xinwen/xinwenlist.jsp");
		return SUCCESS;

	}
//跳转到添加站内新闻页面
	public String xinwenadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!xinwenadd2");
		request.setAttribute("title", "站内新闻添加");
		this.setUrl("xinwen/xinwenadd.jsp");
		return SUCCESS;
	}
//添加站内新闻操作
	public void xinwenadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String content = request.getParameter("content");
		String title = request.getParameter("title");

		Xinwen bean = new Xinwen();
		bean.setContent(content);
		bean.setCreatetime(new Date());
		bean.setTitle(title);
		xinwenDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!xinwenlist';</script>");
		
		
		
	}
//跳转到更新站内新闻页面
	public String xinwenupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Xinwen bean = xinwenDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!xinwenupdate2");
		request.setAttribute("title", "站内新闻修改");
		this.setUrl("xinwen/xinwenupdate.jsp");
		return SUCCESS;
	}
//更新站内新闻操作
	public void xinwenupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String content = request.getParameter("content");
		String title = request.getParameter("title");

		Xinwen bean = xinwenDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setContent(content);
		bean.setTitle(title);
		xinwenDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!xinwenlist';</script>");
	}
//删除站内新闻操作
	public void xinwendelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Xinwen bean = xinwenDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setXinwenlock(1);
		xinwenDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!xinwenlist';</script>");
	}
	
	//跳转到查看站内新闻页面
	public String xinwenupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Xinwen bean = xinwenDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!xinwenupdate2");
		request.setAttribute("title", "站内新闻查看");
		this.setUrl("xinwen/xinwenupdate3.jsp");
		return SUCCESS;
	}
	
	
	//毕业生用户列表
	public String userlist2() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String truename = request.getParameter("truename");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		if (username != null && !"".equals(username)) {

			sb.append("username like '%" + username + "%'");
			sb.append(" and ");
			
			sb2.append("username like '%" + username + "%'");
			sb2.append(" and ");
			request.setAttribute("username", username);
		}

		if (truename != null && !"".equals(truename)) {
			sb.append("truename like '%" + truename + "%'");
			sb.append(" and ");
			
			sb2.append("truename like '%" + truename + "%'");
			sb2.append(" and ");
			request.setAttribute("truename", truename);
		}
		
		
		sb.append(" userlock=0 and role=2  order by banji  ");
		String where = sb.toString();
		
		sb2.append(" userlock=0 and role=2    ");
		String where2 = sb2.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where2);
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist2", "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist2");
		request.setAttribute("url2", "method!user");
		request.setAttribute("title", "毕业生用户管理");
		this.setUrl("user/userlist2.jsp");
		return SUCCESS;

	}
//跳转到添加毕业生用户页面
	public String useradd3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!useradd4");
		request.setAttribute("title", "毕业生用户添加");
		request.setAttribute("list", xiDao.selectBeanList(0, 9999, " where xilock=0  "));
		this.setUrl("user/useradd3.jsp");
		return SUCCESS;
	}
//添加毕业生用户操作
	public void useradd4() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String truename = request.getParameter("truename");
		String banji = request.getParameter("banji");
		String lianxidizhi = request.getParameter("lianxidizhi");
		String lianxifangshi = request.getParameter("lianxifangshi");
		String nianji = request.getParameter("nianji");
		String xingbie = request.getParameter("xingbie");

		String zhuanye = request.getParameter("zhuanye");
		Zhuanye z =  null;
		if(zhuanye!=null&&!"".equals(zhuanye))
		z = zhuanyeDao.selectBean(" where id= "+zhuanye);
		String xi = request.getParameter("xi");
		Xi x = null;
		if(xi!=null&&!"".equals(xi))
		x = xiDao.selectBean(" where id= "+xi);

		User bean = userDao.selectBean(" where username='"+username+"' and userlock=0   and role=2 ");
		if(bean==null){
			bean = new User();
			bean.setCreatetime(new Date());
			bean.setPassword("111111");
			bean.setRole(2);
			bean.setTruename(truename);
			bean.setUsername(username);
			bean.setBanji(banji);
			bean.setLianxidizhi(lianxidizhi);
			bean.setLianxifangshi(lianxifangshi);
			bean.setNianji(nianji);
			if(z!=null){
				bean.setZhuanye(z.getName());
			}
			if(x!=null){
				bean.setXi(x.getName());
			}
			
			bean.setXingbie(xingbie);
			userDao.insertBean(bean);
			
			Jiuye jiuye = new Jiuye();
			jiuye.setCreatetime(new Date());
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			jiuye.setLaoshi(user);
			jiuye.setUser(bean);
			jiuye.setXingbie(xingbie);
			jiuyeDao.insertBean(jiuye);
			
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!userlist2';</script>");
		}else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作失败，该学号已经存在');window.location.href='method!userlist2';</script>");
		}
	}
//跳转到更新毕业生用户页面
	public String userupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!userupdate4");
		request.setAttribute("title", "毕业生用户修改");
		this.setUrl("user/userupdate3.jsp");
		request.setAttribute("list", xiDao.selectBeanList(0, 9999, " where xilock=0  "));
		return SUCCESS;
	}
//更新毕业生用户操作
	public void userupdate4() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String truename = request.getParameter("truename");
		String banji = request.getParameter("banji");
		String lianxidizhi = request.getParameter("lianxidizhi");
		String lianxifangshi = request.getParameter("lianxifangshi");
		String nianji = request.getParameter("nianji");
		String zhuanye = request.getParameter("zhuanye");
		String xingbie = request.getParameter("xingbie");
		Zhuanye z =  null;
		if(zhuanye!=null&&!"".equals(zhuanye))
		z = zhuanyeDao.selectBean(" where id= "+zhuanye);
		String xi = request.getParameter("xi");
		Xi x = null;
		if(xi!=null&&!"".equals(xi))
		x = xiDao.selectBean(" where id= "+xi);
		
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setXingbie(xingbie);
		bean.setBanji(banji);
		bean.setLianxidizhi(lianxidizhi);
		bean.setLianxifangshi(lianxifangshi);
		bean.setNianji(nianji);

		if(z!=null){
			bean.setZhuanye(z.getName());
		}
		if(x!=null){
			bean.setXi(x.getName());
		}
		bean.setTruename(truename);
		userDao.updateBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!userlist2';</script>");
	}
//删除毕业生用户操作
	public void userdelete2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setUserlock(1);
		userDao.updateBean(bean);
		Jiuye jiuye = jiuyeDao.selectBean(" where user.id= "+bean.getId());
		if(jiuye!=null){
			jiuye.setJiuyelock(1);
			jiuyeDao.updateBean(jiuye);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!userlist2';</script>");
	}
	
	//企业用户注册操作
	public void register() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String username = java.net.URLDecoder.decode(request.getParameter("username"), "utf-8");

		User user = userDao.selectBean(" where username='"+username+"' and  userlock=0 ");
		if(user==null){
			user = new User();
			user.setUsername(username);
			
			user.setPassword(request.getParameter("password"));
			
			user.setTruename(java.net.URLDecoder.decode(request.getParameter("truename"), "utf-8"));
			
			user.setQiyedizhi(java.net.URLDecoder.decode(request.getParameter("qiyedizhi"), "utf-8"));
			user.setQiyelianxidianhua(java.net.URLDecoder.decode(request.getParameter("qiyelianxidianhua"), "utf-8"));
			user.setQiyemingchen(java.net.URLDecoder.decode(request.getParameter("qiyemingchen"), "utf-8"));
			user.setFarendaibiao(java.net.URLDecoder.decode(request.getParameter("farendaibiao"), "utf-8"));
			user.setCongshihangye(java.net.URLDecoder.decode(request.getParameter("congshihangye"), "utf-8"));
			user.setQiyequyu(java.net.URLDecoder.decode(request.getParameter("qiyequyu"), "utf-8"));
			user.setCreatetime(new Date());
			user.setRole(1);
			userDao.insertBean(user);
			
			response.setCharacterEncoding("utf-8");
			
			response.getWriter().write("注册新用户成功！您的用户名"+user.getUsername()+",请妥善保管！");
		}else{
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("该用户名已经存在，请重新注册！");
		}
	}
	
	private ZhiweiDao zhiweiDao;


	public ZhiweiDao getZhiweiDao() {
		return zhiweiDao;
	}

	public void setZhiweiDao(ZhiweiDao zhiweiDao) {
		this.zhiweiDao = zhiweiDao;
	}
	
	//岗位列表
	public String zhiweilist()  {
		HttpServletRequest request = ServletActionContext.getRequest();
		String zhiweimingchen = request.getParameter("zhiweimingchen");
		
		String zhiweileibie = request.getParameter("zhiweileibie");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(zhiweimingchen!=null&&!"".equals(zhiweimingchen)){
			sb.append("zhiweimingchen like '%"+zhiweimingchen+"%'");
			sb.append(" and ");
			sb2.append("zhiweimingchen like '%"+zhiweimingchen+"%'");
			sb2.append(" and ");

			request.setAttribute("zhiweimingchen", zhiweimingchen);
		}
		
		if(zhiweileibie!=null&&!"".equals(zhiweileibie)){
			sb.append("zhiweileibie like '%"+zhiweileibie+"%'");
			sb.append(" and ");
			sb2.append("zhiweileibie like '%"+zhiweileibie+"%'");
			sb2.append(" and ");

			request.setAttribute("zhiweileibie", zhiweileibie);
		}
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		sb.append(" zhiweilock=0  and zhiweifaburen.id="+user.getId()+" order by id desc");
		String where = sb.toString();
		sb2.append(" zhiweilock=0 and zhiweifaburen.id="+user.getId());
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =10;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = zhiweiDao.selectBeanCount(where2);
		request.setAttribute("list", zhiweiDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!zhiweilist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!zhiweilist");
		request.setAttribute("url2", "method!zhiwei");
		
		this.setUrl("zhiwei/zhiweilist.jsp");
		return SUCCESS;
	}

//跳转到添加岗位页面
	public String zhiweiadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!zhiweiadd2");
		this.setUrl("zhiwei/zhiweiadd.jsp");
		return SUCCESS;
	}
//添加岗位操作
	public void zhiweiadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String gongzuodidian = request.getParameter("gongzuodidian");
		String gongzuoleixing = request.getParameter("gongzuoleixing");
		String gongzuonianxian = request.getParameter("gongzuonianxian");
		String xingbie = request.getParameter("xingbie");
		String xueli = request.getParameter("xueli");
		String youxianqi = request.getParameter("youxianqi");
		String yuexin = request.getParameter("yuexin");
		String zhaopinrenshu = request.getParameter("zhaopinrenshu");
		String zhiweileibie = request.getParameter("zhiweileibie");
		String zhiweimiaoshu = request.getParameter("zhiweimiaoshu");
		String zhiweimingchen = request.getParameter("zhiweimingchen");
		String gongzuoshengfen = request.getParameter("gongzuoshengfen");
		Zhiwei bean = new Zhiwei();
		bean.setGongzuodidian(gongzuodidian);
		bean.setGongzuoleixing(gongzuoleixing);
		bean.setGongzuonianxian(gongzuonianxian);
		bean.setXingbie(xingbie);
		bean.setXueli(xueli);
		bean.setYouxianqi(youxianqi);
		bean.setYuexin(yuexin);
		bean.setZhaopinrenshu(zhaopinrenshu);
		bean.setZhiweileibie(zhiweileibie);
		bean.setZhiweimiaoshu(zhiweimiaoshu);
		bean.setZhiweimingchen(zhiweimingchen);
		bean.setGongzuoshengfen(gongzuoshengfen);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		bean.setZhiweifaburen(user);
		bean.setCreatetime(new Date());
		zhiweiDao.insertBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!zhiweilist';</script>");

		
	}
//跳转到更新岗位页面
	public String zhiweiupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Zhiwei bean = zhiweiDao.selectBean(" where id= "+request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!zhiweiupdate2");
		this.setUrl("zhiwei/zhiweiupdate.jsp");
		return SUCCESS;
	}
//更新岗位操作
	public void zhiweiupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String gongzuodidian = request.getParameter("gongzuodidian");
		String gongzuoleixing = request.getParameter("gongzuoleixing");
		String gongzuonianxian = request.getParameter("gongzuonianxian");
		String xingbie = request.getParameter("xingbie");
		String xueli = request.getParameter("xueli");
		String youxianqi = request.getParameter("youxianqi");
		String yuexin = request.getParameter("yuexin");
		String zhaopinrenshu = request.getParameter("zhaopinrenshu");
		String zhiweileibie = request.getParameter("zhiweileibie");
		String zhiweimiaoshu = request.getParameter("zhiweimiaoshu");
		String zhiweimingchen = request.getParameter("zhiweimingchen");
		Zhiwei bean = zhiweiDao.selectBean(" where id= "+request.getParameter("id"));
		bean.setGongzuodidian(gongzuodidian);
		bean.setGongzuoleixing(gongzuoleixing);
		bean.setGongzuonianxian(gongzuonianxian);
		bean.setXingbie(xingbie);
		bean.setXueli(xueli);
		bean.setYouxianqi(youxianqi);
		bean.setYuexin(yuexin);
		bean.setZhaopinrenshu(zhaopinrenshu);
		bean.setZhiweileibie(zhiweileibie);
		bean.setZhiweimiaoshu(zhiweimiaoshu);
		bean.setZhiweimingchen(zhiweimingchen);
		zhiweiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!zhiweilist';</script>");
	}

//查看岗位操作
	public String zhiweiupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Zhiwei bean = zhiweiDao.selectBean(" where id= "+request.getParameter("id"));
		request.setAttribute("bean", bean);
		this.setUrl("zhiwei/zhiweiupdate3.jsp");
		return SUCCESS;
	}
//删除岗位操作
	public void zhiweidelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Zhiwei bean = zhiweiDao.selectBean(" where id= "+request.getParameter("id"));
		bean.setZhiweilock(1);
		zhiweiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!zhiweilist';</script>");
	}
	
	private  JianliDao jianliDao;


	public JianliDao getJianliDao() {
		return jianliDao;
	}

	public void setJianliDao(JianliDao jianliDao) {
		this.jianliDao = jianliDao;
	}
	
	//简历列表
	public String jianlilist()  {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jianlimingchen = request.getParameter("jianlimingchen");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(jianlimingchen!=null&&!"".equals(jianlimingchen)){
			sb.append("jianlimingchen like '%"+jianlimingchen+"%'");
			sb.append(" and ");
			sb2.append("jianlimingchen like '%"+jianlimingchen+"%'");
			sb2.append(" and ");

			request.setAttribute("jianlimingchen", jianlimingchen);
		}
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		sb.append(" jianlilock=0  and qiuzhiren.id="+user.getId()+" order by id desc");
		String where = sb.toString();
		sb2.append(" jianlilock=0 and qiuzhiren.id="+user.getId());
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =10;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = jianliDao.selectBeanCount(where2);
		request.setAttribute("list", jianliDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!jianlilist", "共有" + total + "条记录"));
		this.setUrl("jianli/jianlilist.jsp");
		return SUCCESS;
	}

//跳转到添加简历页面
	public String jianliadd() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		Jianli jianli = jianliDao.selectBean(" where qiuzhiren.id="+user.getId()+" and jianlilock=0 ");
		if(jianli!=null){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('你已经添加简历，请勿重复添加');window.location.href='method!jianlilist';</script>");
			return null;
		}
		this.setUrl("jianli/jianliadd.jsp");
		return SUCCESS;
	}
	
	
	private File uploadfile;
	
	public File getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(File uploadfile) {
		this.uploadfile = uploadfile;
	}
	
	
	
	
	
	
//添加简历操作
	public void jianliadd2() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jianlimingchen = request.getParameter("jianlimingchen");
		String filename = request.getParameter("filename");
		Jianli bean = new Jianli();
		bean.setCreatetime(new Date());
		bean.setJianlimingchen(jianlimingchen);
		String savaPath = ServletActionContext.getServletContext().getRealPath("/")+ "/uploadfile/";
		String time = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString();
		String filename2 = filename.substring(filename.lastIndexOf("\\")+1,filename.length());
		String path = time +"_"+filename2;
		File file = new File(savaPath + path);
		Util.copyFile(uploadfile, file);
		String name = time +".zip";
		Util.createZip(path, name, savaPath);

		bean.setPath(name);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		bean.setQiuzhiren(user);
		jianliDao.insertBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!jianlilist';</script>");

		
	}

//删除简历操作
	public void jianlidelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Jianli bean = jianliDao.selectBean(" where id= "+request.getParameter("id"));
		bean.setJianlilock(1);
		jianliDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!jianlilist';</script>");
	}
	
	
	//查询就业岗位
	public String zhiweilist2()  {
		HttpServletRequest request = ServletActionContext.getRequest();
		String zhiweimingchen = request.getParameter("zhiweimingchen");
		
		String zhiweileibie = request.getParameter("zhiweileibie");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(zhiweimingchen!=null&&!"".equals(zhiweimingchen)){
			sb.append("zhiweimingchen like '%"+zhiweimingchen+"%'");
			sb.append(" and ");
			sb2.append("zhiweimingchen like '%"+zhiweimingchen+"%'");
			sb2.append(" and ");

			request.setAttribute("zhiweimingchen", zhiweimingchen);
		}
		
		if(zhiweileibie!=null&&!"".equals(zhiweileibie)){
			sb.append("zhiweileibie like '%"+zhiweileibie+"%'");
			sb.append(" and ");
			sb2.append("zhiweileibie like '%"+zhiweileibie+"%'");
			sb2.append(" and ");

			request.setAttribute("zhiweileibie", zhiweileibie);
		}
		
		
		sb.append(" zhiweilock=0   order by id desc");
		String where = sb.toString();
		sb2.append(" zhiweilock=0 ");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =10;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = zhiweiDao.selectBeanCount(where2);
		request.setAttribute("list", zhiweiDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!zhiweilist2", "共有" + total + "条记录"));
		request.setAttribute("url", "method!zhiweilist2");
		request.setAttribute("url2", "method!zhiwei");
		
		this.setUrl("zhiwei/zhiweilist2.jsp");
		return SUCCESS;
	}
	
	
	private ToudijiluDao toudijiluDao;
	
	
	public ToudijiluDao getToudijiluDao() {
		return toudijiluDao;
	}

	public void setToudijiluDao(ToudijiluDao toudijiluDao) {
		this.toudijiluDao = toudijiluDao;
	}

	//投递简历操作
	public void toutijianli() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		User u = userDao.selectBean(" where id= "+user.getId());
		
		if(u.getZhiwei()!=null){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('您已经成功就业，投递失败！');window.location.href='method!zhiweilist2';</script>");
			return ;
		}
		
		Jianli jianli = jianliDao.selectBean(" where qiuzhiren.id= "+user.getId());
		if(jianli==null){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('请先填写您的简历，在投递');window.location.href='method!zhiweilist2';</script>");
			return ;
		}
		Zhiwei zhiwei = zhiweiDao.selectBean(" where id= "+request.getParameter("id"));
		
		Toudijilu bean = toudijiluDao.selectBean(" where zhiwei.id= "+zhiwei.getId()+" and jianli.id= "+ jianli.getId() +"  and readzhuangtai='未阅读'");
		if(bean!=null){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('您投递的该职位的简历还未阅读，请勿重复投递');window.location.href='method!zhiweilist2';</script>");
			return ;
		}
		bean = new Toudijilu();
		bean.setCreatetime(new Date());
		bean.setJianli(jianli);
		bean.setZhiwei(zhiwei);
		bean.setReadzhuangtai("未阅读");
		bean.setYingpinzhuangtai("未应聘");
		toudijiluDao.insertBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!toudijilulist2';</script>");
	}
	
	
	//职位投递记录管理（求职者）
	public String toudijilulist2()  {
		HttpServletRequest request = ServletActionContext.getRequest();
		String zhiweimingchen = request.getParameter("zhiweimingchen");//职位名称
		String readzhuangtai = request.getParameter("readzhuangtai");//是否阅读

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(zhiweimingchen!=null&&!"".equals(zhiweimingchen)){
			sb.append("zhiwei.zhiweimingchen like '%"+zhiweimingchen+"%'");
			sb.append(" and ");
			sb2.append("zhiwei.zhiweimingchen like '%"+zhiweimingchen+"%'");
			sb2.append(" and ");

			request.setAttribute("zhiweimingchen", zhiweimingchen);
		}
		
		if(readzhuangtai!=null&&!"".equals(readzhuangtai)){
			sb.append("readzhuangtai like '%"+readzhuangtai+"%'");
			sb.append(" and ");
			sb2.append("readzhuangtai like '%"+readzhuangtai+"%'");
			sb2.append(" and ");

			request.setAttribute("readzhuangtai", readzhuangtai);
		}
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		sb.append("  jianli.qiuzhiren.id="+user.getId()+" order by id desc");
		String where = sb.toString();
		sb2.append(" jianli.qiuzhiren.id="+user.getId());
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =10;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = toudijiluDao.selectBeanCount(where2);
		request.setAttribute("list", toudijiluDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!toudijilulist2", "共有" + total + "条记录"));
		this.setUrl("toudijilu/toudijilulist2.jsp");
		return SUCCESS;
	}
	
	//查看招聘单位
	public String userlist3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String qiyemingchen = request.getParameter("qiyemingchen");
		String congshihangye = request.getParameter("congshihangye");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		if (qiyemingchen != null && !"".equals(qiyemingchen)) {

			sb.append("qiyemingchen like '%" + qiyemingchen + "%'");
			sb.append(" and ");
			
			sb2.append("qiyemingchen like '%" + qiyemingchen + "%'");
			sb2.append(" and ");
			request.setAttribute("qiyemingchen", qiyemingchen);
		}

		if (congshihangye != null && !"".equals(congshihangye)) {
			sb.append("congshihangye like '%" + congshihangye + "%'");
			sb.append(" and ");
			sb2.append("qiyemingchen like '%" + qiyemingchen + "%'");
			sb2.append(" and ");
			
			request.setAttribute("congshihangye", congshihangye);
		}
		
		
		sb.append(" userlock=0 and role=1  order by id desc ");
		String where = sb.toString();
		
		sb2.append(" userlock=0 and role=1 ");
		String where2 = sb2.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where2);
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist3", "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist3");
		request.setAttribute("url2", "method!user");
		this.setUrl("user/userlist3.jsp");
		return SUCCESS;

	}
	
	
	//职位投递记录管理（职位发布人）
	public String toudijilulist()  {
		HttpServletRequest request = ServletActionContext.getRequest();
		String zhiweimingchen = request.getParameter("zhiweimingchen");//职位名称
		String readzhuangtai = request.getParameter("readzhuangtai");//职位名称

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(zhiweimingchen!=null&&!"".equals(zhiweimingchen)){
			sb.append("zhiwei.zhiweimingchen like '%"+zhiweimingchen+"%'");
			sb.append(" and ");
			sb2.append("zhiwei.zhiweimingchen like '%"+zhiweimingchen+"%'");
			sb2.append(" and ");

			request.setAttribute("zhiweimingchen", zhiweimingchen);
		}
		
		if(readzhuangtai!=null&&!"".equals(readzhuangtai)){
			sb.append("readzhuangtai like '%"+readzhuangtai+"%'");
			sb.append(" and ");
			sb2.append("readzhuangtai like '%"+readzhuangtai+"%'");
			sb2.append(" and ");

			request.setAttribute("readzhuangtai", readzhuangtai);
		}
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		sb.append("  zhiwei.zhiweifaburen.id="+user.getId()+" order by id desc");
		String where = sb.toString();
		sb2.append(" zhiwei.zhiweifaburen.id="+user.getId());
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =10;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = toudijiluDao.selectBeanCount(where2);
		request.setAttribute("list", toudijiluDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!toudijilulist", "共有" + total + "条记录"));
		this.setUrl("toudijilu/toudijilulist.jsp");
		return SUCCESS;
	}
	
	
	//招聘录入操作
	public void luru() throws IOException, ParseException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Toudijilu bean = toudijiluDao.selectBean(" where id= "+request.getParameter("id"));
		User user = userDao.selectBean(" where id= "+bean.getJianli().getQiuzhiren().getId());
		if(user.getZhiwei()!=null){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作失败，该求职者已经成功应聘');window.location.href='method!toudijilulist';</script>");
			return;
		}
		Zhiwei zhiwei = zhiweiDao.selectBean(" where id= "+bean.getZhiwei().getId());
		user.setZhiwei(zhiwei);
		
		userDao.updateBean(user);
		bean.setYingpinzhuangtai("应聘成功");
		toudijiluDao.updateBean(bean);
		User u2 = userDao.selectBean(" where id= "+zhiwei.getZhiweifaburen().getId());
		
		Jiuye jiuye = jiuyeDao.selectBean(" where user.id= "+user.getId());
		
		jiuye.setCongshihangye(u2.getCongshihangye());
		jiuye.setJiuyefenbu(u2.getQiyequyu());
		jiuye.setJiuyeshijian(Util.getRiqi());
		jiuye.setJiuyeshengfen(zhiwei.getGongzuoshengfen());
		jiuye.setJiuyeyuefen(Util.getYuefen());

		if(Util.getTime(jiuye.getJiuyeshijian())<Util.getTime(Util.BIYESHIJIAN)){
			jiuye.setBiyeqianhoujiuye("毕业前就业");
		}else{
			jiuye.setBiyeqianhoujiuye("毕业后就业");
		}
		jiuyeDao.updateBean(jiuye);
		
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!toudijilulist';</script>");
	}
	
	
	//删除投递记录操作
	public void toudijiludelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Toudijilu bean = toudijiluDao.selectBean(" where id= "+request.getParameter("id"));
		
		toudijiluDao.deleteBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!toudijilulist';</script>");
	}
	
	//下载简历
	public void jianlixiazai() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Toudijilu bean = toudijiluDao.selectBean(" where id= "+request.getParameter("id"));
		bean.setReadzhuangtai("已阅读");
		toudijiluDao.updateBean(bean);
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		HttpServletResponse response = ServletActionContext.getResponse();
		response.sendRedirect(basePath+"uploadfile/"+bean.getJianli().getPath());
		
	}
	
	
	
	//企业用户管理
	public String userlist4() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String qiyemingchen = request.getParameter("qiyemingchen");
		String congshihangye = request.getParameter("congshihangye");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		if (username != null && !"".equals(username)) {

			sb.append("username like '%" + username + "%'");
			sb.append(" and ");
			
			sb2.append("username like '%" + username + "%'");
			sb2.append(" and ");
			request.setAttribute("username", username);
		}
		
		if (qiyemingchen != null && !"".equals(qiyemingchen)) {

			sb.append("qiyemingchen like '%" + qiyemingchen + "%'");
			sb.append(" and ");
			sb2.append("qiyemingchen like '%" + qiyemingchen + "%'");
			sb2.append(" and ");
			request.setAttribute("qiyemingchen", qiyemingchen);
		}

		if (congshihangye != null && !"".equals(congshihangye)) {
			sb.append("congshihangye like '%" + congshihangye + "%'");
			sb.append(" and ");
			sb2.append("congshihangye like '%" + congshihangye + "%'");
			sb2.append(" and ");
			request.setAttribute("congshihangye", congshihangye);
		}
		
		
		sb.append(" userlock=0 and role=1  order by id desc ");
		String where = sb.toString();
		
		sb2.append(" userlock=0 and role=1  ");
		String where2 = sb2.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where2);
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist4", "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist4");
		request.setAttribute("url2", "method!user");
		this.setUrl("user/userlist4.jsp");
		return SUCCESS;

	}
	
	
	//删除企业用户操作
	public void userdelete4() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setUserlock(1);
		userDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!userlist4';</script>");
	}
	
	
	//岗位列表(管理员权限)
	public String zhiweilist3()  {
		HttpServletRequest request = ServletActionContext.getRequest();
		String zhiweimingchen = request.getParameter("zhiweimingchen");
		
		String zhiweileibie = request.getParameter("zhiweileibie");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(zhiweimingchen!=null&&!"".equals(zhiweimingchen)){
			sb.append("zhiweimingchen like '%"+zhiweimingchen+"%'");
			sb.append(" and ");
			sb2.append("zhiweimingchen like '%"+zhiweimingchen+"%'");
			sb2.append(" and ");

			request.setAttribute("zhiweimingchen", zhiweimingchen);
		}
		
		if(zhiweileibie!=null&&!"".equals(zhiweileibie)){
			sb.append("zhiweileibie like '%"+zhiweileibie+"%'");
			sb.append(" and ");
			sb2.append("zhiweileibie like '%"+zhiweileibie+"%'");
			sb2.append(" and ");

			request.setAttribute("zhiweileibie", zhiweileibie);
		}

		
		sb.append(" zhiweilock=0  order by id desc");
		String where = sb.toString();
		sb2.append(" zhiweilock=0 ");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =10;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = zhiweiDao.selectBeanCount(where2);
		request.setAttribute("list", zhiweiDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!zhiweilist3", "共有" + total + "条记录"));
		request.setAttribute("url", "method!zhiweilist3");
		request.setAttribute("url2", "method!zhiwei");
		
		this.setUrl("zhiwei/zhiweilist3.jsp");
		return SUCCESS;
	}
	
	//删除岗位操作(管理员权限)
	public void zhiweidelete2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Zhiwei bean = zhiweiDao.selectBean(" where id= "+request.getParameter("id"));
		bean.setZhiweilock(1);
		zhiweiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!zhiweilist3';</script>");
	}
	
	
	private WendangDao wendangDao;


	public WendangDao getWendangDao() {
		return wendangDao;
	}

	public void setWendangDao(WendangDao wendangDao) {
		this.wendangDao = wendangDao;
	}
	
	
	
	//文档列表
	public String wendanglist()  {
		HttpServletRequest request = ServletActionContext.getRequest();
		String title = request.getParameter("title");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(title!=null&&!"".equals(title)){
			sb.append("title like '%"+title+"%'");
			sb.append(" and ");
			sb2.append("title like '%"+title+"%'");
			sb2.append(" and ");

			request.setAttribute("title", title);
		}
		
		

		
		sb.append(" wendanglock=0   order by id desc");
		String where = sb.toString();
		sb2.append(" wendanglock=0 ");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =10;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = wendangDao.selectBeanCount(where2);
		request.setAttribute("list", wendangDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!wendanglist", "共有" + total + "条记录"));
		this.setUrl("wendang/wendanglist.jsp");
		return SUCCESS;
	}

//跳转到添加文档页面
	public String wendangadd() throws IOException {

		this.setUrl("wendang/wendangadd.jsp");
		return SUCCESS;
	}
	

	
	
	
	
	
	
//添加文档操作
	public void wendangadd2() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String title = request.getParameter("title");
		String filename = request.getParameter("filename");
		Wendang bean = new Wendang();
		bean.setCreatetime(new Date());
		bean.setTitle(title);
		
		String savaPath = ServletActionContext.getServletContext().getRealPath("/")+ "/uploadfile/";
		String time = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString();
		String filename2 = filename.substring(filename.lastIndexOf("\\")+1,filename.length());
		String path = time +"_"+filename2;
		File file = new File(savaPath + path);
		Util.copyFile(uploadfile, file);
		String name = time +".zip";
		Util.createZip(path, name, savaPath);

		bean.setPath(name);
		wendangDao.insertBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!wendanglist';</script>");

		
	}

//删除文档操作
	public void wendangdelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Wendang bean = wendangDao.selectBean(" where id= "+request.getParameter("id"));
		bean.setWendanglock(1);
		wendangDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!wendanglist';</script>");
	}
	
	private GonggaoDao gonggaoDao;


	public GonggaoDao getGonggaoDao() {
		return gonggaoDao;
	}

	public void setGonggaoDao(GonggaoDao gonggaoDao) {
		this.gonggaoDao = gonggaoDao;
	}
	
	
	//公告列表
	public String gonggaolist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String title = request.getParameter("title");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		if (title != null && !"".equals(title)) {

			sb.append("title like '%" + title + "%'");
			sb.append(" and ");
			
			sb2.append("title like '%" + title + "%'");
			sb2.append(" and ");
			request.setAttribute("title", title);
		}

		
		
		
		sb.append(" gonggaolock=0   order by id desc ");
		String where = sb.toString();
		
		sb2.append(" gonggaolock=0    ");
		String where2 = sb2.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = gonggaoDao.selectBeanCount(where2);
		request.setAttribute("list", gonggaoDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!gonggaolist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!gonggaolist");
		request.setAttribute("url2", "method!gonggao");
	
		this.setUrl("gonggao/gonggaolist.jsp");
		return SUCCESS;

	}
//跳转到添加公告页面
	public String gonggaoadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!gonggaoadd2");
		request.setAttribute("title", "公告添加");
		this.setUrl("gonggao/gonggaoadd.jsp");
		return SUCCESS;
	}
//添加公告操作
	public void gonggaoadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String content = request.getParameter("content");
		String title = request.getParameter("title");

		Gonggao bean = new Gonggao();
		bean.setContent(content);
		bean.setCreatetime(new Date());
		bean.setTitle(title);
		gonggaoDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!gonggaolist';</script>");
		
		
		
	}
//跳转到更新公告页面
	public String gonggaoupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Gonggao bean = gonggaoDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!gonggaoupdate2");
		request.setAttribute("title", "公告修改");
		this.setUrl("gonggao/gonggaoupdate.jsp");
		return SUCCESS;
	}
//更新公告操作
	public void gonggaoupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String content = request.getParameter("content");
		String title = request.getParameter("title");

		Gonggao bean = gonggaoDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setContent(content);
		bean.setTitle(title);
		gonggaoDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!gonggaolist';</script>");
	}
//删除公告操作
	public void gonggaodelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Gonggao bean = gonggaoDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setGonggaolock(1);
		gonggaoDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!gonggaolist';</script>");
	}
	
	//跳转到查看公告页面
	public String gonggaoupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Gonggao bean = gonggaoDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!gonggaoupdate2");
		request.setAttribute("title", "公告查看");
		this.setUrl("gonggao/gonggaoupdate3.jsp");
		return SUCCESS;
	}

	private LiuyanDao liuyanDao;


	public LiuyanDao getLiuyanDao() {
		return liuyanDao;
	}

	public void setLiuyanDao(LiuyanDao liuyanDao) {
		this.liuyanDao = liuyanDao;
	}

	
	//留言列表
	public String liuyanlist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String title = request.getParameter("title");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		if (title != null && !"".equals(title)) {

			sb.append("title like '%" + title + "%'");
			sb.append(" and ");
			
			sb2.append("title like '%" + title + "%'");
			sb2.append(" and ");
			request.setAttribute("title", title);
		}

		
		
		
		sb.append(" liuyanlock=0   order by id desc ");
		String where = sb.toString();
		
		sb2.append(" liuyanlock=0    ");
		String where2 = sb2.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = liuyanDao.selectBeanCount(where2);
		request.setAttribute("list", liuyanDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!liuyanlist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!liuyanlist");
		request.setAttribute("url2", "method!liuyan");
	
		this.setUrl("liuyan/liuyanlist.jsp");
		return SUCCESS;

	}
//跳转到添加留言页面
	public String liuyanadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!liuyanadd2");
		request.setAttribute("title", "留言添加");
		this.setUrl("liuyan/liuyanadd.jsp");
		return SUCCESS;
	}
//添加留言操作
	public void liuyanadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String content = request.getParameter("content");
		String title = request.getParameter("title");

		Liuyan bean = new Liuyan();
		bean.setContent(content);
		bean.setCreatetime(new Date());
		bean.setTitle(title);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		bean.setUser(user);
		liuyanDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!liuyanlist2';</script>");
		
		
		
	}

//删除留言操作
	public void liuyandelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Liuyan bean = liuyanDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setLiuyanlock(1);
		liuyanDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!liuyanlist';</script>");
	}
	
	//跳转到查看留言页面
	public String liuyanupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Liuyan bean = liuyanDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!liuyanupdate2");
		request.setAttribute("title", "留言查看");
		this.setUrl("liuyan/liuyanupdate3.jsp");
		return SUCCESS;
	}
	
	
	//站内新闻列表
	public String xinwenlist2() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String title = request.getParameter("title");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		if (title != null && !"".equals(title)) {

			sb.append("title like '%" + title + "%'");
			sb.append(" and ");
			
			sb2.append("title like '%" + title + "%'");
			sb2.append(" and ");
			request.setAttribute("title", title);
		}

		
		
		
		sb.append(" xinwenlock=0   order by id desc ");
		String where = sb.toString();
		
		sb2.append(" xinwenlock=0    ");
		String where2 = sb2.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = xinwenDao.selectBeanCount(where2);
		request.setAttribute("list", xinwenDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!xinwenlist2", "共有" + total + "条记录"));
		request.setAttribute("url", "method!xinwenlist2");
		request.setAttribute("url2", "method!xinwen");
	
		this.setUrl("xinwen/xinwenlist2.jsp");
		return SUCCESS;

	}
	
	
	//文档列表
	public String wendanglist2()  {
		HttpServletRequest request = ServletActionContext.getRequest();
		String title = request.getParameter("title");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(title!=null&&!"".equals(title)){
			sb.append("title like '%"+title+"%'");
			sb.append(" and ");
			sb2.append("title like '%"+title+"%'");
			sb2.append(" and ");

			request.setAttribute("title", title);
		}
		
		

		
		sb.append(" wendanglock=0   order by id desc");
		String where = sb.toString();
		sb2.append(" wendanglock=0 ");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =10;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = wendangDao.selectBeanCount(where2);
		request.setAttribute("list", wendangDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!wendanglist2", "共有" + total + "条记录"));
		this.setUrl("wendang/wendanglist2.jsp");
		return SUCCESS;
	}
	
	
	//公告列表
	public String gonggaolist2() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String title = request.getParameter("title");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		if (title != null && !"".equals(title)) {

			sb.append("title like '%" + title + "%'");
			sb.append(" and ");
			
			sb2.append("title like '%" + title + "%'");
			sb2.append(" and ");
			request.setAttribute("title", title);
		}

		
		
		
		sb.append(" gonggaolock=0   order by id desc ");
		String where = sb.toString();
		
		sb2.append(" gonggaolock=0    ");
		String where2 = sb2.toString();
		

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = gonggaoDao.selectBeanCount(where2);
		request.setAttribute("list", gonggaoDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!gonggaolist2", "共有" + total + "条记录"));
		request.setAttribute("url", "method!gonggaolist2");
		request.setAttribute("url2", "method!gonggao");
	
		this.setUrl("gonggao/gonggaolist2.jsp");
		return SUCCESS;

	}
	
	
	
	//留言列表
	public String liuyanlist2() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String title = request.getParameter("title");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		if (title != null && !"".equals(title)) {

			sb.append("title like '%" + title + "%'");
			sb.append(" and ");
			sb2.append("title like '%" + title + "%'");
			sb2.append(" and ");
			
			request.setAttribute("title", title);
		}

		
		
		
		sb.append(" liuyanlock=0   order by id desc ");
		String where = sb.toString();
		
		sb2.append(" liuyanlock=0   ");
		String where2 = sb2.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = liuyanDao.selectBeanCount(where2);
		request.setAttribute("list", liuyanDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!liuyanlist2", "共有" + total + "条记录"));
		request.setAttribute("url", "method!liuyanlist2");
		request.setAttribute("url2", "method!liuyan");
	
		this.setUrl("liuyan/liuyanlist2.jsp");
		return SUCCESS;

	}
	
	
	//企业用户个人信息管理
	public String userlist5() {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		sb.append(" id= "+user.getId());
		String where = sb.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where);
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist5", "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist5");
		request.setAttribute("url2", "method!user");
		this.setUrl("user/userlist5.jsp");
		return SUCCESS;

	}
	
	
	//跳转到更新个人信息页面
	public String userupdate5() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!userupdate6");
		request.setAttribute("title", "个人信息修改");
		this.setUrl("user/userupdate5.jsp");
		return SUCCESS;
	}
//更新个人信息操作
	public void userupdate6() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String truename = request.getParameter("truename");
		String qiyedizhi = request.getParameter("qiyedizhi");
		String qiyelianxidianhua = request.getParameter("qiyelianxidianhua");
		String qiyemingchen = request.getParameter("qiyemingchen");
		String congshihangye = request.getParameter("congshihangye");
		String farendaibiao = request.getParameter("farendaibiao");
		

		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setQiyedizhi(qiyedizhi);
		bean.setQiyelianxidianhua(qiyelianxidianhua);
		bean.setQiyemingchen(qiyemingchen);
		bean.setCongshihangye(congshihangye);
		bean.setFarendaibiao(farendaibiao);
		bean.setTruename(truename);
		userDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!userlist5';</script>");
	}
	
	
	//毕业生用户个人信息管理
	public String userlist6() {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		sb.append(" id= "+user.getId());
		String where = sb.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where);
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist6", "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist6");
		request.setAttribute("url2", "method!user");
		this.setUrl("user/userlist6.jsp");
		return SUCCESS;

	}
	
	
	//跳转到更新个人信息页面
	public String userupdate7() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!userupdate8");
		request.setAttribute("title", "个人信息修改");
		this.setUrl("user/userupdate7.jsp");
		request.setAttribute("list", xiDao.selectBeanList(0, 9999, " where xilock=0  "));
		return SUCCESS;
	}
//更新个人信息操作
	public void userupdate8() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String truename = request.getParameter("truename");
		String banji = request.getParameter("banji");
		String lianxidizhi = request.getParameter("lianxidizhi");
		String lianxifangshi = request.getParameter("lianxifangshi");
		String nianji = request.getParameter("nianji");

		String zhuanye = request.getParameter("zhuanye");
		Zhuanye z =  null;
		if(zhuanye!=null&&!"".equals(zhuanye))
		z = zhuanyeDao.selectBean(" where id= "+zhuanye);
		String xi = request.getParameter("xi");
		Xi x = null;
		if(xi!=null&&!"".equals(xi))
		x = xiDao.selectBean(" where id= "+xi);

		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setTruename(truename);
		bean.setBanji(banji);
		bean.setLianxidizhi(lianxidizhi);
		bean.setLianxifangshi(lianxifangshi);
		bean.setNianji(nianji);

		if(z!=null){
			bean.setZhuanye(z.getName());
		}
		if(x!=null){
			bean.setXi(x.getName());
		}
		userDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!userlist6';</script>");
	}
	
	private  JiuyeDao jiuyeDao;


	public JiuyeDao getJiuyeDao() {
		return jiuyeDao;
	}

	public void setJiuyeDao(JiuyeDao jiuyeDao) {
		this.jiuyeDao = jiuyeDao;
	}
	
	//就业情况查询
	public String jiuyelist()  {
		HttpServletRequest request = ServletActionContext.getRequest();
		String truename = request.getParameter("truename");
		String jiuyefenbu = request.getParameter("jiuyefenbu");
		String xingbie = request.getParameter("xingbie");
		String congshihangye = request.getParameter("congshihangye");
		String zhuanye = request.getParameter("zhuanye");


		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(truename!=null&&!"".equals(truename)){
			sb.append("user.truename like '%"+truename+"%'");
			sb.append(" and ");
			sb2.append("user.truename like '%"+truename+"%'");
			sb2.append(" and ");

			request.setAttribute("truename", truename);
		}
		
		if(jiuyefenbu!=null&&!"".equals(jiuyefenbu)){
			sb.append("jiuyefenbu like '%"+jiuyefenbu+"%'");
			sb.append(" and ");
			sb2.append("jiuyefenbu like '%"+jiuyefenbu+"%'");
			sb2.append(" and ");

			request.setAttribute("jiuyefenbu", jiuyefenbu);
		}
		
		if(xingbie!=null&&!"".equals(xingbie)){
			sb.append("xingbie like '%"+xingbie+"%'");
			sb.append(" and ");
			sb2.append("xingbie like '%"+xingbie+"%'");
			sb2.append(" and ");

			request.setAttribute("xingbie", xingbie);
		}
		
		if(congshihangye!=null&&!"".equals(congshihangye)){
			sb.append("congshihangye like '%"+congshihangye+"%'");
			sb.append(" and ");
			sb2.append("congshihangye like '%"+congshihangye+"%'");
			sb2.append(" and ");

			request.setAttribute("congshihangye", congshihangye);
		}
		
		if(zhuanye!=null&&!"".equals(zhuanye)){
			sb.append(" user.zhuanye like '%"+zhuanye+"%'");
			sb.append(" and ");
			sb2.append(" user.zhuanye like '%"+zhuanye+"%'");
			sb2.append(" and ");

			request.setAttribute("zhuanye", zhuanye);
		}
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if("admin".equals(user.getUsername())){
			sb.append("  jiuyelock=0 order by id desc");
			sb2.append(" jiuyelock=0 ");
		}else{
			sb.append(" jiuyelock=0 and  laoshi.id="+user.getId()+" order by id desc");
			sb2.append(" jiuyelock=0 and laoshi.id="+user.getId());
		}
		
		
		String where = sb.toString();
		
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =10;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = jiuyeDao.selectBeanCount(where2);
		request.setAttribute("list", jiuyeDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!jiuyelist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!jiuyelist");
		
		this.setUrl("jiuye/jiuyelist.jsp");
		return SUCCESS;
	}
	
	
	//就业情况(打印信息)
	public String jiuyelist2()  {
		HttpServletRequest request = ServletActionContext.getRequest();
		String truename = request.getParameter("truename");
		String jiuyefenbu = request.getParameter("jiuyefenbu");
		String xingbie = request.getParameter("xingbie");
		String congshihangye = request.getParameter("congshihangye");
		String zhuanye = request.getParameter("zhuanye");


		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(truename!=null&&!"".equals(truename)){
			sb.append("user.truename like '%"+truename+"%'");
			sb.append(" and ");
			sb2.append("user.truename like '%"+truename+"%'");
			sb2.append(" and ");

			request.setAttribute("truename", truename);
		}
		
		if(jiuyefenbu!=null&&!"".equals(jiuyefenbu)){
			sb.append("jiuyefenbu like '%"+jiuyefenbu+"%'");
			sb.append(" and ");
			sb2.append("jiuyefenbu like '%"+jiuyefenbu+"%'");
			sb2.append(" and ");

			request.setAttribute("jiuyefenbu", jiuyefenbu);
		}
		
		if(xingbie!=null&&!"".equals(xingbie)){
			sb.append("xingbie like '%"+xingbie+"%'");
			sb.append(" and ");
			sb2.append("xingbie like '%"+xingbie+"%'");
			sb2.append(" and ");

			request.setAttribute("xingbie", xingbie);
		}
		
		if(congshihangye!=null&&!"".equals(congshihangye)){
			sb.append("congshihangye like '%"+congshihangye+"%'");
			sb.append(" and ");
			sb2.append("congshihangye like '%"+congshihangye+"%'");
			sb2.append(" and ");

			request.setAttribute("congshihangye", congshihangye);
		}
		
		if(zhuanye!=null&&!"".equals(zhuanye)){
			sb.append(" user.zhuanye like '%"+zhuanye+"%'");
			sb.append(" and ");
			sb2.append(" user.zhuanye like '%"+zhuanye+"%'");
			sb2.append(" and ");

			request.setAttribute("zhuanye", zhuanye);
		}
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if("admin".equals(user.getUsername())){
			sb.append("  jiuyelock=0 order by id desc");
			sb2.append(" jiuyelock=0 ");
			
		}else{
			sb.append(" jiuyelock=0 and  laoshi.id="+user.getId()+" order by id desc");
			sb2.append(" jiuyelock=0 and  laoshi.id="+user.getId());
		}
		
		
		
		String where = sb.toString();
		
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =10000;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = jiuyeDao.selectBeanCount(where2);
		request.setAttribute("list", jiuyeDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!jiuyelist2", "共有" + total + "条记录"));
		request.setAttribute("url", "method!jiuyelist2");
		
		this.setUrl("jiuye/jiuyelist2.jsp");
		return SUCCESS;
	}
	
	
	//导出excel

	public String jiuyelist3() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String truename = request.getParameter("truename");
		String jiuyefenbu = request.getParameter("jiuyefenbu");
		String xingbie = request.getParameter("xingbie");
		String congshihangye = request.getParameter("congshihangye");
		String zhuanye = request.getParameter("zhuanye");


		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if(truename!=null&&!"".equals(truename)){
			sb.append("user.truename like '%"+truename+"%'");
			sb.append(" and ");


			request.setAttribute("truename", truename);
		}
		
		if(jiuyefenbu!=null&&!"".equals(jiuyefenbu)){
			sb.append("jiuyefenbu like '%"+jiuyefenbu+"%'");
			sb.append(" and ");


			request.setAttribute("jiuyefenbu", jiuyefenbu);
		}
		
		if(xingbie!=null&&!"".equals(xingbie)){
			sb.append("xingbie like '%"+xingbie+"%'");
			sb.append(" and ");


			request.setAttribute("xingbie", xingbie);
		}
		
		if(congshihangye!=null&&!"".equals(congshihangye)){
			sb.append("congshihangye like '%"+congshihangye+"%'");
			sb.append(" and ");


			request.setAttribute("congshihangye", congshihangye);
		}
		
		if(zhuanye!=null&&!"".equals(zhuanye)){
			sb.append(" user.zhuanye like '%"+zhuanye+"%'");
			sb.append(" and ");


			request.setAttribute("zhuanye", zhuanye);
		}
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		if("admin".equals(user.getUsername())){
			sb.append("  jiuyelock=0   order by id desc");

			
		}else{
			sb.append(" jiuyelock=0 and  laoshi.id="+user.getId()+" order by id desc");
	
		}
		
		
		
		String where = sb.toString();


		
		int currentpage = 1;
		int pagesize =10000;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}



		List<Jiuye> list = jiuyeDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where);
		
		
		HttpServletResponse response = ServletActionContext.getResponse();

		int columnCount = 9;
		
		
		
		response.setContentType("application/vnd.ms-excel");
		String filename ="就业信息表.xls";
		
		response.setHeader("Content-Disposition","attachment; filename=\""+new String(filename.getBytes("gb18030"),"iso8859-1") + "\"");
		//首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象  
		WritableWorkbook wwb = Workbook.createWorkbook(response.getOutputStream());
		//创建一个可写入的工作表    
        //Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置    
        WritableSheet ws = wwb.createSheet("sheet1", 0); 
        //int lie = Integer.parseInt(request.getParameter("lieming"));
		// 获得导出数据的列数
		String[] title = { "真实姓名", "学号","专业", "就业分布","工作省份", "性别", "就业时间","就业状态","从事行业"};
		//写入字段名
		for (int i = 0; i < columnCount; i++) {
			jxl.write.Label lab = new jxl.write.Label(i,0,title[i]);
			ws.addCell(lab);
		}
		 for(int i=0;i<list.size();i++)  {
			 Jiuye  bean =  list.get(i); 

			 	jxl.write.Label lab1 = new jxl.write.Label(0,i+1,bean.getUser().getTruename()+"");
			 	
				jxl.write.Label lab2 = new jxl.write.Label(1,i+1,bean.getUser().getUsername()+"");
				jxl.write.Label lab3 = new jxl.write.Label(2,i+1,bean.getUser().getZhuanye()+"");
				jxl.write.Label lab4 = new jxl.write.Label(3,i+1,bean.getJiuyefenbu()+"");
				jxl.write.Label lab5 = new jxl.write.Label(4,i+1,bean.getJiuyeshengfen()+"");
				jxl.write.Label lab6 = new jxl.write.Label(5,i+1,bean.getXingbie()+"");
				jxl.write.Label lab7 = new jxl.write.Label(6,i+1,bean.getJiuyeshijian()+"");
				jxl.write.Label lab8 = new jxl.write.Label(7,i+1,bean.getBiyeqianhoujiuye()+"");
				jxl.write.Label lab9 = new jxl.write.Label(8,i+1,bean.getCongshihangye()+"");


				ws.addCell(lab1);
				ws.addCell(lab2);
				ws.addCell(lab3);
				ws.addCell(lab4);
				ws.addCell(lab5);
				ws.addCell(lab6);
				ws.addCell(lab7);
				ws.addCell(lab8);
				ws.addCell(lab9);

				
			
		   }
		//从内存中写入文件中    
        wwb.write();
        //关闭资源，释放内存    
        wwb.close(); 
       // response.getWriter();
	
	return null;
		
	}
	
	
	
	//统计列表(区域分布统计)
	public String jiuyelist4() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("xilist", xiDao.selectBeanList(0, 999, " where xilock=0  "));
		
		
		
		String banji = request.getParameter("banji");
		String xi = request.getParameter("xi");
		String zhuanye = request.getParameter("zhuanye");
		String nianji = request.getParameter("nianji");
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(banji!=null&&!"".equals(banji)){
			sb.append("user.banji like '%"+banji+"%'");
			sb.append(" and ");
			sb2.append("user.banji like '%"+banji+"%'");
			sb2.append(" and ");

			request.setAttribute("banji", banji);
		}
		
		if(xi!=null&&!"0".equals(xi)){
			Xi x = xiDao.selectBean(" where id= "+xi);
			sb.append("user.xi like '%"+x.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.xi like '%"+x.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("xi", x.getName());
		}
		
		if(zhuanye!=null&&!"0".equals(zhuanye)){
			Zhuanye z = zhuanyeDao.selectBean(" where id= "+zhuanye);
			sb.append("user.zhuanye like '%"+z.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.zhuanye like '%"+z.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("zhuanye", z.getName());
		}
		
		if(nianji!=null&&!"".equals(nianji)){
			sb.append("user.nianji like '%"+nianji+"%'");
			sb.append(" and ");
			sb2.append("user.nianji like '%"+nianji+"%'");
			sb2.append(" and ");

			request.setAttribute("nianji", nianji);
		}
		
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if("admin".equals(user.getUsername())){
			sb.append(" jiuyelock=0 and  jiuyefenbu='南方' ");
			sb2.append(" jiuyelock=0 and  jiuyefenbu='北方' ");
			
		}else{
			sb.append(" jiuyelock=0 and  jiuyefenbu='南方' and  laoshi.id="+user.getId());
			sb2.append(" jiuyelock=0 and  jiuyefenbu='北方' and laoshi.id="+user.getId());
		}

		
		int count1 =  jiuyeDao.selectBeanCount(sb.toString());
		int count2 =  jiuyeDao.selectBeanCount(sb2.toString());

		
		int count3 = count1+count2;
		
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue(((double)count1/count3)*100, "南方就业","南方就业"+count1+"人");
			dataset.addValue(((double)count2/count3)*100, "北方就业","北方就业"+count2+"人"); 

			JFreeChart chart = ChartFactory.createBarChart3D(null, "区域分布","百分比（%）", dataset, PlotOrientation.VERTICAL, true, false,false); 
			
			//柱状图(CategoryPlot):   
		CategoryPlot plot=chart.getCategoryPlot();
			//获取图表区域对象   
		CategoryAxis domainAxis=plot.getDomainAxis();    
			//水平底部列表    
		domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));    
			//水平底部标题    
		domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));
			//垂直标题    
		ValueAxis rangeAxis=plot.getRangeAxis();
			//获取柱状    
		rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));     
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
			
		String s = new Date().getTime()+"";
		request.setAttribute("time", s);
		
		String savaPath = ServletActionContext.getServletContext().getRealPath(
			"/")
			+ "/uploadfile/"+s+".png";
			
		ChartUtilities.saveChartAsPNG(new File(savaPath), chart, 600, 400); 
			
	
		
		this.setUrl("jiuye/jiuyelist4.jsp");
		return SUCCESS;

	}
	
	
	
	
	
	//统计列表(性别分布统计)
	public String jiuyelist5() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		
request.setAttribute("xilist", xiDao.selectBeanList(0, 999, " where xilock=0  "));
		
		
		
		String banji = request.getParameter("banji");
		String xi = request.getParameter("xi");
		String zhuanye = request.getParameter("zhuanye");
		String nianji = request.getParameter("nianji");
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(banji!=null&&!"".equals(banji)){
			sb.append("user.banji like '%"+banji+"%'");
			sb.append(" and ");
			sb2.append("user.banji like '%"+banji+"%'");
			sb2.append(" and ");

			request.setAttribute("banji", banji);
		}
		
		if(xi!=null&&!"0".equals(xi)){
			Xi x = xiDao.selectBean(" where id= "+xi);
			sb.append("user.xi like '%"+x.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.xi like '%"+x.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("xi", x.getName());
		}
		
		if(zhuanye!=null&&!"0".equals(zhuanye)){
			Zhuanye z = zhuanyeDao.selectBean(" where id= "+zhuanye);
			sb.append("user.zhuanye like '%"+z.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.zhuanye like '%"+z.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("zhuanye", z.getName());
		}
		
		if(nianji!=null&&!"".equals(nianji)){
			sb.append("user.nianji like '%"+nianji+"%'");
			sb.append(" and ");
			sb2.append("user.nianji like '%"+nianji+"%'");
			sb2.append(" and ");

			request.setAttribute("nianji", nianji);
		}
		
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if("admin".equals(user.getUsername())){
			sb.append(" jiuyelock=0 and  xingbie='男' and  congshihangye is not null ");
			sb2.append(" jiuyelock=0 and  xingbie='女' and  congshihangye is not null ");
			
		}else{
			sb.append(" jiuyelock=0 and  xingbie='男' and  congshihangye is not null and  laoshi.id="+user.getId());
			sb2.append(" jiuyelock=0 and   xingbie='女' and  congshihangye is not null and laoshi.id="+user.getId());
		}

		
		int count1 =  jiuyeDao.selectBeanCount(sb.toString());
		int count2 =  jiuyeDao.selectBeanCount(sb2.toString());
		
		
		
		
		
		int count3 = count1+count2;
		
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue(((double)count1/count3)*100, "男生就业","男生就业"+count1+"人");
			dataset.addValue(((double)count2/count3)*100, "女生就业","女生就业"+count2+"人"); 

			JFreeChart chart = ChartFactory.createBarChart3D(null, "性别分布","百分比（%）", dataset, PlotOrientation.VERTICAL, true, false,false); 
			
			//柱状图(CategoryPlot):   
		CategoryPlot plot=chart.getCategoryPlot();
			//获取图表区域对象   
		CategoryAxis domainAxis=plot.getDomainAxis();    
			//水平底部列表    
		domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));    
			//水平底部标题    
		domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));
			//垂直标题    
		ValueAxis rangeAxis=plot.getRangeAxis();
			//获取柱状    
		rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));     
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
			
		String s = new Date().getTime()+"";
		request.setAttribute("time", s);
		
		String savaPath = ServletActionContext.getServletContext().getRealPath(
			"/")
			+ "/uploadfile/"+s+".png";
			
		ChartUtilities.saveChartAsPNG(new File(savaPath), chart, 600, 400); 
			
	
		
		this.setUrl("jiuye/jiuyelist5.jsp");
		return SUCCESS;

	}
	
	
	//统计列表(时间分布统计)
	public String jiuyelist6() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();

request.setAttribute("xilist", xiDao.selectBeanList(0, 999, " where xilock=0  "));
		
		
		
		String banji = request.getParameter("banji");
		String xi = request.getParameter("xi");
		String zhuanye = request.getParameter("zhuanye");
		String nianji = request.getParameter("nianji");
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");

		if(banji!=null&&!"".equals(banji)){
			sb.append("user.banji like '%"+banji+"%'");
			sb.append(" and ");
			sb2.append("user.banji like '%"+banji+"%'");
			sb2.append(" and ");

			request.setAttribute("banji", banji);
		}
		
		if(xi!=null&&!"0".equals(xi)){
			Xi x = xiDao.selectBean(" where id= "+xi);
			sb.append("user.xi like '%"+x.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.xi like '%"+x.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("xi", x.getName());
		}
		
		if(zhuanye!=null&&!"0".equals(zhuanye)){
			Zhuanye z = zhuanyeDao.selectBean(" where id= "+zhuanye);
			sb.append("user.zhuanye like '%"+z.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.zhuanye like '%"+z.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("zhuanye", z.getName());
		}
		
		if(nianji!=null&&!"".equals(nianji)){
			sb.append("user.nianji like '%"+nianji+"%'");
			sb.append(" and ");
			sb2.append("user.nianji like '%"+nianji+"%'");
			sb2.append(" and ");

			request.setAttribute("nianji", nianji);
		}
		
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if("admin".equals(user.getUsername())){
			sb.append(" jiuyelock=0 and  biyeqianhoujiuye='毕业前就业' ");
			sb2.append(" jiuyelock=0 and  biyeqianhoujiuye='毕业后就业' ");
			
		}else{
			sb.append(" jiuyelock=0 and  biyeqianhoujiuye='毕业前就业' and  laoshi.id="+user.getId());
			sb2.append(" jiuyelock=0 and  biyeqianhoujiuye='毕业后就业' and laoshi.id="+user.getId());
		}

		
		int count1 =  jiuyeDao.selectBeanCount(sb.toString());
		int count2 =  jiuyeDao.selectBeanCount(sb2.toString());
		
		
	
		
		
		
		int count3 = count1+count2;
		
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue(((double)count1/count3)*100, "毕业前就业","毕业前就业"+count1+"人");
			dataset.addValue(((double)count2/count3)*100, "毕业后就业","毕业后就业"+count2+"人"); 

			JFreeChart chart = ChartFactory.createBarChart3D(null, "时间分布","百分比（%）", dataset, PlotOrientation.VERTICAL, true, false,false); 
			
			//柱状图(CategoryPlot):   
		CategoryPlot plot=chart.getCategoryPlot();
			//获取图表区域对象   
		CategoryAxis domainAxis=plot.getDomainAxis();    
			//水平底部列表    
		domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));    
			//水平底部标题    
		domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));
			//垂直标题    
		ValueAxis rangeAxis=plot.getRangeAxis();
			//获取柱状    
		rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));     
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
			
		String s = new Date().getTime()+"";
		request.setAttribute("time", s);
		
		String savaPath = ServletActionContext.getServletContext().getRealPath(
			"/")
			+ "/uploadfile/"+s+".png";
			
		ChartUtilities.saveChartAsPNG(new File(savaPath), chart, 600, 400); 
			
		
		
		this.setUrl("jiuye/jiuyelist6.jsp");
		return SUCCESS;

	}
	
	
	//统计列表(从事行业分布统计)
	public String jiuyelist7() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		
request.setAttribute("xilist", xiDao.selectBeanList(0, 999, " where xilock=0  "));
		
		
		
		String banji = request.getParameter("banji");
		String xi = request.getParameter("xi");
		String zhuanye = request.getParameter("zhuanye");
		String nianji = request.getParameter("nianji");
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();


		if(banji!=null&&!"".equals(banji)){
			sb.append("user.banji like '%"+banji+"%'");
			sb.append(" and ");
			sb2.append(" user.banji like '%"+banji+"%'");
			sb2.append(" and ");

			request.setAttribute("banji", banji);
		}
		
		if(xi!=null&&!"0".equals(xi)){
			Xi x = xiDao.selectBean(" where id= "+xi);
			sb.append("user.xi like '%"+x.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.xi like '%"+x.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("xi", x.getName());
		}
		
		if(zhuanye!=null&&!"0".equals(zhuanye)){
			Zhuanye z = zhuanyeDao.selectBean(" where id= "+zhuanye);
			sb.append("user.zhuanye like '%"+z.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.zhuanye like '%"+z.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("zhuanye", z.getName());
		}
		
		if(nianji!=null&&!"".equals(nianji)){
			sb.append("user.nianji like '%"+nianji+"%'");
			sb.append(" and ");
			sb2.append("user.nianji like '%"+nianji+"%'");
			sb2.append(" and ");

			request.setAttribute("nianji", nianji);
		}
		
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if("admin".equals(user.getUsername())){
			sb.append(" jiuyelock=0   ");
			sb2.append(" jiuyelock=0   ");
			
		}else{
			sb.append("  jiuyelock=0 and  laoshi.id="+user.getId());
			sb2.append(" jiuyelock=0 and  laoshi.id="+user.getId());
		}

		
	
		String where = sb.toString();
		String where2 = sb2.toString();
		
	
		
		List<Jiuye> list = jiuyeDao.selectBeanList(0, 9999, where );
		Set<String> set = new HashSet<String>();
		for(Jiuye j:list){
			set.add(j.getCongshihangye());
		}
		
		
	
		Map<String,Integer> map = new HashMap<String,Integer>();
		
		int count = 0;
		
		for(String s:set){
			int c = jiuyeDao.selectBeanCount(" where congshihangye='"+s+"' and "+ where2 );
			count+=c;
			map.put(s, c);
			
		}
		
		
		
		
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(String s:set){
			if(s!=null){
				dataset.addValue(((double)map.get(s)/count)*100, s,s+"："+map.get(s)+"人");
			}
			
			
		}

		JFreeChart chart = ChartFactory.createBarChart3D(null, "从事行业分布","百分比（%）", dataset, PlotOrientation.VERTICAL, true, false,false); 
			
			//柱状图(CategoryPlot):   
		CategoryPlot plot=chart.getCategoryPlot();
			//获取图表区域对象   
		CategoryAxis domainAxis=plot.getDomainAxis();    
			//水平底部列表    
		domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));    
			//水平底部标题    
		domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));
			//垂直标题    
		ValueAxis rangeAxis=plot.getRangeAxis();
			//获取柱状    
		rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));     
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
			
		String s = new Date().getTime()+"";
		request.setAttribute("time", s);
		
		String savaPath = ServletActionContext.getServletContext().getRealPath(
			"/")
			+ "/uploadfile/"+s+".png";
			
		ChartUtilities.saveChartAsPNG(new File(savaPath), chart, 600, 400); 
			

		
		this.setUrl("jiuye/jiuyelist7.jsp");
		return SUCCESS;

	}
	
	private ZhuanyeDao zhuanyeDao;
	
	private  XiDao xiDao;


	public ZhuanyeDao getZhuanyeDao() {
		return zhuanyeDao;
	}

	public void setZhuanyeDao(ZhuanyeDao zhuanyeDao) {
		this.zhuanyeDao = zhuanyeDao;
	}

	public XiDao getXiDao() {
		return xiDao;
	}

	public void setXiDao(XiDao xiDao) {
		this.xiDao = xiDao;
	}
	
	//系列表
	public String xilist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		if (name != null && !"".equals(name)) {

			sb.append("name like '%" + name + "%'");
			sb.append(" and ");
			
			sb2.append("name like '%" + name + "%'");
			sb2.append(" and ");
			request.setAttribute("name", name);
		}

		
		
		
		sb.append(" xilock=0   order by id desc ");
		String where = sb.toString();
		
		sb2.append(" xilock=0    ");
		String where2 = sb2.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = xiDao.selectBeanCount(where2);
		request.setAttribute("list", xiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!xilist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!xilist");
		request.setAttribute("url2", "method!xi");
	
		this.setUrl("xi/xilist.jsp");
		return SUCCESS;

	}
//跳转到添加系页面
	public String xiadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!xiadd2");
		request.setAttribute("title", "系添加");
		this.setUrl("xi/xiadd.jsp");
		return SUCCESS;
	}
//添加系操作
	public void xiadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");
		Xi bean = new Xi();
		bean.setName(name);

		xiDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!xilist';</script>");
		
		
		
	}
//跳转到更新系页面
	public String xiupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Xi bean = xiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!xiupdate2");
		request.setAttribute("title", "系修改");
		this.setUrl("xi/xiupdate.jsp");
		return SUCCESS;
	}
//更新系操作
	public void xiupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");

		Xi bean = xiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setName(name);
		xiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!xilist';</script>");
	}
//删除系操作
	public void xidelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Xi bean = xiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setXilock(1);
		xiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!xilist';</script>");
	}
	
	//跳转到查看系页面
	public String xiupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Xi bean = xiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!xiupdate2");
		request.setAttribute("title", "系查看");
		this.setUrl("xi/xiupdate3.jsp");
		return SUCCESS;
	}
	
	
	//专业列表
	public String zhuanyelist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		if (name != null && !"".equals(name)) {

			sb.append("name like '%" + name + "%'");
			sb.append(" and ");
			
			sb2.append("name like '%" + name + "%'");
			sb2.append(" and ");
			request.setAttribute("name", name);
		}

		
		
		
		sb.append(" zhuanyelock=0   order by id desc ");
		String where = sb.toString();
		
		sb2.append(" zhuanyelock=0   ");
		String where2 = sb2.toString();

		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = zhuanyeDao.selectBeanCount(where2);
		request.setAttribute("list", zhuanyeDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!zhuanyelist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!zhuanyelist");
		request.setAttribute("url2", "method!zhuanye");
	
		this.setUrl("zhuanye/zhuanyelist.jsp");
		return SUCCESS;

	}
//跳转到添加专业页面
	public String zhuanyeadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!zhuanyeadd2");
		request.setAttribute("title", "专业添加");
		this.setUrl("zhuanye/zhuanyeadd.jsp");
		request.setAttribute("list", xiDao.selectBeanList(0, 9999, " where xilock=0  "));
		return SUCCESS;
	}
//添加专业操作
	public void zhuanyeadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");
		String xi = request.getParameter("xi");
		Zhuanye bean = new Zhuanye();
		bean.setName(name);
		bean.setXi(xiDao.selectBean(" where id= "+xi));
		zhuanyeDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!zhuanyelist';</script>");
		
		
		
	}
//跳转到更新专业页面
	public String zhuanyeupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Zhuanye bean = zhuanyeDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!zhuanyeupdate2");
		request.setAttribute("title", "专业修改");
		this.setUrl("zhuanye/zhuanyeupdate.jsp");
		return SUCCESS;
	}
//更新专业操作
	public void zhuanyeupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");

		Zhuanye bean = zhuanyeDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setName(name);
		zhuanyeDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!zhuanyelist';</script>");
	}
//删除专业操作
	public void zhuanyedelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Zhuanye bean = zhuanyeDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setZhuanyelock(1);
		zhuanyeDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!zhuanyelist';</script>");
	}
	
	//跳转到查看专业页面
	public String zhuanyeupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Zhuanye bean = zhuanyeDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!zhuanyeupdate2");
		request.setAttribute("title", "专业查看");
		this.setUrl("zhuanye/zhuanyeupdate3.jsp");
		return SUCCESS;
	}
	
	
	//获得子类列表操作
	public String getcate() throws Exception{
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	response.setContentType("text/xml");
    	response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	    response.setCharacterEncoding("UTF-8");

    	String where = " where zhuanyelock=0 and xi.id= " +request.getParameter("pid");
    	List<Zhuanye> list = zhuanyeDao.selectBeanList(0, 9999, where);
    	String xml_start = "<selects>";
        String xml_end = "</selects>";
        String xml = "";
        for(int i=0;i<list.size();i++){
        	xml+="<select><value>"+list.get(i).getId()+"</value><text>"+list.get(i).getName()+"</text></select>";
        }
        String last_xml = xml_start + xml + xml_end;
        response.getWriter().write(last_xml);
        return null;
    }
	
	
	//获得子类列表操作
	public String getcate2() throws Exception{
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	response.setContentType("text/xml");
    	response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	    response.setCharacterEncoding("UTF-8");

    	String where = " where zhuanyelock=0 and xi.id= " +request.getParameter("pid");
    	List<Zhuanye> list = zhuanyeDao.selectBeanList(0, 9999, where);
    	String xml_start = "<selects>";
        String xml_end = "</selects>";
        String xml = "";
        xml+="<select><value>"+"0"+"</value><text>"+"所有专业"+"</text></select>";
        
        for(int i=0;i<list.size();i++){
        	xml+="<select><value>"+list.get(i).getId()+"</value><text>"+list.get(i).getName()+"</text></select>";
        }
        String last_xml = xml_start + xml + xml_end;
        response.getWriter().write(last_xml);
        return null;
    }
	
	//跳转到导入毕业生用户页面
	public String useradd7() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!useradd8");
		request.setAttribute("title", "毕业生用户添加");
		request.setAttribute("list", xiDao.selectBeanList(0, 9999, " where xilock=0  "));
		this.setUrl("user/useradd7.jsp");
		return SUCCESS;
	}
	
	
	
	//excel导入信息
	public void useradd8() throws IOException {
		int count = 0;
		try {
			Workbook wb = Workbook.getWorkbook(uploadfile);
			Sheet st = wb.getSheet("Sheet1");//得到工作薄中的第一个工作表 
			int row = st.getRows();
			for (int i = 1; i < row; i++) {
				Cell cell0 = st.getCell(0, i);//学号
				Cell cell1 = st.getCell(1, i);//真实姓名
				Cell cell2 = st.getCell(2, i);//性别
				Cell cell3= st.getCell(3, i);//系
				Cell cell4= st.getCell(4, i);//专业
				Cell cell5= st.getCell(5, i);//年级
				Cell cell6= st.getCell(6, i);//班级
				Cell cell7= st.getCell(7, i);//联系方式
				Cell cell8= st.getCell(8, i);//联系地址

				
				String username = cell0.getContents();
				String truename = cell1.getContents();
				String xingbie = cell2.getContents();
				String xi = cell3.getContents();
				String zhuanye = cell4.getContents();
				String nianji = cell5.getContents();
				String banji = cell6.getContents();
				String lianxifangshi = cell7.getContents();
				String lianxidizhi = cell8.getContents();


			
				if (null==username )
					continue;
				if (null==truename )
					continue;
				if (null==xingbie )
					continue;
				if (null==xi )
					continue;
				if (null==zhuanye )
					continue;
				if (null==nianji )
					continue;
				if (null==banji )
					continue;
				if (null==lianxifangshi )
					continue;
				if (null==lianxidizhi )
					continue;
				
				
				User bean = userDao.selectBean(" where username='"+username+"' and userlock=0   and role=2 ");
				if(bean!=null){
					continue;
				}
				
				bean = new User();
				bean.setUsername(username);
				bean.setTruename(truename);
				bean.setXingbie(xingbie);
				bean.setXi(xi);
				bean.setZhuanye(zhuanye);
				bean.setNianji(nianji);
				bean.setBanji(banji);
				bean.setLianxifangshi(lianxifangshi);
				bean.setLianxidizhi(lianxidizhi);
				bean.setCreatetime(new Date());
				bean.setPassword("111111");
				bean.setRole(2);		
				userDao.insertBean(bean);
				count++;
				
				Xi x = xiDao.selectBean(" where name='"+xi+"' and xilock=0 ");
				if(x==null){
					x = new Xi();
					x.setName(xi);
					xiDao.insertBean(x);
					Zhuanye z = new Zhuanye();
					z.setName(zhuanye);
					z.setXi(x);
					zhuanyeDao.insertBean(z);
				}else{
					Zhuanye z = zhuanyeDao.selectBean(" where  name='"+zhuanye+"' and zhuanyelock=0 ");
					if(z==null){
						z = new Zhuanye();
						z.setName(zhuanye);
						z.setXi(x);
						zhuanyeDao.insertBean(z);
					}
				}
				
				Jiuye jiuye = new Jiuye();
				jiuye.setCreatetime(new Date());
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				jiuye.setLaoshi(user);
				jiuye.setUser(bean);
				jiuye.setXingbie(xingbie);
				jiuyeDao.insertBean(jiuye);
				
				
			}
			wb.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功,成功导入"+count+"条');window.location.href='method!userlist2';</script>");
		
	}
	
	
	//统计列表(工作省份分布统计)
	public String jiuyelist8() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		
		request.setAttribute("xilist", xiDao.selectBeanList(0, 999, " where xilock=0  "));
		String banji = request.getParameter("banji");
		String xi = request.getParameter("xi");
		String zhuanye = request.getParameter("zhuanye");
		String nianji = request.getParameter("nianji");
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();


		if(banji!=null&&!"".equals(banji)){
			sb.append("user.banji like '%"+banji+"%'");
			sb.append(" and ");
			sb2.append(" user.banji like '%"+banji+"%'");
			sb2.append(" and ");

			request.setAttribute("banji", banji);
		}
		
		if(xi!=null&&!"0".equals(xi)){
			Xi x = xiDao.selectBean(" where id= "+xi);
			sb.append("user.xi like '%"+x.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.xi like '%"+x.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("xi", x.getName());
		}
		
		if(zhuanye!=null&&!"0".equals(zhuanye)){
			Zhuanye z = zhuanyeDao.selectBean(" where id= "+zhuanye);
			sb.append("user.zhuanye like '%"+z.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.zhuanye like '%"+z.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("zhuanye", z.getName());
		}
		
		if(nianji!=null&&!"".equals(nianji)){
			sb.append("user.nianji like '%"+nianji+"%'");
			sb.append(" and ");
			sb2.append("user.nianji like '%"+nianji+"%'");
			sb2.append(" and ");

			request.setAttribute("nianji", nianji);
		}
		
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if("admin".equals(user.getUsername())){
			sb.append("  1=1 ");
			sb2.append(" 1=1 ");
			
		}else{
			sb.append("   laoshi.id="+user.getId());
			sb2.append("  laoshi.id="+user.getId());
		}

		
	
		String where = sb.toString();
		String where2 = sb2.toString();
		
	
		
		List<Jiuye> list = jiuyeDao.selectBeanList(0, 9999, where );
		Set<String> set = new HashSet<String>();
		for(Jiuye j:list){
			set.add(j.getJiuyeshengfen());
		}
		
		
	
		Map<String,Integer> map = new HashMap<String,Integer>();
		
		int count = 0;
		
		for(String s:set){
			int c = jiuyeDao.selectBeanCount(" where jiuyeshengfen='"+s+"' and "+ where2 );
			count+=c;
			map.put(s, c);
			
		}
		
		
		
		
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(String s:set){
			if(s!=null){
				dataset.addValue(((double)map.get(s)/count)*100, s,s+"："+map.get(s)+"人");
			}
			
			
		}

		JFreeChart chart = ChartFactory.createBarChart3D(null, "工作省份分布","百分比（%）", dataset, PlotOrientation.VERTICAL, true, false,false); 
			
			//柱状图(CategoryPlot):   
		CategoryPlot plot=chart.getCategoryPlot();
			//获取图表区域对象   
		CategoryAxis domainAxis=plot.getDomainAxis();    
			//水平底部列表    
		domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));    
			//水平底部标题    
		domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));
			//垂直标题    
		ValueAxis rangeAxis=plot.getRangeAxis();
			//获取柱状    
		rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));     
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
			
		String s = new Date().getTime()+"";
		request.setAttribute("time", s);
		
		String savaPath = ServletActionContext.getServletContext().getRealPath(
			"/")
			+ "/uploadfile/"+s+".png";
			
		ChartUtilities.saveChartAsPNG(new File(savaPath), chart, 600, 400); 
			

		
		this.setUrl("jiuye/jiuyelist8.jsp");
		return SUCCESS;

	}
	
	
	//统计列表(工作省份分布统计)
	public String jiuyelist9() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		
		request.setAttribute("xilist", xiDao.selectBeanList(0, 999, " where xilock=0  "));
		String banji = request.getParameter("banji");
		String xi = request.getParameter("xi");
		String zhuanye = request.getParameter("zhuanye");
		String nianji = request.getParameter("nianji");
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();


		if(banji!=null&&!"".equals(banji)){
			sb.append("user.banji like '%"+banji+"%'");
			sb.append(" and ");
			sb2.append(" user.banji like '%"+banji+"%'");
			sb2.append(" and ");

			request.setAttribute("banji", banji);
		}
		
		if(xi!=null&&!"0".equals(xi)){
			Xi x = xiDao.selectBean(" where id= "+xi);
			sb.append("user.xi like '%"+x.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.xi like '%"+x.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("xi", x.getName());
		}
		
		if(zhuanye!=null&&!"0".equals(zhuanye)){
			Zhuanye z = zhuanyeDao.selectBean(" where id= "+zhuanye);
			sb.append("user.zhuanye like '%"+z.getName()+"%'");
			sb.append(" and ");
			sb2.append("user.zhuanye like '%"+z.getName()+"%'");
			sb2.append(" and ");

			request.setAttribute("zhuanye", z.getName());
		}
		
		if(nianji!=null&&!"".equals(nianji)){
			sb.append("user.nianji like '%"+nianji+"%'");
			sb.append(" and ");
			sb2.append("user.nianji like '%"+nianji+"%'");
			sb2.append(" and ");

			request.setAttribute("nianji", nianji);
		}
		
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if("admin".equals(user.getUsername())){
			sb.append("  1=1 ");
			sb2.append(" 1=1 ");
			
		}else{
			sb.append("   laoshi.id="+user.getId());
			sb2.append("  laoshi.id="+user.getId());
		}

		
	
		String where = sb.toString();
		String where2 = sb2.toString();
		
	
		
		List<Jiuye> list = jiuyeDao.selectBeanList(0, 9999, where );
		Set<String> set = new HashSet<String>();
		for(Jiuye j:list){
			set.add(j.getJiuyeyuefen());
		}
		
		
	
		Map<String,Integer> map = new HashMap<String,Integer>();
		
		int count = 0;
		
		for(String s:set){
			int c = jiuyeDao.selectBeanCount(" where jiuyeyuefen='"+s+"' and "+ where2 );
			count+=c;
			map.put(s, c);
			
		}
		
		
		
		
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(String s:set){
			if(s!=null){
				dataset.addValue(((double)map.get(s)/count)*100, s,s+"："+map.get(s)+"人");
			}
			
			
		}

		JFreeChart chart = ChartFactory.createBarChart3D(null, "就业月份分布","百分比（%）", dataset, PlotOrientation.VERTICAL, true, false,false); 
			
			//柱状图(CategoryPlot):   
		CategoryPlot plot=chart.getCategoryPlot();
			//获取图表区域对象   
		CategoryAxis domainAxis=plot.getDomainAxis();    
			//水平底部列表    
		domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));    
			//水平底部标题    
		domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));
			//垂直标题    
		ValueAxis rangeAxis=plot.getRangeAxis();
			//获取柱状    
		rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));     
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
			
		String s = new Date().getTime()+"";
		request.setAttribute("time", s);
		
		String savaPath = ServletActionContext.getServletContext().getRealPath(
			"/")
			+ "/uploadfile/"+s+".png";
			
		ChartUtilities.saveChartAsPNG(new File(savaPath), chart, 600, 400); 
			

		
		this.setUrl("jiuye/jiuyelist9.jsp");
		return SUCCESS;

	}
	
}
