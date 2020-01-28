package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.DataUtil;
import model.ProductDAO;
import model.ProductVO;

public class SalesWatchTabController implements Initializable {
	@FXML
	private TreeView<String> productTreeView;
	@FXML
	private ImageView productImageView00;
	@FXML
	private ImageView productImageView10;
	@FXML
	private ImageView productImageView20;
	@FXML
	private ImageView productImageView30;
	@FXML
	private ImageView productImageView01;
	@FXML
	private ImageView productImageView11;
	@FXML
	private ImageView productImageView21;
	@FXML
	private ImageView productImageView31;
	@FXML
	private ImageView productImageView02;
	@FXML
	private ImageView productImageView12;
	@FXML
	private ImageView productImageView22;
	@FXML
	private ImageView productImageView32;
	@FXML
	private ImageView productImageView03;
	@FXML
	private ImageView productImageView13;
	@FXML
	private ImageView productImageView23;
	@FXML
	private ImageView productImageView33;
	@FXML
	private Button btnLast;
	@FXML
	private Button btnNext;

	// 선택된 제품 종류
	private String key = null;

	// 제품 페이지 카운트
	private int pPageNum = 0;

	// 이미지 파일명, 경로, 파일
	private String selectFileName[] = new String[16];
	private String localUrl = "";
	private Image localImage;

	// 제품조회 서브창 팝업
	private Popup popup = new Popup();

	private ProductDAO pddao = ProductDAO.getInstance();

	private SalesTradeTabController sttController;

	public void setSttController(SalesTradeTabController sttController) {
		this.sttController = sttController;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TreeItem에 사용할 이미지와 TreeItem 객체 생성
		String[] imgName = DataUtil.getKey("id");
		String[] itemName = DataUtil.getKey("pSort");
		Object[] itemList = new Object[itemName.length];

		for (int i = 0; i < itemName.length; i++) {
			StringBuffer imgPath = new StringBuffer();
			imgPath.append("file:C:\\COMP\\image\\TreeView\\");
			imgPath.append(imgName[i]);
			imgPath.append(".png");

			Image pChildIcon = new Image(imgPath.toString(), 10, 10, false, false);
			itemList[i] = new TreeItem<String>(itemName[i], new ImageView(pChildIcon));
		}

		// CSS 적용
		productTreeView.getStylesheets().add("/application/treeView.css");
		productTreeView.getStyleClass().add("my-tree-view");

		// 이미지 경로 설정과 root 설정
		StringBuffer selImg = new StringBuffer();
		selImg.append("file:C:\\COMP\\image\\TreeView\\");

		if (LoginMainController.isTheme()) {
			selImg.append("COM_LIGHT.png");
		} else {
			selImg.append("COM_DARK.png");
		}

		Image comChildIcon = new Image(selImg.toString(), 10, 10, false, false);
		TreeItem<String> root = new TreeItem("제품구성", new ImageView(comChildIcon));
		root.setExpanded(true);

		// TreeView 생성
		for (int i = 0; i < itemList.length; i++) {
			TreeItem<String> item = (TreeItem<String>) itemList[i];
			root.getChildren().add(item);
		}

		productTreeView.setCellFactory(treeView -> new MyTreeCell());
		productTreeView.setRoot(root);

		productTreeView.getSelectionModel().select(1);

		btnLast.setDisable(true);
	}

	/**
	 * imageSel() : 이미지 선택 시, 제품조회 서브창 호출
	 * 
	 * @param event
	 */
	public void imageSel(MouseEvent event) {
		ImageView selImg = (ImageView) event.getSource();

		if (!(selImg.getImage() == null)) {
			Stage stage = (Stage) selImg.getScene().getWindow();
			try {
				// 파일명이 저장된 배열의 인덱스로 변환
				String viewName = selImg.getId();
				int row = Integer.parseInt(viewName.substring(viewName.length() - 1, viewName.length()));
				int col = Integer.parseInt(viewName.substring(viewName.length() - 2, viewName.length() - 1));
				int imgIdx = (col) + (row * 4);

				// pvo 값 호출
				ArrayList<ProductVO> list = pddao.getProductSelected("p_num", (key + "_" + selectFileName[imgIdx]));
				ProductVO pvo = new ProductVO();

				if (list.size() > 0) {
					pvo = list.get(0);
				}

				// 팝업의 FXML 로드
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/salesWatchSub.fxml"));
				Parent parent = loader.load();

				// Popup의 배경색과 테두리 설정
				String selBorderColor = "";
				String selBackColor = "";
				if (LoginMainController.isTheme()) {
					selBackColor = "#EFF8FF";
					selBorderColor = "black";
				} else {
					selBackColor = "#555555";
					selBorderColor = "skyblue";
				}

				parent.setStyle("-fx-background-color: " + selBackColor + ";" + "-fx-border-color: " + selBorderColor
						+ ";" + "-fx-border-width:2;" + "-fx-border-radius:3;" + "-fx-hgap:3;-fx-vgap:5;");

				// 로드된 FXML의 Controller 연결
				SalesWatchSubController swsController = loader.getController();
				swsController.setSttController(sttController);
				swsController.setStage(stage);
				swsController.setPopup(popup);
				swsController.setPvo(pvo);
				swsController.setPInfo();
				swsController.handlePopup(parent);

			} catch (Exception e) {
				System.out.println("imageSel() error = " + e.getMessage());
			}
		}
	}

