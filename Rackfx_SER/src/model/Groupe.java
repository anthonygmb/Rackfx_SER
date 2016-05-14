package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Groupe implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = 1L;
	private String nom_groupe;
	private transient StringProperty nom_groupeProp;
	private String carac_groupe;
	private String pays_groupe;
	private String region_groupe;
	private ArrayList<Personne> liste_personne = new ArrayList<>();
	private ArrayList<Titre> liste_titre = new ArrayList<>();
	private ArrayList<Representation> liste_representation = new ArrayList<>();
	private byte[] image;

	// =================================================================================================
	public String getNom_groupe() {
		return nom_groupe;
	}

	public void setNom_groupe(String nom_groupe) {
		this.nom_groupe = nom_groupe;
		this.nom_groupeProp = new SimpleStringProperty(nom_groupe);
	}

	public final StringProperty nom_groupeProperty() {
		return nom_groupeProp;
	}

	// =================================================================================================
	public String getCarac_groupe() {
		return carac_groupe;
	}

	public void setCarac_groupe(String carac_groupe) {
		this.carac_groupe = carac_groupe;
	}

	// =================================================================================================
	public String getPays_groupe() {
		return pays_groupe;
	}

	public void setPays_groupe(String pays_groupe) {
		this.pays_groupe = pays_groupe;
	}

	// =================================================================================================
	public String getRegion_groupe() {
		return region_groupe;
	}

	public void setRegion_groupe(String region_groupe) {
		this.region_groupe = region_groupe;
	}

	// =================================================================================================
	public ObservableList<Personne> getListe_personne() {
		ObservableList<Personne> liste_personneO = FXCollections.observableArrayList();
		for (Personne personne : liste_personne) {
			liste_personneO.add(personne);
		}
		return liste_personneO;
	}

	public void addPersonneToList(Personne personne) {
		this.liste_personne.add(personne);
	}

	public void setPersonneToList(int index, Personne personne) {
		this.liste_personne.set(index, personne);
	}

	public void removePersonneToList(Personne personne) {
		this.liste_personne.remove(personne);
	}

	// =================================================================================================
	public ObservableList<Titre> getListe_titre() {
		ObservableList<Titre> liste_titreO = FXCollections.observableArrayList();
		for (Titre titre : liste_titre) {
			liste_titreO.add(titre);
		}
		return liste_titreO;
	}

	public void addTitreToList(Titre titre) {
		this.liste_titre.add(titre);
	}

	public void setTitreToList(int index, Titre titre) {
		this.liste_titre.set(index, titre);
	}

	public void removeTitreToList(Titre titre) {
		this.liste_titre.remove(titre);
	}

	// =================================================================================================
	public ObservableList<Representation> getListe_representation() {
		ObservableList<Representation> liste_RepresentationO = FXCollections.observableArrayList();
		for (Representation representation : liste_representation) {
			liste_RepresentationO.add(representation);
		}
		return liste_RepresentationO;
	}

	public void addRepresentationToList(Representation representation) {
		this.liste_representation.add(representation);
	}

	public void setRepresentationToList(int index, Representation representation) {
		this.liste_representation.set(index, representation);
	}

	public void removeRepresentationToList(Representation representation) {
		this.liste_representation.remove(representation);
	}

	// =================================================================================================
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	// =================================================================================================
	@Override
	public void validateObject() throws InvalidObjectException {
		if (this.nom_groupe == null || this.nom_groupe.length() == 0) {
			throw new InvalidObjectException("Le champ nom ne doit pas être vide");
		}
	}
}
