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
	<acme:input-textbox code="auditor.auditing-record.form.label.subject" path="subject"/>
	<acme:input-textbox code="auditor.auditing-record.form.label.assessment" path="assessment"/>
	<acme:input-moment code="auditor.auditing-record.form.label.startDate" path="startDate"/>
	<acme:input-moment code="auditor.auditing-record.form.label.finishDate" path="finishDate"/>
	<acme:input-select code="auditor.auditing-record.form.label.mark" path="mark" choices="${marks}"/>
	<acme:input-url code="auditor.auditing-record.form.label.link" path="link"/>

	<jstl:if test="${_command == 'correct'}">
		<acme:input-checkbox code="auditor.auditing-record.label.confirm" path="confirm"/>
	</jstl:if>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="auditor.auditing-record.form.button.update" action="/auditor/auditing-record/update"/>
			<acme:submit code="auditor.auditing-record.form.button.delete" action="/auditor/auditing-record/delete"/>
			<acme:submit code="auditor.auditing-record.form.button.publish" action="/auditor/auditing-record/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditing-record.form.button.create" action="/auditor/auditing-record/create?masterId=${masterId}"/>
		</jstl:when>		
	<jstl:when test="${_command == 'correct'}">
			<acme:submit code="auditor.auditing-record.form.button.correct" action="/auditor/auditing-record/correct?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
