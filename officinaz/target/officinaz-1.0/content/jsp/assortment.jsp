<?xml version="1.0" encoding="iso-8859-2"?>
<jsp:root 
	xmlns="http://www.w3.org/1999/xhtml" 
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	version="2.0">
	<jsp:directive.page 
		language="java" contentType="text/html; charset=iso-8859-2" 
		pageEncoding="iso-8859-2" session="true"/>
	<jsp:text>
		<![CDATA[<!DOCTYPE html>]]>
	</jsp:text>
	<html>
		<head>
			<link rel="stylesheet" type="text/css" href="../css/style.css"/>
			<meta http-equiv="content-type" content="text/html; charset=iso-8859-2"/>
			<meta http-equiv="content-script-type" content="text/javascript"/>
			<meta http-equiv="content-language" content="en"/>
			<meta http-equiv="cache-control" content="private, no-cache, no-store, must-revalidate, pre-check=0, post-check=0"/>
			<meta http-equiv="expires" content="0"/>
			<meta http-equiv="pragma" content="no-cache"/>
			<meta name="keywords" content="officinaz, online store"/>
			<meta name="description" content="online store"/>
			<meta name="robots" content="all"/>
			<title>Assortment</title>
		</head>
	    <body>
	        <div class="background">
				<div id="logo">
					<h1>                                    
						<span style="background-color: #1b1b1b; border: 2px solid #f6f6f6; padding: 3px;">
							Assortment
						</span>                    
					</h1>				
				</div>
		        <div id="container">
					<div class="menu">
						<dl>
							<dt>Menu</dt>						
							<dd>
								<a href="${pageContext.request.contextPath}/content/jsf/main.xhtml">
									Main Site&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
								</a>
							</dd>						
							<dd>
								<a href="../../check.do">
									Assortment&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
								</a>
							</dd>
							<dd>
								<a href="${pageContext.request.contextPath}/content/jsf/sign-in.xhtml">
									Sign In&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
								</a>
							</dd>
							<dd>
								<a href="${pageContext.request.contextPath}/content/jsf/sign-up.xhtml">
									Sign Up&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
								</a>
							</dd>
							<c:choose>
								<c:when test="${fn:substring(sessionScope.clientLogin, fn:indexOf(sessionScope.clientLogin, '/') + 1, fn:length(clientLogin)) eq 'client'}">
									<dd>
										<a href="${pageContext.request.contextPath}/content/jsf/suggestion.xhtml">
											Suggestion&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
										</a>
									</dd>
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${sessionScope.disable eq 'true'}">
									<dd>
										<a href="${pageContext.request.contextPath}/content/jsf/questionnaire.xhtml">
											Questionnaire&#160;&#160;&#160;&#160;&#160;
										</a>
									</dd>
								</c:when>
							</c:choose>							
							<c:choose>
								<c:when test="${fn:substring(sessionScope.clientLogin, fn:indexOf(sessionScope.clientLogin, '/') + 1, fn:length(clientLogin)) eq 'client'}">			
									<dd>
										<a href="${pageContext.request.contextPath}/content/jsp/purchases.jsp">
											Cart&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
										</a>
									</dd>
								</c:when>
							</c:choose>
						</dl>
					</div>
					<div class="content">
						<div class="title">
							<span style="font-weight: bold;">
								<c:out value="${fn:substring(sessionScope.clientLogin, 0, fn:indexOf(sessionScope.clientLogin, '/'))}"/>
							</span>, we have in stock:
						</div>
						<div class="text">
							<form method="post" action="../../cart.do">
								<table style="border: 2px solid #eee;">					
									<c:set var="index" scope="page" value="0"/>
									<c:forEach var="element" items="${sessionScope.assortment}" varStatus ="status">
										<tr>
											<td>
												<c:out value="${pageScope.index + 1}."/>
											</td>
											<td>
												<c:out value="${pageScope.element.value.name}"/>
											</td>                               
											<td>unit price:</td>
											<td style="text-align: right;">
												<c:out value="${pageScope.element.value.price}"/>
											</td>
											<td style="text-align: right;">PLN</td>
											<td style="text-align: right;">
												<c:out value="${pageScope.element.value.quantity}"/>
											</td>
											<td>unit(s)</td>
											<td>
												<input type="number" size="2" name="obj${pageScope.element.key}/purchased" value="0"/>
											</td>
										</tr>
										<tr>
											<td>
												<input type="hidden" name="obj${pageScope.element.key}/map" value="${pageScope.element.key}"/>
											</td>
											<td>
												<input type="hidden" name="obj${pageScope.element.key}/name" value="${pageScope.element.value.name}"/>
											</td>
											<td>
												<input type="hidden" name="obj${pageScope.element.key}/id" value="${pageScope.element.value.id}"/>
											</td>
											<td>
												<input type="hidden" name="obj${pageScope.element.key}/price" value="${pageScope.element.value.price}"/>
											</td>
											<td>
												<input type="hidden" name="obj${pageScope.element.key}/quantity" value="${pageScope.element.value.quantity}"/>
											</td>
										</tr>
										<c:set var="index" value="${pageScope.index + 1}" scope="page"/>
									</c:forEach>
									<tr>
										<td>								 
											<input type="hidden" name="counted" value="${sessionScope.counted}"/>
										</td>
									</tr>
									<tr>
										<td colspan="7"/>
										<td style="text-align: center; margin: 0 auto;">
											<input type="submit" value="Buy"/>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
				<div class="date">Added on: 10.05.2011</div>
				<div id="footer">
					<div id="copyright">
						Copyright by Officinaz
					</div>
				</div>
			</div>
		</body>
	</html>
</jsp:root>