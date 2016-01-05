<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include_zp.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="keywords" content="校园招聘，校招，宣讲会，招聘会，大学生，优质招聘，优质信息，实习，第一站，精准推荐" />
<meta name="description" content="第一站zhancampus.com为大学生推荐最新、最优质的校招和实习信息，帮助大学生迈出职场第一步！" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="wclassth=device-wclassth, initial-scale=1.0">
<title>我的账号 账号绑定</title>
<style type="text/css">
body,html {
	height: 100%;
}

.acc_settings_curLogin {
	font-weight: bold;
	color: #1C79C9;
	padding-left: 12px;
}

.acc_settings_afterbind {
	font-weight: bold;
	color: #1C79C9;
	padding-left: 12px;
}

.qq_binding {
	font-weight: bold;
	color: #1C79C9;
	padding-left: 12px;
	width: 100px;
	padding-top: 15px;
}

.wechat_binding {
	font-weight: bold;
	color: #1C79C9;
	padding-left: 12px;
	width: 100px;
	padding-top: 15px;
}
</style>
</head>
<body>
	<%@include file="/WEB-INF/views/sliderbar.jsp"%>
	<div class="stuMS_topMenu">
		<div class="stuMS_safetybtn" style="background-color: #6AC1FF;">
			<a href="resume/account_settings?id=${memberDTO.memberId}">账号安全</a>
		</div>
		<div class="stuMS_tidebtn" style="background-color: #ffffff;">
			<a href="javascript:;">账号绑定</a>
		</div>
	</div>
	<input type="hidden" id="hdMemberId" value="${memberDTO.memberId}" />
	<div class="stuMS_blank"></div>
	<div class="stuMS_center">
		<!--内容-->
		<div class="stuMS_content">
			<!--当前-->
			<div class="stuMS_rightnow">
				<table>
					<tr>
						<td class="acc_settings_curLogin">当前登录账号</td>
						<td>${account}</td>
					</tr>
					<tr>
						<td colspan="2" class="acc_settings_afterbind">绑定后，您可以同时使用以下方式登录第一站</td>
					</tr>
				</table>
			</div>
			<!--QQ-->
			<div class="stuMS_QQ">
				<table>
					<tr>
						<td class="qq_binding"><img src="images_zp/QQ.png" width="20" height="20" alt="" />&nbsp;&nbsp;QQ账号</td>
						<td class="stuMS_2ndrow" style="color: #A5A5A5; font-weight: bold; text-align: center;">未绑定</td>
						<td class="stuMS_4throw"><a href="javascript:;" style="display: inline; margin-right: 20px;">绑定</a></td>
					</tr>
				</table>
			</div>
			<!--wechat-->
			<div class="stuMS_wechat">
				<table>
					<tr>
						<td class="wechat_binding"><img src="images_zp/wechat.png" width="20" height="20" alt="" />&nbsp;&nbsp;微信账号</td>
						<td class="stuMS_2ndrow" style="color: #A5A5A5; font-weight: bold; text-align: center;">未绑定</td>
						<td class="stuMS_4throw"><a href="javascript:;" style="display: inline; margin-right: 20px;">绑定</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>