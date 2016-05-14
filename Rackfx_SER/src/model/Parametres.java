package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;

public class Parametres implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = -6316722996286908854L;
	private String langue;
	private String theme;

	// =================================================================================================
	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	// =================================================================================================
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	// =================================================================================================
	@Override
	public void validateObject() throws InvalidObjectException {
		if (this.langue == null || this.langue.length() == 0) {
			throw new InvalidObjectException("Le champ langue ne doit pas être vide");
		} else if (this.theme == null || this.theme.length() == 0) {
			throw new InvalidObjectException("Le champ theme ne doit pas être vide");
		}
	}
}
