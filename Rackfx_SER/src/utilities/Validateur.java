package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Validateur {

	private static Stage popup;

	/**
	 * Méthode d'affichage de popup de messages d'erreurs.
	 * 
	 * @param type
	 * @param titre
	 * @param headerText
	 * @param message
	 * @return alert
	 */
	public static Alert showPopup(AlertType type, String titre, String headerText, String message) {
		Alert alert = new Alert(type);
		alert.initOwner(popup);
		alert.setTitle(titre);
		alert.setHeaderText(headerText);
		alert.setContentText(message);
		return alert;
	}
}
