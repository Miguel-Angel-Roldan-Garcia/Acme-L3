

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.course.list.label.code" path="code" width="10%"/>
	<acme:list-column code="student.course.list.label.title" path="title" width="10%"/>
	<acme:list-column code="student.course.list.label.abstract$" path="abstract$" width="80%"/>	
	<acme:list-column code="student.course.list.label.retailPrice" path="retailPrice" width="80%"/>	
	<acme:list-column code="student.course.list.label.link" path="link" width="80%"/>	
</acme:list>