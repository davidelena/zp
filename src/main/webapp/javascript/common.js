/**
 * 公共js
 */

var isMobile = function(cp) {
	var mobile = cp;
	if (mobile != '' && mobile.length == 11) {
		var reg = /^((\(\d{3}\))|(\d{3}\-))?13\d{9}|14[57]\d{8}|15\d{9}|18\d{9}|176\d{8}|177\d{8}|178\d{8}|1700\d{7}|1705\d{7}|1709\d{7}$/;
		return reg.test(mobile);
	}

	return false;
};

var onlyNum = function() {
	if (!(event.keyCode == 46) && !(event.keyCode == 8) && !(event.keyCode == 37) && !(event.keyCode == 39))
		if (!((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)))
			event.returnValue = false;
};

var checkTextarea = function(val, length) {
	var textareaVal = val;
	if (!(event.keyCode == 46) && !(event.keyCode == 8) && !(event.keyCode == 37) && !(event.keyCode == 39)) {
		if (textareaVal.length >= length) {
			event.returnValue = false;
		}
	}
};
