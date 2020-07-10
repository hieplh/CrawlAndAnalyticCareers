<%-- 
    Document   : advance
    Created on : Jul 1, 2020, 2:39:30 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Advance Page</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
        <script>
            function invokeMainServlet(value) {
                window.location = "ForwardController?btnAction=advance"
                        + "&type=" + value;
            }

            function callTextOrChart(type, display, page, pages) {
                urlReWriting = "advance.jsp"
                        + "?type=" + type
                        + "&display=" + display
                        + "&page=" + page
                        + "&pages=" + pages;
                window.location = urlReWriting;
            }
        </script>
    </head>
    <body>
        <jsp:include page="headerpage.jsp"/> <br/>

        <c:choose>
            <c:when test="${requestScope.TYPE != null}">
                <c:set var="type" value="${requestScope.TYPE}"/>
            </c:when>
            <c:when test="${param.type != null}">
                <c:set var="type" value="${param.type}"/>
            </c:when>
            <c:otherwise>
                <c:redirect url="ForwardController"/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${requestScope.DISPLAY != null}">
                <c:set var="display" value="${requestScope.DISPLAY}"/>
            </c:when>
            <c:when test="${param.display != null}">
                <c:set var="display" value="${param.display}"/>
            </c:when>
            <c:otherwise>
                <c:redirect url="ForwardController"/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${requestScope.PAGE != null}">
                <c:set var="page" value="${requestScope.PAGE}"/>
            </c:when>    
            <c:when test="${param.page != null}">
                <c:set var="page" value="${param.page}"/>
            </c:when>
            <c:otherwise>
                <c:set var="page" value="1"/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${requestScope.PAGES != null}">
                <c:set var="pages" value="${requestScope.PAGES}"/>
            </c:when>
            <c:when test="${param.pages != null}">
                <c:set var="pages" value="${param.pages}"/>
            </c:when>
            <c:otherwise>
                <c:redirect url="ForwardController"/>
            </c:otherwise>
        </c:choose>


        <div id="container">
            <div>
                <select onchange="invokeMainServlet(this.value)">
                    <c:choose>
                        <c:when test="${type == 'career'}">
                            <option value="career" selected="selected">
                                Career
                            </option>
                        </c:when>
                        <c:otherwise>
                            <option value="career">
                                Career
                            </option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${type == 'province'}">
                            <option value="province" selected="selected">
                                Province
                            </option>
                        </c:when>
                        <c:otherwise>
                            <option value="province">
                                Province
                            </option>
                        </c:otherwise>
                    </c:choose>
                </select>

                <select onchange="callTextOrChart('${type}', this.value, '${page}', '${pages}')">
                    <c:choose>
                        <c:when test="${display == 'text'}">
                            <option value="text" selected="selected">
                                Text/Plain
                            </option>
                        </c:when>
                        <c:otherwise>
                            <option value="text">
                                Text/Plain
                            </option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${display == 'chart'}">
                            <option value="chart" selected="selected">
                                Chart
                            </option>
                        </c:when>
                        <c:otherwise>
                            <option value="chart">
                                Chart
                            </option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>

            <c:if test="${type == 'career'}">
                <c:if test="${display == 'text'}">
                    <jsp:include page="textcareer.jsp"/>    
                </c:if>
                <c:if test="${display == 'chart'}">
                    <jsp:include page="chartcareer.jsp"/>    
                </c:if>
            </c:if>
            <c:if test="${type == 'province'}">
                <c:if test="${display == 'text'}">
                    <jsp:include page="textprovince.jsp"/>    
                </c:if>
                <c:if test="${display == 'chart'}">
                    <jsp:include page="chartprovince.jsp"/>    
                </c:if>
            </c:if>
        </div>
    </body>
</html>