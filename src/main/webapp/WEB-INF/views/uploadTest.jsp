<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include_zp.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload测试页面</title>
</head>
<body>
	<script type="text/javascript">
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
	</script>

	<input id="logoMemberId" name="logoMemberId" type="hidden"
		value="1446052088148" />
	<input id="logoCompanyId" name="logoCompanyId" type="hidden" value="9" />
	<!-- 切记异步提交上传表单内不得含有费file文件的字段，否则会有400错误 -->
	<form id="uploadCompanyLogo" method="post"
		enctype="multipart/form-data">
		<input id="logoFile" type="file" name="file"
			style="margin-left: 10px; padding-bottom: 3px;" /><br /> <input
			type="button" name="name" value="上传Logo" onclick="uploadLogo()"
			style="margin-left: 75px;" />
	</form>

	<div id="send_resume_dialog">
		<table>
			<tr>
				<td colspan="4"></td>
			</tr>
			<tr>
				<td colspan="4"></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="4">
						<a href="javascript:void(0)" class="uploadbtn">确认投递简历</a>
				</td>
			</tr>
		</table>

<!-- 		<h5>请选择简历</h5>
		<ul>
			<li><select select name="select">
					<option value="选择简历">选择简历</option>
					<option value="简历">简历</option>
					<option value="简历">简历</option>
			</select></li>
			<li class="row"><select select name="select">
					<option value="中文">中文</option>
					<option value="英文">英文</option>
			</select></li>
		</ul>
		<div>
			<a href="#">预览</a> | <a href="#">修改</a>
		</div> -->
	</div>
</body>
</html>