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
<title>公司个人账号信息</title>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			$("input[name='memberSex']").each(function(index, item) {
				if ($(item).val() == $("#hdMemberSex").val()) {
					$(item).attr("checked", true);
				}
			});
			initPswForm();
			initMemberForm();
			initPasswordDialog();
		});

		var toStep2 = function() {
			window.location.href = "recruit/company_info_step2?id=" + $("#memberId").val();
		};

		var setMemberSexVal = function() {
			var checkedVal = $("input[name='memberSex']:checked").val();
			$("#hdMemberSex").val(checkedVal);
		};

		var cancelPassword = function() {
			$("#resetPasswordDiv").hide();
		};

		var setMemberSexVal = function() {
			var checkedVal = $("input[name='memberSex']:checked").val();
			$("#hdMemberSex").val(checkedVal);
		};

		var resetPassword = function() {
			$("#reset_password_dialog").dialog("open");
			$("#pswForm").find("input[type='password']").val("");
		};

		var cancelPassword = function() {
			$("#reset_password_dialog").dialog("close");
		};

		var showErrorMsg = function(label, element) {
			label.insertAfter(element).css("margin", "0 auto");
		};

		var initPasswordDialog = function() {
			$("#reset_password_dialog").dialog({
				autoOpen : false,
				title : "重置密码",
				show : {
					duration : 200
				},
				hide : {
					duration : 200
				},
				resizable : false,
				height : 360,
				width : 440,
				modal : true,
				buttons : {
					"确认" : function() {
						resetNewPassword();
					},
					"取消" : function() {
						cancelPassword();
					}
				}
			});
		};

		var resetNewPassword = function() {
			$("#pswForm").submit();
		};

		var confirmToReset = function(form) {

			var memberId = $("#memberId").val();
			var newPassWord = $("#newPassword").val();

			$(form).find("#submit").attr("disabled", true);

			$.ajax({
				type : 'POST',
				data : {
					memberId : memberId,
					password : newPassWord

				},
				url : "member/updatePassword",
				dataType : 'json'
			}).done(function(result) {
				if (result.success) {
					alert("密码修改成功!");
					$("#reset_password_dialog").dialog("close");
				} else {
					$('#beError').text(result.msg).show();
				}
				$(form).find(":submit").attr("disabled", false);
			});
		};

		var initPswForm = function() {
			$("#pswForm").validate({
				rules : {
					oldPassword : {
						required : true,
						remote : {
							type : 'POST',
							url : 'member/checkPassword',
							dataType : "json",
							data : {
								memberId : function() {
									return $("#memberId").val();
								},
								password : function() {
									return $("#oldPassword").val();
								}
							}
						},
						rangelength : [ 6, 16 ]
					},
					newPassword : {
						required : true,
						rangelength : [ 6, 16 ]
					},
					confirmPassword : {
						required : true,
						rangelength : [ 6, 16 ],
						equalTo : "#newPassword"
					}
				},
				messages : {
					oldPassword : {
						required : "请输入原密码",
						remote : "原密码不正确",
						rangelength : "请输入6-16位密码，字母区分大小写"
					},
					newPassword : {
						required : "请输入新密码",
						rangelength : "请输入6-16位密码，字母区分大小写"
					},
					confirmPassword : {
						required : "请再次输入新密码",
						rangelength : "请输入6-16位密码，字母区分大小写",
						equalTo : "两次输入的密码不一致，请重新输入"
					}
				},
				errorPlacement : function(label, element) {
					showErrorMsg(label, element);
				},
				submitHandler : function(form) {
					confirmToReset(form);
				}
			});
		};

		//初始化company member表格
		var initMemberForm = function() {
			$("#companyMemberInfoForm").validate({
				rules : {
					memberName : {
						required : true
					},
					memberCommonEmail : {
						required : true,
						email : true
					}
				},
				messages : {
					memberName : {
						required : "请输入您的姓名"
					},
					memberCommonEmail : {
						required : "请输入接受简历邮箱",
						email : "请输入正确的邮箱格式"
					}
				},
				errorPlacement : function(label, element) {
					showMemberErrorMsg(label, element);
				},
				submitHandler : function(form) {
					confirmCompanyMemberInfo(form);
				}
			});
		};

		var showMemberErrorMsg = function(label, element) {
			label.insertAfter(element);
		};

		//save company step1 info
		var confirmCompanyMemberInfo = function(form) {
			$(form).find("#btnFinish").attr("disabled", true);

			$.ajax({
				url : "recruit/saveCompanyMemberInfo",
				type : "post",
				dataType : "json",
				data : {
					memberId : $("#memberId").val(),
					memberName : $("#memberName").val(),
					memberCommonEmail : $("#memberCommonEmail").val(),
					hdMemberSex : $("#hdMemberSex").val(),
					memberTelephone : $("#memberTelephone").val(),
					memberMobile : $("#memberMobile").val(),
					positionName : $("#positionName").val(),
					memberDemand : $("#memberDemand").val()
				}
			}).done(function(data) {
				if (data.success) {
					$("#memberContact-error").hide();
					window.location.href = "recruit/job_basic_info?id=" + $("#memberId").val() + "&rid=0";
				} else {
					$("#memberContact-error").text(data.message);
					$("#memberContact-error").show();
				}
				$(form).find("#btnFinish").attr("disabled", false);
			});
		};

		var submitCompanyMemberForm = function() {
			$("#companyMemberInfoForm").submit();
		};
	</script>
	<%@include file="/WEB-INF/views/sliderbar_cp.jsp"%>
	<div id="reset_password_dialog">
		<form id="pswForm">
			<table>
				<tr>
					<td>原密码：</td>
					<td><input type="password" name="oldPassword" id="oldPassword"
						tabindex="1" placeholder="请输入旧密码" class="coLGINSUC_mn_border"
						style="border: solid 1px;" /></td>
				</tr>
				<tr>
					<td>新密码：</td>
					<td><input type="password" name="newPassword" id="newPassword"
						tabindex="2" placeholder="请输入新密码" class="coLGINSUC_mn_border" /></td>
				</tr>
				<tr>
					<td>新密码确认：</td>
					<td><input type="password" name="confirmPassword"
						id="confirmPassword" tabindex="3" placeholder="请再次输入新密码"
						class="coLGINSUC_mn_border" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div class="coLGINSUC_body">
		<div class="coLGINSUC_welcome">
			<div class="fix_sliderbar">
				<ul>
					<li><h1>
							欢迎来到第一站！
							<h1></li>
					<li><h5>恭喜您注册成功！请完成个人信息</h5></li>
				</ul>
			</div>
		</div>
		<!--主体-->
		<div class="coLGINSUC_content">
			<input id="memberId" name="memberId" type="hidden"
				value="${memberInfo.memberId}" />
			<form id="companyMemberInfoForm">
				<fieldset>
					<legend>第三步：个人账号信息（必填）</legend>
					<table>
						<tr>
							<td class="coLGINSUC_col1">您的姓名：</td>
							<td><input type="text" id="memberName" name="memberName"
								value="${memberInfo.name}" class="coLGINSUC_mn"
								placeholder="如不便，可只填姓或填英文名" /> <input type="radio"
								name="memberSex" onclick="setMemberSexVal();" value="1"
								style="cursor: pointer;" checked="checked" />男士<input
								type="radio" name="memberSex" onclick="setMemberSexVal();"
								value="2" style="cursor: pointer;" />女士<input type="hidden"
								id="hdMemberSex" name="hdMemberSex" value="${memberInfo.sex}">
								<br /> <label id="memberName-error" class="error"
								for="memberName"></label></td>
						</tr>
						<tr>
							<td class="coLGINSUC_col1">接收简历邮箱：</td>
							<td><input type="text" id="memberCommonEmail"
								name="memberCommonEmail" value="${memberInfo.commonEmail}"
								class="coLGINSUC_mn" /><br /> <label
								id="memberCommonEmail-error" class="error"
								for="memberCommonEmail"></label></td>
						</tr>
						<tr>
							<td class="coLGINSUC_col1">固定电话：</td>
							<td><input type="coLGINSUC_mn2" id="memberTelephone"
								name="memberTelephone" value="${memberInfo.telephone}"
								maxlength="14" class="coLGINSUC_mn"
								placeholder="手机号码与固定电话任意填写一个即可" /></td>
						</tr>
						<tr>
							<td class="coLGINSUC_col1">手机号码：</td>
							<td><input type="text" id="memberMobile" name="memberMobile"
								value="${memberInfo.mobile}" maxlength="11" class="coLGINSUC_mn"
								placeholder="手机号码与固定电话任意填写一个即可" /> <br /> <label
								id="memberContact-error" class="error" for="memberContact"></label></td>
						</tr>
						<tr>
							<td class="coLGINSUC_col1">您的职位：</td>
							<td><input type="text" id="positionName" name="positionName"
								value="${memberInfo.position}" class="coLGINSUC_mn"
								placeholder="可以选填您在公司工作的部门、职位等" /></td>
						</tr>
						<tr>
							<td class="coLGINSUC_col1"><span>招聘需求设置：</span></td>
							<td><select id="memberDemand" name="memberDemand"
								class="coLGINSUC_mn"><option value="-1">请选择招聘需求</option>
									<c:forEach items="${recruitDemandEnums}" var="item">
										<option value="${item.code}"
											${item.code==memberInfo.demand?"selected=selected":""}>${item.desc}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td class="coLGINSUC_col1"><span>重置密码：</span></td>
							<td><input type="button" id="btnResetPassword"
								value="点击重置密码" class="coLGINSUC_special"
								onclick="resetPassword();" /></td>
						</tr>
					</table>
				</fieldset>
				<div class="coLGINSUC3_bottom" style="width: 230px;">
					<input type="button" value="上一步" onclick="toStep2()"
						class="coLGINSUC_save" /> <input id="btnFinish" type="button"
						value="完成，去发布职位" onclick="submitCompanyMemberForm()"
						class="coLGINSUC_save" />
				</div>
			</form>
		</div>
	</div>
	<!--底部-->
	<div class="coLGINSUC_footer">
		第一站的微信号是：zhancampus，欢迎关注！<br />有问题？您可以电话联系我们：021-21458392；或致信邮箱us@zhancampus.com
	</div>
</body>
</html>