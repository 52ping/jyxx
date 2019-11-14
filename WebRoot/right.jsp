<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="Images/css1/css.css" rel="stylesheet" type="text/css">
</head>
<SCRIPT language=javascript>
<!--
var displayBar=true;
function switchBar(obj){
	if (displayBar)
	{
		parent.frame.cols="0,*";
		displayBar=false;
		obj.value="打开左边管理菜单";
	}
	else{
		parent.frame.cols="195,*";
		displayBar=true;
		obj.value="关闭左边管理菜单";
	}
}

function fullmenu(url){
	if (url==null) {url = "admin_left.asp";}
	parent.leftFrame.location = url;
}

//-->
</SCRIPT>


 

 

<script>  
 function changeClock()  
 {  
     var d = new Date();  
     document.getElementById("clock").innerHTML = 
     
    // "时间："+
     d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();  
 }  
 window.setInterval(changeClock, 1000);  
</script>
<body>
	


<div align="right" id="clock" style="font-size: 25px;"></div>
<br/><br/><br/><br/><br/>
<div align="center" style="font-size: 30px;"background="img/3970232_065219057569_2.gif">
欢迎${user.truename }使用本系统，
您的用户权限是
<c:if test="${user.role==4}">系统管理员</c:if>
<c:if test="${user.role==3}">教师管理员</c:if>
<c:if test="${user.role==2}">毕业生用户</c:if>
<c:if test="${user.role==1}">企业用户</c:if>
<br/>
请选择左边的菜单栏使用本系统

</body>
</html>

