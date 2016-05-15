package controller;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;
import org.controlsfx.control.textfield.CustomTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Groupe;
import model.Parametres;
import model.Rencontre;
import model.Representation;
import model.User;
import utilities.Crud;
import utilities.CryptEtDecrypt;
import utilities.FileUtils;
import utilities.Validateur;

public final class MainViewController {

	/*
	 * =========================================================================
	 * GENERAL
	 */

	@FXML
	private TabPane tabpane_onglets;
	private String login_admin = "root";
	private String pwd_admin = "admin";
	private String salt = "[B@49396ed";
	private ResourceBundle Lang_bundle;
	protected boolean connectAdmin = false;
	protected boolean connectUser = false;
	protected Optional<ButtonType> result;
	private Label vide1;
	private Label vide2;
	private Label vide3;
	private int compteurAdmin = 0;
	private int compteurUser = 0;

	/* Singleton */
	/** Instance unique pré-initialisée */
	private static MainViewController INSTANCE_MAIN_VIEW_CONTROLLER;

	/** Point d'accès pour l'instance unique du singleton */
	public static MainViewController getInstance() {
		return INSTANCE_MAIN_VIEW_CONTROLLER;
	}

	public MainViewController() {
	}

	/**
	 * Initialise la classe controller. Cette methode est appelé automatiquement
	 * après que le fichier fxml a été chargé. Elle initialise la tableview
	 * groupe avec ses colonnes, vide les détails de groupe et place un listener
	 * sur la tableview groupe. Idem pour la tableview rencontre. Elle fait
	 * appel à la méthode <code>showGroupeDetails</code> et
	 * <code>showEventDetails</code>
	 * 
	 * @throws InterruptedException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 */
	@FXML
	private void initialize() throws InterruptedException {
		INSTANCE_MAIN_VIEW_CONTROLLER = this;

		this.Lang_bundle = MainApp.getInstance().Lang_bundle;

		// Session s = HibernateSetUp.getSession();
		// FullTextSession fullTextSession = Search.getFullTextSession(s);
		// fullTextSession.createIndexer().startAndWait();
		// fullTextSession.close();

		try {
			tv_reper.setItems(Crud.Deserialize(Groupe.class));
		} catch (ClassNotFoundException | IOException e) {
			System.out.printf("Aucun fichier de sauvegarde groupes trouvé\n");
		}
		try {
			tv_planif.setItems(Crud.Deserialize(Rencontre.class));
		} catch (ClassNotFoundException | IOException e) {
			System.out.printf("Aucun fichier de sauvegarde rencontres trouvé\n");
		}
		try {
			tv_admin.setItems(Crud.Deserialize(User.class));
		} catch (ClassNotFoundException | IOException e) {
			System.out.printf("Aucun fichier de sauvegarde utilisateurs trouvé\n");
		}

		/*
		 * reecriture des champs ayants des SimpleProperties pour l'affichage
		 * dans les tableaux
		 */
		for (Groupe groupe : tv_reper.getItems()) {
			groupe.setNom_groupe(groupe.getNom_groupe());
			for (int j = 0; j < groupe.getListe_titre().size(); j++) {
				groupe.getListe_titre().get(j).setTitre(groupe.getListe_titre().get(j).getTitre());
				groupe.getListe_titre().get(j).setAnnee(groupe.getListe_titre().get(j).getAnnee());
				groupe.getListe_titre().get(j).setDuree(groupe.getListe_titre().get(j).getDuree());
			}
		}
		for (Rencontre rencontre : tv_planif.getItems()) {
			rencontre.setNom_renc(rencontre.getNom_renc());
			rencontre.setDate_deb_renc(rencontre.getDate_deb_renc());
			rencontre.setDate_fin_renc(rencontre.getDate_fin_renc());
			rencontre.setVille_renc(rencontre.getVille_renc());
			for (int j = 0; j < rencontre.getListe_representation().size(); j++) {
				rencontre.getListe_representation().get(j)
						.setHeure_debut(rencontre.getListe_representation().get(j).getHeure_debut());
				rencontre.getListe_representation().get(j)
						.setHeure_fin(rencontre.getListe_representation().get(j).getHeure_fin());
				rencontre.getListe_representation().get(j)
						.setNom_Groupe(rencontre.getListe_representation().get(j).getNom_Groupe());
				rencontre.getListe_representation().get(j)
						.setNom_Titre(rencontre.getListe_representation().get(j).getNom_Titre());
			}
		}
		for (User user : tv_admin.getItems()) {
			user.setLogin(user.getLogin());
			user.setMot_de_passe(user.getMot_de_passe());
			user.setDroit_auth(user.getDroit_auth());
		}

		/* récupération du nombre d'utilisateurs et d'administrateurs */
		countUser();

		/*
		 * listener pour mettre à jour le nombre d'utilisateurs et
		 * d'administrateurs
		 */
		tv_admin.getItems().addListener(new ListChangeListener<User>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends User> c) {
				countUser();
			}
		});

		nom_groupeColumn.setCellValueFactory(cellData -> cellData.getValue().nom_groupeProperty());
		showGroupeDetails(null);
		tv_reper.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showGroupeDetails(newValue));

		col_event.setCellValueFactory(cellData -> cellData.getValue().nom_rencProperty());
		col_ville.setCellValueFactory(cellData -> cellData.getValue().ville_rencProperty());
		showEventDetails(null);
		tv_planif.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showEventDetails(newValue));
		tab_administration.setDisable(true);

		col_login.setCellValueFactory(cellData -> cellData.getValue().loginProperty());
		col_mdp.setCellValueFactory(cellData -> cellData.getValue().mot_de_passeProperty());
		col_droits.setCellValueFactory(cellData -> cellData.getValue().droit_authProperty());
		ts_adm_user.setSelected(true);

		btn_new_groupe.setDisable(true);
		btn_new_event.setDisable(true);
		btn_supp_groupe.setDisable(true);
		btn_supp_event.setDisable(true);

		vide1 = new Label(Lang_bundle.getString("vide"));
		vide2 = new Label(Lang_bundle.getString("vide"));
		vide3 = new Label(Lang_bundle.getString("vide"));
		tv_reper.setPlaceholder(vide1);
		tv_planif.setPlaceholder(vide2);
		tv_admin.setPlaceholder(vide3);
		
		/* limite le textfiel à X caractères */
		tf_admin_login.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (tf_admin_login.getText() != null && newValue.length() > MainApp.getInstance().VALMAX) {
					tf_admin_login.setText(oldValue);
				} else {
					tf_admin_login.setText(newValue);
				}
			}
		});
	}

	/*
	 * =========================================================================
	 * BANDEAU DE CONNECTION
	 */

	@FXML
	private TextField tf_login;
	@FXML
	private PasswordField pwf_mdp;
	@FXML
	private Button btn_connect;
	@FXML
	private Label lb_connection;

	/**
	 * Methode de connection à la session admin ou user. Elle est appelée
	 * lorsque l'on clique sur le bouton connection du bandeau. Vérifie si le
	 * login et le mot de passe sont conforme. Fait appel à la méthode
	 * <code>deconnection</code>
	 * 
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 */
	@FXML
	private void connection() {
		if (btn_connect.getText().equals(Lang_bundle.getString("Connexion"))) {
			if (tf_login.getText().equals(login_admin) && pwf_mdp.getText().equals(pwd_admin)) {
				connectAdmin = true;
			}

			/* Test de connection */
			for (int i = 0; i < tv_admin.getItems().size(); i++) {
				if (tf_login.getText().equals(tv_admin.getItems().get(i).getLogin())
						&& CryptEtDecrypt.getSecurePassword(pwf_mdp.getText(), salt)
								.equals(tv_admin.getItems().get(i).getMot_de_passe())) {

					if (tv_admin.getItems().get(i).getDroit_auth().equals("administrateur")) {
						connectAdmin = true;
					} else {
						connectUser = true;
					}
				}
			}

			/* Cas si l'utilisateur est connecté */
			if (connectAdmin || connectUser) {
				btn_connect.setText(Lang_bundle.getString("Deconnexion"));
				if (tf_login.getText().equals(login_admin)
						&& btn_connect.getText().equals(Lang_bundle.getString("Deconnexion"))) {
					lb_connection.setText(Lang_bundle.getString("Connecté.en.temps.que.[super.admin]"));
				} else {
					if (connectUser) {
						lb_connection.setText(Lang_bundle.getString("Connecté.en.temps.que.[user]"));
					} else {
						lb_connection.setText(Lang_bundle.getString("Connecté.en.temps.que.[admin]"));
					}
				}
				lb_connection.setTextFill(Color.web("#DEFFFF"));
				pwf_mdp.setStyle("-fx-text-inner-color: #000000;");
				tf_login.setStyle("-fx-text-inner-color: #000000;");
				tf_login.setDisable(true);
				pwf_mdp.setDisable(true);
				pwf_mdp.clear();
				tab_administration.setDisable((connectAdmin) ? false : true);
				btn_new_groupe.setDisable(false);
				btn_new_event.setDisable(false);
				btn_supp_groupe.setDisable((connectAdmin) ? false : true);
				btn_supp_event.setDisable((connectAdmin) ? false : true);
			} else {
				/* Cas si le login ou le mot de passe est faux */
				pwf_mdp.setStyle("-fx-text-inner-color: dc320c;");
				tf_login.setStyle("-fx-text-inner-color: dc320c;");
				lb_connection.setText(Lang_bundle.getString("Erreur.de.connexion"));
				lb_connection.setTextFill(Color.web("#dc320c"));
				connectAdmin = false;
			}
		} else {
			deconnection();
		}
	}

	/**
	 * Methode de deconnection
	 */
	private void deconnection() {
		tab_administration.setDisable(true);
		tabpane_onglets.getSelectionModel().select(0);
		lb_connection.setText("");
		btn_connect.setText(Lang_bundle.getString("Connexion"));
		tf_login.setDisable(false);
		pwf_mdp.setDisable(false);
		tf_login.clear();
		pwf_mdp.clear();
		connectAdmin = false;
		connectUser = false;
		btn_new_groupe.setDisable(true);
		btn_new_event.setDisable(true);
		btn_supp_groupe.setDisable(true);
		btn_supp_event.setDisable(true);
	}

	/*
	 * =========================================================================
	 * MENU
	 */

	private Parametres param;
	protected boolean lang_modif = false;

	/**
	 * Methode de configuration de la langue du programme Redémarre le programme
	 * pour prendre en compte les modifications.
	 */
	@FXML
	private void change_lang() {
		if (MainApp.getInstance().parametresData.isEmpty()) {
			param = new Parametres();
		} else {
			param = MainApp.getInstance().parametresData.get(0);
		}
		MainApp.getInstance().showLangueDialog(param);
		MainApp.getInstance().choise_lang(MainApp.getInstance().parametresData.get(0).getLangue());
		if (lang_modif) {
			MainApp.getInstance().primaryStage.close();
			MainApp.getInstance().start(MainApp.getInstance().primaryStage);
		}
	}

	/**
	 * Methode pour quitter l'application
	 */
	@FXML
	private void quitter() {
		result = Validateur.showPopup(AlertType.CONFIRMATION, Lang_bundle.getString("Quitter"),
				Lang_bundle.getString("Confirmation.de.fermeture"),
				Lang_bundle.getString("Etes-vous.sûr.de.vouloir.quitter.?")).showAndWait();
		if (result.get() == ButtonType.OK) {
			MainApp.getInstance().primaryStage.close();
		}
	}

	/*
	 * =========================================================================
	 * ONGLET ACCUEIL
	 */

	@FXML
	private CustomTextField cst_tf_search;
	@FXML
	private VBox vb_link = new VBox();

	@FXML
	private void searchBar() {
		vb_link.getChildren().clear();

		if (!cst_tf_search.getText().equals("")) {
			/*
			 * try { Session s = HibernateSetUp.getSession(); FullTextSession
			 * fullTextSession = Search.getFullTextSession(s); Transaction tx =
			 * fullTextSession.beginTransaction();
			 * 
			 * QueryBuilder qb =
			 * fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(
			 * Groupe.class).get(); org.apache.lucene.search.Query query =
			 * qb.keyword().onFields("nom_groupe")
			 * .matching(cst_tf_search.getText()).createQuery();
			 * org.hibernate.Query hibQuery =
			 * fullTextSession.createFullTextQuery(query, Groupe.class);
			 * 
			 * @SuppressWarnings("unchecked") List<Groupe> result1 =
			 * hibQuery.list();
			 * 
			 * qb =
			 * fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(
			 * Personne.class).get(); query =
			 * qb.keyword().onFields("nom_membre",
			 * "prenom_membre").matching(cst_tf_search.getText())
			 * .createQuery(); hibQuery =
			 * fullTextSession.createFullTextQuery(query, Personne.class);
			 * 
			 * @SuppressWarnings("unchecked") List<Personne> result2 =
			 * hibQuery.list();
			 * 
			 * qb =
			 * fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(
			 * Titre.class).get(); query = qb.keyword().onFields("titre",
			 * "annee").matching(cst_tf_search.getText()).createQuery();
			 * hibQuery = fullTextSession.createFullTextQuery(query,
			 * Titre.class);
			 * 
			 * @SuppressWarnings("unchecked") List<Titre> result3 =
			 * hibQuery.list();
			 * 
			 * qb =
			 * fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(
			 * Rencontre.class).get(); query = qb.keyword().onFields("nom_renc",
			 * "ville_renc", "periodicite_renc")
			 * .matching(cst_tf_search.getText()).createQuery(); hibQuery =
			 * fullTextSession.createFullTextQuery(query, Rencontre.class);
			 * 
			 * @SuppressWarnings("unchecked") List<Rencontre> result4 =
			 * hibQuery.list();
			 * 
			 * qb =
			 * fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(
			 * Organisateur.class).get(); query =
			 * qb.keyword().onFields("nom_orga", "prenom_orga",
			 * "entreprise_orga")
			 * .matching(cst_tf_search.getText()).createQuery(); hibQuery =
			 * fullTextSession.createFullTextQuery(query, Organisateur.class);
			 * 
			 * @SuppressWarnings("unchecked") List<Organisateur> result5 =
			 * hibQuery.list();
			 * 
			 * if (!result1.isEmpty()) { Label ctgr_groupe = new
			 * Label(Lang_bundle.getString("Ctgr.groupe"));
			 * vb_link.getChildren().add(ctgr_groupe); for (Groupe groupe :
			 * result1) { Hyperlink link_groupe = new
			 * Hyperlink(groupe.getNom_groupe()); link_groupe.setOnAction(new
			 * EventHandler<ActionEvent>() {
			 * 
			 * @Override public void handle(ActionEvent event) {
			 * MainApp.getInstance().showFicheGroupeEditDialog(groupe, true, 0);
			 * } }); vb_link.getChildren().add(link_groupe); } } if
			 * (!result2.isEmpty()) { Line ligne = new Line(0, 0, 588, 0); Label
			 * ctgr_personne = new
			 * Label(Lang_bundle.getString("Ctgr.personne"));
			 * vb_link.getChildren().add(ligne);
			 * vb_link.getChildren().add(ctgr_personne); for (Personne personne
			 * : result2) { Hyperlink link_personne = new
			 * Hyperlink(personne.getNom_membre());
			 * link_personne.setOnAction(new EventHandler<ActionEvent>() {
			 * 
			 * @Override public void handle(ActionEvent event) {
			 * MainApp.getInstance().showFicheGroupeEditDialog(personne.
			 * getGroupe(), true, 1); } });
			 * vb_link.getChildren().add(link_personne); } } if
			 * (!result3.isEmpty()) { Line ligne = new Line(0, 0, 588, 0); Label
			 * ctgr_titre = new Label(Lang_bundle.getString("Ctgr.titre"));
			 * vb_link.getChildren().add(ligne);
			 * vb_link.getChildren().add(ctgr_titre); for (Titre titre :
			 * result3) { Hyperlink link_titre = new
			 * Hyperlink(titre.getTitre()); link_titre.setOnAction(new
			 * EventHandler<ActionEvent>() {
			 * 
			 * @Override public void handle(ActionEvent event) {
			 * MainApp.getInstance().showFicheGroupeEditDialog(titre.getGroupe()
			 * , true, 2); } }); vb_link.getChildren().add(link_titre); } } if
			 * (!result4.isEmpty()) { Line ligne = new Line(0, 0, 588, 0); Label
			 * ctgr_rencontre = new
			 * Label(Lang_bundle.getString("Ctgr.rencontre"));
			 * vb_link.getChildren().add(ligne);
			 * vb_link.getChildren().add(ctgr_rencontre); for (Rencontre
			 * rencontre : result4) { Hyperlink link_rencontre = new
			 * Hyperlink(rencontre.getNom_renc());
			 * link_rencontre.setOnAction(new EventHandler<ActionEvent>() {
			 * 
			 * @Override public void handle(ActionEvent event) {
			 * MainApp.getInstance().showFicheEventEditDialog(rencontre, true,
			 * 0); } }); vb_link.getChildren().add(link_rencontre); }
			 * 
			 * } if (!result5.isEmpty()) { Line ligne = new Line(0, 0, 588, 0);
			 * Label ctgr_organisateur = new
			 * Label(Lang_bundle.getString("Ctgr.organisateur"));
			 * vb_link.getChildren().add(ligne);
			 * vb_link.getChildren().add(ctgr_organisateur); for (Organisateur
			 * organisateur : result5) { Hyperlink link_organisateur = new
			 * Hyperlink(organisateur.getNom_orga());
			 * link_organisateur.setOnAction(new EventHandler<ActionEvent>() {
			 * 
			 * @Override public void handle(ActionEvent event) {
			 * MainApp.getInstance().showFicheEventEditDialog(organisateur.
			 * getRencontre(), true, 1); } });
			 * vb_link.getChildren().add(link_organisateur); } } tx.commit();
			 * s.close(); } catch (Exception e) { // ommition de la recherche a
			 * vide }
			 */
		}
	}

	/*
	 * =========================================================================
	 * ONGLET REPERTOIRE
	 */

	@FXML
	protected TableView<Groupe> tv_reper;
	@FXML
	private TableColumn<Groupe, String> nom_groupeColumn;
	@FXML
	private Label lb_nom_groupe;
	@FXML
	private Label lb_carac_groupe;
	@FXML
	private Label lb_region_groupe;
	@FXML
	private Label lb_pays_groupe;
	@FXML
	private Button btn_new_groupe;
	@FXML
	private Button btn_supp_groupe;
	@FXML
	private Label lb_nb_membre;
	@FXML
	private Label lb_nb_titre;
	@FXML
	private Label lb_nb_event_futur;
	@FXML
	private Label lb_nb_event_passe;
	@FXML
	private ImageView img_view_main;
	private ObservableList<Rencontre> rencontreDataTri = FXCollections.observableArrayList();
	public ObservableList<Rencontre> rencontreDataF = FXCollections.observableArrayList();
	public ObservableList<Rencontre> rencontreDataP = FXCollections.observableArrayList();
	private Date auj = new Date();
	private Image imageOrigine = new Image("file:src/img/cd_music.png");

	/**
	 * Appelé quand l'utilisateur clique sur le bouton Nouveau de l'onglet
	 * repertoire de la fenetre principale. Fait appel à la méthode
	 * <code>showFicheGroupeEditDialog</code> Fait appel à la méthode
	 * <code>getGroupeData</code> Sauve le groupe en base de donnée via
	 * Hibernate.
	 */
	@FXML
	private void nouveauGroupe() {
		Groupe tempGroupe = new Groupe();
		MainApp.getInstance().showFicheGroupeEditDialog(tempGroupe, false, 0);
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton Editer de l'onglet
	 * repertoire de la fenetre principale. Fait appel à la méthode
	 * <code>showFicheGroupeEditDialog</code> Fait appel à la méthode
	 * <code>showGroupeDetails</code> Necessite de selectionner un groupe dans
	 * la liste de groupes autrement un message d'erreur apparait.
	 */
	@FXML
	private void editGroupe() {
		Groupe selectedGroupe = tv_reper.getSelectionModel().getSelectedItem();
		if (selectedGroupe != null) {
			MainApp.getInstance().showFicheGroupeEditDialog(selectedGroupe, true, 0);
		} else {
			Validateur.showPopup(AlertType.WARNING, Lang_bundle.getString("Attention"),
					Lang_bundle.getString("Aucun.item.selectionne"),
					Lang_bundle.getString("Veuillez.selectionner.un.item")).showAndWait();
		}
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton Supprimer de l'onglet
	 * repertoire de la fenetre principale. Necessite de selectionner un groupe
	 * dans la liste de groupes autrement un message d'erreur apparait.
	 */
	@FXML
	private void supprimerGroupe() {
		result = Validateur.showPopup(AlertType.CONFIRMATION, Lang_bundle.getString("Confirmation.d'action"),
				Lang_bundle.getString("Confirmation.de.suppression"), Lang_bundle.getString("Confirmation.groupe.supp"))
				.showAndWait();
		if (result.get() == ButtonType.OK) {
			tv_reper.getItems().remove(tv_reper.getSelectionModel().getSelectedIndex());
			try {
				if (!tv_reper.getItems().isEmpty()) {
					Crud.Serialize(tv_reper.getItems());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Methode appelée par la méthode <code>editGroupe</code> pour renseigner
	 * les labels de l'onglet repertoire de la fenetre principale.
	 */
	protected void showGroupeDetails(Groupe groupe) {
		rencontreDataTri.clear();
		rencontreDataF.clear();
		rencontreDataP.clear();

		if (groupe != null) {
			lb_nom_groupe.setText(groupe.getNom_groupe());
			lb_carac_groupe.setText(groupe.getCarac_groupe());
			lb_pays_groupe.setText(groupe.getPays_groupe());
			lb_region_groupe.setText(groupe.getRegion_groupe());
			lb_nb_membre.setText(String.valueOf(groupe.getListe_personne().size()));
			lb_nb_titre.setText(String.valueOf(groupe.getListe_titre().size()));

			for (Rencontre rencontre : tv_planif.getItems()) {
				for (Representation repre : rencontre.getListe_representation()) {
					if (groupe.getNom_groupe().equals(repre.getNom_Groupe())) {
						rencontreDataTri.add(rencontre);
					}
				}
			}

			for (Rencontre rencTri : rencontreDataTri) {
				if (rencTri.getDate_fin_renc().getTime() > auj.getTime()) {
					rencontreDataF.add(rencTri);
				} else {
					rencontreDataP.add(rencTri);
				}
			}

			lb_nb_event_futur.setText(String.valueOf(rencontreDataF.size()));
			lb_nb_event_passe.setText(String.valueOf(rencontreDataP.size()));
			if (groupe.getImage() != null) {
				img_view_main.setImage(FileUtils.convertByteToImage(groupe.getImage()));
			} else {
				img_view_main.setImage(imageOrigine);
			}
		} else {
			lb_nom_groupe.setText("");
			lb_carac_groupe.setText("");
			lb_pays_groupe.setText("");
			lb_region_groupe.setText("");
			lb_nb_membre.setText("");
			lb_nb_titre.setText("");
			lb_nb_event_futur.setText("");
			lb_nb_event_passe.setText("");
		}
	}

	/*
	 * =========================================================================
	 * ONGLET PLANIFICATION
	 */

	@FXML
	protected TableView<Rencontre> tv_planif;
	@FXML
	private TableColumn<Rencontre, String> col_event;
	@FXML
	private TableColumn<Rencontre, String> col_ville;
	@FXML
	private Label lb_lieu;
	@FXML
	private Label lb_date_deb;
	@FXML
	private Label lb_date_fin;
	@FXML
	private Label lb_perio;
	@FXML
	private Label lb_nb_pers;
	@FXML
	private Button btn_new_event;
	@FXML
	private Button btn_supp_event;
	@FXML
	private Label lb_nb_orga;
	@FXML
	private Label lb_nb_repre;
	private SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Appelé quand l'utilisateur clique sur le bouton Nouveau de l'onglet
	 * planification de la fenetre principale. Fait appel à la méthode
	 * <code>showFicheEventEditDialog</code> Fait appel à la méthode
	 * <code>getRencontreData</code>
	 */
	@FXML
	private void nouvelEvent() {
		Rencontre tempRencontre = new Rencontre();
		MainApp.getInstance().showFicheEventEditDialog(tempRencontre, false, 0);
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton Editer de l'onglet
	 * planification de la fenetre principale. Fait appel à la méthode
	 * <code>showFicheEventEditDialog</code> Fait appel à la méthode
	 * <code>showEventDetails</code> Necessite de selectionner une rencontre
	 * dans la liste de rencontres autrement un message d'erreur apparait.
	 */
	@FXML
	private void editEvent() {
		Rencontre selectedRencontre = tv_planif.getSelectionModel().getSelectedItem();
		if (selectedRencontre != null) {
			MainApp.getInstance().showFicheEventEditDialog(selectedRencontre, true, 0);
		} else {
			Validateur.showPopup(AlertType.WARNING, Lang_bundle.getString("Attention"),
					Lang_bundle.getString("Aucun.item.selectionne"),
					Lang_bundle.getString("Veuillez.selectionner.un.item")).showAndWait();
		}
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton Supprimer de l'onglet
	 * planification de la fenetre principale. Necessite de selectionner une
	 * rencontre dans la liste de rencontres autrement un message d'erreur
	 * apparait.
	 */
	@FXML
	private void supprimerEvent() {
		result = Validateur.showPopup(AlertType.CONFIRMATION, Lang_bundle.getString("Confirmation.d'action"),
				Lang_bundle.getString("Confirmation.de.suppression"),
				Lang_bundle.getString("Confirmation.rencontre.supp")).showAndWait();
		if (result.get() == ButtonType.OK) {
			tv_planif.getItems().remove(tv_planif.getSelectionModel().getSelectedIndex());
			try {
				if (!tv_planif.getItems().isEmpty()) {
					Crud.Serialize(tv_planif.getItems());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Methode appelée par la méthode <code>editEvent</code> pour renseigner les
	 * labels de l'onglet planification de la fenetre principale.
	 */
	protected void showEventDetails(Rencontre rencontre) {
		if (rencontre != null) {
			lb_lieu.setText(rencontre.getLieu_renc());
			lb_date_deb.setText(simpleFormat.format(rencontre.getDate_deb_renc()));
			lb_date_fin.setText(simpleFormat.format(rencontre.getDate_fin_renc()));
			lb_nb_pers.setText(rencontre.getNb_pers_attendues().toString());
			lb_perio.setText(rencontre.getPeriodicite_renc());
			lb_nb_orga.setText(String.valueOf(rencontre.getListe_organisateur().size()));
			lb_nb_repre.setText(String.valueOf(rencontre.getListe_representation().size()));
		} else {
			lb_lieu.setText("");
			lb_date_deb.setText("");
			lb_date_fin.setText("");
			lb_nb_pers.setText("");
			lb_perio.setText("");
			lb_nb_orga.setText("");
			lb_nb_repre.setText("");
		}
	}

	/*
	 * =========================================================================
	 * ONGLET ADMINISTRATION
	 */

	@FXML
	private Tab tab_administration;
	@FXML
	private TableView<User> tv_admin;
	@FXML
	private TableColumn<User, String> col_login;
	@FXML
	private TableColumn<User, String> col_mdp;
	@FXML
	private TableColumn<User, String> col_droits;
	@FXML
	private TextField tf_admin_login;
	@FXML
	private TextField tf_admin_mdp;
	@FXML
	private ToggleSwitch ts_adm_user;
	@FXML
	private Label lb_nb_user;
	@FXML
	private Label lb_nb_admin;
	@FXML
	private Label lb_nb_total;

	/**
	 * Récupération du nombre d'utilisateurs et d'administrateurs
	 */
	private void countUser() {
		compteurAdmin = 0;
		compteurUser = 0;

		for (User user : tv_admin.getItems()) {
			if (user.getDroit_auth().equals("administrateur")) {
				compteurAdmin++;
			} else {
				compteurUser++;
			}
		}

		lb_nb_admin.setText(String.valueOf(compteurAdmin));
		lb_nb_user.setText(String.valueOf(compteurUser));
		lb_nb_total.setText(String.valueOf(compteurAdmin + compteurUser));
	}

	/**
	 * Methode executée lorsque l'utilisateur clique sur le bouton Créer.
	 * Renseigne les attributs de l'objet User.
	 */
	@FXML
	private void creerModifierUser() {
		User user = new User();
		user.setLogin(tf_admin_login.getText());
		if (tf_admin_mdp.getText().length() != 0) {
			user.setMot_de_passe(CryptEtDecrypt.getSecurePassword(tf_admin_mdp.getText(), salt));
		}
		if (!ts_adm_user.isSelected()) {
			user.setDroit_auth("administrateur");
		} else {
			user.setDroit_auth("utilisateur");
		}
		try {
			user.validateObject();
			tv_admin.getItems().add(user);
			Crud.Serialize(tv_admin.getItems());
			annulerUser();
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
	private void annulerUser() {
		tf_admin_login.clear();
		tf_admin_mdp.clear();
		if (!ts_adm_user.isSelected()) {
			ts_adm_user.setSelected(true);
		}
		tv_admin.getSelectionModel().clearSelection();
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton supprimer. Supprime
	 * l'utilisateur dans la liste à l'index sélectionné.
	 */
	@FXML
	private void supprimerUser() {
		if (tv_admin.getSelectionModel().getSelectedItem().getLogin().equals(tf_login.getText())) {
			result = Validateur.showPopup(AlertType.CONFIRMATION, Lang_bundle.getString("Confirmation.d'action"),
					Lang_bundle.getString("Confirmation.de.suppression"), Lang_bundle.getString("Login.deja.utilise"))
					.showAndWait();
			if (result.get() == ButtonType.OK) {
				tv_admin.getItems().remove(tv_admin.getSelectionModel().getSelectedItem());
				annulerUser();
				deconnection();
			}
		} else {
			result = Validateur.showPopup(AlertType.CONFIRMATION, Lang_bundle.getString("Confirmation.d'action"),
					Lang_bundle.getString("Confirmation.de.suppression"),
					Lang_bundle.getString("Voulez-vous.supprimer.utilisateur.?")).showAndWait();
			if (result.get() == ButtonType.OK) {
				tv_admin.getItems().remove(tv_admin.getSelectionModel().getSelectedItem());
				annulerUser();
			}
		}
		try {
			if (!tv_admin.getItems().isEmpty()) {
				Crud.Serialize(tv_admin.getItems());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
