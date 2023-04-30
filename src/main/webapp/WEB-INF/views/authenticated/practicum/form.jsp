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
	<acme:input-textbox code="company.practicum.form.label.code" path="code"/>
	<acme:input-textarea code="company.practicum.form.label.title" path="title"/>
	<acme:input-textarea code="company.practicum.form.label.abstract" path="abstract$"/>
	<acme:input-textarea code="company.practicum.form.label.goals" path="goals"/>
	<acme:input-double code="company.practicum.form.label.estimated-total-time" path="estimatedTotalTime" readonly="true"/>
	<acme:input-textbox code="company.practicum.form.label.course" path="courseCode" readonly="true"/>

	<acme:button code="company.practicum.form.button.company" action="/authenticated/company/show?username=${company.userAccount.username}"/>
</acme:form>
