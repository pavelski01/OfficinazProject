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
	<jsp:directive.page import="capital.officinaz.dto.Purchase"/>
	<jsp:directive.page import="java.util.ArrayList"/>
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
			<style type="text/css">p { text-align: justify; }</style>
			<title>Purchases</title>
		</head>
	    <body>
	        <div class="background">
				<div id="logo">
					<h1>                                    
						<span style="background-color: #1b1b1b; border: 2px solid #f6f6f6; padding: 3px;">
							Purchases
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
							<jsp:declaration>
								String clientLogin = null; 
								Boolean disable = null;
							</jsp:declaration>
							<jsp:scriptlet>
								clientLogin = (String)session.getAttribute("clientLogin");
								if (clientLogin.substring(clientLogin.indexOf("/") + 1, clientLogin.length()).equals("client"))
								{
							</jsp:scriptlet>
							<dd>
								<a href="${pageContext.request.contextPath}/content/jsf/suggestion.xhtml">
									Suggestion&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
								</a>
							</dd>
							<jsp:scriptlet>
								}
							</jsp:scriptlet>						
							<jsp:scriptlet>
								disable = (Boolean)session.getAttribute("disable");
								if (disable == true)
								{
							</jsp:scriptlet>
							<dd>
								<a href="${pageContext.request.contextPath}/content/jsf/questionnaire.xhtml">
									Questionnaire&#160;&#160;&#160;&#160;&#160;
								</a>
							</dd>
							<jsp:scriptlet>
								}
								disable = null;
							</jsp:scriptlet>
							<jsp:scriptlet>
								if (clientLogin.substring(clientLogin.indexOf("/") + 1, clientLogin.length()).equals("client"))
								{
							</jsp:scriptlet>
							<dd>
								<a href="${pageContext.request.contextPath}/content/jsp/purchases.jsp">
									Cart&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
								</a>
							</dd>
							<jsp:scriptlet>
								}
								clientLogin = null;
							</jsp:scriptlet>							
						</dl>
					</div>
					<div class="content">
						<div class="title">
							<span style="font-weight: bold;">
								<c:out value="${fn:substring(sessionScope.clientLogin, 0, fn:indexOf(sessionScope.clientLogin, '/'))}"/>
							</span>, you bought:
						</div>
						<div class="text">                                         
							<table style="border: 2px solid #eee;">
								<c:set var="index" scope="page" value="0"/>
						 		<jsp:declaration>int counter = 0, sum = 0;</jsp:declaration>
						 		<jsp:scriptlet>
						 			<![CDATA[
						 				@SuppressWarnings("unchecked") ArrayList<capital.officinaz.dto.Purchase> purchases = 
						 					(ArrayList<capital.officinaz.dto.Purchase>)request.getSession().getAttribute("shopping");
						 				pageContext.setAttribute("purchases", purchases);
					 				]]>
				 				</jsp:scriptlet>		
								<c:forEach var="purchase" items="${pageScope.purchases}" varStatus ="status">
									<tr>
					                    <td>${pageScope.index + 1}.</td>
					                    <td>${pageScope.purchase.name}</td>
					                    <td>&#160;</td>
					                    <td>amount:</td>
					                    <td style="text-align: right;">${pageScope.purchase.quantity}</td>
					                    <td>piece(s)</td>
					                    <td>&#160;</td>
					                    <td>payment:</td>
					                    <td style="text-align: right;">${pageScope.purchase.payment}</td>
					                    <td>PLN</td>					                    
					                </tr>
					                <jsp:scriptlet>
					                	sum += ((capital.officinaz.dto.Purchase)purchases.get(counter)).getPayment();
										counter++;
									</jsp:scriptlet>
				                    <c:set var="index" value="${pageScope.index + 1}" scope="page"/>
            					</c:forEach>						 		
	            				<tr>
	            					<td colspan="8" style="text-align: right;">FOR PAYMENT:</td>
	           						<td style="text-align: right;">
	           							<jsp:expression>sum</jsp:expression>
           							</td>
									<td>PLN</td>
								</tr>
								<jsp:scriptlet>counter = sum = 0;</jsp:scriptlet>
							</table>						
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