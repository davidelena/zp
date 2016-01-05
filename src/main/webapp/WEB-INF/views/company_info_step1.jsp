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
<title>公司基本信息</title>
</head>
<body>
	<script type="text/javascript">
		//初始化对话框
		var initIndustryDialog = function() {
			$("#dialog_industry").dialog({
				autoOpen : false,
				title : "请选择期望行业",
				show : {
					duration : 200
				},
				hide : {
					duration : 200
				},
				resizable : false,
				height : 500,
				width : 750,
				modal : true,
				buttons : {
					"确认" : function() {
						saveIndustryData();
						$("#dialog_industry").dialog("close");
					},
					"取消" : function() {
						$("#dialog_industry").dialog("close");
					}
				}
			});
		};

		//行业
		var saveIndustryData = function() {
			var checkedItem = $("input[name='cpIndustry']:checked");
			$("#companyIndustry").val($(checkedItem).attr("desc"));
			$("#hdCompanyIndustry").val($(checkedItem).val());
		};

		var showIndustryDialog = function() {
			$("#dialog_industry").dialog("open");
			var id = $("#hdCompanyIndustry").val();
			$("input[name='cpIndustry']").each(function(index, item) {
				if (id == $(item).val()) {
					$(item).attr("checked", true);
				}
			});
		};

		//城市
		var initCityDialog = function() {
			$("#dialog_city").dialog({
				autoOpen : false,
				title : "请选择期望城市",
				show : {
					duration : 200
				},
				hide : {
					duration : 200
				},
				resizable : false,
				height : 500,
				width : 750,
				modal : true,
				buttons : {
					"确认" : function() {
						saveCityData();
						$("#dialog_city").dialog("close");
					},
					"取消" : function() {
						$("#dialog_city").dialog("close");
					}
				}
			});
		};

		var showCityDialog = function() {
			$("#dialog_city").dialog("open");
			var id = $("#hdCompanyHeaderQuartersId").val();
			$("input[name='cpCity']").each(function(index, item) {
				if (id == $(item).val()) {
					$(item).attr("checked", true);
				}
			});
		};

		//期望城市
		var saveCityData = function() {
			var checkedItem = $("input[name='cpCity']:checked");
			$("#companyHeaderQuarters").val($(checkedItem).attr("desc"));
			$("#hdCompanyHeaderQuartersId").val($(checkedItem).val());
		};

		//save company step1 info
		var saveCompanyBasicInfoStep1 = function() {
			$.ajax({
				url : "recruit/saveCompanyBasicInfoStep1",
				type : "post",
				dataType : "json",
				data : {
					memberId : $("#logoMemberId").val(),
					companyId : $("#logoCompanyId").val(),
					companyName : $("#companyName").val(),
					hdCompanyIndustry : $("#hdCompanyIndustry").val(),
					hdCompanyHeaderQuartersId : $("#hdCompanyHeaderQuartersId").val(),
					companyHeaderQuarters : $("#companyHeaderQuarters").val(),
					companyDetailAddress : $("#companyDetailAddress").val(),
					companyOfficialWebSite : $("#companyOfficialWebSite").val(),
					companyScale : $("#companyScale").val(),
					companySynopsis : $("#companySynopsis").val(),
					filename : $("#hdCompanyLogo").val()
				},
				success : function(data) {
					if (data.success) {
						hideAll();
						window.location.href = "recruit/company_info_step2?id=" + $("#logoMemberId").val();
					} else {
						hideAll();
						var errorArr = data.msgdata;
						$(errorArr).each(function(idx, item) {
							$("#" + item.validateKey).text(item.validateMsg);
							$("#" + item.validateKey).show();
						});
					}
				}
			});
		};

		var hideAll = function() {
			$("#companyName-error").hide();
			$("#companyIndustry-error").hide();
			$("#companyHeaderQuarters-error").hide();
			$("#companyScale-error").hide();
			$("#companySynopsis-error").hide();
		};

		var initLogoUploadForm = function() {
			var logoMemberId = $("#logoMemberId").val();
			var logoCompanyId = $("#logoCompanyId").val();
			var options = {
				url : "recruit/uploadCompanyLogo",
				type : "POST",
				data : {
					logoMemberId : logoMemberId,
					logoCompanyId : logoCompanyId,
				},
				dataType : "json",
				success : function(data) {
					if (data.success) {
						$("#companyLogoImg").attr("src", data.imgUrl);
						$("#hdCompanyLogo").val(data.filename);
					}
				}
			};
			$("#uploadCompanyLogo").ajaxForm(options);
		};

		// 上传作品文件
		var uploadLogo = function() {
			initLogoUploadForm();
			$("#uploadCompanyLogo").submit();
		};

		$(function() {
			initIndustryDialog();
			initCityDialog();
			var navi = navigator.userAgent.toLowerCase();
			if (navi.indexOf("chrome") != -1) {
				$("#filterDiv").css("height", "570px");
			} else {
				$("#filterDiv").css("height", "640px");
			}
		});
	</script>
	<c:choose>
		<c:when test="${memberDTO.source==2}">
			<%@include file="/WEB-INF/views/sliderbar_cp.jsp"%>
		</c:when>
		<c:otherwise>
			<%@include file="/WEB-INF/views/sliderbar.jsp"%>
		</c:otherwise>
	</c:choose>

	<div id="dialog_industry">
		<div style="display: block;">
			<table id="industryTable" class="tableTd" style="width: 100%;"
				border="1" cellspacing="1" cellpadding="0">

				<c:forEach items="${industryMap}" var="m">
					<tr>
						<td class="tableTd" class="ml6""><span class="ml6">${m.key}</span></td>
						<td class="tableTd">
							<div class="ml6 block">
								<table>
									<c:forEach items="${m.value}" var="industry" varStatus="item">
										<c:if test="${item.index%3==0}">
											<tr>
										</c:if>
										<td><input type="radio" name="cpIndustry"
											value="${industry.id}" desc="${industry.name}"
											style="margin-left: 6px; cursor: pointer;" />${industry.name}</td>
										<c:if test="${(item.index+1)%3==0}">
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

	<div id="dialog_city">
		<div style="display: block;">
			<table id="hotCityTable" class="tableTd" style="width: 100%;"
				border="1" cellspacing="1" cellpadding="0">
				<tr>
					<td colspan="4"><span class="ml6">热门城市</span></td>
				</tr>
				<c:forEach items="${hotCities}" var="city" varStatus="item">
					<c:if test="${item.index%4==0}">
						<tr>
					</c:if>
					<td><input type="radio" name="cpCity" value="${city.id}"
						desc="${city.name}" style="margin-left: 6px; cursor: pointer;" />${city.name}</td>
					<c:if test="${(item.index+1)%4==0}">
						</tr>
					</c:if>
				</c:forEach>
			</table>

			<table id="nonCityTable" class="tableTd"
				style="width: 100%; margin-top: 5px;" border="1" cellspacing="1"
				cellpadding="0">
				<tr>
					<td colspan="4"><span class="ml6">内地省份</span></td>
				</tr>
				<c:forEach items="${cityMap}" var="m">
					<tr>
						<td class="tableTd" class="ml6"><span class="ml6">${m.key}</span></td>
						<td class="tableTd">
							<div class="ml6 block">
								<table>
									<c:forEach items="${m.value}" var="city" varStatus="item">
										<c:if test="${item.index%8==0}">
											<tr>
										</c:if>
										<td><input type="radio" name="cpCity" value="${city.id}"
											desc="${city.name}"
											style="margin-left: 6px; cursor: pointer;" />${city.name}</td>
										<c:if test="${(item.index+1)%8==0}">
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>

			<table id="otherCityTable" class="tableTd"
				style="width: 100%; margin-top: 5px;" border="1" cellspacing="1"
				cellpadding="0">
				<tr>
					<td colspan="4"><span class="ml6">其他</span></td>
				</tr>
				<c:forEach items="${otherCities}" var="city" varStatus="item">
					<c:if test="${item.index%4==0}">
						<tr>
					</c:if>
					<td><input type="radio" name="cpCity" value="${city.id}"
						desc="${city.name}" style="margin-left: 6px; cursor: pointer;" />${city.name}</td>
					<c:if test="${(item.index+1)%4==0}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>
	<div class="coLGINSUC_body">
		<div class="coLGINSUC_welcome">
			<div class="fix_sliderbar">
				<ul>
					<li><h1>
							欢迎来到第一站！
							<h1></li>
					<li><h5>恭喜您注册成功！请完成基本信息</h5></li>
				</ul>
			</div>
		</div>

		<!--主体-->
		<div class="coLGINSUC_content">
			<input id="logoMemberId" name="logoMemberId" type="hidden"
				value="${memberId}" /> <input id="logoCompanyId"
				name="logoCompanyId" type="hidden" value="${companyInfo.id}" /> <input
				type="hidden" id="hdCompanyLogo" value="${companyLogo}">
			<form id="uploadCompanyLogo" method="post"
				enctype="multipart/form-data">
				<c:if test="${!isFirst}">
					<div id="filterDiv"
						style="position: absolute; width: 700px; height: 570px; margin: 0 auto; background-color: rgba(105, 105, 105, 0.3); filter: alpha(opacity = 50) ； z-index:9;"></div>
				</c:if>

				<fieldset>
					<legend>第一步：公司基本信息（必填）</legend>
					公司全称：<input type="text" id="companyName" name="companyName"
						value="${companyInfo.name}" class="coLGINSUC_mn"
						placeholder="名字需与贵公司营业执照上名称一致" /> <label id="companyName-error"
						class="error" for="companyName"></label> <br /> 公司行业： <input
						type="text" id="companyIndustry" name="companyIndustry"
						placeholder="公司行业" value="${companyInfo.industryDesc}"
						readonly="readonly" class="coLGINSUC_mn"
						onclick="showIndustryDialog();" /> <input type="hidden"
						id="hdCompanyIndustry" name="hdCompanyIndustry"
						value="${companyInfo.industry}"> <label
						id="companyIndustry-error" class="error" for="companyIndustry"></label><br />
					公司总部： <input type="text" id="companyHeaderQuarters"
						readonly="readonly" name="companyHeaderQuarters"
						placeholder="公司总部城市" value="${companyInfo.headerQuarters}"
						class="coLGINSUC_mn" onclick="showCityDialog();" /> <input
						type="hidden" id="hdCompanyHeaderQuartersId"
						name="hdCompanyHeaderQuartersId"
						value="${companyInfo.headerQuartersId}"> <input
						type="text" id="companyDetailAddress" name="companyDetailAddress"
						value="${companyInfo.detailAddress}" class="coLGINSUC_mn"
						placeholder="具体地址，选填" /><br /> <label
						id="companyHeaderQuarters-error" class="error ml73"
						for="companyHeaderQuarters"></label> <br /> 公司Logo：<img
						id="companyLogoImg" src="${companyInfo.logo}" width="100"
						height="100" alt="公司logo图片" style="margin-top: 5px;" /><input
						id="logoFile" type="file" name="file"
						style="margin-left: 10px; padding-bottom: 3px;" /><br /> <input
						type="button" name="name" value="上传Logo" onclick="uploadLogo()"
						style="margin-left: 75px;" /><br /> 公司官网：<input type="text"
						id="companyOfficialWebSite" name="companyOfficialWebSite"
						value="${companyInfo.officialWebsite}" class="coLGINSUC_mn"
						placeholder="请填写公司官网（选填）" /><br /> 公司规模：<select
						id="companyScale" name="companyScale" class="coLGINSUC_mn">
						<c:forEach items="${companySizeEnums}" var="item">
							<option value="${item.code}"
								${item.code==companyInfo.scale?"selected=selected":""}>${item.desc}</option>
						</c:forEach>
					</select><label id="companyScale-error" class="error" for="companyScale"></label><br />
					<span>公司简介：</span>
					<textarea rows="10" cols="30" id="companySynopsis"
						name="companySynopsis"
						placeholder="您可以简要介绍公司核心业务、发展现状、公司愿景、企业文化、创始人情况等。1000字以内。">${companyInfo.synopsis}</textarea>
					<br /> <label id="companySynopsis-error" class="error ml73"
						for="companySynopsis"></label>
				</fieldset>
				<div class="coLGINSUC_bottom">
					<input id="btnFinishToStep2" type="button" value="下一步：继续完成公司信息"
						onclick="saveCompanyBasicInfoStep1()" class="coLGINSUC_save" />
				</div>
			</form>
		</div>
	</div>
	<!--底部-->
	<div class="coLGINSUC_footer">
		第一站的微信号是：zhancampus，欢迎关注！<br />有问题？您可以电话联系我们：021-21458392；或致信邮箱us@zhancampus.com
	</div>
</body>
</html>