<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:import url="layout/template.jsp">
  <c:param name="title" value="login"/>
  <c:param name="content">
      <s:form action="LoginAction" cssClass="frms">
          <h2 class="thdr2">Login</h2>
        <s:textfield label="User ID" name="userid"/><br>
        <s:password label="Password" name="passwd"/>
        <s:submit value="Login" cssClass="buttonM"/>
    </s:form>
  </c:param>
</c:import>
