package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.ProductVO;

public class SalesWatchSubController implements Initializable {
	@FXML
	private Label lblTitle;
	@FXML
	private Label lblPNum;
	@FXML
	private Label lblPName;
	@FXML
	private Label lblPPrice;
	@FXML
	private Label lblPSize;
	@FXML
	private Label lblPGrt;
	@FXML
	private Label lblPDate;
	@FXML
	private TextField txtPNum;
	@FXML
	private TextField txtPName;
	@FXML
	private TextField txtPPrice;
	@FXML
	private TextField txtPSize;
	@FXML
	private TextField txtPGrt;
	@FXML
	private TextField txtPDate;
	@FXML
	private Button btnSelProduct;

	private ProductVO pvo;

	private Stage stage;

	private Popup popup;

	private SalesTradeTabController sttController;

	public void setPvo(ProductVO pvo) {
		this.pvo = pvo;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setPopup(Popup popup) {
		this.popup = popup;
	}

	public void setSttController(SalesTradeTabController sttController) {
		this.sttController = sttController;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setTheme();
		setEditable(false);
	}

	/**
	 * handlePopup() : 팝업 출력
	 * 
	 * @param parent 팝업에 설정할 로드된 FXML
	 */
	public void handlePopup(Parent parent) {
		Button btnSelProduct = (Button) parent.lookup("#btnSelProduct");

		btnSelProduct.setOnAction((e) -> {
			if (btnSelProduct())
				popup.hide();
		});

		popup.getContent().add(parent);
		// 다른 윈도우로 포커스를 이동하면 Popup은 자동으로 닫히도록 한다.
		popup.setAutoHide(true);
		popup.show(stage);
	}

	/**
	 * btnSelProduct() : 주문하기 탭으로 제품명과, 단가를 전달
	 * 
	 * @return success 값이 전달되었는지 여부를 반환
	 */
	public boolean btnSelProduct() {
		boolean success = false;
		try {
			sttController.setPvo(pvo);
			sttController.setField();
			success = true;
		} catch (Exception e) {
			System.out.println("btnSelProduct() error = " + e.getMessage());
		}

		return success;
	}

	/**
	 * setPInfo() : 제품의 정보를 각 필드에 설정한다.
	 * 
	 */
	public void setPInfo() {
		txtPNum.setText(pvo.getP_num());
		txtPName.setText(pvo.getP_name());
		txtPPrice.setText(pvo.getP_price() + "");
		txtPSize.setText(pvo.getP_size());
		txtPGrt.setText(pvo.getP_grt());
		txtPDate.setText(pvo.getP_date());
	}

	/**
	 * setEditable() : 각 필드의 수정 여부를 설정
	 * 
	 * @param bool true면 수정가능, false면 수정불가
	 */
	private void setEditable(boolean bool) {
		txtPNum.setEditable(bool);
		txtPName.setEditable(bool);
		txtPPrice.setEditable(bool);
		txtPSize.setEditable(bool);
		txtPGrt.setEditable(bool);
		txtPDate.setEditable(bool);
	}

	/**
	 * setTheme() : 설정한 테마에 맞춰서, 색깔을 설정
	 * 
	 */
	private void setTheme() {
		StringBuffer lblColor = new StringBuffer();
		lblColor.append("-fx-text-fill: ");

		if (LoginMainController.isTheme()) {
			lblColor.append("black;");
		} else {
			lblColor.append("white;");
		}
		
		lblTitle.setStyle(lblColor.toString());
		lblPNum.setStyle(lblColor.toString());
		lblPName.setStyle(lblColor.toString());
		lblPPrice.setStyle(lblColor.toString());
		lblPSize.setStyle(lblColor.toString());
		lblPGrt.setStyle(lblColor.toString());
		lblPDate.setStyle(lblColor.toString());
	}
}
