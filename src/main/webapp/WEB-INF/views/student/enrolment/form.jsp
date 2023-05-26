

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="${draftMode == false }">
	<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
	<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courses}"/>
	<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
	<acme:input-textarea code="student.enrolment.form.label.goals" path="goals"/>
	<jstl:if test="${_command != 'create' && draftMode == true}">
		<acme:input-textbox code="student.enrolment.form.label.credit-card-number" path="creditCardNumber"/>
		<acme:input-textbox code="student.enrolment.form.label.expiry-date" path="expiryDate"/>
		<acme:input-textbox code="student.enrolment.form.label.cvc" path="cvc"/>
		<acme:input-textbox code="student.enrolment.form.label.finish-holder-name" path="holderName"/>
		<acme:input-checkbox code="student.enrolment.form.label.confirm" path="confirmed"/>
	</jstl:if>
	<jstl:if test="${_command != 'create' && draftMode == false }">
		<acme:input-textbox code="student.enrolment.form.label.holder-name" path="holderName"/>
		<acme:input-textbox code="student.enrolment.form.label.lower-nibble" path="lowerNibble"/>
	</jstl:if>
	
		
	
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">	
		<%-- AQUI IRÍA EL BOTÓN PARA  VER LAS ACTIVIDADES --%>
			<acme:input-double readonly="true" code="student.enrolment.form.label.work-time" path="workTime"/>
			<acme:button code="student.enrolment.form.button.activities" action="/student/activity/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${(_command == 'show' || _command == 'update' || _command == 'delete' || _command == 'finalise ') && draftMode == true}">
			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
		<acme:submit code="student.enrolment.form.button.create" action="/student/enrolment/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>

