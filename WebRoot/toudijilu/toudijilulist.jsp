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
<form action="method!toudijilulist" method="post">
<div align="left">
<img src="Images/ss.gif"/>


职位名称：<input type="text" name="zhiweimingchen" value="${zhiweimingchen}">

阅读状态：<select name="readzhuangtai">
		<option value="">所有状态</option>
		<option value="未阅读" <c:if test="${readzhuangtai=='未阅读' }">selected</c:if>>未阅读</option>
		<option value="已阅读" <c:if test="${readzhuangtai=='已阅读' }">selected</c:if>>已阅读</option>
		
    
      
      </select>

<input type="submit" value="搜索">
</div>
</form>



<table class="table" cellspacing="1" cellpadding="2" width="99%" align="center" border="0">
  <tbody>
    
    
    <tr>
      <td class="td_bg"  height="23">职位名称</td>
      <td class="td_bg"  height="23">求职者姓名</td>
       <td class="td_bg"  height="23">简历名称</td>
        <td class="td_bg"  height="23">投递时间</td>
        <td class="td_bg"  height="23">下载阅读</td>
          <td class="td_bg"  height="23">应聘状态</td>

   		<td class="td_bg" >操作</td>
    </tr>
    <c:forEach items="${list}" var="bean">
    <tr>
      <td class="td_bg"  height="23">${bean.zhiwei.zhiweimingchen }</td>
      <td class="td_bg"  height="23">${bean.jianli.qiuzhiren.truename }</td>
      <td class="td_bg"  height="23">${bean.jianli.jianlimingchen }</td>
      <td class="td_bg"  height="23">${fn:substring(bean.createtime,0, 19)}</td>
       <td class="td_bg"  height="23">${bean.readzhuangtai}</td>
        <td class="td_bg"  height="23">${bean.yingpinzhuangtai}</td>

      <td class="td_bg" >
      <a href="method!zhiweiupdate3?id=${bean.zhiwei.id }"><span style="font-size: 15px;">查看职位</span></a>
     <a href="method!jianlixiazai?id=${bean.id }"  target="_blank"><span style="font-size: 15px;">下载简历</span></a>
     
     <c:if test="${bean.jianli.qiuzhiren.zhiwei==null}">
     
    
     <a href="method!luru?id=${bean.id }"><span style="font-size: 15px;">招聘录入</span></a>
      </c:if>
     
     <a href="method!toudijiludelete?id=${bean.id }"><span style="font-size: 15px;">删除记录</span></a>
      </td>
    </tr>
    </c:forEach>
    
    
    
  
    
  </tbody>
</table>
${pagerinfo }
</body>
</html>

