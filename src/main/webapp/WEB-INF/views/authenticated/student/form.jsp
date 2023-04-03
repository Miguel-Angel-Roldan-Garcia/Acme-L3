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
	<acme:input-textarea code="authenticated.student.form.label.statement" path="statement"/>
	<acme:input-textarea code="authenticated.student.form.label.strong-features" path="strongFeatures"/>
	<acme:input-textarea code="authenticated.student.form.label.weak-features" path="weakFeatures"/>
	<acme:input-url code="authenticated.student.form.label.link" path="link"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.student.form.button.create" action="/authenticated/student/create"/>
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="authenticated.student.form.button.update" action="/authenticated/student/update"/>
		<acme:button code="authenticated.student.form.button.enrolments" action="/student/enrolment/list"/>
	</jstl:if>
</acme:form>