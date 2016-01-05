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
<title>候选人管理</title>
<style type="text/css">
.pointer {
	cursor: pointer;
}
</style>
</head>
<body>
	<script type="text/javascript">
		var setFeedStatusSettings = function(status) {
			$("#hdFeedStatus").val(status);
			$(".feedStatus").css("color", "black");
			$("#link_"+status).css("color", "blue");
			loadMyApplicantResult();
		};

		var setPnSettings = function(pn) {
			$("#hdPn").val(pn);
			$(".pn").css("background", "#313131 none repeat scroll 0 0");
			$("#page_"+pn).css("background", "#676767 none repeat scroll 0 0");
			loadMyApplicantResult();
		};
		
		var loadMyApplicantResult = function() {
			var memberId = $("#hdMemberId").val();
			var feedStatus = $("#hdFeedStatus").val();
			var pn = $("#hdPn").val();
			var maPosition = $("#maPositionList").val();
			var maSchool = $("#maSchoolList").val();
			var maDiploma = $("#maDiplomaList").val();
			var maGraduationYear = $("#maGraduationYearList").val();
			var maMajor = $("#maMajorList").val();
			var maWorkExp = $("#maWorkExpList").val();
			var maGender = $("#maGenderList").val();		
			$.ajax({
				url: "recruit/loadMyApplicantResult",
				type: "post",
				dataType: "json",
				data:{
					id: memberId,
					pn: pn,
					filterFeedStatus: feedStatus,
					filterRecruitId: maPosition,
					filterSchool: maSchool,
					filterDiploma: maDiploma,
					filterGraduationYear: maGraduationYear,
					filterMajor: maMajor,
					filterWorkExp: maWorkExp,
					filterGender: maGender
				},
				success: function(data){
					if(data.success){
						$("#myApplicantSearchUl").html(data.resultHtml);
						$("#paginationDiv").html(data.paginateHtml);
						clearCheckbox();
					}
				}
			});
		};
		
		var checkAll = function(){
			var type = $("#hdChkAll").val();
			$(".chk").each(function(idx, item){
				if(type == "0"){
					$(this).prop("checked", true);
					$("#hdChkAll").val("1");
				} else {
					$(this).prop("checked", false);
					$("#hdChkAll").val("0");
				}
			});
		};
		
		var clearCheckbox = function(){
			$("#hdChkAll").val("0");
			$("#chkAll").prop("checked", false);
			$(".chk").each(function(idx, item){
				$(item).prop("checked", false);
			});
		};
		
		//更新单项申请人
		var updateMemberRecruit = function(id, status){
			$.ajax({
				url: "recruit/updateMemberRecruit",
				type:"post",
				dataType:"json",
				data:{
					id : id,
					status : status
				},
				success:function(data){
					if(data.success){
						setFeedStatusSettings(status);
						setPnSettings(1);
						searchMyApplicantResult();
					};
				}
			});
			
		};
		
		// 批量更新所有申请人
		var batchUpdateMemberRecruit = function(status){
			var checkedArgArr = [];
			$(".chk").each(function(idx, item){
				if($(item).prop("checked") == true){
					var obj = $(item).attr("val");
					var hdObj = $(item).parent().parent().find("input[type='hidden']");
					var args = $(hdObj).attr("val");
					checkedArgArr.push(args);
				}
			});
			
			var memberRecruitIds= checkedArgArr.join(",");
			
			$.ajax({
				url: "recruit/batchUpdateMemberRecruit",
				type:"post",
				dataType:"json",
				data:{
					memberRecruitIds: memberRecruitIds,
					status : status
				},
				success:function(data){
					if(data.success){
						setFeedStatusSettings(status);
						setPnSettings(1);
						searchMyApplicantResult();
					};
				}
			});
		};
		
		// 批量删除所有申请关系
		var batchDelete = function(){
			if(confirm("确认删除当前条目?")){
				var checkedArr = [];
				$(".chk").each(function(idx, item){
					if($(item).prop("checked") == true){
						var obj = $(item).attr("val");
						checkedArr.push(obj);
					}
				});
				
				var memberRecruitIds= checkedArr.join(",");
				
				if(memberRecruitIds!=""){
					$.ajax({
						url: "recruit/batchDeleteMemberRecruit",
						type:"post",
						dataType:"json",
						data:{
							memberRecruitIds: memberRecruitIds
						},
						success:function(data){
							if(data.success){
								setPnSettings(1);
								searchMyApplicantResult();
							};
						}
					});
				}
			}
		};
		
		var informInterview = function(id, memberId){
			window.open("recruit/inform_interview?id="+memberId + "&mrid="+id, "_blank");
		};
		
		var viewResume = function(resumeId){
			window.location.href="resume/resume_info_view?id=" + $("#hdMemberId").val() + "&rsid=" + resumeId;
		};
		
		var downloadResume = function(resumeId){
			window.location.href = "resume/downloadResume?resumeId=" + id;
		};
		
		var batchDownload = function(){
			var checkedArr = [];
			$(".chk").each(function(idx, item){
				if($(item).prop("checked") == true){
					var obj = $(item).attr("resumeId");
					checkedArr.push(obj);
				}
			});
			var resumeIds=checkedArr.join("_");
			if(resumeIds != null) {
				window.location.href="resume/batchDownloadResume?ids="+resumeIds;
			}
		};
		
		var initMyApplicantDiv = function(){
			$("#nopublishjob_div").hide();
			$("#noapplicant_div").hide();
			$("#applicantdata_div").hide();
			
			var pageType = $("#hdPageType").val();
			if(pageType == "nopublishjob"){
				$("#nopublishjob_div").show();
			} else if(pageType=="noapplicant"){
				$("#noapplicant_div").show();
			} else{
				$("#applicantdata_div").show();
			}
		};
		
		$(function(){
			initMyApplicantDiv();
		});
		
	</script>
	<%@include file="/WEB-INF/views/sliderbar_cp.jsp"%>
	<input type="hidden" id="hdFeedStatus" value="5" />
	<input type="hidden" id="hdMemberId" value="${memberId}" />
	<input type="hidden" id="hdPageType" value="${pageType}" />
	<%-- 	<c:choose>
		<c:when test="${pageType.equalsIgnoreCase(\"nopublishjob\")}"> --%>
	<div id="nopublishjob_div">
		<div class="coGJ_blank"></div>
		<!--主体-->
		<div class="coGJ_content">
			<div
				style="width: 500px; height: 100px; margin: 100px auto; border: 1px dashed #3c3c3c; line-height: 50px; text-align: center;">
				<p>您还没有发布职位，赶快发布您的第一条职位，收取简历吧!</p>
				<p>
					<a href="recruit/job_basic_info?id=${memberDTO.memberId}&rid=0"><img
						src="images_zp/add_16.png" width="18" height="18" alt=""
						style="vertical-align: middle;" />发布第一条职位</a>
				</p>
			</div>
		</div>
	</div>
	<%-- 		</c:when>
		<c:when test="${pageType.equalsIgnoreCase(\"noapplicant\")}"> --%>
	<div id="noapplicant_div">
		<div class="coGJ_blank"></div>
		<!--主体-->
		<div class="coGJ_content">
			<div
				style="width: 500px; height: 100px; margin: 100px auto; border: 1px dashed #3c3c3c; line-height: 50px; text-align: center;">
				<p>您发布的职位暂未收到简历，请耐心等待!</p>
				<p>
					<a href="javascript:;"><img src="images_zp/flag_1.png"
						width="18" height="18" alt="" />点此查看企业增值服务，收取更多简历(暂未开启)</a>
				</p>
			</div>
		</div>
	</div>
	<%-- 		</c:when>
		<c:otherwise> --%>
	<div id="applicantdata_div">
		<div class="coSP_container">
			<h1 class="coSP_title">候选人管理</h1>
			<div class="coSP_top">
				<span><a href="javascript:void(0)" id="link_5"
					onclick="setFeedStatusSettings(5)" class="feedStatus"
					style="color: blue;">全部候选人</a></span> | <span><a id="link_1"
					href="javascript:void(0)" onclick="setFeedStatusSettings(1)"
					class="feedStatus">未查看</a></span> | <span><a id="link_2"
					href="javascript:void(0)" onclick="setFeedStatusSettings(2)"
					class="feedStatus">待定</a></span> | <span><a id="link_3"
					href="javascript:void(0)" onclick="setFeedStatusSettings(3)"
					class="feedStatus">通知面试</a></span> | <span><a id="link_4"
					href="javascript:void(0)" onclick="setFeedStatusSettings(4)"
					class="feedStatus">不合适</a></span>
			</div>
			<br />
			<div class="coSP_top2">
				<span><select id="maPositionList" class="coSP_option">
						<option value="-1">--请选择--</option>
						<c:forEach items="${maPositionList}" var="item">
							<option value="${item.id}">${item.positionName}</option>
						</c:forEach>
				</select> </span> <span><select id="maSchoolList" class="coSP_option">
						<option value="-1">--请选择--</option>
						<c:forEach items="${maSchoolList}" var="item">
							<c:if test="${item.code<8}">
								<option value="${item.code}">${item.desc}</option>
							</c:if>
						</c:forEach>
				</select> </span> <span><select id="maDiplomaList" class="coSP_option">
						<option value="-1">--请选择--</option>
						<c:forEach items="${maDiplomaList}" var="item">
							<c:if test="${item.code>1}">
								<option value="${item.code}">${item.desc}</option>
							</c:if>
						</c:forEach>
				</select> </span> <span><select id="maGraduationYearList" class="coSP_option">
						<option value="-1">--请选择--</option>
						<c:forEach items="${maGraduationYearList}" var="item">
							<option value="${item}">${item}</option>
						</c:forEach>
				</select> </span> <span><select id="maMajorList" class="coSP_option">
						<option value="-1">--请选择--</option>
						<c:forEach items="${maMajorList}" var="item">
							<c:if test="${item.code<13}">
								<option value="${item.code}">${item.desc}</option>
							</c:if>
						</c:forEach>
				</select> </span> <span><select id="maWorkExpList" class="coSP_option">
						<option value="-1">--请选择--</option>
						<c:forEach items="${maWorkExpList}" var="item">
							<option value="${item.code}">${item.desc}</option>
						</c:forEach>
				</select> </span> <span><select id="maGenderList" class="coSP_option">
						<option value="-1">--请选择--</option>
						<c:forEach items="${maGenderList}" var="item">
							<option value="${item.code}">${item.desc}</option>
						</c:forEach>
				</select> </span> <span class="coSP_option"> <span class="coSP_search">
						<a href="javascript:void(0)" class="coSP_searchBtn"
						onclick="loadMyApplicantResult()"><img
							src="images_zp/chance.png" width="30" height="30" alt="" /></a>
				</span>
				</span>
			</div>
			<div class="coSP_content">
				<h4>
					<input type="hidden" id="hdChkAll" value="0" /> <input
						type="checkbox" id="chkAll" name="facetoface" value="面谈"
						onclick="checkAll()" class="pointer">全选 <span><a
						href="javascript:;" onclick="batchUpdateMemberRecruit(4)"
						style="display: inline; color: #fff;">不合适</a></span> <span><a
						href="javascript:;" onclick="batchUpdateMemberRecruit(2)"
						style="display: inline; color: #fff;">待定</a></span> <span
						class="coSP_multi"><a href="javascript:;"
						onclick="batchDownload()" style="display: inline; color: #fff;"><img
							src="images_zp/document_letter_download.png" width="16"
							height="16" alt="" />批量下载</a></span> <span class="coSP_multi"><a
						href="javascript:;" onclick="batchDelete()"
						style="display: inline; color: #fff;"><img
							src="images_zp/font_red_delete.png" width="16" height="16" alt="" />批量删除</a></span>
				</h4>
			</div>
			<div class="coSP_twoC">
				<div class="coSP_leftC">
					<ul id="myApplicantSearchUl">
						<c:forEach items="${myApplicantDTOs}" var="item">
							<li><input type="hidden" id="hd_${item.id}"
								memberId="${item.memberId}" resumeId="${item.resumeId}"
								recruitId="${item.recruitId}" val="${item.id}" /> <label
								class="coSP_checkbox-checkOne"> <input
									id="chk_${item.id}" class="pointer chk" type="checkbox"
									val="${item.id}" resumeId="${item.resumeId}" /> <i></i>
							</label>
								<div class="coSP_resume-img">
									<img width="50" height="50" src="${item.resumeAvatar}" />
								</div>
								<div class="coSP_resume-info">
									<h4 class="coSP_read">${item.memberName}</h4>
									<span class="coSP_resume-text" title="新媒体运营">应聘职位：${item.recruitName}</span><br />
									<span class="coSP_resume-text">${item.schoolName==""?"缺省":item.schoolName}
										| ${item.majorName==""?"缺省":item.majorName} |
										${item.diplomaDesc==""?"缺省":item.diplomaDesc} </span>
								</div>
								<div class="coSP_links">
									<span class="coSP_resume_refuse"><a href="javascript:;"
										onclick="viewResume(${item.resumeId})" class="coSP_look">查看简历</a></span>
									<span class="coSP_resume_refuse"><a
										href="resume/downloadResume?resumeId=${item.resumeId}"
										class="coSP_download">下载简历</a></span>
									<c:if test="${item.feedStatus<3}">
										<a href="javascript:;"
											onclick="informInterview(${item.id}, ${memberId})"
											class="coSP_resume_notice" title="发送短信邀请面试">通知面试</a>
										<a href="javascript:;"
											onclick="updateMemberRecruit(${item.id},4)"
											class="coSP_resume_refuse">不合适</a>
										<a href="javascript:;"
											onclick="updateMemberRecruit(${item.id},2)"
											class="coSP_resume_caninterview">待定</a>
									</c:if>
								</div></li>
						</c:forEach>
					</ul>
					<input type="hidden" id="hdPn" value="1" />
					<div id="paginationDiv" class="Pagination_myself">${paginateHtml}</div>
				</div>
				<div class="coSP_rightC"></div>
			</div>
		</div>
	</div>
	<%-- 		</c:otherwise>
	</c:choose> --%>
</body>
</html>