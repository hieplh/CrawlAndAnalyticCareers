<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
        <script>
            <c:set var="colors" value="${sessionScope.COLORS}"/>
            function drawChartTop10HotCareers () {
            <c:set var="flag" value="0"/>
            new Chart(document.getElementById("top_10_hot_careers"), {
            type: 'bar',
                    data: {
                    labels: ["2020"],
                            datasets: [
            <c:forEach var="career" items="${sessionScope.HOT_CAREERS}" varStatus="counter">
                <c:choose>
                    <c:when test="${flag == 0}">
                        <c:set var="flag" value="1"/>
                    </c:when>
                    <c:otherwise>
                            ,
                    </c:otherwise>
                </c:choose>
                            {
                            label: "${career.careerName}",
                                    backgroundColor: "${colors[counter.index]}",
                                    data: [${career.count}]
                            }
            </c:forEach>
                            ]
                    },
                    options: {
                    title: {
                    display: true,
                            text: 'Biểu đồ top 10 ngành nghề tuyển dụng nhiều năm 2020'
                    }
                    }
            });
            }

            function drawChartTop10LowestRecruitCareers () {
            <c:set var="flag" value="0"/>
            new Chart(document.getElementById("top_10_lowest_recruitment_careers"), {
            type: 'bar',
                    data: {
                    labels: ["2020"],
                            datasets: [
            <c:forEach var="career" items="${sessionScope.LOWEST_RECRUITMENT_CAREERS}" varStatus="counter">
                <c:choose>
                    <c:when test="${flag == 0}">
                        <c:set var="flag" value="1"/>
                    </c:when>
                    <c:otherwise>
                            ,
                    </c:otherwise>
                </c:choose>
                            {
                            label: "${career.careerName}",
                                    backgroundColor: "${colors[counter.index]}",
                                    data: [${career.count}]
                            }
            </c:forEach>
                            ]
                    },
                    options: {
                    title: {
                    display: true,
                            text: 'Biểu đồ top 10 ngành nghề tuyển dụng thấp năm 2020'
                    }
                    }
            });
            }
        </script>
        <style>
            .clearfix::after {
                content: "";
                clear: both;
                display: table;
            }
            .draw_canvas {
                width: 48%; height: 600px;
                float: left;
                position: relative;
            }
            .canvas {
                position: absolute;
                bottom: 0;
            }
            .detail_canvas {
                position: absolute;
                bottom: 0;
            }
            .avg_salary_table {
                width: fit-content;
                float: left;
            }
        </style>
    </head>
    <body>
        <jsp:include page="headerpage.jsp"/>
        <br/>
        <div>
            <div class="draw_canvas">
                <canvas class="canvas" id="top_10_hot_careers" width="430" height="400"></canvas>
                    <c:url var="detail" value="ForwardController">
                        <c:param name="btnAction" value="detail"/>
                        <c:param name="careerId" value="${sessionScope.HOT_CAREERS.get(0).careerId}"/>
                        <c:param name="typeCareer" value="top"/>
                    </c:url>
                <a class="detail_canvas" href="${detail}">Xem thêm</a>
            </div>
            <div class="draw_canvas" style="margin-left: 2%">
                <canvas class="canvas" id="top_10_lowest_recruitment_careers" width="430" height="400"></canvas>
                    <c:url var="detail" value="ForwardController">
                        <c:param name="btnAction" value="detail"/>
                        <c:param name="careerId" value="${sessionScope.LOWEST_RECRUITMENT_CAREERS.get(0).careerId}"/>
                        <c:param name="typeCareer" value="lowest"/>
                    </c:url>
                <a class="detail_canvas" href="${detail}">Xem thêm</a>
            </div>
            <div class="clearfix"></div>
            <p style="text-align: center">
                Số liệu tuyển dụng dựa trên website: 
                <a href="https://www.timviecnhanh.com/">https://www.timviecnhanh.com/</a>
            </p>
        </div> <br/>

        <c:set var="count" value="0"/>
        <div style="width: fit-content; margin: auto;">
            <h3 style="text-align: center">
                Bảng thống kê lương trung bình của một số ngành nghề
            </h3>
            <div class="avg_salary_table" style="transform: translateX(-25%)">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Career Name</th>
                            <th>AVG Salary</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="career" items="${sessionScope.AVG_SALARY_OF_CAREERS}" begin="0" end="${sessionScope.AVG_SALARY_OF_CAREERS.size() / 2 - 1}" varStatus="counter">
                            <tr>
                                <td style="text-align: center">
                                    <c:set var="count" value="${count + 1}"/>
                                    <c:out value="${count}"/>
                                </td>
                                <td>
                                    <c:out value="${career.careerName}"/>
                                </td>
                                <td style="text-align: right">
                                    <c:out value="${career.salary}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="avg_salary_table" style="transform: translateX(25%)">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Career Name</th>
                            <th>AVG Salary</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="career" items="${sessionScope.AVG_SALARY_OF_CAREERS}" begin="${sessionScope.AVG_SALARY_OF_CAREERS.size() / 2}" varStatus="counter">
                            <tr>
                                <td style="text-align: center">
                                    <c:set var="count" value="${count + 1}"/>
                                    <c:out value="${count}"/>
                                </td>
                                <td>
                                    <c:out value="${career.careerName}"/>
                                </td>
                                <td style="text-align: right">
                                    <c:out value="${career.salary}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="clearfix"></div>
            <p>
                Số liệu lương trung bình của một số ngành nghề dựa trên website 
                <a href="https://www.timviecnhanh.com/">https://www.timviecnhanh.com/</a>
            </p>
        </div>
        <script>
            drawChartTop10HotCareers();
            drawChartTop10LowestRecruitCareers();
        </script>
    </body>
</html>