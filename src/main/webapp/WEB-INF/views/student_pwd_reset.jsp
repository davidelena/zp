<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include_zp.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta name="keywords" content="校园招聘，校招，宣讲会，招聘会，大学生，优质招聘，优质信息，实习，第一站，精准推荐" />
<meta name="description" content="第一站zhancampus.com为大学生推荐最新、最优质的校招和实习信息，帮助大学生迈出职场第一步！" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>忘记密码</title>
<style>
html,body {
	height: 100%;
}
</style>
<body>
	<script type="text/javascript">
		$(function() {
			initPage();
		});

		var initPage = function() {
			$("#student_pwd_reset_form").validate({
				rules : {
					account : {
						required : true
					},
					code : {
						required : true,
						number : true
					}
				},
				messages : {
					account : {
						required : "请输入注册时候的邮箱地址/手机",
					},
					code : {
						required : "请输入6位验证码",
						number : "验证码只能为数字"
					}
				},
				errorPlacement : function(label, element) {
					showErrorMsg(label, element);
				},
				submitHandler : function(form) {
					doPasswordReset(form);
				}
			});
		};

		//获取短信验证码
		var getResetCode = function() {
			var account = $("#account").val();
			var accountType = $("#accountType").val();
			$.ajax({
				"url" : "member/getSmsCode",
				"type" : "POST",
				"dataType" : "JSON",
				"data" : {
					account : account,
					accountType : accountType,
					isReset : true
				},
				"success" : function(data) {
					var obj = $("#code-error");
					if (data.isaccount != undefined && data.isaccount) {
						obj = $("#account-error");
					}

					if (data.success == 1) {
						$(obj).text("验证码已发送，请查收");
						$(obj).show();
						setInterval(function() {
							$(obj).hide();
						}, 5000);
					} else {
						$(obj).text(data.msg);
						$(obj).show();
					}
				}
			});
		};

		//检查输入的合法性
		var checkInput = function() {
			var inputVal = $("#account").val();
			if (isMobile(inputVal)) {
				$("#accountType").val("mobile");
				$("#account-error").hide();
				$("#account").rules("remove", "email");
			} else {
				$("#account").rules("add", {
					email : true,
					messages : {
						email : "请输入有效的邮箱地址，例如：david@diyizhan.com"
					}
				});
				$("#accountType").val("email");
			}
		};

		var doPasswordReset = function(form) {
			var account = $("#account").val();
			var source = $("#pageType").val();
			var accountType = $("#accountType").val();
			var code = $("#code").val();
			$(form).find(".aLinkResetPassword").attr("disabled", true);

			$.ajax({
				type : 'POST',
				data : {
					account : account,
					source : source,
					accountType : accountType,
					code : code
				},
				url : "member/resetStudentPassword",
				dataType : 'json'
			}).done(function(result) {
				if (result.success) {
					$('#code-error').hide();
					window.location.href = "student_login";
				} else {
					$('#code-error').text(result.msg);
					$('#code-error').show();
				}
				$(form).find(".aLinkResetPassword").attr("disabled", false);
			});
		};

		var showErrorMsg = function(label, element) {
			label.insertAfter(element).css("position", "absolute").css("top", "260px").css("left", "30px");
		};

		var submitReset = function() {
			$("#student_pwd_reset_form").submit();
			$("#aLinkResetPassword").focus();
		};
	</script>
	<input type="hidden" id="pageType" value="${pageType}" />
	<input id="accountType" name="accountType" value="email" type="hidden" />
	<div class="stuFP_blank"></div>
	<div class="stuFP_body">
		<div class="stuFP_left">
			<img src="images_zp/erweima.jpg" alt="qrcode" class="stuFP_pic" /> <br /> <img src="images_zp/stuloginwelcome.png"
				alt="wechat" class="stuFP_text" />
		</div>
		<div class="stuFP_right">
			<h1 style="margin-left: 30px;">忘了密码?</h1>
			<h3 style="margin-left: 30px;">请输入收到验证码，帮我找回密码，再登录邮箱查看重置后的初始密码</h3>
			<form id="student_pwd_reset_form">
				<input type="text" placeholder="请输入您注册的邮箱/手机号" id="account" name="account" class="stuFP_user" onblur="checkInput()" />
				<br /> <label id="account-error" class="error" for="account"
					style="position: absolute; top: 190px; margin-left: 30px;"></label> <input type="text" placeholder="请输入收到的验证码"
					id="code" name="code" class="stuFP_code" maxlength="6" /> <br /> <label id="code-error" class="error"
					for="password" style="position: absolute; top: 260px; left:30px;"></label> <a href="javascript:void(0)"
					onclick="getResetCode();" class="stuFP_codeBotton" style="top: 207px;">获取验证码</a> <a href="javascript:void(0)"
					id="aLinkResetPassword" class="stuFP_findBotton" onclick="submitReset();">帮我找回密码</a>
			</form>
		</div>
	</div>
</body>
</html>