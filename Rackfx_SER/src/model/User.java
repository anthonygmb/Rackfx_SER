package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = -1615086916724785606L;
	private String login;
	private String mot_de_passe;
	private String droit_auth;
	private transient StringProperty loginProp;
	private transient StringProperty mot_de_passeProp;
	private transient StringProperty droit_authProp;

	public User() {
		this(null, null, null);
	}

	public User(String login, String mot_de_passe, String droit_auth) {
		this.login = login;
		this.mot_de_passe = mot_de_passe;
		this.droit_auth = droit_auth;
	}

	// =================================================================================================
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
		this.loginProp = new SimpleStringProperty(login);
	}

	public StringProperty loginProperty() {
		return loginProp;
	}

	// =================================================================================================
	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
		this.mot_de_passeProp = new SimpleStringProperty(mot_de_passe);
	}

	public StringProperty mot_de_passeProperty() {
		return mot_de_passeProp;
	}

	// =================================================================================================
	public String getDroit_auth() {
		return droit_auth;
	}

	public void setDroit_auth(String droit_auth) {
		this.droit_auth = droit_auth;
		this.droit_authProp = new SimpleStringProperty(droit_auth);
	}

	public StringProperty droit_authProperty() {
		return droit_authProp;
	}

	// =================================================================================================
	@Override
	public void validateObject() throws InvalidObjectException {
		if (this.login == null || this.login.length() == 0) {
			throw new InvalidObjectException("Le champ login ne doit pas être vide");
		} else if (this.mot_de_passe == null || this.mot_de_passe.length() == 0) {
			throw new InvalidObjectException("Le champ mot de passe ne doit pas être vide");
		}
	}
}
