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
<title>注册成功</title>
<title>学生填写基本信息</title>
</head>
<body
	style="background: rgba(0, 0, 0, 0) -moz-linear-gradient(center top, #c9d9e6, #014d88) repeat scroll 0 0">
	<!-- 1445836364841 -->
	<script type="text/javascript" src="javascript/common_dialog.js"></script>
	<script type="text/javascript">
		$(function() {
			initUniversityDialog();
			initFormValidation();
		});

		var initFormValidation = function() {
			$("#memberBasicForm").validate({
				rules : {
					memberName : {
						required : true
					},
					memberSchool : {
						required : true
					},
					memberCommonEmail : {
						required : true,
						email : true
					},
					memberMobile : {
						required : true,
						remote : {
							type : 'POST',
							url : 'resume/isMobileValid',
							dataType : "json",
							data : {
								mobile : function() {
									return $("#memberMobile").val();
								}
							}
						}
					}
				},
				messages : {
					memberName : {
						required : "请输入您的姓名",

					},
					memberSchool : {
						required : "请选择您的学校"
					},
					memberCommonEmail : {
						required : "请输入您的常用邮箱",
						email : "请输入正确的邮箱格式"
					},
					memberMobile : {
						required : "请输入您的手机号码",
						remote : "手机格式不正确"
					}
				},
				errorPlacement : function(label, element) {
					showErrorMsg(label, element);
				},
				submitHandler : function(form) {
					dofillBasicInfo(form);
				}
			});
		};

		var showErrorMsg = function(label, element) {
			var id = $(element).attr("id");
			if (id == "memberName") {
				label.insertAfter(element).css("position", "absolute").css("top", "153px").css("margin-left", "20px");
			} else if (id == "memberSchool") {
				label.insertAfter(element).css("position", "absolute").css("top", "210px").css("margin-left", "20px");
			} else if (id == "memberCommonEmail") {
				label.insertAfter($(element).parent()).css("position", "absolute").css("top", "267px").css(
						"margin-left", "42px");
			} else if (id == "memberMobile") {
				label.insertAfter($(element).parent()).css("position", "absolute").css("top", "326px").css(
						"margin-left", "42px");
			}

		};

		var dofillBasicInfo = function(form) {
			var memberId = $('#memberId').val();
			var memberName = $('#memberName').val();
			var memberSchoolId = $("#memberSchoolId").val();
			var memberCommonEmail = $("#memberCommonEmail").val();
			var memberMobile = $("#memberMobile").val();

			$(form).find("#btnFinishToResume").attr("disabled", true);

			$.ajax({
				url : "resume/fillBasicInfo",
				type : 'POST',
				data : {
					memberId : memberId,
					memberName : memberName,
					memberSchoolId : memberSchoolId,
					memberCommonEmail : memberCommonEmail,
					memberMobile : memberMobile
				},
				dataType : 'json'
			}).done(function(result) {
				if (result.success) {
					window.location.href = result.return_url;
				}

				$(form).find("#btnFinishToResume").attr("disabled", false);
			});
		};

		var fillBasicInfo = function() {
			$("#memberBasicForm").submit();
		};
	</script>
	<%@include file="/WEB-INF/views/sliderbar.jsp"%>
	<%@include file="/WEB-INF/views/common_university_include.jsp"%>
	<div class="stuLS_welcome">
		<div class="fix_sliderbar">
			<ul>
				<li><h1>欢迎来到第一站！</h1></li>
				<li><h5>
						恭喜您注册成功！验证邮件已发送
						<!--至某个邮箱地址-->
						，请您前往完成验证！
					</h5></li>
			</ul>
		</div>
		<div class="stuLS_box">
			<div class="stuLS_container">
				<p class="stuLS_basicInfo">基本信息</p>
				<p class="stuLS_no">未经您授权，他人无法查看您的联系方式，请放心填写</p>
				<form id="memberBasicForm">
					<input id="memberId" name="memberId" type="hidden"
						value="${model.memberId}" /> <input id="hdIsSearch"
						name="hdIsSearch" type="hidden" value="0" />
					<div class="stuLS_input">
						<input type="text" id="memberName" name="memberName"
							value="${model.name}" placeholder="请输入您的姓名" class="inputText" />
						<br /> <label id="memberName-error" class="error"
							for="memberName"
							style="position: absolute; top: 153px; margin-left: 20px;"></label>
						<input type="text" id="memberSchool" name="memberSchool"
							onclick="showUniversityDialog()" value="${model.schoolDesc}"
							placeholder="请选择您的学校" class="inputText" readonly="readonly" /><input
							type="hidden" id="memberSchoolId" name="memberSchoolId"
							value="${model.schoolId}" /><br /> <label
							id="memberSchool-error" class="error" for="memberSchool"
							style="position: absolute; top: 210px; margin-left: 20px;"></label>
						<input type="text" id="memberCommonEmail" name="memberCommonEmail"
							placeholder="请输入您的常用邮箱" value="${model.commonEmail}"
							class="inputText" /> <br /> <input type="text"
							id="memberMobile" placeholder="请输入您的手机号" name="memberMobile"
							value="${model.mobile}" class="inputText" />
					</div>
					<div class="stuLS_button">
						<a href="javascript:void(0)" id="btnFinishToResume"
							onclick="fillBasicInfo();" class="stuLS_signBotton">下一步：继续完善简历信息</a>
					</div>
				</form>
				<p class="stuLS_goOut">
					<a href="member/search_job_list">暂不完善简历，保存基本信息，去看看热门职位</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>