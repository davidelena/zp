/*
 * resume_name_info start
 */
var initResumeNamePage = function() {
	$("#resume_name_edit").hide();
	initResumeNameForm();
};

var showResumeNameEdit = function() {
	$("#resume_name_edit").show();
	$("#resume_name_view").hide();
};

var hideResumeNameEdit = function() {
	$("#resume_name_edit").hide();
	$("#resume_name_view").show();
};

var initResumeNameForm = function() {
	$("#resumeNameForm").validate({
		rules : {
			resumeName : {
				required : true
			}
		},
		messages : {
			resumeName : {
				required : "请输入简历名称",
			}
		},
		errorPlacement : function(label, element) {
			showErrorMsg(label, element);
		},
		submitHandler : function(form) {
			saveResumeNameInfo(form);
		}
	});
};

var showErrorMsg = function(label, element) {
	label.insertAfter(element).css("position", "relative").css("top", "10px").css("left", "480px");
};

var saveResumeNameInfo = function(form) {
	var resumeId = $("#hdResumeId").val();
	var resumeName = $("#resumeName").val();

	$(form).find("#btnResumeName").attr("disabled", true);

	$.ajax({
		url : "resume/saveResumeNameInfo",
		type : "POST",
		data : {
			resumeId : resumeId,
			resumeName : resumeName
		},
		dataType : "json"
	}).done(function(result) {
		if (result.success) {
			$("#resumeName").val(result.resumeName);
			$("#spanResumeName").text(result.resumeName);
			$("#resume_name_edit").hide();
			$("#resume_name_view").show();
		}

		$(form).find("#btnResumeName").attr("disabled", false);
	});
};

var submitResumeNameForm = function() {
	$("#resumeNameForm").submit();
};

/*
 * resume_name_info end
 */

/*
 * resume_basic_info start
 */

var initBasicInfoPage = function() {
	$(".time").datepicker();
	$("#resume_basicinfo_edit").hide();
	$("#new_resume_basic_info").hide();
	initUniversityDialog();
	$(".ui-dialog-buttonset").find(".ui-button").eq(0).hide();
	initBasicInfoForm();
};

// 大学选项框
var initUniversityDialog = function() {
	$("#dialog_school").dialog({
		autoOpen : false,
		title : "请选择学校",
		show : {
			duration : 200
		},
		hide : {
			duration : 200
		},
		resizable : false,
		height : 400,
		width : 890,
		modal : true,
		buttons : {
			"省份" : function() {
				toProvince();
			},
			"确认" : function() {
				saveUniversityData();
				$("#dialog_school").dialog("close");
			},
			"取消" : function() {
				$("#dialog_school").dialog("close");
			}
		}
	});
};

var saveUniversityData = function() {
	var checkedItem = $("input[name='chkUniversity']:checked");
	$("#resumeSchool").val($(checkedItem).attr("desc"));
	$("#hdResumeSchoolId").val($(checkedItem).val());
};

var showUniversityDialog = function() {
	$("#dialog_school").dialog("open");
	var id = $("#hdResumeSchoolId").val();
	$("input[name='chkUniversity']").each(function(index, item) {
		if (id == $(item).val()) {
			$(item).attr("checked", true);
		}
	});
};

var showSchoolDetailDiv = function() {
	var proviceId = $("input[name='chkUniversityGeo']:checked").val();
	$("#school_geo").hide();
	loadUniversity(proviceId);
	$(".ui-dialog-buttonset").find(".ui-button").eq(0).show();
};

var loadUniversity = function(proviceId) {
	$.ajax({
		url : "resume/loadUniversity",
		type : "post",
		dataType : "json",
		data : {
			proviceId : proviceId,
		},
		success : function(data) {
			var html = "";
			var tempHtml = "";
			if (data.success == 1) {
				for (var i = 0; i < data.list.length; i++) {
					if (i % 4 == 0) {
						html += '<tr>';
					}
					tempHtml = '<td class="borderStyle"><input type="radio" name="chkUniversity" value="'
							+ data.list[i].id + '" desc="' + data.list[i].name
							+ '" onclick="saveUniversityData();" style="margin-left: 6px; cursor: pointer;" />'
							+ data.list[i].name + '</td>';
					html += tempHtml;
					if ((i + 1) % 4 == 0) {
						html += '</tr>';
					}

				}
			}

			$("#school_detail").html(html);
			$("#school_detail").show();
		}
	});
};

var toProvince = function() {
	$("#school_detail").hide();
	$("#school_geo").show();
	$(".ui-dialog-buttonset").find(".ui-button").eq(0).hide();
};

var showBasicEdit = function() {
	$("#resume_basicinfo_edit").show();
	$("#resume_basicinfo_view").hide();
};

var hideBasicEdit = function() {
	$("#resume_basicinfo_edit").hide();
	$("#resume_basicinfo_view").show();
};

var addBasicEdit = function() {
	var html = $("#new_resume_basic_info").html();
	$("#resume_basicinfo_edit").appendTo(html);
	$("#new_resume_basic_info").show();
};

var initBasicInfoForm = function() {
	$("#resumeBasicInfoForm").validate({
		rules : {
			resumeMemberName : {
				required : true
			},
			resumeSchool : {
				required : true
			},
			resumeEmail : {
				required : true,
				email : true
			},
			resumeMobile : {
				required : true,
				number : true,
				minlength : 11
			}
		},
		messages : {
			resumeMemberName : {
				required : "请输入姓名"
			},
			resumeSchool : {
				required : "请选择学校"
			},
			resumeEmail : {
				required : "请输入邮箱",
				email : "邮箱格式不正确"
			},
			resumeMobile : {
				required : "请输入手机",
				number : "手机号码必须为数字",
				minlength : "手机号码最少为11位"
			}
		},
		errorPlacement : function(label, element) {
			showBasicErrorMsg(label, element);
		},
		submitHandler : function(form) {
			saveResumeBasicInfo(form);
		}
	});
};

var showBasicErrorMsg = function(label, element) {
	label.insertAfter(element).css("margin-left", "10px");
};

var saveResumeBasicInfo = function(form) {
	$("#btnBasicSave").attr("disabled", true);

	$.ajax({
		url : "resume/saveBasicInfo",
		type : "post",
		dataType : "json",
		data : {
			memberId : $("#hdMemberId").val(),
			resumeId : $("#hdResumeId").val(),
			resumeName : $("#resumeName").val(),
			resumeMemberName : $("#resumeMemberName").val(),
			resumeGender : $("#resumeGender").val(),
			resumeSchool : $("#hdResumeSchoolId").val(),
			resumeMajor : $("#resumeMajor").val(),
			resumeDiploma : $("#resumeDiploma").val(),
			resumeGraduationTime : $("#resumeGraduationTime").val(),
			resumeEmail : $("#resumeEmail").val(),
			resumeMobile : $("#resumeMobile").val()
		}
	}).done(function(data) {
		if (data.success) {
			hideBasicEdit();
			$("#memberNameView").text(data.resumeInfo.memberDTO.name);
			$("#memberSchoolView").text(data.resumeInfo.memberDTO.schoolDesc);
			$("#memberMobileView").text(data.resumeInfo.memberDTO.mobile);
			$("#memberEmailView").text(data.resumeInfo.memberDTO.commonEmail);
		}

		$("#btnBasicSave").attr("disabled", false);
	});
};

var submitResumeBasicInfoForm = function() {
	$("#resumeBasicInfoForm").submit();
};

// jquery.form.js是表单提交异步化
var initHeadPicForm = function() {
	var resumeId = $("#hdResumeId").val();

	var options = {
		url : "resume/uploadHeadPic",
		type : "POST",
		data : {
			resumeId : resumeId
		},
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$("#errorMsg").text("");
				$("#basicinfo_headpic").attr("src", data.url);
				$("#basicinfo_headpic_view").attr("src", data.url);
				$("#headPic_error").text("");
				$("#headPic_error").hide();
			} else {
				$("#headPic_error").text(data.message);
				$("#headPic_error").show();
			}
		}
	};
	$("#basic_info_headpic_form").ajaxForm(options);
};

// 上传作品文件
var uploadHeadPic = function() {
	initHeadPicForm();
	$("#basic_info_headpic_form").submit();
};

/*
 * resume_basic_info end
 */

// 编辑添加状态下隐藏各个操作保证只能修改其中一个
var disableLinks = function(status) {
	if (status) {
		$(".aLinkEdit").hide();
		$(".aLinkDelete").hide();
		$(".aLinkAdd").hide();
	} else {
		$(".aLinkEdit").show();
		$(".aLinkDelete").show();
		$(".aLinkAdd").show();
	}
};

/*
 * resume_education_exp start
 */

var initResumeEduExpPage = function() {
	$.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
	$("#resumeEduExpAcademicStarts").datepicker({
		onClose : function(selectedDate) {
			$("#resumeEduExpGraduationTime").datepicker("option", "minDate", selectedDate);
		}
	});
	$("#resumeEduExpGraduationTime").datepicker({
		onClose : function(selectedDate) {
			$("#resumeEduExpAcademicStarts").datepicker("option", "maxDate", selectedDate);
		}
	});
	$("#resume_educationexp_edit_module").hide();
	var count = $("#hdResumeEducationExpSize").val();
	if (count == 0) {
		$("#resume_educationexp_view_module").hide();
		$("#resume_neweduexp_div").show();
	}
	if (count >= 5) {
		$("#aLinkEduAddDiv").hide();
	}
	initResumeEduExpForm();
};

// 保存教育经历
var initResumeEduExpForm = function() {
	$("#resumeEduExpForm").validate({
		rules : {
			resumeEduExpSchool : {
				required : true
			},
			resumeEduExpMajor : {
				required : true
			},
			resumeEduExpAcademicStarts : {
				required : true
			},
			resumeEduExpGraduationTime : {
				required : true
			}
		},
		messages : {
			resumeEduExpSchool : {
				required : "请选择学校"
			},
			resumeEduExpMajor : {
				required : "请输入专业"
			},
			resumeEduExpAcademicStarts : {
				required : "请选择入学时间"
			},
			resumeEduExpGraduationTime : {
				required : "请选择毕业时间"
			}
		},
		errorPlacement : function(label, element) {
			showResumeExpErrorMsg(label, element);
		},
		submitHandler : function(form) {
			saveResumeEduExp(form);
		}
	});
};

var showResumeExpErrorMsg = function(label, element) {
	label.insertAfter(element).css("margin-left", "10px");
};

var saveResumeEduExp = function(form) {
	var eduExpId = $("#hdEduEditId").val();
	var resumeId = $("#hdResumeId").val();
	$("#btnResumeEduExp").attr("disabled", true);

	$.ajax({
		url : "resume/saveResumeEduExp",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			resumeEduExpId : eduExpId,
			resumeEduExpDiploma : $("#resumeEduExpDiploma").val(),
			resumeEduExpSchool : $("#resumeEduExpSchool").val(),
			resumeEduExpMajor : $("#resumeEduExpMajor").val(),
			resumeEduExpMajorType : $("#resumeEduExpMajorType").val(),
			resumeEduExpAcademicStarts : $("#resumeEduExpAcademicStarts").val(),
			resumeEduExpGraduationTime : $("#resumeEduExpGraduationTime").val(),
			resumeEduExpScoreTop : $("#resumeEduExpScoreTop").val()
		}
	}).done(function(data) {
		if (data.success) {
			if (eduExpId > 0) {
				var newView = $("#edu_exp_" + eduExpId);
				$(newView).html(data.resumeEducationExpHtml);
				$(newView).show();
			} else {
				$("#resume_educationexp_view").append(data.resumeEducationExpHtml);
			}
			$("#resume_neweduexp_div").hide();
			$("#resume_educationexp_view_module").show();
			$("#hdResumeEducationExpSize").val(data.resumeEducationExpSize);
			if (data.resumeEducationExpSize >= 5) {
				$("#aLinkEduAddDiv").hide();
			} else {
				$("#aLinkEduAddDiv").show();
			}
			disableLinks(false);
			$("#resume_educationexp_edit_module").hide();
		}

		$("#btnResumeEduExp").attr("disabled", false);
	});
};

