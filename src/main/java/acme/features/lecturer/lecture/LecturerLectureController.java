/*
 * EmployerDutyController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.lecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.individual.lectures.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerLectureController extends AbstractController<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

//	@Autowired
//	protected LecturerLectureListService	listService;
//
//	@Autowired
//	protected LecturerLectureShowService	showService;
//
//	@Autowired
//	protected LecturerLectureCreateService	createService;
//
//	@Autowired
//	protected LecturerLectureUpdateService	updateService;
//
//	@Autowired
//	protected LecturerLectureDeleteService	deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
//		super.addBasicCommand("list", this.listService);
//		super.addBasicCommand("show", this.showService);
//		super.addBasicCommand("create", this.createService);
//		super.addBasicCommand("update", this.updateService);
//		super.addBasicCommand("delete", this.deleteService);
	}

}
