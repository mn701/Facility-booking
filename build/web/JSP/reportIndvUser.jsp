<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:import url="layout/template.jsp">
    <c:param name="title" value="login"/>
    <c:param name="content">
        From: <s:property value="reportFrom"/>  
        To: <s:property value="reportTo"/>
        <table class="admTable">
            <caption class="thdr2">Booking Records of <s:property value="rptUid"/></caption>
            <thead><tr>
                <th class="firstTh">Booking ID</th><th>Date</th><th>From</th><th>To</th><th>Facility</th><th>Remarks</th><th>Equipment</th><th class="lastTh">Active</th>
            </tr></thead>
            <s:iterator value="rptUserIndv">
                <tr>
                    <td><s:property value="id"/></td>
                    <td><s:date name="evdate" format="dd/MM/yyyy" /></td>
                    <td><s:date name="startTime" format="HH:mm" /></td>
                    <td><s:date name="endTime" format="HH:mm" /></td>
                    <td><s:property value="facility.getName()"/></td>
                    <td><s:property value="remarks"/></td>
                    <td><s:property value="equipment"/></td>
                    <td><s:property value="active"/></td>
                </tr>
            </s:iterator>
        </table>
    </c:param>
</c:import>
