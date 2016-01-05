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
<title>公司其他信息</title>
</head>
<body>
	<script type="text/javascript">
		var toStep1 = function() {
			window.location.href = "recruit/company_info_step1?id=" + $("#wechatMemberId").val();
		};

		//save company step2 info
		var saveCompanyBasicInfoStep2 = function() {
			$.ajax({
				url : "recruit/saveCompanyBasicInfoStep2",
				type : "post",
				dataType : "json",
				data : {
					memberId : $("#wechatMemberId").val(),
					companyId : $("#wechatCompanyId").val(),
					companyProduct : $("#companyProduct").val(),
					companyAchievements : $("#companyAchievements").val(),
					companyWeibo : $("#companyWeibo").val(),
					companySeniorExecutiveDesc : $("#companySeniorExecutiveDesc").val()
				},
				success : function(data) {
					if (data.success) {
						window.location.href = "recruit/company_member_info?id=" + $("#wechatMemberId").val();
					}
				}
			});
		};

		var initWechatUploadForm = function() {
			var wechatMemberId = $("#wechatMemberId").val();
			var wechatCompanyId = $("#wechatCompanyId").val();
			var options = {
				url : "recruit/uploadCompanyWechat",
				type : "POST",
				data : {
					wechatMemberId : wechatMemberId,
					wechatCompanyId : wechatCompanyId,
				},
				dataType : "json",
				success : function(data) {
					if (data.success) {
						$("#companyWechatImg").attr("src", data.imgUrl);
					}
				}
			};
			$("#uploadCompanyWechat").ajaxForm(options);
		};

		// 上传作品文件
		var uploadWechat = function() {
			initWechatUploadForm();
			$("#uploadCompanyWechat").submit();
		};

		$(function() {
			var navi = navigator.userAgent.toLowerCase();
			if (navi.indexOf("chrome") != -1) {
				$("#filterDiv").css("height", "490px");
			} else {
				$("#filterDiv").css("height", "550px");
			}
		});
	</script>
	<%@include file="/WEB-INF/views/sliderbar_cp.jsp"%>
	<div class="coLGINSUC_welcome">
		<div class="fix_sliderbar">
			<ul>
				<li><h1>
						欢迎来到第一站！
						<h1></li>
				<li><h5>恭喜您注册成功！请完成其他信息</h5></li>
			</ul>
		</div>
	</div>
	<!--主体-->
	<div class="coLGINSUC_content">
		<input id="wechatMemberId" name="wechatMemberId" type="hidden"
			value="${memberId}" /> <input id="wechatCompanyId"
			name="wechatCompanyId" type="hidden" value="${companyInfo.id}" />
		<form id="uploadCompanyWechat" name="uploadCompanyWechat"
			method="post" enctype="multipart/form-data">
			<c:if test="${!isFirst}">
				<div id="filterDiv"
					style="position: absolute; width: 700px; height: 490px; margin: 0 auto; background-color: rgba(105, 105, 105, 0.3); filter: alpha(opacity = 50) ； z-index:9;">
				</div>
			</c:if>
			<fieldset>
				<legend>第二步：公司其他信息（必填）</legend>
				<table>
					<tr>
						<td>知名产品：</td>
						<td><input type="text" id="companyProduct"
							name="companyProduct" value="${companyInfo.product}"
							class="coLGINSUC_mn"
							placeholder="互联网产品如网站/app，实物产品如衣服/食品等均可。30字内。" /></td>
					</tr>
					<tr>
						<td>突出成就：</td>
						<td><input type="text" id="companyAchievements"
							name="companyAchievements" value="${companyInfo.achievements}"
							class="coLGINSUC_mn" placeholder="贵司在所在行业突出的成就，如电冰箱销量全国第一。50字内。" /></td>
					</tr>

					<tr>
						<td>官方微信：</td>
						<td><span class="coLGINSUC_pic"> <img
								id="companyWechatImg" src="${companyInfo.wechat}" width="100"
								height="100" alt="公司wechat图片" />
						</span><input type="file" name="file" /> <br /> <input type="button"
							onclick="uploadWechat()" name="name" value="上传二维码" /></td>
					</tr>
					<tr>
						<td>官方微博：</td>
						<td><input type="text" id="companyWeibo" name="companyWeibo"
							value="${companyInfo.weibo}" class="coLGINSUC_mn"
							placeholder="微博链接，如http://weibo.com/u/3720875" /></td>
					</tr>
					<tr>
						<td><span>高管简介：</span></td>
						<td><textarea id="companySeniorExecutiveDesc"
								style="resize: none;" name="companySeniorExecutiveDesc"
								rows="10" cols="30"
								placeholder="您可以简要介绍公司主要管理人员姓名、现任职务、过去的工作经历、突出成就、新浪微博等。创业公司可着重介绍（联合）创始人。500字以内。">${companyInfo.seniorExecutiveDesc}</textarea></td>
					</tr>
				</table>
			</fieldset>
			<div class="coLGINSUC2_bottom">
				<input type="button" value="上一步" onclick="toStep1()"
					class="coLGINSUC_save" /> <input type="button"
					id="btnFinishToStep2" value="下一步：继续完成公司信息"
					onclick="saveCompanyBasicInfoStep2()" class="coLGINSUC_save" />
			</div>
		</form>
	</div>
	<!--底部-->
	<div class="coLGINSUC_footer">
		第一站的微信号是：zhancampus，欢迎关注！<br />有问题？您可以电话联系我们：021-21458392；或致信邮箱us@zhancampus.com
	</div>
</body>
</html>