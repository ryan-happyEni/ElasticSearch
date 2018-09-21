<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


[${session_user.username}]
MyPage=[<spring:message code="join01.01" />]

<a href="/">home</a>&nbsp;&nbsp;
<a href="/logout">logout</a>