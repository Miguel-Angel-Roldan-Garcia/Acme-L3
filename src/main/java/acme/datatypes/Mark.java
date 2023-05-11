
package acme.datatypes;

public enum Mark {

	APLUS("A+"), A("A"), B("B"), C("C"), F("F"), FMINUS("F-");


	private String mark;


	private Mark(final String mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return this.mark;
	}

	public static Mark transform(final String c) {
		for (final Mark m : Mark.values())
			if (m.toString().equals(c))
				return m;
		return null;
	}

}
