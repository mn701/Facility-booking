<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<c:import url="layout/template.jsp">
    <c:param name="title" value="detail"/>
    <c:param name="content">
        <h1 id="admTitle">Facility Management</h1>
        <div>
            <h2>Create Facilities</h1>
            <s:form action="FacilityCreateAction">
                <s:textfield required="true" label="Name" name="f_name" />
                <s:textfield required="true" label="Capacity" name="f_capacity" />
                <s:radio label="Facility Type" list="{'Meeting Rooms','Conference Rooms','Discussion Rooms'}" name="f_typestr" multiple="true"></s:radio>
                <s:textfield required="true" label="Description" name="f_description" />
                <s:submit value="Create" cssClass="buttonM" />
            </s:form>
        </div>
        <hr class="adm"/>
        <table class="admTable" align="center" width="100%">
            <caption class="thdr3">Existing Facilities</caption>
            <s:iterator value="facilList">
                <tr>
                    <td><s:property value="id"/></td>
                    <td><s:property value="name"/></td>
                    <td><s:property value="capacity"/></td>
                    <td><s:property value="type"/></td>
                    <td><s:property value="description"/></td>
                    <td>
                        <s:form action="FacilityUpdateBefore" >
                            <s:hidden name="facilityid" value="%{id}"/>
                            <s:submit value="Update" cssClass="buttonS" />
                        </s:form>
                    </td>
                    <td>
                        <s:form action="FacilityDelete" >
                            <s:hidden name="facilityid" value="%{id}"/>
                            <s:submit value="Delete" cssClass="buttonS" />
                        </s:form>
                    </td>
                </tr>
            </s:iterator>
        </table>
    </c:param>
</c:import>
