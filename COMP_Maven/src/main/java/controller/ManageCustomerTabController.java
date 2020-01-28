package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.CustomerDAO;
import model.CustomerVO;
import model.DataUtil;

public class ManageCustomerTabController implements Initializable {
	@FXML
	private ImageView imgCustomer;
	@FXML
	private TextField txtCNum;
	@FXML
	private TextField txtCName;
	@FXML
	private TextField txtCId;
	@FXML
	private Button btnIdCheck;
	@FXML
	private PasswordField pwCPasswd;
	@FXML
	private TextField txtCPhone;
	@FXML
	private TextField txtCAddress;
	@FXML
	private TextField txtCBrith;
	@FXML
	private TextField txtCEmail;
	@FXML
	private Button btnCInsert;
	@FXML
	private Button btnCUpdate;
	@FXML
	private Button btnCDelete;
	@FXML
	private Button btnCClear;
	@FXML
	private ComboBox<String> cbxCSearchKey;
	@FXML
	private TextField txtCSearchValue;
	@FXML
	private Button btnCSearch;
	@FXML
	private TableView<CustomerVO> customerTableView;

	String selectedCustomerIndex;

	private static ObservableList<CustomerVO> customerDataList = FXCollections.observableArrayList();

	private CustomerDAO cdao = CustomerDAO.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 아이콘 설정
		String imageName = DataUtil.getImgPath();
		Image localImage = new Image(imageName + "id_card.png", 40, 40, false, false);
		imgCustomer.setImage(localImage);

		// 테이블뷰의 컬럼이름이 될 필드명을 가져온다.
		List<String> title = DataUtil.fieldName(new CustomerVO());
		// 컬럼 중 pw필드를 건너띈다.
		title.remove(3);

		// 설정을 받을 Table의 열
		for (int i = 0; i < title.size(); i++) {
			TableColumn<CustomerVO, ?> columnName = customerTableView.getColumns().get(i);
			columnName.setCellValueFactory(new PropertyValueFactory<>(title.get(i)));
		}

		// 테이블에 항목 설정
		customerTableView.setItems(customerDataList);

