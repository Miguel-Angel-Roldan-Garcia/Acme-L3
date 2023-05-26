

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.activity.list.label.title" path="title" width="10%"/>
	<acme:list-column code="student.activity.list.label.link" path="link" width="10%"/>
	<acme:list-column code="student.activity.list.label.nature" path="nature" width="80%"/>
	<%-- LO LÓGICO AQUI SERÍA DE QUE SE MOSTRASE TAMBIÉN AQUI EL PERIODO DE TIEMPO --%>
</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="student.activity.list.button.create" action="/student/activity/create?masterId=${masterId}"/>
</jstl:if>