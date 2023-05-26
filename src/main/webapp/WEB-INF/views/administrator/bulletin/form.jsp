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
	<acme:input-moment code="administrator.bulletin.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>	
	<acme:input-textbox code="administrator.bulletin.form.label.title" path="title" />
	<acme:input-textbox code="administrator.bulletin.form.label.message" path="message" />
	<acme:input-checkbox code="administrator.bulletin.form.label.critical" path="critical" />
	<acme:input-url code="administrator.bulletin.form.label.link" path="link" />
	
	<jstl:choose> 
		<jstl:when test="${_command == 'create'}">
			<acme:input-checkbox code="administrator.bulletin.form.label.confirmation" path="confirmation"/>
			<acme:submit code="administrator.bulletin.form.button.create" action="/administrator/bulletin/create" />
		</jstl:when>
	</jstl:choose>
</acme:form>