var submitResumeEduExpForm = function() {
	$("#resumeEduExpForm").submit();
};

// 添加教育经历
var addResumeEducationExp = function() {
	$("#p_educationexp_title").removeClass("none");
	$("#aLinkEduAddDiv").hide();
	$("#resume_educationexp_edit_module").show();
	$("#resume_educationexp_edit_module").insertAfter($("#resume_educationexp_view_module").parent());
	$("#resumeEduExpDiploma").val(1);
	$("#resumeEduExpSchool").val("");
	$("#resumeEduExpMajor").val("");
	$("#resumeEduExpMajorType").val(1);
	$("#resumeEduExpAcademicStarts").val("");
	$("#resumeEduExpGraduationTime").val("");
	$("#resumeEduExpScoreTop").val(1);
	$("#hdEduEditId").val(0);

	var totalSize = $("#hdResumeEducationExpSize").val();
	if (totalSize == 0) {
		$("#resume_neweduexp_div").hide();
	}
	disableLinks(true);
};

// 编辑教育经历
var editResumeEduExp = function(id) {
	$("#p_educationexp_title").addClass("none");
	$("#resumeEduExpForm").find(".error").removeClass("error");
	$("#resumeEduExpForm").find("label").hide();
	$("#aLinkEduAddDiv").hide();
	var hideView = $("#edu_exp_" + id);
	$(hideView).hide();
	disableLinks(true);
	$("#resume_educationexp_edit_module").insertAfter($(hideView));
	$("#hdEduEditId").val(id);

	var resumeEduDetail = $("#edu_exp_detail_" + id);

	var resumeEduExpDiploma = $(resumeEduDetail).attr("diploma");
	var resumeEduExpSchool = $(resumeEduDetail).attr("school");
	var resumeEduExpMajor = $(resumeEduDetail).attr("major");
	var resumeEduExpMajorType = $(resumeEduDetail).attr("majorType");
	var resumeEduExpAcademicStarts = $(resumeEduDetail).attr("academicStartsDesc");
	var resumeEduExpGraduationTime = $(resumeEduDetail).attr("graduationTimeDesc");
	var resumeEduExpScoreTop = $(resumeEduDetail).attr("scoreTop");

	$("#resume_educationexp_edit_module").show();
	$("#resumeEduExpDiploma").val(resumeEduExpDiploma);
	$("#resumeEduExpSchool").val(resumeEduExpSchool);
	$("#resumeEduExpMajor").val(resumeEduExpMajor);
	$("#resumeEduExpMajorType").val(resumeEduExpMajorType);
	$("#resumeEduExpAcademicStarts").val(resumeEduExpAcademicStarts);
	$("#resumeEduExpGraduationTime").val(resumeEduExpGraduationTime);
	$("#resumeEduExpScoreTop").val(resumeEduExpScoreTop);

};

// 删除教育经历
var deleteResumeEduExp = function(id) {
	if (confirm("确认删除此条目?")) {
		$.ajax({
			url : "resume/deleteResumeEduExp",
			type : "post",
			dataType : "json",
			data : {
				resumeEduExpId : id,
				resumeId : $("#hdResumeId").val()
			},
			success : function(data) {
				if (data.success) {
					$("#hdResumeEducationExpSize").val(data.resumeEducationExpSize);
					$("#edu_exp_" + id).remove();
					if (data.resumeEducationExpSize == 0) {
						$("#resume_neweduexp_div").show();
						$("#resume_educationexp_view_module").hide();
					} else if (data.resumeEducationExpSize > 0 && data.resumeEducationExpSize < 5) {
						$("#aLinkEduAddDiv").show();
					}
				}
			}
		});
	}
};

// 取消编辑教育经历
var cancelResumeEduExp = function() {
	var totalSize = $("#hdResumeEducationExpSize").val();
	if (totalSize > 0) {
		$("#aLinkEduAddDiv").show();
		$("#resume_educationexp_edit_module").hide();
		var editId = $("#hdEduEditId").val();
		var showView = $("#edu_exp_" + editId);
		$(showView).show();
	} else {
		$("#resume_neweduexp_div").show();
		$("#resume_educationexp_edit_module").hide();
	}
	disableLinks(false);
};

/*
 * resume_education_exp end
 */

/*
 * resume_work_exp start
 */

var initResumeWorkExpPage = function() {
	$.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
	$("#resumeWorkExpStartTime").datepicker({
		onClose : function(selectedDate) {
			$("#resumeWorkExpEndTime").datepicker("option", "minDate", selectedDate);
		}
	});
	$("#resumeWorkExpEndTime").datepicker({
		onClose : function(selectedDate) {
			$("#resumeWorkExpStartTime").datepicker("option", "maxDate", selectedDate);
		}
	});
	$("#resume_workexp_edit_module").hide();
	var count = $("#hdResumeWorkExpSize").val();
	if (count == 0) {
		$("#resume_workexp_view_module").hide();
		$("#resume_newworkexp_div").show();
	}
	if (count >= 5) {
		$("#aLinkWorkAddDiv").hide();
	}
	initResumeWorkExpForm();
};

// 保存实习工作经历
var initResumeWorkExpForm = function() {
	$("#resumeWorkExpForm").validate({
		rules : {
			resumeWorkExpCompanyName : {
				required : true
			},
			resumeWorkExpPositionName : {
				required : true
			},
			resumeWorkExpStartTime : {
				required : true
			},
			resumeWorkExpEndTime : {
				required : true
			}
		},
		messages : {
			resumeWorkExpCompanyName : {
				required : "请输入公司名称"
			},
			resumeWorkExpPositionName : {
				required : "请输入职位名称"
			},
			resumeWorkExpStartTime : {
				required : "请选择起始时间"
			},
			resumeWorkExpEndTime : {
				required : "请选择结束时间"
			}
		},
		errorPlacement : function(label, element) {
			showResumeWorkErrorMsg(label, element);
		},
		submitHandler : function(form) {
			saveResumeWorkExp(form);
		}
	});
};

var showResumeWorkErrorMsg = function(label, element) {
	label.insertAfter(element);
};

var saveResumeWorkExp = function(form) {
	var workExpId = $("#hdWorkEditId").val();
	var resumeId = $("#hdResumeId").val();
	$("#btnResumeWorkExp").attr("disabled", true);

	$.ajax({
		url : "resume/saveResumeWorkExp",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			resumeWorkExpId : workExpId,
			resumeWorkExpCompanyName : $("#resumeWorkExpCompanyName").val(),
			resumeWorkExpPositionName : $("#resumeWorkExpPositionName").val(),
			resumeWorkExpStartTime : $("#resumeWorkExpStartTime").val(),
			resumeWorkExpEndTime : $("#resumeWorkExpEndTime").val(),
			resumeWorkExpDesc : $("#resumeWorkExpDesc").val()
		}
	}).done(function(data) {
		if (data.success) {
			if (workExpId > 0) {
				var newView = $("#work_exp_" + workExpId);
				$(newView).html(data.resumeWorkExpHtml);
				$(newView).show();
			} else {
				$("#resume_workexp_view").append(data.resumeWorkExpHtml);
			}
			$("#resume_newworkexp_div").hide();
			$("#resume_workexp_view_module").show();
			$("#hdResumeWorkExpSize").val(data.resumeWorkExpSize);
			if (data.resumeWorkExpSize >= 5) {
				$("#aLinkWorkAddDiv").hide();
			} else {
				$("#aLinkWorkAddDiv").show();
			}
			disableLinks(false);
			$("#resume_workexp_edit_module").hide();
		}

		$("#btnResumeWorkExp").attr("disabled", false);
	});
};

var submitResumeWorkExpForm = function() {
	$("#resumeWorkExpForm").submit();
};

// 添加实习工作经历
var addResumeWorkExp = function() {
	$("#p_workexp_title").removeClass("none");
	$("#aLinkWorkAddDiv").hide();
	$("#resume_workexp_edit_module").show();
	$("#resume_workexp_edit_module").insertAfter($("#resume_workexp_view_module").parent());
	$("#resumeWorkExpCompanyName").val("");
	$("#resumeWorkExpPositionName").val("");
	$("#resumeWorkExpStartTime").val("");
	$("#resumeWorkExpEndTime").val("");
	$("#resumeWorkExpDesc").val("");
	var totalSize = $("#hdResumeWorkExpSize").val();
	if (totalSize == 0) {
		$("#resume_newworkexp_div").hide();
	}
	$("#hdWorkEditId").val(0);
	disableLinks(true);
};

// 编辑实习工作经历
var editResumeWorkExp = function(id) {
	$("#p_workexp_title").addClass("none");
	$("#aLinkWorkAddDiv").hide();
	var hideView = $("#work_exp_" + id);
	$(hideView).hide();
	disableLinks(true);
	$("#resume_workexp_edit_module").insertAfter($(hideView));
	$("#hdWorkEditId").val(id);

	var resumeWorkDetail = $("#work_exp_detail_" + id);

	var resumeWorkExpCompanyName = $(resumeWorkDetail).attr("companyName");
	var resumeWorkExpPositionName = $(resumeWorkDetail).attr("positionName");
	var resumeWorkExpStartTime = $(resumeWorkDetail).attr("startTimeDesc");
	var resumeWorkExpEndTime = $(resumeWorkDetail).attr("endTimeDesc");
	var resumeWorkExpDesc = $(resumeWorkDetail).attr("workDescription");

	$("#resume_workexp_edit_module").show();
	$("#resumeWorkExpCompanyName").val(resumeWorkExpCompanyName);
	$("#resumeWorkExpPositionName").val(resumeWorkExpPositionName);
	$("#resumeWorkExpStartTime").val(resumeWorkExpStartTime);
	$("#resumeWorkExpEndTime").val(resumeWorkExpEndTime);
	$("#resumeWorkExpDesc").val(resumeWorkExpDesc);

};

// 删除实习工作经历
var deleteResumeWorkExp = function(id) {
	if (confirm("确认删除此条目?")) {
		$.ajax({
			url : "resume/deleteResumeWorkExp",
			type : "post",
			dataType : "json",
			data : {
				resumeWorkExpId : id,
				resumeId : $("#hdResumeId").val()
			},
			success : function(data) {
				if (data.success) {
					$("#hdResumeWorkExpSize").val(data.resumeWorkExpSize);
					$("#work_exp_" + id).remove();
					if (data.resumeWorkExpSize == 0) {
						$("#resume_newworkexp_div").show();
						$("#resume_workexp_view_module").hide();
					} else if (data.resumeWorkExpSize > 0 && data.resumeWorkExpSize < 5) {
						$("#aLinkWorkAddDiv").show();
					}
				}
			}
		});
	}
};

// 取消编辑实习工作经历
var cancelResumeWorkExp = function() {
	var totalSize = $("#hdResumeWorkExpSize").val();
	if (totalSize > 0) {
		$("#aLinkWorkAddDiv").show();
		$("#resume_workexp_edit_module").hide();
		var editId = $("#hdWorkEditId").val();
		var showView = $("#work_exp_" + editId);
		$(showView).show();
	} else {
		$("#resume_newworkexp_div").show();
		$("#resume_workexp_edit_module").hide();
	}
	disableLinks(false);
};

/*
 * resume_work_exp end
 */

/*
 * resume_activity_exp start
 */

