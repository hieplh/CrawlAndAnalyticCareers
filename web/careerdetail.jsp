<%-- 
    Document   : careerdetail
    Created on : Jun 28, 2020, 7:01:59 PM
    Author     : Admin
--%>

<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Career Page</title>
        <link rel="stylesheet" href="Stylesheet/Pagination.css"/>
        <style>
            .selected {
                width: 50%;
                float: left;
            }
            .clearfix::after {
                content: "";
                clear: both;
                display: table;
            }
        </style>
        <script>
            function callbackTypeSearch(type, value) {
                var xhtml = new XMLHttpRequest();
                xhtml.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("table_content").innerHTML = this.responseText;
                    }
                };
                var s = "ForwardController?btnAction=detail"
                        + "&careerId=" + value
                        + "&typeCareer=" + type
                        + "&ajax=ajax";
                xhtml.open("GET", s, true);
                xhtml.send();
            }

            function callFormTypeCareer(value) {
                window.location = "ForwardController?btnAction=detail"
                        + "&typeCareer=" + value;
            }
        </script>
    </head>
    <body>
        <jsp:include page="headerpage.jsp"/>
        <c:set var="type" value="${requestScope.TYPE}"/>
        <br/>
        <div class="selected-menu" style="margin-top: 20px">
            <div class="selected">
                <select onchange="callbackTypeSearch('${type}', this.value)">
                    <c:if test="${requestScope.TYPE == 'top'}">
                        <c:forEach var="career" items="${sessionScope.HOT_CAREERS}">
                            <c:choose>
                                <c:when test="${career.careerId == CAREER_ID}">
                                    <option value="${career.careerId}" selected="selected">
                                        ${career.careerName}
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${career.careerId}">
                                        ${career.careerName}
                                    </option>        
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>        
                    </c:if>
                    <c:if test="${requestScope.TYPE == 'lowest'}">
                        <c:forEach var="career" items="${sessionScope.LOWEST_RECRUITMENT_CAREERS}">
                            <c:choose>
                                <c:when test="${career.careerId == CAREER_ID}">
                                    <option value="${career.careerId}" selected="selected">
                                        ${career.careerName}
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${career.careerId}">
                                        ${career.careerName}
                                    </option>        
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>        
                    </c:if>
                </select>
            </div>

            <div class="selected" style="text-align: right">
                <select onchange="callFormTypeCareer(this.value)">
                    <c:choose>
                        <c:when test="${requestScope.TYPE == 'top'}">
                            <option value="top" selected="selected">
                                Top Recruit Careers
                            </option>
                        </c:when>
                        <c:otherwise>
                            <option value="top">
                                Top Recruit Career
                            </option>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${requestScope.TYPE == 'lowest'}">
                            <option value="lowest" selected="selected">
                                Lowest Recruit Careers
                            </option>
                        </c:when>
                        <c:otherwise>
                            <option value="lowest">
                                Lowest Recruit Careers
                            </option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
            <div class="clearfix">
            </div>
        </div>

        <div id="table_content">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Company</th>
                        <th>Job Name</th>
                        <th>Recruitment</th>
                        <th>Salary</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${requestScope.CAREER_DETAIL != null and requestScope.CAREER_DETAIL != 'null'}">
                        <x:parse var="doc" doc="${requestScope.CAREER_DETAIL}"/>
                    <tbody>
                        <x:forEach var="career" select="$doc//Career" varStatus="counter">
                            <tr>
                                <td style="text-align: center">
                                    ${counter.count}
                                </td>
                                <td>
                                    <x:out select="$career//COMPANY"/>
                                </td>
                                <td>
                                    <x:out select="$career//JOB_NAME"/>
                                </td>
                                <td style="text-align: center">
                                    <x:out select="$career//RECRUITMENT"/>
                                </td>
                                <td style="text-align: right">
                                    <x:out select="$career//SALARY"/>
                                </td>
                            </tr>
                        </x:forEach>
                    </tbody>
                </c:if>
                </tbody>
            </table>

            <c:if test="${requestScope.CAREER_DETAIL != null and requestScope.CAREER_DETAIL != 'null'}">
                <br/>
                <div class="pagination">
                    <c:set var="index" value="3"/>
                    <c:forEach begin="0" end="2" step="1">
                        <c:if test="${requestScope.PAGE - index > 0}">
                            <c:url var="urlReWriting" value="ForwardController">
                                <c:param name="btnAction" value="detail"/>
                                <c:param name="typeCareer" value="${requestScope.TYPE}"/>
                                <c:param name="careerId" value="${requestScope.CAREER_ID}"/>
                                <c:param name="page" value="${requestScope.PAGE - index}"/>
                            </c:url>
                            <a class="page" href="${urlReWriting}">${requestScope.PAGE - index}</a>
                            &nbsp;
                        </c:if>
                        <c:set var="index" value="${index - 1}"/>    
                    </c:forEach>

                    <c:url var="urlReWriting" value="ForwardController">
                        <c:param name="btnAction" value="detail"/>
                    </c:url>
                    <span class="page" style="background-color: #E2E2E2;">${requestScope.PAGE}</span>


                    <c:set var="index" value="1"/>
                    <c:forEach begin="0" end="2" step="1">
                        <c:if test="${requestScope.PAGE + index <= requestScope.PAGES}">
                            <c:url var="urlReWriting" value="ForwardController">
                                <c:param name="btnAction" value="detail"/>
                                <c:param name="typeCareer" value="${requestScope.TYPE}"/>
                                <c:param name="careerId" value="${requestScope.CAREER_ID}"/>
                                <c:param name="page" value="${requestScope.PAGE + index}"/>
                            </c:url>
                            &nbsp;
                            <a class="page" href="${urlReWriting}">${requestScope.PAGE + index}</a>
                        </c:if>
                        <c:set var="index" value="${index + 1}"/>    
                    </c:forEach>
                </div> 
            </c:if>
        </div>
    </body>
</html>