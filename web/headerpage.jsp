<%-- 
    Document   : headerpage
    Created on : Jul 2, 2020, 8:40:08 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="Stylesheet/HeaderAndFooter.css"/>
</head>
<body>
    <div class="header">
        <h1>
            Crawl And Analytic Careers
        </h1>
        <div>
            <ul id="header-menu">
                <li>
                    <a href="ForwardController">
                        <h2>
                            Home
                        </h2>
                    </a>
                </li>
                <li>
                    <c:url var="advance" value="ForwardController">
                        <c:param name="btnAction" value="advance"/>
                        <c:param name="type" value="career"/>
                    </c:url>
                    <a href="${advance}">
                        <h2>
                            Advance
                        </h2>
                    </a>
                </li>
                <li>
                    <c:url var="crawl" value="ForwardController">
                        <c:param name="btnAction" value="crawl"/>
                    </c:url>
                    <a href="${crawl}">
                        <h2>
                            Crawl
                        </h2>
                    </a>
                </li>
            </ul>
            <div class="clearfix">
            </div>
        </div>
    </div>
</body>