<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<c:import url="layout/template.jsp">
    <c:param name="title" value="detail"/>
    <c:param name="content">
        <h1 id="admTitle">Administrator Main Page</h1>
        <div class="admMenu">
            <h2>Manage Users</h2>
            <ul>
                <li><a href="<s:url action="PrepareUserList" />">Create New Users</a></li>
            </ul>
        </div>
        <div class="admMenu">
            <h2>Manage Facilities</h2>
            <ul>
                <li><a href="<s:url action="PrepareFacilityList" />">Manage Facilities</a></li>
            </ul>
        </div>
        <div class="admMenu">
            <h2>Reports</h2>
            <p style="margin-left: 20pt">Select Period</p>
            <s:form action="ReportDates" validate="true">
                <sx:datetimepicker label="From" displayFormat="dd-MMM-yyyy" name="reportFrom"></sx:datetimepicker>
                <sx:datetimepicker label="To" displayFormat="dd-MMM-yyyy" name="reportTo"></sx:datetimepicker>
                <s:submit cssClass="buttonM" />
            </s:form>
        </div>
    </c:param>
</c:import>