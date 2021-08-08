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
            <s:form action="FacilityUpdateAction">
                <s:hidden name="f_id" value="%{facilityToUpdate.id}"/>
                <s:textfield label="f_id_str" name="f_id_str" value="%{facilityToUpdate.id}"/>
                <s:textfield label="Name" name="f_name" value="%{facilityToUpdate.name}"/>
                <s:textfield label="Capacity" name="f_capacity" value="%{facilityToUpdate.capacity}" />
                <s:radio label="Facility Type" list="{'Meeting Rooms','Conference Rooms','Discussion Rooms'}" name="f_typestr" value="%{ftypestr_before}" multiple="true"></s:radio>
                <s:textfield label="Description" name="f_description" value="%{facilityToUpdate.description}"/>
                <s:hidden label="Picture" name="f_picstr" value="%{facilityToUpdate.picstr}"/>
                <s:submit value="Update" cssClass="buttonM" />
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
                        <s:form action="FacilityUpdate" >
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