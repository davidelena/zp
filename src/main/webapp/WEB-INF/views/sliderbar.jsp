<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {
		initControl();
	});

	var initControl = function() {
		$("#sliderbar_open").hide();
		$("#slider_menu").hover(function() {
			$(this).removeClass("menuClosed");
			$(this).addClass("stu_menuOpened");
			$("#sliderbar_close").hide();
			$("#sliderbar_open").show("slow");
		}, function() {
			$(this).removeClass("stu_menuOpened");
			$(this).addClass("menuClosed");
			$("#sliderbar_open").hide();
			$("#sliderbar_close").show("slow");
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
<div id="slider_menu">
	<div id="sliderbar_close" class="menuClosed">
		<ul id="menuClosed">
			<li><a href="javascript:;"><img
					src="images_zp/singlelogo.png" class="logo"></a></li>
			<li><a href="javascript:;"><img src="images_zp/my.png"></a></li>
			<li><a href="javascript:;"><img src="images_zp/chance.png"></a></li>
			<li><a href="javascript:;"><img src="images_zp/mine.png"></a></li>
			<li><a href="javascript:;"><img src="images_zp/set.png"></a></li>
			<li><a href="javascript:;"><img src="images_zp/logout.png"></a></li>
		</ul>
	</div>
	<div id="sliderbar_open" class="stu_menuOpened">
		<ul id="stu_menuOpened">
			<li><a href="javascript:;"><img
					src="images_zp/stuloginlogo.png" style="height: 70px;"></a></li>
			<li><a
				href="resume/my_resume?id=${sessionScope.memberDTO.memberId}"><img
					src="images_zp/my.png">
					<p>我的简历</p></a></li>
			<li><a
				href="resume/search_joblist?id=${sessionScope.memberDTO.memberId}"><img
					src="images_zp/chance.png">
					<p>工作/实习机会</p></a></li>
			<li><a
				href="resume/my_apply?id=${sessionScope.memberDTO.memberId}"><img
					src="images_zp/mine.png">
					<p>我的申请</p></a></li>
			<li><a
				href="resume/account_settings?id=${sessionScope.memberDTO.memberId}"><img
					src="images_zp/set.png">
					<p>账号设置</p></a></li>
			<li><a href="javascript:;" onclick="logout()"><img
					src="images_zp/logout.png">
					<p>退出登录</p></a></li>
		</ul>
	</div>
</div>