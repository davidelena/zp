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
<title>公司主页</title>
<style type="text/css">
h1,h2 {
	margin-top: 0;
}

p {
	color: #ccc;
	line-height: 1.5;
}

#page {
	width: 940px;
	height: 400px;
	padding: 20px;
	margin: 0 auto;
}

.panel {
	display: none;
	width: 220px;
	padding: 20px;
	background-color: #333;
	color: #fff;
	box-shadow: inset 0 0 5px 5px #222;
}

.menuOpened {
	width: 220px;
	height: 100%;
}

.menuOpened ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 220px;
	height: 100%;
	background: #313131;
}

.menuOpened ul li {
	width: 220px;
	margin: 0 auto;
	border-bottom: 1px solid #5c5c5c;
}

.menuOpened a {
	text-decoration: none;
	color: #eaebec;
	padding: 20px 0;
	position: relative;
}

.menuOpened p {
	width: 220px;
	display: inline;
	position: absolute;
	margin-top: 15px;
}

.menuOpened ul img {
	height: 50px;
	width: auto;
	margin: 0px 5px;
}
</style>
</head>
<body>
	<div id="sliderbar_cp_close" class="menuClosed">
		<ul>
			<li><a href="javascript:;"><img
					src="images_zp/singlelogo.png" class="logo"></a></li>
			<li><a href="javascript:;"><img src="images_zp/my.png"></a></li>
			<li><a href="javascript:;"><img src="images_zp/jianliku.png"></a></li>
			<li><a href="javascript:;"><img
					src="images_zp/shenqingr.png"></a></li>
			<li><a href="javascript:;"><img src="images_zp/mine.png"></a></li>
			<li><a href="javascript:;"><img
					src="images_zp/zhiweiliebiao.png"></a></li>
			<li><a href="javascript:;"><img src="images_zp/logout.png"></a></li>
		</ul>
	</div>

	<div id="left-panel" class="panel">
		<div id="sliderbar_cp_open" class="menuOpened">
			<ul>
				<li><a href="javascript:;"><img
						src="images_zp/stuloginlogo.png" style="height: 70px;"></a></li>
				<li><a
					href="recruit/company_view?id=${sessionScope.memberDTO.memberId}"><img
						src="images_zp/my.png">
						<p>我的账号</p></a></li>
				<li><a
					href="recruit/resume_database?id=${sessionScope.memberDTO.memberId}"><img
						src="images_zp/jianliku.png">
						<p>简历库</p></a></li>
				<li><a
					href="recruit/my_applicant?id=${sessionScope.memberDTO.memberId}"><img
						src="images_zp/shenqingr.png">
						<p>我的申请人</p></a></li>
				<li><a
					href="recruit/job_basic_info?id=${sessionScope.memberDTO.memberId}&rid=0"><img
						src="images_zp/mine.png">
						<p>发布新职位</p></a></li>
				<li><a
					href="recruit/my_joblist?id=${sessionScope.memberDTO.memberId}"><img
						src="images_zp/zhiweiliebiao.png">
						<p>我的职位列表</p></a></li>
				<li><a href="javascript:;" onclick="logout()"><img
						src="images_zp/logout.png">
						<p>退出登录</p></a></li>
			</ul>
		</div>
	</div>

	<div class="coCI_blank"></div>
	<!--主体-->
	<div id="page">
		<div id="share_companyinfo_dialog"
			style="text-align: center; vertical-align: middle;">
			<img id="share_cp_img" alt="公司主页二维码" src="" class="w300 h300" />
		</div>

		<input type="hidden" id="hdCompanyId" value="${companyInfo.id}" />
		<div class="coCI_container">
			<div class="coCI_all">
				<div class="coCI_center">
					<!--第一块-->
					<div class="coCI_content">
						<div class="coCI_jobName">
							<p>
							<h3>公司简介</h3>
							<a class="share" href="javascript:;" onclick="shareCompanyInfo()"><img
								src="images_zp/network-share.png" width="16" height="16"
								alt="分享" />分享公司主页</a>
							<c:if test="${memberInfo.source==2}">
								<a class="share" id="testlink" href="#left-panel"><img
									src="images_zp/pencil_2.png" width="16" height="16" alt="分享" />编辑公司主页</a>
							</c:if>
							</p>
							<p class="coCI_department">${companyInfo.synopsis}</p>
						</div>
					</div>
					<!--第二块-->
					<div class="coCI_content">
						<div class="coCI_basicInfo">
							<p>
							<h4>公司网站</h4>
							&nbsp;&nbsp;&nbsp;&nbsp;<span>公司官网：</span><span>${companyInfo.officialWebsite}</span>
							</p>
							<p>
								<br />
							<h4>公司微博</h4>
							&nbsp;&nbsp;&nbsp;&nbsp;<span>官方微博：</span><span>${companyInfo.weibo}</span>
							</p>
							<p>
								<br />
							<h4 style="vertical-align: top">公司微信</h4>
							&nbsp;&nbsp;&nbsp;&nbsp;<span style="vertical-align: top">微信二维码</span><span
								style="vertical-align: top"><img
								src="${companyInfo.wechat}" width="120" height="120" alt="qr" /></span>
							</p>
						</div>
					</div>

					<!--第三块-->
					<div class="coCI_content_new">
						<div class="coCI_discribe">
							<p>
							<h3>公司发展历程</h3>
							</p>
							<table style="font-size: 12px;">
								<tr>
									<td>${companyInfo.achievements}</td>
								</tr>
							</table>
							<h3>公司知名产品</h3>
							</p>
							<table style="font-size: 12px;">
								<tr>
									<td>${companyInfo.product}</td>
								</tr>
							</table>
							<c:choose>
								<c:when test="${memberInfo.source==2}">
									<p>
									<h3>个人账号信息</h3>
									</p>
									<div class="coCI_list">
										<table>
											<tr>
												<td><img src="images_zp/user.png" width="50"
													height="50" alt="1" />${memberInfo.name}</td>
												<td><img src="images_zp/iphone.png" width="50"
													height="50" alt="2" />${memberInfo.mobile}</td>
											</tr>
											<tr>
												<td><img src="images_zp/receive_mail.png" width="50"
													height="50" alt="3" />${memberInfo.commonEmail}</td>
												<td><img src="images_zp/id_card.png" width="50"
													height="50" alt="4" />${memberInfo.position}</td>
											</tr>
										</table>
										<%-- 									<ul>
										<li><img src="images_zp/user.png" width="50" height="50" alt="1" />${memberInfo.name}</li>
										<li><img src="images_zp/iphone.png" width="50" height="50" alt="2" />${memberInfo.mobile}</li>
										<li><img src="images_zp/receive_mail.png" width="50" height="50" alt="3" />${memberInfo.commonEmail}</li>
										<li><img src="images_zp/id_card.png" width="50" height="50" alt="4" />${memberInfo.position}</li>
									</ul> --%>
									</div>
								</c:when>
								<c:otherwise>
									<p>
									<h3>职位列表</h3>
									</p>
									<c:forEach items="${recruitInfos}" var="item">
										<div class="stuCI_list">
											<ul>
												<li class="stuCI_line"><img
													src="${item.companyInfoDTO.logo}"
													alt="${item.companyInfoDTO.name}" class="stuCI_duitang" />
													<div class="stuCI_jobInfo">
														<ul>
															<li><h5>${item.companyInfoDTO.name}</h5>&nbsp;&nbsp;
																<h5>${item.positionName}</h5>&nbsp;&nbsp;&nbsp;&nbsp;${item.validityTimeDesc}发布</li>

															<li class="stuCI_condition"><span><img
																	src="images_zp/flag_1.png" alt="位置" />${item.workCityDTO.city}</span>
																<span><img src="images_zp/money_yen.png" alt="月薪" />${item.minSalary}~${item.maxSalary}</span>
																<span><img src="images_zp/world.png" alt="互联网" />${item.companyInfoDTO.industryDesc}</span>
																<c:forEach items="${item.importantRemarkList}"
																	var="irItem">
																	<span class="stuCI_wa">${irItem.desc}</span>
																</c:forEach> <br /> <a
																href="recruit/job_view?id=${memberInfo.memberId}&rid=${item.id}"
																class="stuCI_checkOut">查看</a> <a href="#"
																class="stuCI_collect">收藏</a></li>
														</ul>
													</div></li>
											</ul>
										</div>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>
					</div>

				</div>
				<!--侧栏-->
				<div class="coCI_rightBar">
					<div class="coCI_jobLogo">
						<img src="${companyInfo.logo}" alt="第一站" />
						<p>上海时站科技有限公司</p>
					</div>
					<div class="coCI_scale">
						<p>
							<img src="images_zp/world.png" width="16" height="16"
								alt="hulianw" />${companyInfo.industryDesc}
						</p>
						<p>
							<img src="images_zp/user_male.png" width="16" height="16"
								alt="renshu" />${companyInfo.scaleDesc}
						</p>
					</div>
					<div class="coCI_jobMap">
						<p>
							公司地址：<br /> <br /> <span>${companyInfo.headerQuarters}</span> <br />
							<br /> <span>${companyInfo.detailAddress}</span>
						</p>
						<!--百度地图容器-->
						<!-- <div style="width: 150px; height: 150px; border: #ccc solid 1px;" class="coCI_dituContent">这里是地图</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="coCI_footer">
		第一站的微信号是：zhancampus，欢迎关注！<br />有问题？您可以电话联系我们：021-21458392；或致信邮箱us@zhancampus.com
	</div>
	<script type="text/javascript">
		$(function() {
			initShareCompanyInfoDialog();
			initEvent();
		});

		var initEvent = function() {
			$('#testlink').panelslider();
		};

		var initShareCompanyInfoDialog = function() {
			$("#share_companyinfo_dialog").dialog({
				autoOpen : false,
				title : "分享公司主页",
				show : {
					duration : 200
				},
				hide : {
					duration : 200
				},
				resizable : false,
				height : 380,
				width : 400,
				modal : true
			});
		};

		//分享生成二维码
		var shareCompanyInfo = function() {
			$.ajax({
				url : "recruit/generateQrcode",
				type : "post",
				dataType : "json",
				data : {
					cpid : $("#hdCompanyId").val()
				},
				success : function(data) {
					if (data.success) {
						$("#share_cp_img").attr("src", data.url);
						$("#share_companyinfo_dialog").dialog("open");
					} else {
						alert(data.message);
					}
				}
			});
		};
	</script>
</body>
</html>