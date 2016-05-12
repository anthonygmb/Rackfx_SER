package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Groupe implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = 2167626103464193289L;
	private String nom_groupe;
	private transient StringProperty nom_groupeProp;
	private String carac_groupe;
	private String pays_groupe;
	private String region_groupe;
	private Set<Personne> liste_personne = new HashSet<Personne>();
	private Set<Titre> liste_titre = new HashSet<Titre>();
	private Set<Representation> liste_representation = new HashSet<Representation>();
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
	public Set<Personne> getListe_personne() {
		return liste_personne;
	}

	public void setListe_personne(Set<Personne> liste_personne) {
		this.liste_personne = liste_personne;
	}

	// =================================================================================================
	public Set<Titre> getListe_titre() {
		return liste_titre;
	}

	public void setListe_titre(Set<Titre> liste_titre) {
		this.liste_titre = liste_titre;
	}

	// =================================================================================================
	public Set<Representation> getListe_representation() {
		return liste_representation;
	}

	public void setListe_representation(Set<Representation> liste_representation) {
		this.liste_representation = liste_representation;
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
		if (this.nom_groupe == null) {
			throw new InvalidObjectException("Le champ nom ne doit pas être vide");
		}
	}
}
