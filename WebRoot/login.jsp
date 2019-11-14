<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>大学毕业生就业信息管理系统</title>
<style type="text/css">
<!--
a{ color:#008EE3}
a:link  { text-decoration: none;color:#008EE3}
A:visited {text-decoration: none;color:#666666}
A:active {text-decoration: underline}
A:hover {text-decoration: underline;color: #0066CC}
A.b:link {
	text-decoration: none;
	font-size:50px;
	font-family: "Helvetica,微软雅黑,宋体";
	color: #FFFFFF;
}
A.b:visited {
	text-decoration: none;
	font-size:50px;
	font-family: "Helvetica,微软雅黑,宋体";
	color: #FFFFFF;
}
A.b:active {
	text-decoration: underline;
	color: #FF0000;

}
A.b:hover {text-decoration: underline; color: #ffffff}

.table1 {
	border: 1px solid #CCCCCC;
}
.font {
	font-size: 12px;
	text-decoration: none;
	color: #999999;
	line-height: 20px;
	

}
.input {
	font-size: 12px;
	color: #999999;
	text-decoration: none;
	border: 0px none #999999;


}

td {
	font-size: 12px;
	color: #007AB5;
}
form {
	margin: 1px;
	padding: 1px;
}
input {
	border: 0px;
	height: 26px;
	color: #007AB5;

	.unnamed1 {
	border: thin none #FFFFFF;
}
.unnamed1 {
	border: thin none #FFFFFF;
}
select {
	border: 1px solid #cccccc;
	height: 18px;
	color: #666666;

	.unnamed1 {
	border: thin none #FFFFFF;
}
body {
	background-repeat: no-repeat;
	background-color: #9CDCF9;
	background-position: 0px 0px;

}
.tablelinenotop {
	border-top: 0px solid #CCCCCC;
	border-right: 1px solid #CCCCCC;
	border-bottom: 0px solid #CCCCCC;
	border-left: 1px solid #CCCCCC;
}
.tablelinenotopdown {

	border-top: 1px solid #eeeeee;
	border-right: 1px solid #eeeeee;
	border-bottom: 1px solid #eeeeee;
	border-left: 1px solid #eeeeee;
}
.style6 {FONT-SIZE: 9pt; color: #7b8ac3; }

-->
</style>
<script language="javascript" type="text/javascript"> 

		
function registershow(){
		var now = new Date(); 
		var t = now.getTime()+''; 
		window.showModalDialog("register.jsp?t="+t, null, 
		'dialogWidth:700px;dialogHeight:500px;help:no;unadorned:no;resizable:no;status:no;scroll:no');
	}
</script>


<script type="text/javascript">


		
		
var code ; //在全局 定义验证码
function createCode(){ 
code = "";
var codeLength = 4;//验证码的长度
var checkCode = document.getElementById("checkCode");
checkCode.value = "";

var selectChar = new Array(2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');

for(var i=0;i<codeLength;i++) {
   var charIndex = Math.floor(Math.random()*32);
   code +=selectChar[charIndex];
}
if(code.length != codeLength){
   createCode();
}
checkCode.value = code;
}
		
		

		function check(){
		
		var inputCode = document.getElementById("input1").value.toUpperCase();

if(inputCode.length <=0) {
   alert("请输入验证码！");
   return false;
}
else if(inputCode != code ){
   alert("验证码输入错误！");
   createCode();
   return false;
		
		
		
}
	 
	

	return true;
	
}




	</script>

</head>
<body onload="createCode();">


<div align="center" >
<p></p>
      <span align="center" style="font-size: 40px;font-weight: bold;">大学毕业生就业信息管理系统</span>
</div>
<table width="681" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:120px">
  <tr>
    <td width="353" height="259" align="center" valign="bottom" background="Images/login_1.gif">
    <div style="margin-top: 100px;" >
    <!--  
    <img src="Images/1111.gif" width="120" height="100"/>
    -->
    </div>
    <table width="90%" border="0" cellspacing="3" cellpadding="0">
      <tr>
     
      </tr>
    </table></td>
    <td width="195" background="Images/login_2.gif"><table width="190" height="106" border="0" align="center" cellpadding="2" cellspacing="0">
      <form method="post"  name="NETSJ_Login" action="method!login"  onsubmit=" return  check()">
            <tr>
              <td height="50" colspan="2" align="left"><br/></td>
            </tr>
            
            <tr>
              <td width="60" height="30" align="left">用户角色</td>
              <td>
              <select name="role">
                  <option value="4">系统管理员</option>
                <option value="2">毕业生用户</option>
                <option value="1">企业用户</option>
                <option value="3">教师管理员</option>
              </select>
              
              </td>
            </tr>
            
            <tr>
              <td width="60" height="30" align="left">用户名<br/>(或学号)</td>
              <td><input name="username" type="TEXT" style="background:url(Images/login_6.gif) repeat-x; border:solid 1px #27B3FE;width: 134px; height:20px; background-color:#FFFFFF" id="UserName"size="14"></td>
            </tr>
            <tr>
              <td height="30" align="left">密码</td>
              <td><input name="password" TYPE="PASSWORD" style="background:url(Images/login_6.gif) repeat-x; border:solid 1px #27B3FE;width: 134px; height:20px; background-color:#FFFFFF" id="Password" size="16"></td>
            </tr>
            
             <tr>
              <td height="30" align="left">输入验证码:</td>
              <td><input type="text" id="input1" size="16"/></td>
            </tr>
            
             <tr>
              <td height="30" align="left">获取验证码:</td>
              <td><input type="text" id="checkCode" class="code" style="width: 55px" size="10"/> <a href="####" onclick="createCode()">看不清楚</a></td>
            </tr>
            
            
      
            
            
              <td colspan="2" align="center"><input type="submit" name="submit" style="background:url(Images/login_5.gif) no-repeat" value=" 登  陆 "> 
			  <input type="reset" name="Submit" style="background:url(Images/login_5.gif) no-repeat" value=" 取  消 ">
			  <br/> 
			  <a href="####" onclick="registershow()">企业用户注册</a>
			  
			  </td>
            <tr>
              <td height="5" colspan="2"></td>
   
    </table></td>
    <td width="133" background="Images/login_3.gif">&nbsp;</td>
  </tr>
  <tr>
    <td height="161" colspan="3" background="Images/login_4.gif"></td>
  </tr>
</table>

</body>
</html>