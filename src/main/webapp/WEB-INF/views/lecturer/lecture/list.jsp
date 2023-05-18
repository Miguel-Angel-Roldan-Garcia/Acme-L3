
<%@page language="java"%>5

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.code"
		path="code" width="10%" />
	<acme:list-column code="lecturer.lecture.list.label.title" path="title"
		width="50%" />
	<acme:list-column code="lecturer.lecture.list.label.nature"
		path="nature" width="20%" />
	<acme:list-column
		code="lecturer.lecture.list.label.estimated-learning-time"
		path="estimatedLearningTime" width="20%" />
	<acme:list-column
		code="lecturer.lecture.list.label.published"
		path="published" width="10%" />
</acme:list>

<jstl:if test="${!(param.courseId!=null)}">
<acme:button code="lecturer.lecture.list.button.create"
	action="/lecturer/lecture/create" />
</jstl:if>

