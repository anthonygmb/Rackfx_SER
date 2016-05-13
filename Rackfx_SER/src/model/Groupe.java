package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;
import java.util.Hashtable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Groupe implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = 2167626103464193289L;
	private String nom_groupe;
	private transient StringProperty nom_groupeProp;
	private String carac_groupe;
	private String pays_groupe;
	private String region_groupe;
	private Hashtable<Integer, Personne> liste_personneSER = new Hashtable<Integer, Personne>();
	private Hashtable<Integer, Titre> liste_titreSER = new Hashtable<Integer, Titre>();
	private Hashtable<Integer, Representation> liste_representationSER = new Hashtable<Integer, Representation>();
	private transient ObservableList<Personne> liste_personne = FXCollections.observableArrayList();
	private transient ObservableList<Titre> liste_titre = FXCollections.observableArrayList();
	private transient ObservableList<Representation> liste_representation = FXCollections.observableArrayList();
	private byte[] image;

	public Groupe() {
		this(null, null, null, null);
	}

	public Groupe(String nomGroupe, String carac_groupe, String pays_groupe, String region_groupe) {
		this.nom_groupe = nomGroupe;
		this.carac_groupe = carac_groupe;
		this.pays_groupe = pays_groupe;
		this.region_groupe = region_groupe;
	}

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
		liste_personne.clear();
		for (int i = 0; i < this.liste_personneSER.size(); i++) {
			liste_personne.add(this.liste_personneSER.get(i));
		}
		return liste_personne;
	}

	public void setListe_personne(ObservableList<Personne> liste_personne) {
		this.liste_personne = liste_personne;
		for (int i = 0; i < this.liste_personne.size(); i++) {
			liste_personneSER.put(i, this.liste_personne.get(i));
		}
	}

	// =================================================================================================
	public ObservableList<Titre> getListe_titre() {
		liste_titre.clear();
		for (int i = 0; i < this.liste_titreSER.size(); i++) {
			liste_titre.add(this.liste_titreSER.get(i));
		}
		return liste_titre;
	}

	public void setListe_titre(ObservableList<Titre> liste_titre) {
		this.liste_titre = liste_titre;
		for (int i = 0; i < this.liste_titre.size(); i++) {
			liste_titreSER.put(i, this.liste_titre.get(i));
		}
	}

	// =================================================================================================
	public ObservableList<Representation> getListe_representation() {
		liste_representation.clear();
		for (int i = 0; i < this.liste_representationSER.size(); i++) {
			liste_representation.add(this.liste_representationSER.get(i));
		}
		return liste_representation;
	}

	public void setListe_representation(ObservableList<Representation> liste_representation) {
		this.liste_representation = liste_representation;
		for (int i = 0; i < this.liste_representation.size(); i++) {
			liste_representationSER.put(i, this.liste_representation.get(i));
		}
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