var initResumeActivityExpPage = function() {
	$.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
	$("#resumeActivityExpStartTime").datepicker({
		onClose : function(selectedDate) {
			$("#resumeActivityExpEndTime").datepicker("option", "minDate", selectedDate);
		}
	});
	$("#resumeActivityExpEndTime").datepicker({
		onClose : function(selectedDate) {
			$("#resumeActivityExpStartTime").datepicker("option", "maxDate", selectedDate);
		}
	});
	$("#resume_activityexp_edit_module").hide();
	var count = $("#hdResumeActivityExpSize").val();
	if (count == 0) {
		$("#resume_activityexp_view_module").hide();
		$("#aLinkActivityAddNew").show();
	} else {
		$("#aLinkActivityAddNew").hide();
	}

	if (count >= 5) {
		$("#aLinkEduAddDiv").hide();
	}
	initResumeActivityExpForm();
};

// 保存校园活动经历
var initResumeActivityExpForm = function() {
	$("#resumeActivityExpForm").validate({
		rules : {
			resumeActivityExpName : {
				required : true
			},
			resumeActivityExpPositionName : {
				required : true
			},
			resumeActivityExpStartTime : {
				required : true
			},
			resumeActivityExpEndTime : {
				required : true
			}
		},
		messages : {
			resumeActivityExpName : {
				required : "请输入组织或活动名称"
			},
			resumeActivityExpPositionName : {
				required : "请输入职位名称"
			},
			resumeActivityExpStartTime : {
				required : "请选择起始时间"
			},
			resumeActivityExpEndTime : {
				required : "请选择结束时间"
			}
		},
		errorPlacement : function(label, element) {
			showResumeActivityErrorMsg(label, element);
		},
		submitHandler : function(form) {
			saveResumeActivityExp(form);
		}
	});
};

var showResumeActivityErrorMsg = function(label, element) {
	label.insertAfter(element);
};

var saveResumeActivityExp = function(form) {
	var activityExpId = $("#hdActivityEditId").val();
	var resumeId = $("#hdResumeId").val();
	$("#btnResumeActivityExp").attr("disabled", true);

	$.ajax({
		url : "resume/saveResumeActivityExp",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			resumeActivityExpId : activityExpId,
			resumeActivityExpName : $("#resumeActivityExpName").val(),
			resumeActivityExpPositionName : $("#resumeActivityExpPositionName").val(),
			resumeActivityExpStartTime : $("#resumeActivityExpStartTime").val(),
			resumeActivityExpEndTime : $("#resumeActivityExpEndTime").val(),
			resumeActivityExpDesc : $("#resumeActivityExpDesc").val()
		}
	}).done(function(data) {
		if (data.success) {
			$("#aLinkActivityAddNew").hide();
			$("#resume_activityexp_view_module").show();
			if (activityExpId > 0) {
				var newView = $("#activity_exp_" + activityExpId);
				$(newView).html(data.resumeActivityExpHtml);
				$(newView).show();
			} else {
				$("#resume_activityexp_view").append(data.resumeActivityExpHtml);
			}
			$("#hdResumeActivityExpSize").val(data.resumeActivityExpSize);
			if (data.resumeActivityExpSize >= 5) {
				$("#aLinkActivityAddDiv").hide();
			} else {
				$("#aLinkActivityAddDiv").show();
			}
			disableLinks(false);
			$("#resume_activityexp_edit_module").hide();
		}

		$("#btnResumeActivityExp").attr("disabled", false);
	});
};

var submitResumeActivityExpForm = function() {
	$("#resumeActivityExpForm").submit();
};

// 添加校园活动经历
var addResumeActivityExp = function() {
	$("#p_activityexp_title").removeClass("none");
	$("#aLinkActivityAddDiv").show();
	$("#resume_activityexp_edit_module").show();
	$("#resume_activityexp_edit_module").insertAfter($("#resume_activityexp_view_module").parent());
	$("#resumeActivityExpName").val("");
	$("#resumeActivityExpPositionName").val("");
	$("#resumeActivityExpStartTime").val("");
	$("#resumeActivityExpEndTime").val("");
	$("#resumeActivityExpDesc").val("");
	$("#hdActivityEditId").val(0);
	var totalSize = $("#hdResumeActivityExpSize").val();
	if (totalSize == 0) {
		$("#aLinkActivityAddNew").hide();
	}

	disableLinks(true);
};

// 编辑校园活动经历
var editResumeActivityExp = function(id) {
	$("#p_activityexp_title").addClass("none");
	$("#aLinkActivityAddDiv").hide();
	var hideView = $("#activity_exp_" + id);
	$(hideView).hide();
	disableLinks(true);
	$("#resume_activityexp_edit_module").insertAfter($(hideView));
	$("#hdActivityEditId").val(id);

	var resumeWorkDetail = $("#activity_exp_detail_" + id);

	var resumeActivityExpName = $(resumeWorkDetail).attr("activityName");
	var resumeActivityExpPositionName = $(resumeWorkDetail).attr("positionName");
	var resumeActivityExpStartTime = $(resumeWorkDetail).attr("startTimeDesc");
	var resumeActivityExpEndTime = $(resumeWorkDetail).attr("endTimeDesc");
	var resumeActivityExpDesc = $(resumeWorkDetail).attr("activityDesc");

	$("#resume_activityexp_edit_module").show();
	$("#resumeActivityExpName").val(resumeActivityExpName);
	$("#resumeActivityExpPositionName").val(resumeActivityExpPositionName);
	$("#resumeActivityExpStartTime").val(resumeActivityExpStartTime);
	$("#resumeActivityExpEndTime").val(resumeActivityExpEndTime);
	$("#resumeActivityExpDesc").val(resumeActivityExpDesc);

};

// 删除校园活动经历
var deleteResumeActivityExp = function(id) {
	if (confirm("确认删除此条目?")) {
		$.ajax({
			url : "resume/deleteResumeActivityExp",
			type : "post",
			dataType : "json",
			data : {
				resumeActivityExpId : id,
				resumeId : $("#hdResumeId").val()
			},
			success : function(data) {
				if (data.success) {
					$("#hdResumeActivityExpSize").val(data.resumeActivityExpSize);
					$("#activity_exp_" + id).remove();
					if (data.resumeActivityExpSize == 0) {
						$("#aLinkActivityAddNew").show();
						$("#resume_activityexp_view_module").hide();
					} else if (data.resumeActivityExpSize > 0 && data.resumeActivityExpSize < 5) {
						$("#aLinkActivityAddDiv").show();
					}
				}
			}
		});
	}
};

// 取消编辑校园活动经历
var cancelResumeActivityExp = function() {
	var totalSize = $("#hdResumeActivityExpSize").val();
	if (totalSize > 0) {
		$("#aLinkActivityAddDiv").show();
		$("#resume_activityexp_edit_module").hide();
		var editId = $("#hdActivityEditId").val();
		var showView = $("#activity_exp_" + editId);
		$(showView).show();
	} else {
		$("#aLinkActivityAddNew").show();
		$("#resume_activityexp_edit_module").hide();
	}
	disableLinks(false);
};

/*
 * resume_activity_exp end
 */

/*
 * resume_project_exp start
 */

var initResumeProjectExpPage = function() {
	$.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
	$("#resumeProjectExpStartTime").datepicker({
		onClose : function(selectedDate) {
			$("#resumeProjectExpEndTime").datepicker("option", "minDate", selectedDate);
		}
	});
	$("#resumeProjectExpEndTime").datepicker({
		onClose : function(selectedDate) {
			$("#resumeProjectExpStartTime").datepicker("option", "maxDate", selectedDate);
		}
	});
	$("#resume_projectexp_edit_module").hide();
	var count = $("#hdResumeProjectExpSize").val();
	if (count == 0) {
		$("#resume_projectexp_view_module").hide();
		$("#aLinkProjectAddNew").show();
	} else {
		$("#aLinkProjectAddNew").hide();
	}

	if (count >= 5) {
		$("#aLinkProjectAddDiv").hide();
	}
	initResumeProjectExpForm();
};

var initResumeProjectExpForm = function() {
	$("#resumeProjectExpForm").validate({
		rules : {
			resumeProjectExpName : {
				required : true
			},
			resumeProjectExpPositionName : {
				required : true
			},
			resumeProjectExpStartTime : {
				required : true
			},
			resumeProjectExpEndTime : {
				required : true
			}
		},
		messages : {
			resumeProjectExpName : {
				required : "请输入项目名称"
			},
			resumeProjectExpPositionName : {
				required : "请输入项目职位"
			},
			resumeProjectExpStartTime : {
				required : "请输入起始时间"
			},
			resumeProjectExpEndTime : {
				required : "请输入结束时间"
			}
		},
		errorPlacement : function(label, element) {
			showResumeProjectExpErrorMsg(label, element);
		},
		submitHandler : function(form) {
			saveResumeProjectExp(form);
		}
	});
};

var showResumeProjectExpErrorMsg = function(label, element) {
	label.insertAfter(element);
};

// 保存项目经验
var saveResumeProjectExp = function(form) {
	var projectExpId = $("#hdProjectEditId").val();
	var resumeId = $("#hdResumeId").val();
	$("#btnResumeProjectExp").attr("disabled", true);

	$.ajax({
		url : "resume/saveResumeProjectExp",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			resumeProjectExpId : projectExpId,
			resumeProjectExpName : $("#resumeProjectExpName").val(),
			resumeProjectExpPositionName : $("#resumeProjectExpPositionName").val(),
			resumeProjectExpStartTime : $("#resumeProjectExpStartTime").val(),
			resumeProjectExpEndTime : $("#resumeProjectExpEndTime").val(),
			resumeProjectExpDesc : $("#resumeProjectExpDesc").val()
		}
	}).done(function(data) {
		if (data.success) {
			$("#aLinkProjectAddNew").hide();
			$("#resume_projectexp_view_module").show();
			if (projectExpId > 0) {
				var newView = $("#project_exp_" + projectExpId);
				$(newView).html(data.resumeProjectExpHtml);
				$(newView).show();
			} else {
				$("#resume_projectexp_view").append(data.resumeProjectExpHtml);
			}
			$("#hdResumeProjectExpSize").val(data.resumeProjectExpSize);
			if (data.resumeProjectExpSize >= 5) {
				$("#aLinkProjectAddDiv").hide();
			} else {
				$("#aLinkProjectAddDiv").show();
			}
			disableLinks(false);
			$("#resume_projectexp_edit_module").hide();
		}

		$("#btnResumeProjectExp").attr("disabled", false);
	});
};

var submitResumeProjectExpForm = function() {
	$("#resumeProjectExpForm").submit();
};

// 添加项目经验
var addResumeProjectExp = function() {
	$("#p_projectexp_title").removeClass("none");
	$("#aLinkProjectAddDiv").show();
	$("#resume_projectexp_edit_module").show();
	$("#resume_projectexp_edit_module").insertAfter($("#resume_projectexp_view_module").parent());
	$("#resumeProjectExpName").val("");
	$("#resumeProjectExpPositionName").val("");
	$("#resumeProjectExpStartTime").val("");
	$("#resumeProjectExpEndTime").val("");
	$("#resumeProjectExpDesc").val("");
	$("#hdProjectEditId").val(0);
	var totalSize = $("#hdResumeProjectExpSize").val();
	if (totalSize == 0) {
		$("#aLinkProjectAddNew").hide();
	}
	disableLinks(true);
};

// 编辑项目经验
var editResumeProjectExp = function(id) {
	$("#p_projectexp_title").addClass("none");
	var hideView = $("#project_exp_" + id);
	$(hideView).hide();
	disableLinks(true);
	$("#resume_projectexp_edit_module").insertAfter($(hideView));
	$("#hdProjectEditId").val(id);

	var resumeWorkDetail = $("#project_exp_detail_" + id);
	var resumeProjectExpName = $(resumeWorkDetail).attr("projectName");
	var resumeProjectExpPositionName = $(resumeWorkDetail).attr("positionName");
	var resumeProjectExpStartTime = $(resumeWorkDetail).attr("startTimeDesc");
	var resumeProjectExpEndTime = $(resumeWorkDetail).attr("endTimeDesc");
	var resumeProjectExpDesc = $(resumeWorkDetail).attr("projectDesc");

	$("#resume_projectexp_edit_module").show();
	$("#resumeProjectExpName").val(resumeProjectExpName);
	$("#resumeProjectExpPositionName").val(resumeProjectExpPositionName);
	$("#resumeProjectExpStartTime").val(resumeProjectExpStartTime);
	$("#resumeProjectExpEndTime").val(resumeProjectExpEndTime);
	$("#resumeProjectExpDesc").val(resumeProjectExpDesc);

};

