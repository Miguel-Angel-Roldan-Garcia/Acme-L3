
package acme.features.company.dashboard;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.individual.companies.Practicum;
import acme.forms.individual.companies.CompanyDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Controller
public class CompanyDashboardShowService extends AbstractService<Company, CompanyDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CompanyDashboard dashboard;
		Double averagePractica;
		final Double deviationPractica;
		final Integer minimumPractica;
		final Integer maximumPractica;
		final Double averageSession;
		final Double deviationSession;
		final Double minimumSession;
		final Double maximumSession;
		final List<Integer> totalPracticum;
		final List<Integer> totalTheoreticalPracticumLastYear = new ArrayList<>(Collections.nCopies(12, 0));
		;
		final List<Integer> totalHandsOnPracticumLastYear = new ArrayList<>(Collections.nCopies(12, 0));

		Integer companyId;
		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		averagePractica = this.repository.averageDurationPractica(companyId);
		deviationPractica = this.repository.deviationDuractionPractica(companyId);
		minimumPractica = this.repository.minDurationPractica(companyId);
		maximumPractica = this.repository.maxDurationPractica(companyId);
		averageSession = this.repository.averageDurationPracticumSession(companyId);
		deviationSession = this.repository.deviationDurationPracticumSession(companyId);
		minimumSession = this.repository.minDurationPracticumSession(companyId);
		maximumSession = this.repository.maxDurationPracticumSession(companyId);

		final Date instant = MomentHelper.getCurrentMoment();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(instant);
		calendar.add(Calendar.YEAR, -1);
		final Date oneYearAgo = calendar.getTime();

		final List<Practicum> practicumLastYear = this.repository.practicumLastYear(companyId, oneYearAgo, instant);

		for (final Practicum p : practicumLastYear) {
			calendar.setTime(p.getCreationDate());
			final int month = calendar.get(Calendar.MONTH);
			if (p.getCourse().getCourseType() == Nature.HANDS_ON) {
				final int number = totalHandsOnPracticumLastYear.get(month - 1);
				totalHandsOnPracticumLastYear.add(month - 1, number + 1);
			} else {
				final int number = totalTheoreticalPracticumLastYear.get(month - 1);
				totalTheoreticalPracticumLastYear.add(month - 1, number + 1);
			}
		}

		dashboard = new CompanyDashboard();
		dashboard.setAveragePractica(averagePractica);
		dashboard.setDeviationPractica(deviationPractica);
		dashboard.setMinimumPractica(minimumPractica);
		dashboard.setMaximumPractica(maximumPractica);
		dashboard.setAverageSession(averageSession);
		dashboard.setDeviationSession(deviationSession);
		dashboard.setMinimumSession(minimumSession);
		dashboard.setMaximumSession(maximumSession);
		dashboard.setHandsOnPracticum(totalHandsOnPracticumLastYear);
		dashboard.setTheoreticalPracticum(totalTheoreticalPracticumLastYear);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final CompanyDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"averagePractica", "deviationPractica", // 
			"minimumPractica", "maximumPractica", "averageSession", "deviationSession", "minimumSession", "maximumSession", "handsOnPracticum", "theoreticalPracticum");

		super.getResponse().setData(tuple);
	}

}
