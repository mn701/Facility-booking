<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="/struts-dojo-tags" prefix="sx"%>

<c:import url="layout/template.jsp">
    <c:param name="title" value="Index"/>
        <c:param name="content">
            <div id="header0">
                <div id="menu0">
                    <ul>
                        <li>
                            <a href="<s:url action="GoToBooking" />">Booking Entry</a>
                        </li>
                        <li>
                            <a href="<s:url action="HistoryAction" />">View History</a>
                        </li>
                        <li>
                            <a href="<s:url action="LoginPage" />">LOGIN</a>
                        </li>
                        <li>
                            <div>Logged in as: <span class="loginuser"><s:property value="#session.curUser.getId()"/></span></div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="menu1">
                <s:form action="SearchResult" >    
                    <div>
                        <s:radio label="Facility Type" name="faciTypeStr" list="{'All','Meeting Rooms','Conference Rooms','Discussion Rooms'}"/>
                    </div>
                    <div>
                        <s:select name="availItemStr" list="{'Show All', 'Show Only Available'}" value="availItem" />
                        <sx:datetimepicker label="Date" displayFormat="dd-MMM-yyyy" name="selDate"></sx:datetimepicker>
                        <s:textfield label="From" name="selSTimeStr" />
                        <s:textfield label="To" name="selETimeStr" />
                    </div>
                    <s:submit cssClass="buttonM" />
                </s:form>
            </div>
            <div class="menu11">
                <s:label value= "Selected Facility Type:" /> <s:property value="typeItem" /> <br/>
                <s:property value="availItem" /> <s:property value="availItemStr" /><br/>
                For: <s:date name="selDate" format="dd/MM/yyyy" />
                From: <s:date name="selSTime" format="HH:mm" />
                To: <s:date name="selETime" format="HH:mm" />
            </div>
            <table class="mainTable" border="0">
                <s:iterator value="facilities">
                    <tr>
                        <td width="150px" align="center" padding ="20px">
                            <img src="${pageContext.request.contextPath}/<s:property value="picstr"/>" width="120px" height="85px" >
                        </td>
                        <td width="200px" class="fname" ><s:property value="name"/></td>
                        <td><s:property value="description"/></td>
                        <td width="120px"><s:property value="type"/></td>
                        <td width="50px"><s:property value="capacity"/></td>
                        <td>
                            <s:form action="GoToDetail" >
                                <s:hidden name="facilityId" value="%{id}"/>
                                <s:submit value="View" cssClass="buttonS"/>
                            </s:form>
                        </td>
                            

                    </tr>
                </s:iterator>
            </table>

    </c:param>
</c:import>
