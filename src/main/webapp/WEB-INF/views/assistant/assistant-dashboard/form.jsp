<%--
- form.jsp
-
- Copyright (C) 2022-2023 Miguel Ángel Roldán.
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
	<acme:message code="assistant.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.tittle.session-indicators"/>
		</th>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.average-time-sessions"/>
		</th>
		<td>
			<acme:print value="${sessionTimeStatistics.getAverageValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.deviation-time-sessions"/>
		</th>
		<td>
			<acme:print value="${sessionTimeStatistics.getDeviationValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.min-time-sessions"/>
		</th>
		<td>
			<acme:print value="${sessionTimeStatistics.getMinValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.max-time-sessions"/>
		</th>
		<td>
			<acme:print value="${sessionTimeStatistics.getMaxValue()}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.tittle.tutorial-indicators"/>
		</th>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.average-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${tutorialTimeStatistics.getAverageValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.deviation-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${tutorialTimeStatistics.getDeviationValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.min-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${tutorialTimeStatistics.getMinValue()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.max-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${tutorialTimeStatistics.getMaxValue()}"/>
		</td>
	</tr>
</table>

<h2>
	<acme:message code="assistant.dashboard.form.title.number-of-tutorials-by-nature"/>
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
						<jstl:out value="${tutorialCount[Nature.THEORETICAL]}"/>,
						<jstl:out value="${tutorialCount[Nature.HANDS_ON]}"/>,
						<jstl:out value="${tutorialCount[Nature.BALANCED]}"/>
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
							// suggestedMax : 1.0
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

