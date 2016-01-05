/<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<title>查看实习职位（学生）</title>
<style type="text/css">
body,html {
	height: 100%;
}
</style>
<script src="javascript/common_dialog.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/common_dialog_include.jsp"%>
	<input type="hidden" id="hdMemberId" value="${memberId}" />
	<%@include file="/WEB-INF/views/sliderbar.jsp"%>

	<div class="stuJLI_topMenu">
		<div class="stuJLI_findFulltime">
			<a href="resume/search_joblist?id=${memberId}">找全职工作</a>
		</div>
		<div class="stuJLI_findIntern">
			<a href="resume/search_intern_joblist?id=${memberId}">找实习</a>
		</div>
	</div>
	<div class="stuJLI_blank"></div>
	<div class="stuJLI_center">
		<!--第一块-->
		<div class="stuJLI_content">
			<h4>实习筛选</h4>
			<br /> <br />
			<div class="stuJLI_choice">
				<ul>
					<li class="stuJLF_row"><select id="filter_internshipdays"
						name="filter_internshipdays" class="stuJLF_mn_new">
							<option value="-1">---请选择---</option>
							<c:forEach items="${internshipDaysEnums}" var="item">
								<option value="${item.code}">${item.desc}</option>
							</c:forEach>
					</select></li>
					<li class="stuJLF_row"><input type="hidden" id="hdIndustry"
						name="hdIndustry" value="" /><select id="filter_industry"
						name="filter_industry" class="stuJLF_mn_new"
						onclick="showIndustryDialog()">
							<option value="-1">---请选择---</option>
					</select></li>
					<li class="stuJLF_row"><input type="hidden" id="hdCity"
						name="hdCity" value="" /><select id="filter_geoarea"
						name="filter_geoarea" class="stuJLF_mn_new"
						onclick="showCityDialog()">
							<option value="-1">---请选择---</option>
					</select></li>
					<li class="stuJLF_row"><select id="filter_diploma"
						name="filter_diploma" class="stuJLF_mn_new">
							<option value="-1">---请选择---</option>
							<c:forEach items="${positionEducationEnums}" var="item">
								<option value="${item.code}">${item.desc}</option>
							</c:forEach>
					</select></li>
					<li class="stuJLI_row"
						style="font-size: 14px; text-align: left; letter-spacing: 6px; margin-top: 5px; margin-right: 0px; width: 200px;">
						<input id="filter_favorite" name="filter_favorite" type="checkbox"
						value="" />我的收藏
					</li>
					<li class="stuJLI_row"
						style="font-size: 14px; text-align: left; letter-spacing: 6px; margin-top: 5px; width: 200px;">
						<input id="filter_islatestest" name="filter_islatestest"
						type="checkbox" value="" />最新实习
					</li>
					<li class="stuJLI_hope"><input id="filter_searchtxt"
						name="filter_searchtxt" type="text"
						placeholder="请输入期望的实习岗位 公司名称 或职位名称" /></li>
					<a href="javascript:;" onclick="searchResult()"
						class="stuJLI_search">搜索</a>
				</ul>
			</div>
			<!--结果列表-->
			<input type="hidden" id="hdPageInfo" value="1">
			<div class="stuJLI_list">
				<ul id="fulltime_job_ul">
				</ul>
				<div id="paginate_html_div" class="Pagination_myself"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			searchResult();
			initIndustryDialog();
			initCityDialog();
		});

		//收藏职位
		var favoriteJob = function(id, type) {
			var memberId = $("#hdMemberId").val();

			$.ajax({
				url : "resume/favoriteJob",
				type : "post",
				dataType : "json",
				data : {
					memberId : memberId,
					recruitId : id,
					type : type
				},
				success : function(data) {
					if (data.success) {
						if (type == 1) {
							alert("收藏成功!");
						} else {
							alert("取消收藏!");
						}
						searchResult();
					} else {
						alert(data.message);
					}
				}
			});
		};

		var setPn = function(pn) {
			$("#hdPageInfo").val(pn);
			searchResult();
		};

		//搜索全局结果
		var searchResult = function() {
			var memberId = $("#hdMemberId").val();
			var filterInternshipDays = $("#filter_internshipdays").val();
			var filterIndustries = $("#hdIndustry").val();
			var filterGeoareas = $("#hdCity").val();
			var filterDiploma = $("#filter_diploma").val();
			var filterFavorite = $("#filter_favorite").prop("checked");
			var filterLastested = $("#filter_islatestest").prop("checked");
			var filterSearchtxt = $("#filter_searchtxt").val();
			var filterType = 2;

			$.ajax({
				url : "resume/searchJobList",
				type : "post",
				dataType : "json",
				data : {
					memberId : memberId,
					filterInternshipDays : filterInternshipDays,
					filterIndustries : filterIndustries,
					filterGeoareas : filterGeoareas,
					filterDiploma : filterDiploma,
					filterFavorite : filterFavorite,
					filterLastested : filterLastested,
					filterSearchtxt : filterSearchtxt,
					filterType : filterType
				},
				success : function(data) {
					if (data.success) {
						$("#fulltime_job_ul").html(data.resultHtml);
						$("#paginate_html_div").html(data.paginateHtml);
					} else {
						alert(data.message);
					}
				}
			});
		};
	</script>
</body>
</html>