package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Rencontre implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = -7356427739580246970L;
	private String nom_renc;
	private String ville_renc;
	private transient StringProperty nom_rencProp;
	private transient StringProperty ville_rencProp;
	private String lieu_renc;
	private Date date_deb_renc;
	private Date date_fin_renc;
	private transient ObjectProperty<Date> date_deb_rencProp;
	private transient ObjectProperty<Date> date_fin_rencProp;
	private String periodicite_renc;
	private Long nb_pers_attendues;
	private ArrayList<Organisateur> liste_organisateur = new ArrayList<>();
	private ArrayList<Representation> liste_representation = new ArrayList<>();

	// =================================================================================================
	public String getNom_renc() {
		return nom_renc;
	}

	public void setNom_renc(String nom_renc) {
		this.nom_renc = nom_renc;
		this.nom_rencProp = new SimpleStringProperty(nom_renc);
	}

	public StringProperty nom_rencProperty() {
		return nom_rencProp;
	}

	// =================================================================================================
	public String getVille_renc() {
		return ville_renc;
	}

	public void setVille_renc(String ville_renc) {
		this.ville_renc = ville_renc;
		this.ville_rencProp = new SimpleStringProperty(ville_renc);
	}

	public StringProperty ville_rencProperty() {
		return ville_rencProp;
	}

	// =================================================================================================
	public String getLieu_renc() {
		return lieu_renc;
	}

	public void setLieu_renc(String lieu_renc) {
		this.lieu_renc = lieu_renc;
	}

	// =================================================================================================
	public Date getDate_deb_renc() {
		return date_deb_renc;
	}

	public void setDate_deb_renc(Date date_deb_renc) {
		this.date_deb_renc = date_deb_renc;
		this.date_deb_rencProp = new SimpleObjectProperty<Date>(date_deb_renc);
	}

	public ObjectProperty<Date> date_deb_rencProperty() {
		return date_deb_rencProp;
	}

	// =================================================================================================
	public Date getDate_fin_renc() {
		return date_fin_renc;
	}

	public void setDate_fin_renc(Date date_fin_renc) {
		this.date_fin_renc = date_fin_renc;
		this.date_fin_rencProp = new SimpleObjectProperty<Date>(date_fin_renc);
	}

	public ObjectProperty<Date> date_fin_rencProperty() {
		return date_fin_rencProp;
	}

	// =================================================================================================
	public String getPeriodicite_renc() {
		return periodicite_renc;
	}

	public void setPeriodicite_renc(String periodicite_renc) {
		this.periodicite_renc = periodicite_renc;
	}

	// =================================================================================================
	public Long getNb_pers_attendues() {
		return nb_pers_attendues;
	}

	public void setNb_pers_attendues(Long nb_pers_attendues) {
		this.nb_pers_attendues = nb_pers_attendues;
	}

	// =================================================================================================
	public ObservableList<Organisateur> getListe_organisateur() {
		ObservableList<Organisateur> liste_organisateurO = FXCollections.observableArrayList();
		for (Organisateur organisateur : liste_organisateur) {
			liste_organisateurO.add(organisateur);
		}
		return liste_organisateurO;
	}

	public void addOrganisateurToList(Organisateur organisateur) {
		this.liste_organisateur.add(organisateur);
	}

	public void setOrganisateurToList(int index, Organisateur organisateur) {
		this.liste_organisateur.set(index, organisateur);
	}

	public void removeOrganisateurToList(Organisateur organisateur) {
		this.liste_organisateur.remove(organisateur);
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
	@Override
	public void validateObject() throws InvalidObjectException {
		if (this.nom_renc == null || this.nom_renc.length() == 0) {
			throw new InvalidObjectException("Le champ nom ne doit pas être vide");
		} else if (this.ville_renc == null || this.ville_renc.length() == 0) {
			throw new InvalidObjectException("Le champ ville ne doit pas être vide");
		}
	}
}
