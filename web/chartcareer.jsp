<%-- 
    Document   : chartcareer
    Created on : Jul 3, 2020, 5:25:09 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="Stylesheet/Pagination.css"/>
</head>
<body>
    <div>
        <div>
            <c:set var="careers" value="${applicationScope.ALL_CAREERS}"/>
            <c:forEach var="list" items="${sessionScope.ADVANCE_MAP_RESULT}">
                <h3>
                    ${careers.get(list.getKey())}
                </h3>
                <canvas id="${list.getKey()}" width="1500" height="500"></canvas>
                <script>
                    <c:set var="flag" value="0"/>
                    var dynamicColors = function() {
                    var r = Math.floor(Math.random() * 255);
                    var g = Math.floor(Math.random() * 255);
                    var b = Math.floor(Math.random() * 255);
                    return "rgb(" + r + "," + g + "," + b + ")";
                    };
                    new Chart(document.getElementById('${list.getKey()}'), {
                    type: 'bar',
                            data: {
                            labels: ["2020"],
                                    datasets: [
                    <c:forEach var="result" items="${list.getValue()}" varStatus="counter">
                        <c:choose>
                            <c:when test="${flag == 0}">
                                <c:set var="flag" value="1"/>
                            </c:when>
                            <c:otherwise>
                                    ,
                            </c:otherwise>
                        </c:choose>
                                    {
                                    label: "${result.name}",
                                            backgroundColor: dynamicColors(),
                                            data: [${result.count}]
                                    }
                    </c:forEach>
                                    ]
                            },
                            options: {
                            title: {
                            display: true,
                                    text: 'Biểu đồ tuyển dụng của ngành nghề hiện có trên thị trường'
                            },
                                    layout: {
                                    padding: {
                                    left: 50,
                                            right: 0,
                                            top: 0,
                                            bottom: 0
                                    }
                                    }
                            }
                    });
                </script>
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
                        <c:param name="display" value="chart"/>
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
                        <c:param name="display" value="chart"/>
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