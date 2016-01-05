<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="dialog_industry" class="none">
	<div style="display: block;">
		<table id="industryTable" class="tableTd" style="width: 100%;"
			border="1" cellspacing="1" cellpadding="0">

			<c:forEach items="${industryMap}" var="m">
				<tr>
					<td class="tableTd" class="ml6""><span class="ml6">${m.key}</span></td>
					<td class="tableTd">
						<div class="ml6 block">
							<table>
								<c:forEach items="${m.value}" var="industry" varStatus="item">
									<c:if test="${item.index%3==0}">
										<tr>
									</c:if>
									<td><input type="checkbox" name="chkIndustry"
										onclick="checkIndustry();" value="${industry.id}"
										desc="${industry.name}"
										style="margin-left: 6px; cursor: pointer;" />${industry.name}</td>
									<c:if test="${(item.index+1)%3==0}">
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

<div id="dialog_city" class="none">
	<div style="display: block;">
		<table id="hotCityTable" class="tableTd" style="width: 100%;"
			border="1" cellspacing="1" cellpadding="0">
			<tr>
				<td colspan="4"><span class="ml6">热门城市</span></td>
			</tr>
			<c:forEach items="${hotCities}" var="city" varStatus="item">
				<c:if test="${item.index%4==0}">
					<tr>
				</c:if>
				<td><input type="checkbox" name="chkCity"
					onclick="checkCity();" value="${city.id}" desc="${city.name}"
					style="margin-left: 6px; cursor: pointer;" />${city.name}</td>
				<c:if test="${(item.index+1)%4==0}">
					</tr>
				</c:if>
			</c:forEach>
		</table>

		<table id="nonCityTable" class="tableTd"
			style="width: 100%; margin-top: 5px;" border="1" cellspacing="1"
			cellpadding="0">
			<tr>
				<td colspan="4"><span class="ml6">内地省份</span></td>
			</tr>
			<c:forEach items="${cityMap}" var="m">
				<tr>
					<td class="tableTd" class="ml6"><span class="ml6">${m.key}</span></td>
					<td class="tableTd">
						<div class="ml6 block">
							<table>
								<c:forEach items="${m.value}" var="city" varStatus="item">
									<c:if test="${item.index%8==0}">
										<tr>
									</c:if>
									<td><input type="checkbox" name="chkCity"
										onclick="checkCity();" value="${city.id}" desc="${city.name}"
										style="margin-left: 6px; cursor: pointer;" />${city.name}</td>
									<c:if test="${(item.index+1)%8==0}">
										</tr>
									</c:if>
								</c:forEach>
							</table>
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>

		<table id="otherCityTable" class="tableTd"
			style="width: 100%; margin-top: 5px;" border="1" cellspacing="1"
			cellpadding="0">
			<tr>
				<td colspan="4"><span class="ml6">其他</span></td>
			</tr>
			<c:forEach items="${otherCities}" var="city" varStatus="item">
				<c:if test="${item.index%4==0}">
					<tr>
				</c:if>
				<td><input type="checkbox" name="chkCity"
					onclick="checkCity();" value="${city.id}" desc="${city.name}"
					style="margin-left: 6px; cursor: pointer;" />${city.name}</td>
				<c:if test="${(item.index+1)%4==0}">
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</div>
</div>