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
	<acme:input-textarea code="assistant.course-session.form.label.title" path="title"/>
	<acme:input-textarea code="assistant.course-session.form.label.abstract" path="abstract$"/>
	<acme:input-select path="nature" code="assistant.course-session.form.label.nature" choices="${natures}"/>
	<acme:input-moment code="assistant.course-session.form.label.start-date" path="startDate"/>
	<acme:input-moment code="assistant.course-session.form.label.end-date" path="endDate"/>
	<acme:input-url code="assistant.course-session.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="assistant.course-session.form.button.update" action="/assistant/course-session/update"/>
			<acme:submit code="assistant.course-session.form.button.delete" action="/assistant/course-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.course-session.form.button.create" action="/assistant/course-session/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>

