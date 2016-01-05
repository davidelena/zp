<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include_zp.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="keywords"
	content="校园招聘，校招，宣讲会，招聘会，大学生，优质招聘，优质信息，实习，第一站，精准推荐" />
<meta name="description"
	content="第一站zhancampus.com为大学生推荐最新、最优质的校招和实习信息，帮助大学生迈出职场第一步！" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业用户登录页面</title>
<style type="text/css">
html,body {
	height: 100%;
}
</style>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			$("#loginForm").validate({
				rules : {
					type : {
						required : true
					},
					account : {
						required : true,
						email : true
					},
					password : {
						required : true
					},
					verifyCode : {
						required : true,
						remote : {
							type : 'POST',
							url : 'member/validateCode',
							dataType : "json",
							data : {
								code : function() {
									return $("#verifyCode").val();
								}
							},
							delay : 500
						}
					},
					chkAgree : {
						required : true
					}
				},
				messages : {
					account : {
						required : "请输入常用邮箱地址",
						email : "请输入有效的邮箱地址，如：david@diyizhan.com"
					},
					password : {
						required : "请输入密码"
					},
					verifyCode : {
						required : "请输入验证码",
						remote : "验证码错误，请重新输入"
					}
				},
				errorPlacement : function(label, element) {
					showErrorMsg(label, element);
				},
				submitHandler : function(form) {
					doLogin(form);
				}
			});
		});

		//获取验证码
		var getVerifyCode = function() {
			var timestamp = (new Date()).valueOf();
			var src = "member/verifyCode?timestamp=" + timestamp;
			$("#imgCode").attr("src", src);
		};

		var showErrorMsg = function(label, element) {
			var id = $(element).attr("id");
			if (id == "account") {
				label.insertAfter(element).css("position", "absolute").css("top", "93px").css("margin-left", "30px");
			} else if (id == "password") {
				label.insertAfter(element).css("position", "absolute").css("top", "173px").css("margin-left", "30px");
			} else if (id == "verifyCode") {
				label.insertAfter($(element).parent()).css("position", "absolute").css("top", "235px").css("margin-left", "30px");
			}
		};

		var doLogin = function(form) {
			var account = $('#account').val();
			var password = $('#password').val();
			var verifyCode = $("#verifyCode").val();
			var accountType = $("#accountType").val();
			var pageType = $("#pageType").val();

			$(form).find("#aLinkLogin").attr("disabled", true);

			$.ajax({
				type : 'POST',
				data : {
					account : account,
					password : password,
					code : verifyCode,
					accountType : accountType,
					source : pageType
				},
				url : "member/doLogin",
				dataType : 'json'
			}).done(function(result) {
				if (result.success) {
					window.location.href = result.returnUrl;
					$('#password-error').hide();
				} else {
					var message = result.msg;
					$('#password-error').text(message).show();
				}
				$(form).find("#aLinkLogin").attr("disabled", false);
			});
		};

		var submitLogin = function() {
			$("#loginForm").submit();
			$("#aLinkLogin").focus();
		};
	</script>
	<input type="hidden" id="pageType" value="${pageType}" />
	<div class="coLGIN_blank"></div>
	<div class="coLGIN_header">
		<div class="coLGIN_logo">
			<p></p>
			<div>
				<a href="company_index"><img src="images_zp/stuloginlogo.png"
					alt="第一站" /></a>
			</div>
		</div>
		<div class="coLGIN_tag">
			<p class="coLGIN_stu">
				<a href="student_login">我是学生</a>
			</p>
			<p class="coLGIN_comp">
				<a href="###">我是企业用户</a>
			</p>
		</div>
	</div>
	<div class="coLGIN_body">
		<div class="coLGIN_left">
			<p>
				<img src="images_zp/erweima.jpg" alt="qrcode" class="coLGIN_pic" />
			</p>
			<p>
				<img src="images_zp/stuloginwelcome.png" alt="wechat"
					class="coLGIN_text" />
			</p>
		</div>
		<div class="coLGIN_right">
			<p class="coLGIN_getOne">
				还没有企业账号？<a style="display: inline;" href="company_register">马上注册！</a>
			</p>
			<form id="loginForm" class="loginForm">
				<input id="accountType" name="accountType" value="email"
					type="hidden" /> <input type="text" placeholder="请输入您注册的邮箱/手机号"
					id="account" name="account" class="coLGIN_user" /> <input
					type="password" placeholder="请输入您的密码" id="password" name="password"
					class="coLGIN_pswd" /><label id="password-error" class="error"
					for="password"
					style="position: absolute; top: 173px; margin-left: 30px;"></label>
				<div class="coLGIN_checkBox">
					<input name="auto" type="checkbox" value="" />记住我，下次自动登录
				</div>
				<div id="imgCodeDiv" style="width: 580px;">
					<input type="text" id="verifyCode" name="verifyCode"
						placeholder="请输入验证码" class="stuSUP_code_cp_img" /><img
						src="member/verifyCode" alt="验证码" onclick="getVerifyCode()"
						id="imgCode" class="coLogin_codeBotton_cp pointer"
						style="height: 35px; width: 100px;"> <a id="changeImg"
						href="javascript:void(0)" onclick="getVerifyCode()"
						style="position: absolute; right: 140px; top: 210px; display: inline;">换一张</a>
				</div>
				<div class="coLGIN_forget">
					<a href="company_pwd_reset">忘记密码?</a>
				</div>
				<a href="javascript:void(0)" id="aLinkLogin"
					onclick="submitLogin();" class="coLoginBotton">登录</a>
			</form>
		</div>
	</div>
</body>
</html>