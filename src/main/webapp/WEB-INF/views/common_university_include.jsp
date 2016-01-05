<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="dialog_school" class="none">
	<div id="school_geo" style="display: block;">
		<table id="schoolTable" class="tableTd" style="width: 100%;"
			border="1" cellspacing="1" cellpadding="0">
			<c:forEach items="${geoAreaList}" var="geoArea" varStatus="item">
				<c:if test="${item.index%4==0}">
					<tr>
				</c:if>
				<td><input type="radio" name="chkUniversityGeo"
					value="${geoArea.id}" desc="${geoArea.name}"
					onclick="showSchoolDetailDiv();"
					style="margin-left: 6px; cursor: pointer;" />${geoArea.name}</td>
				<c:if test="${(item.index+1)%4==0}">
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</div>
	<div id="school_detail"></div>
</div>