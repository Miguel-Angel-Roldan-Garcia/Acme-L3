<%--
- list.jsp
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

<acme:list>
	<acme:list-column code="autheticated.note.list.label.title" path="title" width="10%"/>
	<acme:list-column code="authenticated.note.list.label.author" path="author" width="10%"/>
	<acme:list-column code="authenticated.note.list.label.instantiationMoment" path="instantiationMoment" width="80%"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="authenticated.note.list.button.create" action="/authenticated/note/create"/>
</jstl:if>