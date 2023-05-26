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
	<acme:input-textbox code="auditor.audit.form.label.code" path="code"/>
	<acme:input-textarea code="auditor.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textarea code="auditor.audit.form.label.strongPoints" path="strongPoints"/>
	<acme:input-textarea code="auditor.audit.form.label.weakPoints" path="weakPoints"/>
	<acme:input-textbox code="auditor.audit.form.label.course" path="courseCode"/>
	<jstl:if test="${_command != 'create' && marks != null}">
		<acme:input-textbox code="auditor.audit.label.marks" path="marks" readonly="true"/>
	</jstl:if>
	
	<acme:button code="auditor.audit.form.button.auditor" action="/authenticated/auditor/show?username=${auditor.userAccount.username}"/>
</acme:form>