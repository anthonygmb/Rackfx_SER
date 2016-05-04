package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;
import java.sql.Date;

public class Personne implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = -7815741770942733167L;
	private String nom_membre;
	private String prenom_membre;
	private boolean civi_membre;
	private Date date_naiss_membre;
	private String spe_membre;
	private String instru_membre;
	private String respon_membre;
	private boolean correspondant;
	private String adresse_cor;
	private String tel_cor;
	private String fax_cor;
	private String mail_cor;
	private Groupe groupe;

	public Personne() {
		this(null, null, false, null, null, null, null, false, null, null, null, null);
	}

	public Personne(String nom_membre, String prenom_membre, boolean civi_membre, Date date_naiss_membre,
			String spe_membre, String instru_membre, String respon_membre, boolean correspondant, String adresse_cor,
			String tel_cor, String fax_cor, String mail_cor) {
		this.nom_membre = nom_membre;
		this.prenom_membre = prenom_membre;
		this.civi_membre = civi_membre;
		this.date_naiss_membre = date_naiss_membre;
		this.spe_membre = spe_membre;
		this.instru_membre = instru_membre;
		this.respon_membre = respon_membre;
		this.correspondant = correspondant;
		this.adresse_cor = adresse_cor;
		this.tel_cor = tel_cor;
		this.fax_cor = fax_cor;
		this.mail_cor = mail_cor;
	}

	// =================================================================================================
	public String getNom_membre() {
		return nom_membre;
	}

	public void setNom_membre(String nom_membre) {
		this.nom_membre = nom_membre;
	}

	// =================================================================================================
	public String getPrenom_membre() {
		return prenom_membre;
	}

	public void setPrenom_membre(String prenom_membre) {
		this.prenom_membre = prenom_membre;
	}

	// =================================================================================================
	public boolean getCivi_membre() {
		return civi_membre;
	}

	public void setCivi_membre(boolean civi_membre) {
		this.civi_membre = civi_membre;
	}

	// =================================================================================================
	public Date getDate_naiss_membre() {
		return date_naiss_membre;
	}

	public void setDate_naiss_membre(Date date_naiss_membre) {
		this.date_naiss_membre = date_naiss_membre;
	}

	// =================================================================================================
	public String getSpe_membre() {
		return spe_membre;
	}

	public void setSpe_membre(String spe_membre) {
		this.spe_membre = spe_membre;
	}

	// =================================================================================================
	public String getInstru_membre() {
		return instru_membre;
	}

	public void setInstru_membre(String instru_membre) {
		this.instru_membre = instru_membre;
	}

	// =================================================================================================
	public String getRespon_membre() {
		return respon_membre;
	}

	public void setRespon_membre(String respon_membre) {
		this.respon_membre = respon_membre;
	}

	// =================================================================================================
	public boolean getCorrespondant() {
		return correspondant;
	}

	public void setCorrespondant(boolean correspondant) {
		this.correspondant = correspondant;
	}

	// =================================================================================================
	public String getAdresse_cor() {
		return adresse_cor;
	}

	public void setAdresse_cor(String adresse_cor) {
		this.adresse_cor = adresse_cor;
	}

	// =================================================================================================
	public String getTel_cor() {
		return tel_cor;
	}

	public void setTel_cor(String tel_cor) {
		this.tel_cor = tel_cor;
	}

	// =================================================================================================
	public String getFax_cor() {
		return fax_cor;
	}

	public void setFax_cor(String fax_cor) {
		this.fax_cor = fax_cor;
	}

	// =================================================================================================
	public String getMail_cor() {
		return mail_cor;
	}

	public void setMail_cor(String mail_cor) {
		this.mail_cor = mail_cor;
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
		if (this.nom_membre == null) {
			throw new InvalidObjectException("Le champ nom ne doit pas être vide");
		} else if (this.prenom_membre == null) {
			throw new InvalidObjectException("Le champ prenom ne doit pas être vide");
		}
	}
}
