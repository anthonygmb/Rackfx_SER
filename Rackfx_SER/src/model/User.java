package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = -1615086916724785606L;
	private StringProperty login;
	private StringProperty mot_de_passe;
	private StringProperty droit_auth;

	public User() {
		this(null, null, null);
	}

	public User(String login, String mot_de_passe, String droit_auth) {
		this.login = new SimpleStringProperty(login);
		this.mot_de_passe = new SimpleStringProperty(mot_de_passe);
		this.droit_auth = new SimpleStringProperty(droit_auth);
	}

	// =================================================================================================
	public String getLogin() {
		return login.get();
	}

	public void setLogin(String login) {
		this.login.set(login);
	}

	public StringProperty loginProperty() {
		return login;
	}

	// =================================================================================================
	public String getMot_de_passe() {
		return mot_de_passe.get();
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe.set(mot_de_passe);
	}

	public StringProperty mot_de_passeProperty() {
		return mot_de_passe;
	}

	// =================================================================================================
	public String getDroit_auth() {
		return droit_auth.get();
	}

	public void setDroit_auth(String droit_auth) {
		this.droit_auth.set(droit_auth);
	}

	public StringProperty droit_authProperty() {
		return droit_auth;
	}

	// =================================================================================================
	@Override
	public void validateObject() throws InvalidObjectException {
		if (this.login == null) {
			throw new InvalidObjectException("Le champ login ne doit pas être vide");
		} else if (this.mot_de_passe == null) {
			throw new InvalidObjectException("Le champ mot de passe ne doit pas être vide");
		}
	}
}
