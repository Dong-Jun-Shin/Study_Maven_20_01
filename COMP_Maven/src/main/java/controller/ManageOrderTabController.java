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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.CdChartDAO;
import model.CdChartVO;
import model.CdOrderDAO;
import model.CdOrderVO;
import model.DataUtil;
import model.DealerVO;
import model.EmailVO;

public class ManageOrderTabController implements Initializable {
	@FXML
	private Label lblCDNum;
	@FXML
	private Button btnOrderComplete;
	@FXML
	private Button btnOrderCancel;
	@FXML
	private TableView<CdChartVO> orderProgressView;
	@FXML
	private TableView<CdChartVO> orderHistoryView;

	String selectedCdChartIndex;

	private static ObservableList<CdChartVO> progressDataList = FXCollections.observableArrayList();
	private static ObservableList<CdChartVO> historyDataList = FXCollections.observableArrayList();

	private CdChartVO ccvo = new CdChartVO();
	private CdOrderDAO codao = CdOrderDAO.getInstance();
	private CdChartDAO ccdao = CdChartDAO.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 테이블뷰의 컬럼이름이 될 필드명을 가져온다.
		List<String> title = DataUtil.fieldName(new CdChartVO());

		// 설정을 받을 Table의 열
		for (int i = 0; i < title.size(); i++) {
			TableColumn<CdChartVO, ?> columnPName = orderProgressView.getColumns().get(i);
			columnPName.setCellValueFactory(new PropertyValueFactory<>(title.get(i)));

			TableColumn<CdChartVO, ?> columnHName = orderHistoryView.getColumns().get(i);
			columnHName.setCellValueFactory(new PropertyValueFactory<>(title.get(i)));
		}

		// 테이블에 항목 설정
		orderProgressView.setItems(progressDataList);
		orderHistoryView.setItems(historyDataList);

