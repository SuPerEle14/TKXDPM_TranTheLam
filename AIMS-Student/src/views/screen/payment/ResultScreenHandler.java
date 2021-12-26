package views.screen.payment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.payment.PaymentTransaction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;

public class ResultScreenHandler extends BaseScreenHandler {

	private String result;
	private String message;

	public ResultScreenHandler(Stage stage, String screenPath, PaymentTransaction trans) throws IOException {
		super(stage, screenPath);
		if(trans == null) {
			result = "FAIL!";
			message = "Transaction fail!";
		}
		else {
			result = "Successful Transaction!";
			message = "Transaction code: " + trans.getTransactionId();			
		}
		resultLabel.setText(result);
		messageLabel.setText(message);
	}

	@FXML
	private Label pageTitle;

	@FXML
	private Label resultLabel;

	@FXML
	private Button okButton;
	
	@FXML
	private Label messageLabel;

	@FXML
	void confirmPayment(MouseEvent event) throws IOException {
		homeScreenHandler.show();
	}

}
