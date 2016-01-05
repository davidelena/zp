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
<style type="text/css">
html,body {
	height: 100%;
}
</style>
<title>学生用户登录页面</title>
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

		//获取短信验证码
		var getMobileCode = function() {
			var mobile = $("#account").val();

			$.ajax({
				"url" : "member/getSmsCode",
				"type" : "POST",
				"dataType" : "JSON",
				"data" : {
					account : mobile
				},
				"success" : function(data) {
					if (data.success) {
						$("#mobileCode-error").text("短信已发送，请查收");
						$("#mobileCode-error").show();
						$("#mobileCode-error").css("display", "block");
						setTimeout(function() {
							$("#mobileCode-error").hide();
						}, 3000);
					} else {
						$("#mobileCode-error").text(data.msg);
						$("#mobileCode-error").show();
					}
				}
			});
		};

		//显示对应验证码
		var showCode = function() {
			var inputVal = $("#account").val();
			if (isMobile(inputVal)) {
				$("#mobileCodeDiv").show();
				$("#imgCodeDiv").hide();
				$("#accountType").val("mobile");
				$("#account-error").hide();
				$("#account").rules("remove", "email");
				$("#account").rules("add", {
					messages : {
						remote : "该手机已经被注册了，请重新输入"
					}
				});

				$("#mobileCode").rules("add", {
					required : true,
					messages : {
						required : "请输入手机验证码"
					}
				});
			} else {
				$("#imgCodeDiv").show();
				$("#mobileCodeDiv").hide();
				$("#account").rules("add", {
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
							}
						}
					},
					messages : {
						email : "请输入有效的邮箱地址，如：daviddai@qq.com",
						remote : "该邮箱已经被注册了，请重新输入"
					}
				});
				$("#mobileCode").rules("remove", "required");
				$("#accountType").val("email");
			}
		};

		var showErrorMsg = function(label, element) {
			var id = $(element).attr("id");
			if (id == "account") {
				label.insertAfter(element).css("position", "absolute").css("top", "83px").css("margin-left", "30px");
			} else if (id == "password") {
				label.insertAfter(element).css("position", "absolute").css("top", "162px").css("margin-left", "30px");
			} else if (id == "verifyCode") {
				label.insertAfter($(element).parent()).css("position", "absolute").css("top", "248px").css("margin-left", "30px");
			} else if (id == "mobileCode") {
				label.insertAfter($(element).parent()).css("position", "absolute").css("top", "240px").css("margin-left", "30px");
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
				} else {
					$('#mobileCode-error').text(result.msg).css("display", "block");
				}
				$(form).find(".aLinkSubmit").attr("disabled", false);
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
						remote : {
							type : 'POST',
							url : 'member/accountExists',
							dataType : "json",
							data : {
								accountType : function() {
									var account = $("#account").val();
									if (isMobile(account)) {
										$("#accountType").val("mobile");
									}
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
						required : "请输入常用邮箱地址/手机",
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
	<input type="hidden" id="pageType" value="${pageType}" />
	<input id="accountType" name="accountType" value="email" type="hidden" />
	<div class="stuSUP_blank"></div>
	<div class="stuSUP_header">
		<div class="stuSUP_logo">
			<p></p>
			<div>
				<a href="student_index"><img src="images_zp/stuloginlogo.png"
					alt="第一站" /></a>
			</div>
		</div>
		<div class="stuSUP_tag">
			<p class="stuSUP_stu">
				<a href="###">我是学生</a>
			</p>
			<p class="stuSUP_comp">
				<a href="company_register">我是企业用户</a>
			</p>
		</div>
	</div>
	<div class="stuSUP_body">
		<div class="stuSUP_left">
			<p>
				<img src="images_zp/erweima.jpg" alt="qrcode" class="stuSUP_pic" />
			</p>
			<p>
				<img src="images_zp/stuloginwelcome.png" alt="wechat"
					class="stuSUP_text" />
			</p>
		</div>
		<div class="stuSUP_right">
			<p class="stuSUP_already">
				已有学生账号？<a href="student_login">马上登录！</a>
			</p>
			<form id="loginForm" class="loginForm">
				<input type="text" placeholder="您的常用邮箱/手机号" id="account"
					name="account" class="stuSUP_user" onblur="showCode();" /> <input
					type="password" placeholder="密码：6-20位数字字母组合" id="password"
					name="password" class="stuSUP_pswd" />

				<div id="imgCodeDiv" class="stuLGIN_imgcode_div"
					style="width: 580px;">
					<input type="text" id="verifyCode" name="verifyCode"
						placeholder="请输入验证码" class="stuLGIN_verifycode" /><img
						src="member/verifyCode" alt="验证码" id="imgCode"
						class="stuLGIN_verifycode_img pointer" onclick="getVerifyCode()">
					<a id="changeImg" href="javascript:void(0)"
						onclick="getVerifyCode()" class="stuLGIN_changeImgLink">换一张</a>
				</div>

				<div id="mobileCodeDiv" class="stuLGIN_imgcode_div">
					<input type="text" placeholder="请输入收到的验证码" id="mobileCode"
						name="mobileCode" class="stuSUP_code_mobile" /> <a
						href="javascript:void(0)" id="aLinkGetMobileCode"
						onclick="getMobileCode();" class="stuSUP_codeBotton">获取验证码</a> <label
						id="mobileCode-error" class="error" for="mobileCode"
						style="position: absolute; top: 240px; margin-left: 30px; display: block;"></label>
				</div>

				<a href="javascript:void(0)" id="aLinkSubmit"
					onclick="submitRegister();" class="stuSUP_signBotton">注册</a>

				<div
					style="position: absolute; top: 150px; left: 30px; font-size: 12px; color: #3c3c3c; font-weight: normal; height: 30px;">
					<input type="checkbox" id="chkAgree" name="chkAgree"
						checked="checked" class="checkbox valid">点此注册，即同意我们的用户隐私条款和用户协议</input>
					<input type="hidden" id="hdAgree" value="1">
				</div>
			</form>
			<h4>使用其他账号直接登录</h4>
			<div class="stuSUP_weChat">
				<a href="#"><img src="images_zp/wechat.png" alt="wechat"
					class="weChat"></a>
			</div>
			<div class="stuSUP_QQ">
				<a href="#"><img src="images_zp/QQ.png" alt="QQ" class="QQ"></a>
			</div>
		</div>
	</div>
</body>
</html>