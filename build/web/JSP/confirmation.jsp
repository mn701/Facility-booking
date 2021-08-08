<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<c:import url="layout/template.jsp">
    <c:param name="title" value="detail"/>
    <c:param name="content">
         <div class="ribbon_box">
            <h3 class="ribbon">Booking Successful</h3>
            <p style="padding: 0px 20px">Your booking order has been completed. Notification will be send by email. You can update your future bookings from "View History" link.</p>
        </div>
    </c:param>
</c:import>