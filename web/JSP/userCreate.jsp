<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<c:import url="layout/template.jsp">
    <c:param name="title" value="detail"/>
    <c:param name="content">
        <h1 id="admTitle">Create Users</h1>
        <div>
            <s:form action="UserCreateAction">
                <s:textfield required="true" label="User ID" name="u_id" />
                <s:textfield required="true" label="Password" name="u_passwd" />
                <s:textfield required="true" label="Email" name="u_email" />
                <s:textfield required="true" label="User Group" name="u_group" />
                <s:submit />
            </s:form>
        </div>
        <hr class="adm"/>
        <table class="admTable" cellpadding="0" cellspacing="0" align="center" width="100%">
            <caption class="thdr3">Existing Users</caption>
            <s:iterator value="userList">
                <tr>
                    <td><s:property value="id"/></td>
                    <td><s:property value="email"/></td>
                    <td><s:property value="groupid"/></td>
                </tr>
            </s:iterator>
        </table>
            
    </c:param>
</c:import>