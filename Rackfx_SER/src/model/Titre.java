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
	private StringProperty titre;
	private StringProperty annee;
	private ObjectProperty<Time> duree;
	private String genre;
	private boolean reprise_titre;
	private String auteur;
	private Groupe groupe;

	public Titre() {
		this(null, null, null, null, false, null);
	}

	public Titre(String titre, String annee, Time duree, String genre, boolean reprise_titre, String auteur) {
		this.titre = new SimpleStringProperty(titre);
		this.annee = new SimpleStringProperty(annee);
		this.duree = new SimpleObjectProperty<Time>(duree);
		this.genre = genre;
		this.reprise_titre = reprise_titre;
		this.auteur = auteur;
	}

	// =================================================================================================
	public String getTitre() {
		return titre.get();
	}

	public void setTitre(String titre) {
		this.titre.set(titre);
	}

	public StringProperty titreProperty() {
		return titre;
	}

	// =================================================================================================
	public String getAnnee() {
		return annee.get();
	}

	public void setAnnee(String annee) {
		this.annee.set(annee);
	}

	public StringProperty anneeProperty() {
		return annee;
	}

	// =================================================================================================
	public Time getDuree() {
		return duree.get();
	}

	public void setDuree(Time duree) {
		this.duree.set(duree);
	}

	public ObjectProperty<Time> dureeProperty() {
		return duree;
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
	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	// =================================================================================================
	@Override
	public void validateObject() throws InvalidObjectException {
		if (this.titre == null) {
			throw new InvalidObjectException("Le champ titre ne doit pas être vide");
		} else if (this.duree == null) {
			throw new InvalidObjectException("Le champ durée ne doit pas être vide");
		}
	}
}