	/**
	 * btnPageMove() : 페이지 이동
	 * 
	 * @param event
	 */
	public void btnPageMove(ActionEvent event) {
		// ImageView 갯수
		int imgViewCnt = 16;
		// 이전으로 넘어갈 페이지의 제품시작 번호가 0보다 크면 실행.
		boolean sPage = 0 < (pPageNum - 1) * imgViewCnt + 1;
		// 다음으로 넘어갈 페이지의 제품시작 번호가 파일개수보다 작으면 실행
		boolean ePage = getFileCount() > (pPageNum + 1) * imgViewCnt + 1;

		// 이전이나 다음버튼이 눌리면 실행
		if (event.getSource() == btnLast) {
			// 첫 페이지 확인. false = 첫 페이지
			if (sPage) {
				setImgView(-1);
			}
		} else if (event.getSource() == btnNext) {
			// 끝 페이지 확인. false = 끝 페이지
			if (ePage) {
				setImgView(1);
			}
		}
		setPageBtn();
	}

	/**
	 * setPageBtn() : 첫 페이지거나 끝 페이지에 따른 버튼 제어
	 * 
	 */
	private void setPageBtn() {
		// 첫 페이지면 이전버튼 비활성화
		if (0 == pPageNum) {
			btnLast.setDisable(true);
		} else {
			btnLast.setDisable(false);
		}

		// 끝 페이지면 다음버튼 비활성화
		if (pPageNum == getFileCount() / 16) {
			btnNext.setDisable(true);
		} else {
			btnNext.setDisable(false);
		}
	}

	/**
	 * setKey() : 각 TreeItem에 따른 고유값 부여
	 * 
	 * @param id 판단할 ID
	 */
	private void setKey(String id) {
		String idKey[] = DataUtil.getKey("pSort");
		String idVal[] = DataUtil.getKey("id");

		HashMap<String, String> dicKey = new HashMap<String, String>();

		for (int i = 0; i < idKey.length; i++) {
			dicKey.put(idKey[i], idVal[i]);
		}

		key = dicKey.get(id);
	}

	/**
	 * setImgView() : ImageView에 목록사진을 보여준다.
	 * 
	 * @param selFunc 1이면 다음 페이지, -1이면 이전 페이지
	 */
	private void setImgView(int selFunc) {
		if (selFunc <= 1 && selFunc >= -1) {

			// 페이지 변동
			pPageNum += selFunc;
			localUrl = "COMP\\image\\product\\" + key + "\\";

			// 폴더의 존재 여부 확인

			// 설정할 ImageView 배열
			ImageView[] imgViewArr = { productImageView00, productImageView10, productImageView20, productImageView30,
					productImageView01, productImageView11, productImageView21, productImageView31, productImageView02,
					productImageView12, productImageView22, productImageView32, productImageView03, productImageView13,
					productImageView23, productImageView33, };

			// ImageView 새로고침시, 초기화
			for (int i = 0; i < imgViewArr.length; i++) {
				imgViewArr[i].setImage(null);
			}

			// 시작 인덱스 설정
			int sIdx = pPageNum * 16 + 1;

			// 끝 인덱스 설정
			int eIdx = 0;

			// 페이지 제품의 마지막 인덱스가 파일개수보다 크면 파일개수로 설정
			if (!((eIdx = getFileCount()) < (pPageNum + 1) * 16)) {
				eIdx = (pPageNum + 1) * 16;
			}

			// ImageView에 이미지 설정
			for (int i = sIdx, j = 0; i <= eIdx; i++, j++) {
				selectFileName[j] = String.format("%03d", i);
				if (new File("C:\\" + localUrl + selectFileName[j] + ".jpg").isFile()) {
					localImage = new Image("file:C:\\" + localUrl + selectFileName[j] + ".jpg", 170,
							170, false, false);
					imgViewArr[j].setImage(localImage);
				} else {
					DataUtil.showAlert("이미지 읽기 오류", "\"" + key + selectFileName[j] + ".jpg\"" + " 파일이 없습니다.");
					eIdx += 1;
					j--;
				}
			}
		}
	}

	/**
	 * getFileCount() : 폴더 내 jpg의 개수를 센다.
	 * 
	 * @return count jpg의 개수를 반환
	 */
	private int getFileCount() {
		File dir = new File("C:\\" + localUrl);
		int count = 0;
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (File item : files) {
				if (item.getName().endsWith(".jpg")) {
					count++;
				}
			}
		} else {
			DataUtil.showAlert("이미지 읽기 오류", "이미지 폴더가 없습니다.");
		}

		return count;
	}

	/**
	 * class MyTreeCell : (내부 클래스) TreeView의 이벤트 적용
	 * 
	 */
	class MyTreeCell extends TreeCell<String> {
		public MyTreeCell() {
			selectedProperty().addListener((observable, oldValue, newValue) -> {
				// Root 선택시 기능제어
				if (newValue && !getTreeItem().getValue().equals("제품구성")) {
					// 페이지번호 초기화
					pPageNum = 0;
					// 현재 선택된 TreeItem에 따른 고유값 부여
					setKey(getTreeItem().getValue());
					// ImageView에 첫 페이지 이미지 설정
					setImgView(0);
					// 이동에 따른 첫 페이지 초기화
					setPageBtn();
				}
			});
		}

		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty) {
				setText(item);
				setGraphic(getTreeItem().getGraphic());
			} else {
				setText(null);
				setGraphic(null);
			}
		}
	}
}
