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

<%@page language="java" import="acme.roles.Assistant"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="true">
	<acme:input-textbox code="student.course.form.label.code" path="code"/>
	<acme:input-textbox code="student.course.form.label.title" path="title"/>
	<acme:input-textarea code="student.course.form.label.abstract$" path="abstract$"/>
	<acme:input-url code="student.course.form.label.link" path="link"/>
	<acme:input-money code="student.course.form.label.rentail-price" path="retailPrice"/>
	
	<acme:button code="student.form.button.lectures" action="/student/lecture/list?masterId=${id}"/>
	<acme:button code="student.form.button.lecturer" action="/student/lecturer/show?masterId=${id}"/>
	
	<acme:check-access test="isAuthenticated()">
		<acme:button code="any.course.form.button.tutorials-list" action="/authenticated/tutorial/list?courseId=${id}"/>
	</acme:check-access>
	<acme:check-access test="hasRole('Assistant')">
		<acme:button code="any.course.form.button.tutorials-create" action="/assistant/tutorial/create?courseId=${id}"/>
	</acme:check-access>
</acme:form>