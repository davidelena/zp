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
<title>发布职位（第一步）</title>
</head>
<body>
	<script type="text/javascript">
		//页面加载
		$(function() {
			$("#validityTime").datepicker($.datepicker.regional["zh-CN"]);
			initCityDialog();
			initPositionTypeDialog();
			initImportantRemarkDialog();
			initJobBasicInfoForm();

			var hdRecruitType = $("#hdRecruitType").val();
			$("input[name='recruitType']").each(function(index, item) {
				if ($(item).val() == hdRecruitType) {
					$(item).attr("checked", true);
				}
			});

			var isNegotiable = $("#isNegotiable").val();
			if (isNegotiable == "true") {
				$("#chkNegotiable").attr("checked", true);
			} else {
				$("#chkNegotiable").attr("checked", false);
			}
		});

		//初始化company member表格
		var initJobBasicInfoForm = function() {
			$("#jobBasicInfoForm").validate({
				rules : {
					recruitType : {
						required : true
					},
					positionName : {
						required : true
					},
					postDuty : {
						required : true
					},
					workCity : {
						required : true
					},
					validityTime : {
						required : true
					}
				},
				messages : {
					recruitType : {
						required : "请选择招聘类型"
					},
					positionName : {
						required : "请输入职位名称"
					},
					postDuty : {
						required : "请输入岗位职位"
					},
					workCity : {
						required : "请选择工作城市"
					},
					validityTime : {
						required : "请选择职位截止日期"
					}
				},
				errorPlacement : function(label, element) {
					showErrorMsg(label, element);
				},
				submitHandler : function(form) {
					confirmSaveJobBasicInfo(form);
				}
			});
		};

		var showErrorMsg = function(label, element) {
			label.insertAfter(element);
		};

		//save company step1 info
		var confirmSaveJobBasicInfo = function(form) {
			$(form).find(".btnFinish").attr("disabled", true);
			var isNegotiable = $("#chkNegotiable").prop("checked") ? 1 : 0;

			$.ajax({
				url : "recruit/saveJobBasicInfo",
				type : "post",
				dataType : "json",
				data : {
					hdRecruitId : $("#hdRecruitId").val(),
					hdMemberId : $("#hdMemberId").val(),
					recruitType : $("input[name='recruitType']:checked").val(),
					hdPositionType : $("#hdPositionType").val(),
					positionName : $("#positionName").val(),
					departmentName : $("#departmentName").val(),
					postDuty : $("#postDuty").val(),
					hdWorkCity : $("#hdWorkCity").val(),
					detailAddress : $("#detailAddress").val(),
					minSalary : $("#minSalary").val(),
					maxSalary : $("#maxSalary").val(),
					chkNegotiable : isNegotiable,
					hdImportantRemark : $("#hdImportantRemark").val(),
					needNum : $("#needNum").val(),
					acceptEmail : $("#acceptEmail").val(),
					emailType : $("#emailType").val(),
					validityTime : $("#validityTime").val(),
					saveType : $("#saveType").val()
				}
			}).done(function(data) {
				if (data.success) {
					$("#salary-error").hide();
					window.location.href = data.url;
				} else {
					$("#salary-error").text(data.message);
					$("#salary-error").show();
				}
				$(form).find(".btnSave").attr("disabled", false);
			});
		};

		var submitCompanyMemberForm = function() {
			$("#companyMemberInfoForm").submit();
		};

		var saveJobBasicInfo = function(type) {
			$("#saveType").val(type);
			$("#jobBasicInfoForm").submit();
		};

		var initImportantRemarkDialog = function() {
			$("#dialog_important_remark").dialog({
				autoOpen : false,
				title : "请选择职位福利亮点",
				show : {
					duration : 200
				},
				hide : {
					duration : 200
				},
				resizable : false,
				height : 280,
				width : 750,
				modal : true,
				buttons : {
					"重置" : function() {
						resetImportantRemarkCheckbox();
					},
					"确认" : function() {
						saveImportantRemarkData();
						$("#dialog_important_remark").dialog("close");
					},
					"取消" : function() {
						$("#dialog_important_remark").dialog("close");
					}
				}
			});
		};

		var saveImportantRemarkData = function() {
			var valueArr = [];
			var valueIdArr = [];
			$("input[name='chkImportantRemark']:checked").each(function() {
				valueArr.push($(this).attr("desc"));
				valueIdArr.push($(this).val());
			});
			$("#importantRemark").val(valueArr.join(","));
			$("#hdImportantRemark").val(valueIdArr.join(","));
		};

		var showImportantRemarkDialog = function() {
			$("#dialog_important_remark").dialog("open");
			var idStr = $("#hdImportantRemark").val();
			var ids = idStr.indexOf(',') != -1 ? idStr.split(',') : [ idStr ];
			$("input[name='chkImportantRemark']").each(function(index, item) {
				if ($.inArray($(item).val(), ids) != -1) {
					$(item).attr("checked", true);
				}
			});
			checkImportantRemark();
		};

		//检验选项框
		var checkImportantRemark = function() {
			var count = $("input[name='chkImportantRemark']:checked").length;
			if (count >= 6) {
				$("input[name='chkImportantRemark']:unchecked").each(function() {
					$(this).attr("disabled", true);
				});
			} else {
				$("input[name='chkImportantRemark']:unchecked").each(function() {
					$(this).attr("disabled", false);
				});
			}
		};

		//重置选项框
		var resetImportantRemarkCheckbox = function() {
			$("input[name='chkImportantRemark']:checked").each(function() {
				$(this).attr("checked", false);
			});
			checkImportantRemark();
		};

		//职位类型
		var initPositionTypeDialog = function() {
			$("#dialog_position_type").dialog({
				autoOpen : false,
				title : "请选择职位类别",
				show : {
					duration : 200
				},
				hide : {
					duration : 200
				},
				resizable : false,
				height : 500,
				width : 850,
				modal : true,
				buttons : {
					"确认" : function() {
						savePositionTypeData();
						$("#dialog_position_type").dialog("close");
					},
					"取消" : function() {
						$("#dialog_position_type").dialog("close");
					}
				}
			});
		};

		var savePositionTypeData = function() {
			var checkedItem = $("input[name='dialogPositionType']:checked");
			$("#positionType").val($(checkedItem).attr("desc"));
			$("#hdPositionType").val($(checkedItem).val());
		};

		var showPositionTypeDialog = function() {
			$("#dialog_position_type").dialog("open");
			var id = $("#hdPositionType").val();
			$("input[name='dialogPositionType']").each(function(index, item) {
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
			var id = $("#hdWorkCity").val();
			$("input[name='cpCity']").each(function(index, item) {
				if (id == $(item).val()) {
					$(item).attr("checked", true);
				}
			});
		};

		var saveCityData = function() {
			var checkedItem = $("input[name='cpCity']:checked");
			$("#workCity").val($(checkedItem).attr("desc"));
			$("#hdWorkCity").val($(checkedItem).val());
		};
	</script>

	<div id="dialog_important_remark" class="none">
		<div style="display: block;">
			<table id="importantRemarkTable" class="tableTd" style="width: 100%;"
				border="1" cellspacing="1" cellpadding="0">
				<c:forEach items="${jobBenefitsEnums}" var="importantRemark"
					varStatus="item">
					<c:if test="${item.index%5==0}">
						<tr>
					</c:if>
					<td><input type="checkbox" name="chkImportantRemark"
						onclick="checkImportantRemark();" value="${importantRemark.code}"
						desc="${importantRemark.desc}"
						style="margin-left: 6px; cursor: pointer;" />${importantRemark.desc}</td>
					<c:if test="${(item.index+1)%5==0}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>

	<div id="dialog_position_type" class="none">
		<div style="display: block;">
			<table id="positionTypeTable" class="tableTd" style="width: 100%;"
				border="1" cellspacing="1" cellpadding="0">

				<c:forEach items="${positionTypeMap}" var="m">
					<tr>
						<td class="tableTd" class="ml6""><span class="ml6">${m.key}</span></td>
						<td class="tableTd">
							<div class="ml6 block">
								<table>
									<c:forEach items="${m.value}" var="positionType"
										varStatus="item">
										<c:if test="${item.index%3==0}">
											<tr>
										</c:if>
										<td><input type="radio" name="dialogPositionType"
											value="${positionType.id}" desc="${positionType.name}"
											style="margin-left: 6px; cursor: pointer;" />${positionType.name}</td>
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

	<div class="fullSize clearfix">
		<%@include file="/WEB-INF/views/sliderbar_cp.jsp"%>
		<div class="coGJ_blank"></div>
		<!--主体-->
		<div class="coGJ_content">
			<form id="jobBasicInfoForm">
				<input type="hidden" id="hdMemberId" name="hdMemberId"
					value="${memberId}" /> <input type="hidden" id="hdRecruitId"
					name="hdRecruitId" value="${recruitInfo.id}" /> <input
					type="hidden" id="saveType" name="saveType" value="1">
				<fieldset>
					<legend>第一步：职位基本信息</legend>
					招聘类型：<span class="coGJ_type"><input type="radio"
						checked="checked" name="recruitType" class="pointer" value="1" />全职</span><span
						class="coGJ_type"><input type="radio" name="recruitType"
						class="pointer" value="2" />实习</span><input type="hidden"
						id="hdRecruitType" name="hdRecruitType"
						value="${recruitInfo.recruitType}" /><br /> <label
						id="recruitType-error" class="error ml73" for="recruitType"></label><br />
					职位类别： <input type="text" id="positionType" name="positionType"
						readonly="readonly" onclick="showPositionTypeDialog();"
						class="coGJ_mn" value="${recruitInfo.positionTypeDesc}"
						placeholder="职位类别" /> <input type="hidden" id="hdPositionType"
						name="hdPositionType" value="${recruitInfo.positionType}" />
					(未找到合适的类别可以不选择) <br /> 职位名称：<input type="text" id="positionName"
						name="positionName" value="${recruitInfo.positionName}"
						class="coGJ_mn" placeholder="可填写30个汉字/60个字符" />(填写精确职位名称可提升招聘效果)<br />
					<label id="positionName-error" class="error ml73 h25"
						for="positionName"></label><br /> 工作部门：<input type="text"
						id="departmentName" name="departmentName"
						value="${recruitInfo.departmentName}" class="coGJ_mn"
						placeholder="选填" /><br /> <span>岗位职责：</span>
					<textarea rows="10" cols="30" id="postDuty" name="postDuty"
						placeholder="您可以简要介绍岗位的工作内容。300字以内。请勿输入公司邮箱、联系电话，系统将自动删除，敬请谅解。">${recruitInfo.postDuty}</textarea>
					<br /> <label id="postDuty-error" class="error ml73"
						for="postDuty"></label><br /> 工作地点： <input type="text"
						id="workCity" name="workCity" onclick="showCityDialog();"
						class="coGJ_mn" value="${recruitInfo.workCityDTO.city}"
						placeholder="城市" readonly="readonly" /> <input type="hidden"
						id="hdWorkCity" name="hdWorkCity"
						value="${recruitInfo.workCityDTO.cityID}" /> <input type="text"
						id="detailAddress" name="detailAddress"
						value="${recruitInfo.workCityDTO.detailAddress}" class="coGJ_mn"
						placeholder="具体地址，选填" /><br /> <label id="workCity-error"
						class="error ml73" for="workCity"></label><br />
					<hr />
					月薪水平：<span class="coGJ_mn"><input type="text"
						onkeydown="onlyNum()" id="minSalary" name="minSalary"
						style="ime-mode: Disabled" value="${recruitInfo.minSalary}"
						class="coGJ_special" placeholder="最低月薪，整数" /></span>K<span
						class="coGJ_mn"><input type="text" id="maxSalary"
						name="maxSalary" onkeydown="onlyNum()"
						value="${recruitInfo.maxSalary}" class="coGJ_special"
						placeholder="最高月薪，整数" style="ime-mode: Disabled" /></span>K<input
						type="checkbox" class="pointer ml6" id="chkNegotiable"
						name="chkNegotiable" value="1" /> <input type="hidden"
						id="isNegotiable" name="isNegotiable"
						value="${recruitInfo.isNegotiable}" />面议<br /> <label
						id="salary-error" class="error ml73" for="salary"></label><br />
					福利亮点： <input type="text" id="importantRemark"
						name="importantRemark" onclick="showImportantRemarkDialog();"
						value="${recruitInfo.importantRemarkDesc}" class="coGJ_mn"
						placeholder="职位和福利亮点" /> <input type="hidden"
						id="hdImportantRemark" name="hdImportantRemark"
						value="${recruitInfo.importantRemark}" />
					<hr />
					招聘人数：<input type="text" id="needNum" name="needNum"
						value="${recruitInfo.needNum}" class="coGJ_mn" /><br /> 招聘邮箱：<input
						type="text" id="acceptEmail" name="acceptEmail"
						value="${recruitInfo.acceptEmail}" class="coGJ_mn"
						placeholder="请填写接收简历的邮箱" /> <select id="emailType"
						name="emailType" class="coGJ_mn">
						<c:forEach items="${recruitEmailTypeEnums}" var="item">
							<option value="${item.code}"
								${item.code==recruitInfo.emailType?"selected=selected":""}>${item.desc}</option>
						</c:forEach>
					</select> <br /> 截止日期：<input type="text" id="validityTime"
						name="validityTime" value="${recruitInfo.validityTimeDesc}"
						class="coGJ_mn" placeholder="建议设置在3个月以内。" />(有效期越长，对求职者的吸引力越低！)<br />
					<label id="validityTime-error" class="error ml73"
						for="validityTime"></label>
				</fieldset>
				<div class="coGJ_bottom">
					<input type="button" class="coGJ_save btnSave" value="保存草稿"
						onclick="saveJobBasicInfo(1)" class="coGJ_save" /> <input
						type="button" value="下一步：完善精准职位要求" onclick="saveJobBasicInfo(2)"
						class="coGJ_save btnSave" />
				</div>
			</form>
		</div>
		<!--底部-->
		<div class="coGJ_footer">
			第一站的微信号是：zhancampus，欢迎关注！<br />有问题？您可以电话联系我们：021-21458392；或致信邮箱us@zhancampus.com
		</div>
	</div>
</body>
</html>