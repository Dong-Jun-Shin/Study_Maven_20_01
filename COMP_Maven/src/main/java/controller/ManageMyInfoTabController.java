package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.DataUtil;
import model.DealerVO;

public class ManageMyInfoTabController implements Initializable {
	@FXML
	private ImageView imgMyInfo;
	@FXML
	private TextField txtDName;
	@FXML
	private TextField txtDId;
	@FXML
	private PasswordField pwDPasswd;
	@FXML
	private TextField txtDEId;
	@FXML
	private PasswordField pwDEPasswd;
	@FXML
	private TextField txtDPhone;
	@FXML
	private TextField txtDAddress;
	@FXML
	private TextField txtDBOwner;
	@FXML
	private TextField txtDBNum;
	@FXML
	private TextField txtDBName;
	@FXML
	private Button btnDUpdate;

	private DealerVO dvo = DealerVO.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 아이콘 설정
		String imageName = DataUtil.getImgPath();
		Image localImage = new Image(imageName + "d_id_card.png", 40, 40, false, false);
		imgMyInfo.setImage(localImage);

		dvo = DealerVO.getInstance();

		// 각 필드에 판매업체 정보를 설정
		txtDName.setText(dvo.getDName());
		txtDId.setText(dvo.getDId());
		txtDEId.setText(dvo.getDEId());
		pwDEPasswd.setText(dvo.getDEPw());
		txtDPhone.setText(dvo.getDPhone());
		txtDAddress.setText(dvo.getDAddress());
		txtDBOwner.setText(dvo.getDBOwner());
		txtDBNum.setText(dvo.getDBNum());
		txtDBName.setText(dvo.getDBName());
	}

	/**
	 * btnDUpdate() : 판매업체의 정보가 파일을 통해 수정 및 기록된다.
	 * 
	 * @param event
	 */
	public void btnDUpdate(ActionEvent event) {
		// 각 필드의 유효성 체크
		if (!DataUtil.validityCheck(txtDName.getText(), "업체명") || !DataUtil.valLimitCheck(txtDName.getText(), 30)) {
			return;
		} else if (!DataUtil.validityCheck(txtDEId.getText(), "Email ID")
				|| !DataUtil.valLimitCheck(txtDEId.getText(), 30)) {
			return;
		} else if (!DataUtil.validityCheck(pwDEPasswd.getText(), "Email PW")
				|| !DataUtil.valLimitCheck(pwDEPasswd.getText(), 30)) {
			return;
		} else if (!DataUtil.validityCheck(txtDPhone.getText(), "전화번호")
				|| !DataUtil.valLimitCheck(txtDPhone.getText(), 13)) {
			return;
		} else if (txtDAddress.getText().equals("") && !DataUtil.valLimitCheck(txtDAddress.getText(), 60)) {
			DataUtil.validityCheck("", "주소를");
		} else if (!DataUtil.validityCheck(txtDBOwner.getText(), "계좌주")
				|| !DataUtil.valLimitCheck(txtDBOwner.getText(), 12)) {
			return;
		} else if (!DataUtil.validityCheck(txtDBNum.getText(), "계좌번호")
				|| !DataUtil.valLimitCheck(txtDBNum.getText(), 16)) {
			return;
		} else if (!DataUtil.validityCheck(txtDBName.getText(), "계좌주")
				|| !DataUtil.valLimitCheck(txtDBName.getText(), 30)) {
			return;
		} else if (pwDPasswd.getText().equals(dvo.getDPasswd())) {
			// 각 필드의 내용을 dvo에 설정
			dvo.setDEId(txtDEId.getText());
			dvo.setDEPw(pwDEPasswd.getText());
			dvo.setDName(txtDName.getText());
			dvo.setDPhone(txtDPhone.getText());
			dvo.setDAddress(txtDAddress.getText());
			dvo.setDBOwner(txtDBOwner.getText());
			dvo.setDBNum(txtDBNum.getText());
			dvo.setDBName(txtDBName.getText());

			// 판매없체의 정보를 파일로 쓰기
			try (ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File("C:\\COMP\\data\\DealerVO.dat")))) {
				// 판매업체 정보 쓰기
				oos.writeObject(dvo);
				pwDPasswd.clear();
				DataUtil.showInfoAlert("정보 변경 성공", "변경된 정보가 적용되었습니다.");
			} catch (IOException io) {
				System.out.println("btnDUpdate() error = " + io.getMessage());
			} catch (Exception e) {
				System.out.println("btnDUpdate() error = " + e.getMessage());
			}
		} else {
			DataUtil.showAlert("정보 변경 실패", "비밀번호가 일치하지 않습니다.");
		}
	}
}
