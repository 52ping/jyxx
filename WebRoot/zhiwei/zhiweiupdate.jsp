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
      <td class="td_bg" ><input type="text" name="zhiweileibie"  value="${bean.zhiweileibie }" /></td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">职位名称</td>
      <td class="td_bg" ><input type="text" name="zhiweimingchen"  value="${bean.zhiweimingchen }" /></td>
     
    </tr>
    
    <tr>
      <td class="td_bg"  height="23">招聘人数</td>
      <td class="td_bg" ><input type="text" name="zhaopinrenshu" id="zhaopinrenshuid"  value="${bean.zhaopinrenshu }"/></td>
     
    </tr>
    
     <tr>
      <td class="td_bg"  height="23">工作省份</td>
      <td class="td_bg" >
      <select name="gongzuoshengfen">
     											  <option value="北京市"  <c:if test="${bean.gongzuoshengfen=='北京市' }">selected</c:if> >北京市</option>
                                                  <option value="浙江省" <c:if test="${bean.gongzuoshengfen=='浙江省' }">selected</c:if> >浙江省</option>
                                                  <option value="天津市" <c:if test="${bean.gongzuoshengfen=='天津市' }">selected</c:if> >天津市</option>
                                                  <option value="安徽省" <c:if test="${bean.gongzuoshengfen=='安徽省' }">selected</c:if> >安徽省</option>
                                                  <option value="上海市" <c:if test="${bean.gongzuoshengfen=='上海市' }">selected</c:if> >上海市</option>
                                                  <option value="福建省" <c:if test="${bean.gongzuoshengfen=='福建省' }">selected</c:if> >福建省</option>
                                                  <option value="重庆市" <c:if test="${bean.gongzuoshengfen=='重庆市' }">selected</c:if> >重庆市</option>
                                                  <option value="江西省" <c:if test="${bean.gongzuoshengfen=='江西省' }">selected</c:if> >江西省</option>
                                                  <option value="山东省" <c:if test="${bean.gongzuoshengfen=='山东省' }">selected</c:if> >山东省</option>
                                                  <option value="河南省" <c:if test="${bean.gongzuoshengfen=='河南省' }">selected</c:if> >河南省</option>
                                                  <option value="湖北省" <c:if test="${bean.gongzuoshengfen=='湖北省' }">selected</c:if> >湖北省</option>
                                                  <option value="湖南省"<c:if test="${bean.gongzuoshengfen=='湖南省' }">selected</c:if> >湖南省</option>
                                                  <option value="广东省" <c:if test="${bean.gongzuoshengfen=='广东省' }">selected</c:if> >广东省</option>
                                                  <option value="海南省" <c:if test="${bean.gongzuoshengfen=='海南省' }">selected</c:if> >海南省</option>
                                                  <option value="山西省" <c:if test="${bean.gongzuoshengfen=='山西省' }">selected</c:if> >山西省</option>
                                                  <option value="青海省"<c:if test="${bean.gongzuoshengfen=='青海省' }">selected</c:if> >青海省</option>
                                                  <option value="江苏省" <c:if test="${bean.gongzuoshengfen=='江苏省' }">selected</c:if> >江苏省</option>
                                                  <option value="辽宁省" <c:if test="${bean.gongzuoshengfen=='辽宁省' }">selected</c:if> >辽宁省</option>
                                                  <option value="吉林省" <c:if test="${bean.gongzuoshengfen=='吉林省' }">selected</c:if> >吉林省</option>
                                                  <option value="台湾省" <c:if test="${bean.gongzuoshengfen=='台湾省' }">selected</c:if> >台湾省</option>
                                                  <option value="河北省" <c:if test="${bean.gongzuoshengfen=='河北省' }">selected</c:if> >河北省</option>
                                                  <option value="贵州省" <c:if test="${bean.gongzuoshengfen=='贵州省' }">selected</c:if> >贵州省</option>
                                                  <option value="四川省" <c:if test="${bean.gongzuoshengfen=='四川省' }">selected</c:if> >四川省</option>
                                                  <option value="云南省" <c:if test="${bean.gongzuoshengfen=='云南省' }">selected</c:if> >云南省</option>
                                                  <option value="陕西省" <c:if test="${bean.gongzuoshengfen=='陕西省' }">selected</c:if> >陕西省</option>
                                                  <option value="甘肃省" <c:if test="${bean.gongzuoshengfen=='甘肃省' }">selected</c:if> >甘肃省</option>
                                                  <option value="黑龙江省" <c:if test="${bean.gongzuoshengfen=='黑龙江省' }">selected</c:if> >黑龙江省</option>
                                                  <option value="香港特别行政区" <c:if test="${bean.gongzuoshengfen=='香港特别行政区' }">selected</c:if> >香港特别行政区</option>
                                                  <option value="澳门特别行政区" <c:if test="${bean.gongzuoshengfen=='澳门特别行政区' }">selected</c:if> >澳门特别行政区</option>
                                                  <option value="广西壮族自治区" <c:if test="${bean.gongzuoshengfen=='广西壮族自治区' }">selected</c:if> >广西壮族自治区</option>
                                                  <option value="宁夏回族自治区" <c:if test="${bean.gongzuoshengfen=='宁夏回族自治区' }">selected</c:if> >宁夏回族自治区</option>
                                                  <option value="新疆维吾尔自治区" <c:if test="${bean.gongzuoshengfen=='新疆维吾尔自治区' }">selected</c:if> >新疆维吾尔自治区</option>
                                                  <option value="内蒙古自治区" <c:if test="${bean.gongzuoshengfen=='内蒙古自治区' }">selected</c:if> >内蒙古自治区</option>
                                                  <option value="西藏自治区" <c:if test="${bean.gongzuoshengfen=='西藏自治区' }">selected</c:if> >西藏自治区</option>
