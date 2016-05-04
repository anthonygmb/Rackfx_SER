package model;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;

public class Organisateur implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = 6669855842886526723L;
	private String nom_orga;
	private String prenom_orga;
	private boolean civi_orga;
	private String adresse_entreprise_orga;
	private String tel_orga;
	private String fax_orga;
	private String mail_orga;
	private String entreprise_orga;
	private Rencontre rencontre;

	public Organisateur() {
		this(null, null, false, null, null, null, null, null);
	}

	public Organisateur(String nom_orga, String prenom_orga, boolean civi_orga, String adresse_entreprise_orga,
			String tel_orga, String fax_orga, String mail_orga, String entreprise_orga) {
		this.nom_orga = nom_orga;
		this.prenom_orga = prenom_orga;
		this.civi_orga = civi_orga;
		this.adresse_entreprise_orga = adresse_entreprise_orga;
		this.tel_orga = tel_orga;
		this.fax_orga = fax_orga;
		this.mail_orga = mail_orga;
		this.entreprise_orga = entreprise_orga;
	}

	// =================================================================================================
	// public long getOrgaId() {
	// return orgaId;
	// }
	//
	// @SuppressWarnings("unused")
	// private void setOrgaId(long orgaId) {
	// this.orgaId = orgaId;
	// }

	// =================================================================================================
	public String getNom_orga() {
		return nom_orga;
	}

	public void setNom_orga(String nom_orga) {
		this.nom_orga = nom_orga;
	}

	// =================================================================================================
	public String getPrenom_orga() {
		return prenom_orga;
	}

	public void setPrenom_orga(String prenom_orga) {
		this.prenom_orga = prenom_orga;
	}

	// =================================================================================================
	public boolean getCivi_orga() {
		return civi_orga;
	}

	public void setCivi_orga(boolean civi_orga) {
		this.civi_orga = civi_orga;
	}

	// =================================================================================================
	public String getAdresse_entreprise_orga() {
		return adresse_entreprise_orga;
	}

	public void setAdresse_entreprise_orga(String adresse_entreprise_orga) {
		this.adresse_entreprise_orga = adresse_entreprise_orga;
	}

	// =================================================================================================
	public String getTel_orga() {
		return tel_orga;
	}

	public void setTel_orga(String tel_orga) {
		this.tel_orga = tel_orga;
	}

	// =================================================================================================
	public String getFax_orga() {
		return fax_orga;
	}

	public void setFax_orga(String fax_orga) {
		this.fax_orga = fax_orga;
	}

	// =================================================================================================
	public String getMail_orga() {
		return mail_orga;
	}

	public void setMail_orga(String mail_orga) {
		this.mail_orga = mail_orga;
	}

	// =================================================================================================
	public String getEntreprise_orga() {
		return entreprise_orga;
	}

	public void setEntreprise_orga(String entreprise_orga) {
		this.entreprise_orga = entreprise_orga;
	}

	// =================================================================================================
	public Rencontre getRencontre() {
		return rencontre;
	}

	public void setRencontre(Rencontre rencontre) {
		this.rencontre = rencontre;
	}

	// =================================================================================================
	@Override
	public void validateObject() throws InvalidObjectException {
		if (this.nom_orga == null) {
			throw new InvalidObjectException("Le champ nom ne doit pas être vide");
		} else if (this.prenom_orga == null) {
			throw new InvalidObjectException("Le champ prenom ne doit pas être vide");
		} else if (this.tel_orga == null) {
			throw new InvalidObjectException("Le champ telephone ne doit pas être vide");
		}
	}
}
