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
<style>
html,body {
	height: 100%;
}
</style>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			initPage();
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
				label.insertAfter($(element).parent()).css("position", "absolute").css("top", "252px").css("margin-left", "30px");
			}
		};

		var doRegister = function(form) {
			var type = $("#pageType").val();
			var account = $('#account').val();
			var password = $('#password').val();
			var accountType = $("#accountType").val();
			var verifyCode = accountType == "email" ? $("#verifyCode").val() : $("#mobileCode").val();
			$(form).find(".aLinkSubmit").attr("disabled", true);

			$.ajax({
				type : 'POST',
				data : {
					account : account,
					password : password,
					type : type,
					code : verifyCode,
					accountType : accountType
				},
				url : "member/doRegister",
				dataType : 'json'
			}).done(function(result) {
				if (result.success) {
					window.location.href = result.returnUrl;
					$("#account-error").text("");
					$("#account-error").hide();
				} else {
					$("#account-error").text(result.msg);
					$("#account-error").show();
					$(form).find(".aLinkSubmit").attr("disabled", false);
				}
			});
		};

		var submitRegister = function() {
			$("#loginForm").submit();
			$("#aLinkSubmit").focus();
		};

		//初始化页面
		var initPage = function() {
			$("#chkAgree").click(function() {
				var agreeVal = $("#hdAgree").val();
			});

			$("#mobileCodeDiv").hide();

			$("#loginForm").validate({
				rules : {
					account : {
						required : true,
						email : true,
						remote : {
							type : 'POST',
							url : 'member/accountExists',
							dataType : "json",
							data : {
								accountType : function() {
									return $("#accountType").val();
								},
								account : function() {
									return $("#account").val();
								},
								pageType : function() {
									return $("#pageType").val();
								}
							}
						},
					},
					password : {
						required : true,
						rangelength : [ 6, 16 ]
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
						email : "邮箱格式不正确，例如diyizhan@qq.com",
						remote : "该邮箱已经被注册了，请重新输入"
					},
					password : {
						required : "请输入密码",
						rangelength : "请输入6-16位密码，字母区分大小写"
					},
					verifyCode : {
						required : "请输入验证码",
						remote : "验证码错误，请重新输入"
					},
					chkAgree : {
						required : "请接受第一站用户协议"
					}
				},
				errorPlacement : function(label, element) {
					showErrorMsg(label, element);
				},
				submitHandler : function(form) {
					doRegister(form);
				}
			});
		};
	</script>
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
				<a href="student_register">我是学生</a>
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
				已有企业账号？<a style="display: inline;" href="company_login">马上登录！</a>
			</p>
			<input type="hidden" id="pageType" value="${pageType}" /> <input
				id="accountType" name="accountType" value="email" type="hidden" />
			<form id="loginForm" class="loginForm">
				<input type="text" placeholder="请填写您的公司邮箱" id="account"
					name="account" class="coLGIN_user" /> <label id="account-error"
					class="error" for="account"
					style="position: absolute; top: 93px; margin-left: 30px;"></label>
				<input type="password" placeholder="密码：6-20位数字字母组合" id="password"
					name="password" class="coLGIN_pswd" />

				<div id="imgCodeDiv" class="coLGIN_imgcode_div">
					<input type="text" placeholder="请输入验证码" id="verifyCode"
						name="verifyCode" class="coLGIN_verifycode" /> <img
						src="member/verifyCode" alt="验证码" id="imgCode"
						class="coLGIN_verifycode_img pointer" onclick="getVerifyCode()" />
					<a id="changeImg" href="javascript:void(0)"
						onclick="getVerifyCode()" class="coRegisterImg">换一张</a>
				</div>
				<a href="javascript:void(0)" id="aLinkSubmit"
					onclick="submitRegister();" class="coLGIN_signBotton">注册</a>
				<h4 class="coLGIN_agree">
					<input type="checkbox" id="chkAgree" name="chkAgree"
						checked="checked" class="checkbox valid">点此注册，即同意我们的用户隐私条款和用户协议</input>
					<input type="hidden" id="hdAgree" value="1">
				</h4>
			</form>
		</div>
	</div>
</body>
</html>