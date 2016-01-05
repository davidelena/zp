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
<title>发布职位（第二步）</title>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			initComputerSkillDialog();
			initEnglishSkillDialog();
			initOtherLangSkillDialog();
		});

		var toStepOne = function() {
			window.location.href = "recruit/job_basic_info?id=" + $("#hdMemberId").val() + "&rid="
					+ $("#hdRecruitId").val();
		};

		var toJobView = function() {
			window.location.href = "recruit/job_view?id=" + $("#hdMemberId").val() + "&rid=" + $("#hdRecruitId").val();
		};

		var toMyJobList = function() {
			$("#preciseOrientationForm").submit();
		};

		//计算机技能
		var initComputerSkillDialog = function() {
			$("#dialog_computer_skill").dialog({
				autoOpen : false,
				title : "请选择计算机技能",
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
						resetComputerSkillCheckbox();
					},
					"确认" : function() {
						saveComputerSkillData();
						$("#dialog_computer_skill").dialog("close");
					},
					"取消" : function() {
						$("#dialog_computer_skill").dialog("close");
					}
				}
			});
		};

		var saveComputerSkillData = function() {
			var valueArr = [];
			var valueIdArr = [];
			$("input[name='chkComputerSkill']:checked").each(function() {
				valueArr.push($(this).attr("desc"));
				valueIdArr.push($(this).val());
			});
			$("#targetComputerSkill").val(valueArr.join(","));
			$("#hdTargetComputerSkill").val(valueIdArr.join(","));
		};

		var showComputerSkillDialog = function() {
			$("#dialog_computer_skill").dialog("open");
			var idStr = $("#hdTargetComputerSkill").val();
			var ids = idStr.indexOf(',') != -1 ? idStr.split(',') : [ idStr ];
			$("input[name='chkComputerSkill']").each(function(index, item) {
				if ($.inArray($(item).val(), ids) != -1) {
					$(item).attr("checked", true);
				}
			});
			checkComputeSkill();
		};

		var checkComputeSkill = function() {
			var count = $("input[name='chkComputerSkill']:checked").length;
			if (count >= 5) {
				$("input[name='chkComputerSkill']:unchecked").each(function() {
					$(this).attr("disabled", true);
				});
			} else {
				$("input[name='chkComputerSkill']:unchecked").each(function() {
					$(this).attr("disabled", false);
				});
			}
		};

		var resetComputerSkillCheckbox = function() {
			$("input[name='chkComputerSkill']:checked").each(function() {
				$(this).attr("checked", false);
			});
			checkComputeSkill();
		};

		//英语技能
		var initEnglishSkillDialog = function() {
			$("#dialog_english_skill").dialog({
				autoOpen : false,
				title : "请选择英语技能",
				show : {
					duration : 200
				},
				hide : {
					duration : 200
				},
				resizable : false,
				height : 260,
				width : 750,
				modal : true,
				buttons : {
					"重置" : function() {
						resetEnglishSikllCheckbox();
					},
					"确认" : function() {
						saveEnglishSillData();
						$("#dialog_english_skill").dialog("close");
					},
					"取消" : function() {
						$("#dialog_english_skill").dialog("close");
					}
				}
			});
		};

		var saveEnglishSillData = function() {
			var valueArr = [];
			var valueIdArr = [];
			$("input[name='chkEnglishSkill']:checked").each(function() {
				valueArr.push($(this).attr("desc"));
				valueIdArr.push($(this).val());
			});
			$("#targetEnglishLang").val(valueArr.join(","));
			$("#hdTargetEnglishLang").val(valueIdArr.join(","));
		};

		var showEnglishSkillDialog = function() {
			$("#dialog_english_skill").dialog("open");
			var idStr = $("#hdTargetEnglishLang").val();
			var ids = idStr.indexOf(',') != -1 ? idStr.split(',') : [ idStr ];
			$("input[name='chkEnglishSkill']").each(function(index, item) {
				if ($.inArray($(item).val(), ids) != -1) {
					$(item).attr("checked", true);
				}
			});
			checkEnglishSkill();
		};

		var checkEnglishSkill = function() {
			var count = $("input[name='chkEnglishSkill']:checked").length;
			if (count >= 5) {
				$("input[name='chkEnglishSkill']:unchecked").each(function() {
					$(this).attr("disabled", true);
				});
			} else {
				$("input[name='chkEnglishSkill']:unchecked").each(function() {
					$(this).attr("disabled", false);
				});
			}
		};

		var resetEnglishSikllCheckbox = function() {
			$("input[name='chkEnglishSkill']:checked").each(function() {
				$(this).attr("checked", false);
			});
			checkEnglishSkill();
		};

		//其他语言技能
		var initOtherLangSkillDialog = function() {
			$("#dialog_otherLang_skill").dialog({
				autoOpen : false,
				title : "请选择其他语言技能",
				show : {
					duration : 200
				},
				hide : {
					duration : 200
				},
				resizable : false,
				height : 230,
				width : 750,
				modal : true,
				buttons : {
					"重置" : function() {
						resetOtherLangSkillCheckbox();
					},
					"确认" : function() {
						saveOtherLangSkillData();
						$("#dialog_otherLang_skill").dialog("close");
					},
					"取消" : function() {
						$("#dialog_otherLang_skill").dialog("close");
					}
				}
			});
		};

		var saveOtherLangSkillData = function() {
			var valueArr = [];
			var valueIdArr = [];
			$("input[name='chkOtherLangSkill']:checked").each(function() {
				valueArr.push($(this).attr("desc"));
				valueIdArr.push($(this).val());
			});
			$("#targetOtherLang").val(valueArr.join(","));
			$("#hdTargetOtherLang").val(valueIdArr.join(","));
		};

		var showOtherLangSkillDialog = function() {
			$("#dialog_otherLang_skill").dialog("open");
			var idStr = $("#hdTargetOtherLang").val();
			var ids = idStr.indexOf(',') != -1 ? idStr.split(',') : [ idStr ];
			$("input[name='chkOtherLangSkill']").each(function(index, item) {
				if ($.inArray($(item).val(), ids) != -1) {
					$(item).attr("checked", true);
				}
			});
			checkOtherLangSkill();
		};

		var checkOtherLangSkill = function() {
			var count = $("input[name='chkOtherLangSkill']:checked").length;
			if (count >= 5) {
				$("input[name='chkOtherLangSkill']:unchecked").each(function() {
					$(this).attr("disabled", true);
				});
			} else {
				$("input[name='chkOtherLangSkill']:unchecked").each(function() {
					$(this).attr("disabled", false);
				});
			}
		};

		var resetOtherLangSkillCheckbox = function() {
			$("input[name='chkOtherLangSkill']:checked").each(function() {
				$(this).attr("checked", false);
			});
			checkOtherLangSkill();
		};
	</script>

	<div id="dialog_computer_skill" class="none">
		<div style="display: block;">
			<table class="tableTd" style="width: 100%;" border="1"
				cellspacing="1" cellpadding="0">

				<c:forEach items="${computerSkillMap}" var="m">
					<tr>
						<td class="tableTd" class="ml6""><span class="ml6">${m.key}</span></td>
						<td class="tableTd">
							<div class="ml6 block">
								<table>
									<c:forEach items="${m.value}" var="computerSkill"
										varStatus="item">
										<c:if test="${item.index%7==0}">
											<tr>
										</c:if>
										<td><input type="checkbox" name="chkComputerSkill"
											onclick="checkComputeSkill();" value="${computerSkill.code}"
											desc="${computerSkill.desc}"
											style="margin-left: 6px; cursor: pointer;" />${computerSkill.desc}</td>
										<c:if test="${(item.index+1)%7==0}">
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

	<div id="dialog_english_skill" class="none">
		<div style="display: block;">
			<table class="tableTd" style="width: 100%;" border="1"
				cellspacing="1" cellpadding="0">

				<c:forEach items="${englishSkillEnums}" var="englishSkill"
					varStatus="item">
					<c:if test="${item.index%4==0}">
						<tr>
					</c:if>
					<td><input type="checkbox" name="chkEnglishSkill"
						onclick="checkEnglishSkill();" value="${englishSkill.code}"
						desc="${englishSkill.desc}"
						style="margin-left: 6px; cursor: pointer;" />${englishSkill.desc}</td>
					<c:if test="${(item.index+1)%4==0}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>

	<div id="dialog_otherLang_skill" class="none">
		<div style="display: block;">
			<table class="tableTd" style="width: 100%;" border="1"
				cellspacing="1" cellpadding="0">

				<c:forEach items="${otherLanguageSkillEnums}" var="otherLangSkill"
					varStatus="item">
					<c:if test="${item.index%4==0}">
						<tr>
					</c:if>
					<td><input type="checkbox" name="chkOtherLangSkill"
						onclick="checkOtherLangSkill();" value="${otherLangSkill.code}"
						desc="${otherLangSkill.desc}"
						style="margin-left: 6px; cursor: pointer;" />${otherLangSkill.desc}</td>
					<c:if test="${(item.index+1)%4==0}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>

	<%@include file="/WEB-INF/views/sliderbar_cp.jsp"%>
	<!--主体-->
	<div class="coGJ_content">
		<form id="preciseOrientationForm"
			action="recruit/savePreciseOrientation" method="post">
			<input type="hidden" id="hdMemberId" name="hdMemberId"
				value="${memberId}" /> <input type="hidden" id="hdRecruitId"
				name="hdRecruitId" value="${recruitInfo.id}" />
			<fieldset>
				<legend>第二步：精准定位职位要求</legend>
				<table>
					<tr>
						<td align="right">学校：</td>
						<td><select id="targetSchool" name="targetSchool"
							class="coGJ_mn">
								<c:forEach items="${positionSchoolTypeEnums}" var="item">
									<option value="${item.code}"
										${item.code==recruitInfo.school?"selected=selected":""}>${item.desc}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td align="right">学历：</td>
						<td><select id="targetEducational" name="targetEducational"
							class="coGJ_mn">
								<c:forEach items="${positionEducationEnums}" var="item">
									<option value="${item.code}"
										${item.code==recruitInfo.educational?"selected=selected":""}>${item.desc}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td align="right">专业：</td>
						<td><select id="targetMajor" name="targetMajor"
							class="coGJ_mn">
								<c:forEach items="${specialityEnums}" var="item">
									<option value="${item.code}"
										${item.code==recruitInfo.major?"selected=selected":""}>${item.desc}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td align="right">成绩：</td>
						<td><select id="targetScore" name="targetScore"
							class="coGJ_mn">
								<c:forEach items="${achievementEnums}" var="item">
									<option value="${item.code}"
										${item.code==recruitInfo.score?"selected=selected":""}>${item.desc}</option>
								</c:forEach>
						</select></td>
					</tr>
					<c:if test="${recruitInfo.recruitType == 2}">
						<tr>
							<td align="right">每周实习天数：</td>
							<td><select id="targetIternshipDays"
								name="targetIternshipDays" class="coGJ_mn">
									<c:forEach items="${internshipDaysEnums}" var="item">
										<option value="${item.code}"
											${item.code==recruitInfo.internshipDays?"selected=selected":""}>${item.desc}</option>
									</c:forEach>
							</select></td>
						</tr>
					</c:if>
					<tr>
						<td align="right">实习经历：</td>
						<td><select id="targetIternshipExp" name="targetIternshipExp"
							class="coGJ_mn">
								<c:forEach items="${internshipExpEnums}" var="item">
									<option value="${item.code}"
										${item.code==recruitInfo.internshipExp?"selected=selected":""}>${item.desc}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td align="right">学校活动经历：</td>
						<td><select id="targetSchoolActivity"
							name="targetSchoolActivity" class="coGJ_mn">
								<c:forEach items="${schoolActivityEnums}" var="item">
									<option value="${item.code}"
										${item.code==recruitInfo.activityExp?"selected=selected":""}>${item.desc}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td align="right">计算机技能：</td>
						<td><input type="text" id="targetComputerSkill"
							class="coGJ_mn" name="targetComputerSkill"
							onclick="showComputerSkillDialog();"
							value="${recruitInfo.skillDesc}" placeholder="计算机技能"
							readonly="readonly" /> <input type="hidden"
							id="hdTargetComputerSkill" name="hdTargetComputerSkill"
							value="${recruitInfo.skill}" /></td>
						<td><select id="targetSkillLevel" name="targetSkillLevel"
							class="coGJ_mn">
								<c:forEach items="${skillLevelEnums}" var="item">
									<option value="${item.code}"
										${item.code==recruitInfo.skillLevel?"selected=selected":""}>${item.desc}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td align="right">语言技能：</td>
						<td><input type="text" id="targetEnglishLang"
							name="targetEnglishLang" onclick="showEnglishSkillDialog();"
							value="${recruitInfo.englishDesc}" class="coGJ_mn"
							placeholder="英语技能" readonly="readonly" /> <input type="hidden"
							id="hdTargetEnglishLang" name="hdTargetEnglishLang"
							value="${recruitInfo.english}" /></td>
						<td><input type="text" id="targetOtherLang"
							name="targetOtherLang" onclick="showOtherLangSkillDialog();"
							value="${recruitInfo.otherLanguageDesc}" class="coGJ_mn"
							placeholder="其他语言" readonly="readonly" /> <input type="hidden"
							id="hdTargetOtherLang" name="hdTargetOtherLang"
							value="${recruitInfo.otherLanguage}" /></td>
					</tr>
					<tr>
						<td vertical="top" align="right"><span>其他岗位要求：</span></td>
						<td colspan="2"><textarea id="targetOtherClaim"
								name="targetOtherClaim" rows="10" cols="30"
								placeholder="您可以简要介绍岗位的工作内容。300字以内。请勿输入公司邮箱、联系电话，系统将自动删除，敬请谅解。">${recruitInfo.otherClaim}</textarea></td>
				</table>
			</fieldset>
			<div class="coGJ2_bottom">
				<input type="button" value="返回" onclick="toStepOne()"
					class="coGJ_save" /> <input type="button" value="预览"
					onclick="toJobView()" class="coGJ_save" /> <input type="button"
					value="发布职位" onclick="toMyJobList()" class="coGJ_save" />
				<p>
					想更快收到更多简历？请点<a href="javascript:void(0)"
						style="color: #B7CDDE; margin-left: 5px;">这里</a>
				</p>
			</div>
		</form>
	</div>
	<!--底部-->
	<div class="coGJ2_footer">
		第一站的微信号是：zhancampus，欢迎关注！<br />有问题？您可以电话联系我们：021-21458392；或致信邮箱us@zhancampus.com
	</div>
</body>
</html>