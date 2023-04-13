package acme.features.students.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.students.Activity;
import acme.entities.individual.students.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

    @Query("select enrolment from Enrolment enrolment where enrolment.id = :id")
    Enrolment findOneEnrolmentById(int id);

    @Query("SELECT activity from Activity activity where activity.enrolment.id =:masterId")
    Collection<Activity> findManyActivitiesByEnrolmentId(int masterId);

    @Query("select activity.enrolment from Activity activity where activity.id = :activityId")
    Enrolment findOneEnrolmentByActivityId(int activityId);

    @Query("select activity from Activity activity where activity.id = :id")
    Activity findOneActivityById(int id);

}
