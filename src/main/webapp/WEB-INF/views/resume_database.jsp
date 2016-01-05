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
<title>简历库搜索</title>
<script src="javascript/common_dialog.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/common_university_include.jsp"%>
	<%@include file="/WEB-INF/views/common_dialog_include.jsp"%>
	<%@include file="/WEB-INF/views/sliderbar_cp.jsp"%>
	<div class="coSR_container">
		<h1 class="coSR_title">简历库搜索</h1>
		<div class="coSR_top">
			<span><input type="text" id="filter_searchtxt"
				name="filter_searchtxt" placeholder="输入多个简历搜索关键词，以空格隔开，如上海 第一站"
				class="coSR_topsearch" />（包含任意一个关键词即可）</span> | <span><a
				href="javascript:;" onclick="clearSearchCondition()"><img
					src="images_zp/font_red_delete.png" width="16" height="16" alt="" />清空搜索条件</a></span>
		</div>
		<br />
		<div class="coSR_top2">
			<span> <input id="hdIsSearch" name="hdIsSearch" type="hidden"
				value="1" /> <input type="hidden" id="memberSchoolId"
				name="memberSchoolId" value="${model.schoolId}" /> <select
				id="filterUniversity" name="filterUniversity" class="coSR_option"
				onclick="showUniversityDialog()">
					<option value="-1">---请选择---</option>
			</select>
			</span> <span><select id="filterDiploma" name="filterDiploma"
				class="coSR_option">
					<option value="-1">---请选择---</option>
					<c:forEach items="${educationEnums}" var="item">
						<option value="${item.code}">${item.desc}</option>
					</c:forEach>
			</select> </span> <span><select id="filterGraduationYear"
				name="filterGraduationYear" class="coSR_option">
					<option value="-1">---请选择---</option>
					<c:forEach items="${graduationYears}" var="item">
						<option value="${item}">${item}</option>
					</c:forEach>
			</select> </span> <span><select id="filterMajorType" name="filterMajorType"
				class="coSR_option">
					<option value="-1">---请选择---</option>
					<c:forEach items="${specialityEnums}" var="item">
						<option value="${item.code}">${item.desc}</option>
					</c:forEach>
			</select> </span> <span><select id="filterInternshipExp"
				name="filterInternshipExp" class="coSR_option">
					<option value="-1">---请选择---</option>
					<c:forEach items="${internshipExpEnums}" var="item">
						<option value="${item.code}">${item.desc}</option>
					</c:forEach>
			</select> </span> <span><select id="filterGender" name="filterGender"
				class="coSR_option">
					<option value="-1">---请选择---</option>
					<c:forEach items="${genderEnums}" var="item">
						<option value="${item.code}">${item.desc}</option>
					</c:forEach>
			</select> </span> <span> <input type="hidden" id="hdIndustry"
				name="hdIndustry" value="" /> <select id="filter_industry"
				name="filter_industry" class="coSR_option"
				onclick="showIndustryDialog()">
					<option value="-1">---请选择---</option>
			</select>
			</span> <span><input type="hidden" id="hdCity" name="hdCity" value="" />
				<select id="filter_geoarea" name="filter_geoarea"
				class="coSR_option" onclick="showCityDialog()">
					<option value="-1">---请选择---</option>
			</select> </span> <span class="coSR_option"> <span class="coSR_search">
					<a href="javascript:;" onclick="searchResult()"
					class="coSR_searchBtn"><img src="images_zp/chance.png"
						width="30" height="30" alt="" /></a>
			</span>
			</span>
		</div>
		<input type="hidden" id="hdPageInfo" value="1"> <input
			type="hidden" id="hdChkAll" value="0" /> <input type="hidden"
			id="hdMemberId" value="${memberId}" />
		<div class="coSR_content">
			<h4>
				<input type="checkbox" name="facetoface" value="面谈"
					style="cursor: pointer;" onclick="checkAll()">全选 <span
					id="btnInvitePosition"><a onclick="invite()"
					style="display: inline; cursor: pointer;">邀请投递职位</a></span> <select
					id="dlMyJobList" class="coSR_option matuo">
					<option value="-1">--请选择--</option>
					<c:forEach items="${recruitInfoDTOs}" var="item">
						<option value="${item.id}">${item.positionName}</option>
					</c:forEach>
					<option value="1"></option>
				</select> <span id="btnIgnore" onclick="ignore()"><a
					style="display: inline; cursor: pointer;">忽略</a></span> <span
					class="coSR_multi pointer" onclick="batchDownload()"><img
					src="images_zp/document_letter_download.png" width="16" height="16"
					alt="" />批量下载</span>

			</h4>

		</div>
		<div class="coSR_twoC">
			<div class="coSR_leftC">
				<ul id="resumeinfo_ul">
				</ul>
			</div>
			<div id="paginate_html_div" class="Pagination_myself"></div>
		</div>
	</div>
	<div class="coSR_blank"></div>
	<script type="text/javascript">
		$(function() {
			searchResult();
			initUniversityDialog();
			initIndustryDialog();
			initCityDialog();
		});

		var batchDownload = function() {
			var checkedArr = [];
			$(".chk").each(function(idx, item) {
				if ($(item).prop("checked") == true) {
					var obj = $(item).attr("resumeId");
					checkedArr.push(obj);
				}
			});
			var resumeIds = checkedArr.join("_");
			if (resumeIds != "") {
				window.location.href = "resume/batchDownloadResume?ids=" + resumeIds;
			}
		};

		var invite = function() {
			var checkMemberIds = getCheckedMemberVals();
			var checkedMemberIdsStr = checkMemberIds.join(",");
			var recruitId = $("#dlMyJobList").val();

			$.ajax({
				url : "recruit/inviteGuysForJob",
				type : "post",
				dataType : "json",
				data : {
					memberIds : checkedMemberIdsStr,
					recruitId : recruitId
				},
				success : function(data) {
					if (data.success) {
						if (data.message != "") {
							alert(data.message);
						}
					} else {
						alert(data.message);
					}
				}
			});
		};

		var ignore = function() {
			var checkedIds = getCheckedVals();
			var checkedStr = checkedIds.join(",");
			$.ajax({
				url : "recruit/ignoreResumeInfo",
				type : "post",
				dataType : "json",
				data : {
					resumeIds : checkedStr
				},
				success : function(data) {
					if (data.success) {
						setTimeout(function() {
							searchResult();
						}, 1000);
					} else {
						alert(data.message);
					}
				}
			});
		};

		var getCheckedVals = function() {
			var checkedArr = $("#resumeinfo_ul").find("input[type='checkbox']:checked");

			var checkedIds = [];
			$(checkedArr).each(function(idx, item) {
				checkedIds.push($(item).attr("val"));
			});
			return checkedIds;
		};

		var getCheckedMemberVals = function() {
			var checkedArr = $("#resumeinfo_ul").find("input[type='checkbox']:checked");
			var checkedMemberIds = [];
			$(checkedArr).each(function(idx, item) {
				var memberId = $(item).attr("memberId");
				if (jQuery.inArray(memberId, checkedMemberIds) == -1) {
					checkedMemberIds.push(memberId);
				}
			});
			return checkedMemberIds;
		};

		var downloadResume = function(id) {
			window.location.href = "resume/downloadResume?resumeId=" + id;
		};

		var clearSearchCondition = function() {
			$("#filterUniversity").html("<option value='-1'>---请选择---</option>");
			$("#memberSchoolId").val(-1);
			$("#filterDiploma").val(-1);
			$("#filterGraduationYear").val(-1);
			$("#filterMajorType").val(-1);
			$("#filterInternshipExp").val(-1);
			$("#filterGender").val(-1);
			$("#filter_industry").html("<option value='-1'>---请选择---</option>");
			$("#hdIndustry").val("");
			resetIndustryCheckbox();
			$("#filter_geoarea").html("<option value='-1'>---请选择---</option>");
			$("#hdCity").val("");
			resetCityCheckbox();
		};

		var setPn = function(pn) {
			$("#hdPageInfo").val(pn);
			searchResult();
		};

		//搜索全局结果
		var searchResult = function() {
			var memberId = $("#hdMemberId").val();
			var filterUniversity = $("#memberSchoolId").val();
			var filterDiploma = $("#filterDiploma").val();
			var filterGraduationYear = $("#filterGraduationYear").val();
			var filterMajorType = $("#filterMajorType").val();
			var filterInternshipExp = $("#filterInternshipExp").val();
			var filterGender = $("#filterGender").val();
			var filterIndustries = $("#hdIndustry").val();
			var filterGeos = $("#hdCity").val();
			var filterSearchtxt = $("#filter_searchtxt").val();
			var pageStart = $("#hdPageInfo").val();

			$.ajax({
				url : "recruit/searchResumeInfos",
				type : "post",
				dataType : "json",
				data : {
					memberId : memberId,
					filterUniversity : filterUniversity,
					filterDiploma : filterDiploma,
					filterGraduationYear : filterGraduationYear,
					filterMajorType : filterMajorType,
					filterInternshipExp : filterInternshipExp,
					filterIndustries : filterIndustries,
					filterGeos : filterGeos,
					filterGender : filterGender,
					filterSearchtxt : filterSearchtxt,
					pageStart : pageStart
				},
				success : function(data) {
					if (data.success) {
						$("#resumeinfo_ul").html(data.resultHtml);
						$("#paginate_html_div").html(data.paginateHtml);
					} else {
						alert(data.message);
					}
				}
			});
		};

		var checkAll = function() {
			var type = $("#hdChkAll").val();
			$(".chk").each(function(idx, item) {
				if (type == "0") {
					$(this).prop("checked", true);
					$("#hdChkAll").val("1");
				} else {
					$(this).prop("checked", false);
					$("#hdChkAll").val("0");
				}
			});
		};

		var clearCheckbox = function() {
			$("#hdChkAll").val("0");
			$("#chkAll").prop("checked", false);
			$(".chk").each(function(idx, item) {
				$(item).prop("checked", false);
			});
		};
	</script>
</body>
</html>