<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<sec:authentication var="principal" property="principal" />

<style>
	.fa-signout-inmenu:before {
		margin-left: 2px !important;
	}
</style>


<section class="sidebar_conatiner">
	<div class="sidebar">
		<h5>
			<span>Navigation Menu</span>
			<button type="button" id="btn_navToggle"><i class="fa-solid fa-angles-left"></i></button>
		</h5>

		<ul class="nav-links">
			<li><a href="${contextPath}/home"><i class="fa fa-angle-right"></i><span class="link_name">Dashboard</span></a></li>
			<sec:authorize access="isAuthenticated()">
				<c:forEach items="${sessionScope.USER_MENUS}" var="node">
					<c:if test="${node.isDisplay eq true}">
						<li class="sub-menu" id="menuDiv">
							<c:choose>
								<c:when test="${node.isParent eq true}">
									<div class="iocn-link">
										<a href="#mnu${node.menuId}">
											<i class="fa fa-angle-right"></i>
											<span class="link_name">${node.menuText}</span>
										</a>
										<i class="fa fa-caret-down right arrow"></i>
									</div>
									<ul class="sub-menu">
										<c:forEach items="${node.children}" var="menu">
											<c:set var="node" value="${menu}" scope="request" />
											<jsp:include page="node.jsp" />
										</c:forEach>
									</ul>
								</c:when>
								<c:when test="${node.isParent eq false}">
									<a href="${contextPath}${node.menuURL}"><i class="${node.menuIcon}"></i>
										<p style="color: #fff;" class="sub-menu-p">
											<i class="fa fa-navicon" style="font-size: 10px; margin-right: 5px;"></i>${node.menuText}
										</p>
									</a>
								</c:when>
							</c:choose>
						</li>
					</c:if>
				</c:forEach>
			</sec:authorize>
		</ul>
	</div>
</section>
        

