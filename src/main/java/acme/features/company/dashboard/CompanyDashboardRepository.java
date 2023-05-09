
package acme.features.company.dashboard;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.companies.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.company.id = :id and p.creationDate BETWEEN :oneYearAgo AND :current ")
	List<Practicum> practicumLastYear(int id, Date oneYearAgo, Date current);

	//	@Query("select avg(p.estimatedTime) from Practicum p where p.company.id = :id")
	//	Double averageDurationPractica(int id);
	//
	//	@Query("SELECT STDDEV(p.estimatedTime) FROM Practicum p where p.company.id = :id")
	//	Double deviationDuractionPractica(int id);
	//
	//	@Query("select min(p.estimatedTime) from Practicum p where p.company.id =:id")
	//	Integer minDurationPractica(int id);
	//
	//	@Query("select max(p.estimatedTime) from Practicum p where p.company.id = :id")
	//	Integer maxDurationPractica(int id);
	//
	//	@Query("SELECT AVG(EXTRACT(DAY FROM ps.endDate) - EXTRACT(DAY FROM ps.startDate)) FROM PracticumSession ps WHERE ps.practicum.company.id = :id")
	//	Double averageDurationPracticumSession(int id);
	//
	//	@Query("SELECT STDDEV(EXTRACT(DAY FROM ps.endDate) - EXTRACT(DAY FROM ps.startDate)) FROM PracticumSession ps WHERE ps.practicum.company.id = :id")
	//	Double deviationDurationPracticumSession(int id);
	//
	//	@Query("SELECT max(EXTRACT(DAY FROM ps.endDate) - EXTRACT(DAY FROM ps.startDate)) FROM PracticumSession ps WHERE ps.practicum.company.id = :id")
	//	Double maxDurationPracticumSession(int id);
	//
	//	@Query("SELECT min(EXTRACT(DAY FROM ps.endDate) - EXTRACT(DAY FROM ps.startDate)) FROM PracticumSession ps WHERE ps.practicum.company.id = :id")
	//	Double minDurationPracticumSession(int id);

}
