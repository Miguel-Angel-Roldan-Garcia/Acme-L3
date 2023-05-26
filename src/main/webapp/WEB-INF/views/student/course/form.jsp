

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
	
</acme:form>