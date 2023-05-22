<%--
- form.jsp
-
- Copyright (C) 2022-2023 Javier Fernández Castillo.
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
	<acme:input-moment code="administrator.offer.form.label.instantiation-moment" path="instantiationMoment" readonly="true"/>
	<acme:input-moment code="administrator.offer.form.label.availability-period-start-date" path="availabilityPeriodStartDate"/>
	<acme:input-moment code="administrator.offer.form.label.availability-period-end-date" path="availabilityPeriodEndDate"/>
	<acme:input-textbox code="administrator.offer.form.label.heading" path="heading"/>
	<acme:input-textbox code="administrator.offer.form.label.summary" path="summary"/>
	<acme:input-money code="administrator.offer.form.label.price" path="price"/>
	<acme:input-url code="administrator.offer.form.label.link" path="link"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && !isBeingDisplayed && updateOrDelete}">
			<acme:submit code="administrator.offer.form.button.update" action="/administrator/offer/update"/>
			<acme:submit code="administrator.offer.form.button.delete" action="/administrator/offer/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.offer.form.button.create" action="/administrator/offer/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>