		// 콤보박스 설정
		setCbxList();
		// 거래처 전체 목록
		customerTotalList();
		setInsertBtn(true);
		clear();
	}

	/**
	 * setCNum() : 새로운 고객에게 부여될 다음 번호를 가져온다.
	 * 
	 * @param event
	 */
	public void setCNum(MouseEvent event) {
		StringBuffer sb = new StringBuffer();
		sb.append("C_");
		sb.append(cdao.getCustomerCount());
		txtCNum.setText(sb.toString());
	}

	/**
	 * btnIdCheck() : 고객 입력 시, 아이디 체크를 활성화한다.
	 *  
	 * @param event
	 */
	public void btnIdCheck(ActionEvent event) {
		//일치하는게 있으면 True, 없으면 False
		boolean success = cdao.customerLoginOverlap(txtCId.getText());
		
		if(!success) {
			DataUtil.showInfoAlert("ID 체크", "사용 가능한 ID입니다.");
			txtCId.setEditable(false);
			btnIdCheck.setDisable(true);
			btnCInsert.setDisable(false);
		}else {
			DataUtil.showAlert("ID 체크", "이미 사용중인 ID입니다.");
		}
	}
	
	/**
	 * btnCInsert() : 새로운 고객을 입력한다.
	 * 
	 * @param event
	 */
	public void btnCInsert(ActionEvent event) {
		boolean success = false;
		// 날짜 형식 유효성 체크
		StringBuffer sb = new StringBuffer();
		sb = DataUtil.dateCheck(txtCBrith.getText());

		try {
			if (!DataUtil.validityCheck(txtCNum.getText(), "고객번호")) {
				return;
			} else if (!DataUtil.validityCheck(txtCName.getText(), "고객명")
					|| !DataUtil.valLimitCheck(txtCName.getText(), 12)) {
				return;
			} else if (!DataUtil.validityCheck(txtCId.getText(), "ID")
					|| !DataUtil.valLimitCheck(txtCId.getText(), 30)) {
				return;
			} else if (!DataUtil.validityCheck(pwCPasswd.getText(), "PW")
					|| !DataUtil.valLimitCheck(pwCPasswd.getText(), 30)) {
				return;
			} else if (!DataUtil.validityCheck(txtCPhone.getText(), "전화번호")
					|| !DataUtil.valLimitCheck(txtCPhone.getText(), 13)) {
				return;
			} else if (txtCAddress.getText().equals("") && !DataUtil.valLimitCheck(txtCAddress.getText(), 60)) {
				DataUtil.validityCheck("", "주소");
			} else if (!DataUtil.validityCheck(txtCEmail.getText(), "이메일")
					|| !DataUtil.valLimitCheck(txtCEmail.getText(), 30)) {
				return;
			} else {
				CustomerVO cvo = new CustomerVO();
				cvo.setC_num(txtCNum.getText().trim());
				cvo.setC_name(txtCName.getText().trim());
				cvo.setC_id(txtCId.getText().trim());
				cvo.setC_pw(pwCPasswd.getText().trim());
				cvo.setC_phone(txtCPhone.getText().trim());
				cvo.setC_add(txtCAddress.getText().trim());
				cvo.setC_birth(sb.toString());
				cvo.setC_email(txtCEmail.getText().trim());

				success = cdao.customerInsert(cvo);

				if (success == true) {
					DataUtil.showInfoAlert("고객 등록 결과", "[" + txtCName.getText() + "]의 등록을 성공하였습니다.");
					reset();
				} else {
					DataUtil.showInfoAlert("고객 등록 결과", "고객의 정보 등록에 문제가 있어 완료하지 못하였습니다.");
				}
			}
		} catch (Exception e) {
			System.out.println("btnCInsert() error = " + e.getMessage());
		}
	}

	/**
	 * btnCUpdate() : 선택된 고객를 수정한다.
	 * 
	 * @param event
	 */
	public void btnCUpdate(ActionEvent event) {
		boolean success = false;

		try {
			if (!DataUtil.validityCheck(txtCNum.getText(), "고객번호")) {
				return;
			} else if (!DataUtil.validityCheck(txtCName.getText(), "고객명")
					|| !DataUtil.valLimitCheck(txtCName.getText(), 12)) {
				return;
			} else if (!DataUtil.validityCheck(txtCId.getText(), "ID")
					|| !DataUtil.valLimitCheck(txtCId.getText(), 30)) {
				return;
			} else if (!DataUtil.validityCheck(pwCPasswd.getText(), "PW")
					|| !DataUtil.valLimitCheck(pwCPasswd.getText(), 30)) {
				return;
			} else if (!DataUtil.validityCheck(txtCPhone.getText(), "전화번호")
					|| !DataUtil.valLimitCheck(txtCPhone.getText(), 13)) {
				return;
			} else if (txtCAddress.getText().equals("") && !DataUtil.valLimitCheck(txtCAddress.getText(), 60)) {
				DataUtil.validityCheck("", "주소");
			} else if (!DataUtil.validityCheck(txtCEmail.getText(), "이메일")
					|| !DataUtil.valLimitCheck(txtCEmail.getText(), 30)) {
				return;
			} else {
				CustomerVO cvo = new CustomerVO();
				cvo.setC_num(txtCNum.getText().trim());
				cvo.setC_name(txtCName.getText().trim());
				cvo.setC_id(txtCId.getText().trim());
				cvo.setC_pw(pwCPasswd.getText().trim());
				cvo.setC_phone(txtCPhone.getText().trim());
				cvo.setC_add(txtCAddress.getText().trim());
				cvo.setC_email(txtCEmail.getText().trim());

				success = cdao.customerUpdate(cvo);

				if (success == true) {
					DataUtil.showInfoAlert("고객 수정 결과", "[" + txtCName.getText() + "]의 수정을 성공하였습니다.");
					reset();
				} else {
					DataUtil.showInfoAlert("고객 수정 결과", "고객의 정보 등록에 문제가 있어 수정을 완료하지 못하였습니다.");
				}
			}
		} catch (Exception e) {
			System.out.println("btnCUpdate() error = " + e.getMessage());
		}
	}

	/**
	 * btnCDelete() : 선택된 고객을 삭제한다.
	 * 
	 * @param event
	 */
	public void btnCDelete(ActionEvent event) {
		boolean success = false;

		try {
			CustomerVO cvo = new CustomerVO();
			cvo.setC_num(selectedCustomerIndex);
			success = cdao.customerDelete(cvo);
		} catch (Exception e) {
			System.out.println("btnCDelete() error = " + e.getMessage());
		}

		if (success == true) {
			DataUtil.showInfoAlert("고객 삭제 결과", "[" + txtCName.getText() + "]의 삭제를 성공하였습니다.");
			reset();
		} else {
			DataUtil.showInfoAlert("고객 삭제 결과", "문제가 있어 삭제를 완료하지 못하였습니다.");
		}
	}

	/**
	 * btnCClear() : 탭의 모든 데이터와 필드를 초기화한다.
	 * 
	 * @param event
	 */
	public void btnCClear(ActionEvent event) {
		// 콤보박스 초기화
		cbxCSearchKey.getSelectionModel().clearSelection();

		// 테이블 인덱스 초기화
		customerTableView.getSelectionModel().select(null);
		selectedCustomerIndex = null;

		// 필드 초기화
		txtCSearchValue.setText("");

		reset();

	}

	/**
	 * btnCSearch() : 검색기준에 맞춰서 결과를 테이블 뷰에 보여준다.
	 * 
	 * @param event
	 */
	public void btnCSearch(ActionEvent event) {
		CustomerVO cvo = null;
		ArrayList<CustomerVO> list = null;

		try {
			if (txtCSearchValue != null && txtCSearchValue.getText().length() != 0) {
				switch (cbxCSearchKey.getValue().toString()) {
				case "고객번호":
					list = cdao.getCustomerSelected("c_num", txtCSearchValue.getText());
					break;
				case "고객명":
					list = cdao.getCustomerSelected("c_name", txtCSearchValue.getText());
					break;
				}
			} else {
				cbxCSearchKey.getSelectionModel().clearSelection();
				list = cdao.getCustomerTotalList();
			}

			customerDataList.removeAll(customerDataList);

			for (int index = 0; index < list.size(); index++) {
				// 결과 리스트에서 한 행을 가져다가 cvo에 대입
				cvo = list.get(index);
				// 한 행을 추가
				customerDataList.add(cvo);
			}
		} catch (NullPointerException npe) {
			DataUtil.showInfoAlert("검색 결과", "검색 구분을 선택해주세요.");
		} catch (Exception e) {
			System.out.println("btnCSearch() error = " + e.getMessage());
		}
	}

	/**
	 * customerTableView() : 테이블 뷰에서 행을 더블클릭 시, 해당 행의 값을 가져와서 보여준다.
	 * 
	 * @param event
	 */
	public void customerTableView(MouseEvent event) {
		if (event.getClickCount() == 2) {
			CustomerVO selectCustomer = customerTableView.getSelectionModel().getSelectedItem();
			if (selectCustomer != null) {
				selectedCustomerIndex = selectCustomer.getC_num();

				txtCNum.setText(selectCustomer.getC_num());
				txtCName.setText(selectCustomer.getC_name());
				txtCId.setText(selectCustomer.getC_id());
				pwCPasswd.setText(selectCustomer.getC_pw());
				txtCPhone.setText(selectCustomer.getC_phone());
				txtCAddress.setText(selectCustomer.getC_add());
				txtCBrith.setText(selectCustomer.getC_birth());
				txtCEmail.setText(selectCustomer.getC_email());

				setInsertBtn(false);
				txtCNum.setEditable(false);
				txtCName.setEditable(false);
				txtCBrith.setEditable(false);
			}
		}
	}

	/**
	 * customerTotalList() : 테이블뷰 레코드 출력(거래처 전체 리스트)
	 * 
	 */
	public void customerTotalList() {
		customerDataList.removeAll(customerDataList);
		CustomerVO cvo = null;
		ArrayList<CustomerVO> list;

		try {
			list = cdao.getCustomerTotalList();

			for (int index = 0; index < list.size(); index++) {
				// 결과 리스트에서 한 행을 가져다가 svo에 대입
				cvo = list.get(index);
				// 한 행을 추가
				customerDataList.add(cvo);
			}
		} catch (Exception e) {
			System.out.println("customerTotalList() error = " + e.getMessage());
		}
	}

	/**
	 * setCbxList() : 콤보박스에 목록을 설정
	 * 
	 */
	public void setCbxList() {
		cbxCSearchKey.setItems(FXCollections.observableArrayList("고객번호", "고객명"));
	}

	/**
	 * editable() : 각 필드의 수정 여부를 설정
	 * 
	 * @param bool true면 수정가능, false면 수정불가
	 */
	private void editable(boolean bool) {
		txtCName.setEditable(bool);
		txtCId.setEditable(bool);
		pwCPasswd.setEditable(bool);
		txtCPhone.setEditable(bool);
		txtCAddress.setEditable(bool);
		txtCBrith.setEditable(bool);
		txtCEmail.setEditable(bool);
	}

	/**
	 * clear() : 각 필드값 지우기
	 * 
	 */
	private void clear() {
		txtCNum.clear();
		txtCName.clear();
		txtCId.clear();
		pwCPasswd.clear();
		txtCPhone.clear();
		txtCAddress.clear();
		txtCBrith.clear();
		txtCEmail.clear();
	}

	/**
	 * setInsertBtn() : 버튼의 활성화를 제어
	 * 
	 * @param bool true면 등록 활성화, false면 수정과 삭제 활성화
	 */
	private void setInsertBtn(boolean bool) {
		btnIdCheck.setDisable(!bool);
		btnCInsert.setDisable(bool);
		btnCUpdate.setDisable(bool);
		btnCDelete.setDisable(bool);
	}

	/**
	 * reset() : 각 필드를 초기화, 버튼 제어 초기화, 테이블 뷰에 전체 리스트를 출력
	 * 
	 */
	private void reset() {
		customerTotalList();
		clear();
		editable(true);
		setInsertBtn(true);
	}
}
