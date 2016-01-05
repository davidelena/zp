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
<title>我的简历</title>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			initPage();
		});

		var demandType = '${studentInfoDTO.demandType}';

		//初始化页面时间
		var initPage = function() {
			initIndustryDialog();
			initCityDialog();

			$("#sysEdit").hide();
			$("#aLinkSysEdit").click(function() {
				$(this).hide();
				$("#sysEdit").show();
				$("#sysView").hide();

				$("#stuIsAllowRecommend").prop("disabled", false);
				$("#stuIsAllowEmail").prop("disabled", false);
				$("#stuRecommendFrequency").prop("disabled", false);
			});

			$("#demandEdit").hide();
			$("#aLinkDemandEdit").click(function() {
				$(this).hide();
				$("#demandEdit").show();
				$("#demandView").hide();

				$("input[name='stuDemandType']").each(function(index, item) {
					if ($(item).val() == demandType) {
						$(item).attr("checked", true);
					}
				});
			});

			$("#stuJobStatus").change(function() {
				if ($(this).val() == 3) {
					$("#stuIsAllowRecommend").val(0);
					$("#stuIsAllowEmail").val(0);
					$("#stuIsAllowRecommend").prop("disabled", true);
					$("#stuIsAllowEmail").prop("disabled", true);
					$("#stuRecommendFrequency").val(4);
					$("#stuRecommendFrequency").prop("disabled", true);
				} else {
					$("#stuIsAllowRecommend").val(1);
					$("#stuIsAllowEmail").val(1);
					$("#stuIsAllowRecommend").prop("disabled", false);
					$("#stuIsAllowEmail").prop("disabled", false);
					$("#stuRecommendFrequency").val(1);
					$("#stuRecommendFrequency").prop("disabled", false);
				}
			});

			$("#stuIsAllowEmail").change(function() {
				if ($(this).val() == 0) {
					$("#stuRecommendFrequency").val(4);
					$("#stuRecommendFrequency").prop("disabled", true);
				} else {
					$("#stuRecommendFrequency").val(1);
					$("#stuRecommendFrequency").prop("disabled", false);
				}
			});

			initStuSysForm();
		};

		var deleteResumeInfo = function(id) {
			if (confirm("确定删除当前简历?")) {
				$.ajax({
					url : "resume/deleteResumeInfo",
					type : "post",
					dataType : "json",
					data : {
						resumeId : id
					},
					success : function(data) {
						if (data.success) {
							window.location.href = "resume/my_resume?id=" + $("#hdMemberId").val();
						}
					}
				});
			}
		};

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
					"重置" : function() {
						resetIndustryCheckbox();
					},
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

		//保存期望行业
		var saveIndustryData = function() {
			var valueArr = [];
			var valueIdArr = [];
			$("input[name='demandIndustry']:checked").each(function() {
				valueArr.push($(this).attr("desc"));
				valueIdArr.push($(this).val());
			});
			$("#stuDemandIndustry").val(valueArr.join(","));
			$("#hdStuDemandIndustry").val(valueIdArr.join(","));
		};

		var showIndustryDialog = function() {
			$("#dialog_industry").dialog("open");
			var idStr = $("#hdStuDemandIndustry").val();
			var ids = idStr.indexOf(',') != -1 ? idStr.split(',') : [ idStr ];
			$("input[name='demandIndustry']").each(function(index, item) {
				if ($.inArray($(item).val(), ids) != -1) {
					$(item).attr("checked", true);
				}
			});
			checkIndustry();
		};

		//检验行业选项框
		var checkIndustry = function() {
			var count = $("input[name='demandIndustry']:checked").length;
			if (count >= 3) {
				$("input[name='demandIndustry']:unchecked").each(function() {
					$(this).attr("disabled", true);
				});
			} else {
				$("input[name='demandIndustry']:unchecked").each(function() {
					$(this).attr("disabled", false);
				});
			}
		};

		//重置选项框
		var resetIndustryCheckbox = function() {
			$("input[name='demandIndustry']:checked").each(function() {
				$(this).attr("checked", false);
			});
			checkIndustry();
		};

		//期望城市
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
					"重置" : function() {
						resetCityCheckbox();
					},
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
			var idStr = $("#hdStuDemandCity").val();
			var ids = idStr.indexOf(',') != -1 ? idStr.split(',') : [ idStr ];
			$("input[name='demandCity']").each(function(index, item) {
				if ($.inArray($(item).val(), ids) != -1) {
					$(item).attr("checked", true);
				}
			});
			checkCity();
		};

		//期望城市
		var saveCityData = function() {
			var valueArr = [];
			var valueIdArr = [];
			$("input[name='demandCity']:checked").each(function() {
				valueArr.push($(this).attr("desc"));
				valueIdArr.push($(this).val());
			});
			$("#stuDemandCity").val(valueArr.join(","));
			$("#hdStuDemandCity").val(valueIdArr.join(","));
		};

		//检验行业选项框
		var checkCity = function() {
			var count = $("input[name='demandCity']:checked").length;
			if (count >= 3) {
				$("input[name='demandCity']:unchecked").each(function() {
					$(this).attr("disabled", true);
				});
			} else {
				$("input[name='demandCity']:unchecked").each(function() {
					$(this).attr("disabled", false);
				});
			}
		};

		//重置选项框
		var resetCityCheckbox = function(name) {
			$("input[name='demandCity']:checked").each(function() {
				$(this).attr("checked", false);
			});
			checkCity();
		};

		//系统设置
		var initStuSysForm = function() {
			$("#stuSysForm").validate({
				rules : {
					stuRecommendEmail : {
						email : true
					}
				},
				messages : {
					stuRecommendEmail : {
						email : "邮箱格式不正确"
					}
				},
				errorPlacement : function(label, element) {
					showErrorMsg(label, element);
				},
				submitHandler : function(form) {
					saveSysInfo(form);
				}
			});
		};

		var showErrorMsg = function(label, element) {
			label.insertAfter(element);
		};

		var saveSysInfo = function(form) {
			$("#sysEdit").hide();
			$("#sysView").show();
			$("#aLinkSysEdit").show();
			$("#btnSysSave").attr("disabled", true);
			$.ajax({
				url : "resume/saveSysInfo",
				type : "post",
				dataType : "json",
				data : {
					memberId : $("#hdMemberId").val(),
					studentId : $("#hdStudentId").val(),
					stuJobStatus : $("#stuJobStatus").val(),
					stuIsAllowRecommend : $("#stuIsAllowRecommend").val(),
					stuIsAllowEmail : $("#stuIsAllowEmail").val(),
					stuRecommendEmail : $("#stuRecommendEmail").val(),
					stuRecommendFrequency : $("#stuRecommendFrequency").val()
				}
			}).done(function(data) {
				if (data.success) {
					initSysData(data.student);
				} else {
					alert(data.message);
				}

				$("#btnSysSave").attr("disabled", false);
			});
		};

		var submitStuSysForm = function() {
			$("#stuSysForm").submit();
		};

		var initSysData = function(data) {
			$("#studentJobStatusView").text(data.jobStatusDesc);
			$("#studentAllowRecommendView").text(data.allowRecommend ? "是" : "否");
			$("#studentAllowEmailView").text(data.allowEmail ? "是" : "否");
			$("#studentRecommendEmailView").text(data.recommendEmail);
			$("#studentRecommendFreqView").text(data.recommendFrequencyDesc);
		};

		var cancelSysInfo = function() {
			$("#sysEdit").hide();
			$("#sysView").show();
			$("#aLinkSysEdit").show();
		};

		//求职意向
		var saveDemandInfo = function() {
			$("#demandEdit").hide();
			$("#demandView").show();
			$("#aLinkDemandEdit").show();

			$.ajax({
				url : "resume/saveDemandInfo",
				type : "post",
				dataType : "json",
				data : {
					memberId : $("#hdMemberId").val(),
					studentId : $("#hdStudentId").val(),
					hdStuDemandIndustry : $("#hdStuDemandIndustry").val(),
					stuDemandPostion : $("#stuDemandPostion").val(),
					stuDemandType : $("input[name='stuDemandType']:checked").val(),
					hdStuDemandCity : $("#hdStuDemandCity").val(),
					stuDemandSalary : $("#stuDemandSalary").val()
				},
				success : function(data) {
					if (data.success) {
						initDemandData(data);
					} else {
						alert(data.message);
					}
				}
			});
		};

		var initDemandData = function(data) {
			var demandPosition = data.student.demandPosition;
			var demandTypeDesc = data.student.demandTypeDesc;
			var demandIndustryDesc = data.demandIndustryDesc;
			var demandCityDesc = data.demandCityDesc;
			var demandSalaryDesc = data.student.demandSalaryDesc;

			$("#demandIndustryDesc").text(demandIndustryDesc);
			$("#demandPosition").text(demandPosition);
			$("#demandTypeDesc").text(demandTypeDesc);
			$("#demandCityDesc").text(demandCityDesc);
			$("#demandSalaryDesc").text(demandSalaryDesc);
		};

		var cancelDemandInfo = function() {
			$("#demandEdit").hide();
			$("#demandView").show();
			$("#aLinkDemandEdit").show();
		};

		var createChineseResume = function() {
			$.ajax({
				url : "resume/createChineseResume",
				type : "post",
				dataType : "json",
				data : {
					memberId : $("#hdMemberId").val()
				},
				success : function(data) {
					if (data.success) {
						window.location.href = "resume/online_resume?id=" + data.memberId + "&rsid=" + data.resumeId;
					}
				}
			});
		};
	</script>
	<%@include file="/WEB-INF/views/sliderbar.jsp"%>

	<div id="dialog_industry" class="none">
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
										<td><input type="checkbox" name="demandIndustry"
											onclick="checkIndustry();" value="${industry.id}"
											desc="${industry.name}"
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

	<div id="dialog_city" class="none">
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
					<td><input type="checkbox" name="demandCity"
						onclick="checkCity();" value="${city.id}" desc="${city.name}"
						style="margin-left: 6px; cursor: pointer;" />${city.name}</td>
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
										<td><input type="checkbox" name="demandCity"
											onclick="checkCity();" value="${city.id}" desc="${city.name}"
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
					<td><input type="checkbox" name="demandCity"
						onclick="checkCity();" value="${city.id}" desc="${city.name}"
						style="margin-left: 6px; cursor: pointer;" />${city.name}</td>
					<c:if test="${(item.index+1)%4==0}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>

	<div class="stuMR_container">
		<div class="fix_sliderbar">
			<h1 class="stuMR_title">我的简历</h1>
		</div>
		<!--第一块-->
		<div class="stuMR_content_title">
			<input type="hidden" id="hdMemberId" value="${memberDTO.memberId}" />
			<input type="hidden" id="hdStudentId" value="${studentInfoDTO.id}" />
			<div class="stuMR_top">
				<img src="images_zp/folder_32.png" alt="简历列表" />
				<h4>我的简历列表</h4>
				<h6>您共可以创建6份简历，已创建<c:out value="${resumelist.size()}" />
					份。您可以针对不同行业准备不同简历。
				</h6>
			</div>
			<div class="stuMR_iContent">
				<c:if test="${resumelist.size()>0}">
					<table class="stuMR_resumeTable" rules="none">
						<c:forEach items="${resumelist}" var="item">
							<tr>
								<td colspan="2" class="stuMR_name"><a
									href="resume/online_resume?id=${memberDTO.memberId}&rsid=${item.id}">${item.resumeName}</a></td>
								<td class="stuMR_delete"><a href="javascript:void(0)"
									onclick="deleteResumeInfo('${item.id}')">删除</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${resumelist.size()<6}">
					<a href="javascript:void(0)" id="btnCreateChineseResume"
						onclick="createChineseResume();" class="stuMR_inputChinese">创建中文简历</a>
					<!-- <a href="javascript:void(0)" id="btnCreateEnglishResume" class="stuMR_inputEnglish">创建英文简历</a> -->
				</c:if>

			</div>
		</div>
		<!--第二块-->
		<div class="stuMR_content">
			<div class="stuMR_top">
				<img src="images_zp/user_business_32.png" alt="求职意向" />
				<h4>求职意向</h4>
				<h6>我们将根据您的求职意向推荐职位给您。</h6>
				<a href="javascript:void(0)"
					style="font-size: 14px; display: inline;" id="aLinkDemandEdit" class="stuMR_editsth">编辑</a>
			</div>
			<div id="demandView" class="stuMR_iContent">
				<!--1-->
				<div class="stuMR_noOne"
					style="height: 36px; width: 255px; margin-left: 20px;">
					<span class="stuMR_cur-select fontweight">期望行业：</span><br /> <span
						class="resultdisplay" id="demandIndustryDesc">${demandIndustryDesc}</span>
				</div>
				<!--2-->
				<div class="stuMR_noOne"
					style="height: 36px; width: 200px; margin-left: 20px;">
					<p class="stuMR_cur-select fontweight">期望职位</p>
					<span id="demandPosition" class="resultdisplay">${studentInfoDTO.demandPosition}</span>
				</div>
				<div class="stuMR_noOne"
					style="height: 36px; width: 255px; margin-left: 20px;">
					<p class="stuMR_cur-select fontweight">期望求职类别</p>
					<span id="demandTypeDesc" class="resultdisplay">${studentInfoDTO.demandTypeDesc}</span>
				</div>
				<!--3-->
				<div class="stuMR_noOne"
					style="height: 36px; width: 255px; margin-left: 20px; margin-top: 35px;">
					<span class="stuMR_cur-select fontweight">期望城市</span> <br /> <span
						id="demandCityDesc" class="resultdisplay">${demandCityDesc}</span>
				</div>
				<!--4-->
				<div class="stuMR_noOne"
					style="height: 36px; width: 255px; margin-left: 20px; margin-top: 35px;">
					<span class="stuMR_cur-select fontweight">期望月薪</span> <br /> <span
						id="demandSalaryDesc" class="resultdisplay">${studentInfoDTO.demandSalaryDesc}</span>
				</div>
			</div>

			<div id="demandEdit" class="stuMR_iContent">
				<form id="stuDemandForm" name="stuDemandForm">
					<!--1-->
					<div class="stuMR_noOne">
						<span class="stuMR_cur-select fontweight">期望行业</span> <br /> <input
							type="text" id="stuDemandIndustry" name="stuDemandIndustry"
							onclick="showIndustryDialog();" value="${demandIndustryDesc}"
							style="width: 300px; height: 36px;" readonly="readonly" /> <input
							type="hidden" id="hdStuDemandIndustry" name="hdStuDemandIndustry"
							value="${studentInfoDTO.demandIndustry}" />
					</div>
					<!--2-->
					<div class="stuMR_noTwo">
						<p class="stuMR_cur-select fontweight">期望职位</p>
						<input type="text" id="stuDemandPostion" name="stuDemandPostion"
							value="${studentInfoDTO.demandPosition}" name="stuMR_position" />
					</div>
					<!--单选-->
					<div class="stuMR_choice" style="margin-top: 26px;">
						<label><input name="stuDemandType" value="1" type="radio"
							style="cursor: pointer;" />全职 </label> &nbsp <label><input
							name="stuDemandType" value="2" type="radio"
							style="cursor: pointer;" />实习 </label>
					</div>
					<!--3-->
					<div class="stuMR_noThree">
						<span class="stuMR_cur-select fontweight">期望城市</span> <br /> <input
							type="text" id="stuDemandCity" name="stuDemandCity"
							onclick="showCityDialog();" value="${demandCityDesc}"
							style="width: 300px; height: 36px;" /> <input type="hidden"
							id="hdStuDemandCity" name="hdStuDemandCity"
							value="${studentInfoDTO.demandCity}" />
					</div>
					<!--4-->
					<div class="stuMR_noFour">
						<span class="stuMR_cur-select fontweight">期望月薪</span> <br /> <select
							id="stuDemandSalary" name="stuDemandSalary">
							<c:forEach items="${salaryLevelEnums}" var="item">
								<option value="${item.code}"
									${item.code==studentInfoDTO.demandSalary?"selected=selected":""}>${item.desc}</option>
							</c:forEach>
						</select>
					</div>
					<!--按钮-->
					<a href="javascript:void(0)" id="btnDemandSave"
						onclick="saveDemandInfo();" class="stuMR_save"
						style="right: 400px; top: 135px;">保存</a>
					<!--暂无意向-->
					<p class="stuMR_no">
						<a href="javascript:void(0)" id="btnDemandCancel"
							style="top: 145px; right: 300px;" onclick="cancelDemandInfo();">暂无意向</a>
					</p>
				</form>
			</div>
		</div>

		<!--第三块-->
		<div class="stuMR_content">
			<div class="stuMR_top">
				<img src="images_zp/screen_off_32.png" alt="系统设置" />
				<h4>系统设置</h4>
				<h6>请选择您的状态，以便我们更好地提供服务。</h6>
				<a href="javascript:void(0)"
					style="font-size: 14px; display: inline;" id="aLinkSysEdit" class="stuMR_editsth">编辑</a>
			</div>

			<div id="sysView" class="stuMR_iContent">
				<!--1-->
				<div class="stuMR_noOne"
					style="height: 36px; width: 200px; margin-left: 20px;">
					<span class="stuMR_cur-select fontweight">我目前的求职状态</span><br /> <span
						class="resultdisplay" id="studentJobStatusView">${studentInfoDTO.jobStatusDesc==""?"":studentInfoDTO.jobStatusDesc}</span>
				</div>
				<!--2-->
				<div class="stuMR_noOne"
					style="height: 36px; width: 270px; margin-left: 20px;">
					<p class="stuMR_cur-select fontweight">公司是否可发送职位邀请给我</p>
					<span id="studentAllowRecommendView" class="resultdisplay">${studentInfoDTO.allowRecommend?"是":"否"}</span>
				</div>
				<div class="stuMR_noOne"
					style="height: 36px; width: 200px; margin-left: 20px;">
					<p class="stuMR_cur-select fontweight">是否接受邮箱职位推荐</p>
					<span id="studentAllowEmailView" class="resultdisplay">${studentInfoDTO.allowEmail?"是":"否"}</span>
				</div>
				<!--3-->
				<div class="stuMR_noOne"
					style="height: 36px; width: 255px; margin-left: 20px; margin-top: 35px;">
					<span class="stuMR_cur-select fontweight">接受职位推荐的频率</span> <br />
					<span id="studentRecommendFreqView" class="resultdisplay">${studentInfoDTO.recommendFrequencyDesc==""?"":studentInfoDTO.recommendFrequencyDesc}</span>
				</div>
				<!--4-->
				<div class="stuMR_noOne"
					style="height: 36px; width: 255px; margin-left: -35px; margin-top: 35px;">
					<span class="stuMR_cur-select fontweight">接受职位推荐的邮箱地址</span> <br />
					<span id="studentRecommendEmailView" class="resultdisplay">${studentInfoDTO.recommendEmail==""?"":studentInfoDTO.recommendEmail}</span>
				</div>
			</div>

			<div id="sysEdit" class="stuMR_iContent">
				<form id="stuSysForm" name="stuSysForm">
					<!--1-->
					<div class="stuMR_noOne">
						<span class="stuMR_cur-select fontweight">我目前的求职状态</span> <br />
						<select id="stuJobStatus" name="stuJobStatus" class="mt10 wd300">
							<c:forEach items="${jobStatusEnums}" var="item">
								<option value="${item.code}"
									${item.code==studentInfoDTO.jobStatus?"selected=selected":""}>${item.desc}</option>
							</c:forEach>
						</select>
					</div>
					<!--2-->
					<div class="stuMR_noTwo">
						<span class="stuMR_cur-select fontweight">公司是否可发送职位邀请给我</span> <br />
						<select id="stuIsAllowRecommend" name="stuIsAllowRecommend"
							class="mt10 wd300">
							<c:forEach items="${yesNoEnums}" var="item">
								<option value="${item.code}"
									${item.code==(studentInfoDTO.allowRecommend?1:0)?"selected=selected":""}>${item.desc}</option>
							</c:forEach>
						</select>
					</div>
					<!--3-->
					<div class="stuMR_frequency">
						<span class="stuMR_cur-select fontweight">是否接受邮箱职位推荐</span> <br />
						<select id="stuIsAllowEmail" name="stuIsAllowEmail"
							class="mt10 wd300">
							<c:forEach items="${yesNoEnums}" var="item">
								<option value="${item.code}"
									${item.code==(studentInfoDTO.allowEmail?1:0)?"selected=selected":""}>${item.desc}</option>
							</c:forEach>
						</select>
					</div>
					<!--4-->
					<div class="stuMR_noThree">
						<span class="stuMR_cur-select fontweight">接受职位推荐的频率</span> <br />
						<select id="stuRecommendFrequency" name="stuRecommendFrequency"
							class="mt10 wd300">
							<c:forEach items="${recommendFrequencyEnums}" var="item">
								<option value="${item.code}"
									${item.code==studentInfoDTO.recommendFrequency?"selected=selected":""}>${item.desc}</option>
							</c:forEach>
						</select>

					</div>
					<!--5-->
					<div class="stuMR_noFour">
						<span class="stuMR_cur-select fontweight">接受职位推荐的邮箱地址</span> <br />
						<input type="text" id="stuRecommendEmail" name="stuRecommendEmail"
							value="${studentInfoDTO.recommendEmail}" class="mt10" /> <br />
						<label id="stuRecommendEmail-error" class="error"
							for="stuRecommendEmail"></label>
					</div>
					<!--按钮-->
					<a href="javascript:void(0)" id="btnSysSave"
						onclick="submitStuSysForm();" class="stuMR_save"
						style="margin-top: 18px;">保存</a>
					<p class="stuMR_no">
						<a href="javascript:void(0)" id="btnSysCancel"
							style="top: 125px; right: 85px;" onclick="cancelSysInfo();">取消</a>
					</p>
				</form>
			</div>
		</div>
	</div>
</body>
</html>