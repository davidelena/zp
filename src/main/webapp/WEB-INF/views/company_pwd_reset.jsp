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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>忘记密码</title>
<style>
html,body {
	height: 100%;
}
</style>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			$("#pwdreset_form").validate({
				rules : {
					account : {
						required : true,
						email : true
					}
				},
				messages : {
					account : {
						required : "请输入注册的邮箱地址",
						email : "请输入有效的邮箱地址，如：david@diyizhan.com"
					}
				},
				errorPlacement : function(label, element) {
					showErrorMsg(label, element);
				},
				submitHandler : function(form) {
					doReset(form);
				}
			});
		});

		var showErrorMsg = function(label, element) {
			label.insertAfter(element).css("position", "absolute").css("top", "180px").css("margin-left", "30px");
		};

		var doReset = function(form) {
			var account = $('#account').val();
			var pageType = $("#pageType").val();

			$(form).find("#aLinkReset").attr("disabled", true);

			$.ajax({
				url : "sendCompanyPwdResetInfo",
				type : 'POST',
				data : {
					account : account,
					source : pageType
				},
				dataType : 'json'
			}).done(function(result) {
				if (result.success) {
					$("#reset_msg").hide();
				} else {
					$("#reset_msg").text(result.msg);
					$("#reset_msg").show();
				}
				$(form).find("#aLinkReset").attr("disabled", false);
			});
		};

		var submitReset = function() {
			$("#pwdreset_form").submit();
			$("#aLinkReset").focus();
		};
	</script>
	<input type="hidden" id="pageType" value="${pageType}" />
	<div class="coFP_blank"></div>
	<div class="coFP_body">
		<div class="coFP_left">
			<img src="images_zp/erweima.jpg" alt="qrcode" class="coFP_pic" /> <br />
			<img src="images_zp/stuloginwelcome.png" alt="wechat"
				class="coFP_text" />
		</div>
		<div class="coFP_right">
			<h1 style="margin-left: 30px;">忘了密码?</h1>
			<h3 style="margin-left: 30px;">请点击下方帮我找回密码，再登录邮箱查看重置密码链接</h3>
			<form id="pwdreset_form">
				<input type="text" placeholder="请输入您注册的公司邮箱" id="account"
					name="account" class="coFP_user" /><br /> <label id="reset_msg"
					class="error" for="account"
					style="position: absolute; top: 215px; margin-left: 30px;"></label>
				<a id="aLinkReset" href="javascript:void(0)"
					onclick="submitReset();" class="coFP_findBotton">帮我找回密码</a>
			</form>
		</div>
	</div>
</body>
</html>