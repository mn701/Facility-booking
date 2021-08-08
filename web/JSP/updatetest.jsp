<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<c:import url="layout/template.jsp">
    <c:param name="title" value="detail"/>
    <c:param name="content">
        <h1 id="admTitle">Update Facilities</h1>
        <div>
            <s:property value="facilityToUpdate.id"/>
            <s:property value="facilityToUpdate.name"/>
            <s:form action="UpdateActionTest">
                <s:textfield label="ID" name="f_id" value="%{facilityToUpdate.id}"/>
                <s:textfield label="Name" name="f_name" value="%{facilityToUpdate.name}"/>
                <s:textfield label="Capacity" name="f_capacity" value="%{facilityToUpdate.capacity}" />
                <s:radio label="Facility Type" name="f_typeStr" value="%{facilityToUpdate.type}" list="{'MEETING','CONFERENCE','DISCUSSION'}"/>
                <s:textfield label="Description" name="f_description" value="%{facilityToUpdate.description}"/>
                <s:hidden label="Picture" name="f_picstr" value="%{facilityToUpdate.picstr}"/>
                <s:submit value="Update" cssClass="buttonM" />
            </s:form>
        </div>
    </c:param>
</c:import>