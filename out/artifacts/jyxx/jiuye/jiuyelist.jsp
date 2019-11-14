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


function aaa(){
	
	document.form1.submit();
	
}

function bbb(){
	
	document.form2.submit();
	
}

//-->
</SCRIPT>


 

 

<body>
<input onClick="switchBar(this)" type="button" value="关闭左边管理菜单" name="SubmitBtn" />
<form action="method!jiuyelist2" method="post" name="form1">

<input type="hidden" name="truename" value="${truename}">
<input type="hidden" name="zhuanye" value="${zhuanye}">
<input type="hidden" name="jiuyefenbu" value="${jiuyefenbu}">
<input type="hidden" name="xingbie" value="${xingbie}">
<input type="hidden" name="congshihangye" value="${congshihangye}">

</form>

<form action="method!jiuyelist3" method="post" name="form2">

<input type="hidden" name="truename" value="${truename}">
<input type="hidden" name="zhuanye" value="${zhuanye}">
<input type="hidden" name="jiuyefenbu" value="${jiuyefenbu}">
<input type="hidden" name="xingbie" value="${xingbie}">
<input type="hidden" name="congshihangye" value="${congshihangye}">

</form>

<form action="${url }" method="post">
<div align="left">
<img src="Images/ss.gif"/>
学生姓名：<input type="text" name="truename" value="${truename}">
专业：<input type="text" name="zhuanye" value="${zhuanye}">
就业分布：<input type="text" name="jiuyefenbu" value="${jiuyefenbu}">
性别：<input type="text" name="xingbie" value="${xingbie}">
从事行业：<input type="text" name="congshihangye" value="${congshihangye}">


<input type="submit" value="搜索">
</div>
</form>




<a href="javascript:void(0)" onclick="aaa()"  ><span style="font-size: 25px;">打印输出</span></a>
<a href="javascript:void(0)" onclick="bbb()"  ><span style="font-size: 25px;">导出excel</span></a>
<table class="table" cellspacing="1" cellpadding="2" width="99%" align="center" border="0">
  <tbody>
    
    
    <tr>
      <td class="td_bg" >真实姓名</td>
       <td class="td_bg" >学号</td>
        <td class="td_bg" >专业</td>
 <td class="td_bg" >就业分布</td>
  <td class="td_bg" >工作省份</td>
  <td class="td_bg" >性别</td>
   <td class="td_bg" >就业时间</td>
    <td class="td_bg" >就业状态</td>
     <td class="td_bg" >从事行业</td>



  
    </tr>
    <c:forEach items="${list}" var="bean">
    <tr>
      <td class="td_bg"  height="23">${bean.user.truename }</td>
    
       <td class="td_bg" >${bean.user.username }</td>
        <td class="td_bg" >${bean.user.zhuanye }</td>
        <td class="td_bg" >${bean.jiuyefenbu }</td>
         <td class="td_bg" >${bean.jiuyeshengfen }</td>
        <td class="td_bg" >${bean.xingbie }</td>
        <td class="td_bg" >${bean.jiuyeshijian }</td>
        <td class="td_bg" >${bean.biyeqianhoujiuye }</td>
        <td class="td_bg" >${bean.congshihangye }</td>

       

      
    </tr>
    </c:forEach>
    
    
    
  
    
  </tbody>
</table>
${pagerinfo }
</body>
</html>

