<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include_zp.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="keywords" content="校园招聘，校招，宣讲会，招聘会，大学生，优质招聘，优质信息，实习，第一站，精准推荐" />
<meta name="description" content="第一站zhancampus.com为大学生推荐最新、最优质的校招和实习信息，帮助大学生迈出职场第一步！" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>通知面试</title>
<style type="text/css">
html,body {
	height: 100%;
}

.coNI_content table tr td {
	margin-left: 10px;
}

.wd260 {
	width: 180px;
}
</style>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			$("#interviewDate").datepicker();
			initInformInterviewForm();
		});

		var backToMyApplicant = function() {
			window.location.href = "recruit/my_applicant?id=" + $("#hdPubMemberId").val();
		};

		var initInformInterviewForm = function() {
			$("#interviewForm").validate({
				rules : {
					interviewDate : {
						required : true
					},
					interviewTime : {
						required : true
					},
					interviewAddress : {
						required : true
					}
				},
				messages : {
					interviewDate : {
						required : "请选择面试日期",
					},
					interviewTime : {
						required : "请输入具体面试时间"
					},
					interviewAddress : {
						required : "请输入面试地点"
					}
				},
				errorPlacement : function(label, element) {
					showErrorMsg(label, element);
				},
				submitHandler : function(form) {
					saveInformInterviewData(form);
				}
			});
		};

		var showErrorMsg = function(label, element) {
			label.insertAfter(element).css("margin-left", "5px");
		};

		var saveInformInterviewData = function(form) {
			var recruitId = $("#hdRecruitId").val();
			var hdPubMemberId = $("#hdPubMemberId").val();
			var interviewMemberId = $("#hdMemberId").val();
			var interviewMemberName = $("#interviewMemberName").text();
			var interviewMemberMail = $("#interviewMemberMail").text();
			var interviewDate = $("#interviewDate").val();
			var interviewTime = $("#interviewTime").val();
			var interviewAddress = $("#interviewAddress").val();
			var interviewContactPerson = $("#interviewContactPerson").val();
			var interviewContactPhone = $("#interviewContactPhone").val();
			var interviewContent = $("#interviewContent").val();

			$(form).find("#btnSave").attr("disabled", true);

			$.ajax({
				url : "recruit/saveInformInterviewData",
				type : 'POST',
				data : {
					recruitId : recruitId,
					hdPubMemberId : hdPubMemberId,
					interviewMemberId : interviewMemberId,
					interviewMemberName : interviewMemberName,
					interviewMemberMail : interviewMemberMail,
					interviewDate : interviewDate,
					interviewTime : interviewTime,
					interviewAddress : interviewAddress,
					interviewContactPerson : interviewContactPerson,
					interviewContactPhone : interviewContactPhone,
					interviewContent : interviewContent
				},
				dataType : 'json'
			}).done(function(result) {
				if (result.success) {
					window.location.href = result.returnUrl;
				}

				$(form).find("#btnSave").attr("disabled", false);
			});
		};

		var submitFormData = function() {
			$("#interviewTime").focus();
			$("#interviewForm").submit();
		};
		
	</script>
	<div class="coNI_content">
		<h4>通知面试</h4>
		<form id="interviewForm">
			<input type="hidden" id="hdMemberId" value="${informInterviewDTO.memberId}" /> <input type="hidden"
				id="hdPubMemberId" value="${memberId}" /> <input type="hidden" id="hdRecruitId" value="${recruitInfoDTO.id}" />
			<table style="margin: 0 auto;">
				<tr>
					<td class="coNI_left">收件人</td>
					<td class="coNI_right"><span id="interviewMemberName">${informInterviewDTO.memberName}</span></td>
				</tr>
				<tr>
					<td class="coNI_left">面试者邮箱</td>
					<td class="coNI_right"><span id="interviewMemberMail">${informInterviewDTO.memberEmail}</span></td>
				</tr>
				<tr>
					<td class="coNI_left">*面试日期</td>
					<td class="coNI_right"><input type="text" id="interviewDate" name="interviewDate" value="" readonly="readonly"
						class="wd260" /></td>
				</tr>
				<tr>
					<td class="coNI_left" style="">*具体面试时间</td>
					<td class="coNI_right"><input type="text" id="interviewTime" name="interviewTime" class="wd260"
						placeholder="请输入具体几点安排面试" maxlength="150" /></td>
				</tr>
				<tr>
					<td class="coNI_left">*面试地点</td>
					<td class="coNI_right"><input type="text" id="interviewAddress" name="interviewAddress" class="wd260"
						placeholder="如：上海市杨浦区XX路……" maxlength="150" /></td>
				</tr>
				<tr>
					<td class="coNI_left">联系人</td>
					<td class="coNI_right"><input type="text" id="interviewContactPerson" name="interviewContactPerson"
						class="wd260" maxlength="50" /></td>
				</tr>
				<tr>
					<td class="coNI_left">电话</td>
					<td class="coNI_right"><input type="text" id="interviewContactPhone" name="interviewContactPhone"
						maxlength="11" onkeydown="onlyNum()" class="wd260" /></td>
				</tr>
				<tr>
					<td class="coNI_left">通知面试内容</td>
					<td class="coNI_right"><textarea rows="10" cols="30" id="interviewContent" name="interviewContent"
							placeholder="您可以简要介绍岗位的工作内容。300字以内。请勿输入公司邮箱、联系电话，系统将自动删除，敬请谅解。" style="width: 300px;"></textarea><a
						href="javascript:;" style="display: inline; resize: none;" onkeydown="checkTextarea()"></a></td>
				</tr>
				<tr>
					<td class="coNI_left"></td>
					<td class="coNI_right"><input type="button" id="btnSave" value="确定" class="coNI_yes"
						onclick="submitFormData()" /><input type="button" value="取消" class="coNI_no" onclick="backToMyApplicant()" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>