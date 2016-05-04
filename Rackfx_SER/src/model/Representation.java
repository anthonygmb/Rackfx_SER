package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;
import java.sql.Time;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Representation implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = -3784241360835211682L;
	private ObjectProperty<Time> heure_debut;
	private ObjectProperty<Time> heure_fin;
	private StringProperty nom_groupe;
	private StringProperty nom_titre;
	private Rencontre rencontre;
	private Groupe groupe;

	public Representation() {
		this(null, null, null, null);
	}

	public Representation(String nom_groupe, String nom_titre, Time heure_debut, Time heure_fin) {
		this.nom_groupe = new SimpleStringProperty(nom_groupe);
		this.nom_titre = new SimpleStringProperty(nom_titre);
		this.heure_debut = new SimpleObjectProperty<Time>(heure_debut);
		this.heure_fin = new SimpleObjectProperty<Time>(heure_fin);
	}

	// =================================================================================================
	public Time getHeure_debut() {
		return heure_debut.get();
	}

	public void setHeure_debut(Time heure_debut) {
		this.heure_debut.set(heure_debut);
	}

	public ObjectProperty<Time> heure_debutProperty() {
		return heure_debut;
	}

	// =================================================================================================
	public Time getHeure_fin() {
		return heure_fin.get();
	}

	public void setHeure_fin(Time heure_fin) {
		this.heure_fin.set(heure_fin);
	}

	public ObjectProperty<Time> heure_fintProperty() {
		return heure_fin;
	}

	// =================================================================================================
	public String getNom_Groupe() {
		return nom_groupe.get();
	}

	public void setNom_Groupe(String nom_groupe) {
		this.nom_groupe.set(nom_groupe);
	}

	public StringProperty nom_groupeProperty() {
		return nom_groupe;
	}

	// =================================================================================================
	public String getNom_Titre() {
		return nom_titre.get();
	}

	public void setNom_Titre(String nom_titre) {
		this.nom_titre.set(nom_titre);
	}

	public StringProperty nom_titreProperty() {
		return nom_titre;
	}

	// =================================================================================================
	public Rencontre getRencontre() {
		return rencontre;
	}

	public void setRencontre(Rencontre rencontre) {
		this.rencontre = rencontre;
	}

	// =================================================================================================
	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	// =================================================================================================
	@Override
	public void validateObject() throws InvalidObjectException {
		if (this.heure_debut == null) {
			throw new InvalidObjectException("Le champ heure de début ne doit pas être vide");
		} else if (this.heure_fin == null) {
			throw new InvalidObjectException("Le champ heure de fin ne doit pas être vide");
		} else if (this.nom_groupe == null) {
			throw new InvalidObjectException("Le champ groupe ne doit pas être vide");
		} else if (this.nom_titre == null) {
			throw new InvalidObjectException("Le champ titre ne doit pas être vide");
		}
	}
}
