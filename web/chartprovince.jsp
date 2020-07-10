<%-- 
    Document   : chartprovince
    Created on : Jul 3, 2020, 5:25:18 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="Stylesheet/Pagination.css"/>
    <style>
        .chart-content {
            width: 50%;
            float: left;
        }
        .chart-content canvas {
            width: fit-content;
        }
    </style>
</head>
<body>
    <div>
        <div id="content-container">
            <c:set var="careers" value="${applicationScope.ALL_PROVINCES}"/>
            <c:forEach var="list" items="${sessionScope.ADVANCE_MAP_RESULT}">
                <div class="content">
                    <h3>
                        ${careers.get(list.getKey())}
                    </h3>
                    <div class="chart-content">
                        <canvas id="bar-${list.getKey()}" height="300"></canvas>
                    </div>
                    <div class="chart-content">
                        <canvas id="pie-${list.getKey()}" height="300"></canvas>
                    </div>
                    <div class="clearfix">
                    </div>
                    <script>
                        <c:set var="flag" value="0"/>
                        var dynamicColors = function() {
                        var r = Math.floor(Math.random() * 255);
                        var g = Math.floor(Math.random() * 255);
                        var b = Math.floor(Math.random() * 255);
                        return "rgb(" + r + "," + g + "," + b + ")";
                        };
                        var arrColors = [];
                        <c:forEach var="result" items="${list.getValue()}" varStatus="counter">
                        arrColors.push(dynamicColors());
                        </c:forEach>

                        new Chart(document.getElementById('bar-${list.getKey()}'), {
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
                                                backgroundColor: arrColors[${counter.index}],
                                                data: [${result.count}]
                                        }
                        </c:forEach>
                                        ]
                                },
                                options: {
                                title: {
                                display: true,
                                        text: 'Biểu đồ tuyển dụng của từng ngành nghề hiện có trong tỉnh'
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
                        new Chart(document.getElementById('pie-${list.getKey()}'), {
                        type: 'pie',
                                data: {
                                labels: [
                        <c:set var="flag" value="0"/>
                        <c:forEach var="result" items="${list.getValue()}" varStatus="counter">
                            <c:choose>
                                <c:when test="${flag == 0}">
                                    <c:set var="flag" value="1"/>
                                </c:when>
                                <c:otherwise>
                                ,
                                </c:otherwise>
                            </c:choose>
                                '${result.name}'
                        </c:forEach>
                                ],
                                        datasets: [{
                                        backgroundColor: [
                        <c:set var="flag" value="0"/>
                        <c:forEach var="result" items="${list.getValue()}" varStatus="counter">
                            <c:choose>
                                <c:when test="${flag == 0}">
                                    <c:set var="flag" value="1"/>
                                </c:when>
                                <c:otherwise>
                                        ,
                                </c:otherwise>
                            </c:choose>
                                        arrColors[${counter.index}]
                        </c:forEach>
                                        ],
                                                data: [
                        <c:set var="flag" value="0"/>
                        <c:forEach var="result" items="${list.getValue()}" varStatus="counter">
                            <c:choose>
                                <c:when test="${flag == 0}">
                                    <c:set var="flag" value="1"/>
                                </c:when>
                                <c:otherwise>
                                                ,
                                </c:otherwise>
                            </c:choose>
                            ${result.percent}
                        </c:forEach>
                                                ]
                                        }]
                                },
                                options: {
                                title: {
                                display: true,
                                        text: 'Biểu đồ thị phần của từng ngành nghề hiện có trong tỉnh'
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
                </div>
            </c:forEach>
        </div>

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
                        <c:param name="type" value="province"/>
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
                        <c:param name="type" value="province"/>
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