<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<script src="javascript/jquery.1.10.1.min.js"></script>
<script src="javascript/jquery.validate.js"></script>
<script src="javascript/jquery.form.js"></script>
<script src="javascript/common.js"></script>
<script src="javascript/jcrop/jquery.Jcrop.js"></script>
<script src="javascript/jquery.panelslider.js"></script>
<script src="javascript/jquery-ui/js/jquery-ui.js"></script>
<script src="javascript/jquery-ui/js/jquery.ui.datepicker-zh-TW.js"></script>
<!-- firstportal style css -->
 <link href="css/dophin/stu_style.css" rel="stylesheet" type="text/css" />
<link href="css/dophin/co_style.css" rel="stylesheet" type="text/css" />
 <link href="css/dophin/other_style.css" rel="stylesheet" type="text/css" /> 
<link rel="stylesheet" href="css/dophin/custom.css">
<link rel="stylesheet"
	href="javascript/jquery-ui/css/jquery-ui-1.10.4.custom.css">
<link rel="stylesheet" href="javascript/jcrop/jquery.Jcrop.css">
<link rel="shortcut icon" href="http://www.zhancampus.com/favicon.ico"
	type="image/x-icon" />
<link rel="icon" href="favicon.ico" type="image/x-icon">