// 删除项目经验
var deleteResumeProjectExp = function(id) {
	if (confirm("确认删除此条目?")) {
		$.ajax({
			url : "resume/deleteResumeProjectExp",
			type : "post",
			dataType : "json",
			data : {
				resumeProjectExpId : id,
				resumeId : $("#hdResumeId").val()
			},
			success : function(data) {
				if (data.success) {
					$("#hdResumeProjectExpSize").val(data.resumeProjectExpSize);
					$("#project_exp_" + id).remove();
					if (data.resumeProjectExpSize == 0) {
						$("#aLinkProjectAddNew").show();
						$("#resume_projectexp_view_module").hide();
					} else if (data.resumeProjectExpSize > 0 && data.resumeProjectExpSize < 5) {
						$("#aLinkProjectAddDiv").show();
					}
				}
			}
		});
	}
};

// 取消编辑项目经验
var cancelResumeProjectExp = function() {
	var totalSize = $("#hdResumeProjectExpSize").val();
	if (totalSize > 0) {
		$("#resume_projectexp_edit_module").hide();
		var editId = $("#hdProjectEditId").val();
		var showView = $("#project_exp_" + editId);
		$(showView).show();
	} else {
		$("#aLinkProjectAddNew").show();
		$("#resume_projectexp_edit_module").hide();
	}
	disableLinks(false);
};

/*
 * resume_project_exp end
 */

/*
 * resume_hobby_special start
 */

var initResumeHobbySpecialPage = function() {
	$("#resume_hobbyspecial_edit_module").hide();
	var count = $("#hdResumeHobbySpecialSize").val();
	if (count == 0) {
		$("#resume_hobbyspecial_view_module").hide();
		$("#aLinkHobbySpecialAddNew").show();
	} else {
		$("#aLinkHobbySpecialAddNew").hide();
	}

	if (count >= 5) {
		$("#aLinkHobbySpecialAddDiv").hide();
	}
	initResumeHobbySpecialForm();
};

var initResumeHobbySpecialForm = function() {
	$("#resumeHobbySpecialForm").validate({
		rules : {
			resumeHobbySpecialName : {
				required : true
			}
		},
		messages : {
			resumeHobbySpecialName : {
				required : "请输入爱好特长名称"
			}
		},
		errorPlacement : function(label, element) {
			showResumeHobbySpecialErrorMsg(label, element);
		},
		submitHandler : function(form) {
			saveResumeHobbySpecialExp(form);
		}
	});
};

var showResumeHobbySpecialErrorMsg = function(label, element) {
	label.insertAfter(element);
};

// 保存爱好特长经历
var saveResumeHobbySpecialExp = function(form) {
	var hobbySpecialId = $("#hdHobbySpecialEditId").val();
	var resumeId = $("#hdResumeId").val();
	$("#btnResumeHobbySpecial").attr("disabled", true);

	$.ajax({
		url : "resume/saveResumeHobbySpecialExp",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			resumeHobbySpecialId : hobbySpecialId,
			resumeHobbySpecialName : $("#resumeHobbySpecialName").val(),
			resumeHobbySpecialDesc : $("#resumeHobbySpecialDesc").val()
		}
	}).done(function(data) {
		if (data.success) {
			$("#aLinkHobbySpecialAddNew").hide();
			$("#resume_hobbyspecial_view_module").show();
			if (hobbySpecialId > 0) {
				var newView = $("#hobby_special_" + hobbySpecialId);
				$(newView).html(data.resumeHobbySpeciaHtml);
				$(newView).show();
			} else {
				$("#resume_hobbyspecial_view").append(data.resumeHobbySpeciaHtml);
			}
			$("#hdResumeHobbySpecialSize").val(data.resumeHobbySpecialSize);
			if (data.resumeHobbySpecialSize >= 5) {
				$("#aLinkHobbySpecialAddDiv").hide();
			} else {
				$("#aLinkHobbySpecialAddDiv").show();
			}
			disableLinks(false);
			$("#resume_hobbyspecial_edit_module").hide();
		}

		$("#btnResumeHobbySpecial").attr("disabled", false);
	});
};

var submitResumeHobbySpecialForm = function() {
	$("#resumeHobbySpecialForm").submit();
};

// 添加爱好特长经历
var addResumeHobbySpecialExp = function() {
	$("#p_hobbyspecial_title").removeClass("none");
	$("#aLinkHobbySpecialAddDiv").show();
	$("#resume_hobbyspecial_edit_module").show();
	$("#resume_hobbyspecial_edit_module").insertAfter($("#resume_hobbyspecial_view_module").parent());
	$("#resumeHobbySpecialName").val("");
	$("#resumeHobbySpecialDesc").val("");
	$("#hdActivityEditId").val(0);
	var totalSize = $("#hdResumeHobbySpecialSize").val();
	if (totalSize == 0) {
		$("#aLinkHobbySpecialAddNew").hide();
	}

	disableLinks(true);
};

// 编辑爱好特长经历
var editResumeHobbySpecialExp = function(id) {
	$("#p_hobbyspecial_title").addClass("none");
	$("#aLinkHobbySpecialAddDiv").hide();
	var hideView = $("#hobby_special_" + id);
	$(hideView).hide();
	disableLinks(true);

	$("#resume_hobbyspecial_edit_module").insertAfter($(hideView));
	$("#hdHobbySpecialEditId").val(id);

	var resumeWorkDetail = $("#hobby_special_detail_" + id);
	var resumeHobbySpecialName = $(resumeWorkDetail).attr("specialName");
	var resumeHobbySpecialDesc = $(resumeWorkDetail).attr("specialDesc");

	$("#resume_hobbyspecial_edit_module").show();
	$("#resumeHobbySpecialName").val(resumeHobbySpecialName);
	$("#resumeHobbySpecialDesc").val(resumeHobbySpecialDesc);

};

// 删除爱好特长经历
var deleteResumeHobbySpecialExp = function(id) {
	if (confirm("确认删除此条目?")) {
		$.ajax({
			url : "resume/deleteResumeHobbySpecialExp",
			type : "post",
			dataType : "json",
			data : {
				resumeHobbySpecialId : id,
				resumeId : $("#hdResumeId").val()
			},
			success : function(data) {
				if (data.success) {
					$("#hdResumeHobbySpecialSize").val(data.resumeHobbySpecialSize);
					$("#hobby_special_" + id).remove();
					if (data.resumeHobbySpecialSize == 0) {
						$("#aLinkHobbySpecialAddNew").show();
						$("#resume_hobbyspecial_view_module").hide();
					} else if (data.resumeHobbySpecialSize > 0 && data.resumeHobbySpecialSize < 5) {
						$("#aLinkHobbySpecialAddDiv").show();
					}
				}
			}
		});
	}
};

// 取消编辑爱好特长经历
var cancelResumeHobbySpecialExp = function() {
	var totalSize = $("#hdResumeHobbySpecialSize").val();
	if (totalSize > 0) {
		$("#aLinkHobbySpecialAddDiv").show();
		$("#resume_hobbyspecial_edit_module").hide();
		var editId = $("#hdHobbySpecialEditId").val();
		var showView = $("#hobby_special_" + editId);
		$(showView).show();
	} else {
		$("#aLinkHobbySpecialAddNew").show();
		$("#resume_hobbyspecial_edit_module").hide();
	}
	disableLinks(false);
};

/*
 * resume_hobby_special end
 */

/*
 * resume_prizeinfo start
 */

var initResumePrizeInfoPage = function() {
	$("#resume_prizeinfo_edit_module").hide();
	var count = $("#hdResumePrizeInfoSize").val();
	if (count == 0) {
		$("#resume_prizeinfo_view_module").hide();
		$("#aLinkPrizeInfoAddNew").show();
	} else {
		$("#aLinkPrizeInfoAddNew").hide();
	}

	if (count >= 5) {
		$("#aLinkPrizeInfoAddDiv").hide();
	}
	initResumePrizeForm();
};

// 保存所获奖项
var initResumePrizeForm = function() {
	$("#resumePrizeForm").validate({
		rules : {
			resumePrizeName : {
				required : true
			},
			resumeGainTime : {
				required : true
			}
		},
		messages : {
			resumePrizeName : {
				required : "请输入奖项名称"
			},
			resumeGainTime : {
				required : "请输入获奖时间"
			}
		},
		errorPlacement : function(label, element) {
			showResumePrizeErrorMsg(label, element);
		},
		submitHandler : function(form) {
			saveResumePrizeInfo(form);
		}
	});
};

var showResumePrizeErrorMsg = function(label, element) {
	label.insertAfter(element);
};

var saveResumePrizeInfo = function(form) {
	var prizeInfoId = $("#hdPrizeInfoEditId").val();
	var resumeId = $("#hdResumeId").val();
	$("#btnResumePrizeInfo").attr("disabled", true);

	$.ajax({
		url : "resume/saveResumePrizeInfo",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			resumePrizeInfoId : prizeInfoId,
			resumePrizeName : $("#resumePrizeName").val(),
			resumePrizeLevel : $("#resumePrizeLevel").val(),
			resumeGainTime : $("#resumeGainTime").val()
		}
	}).done(function(data) {
		if (data.success) {
			$("#aLinkPrizeInfoAddNew").hide();
			$("#resume_prizeinfo_view_module").show();
			if (prizeInfoId > 0) {
				var newView = $("#hobby_prizeinfo_" + prizeInfoId);
				$(newView).html(data.resumePrizeInfoHtml);
				$(newView).show();
			} else {
				$("#resume_prizeinfo_view").append(data.resumePrizeInfoHtml);
			}
			$("#hdResumePrizeInfoSize").val(data.resumePrizeInfoSize);
			if (data.resumePrizeInfoSize >= 5) {
				$("#aLinkPrizeInfoAddDiv").hide();
			} else {
				$("#aLinkPrizeInfoAddDiv").show();
			}
			disableLinks(false);
			$("#resume_prizeinfo_edit_module").hide();
		}

		$("#btnResumePrizeInfo").attr("disabled", false);
	});
};

var submitResumePrizeForm = function() {
	$("#resumePrizeForm").submit();
};

// 添加所获奖励经历
var addResumePrizeInfo = function() {
	$("#p_prizeinfo_title").removeClass("none");
	$("#aLinkPrizeInfoAddDiv").show();
	$("#resume_prizeinfo_edit_module").show();
	$("#resume_prizeinfo_edit_module").insertAfter($("#resume_prizeinfo_view_module").parent());
	$("#resumePrizeName").val("");
	$("#resumePrizeLevel").val(1);
	$("#resumeGainTime").val("");
	$("#hdPrizeInfoEditId").val(0);
	var totalSize = $("#hdResumePrizeInfoSize").val();
	if (totalSize == 0) {
		$("#aLinkPrizeInfoAddNew").hide();
	}

	disableLinks(true);
};

// 编辑所获奖励经历
var editResumePrizeInfo = function(id) {
	$("#p_prizeinfo_title").addClass("none");
	$("#aLinkPrizeInfoAddDiv").hide();
	var hideView = $("#hobby_prizeinfo_" + id);
	$(hideView).hide();
	disableLinks(true);
	$("#resume_prizeinfo_edit_module").insertAfter($(hideView));
	$("#hdPrizeInfoEditId").val(id);

	var resumePrizeInfo = $("#hobby_prizeinfo_detail_" + id);

	var resumePrizeName = $(resumePrizeInfo).attr("prizeName");
	var resumePrizeLevel = $(resumePrizeInfo).attr("prizeLevel");
	var resumeGainTime = $(resumePrizeInfo).attr("gainTimeDesc");

	$("#resume_prizeinfo_edit_module").show();
	$("#resumePrizeName").val(resumePrizeName);
	$("#resumePrizeLevel").val(resumePrizeLevel);
	$("#resumeGainTime").val(resumeGainTime);

};

