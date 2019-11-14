<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>新用户注册</title>
		<%
		response.addHeader("Cache-Control", "no-store, must-revalidate"); 
		response.addHeader("Expires", new java.util.Date().getTime()+"");
		%>
		
	<style type="text/css">
#regdiv {
	position: absolute;
	width: 700px;
	height: 500px;
	background-image: url(img/b2c_04.jpg);
}
</style>
<script language="javascript" type="text/javascript" src="js/jquery.min.js"></script>

<script language="javascript" type="text/javascript">

function checkregisterform()
{
	 
	 if (document.getElementById('usernameid').value=="")
	{
		alert("用户名不能为空");
		return false;
	}
	var valid=/^\w+$/;
	if(!valid.test(document.getElementById('usernameid').value)){
		alert("用户名必须是数字、字母或下划线");
		return false;
	}

	if (document.getElementById('passwordid').value=="")
	{
		alert("密码不能为空");
		return false;
	}
	if (document.getElementById('passwordid').value.length<5)
	{
		alert("密码长度必须大于5位");
		return false;
	}
	if (document.getElementById('password2id').value != document.getElementById('passwordid').value)
	{
		alert("确认密码与密码不一致");
		return false;
	}	 
	if (document.getElementById('truenameid').value=="")
	{
		alert("真实姓名不能为空");
		return false;
	}
	if (document.getElementById('qiyemingchenid').value=="")
	{
		alert("企业名称不能为空");
		return false;
	}
	
	if (document.getElementById('qiyelianxidianhuaid').value=="")
	{
		alert("企业联系电话不能为空");
		return false;
	}
	
	if (document.getElementById('qiyedizhiid').value=="")
	{
		alert("企业地址不能为空");
		return false;
	}
	
	if (document.getElementById('congshihangyeid').value=="")
	{
		alert("从事行业不能为空");
		return false;
	}
	
	if (document.getElementById('farendaibiaoid').value=="")
	{
		alert("法人代表不能为空");
		return false;
	}
	doRequestUsingPOSTregister();
	
}

function createQueryStringregister(){
	//必须两次编码才能解决中文问题
	var username = encodeURI(encodeURI($("#usernameid").val()));
	var password = encodeURI(encodeURI($("#passwordid").val()));
	var truename = encodeURI(encodeURI($("#truenameid").val()));
	var qiyemingchen = encodeURI(encodeURI($("#qiyemingchenid").val()));
	var qiyelianxidianhua = encodeURI(encodeURI($("#qiyelianxidianhuaid").val()));
	var qiyedizhi = encodeURI(encodeURI($("#qiyedizhiid").val()));
	var congshihangye = encodeURI(encodeURI($("#congshihangyeid").val()));
	var farendaibiao = encodeURI(encodeURI($("#farendaibiaoid").val()));
	var qiyequyu = encodeURI(encodeURI($("#qiyequyuid").val()));

	var queryString = 
	"username="+username+
	"&password="+password+
	"&truename="+truename+
	"&qiyemingchen="+qiyemingchen+
	"&qiyelianxidianhua="+qiyelianxidianhua+
	"&qiyedizhi="+qiyedizhi+
	"&congshihangye="+congshihangye+
	"&qiyequyu="+qiyequyu+
	"&farendaibiao="+farendaibiao;
	return queryString;
}



function doRequestUsingPOSTregister(){

		$.ajax({
			type: "POST",
			url: "method!register",
			data: createQueryStringregister(),
			success: function(data){
			var returnvalue = decodeURI(data);
				alert(returnvalue);
				if('该用户名已经存在，请重新注册！'!=returnvalue){
					window.close();
				}
				
			}
		});
}


function resetform(){
window.close();
}
</script>


</head>


	<body>



		<div id="regdiv">

			<br />
			<br />
			<table align="center">
				<tr align="center">
					<td>
						新用户注册
					</td>
				</tr>
			</table>
			
			
				<table align="center" border="1" cellpadding="5" cellspacing="3">
					<tr>
						<td>
							用户名：
						</td>
						<td>
							<input type="text" name="username" size="25"  id="usernameid"/>
						</td>
					</tr>
					<tr>
						<td>
							密码：
						</td>
						<td>
							<input type="password" name="password" size="25" id="passwordid"/>
						</td>
					</tr>
					<tr>
						<td>
							确认密码：
						</td>
						<td>
							<input type="password" name="password2" size="25" id="password2id"/>
						</td>
					</tr>
					<tr>
						<td>
							真实姓名
						</td>
						<td>
							<input type="text" name="truename" size="25" id="truenameid"/>
						</td>
					</tr>
					<tr>
						<td>
							企业名称
						</td>
						<td>
							<input type="text" name="qiyemingchen" size="25" id="qiyemingchenid"/>
						</td>
					</tr>
					<tr>
						<td>
							企业联系电话
						</td>
						<td>
							<input type="text" name="qiyelianxidianhua" size="25" id="qiyelianxidianhuaid" />
						</td>
					</tr>
					<tr>
						<td>
							企业地址
						</td>
						<td>
							<input type="text" name="qiyedizhi" size="25" id="qiyedizhiid"/>
						</td>
					</tr>
					<tr>
						<td>
							从事行业
						</td>
						<td>
							<input type="text" name="congshihangye" size="25" id="congshihangyeid"/>
						</td>
					</tr>
					<tr>
						<td>
							法人代表
						</td>
						<td>
							<input type="text" name="farendaibiao" size="25" id="farendaibiaoid"/>
						</td>
					</tr>
					
					<tr>
						<td>
							企业区域
						</td>
						<td>
							<select name="qiyequyu" id="qiyequyuid">
							<option value="南方">南方</option>
							<option value="北方">北方</option>
							</select>
						</td>
					</tr>
					
					<tr>

						<td colspan="2">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="submit" value="注册"  onclick="checkregisterform()"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="取消" onclick="resetform()"/>
						</td>
					</tr>
				</table>


		</div>

	</body>

</html>
