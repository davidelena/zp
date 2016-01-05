<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		initCpControl();
	});

	var initCpControl = function() {

		$("#sliderbar_cp_open").hide();
		$("#slider_menu_cp").hover(function() {
			$(this).removeClass("menuClosed");
			$(this).addClass("menuOpened");
			$("#sliderbar_cp_close").hide();
			$("#sliderbar_cp_open").show("slow");

		}, function() {
			$(this).removeClass("menuOpened");
			$(this).addClass("menuClosed");
			$("#sliderbar_cp_open").hide();
			$("#sliderbar_cp_close").show("slow");
		});
	};

	var logout = function() {
		$.ajax({
			url : "member/logout",
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					window.location.href = data.url;
				} else {
					alert(data.message);
				}
			}
		});
	};
</script>
<span id="value"></span>
<div id="slider_menu_cp">
	<div id="sliderbar_cp_close" class="menuClosed">
		<ul>
			<li><a href="javascript:;"><img
					src="images_zp/singlelogo.png" class="logo"></a></li>
			<li><a href="javascript:;"><img src="images_zp/my.png"></a></li>
			<li><a href="javascript:;"><img src="images_zp/jianliku.png"></a></li>
			<li><a href="javascript:;"><img
					src="images_zp/shenqingr.png"></a></li>
			<li><a href="javascript:;"><img src="images_zp/mine.png"></a></li>
			<li><a href="javascript:;"><img
					src="images_zp/zhiweiliebiao.png"></a></li>
			<li><a href="javascript:;"><img src="images_zp/logout.png"></a></li>
		</ul>
	</div>

	<div id="sliderbar_cp_open" class="menuOpened">
		<ul>
			<li><a href="javascript:;"><img
					src="images_zp/stuloginlogo.png" style="height: 70px;"></a></li>
			<li><a
				href="recruit/company_view?id=${sessionScope.memberDTO.memberId}"><img
					src="images_zp/my.png">
					<p>我的账号</p></a></li>
			<li><a
				href="recruit/resume_database?id=${sessionScope.memberDTO.memberId}"><img
					src="images_zp/jianliku.png">
					<p>简历库</p></a></li>
			<li><a
				href="recruit/my_applicant?id=${sessionScope.memberDTO.memberId}"><img
					src="images_zp/shenqingr.png">
					<p>我的申请人</p></a></li>
			<li><a
				href="recruit/job_basic_info?id=${sessionScope.memberDTO.memberId}&rid=0"><img
					src="images_zp/mine.png">
					<p>发布新职位</p></a></li>
			<li><a
				href="recruit/my_joblist?id=${sessionScope.memberDTO.memberId}"><img
					src="images_zp/zhiweiliebiao.png">
					<p>我的职位列表</p></a></li>
			<li><a href="javascript:;" onclick="logout()"><img
					src="images_zp/logout.png">
					<p>退出登录</p></a></li>
		</ul>
	</div>
</div>