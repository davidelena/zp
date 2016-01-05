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
<title>查看简历反馈</title>
<style type="text/css">
body {
	height: 100%;
}
</style>
</head>
<body>
	<script type="text/javascript">
		var reloadList = function(type) {
			var memberId = $("#hdMemberId").val();
			window.location.href = "resume/my_apply?id=" + memberId + "&type=" + type;
		};

		$(function() {
			var type = $("#hdType").val();
			$(".link").css("color", "black");
			$("#aLink_" + type).css("color", "blue");
		});
	</script>
	<input id="hdMemberId" type="hidden" value="${memberDTO.memberId}" />
	<input id="hdType" type="hidden" value="${type}" />
	<%@include file="/WEB-INF/views/sliderbar.jsp"%>
	<div class="container">
		<h1 class="title">申请状态</h1>
		<div style="width: 800px; margin: 0 auto;">
			<div class="top">
				<span><a href="javascript:void(0)" id="aLink_all" onclick="reloadList('all')" style="color: blue;"
					class="link">全部</a></span> | <span><a id="aLink_unnotify" href="javascript:void(0)"
					onclick="reloadList('unnotify')" class="link">未通知</a></span> | <span><a href="javascript:void(0)" id="aLink_3"
					onclick="reloadList('3')" class="link">通知面试</a></span> | <span><a id="aLink_4" href="javascript:void(0)"
					onclick="reloadList('4')" class="link">不合适</a></span>
			</div>
			<c:forEach items="${memberRecruitDTOs}" var="item">
				<div class="stuSR_content">
					<h4>${item.positionName}</h4>
					<div class="stuSR_condition">
						<p class="stuSR_row">${item.companyName}</p>
						<p class="stuSR_right">2015-08-14 16:02</p>
						<br />
						<p class="stuSR_row">使用简历：${item.resumeName}</p>
						<c:choose>
							<c:when test="${item.feedStatus == 3}">
								<p class="stuSR_right" style="color: #2C9350;">${item.feedStatusDesc}</p>
							</c:when>
							<c:when test="${item.feedStatus == 4}">
								<p class="stuSR_right" style="color: #E50308;">${item.feedStatusDesc}</p>
							</c:when>
							<c:otherwise>
								<p class="stuSR_right" style="color: #FF9524;">状态： 未通知</p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:forEach>
			<div class="Pagination_myself">${paginateHtml}</div>
		</div>
	</div>
</body>
</html>