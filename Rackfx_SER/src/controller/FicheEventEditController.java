package controller;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.scene.control.LocalTimePicker;
import model.Groupe;
import model.Organisateur;
import model.Rencontre;
import model.Representation;
import model.Titre;
import utilities.Crud;
import utilities.Res_listes;
import utilities.Validateur;

public class FicheEventEditController {

	/*
	 * =========================================================================
	 * GENERAL
	 */
	@FXML
	private TabPane tp_fiche_event;
	private Stage dialogStage;
	private String NbPers;
	private String telNumber = "";
	private String faxNumber = "";
	private ResourceBundle Lang_bundle;
	private Label vide1;

	/* Singleton */
	/** Instance unique pré-initialisée */
	private static FicheEventEditController INSTANCE_FICHE_EVENT_CONTROLLER;

	/** Point d'accès pour l'instance unique du singleton */
	public static FicheEventEditController getInstance() {
		return INSTANCE_FICHE_EVENT_CONTROLLER;
	}

	/**
	 * Méthode initialize appellé automatiquement après que le fxml est été
	 * chargé.
	 */
	@FXML
	private void initialize() {
		INSTANCE_FICHE_EVENT_CONTROLLER = this;
		this.Lang_bundle = MainApp.getInstance().Lang_bundle;

		/* limite le textfiel à X caractères */
		tf_nom_event.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (tf_nom_event.getText() != null && newValue.length() > MainApp.getInstance().VALMAX) {
					tf_nom_event.setText(oldValue);
				} else {
					tf_nom_event.setText(newValue);
				}
			}
		});

		/* limite le textfiel à X caractères */
		tf_ville_event.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (tf_ville_event.getText() != null && newValue.length() > MainApp.getInstance().VALMAX) {
					tf_ville_event.setText(oldValue);
				} else {
					tf_ville_event.setText(newValue);
				}
			}
		});

		/* limite le textfiel à X caractères */
		tf_lieu_event.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (tf_lieu_event.getText() != null && newValue.length() > MainApp.getInstance().VALMAX) {
					tf_lieu_event.setText(oldValue);
				} else {
					tf_lieu_event.setText(newValue);
				}
			}
		});

		cmbox_orga.setButtonCell(new ListCell<Organisateur>() {
			@Override
			protected void updateItem(Organisateur item, boolean empty) {
				super.updateItem(item, empty);
				setText(null);
				if (!empty && item != null) {
					final String text = String.format("%s, %s %s", item.getEntreprise_orga(), item.getCivi_orga(),
							item.getNom_orga());
					setText(text);
				}
			}
		});

		cmbox_orga.setCellFactory(new Callback<ListView<Organisateur>, ListCell<Organisateur>>() {
			@Override
			public ListCell<Organisateur> call(ListView<Organisateur> param) {
				return new ListCell<Organisateur>() {

					@Override
					protected void updateItem(Organisateur item, boolean empty) {
						super.updateItem(item, empty);
						setText(null);
						if (!empty && item != null) {
							final String text = String.format("%s, %s %s", item.getEntreprise_orga(),
									item.getCivi_orga(), item.getNom_orga());
							setText(text);
						}
					}
				};
			}
		});

		/* valeures par defaut de l'onglet organisateur */
		annulerOrganisateur();

		tf_nb_pers_event.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*") && newValue.length() < 7) {
					NbPers = newValue;
				} else {
					tf_nb_pers_event.setText(oldValue);
				}
			}
		});
		tf_nb_pers_event.positionCaret(tf_nb_pers_event.getLength());

		/* limite le textfiel à X caractères */
		tf_nom_orga.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (tf_nom_orga.getText() != null && newValue.length() > MainApp.getInstance().VALMAX) {
					tf_nom_orga.setText(oldValue);
				} else {
					tf_nom_orga.setText(newValue);
				}
			}
		});

		/* limite le textfiel à X caractères */
		tf_prenom_orga.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (tf_prenom_orga.getText() != null && newValue.length() > MainApp.getInstance().VALMAX) {
					tf_prenom_orga.setText(oldValue);
				} else {
					tf_prenom_orga.setText(newValue);
				}
			}
		});

		/* limite le textfiel à X caractères */
		tf_entreprise_orga.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (tf_entreprise_orga.getText() != null && newValue.length() > MainApp.getInstance().VALMAX) {
					tf_entreprise_orga.setText(oldValue);
				} else {
					tf_entreprise_orga.setText(newValue);
				}
			}
		});

		/* limite le textfiel à X caractères */
		tf_adress_orga.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (tf_adress_orga.getText() != null
						&& newValue.length() > MainApp.getInstance().VALMAX + MainApp.getInstance().VALMAX) {
					tf_adress_orga.setText(oldValue);
				} else {
					tf_adress_orga.setText(newValue);
				}
			}
		});

		/* limite le textfiel à X caractères */
		tf_mail_orga.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (tf_mail_orga.getText() != null && newValue.length() > MainApp.getInstance().VALMAX) {
					tf_mail_orga.setText(oldValue);
				} else {
					tf_mail_orga.setText(newValue);
				}
			}
		});

		tf_tel_orga.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*") && newValue.length() <= 13) {
					telNumber = newValue;
				} else {
					tf_tel_orga.setText(oldValue);
				}
			}
		});
		tf_tel_orga.positionCaret(tf_tel_orga.getLength());

		tf_fax_orga.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*") && newValue.length() <= 13) {
					faxNumber = newValue;
				} else {
					tf_fax_orga.setText(oldValue);
				}
			}
		});
		tf_fax_orga.positionCaret(tf_fax_orga.getLength());

		/* valeures par defaut de l'onglet représentation */
		annulerProg();

		/* formatte le tableau de représentation */
		col_groupe_prog.setCellValueFactory(cellData -> cellData.getValue().nom_groupeProperty());
		col_titre_prog.setCellValueFactory(cellData -> cellData.getValue().nom_titreProperty());
		col_deb_prog.setCellValueFactory(cellData -> cellData.getValue().heure_debutProperty());
		col_fin_prog.setCellValueFactory(cellData -> cellData.getValue().heure_fintProperty());
		tbv_prog.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setProg());

		/* formatte la combobox pour qu'elle affiche le texte voulu */
		cmbox_groupe_event.setButtonCell(new ListCell<Groupe>() {
			@Override
			protected void updateItem(Groupe item, boolean empty) {
				super.updateItem(item, empty);
				setText(null);
				if (!empty && item != null) {
					final String text = String.format("%s", item.getNom_groupe());
					setText(text);
				}
			}
		});
		cmbox_groupe_event.setCellFactory(new Callback<ListView<Groupe>, ListCell<Groupe>>() {
			@Override
			public ListCell<Groupe> call(ListView<Groupe> param) {
				return new ListCell<Groupe>() {

					@Override
					protected void updateItem(Groupe groupe, boolean empty) {
						super.updateItem(groupe, empty);
						setText(null);
						if (!empty && groupe != null) {
							final String text = String.format("%s", groupe.getNom_groupe());
							setText(text);
						}
					}
				};
			}
		});

		/* formatte la combobox pour qu'elle affiche le texte voulu */
		cmbox_titre_event.setButtonCell(new ListCell<Titre>() {
			@Override
			protected void updateItem(Titre item, boolean empty) {
				super.updateItem(item, empty);
				setText(null);
				if (!empty && item != null) {
					final String text = String.format("%s", item.getTitre());
					setText(text);
				}
			}
		});
		cmbox_titre_event.setCellFactory(new Callback<ListView<Titre>, ListCell<Titre>>() {
			@Override
			public ListCell<Titre> call(ListView<Titre> param) {
				return new ListCell<Titre>() {

					@Override
					protected void updateItem(Titre titre, boolean empty) {
						super.updateItem(titre, empty);
						setText(null);
						if (!empty && titre != null) {
							final String text = String.format("%s ", titre.getTitre());
							setText(text);
						}
					}
				};
			}
		});

		cmbox_groupe_event.setItems(MainViewController.getInstance().tv_reper.getItems());
		cmbox_groupe_event.valueProperty().addListener(new ChangeListener<Groupe>() {
			@Override
			public void changed(ObservableValue<? extends Groupe> observable, Groupe oldValue, Groupe newValue) {
				if (cmbox_groupe_event.getSelectionModel().getSelectedItem() == null) {
					cmbox_titre_event.getSelectionModel().clearSelection();
				} else {
					cmbox_titre_event.getItems().clear();
					cmbox_titre_event
							.setItems(cmbox_groupe_event.getSelectionModel().getSelectedItem().getListe_titre());
				}
			}
		});

		btn_creer_event.setDisable(true);
		btn_creer_orga.setDisable(true);
		btn_creer_prog.setDisable(true);
		btn_supp_orga.setDisable(true);
		btn_supp_prog.setDisable(true);

		if (MainViewController.getInstance().connectAdmin) {
			btn_creer_event.setDisable(false);
			btn_creer_orga.setDisable(false);
			btn_creer_prog.setDisable(false);
			btn_supp_orga.setDisable(false);
			btn_supp_prog.setDisable(false);
		} else if (MainViewController.getInstance().connectUser) {
			btn_creer_event.setDisable(false);
			btn_creer_orga.setDisable(false);
			btn_creer_prog.setDisable(false);
		}

		vide1 = new Label(Lang_bundle.getString("vide"));
		tbv_prog.setPlaceholder(vide1);
	}

	/**
	 * Methode pour afficher ou cacher les onglets autres que l'onglet
	 * information.
	 * 
	 * @param modif
	 */
	protected void geleTab(boolean modif) {
		if (!modif) {
			tab_orga_event.setDisable(true);
			tab_prog_event.setDisable(true);
		} else {
			tab_orga_event.setDisable(false);
			tab_prog_event.setDisable(false);
		}
	}

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	protected void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/*
	 * =========================================================================
	 * ONGLET INFORMATIONS
	 */

	@FXML
	private TextField tf_ville_event;
	@FXML
	private ComboBox<String> cmbox_perio_event = new ComboBox<>(Res_listes.perio_event);
	@FXML
	private TextField tf_nom_event;
	@FXML
	private TextField tf_lieu_event;
	@FXML
	private DatePicker dt_debut_event;
	@FXML
	private DatePicker dt_fin_event;
	@FXML
	private TextField tf_nb_pers_event;
	@FXML
	private Button btn_creer_event;
	private Rencontre rencontre;

	/**
	 * Renseigne la fenetre d'édition de rencontre avec la rencontre passée en
	 * parametre.
	 * 
	 * @param rencontre
	 * @param modif
	 */
	protected void setEvent(Rencontre rencontre, boolean modif, int tab) {
		tp_fiche_event.getSelectionModel().select(tab);
		this.rencontre = rencontre;
		tf_nom_event.setText(rencontre.getNom_renc());
		tf_ville_event.setText(rencontre.getVille_renc());
		tf_lieu_event.setText(rencontre.getLieu_renc());
		tf_nb_pers_event.setText(String.valueOf(rencontre.getNb_pers_attendues()));

		if (modif) {
			dt_debut_event.setValue(rencontre.getDate_deb_renc().toLocalDate());
		} else {
			dt_debut_event.setValue(LocalDate.now());
		}

		if (modif) {
			dt_fin_event.setValue(rencontre.getDate_fin_renc().toLocalDate());
		} else {
			dt_fin_event.setValue(LocalDate.now());
		}

		if (rencontre.getPeriodicite_renc() == null) {
			cmbox_perio_event.getSelectionModel().clearSelection();
		} else {
			cmbox_perio_event.getSelectionModel().select(rencontre.getPeriodicite_renc());
		}
		cmbox_perio_event.getItems().addAll(Res_listes.perio_event);
		btn_creer_event.setText((modif) ? Lang_bundle.getString("Appliquer") : Lang_bundle.getString("Creer"));
		cmbox_orga.setItems(rencontre.getListe_organisateur());
		tbv_prog.setItems(rencontre.getListe_representation());
	}

	/**
	 * Methode executée lorsque l'utilisateur clique sur le bouton Créer.
	 * Enregistre ou met à jour une rencontre en base de données.
	 */
	@FXML
	private void creerModifierRencontre() {
		rencontre.setNom_renc(tf_nom_event.getText());
		rencontre.setVille_renc(tf_ville_event.getText());
		rencontre.setLieu_renc(tf_lieu_event.getText());
		if (tf_nb_pers_event.getText().equals("")) {
			NbPers = "0";
		}
		rencontre.setNb_pers_attendues(Long.parseLong(NbPers));
		rencontre.setDate_deb_renc(java.sql.Date.valueOf(dt_debut_event.getValue()));
		rencontre.setDate_fin_renc(java.sql.Date.valueOf(dt_fin_event.getValue()));
		if (!cmbox_perio_event.getSelectionModel().isEmpty()) {
			rencontre.setPeriodicite_renc(cmbox_perio_event.getSelectionModel().getSelectedItem().toString());
		}
		try {
			rencontre.validateObject();
			if (dialogStage.getTitle().equals(Lang_bundle.getString("Nouvelle.rencontre"))) {
				geleTab(true);
				MainViewController.getInstance().tv_planif.getItems().add(rencontre);
				MainViewController.getInstance().tv_planif.getSelectionModel().selectLast();
			} else {
				MainViewController.getInstance().showEventDetails(rencontre);
				int index = MainViewController.getInstance().tv_planif.getSelectionModel().getFocusedIndex();
				MainViewController.getInstance().tv_planif.getItems().set(index, rencontre);
				MainViewController.getInstance().tv_planif.getSelectionModel().select(index);
			}
			Crud.Serialize(MainViewController.getInstance().tv_planif.getItems());
			dialogStage.setTitle(rencontre.getNom_renc());
			btn_creer_event.setText(Lang_bundle.getString("Appliquer"));
			cmbox_orga.setItems(rencontre.getListe_organisateur());
			tbv_prog.setItems(rencontre.getListe_representation());
		} catch (InvalidObjectException e) {
			Validateur.showPopup(AlertType.WARNING, Lang_bundle.getString("Erreur"), Lang_bundle.getString("Attention"),
					e.getMessage()).showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode executée lorsque l'utilisateur clique sur le bouton Annuler.
	 * Ferme la fenetre sans enregistrer les modifications.
	 */
	@FXML
	private void annulerRencontre() {
		dialogStage.close();
	}

	/*
	 * =========================================================================
	 * ONGLET ORGANISATEURS
	 */
	@FXML
	private Tab tab_orga_event;
	@FXML
	private CheckBox ckbox_mme_civi_orga;
	@FXML
	private CheckBox ckbox_mr_civi_orga;
	@FXML
	private TextField tf_nom_orga;
	@FXML
	private TextField tf_prenom_orga;
	@FXML
	private TextField tf_adress_orga;
	@FXML
	private TextField tf_tel_orga;
	@FXML
	private TextField tf_fax_orga;
	@FXML
	private TextField tf_mail_orga;
	@FXML
	private TextField tf_entreprise_orga;
	@FXML
	private ComboBox<Organisateur> cmbox_orga = new ComboBox<>();
	@FXML
	private Button btn_creer_orga;
	@FXML
	private Button btn_supp_orga;
	private Organisateur organisateur;

	/**
	 * Methode sur la checkBox Monsieur, si la case Monsieur est cochée, Madamme
	 * est décochée.
	 */
	@FXML
	private void setCiviliteMr() {
		if (ckbox_mr_civi_orga.isSelected()) {
			ckbox_mme_civi_orga.setSelected(false);
		}
	}

	/**
	 * Methode sur la checkBox Madamme, si la case Madamme est cochée, Monsieur
	 * est décochée.
	 */
	@FXML
	private void setCiviliteMme() {
		if (ckbox_mme_civi_orga.isSelected()) {
			ckbox_mr_civi_orga.setSelected(false);
		}
	}

	/**
	 * renseigne la fenetre d'édition de l'organisateur.
	 */
	@FXML
	private void setOrganisateur() {
		if (cmbox_orga.getSelectionModel().getSelectedItem() == null) {
			annulerOrganisateur();
		} else {
			organisateur = cmbox_orga.getSelectionModel().getSelectedItem();
			if (organisateur.getCivi_orga() == true) {
				ckbox_mr_civi_orga.setSelected(true);
				ckbox_mme_civi_orga.setSelected(false);
			} else {
				ckbox_mme_civi_orga.setSelected(true);
				ckbox_mr_civi_orga.setSelected(false);
			}
			tf_nom_orga.setText(organisateur.getNom_orga());
			tf_prenom_orga.setText(organisateur.getPrenom_orga());
			tf_entreprise_orga.setText(organisateur.getEntreprise_orga());
			tf_adress_orga.setText(organisateur.getAdresse_entreprise_orga());

			if (organisateur.getTel_orga() == null) {
				tf_tel_orga.clear();
			} else {
				tf_tel_orga.setText(String.valueOf(organisateur.getTel_orga()));
			}

			if (organisateur.getFax_orga() == null) {
				tf_fax_orga.clear();
			} else {
				tf_fax_orga.setText(String.valueOf(organisateur.getFax_orga()));
			}

			tf_mail_orga.setText(organisateur.getMail_orga());
			btn_creer_orga.setText(Lang_bundle.getString("Appliquer"));
			btn_supp_orga.setDisable((MainViewController.getInstance().connectAdmin) ? false : true);
		}
	}

	/**
	 * Methode executée lorsque l'utilisateur clique sur le bouton Créer.
	 * Enregistre ou met à jour un organisateur en base de données.
	 */
	@FXML
	private void creerModifierOrganisateur() {
		if (cmbox_orga.getSelectionModel().getSelectedItem() == null) {
			organisateur = new Organisateur();
		} else {
			organisateur = cmbox_orga.getSelectionModel().getSelectedItem();
		}
		if (ckbox_mr_civi_orga.isSelected()) {
			organisateur.setCivi_orga(true);
		} else {
			organisateur.setCivi_orga(false);
		}
		organisateur.setNom_orga(tf_nom_orga.getText());
		organisateur.setPrenom_orga(tf_prenom_orga.getText());
		organisateur.setEntreprise_orga(tf_entreprise_orga.getText());
		organisateur.setAdresse_entreprise_orga(tf_adress_orga.getText());
		if (telNumber.equals("")) {
			organisateur.setTel_orga(null);
		} else {
			organisateur.setTel_orga(telNumber);
		}
		if (faxNumber.equals("")) {
			organisateur.setFax_orga(null);
		} else {
			organisateur.setFax_orga(faxNumber);
		}
		organisateur.setMail_orga(tf_mail_orga.getText());
		try {
			organisateur.validateObject();
			if (cmbox_orga.getSelectionModel().getSelectedItem() == null) {
				rencontre.addOrganisateurToList(organisateur);
			} else {
				rencontre.setOrganisateurToList(cmbox_orga.getSelectionModel().getSelectedIndex(), organisateur);
			}
			MainViewController.getInstance().tv_planif.getItems()
					.set(MainViewController.getInstance().tv_planif.getSelectionModel().getSelectedIndex(), rencontre);
			cmbox_orga.setItems(rencontre.getListe_organisateur());
			Crud.Serialize(MainViewController.getInstance().tv_planif.getItems());
			annulerOrganisateur();
		} catch (InvalidObjectException e) { /* si la validation a échoué */
			Validateur.showPopup(AlertType.WARNING, Lang_bundle.getString("Erreur"), Lang_bundle.getString("Attention"),
					e.getMessage()).showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode executée lorsque l'utilisateur clique sur le bouton Annuler.
	 * Réinitialise les champs et les composants.
	 */
	@FXML
	private void annulerOrganisateur() {
		tf_nom_orga.clear();
		tf_prenom_orga.clear();
		tf_entreprise_orga.clear();
		tf_adress_orga.clear();
		tf_tel_orga.clear();
		tf_fax_orga.clear();
		tf_mail_orga.clear();
		ckbox_mr_civi_orga.setSelected(true);
		ckbox_mme_civi_orga.setSelected(false);
		cmbox_orga.getSelectionModel().clearSelection();
		btn_supp_orga.setDisable(true);
		btn_creer_orga.setText(Lang_bundle.getString("Creer"));
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton supprimer. Supprime
	 * l'organisateur dans la liste à l'index sélectionné.
	 */
	@FXML
	private void supprimerOrganisateur() {
		MainViewController.getInstance().result = Validateur.showPopup(AlertType.CONFIRMATION,
				Lang_bundle.getString("Confirmation.d'action"), Lang_bundle.getString("Confirmation.de.suppression"),
				Lang_bundle.getString("Voulez-vous.supprimer.cet.organisateur.?")).showAndWait();
		if (MainViewController.getInstance().result.get() == ButtonType.OK) {
			rencontre.removeOrganisateurToList(cmbox_orga.getSelectionModel().getSelectedItem());
			MainViewController.getInstance().tv_planif.getItems()
					.set(MainViewController.getInstance().tv_planif.getSelectionModel().getSelectedIndex(), rencontre);
			cmbox_orga.setItems(rencontre.getListe_organisateur());
			try {
				Crud.Serialize(MainViewController.getInstance().tv_planif.getItems());
			} catch (IOException e) {
				e.printStackTrace();
			}
			annulerOrganisateur();
		}
	}

	/*
	 * =========================================================================
	 * ONGLET REPRESENTATION
	 */
	@FXML
	private Tab tab_prog_event;
	@FXML
	private Button btn_creer_prog;
	@FXML
	private Button btn_supp_prog;
	@FXML
	private TableView<Representation> tbv_prog = new TableView<>();
	@FXML
	private TableColumn<Representation, String> col_groupe_prog;
	@FXML
	private TableColumn<Representation, String> col_titre_prog;
	@FXML
	private TableColumn<Representation, Time> col_deb_prog;
	@FXML
	private TableColumn<Representation, Time> col_fin_prog;
	@FXML
	private ComboBox<Groupe> cmbox_groupe_event = new ComboBox<>(MainViewController.getInstance().tv_reper.getItems());
	@FXML
	private ComboBox<Titre> cmbox_titre_event = new ComboBox<>();
	@FXML
	private LocalTimePicker ltp_h_deb_prog;
	@FXML
	private LocalTimePicker ltp_h_fin_prog;
	private Representation representation;

	/**
	 * Renseigne la fenetre d'édition de la représentation.
	 */
	@FXML
	private void setProg() {
		if (tbv_prog.getSelectionModel().getSelectedItem() == null) {
			annulerProg();
		} else {
			representation = tbv_prog.getSelectionModel().getSelectedItem();
			for (Groupe groupeIt : MainViewController.getInstance().tv_reper.getItems()) {
				if (representation.getNom_Groupe().equals(groupeIt.getNom_groupe())) {
					cmbox_groupe_event.getSelectionModel().select(groupeIt);
				}
				for (Titre titreiT : groupeIt.getListe_titre()) {
					if (representation.getNom_Titre().equals(titreiT.getTitre())) {
						cmbox_titre_event.getSelectionModel().select(titreiT);
					}
				}
			}
			ltp_h_deb_prog.setLocalTime(representation.getHeure_debut().toLocalTime());
			ltp_h_fin_prog.setLocalTime(representation.getHeure_fin().toLocalTime());
			btn_creer_prog.setText(Lang_bundle.getString("Appliquer"));
			btn_supp_prog.setDisable((MainViewController.getInstance().connectAdmin) ? false : true);
		}
	}

	/**
	 * Methode executée lorsque l'utilisateur clique sur le bouton Créer.
	 * Enregistre ou met à jour une representation en base de données.
	 */
	@FXML
	private void creerModifierProg() {
		if (tbv_prog.getSelectionModel().getSelectedItem() == null) {
			representation = new Representation();
		} else {
			representation = tbv_prog.getSelectionModel().getSelectedItem();
		}
		representation.setNom_Groupe(cmbox_groupe_event.getSelectionModel().getSelectedItem().getNom_groupe());
		representation.setNom_Titre(cmbox_titre_event.getSelectionModel().getSelectedItem().getTitre());
		representation.setHeure_debut(java.sql.Time.valueOf(ltp_h_deb_prog.getLocalTime()));
		representation.setHeure_fin(java.sql.Time.valueOf(ltp_h_fin_prog.getLocalTime()));

		try {
			Groupe tempGroupe = MainViewController.getInstance().tv_reper.getItems()
					.get(cmbox_groupe_event.getSelectionModel().getSelectedIndex());
			representation.validateObject();

			if (tbv_prog.getSelectionModel().getSelectedItem() == null) {
				rencontre.addRepresentationToList(representation);
				tempGroupe.addRepresentationToList(representation);
				tbv_prog.getItems().add(representation);
			} else {
				int index2 = 0;
				for (int i = 0; i < rencontre.getListe_representation().size(); i++) {
					if (rencontre.getListe_representation().get(i).getNom_Groupe()
							.equals(representation.getNom_Groupe())) {
						index2 = i;
					}
				}

				rencontre.setRepresentationToList(index2, representation);

				MainViewController.getInstance().tv_reper.getItems()
						.get(cmbox_groupe_event.getSelectionModel().getSelectedIndex())
						.setRepresentationToList(index2, representation);

				tbv_prog.getItems().set(tbv_prog.getSelectionModel().getSelectedIndex(), representation);
			}
			MainViewController.getInstance().tv_planif.getItems()
					.set(MainViewController.getInstance().tv_planif.getSelectionModel().getSelectedIndex(), rencontre);
			MainViewController.getInstance().tv_reper.getItems()
					.set(cmbox_groupe_event.getSelectionModel().getSelectedIndex(), tempGroupe);

			Crud.Serialize(MainViewController.getInstance().tv_reper.getItems());
			Crud.Serialize(MainViewController.getInstance().tv_planif.getItems());
			cmbox_groupe_event.setItems(MainViewController.getInstance().tv_reper.getItems());
			annulerProg();
		} catch (InvalidObjectException e) {
			Validateur.showPopup(AlertType.WARNING, Lang_bundle.getString("Erreur"), Lang_bundle.getString("Attention"),
					e.getMessage()).showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode executée lorsque l'utilisateur clique sur le bouton Annuler.
	 * Réinitialise les champs et les composants.
	 */
	@FXML
	private void annulerProg() {
		cmbox_titre_event.getItems().clear();
		cmbox_groupe_event.getSelectionModel().clearSelection();
		ltp_h_deb_prog.setLocalTime(MainApp.getInstance().def_time);
		ltp_h_fin_prog.setLocalTime(MainApp.getInstance().def_time);
		btn_supp_prog.setDisable(true);
		tbv_prog.getSelectionModel().clearSelection();
		btn_creer_prog.setText(Lang_bundle.getString("Creer"));
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton supprimer. Supprime la
	 * représentation dans la liste à l'index sélectionné.
	 */
	@FXML
	private void supprimerProg() {
		MainViewController.getInstance().result = Validateur.showPopup(AlertType.CONFIRMATION,
				Lang_bundle.getString("Confirmation.d'action"), Lang_bundle.getString("Confirmation.de.suppression"),
				Lang_bundle.getString("Voulez-vous.supprimer.cette.representation.?")).showAndWait();
		if (MainViewController.getInstance().result.get() == ButtonType.OK) {
			rencontre.removeRepresentationToList(tbv_prog.getSelectionModel().getSelectedItem());
			Groupe tempGroupe = MainViewController.getInstance().tv_reper.getItems()
					.get(cmbox_groupe_event.getSelectionModel().getSelectedIndex());
			tempGroupe.removeRepresentationToList(tbv_prog.getSelectionModel().getSelectedItem());

			MainViewController.getInstance().tv_planif.getItems()
					.set(MainViewController.getInstance().tv_planif.getSelectionModel().getSelectedIndex(), rencontre);

			MainViewController.getInstance().tv_reper.getItems()
					.set(cmbox_groupe_event.getSelectionModel().getSelectedIndex(), tempGroupe);

			cmbox_groupe_event.setItems(MainViewController.getInstance().tv_reper.getItems());
			tbv_prog.getItems().remove(tbv_prog.getSelectionModel().getSelectedItem());

			try {
				Crud.Serialize(MainViewController.getInstance().tv_reper.getItems());
				Crud.Serialize(MainViewController.getInstance().tv_planif.getItems());
			} catch (IOException e) {
				e.printStackTrace();
			}
			annulerProg();
		}
	}
}
