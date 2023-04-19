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

<%@page language="java" import="acme.datatypes.Nature"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="student.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.tittle.activities-indicators"/>
		</th>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.average-time-period-activities"/>
		</th>
		<td>
			<acme:print value="${activitiesInEnrolments.getAverageValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.deviation-time-period-activities"/>
		</th>
		<td>
			<acme:print value="${activitiesInEnrolments.getDeviationValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.min-time-period-activities"/>
		</th>
		<td>
			<acme:print value="${activitiesInEnrolments.getMinValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.max-time-period-activities"/>
		</th>
		<td>
			<acme:print value="${activitiesInEnrolments.getMaxValue()}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.tittle.enrolments-indicators"/>
		</th>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.average-total-time-enrolments"/>
		</th>
		<td>
			<acme:print value="${enrolmentsInCourses.getAverageValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.deviation-total-time-enrolments"/>
		</th>
		<td>
			<acme:print value="${enrolmentsInCourses.getDeviationValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.min-total-time-enrolments"/>
		</th>
		<td>
			<acme:print value="${enrolmentsInCourses.getMinValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.max-total-time-enrolments"/>
		</th>
		<td>
			<acme:print value="${enrolmentsInCourses.getMaxValue()}"/>
		</td>
	</tr>
</table>

<h2>
	<acme:message code="student.dashboard.form.title.number-of-activities-by-nature"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"THEORETICAL", "HANDS_ON", "BALANCED"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${activityCount[Nature.THEORETICAL]}"/>,
						<jstl:out value="${activityCount[Nature.HANDS_ON]}"/>,
						<jstl:out value="${activityCount[Nature.BALANCED]}"/>
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
							suggestedMax : 1.0
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