		setBtn(false);
		progressTotalList();
		historyTotalList();
	}

	/**
	 * btnOrderComplete() : 선택한 주문의 상태를 '거래완료'로 변경한다.
	 * 
	 * @param event
	 */
	public void btnOrderComplete(ActionEvent event) {
		boolean success = false;
		try {
			if (!DataUtil.valLimitCheck(lblCDNum.getText(), 10) || lblCDNum.getText().equals("(주문번호)")) {
				DataUtil.showAlert("주문 선택", "수정할 주문건을 더블클릭해주세요.");
				return;
			} else {
				CdOrderVO covo = new CdOrderVO();
				covo.setCd_num(lblCDNum.getText().trim());
				covo.setCd_sort("거래완료");

				success = codao.cd_orderUpdate(covo);

				if (success == true) {
					DataUtil.showInfoAlert("주문 처리 결과", "[" + lblCDNum.getText() + "]의 처리를 완료하였습니다.");
					reset();
				} else {
					DataUtil.showInfoAlert("주문 처리 결과", "주문의 처리에 문제가 있어 완료하지 못하였습니다.");
				}
			}
		} catch (Exception e) {
			System.out.println("btnOrderComplete() error = " + e.getMessage());
		}
	}

	/**
	 * btnOrderCancel() : 선택한 주문의 상태를 '거래취소'로 변경하고, 취소 안내 메일을 전송한다.
	 * 
	 * @param event
	 */
	public void btnOrderCancel(ActionEvent event) {
		boolean success = false;
		try {
			if (!DataUtil.valLimitCheck(lblCDNum.getText(), 10) || lblCDNum.getText().equals("(주문번호)")) {
				DataUtil.showAlert("주문 선택", "수정할 주문건을 더블클릭해주세요.");
				return;
			} else {
				CdOrderVO covo = new CdOrderVO();
				covo.setCd_num(lblCDNum.getText().trim());
				covo.setCd_sort("거래취소");

				success = codao.cd_orderUpdate(covo);

				// 거래 취소의 성공 여부
				if (success == true) {
					// 취소 이메일 전송
					boolean sendSuccess = sendCancle();

					String alertSend = "안내 이메일 전송을 실패했습니다.";
					// 이메일 전송 여부
					if (sendSuccess) {
						alertSend = "안내 이메일 전송을 성공하였습니다.";
					}
					DataUtil.showInfoAlert("주문 처리 결과", "[" + lblCDNum.getText() + "]의 처리를 완료하였습니다.\n" + alertSend);

					reset();
				} else {
					DataUtil.showInfoAlert("주문 처리 결과", "주문의 처리에 문제가 있어 완료하지 못하였습니다.");
				}
			}
		} catch (Exception e) {
			System.out.println("btnOrderCancel() error = " + e.getMessage());
		}
	}

	/**
	 * orderProgressView() : '진행 중인 주문 내역' 테이블을 더블 클릭하면, 해당 주문을 선택한다.
	 * 
	 * @param event
	 */
	public void orderProgressView(MouseEvent event) {
		if (event.getClickCount() == 2) {
			CdChartVO selectCdChart = orderProgressView.getSelectionModel().getSelectedItem();
			if (selectCdChart != null) {
				ccvo = selectCdChart;
				selectedCdChartIndex = selectCdChart.getCd_num();

				lblCDNum.setText(selectCdChart.getCd_num());

				setBtn(true);
			}
		}
	}

	/**
	 * progressTotalList() : '진행 중인 주문 내역' 테이블의 내용을 가져와서, 테이블에 설정해준다.
	 * 
	 */
	public void progressTotalList() {
		progressDataList.removeAll(progressDataList);
		CdChartVO ccvo = null;
		ArrayList<CdChartVO> list;

		try {
			list = ccdao.getProgressOrderList();

			for (int index = 0; index < list.size(); index++) {
				// 결과 리스트에서 한 행을 가져다가 svo에 대입
				ccvo = list.get(index);
				// 한 행을 추가
				progressDataList.add(ccvo);
			}
		} catch (Exception e) {
			System.out.println("progressTotalList() error = " + e.getMessage());
		}
	}

	/**
	 * historyTotalList() : '이전 주문 내역' 테이블의 내용을 가져와서, 테이블에 설정해준다.
	 * 
	 */
	public void historyTotalList() {
		historyDataList.removeAll(historyDataList);
		CdChartVO ccvo = null;
		ArrayList<CdChartVO> list;

		try {
			list = ccdao.getHistoryOrderList();

			for (int index = 0; index < list.size(); index++) {
				// 결과 리스트에서 한 행을 가져다가 svo에 대입
				ccvo = list.get(index);
				// 한 행을 추가
				historyDataList.add(ccvo);
			}
		} catch (Exception e) {
			System.out.println("historyTotalList() error = " + e.getMessage());
		}
	}

	/**
	 * setBtn() : 주문처리 하는 버튼의 상태를 제어
	 * 
	 * @param bool true면 클릭 가능, false면 클릭 불가능
	 */
	private void setBtn(boolean bool) {
		btnOrderComplete.setDisable(!bool);
		btnOrderCancel.setDisable(!bool);
	}

	/**
	 * reset() : 선택된 주문을 선택 해제하고, '진행 중인 주문 내역'과 '이전 주문 내역'의 테이블을 새로고침해준다.
	 * 
	 */
	private void reset() {
		lblCDNum.setText("(주문번호)");
		selectedCdChartIndex = null;
		setBtn(false);
		progressTotalList();
		historyTotalList();
	}

	/**
	 * sendCancle() : 취소 안내 메일을 전송한다.
	 * 
	 * @return success 메일의 전송 결과
	 */
	private boolean sendCancle() {
		boolean success = false;

		DealerVO dvo = DealerVO.getInstance();
		dvo.reset();

		/*
		 * 제목 : '구매자명'님, 주문이 취소되었습니다. 본문 : 고객 - 성함, 연락처, 주소 ------------------- 환불 금액
		 */
		StringBuffer sbHead = new StringBuffer();
		sbHead.append(ccvo.getC_name() + "님, 주문취소가 완료되었습니다.");

		StringBuffer sbSubject = new StringBuffer();
		sbSubject.append(dvo.getDName() + "에서 구매하신 내역에 대해 취소되었습니다.\n 다음은 처리된 내역입니다.\n");
		sbSubject.append("고객 정보 - " + ccvo.getC_name() + ", " + ccvo.getC_phone() + ", " + ccvo.getC_add() + "\n\n");

		EmailVO evo = new EmailVO(dvo.getDEId(), dvo.getDEPw(), ccvo.getC_email(), ccvo.getC_name(), dvo.getDEId(),
				dvo.getDName(), sbHead.toString(), sbSubject.toString());

		String str = DataUtil.send(evo);
		if (str.equals("Success")) {
			success = true;
		}

		return success;
	}
}
