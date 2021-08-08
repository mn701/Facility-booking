<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="/struts-dojo-tags" prefix="sx"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>header</title>
         <s:head/>

    </head>
    <body>
        <div id="topMenu">
            <a href="<s:url action="AdminPage" />">Admin Only</a>
            <a href="<s:url action="Index" />">Home</a>
        </div>
        <a href="<s:url action="Index" />"><div id="siteTitle">Facility Booking System</div></a>
        
    </body>
</html>
