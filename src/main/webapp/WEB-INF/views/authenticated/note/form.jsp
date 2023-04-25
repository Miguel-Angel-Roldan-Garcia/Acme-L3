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

<acme:form readonly="${_command == 'show' }">
	<acme:input-textbox code="authenticated.note.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.note.form.label.message" path="message"/>
	<acme:input-url code="authenticated.note.form.label.link" path="link"/>
	<jstl:choose>
		<jstl:when test="${_command == 'create' }">
			<acme:input-checkbox code="authenticated.note.form.label.confirm" path="confirmed"/>
			<acme:submit code="authenticated.note.form.button.create" action="/authenticated/note/create"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:input-moment code="authenticated.note.form.label.instantiation-moment" path="instantiationMoment"/>
			<acme:input-textbox code="authenticated.note.form.label.author" path="author"/>
			<acme:input-textbox code="authenticated.note.form.label.email" path="email"/>
		</jstl:otherwise>
	</jstl:choose>
	

</acme:form>