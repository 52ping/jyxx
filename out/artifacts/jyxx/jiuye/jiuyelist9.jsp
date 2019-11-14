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

<script type="text/javascript">
    var req;
   
    function Change_Select(selectedId){//当第一个下拉框的选项发生改变时调用该函数
      var now = new Date();
      var url = "method!getcate2?pid="+selectedId+"&t="+now.getTime();
      if(window.XMLHttpRequest){
        req = new XMLHttpRequest();
      }else if(window.ActiveXObject){
        req = new ActiveXObject("Microsoft.XMLHTTP");
      }
      if(req){
        //指定回调函数为callback
      	req.onreadystatechange = callback;
        req.open("GET",url,true);
        req.send(null);
      }
    }
    //回调函数
    function callback(){
      if(req.readyState ==4){
        if(req.status ==200){
          parseMessage();//解析XML文档
        }else{
          alert("不能得到描述信息:" + req.statusText);
        }
      }
    }
    //解析返回xml的方法
    function parseMessage(){
      var xSel = req.responseXML.getElementsByTagName('select');//获得返回的XML文档
      //获得XML文档中的所有<select>标记
      var select_root = document.getElementById('cid');
      //获得网页中的第二个下拉框
      select_root.options.length=0;
      //每次获得新的数据的时候先把每二个下拉框架的长度清0
     
      for(var i=0;i<xSel.length;i++){
        var xValue = xSel[i].childNodes[0].firstChild.nodeValue;
        //获得每个<select>标记中的第一个标记的值,也就是<value>标记的值
        var xText = xSel[i].childNodes[1].firstChild.nodeValue;
        //获得每个<select>标记中的第二个标记的值,也就是<text>标记的值
        var option = new Option(xText, xValue);
        //根据每组value和text标记的值创建一个option对象
       
        try{
          select_root.add(option);//将option对象添加到第二个下拉框中
        }catch(e){
        }
      }
    }       






</SCRIPT>

 

 

<body  >



<form action="method!jiuyelist7?t=${time }" method="post">
<div align="left">
查询统计
<img src="Images/ss.gif"/>
系：
<select name="xi" id="pid" onchange="Change_Select(this.value)">
						<option value="0">所有系</option>
							<c:forEach items="${xilist}" var="bean22">
                                <option value="${bean22.id }" >
                                   	${bean22.name }
                                </option>
                                </c:forEach>
     </select> 
专业:
<select name="zhuanye" id="cid">
                           		
                                <option value="0">
                                   所有专业
                                </option>

</select>  

年级

                           		
     <select name="nianji">
     <option value="">所有年级</option>
      <option value="2009级">2009级</option>
       <option value="2010级">2010级</option>
        <option value="2011级">2011级</option>
         <option value="2012级">2012级</option>
          <option value="2013级">2013级</option>
      </select>



班级：<input type="text" name="banji" >

<input type="submit" value="统计">
</div>
</form>

<div style="font-size: 30px;font-weight: bold;">${xi}${zhuanye}${nianji}${banji}就业月份分布统计</div>
<div>

<img src="<%=basePath %>/uploadfile/${time }.png?t=${time }"/>





</body>
</html>

