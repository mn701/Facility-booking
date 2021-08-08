<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>
<c:import url="layout/template.jsp">
    <c:param name="title" value="detail"/>
    <c:param name="content">
        <div id="left-box">
            <img src="${pageContext.request.contextPath}/<s:property value="picstr"/>" height="300px" >
        </div>
        <div id="right-box">
            <table>
                <tr>
                    <th> Facility Name: </th>
                    <td><s:property value="sel.name"/></td>
                </tr>
                <tr>
                    <th>Capacity: </th>
                    <td><s:property value="sel.capacity"/></td>
                </tr>
                <tr>
                    <th>Facility Type: </th>
                    <td><s:property value="sel.type"/></td>
                </tr>
                <tr>
                    <th>Description:</th>
                    <td><s:property value="sel.description"/></td>
                </tr>
                <tr>
                    <th></th>
                    <td padding="30px"> <a href="<s:url action="GoToBooking" />">Book This Facility</a></td>
                </tr>
               
            </table>
        </div>
        <div id="btm-box">
            <div class="menu1">
                <s:form action="AvailabilityCheck" >    
                    <sx:datetimepicker label="Date" displayFormat="dd-MMM-yyyy" name="selDate" value="%{#session.bookingdate}"></sx:datetimepicker>
                    <s:textfield label="From" name="selSTimeStr" value="%{#session.bookingstimestr}"/> 
                    <s:textfield label="To" name="selETimeStr"value="%{#session.bookingetimestr}"/> 
                    <s:submit value="check" cssClass="buttonS" />
                </s:form>
            </div>
            <div class="<s:property value="res"/>">
                <s:property value="messages"/>
            </div>
            <table>
                <tr><th>time</th><th>availability</th></tr>
                <s:iterator value="slot">
                    <tr>
                        <td><s:date name="stime" format="HH:mm" /></td>
                        <td>
                            <div class=<s:property value="avail"/> >
                                <s:property value="avail"/>
                            </div>
                        </td>
                    </tr>
                </s:iterator>
            </table>
            <table class="samedayTable" border="0">
                <caption class="thdr2">Same Day Bookings</caption>
                <thead><tr>
                    <th class="firstTh">Booking ID</th><th>Date</th><th>From</th><th>To</th><th>Facility</th><th>Remarks</th><th class="lastTh">Active</th>
                </tr></thead>
                <s:iterator value="sameDayBookingsList">
                    <tr>
                        <td><s:property value="id"/></td>
                        <td><s:date name="evdate" format="dd/MM/yyyy" /></td>
                        <td><s:date name="startTime" format="HH:mm" /></td>
                        <td><s:date name="endTime" format="HH:mm" /></td>
                        <td><s:property value="facility.getName()"/></td>
                        <td><s:property value="remarks"/></td>
                        <td><s:property value="active"/></td>
                    </tr>
                </s:iterator>
            </table>
        </div>
    </c:param>
</c:import>
