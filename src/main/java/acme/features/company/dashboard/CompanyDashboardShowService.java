
package acme.features.company.dashboard;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.datatypes.Statistic;
import acme.entities.individual.companies.Practicum;
import acme.entities.individual.companies.PracticumSession;
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
		final CompanyDashboard dashboard;
		final int companyId;
		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		final Collection<PracticumSession> practicumSessions = new ArrayList<>();
		final Collection<Practicum> practicum = this.repository.findAllPracticumByCompanyId(companyId);

		// PRACTICUM BY MONTH IN THE LAST YEAR

		Collection<PracticumSession> practicumSessionsByPracticumId;
		final int[] practicumCountByMonth = new int[12];

		final Date currentMoment = MomentHelper.getCurrentMoment();
		final Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentMoment);
		final int currentMonth = currentCalendar.get(Calendar.MONTH);
		final int currentYear = currentCalendar.get(Calendar.YEAR);
		final int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);

		for (final Practicum p : practicum) {
			practicumSessionsByPracticumId = this.repository.findAllPracticumSessionsByPracticumId(p.getId());
			practicumSessions.addAll(practicumSessionsByPracticumId); // Recopila todas las sesiones de practica
			final List<Boolean> counted = new ArrayList<>(Collections.nCopies(12, false));
			for (final PracticumSession ps : practicumSessionsByPracticumId) {

				final Date initialMoment = ps.getStartDate();
				final Calendar psInitialCalendar = Calendar.getInstance();
				psInitialCalendar.setTime(initialMoment);
				final int psInitialMonth = psInitialCalendar.get(Calendar.MONTH);
				final int psInitialYear = psInitialCalendar.get(Calendar.YEAR);
				final int psInitialDay = psInitialCalendar.get(Calendar.DAY_OF_MONTH);

				final Date endMoment = ps.getEndDate();
				final Calendar psEndCalendar = Calendar.getInstance();
				psEndCalendar.setTime(endMoment);
				final int psEndMonth = psEndCalendar.get(Calendar.MONTH);
				final int psEndYear = psEndCalendar.get(Calendar.YEAR);
				final int psEndDay = psEndCalendar.get(Calendar.DAY_OF_MONTH);

				if (currentYear == psInitialYear && currentYear == psEndYear) { // Inicio y fin en el año actual
					if (currentMonth == psInitialMonth && currentMonth == psEndMonth) { // Inicio y fin en el mes actual
						if (currentDay >= psInitialDay && currentDay >= psEndDay) // Inicio y fin previo al dia actual
							counted.set(11, true);
					} else if (currentMonth - 1 == psInitialMonth && currentMonth == psEndMonth) { // Inicio en el mes
						// anterior y fin en
						// el mes actual
						if (currentDay >= psEndDay) { // Día final previo al día actual
							counted.set(11, true);
							counted.set(10, true);
						}
					} else if (currentMonth > psEndMonth && psEndMonth != psInitialMonth) { // Inicio y fin en meses
						// distintos
						counted.set(psInitialMonth + 11 - currentMonth, true);
						counted.set(psEndMonth + 11 - currentMonth, true);
					} else if (currentMonth > psEndMonth && psInitialMonth == psEndMonth) // Inicio y fin en meses
						// iguales previos
						counted.set(psEndMonth + 11 - currentMonth, true);

				} else if (currentYear - 1 == psInitialYear && currentYear == psEndYear) { // Inicio en el año anterior
					// y fin en el actual
					counted.set(11 - currentMonth, true);
					counted.set(11 - currentMonth - 1, true);

				} else if (currentYear - 1 == psInitialYear && currentYear - 1 == psEndYear) // Inicio y fin en el año
					// anterior
					if (currentMonth < psInitialMonth && psEndMonth != psInitialMonth) { // Inicio y fin en meses
						// distintos y comprueba que la
						// sesión este en los últimos
						// 12 meses
						counted.set(psInitialMonth - currentMonth - 1, true);
						counted.set(psEndMonth - currentMonth - 1, true);
					} else if (currentMonth < psInitialMonth && psInitialMonth == psEndMonth) // Inicio y fin en meses
						// iguales previos y
						// comprueba que la sesión
						// este en los últimos 12
						// meses
						counted.set(psEndMonth - currentMonth - 1, true);
					else if (currentMonth < psEndMonth && psInitialMonth != psEndMonth) // Inicio y fin en meses
						// distintos para el caso del
						// último mes y el anterior
						counted.set(psEndMonth - currentMonth - 1, true);
			}

			for (int i = 0; i < 12; i++)
				if (counted.get(i).equals(true))
					practicumCountByMonth[i]++;

		}

		// PRACTICUM SESSION
		// -----------------------------------------------------------------------

		final int sessionNumber = (int) practicumSessions.stream().count();
		final Double sessionAverage = sessionNumber != 0 ? practicumSessions.stream().mapToDouble(session -> session.getDurationInHours()).sum() / sessionNumber : 0.;
		final Double sessionDeviation = sessionNumber != 0 ? Math.sqrt(practicumSessions.stream().mapToDouble(session -> Math.pow(session.getDurationInHours() - sessionAverage, 2)).sum() / sessionNumber) : 0.;
		final Double sessionMax = practicumSessions.stream().mapToDouble(session -> session.getDurationInHours()).max().orElse(0.0);
		final Double sessionMin = practicumSessions.stream().mapToDouble(session -> session.getDurationInHours()).min().orElse(0.0);

		Statistic practicumSessionsDuration;

		practicumSessionsDuration = new Statistic();
		practicumSessionsDuration.setCount(sessionNumber);
		practicumSessionsDuration.setAverageValue(sessionAverage);
		practicumSessionsDuration.setDeviationValue(sessionDeviation);
		practicumSessionsDuration.setMaxValue(sessionMax);
		practicumSessionsDuration.setMinValue(sessionMin);

		// PRACTICUM
		// ---------------------------------------------------------------------------------

		final int practicumNumber = (int) practicum.stream().count();

		Collection<PracticumSession> practicumDurationSessions;
		double estimatedTotalTime = 0.;
		int count = 0;
		double deviation = 0.;
		for (final Practicum p : practicum) {
			practicumDurationSessions = this.repository.findAllPracticumSessionsByPracticumId(p.getId());
			count++;
			for (final PracticumSession ps : practicumDurationSessions)
				estimatedTotalTime += ps.getDurationInHours();
		}

		final Double practicumAverage = count != 0 ? estimatedTotalTime / count : 0.;

		estimatedTotalTime = 0.;

		for (final Practicum p : practicum) {
			practicumDurationSessions = this.repository.findAllPracticumSessionsByPracticumId(p.getId());
			count++;
			for (final PracticumSession ps : practicumDurationSessions)
				estimatedTotalTime += ps.getDurationInHours();

			deviation += Math.pow(estimatedTotalTime - practicumAverage, 2);
		}

		final Double practicumDeviation = count != 0 ? Math.sqrt(deviation / count) : 0.;

		Double practicumMax = 0.;
		Double practicumMin = null;

		for (final Practicum p : practicum) {
			estimatedTotalTime = 0.;

			practicumDurationSessions = this.repository.findAllPracticumSessionsByPracticumId(p.getId());
			count++;
			for (final PracticumSession ps : practicumDurationSessions)
				estimatedTotalTime += ps.getDurationInHours();

			deviation += Math.pow(estimatedTotalTime - practicumAverage, 2);

			if (estimatedTotalTime > practicumMax)
				practicumMax = estimatedTotalTime;
			else if (practicumMin == null || estimatedTotalTime < practicumMin)
				practicumMin = estimatedTotalTime;
		}

		practicumMin = practicumMin != null ? practicumMin : 0.;

		Statistic practicumDuration;

		practicumDuration = new Statistic();
		practicumDuration.setCount(practicumNumber);
		practicumDuration.setAverageValue(practicumAverage);
		practicumDuration.setDeviationValue(practicumDeviation);
		practicumDuration.setMaxValue(practicumMax);
		practicumDuration.setMinValue(practicumMin);

		// ADDING INFO TO DASHBOARD OBJECT
		// ----------------------------------------------------------

		dashboard = new CompanyDashboard();
		dashboard.setPracticumCountByMonth(practicumCountByMonth);
		dashboard.setPracticumPeriodLenght(practicumDuration);
		dashboard.setPracticumSessionsPeriodLenght(practicumSessionsDuration);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final CompanyDashboard object) {
		final Tuple tuple;

		int maximumYScale = 0;
		final int[] practicumCountByMonth = object.getPracticumCountByMonth();

		for (int i = 0; i < practicumCountByMonth.length; i++)
			if (practicumCountByMonth[i] > maximumYScale)
				maximumYScale = practicumCountByMonth[i];

		tuple = super.unbind(object, //
			"practicumCountByMonth", "practicumPeriodLenght", //
			"practicumSessionsPeriodLenght");
		tuple.put("maximumYScale", maximumYScale + 1);

		final Date currentMoment = MomentHelper.getCurrentMoment();
		final Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentMoment);
		final int currentMonth = currentCalendar.get(Calendar.MONTH);
		final int currentYear = currentCalendar.get(Calendar.YEAR);
		final int previousYear = currentCalendar.get(Calendar.YEAR) - 1;
		final int[] months = new int[12];
		Integer yearOfTheMonth;

		for (int i = 11; i >= 0; i--) {
			months[i] = i - currentMonth + 1;
			if (months[i] >= 0) {
				yearOfTheMonth = currentYear;
				months[i]++;
			} else {
				yearOfTheMonth = previousYear;
				months[i] = months[i] + 13;
			}

			final String monthResulti = "month" + i;
			final String yearResulti = "year" + i;

			tuple.put(monthResulti, months[i]);
			tuple.put(yearResulti, yearOfTheMonth);

		}

		final String currentLocale = super.getRequest().getLocale().getDisplayLanguage();

		tuple.put("displayLenguage", currentLocale);

		super.getResponse().setData(tuple);
	}

}
