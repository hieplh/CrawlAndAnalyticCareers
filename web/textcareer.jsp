<%-- 
    Document   : textcareer
    Created on : Jul 3, 2020, 11:01:15 AM
    Author     : Admin
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="Stylesheet/Pagination.css"/>
</head>
<body>
    <c:set var="careers" value="${applicationScope.ALL_CAREERS}"/>
    <div style="width: fit-content;">
        <div>
            <c:forEach var="career" items="${sessionScope.ADVANCE_MAP_RESULT}">
                <h3>
                    ${careers.get(career.getKey())}
                </h3>
                <table style="margin-left: 30px;">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Province</th>
                            <th>Num of recruitment</th>
                            <th>Percent</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="province" items="${career.getValue()}" varStatus="counter">
                            <tr>
                                <td style="text-align: center">
                                    ${counter.count}
                                </td>
                                <td>
                                    ${province.name}
                                </td>
                                <td style="text-align: center">
                                    ${province.count}
                                </td>
                                <td style="text-align: center">
                                    <fmt:formatNumber var="percent" type="percent" pattern="#.#%" value="${province.percent}"/>
                                    ${percent}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:forEach>
        </div> <br/>

        <div class="pagination">
            <c:set var="index" value="3"/>
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
                <c:otherwise>
                    <c:set var="pages" value="${param.pages}"/>
                </c:otherwise>
            </c:choose>


            <c:forEach begin="0" end="2" step="1">
                <c:if test="${page - index > 0}">
                    <c:url var="urlReWriting" value="ForwardController">
                        <c:param name="btnAction" value="advance"/>
                        <c:param name="type" value="career"/>
                        <c:param name="page" value="${page - index}"/>
                    </c:url>
                    <a class="page" href="${urlReWriting}">${page - index}</a>
                    &nbsp;
                </c:if>
                <c:set var="index" value="${index - 1}"/>    
            </c:forEach>

            <span class="page" style="background-color: #E2E2E2;">${page}</span>

            <c:set var="index" value="1"/>
            <c:forEach begin="0" end="2" step="1">
                <c:if test="${page + index <= pages}">
                    <c:url var="urlReWriting" value="ForwardController">
                        <c:param name="btnAction" value="advance"/>
                        <c:param name="type" value="career"/>
                        <c:param name="page" value="${page + index}"/>
                    </c:url>
                    &nbsp;
                    <a class="page" href="${urlReWriting}">${page + index}</a>
                </c:if>
                <c:set var="index" value="${index + 1}"/>    
            </c:forEach>
        </div>
    </div>
</body>
