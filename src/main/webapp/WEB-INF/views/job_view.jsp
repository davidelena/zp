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
<title>查看单个职位（学生）</title>
<style type="text/css">
#send_resume_dialog tr td {
	font-size: medium;
	font-family: Microsoft YaHei;
}
</style>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			initResumeDialog();
			initResumeSuccessDialog();
			initShareCompanyInfoDialog();
		});

		var sendResume = function() {
			$("#send_resume_dialog").dialog("open");
			$("#initResumeSuccessDialog").dialog("open");
		};

		var initResumeDialog = function() {
			$("#send_resume_dialog").dialog({
				autoOpen : false,
				title : "投递简历",
				show : {
					duration : 200
				},
				hide : {
					duration : 200
				},
				resizable : false,
				height : 200,
				width : 500,
				modal : true,
				buttons : {
					"确认" : function() {
						confirmToSend();
					},
					"取消" : function() {
						$("#send_resume_dialog").dialog("close");
					}
				}
			});
		};

		var initResumeSuccessDialog = function() {
			$("#send_resume_success_dialog").dialog({
				autoOpen : false,
				title : "投递简历成功",
				show : {
					duration : 200
				},
				hide : {
					duration : 200
				},
				resizable : false,
				height : 200,
				width : 300,
				modal : true
			});
		};

		var resumePreview = function() {
			var resumeId = $("#selectResume").val();
			window.location.href = "resume/resume_info_view?id=" + $("#hdMemberId").val() + "&rsid=" + resumeId;
		};

		var resumeEdit = function() {
			var resumeId = $("#selectResume").val();
			window.location.href = "resume/online_resume?id=" + $("#hdMemberId").val() + "&rsid=" + resumeId;
		};

		var confirmToSend = function() {
			$.ajax({
				url : "resume/sendResumeInfo",
				type : "post",
				dataType : "json",
				data : {
					memberId : $("#hdMemberId").val(),
					recruitId : $("#hdRecruitId").val(),
					resumeId : $("#selectResume").val()
				},
				success : function(data) {
					if (data.success) {
						$("#send_resume_dialog").dialog("close");
						$("#send_resume_success_dialog").dialog("open");
						setTimeout(function() {
							$("#send_resume_success_dialog").dialog("close");
						}, 1000);
					}
				}
			});
		}
	</script>

	<div id="send_resume_success_dialog"
		style="text-align: center; vertical-align: middle;">简历发送成功！</div>

	<div id="share_companyinfo_dialog"
		style="text-align: center; vertical-align: middle;">
		<img id="share_cp_img" alt="公司主页二维码"
			src="http://7xonvy.com2.z0.glb.qiniucdn.com/logo_cp1-sina_erweima.jpg"
			class="w300 h300" />
	</div>

	<div id="send_resume_dialog">
		<table>
			<tr>
				<td style="width: 30%;">请选择简历:</td>
				<td style="width: 70%;" colspan="3"><select id="selectResume"
					name="selectResume" style="width: 260px;">
						<c:forEach items="${resumeInfos}" var="item">
							<option value="${item.id}">${item.resumeName}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>简历类型：</td>
				<td><select id="selectResumeType" name="selectResumeType"
					style="width: 180px;">
						<option value="中文">中文</option>
						<option value="英文">英文</option>
				</select></td>
				<td><span
					style="margin-left: 6px; display: inline; width: 80px;"><a
						href="javascript:void(0)" onclick="resumePreview()">预览</a></span></td>
				<td><span
					style="margin-left: 6px; display: inline; width: 80px;"><a
						href="javascript:void(0)" onclick="resumeEdit()">编辑</a></span></td>
			</tr>
			<!-- 			<tr style="position: absolute; left: 200px; bottom: 30px;">
				<td colspan="4"><a href="javascript:void(0)"
					onclick="confirmToSend()">确认投递简历</a></td>
			</tr> -->
		</table>
	</div>

	<c:choose>
		<c:when test="${memberDTO.source==1}">
			<%@include file="/WEB-INF/views/sliderbar.jsp"%>
		</c:when>
		<c:otherwise>
			<%@include file="/WEB-INF/views/sliderbar_cp.jsp"%>
		</c:otherwise>
	</c:choose>
	<!--主体-->
	<div class="container">
		<input type="hidden" id="hdRecruitId" value="${recruitInfo.id}" /> <input
			type="hidden" id="hdMemberId" value="${memberDTO.memberId}" />
		<div class="stuSJ_all">
			<div class="stuSJ_center">
				<!--第一块-->
				<div class="stuSJ_content" style="height: 100px;">
					<div class="stuSJ_jobName">
						<p>
						<h3>${recruitInfo.positionName}</h3>
						&nbsp;&nbsp;
						<h6>两天前发布</h6>
						<a class="share" href="javascript:;"><img
							src="images_zp/network-share.png" width="16" height="16" alt="分享" />分享</a>
						<c:if test="${memberDTO.source==1}">
							<!-- 							<a class="collection" href="javascript:void(0)"><img
								src="images_zp/heart_add.png" width="16" height="16" alt="收藏" />收藏</a> -->
						</c:if>
						<a class="alert" href="javascript:void(0)"><img
							src="images_zp/bomb.png" width="16" height="16" alt="举报" />举报</a>
						</p>
						<p id="department">${recruitInfo.departmentName}</p>
					</div>
				</div>
				<!--第二块-->
				<div class="stuSJ_content" style="height: 100px;">
					<div class="stuSJ_basicInfo">
						<p>
						<h4>岗位亮点</h4>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<c:forEach items="${recruitInfo.importantRemarkList}" var="item">
							<span>${item.desc}</span>
						</c:forEach>
						</p>
						<p>
							<br />
						<h4>岗位要求</h4>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<c:if test="${recruitInfo.school>0}">
							<span>${recruitInfo.schoolDesc}</span>
						</c:if>
						<c:if test="${recruitInfo.educational>0}">
							<span>${recruitInfo.educationalDesc}</span>
						</c:if>
						<c:if test="${recruitInfo.major>0}">
							<span>${recruitInfo.majorDesc}</span>
						</c:if>
						<c:if test="${recruitInfo.internshipExp>0}">
							<span>${recruitInfo.intershipExpDesc}</span>
						</c:if>
						</p>
					</div>
				</div>
				<!--第三块-->
				<div class="stuSJ_content" style="height: 500px;">
					<div class="stuSJ_discribe">
						<p>
						<h3>职位描述</h3>
						</p>
						<table>
							<tr>
								<td style="width: 90;" class="stuSJ_left">工作地点</td>
								<td class="stuSJ_right">${recruitInfo.workCityDTO.city}&nbsp;&nbsp;${recruitInfo.workCityDTO.detailAddress}</td>
							</tr>
							<tr>
								<td class="stuSJ_left">月薪</td>
								<td class="stuSJ_right">${recruitInfo.minSalary}~${recruitInfo.maxSalary}</td>
							</tr>
							<tr>
								<td class="stuSJ_left">岗位职责</td>
								<td class="stuSJ_right">${recruitInfo.postDuty}</td>
							</tr>
							<tr>
								<td class="stuSJ_left">其他岗位要求</td>
								<td class="stuSJ_right">${recruitInfo.otherClaim}</td>
							</tr>
							<tr>
								<td class="stuSJ_left">截止日期</td>
								<td class="stuSJ_right">${recruitInfo.validityTimeDesc2}</td>
							</tr>
						</table>
						<c:if test="${memberDTO.source==1}">
							<a href="javascript:void(0)" class="stuSJ_checkOut"
								onclick="sendResume()">投递简历</a>
						</c:if>
					</div>
				</div>
				<!--占位的-->
				<div class="stuSJ_blank"></div>
			</div>
			<!--侧栏-->
			<div class="stuSJ_rightBar">
				<div class="stuSJ_jobLogo">
					<img src="${recruitInfo.companyInfoDTO.logo}" alt="公司logo" />
					<p>${recruitInfo.companyInfoDTO.name}</p>
				</div>
				<div class="stuSJ_scale">
					<p>
						<img src="images_zp/world.png" width="16" height="16"
							alt="hulianw" />${recruitInfo.companyInfoDTO.industryDesc}
					</p>
					<p>
						<img src="images_zp/user_male.png" width="16" height="16"
							alt="renshu" />${recruitInfo.companyInfoDTO.scaleDesc}
					</p>
				</div>
				<div class="stuSJ_jobMap">
					<p>
						公司地址：<br />${recruitInfo.workCityDTO.city}
						${recruitInfo.workCityDTO.detailAddress}
					</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>