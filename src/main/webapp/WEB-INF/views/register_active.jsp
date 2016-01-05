<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include_zp.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta name="keywords"
	content="校园招聘，校招，宣讲会，招聘会，大学生，优质招聘，优质信息，实习，第一站，精准推荐" />
<meta name="description"
	content="第一站zhancampus.com为大学生推荐最新、最优质的校招和实习信息，帮助大学生迈出职场第一步！" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>注册激活</title>
<style type="text/css">
html,body {
	height: 100%;
}
</style>
<body>
	<script type="text/javascript">
		var resend = function() {
			$.ajax({
				url : "member/resend",
				type : 'POST',
				data : {
					memberId : $("#memberid").val(),
					email : $("#email").val()
				},
				dataType : 'json',
				success : function(data) {
					if (data.success == 1) {
						alert("发送成功");
					}
				}
			});
		};
	</script>
	<input type="hidden" value="${memberDTO.memberId}" name="memberid"
		id="memberid" />
	<input type="hidden" value="${memberDTO.email}" name="email" id="email" />
	<div class="stuCFi_blank"></div>
	<div class="stuCFi_body">
		<div class="stuCFi_left">
			<center>
				<img src="images_zp/hello.jpg" width="300" alt="" />
			</center>
			<br />
			<center>
				<img src="images_zp/stuloginlogo.png" width="200" alt="" />
			</center>
			<center>
				<a href="student_index"
					style="color: #3c3c3c; text-decoration: underline;">点击回到首页，看看有什么新鲜事</a>
			</center>
		</div>
		<div class="stuCFi_right">
			<div
				style="position: relative; width: 570px; height: 190px; border-bottom: 1px dashed #3c3c3c;">
				<h1>验证邮箱，完成注册</h1>
				<h3 style="font-size: 16px; color: #3c3c3c;">
					我们已将验证邮件发送至邮箱：<span style="color: #339933;">${memberDTO.email}</span><br />点击邮件中的链接即可完成注册，并可使用第一站的所有功能
				</h3>
				<a href="${mailGatewayAddress}" class="stuCFi_findBotton"
					style="top: 120px;">登录邮箱验证</a>
			</div>
			<div class="CFhow"
				style="position: relative; width: 570px; height: 150px;">
				<h3 style="margin-top: 20px; margin-bottom: 20px;">没有收到验证邮件，怎么办？</h3>
				<h6>
					邮箱填写错误？<a href="student_register">换个邮箱</a>
				</h6>
				<h6>看看是否在邮箱的垃圾邮件、广告邮件目录里</h6>
				<div>
					<h6>
						稍等几分钟，若还未收到验证邮件，<a href="javascript:void(0)" onclick="resend();">重新发送验证邮件</a>
					</h6>
					<div></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>