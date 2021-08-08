<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:import url="layout/template.jsp">
    <c:param name="title" value="login"/>
    <c:param name="content">
        From: <s:property value="reportFrom"/>  
        To: <s:property value="reportTo"/>
        <table class="admTable">
            <caption class="thdr3">Facility Utilization</caption>
            <thead><tr>
                <th class="rptFacilTotal">Facility ID</th><th>Facility Name</th><th class="lastTh">Total Utilization Time</th>
            </tr></thead>
            <s:iterator value="rptFacilTotal" var="record">
                <tr>
                    <td><s:property value="#record[0]"/></td>
                    <td><s:property value="#record[1]"/></td>
                    <td><s:property value="#record[2]"/></td>
                    <td>
                        <s:form action="ReportFacility" >
                            <s:hidden name="rptFid" value="%{#record[0]}"/>
                            <s:hidden name="reportFrom" value="%{reportFrom}"/>
                            <s:hidden name="reportTo" value="%{reportTo}"/>
                            <s:submit value="View" cssClass="buttonS"/>
                        </s:form>
                    </td>
                </tr>
            </s:iterator>
        </table>
        
        <table border="0" class="admTable">
            <caption class="thdr3">Facility Utilization By User</caption>
            <thead><tr>
                <th class="firstTh">User ID</th><th class="lastTh">Total Utilization Time</th>
            </tr></thead>
            <s:iterator value="rptUserTotal" var="record">
                <tr>
                    <td><s:property value="#record[0]"/></td>
                    <td><s:property value="#record[1]"/></td>
                    <td>
                        <s:form action="ReportUser" >
                            <s:hidden name="reportFrom" value="%{reportFrom}"/>
                            <s:hidden name="reportTo" value="%{reportTo}"/>
                            <s:hidden name="rptUid" value="%{#record[0]}"/>
                            <s:submit value="View" cssClass="buttonS"/>
                        </s:form>
                    </td>
                </tr>
            </s:iterator>
        </table>
    </c:param>
</c:import>
