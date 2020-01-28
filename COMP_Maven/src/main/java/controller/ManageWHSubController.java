package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.TraderDAO;
import model.TraderVO;

public class ManageWHSubController implements Initializable {
	@FXML
	private TableView<TraderVO> wareTableList;
	private ObservableList<TraderVO> wareDataList = FXCollections.observableArrayList();

	private TraderDAO tdao = TraderDAO.getInstance();

	private ManageStockSubController mssController;
	private Stage stage;

	public void setMssController(ManageStockSubController mssController) {
		this.mssController = mssController;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TableColumn<TraderVO, ?> columnName = wareTableList.getColumns().get(0);
		columnName.setCellValueFactory(new PropertyValueFactory<>("tr_name"));

		wareTableList.setItems(wareDataList);
		tableViewData();
	}

	/**
	 * wareTableList() : 거래처의 정보를 테이블에 설정해준다.
	 * 
	 * @param event
	 */
	public void wareTableList(MouseEvent event) {
		if (event.getClickCount() == 2) {
			TraderVO selectTrader = wareTableList.getSelectionModel().getSelectedItem();
			if (selectTrader != null) {
				mssController.getTxtTRNum().setText(selectTrader.getTr_num());
				stage.close();
			}
		}
	}

	/**
	 * tableViewData() : 테이블뷰에 보여줄 레코드 설정
	 * 
	 */
	private void tableViewData() {
		// 다른 결과가 있을수도 있는 DB결과를 담는 변수를 비워준다.
		wareDataList.removeAll(wareDataList);

		TraderVO tvo = null;
		ArrayList<TraderVO> list;

		try {
			list = tdao.getTraderTotalList();

			int rowCount = list.size();
			for (int index = 0; index < rowCount; index++) {
				tvo = list.get(index);
				wareDataList.add(tvo);
			}
		} catch (Exception e) {
			System.out.println("tableViewData() error = " + e.getMessage());
		}
	}
}