// 删除所获奖励经历
var deletePrizeInfo = function(id) {
	if (confirm("确认删除此条目?")) {
		$.ajax({
			url : "resume/deletePrizeInfo",
			type : "post",
			dataType : "json",
			data : {
				resumePrizeInfoId : id,
				resumeId : $("#hdResumeId").val()
			},
			success : function(data) {
				if (data.success) {
					$("#hdResumePrizeInfoSize").val(data.resumePrizeInfoSize);
					$("#hobby_prizeinfo_" + id).remove();
					if (data.resumePrizeInfoSize == 0) {
						$("#aLinkPrizeInfoAddNew").show();
						$("#resume_prizeinfo_view_module").hide();
					} else if (data.resumePrizeInfoSize > 0 && data.resumePrizeInfoSize < 5) {
						$("#aLinkPrizeInfoAddDiv").show();
					}
				}
			}
		});
	}
};

// 取消编辑所获奖励经历
var cancelResumePrizeInfo = function() {
	var totalSize = $("#hdResumePrizeInfoSize").val();
	if (totalSize > 0) {
		$("#aLinkPrizeInfoAddDiv").show();
		$("#resume_prizeinfo_edit_module").hide();
		var editId = $("#hdPrizeInfoEditId").val();
		var showView = $("#hobby_prizeinfo_" + editId);
		$(showView).show();
	} else {
		$("#aLinkPrizeInfoAddNew").show();
		$("#resume_prizeinfo_edit_module").hide();
	}
	disableLinks(false);
};

/*
 * resume_prizeinfo end
 */

/*
 * resume_socialnet start
 */

var initResumeSocialNetPage = function() {
	$("#resume_socialnet_edit_module").hide();
	$("#resume_socialnet_button_module").hide();
	var count = $("#hdResumeSocialNetSize").val();
	if (count == 0) {
		$("#resume_socialnet_view_module").hide();
		$("#aLinkSocialNetAddNew").show();
		$("#closeResumeSocialNet").show();
	} else {
		$("#aLinkSocialNetAddNew").hide();
		$("#closeResumeSocialNet").hide();
	}

	if (count >= 5) {
		$("#aLinkSocialNetAddDiv").hide();
	}

};

var editResumeSocialNet = function() {
	$("#resume_socialnet_edit_module").show();
	$("#resume_socialnet_button_module").show();
	$("#resume_socialnet_view_module").hide();
	disableLinks(true);
	$(".aLinkSnDelete").show();
};

var showResumeSocialNetModule = function() {
	$("#resume_socialnet_edit_module").show();
	addResumeSocialNet();
};

var addInfos = [];
var editInfos = [];
var delIds = [];
var editIds = [];
var index = 0;

var addResumeSocialNet = function() {
	var html = '<li type="add_'
			+ index
			+ '" class="addclass"><img src="images_zp/graduate.png" style="padding-left: 10px;" alt="社交网络"><select id="resumeSocialNetAccount" name="resumeSocialNetAccount" class="mn">'
			+ '<option value="1">微博</option><option value="2">知乎</option><option value="3">领英</option><option value="4">Lofter</option>'
			+ '<option value="5">豆瓣</option><option value="6">微博</option></select> '
			+ '<img src="images_zp/major.png" alt="链接地址"><input type="text" placeholder="请输入链接，如：www.zhihu.com" id="resumeSocialNetUrl" '
			+ 'name="resumeSocialNetUrl" class="mn socialnet_url" style="width: 270px;" value="" />'
			+ '<a style="display: inline; margin-left: 15px;" class="aLinkDelete" href="javascript:;" onclick="deleteResumeSocialNet(\'add_'
			+ index + '\');">删除</a></li>';
	index++;
	totalSocialNet++;
	$(html).appendTo("#resume_socialnet_edit_ul");
	if (totalSocialNet >= 5) {
		$("#btnAddSocialNet").hide();
	}
};

var deleteResumeSocialNet = function(id) {
	if (confirm("确认删除此条目?")) {
		if (id.indexOf("add_") != -1) {
			$("li[type='" + id + "']").remove();
		} else {
			delIds.push(id);
			$("#socialNetRow_" + id).remove();
		}
		totalSocialNet--;
	}
	if (totalSocialNet < 5) {
		$("#aLinkSocialNetAddDiv").show();
		$("#btnAddSocialNet").show();
	}
	if (totalSocialNet == 0) {
		$("#closeResumeSocialNet").hide();
	} else {
		$("#closeResumeSocialNet").show();
	}
};

// 记录以,分割，没条记录的item用-分割
var saveResumeSocialNet = function() {
	var editIdStr = $("#hdSocialNetEditIds").val();
	editIds = editIdStr.indexOf(",") != -1 ? editIdStr.split(',') : [ editIdStr ];

	$(delIds).each(function(index, item) {
		// 如果原编辑id中有删除id则需要将editIds数组中的编辑id去除，表示需要删除该记录
		if ($.inArray(item, editIds)) {
			editIds.splice($.inArray(item, editIds), 1);
		}
	});

	$(editIds).each(function(index, item) {
		var editInfo = $("#socialNetRow_" + item);
		var resumeSocialNetAccount = $(editInfo).find("select[id='resumeSocialNetAccount']").val();
		var resumeSocialNetUrl = $(editInfo).find("input[id='resumeSocialNetUrl']").val();
		if (resumeSocialNetAccount != undefined && resumeSocialNetUrl != undefined) {
			var info = item + "-" + resumeSocialNetAccount + "-" + resumeSocialNetUrl;
			editInfos.push(info);
		}
	});

	$("li[class='addclass']").each(function(index, item) {
		var resumeSocialNetAccount = $(item).find("select[id='resumeSocialNetAccount']").val();
		var resumeSocialNetUrl = $(item).find("input[id='resumeSocialNetUrl']").val();
		var info = resumeSocialNetAccount + "-" + resumeSocialNetUrl;
		addInfos.push(info);
	});

	var delIdsArgs = delIds.join(",");
	var editInfosArgs = editInfos.join(",");
	var addInfosArgs = addInfos.join(",");

	var resumeId = $("#hdResumeId").val();
	$.ajax({
		url : "resume/saveResumeSocialNet",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			delIds : delIdsArgs,
			editInfos : editInfosArgs,
			addInfos : addInfosArgs
		},
		success : function(data) {
			if (data.success) {
				$("#resume_socialnet_edit_module").hide();
				$("#resume_socialnet_edit_ul").html(data.resumeSocialNetEditHtml);
				$("#resume_socialnet_view").html(data.resumeSocialNetViewHtml);
				$("#resume_socialnet_view_module").show();
				$("#aLinkSocialNetEdit").show();
				$("#hdSocialNetEditIds").val(data.hdSocialNetEditIds);
				disableLinks(false);
				if (data.resumeSocialNetSize > 0 && data.resumeSocialNetSize < 5) {
					$("#aLinkSocialNetAddDiv").show();
					$("#aLinkSocialNetAddNew").hide();
					$("#closeResumeSocialNet").hide();
				} else if (data.resumeSocialNetSize >= 5) {
					$("#aLinkSocialNetAddDiv").hide();
					$("#aLinkSocialNetAddNew").hide();
					$("#closeResumeSocialNet").hide();
				} else {
					$("#aLinkSocialNetAddNew").show();
					$("#resume_socialnet_view_module").hide();
					$("#resume_socialnet_edit_module").hide();
					if (totalSocialNet == 0) {
						$("#closeResumeSocialNet").show();
					}
				}

				clearResumeSocialNetOpt(data);
			}
		}
	});
};

var cancelResumeSocialNet = function() {
	$("#resume_socialnet_edit_module").hide();
	$("#resume_socialnet_button_module").hide();
	$("#resume_socialnet_view_module").show();
	$(".addclass").remove();
	totalSocialNet = $("#hdResumeSocialNetSize").val();
	if (totalSocialNet < 5) {
		$("#btnAddSocialNet").show();
	}
	disableLinks(false);
	if (totalSocialNet == 0) {
		$("#resume_socialnet_view_module").hide();
		$("#aLinkSocialNetAddNew").show();
		$("#closeResumeSocialNet").show();
	} else {
		$("#closeResumeSocialNet").hide();
	}
};

var clearResumeSocialNetOpt = function(data) {
	addInfos = [];
	editInfos = [];
	delIds = [];
	editIds = [];
	index = 0;

	$("#hdSocialNetEditIds").val(data.hdSocialNetEditIds);
	$("#hdResumeSocialNetSize").val(data.resumeSocialNetSize);
	totalSocialNet = $("#hdResumeSocialNetSize").val();
};

var closeResumeSocialNetModule = function() {
	$("#resume_socialnet_view_module").hide();
	$("#aLinkSocialNetAddNew").show();
};

/*
 * resume_socialnet end
 */

/*
 * resume_hobby_special end
 */

/*
 * resume_custom start
 */

var initResumeCustomPage = function() {
	$("#resume_custom_edit_module").hide();
	var count = $("#hdResumeCustomSize").val();
	if (count == 0) {
		$("#resume_custom_view_module").hide();
		$("#aLinkCustomAddNew").show();
	} else {
		$("#aLinkCustomAddNew").hide();
	}

	if (count >= 5) {
		$("#aLinkCustomAddDiv").hide();
	}
};

// 保存自定义板块经历
var saveResumeCustom = function() {
	var customId = $("#hdCustomEditId").val();
	var resumeId = $("#hdResumeId").val();
	$.ajax({
		url : "resume/saveResumeCustom",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			resumeCustomId : customId,
			resumeCustomName : $("#resumeCustomName").val(),
			resumeCustomDesc : $("#resumeCustomDesc").val()
		},
		success : function(data) {
			if (data.success) {
				$("#aLinkCustomAddNew").hide();
				$("#resume_custom_view_module").show();
				if (customId > 0) {
					var newView = $("#hobby_custom_" + customId);
					$(newView).html(data.resumeCustomHtml);
					$(newView).show();
				} else {
					$("#resume_custom_view").append(data.resumeCustomHtml);
				}
				$("#hdResumeCustomSize").val(data.resumeCustomSize);
				if (data.resumeCustomSize >= 5) {
					$("#aLinkCustomAddDiv").hide();
				} else {
					$("#aLinkCustomAddDiv").show();
				}
				disableLinks(false);
				$("#resume_custom_edit_module").hide();
			}
		}
	});
};

// 添加自定义板块经历
var addResumeCustom = function() {
	$("#p_custom_title").removeClass("none");
	$("#aLinkCustomAddDiv").show();
	$("#resume_custom_edit_module").show();
	$("#resume_custom_edit_module").insertAfter($("#resume_custom_view_module").parent());
	$("#resumeCustomName").val("");
	$("#resumeCustomDesc").val("");
	$("#hdCustomEditId").val(0);
	var totalSize = $("#hdResumeCustomSize").val();
	if (totalSize == 0) {
		$("#aLinkCustomAddNew").hide();
	}

	disableLinks(true);
};

// 编辑自定义板块经历
var editResumeCustom = function(id) {
	$("#p_custom_title").addClass("none");
	$("#aLinkCustomAddDiv").hide();
	var hideView = $("#hobby_custom_" + id);
	$(hideView).hide();
	disableLinks(true);
	$("#resume_custom_edit_module").insertAfter($(hideView));
	$("#hdCustomEditId").val(id);

	var resumeCustom = $("#hobby_custom_detail_" + id);

	var resumeCustomName = $(resumeCustom).attr("name");
	var resumeCustomDesc = $(resumeCustom).attr("description");

	$("#resume_custom_edit_module").show();
	$("#resumeCustomName").val(resumeCustomName);
	$("#resumeCustomDesc").val(resumeCustomDesc);

};

