
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="${!draftMode}"> 
	<acme:input-textbox code="lecturer.course.form.label.code" path="code"/>
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textbox code="lecturer.course.form.label.abstract$" path="abstract$"/>
	<jstl:if test="${!(_command == 'create')}">
		<acme:input-textbox code="lecturer.course.form.label.nature" path="nature" readonly="true"/>
	</jstl:if>
	<acme:input-url code="lecturer.course.form.label.link" path="link"/>
	<acme:input-money code="lecturer.course.form.label.retail-price" path="retailPrice"/>
	
		<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="lecturer.course.form.button.lectures-in-course" action="/lecturer/lecture/list-mine?courseId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
			<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
			<br>
			<br>
			
			<acme:button
	code="lecturer.course.form.button.lectures-in-course"
	action="/lecturer/course-lecture/list-mine?courseId=${id}" />


			<jstl:if test="${publishable}">
			<acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish"/>
			</jstl:if>

		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create?courseId=G139"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
