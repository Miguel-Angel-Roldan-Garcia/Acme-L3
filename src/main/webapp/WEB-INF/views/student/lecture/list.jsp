

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list navigable="false">
	<acme:list-column code="student.lecture.list.label.title" path="title" width="10%"/>
	<acme:list-column code="student.lecture.list.label.nature" path="nature" width="80%"/>	
	<acme:list-column code="student.lecture.list.label.estimated-learning-time" path="estimatedLearningTime" width="80%"/>	
	<acme:list-column code="student.lecture.list.label.link" path="link" width="80%"/>	
</acme:list>