// 删除自定义板块经历
var deleteResumeCustom = function(id) {
	if (confirm("确认删除此条目?")) {
		$.ajax({
			url : "resume/deleteResumeCustom",
			type : "post",
			dataType : "json",
			data : {
				resumeCustomId : id,
				resumeId : $("#hdResumeId").val()
			},
			success : function(data) {
				if (data.success) {
					$("#hdResumeCustomSize").val(data.resumeCustomSize);
					$("#hobby_custom_" + id).remove();
					if (data.resumeCustomSize == 0) {
						$("#aLinkCustomAddNew").show();
						$("#resume_custom_view_module").hide();
					} else if (data.resumeCustomSize > 0 && data.resumeCustomSize < 5) {
						$("#aLinkCustomAddDiv").show();
					}
				}
			}
		});
	}
};

// 取消编辑自定义板块经历
var cancelResumeCustom = function() {
	var totalSize = $("#hdResumeCustomSize").val();
	if (totalSize > 0) {
		$("#aLinkCustomAddDiv").show();
		$("#resume_custom_edit_module").hide();
		var editId = $("#hdCustomEditId").val();
		var showView = $("#hobby_custom_" + editId);
		$(showView).show();
	} else {
		$("#aLinkCustomAddNew").show();
		$("#resume_custom_edit_module").hide();
	}
	disableLinks(false);
};

/*
 * resume_custom end
 */

/*
 * resume_specialtyskill start
 */

var initSpecialSkillPage = function() {
	var flag = totalSpecialtySkillEn > 0 || totalSpecialtySkillOther > 0 || totalSpecialtySkillComputer > 0
			|| totalSpecialtySkillCert > 0;

	if (flag) {
		$("#resume_special_all_div").show();
		$("#aLinkSpecialSkillAddNew").hide();
	} else {
		$("#resume_special_all_div").hide();
		$("#aLinkSpecialSkillAddNew").show();
	}

	if (totalSpecialtySkillEn == 1) {
		$(".aLinkSpSkillEnDel").hide();
	}
	if (totalSpecialtySkillOther == 1) {
		$(".aLinkSpSkillOtherDel").hide();
	}
	if (totalSpecialtySkillComputer == 1) {
		$(".aLinkSpSkillComputerDel").hide();
	}
	if (totalSpecialtySkillCert == 1) {
		$(".aLinkSpSkillCertDel").hide();
	}
	initSkillButtons();
};

var initSkillButtons = function() {
	if (totalSpecialtySkillEn == 0) {
		$("#resume_specialtyskill_en_view_default").show();
		$("#resume_specialtyskill_en_view_default_hr").show();
	}
	if (totalSpecialtySkillOther == 0) {
		$("#resume_specialtyskill_other_view_default").show();
		$("#resume_specialtyskill_other_view_default_hr").show();
	}
	if (totalSpecialtySkillComputer == 0) {
		$("#resume_specialtyskill_computer_view_default").show();
		$("#resume_specialtyskill_computer_view_default_hr").show();
	}
	if (totalSpecialtySkillCert == 0) {
		$("#resume_specialtyskill_cert_view_default").show();
	}
};

var showSpecialSkillAllModule = function() {
	$("#resume_special_all_div").show();
	$("#aLinkSpecialSkillAddNew").hide();
	if (totalSpecialtySkillEn == 0) {
		$("#resume_specialtyskill_en_view_default").show();
	}

	if (totalSpecialtySkillOther == 0) {
		$("#resume_specialtyskill_other_view_default").show();
	}

	if (totalSpecialtySkillComputer == 0) {
		$("#resume_specialtyskill_computer_view_default").show();
	}

	if (totalSpecialtySkillCert == 0) {
		$("#resume_specialtyskill_cert_view_default").show();
	}
};

var closeSpecialSkillAllModule = function() {
	$("#resume_special_all_div").hide();
	$("#aLinkSpecialSkillAddNew").show();
};

/*
 * resume_specialtyskill end
 */

/*
 * resume_specialtyskill_en start
 */

var editResumeSpecialtySkillEn = function() {
	$("#resume_specialtyskill_en_view").hide();
	$("#resume_specialtyskill_en_edit_module").show();
	disableLinks(true);
	var resumeSpecialtySkillEnSize = $("#resumeSpecialtySkillEnSize").val();
	if (resumeSpecialtySkillEnSize == 0) {
		$("#resume_specialtyskill_en_view_default").hide();
	}

	if (resumeSpecialtySkillEnSize >= 5) {
		$("#btnAddSpecialtySkillEnDiv").hide();
		$("#btnAddSpecialtySkillEn").hide();
	} else {
		if (totalSpecialtySkillEn == 1) {
			$(".aLinkSpSkillEnDel").hide();
		} else {
			$(".aLinkSpSkillEnDel").show();
		}
		$("#btnAddSpecialtySkillEnDiv").show();
		$("#btnAddSpecialtySkillEn").show();
	}
};

var index = 0;
var addResumeSpecialtySkillEn = function() {
	var html = '<tr type="add_'
			+ index
			+ '" class="spsken"><td class="wd250"><img src="images_zp/graduate.png" alt="毕业年份"><select id="resumeSpecialtySkillEnName" name="resumeSpecialtySkillEnName" class="mn specialtyskill_en_select">'
			+ '<option value="1">大学英语考试四级</option><option value="2">大学英语考试六级</option><option value="3">专业四级</option><option value="4">专业八级</option>'
			+ '<option value="5">托业</option><option value="6">TOEFL</option><option value="7">GRE</option>'
			+ '<option value="8">GMAT</option><option value="9">IELTS</option><option value="10">剑桥商务英语初级</option>'
			+ '<option value="11">剑桥商务英语中级</option><option value="12">剑桥商务英语高级</option><option value="13">剑桥英语入门考试</option>'
			+ '<option value="14">剑桥初级英语考试</option><option value="15">剑桥第一英语证书考试</option><option value="16">全国公共英语等级考试</option>'
			+ '<option value="17">中级口译</option><option value="18">高级口译</option></select></td>'
			+ '<td class="wd235"><img src="images_zp/major.png" alt="专业"><input type="text" id="resumeSpecialtySkillEnScore" name="resumeSpecialtySkillEnScore" placeholder="考试成绩" value="" class="mn specialtyskill_en_txt" /></td>'
			+ '<td class="wd50"><a href="javascript:;" class="aLinkDelete aLinkSpSkillEnDel" onclick="deleteResumeSpecialtySkillEn(\'add_'
			+ index + '\')"><img src="images_zp/close_16.png" width="16" height="16" alt="删除" /></a></td>';
	index++;
	totalSpecialtySkillEn++;
	$("#resume_specialtyskill_en_edit_tb").append(html);
	if (totalSpecialtySkillEn >= 5) {
		$("#btnAddSpecialtySkillEn").hide();
	}
	if (totalSpecialtySkillEn > 1) {
		$(".aLinkSpSkillEnDel").show();
	}
};

// 记录以,分割，没条记录的item用-分割
var saveResumeSpecialtySkillEn = function() {
	var infos = [];
	$("tr[class='spsken']").each(function(index, item) {
		var resumeSpecialtySkillEnName = $(item).find("select[id='resumeSpecialtySkillEnName']").val();
		var resumeSpecialtySkillEnScore = $(item).find("input[id='resumeSpecialtySkillEnScore']").val();
		var info = resumeSpecialtySkillEnName + "-" + resumeSpecialtySkillEnScore;
		infos.push(info);
	});

	var infoArgs = infos.join(",");
	var resumeId = $("#hdResumeId").val();

	$.ajax({
		url : "resume/saveResumeSpecialtySkillEn",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			infoArgs : infoArgs
		},
		success : function(data) {
			if (data.success) {
				if (data.resumeSpecialtySkillEnSize >= 5) {
					$("#btnAddSpecialtySkillEnDiv").hide();
					$("#btnAddSpecialtySkillEn").hide();
					$("#resume_specialtyskill_en_view_default").hide();
				} else {
					if (data.resumeSpecialtySkillEnSize == 0) {
						$("#resume_specialtyskill_en_view_default").show();
					}
					$("#btnAddSpecialtySkillEnDiv").show();
					$("#btnAddSpecialtySkillEn").show();
				}
				$("#resume_specialtyskill_en_edit_module").hide();
				$("#resume_specialtyskill_en_edit_tb").html(data.resumeSpecialtySkillEnEditHtml);
				$("#resume_specialtyskill_en_view").html(data.resumeSpecialtySkillEnViewHtml);
				$("#resume_specialtyskill_en_view").show();

				disableLinks(false);
				clearResumeSpecialtySkillEn(data);
			}
		}
	});
};

var deleteResumeSpecialtySkillEn = function(id) {
	if (confirm("确认删除此条目?")) {
		if (id.indexOf("add_") != -1) {
			$("tr[type='" + id + "']").remove();
		} else {
			delIds.push(id);
			$("#specialtySkillEnRow_" + id).remove();
		}
		totalSpecialtySkillEn--;
	}
	if (totalSpecialtySkillEn > 1 && totalSpecialtySkillEn < 5) {
		$("#btnAddSpecialtySkillEn").show();
		$("#btnAddSpecialtySkillEnDiv").show();
	}

	if (totalSpecialtySkillEn == 1) {
		$(".aLinkSpSkillEnDel").hide();
	}
};

var cancelResumeSpecialtySkillEn = function() {
	$("#resume_specialtyskill_en_view").show();
	$("#resume_specialtyskill_en_edit_module").hide();
	$(".addclass").remove();
	if (totalSpecialtySkillEn < 5) {
		$("#btnAddSpecialtySkillEn").show();
	}

	$("#resume_specialtyskill_en_view_default").show();
	disableLinks(false);
};

var clearResumeSpecialtySkillEn = function(data) {
	index = 0;
	infos = [];
	$("#resumeSpecialtySkillEnSize").val(data.resumeSpecialtySkillEnSize);
	totalSpecialtySkillEn = $("#resumeSpecialtySkillEnSize").val();
};

/*
 * resume_specialtyskill_en end
 */

/*
 * resume_specialtyskill_other start
 */

var editResumeSpecialtySkillOther = function() {
	$("#resume_specialtyskill_other_edit_module").show();
	$("#resume_specialtyskill_other_view").hide();
	disableLinks(true);
	$(".aLinkSpSkillOtherDel").show();
	var resumeSpecialtySkillOtherSize = $("#resumeSpecialtySkillOtherSize").val();
	if (resumeSpecialtySkillOtherSize == 0) {
		$("#resume_specialtyskill_other_view_default").hide();
	}

	if (resumeSpecialtySkillOtherSize >= 5) {
		$("#btnAddSpecialtySkillOtherDiv").hide();
		$("#btnAddSpecialtySkillOther").hide();
	} else {
		if (totalSpecialtySkillOther == 1) {
			$(".aLinkSpSkillOtherDel").hide();
		} else {
			$(".aLinkSpSkillOtherDel").show();
		}
		$("#btnAddSpecialtySkillOtherDiv").show();
		$("#btnAddSpecialtySkillOther").show();
	}
};

