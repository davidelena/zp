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
<title>在线填写简历</title>
</head>
<body>
	<script src="javascript/resume_info_new.js"></script>
	<script type="text/javascript">
		var totalSocialNet = 0;
		var totalSpecialtySkillEn = 0;
		var totalSpecialtySkillOther = 0;
		var totalSpecialtySkillComputer = 0;
		var totalSpecialtySkillCert = 0;

		$(function() {
			initPage();
		});

		var initPage = function() {
			initResumeNamePage();
			initBasicInfoPage();
			initResumeEduExpPage();
			initResumeWorkExpPage();
			initResumeActivityExpPage();
			initResumeProjectExpPage();
			initResumeHobbySpecialPage();
			initResumePrizeInfoPage();
			initResumeSocialNetPage();
			initResumeOpusInfoPage();
			initResumeCustomPage();

			getComputerSkillDropdownHtml();
			totalSocialNet = $("#hdResumeSocialNetSize").val();
			totalSpecialtySkillEn = $("#resumeSpecialtySkillEnSize").val();
			totalSpecialtySkillOther = $("#resumeSpecialtySkillOtherSize")
					.val();
			totalSpecialtySkillComputer = $("#resumeSpecialtySkillComputerSize")
					.val();
			totalSpecialtySkillCert = $("#resumeSpecialtySkillCertSize").val();

			initUploadResumeInfo();
			initSpecialSkillPage();

		};
	</script>

	<div id="upload_resume_dialog">
		<form id="upload_resume_form" method="post"
			enctype="multipart/form-data">
			<table class="fontsize14">
				<tr>
					<td>简历类型：</td>
					<td><input type="file" id="resumeAttachment"
						name="resumeAttachment" /></td>
				</tr>
				<tr style="height: 10px;">
					<td colspan="2"></td>
				</tr>
				<tr>
					<td colspan="2"><span class="attachment_info">支持jpg，doc，docx，pdf，txt格式，附件大小不得超过5M</span>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dialog_school" class="none">
		<div id="school_geo" style="display: block;">
			<table id="schoolTable" class="tableTd" style="width: 100%;"
				border="1" cellspacing="1" cellpadding="0">
				<c:forEach items="${geoAreaList}" var="geoArea" varStatus="item">
					<c:if test="${item.index%4==0}">
						<tr>
					</c:if>
					<td><input type="radio" name="chkUniversityGeo"
						value="${geoArea.id}" desc="${geoArea.name}"
						onclick="showSchoolDetailDiv();"
						style="margin-left: 6px; cursor: pointer;" />${geoArea.name}</td>
					<c:if test="${(item.index+1)%4==0}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
		<div id="school_detail"></div>
	</div>
	<%@include file="/WEB-INF/views/sliderbar.jsp"%>
	<div class="container">
		<h1 class="title">在线填写简历</h1>
		<div class="all">
			<div class="center">
				<!-- 简历名称start -->
				<div id="resume_name_view" class="content">
					<div class="stuOR_resumeName">
						<h4>
							<span id="spanResumeName">${resumeInfo.resumeName}</span>
						</h4>
						<img src="images_zp/pencil.png" alt="编辑"> <a
							href="javascript:;" onclick="showResumeNameEdit()">编辑简历名称</a>
					</div>
				</div>
				<div id="resume_name_edit" class="content">
					<form id="resumeNameForm">
						<input type="hidden" id="hdMemberId" name="hdMemberId"
							value="${memberInfo.memberId}" /> <input type="hidden"
							id="hdResumeId" name="hdResumeId" value="${resumeInfo.id}" />

						<div class="resumeName">
							<input type="text" placeholder="请输入简历名称" id="resumeName"
								name="resumeName" value="${resumeInfo.resumeName}" /> <a
								id="btnResumeName" href="javascript:;" class="changesave"
								onclick="submitResumeNameForm()">保存</a> <a href="javascript:;"
								class="changecancel" onclick="hideResumeNameEdit()">取消</a>
						</div>
					</form>
				</div>

				<!-- 简历基本信息start -->
				<div id="resume_basicinfo_view" class="content"
					style="min-height: 260px; height: auto;">
					<div class="stuOR_basicInfo">
						<p>基本信息</p>
						<img src="images_zp/pencil.png" alt="编辑" class="pencil"> <a
							href="javascript:;" onclick="showBasicEdit()">编辑基本信息</a>
						<div class="text">
							<h1>
								<span id="memberNameView">${memberInfo.name}</span>
							</h1>
							<ul>
								<li><img src="images_zp/house.png" alt="学校"><span
									id="memberSchoolView">${memberInfo.schoolId>0?memberInfo.schoolDesc:memberInfo.school}</span></li>
								<li><img src="images_zp/phone.png" alt="手机"><span
									id="memberMobileView">${memberInfo.mobile}</span></li>
								<li><img src="images_zp/mail.png" alt="邮箱"><span
									id="memberEmailView">${memberInfo.commonEmail}</span></li>
							</ul>
							<div class="pic">
								<img id="basicinfo_headpic_view" src="${resumeInfo.avatar}" />
							</div>
						</div>
					</div>
				</div>
				<div id="resume_basicinfo_edit" class="content"
					style="min-height: 300px; height: auto;">
					<div class="basicInfo">
						<p>基本信息</p>
						<a href="javascript:" class="changesave" id="btnBasicSave"
							onclick="submitResumeBasicInfoForm()">保存</a> <a
							href="javascript:;" class="changecancel"
							onclick="hideBasicEdit()">取消</a>
						<div class="text" style="width: 600px; height: 250px;">
							<form id="resumeBasicInfoForm">
								<input type="hidden" id="hdBasicMemberId" name="hdBasicMemberId"
									value="${memberInfo.memberId}" /> <input type="hidden"
									id="hdBasicResumeId" name="hdBasicResumeId"
									value="${resumeInfo.id}" /> <input type="text"
									placeholder="请输入您的姓名" id="resumeMemberName"
									name="resumeMemberName" class="myn" value="${memberInfo.name}" />
								<ul>
									<li><img src="images_zp/gender.png" alt="性别"><select
										id="resumeGender" name="resumeGender" class="mn">
											<c:forEach items="${genderEnums}" var="item">
												<option value="${item.code}"
													${item.code==resumeInfo.sex?"selected=selected":""}>${item.desc}</option>
											</c:forEach>
									</select></li>

									<li class="row"><img src="images_zp/phone.png" alt="手机"><input
										maxlength="11" type="text" placeholder="请输入您的手机号码"
										id="resumeMobile" name="resumeMobile" class="mn"
										value="${memberInfo.mobile}" /><br /> <label
										id="resumeMobile-error" class="error" for="resumeMobile"
										style="position: relative; left: 22px; top: -15px;"></label></li>

									<li><img src="images_zp/mail.png" alt="邮箱"><input
										type="text" placeholder="请输入您的常用邮箱" id="resumeEmail"
										name="resumeEmail" value="${memberInfo.commonEmail}"
										maxlength="50" class="mn" /><br /> <label
										id="resumeEmail-error" class="error" for="resumeEmail"
										style="position: relative; left: 240px; top: -15px;"></label></li>

									<li class="row"><img src="images_zp/house.png" alt="学校"><input
										type="text" placeholder="请输入您的常用邮箱" id="resumeSchool"
										name="resumeSchool" value="${memberInfo.schoolDesc}"
										onclick="showUniversityDialog()" readonly="readonly"
										class="mn" /><input type="hidden" id="hdResumeSchoolId"
										name="hdResumeSchoolId" value="${memberInfo.schoolId}" /> <br />
										<label id="resumeSchool-error" class="error"
										for="resumeSchool"
										style="position: relative; left: 22px; top: -15px;"></label></li>

									<li><img src="images_zp/major.png" alt="专业"><input
										type="text" placeholder="请输入您的专业" id="resumeMajor"
										name="resumeMajor" value="${resumeInfo.major}" class="mn" /></li>

									<li class="row"><img src="images_zp/highest education.png"
										alt="最高学历"><select id="resumeDiploma"
										name="resumeDiploma" class="mn">
											<c:forEach items="${educationEnums}" var="item">
												<option value="${item.code}"
													${item.code==resumeInfo.diploma?"selected=selected":""}>${item.desc}</option>
											</c:forEach>
									</select></li>

									<li><img src="images_zp/graduate.png" alt="毕业年份"> <input
										type="text" placeholder="毕业年份" id="resumeGraduationTime"
										name="resumeGraduationTime" readonly="readonly"
										value="${resumeInfo.graduationTimeDesc}" class="mn time" /></li>
								</ul>
							</form>
							<div class="picT">
								<div
									style="display: none; background-color: rgb(231, 231, 231);">
									<span>上传自己的头像</span>
								</div>
								<div style="display: block;">
									<img id="basicinfo_headpic" width="120" height="120"
										src="${resumeInfo.avatar}"> <span>更换头像</span>
								</div>
								<form id="basic_info_headpic_form" enctype="multipart/form-data"
									style="width: 200px;">
									<input id="headPicFile" name="headPicFile" type="file"
										onchange="img_check(this,'h/resume/uploadPhoto.json','headPic');"
										title="支持jpg、jpeg、gif、png格式，文件小于5M" value=""> <br />
									<input type="button" id="btnOpusUpload"
										onclick="uploadHeadPic();" value="上传"
										style="margin-left: 1px;"> <br /> <em
										style="width: 100%"> 尺寸：120*120px <br> 大小：小于5M
									</em> <br /> <span class="error" id="headPic_error"
										style="display: none; width: 150px;"></span>
								</form>
							</div>
						</div>
					</div>
				</div>

				<!--简历教育经历start-->
				<input type="hidden" id="hdEduEditId" value="0" /> <input
					type="hidden" id="hdResumeEducationExpSize"
					value="${resumeEducationExpSize}" />
				<div class="content">
					<div id="resume_neweduexp_div" class="content"
						style="height: 260px; display: none;">
						<div class="stuOR_education">
							<p>教育经历</p>
							<a id="aLinkEduAddNew" onclick="addResumeEducationExp()"
								href="javascript:;" class="add">+添加教育经历</a>
						</div>
					</div>
					<div id="resume_educationexp_view_module" class="stuMRF_education"
						style="height: auto; min-height: 150px;">
						<p>教育经历</p>
						<ul id="resume_educationexp_view" class="font_normal">
							<c:forEach items="${resumeInfo.resumeEducationExpDTOs}"
								var="item">
								<li id="edu_exp_${item.id}"><a href="javascript:void(0)"
									id="aLinkEdit_${item.id}" class="aLinkEdit"
									onclick="editResumeEduExp('${item.id}')">编辑</a> <a
									href="javascript:void(0)" id="aLinkDelete_${item.id}"
									class="aLinkDelete ml6"
									onclick="deleteResumeEduExp('${item.id}')">删除</a> <input
									type="hidden" id="edu_exp_detail_${item.id}"
									diploma="${item.diploma}" diplomaDesc="${item.diplomaDesc}"
									school="${item.school}" major="${item.major}"
									majorType="${item.majorType}"
									majorTypeDesc="${item.majorTypeDesc}"
									academicStartsDesc="${item.academicStartsDesc}"
									graduationTimeDesc="${item.graduationTimeDesc}"
									scoreTop="${item.scoreTop}" scoreTopDesc="${item.scoreTopDesc}"
									value="${item.id}" />
									<div>${item.school}<span class="mr3"></span>${item.academicStartsDesc}~${item.graduationTimeDesc}<span
											class="mr3"></span>${item.diplomaDesc}<span class="mr3"></span>${item.major}
										<span class="mr3"></span>${item.majorTypeDesc} <span
											class="mr3"></span>${item.scoreTopDesc}
									</div>
									<hr style="width: auto; border-color: #9c9c9c;" /></li>
							</c:forEach>
						</ul>

						<%-- <ul id="resume_educationexp_view" style="font-size: 14px;">
							<c:forEach items="${resumeInfo.resumeEducationExpDTOs}"
								var="item" varStatus="indexItem">
								<div id="edu_exp_${item.id}" class="div_boder">
									<a href="javascript:void(0)" id="aLinkEdit_${item.id}"
										class="aLinkEdit" onclick="editResumeEduExp('${item.id}')">编辑</a>
									<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
										class="aLinkDelete ml6"
										onclick="deleteResumeEduExp('${item.id}')">删除</a> <input
										type="hidden" id="edu_exp_detail_${item.id}"
										diploma="${item.diploma}" diplomaDesc="${item.diplomaDesc}"
										school="${item.school}" major="${item.major}"
										majorType="${item.majorType}"
										majorTypeDesc="${item.majorTypeDesc}"
										academicStartsDesc="${item.academicStartsDesc}"
										graduationTimeDesc="${item.graduationTimeDesc}"
										scoreTop="${item.scoreTop}"
										scoreTopDesc="${item.scoreTopDesc}" value="${item.id}" /> <span>学历/学位：${item.diplomaDesc}</span><br />
									<span>学校名称：${item.school}</span><br /> <span>专业名称：${item.major}</span><br />
									<span>专业分类：${item.majorTypeDesc}</span><input type="hidden"
										id="edu_exp_majorType_${item.id}" value="${item.majorType}"
										desc="${item.majorTypeDesc}" /><br /> <span>入学时间：${item.academicStartsDesc}</span><br />
									<span>毕业时间：${item.graduationTimeDesc}</span><br /> <span>成绩排名：${item.scoreTopDesc}</span>
									<hr style="width: auto; border-color: #9c9c9c;" />
								</div>

							</c:forEach>
						</ul> --%>
						<div id="aLinkEduAddDiv" style="display: block; height: 50px;">
							<a id="aLinkEduAdd" href="javascript:;"
								onclick="addResumeEducationExp()" class="aLinkAdd"
								style="right: 300px; position: relative; top: 5px;"><img
								src="images_zp/add_32.png" />添加一项教育经历</a>
						</div>
					</div>
				</div>

				<div id="resume_educationexp_edit_module" class="content"
					style="height: 285px;">
					<form id="resumeEduExpForm">
						<div class="education">
							<p id="p_educationexp_title">教育经历</p>
							<a href="javascript:;" onclick="submitResumeEduExpForm()"
								id="btnResumeEduExp" class="changesave">保存</a> <a
								href="javascript:;" onclick="cancelResumeEduExp()"
								class="changecancel">取消</a>
							<p></p>
							<ul class="editStyle height220 mt5">
								<li><img src="images_zp/highest education.png" alt="最高学历"><select
									id="resumeEduExpDiploma" name="resumeEduExpDiploma" class="mn">
										<c:forEach items="${educationEnums}" var="item">
											<option value="${item.code}">${item.desc}</option>
										</c:forEach>
								</select></li>

								<li class="row"><img src="images_zp/house.png" alt="学校"><input
									type="text" placeholder="请选择你的学校" id="resumeEduExpSchool"
									name="resumeEduExpSchool" class="mn" /> <label
									id="resumeEduExpSchool-error" class="error"
									for="resumeEduExpSchool" style="margin-left: 5px;"></label></li>

								<li><img src="images_zp/major.png" alt="专业"><input
									type="text" placeholder="请输入您的专业" id="resumeEduExpMajor"
									name="resumeEduExpMajor" class="mn" /><label
									id="resumeEduExpMajor-error" class="error"
									for="resumeEduExpMajor" style="margin-left: 8px;"></label></li>

								<li class="row"><img src="images_zp/major.png" alt="专业分类"><select
									id="resumeEduExpMajorType" name="resumeEduExpMajorType"
									class="mn">
										<c:forEach items="${specialityEnums}" var="item">
											<option value="${item.code}">${item.desc}</option>
										</c:forEach>
								</select></li>

								<li><img src="images_zp/highest education.png" alt="成绩排名"><select
									id="resumeEduExpScoreTop" name="resumeEduExpScoreTop"
									class="mn">
										<c:forEach items="${achievementEnums}" var="item">
											<option value="${item.code}">${item.desc}</option>
										</c:forEach>
								</select></li>

								<li class="row"><img src="images_zp/graduate.png"
									alt="入学时间">（入学）<input type="text"
									id="resumeEduExpAcademicStarts"
									name="resumeEduExpAcademicStarts" placeholder="入学时间" value=""
									class="mn txt" readonly="readonly" /><br /> <label
									id="resumeEduExpAcademicStarts-error" class="error"
									for="resumeEduExpAcademicStarts"
									style="position: relative; left: 85px; top: -7px;"></label></li>

								<li><img src="images_zp/graduate.png" alt="毕业年份">（毕业）<input
									type="text" id="resumeEduExpGraduationTime"
									name="resumeEduExpGraduationTime" placeholder="毕业时间" value=""
									class="mn txt" readonly="readonly" /><br /> <label
									id="resumeEduExpGraduationTime-error" class="error"
									for="resumeEduExpGraduationTime"
									style="position: relative; left: 86px; top: -9px;"></label></li>
							</ul>
						</div>
					</form>
				</div>

				<!--简历工作经历start-->
				<input type="hidden" id="hdWorkEditId" value="0" /><input
					type="hidden" id="hdResumeWorkExpSize" value="${resumeWorkExpSize}" />
				<div class="content">
					<div id="resume_newworkexp_div" class="content"
						style="height: 260px; display: none;"">
						<div class="stuOR_job">
							<p>实习/工作经历</p>
							<a id="aLinkWorkAddNew" onclick="addResumeWorkExp()"
								href="javascript:;" class="add">+添加实习/工作经历</a>
						</div>
					</div>
					<div id="resume_workexp_view_module" class="stuMRF_job"
						style="min-height: 250px; height: auto;">
						<p>实习/工作经历</p>
						<ul id="resume_workexp_view" style="font-size: 14px;">
							<c:forEach items="${resumeInfo.resumeWorkExpDTOs}" var="item">
								<div id="work_exp_${item.id}" class="div_boder">
									<a href="javascript:void(0)" id="aLinkEdit_${item.id}"
										class="aLinkEdit" onclick="editResumeWorkExp('${item.id}')">编辑</a>
									<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
										class="aLinkDelete ml6"
										onclick="deleteResumeWorkExp('${item.id}')">删除</a> <input
										type="hidden" id="work_exp_detail_${item.id}"
										companyName="${item.companyName}"
										positionName="${item.positionName}"
										startTimeDesc="${item.startTimeDesc}"
										endTimeDesc="${item.endTimeDesc}"
										workDescription="${item.workDescription}" value="${item.id}" />
									<span>公司名称：${item.companyName}</span><br /> <span>职位名称：${item.positionName}</span><br />
									<span>开始时间：${item.startTimeDesc}</span><br /> <span>结束时间：${item.endTimeDesc}</span><br />
									<span>工作描述：
										<div>${item.workDescription}</div>
									</span>
									<hr style="width: auto; border-color: #9c9c9c;" />
								</div>
							</c:forEach>
						</ul>
						<div id="aLinkWorkAddDiv" style="display: block; height: 50px;">
							<a id="aLinkWorkAdd" href="javascript:;"
								onclick="addResumeWorkExp()" class="aLinkAdd"
								style="right: 300px; position: relative; top: 5px;"><img
								src="images_zp/add_32.png" />添加一项实习/工作经历</a>
						</div>
					</div>
				</div>

				<div id="resume_workexp_edit_module" class="content"
					style="min-height: 300px; height: auto; margin-top: 0px;">
					<div class="job">
						<p id="p_workexp_title" class="none">实习/工作经历</p>
						<a href="javascript:;" onclick="submitResumeWorkExpForm()"
							id="btnResumeWorkExp" class="changesave">保存</a> <a id="btnCancel"
							href="javascript:;" onclick="cancelResumeWorkExp()"
							class="changecancel">取消</a>
						<p></p>
						<form id="resumeWorkExpForm">
							<ul class="editStyle" style="margin-top: 10px;">
								<li class="row"><img src="images_zp/major.png" alt="专业"><input
									type="text" placeholder="请输入公司名称" id="resumeWorkExpCompanyName"
									name="resumeWorkExpCompanyName" class="mn" /> <label
									id="resumeWorkExpCompanyName-error" class="error"
									for="resumeWorkExpCompanyName" style="margin-left: 3px;"></label></li>

								<li><img src="images_zp/major.png" alt="专业"><input
									type="text" placeholder="请输入职位名称"
									id="resumeWorkExpPositionName" name="resumeWorkExpPositionName"
									class="mn" /><label id="resumeWorkExpPositionName-error"
									class="error" for="resumeWorkExpPositionName"
									style="margin-left: 5px;"></label></li>

								<li class="row"><img src="images_zp/graduate.png"
									alt="起始时间">（起始）<input type="text"
									id="resumeWorkExpStartTime" name="resumeWorkExpStartTime"
									placeholder="起始时间" value="" class="mn txt" readonly="readonly" />
									<br /> <label id="resumeWorkExpStartTime-error" class="error"
									for="resumeWorkExpStartTime"
									style="position: relative; left: 85px; top: -7px;"></label></li>

								<li><img src="images_zp/graduate.png" alt="结束时间">（结束）<input
									type="text" id="resumeWorkExpEndTime"
									name="resumeWorkExpEndTime" placeholder="结束时间" value=""
									class="mn txt" readonly="readonly" /><br /> <label
									id="resumeWorkExpEndTime-error" class="error"
									for="resumeWorkExpEndTime"
									style="position: relative; left: 86px; top: -9px;"></label></li>

								<li><textarea id="resumeWorkExpDesc" rows="5" cols="70"
										class="ml6 mb6"
										placeholder="请您描述这段经历中，您的作用与积极影响，可以参照SMART原则等，也可查看下方范例。如果您还没有想好，可以先不填写。（限500字）"></textarea></li>
							</ul>
						</form>
					</div>

				</div>

				<!-- 校园活动经历 start -->
				<input type="hidden" id="hdActivityEditId" value="0" /><input
					type="hidden" id="hdResumeActivityExpSize"
					value="${resumeActivityExpSize}" />
				<div class="content">
					<div id="resume_activityexp_view_module" class="stuMRF_campus"
						style="min-height: 250px; height: auto;">
						<p>校园活动经历</p>
						<ul id="resume_activityexp_view" style="font-size: 14px;">
							<c:forEach items="${resumeInfo.resumeActivityExpDTOs}" var="item">
								<div id="activity_exp_${item.id}" class="div_boder">
									<a href="javascript:void(0)" id="aLinkEdit_${item.id}"
										class="aLinkEdit"
										onclick="editResumeActivityExp('${item.id}')">编辑</a> <a
										href="javascript:void(0)" id="aLinkDelete_${item.id}"
										class="aLinkDelete ml6"
										onclick="deleteResumeActivityExp('${item.id}')">删除</a> <input
										type="hidden" id="activity_exp_detail_${item.id}"
										activityName="${item.activityName}"
										positionName="${item.positionName}"
										startTimeDesc="${item.startTimeDesc}"
										endTimeDesc="${item.endTimeDesc}"
										activityDesc="${item.activityDesc}" value="${item.id}" /> <span>组织或活动名称：${item.activityName}</span><br />
									<span>职位名称：${item.positionName}</span><br /> <span>开始时间：${item.startTimeDesc}</span><br />
									<span>结束时间：${item.endTimeDesc}</span><br /> <span>工作描述：
										<div>${item.activityDesc}</div>
									</span>
									<hr style="width: auto; border-color: #9c9c9c;" />
								</div>
							</c:forEach>
						</ul>
						<div id="aLinkActivityAddDiv"
							style="display: block; height: 50px;">
							<a id="aLinkActivityAdd" href="javascript:;"
								onclick="addResumeActivityExp()" class="aLinkAdd"
								style="right: 300px; position: relative; top: 5px;"><img
								src="images_zp/add_32.png" />添加校园活动经历</a>
						</div>
					</div>
				</div>

				<div id="resume_activityexp_edit_module" class="content"
					style="min-height: 265px; height: auto; margin-top: 0px; padding-bottom: 10px;">
					<div class="campus">
						<a id="btnResumeActivityExp" href="javascript:;"
							onclick="submitResumeActivityExpForm()" class="changesave">保存</a>
						<a href="javascript:;" onclick="cancelResumeActivityExp()"
							class="changecancel">取消</a>
						<p id="p_activityexp_title" class="none">校园活动经历</p>
						<form id="resumeActivityExpForm">
							<ul class="editStyle" style="margin-top: 20px;">
								<li class="row2"><img src="images_zp/major.png"
									alt="组织或活动名称"><input type="text" placeholder="组织或活动名称"
									id="resumeActivityExpName" name="resumeActivityExpName"
									class="mn" /><br /> <label id="resumeActivityExpName-error"
									class="error ml22" for="resumeActivityExpName"></label></li>

								<li><img src="images_zp/major.png" alt="职位名称"><input
									type="text" placeholder="请输入职位名称"
									id="resumeActivityExpPositionName"
									name="resumeActivityExpPositionName" class="mn" /><br /> <label
									id="resumeActivityExpPositionName-error" class="error ml22"
									for="resumeActivityExpPositionName"></label></li>

								<li class="row"><img src="images_zp/graduate.png"
									alt="起始时间">（起始）<input type="text"
									id="resumeActivityExpStartTime"
									name="resumeActivityExpStartTime" placeholder="起始时间" value=""
									class="mn txt" readonly="readonly" /><br /> <label
									id="resumeActivityExpStartTime-error" class="error ml85"
									for="resumeActivityExpStartTime"></label></li>

								<li><img src="images_zp/graduate.png" alt="结束时间">（结束）<input
									type="text" id="resumeActivityExpEndTime"
									name="resumeActivityExpEndTime" placeholder="结束时间" value=""
									class="mn txt" readonly="readonly" /><br /> <label
									id="resumeActivityExpEndTime-error" class="error ml85"
									for="resumeActivityExpEndTime"></label></li>
								<li><textarea rows="5" cols="70" class="ml6 mb6"
										id="resumeActivityExpDesc"
										placeholder="请您描述这段经历中，您的作用与积极影响，可以参照SMART原则等，也可查看下方范例。如果您还没有想好，可以先不填写。（限500字）"></textarea></li>
							</ul>
						</form>
					</div>
				</div>

				<!-- 项目经验 start-->
				<input type="hidden" id="hdProjectEditId" value="0" /> <input
					type="hidden" id="hdResumeProjectExpSize"
					value="${resumeProjectExpSize}" />

				<div class="content">
					<div id="resume_projectexp_view_module" class="stuMRF_project"
						style="min-height: 250px; height: auto;">
						<p>项目经历</p>
						<ul id="resume_projectexp_view" style="font-size: 14px;">
							<c:forEach items="${resumeInfo.resumeProjectExpDTOs}" var="item">
								<div id="project_exp_${item.id}" class="div_boder">
									<a href="javascript:void(0)" id="aLinkEdit_${item.id}"
										class="aLinkEdit" onclick="editResumeProjectExp('${item.id}')">编辑</a>
									<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
										class="aLinkDelete ml6"
										onclick="deleteResumeProjectExp('${item.id}')">删除</a> <input
										type="hidden" id="project_exp_detail_${item.id}"
										projectName="${item.projectName}"
										positionName="${item.positionName}"
										startTimeDesc="${item.startTimeDesc}"
										endTimeDesc="${item.endTimeDesc}"
										projectDesc="${item.projectDesc}" value="${item.id}" /> <span>项目名称：${item.projectName}</span><br />
									<span>职位名称：${item.positionName}</span><br /> <span>开始时间：${item.startTimeDesc}</span><br />
									<span>结束时间：${item.endTimeDesc}</span><br /> <span>工作描述：
										<div>${item.projectDesc}</div>
									</span>
									<hr style="width: auto; border-color: #9c9c9c;" />
								</div>
							</c:forEach>
						</ul>
						<div id="aLinkProjectAddDiv" style="display: block; height: 50px;">
							<a id="aLinkProjectAdd" href="javascript:;"
								onclick="addResumeProjectExp()" class="aLinkAdd"
								style="right: 300px; position: relative; top: 5px;"><img
								src="images_zp/add_32.png" />添加项目经验</a>
						</div>
					</div>
				</div>

				<div id="resume_projectexp_edit_module" class="content"
					style="height: 270px; margin-top: 0px; padding-bottom: 10px;">
					<div class="project">
						<a id="btnResumeProjectExp" href="javascript:;"
							onclick="submitResumeProjectExpForm()" class="changesave">保存</a>
						<a href="javascript:;" onclick="cancelResumeProjectExp()"
							class="changecancel">取消</a>
						<p id="p_projectexp_title" class="none">项目经历</p>
						<form id="resumeProjectExpForm">
							<ul class="editStyle">
								<li class="row"><img src="images_zp/major.png" alt="专业"><input
									type="text" placeholder="请输入项目名称" id="resumeProjectExpName"
									name="resumeProjectExpName" class="mn" /> <br /> <label
									id="resumeProjectExpName-error" class="error ml22"
									for="resumeProjectExpName"></label></li>

								<li><img src="images_zp/major.png" alt="项目职位"><input
									type="text" placeholder="请输入项目职位"
									id="resumeProjectExpPositionName"
									name="resumeProjectExpPositionName" class="mn" /><br /> <label
									id="resumeProjectExpPositionName-error" class="error ml22"
									for="resumeProjectExpPositionName"></label></li>

								<li class="row"><img src="images_zp/graduate.png"
									alt="起始时间">（起始）<input type="text"
									id="resumeProjectExpStartTime" name="resumeProjectExpStartTime"
									placeholder="起始时间" value="" class="mn txt" readonly="readonly" /><br />
									<label id="resumeProjectExpStartTime-error" class="error ml86"
									for="resumeProjectExpStartTime"></label></li>

								<li><img src="images_zp/graduate.png" alt="结束时间">（结束）<input
									type="text" id="resumeProjectExpEndTime"
									name="resumeProjectExpEndTime" placeholder="结束时间" value=""
									class="mn txt" readonly="readonly" /><br /> <label
									id="resumeProjectExpEndTime-error" class="error ml86"
									for="resumeProjectExpEndTime"></label></li>

								<li><textarea id="resumeProjectExpDesc" rows="5" cols="70"
										class="ml6 mt6"
										placeholder="请您描述这段经历中，您的作用与积极影响，可以参照SMART原则等，也可查看下方范例。如果您还没有想好，可以先不填写。（限500字）"></textarea></li>
							</ul>
						</form>
					</div>
				</div>

				<!-- 爱好特长start-->
				<input type="hidden" id="hdHobbySpecialEditId" value="0" /> <input
					type="hidden" id="hdResumeHobbySpecialSize"
					value="${resumeHobbySpecialSize}" />

				<div class="content">
					<div id="resume_hobbyspecial_view_module" class="stuMRF_interest"
						style="min-height: 150px; height: auto;">
						<p>爱好特长</p>
						<ul id="resume_hobbyspecial_view" style="font-size: 14px;">
							<c:forEach items="${resumeInfo.resumeHobbySpecialDTOs}"
								var="item">
								<div id="hobby_special_${item.id}" class="div_boder">
									<a href="javascript:void(0)" id="aLinkEdit_${item.id}"
										class="aLinkEdit"
										onclick="editResumeHobbySpecialExp('${item.id}')">编辑</a> <a
										href="javascript:void(0)" id="aLinkDelete_${item.id}"
										class="aLinkDelete ml6"
										onclick="deleteResumeHobbySpecialExp('${item.id}')">删除</a> <input
										type="hidden" id="hobby_special_detail_${item.id}"
										specialName="${item.specialName}"
										specialDesc="${item.specialDesc}" value="${item.id}" /> <span>爱好特长名称：${item.specialName}</span><br />
									<span>爱好特长描述：
										<div>${item.specialDesc}</div>
									</span>
									<hr style="width: auto; border-color: #9c9c9c;" />
								</div>
							</c:forEach>
						</ul>
						<div id="aLinkHobbySpecialAddDiv"
							style="display: block; height: 40px;">
							<a id="aLinkHobbySpecialAdd" href="javascript:;"
								onclick="addResumeHobbySpecialExp()" class="aLinkAdd"
								style="right: 300px; position: relative; top: 5px;"><img
								src="images_zp/add_32.png" />添加兴趣爱好</a>
						</div>
					</div>
				</div>

				<div id="resume_hobbyspecial_edit_module" class="content"
					style="min-height: 230px; height: auto; padding-bottom: 5px;">
					<div class="interest">
						<a id="btnResumeHobbySpecial" href="javascript:;"
							onclick="submitResumeHobbySpecialForm()" class="changesave">保存</a>
						<a href="javascript:;" onclick="cancelResumeHobbySpecialExp()"
							class="changecancel">取消</a>
						<p id="p_hobbyspecial_title" class="none">爱好特长</p>
						<form id="resumeHobbySpecialForm">
							<ul class="editStyle h190">
								<li><img src="images_zp/major.png" alt="爱好特长名称"><input
									type="text" placeholder="请填写爱好特长名称，任何方面都可以"
									id="resumeHobbySpecialName" name="resumeHobbySpecialName"
									class="mn" /><br /> <label id="resumeHobbySpecialName-error"
									class="error ml22" for="resumeHobbySpecialName"></label></li>
								<li><textarea id="resumeHobbySpecialDesc" rows="5"
										cols="70" class="ml20 mt6"
										placeholder="您可以描述您在该爱好特长方面的突出表现，如坚持写作5年，如曾参加10次马拉松比赛，有3次进入前十。(50字以内）"></textarea></li>
							</ul>
						</form>
					</div>
				</div>

				<!-- 所获奖励start -->
				<input type="hidden" id="hdPrizeInfoEditId" value="0" /><input
					type="hidden" id="hdResumePrizeInfoSize"
					value="${resumePrizeInfoSize}" />

				<div class="content">
					<div id="resume_prizeinfo_view_module" class="stuMRF_award"
						style="min-height: 150px; height: auto;">
						<p>所获奖励</p>
						<ul id="resume_prizeinfo_view" style="font-size: 14px;">
							<c:forEach items="${resumeInfo.resumePrizeInfoDTOs}" var="item">
								<div id="hobby_prizeinfo_${item.id}" class="div_boder">
									<a href="javascript:void(0)" id="aLinkEdit_${item.id}"
										class="aLinkEdit" onclick="editResumePrizeInfo('${item.id}')">编辑</a>
									<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
										class="aLinkDelete ml6"
										onclick="deletePrizeInfo('${item.id}')">删除</a> <input
										type="hidden" id="hobby_prizeinfo_detail_${item.id}"
										prizeName="${item.prizeName}" prizeLevel="${item.prizeLevel}"
										prizeLevelDesc="${item.prizeLevelDesc}"
										gainTimeDesc="${item.gainTimeDesc}" value="${item.id}" /> <span>奖项名称：${item.prizeName}</span><br />
									<span>奖项级别：${item.prizeLevelDesc}</span><br /> <span>获奖时间：${item.gainTimeDesc}</span>
									<hr style="width: auto; border-color: #9c9c9c;" />
								</div>
							</c:forEach>
						</ul>
						<div id="aLinkPrizeInfoAddDiv"
							style="display: block; height: 40px;">
							<a id="aLinkPrizeInfoAdd" href="javascript:;"
								onclick="addResumePrizeInfo()" class="aLinkAdd"
								style="right: 300px; position: relative; top: 5px;"><img
								src="images_zp/add_32.png" />添加所获奖励</a>
						</div>
					</div>
				</div>

				<div id="resume_prizeinfo_edit_module" class="content"
					style="height: 160px; padding-bottom: 5px;">
					<div class="award">
						<a id="btnResumePrizeInfo" href="javascript:;"
							onclick="submitResumePrizeForm()" class="changesave">保存</a> <a
							href="javascript:;" onclick="cancelResumePrizeInfo()"
							class="changecancel">取消</a>
						<p id="p_prizeinfo_title" class="none">所获奖励</p>
						<form id="resumePrizeForm">
							<ul class="editStyle" style="margin-top: 20px;">
								<li><img src="images_zp/major.png" alt="专业"><input
									type="text" placeholder="请输入奖项名称" id="resumePrizeName"
									name="resumePrizeName" class="mn"
									style="border: none; height: 30px; width: 396px; font-size: 14px; text-indent: 2em; margin-top: 10px; margin-bottom: 10px;" />
									<label id="resumePrizeName-error" class="error ml3"
									for="resumePrizeName"></label></li>

								<li class="row"><img src="images_zp/highest education.png"
									alt="最高学历"><select id="resumePrizeLevel"
									name="resumePrizeLevel" class="mn"
									style="border: none; height: 30px; width: 180px; font-size: 14px; text-indent: 2em; margin-top: 10px; margin-bottom: 10px;">
										<c:forEach items="${rewardLevelEnums}" var="item">
											<option value="${item.code}">${item.desc}</option>
										</c:forEach>
								</select></li>

								<li><img src="images_zp/graduate.png" alt="获奖时间"> <input
									type="text" placeholder="获奖时间" id="resumeGainTime"
									name="resumeGainTime" class="myn time" /><label
									id="resumeGainTime-error" class="error ml3"
									for="resumeGainTime"></label></li>
							</ul>
						</form>
					</div>
				</div>

				<!-- 社交网络start -->
				<input type="hidden" id="hdSocialNetEditIds"
					value="${socialNetEditIds}" /> <input type="hidden"
					id="hdResumeSocialNetSize" value="${resumeSocialNetSize}" />

				<div class="content">
					<div id="resume_socialnet_view_module" class="stuMRF_net"
						style="min-height: 150px; height: auto;">
						<p>社交网络</p>
						<a href="javascript:void(0)" id="aLinkSocialNetEdit"
							class="aLinkEdit changesave" onclick="editResumeSocialNet();">编辑</a>
						<a id="closeResumeSocialNet" href="javascript:void(0)"
							class="aLinkEdit changecancel"
							onclick="closeResumeSocialNetModule();">删除</a>

						<ul id="resume_socialnet_view" style="font-size: 14px;">
							<c:forEach items="${resumeInfo.resumeSocialNetDTOs}" var="item">
								<div id="hobby_socialnet_${item.id}" class="div_boder">
									<input type="hidden" id="hobby_socialnet_detail_${item.id}"
										account="${item.account}" accountDesc="${item.accountDesc}"
										url="${item.url}" value="${item.id}" /> <span>社交网络账号：${item.accountDesc}</span><br />
									<span>链接：${item.url}</span>
									<hr style="width: auto; border-color: #9c9c9c;" />
								</div>
							</c:forEach>
						</ul>
					</div>
				</div>

				<div id="resume_socialnet_edit_module" class="content"
					style="min-height: 150px; height: auto;">
					<div class="net">
						<a id="btnSaveResumeSocialNet" href="javascript:;"
							onclick="saveResumeSocialNet()" class="changesave">保存</a> <a
							href="javascript:;" onclick="cancelResumeSocialNet()"
							class="changecancel">取消</a>
						<p></p>
						<form id="resumeSocialNetForm">
							<ul id="resume_socialnet_edit_ul" class="editStyle"
								style="margin-top: 20px;">
								<c:forEach items="${resumeInfo.resumeSocialNetDTOs}" var="item">
									<li id="socialNetRow_${item.id}"><img
										style="padding-left: 10px;" src="images_zp/graduate.png"
										alt="社交网络"><select id="resumeSocialNetAccount"
										name="resumeSocialNetAccount" class="mn">
											<c:forEach items="${socialNetworkEnums}" var="enumItem">
												<option value="${enumItem.code}"
													${enumItem.code==item.account?"selected=selected":""}>${enumItem.desc}</option>
											</c:forEach>
									</select> <img src="images_zp/major.png" alt="链接地址"><input
										type="text" placeholder="请输入链接，如：www.zhihu.com"
										id="resumeSocialNetUrl" name="resumeSocialNetUrl"
										class="mn socialnet_url" style="width: 270px;"
										value="${item.url}" /><a
										style="display: inline; margin-left: 15px;"
										href="javascript:void(0)" class="aLinkDelete aLinkSnDelete"
										onclick="deleteResumeSocialNet('${item.id}');">删除</a></li>
								</c:forEach>
							</ul>
						</form>
					</div>
					<label id="resumeSocialNetUrl-error" class="error ml90"
						for="resumeSocialNetUrl"></label> <br />
					<div id="aLinkSocialNetAddDiv"
						style="margin-top: 0px; margin-left: 350px; padding-bottom: 10px;">
						<a id="btnAddSocialNet" href="javascript:;"
							onclick="addResumeSocialNet()" style="color: #3c3c3c;"><img
							src="images_zp/add_32.png"
							style="vertical-align: middle; margin-right: 5px;" />添加社交网络</a>
					</div>
				</div>

				<!-- 增加专业技能模块 -->
				<input type="hidden" id="resumeSpecialtySkillEnSize"
					value="${resumeSpecialtySkillEnSize}" /> <input type="hidden"
					id="resumeSpecialtySkillOtherSize"
					value="${resumeSpecialtySkillOtherSize}" /> <input type="hidden"
					id="resumeSpecialtySkillComputerSize"
					value="${resumeSpecialtySkillComputerSize}" /> <input
					type="hidden" id="resumeSpecialtySkillCertSize"
					value="${resumeSpecialtySkillCertSize}" />

				<div class="content">
					<div id="resume_special_all_div" class="stuMRF_skill"
						style="min-height: 200px; height: auto;">
						<p>专业技能</p>
						<a href="javascript:void(0)" class="aLinkEdit changecancel"
							onclick="closeSpecialSkillAllModule();">删除</a>

						<!-- 英语技能 -->
						<ul id="resume_specialtyskill_en_view" style="font-size: 14px;">
							<a href="javascript:;" id="aLinkSpSkillEnEdit"
								class="aLinkEdit aLinkSkillEn"
								onclick="editResumeSpecialtySkillEn()"><img
								src="images_zp/pencil (2).png" width="16" height="16" alt="编辑"></a>
							<c:forEach
								items="${resumeInfo.resumeSpecialtySkillDTO.enLangSkillDTOs}"
								var="item" varStatus="status">
								<div id="specialtyskill_en_${status.index}" class="div_boder">

									<input type="hidden"
										id="specialtyskill_en_detail_${status.index}" id="${item.id}"
										name="${item.name}" nameDesc="${item.nameDesc}"
										score="${item.score}" value="${item.id}" /> <span>英语考试名称：${item.nameDesc}</span><br />
									<span>考试成绩：${item.score}</span>
								</div>
							</c:forEach>
						</ul>
						<div id="resume_specialtyskill_en_edit_module"
							class="englishExam specialtyskill_en_edit editStyle">
							<a href="javascript:;" onclick="saveResumeSpecialtySkillEn()"
								class="skill_changesave">保存</a> <a href="javascript:;"
								id="aLinkEnCancel" onclick="cancelResumeSpecialtySkillEn()"
								class="skill_changecancel">取消</a>
							<p>英语技能</p>
							<table id="resume_specialtyskill_en_edit_tb" class="ml95">
								<c:forEach
									items="${resumeInfo.resumeSpecialtySkillDTO.enLangSkillDTOs}"
									var="item" varStatus="status">
									<tr id="specialtySkillEnRow_${status.index}" class="spsken">
										<td class="wd250"><img src="images_zp/graduate.png"
											alt="毕业年份"><select id="resumeSpecialtySkillEnName"
											name="resumeSpecialtySkillEnName"
											class="mn specialtyskill_en_select">
												<c:forEach items="${englishSkillEnums}" var="enumItem">
													<option value="${enumItem.code}"
														${enumItem.code==item.name?"selected=selected":""}>${enumItem.desc}</option>
												</c:forEach>
										</select></td>
										<td class="wd235"><img src="images_zp/major.png" alt="专业"><input
											type="text" placeholder="请输入考试成绩"
											id="resumeSpecialtySkillEnScore"
											name="resumeSpecialtySkillEnScore"
											class="mn specialtyskill_en_txt" value="${item.score}" /></td>
										<td class="wd50"><a href="javascript:;"
											class="aLinkDelete aLinkSpSkillEnDel"
											onclick="deleteResumeSpecialtySkillEn('${status.index}');"><img
												src="images_zp/close_16.png" width="16" height="16" alt="删除" /></a></td>
									</tr>
								</c:forEach>
							</table>
							<div id="btnAddSpecialtySkillEnDiv" class="specialtyskill_en_div">
								<a id="btnAddSpecialtySkillEn"
									class="aLinkAdd specialtyskill_en_add"
									onclick="addResumeSpecialtySkillEn()" href="javascript:;">
									<img src="images_zp/add_32.png"> 添加英语技能
								</a>
							</div>
						</div>
						<ul id="resume_specialtyskill_en_view_default"
							style="display: none">请添加英语技能
						</ul>
						<hr id="resume_specialtyskill_en_view_default_hr"
							style="width: auto; border-color: #9c9c9c; border-style: dotted;" />

						<!-- 其他语言技能 -->
						<ul id="resume_specialtyskill_other_view" style="font-size: 14px;">
							<a href="javascript:;" class="aLinkEdit aLinkSkillOther"
								onclick="editResumeSpecialtySkillOther()"><img
								src="images_zp/pencil (2).png" width="16" height="16" alt="编辑"></a>
							<c:forEach
								items="${resumeInfo.resumeSpecialtySkillDTO.otherLangSkillDTOs}"
								var="item" varStatus="status">
								<div id="specialtyskill_other_${status.index}" class="div_boder">
									<input type="hidden"
										id="specialtyskill_other_detail_${status.index}"
										id="${item.id}" name="${item.name}"
										nameDesc="${item.nameDesc}" level="${item.level}"
										value="${item.id}" /> <span>其他语言名称：${item.nameDesc}</span><br />
									<span>考试成绩：${item.level}</span>
								</div>
							</c:forEach>
						</ul>

						<div id="resume_specialtyskill_other_edit_module"
							class="englishExam specialtyskill_other_edit editStyle">
							<a href="javascript:;" onclick="saveResumeSpecialtySkillOther()"
								class="skill_changesave">保存</a> <a href="javascript:;"
								id="aLinkOtherCancel"
								onclick="cancelResumeSpecialtySkillOther()"
								class="skill_changecancel">取消</a>
							<p>其他语言技能</p>
							<table id="resume_specialtyskill_other_edit_tb" class="ml95">
								<c:forEach
									items="${resumeInfo.resumeSpecialtySkillDTO.otherLangSkillDTOs}"
									var="item" varStatus="status">
									<tr id="specialtySkillOtherRow_${status.index}"
										class="spskother">
										<td class="wd250"><img src="images_zp/graduate.png"
											alt="毕业年份"><select id="resumeSpecialtySkillOtherName"
											name="resumeSpecialtySkillOtherName"
											class="mn specialtyskill_other_select">
												<c:forEach items="${otherLanguageSkillEnums}" var="enumItem">
													<option value="${enumItem.code}"
														${enumItem.code==item.name?"selected=selected":""}>${enumItem.desc}</option>
												</c:forEach>
										</select></td>
										<td class="wd235"><img src="images_zp/major.png"
											alt="掌握程度" /><input type="text" placeholder="掌握程度"
											id="resumeSpecialtySkillOtherLevel"
											name="resumeSpecialtySkillOtherLevel"
											class="mn specialtyskill_other_txt" value="${item.level}" /></td>
										<td class="wd50"><a href="javascript:;"
											class="aLinkDelete aLinkSpSkillOtherDel"
											onclick="deleteResumeSpecialtySkillOther('${status.index}');"><img
												src="images_zp/close_16.png" width="16" height="16" alt="删除" /></a></td>
									</tr>
								</c:forEach>
							</table>
							<div id="btnAddSpecialtySkillOtherDiv"
								class="specialtyskill_other_div">
								<a id="btnAddSpecialtySkillOther"
									class="aLinkAdd specialtyskill_other_add"
									onclick="addResumeSpecialtySkillOther()" href="javascript:;">
									<img src="images_zp/add_32.png"> 添加其他语言技能
								</a>
							</div>
						</div>

						<ul id="resume_specialtyskill_other_view_default"
							style="display: none">请添加其他语言技能
						</ul>
						<hr id="resume_specialtyskill_other_view_default_hr"
							style="width: auto; border-color: #9c9c9c; border-style: dotted;" />

						<!-- 计算机技能 -->
						<ul id="resume_specialtyskill_computer_view"
							style="font-size: 14px;">
							<a href="javascript:;" class="aLinkEdit aLinkSkillComputer"
								onclick="editResumeSpecialtySkillComputer()"><img
								src="images_zp/pencil (2).png" width="16" height="16" alt="编辑"></a>
							<c:forEach
								items="${resumeInfo.resumeSpecialtySkillDTO.computerSkillDTOs}"
								var="item" varStatus="status">
								<div id="specialtyskill_computer_${status.index}"
									class="div_boder">
									<input type="hidden"
										id="specialtyskill_computer_detail_${status.index}"
										id="${item.id}" skill="${item.skill}"
										skillDesc="${item.skillDesc}" skillLevel="${item.skillLevel}"
										skillLevelDesc="${item.skillLevelDesc}" value="${item.id}" />
									<span>计算机技能：${item.skillDesc}</span><br /> <span>计算机熟练程度说明：${item.skillLevelDesc}</span><br />
									</span>
								</div>
							</c:forEach>
						</ul>

						<div id="resume_specialtyskill_computer_edit_module"
							class="englishExam specialtyskill_computer_edit editStyle">
							<a href="javascript:;"
								onclick="saveResumeSpecialtySkillComputer()"
								class="skill_changesave">保存</a> <a href="javascript:;"
								onclick="cancelResumeSpecialtySkillComputer()"
								class="skill_changecancel">取消</a>
							<p>计算机技能</p>
							<table id="resume_specialtyskill_computer_edit_tb" class="ml95">
								<c:forEach
									items="${resumeInfo.resumeSpecialtySkillDTO.computerSkillDTOs}"
									var="item" varStatus="status">
									<tr id="specialtySkillComputerRow_${status.index}"
										class="spskcomputer">
										<td class="wd250"><img src="images_zp/graduate.png"
											alt="计算机技能"><select
											id="resumeSpecialtySkillComputerSkill"
											name="resumeSpecialtySkillComputerSkill"
											class="mn specialtyskill_computer_select">
												<c:forEach items="${computerSkillMap}" var="m">
													<optgroup label="${m.key}">
														<c:forEach items="${m.value}" var="enumItem">
															<option value="${enumItem.code}"
																${enumItem.code==item.skill?"selected=selected":""}>${enumItem.desc}</option>
														</c:forEach>
													</optgroup>
												</c:forEach>
										</select></td>
										<td class="wd235"><img src="images_zp/major.png"
											alt="熟练程度说明" /><select
											id="resumeSpecialtySkillComputerSkillLevel"
											name="resumeSpecialtySkillComputerSkillLevel"
											class="mn specialtyskill_computer_select">
												<c:forEach items="${skillLevelEnums}" var="enumItem">
													<option value="${enumItem.code}"
														${enumItem.code==item.skillLevel?"selected=selected":""}>${enumItem.desc}</option>
												</c:forEach>
										</select></td>
										<td class="wd50"><a href="javascript:;"
											class="aLinkDelete aLinkSpSkillComputerDel"
											onclick="deleteResumeSpecialtySkillComputer('${status.index}');"><img
												src="images_zp/close_16.png" width="16" height="16" alt="删除" /></a></td>
									</tr>
								</c:forEach>
							</table>
							<div id="btnAddSpecialtySkillComputerDiv"
								class="specialtyskill_computer_div">
								<a id="btnAddSpecialtySkillComputer"
									class="aLinkAdd specialtyskill_computer_add"
									onclick="addResumeSpecialtySkillComputer()" href="javascript:;">
									<img src="images_zp/add_32.png"> 添加计算机技能
								</a>
							</div>
						</div>

						<ul id="resume_specialtyskill_computer_view_default"
							style="display: none">请添加计算机技能
						</ul>
						<hr id="resume_specialtyskill_computer_view_default_hr"
							style="width: auto; border-color: #9c9c9c; border-style: dotted;" />

						<!-- 证书 -->
						<ul id="resume_specialtyskill_cert_view" style="font-size: 14px;">
							<a href="javascript:;" class="aLinkEdit aLinkSkillCert"
								onclick="editResumeSpecialtySkillCert()"><img
								src="images_zp/pencil (2).png" width="16" height="16" alt="编辑"></a>
							<c:forEach
								items="${resumeInfo.resumeSpecialtySkillDTO.certificateDTOs}"
								var="item" varStatus="status">
								<div id="specialtyskill_cert_${status.index}" class="div_boder">
									<input type="hidden"
										id="specialtyskill_cert_detail_${status.index}"
										id="${item.id}" diploma="${item.diploma}" value="${item.id}" />
									<span>证书：${item.diploma}</span>
								</div>
							</c:forEach>
						</ul>

						<div id="resume_specialtyskill_cert_edit_module"
							class="englishExam specialtyskill_cert_edit editStyle">
							<a href="javascript:;" onclick="saveResumeSpecialtySkillCert()"
								class="skill_changesave">保存</a> <a href="javascript:;"
								onclick="cancelResumeSpecialtySkillCert()"
								class="skill_changecancel">取消</a>
							<p>证书</p>
							<table id="resume_specialtyskill_computer_cert_tb" class="ml95">
								<c:forEach
									items="${resumeInfo.resumeSpecialtySkillDTO.certificateDTOs}"
									var="item" varStatus="status">
									<tr id="specialtySkillCertRow_${status.index}" class="spskcert">
										<td class="wd250"><img src="images_zp/major.png"
											alt="证书名称" /><input type="text" placeholder="证书名称"
											id="resumeSpecialtySkillCertName"
											name="resumeSpecialtySkillCertName"
											class="mn specialtyskill_cert_txt" value="${item.diploma}" /></td>
										<td class="wd35"><a href="javascript:;"
											class="aLinkDelete aLinkSpSkillCertDel"
											onclick="deleteResumeSpecialtySkillCert('${status.index}');"><img
												src="images_zp/close_16.png" width="16" height="16" alt="删除" /></a></td>
									</tr>
								</c:forEach>
							</table>
							<div id="btnAddSpecialtySkillCertDiv"
								class="specialtyskill_cert_div">
								<a id="btnAddSpecialtySkillCert"
									class="aLinkAdd specialtyskill_cert_add"
									onclick="addResumeSpecialtySkillCert()" href="javascript:;">
									<img src="images_zp/add_32.png"> 添加证书
								</a>
							</div>
						</div>

						<ul id="resume_specialtyskill_cert_view_default"
							style="display: none">
							<span>请添加证书</span>
						</ul>
					</div>
				</div>

				<input type="hidden" id="hdOpusInfoEditId" value="0" /> <input
					type="hidden" id="hdResumeOpusInfoSize"
					value="${resumeOpusInfoSize}" />
				<div class="content">
					<div id="resume_opusinfo_view_module" class="stuMRF_myWork"
						style="height: auto; min-height: 150px;">
						<p>我的作品</p>
						<ul id="resume_opusinfo_view" style="font-size: 14px;">
							<c:forEach items="${resumeInfo.resumeOpusInfoDTOs}" var="item">
								<div id="opusinfo_${item.id}" class="div_boder" val="${item.id}">
									<a href="javascript:void(0)" id="aLinkEdit_${item.id}"
										class="aLinkEdit" onclick="editResumeOpusInfo('${item.id}')">编辑</a>
									<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
										class="aLinkDelete ml6"
										onclick="deleteResumeOpusInfo('${item.id}')">删除</a> <input
										type="hidden" id="opusinfo_detail_${item.id}"
										opusName="${item.opusName}" opusUrl="${item.opusUrl}"
										opusPath="${item.opusPath}" value="${item.id}"
										opusPathFile="${item.opusPathFile}" /> <span>作品名称：${item.opusName}</span><br />
									<span>作品链接：${item.opusUrl} </span> <br />
									<c:choose>
										<c:when test="${!item.opusPath.isEmpty()}">
											<span>作品下载：<a class="opusfile_path"
												href="${item.opusPath}">${item.opusPathFile}</a>
											</span>
										</c:when>
										<c:otherwise>
											<div id="resumeOpusFileUpload" class="upload opusfile_upload">
												<form id="opusUploadForm" enctype="multipart/form-data"
													class="opusfile_upload_form uploadForm">
													<div>
														<input type="file" name="file" id="resumeOpusUploadFile" />
														<input type="button" id="btnOpusUpload"
															onclick="uploadOpusFile(${item.id});" value="上传"><span>上传作品（大小不超过5M，压缩文件形式）</span>
														<div id="errorMsg" class="opusfile_upload_errormsg"></div>
													</div>
												</form>
											</div>
										</c:otherwise>
									</c:choose>
									<hr style="width: auto; border-color: #9c9c9c;" />
								</div>
							</c:forEach>
						</ul>
						<div id="aLinkOpusInfoAddDiv"
							style="display: block; height: 40px;">
							<a id="aLinkOpusInfoAdd" href="javascript:;"
								onclick="addResumeOpusInfo()" class="aLinkAdd"
								style="right: 300px; position: relative; top: 5px;"><img
								src="images_zp/add_32.png" />添加我的作品</a>
						</div>
					</div>
				</div>

				<div id="resume_opusinfo_edit_module" class="content"
					style="min-height: 165px; height: auto;">
					<div class="myWork">
						<a href="javascript:;" onclick="saveResumeOpusInfo()"
							class="changesave">保存</a> <a href="javascript:;"
							onclick="cancelResumeOpusInfo()" class="changecancel">取消</a>
						<p id="p_opusinfo_title" class="none">我的作品</p>
						<ul
							style="background-color: #FAF0E6; height: auto; min-height: 100px; margin-top: 20px;">
							<li><img src="images_zp/major.png" alt="专业"><input
								type="text" placeholder="请填写作品名称" id="resumeOpusInfoName"
								name="resumeOpusInfoName" class="mn"
								style="border: none; height: 30px; font-size: 14px; text-indent: 2em; margin-top: 10px; margin-bottom: 10px;" />
							</li>
							<li><img src="images_zp/major.png" alt="专业"><input
								type="text" placeholder="如作品有链接，请输入" id="resumeOpusInfoUrl"
								name="resumeOpusInfoUrl" class="mn"
								style="border: none; height: 30px; width: 350px; font-size: 14px; text-indent: 2em; margin-top: 10px; margin-bottom: 10px;" />
							</li>
							<li id="opusUploadLi">
								<form id="opusUploadForm" enctype="multipart/form-data"
									class="opusfile_path uploadForm">
									<div id="opusEditForm" class="opusfile_upload"
										style="margin-left: 310px;">
										<input type="file" name="file" id="resumeOpusUploadFile" /><input
											type="button" id="btnOpusUpload" onclick="uploadOpusFile();"
											value="上传"><span>上传作品（大小不超过5M，压缩文件形式）</span>
										<div id="errorMsg" class="opusfile_upload_errormsg"></div>
									</div>
								</form>
							</li>
						</ul>
					</div>
				</div>

				<!-- 自定义其他板块 start-->
				<input type="hidden" id="hdCustomEditId" value="0" /><input
					type="hidden" id="hdResumeCustomSize" value="${resumeCustomSize}" />

				<div class="content">
					<div id="resume_custom_view_module" class="stuMRF_self"
						style="min-height: 150px; height: auto;">
						<p>其他版块</p>
						<ul id="resume_custom_view" style="font-size: 14px;">
							<c:forEach items="${resumeInfo.resumeCustomDTOs}" var="item">
								<div id="hobby_custom_${item.id}" class="div_boder">
									<a href="javascript:void(0)" id="aLinkEdit_${item.id}"
										class="aLinkEdit" onclick="editResumeCustom('${item.id}')">编辑</a>
									<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
										class="aLinkDelete ml6"
										onclick="deleteResumeCustom('${item.id}')">删除</a> <input
										type="hidden" id="hobby_custom_detail_${item.id}"
										name="${item.name}" description="${item.description}"
										value="${item.id}" /> <span>自定义板块名称：${item.name}</span><br />
									<span>自定义板块描述：
										<div>${item.description}</div>
									</span>
									<hr style="width: auto; border-color: #9c9c9c;" />
								</div>
							</c:forEach>
						</ul>
						<div id="aLinkCustomAddDiv" style="display: block; height: 40px;">
							<a id="aLinkCustomAdd" href="javascript:;"
								onclick="addResumeCustom()" class="aLinkAdd"
								style="right: 300px; position: relative; top: 5px;"><img
								src="images_zp/add_32.png" />添加自定义板块</a>
						</div>
					</div>
				</div>

				<div id="resume_custom_edit_module" class="content"
					style="min-height: 150px; height: auto;">
					<div class="self">
						<a href="javascript:;" onclick="saveResumeCustom()"
							class="changesave">保存</a> <a href="javascript:;"
							onclick="cancelResumeCustom()" class="changecancel">取消</a>
						<p id="p_custom_title" class="none">其他版块</p>
						<ul class="editStyle mt25">
							<li><img src="images_zp/major.png" alt="专业"><input
								type="text" placeholder="请填写自定义板块名称，如政治面貌，籍贯，自我评价等"
								id="resumeCustomName" name="resumeCustomName" class="mn" /></li>
							<li><textarea id="resumeCustomDesc" rows="5" cols="70"
									class="ml6 mt6" placeholder="请对自定义板块进行说明(100字以内）"></textarea></li>
						</ul>
					</div>
				</div>

				<!-- 简历附件上传 -->
				<input type="hidden" id="hasAttachmentFile"
					value="${hasAttachmentFile}" />
				<div id="resume_upload_attachment_div" class="content"
					style="min-height: 50px; height: auto;">
					<p>简历附件上传</p>
					<div class="stuAt_sixbtn" align="center">
						<span><a id="attachment_file_url"
							href="${resumeInfo.uploadPath}">${resumeInfo.uploadPathFile}</a><a
							id="attachement_file_url_edit" onclick="openUploadResumeDialog()"
							class="fontsize14 ml6 dsinline pointer">编辑</a><a
							id="attachement_file_url_edit" onclick="deleteResumeAttachment()"
							class="fontsize14 dsinline ml6 pointer">删除</a></span>
					</div>
				</div>

				<!--后面8大模块-->
				<div id="custom_div" class="content"
					style="min-height: 200px; height: auto;">
					<p>自定义版块</p>
					<div class="stuOR_sixbtn">
						<a id="aLinkActivityAddNew" onclick="addResumeActivityExp()"
							href="javascript:;" class="selfAdd">+添加活动经历</a> <a
							id="aLinkProjectAddNew" onclick="addResumeProjectExp()"
							href="javascript:;" class="selfAdd">+添加项目经验</a> <a
							id="aLinkHobbySpecialAddNew" onclick="addResumeHobbySpecialExp()"
							href="javascript:;" class="selfAdd">+添加兴趣爱好</a><a
							id="aLinkPrizeInfoAddNew" onclick="addResumePrizeInfo()"
							href="javascript:;" class="selfAdd">+添加所获奖励</a> <a
							id="aLinkSocialNetAddNew" onclick="showResumeSocialNetModule()"
							href="javascript:;" class="selfAdd">+添加社交网络</a> <a
							id="aLinkSpecialSkillAddNew"
							onclick="showSpecialSkillAllModule()" href="javascript:;"
							class="selfAdd">+添加专业技能</a><a id="aLinkOpusInfoAddNew"
							href="javascript:;" onclick="addResumeOpusInfo()" class="selfAdd">+添加我的作品</a>
						<a id="aLinkCustomAddNew" onclick="addResumeCustom()"
							href="javascript:;" class="selfAdd">+添加其他版块</a>
					</div>
				</div>

				<!--保存/预览简历按钮-->
				<div class="content" style="background: none; height: 250px;">
					<div class="savebtn">
						<a href="resume/search_joblist?id=${memberInfo.memberId}"
							class="save">完成简历，去查看职位</a>
					</div>
				</div>
			</div>
			<!--侧栏-->
			<div class="rightBar">
				<div class="attach">
					<img src="images_zp/send_mail.png" alt="upload" /> <a
						id="alinkUploadAttachment" href="javascript:;"
						onclick="openUploadResumeDialog()">点击上传附件简历</a>
				</div>
				<div class="rightResumeView">
					<a target="_blank"
						href="resume/resume_info_view?id=${memberInfo.memberId}&rsid=${resumeInfo.id}">预览简历</a>
				</div>
				<div class="rightLogo">
					<img src="images_zp/stuloginlogo.png" alt="logo" />
				</div>
				<div class="qrCode">
					<img src="images_zp/erweima_fp.jpg" alt="qrcode" /> <img
						src="images_zp/stuloginwelcome.png" alt="welcome" />
				</div>
			</div>

			<select id="computerDropdownHtml">

			</select>
		</div>
	</div>
</body>
</html>