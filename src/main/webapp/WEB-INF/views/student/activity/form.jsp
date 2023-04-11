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
	<acme:input-textbox code="student.activity.form.label.title" path="title"/>
	<acme:input-textarea code="student.activity.form.label.abstract$" path="abstract$"/>
	<acme:input-moment code="student.activity.form.label.initial-date" path="initialDate"/>
	<acme:input-moment code="student.activity.form.label.finish-date" path="finishDate"/>
	<acme:input-url code="student.activity.form.label.link" path="link"/>
	
	<jstl:if test="${_command_ != 'create' }">
		<%-- AÑADIR CUANDO SE SEPA EL PERIODO DE TIEMPO --%>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${(_command == 'show' || _command == 'update' || _command == 'delete') && draftMode == true}">
			<acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
			<acme:submit code="student.activity.form.button.delete" action="/student/activity/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.activity.form.button.create" action="/student/activity/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>