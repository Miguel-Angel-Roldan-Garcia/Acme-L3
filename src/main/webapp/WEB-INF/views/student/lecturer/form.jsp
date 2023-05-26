

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="true">
	<acme:input-textbox code="student.lecturer.form.label.alma-mater" path="almaMater"/>
	<acme:input-textarea code="student.lecturer.form.label.resume" path="resume"/>
	<acme:input-textarea code="student.lecturer.form.label.qualifications" path="qualifications"/>
	<acme:input-url code="student.lecturer.form.label.link" path="link"/>
</acme:form>