
package acme.forms.individual.auditors;

import java.util.Map;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;

public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	protected Map<Nature, Integer>	auditCount;

	protected Statistic				auditingRecordInAudit;

	protected Statistic				periodLengthInAuditingRecord;

}