var index = 0;
var addResumeSpecialtySkillOther = function() {
	var html = '<tr type="add_'
			+ index
			+ '" class="spskother"><td class="wd250"><img src="images_zp/graduate.png" alt="毕业年份"><select id="resumeSpecialtySkillOtherName" name="resumeSpecialtySkillOtherName" class="mn specialtyskill_other_select">'
			+ '<option value="1">日语</option><option value="2">法语</option><option value="3">德语</option><option value="4">俄语</option>'
			+ '<option value="5">韩语</option><option value="6">西班牙语</option><option value="7">葡萄牙语</option>'
			+ '<option value="8">阿拉伯语</option><option value="9">意大利语</option><option value="10">中文普通话</option>'
			+ '<option value="11">粤语</option><option value="12">上海话</option><option value="13">闽南语</option>'
			+ '<option value="14">朝鲜语</option></select></td>'
			+ '<td class="wd235"><img src="images_zp/major.png" alt="掌握程度"><input type="text" id="resumeSpecialtySkillOtherLevel" name="resumeSpecialtySkillOtherLevel" placeholder="掌握程度" value="" class="mn specialtyskill_other_txt" /></td>'
			+ '<td class="wd50"><a href="javascript:;" class="aLinkDelete aLinkSpSkillOtherDel" onclick="deleteResumeSpecialtySkillOther(\'add_'
			+ index + '\');"><img src="images_zp/close_16.png" width="16" height="16" alt="删除" /></a></td>';
	index++;
	totalSpecialtySkillOther++;
	$("#resume_specialtyskill_other_edit_tb").append(html);

	if (totalSpecialtySkillOther >= 5) {
		$("#btnAddSpecialtySkillOther").hide();
	}

	if (totalSpecialtySkillOther > 1) {
		$(".aLinkSpSkillOtherDel").show();
	}
};

// 记录以,分割，没条记录的item用-分割
var saveResumeSpecialtySkillOther = function() {
	var infos = [];
	$("tr[class='spskother']").each(function(index, item) {
		var resumeSpecialtySkillOtherName = $(item).find("select[id='resumeSpecialtySkillOtherName']").val();
		var resumeSpecialtySkillOtherLevel = $(item).find("input[id='resumeSpecialtySkillOtherLevel']").val();
		var info = resumeSpecialtySkillOtherName + "-" + resumeSpecialtySkillOtherLevel;
		infos.push(info);
	});

	var infoArgs = infos.join(",");
	var resumeId = $("#hdResumeId").val();

	$.ajax({
		url : "resume/saveResumeSpecialtySkillOther",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			infoArgs : infoArgs
		},
		success : function(data) {
			if (data.success) {
				if (data.resumeSpecialtySkillOtherSize >= 5) {
					$("#btnAddSpecialtySkillOtherDiv").hide();
					$("#btnAddSpecialtySkillOther").hide();
					$("#resume_specialtyskill_other_view_default").hide();
				} else {
					if (data.resumeSpecialtySkillOtherSize == 0) {
						$("#resume_specialtyskill_other_view_default").show();
					}
					$("#btnAddSpecialtySkillOtherDiv").show();
					$("#btnAddSpecialtySkillOther").show();
				}
				$("#resume_specialtyskill_other_edit_module").hide();
				$("#resume_specialtyskill_other_edit_tb").html(data.resumeSpecialtySkillOtherEditHtml);
				$("#resume_specialtyskill_other_view").html(data.resumeSpecialtySkillOtherViewHtml);
				$("#resume_specialtyskill_other_view").show();
				disableLinks(false);
				clearResumeSpecialtySkillOther(data);
			}
		}
	});
};

var deleteResumeSpecialtySkillOther = function(id) {
	if (confirm("确认删除此条目?")) {
		if (id.indexOf("add_") != -1) {
			$("tr[type='" + id + "']").remove();
		} else {
			delIds.push(id);
			$("#specialtySkillOtherRow_" + id).remove();

		}
		totalSpecialtySkillOther--;
	}
	if (totalSpecialtySkillOther < 5) {
		$("#btnAddSpecialtySkillOther").show();
		$("#totalSpecialtySkillOther").show();
	}

	if (totalSpecialtySkillOther == 1) {
		$(".aLinkSpSkillOtherDel").hide();
	}
};

var cancelResumeSpecialtySkillOther = function() {
	$("#resume_specialtyskill_other_edit_module").hide();
	$("#resume_specialtyskill_other_view").show();
	$(".addclass").remove();
	if (totalSpecialtySkillOther < 5) {
		$("#btnAddSpecialtySkillOther").show();
	}

	$("#resume_specialtyskill_other_view_default").show();
	disableLinks(false);
};

var clearResumeSpecialtySkillOther = function(data) {
	index = 0;
	infos = [];
	$("#resumeSpecialtySkillOtherSize").val(data.resumeSpecialtySkillOtherSize);
	totalSpecialtySkillOther = $("#resumeSpecialtySkillOtherSize").val();
};

/*
 * resume_specialtyskill_other end
 */

/*
 * resume_specialtyskill_computer start
 */

var editResumeSpecialtySkillComputer = function() {
	$("#resume_specialtyskill_computer_edit_module").show();
	$("#resume_specialtyskill_computer_view").hide();
	$(".aLinkSpSkillComputerDel").show();
	var resumeSpecialtySkillComputerSize = $("#resumeSpecialtySkillComputerSize").val();
	if (resumeSpecialtySkillComputerSize == 0) {
		$("#resume_specialtyskill_computer_view_default").hide();
	}

	if (resumeSpecialtySkillComputerSize >= 5) {
		$("#btnAddSpecialtySkillComputerDiv").hide();
		$("#btnAddSpecialtySkillComputer").hide();
	} else {
		if (totalSpecialtySkillComputer == 1) {
			$(".aLinkSpSkillComputerDel").hide();
		} else {
			$(".aLinkSpSkillComputerDel").show();
		}
		$("#btnAddSpecialtySkillComputerDiv").show();
		$("#btnAddSpecialtySkillComputer").show();
	}
};

var index = 0;
var addResumeSpecialtySkillComputer = function() {
	var html = '<tr type="add_'
			+ index
			+ '" class="spskcomputer"><td class="wd250"><img src="images_zp/graduate.png" alt="计算机技能"><select id="resumeSpecialtySkillComputerSkill" name="resumeSpecialtySkillComputerSkill" class="mn specialtyskill_computer_select">'
			+ $("#computerDropdownHtml").html()
			+ '</select></td>'
			+ '<td class="wd235"><img src="images_zp/major.png" alt="熟练程度说明" /><select id="resumeSpecialtySkillComputerSkillLevel" name="resumeSpecialtySkillComputerSkillLevel" class="mn specialtyskill_computer_select"><option value="1">精通</option><option value="2">熟悉</option>'
			+ '<option value="3">一般</option></select></td><td class="wd50"><a href="javascript:;" class="aLinkDelete aLinkSpSkillComputerDel" onclick="deleteResumeSpecialtySkillComputer(\'add_'
			+ index + '\');"><img src="images_zp/close_16.png" width="16" height="16" alt="删除" /></a></td>';
	index++;
	totalSpecialtySkillComputer++;
	$("#resume_specialtyskill_computer_edit_tb").append(html);
	if (totalSpecialtySkillComputer >= 5) {
		$("#btnAddSpecialtySkillComputer").hide();
		$("#btnAddSpecialtySkillComputerDiv").hide();
	}

	if (totalSpecialtySkillComputer > 1) {
		$(".aLinkSpSkillComputerDel").show();
	}
};

// 记录以,分割，没条记录的item用-分割
var saveResumeSpecialtySkillComputer = function() {
	var infos = [];
	$("#resume_specialtyskill_computer_edit_tb").find("tr[class='spskcomputer']").each(
			function(index, item) {
				var resumeSpecialtySkillComputerSkill = $(item).find("select[id='resumeSpecialtySkillComputerSkill']")
						.val();
				var resumeSpecialtySkillComputerSkillLevel = $(item).find(
						"select[id='resumeSpecialtySkillComputerSkillLevel']").val();
				var info = resumeSpecialtySkillComputerSkill + "-" + resumeSpecialtySkillComputerSkillLevel;
				infos.push(info);
			});

	var infoArgs = infos.join(",");
	var resumeId = $("#hdResumeId").val();

	$.ajax({
		url : "resume/saveResumeSpecialtySkillComputer",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			infoArgs : infoArgs
		},
		success : function(data) {
			if (data.success) {
				if (data.resumeSpecialtySkillComputerSize >= 5) {
					$("#btnAddSpecialtySkillComputerDiv").hide();
					$("#btnAddSpecialtySkillComputer").hide();
					$("#resume_specialtyskill_en_view_default").hide();
				} else {
					if (data.resumeSpecialtySkillComputerSize == 0) {
						$("#resume_specialtyskill_computer_view_default").show();
					}
					$("#btnAddSpecialtySkillComputerDiv").show();
					$("#btnAddSpecialtySkillComputer").show();
				}
				$("#resume_specialtyskill_computer_edit_module").hide();
				$("#resume_specialtyskill_en_edit_tb").html(data.resumeSpecialtySkillComputerEditHtml);
				$("#resume_specialtyskill_computer_view").html(data.resumeSpecialtySkillComputerViewHtml);
				$("#resume_specialtyskill_computer_view").show();
				clearResumeSpecialtySkillComputer(data);
				disableLinks(false);
			}
		}
	});
};

var deleteResumeSpecialtySkillComputer = function(id) {
	if (confirm("确认删除此条目?")) {
		if (id.indexOf("add_") != -1) {
			$("tr[type='" + id + "']").remove();
		} else {
			delIds.push(id);
			$("#specialtySkillComputerRow_" + id).remove();
		}
		totalSpecialtySkillComputer--;
	}
	if (totalSpecialtySkillComputer < 5) {
		$("#btnAddSpecialtySkillComputer").show();
		$("#btnAddSpecialtySkillComputerDiv").show();
	}

	if (totalSpecialtySkillComputer == 1) {
		$(".aLinkSpSkillComputerDel").hide();
	}
};

var cancelResumeSpecialtySkillComputer = function() {
	$("#resume_specialtyskill_computer_edit_module").hide();
	$("#resume_specialtyskill_computer_view").show();
	$(".addclass").remove();
	if (totalSpecialtySkillComputer < 5) {
		$("#btnAddSpecialtySkillComputer").show();
	}

	$("#resume_specialtyskill_computer_view_default").show();
	disableLinks(false);
};

var clearResumeSpecialtySkillComputer = function(data) {
	index = 0;
	infos = [];
	$("#resumeSpecialtySkillComputerSize").val(data.resumeSpecialtySkillComputerSize);
	totalSpecialtySkillComputer = $("#resumeSpecialtySkillComputerSize").val();
};

// 初始化获取computerDropdownHtml
var getComputerSkillDropdownHtml = function() {
	$.ajax({
		url : "resume/getComputerSkillDropdownHtml",
		type : "get",
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$("#computerDropdownHtml").html(data.getComputerSkillDropdownHtml);
				$("#computerDropdownHtml").hide();
			}
		}
	});
};

/*
 * resume_specialtyskill_computer end
 */

/*
 * resume_specialtyskill_cert start
 */

var editResumeSpecialtySkillCert = function() {
	$("#resume_specialtyskill_cert_edit_module").show();
	$("#resume_specialtyskill_cert_view").hide();
	disableLinks(true);
	$(".aLinkSpSkillCertDel").show();
	var resumeSpecialtySkillCertSize = $("#resumeSpecialtySkillCertSize").val();
	if (resumeSpecialtySkillCertSize == 0) {
		$("#resume_specialtyskill_cert_view_default").hide();
	}
	if (resumeSpecialtySkillCertSize >= 5) {
		$("#btnAddSpecialtySkillCertDiv").hide();
		$("#btnAddSpecialtySkillCert").hide();
	} else {
		if (totalSpecialtySkillCert == 1) {
			$(".aLinkSpSkillCertDel").hide();
		} else {
			$(".aLinkSpSkillCertDel").show();
		}
		$("#btnAddSpecialtySkillCertDiv").show();
		$("#btnAddSpecialtySkillCert").show();
	}
};

