package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;
import java.sql.Time;

import controller.MainApp;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Representation implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = -3784241360835211682L;
	private Time heure_debut;
	private Time heure_fin;
	private String nom_groupe;
	private String nom_titre;
	private transient ObjectProperty<Time> heure_debutProp;
	private transient ObjectProperty<Time> heure_finProp;
	private transient StringProperty nom_groupeProp;
	private transient StringProperty nom_titreProp;

	// =================================================================================================
	public Time getHeure_debut() {
		return heure_debut;
	}

	public void setHeure_debut(Time heure_debut) {
		this.heure_debut = heure_debut;
		this.heure_debutProp = new SimpleObjectProperty<Time>(heure_debut);
	}

	public ObjectProperty<Time> heure_debutProperty() {
		return heure_debutProp;
	}

	// =================================================================================================
	public Time getHeure_fin() {
		return heure_fin;
	}

	public void setHeure_fin(Time heure_fin) {
		this.heure_fin = heure_fin;
		this.heure_finProp = new SimpleObjectProperty<Time>(heure_fin);
	}

	public ObjectProperty<Time> heure_fintProperty() {
		return heure_finProp;
	}

	// =================================================================================================
	public String getNom_Groupe() {
		return nom_groupe;
	}

	public void setNom_Groupe(String nom_groupe) {
		this.nom_groupe = nom_groupe;
		this.nom_groupeProp = new SimpleStringProperty(nom_groupe);
	}

	public StringProperty nom_groupeProperty() {
		return nom_groupeProp;
	}

	// =================================================================================================
	public String getNom_Titre() {
		return nom_titre;
	}

	public void setNom_Titre(String nom_titre) {
		this.nom_titre = nom_titre;
		this.nom_titreProp = new SimpleStringProperty(nom_titre);
	}

	public StringProperty nom_titreProperty() {
		return nom_titreProp;
	}

	// =================================================================================================
	@Override
	public void validateObject() throws InvalidObjectException {
		if (this.heure_debut == null) {
			throw new InvalidObjectException("Le champ heure de début ne doit pas être vide");
		} else if (this.heure_fin == null) {
			throw new InvalidObjectException("Le champ heure de fin ne doit pas être vide");
		} else if (this.nom_groupe == null || this.nom_groupe.length() == 0) {
			throw new InvalidObjectException("Le champ groupe ne doit pas être vide");
		} else if (this.nom_titre == null || this.nom_titre.length() == 0) {
			throw new InvalidObjectException("Le champ titre ne doit pas être vide");
		} else if (this.heure_debut.after(this.heure_fin) || this.heure_debut.equals(this.heure_fin)) {
			throw new InvalidObjectException(
					MainApp.getInstance().Lang_bundle.getString("L'heure.de.debut.doit.etre.avant.l'heure.de.fin"));
		}
	}
}
