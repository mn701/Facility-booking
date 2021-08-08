<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>
<c:import url="layout/template.jsp">
    <c:param name="title" value="detail"/>
    <c:param name="content">
            <div class="menu1">
                <caption class="thdr3">Booking Form</caption>
                <s:form action="BookingSuccess" validate="true" >
                    Login User: <span cssClass="notlabel"><s:property value="#session.curUser.getId()" /></span><br/>
                Selected Facility Name: <span cssClass="notlabel"> <s:property value="#session.selectedFacility.name"/></span>
                <s:textfield required="true" label="Facility ID" name="b_facility.id" value="%{#session.selectedFacility.id}" /> 
                <sx:datetimepicker label="Date" displayFormat="dd-MMM-yyyy" name="b_evdate" value="%{#session.bookingdate}"></sx:datetimepicker>
                <s:textfield required="true" label="From" name="b_startTimeStr" value="%{#session.bookingstimestr}"/> 
                <s:textfield required="true" label="To" name="b_endTimeStr"value="%{#session.bookingetimestr}"/> 
                <s:textarea rows="4" cols="20" label="Remarks" name="b_remarks" />
                <s:textfield label="Equipment" name="b_equipment" />
                <tr><s:submit value="Book" cssClass="buttonM"/></tr>
            </s:form>
            </div>
            <div class="false"><s:property value="bookingFailMessage"/></div>

    </c:param>
</c:import>
