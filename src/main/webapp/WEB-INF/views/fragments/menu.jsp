<%--
- menu.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.roles.Provider,acme.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
	
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link" action="https://www.example.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-by-student-1" action="https://www.amazon.es/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-by-student-2" action="https://www.youtube.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-by-student-3" action="https://ev.us.es/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-by-student-4" action="https://www.instagram.com"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-by-student-5" action="https://www.moxfield.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.create-bulletin" action="/administrator/bulletin/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.system-configuration" action="/administrator/system-configuration/show"/>
			<acme:menu-suboption code="master.menu.administrator.banner" action="/administrator/banner/list-all"/>
			<acme:menu-suboption code="master.menu.administrator.offer" action="/administrator/offer/list-all"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.any" access="true">
			<acme:menu-suboption code="master.menu.any.published-courses" action="/any/course/list"/>
			<acme:menu-suboption code="master.menu.any.published-peeps" action="/any/peep/list"/>
		</acme:menu-option>
	
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.student.offers.list" action="/authenticated/offer/list"/>
			<acme:menu-suboption code="master.menu.authenticated.bulletin.list" action="/authenticated/bulletin/list"/>
			<acme:menu-suboption code="master.menu.authenticated.notes" action="/authenticated/note/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.assistant" access="hasRole('Assistant')">
			<acme:menu-suboption code="master.menu.assistant.list-mine-tutorials" action="/assistant/tutorial/list-mine"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.assistant.show-dashboard" action="/assistant/assistant-dashboard/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.company" access="hasRole('Company')">
			<acme:menu-suboption code="master.menu.company.list-mine-practicum" action="/company/practicum/list-mine"/>
		</acme:menu-option>
    
		<acme:menu-option code="master.menu.student" access="hasRole('Student')">
			<acme:menu-suboption code="master.menu.student.my-enrolments" action="/student/enrolment/list-mine"/>
			<acme:menu-suboption code="master.menu.student.enrolled-courses" action="/student/course/list"/>
			<acme:menu-suboption code="master.menu.student.show-dashboard" action="/student/student-dashboard/show"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.lecturer" access="hasRole('Lecturer')">
			<acme:menu-suboption code="master.menu.lecturer.my-lectures" action="/lecturer/lecture/list-mine"/>
			<acme:menu-suboption code="master.menu.lecturer.my-courses"  action="/lecturer/course/list-mine"/>	
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.lecturer.show-dashboard" action="/lecturer/lecturer-dashboard/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.list-mine-audits" action="/auditor/audit/list-mine"/>
		</acme:menu-option>
		
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-assistant" action="/authenticated/assistant/create" access="!hasRole('Assistant')"/>
			<acme:menu-suboption code="master.menu.user-account.assistant" action="/authenticated/assistant/update" access="hasRole('Assistant')"/>
			<acme:menu-suboption code="master.menu.user-account.become-student" action="/authenticated/student/create" access="!hasRole('Student')"/>
			<acme:menu-suboption code="master.menu.user-account.student" action="/authenticated/student/update" access="hasRole('Student')"/>
			<acme:menu-suboption code="master.menu.user-account.become-lecturer" action="/authenticated/lecturer/create" access="!hasRole('Lecturer')"/>
			<acme:menu-suboption code="master.menu.user-account.lecturer" action="/authenticated/lecturer/update" access="hasRole('Lecturer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-company" action="/authenticated/company/create" access="!hasRole('Company')"/>
			<acme:menu-suboption code="master.menu.user-account.company" action="/authenticated/company/update" access="hasRole('Company')"/>
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>
		</acme:menu-option>
		

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

