<%--
- moneyExchangeBox.tag
-
- Copyright (C) 2022-2023 Miguel Ángel Roldán.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>


<%@tag language="java" body-content="empty"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<%@ attribute name="moneyExchange" required="true" type="acme.forms.group.MoneyExchange"%>

<jstl:choose>
	<jstl:when test="${moneyExchange != null}">
		<table class="table table-striped table-condensed table-hover nowrap w-100">
			<thead>
				<tr>
					<th style="width: 25%">
						<acme:message code="money-exchange.list.source"/>
					</th>
					<th style="width: 25%">
						<acme:message code="money-exchange.list.targetCurrency"/>
					</th>
					<th style="width: 25%">
						<acme:message code="money-exchange.list.target"/>
					</th>
					<th style="width: 25%">
						<acme:message code="money-exchange.list.date"/>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width: 25%">
						<acme:print value="${moneyExchange.source}"/>
					</td>
					<td style="width: 25%">
						<acme:print value="${moneyExchange.targetCurrency}"/>
					</td>
					<td style="width: 25%">
						<acme:print value="${moneyExchange.target}"/>
					</td>
					<td style="width: 25%">
						<acme:print value="${moneyExchange.date}"/>
					</td>
				</tr>
			</tbody>
		</table>
	</jstl:when>
	<jstl:when test="${moneyExchange == null && _command == 'show'}">
		<acme:message code="money-exchange.error.failed"/>
	</jstl:when>
</jstl:choose>