<%--
- form.jsp
-
- Copyright (C) 2022-2023 Javier Fernández Castillo.
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
	<acme:input-textarea code="company.practicum-session.form.label.title" path="title"/>
	<acme:input-textarea code="company.practicum-session.form.label.abstract" path="abstract$"/>
	<acme:input-moment code="company.practicum-session.form.label.start-date" path="startDate"/>
	<acme:input-moment code="company.practicum-session.form.label.end-date" path="endDate"/>
	<acme:input-checkbox code="company.addendum-session.form.label.isAddendum" path="isAddendum" readonly="true"/>
	<acme:input-url code="company.practicum-session.form.label.link" path="link"/>
	
	<jstl:if test="${confirmation}">
		<acme:input-checkbox code="company.addendum-session.form.label.accept" path="accept"/>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="company.practicum-session.form.button.update" action="/company/practicum-session/update"/>
			<acme:submit code="company.practicum-session.form.button.delete" action="/company/practicum-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practicum-session.form.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create-addendum'}">
			<acme:submit code="company.practicum-session.form.button.create" action="/company/practicum-session/create-addendum?masterId=${masterId}"/>
		</jstl:when>			
	</jstl:choose>		
</acme:form>

