<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:input-select path="course" code="lecturer.course-lecture.form.label.nature" choices="${courses}"/>
	<acme:input-select path="lecture" code="lecturer.course-lecture.form.label.nature" choices="${lectures}"/>

	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && editable == true}">
			<acme:submit code="lecturer.course-lecture.form.button.update" action="/lecturer/course-lecture/update"/>
			<acme:submit code="lecturer.course-lecture.form.button.delete" action="/lecturer/course-lecture/delete"/>
			<acme:submit code="lecturer.course-lecture.form.button.publish" action="/lecturer/course-lecture/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course-lecture.form.button.create" action="/lecturer/course-lecture/create"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>

