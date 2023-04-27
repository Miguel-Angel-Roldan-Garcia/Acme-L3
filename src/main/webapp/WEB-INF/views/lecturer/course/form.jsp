
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

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
			<jstl:if test="${!publishable && draftMode}">
			<b style="color:#f95"><acme:message code="lecturer.course.form.error.not-publishable"/></b>
			</jstl:if>
<acme:form readonly="${!draftMode}"> 
	<acme:input-textbox code="lecturer.course.form.label.code" path="code"/>
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.course.form.label.abstract$" path="abstract$"/>
	<jstl:if test="${!(_command == 'create')}">
		<acme:input-textarea code="lecturer.course.form.label.nature" path="nature" readonly="true"/>
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
			<acme:button code="lecturer.course.form.button.lectures-in-course" action="/lecturer/lecture/list-mine?courseId=${id}"/>			
			<acme:button
	code="lecturer.course.form.button.change-lectures"
	action="/lecturer/course-lecture/list-mine?courseId=${id}" />
			<acme:button
	code="lecturer.course.form.button.add-lectures-to-course"
	action="/lecturer/course-lecture/create?courseId=${id}" />

			<jstl:if test="${publishable}">
			<acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish"/>
			</jstl:if>

		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create?courseId=G139"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
