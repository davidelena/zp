// 初始化行业dialog
var initIndustryDialog = function() {
	$("#dialog_industry").dialog({
		autoOpen : false,
		title : "请选择行业",
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
	$("#hdIndustry").val("");
};

// 保存行业分类
var saveIndustryData = function() {
	var valueArr = [];
	var valueIdArr = [];
	$("input[name='chkIndustry']:checked").each(function() {
		valueArr.push($(this).attr("desc"));
		valueIdArr.push($(this).val());
	});
	$("#hdIndustry").val(valueIdArr.join(","));
	if (valueArr.length == 0) {
		showIndustryChecked(true);
	} else {
		showIndustryChecked(false);
	}
};

var showIndustryDialog = function() {
	$("#dialog_industry").dialog("open");
	var idStr = $("#hdIndustry").val();
	var ids = idStr.indexOf(',') != -1 ? idStr.split(',') : [ idStr ];
	$("input[name='chkIndustry']").each(function(index, item) {
		if ($.inArray($(item).val(), ids) != -1) {
			$(item).attr("checked", true);
		}
	});
	initIndustryCheckbox();
	checkIndustry();
};

var initIndustryCheckbox = function() {
	var hdIndustry = $("#hdIndustry").val();
	var industryArr = hdIndustry.indexOf(',') != -1 ? hdIndustry.split(',') : [ hdIndustry ];
	var chkval = "";
	$("input[name='chkIndustry']").each(function(idx, item) {
		chkval = $(item).val();
		if ($.inArray(chkval, industryArr) != -1) {
			$(item).prop("checked", true);
		}
	});
};

// 检验行业选项框
var checkIndustry = function() {
	var count = $("input[name='chkIndustry']:checked").length;
	if (count >= 3) {
		$("input[name='chkIndustry']:unchecked").each(function() {
			$(this).attr("disabled", true);
		});
	} else {
		$("input[name='chkIndustry']:unchecked").each(function() {
			$(this).attr("disabled", false);
		});
	}
};

// 重置行业选项框
var resetIndustryCheckbox = function() {
	$("input[name='chkIndustry']:checked").each(function() {
		$(this).attr("checked", false);
	});
	checkIndustry();
};

var showIndustryChecked = function(isDefault) {
	if (isDefault) {
		$("#filter_industry").html("");
		$("#filter_industry").append("<option value='-1'>---请选择---</option>");
	} else {
		var valueArr = [];
		$("input[name='chkIndustry']:checked").each(function() {
			valueArr.push($(this).attr("desc"));
		});
		var opts = valueArr.join(",");
		$("#filter_industry").html("");
		$("#filter_industry").append("<option value='-1'>" + opts + "</option>");
	}
};

// 初始化城市
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
	$("#hdCity").val("");
};

var showCityDialog = function() {
	$("#dialog_city").dialog("open");
	var idStr = $("#hdCity").val();
	var ids = idStr.indexOf(',') != -1 ? idStr.split(',') : [ idStr ];
	$("input[name='chkCity']").each(function(index, item) {
		if ($.inArray($(item).val(), ids) != -1) {
			$(item).attr("checked", true);
		}
	});
	initCityCheckbox();
	checkCity();
};

var initCityCheckbox = function() {
	var hdCity = $("#hdCity").val();
	var cityArr = hdCity.indexOf(',') != -1 ? hdCity.split(',') : [ hdCity ];
	var chkval = "";
	$("input[name='chkCity']").each(function(idx, item) {
		chkval = $(item).val();
		if ($.inArray(chkval, cityArr) != -1) {
			$(item).prop("checked", true);
		}
	});
};

// 过滤城市
var saveCityData = function() {
	var valueArr = [];
	var valueIdArr = [];
	$("input[name='chkCity']:checked").each(function() {
		valueArr.push($(this).attr("desc"));
		valueIdArr.push($(this).val());
	});
	$("#hdCity").val(valueIdArr.join(","));
	if (valueArr.length == 0) {
		showCityChecked(true);
	} else {
		showCityChecked(false);
	}
};

// 检验城市选项
var checkCity = function() {
	var count = $("input[name='chkCity']:checked").length;
	if (count >= 3) {
		$("input[name='chkCity']:unchecked").each(function() {
			$(this).attr("disabled", true);
		});
	} else {
		$("input[name='chkCity']:unchecked").each(function() {
			$(this).attr("disabled", false);
		});
	}
};

// 重置城市选项框
var resetCityCheckbox = function() {
	$("input[name='chkCity']:checked").each(function() {
		$(this).attr("checked", false);
	});
	checkCity();
};

var showCityChecked = function(isDefault) {
	if (isDefault) {
		$("#filter_geoarea").html("");
		$("#filter_geoarea").append("<option value='-1'>---请选择---</option>");
	} else {
		var valueArr = [];
		$("input[name='chkCity']:checked").each(function() {
			valueArr.push($(this).attr("desc"));
		});
		var opts = valueArr.join(",");
		$("#filter_geoarea").html("");
		$("#filter_geoarea").append("<option value='-1'>" + opts + "</option>");
	}
};

// 大学选项框
var initUniversityDialog = function() {
	$("#dialog_school").dialog({
		autoOpen : false,
		title : "请选择您的学校",
		show : {
			duration : 200
		},
		hide : {
			duration : 200
		},
		resizable : false,
		height : 400,
		width : 940,
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

var showUniversityChecked = function(isDefault) {
	if (isDefault) {
		$("#filterUniversity").html("");
		$("#filterUniversity").append("<option value='-1'>---请选择---</option>");
	} else {
		var valueArr = [];
		$("input[name='chkUniversity']:checked").each(function() {
			valueArr.push($(this).attr("desc"));
		});
		var opts = valueArr.join(",");
		$("#filterUniversity").html("");
		$("#filterUniversity").append("<option value='-1'>" + opts + "</option>");
	}

};

var saveUniversityData = function() {
	var checkedItem = $("input[name='chkUniversity']:checked");
	$("#memberSchool").val($(checkedItem).attr("desc"));
	$("#memberSchoolId").val($(checkedItem).val());
	var isSearch = $("#hdIsSearch").val();
	if (isSearch != "0") {
		if (checkedItem == undefined || checkedItem == "") {
			showUniversityChecked(true);
		} else {
			showUniversityChecked(false);
		}
	}
};

var showUniversityDialog = function() {
	$("#dialog_school").dialog("open");
	var id = $("#memberSchoolId").val();
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