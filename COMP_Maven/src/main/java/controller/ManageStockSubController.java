package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DataUtil;
import model.ProductVO;
import model.WarehouseDAO;
import model.WarehouseVO;

public class ManageStockSubController implements Initializable {
	@FXML
	private TextField txtWHNum;
	@FXML
	private TextField txtTRNum;
	@FXML
	private TextField txtPNum;
	@FXML
	private TextField txtWHQty;
	@FXML
	private Button btnWHInsert;
	@FXML
	private Button btnWHDelete;
	@FXML
	private Button btnWHClear;
	@FXML
	private TableView<WarehouseVO> whTableView;

	private ManageStockTabController mstController;

	private ObservableList<WarehouseVO> whDataList = FXCollections.observableArrayList();

	private WarehouseDAO whdao = WarehouseDAO.getInstance();
	private ProductVO pvo;

	private Stage primaryStage;
	private Stage stage;

	public TextField getTxtTRNum() {
		return txtTRNum;
	}

	public void setMstController(ManageStockTabController mstController) {
		this.mstController = mstController;
	}

	public void setPvo(ProductVO pvo) {
		this.pvo = pvo;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<String> title = DataUtil.fieldName(new WarehouseVO());
		for (int i = 0; i < title.size() - 1; i++) {
			TableColumn<WarehouseVO, ?> columnName = whTableView.getColumns().get(i);
			columnName.setCellValueFactory(new PropertyValueFactory<>(title.get(i)));
		}
		whTableView.setItems(whDataList);

		// 제품번호에 대한 안내 메시지
		Tooltip tooltip = new Tooltip("재고관리에서 제품을 선택하고 클릭하면 자동으로 번호를 부여받을 수 있습니다.");
		tooltip.setFont(new Font(12));
		tooltip.setAutoFix(true);
		tooltip.setAutoHide(false);
		txtPNum.setTooltip(tooltip);

		setWHNum();
		setInsertBtn(true);
		wareTotalList();
	}

	/**
	 * txtTRPopup() : 거래처 목록을 테이블 형태로 보여준다.
	 * 
	 * @param event
	 */
	public void txtTRPopup(MouseEvent event) {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle("거래처 목록");

		try {
			// 팝업의 FXML 로드
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/manageWareHouseSub.fxml"));
			Parent parent = loader.load();
			DataUtil.setTheme(parent);

			ManageWHSubController mwsController = loader.getController();
			mwsController.setMssController(this);
			mwsController.setStage(dialog);

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		} catch (Exception e) {
			System.out.println("txtTRPopup() error = " + e.getMessage());
		}

	}

	/**
	 * btnWHInsert() : 입고내역을 등록한다.
	 * 
	 * @param event
	 */
	public void btnWHInsert(ActionEvent event) {
		if (!DataUtil.validityCheck(txtWHNum.getText(), "입고 번호")) {
			return;
		} else if (!DataUtil.validityCheck(txtTRNum.getText(), "거래처 번호")) {
			return;
		} else if (!DataUtil.validityCheck(txtPNum.getText(), "제품 번호")) {
			DataUtil.showAlert("제품 입력", "재고 관리의 목록에서 원하시는 제품을 더블클릭 해주세요.");
			return;
		} else if (!DataUtil.validityCheck(txtWHQty.getText(), "입고 수량")) {
			return;
		} else {
			WarehouseVO wvo = new WarehouseVO();
			wvo.setWh_num(txtWHNum.getText());
			wvo.setTr_num(txtTRNum.getText());
			wvo.setP_num(txtPNum.getText());
			wvo.setWh_qty(Integer.parseInt(txtWHQty.getText()));
			whdao.warehouseInsert(wvo);
			mstController.productTotalList();

			stage.close();
		}
	}

	/**
	 * btnWHDelete() : 입고내역을 삭제한다.
	 * 
	 * @param event
	 */
	public void btnWHDelete(ActionEvent event) {
		if (!DataUtil.validityCheck(txtWHNum.getText(), "입고 번호")) {
			return;
		} else if (!DataUtil.validityCheck(txtTRNum.getText(), "거래처 번호")) {
			return;
		} else if (!DataUtil.validityCheck(txtPNum.getText(), "제품 번호")) {
			return;
		} else {
			WarehouseVO wvo = new WarehouseVO();
			wvo.setWh_num(txtWHNum.getText());

			whdao.warehouseDelete(wvo);
			mstController.productTotalList();

			reset();
		}
	}

	/**
	 * btnWHClear() : 입고 탭의 필드 내용을 초기화하고 테이블을 새로고침 한다.
	 * 
	 * @param event
	 */
	public void btnWHClear(ActionEvent event) {
		reset();
	}

	/**
	 * whTableView() : 입고 테이블을 더블클릭하면 해당 입고내역을 선택해서 보여준다.
	 * 
	 * @param event
	 */
	public void whTableView(MouseEvent event) {
		if (event.getClickCount() == 2) {
			WarehouseVO wvo = whTableView.getSelectionModel().getSelectedItem();
			if (wvo != null) {
				txtWHNum.setText(wvo.getWh_num());
				txtTRNum.setText(wvo.getTr_num());
				txtPNum.setText(wvo.getP_num());
				txtWHQty.setText(Integer.toString(wvo.getWh_qty()));
				txtWHQty.setEditable(false);
				setInsertBtn(false);
			}
		}
	}

	/**
	 * wareTotalList() : 테이블뷰 레코드 출력(전체 리스트)
	 * 
	 */
	private void wareTotalList() {
		whDataList.removeAll(whDataList);
		WarehouseVO wvo = null;
		ArrayList<WarehouseVO> list;

		try {
			list = whdao.getWarehouseTotalList();

			for (int index = 0; index < list.size(); index++) {
				// 결과 리스트에서 한 행을 가져다가 wvo에 대입
				wvo = list.get(index);
				// 한 행을 추가
				whDataList.add(wvo);
			}
		} catch (Exception e) {
			System.out.println("wareTotalList() error = " + e.getMessage());
		}
	}

	/**
	 * setWHNum() : 새로운 거래처에게 부여될 다음 번호를 가져온다.
	 * 
	 */
	public void setWHNum() {
		StringBuffer sb = new StringBuffer();
		sb.append("WH_");
		sb.append(whdao.getWareHouseCount());
		txtWHNum.setText(sb.toString());
	}

	/**
	 * setInsertBtn() : 버튼의 활성화를 제어
	 * 
	 * @param bool true면 등록 활성화, false면 수정과 삭제 활성화
	 */
	private void setInsertBtn(boolean bool) {
		btnWHInsert.setDisable(!bool);
		btnWHDelete.setDisable(bool);
	}

	/**
	 * setWHInfo() : 재고관리의 선택한 제품번호를 입고 내역의 제품번호로 설정한다.
	 * 
	 */
	public void setWHInfo() {
		txtPNum.setText(pvo.getP_num());
	}

	/**
	 * reset() : 입고 내역의 필드를 초기화하고 버튼 제어를 해준다.
	 * 
	 */
	private void reset() {
		setWHNum();
		txtTRNum.clear();
		txtPNum.setText(pvo.getP_num());
		txtWHQty.clear();
		txtWHQty.setEditable(true);
		setInsertBtn(true);
		wareTotalList();
	}
}