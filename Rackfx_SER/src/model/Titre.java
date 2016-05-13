package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;
import java.sql.Time;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Titre implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = 4638810562674152997L;
	private String titre;
	private String annee;
	private transient StringProperty titreProp;
	private transient StringProperty anneeProp;
	private Time duree;
	private transient ObjectProperty<Time> dureeProp;
	private String genre;
	private boolean reprise_titre;
	private String auteur;

	public Titre() {
		this(null, null, null, null, false, null);
	}

	public Titre(String titre, String annee, Time duree, String genre, boolean reprise_titre, String auteur) {
		this.titre = titre;
		this.annee = annee;
		this.duree = duree;
		this.genre = genre;
		this.reprise_titre = reprise_titre;
		this.auteur = auteur;
	}

	// =================================================================================================
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
		this.titreProp = new SimpleStringProperty(titre);
	}

	public StringProperty titreProperty() {
		return titreProp;
	}

	// =================================================================================================
	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
		this.anneeProp = new SimpleStringProperty(annee);
	}

	public StringProperty anneeProperty() {
		return anneeProp;
	}

	// =================================================================================================
	public Time getDuree() {
		return duree;
	}

	public void setDuree(Time duree) {
		this.duree = duree;
		this.dureeProp = new SimpleObjectProperty<Time>(duree);
	}

	public ObjectProperty<Time> dureeProperty() {
		return dureeProp;
	}

	// =================================================================================================
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	// =================================================================================================
	public boolean getReprise_titre() {
		return reprise_titre;
	}

	public void setReprise_titre(boolean reprise_titre) {
		this.reprise_titre = reprise_titre;
	}

	// =================================================================================================
	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	// =================================================================================================
	@Override
	public void validateObject() throws InvalidObjectException {
		if (this.titre == null || this.titre.length() == 0) {
			throw new InvalidObjectException("Le champ titre ne doit pas être vide");
		} else if (this.duree == null) {
			throw new InvalidObjectException("Le champ durée ne doit pas être vide");
		}
	}
}
