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
<meta name="viewport"
	content="wclassth=device-wclassth, initial-scale=1.0">
<title>我的账号 账号安全</title>
<link rel="shortcut icon" href="http://www.zhancampus.com/favicon.ico"
	type="image/x-icon" />
<link rel="icon" href="favicon.ico" type="image/x-icon">
<style type="text/css">
.ml6 {
	margin-left: 6px;
}

.ml30 {
	margin-left: 30px;
}

.ml55 {
	margin-left: 55px;
}

.wd220 {
	width: 220px;
}
</style>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			initPage();
		});

		var initPage = function() {
			$("#stuMs_loginmail_open").hide();
			$("#mobilephone_open").hide();
			$("#setpswd_open").hide();
			initLoginInfoForm();
			initCheckMobileForm();
			initPswdForm();
		};

		var modifyLoginMail = function() {
			$("#stuMs_loginmail_close").hide();
			$("#stuMs_loginmail_open").show();
			$("#loginPassword").val("");
			$("#newLoginMail").val("");
			$("#verfiyCode").val("");
		};

		var closeLoginMail = function() {
			$("#stuMs_loginmail_close").show();
			$("#stuMs_loginmail_open").hide();
		};

		var initLoginInfoForm = function() {
			var account = $("#hdLoginMail").val() == "" ? $("#hdLoginMobile").val() : $("#hdLoginMail").val()
			var checktype = $("#hdLoginMail").val() == "" ? "mobile" : "email";
			$("#loginInfoForm").validate({
				rules : {
					loginPassword : {
						required : true,
						remote : {
							type : 'POST',
							url : 'resume/checkPassword',
							dataType : "json",
							data : {
								account : function() {
									return account;
								},
								password : function() {
									return $("#loginPassword").val();
								},
								checktype : function() {
									return checktype;
								}
							}
						}
					},
					newLoginMail : {
						required : true,
						email : true,
						remote : {
							type : 'POST',
							url : 'resume/accountExists',
							dataType : "json",
							data : {
								account : function() {
									return $("#newLoginMail").val();
								}
							}
						}
					},
					verfiyCode : {
						required : true,
						remote : {
							type : 'POST',
							url : 'resume/checkVerfiyCode',
							dataType : "json",
							data : {
								account : function() {
									return $("#newLoginMail").val();
								},
								verfiyCode : function() {
									return $("#verfiyCode").val();
								}
							}
						}
					}
				},
				messages : {
					loginPassword : {
						required : "请输入登录密码",
						remote : "密码不正确，请重新输入"
					},
					newLoginMail : {
						required : "请输入新的登录邮箱",
						email : "请输入正确的邮箱格式，例如diyizhan@qq.com",
						remote : "您输入的邮箱已经被注册了，请重新输入"
					},
					verfiyCode : {
						required : "请输入验证码",
						remote : "您输入的验证码不正确，请重新输入"
					}
				},
				submitHandler : function(form) {
					saveLoginInfoFormData(form);
				}
			});
		};

		var saveLoginInfoFormData = function(form) {
			var memberId = $("#hdMemberId").val();
			var loginMail = $("#loginMail").text();
			var newLoginMail = $("#newLoginMail").val();

			$(form).find("#btnSaveMail").attr("disabled", true);

			$.ajax({
				url : "resume/saveLoginInfoFormData",
				type : 'POST',
				data : {
					memberId : memberId,
					loginMail : loginMail,
					newLoginMail : newLoginMail
				},
				dataType : 'json'
			}).done(function(result) {
				if (result.success) {
					$("#stuMs_loginmail_open").hide();
					$("#stuMs_loginmail_close").show();
					$("#loginMail").text(result.newEmail);
					$("#loginMailDisplay").text(result.newEmail);
					$("#loginMailImg").attr("src", "images_zp/accept.png");
				}
				$(form).find("#btnSaveMail").attr("disabled", false);
			});
		};

		var getVerfiyCode = function() {
			$.ajax({
				url : "resume/getVerfiyCode",
				type : "post",
				dataType : "json",
				data : {
					account : $("#newLoginMail").val()
				},
				success : function(data) {
					if (data.success) {
						$("#verfiyCode-error").show();
						$("#verfiyCode-error").text("验证码已发送至邮箱！");
						setTimeout(function() {
							$("#verfiyCode-error").hide();
						}, 3000);
					} else {
						$("#verfiyCode-error").show();
						$("#verfiyCode-error").text(data.message);
					}
				}
			});
		};

		var submitFormData = function() {
			$("#loginInfoForm").submit();
		};

		var modifyMobile = function() {
			$("#mobilephone_close").hide();
			$("#mobilephone_open").show();
			$("#txtMobilephone").val("");
			$("#mobileCheckCode").val("");
			$("#hdMobileType").val("update");
		};

		var checkMobile = function() {
			$("#mobilephone_close").hide();
			$("#mobilephone_open").show();
			$("#txtMobilephone").val("");
			$("#mobileCheckCode").val("");
			$("#hdMobileType").val("bindings");
		};

		var closeMoblie = function() {
			$("#mobilephone_close").show();
			$("#mobilephone_open").hide();
		};

		var initCheckMobileForm = function() {
			$("#checkMobileForm").validate({
				rules : {
					txtMobilephone : {
						required : true,
						remote : {
							type : 'POST',
							url : 'resume/checkMobileExists',
							dataType : "json",
							data : {
								account : function() {
									return $("#txtMobilephone").val();
								}
							}
						}
					},
					mobileCheckCode : {
						required : true,
						remote : {
							type : 'POST',
							url : 'resume/checkMobileVerfiyCode',
							dataType : "json",
							data : {
								account : function() {
									return $("#txtMobilephone").val();
								},
								verfiyCode : function() {
									return $("#mobileCheckCode").val();
								}
							}
						}
					}
				},
				messages : {
					txtMobilephone : {
						required : "请输入手机号",
						remote : "当前绑定手机号已经存在，请重新输入"
					},
					mobileCheckCode : {
						required : "请输入验证码",
						remote : "您输入的验证码不正确，请重新输入"
					}
				},
				submitHandler : function(form) {
					saveMobileInfoFormData(form);
				}
			});
		};

		var saveMobileInfoFormData = function(form) {
			var memberId = $("#hdMemberId").val();
			var mobile = $("#txtMobilephone").val();
			var mobileType = $("#hdMobileType").val();

			$(form).find("#btnMobileChkCode").attr("disabled", true);

			$.ajax({
				url : "resume/saveMobileInfoFormData",
				type : 'POST',
				data : {
					memberId : memberId,
					mobile : mobile,
					mobileType : mobileType
				},
				dataType : 'json'
			}).done(function(result) {
				if (result.success) {
					$("#mobilephone_close").show();
					$("#mobilephone_open").hide();
					var mobileType = $("#hdMobileType").val();
					if (mobileType == "bindings") {
						$("#mobileIsOk").attr("src", "images_zp/accept.png");
					}
					$("#mobilephone").text(result.data);
				}
				$(form).find("#btnMobileChkCode").attr("disabled", false);
			});
		};

		var submitMobileFormData = function() {
			$("#checkMobileForm").submit();
		};

		var getMobileVerfiyCode = function() {
			$.ajax({
				url : "resume/getMobileVerfiyCode",
				type : "post",
				dataType : "json",
				data : {
					account : $("#txtMobilephone").val()
				},
				success : function(data) {
					if (data.success) {
						$("#mobileCheckCode-error").show();
						$("#mobileCheckCode-error").text("验证码已发送至手机！");
						setTimeout(function() {
							$("#mobileCheckCode-error").hide();
						}, 3000);
					} else {
						$("#mobileCheckCode-error").show();
						$("#mobileCheckCode-error").text(data.message);
					}
				}
			});
		};

		var modifyPassword = function() {
			$("#setpswd_close").hide();
			$("#setpswd_open").show();
			$("#oldPassword").val("");
			$("#newPassword").val("");
			$("#newPassword2").val("");
		};

		//关闭密码
		var closePswd = function() {
			$("#setpswd_close").show();
			$("#setpswd_open").hide();
		};

		var initPswdForm = function() {
			$("#pswd_form").validate({
				rules : {
					oldPassword : {
						required : true,
						minlength : 6,
						remote : {
							type : 'POST',
							url : 'resume/checkPasswordWithMemberId',
							dataType : "json",
							data : {
								account : function() {
									return $("#hdMemberId").val();
								},
								password : function() {
									return $("#oldPassword").val();
								}
							}
						}
					},
					newPassword : {
						required : true,
						minlength : 6
					},
					newPassword2 : {
						required : true,
						minlength : 6,
						equalTo : "#newPassword"
					}
				},
				messages : {
					oldPassword : {
						required : "请输入原密码",
						minlength : "密码不能小于6位",
						remote : "原密码不正确"
					},
					newPassword : {
						required : "请输入新密码",
						minlength : "密码不能小于6位"
					},
					newPassword2 : {
						required : "请重新输入新密码",
						minlength : "密码不能小于6位",
						equalTo : "两次输入的密码不正确"
					}
				},
				errorPlacement : function(label, element) {
					showErrorMsg(label, element);
				},
				submitHandler : function(form) {
					savePswdFormData(form);
				}
			});
		};

		var showErrorMsg = function(label, element) {
			label.insertAfter(element).css("margin-left", "5px");
		};

		var savePswdFormData = function(form) {
			var memberId = $("#hdMemberId").val();
			var newPassword = $("#newPassword").val();

			$(form).find("#btnSavePswd").attr("disabled", true);

			$.ajax({
				url : "resume/savePswdFormData",
				type : 'POST',
				data : {
					memberId : memberId,
					newPassword : newPassword
				},
				dataType : 'json'
			}).done(function(result) {
				if (result.success) {
					$("#setpswd_open").hide();
					$("#setpswd_close").show();
					alert("密码修改成功!");
				}

				$(form).find("#btnSavePswd").attr("disabled", false);
			});
		};

		var submitPswdFormData = function() {
			$("#pswd_form").submit();
		};
	</script>
	<%@include file="/WEB-INF/views/sliderbar.jsp"%>
	<div class="stuMS_topMenu">
		<div class="stuMS_safetybtn" style="background-color: #ffffff;">
			<a href="javascript:;">账号安全</a>
		</div>
		<div class="stuMS_tidebtn" style="background-color: #6AC1FF;">
			<a href="resume/account_bindings?id=${memberDTO.memberId}">账号绑定</a>
		</div>
	</div>
	<input type="hidden" id="hdMemberId" value="${memberDTO.memberId}" />
	<input type="hidden" id="hdPageType" value="${pageType}" />
	<input type="hidden" id="hdLoginMail" value="${memberDTO.email}" />
	<input type="hidden" id="hdLoginMobile" value="${memberDTO.mobile}" />
	<input type="hidden" id="hdMobileType" value="bindings" />
	<div class="stuMS_blank"></div>
	<div class="stuMS_center">
		<!--内容-->
		<div class="stuMS_content">
			<!--登录邮箱-->
			<div id="stuMs_loginmail_close" class="stuMS_loginmail">
				<table style="margin: 0 auto;">
					<tr>
						<td
							style="font-weight: bold; color: #1C79C9; padding-left: 50px; width: 380px;"><span
							class="ml6">登陆邮箱</span></td>
						<td class="stuMS_2ndrow"><span id="loginMailDisplay">${memberDTO.email}</span></td>
						<td class="stuMS_3throw"><c:choose>
								<c:when test="${memberDTO.email.isEmpty()}">
									<img id="loginMailImg" src="images_zp/cross.png" width="16"
										height="16" alt="" />
								</c:when>
								<c:otherwise>
									<img id="loginMailImg" src="images_zp/accept.png" width="16"
										height="16" alt="" />
								</c:otherwise>
							</c:choose></td>
						<td class="stuMS_4throw"><a href="javascript:;"
							onclick="modifyLoginMail()"
							style="display: inline; margin-right: 20px;">修改</a></td>
					</tr>
				</table>
			</div>

			<!--登录邮箱-->
			<div id="stuMs_loginmail_open" class="stuMS_loginmail">
				<form id="loginInfoForm">
					<table style="line-height: 50px;">
						<tr>
							<td style="font-weight: bold; color: #1C79C9; text-align: left;"><span
								class="ml30">修改登陆邮箱</span></td>
							<td class="stuMS_2ndrow"
								style="font-size: 12px; line-height: 20px; color: #c3c3c3;">您将修改登录第一站的邮箱地址。<br />接收职位推荐的邮箱地址需进入“我的简历”修改。
							</td>
						</tr>
						<c:choose>
							<c:when test="${!memberDTO.email.isEmpty()}">
								<tr>
									<td style="text-align: right;">原登录邮箱</td>
									<td style="padding-left: 20px;"><span id="loginMail">${memberDTO.email}</span></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td style="text-align: right;">账号手机</td>
									<td style="padding-left: 20px;"><span id="loginMobile">${memberDTO.mobile}</span></td>
								</tr>
							</c:otherwise>
						</c:choose>
						<tr>
							<td style="text-align: right;">登录密码</td>
							<td style="padding-left: 20px;"><input id="loginPassword"
								name="loginPassword" type="password" placeholder="请输入登录密码"
								name="password" class="stuMS_pswd1 wd220" /><br /> <label
								id="loginPassword-error" class="error" for="loginPassword"
								style=""></label></td>
						</tr>
						<tr>
							<td style="text-align: right;">新登录邮箱</td>
							<td style="padding-left: 20px;"><input type="text"
								id="newLoginMail" name="newLoginMail" placeholder="请输入新登录邮箱"
								class="stuMS_newmail wd220" /><br /> <label
								id="newLoginMail-error" class="error" for="newLoginMail"
								style=""></label></td>
						</tr>
						<tr>
							<td style="text-align: right;">验证码</td>
							<td style="padding-left: 20px;"><input type="text"
								placeholder="请输入验证码" id="verfiyCode" name="verfiyCode"
								class="stuMS_writecode wd220" /><input type="button"
								value="获取验证码" class="stuMS_writecodebtn"
								onclick="getVerfiyCode()" /><br /> <label
								id="verfiyCode-error" class="error" for="verfiyCode" style=""></label></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center; font-size: 12px;">如果您没有收到验证码，请电话联系我们：021-12563824</td>
						</tr>
						<tr>
							<td style="text-align: right;"></td>
							<td style="padding-left: 20px;"><input id="btnSaveMail"
								type="button" value="确定" class="stuMS_yes"
								onclick="submitFormData()" /><input type="button" value="取消"
								class="stuMS_no" onclick="closeLoginMail()" /></td>
						</tr>
					</table>
				</form>
			</div>
			<input type="hidden" id="hdmobilephone" value="${memberDTO.mobile}" />
			<!--手机号码-->
			<div id="mobilephone_close" class="stuMS_mobilephone">
				<table>
					<tr>
						<td
							style="font-weight: bold; color: #1C79C9; padding-left: 50px; width: 380px;"><span
							class="ml6">手机号码</span></td>
						<td class="stuMS_2ndrow"><span id="mobilephone">${memberDTO.mobile}</span></td>
						<td class="stuMS_3throw"><img id="mobileIsOk"
							src="${hasCheckMobile?'images_zp/accept.png':'images_zp/cross.png'}"
							width="16" height="16" alt="" /></td>
						<td class="stuMS_4throw"><a href="javascript:;"
							onclick="checkMobile()" style="display: inline;">验证</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
							href="javascript:;" onclick="modifyMobile()"
							style="display: inline; margin-right: 20px;">修改</a></td>
					</tr>
				</table>
			</div>
			<!--手机号码-->
			<div id="mobilephone_open" class="stuMS_mobilephone">
				<input type="hidden" id="hdMobilePhoneType" value="binding" />
				<form id="checkMobileForm">
					<table style="line-height: 50px;">
						<tr>
							<td style="font-weight: bold; color: #1C79C9; text-align: left;"><span
								class="ml55">验证手机</span></td>
							<td class="stuMS_2ndrow"
								style="font-size: 12px; line-height: 20px; color: #c3c3c3;">验证手机后您可以及时收到企业的通知，也可以提升账号安全性。</td>
						</tr>
						<tr>
							<td style="text-align: right;">手机号码</td>
							<td style="padding-left: 20px;"><input type="text"
								placeholder="请输入手机号码" id="txtMobilephone"
								name="txtMobilephone" class="stuMS_newmail" maxlength="11"
								onkeydown="onlyNum()" /> <br /> <label
								id="txtMobilephone-error" class="error" for="txtMobilephone"
								style=""></label></td>
						</tr>
						<tr>
							<td style="text-align: right;">验证码</td>
							<td style="padding-left: 20px;"><input type="text"
								id="mobileCheckCode" name="mobileCheckCode" placeholder="请输入验证码"
								class="stuMS_writecode" /><input type="button" value="获取验证码"
								class="stuMS_writecodebtn" onclick="getMobileVerfiyCode()" /><br />
								<label id="mobileCheckCode-error" class="error"
								for="mobileCheckCode" style=""></label></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center; font-size: 12px;">如果您没有收到验证码，请电话联系我们：021-12563824</td>
						</tr>
						<tr>
							<td style="text-align: right;"></td>
							<td style="padding-left: 20px;"><input type="button"
								id="btnMobileChkCode" value="确定" class="stuMS_yes"
								onclick="submitMobileFormData()" /><input type="button"
								value="取消" class="stuMS_no" onclick="closeMoblie()" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!--修改密码-->
			<div id="setpswd_close" class="stuMS_setpswd">
				<table>
					<tr>
						<td
							style="font-weight: bold; color: #1C79C9; padding-left: 50px; width: 380px;"><span
							class="ml6">修改密码</span></td>
						<td class="stuMS_2ndrow"></td>
						<td class="stuMS_3throw"></td>
						<td class="stuMS_4throw"><a href="javascript:;"
							onclick="modifyPassword()"
							style="display: inline; margin-right: 20px;">修改</a></td>
					</tr>
				</table>
			</div>
			<!--修改密码-->
			<div id="setpswd_open" class="stuMS_setpswd">
				<form id="pswd_form">
					<table style="line-height: 50px;">
						<tr>
							<td style="font-weight: bold; color: #1C79C9; text-align: left;"><span
								class="ml55">修改密码</span></td>
							<td class="stuMS_2ndrow"
								style="font-size: 12px; line-height: 20px; color: #c3c3c3;">定期修改密码可以提升您的账号安全性。</td>
						</tr>
						<tr>
							<td style="text-align: right;">旧密码</td>
							<td style="padding-left: 20px;"><input type="password"
								placeholder="请输入旧密码" id="oldPassword" name="oldPassword"
								class="stuMS_pswd1" /></td>
						</tr>
						<tr>
							<td style="text-align: right;">新密码</td>
							<td style="padding-left: 20px;"><input type="password"
								placeholder="请输入新密码" id="newPassword" name="newPassword"
								class="stuMS_pswd1" /></td>
						</tr>
						<tr>
							<td style="text-align: right;">新密码确认</td>
							<td style="padding-left: 20px;"><input type="password"
								placeholder="请再次输入新密码" id="newPassword2" name="newPassword2"
								class="stuMS_pswd1" /></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center; font-size: 12px;">如果您修改密码遇到问题，请电话联系我们：021-12563824</td>
						</tr>
						<tr>
							<td style="text-align: right;"></td>
							<td style="padding-left: 20px;"><input type="button"
								id="btnSavePswd" onclick="submitPswdFormData()" value="确定"
								class="stuMS_yes" /><input type="button" value="取消"
								class="stuMS_no" onclick="closePswd()" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>