

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	
	<acme:list-column code="lecturer.course-lecture.list.label.course-code"
		path="courseCode" width="10%" />
	<acme:list-column code="lecturer.course-lecture.list.label.lecture-code"
		path="lectureCode" width="10%" />
	<acme:list-column code="lecturer.course-lecture.list.label.lecture-title"
		path="lectureTitle" width="40%" />
	<acme:list-column code="lecturer.course-lecture.list.label.lecture-nature"
		path="lectureNature" width="30%" />
	<acme:list-column code="lecturer.course-lecture.list.label.lecture-published"
		path="lecturePublished" width="10%" />

</acme:list>


			<acme:button
	code="lecturer.course.form.button.add-lectures-to-course"
	action="/lecturer/course-lecture/create?courseId=${param.courseId}" />