</select> 
      
      </td>
     
    </tr>
    
    
    <tr>
      <td class="td_bg"  height="23">工作地点</td>
      <td class="td_bg" ><input type="text" name="gongzuodidian" value="${bean.gongzuodidian }"/></td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">月薪(元)</td>
      <td class="td_bg" ><input type="text" name="yuexin" id="yuexinid" value="${bean.yuexin }" /></td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">学历</td>
      <td class="td_bg" >
      <select name="xueli">
      <option value="大专" <c:if test="${bean.xueli=='大专' }">selected</c:if>>大专</option>
      <option value="本科" <c:if test="${bean.xueli=='本科' }">selected</c:if>>本科</option>
      <option value="硕士" <c:if test="${bean.xueli=='硕士' }">selected</c:if>>硕士</option>
      </select>
      </td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">工作年限</td>
      <td class="td_bg" >
       <select name="gongzuonianxian">
      <option value="不限">不限</option>
      <option value="一年以上" <c:if test="${bean.gongzuonianxian=='一年以上' }">selected</c:if>>一年以上</option>
      <option value="二年以上" <c:if test="${bean.gongzuonianxian=='二年以上' }">selected</c:if>>二年以上</option>
      <option value="三年以上" <c:if test="${bean.gongzuonianxian=='三年以上' }">selected</c:if>>三年以上</option>
      <option value="五年以上" <c:if test="${bean.gongzuonianxian=='五年以上' }">selected</c:if>>五年以上</option>

      </select>
      </td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">工作类型</td>
      <td class="td_bg" >
      <select name="gongzuoleixing">
     
      <option value="全职" <c:if test="${bean.gongzuoleixing=='全职' }">selected</c:if>>全职</option>
      <option value="兼职" <c:if test="${bean.gongzuoleixing=='兼职' }">selected</c:if>>兼职</option>
      <option value="不限" <c:if test="${bean.gongzuoleixing=='不限' }">selected</c:if>>不限</option>
      
      </select></td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">性别</td>
      <td class="td_bg" >
      <select name="xingbie">
      <option value="不限">不限</option>
      <option value="男" <c:if test="${bean.xingbie=='男' }">selected</c:if> >男</option>
      <option value="女" <c:if test="${bean.xingbie=='女' }">selected</c:if> >女</option>
      </select></td>
     
    </tr>
    
     <tr>
      <td class="td_bg"  height="23">职位描述</td>
      <td class="td_bg" >
      <textarea rows="7" cols="50" name="zhiweimiaoshu">${bean.zhiweimiaoshu }</textarea>
      </td>
     
    </tr>
    <tr>
      <td class="td_bg"  height="23">有效期</td>
      <td class="td_bg" >
      <input name="youxianqi" type="text"   id="startTime"   onclick="setday(this);" value="${bean.youxianqi }" />
      </td>
     
    </tr>
    
     <input name="id" type="hidden"    value="${bean.id }" />
    
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

