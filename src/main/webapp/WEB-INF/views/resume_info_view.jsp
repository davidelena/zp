<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include_zp.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords"
	content="校园招聘，校招，宣讲会，招聘会，大学生，优质招聘，优质信息，实习，第一站，精准推荐" />
<meta name="description"
	content="第一站zhancampus.com为大学生推荐最新、最优质的校招和实习信息，帮助大学生迈出职场第一步！" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预览简历</title>
</head>

<body>
	<c:choose>
		<c:when test="${memberInfo.source==1}">
			<%@include file="/WEB-INF/views/sliderbar.jsp"%>
		</c:when>
		<c:otherwise>
			<%@include file="/WEB-INF/views/sliderbar_cp.jsp"%>
		</c:otherwise>
	</c:choose>
	<div class="container">
		<h1 class="title">简历预览</h1>
		<div class="all">
			<div class="center">
				<!--第一块-->
				<div class="content">
					<div class="stuMRF_resumeName">
						<h4>${resumeInfo.resumeName}</h4>
					</div>
				</div>
				<!--第二块-->
				<div class="content">
					<div class="stuMRF_basicInfo"
						style="height: auto; min-height: 300px;">
						<p>基本信息</p>
						<div class="text">
							<h1>${memberInfo.name}</h1>
							<ul style="font-size: 16px;">
								<li><img src="images_zp/user_male.png" alt="性别"
									class="icon" />${resumeInfo.sexDesc}</li>
								<li style="height: auto; width: 300px; word-wrap: break-word;"><img
									src="images_zp/house.png" alt="学校" class="icon" />${memberInfo.schoolId>0?memberInfo.schoolDesc:memberInfo.school}|${resumeInfo.major}|${resumeInfo.diplomaDesc}|${resumeInfo.graduationYear}年毕业</li>
								<li><img src="images_zp/phone.png" alt="手机" class="icon" />${memberInfo.mobile}</li>
								<li><img src="images_zp/mail.png" alt="邮箱" class="icon" />${memberInfo.commonEmail}</li>
							</ul>
							<div class="pic">
								<img src="${resumeInfo.avatar}" />
							</div>
						</div>
					</div>
				</div>
				<!--第三块-->
				<c:if test="${resumeInfo.resumeEducationExpDTOs.size()>0}">
					<div class="content">
						<div class="stuMRF_education"
							style="height: auto; min-height: 100px;">
							<p>教育经历</p>
							<ul style="font-size: 16px;">
								<c:forEach items="${resumeInfo.resumeEducationExpDTOs}"
									var="item">
									<li><span>${item.school}<span
											style="margin-right: 3px"></span>${item.academicStartsDesc}~${item.graduationTimeDesc}<span
											style="margin-right: 3px"></span>${item.diplomaDesc}<span
											style="margin-right: 3px"></span>${item.major} <span
											style="margin-right: 3px"></span>${item.majorTypeDesc} <span
											style="margin-right: 3px"></span>${item.scoreTopDesc}
									</span></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<!--第四块-->
				<c:if test="${resumeInfo.resumeWorkExpDTOs.size()>0}">
					<div class="content">
						<div class="stuMRF_job" style="height: auto; min-height: 150px;">
							<p>实习/工作经历</p>
							<ul style="font-size: 16px;">
								<c:forEach items="${resumeInfo.resumeWorkExpDTOs}" var="item">
									<li><span>${item.companyName}<span
											style="margin-right: 5px"></span>${item.startTimeDesc}-${item.endTimeDesc}<span
											style="margin-right: 5px"></span>${item.positionName}</span></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<!--第五块-->
				<c:if
					test="${resumeInfo.resumeSpecialtySkillDTO.enLangSkillDTOs.size()>0||resumeInfo.resumeSpecialtySkillDTO.otherLangSkillDTOs.size()>0
					||resumeInfo.resumeSpecialtySkillDTO.computerSkillDTOs.size()>0||resumeInfo.resumeSpecialtySkillDTO.certificateDTOs.size()>0}">
					<div class="content">
						<div class="stuMRF_skill" style="height: auto; min-height: 150px;">
							<p>专业技能</p>
							<ul style="font-size: 16px;">

								<li><span><h4>英语</h4> <c:forEach
											items="${resumeInfo.resumeSpecialtySkillDTO.enLangSkillDTOs}"
											var="item">
											<span style="margin-right: 5px"></span>${item.nameDesc}
									${item.score}<span style="margin-right: 5px"></span>|
						</c:forEach> </span></li>
								<li><span><h4>其他语言</h4> <c:forEach
											items="${resumeInfo.resumeSpecialtySkillDTO.otherLangSkillDTOs}"
											var="item">
											<span style="margin-right: 5px"></span>${item.nameDesc}
									${item.level}<span style="margin-right: 5px"></span>|
						</c:forEach> </span></li>
								<li><span><h4>计算机</h4> <c:forEach
											items="${resumeInfo.resumeSpecialtySkillDTO.computerSkillDTOs}"
											var="item">
											<span style="margin-right: 5px"></span>${item.skillDesc}
									${item.skillLevelDesc}<span style="margin-right: 5px"></span>|
						</c:forEach> </span></li>
								<li><span><h4>证书</h4> <c:forEach
											items="${resumeInfo.resumeSpecialtySkillDTO.certificateDTOs}"
											var="item">
											<span style="margin-right: 5px"></span>${item.diploma}<span
												style="margin-right: 5px"></span>|
						</c:forEach> </span></li>
							</ul>
						</div>
					</div>
				</c:if>
				<!--第六块-->
				<c:if test="${resumeInfo.resumeActivityExpDTOs.size()>0}">
					<div class="content">
						<div class="stuMRF_campus"
							style="height: auto; min-height: 150px;">
							<p>校园活动经历</p>
							<ul style="font-size: 16px;">
								<c:forEach items="${resumeInfo.resumeActivityExpDTOs}"
									var="item">
									<li><span>${item.activityName}<span
											style="margin-right: 5px"></span>${item.startTimeDesc}-${item.endTimeDesc}<span
											style="margin-right: 5px"></span>${item.positionName}</span></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<!--第七块-->
				<c:if test="${resumeInfo.resumePrizeInfoDTOs.size()>0}">
					<div class="content">
						<div class="stuMRF_award" style="height: auto; min-height: 150px;">
							<p>所获奖励</p>
							<ul style="font-size: 16px;">
								<c:forEach items="${resumeInfo.resumePrizeInfoDTOs}" var="item">
									<li><span>${item.prizeName}<span
											style="margin-right: 5px"></span>${item.gainTimeDesc}<span
											style="margin-right: 5px"></span><span
											style="margin-right: 5px"></span>${item.prizeLevelDesc}</span></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<!--第八块-->
				<c:if test="${resumeInfo.resumeProjectExpDTOs.size()>0}">
					<div class="content">
						<div class="stuMRF_project"
							style="height: auto; min-height: 150px;">
							<p>项目经历</p>
							<ul style="font-size: 16px;">
								<c:forEach items="${resumeInfo.resumeProjectExpDTOs}" var="item">
									<li><span>${item.projectName}<span
											style="margin-right: 5px"></span>${item.startTimeDesc}-${item.endTimeDesc}<span
											style="margin-right: 5px"></span>${item.positionName}</span></li>
									<li>${item.projectDesc}</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<!--第九块-->
				<c:if test="${resumeInfo.resumeHobbySpecialDTOs.size()>0}">
					<div class="content">
						<div class="stuMRF_interest"
							style="height: auto; min-height: 150px;">
							<p>爱好特长</p>
							<ul style="font-size: 16px;">
								<c:forEach items="${resumeInfo.resumeHobbySpecialDTOs}"
									var="item">
									<li><span><h4>${item.specialName}</h4> <span
											style="margin-right: 5px"></span>${item.specialDesc}</span></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<!--第十块-->
				<c:if test="${resumeInfo.resumeSocialNetDTOs.size()>0}">
					<div class="content">
						<div class="stuMRF_net" style="height: auto; min-height: 150px;">
							<p>社交网络</p>
							<ul style="font-size: 16px;">
								<c:forEach items="${resumeInfo.resumeSocialNetDTOs}" var="item">
									<li><span><h4>${item.accountDesc}</h4> <span
											style="margin-right: 5px"></span>${item.url}</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<!--第十一块-->
				<c:if test="${resumeInfo.resumeOpusInfoDTOs.size()>0}">
					<div class="content">
						<div class="stuMRF_myWork"
							style="height: auto; min-height: 150px;">
							<p>我的作品</p>
							<ul style="font-size: 16px;">
								<c:forEach items="${resumeInfo.resumeOpusInfoDTOs}" var="item">
									<li><span>${item.opusName}<span
											style="margin-right: 5px"></span>${item.opusUrl}<span
											style="margin-right: 5px"></span><a href="${item.opusPath}">${item.opusPathFile}</a></span></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<!--第十二块-->
				<c:if test="${resumeInfo.resumeCustomDTOs.size()>0}">
					<div class="content">
						<div class="stuMRF_self" style="height: auto; min-height: 150px;">
							<p>自定义版块</p>
							<ul style="font-size: 16px;">
								<c:forEach items="${resumeInfo.resumeCustomDTOs}" var="item">
									<li><span>${item.name}<span
											style="margin-right: 5px"></span>|<span
											style="margin-right: 5px"></span>${item.description}</span></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<!--第十三块-->
				<c:if test="${!resumeInfo.uploadPath.isEmpty()}">
					<div class="content">
						<div class="stuMRF_attachResume">
							<p>附件简历</p>
							<ul style="font-size: 16px;">
								<li><span><a id="attachment_file_url"
										href="${resumeInfo.uploadPath}">${resumeInfo.uploadPathFile}</a></span></li>
							</ul>
						</div>
					</div>
				</c:if>
				<!--保存简历按钮-->
				<div class="content" style="background: none;">
					<div class="stuMRF_savebtn">
						<a href="javascript:;" class="save"
							onclick="downloadResume(${resumeInfo.id})">下载简历到桌面</a>
					</div>
				</div>
			</div>
			<!--侧栏-->
			<div class="rightBar">
				<!-- 				<div class="attach">
					<img src="images_zp/send_mail.png" alt="upload" /> <a href="#">点击上传附件简历</a>
				</div> -->
				<div class="rightLogo">
					<img src="images_zp/stuloginlogo.png" alt="logo" />
				</div>
				<div class="qrCode">
					<img src="images_zp/erweima_fp.jpg" alt="qrcode" /> <img
						src="images_zp/stuloginwelcome.png" alt="welcome" />
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var downloadResume = function(id) {
			window.location.href = "resume/downloadResume?resumeId=" + id;
		};
	</script>
</body>
</html>


