package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.DataUtil;
import model.DealerVO;

public class LoginSubController implements Initializable {
	@FXML
	private ToggleGroup groupSearch;
	@FXML
	private TextField txtBOwner;
	@FXML
	private TextField txtBNum;
	@FXML
	private TextField txtSearchID;
	@FXML
	private Button btnIdSearch;
	@FXML
	private HBox idBox;

	private DealerVO dVO = DealerVO.getInstance();

	private Stage dialog;

	public void setDialog(Stage dialog) {
		this.dialog = dialog;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * idPwSearch() : 라디오버튼에 따른 ID 텍스트 필드 숨기기
	 * 
	 * @param event
	 */
	public void idPwSearch(ActionEvent event) {
		if (groupSearch.getSelectedToggle().getUserData().toString().equals("pwSearch")) {
			idBox.setVisible(true);
		} else if (groupSearch.getSelectedToggle().getUserData().toString().equals("idSearch")) {
			idBox.setVisible(false);
		}
	}

	/**
	 * btnIdSearch() : ID & PW 찾기
	 * 
	 * @param event
	 */
	public void btnIdSearch(ActionEvent event) {
		// 유효성검사
		if (!DataUtil.validityCheck(txtBOwner.getText(), "계좌주를 "))
			return;
		else if (!DataUtil.validityCheck(txtBNum.getText(), "계좌번호를 "))
			return;
		else if (idBox.isVisible() && !DataUtil.validityCheck(txtSearchID.getText(), "ID를 "))
			return;

		// 입력 정보를 판단 후, 결과 출력
		StringBuffer sb = new StringBuffer();
		if (txtBOwner.getText().equals(dVO.getDBOwner()) && txtBNum.getText().equals(dVO.getDBNum())) {
			sb.append("ID : ");
			sb.append(dVO.getDId());
			if (txtSearchID.getText().equals(dVO.getDId())) {
				sb.append("\nPW : ");
				sb.append(dVO.getDPasswd());
				DataUtil.showAlert("ID & PW 결과", sb.toString());
				dialog.close();
			} else if (idBox.isVisible()) {
				sb = new StringBuffer();
				sb.append("ID가 잘못되었습니다.\n");
				sb.append("다시 입력해주세요.\n");
			}
		} else {
			sb.append("계좌주 또는 계좌번호가 잘못되었습니다.\n");
			sb.append("다시 입력해주세요.\n");
		}
		DataUtil.showAlert("ID & PW 결과", sb.toString());
	}
}
