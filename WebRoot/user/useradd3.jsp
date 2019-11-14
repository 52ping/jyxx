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


<script type="text/javascript">
    var req;
   
    function Change_Select(selectedId){//当第一个下拉框的选项发生改变时调用该函数
      var now = new Date();
      var url = "method!getcate?pid="+selectedId+"&t="+now.getTime();
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


    <script>

	
	function check() {

	var valid=/^\d+$/;
	if(!valid.test(document.getElementById("xuehaoid").value)){
		alert("学号必须为数字");
		return false;
	}
	
	if(!valid.test(document.getElementById("lianxifangshiid").value)){
		alert("联系方式必须为数字");
		return false;
	}

			return true;
	}
</script>

 

<body>
<input onClick="switchBar(this)" type="button" value="关闭左边管理菜单" name="SubmitBtn" />

<table class="table" cellspacing="1" cellpadding="2" width="99%" align="center" border="0">
  <tbody>
    
    <form method="post"   action="${url }"  onsubmit="return check()">
    
    <tr>
      <td class="td_bg"  height="23">学号</td>
      <td class="td_bg" ><input type="text" name="username" id="xuehaoid" /></td>
     
    </tr>
    
   <tr>
      <td class="td_bg"  height="23">真实姓名</td>
      <td class="td_bg" ><input type="text" name="truename" /></td>
     
    </tr>
    
    <tr>
      <td class="td_bg"  height="23">性别</td>
      <td class="td_bg" >
      <select name="xingbie">
      <option value="男">男</option>
       <option value="女">女</option>
      </select>
      </td>
     
    </tr>
    
   
    
    
     <tr>
      <td class="td_bg"  height="23">系</td>
      <td class="td_bg" >
      <select name="xi" id="pid" onchange="Change_Select(this.value)">
						<option value="0">请选择系</option>
							<c:forEach items="${list}" var="bean">
                                <option value="${bean.id }" >
                                   	${bean.name }
                                </option>
                                </c:forEach>
     </select>     	
      
      </td>
     
    </tr>
    
     <tr>
      <td class="td_bg"  height="23">专业</td>
      <td class="td_bg" >
      <select name="zhuanye" id="cid">
                           		
                                <option value="">
                                   	请选择专业
                                </option>

                            	</select>		
      </td>
     
    </tr>
    
    <tr>
      <td class="td_bg"  height="23">年级</td>
      <td class="td_bg" >
      <select name="nianji">
      <option value="2009级">2009级</option>
       <option value="2010级">2010级</option>
        <option value="2011级">2011级</option>
         <option value="2012级">2012级</option>
          <option value="2013级">2013级</option>
      </select>
      
      </td>
     
    </tr>
    
    <tr>
      <td class="td_bg"  height="23">班级</td>
      <td class="td_bg" ><input type="text" name="banji" /></td>
     
    </tr>
    
   
    
    <tr>
      <td class="td_bg"  height="23">联系方式</td>
      <td class="td_bg" ><input type="text" name="lianxifangshi" id="lianxifangshiid" /></td>
     
    </tr>
    
    <tr>
      <td class="td_bg"  height="23">联系地址</td>
      <td class="td_bg" ><input type="text" name="lianxidizhi" /></td>
     
    </tr>
    
    <tr>
      <td class="td_bg"  height="23">操作</td>
      <td class="td_bg" >
      <input type="submit" value="提交"/>
      &nbsp; &nbsp; &nbsp; &nbsp;
      <input type="reset" value="重置"/>
       &nbsp; &nbsp; &nbsp; &nbsp;
      <input  onclick="javascript:history.go(-1);"  type="button" value="返回" />
      </td>
     
    </tr>
    </from>
</tbody>
</table>

</body>
</html>

