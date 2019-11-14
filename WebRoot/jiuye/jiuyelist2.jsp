<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="Images/css1/css.css" rel="stylesheet" type="text/css">
</head>



 

 

<body  onload="javascript:window.print()">






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

</body>
</html>

