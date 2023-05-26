

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.enrolment.list.label.code" path="code" width="10%"/>
	<acme:list-column code="student.enrolment.list.label.motivation" path="motivation" width="10%"/>
	<acme:list-column code="student.enrolment.list.label.goals" path="goals" width="80%"/>
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="student.enrolment.list.button.create" action="/student/enrolment/create"/>
</jstl:if>		