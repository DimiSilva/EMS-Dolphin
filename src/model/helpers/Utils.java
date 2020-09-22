package model.helpers;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import model.enums.messages.Shared;
import model.exceptions.EncryptionException;

public class Utils {
	public static String hashString(String stringToHash) throws EncryptionException {
		try {
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			
			KeySpec spec = new PBEKeySpec(stringToHash.toCharArray(), salt, 65536, 128);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			
			byte[] hash = factory.generateSecret(spec).getEncoded();
			return hash.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new EncryptionException(e.getMessage());
		}
	}
	
	public static void showErrorAlert(String title, String header, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		
		if(title != null) alert.setTitle("Erro!");
		if(header != null) alert.setHeaderText(Shared.SOMETHING_WENT_WRONG.getText());
		if(message != null) alert.setContentText(message);
			
		alert.showAndWait();
	}
	public static boolean showConfirmAlert(String title, String message, String confirmText, String cancelText) {
		ButtonType foo = new ButtonType(confirmText, ButtonBar.ButtonData.OK_DONE);
		ButtonType bar = new ButtonType(cancelText, ButtonBar.ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.NONE,
				message,
		        foo,
		        bar);

		alert.setTitle(title);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.orElse(bar) == foo) {
		   return true;
		}else {
			return false;
		}
	}
}
