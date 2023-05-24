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
<h2>${x.maxValue}</h2>
<h2>
	<acme:message code="company.dashboard.form.title.practicum"/>
</h2>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum.count"/>
		</th>
		<td>
			<acme:print value="${practicumPeriodLenght.count}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum.average"/>
		</th>
		<td>
			<acme:print value="${practicumPeriodLenght.averageValue}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum.deviation"/>
		</th>
		<td>
			<acme:print value="${practicumPeriodLenght.deviationValue}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum.min"/>
		</th>
		<td>
			<acme:print value="${practicumPeriodLenght.minValue}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum.max"/>
		</th>
		<td>
			<acme:print value="${practicumPeriodLenght.maxValue}"/>
		</td>
	</tr>	
</table>
<h2>
	<acme:message code="company.dashboard.form.title.practicum-session"/>
</h2>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-session.count"/>
		</th>
		<td>
			<acme:print value="${practicumSessionsPeriodLenght.count}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-session.average"/>
		</th>
		<td>
			<acme:print value="${practicumSessionsPeriodLenght.averageValue}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-session.deviation"/>
		</th>
		<td>
			<acme:print value="${practicumSessionsPeriodLenght.deviationValue}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-session.min"/>
		</th>
		<td>
			<acme:print value="${practicumSessionsPeriodLenght.minValue}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-session.max"/>
		</th>
		<td>
			<acme:print value="${practicumSessionsPeriodLenght.maxValue}"/>
		</td>
	</tr>	
</table>


<h2>
	<acme:message code="company.dashboard.form.title.practicum-by-month-in-last-year"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
				<jstl:choose>
					<jstl:when test="${displayLenguage == 'Spanish'}">
						"${month0} / ${year0}",
						"${month1} / ${year1}",
						"${month2} / ${year2}",
						"${month3} / ${year3}",
						"${month4} / ${year4}",
						"${month5} / ${year5}",
						"${month6} / ${year6}",
						"${month7} / ${year7}",
						"${month8} / ${year8}",
						"${month9} / ${year9}",
						"${month10} / ${year10}",
						"${month11} / ${year11}",
					</jstl:when>
					<jstl:when test="${displayLenguage == 'English'}">
						"${year0} / ${month0}",
						"${year1} / ${month1}",
						"${year2} / ${month2}",
						"${year3} / ${month3}",
						"${year4} / ${month4}",
						"${year5} / ${month5}",
						"${year6} / ${month6}",
						"${year7} / ${month7}",
						"${year8} / ${month8}",
						"${year9} / ${month9}",
						"${year10} / ${month10}",
						"${year11} / ${month11}",
					</jstl:when>
				</jstl:choose>
			],
			datasets : [
				{
					backgroundColor: "rgba(76, 153, 0, 0.5)",
					data : [
						<jstl:out value="${practicumCountByMonth[0]}"/>,
						<jstl:out value="${practicumCountByMonth[1]}"/>,
						<jstl:out value="${practicumCountByMonth[2]}"/>,
						<jstl:out value="${practicumCountByMonth[3]}"/>,
						<jstl:out value="${practicumCountByMonth[4]}"/>,
						<jstl:out value="${practicumCountByMonth[5]}"/>,
						<jstl:out value="${practicumCountByMonth[6]}"/>,
						<jstl:out value="${practicumCountByMonth[7]}"/>,
						<jstl:out value="${practicumCountByMonth[8]}"/>,
						<jstl:out value="${practicumCountByMonth[9]}"/>,
						<jstl:out value="${practicumCountByMonth[10]}"/>,
						<jstl:out value="${practicumCountByMonth[11]}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : <jstl:out value="${maximumYScale}"/>
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>

