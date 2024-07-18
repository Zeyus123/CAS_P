<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:if test="${node.isDisplay eq true}">
	<c:choose>
		<c:when test="${node.isParent eq true}">
			<li class="nav-item submenu">
				<a data-bs-toggle="collapse" href="#mnu${node.menuId}">
					<p class="sub-menu-p"><i class="fa fa-forward" style="font-size: 9px; margin-right: 5px;"></i>${node.menuText}</p>
					<span class="caret"></span>
				</a>
				<div class="collapse" id="mnu${node.menuId}">
					<ul style="overflow: hidden;">
						<c:forEach items="${node.children}" var="menu">
							<c:set var="node" value="${menu}" scope="request" />
							<jsp:include page="node.jsp" />
						</c:forEach>
					</ul>
				</div>
			</li>
		</c:when>
		<c:when test="${node.isParent eq false}">
			<li class="nav-item submenu">
				<a href="${contextPath}${node.menuURL}" onclick="showLoader();">
					<p style="color: #fff;" class="sub-menu-p">
					<i class="fa fa-caret-right" style="margin-right: 5px;"></i>${node.menuText}</p>
				</a>
			</li>
		</c:when>
	</c:choose>
</c:if>

