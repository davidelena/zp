<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
<title>查看职位列表（企业）</title>
</head>
<body>
	<script type="text/javascript">
		var reloadList = function(type) {
			var pageType = $("#hdPageType").val();
			var url = "";
			if(pageType == "cp"){
				url = "recruit/my_joblist?id=" + $("#hdMemberId").val() + "&type=" + type+"&range=cp";
				$("#companyjoblist").css("background", "#ffffff");				
			}else{
				url = "recruit/my_joblist?id=" + $("#hdMemberId").val() + "&type=" + type;
				$("#myjoblist").css("background", "#ffffff");
			}
			window.location.href = url;
		};
		
		var offlineRecruitInfo = function(id){
			$.ajax({
				url:"recruit/offlineRecruitInfo",
				type:"post",
				dataType:"json",
				data:{
					rid:id
				},
				success:function(data){
					if(data.success){
						window.location.href="recruit/my_joblist?id=" + $("#hdMemberId").val() + "&type=2";
					}
				}
			});
		};
		
		var deleteRecruitInfo = function(id){
			if(confirm("确认是否删除该条目")){
				$.ajax({
					url:"recruit/deleteRecruitInfo",
					type:"post",
					dataType:"json",
					data:{
						rid:id
					},
					success:function(data){
						if(data.success){
							// $("#recruit_div_"+id).remove();
							var url = "recruit/my_joblist?id=" + $("#hdMemberId").val();
							var type = $("#hdType").val();
							var range = $("#hdPageType").val();
							if(type>0){
								url += ("&type=" + type);
							}
							if(range!=""){
								url += ("&range=" + range);
							}
							window.location.href = url;
						}
					}
				});
			}
		};
		
 		var copytoNewRecruitInfo = function(rid){
			var memberId = $("#hdMemberId").val();
			$.ajax({
				url : "recruit/copytoNewRecruitInfo",
				type : "post",
				dataType:"json",
				data : {
					id : memberId,
					rid : rid
				},
				success:function(data){
					if(data.success){
						window.location.href=data.url;
					}
				}
			});
		};
		
		var setSettings = function(pageType){
			if(pageType=="cp"){
				$("#hdPageType").val(pageType);
				reloadList(1);
			} else {
				$("#hdPageType").val(pageType);
				reloadList(4);
			}
		};

		var editRecruitInfo = function(id) {
			window.location.href = "recruit/job_basic_info?id=" + $("#hdMemberId").val() + "&rid=" + id;
		};

		$(function() {
			$(".aLink").css("color", "black");
			var aLink = $("#hdalink").val();
			$("#" + aLink).css("color", "blue");
			var pageType = $("#hdPageType").val();
			if(pageType == "cp"){
				$("#companyjoblist").css("background", "#ffffff");
				$("#myjoblist").css("background", "#6ac1ff");
				$("#my_companylist_div").show();
				$("#my_joblist_div").hide();
				$("#aLink_5").css("color","blue");
				$(".job_operation").hide();
			} else{
				$("#myjoblist").css("background", "#ffffff");
				$("#companyjoblist").css("background", "#6ac1ff");
				$("#my_joblist_div").show();
				$("#my_companylist_div").hide();
			}
			
		});
	</script>
	<%@include file="/WEB-INF/views/sliderbar_cp.jsp"%>
	<input id="hdMemberId" type="hidden" value="${memberDTO.memberId}" />
	<input id="hdalink" type="hidden" value="${aLinkId}" />
	<input id="hdType" type="hidden" value="${type}" />
	<input id="hdPageType" type="hidden" value="${range}" />

	<c:choose>
		<c:when test="${!hasRecruit}">
			<div class="coGJ_blank"></div>
			<!--主体-->
			<div class="coGJ_content">
				<div
					style="width: 500px; height: 100px; margin: 100px auto; border: 1px dashed #3c3c3c; line-height: 50px; text-align: center;">
					<p>您还没有发布职位，赶快发布您的第一条职位招募人才吧！</p>
					<p>
						<a href="recruit/job_basic_info?id=${memberDTO.memberId}&rid=0"><img
							src="images_zp/add_16.png" width="18" height="18" alt=""
							style="vertical-align: middle;" />发布第一条职位</a>
					</p>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="coSRPLY_topMenu">
				<div class="coSRPLY_findFulltime" id="myjoblist"
					style="background: white;">
					<a href="javascript:void(0)" onclick="setSettings('')">我管理的职位</a>
				</div>
				<div class="coSRPLY_findIntern" id="companyjoblist">
					<a href="javascript:void(0)" onclick="setSettings('cp')">公司所有职位</a>
				</div>
			</div>
			<div class="coSRPLY_container">
				<h1 class="coSRPLY_title">职位列表</h1>
				<div style="width: 800px; margin: 0 auto;">
					<div id="my_joblist_div" class="coSRPLY_top">
						<span><a id="aLink_4" href="javascript:void(0)"
							onclick="reloadList(4)" style="color: blue;" class="aLink">全部</a></span>
						| <span><a id="aLink_1" href="javascript:void(0)"
							onclick="reloadList(1)" class="aLink">正在招募</a></span> | <span><a
							href="javascript:void(0)" id="aLink_2" onclick="reloadList(2)"
							class="aLink">已下线</a></span> | <span><a id="aLink_3"
							href="javascript:void(0)" onclick="reloadList(3)" class="aLink">未发布</a></span>
					</div>
					<div id="my_companylist_div" class="coSRPLY_top"
						style="display: none;">
						<span><a id="aLink_5" href="javascript:void(0)"
							onclick="reloadList(1)" class="aLink">正在招募</a></span>
					</div>
					<c:forEach items="${recruitInfoDTOs}" var="item">
						<div class="coSRPLY_content" id="recruit_div_${item.id}">
							<h4>${item.positionName}</h4>
							<p class="coSRPLY_row">${item.recruitTypeDesc}|${item.minSalary}~${item.maxSalary}/月|${item.workCityDTO.city}|
								${item.resumeCount}份简历</p>
							<p class="coSRPLY_right job_operation">
								<c:choose>
									<c:when test="${item.positionStatus==3}">
										<a href="javascript:void(0)"
											onclick="editRecruitInfo(${item.id})">编辑职位</a>
									</c:when>
									<c:when test="${item.positionStatus==2}">
										<a href="javascript:void(0)"
											onclick="deleteRecruitInfo(${item.id})">删除</a>
										<a href="javascript:void(0)"
											onclick="copytoNewRecruitInfo(${item.id})">编辑为新的职位</a>
									</c:when>
									<c:otherwise>
										<a href="###">刷新</a>
										<a href="javascript:void(0)"
											onclick="offlineRecruitInfo(${item.id})">下线</a>
										<a href="javascript:void(0)"
											onclick="copytoNewRecruitInfo(${item.id})">编辑为新的职位</a>
									</c:otherwise>
								</c:choose>
							</p>
							<br />
							<p class="coSRPLY_row">${item.createTimeDisplay}发布|${item.validityTimeDisplay}截止</p>
							<span class="coSRPLY_right"> <c:choose>
									<c:when test="${item.positionStatus == 1}">
										<p class="coSRPLY_situation1">状态：正在招募</p>
									</c:when>
									<c:when test="${item.positionStatus == 2}">
										<p class="coSRPLY_situation2">状态：已下线</p>
									</c:when>
									<c:otherwise>
										<p class="coSRPLY_situation3">状态
										
										：未发布</p>
									</c:otherwise>
								</c:choose>
							</span>
						</div>
					</c:forEach>

					<div id="paginateDiv" class="Pagination_myself">${paginateHtml}</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>