var index = 0;
var addResumeSpecialtySkillCert = function() {
	var html = '<tr type="add_'
			+ index
			+ '" class="spskcert"><td class="wd250"><img src="images_zp/major.png" alt="证书名称" /><input type="text" id="resumeSpecialtySkillCertName" name="resumeSpecialtySkillCertName" placeholder="证书名称" value="" class="mn specialtyskill_cert_txt" /></td>'
			+ '<td class="wd35"><a href="javascript:;" class="aLinkDelete aLinkSpSkillCertDel" onclick="deleteResumeSpecialtySkillCert(\'add_'
			+ index + '\');"><img src="images_zp/close_16.png" width="16" height="16" alt="删除" /></a></a></td>';
	index++;
	totalSpecialtySkillCert++;
	$("#resume_specialtyskill_computer_cert_tb").append(html);
	if (totalSpecialtySkillCert >= 5) {
		$("#btnAddSpecialtySkillCert").hide();
		$("#btnAddSpecialtySkillCertDiv").hide();
	}
	if (totalSpecialtySkillCert > 1) {
		$(".aLinkSpSkillCertDel").show();
	}
};

// 记录以,分割，没条记录的item用-分割
var saveResumeSpecialtySkillCert = function() {
	var infos = [];
	$("tr[class='spskcert']").each(function(index, item) {
		var resumeSpecialtySkillCertName = $(item).find("input[id='resumeSpecialtySkillCertName']").val();
		var info = resumeSpecialtySkillCertName;
		infos.push(info);
	});

	var infoArgs = infos.join(",");
	var resumeId = $("#hdResumeId").val();

	$.ajax({
		url : "resume/saveResumeSpecialtySkillCert",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			infoArgs : infoArgs
		},
		success : function(data) {
			if (data.success) {
				if (data.resumeSpecialtySkillCertSize >= 5) {
					$("#btnAddSpecialtySkillCertDiv").hide();
					$("#btnAddSpecialtySkillCert").hide();
					$("#resume_specialtyskill_en_view_default").hide();
				} else {
					if (data.resumeSpecialtySkillCertSize == 0) {
						$("#resume_specialtyskill_cert_view_default").show();
					}
					$("#btnAddSpecialtySkillCertDiv").show();
					$("#btnAddSpecialtySkillCert").show();
				}
				$("#resume_specialtyskill_cert_edit_module").hide();
				$("#resume_specialtyskill_computer_cert_tb").html(data.resumeSpecialtySkillCertEditHtml);
				$("#resume_specialtyskill_cert_view").html(data.resumeSpecialtySkillCertViewHtml);
				$("#resume_specialtyskill_cert_view").show();
				clearResumeSpecialtySkillCert(data);
				disableLinks(false);
			}
		}
	});
};

var deleteResumeSpecialtySkillCert = function(id) {

	if (confirm("确认删除此条目?")) {
		if (id.indexOf("add_") != -1) {
			$("tr[type='" + id + "']").remove();
		} else {
			delIds.push(id);
			$("#specialtySkillCertRow_" + id).remove();

		}
		totalSpecialtySkillCert--;
	}
	if (totalSpecialtySkillCert < 5) {
		$("#btnAddSpecialtySkillCert").show();
		$("#btnAddSpecialtySkillCertDiv").show();
	}

	if (totalSpecialtySkillCert == 1) {
		$(".aLinkSpSkillCertDel").hide();
	}
};

var cancelResumeSpecialtySkillCert = function() {
	$("#resume_specialtyskill_cert_edit_module").hide();
	$("#resume_specialtyskill_cert_view").show();
	$(".addclass").remove();
	if (totalSpecialtySkillCert < 5) {
		$("#btnAddSpecialtySkillCert").show();
	}
	$("#resume_specialtyskill_cert_view_default").show();
	disableLinks(false);
};

var clearResumeSpecialtySkillCert = function(data) {
	index = 0;
	infos = [];
	$("#resumeSpecialtySkillCertSize").val(data.resumeSpecialtySkillCertSize);
	totalSpecialtySkillCert = $("#resumeSpecialtySkillCertSize").val();
};

/*
 * resume_specialtyskill_cert end
 */

/*
 * resume_opusinfo start
 */

var initResumeOpusInfoPage = function() {
	$("#resume_opusinfo_edit_module").hide();
	var count = $("#hdResumeOpusInfoSize").val();
	if (count == 0) {
		$("#resume_opusinfo_view_module").hide();
	}
	if (count > 0) {
		$("#aLinkOpusInfoAddNew").hide();
	}
	if (count >= 5) {
		$("#aLinkOpusInfoAddDiv").hide();
	}
};

// jquery.form.js是表单提交异步化
var initOpusFileUploadForm = function(id) {
	var resumeId = $("#hdResumeId").val();
	var opusInfoId = id;

	var options = {
		url : "resume/uploadOpusFile",
		type : "POST",
		data : {
			resumeId : resumeId,
			hdEditId : opusInfoId
		},
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$("#errorMsg").text("");
				var newView = $("#opusinfo_" + opusInfoId);
				$(newView).html(data.resumeOpusInfoHtml);
				$(newView).show();
				$("#resume_opusinfo_edit_module").hide();
				$("#resumeOpusFileUpload").hide();
			} else {
				$("#errorMsg").text(data.message);
				$("#errorMsg").insertAfter($("#opusUploadForm"));
			}
		}
	};
	$(".uploadForm").ajaxForm(options);
};

// 上传作品文件
var uploadOpusFile = function(id) {
	initOpusFileUploadForm(id);
	$(".uploadForm").submit();
};

// 保存作品
var saveResumeOpusInfo = function() {
	var opusInfoId = $("#hdOpusInfoEditId").val();
	var resumeId = $("#hdResumeId").val();
	$.ajax({
		url : "resume/saveResumeOpusInfo",
		type : "post",
		dataType : "json",
		data : {
			resumeId : resumeId,
			resumeOpusInfoId : opusInfoId,
			resumeOpusInfoName : $("#resumeOpusInfoName").val(),
			resumeOpusInfoUrl : $("#resumeOpusInfoUrl").val(),
		},
		success : function(data) {
			if (data.success) {
				$("#hdResumeOpusInfoSize").val(data.resumeOpusInfoSize);
				if (opusInfoId > 0) {
					var newView = $("#opusinfo_" + opusInfoId);
					$(newView).html(data.resumeOpusInfoHtml);
					$(newView).show();
				} else {
					$("#resume_opusinfo_view").append(data.resumeOpusInfoHtml);
					if (data.resumeOpusInfoSize >= 5) {
						$("#aLinkOpusInfoAdd").hide();
						$("#aLinkOpusInfoAddDiv").hide();
					} else if (data.resumeOpusInfoSize > 0 && data.resumeOpusInfoSize < 5) {
						$("#resume_opusinfo_view_module").show();
					}
				}
				disableLinks(false);
				$("#resume_opusinfo_edit_module").hide();
			}
		}
	});
};

// 添加作品
var addResumeOpusInfo = function() {
	$("#p_opusinfo_title").removeClass("none");
	$("#resume_opusinfo_edit_module").show();
	$("#resume_opusinfo_edit_module").insertAfter($("#resume_opusinfo_view_module").parent());
	$("#aLinkOpusInfoAddNew").hide();
	$("#resumeOpusInfoName").val("");
	$("#resumeOpusInfoUrl").val("");
	$("#resumeOpusFile").hide();
	$("#hdOpusInfoEditId").val(0);
	$("#opusUploadLi").hide();
	disableLinks(true);
};

// 编辑作品
var editResumeOpusInfo = function(id) {
	$("#p_opusinfo_title").addClass("none");
	$("#opusUploadTr").show();
	var hideView = $("#opusinfo_" + id);
	$(hideView).hide();
	disableLinks(true);
	$("#resume_opusinfo_edit_module").insertAfter($(hideView));
	$("#hdOpusInfoEditId").val(id);
	$("#opusUploadLi").show();
	var resumeOpusInfoDetail = $("#opusinfo_detail_" + id);
	var resumeOpusInfoName = $(resumeOpusInfoDetail).attr("opusName");
	var resumeOpusInfoUrl = $(resumeOpusInfoDetail).attr("opusUrl");

	$("#resume_opusinfo_edit_module").show();
	$("#resumeOpusInfoName").val(resumeOpusInfoName);
	$("#resumeOpusInfoUrl").val(resumeOpusInfoUrl);

};

// 删除作品
var deleteResumeOpusInfo = function(id) {
	var opusFile = $("#opusinfo_detail_" + id).attr("opusPathFile");
	if (confirm("确认删除此条目?")) {
		$.ajax({
			url : "resume/deleteResumeOpusInfo",
			type : "post",
			dataType : "json",
			data : {
				resumeOpusInfoId : id,
				resumeId : $("#hdResumeId").val(),
				opusFile : opusFile
			},
			success : function(data) {
				if (data.success) {
					$("#hdResumeOpusInfoSize").val(data.resumeOpusInfoSize);
					$("#opusinfo_" + id).remove();
					if (data.resumeOpusInfoSize > 0 && data.resumeOpusInfoSize < 5) {
						$("#aLinkOpusInfoAdd").show();
						$("#aLinkOpusInfoAddDiv").show();
					} else if (data.resumeOpusInfoSize == 0) {
						$("#resume_opusinfo_view_module").hide();
						$("#aLinkOpusInfoAddNew").show();
					}
				}
			}
		});
	}
};

// 取消编辑作品
var cancelResumeOpusInfo = function() {
	$("#resume_opusinfo_edit_module").hide();
	$("#aLinkOpusInfoAddNew").show();
	var editId = $("#hdOpusInfoEditId").val();
	var showView = $("#opusinfo_" + editId);
	$(showView).show();
	disableLinks(false);
};

/*
 * resume_opusinfo end
 */

/**
 * upload resume attachment start
 */
var initUploadResumeInfo = function() {
	$("#upload_resume_dialog").dialog({
		autoOpen : false,
		title : "上传简历附件",
		show : {
			duration : 200
		},
		hide : {
			duration : 200
		},
		position : {
			my : "right-180 top+10% ",
			at : "bottom center",
			of : "#alinkUploadAttachment"
		},
		resizable : false,
		height : 220,
		width : 500,
		modal : true,
		buttons : {
			"上传" : function() {
				uploadResumeAttachment();
			},
			"取消" : function() {
				$("#upload_resume_dialog").dialog("close");
			}
		}
	});

	var hasAttachmentFile = $("#hasAttachmentFile").val();
	if (hasAttachmentFile == "true") {
		$("#resume_upload_attachment_div").show();
	} else {
		$("#resume_upload_attachment_div").hide();
	}
};

var openUploadResumeDialog = function() {
	$("#upload_resume_dialog").dialog("open");
};

var deleteResumeAttachment = function() {
	if (confirm("确认删除当前简历附件")) {
		var resumeId = $("#hdResumeId").val();
		$.ajax({
			url : "resume/deleteResumeAttachment",
			type : "POST",
			data : {
				resumeId : resumeId
			},
			dataType : "json",
			success : function(data) {
				if (data.success) {
					$("#attachment_file_url").text("");
					$("#attachment_file_url").prop("href", "javascript:;");
					$("#resume_upload_attachment_div").hide();
				}
			}
		});
	}
};

// 异步化上传简历附件
var initResumeInfoForm = function() {
	var resumeId = $("#hdResumeId").val();
	var options = {
		url : "resume/uploadResumeAttachment",
		type : "POST",
		data : {
			resumeId : resumeId
		},
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$("#upload_resume_dialog").dialog("close");
				var resume_attachment_div = $("#resume_upload_attachment_div");
				$(resume_attachment_div).focus();
				$(resume_attachment_div).show();
				$("#attachment_file_url").text(data.uploadName);
				$("#attachment_file_url").prop("href", data.attachmentUrl);
			} else {
				alert(data.message);
			}
		}
	};

	$("#upload_resume_form").ajaxForm(options);
};

// 上传简历附件
var uploadResumeAttachment = function() {
	initResumeInfoForm();
	$("#upload_resume_form").submit();
};

/**
 * upload resume attachment end
 */
