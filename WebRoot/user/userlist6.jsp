<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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


 

 

<body>
<input onClick="switchBar(this)" type="button" value="关闭左边管理菜单" name="SubmitBtn" />
<br/>



<table class="table" cellspacing="1" cellpadding="2" width="99%" align="center" border="0">
  <tbody>
    
    
    <tr>
      <td class="td_bg"  height="23">学号</td>
      <td class="td_bg" >真实姓名</td>
      <td class="td_bg" >密码</td>
 <td class="td_bg" >系</td>
        <td class="td_bg" >专业</td>
         <td class="td_bg" >班级</td>
          <td class="td_bg" >年级</td>
           <td class="td_bg" >联系方式</td>
            <td class="td_bg" >联系地址</td>
      <td class="td_bg" >添加时间</td>

      <td class="td_bg" >操作</td>
    </tr>
    <c:forEach items="${list}" var="bean">
    <tr>
      <td class="td_bg"  height="23">${bean.username }</td>
       <td class="td_bg"  height="23">${bean.truename }</td>
      <td class="td_bg" >${bean.password }</td>

        <td class="td_bg" >${bean.xi }</td>
        <td class="td_bg" >${bean.zhuanye }</td>
         <td class="td_bg" >${bean.banji }</td>
          <td class="td_bg" >${bean.nianji }</td>
           <td class="td_bg" >${bean.lianxifangshi }</td>
            <td class="td_bg" >${bean.lianxidizhi }</td>
       <td class="td_bg"  height="23">${fn:substring(bean.createtime,0, 19)}</td>

      <td class="td_bg" >

      <a href="${url2 }update7?id=${bean.id }"><span style="font-size: 15px;">修改个人信息</span></a>

      </td>
    </tr>
    </c:forEach>
    
    
    
  
    
  </tbody>
</table>

</body>
</html>

