<%--
- form.jsp
-
- Copyright (C) 2022-2023 Álvaro Urquijo.
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
	<acme:input-textarea code="authenticated.auditor.form.label.firm" path="firm"/>
	<acme:input-textarea code="authenticated.auditor.form.label.professional-id" path="professionalId"/>
	<acme:input-textarea code="authenticated.auditor.form.label.certifications" path="certifications"/>
	<acme:input-url code="authenticated.auditor.form.label.link" path="link"/>

	<acme:submit test="${_command == 'create'}" code="authenticated.auditor.form.button.create" action="/authenticated/auditor/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.auditor.form.button.update" action="/authenticated/auditor/update"/>
</acme:form>