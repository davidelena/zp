<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@include file="/WEB-INF/views/resume_basic_info.jsp"%> --%>
<%@include file="/WEB-INF/views/include_zp.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>简历详情</title>
</head>
<body>
	<script src="javascript/resume_info.js"></script>
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
			initBasicInfoPage();
			initResumeEduExpPage();
			initResumeWorkExpPage();
			initResumeActivityExpPage();
			initResumeProjectExpPage();
			initResumeHobbySpecialPage();
			initResumeCustomPage();
			initResumePrizeInfoPage();
			initResumeOpusInfoPage();
			initResumeSocialNetPage();
			initResumeSpecialtySkillEnPage();
			initResumeSpecialtySkillOtherPage();
			initResumeSpecialtySkillComputerPage();
			initResumeSpecialtySkillCertPage();

			totalSocialNet = $("#resumeSocialNetSize").val();
			totalSpecialtySkillEn = $("#resumeSpecialtySkillEnSize").val();
			totalSpecialtySkillOther = $("#resumeSpecialtySkillOtherSize").val();
			totalSpecialtySkillComputer = $("#resumeSpecialtySkillComputerSize").val();
			totalSpecialtySkillCert = $("#resumeSpecialtySkillCertSize").val();

			getComputerSkillDropdownHtml();

		};

		var initBasicInfoPage = function() {
			$(".time").datepicker();
			$("#resume_basicinfo_edit").hide();
			$("#new_resume_basic_info").hide();
			initUniversityDialog();
			$(".ui-dialog-buttonset").find(".ui-button").eq(0).hide();
		};

		//大学选项框
		var initUniversityDialog = function() {
			$("#dialog_school").dialog({
				autoOpen : false,
				title : "请选择学校",
				show : {
					duration : 200
				},
				hide : {
					duration : 200
				},
				resizable : false,
				height : 400,
				width : 890,
				modal : true,
				buttons : {
					"省份" : function() {
						toProvince();
					},
					"确认" : function() {
						saveUniversityData();
						$("#dialog_school").dialog("close");
					},
					"取消" : function() {
						$("#dialog_school").dialog("close");
					}
				}
			});
		};

		var saveUniversityData = function() {
			var checkedItem = $("input[name='chkUniversity']:checked");
			$("#resumeSchool").val($(checkedItem).attr("desc"));
			$("#hdResumeSchoolId").val($(checkedItem).val());
		};

		var showUniversityDialog = function() {
			$("#dialog_school").dialog("open");
			var id = $("#hdResumeSchoolId").val();
			$("input[name='chkUniversity']").each(function(index, item) {
				if (id == $(item).val()) {
					$(item).attr("checked", true);
				}
			});
		};

		var showSchoolDetailDiv = function() {
			var proviceId = $("input[name='chkUniversityGeo']:checked").val();
			$("#school_geo").hide();
			loadUniversity(proviceId);
			$(".ui-dialog-buttonset").find(".ui-button").eq(0).show();
		};

		var loadUniversity = function(proviceId) {
			$.ajax({
				url : "resume/loadUniversity",
				type : "post",
				dataType : "json",
				data : {
					proviceId : proviceId,
				},
				success : function(data) {
					var html = "";
					var tempHtml = "";
					if (data.success == 1) {
						for (var i = 0; i < data.list.length; i++) {
							if (i % 4 == 0) {
								html += '<tr>';
							}
							tempHtml = '<td class="borderStyle"><input type="radio" name="chkUniversity" value="'
									+ data.list[i].id + '" desc="' + data.list[i].name
									+ '" onclick="saveUniversityData();" style="margin-left: 6px; cursor: pointer;" />'
									+ data.list[i].name + '</td>';
							html += tempHtml;
							if ((i + 1) % 4 == 0) {
								html += '</tr>';
							}

						}
					}

					$("#school_detail").html(html);
					$("#school_detail").show();
				}
			});
		};

		var toProvince = function() {
			$("#school_detail").hide();
			$("#school_geo").show();
			$(".ui-dialog-buttonset").find(".ui-button").eq(0).hide();
		};

		var showBasicEdit = function() {
			$("#resume_basicinfo_edit").show();
			$("#resume_basicinfo_view").hide();
		};

		var hideBasicEdit = function() {
			$("#resume_basicinfo_edit").hide();
			$("#resume_basicinfo_view").show();
		};

		var addBasicEdit = function() {
			var html = $("#new_resume_basic_info").html();
			$("#resume_basicinfo_edit").appendTo(html);
			$("#new_resume_basic_info").show();
		};

		var saveBasicInfo = function() {
			$.ajax({
				url : "resume/saveBasicInfo",
				type : "post",
				dataType : "json",
				data : {
					memberId : $("#hdMemberId").val(),
					resumeId : $("#hdResumeId").val(),
					resumeName : $("#resumeName").val(),
					resumeMemberName : $("#resumeMemberName").val(),
					resumeGender : $("#resumeGender").val(),
					resumeSchool : $("#hdResumeSchoolId").val(),
					resumeMajor : $("#resumeMajor").val(),
					resumeDiploma : $("#resumeDiploma").val(),
					resumeGraduationTime : $("#resumeGraduationTime").val(),
					resumeEmail : $("#resumeEmail").val(),
					resumeMobile : $("#resumeMobile").val()
				},
				success : function(data) {
					if (data.success) {
						hideBasicEdit();
						$("#resumeNameView").text(data.resumeInfo.resumeName);
						$("#memberNameView").text(data.resumeInfo.memberDTO.name);
						$("#memberSchoolView").text(data.resumeInfo.memberDTO.schoolDesc);
						$("#memberMobileView").text(data.resumeInfo.memberDTO.mobile);
						$("#memberEmailView").text(data.resumeInfo.memberDTO.commonEmail);
						$("#memberSexDescView").text(data.resumeInfo.sexDesc);
						$("#memberMajorView").text(data.resumeInfo.major);
						$("#memberDiplomaDescView").text(data.resumeInfo.diplomaDesc);
						$("#memberGraduationTimeDescView").text(data.resumeInfo.graduationTimeDesc);
					}
				}
			});
		};
	</script>

	<div id="dialog_school">
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

	<div align="center">

		<!-- 基本信息start -->
		<div id="resume_basicinfo_module">
			<h2>
				<strong>基本信息</strong>
			</h2>
			<div id="resume_basicinfo_view" class="div_boder">
				<table>
					<tr>
						<td>简历名称：<span id="resumeNameView">${resumeInfo.resumeName}</span></td>
					</tr>
					<tr>
						<td>姓名：<span id="memberNameView">${memberInfo.name}</span></td>
						<td>学校：<span id="memberSchoolView">${memberInfo.schoolId>0?memberInfo.schoolDesc:memberInfo.school}</span></td>
					</tr>
					<tr>
						<td>手机：<span id="memberMobileView">${memberInfo.mobile}</span></td>
						<td>邮箱：<span id="memberEmailView">${memberInfo.commonEmail}</span></td>
					</tr>
					<tr>
						<td>性别：<span id="memberSexDescView">${resumeInfo.sexDesc}</span></td>
						<td>专业：<span id="memberMajorView">${resumeInfo.major}</span></td>
					</tr>
					<tr>
						<td>学历：<span id="memberDiplomaDescView">${resumeInfo.diplomaDesc}</span></td>
						<td>毕业年份：<span id="memberGraduationTimeDescView">${resumeInfo.graduationYear}</span></td>
					</tr>
					<tr>
						<td colspan="2"><a href="javascript:void(0)" id="aLinkBasic"
							onclick="showBasicEdit();">编辑</a></td>
					</tr>
				</table>
			</div>
			<div id="resume_basicinfo_edit">
				<div>
					<form id="resumeBasicInfoForm">
						<input type="hidden" id="hdMemberId" name="hdMemberId"
							value="${memberInfo.memberId}" /> <input type="hidden"
							id="hdResumeId" name="hdResumeId" value="${resumeInfo.id}" />
						<table>
							<tr>
								<td colspan="2"><input type="text" id="resumeName"
									name="resumeName" placeholder="简历名称"
									value="${resumeInfo.resumeName}" class="txt"
									style="width: 300px" /></td>
							</tr>
							<tr>
								<td>姓名<br /> <input type="text" id="resumeMemberName"
									name="resumeMemberName" placeholder="姓名"
									value="${memberInfo.name}" class="txt" style="width: 300px" /></td>
								<td>性别 <br /> <select id="resumeGender"
									name="resumeGender" class="mt10 wd220">
										<c:forEach items="${genderEnums}" var="item">
											<option value="${item.code}"
												${item.code==resumeInfo.sex?"selected=selected":""}>${item.desc}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td>学校<br /> <input type="text" id="resumeSchool"
									name="resumeSchool" placeholder="学校"
									onclick="showUniversityDialog();"
									value="${memberInfo.schoolDesc}" class="txt"
									style="width: 300px" readonly="readonly" /><input
									type="hidden" id="hdResumeSchoolId" name="hdResumeSchoolId"
									value="${memberInfo.schoolId}" /></td>
								<td>专业 <br /> <input type="text" id="resumeMajor"
									name="resumeMajor" placeholder="专业" value="${resumeInfo.major}"
									class="txt" style="width: 300px" /></td>
							</tr>
							<tr>
								<td>最高学历 <br /> <select id="resumeDiploma"
									name="resumeDiploma" class="mt10 wd220">
										<c:forEach items="${educationEnums}" var="item">
											<option value="${item.code}"
												${item.code==resumeInfo.diploma?"selected=selected":""}>${item.desc}</option>
										</c:forEach>
								</select></td>
								<td>毕业年份 <br /> <input type="text"
									id="resumeGraduationTime" name="resumeGraduationTime"
									placeholder="毕业年份" value="${resumeInfo.graduationTimeDesc}"
									class="txt time" style="width: 300px" /></td>
							</tr>
							<tr>
								<td>邮箱 <br /> <input type="text" id="resumeEmail"
									name="resumeEmail" placeholder="邮箱"
									value="${memberInfo.commonEmail}" class="txt"
									style="width: 300px" /></td>
								<td>手机 <br /> <input type="text" id="resumeMobile"
									name="resumeMobile" placeholder="手机"
									value="${memberInfo.mobile}" class="txt" style="width: 300px" /></td>
							</tr>
							<tr>
								<td><input id="btnSave" type="button"
									style="width: 300px; heigth: 30px;" value="保存"
									onclick="saveBasicInfo();" /></td>
								<td><input id="btnCancel" type="button"
									style="width: 300px; heigth: 30px;" value="取消 "
									onclick="hideBasicEdit();" /></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>

		<!-- 教育经历start -->
		<div id="resume_educationexp">
			<input type="hidden" id="hdEduEditId" value="0" />
			<h2>
				<strong>教育经历</strong>
			</h2>
			<div id="resume_educationexp_view_module">

				<c:if test="${resumeEducationExpSize<5}">
					<a id="aLinkEduAdd" href="javascript:void(0)"
						onclick="addResumeEducationExp();">添加</a>
				</c:if>
				<div id="resume_educationexp_view">
					<c:forEach items="${resumeInfo.resumeEducationExpDTOs}" var="item">
						<div id="edu_exp_${item.id}" class="div_boder">
							<input type="hidden" id="edu_exp_detail_${item.id}"
								diploma="${item.diploma}" diplomaDesc="${item.diplomaDesc}"
								school="${item.school}" major="${item.major}"
								majorType="${item.majorType}"
								majorTypeDesc="${item.majorTypeDesc}"
								academicStartsDesc="${item.academicStartsDesc}"
								graduationTimeDesc="${item.graduationTimeDesc}"
								scoreTop="${item.scoreTop}" scoreTopDesc="${item.scoreTopDesc}"
								value="${item.id}" /> <span>学历/学位：${item.diplomaDesc}</span><br />
							<span>学校名称：${item.school}</span><br /> <span>专业名称：${item.major}</span><br />
							<span>专业分类：${item.majorTypeDesc}</span><input type="hidden"
								id="edu_exp_majorType_${item.id}" value="${item.majorType}"
								desc="${item.majorTypeDesc}" /><br /> <span>入学时间：${item.academicStartsDesc}</span><br />
							<span>毕业时间：${item.graduationTimeDesc}</span><br /> <span>成绩排名：${item.scoreTopDesc}</span><br />
							<a href="javascript:void(0)" id="aLinkEdit_${item.id}"
								class="aLinkEdit" onclick="editResumeEduExp('${item.id}')">编辑</a>
							<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
								class="aLinkDelete ml6"
								onclick="deleteResumeEduExp('${item.id}')">删除</a>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_educationexp_edit_module" class="div_boder">
				<table id="educationExpEditFormTb">
					<tr>
						<td>学历/学位：<br /> <select id="resumeEduExpDiploma"
							name="resumeEduExpDiploma" class="mt10 wd220">
								<c:forEach items="${educationEnums}" var="item">
									<option value="${item.code}">${item.desc}</option>
								</c:forEach>
						</select></td>
						<td>学校名称：<br /> <input type="text" id="resumeEduExpSchool"
							name="resumeEduExpSchool" placeholder="学校" value="" class="txt"
							style="width: 300px" /></td>
					</tr>
					<tr>
						<td>专业名称：<br /> <input type="text" id="resumeEduExpMajor"
							name="resumeEduExpMajor" placeholder="专业名称" value="" class="txt"
							style="width: 300px" /></td>
						<td>专业分类：<br /> <select id="resumeEduExpMajorType"
							name="resumeEduExpMajorType" class="mt10 wd220">
								<c:forEach items="${specialityEnums}" var="item">
									<option value="${item.code}">${item.desc}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td>入学时间：<br /> <input type="text"
							id="resumeEduExpAcademicStarts" name="resumeEduExpAcademicStarts"
							placeholder="入学时间" value="" class="txt" style="width: 300px"
							readonly="readonly" /></td>
						<td>毕业时间：<br /> <input type="text"
							id="resumeEduExpGraduationTime" name="resumeEduExpGraduationTime"
							placeholder="毕业时间" value="" class="txt" style="width: 300px"
							readonly="readonly" /></td>
					</tr>
					<tr>
						<td>成绩排名：<br /> <select id="resumeEduExpScoreTop"
							name="resumeEduExpScoreTop" class="mt10 wd220">
								<c:forEach items="${achievementEnums}" var="item">
									<option value="${item.code}">${item.desc}</option>
								</c:forEach>
						</select></td>

					</tr>
					<tr>
						<td><input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeEduExp();" /></td>
						<td><input id="btnCancel" type="button"
							style="width: 300px; heigth: 30px;" value="取消 "
							onclick="cancelResumeEduExp();" /></td>
					</tr>
				</table>
			</div>
		</div>

		<!-- 实习与工作经历start -->
		<div id="resume_workexp">
			<input type="hidden" id="hdWorkEditId" value="0" />
			<h2>
				<strong>实习和工作经历</strong>
			</h2>
			<div id="resume_workexp_view_module">
				<c:if test="${resumeWorkExpSize<5}">
					<a id="aLinkWorkAdd" href="javascript:void(0)"
						onclick="addResumeWorkExp();">添加</a>
				</c:if>
				<div id="resume_workexp_view">
					<c:forEach items="${resumeInfo.resumeWorkExpDTOs}" var="item">
						<div id="work_exp_${item.id}" class="div_boder">
							<input type="hidden" id="work_exp_detail_${item.id}"
								companyName="${item.companyName}"
								positionName="${item.positionName}"
								startTimeDesc="${item.startTimeDesc}"
								endTimeDesc="${item.endTimeDesc}"
								workDescription="${item.workDescription}" value="${item.id}" />
							<span>公司名称：${item.companyName}</span><br /> <span>职位名称：${item.positionName}</span><br />
							<span>开始时间：${item.startTimeDesc}</span><br /> <span>结束时间：${item.endTimeDesc}</span><br />
							<span>工作描述：
								<div>${item.workDescription}</div>
							</span> <a href="javascript:void(0)" id="aLinkEdit_${item.id}"
								class="aLinkEdit" onclick="editResumeWorkExp('${item.id}')">编辑</a>
							<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
								class="aLinkDelete ml6"
								onclick="deleteResumeWorkExp('${item.id}')">删除</a>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_workexp_edit_module" class="div_boder">
				<table id="workExpEditFormTb">
					<tr>
						<td>公司名称：<br /> <input type="text"
							id="resumeWorkExpCompanyName" name="resumeWorkExpCompanyName"
							placeholder="公司名称" value="" class="txt" style="width: 300px" /></td>
						<td>职位名称：<br /> <input type="text"
							id="resumeWorkExpPositionName" name="resumeWorkExpPositionName"
							placeholder="职位名称" value="" class="txt" style="width: 300px" /></td>
					</tr>
					<tr>
						<td>开始时间：<br /> <input type="text"
							id="resumeWorkExpStartTime" name="resumeWorkExpStartTime"
							placeholder="开始时间" value="" class="txt" style="width: 300px"
							readonly="readonly" /></td>
						<td>结束时间：<br /> <input type="text" id="resumeWorkExpEndTime"
							name="resumeWorkExpEndTime" placeholder="结束时间" value=""
							class="txt" style="width: 300px" readonly="readonly" /></td>
					</tr>
					<tr>
						<td colspan="2">工作描述：<br /> <textarea id="resumeWorkExpDesc"
								style="width: 600px; height: 200px;">${item.workDescription}</textarea></td>
					</tr>
					<tr>
						<td><input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeWorkExp();" /></td>
						<td><input id="btnCancel" type="button"
							style="width: 300px; heigth: 30px;" value="取消 "
							onclick="cancelResumeWorkExp();" /></td>
					</tr>
				</table>
			</div>
		</div>

		<!-- 校园活动经历start -->
		<div id="resume_workexp">
			<input type="hidden" id="hdActivityEditId" value="0" />
			<h2>
				<strong>校园活动经历</strong>
			</h2>
			<div id="resume_activityexp_view_module">
				<c:if test="${resumeActivityExpSize<5}">
					<a id="aLinkActivityAdd" href="javascript:void(0)"
						onclick="addResumeActivityExp();">添加</a>
				</c:if>
				<div id="resume_activityexp_view">
					<c:forEach items="${resumeInfo.resumeActivityExpDTOs}" var="item">
						<div id="activity_exp_${item.id}" class="div_boder">
							<input type="hidden" id="activity_exp_detail_${item.id}"
								activityName="${item.activityName}"
								positionName="${item.positionName}"
								startTimeDesc="${item.startTimeDesc}"
								endTimeDesc="${item.endTimeDesc}"
								activityDesc="${item.activityDesc}" value="${item.id}" /> <span>组织或活动名称：${item.activityName}</span><br />
							<span>职位名称：${item.positionName}</span><br /> <span>开始时间：${item.startTimeDesc}</span><br />
							<span>结束时间：${item.endTimeDesc}</span><br /> <span>工作描述：
								<div>${item.activityDesc}</div>
							</span> <a href="javascript:void(0)" id="aLinkEdit_${item.id}"
								class="aLinkEdit" onclick="editResumeActivityExp('${item.id}')">编辑</a>
							<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
								class="aLinkDelete ml6"
								onclick="deleteResumeActivityExp('${item.id}')">删除</a>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_activityexp_edit_module" class="div_boder">
				<table id="activityExpEditFormTb">
					<tr>
						<td>组织或活动名称：<br /> <input type="text"
							id="resumeActivityExpName" name="resumeActivityExpName"
							placeholder="组织或活动名称" value="" class="txt" style="width: 300px" /></td>
						<td>职位名称：<br /> <input type="text"
							id="resumeActivityExpPositionName"
							name="resumeActivityExpPositionName" placeholder="职位名称" value=""
							class="txt" style="width: 300px" /></td>
					</tr>
					<tr>
						<td>开始时间：<br /> <input type="text"
							id="resumeActivityExpStartTime" name="resumeActivityExpStartTime"
							placeholder="开始时间" value="" class="txt" style="width: 300px"
							readonly="readonly" /></td>
						<td>结束时间：<br /> <input type="text"
							id="resumeActivityExpEndTime" name="resumeActivityExpEndTime"
							placeholder="结束时间" value="" class="txt" style="width: 300px"
							readonly="readonly" /></td>
					</tr>
					<tr>
						<td colspan="2">工作描述：<br /> <textarea
								id="resumeActivityExpDesc" style="width: 600px; height: 200px;">${item.activityDesc}</textarea></td>
					</tr>
					<tr>
						<td><input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeActivityExp();" /></td>
						<td><input id="btnCancel" type="button"
							style="width: 300px; heigth: 30px;" value="取消 "
							onclick="cancelResumeActivityExp();" /></td>
					</tr>
				</table>
			</div>
		</div>

		<!-- 项目经历start -->
		<div id="resume_projectexp">
			<input type="hidden" id="hdProjectEditId" value="0" />
			<h2>
				<strong>项目经历</strong>
			</h2>
			<div id="resume_projectexp_view_module">
				<c:if test="${resumeProjectExpSize<5}">
					<a id="aLinkProjectAdd" href="javascript:void(0)"
						onclick="addResumeProjectExp();">添加</a>
				</c:if>
				<div id="resume_projectexp_view">
					<c:forEach items="${resumeInfo.resumeProjectExpDTOs}" var="item">
						<div id="project_exp_${item.id}" class="div_boder">
							<input type="hidden" id="project_exp_detail_${item.id}"
								projectName="${item.projectName}"
								positionName="${item.positionName}"
								startTimeDesc="${item.startTimeDesc}"
								endTimeDesc="${item.endTimeDesc}"
								projectDesc="${item.projectDesc}" value="${item.id}" /> <span>项目名称：${item.projectName}</span><br />
							<span>职位名称：${item.positionName}</span><br /> <span>开始时间：${item.startTimeDesc}</span><br />
							<span>结束时间：${item.endTimeDesc}</span><br /> <span>工作描述：
								<div>${item.projectDesc}</div>
							</span> <a href="javascript:void(0)" id="aLinkEdit_${item.id}"
								class="aLinkEdit" onclick="editResumeProjectExp('${item.id}')">编辑</a>
							<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
								class="aLinkDelete ml6"
								onclick="deleteResumeProjectExp('${item.id}')">删除</a>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_projectexp_edit_module" class="div_boder">
				<table id="activityExpEditFormTb">
					<tr>
						<td>项目名称：<br /> <input type="text" id="resumeProjectExpName"
							name="resumeProjectExpName" placeholder="项目名称" value=""
							class="txt" style="width: 300px" /></td>
						<td>职位名称：<br /> <input type="text"
							id="resumeProjectExpPositionName"
							name="resumeProjectExpPositionName" placeholder="职位名称" value=""
							class="txt" style="width: 300px" /></td>
					</tr>
					<tr>
						<td>开始时间：<br /> <input type="text"
							id="resumeProjectExpStartTime" name="resumeProjectExpStartTime"
							placeholder="开始时间" value="" class="txt" style="width: 300px" /></td>
						<td>结束时间：<br /> <input type="text"
							id="resumeProjectExpEndTime" name="resumeProjectExpEndTime"
							placeholder="结束时间" value="" class="txt" style="width: 300px" /></td>
					</tr>
					<tr>
						<td colspan="2">工作描述：<br /> <textarea
								id="resumeProjectExpDesc" style="width: 600px; height: 200px;">${item.projectDesc}</textarea></td>
					</tr>
					<tr>
						<td><input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeProjectExp();" /></td>
						<td><input id="btnCancel" type="button"
							style="width: 300px; heigth: 30px;" value="取消 "
							onclick="cancelResumeProjectExp();" /></td>
					</tr>
				</table>
			</div>
		</div>

		<!-- 爱好特长start -->
		<div id="resume_hobbyspecial">
			<input type="hidden" id="hdHobbySpecialEditId" value="0" />
			<h2>
				<strong>爱好特长</strong>
			</h2>
			<div id="resume_hobbyspecial_view_module">
				<c:if test="${resumeHobbySpecialSize<5}">
					<a id="aLinkHobbySpecialAdd" href="javascript:void(0)"
						onclick="addResumeHobbySpecialExp();">添加</a>
				</c:if>
				<div id="resume_hobbyspecial_view">
					<c:forEach items="${resumeInfo.resumeHobbySpecialDTOs}" var="item">
						<div id="hobby_special_${item.id}" class="div_boder">
							<input type="hidden" id="hobby_special_detail_${item.id}"
								specialName="${item.specialName}"
								specialDesc="${item.specialDesc}" value="${item.id}" /> <span>爱好特长名称：${item.specialName}</span><br />
							<span>爱好特长描述：
								<div>${item.specialDesc}</div>
							</span> <a href="javascript:void(0)" id="aLinkEdit_${item.id}"
								class="aLinkEdit"
								onclick="editResumeHobbySpecialExp('${item.id}')">编辑</a> <a
								href="javascript:void(0)" id="aLinkDelete_${item.id}"
								class="aLinkDelete ml6"
								onclick="deleteResumeHobbySpecialExp('${item.id}')">删除</a>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_hobbyspecial_edit_module" class="div_boder">
				<table id="hobbySpecialEditFormTb">
					<tr>
						<td colspan="2">爱好特长名称：<br /> <input type="text"
							id="resumeHobbySpecialName" name="resumeHobbySpecialName"
							placeholder="爱好特长名称" value="${item.specialName}" class="txt"
							style="width: 300px" /></td>
					</tr>
					<tr>
						<td colspan="2">爱好特长描述：<br /> <textarea
								id="resumeHobbySpecialDesc" style="width: 600px; height: 200px;">${item.specialDesc}</textarea></td>
					</tr>
					<tr>
						<td><input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeHobbySpecialExp();" /></td>
						<td><input id="btnCancel" type="button"
							style="width: 300px; heigth: 30px;" value="取消 "
							onclick="cancelResumeHobbySpecialExp();" /></td>
					</tr>
				</table>
			</div>
		</div>

		<!-- 自定义板块start -->
		<div id="resume_custom">
			<input type="hidden" id="hdCustomEditId" value="0" />
			<h2>
				<strong>自定义板块</strong>
			</h2>
			<div id="resume_custom_view_module">
				<c:if test="${resumeCustomSize<5}">
					<a id="aLinkCustomAdd" href="javascript:void(0)"
						onclick="addResumeCustom();">添加</a>
				</c:if>
				<div id="resume_custom_view">
					<c:forEach items="${resumeInfo.resumeCustomDTOs}" var="item">
						<div id="hobby_custom_${item.id}" class="div_boder">
							<input type="hidden" id="hobby_custom_detail_${item.id}"
								name="${item.name}" description="${item.description}"
								value="${item.id}" /> <span>自定义板块名称：${item.name}</span><br />
							<span>自定义板块描述：
								<div>${item.description}</div>
							</span> <a href="javascript:void(0)" id="aLinkEdit_${item.id}"
								class="aLinkEdit" onclick="editResumeCustom('${item.id}')">编辑</a>
							<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
								class="aLinkDelete ml6"
								onclick="deleteResumeCustom('${item.id}')">删除</a>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_custom_edit_module" class="div_boder">
				<table id="customEditFormTb">
					<tr>
						<td colspan="2">自定义板块名称：<br /> <input type="text"
							id="resumeCustomName" name="resumeCustomName"
							placeholder="自定义板块名称" value="" class="txt" style="width: 300px" /></td>
					</tr>
					<tr>
						<td colspan="2">自定义板块描述：<br /> <textarea
								id="resumeCustomDesc" style="width: 600px; height: 200px;">${item.description}</textarea></td>
					</tr>
					<tr>
						<td><input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeCustom();" /></td>
						<td><input id="btnCancel" type="button"
							style="width: 300px; heigth: 30px;" value="取消 "
							onclick="cancelResumeCustom();" /></td>
					</tr>
				</table>
			</div>
		</div>

		<!-- 所获奖励start -->
		<div id="resume_prizeinfo">
			<input type="hidden" id="hdPrizeInfoEditId" value="0" />
			<h2>
				<strong>所获奖励</strong>
			</h2>
			<div id="resume_prizeinfo_view_module">
				<c:if test="${resumePrizeInfoSize<5}">
					<a id="aLinkPrizeInfoAdd" href="javascript:void(0)"
						onclick="addResumePrizeInfo();">添加</a>
				</c:if>
				<div id="resume_prizeinfo_view">
					<c:forEach items="${resumeInfo.resumePrizeInfoDTOs}" var="item">
						<div id="hobby_prizeinfo_${item.id}" class="div_boder">
							<input type="hidden" id="hobby_prizeinfo_detail_${item.id}"
								prizeName="${item.prizeName}" prizeLevel="${item.prizeLevel}"
								prizeLevelDesc="${item.prizeLevelDesc}"
								gainTimeDesc="${item.gainTimeDesc}" value="${item.id}" /> <span>奖项名称：${item.prizeName}</span><br />
							<span>奖项级别：${item.prizeLevelDesc}</span><br /> <span>获奖时间：${item.gainTimeDesc}</span><br />
							</span> <a href="javascript:void(0)" id="aLinkEdit_${item.id}"
								class="aLinkEdit" onclick="editResumePrizeInfo('${item.id}')">编辑</a>
							<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
								class="aLinkDelete ml6" onclick="deletePrizeInfo('${item.id}')">删除</a>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_prizeinfo_edit_module" class="div_boder">
				<table id="prizeInfoEditFormTb">
					<tr>
						<td colspan="2">奖项名称：<br /> <input type="text"
							id="resumePrizeName" name="resumePrizeName" placeholder="奖项名称"
							value="" class="txt" style="width: 300px" /></td>
					</tr>
					<tr>
						<td>奖项级别：<br /> <select id="resumePrizeLevel"
							name="resumePrizeLevel" class="mt10 wd220">
								<c:forEach items="${rewardLevelEnums}" var="item">
									<option value="${item.code}">${item.desc}</option>
								</c:forEach>
						</select>
						</td>
						<td>获奖时间：<br /> <input type="text" id="resumeGainTime"
							name="resumeGainTime" placeholder="获奖时间" value=""
							class="txt time" style="width: 300px" />
						</td>
					</tr>
					<tr>
						<td><input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumePrizeInfo();" /></td>
						<td><input id="btnCancel" type="button"
							style="width: 300px; heigth: 30px;" value="取消 "
							onclick="cancelResumePrizeInfo();" /></td>
					</tr>
				</table>
			</div>
		</div>

		<!-- 社交网络start -->
		<div id="resume_socialnet">
			<input type="hidden" id="hdSocialNetEditIds"
				value="${socialNetEditIds}" /> <input type="hidden"
				id="resumeSocialNetSize" value="${resumeSocialNetSize}" />
			<h2>
				<strong>社交网络</strong>
			</h2>
			<div id="resume_socialnet_view_module">
				<a href="javascript:void(0)" class="aLinkEdit"
					onclick="editResumeSocialNet();">编辑</a>
				<div id="resume_socialnet_view">
					<c:forEach items="${resumeInfo.resumeSocialNetDTOs}" var="item">
						<div id="hobby_socialnet_${item.id}" class="div_boder">
							<input type="hidden" id="hobby_socialnet_detail_${item.id}"
								account="${item.account}" accountDesc="${item.accountDesc}"
								url="${item.url}" value="${item.id}" /> <span>社交网络账号：${item.accountDesc}</span><br />
							<span>链接：${item.url}</span><br /> </span>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_socialnet_edit_module" class="div_boder">

				<table id="socialNetEditFormTb">
					<tr>
						<th>社交网络账号</th>
						<th>作品链接</th>
						<th></th>
					</tr>
					<c:forEach items="${resumeInfo.resumeSocialNetDTOs}" var="item">
						<tr id="socialNetRow_${item.id}">
							<td><select id="resumeSocialNetAccount"
								name="resumeSocialNetAccount" class="mt10 wd220">
									<c:forEach items="${socialNetworkEnums}" var="enumItem">
										<option value="${enumItem.code}"
											${enumItem.code==item.account?"selected=selected":""}>${enumItem.desc}</option>
									</c:forEach>
							</select></td>
							<td><input type="text" id="resumeSocialNetUrl"
								name="resumeSocialNetUrl" placeholder="作品链接" value="${item.url}"
								class="txt" style="width: 300px" /></td>

							<td><a href="javascript:void(0)" class="aLinkDelete"
								onclick="deleteResumeSocialNet('${item.id}');">删除</a></td>
						</tr>
					</c:forEach>
					<c:if test="${resumeSocialNetSize<5}">
						<tr id="btnAddSocialNet">
							<td colspan="3"><a href="javascript:void(0)"
								onclick="addResumeSocialNet();">添加</a></td>
						</tr>
					</c:if>
				</table>
				<div id="resume_socialnet_button_module">
					<div>
						<input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeSocialNet();" /> <input id="btnCancel"
							type="button"
							style="width: 300px; heigth: 30px; margin-left: 10px;"
							value="取消 " onclick="cancelResumeSocialNet();" />
					</div>
				</div>
			</div>
		</div>

		<!-- 专业技能-英语start -->
		<div id="resume_specialtyskill_en">
			<input type="hidden" id="resumeSpecialtySkillEnSize"
				value="${resumeSpecialtySkillEnSize}" />
			<h2>
				<strong>专业技能-英语</strong>
			</h2>
			<div id="resume_specialtyskill_en_view_module">
				<a href="javascript:void(0)" class="aLinkEdit"
					onclick="editResumeSpecialtySkillEn();">编辑</a>
				<div id="resume_specialtyskill_en_view">
					<c:forEach
						items="${resumeInfo.resumeSpecialtySkillDTO.enLangSkillDTOs}"
						var="item" varStatus="status">
						<div id="specialtyskill_en_${status.index}" class="div_boder">
							<input type="hidden"
								id="specialtyskill_en_detail_${status.index}" id="${item.id}"
								name="${item.name}" nameDesc="${item.nameDesc}"
								score="${item.score}" value="${item.id}" /> <span>英语考试名称：${item.nameDesc}</span><br />
							<span>考试成绩：${item.score}</span><br /> </span>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_specialtyskill_en_edit_module" class="div_boder">

				<table id="specialtySkillEnEditFormTb">
					<tr>
						<th>英语考试名称</th>
						<th>作品考试成绩</th>
						<th></th>
					</tr>
					<c:forEach
						items="${resumeInfo.resumeSpecialtySkillDTO.enLangSkillDTOs}"
						var="item" varStatus="status">
						<tr id="specialtySkillEnRow_${status.index}" class="spsken">
							<td><select id="resumeSpecialtySkillEnName"
								name="resumeSpecialtySkillEnName" class="mt10 wd220">
									<c:forEach items="${englishSkillEnums}" var="enumItem">
										<option value="${enumItem.code}"
											${enumItem.code==item.name?"selected=selected":""}>${enumItem.desc}</option>
									</c:forEach>
							</select></td>
							<td><input type="text" id="resumeSpecialtySkillEnScore"
								name="resumeSpecialtySkillEnScore" placeholder="考试成绩"
								value="${item.score}" class="txt" style="width: 300px" /></td>

							<td><a href="javascript:void(0)" class="aLinkDelete"
								onclick="deleteResumeSpecialtySkillEn('${status.index}');">删除</a></td>
						</tr>
					</c:forEach>
					<c:if test="${resumeSpecialtySkillEnSize<5}">
						<tr id="btnAddSpecialtySkillEn">
							<td colspan="3"><a href="javascript:void(0)"
								onclick="addResumeSpecialtySkillEn();">添加</a></td>
						</tr>
					</c:if>
				</table>
				<div id="resume_specialtyskill_en_button_module">
					<div>
						<input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeSpecialtySkillEn();" /> <input id="btnCancel"
							type="button"
							style="width: 300px; heigth: 30px; margin-left: 10px;"
							value="取消 " onclick="cancelResumeSpecialtySkillEn();" />
					</div>
				</div>
			</div>
		</div>

		<!-- 专业技能-其他语言start -->
		<div id="resume_specialtyskill_other">
			<input type="hidden" id="resumeSpecialtySkillOtherSize"
				value="${resumeSpecialtySkillOtherSize}" />
			<h2>
				<strong>专业技能-其他语言</strong>
			</h2>
			<div id="resume_specialtyskill_other_view_module">
				<a href="javascript:void(0)" class="aLinkEdit"
					onclick="editResumeSpecialtySkillOther();">编辑</a>
				<div id="resume_specialtyskill_other_view">
					<c:forEach
						items="${resumeInfo.resumeSpecialtySkillDTO.otherLangSkillDTOs}"
						var="item" varStatus="status">
						<div id="specialtyskill_other_${status.index}" class="div_boder">
							<input type="hidden"
								id="specialtyskill_other_detail_${status.index}" id="${item.id}"
								name="${item.name}" nameDesc="${item.nameDesc}"
								level="${item.level}" value="${item.id}" /> <span>其他语言名称：${item.nameDesc}</span><br />
							<span>考试成绩：${item.level}</span><br /> </span>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_specialtyskill_other_edit_module" class="div_boder">

				<table id="specialtySkillOtherEditFormTb">
					<tr>
						<th>其他语言名称</th>
						<th>其他语言掌握程度说明</th>
						<th></th>
					</tr>
					<c:forEach
						items="${resumeInfo.resumeSpecialtySkillDTO.otherLangSkillDTOs}"
						var="item" varStatus="status">
						<tr id="specialtySkillOtherRow_${status.index}" class="spskother">
							<td><select id="resumeSpecialtySkillOtherName"
								name="resumeSpecialtySkillOtherName" class="mt10 wd220">
									<c:forEach items="${otherLanguageSkillEnums}" var="enumItem">
										<option value="${enumItem.code}"
											${enumItem.code==item.name?"selected=selected":""}>${enumItem.desc}</option>
									</c:forEach>
							</select></td>
							<td><input type="text" id="resumeSpecialtySkillOtherLevel"
								name="resumeSpecialtySkillOtherLevel" placeholder="考试成绩"
								value="${item.level}" class="txt" style="width: 300px" /></td>

							<td><a href="javascript:void(0)" class="aLinkDelete"
								onclick="deleteResumeSpecialtySkillOther('${status.index}');">删除</a></td>
						</tr>
					</c:forEach>
					<c:if test="${resumeSpecialtySkillOtherSize<5}">
						<tr id="btnAddSpecialtySkillOther">
							<td colspan="3"><a href="javascript:void(0)"
								onclick="addResumeSpecialtySkillOther();">添加</a></td>
						</tr>
					</c:if>
				</table>
				<div id="resume_specialtyskill_other_button_module">
					<div>
						<input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeSpecialtySkillOther();" /> <input
							id="btnCancel" type="button"
							style="width: 300px; heigth: 30px; margin-left: 10px;"
							value="取消 " onclick="cancelResumeSpecialtySkillOther();" />
					</div>
				</div>
			</div>
		</div>

		<!-- 专业技能-计算机start -->
		<div id="resume_specialtyskill_computer">
			<input type="hidden" id="resumeSpecialtySkillComputerSize"
				value="${resumeSpecialtySkillComputerSize}" />
			<h2>
				<strong>专业技能-计算机</strong>
			</h2>
			<div id="resume_specialtyskill_computer_view_module">
				<a href="javascript:void(0)" class="aLinkEdit"
					onclick="editResumeSpecialtySkillComputer();">编辑</a>
				<div id="resume_specialtyskill_computer_view">
					<c:forEach
						items="${resumeInfo.resumeSpecialtySkillDTO.computerSkillDTOs}"
						var="item" varStatus="status">
						<div id="specialtyskill_computer_${status.index}"
							class="div_boder">
							<input type="hidden"
								id="specialtyskill_computer_detail_${status.index}"
								id="${item.id}" skill="${item.skill}"
								skillDesc="${item.skillDesc}" skillLevel="${item.skillLevel}"
								skillLevelDesc="${item.skillLevelDesc}" value="${item.id}" /> <span>计算机技能：${item.skillDesc}</span><br />
							<span>计算机熟练程度说明：${item.skillLevelDesc}</span><br /> </span>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_specialtyskill_computer_edit_module"
				class="div_boder">

				<table id="specialtySkillComputerEditFormTb">
					<tr>
						<th>计算机技能</th>
						<th>计算机熟练程度说明</th>
						<th></th>
					</tr>
					<c:forEach
						items="${resumeInfo.resumeSpecialtySkillDTO.computerSkillDTOs}"
						var="item" varStatus="status">
						<tr id="specialtySkillComputerRow_${status.index}"
							class="spskcomputer">
							<td><select id="resumeSpecialtySkillComputerSkill"
								name="resumeSpecialtySkillComputerSkill" class="mt10 wd220"><c:forEach
										items="${computerSkillMap}" var="m">
										<optgroup label="${m.key}">
											<c:forEach items="${m.value}" var="enumItem">
												<option value="${enumItem.code}"
													${enumItem.code==item.skill?"selected=selected":""}>${enumItem.desc}</option>
											</c:forEach>
										</optgroup>
									</c:forEach></select></td>
							<td><select id="resumeSpecialtySkillComputerSkillLevel"
								name="resumeSpecialtySkillComputerSkillLevel" class="mt10 wd220">
									<c:forEach items="${skillLevelEnums}" var="enumItem">
										<option value="${enumItem.code}"
											${enumItem.code==item.skillLevel?"selected=selected":""}>${enumItem.desc}</option>
									</c:forEach>
							</select><a href="javascript:void(0)" class="ml6 aLinkDelete"
								onclick="deleteResumeSpecialtySkillComputer('${status.index}');">删除</a></td>
						</tr>
					</c:forEach>
					<c:if test="${resumeSpecialtySkillComputerSize<5}">
						<tr id="btnAddSpecialtySkillComputer">
							<td colspan="3"><a href="javascript:void(0)"
								onclick="addResumeSpecialtySkillComputer();">添加</a></td>
						</tr>
					</c:if>
				</table>
				<div id="resume_specialtyskill_computer_button_module">
					<div>
						<input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeSpecialtySkillComputer();" /> <input
							id="btnCancel" type="button"
							style="width: 300px; heigth: 30px; margin-left: 10px;"
							value="取消 " onclick="cancelResumeSpecialtySkillComputer();" />
					</div>
				</div>
			</div>
		</div>

		<!-- 专业技能-证书start -->
		<div id="resume_specialtyskill_cert">
			<input type="hidden" id="resumeSpecialtySkillCertSize"
				value="${resumeSpecialtySkillCertSize}" />
			<h2>
				<strong>专业技能-证书</strong>
			</h2>
			<div id="resume_specialtyskill_cert_view_module">
				<a href="javascript:void(0)" class="aLinkEdit"
					onclick="editResumeSpecialtySkillCert();">编辑</a>
				<div id="resume_specialtyskill_cert_view">
					<c:forEach
						items="${resumeInfo.resumeSpecialtySkillDTO.certificateDTOs}"
						var="item" varStatus="status">
						<div id="specialtyskill_cert_${status.index}" class="div_boder">
							<input type="hidden"
								id="specialtyskill_cert_detail_${status.index}" id="${item.id}"
								diploma="${item.diploma}" value="${item.id}" /> <span>证书：${item.diploma}</span>
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_specialtyskill_cert_edit_module" class="div_boder">

				<table id="specialtySkillCertEditFormTb">
					<tr>
						<th>证书</th>
						<th></th>
					</tr>
					<c:forEach
						items="${resumeInfo.resumeSpecialtySkillDTO.certificateDTOs}"
						var="item" varStatus="status">
						<tr id="specialtySkillCertRow_${status.index}" class="spskcert">
							<td><input type="text" id="resumeSpecialtySkillCertName"
								name="resumeSpecialtySkillCertName" placeholder="证书名称"
								value="${item.diploma}" class="txt" style="width: 300px" /></td>

							<td><a href="javascript:void(0)" class="aLinkDelete"
								onclick="deleteResumeSpecialtySkillCert('${status.index}');">删除</a></td>
						</tr>
					</c:forEach>
					<c:if test="${resumeSpecialtySkillCertSize<5}">
						<tr id="btnAddSpecialtySkillCert">
							<td colspan="2"><a href="javascript:void(0)"
								onclick="addResumeSpecialtySkillCert();">添加</a></td>
						</tr>
					</c:if>
				</table>
				<div id="resume_specialtyskill_cert_button_module">
					<div>
						<input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeSpecialtySkillCert();" /> <input
							id="btnCancel" type="button"
							style="width: 300px; heigth: 30px; margin-left: 10px;"
							value="取消 " onclick="cancelResumeSpecialtySkillCert();" />
					</div>
				</div>
			</div>
		</div>

		<!-- 作品start -->
		<div id="resume_opusinfo">
			<input type="hidden" id="hdOpusInfoEditId" value="0" />
			<h2>
				<strong>作品</strong>
			</h2>
			<div id="resume_opusinfo_view_module">
				<c:if test="${resumeOpusInfoSize<5}">
					<a id="aLinkOpusInfoAdd" href="javascript:void(0)"
						onclick="addResumeOpusInfo();">添加</a>
				</c:if>
				<div id="resume_opusinfo_view">
					<c:forEach items="${resumeInfo.resumeOpusInfoDTOs}" var="item">
						<div id="opusinfo_${item.id}" class="div_boder">
							<a href="javascript:void(0)" id="aLinkEdit_${item.id}"
								class="aLinkEdit" onclick="editResumeOpusInfo('${item.id}')">编辑</a>
							<a href="javascript:void(0)" id="aLinkDelete_${item.id}"
								class="aLinkDelete ml6"
								onclick="deleteResumeOpusInfo('${item.id}')">删除</a> <input
								type="hidden" id="opusinfo_detail_${item.id}"
								opusName="${item.opusName}" opusUrl="${item.opusUrl}"
								opusPath="${item.opusPath}" value="${item.id}" /> <span>作品名称：${item.opusName}</span><br />
							<span>作品链接：${item.opusUrl} </span> <br />
							<c:if test="${!item.opusPath.isEmpty()}">
								<span>作品下载：<a href="${item.opusPath}">${item.opusPathFile}</a>
								</span>
								<br />
							</c:if>
							<hr style="width: auto; border-color: #9c9c9c;" />
						</div>
					</c:forEach>
				</div>
			</div>
			<div id="resume_opusinfo_edit_module" class="div_boder">
				<table id="opusInfoEditFormTb">
					<tr>
						<td>作品名称：<br /> <input type="text" id="resumeOpusInfoName"
							name="resumeOpusInfoName" placeholder="作品名称" value="" class="txt"
							style="width: 300px" /></td>
						<td>作品链接：<br /> <input type="text" id="resumeOpusInfoUrl"
							name="resumeOpusInfoUrl" placeholder="作品链接" value="" class="txt"
							style="width: 300px" /></td>
					</tr>
					<tr id="opusUploadTr">
						<td><a href="javascript:void(0)" id="aLinkUploadOpusInfo">上传作品</a></td>
						<td>
							<form id="opusUploadForm" enctype="multipart/form-data"
								class="opusUploadForm">
								<label>上传作品: <input type="file" name="file"
									id="resumeOpusUploadFile" /></label>
								<div id="uploads"></div>
								<input type="button" id="btnOpusUpload"
									onclick="uploadOpusFile();" value="上传">
							</form>
							<div id="errorMsg" style="font-size: 14px; color: red;"></div>
						</td>
					</tr>
					<tr>
						<td><input id="btnSave" type="button"
							style="width: 300px; heigth: 30px;" value="保存"
							onclick="saveResumeOpusInfo();" /></td>
						<td><input id="btnCancel" type="button"
							style="width: 300px; heigth: 30px;" value="取消 "
							onclick="cancelResumeOpusInfo();" /></td>
					</tr>
				</table>
			</div>
		</div>

		<select id="computerDropdownHtml">
		</select>
	</div>
</body>
</html>