<%-- 
    Document   : careerdetailcontent
    Created on : Jun 29, 2020, 6:08:06 PM
    Author     : Admin
--%>

<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Career Detail Content</title>
    </head>
    <body>
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
        <br/>
    </body>
</html>
