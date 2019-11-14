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

    <script>

	
	function check() {

	var valid=/^\d+$/;
	if(!valid.test(document.getElementById("zhaopinrenshuid").value)){
		alert("招聘人数必须为数字");
		return false;
	}
	
	if(!valid.test(document.getElementById("yuexinid").value)){
		alert("月薪必须为数字");
		return false;
	}

			return true;
	}
</script>
 
<script type="text/javascript" src="js/datesplits.js"></script>
 

<body>
<input onClick="switchBar(this)" type="button" value="关闭左边管理菜单" name="SubmitBtn" />

<table class="table" cellspacing="1" cellpadding="2" width="99%" align="center" border="0">
  <tbody>
    
    <form method="post"   action="${url }"  onsubmit="return check()">
    
     <tr>
      <td class="td_bg"  height="23">职位类别</td>
      <td class="td_bg" ><input type="text" name="zhiweileibie"  /></td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">职位名称</td>
      <td class="td_bg" ><input type="text" name="zhiweimingchen" /></td>
     
    </tr>
    
    <tr>
      <td class="td_bg"  height="23">招聘人数</td>
      <td class="td_bg" ><input type="text" name="zhaopinrenshu" id="zhaopinrenshuid" /></td>
     
    </tr>
     <tr>
      <td class="td_bg"  height="23">工作省份</td>
      <td class="td_bg" >
      <select name="gongzuoshengfen">
     											  <option value="北京市">北京市</option>
                                                  <option value="浙江省">浙江省</option>
                                                  <option value="天津市">天津市</option>
                                                  <option value="安徽省">安徽省</option>
                                                  <option value="上海市">上海市</option>
                                                  <option value="福建省">福建省</option>
                                                  <option value="重庆市">重庆市</option>
                                                  <option value="江西省">江西省</option>
                                                  <option value="山东省">山东省</option>
                                                  <option value="河南省">河南省</option>
                                                  <option value="湖北省">湖北省</option>
                                                  <option value="湖南省">湖南省</option>
                                                  <option value="广东省">广东省</option>
                                                  <option value="海南省">海南省</option>
                                                  <option value="山西省">山西省</option>
                                                  <option value="青海省">青海省</option>
                                                  <option value="江苏省">江苏省</option>
                                                  <option value="辽宁省">辽宁省</option>
                                                  <option value="吉林省">吉林省</option>
                                                  <option value="台湾省">台湾省</option>
                                                  <option value="河北省">河北省</option>
                                                  <option value="贵州省">贵州省</option>
                                                  <option value="四川省">四川省</option>
                                                  <option value="云南省">云南省</option>
                                                  <option value="陕西省">陕西省</option>
                                                  <option value="甘肃省">甘肃省</option>
                                                  <option value="黑龙江省">黑龙江省</option>
                                                  <option value="香港特别行政区">香港特别行政区</option>
                                                  <option value="澳门特别行政区">澳门特别行政区</option>
                                                  <option value="广西壮族自治区">广西壮族自治区</option>
                                                  <option value="宁夏回族自治区">宁夏回族自治区</option>
                                                  <option value="新疆维吾尔自治区">新疆维吾尔自治区</option>
                                                  <option value="内蒙古自治区">内蒙古自治区</option>
                                                  <option value="西藏自治区">西藏自治区</option>
</select> 
      
      </td>
     
    </tr>
    
    <tr>
      <td class="td_bg"  height="23">工作地点</td>
      <td class="td_bg" ><input type="text" name="gongzuodidian" /></td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">月薪(元)</td>
      <td class="td_bg" ><input type="text" name="yuexin" id="yuexinid" /></td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">学历</td>
      <td class="td_bg" >
      <select name="xueli">
      <option value="大专">大专</option>
      <option value="本科">本科</option>
      <option value="硕士">硕士</option>
      </select>
      </td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">工作年限</td>
      <td class="td_bg" >
       <select name="gongzuonianxian">
      <option value="不限">不限</option>
      <option value="一年以上">一年以上</option>
      <option value="二年以上">二年以上</option>
      <option value="三年以上">三年以上</option>
      <option value="五年以上">五年以上</option>

      </select>
      </td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">工作类型</td>
      <td class="td_bg" >
      <select name="gongzuoleixing">
     
      <option value="全职">全职</option>
      <option value="兼职">兼职</option>
      <option value="不限">不限</option>
      
      </select></td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">性别</td>
      <td class="td_bg" >
      <select name="xingbie">
      <option value="不限">不限</option>
      <option value="男">男</option>
      <option value="女">女</option>
      </select></td>
     
    </tr>
    
     <tr>
      <td class="td_bg"  height="23">职位描述</td>
      <td class="td_bg" >
      <textarea rows="7" cols="50" name="zhiweimiaoshu"></textarea>
      </td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">有效期</td>
      <td class="td_bg" >
      <input name="youxianqi" type="text"   id="startTime"   onclick="setday(this);" />
      </td>
     
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

