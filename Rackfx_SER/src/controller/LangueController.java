package controller;

import java.io.IOException;
import java.io.InvalidObjectException;

import org.controlsfx.control.ToggleSwitch;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Parametres;
import utilities.Crud;
import utilities.Res_listes;

public class LangueController {

	@FXML
	private ComboBox<String> langue_cmbbox = new ComboBox<>(Res_listes.liste_langues);
	@FXML
	private ToggleSwitch ts_theme;
	private Parametres param;
	private Stage dialogStage;

	/* Singleton */
	/** Instance unique pré-initialisée */
	private static LangueController INSTANCE_LANG_CONTROLLER;

	/** Point d'accès pour l'instance unique du singleton */
	public static LangueController getInstance() {
		return INSTANCE_LANG_CONTROLLER;
	}

	@FXML
	private void initialize() {
		INSTANCE_LANG_CONTROLLER = this;
		MainViewController.getInstance().lang_modif = false;
		langue_cmbbox.getItems().addAll(Res_listes.liste_langues);
	}

	/**
	 * Importe stage dans cette fenetre.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Methode d'affichage des paramètres contenus dans la base de donnée.
	 * 
	 * @param param
	 */
	public void setParametres(Parametres param) {
		this.param = param;
		if (param.getLangue() == null) {
			langue_cmbbox.getSelectionModel().selectFirst();
		} else {
			langue_cmbbox.getSelectionModel().select(param.getLangue());
		}
		if (param.getTheme() != null && param.getTheme().equals("Dark")) {
			ts_theme.setSelected(true);
		} else {
			ts_theme.setSelected(false);
		}
	}

	/**
	 * Methode d'annulation de changement de langue.
	 */
	@FXML
	private void annulerlangue() {
		dialogStage.close();
	}

	/**
	 * Methode de validation de changement de langue.
	 */
	@FXML
	private void validerlangue() {
		ObservableList<Parametres> paramData = FXCollections.observableArrayList();

		param.setLangue(langue_cmbbox.getSelectionModel().getSelectedItem());
		if (ts_theme.isSelected()) {
			param.setTheme("Dark");
		} else {
			param.setTheme("Modena");
		}
		try {
			param.validateObject();
			if (MainApp.getInstance().parametresData.isEmpty()) {
				MainApp.getInstance().parametresData.add(param);
			} else {
				MainApp.getInstance().parametresData.set(0, param);
			}
			paramData.add(param);
			Crud.Serialize(paramData);
			MainViewController.getInstance().lang_modif = true;
			dialogStage.close();
		} catch (InvalidObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
