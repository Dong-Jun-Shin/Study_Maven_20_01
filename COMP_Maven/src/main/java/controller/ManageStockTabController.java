package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DataUtil;
import model.ProductDAO;
import model.ProductVO;

public class ManageStockTabController implements Initializable {
	@FXML
	private ImageView imgStock;
	@FXML
	private TextField txtPNum;
	@FXML
	private ComboBox<String> cbxPSort;
	@FXML
	private TextField txtPName;
	@FXML
	private TextField txtPQty;
	@FXML
	private TextField txtPPrice;
	@FXML
	private TextField txtPSize;
	@FXML
	private TextField txtPGrt;
	@FXML
	private TextField txtPDate;
	@FXML
	private TextField txtPImg;
	@FXML
	private Button btnImgChoice;
	@FXML
	private Button btnPInsert;
	@FXML
	private Button btnPUpdate;
	@FXML
	private Button btnPDelete;
	@FXML
	private ComboBox<String> cbxPSearchKey;
	@FXML
	private TextField txtPSearchValue;
	@FXML
	private Button btnPSearch;
	@FXML
	private Button btnWHPopup;
	@FXML
	private Button btnPClear;
	@FXML
	private TableView<ProductVO> productTableView;

	private HashMap<String, String> dicKey = new HashMap<String, String>();
	private HashMap<String, String> dicVal = new HashMap<String, String>();

	//// 사진을 가져오고 셋하기 위한 필드
	private File selectedFile = null;
	// 이미지 파일명
	private String selectFileName = "";
	// 이미지 저장할 폴더를 매개변수로 파일 객체 생성
	private File dirSave;

	String selectedProductIndex;

	private static ObservableList<ProductVO> productDataList = FXCollections.observableArrayList();

	private ProductVO pvo = new ProductVO();
	private ProductDAO pddao = ProductDAO.getInstance();

	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 아이콘 설정
		String imageName = DataUtil.getImgPath();
		Image localImage = new Image(imageName + "warehouse.png", 40, 40, false, false);
		imgStock.setImage(localImage);
		
		// 테이블뷰의 컬럼이름이 될 필드명을 가져온다.
		List<String> title = DataUtil.fieldName(new ProductVO());

		// 설정을 받을 Table의 열
		for (int i = 0; i < title.size(); i++) {
			TableColumn<ProductVO, ?> columnName = productTableView.getColumns().get(i);
			columnName.setCellValueFactory(new PropertyValueFactory<>(title.get(i)));
		}

		productTableView.setItems(productDataList);

		setList();
		setCbxList();
		reset();
	}

	/**
	 * btnPInsert() : 제품 등록 메소드
	 * 
	 * @param event
	 */
	public void btnPInsert(ActionEvent event) {
		boolean success = false;

		try {
			if (!DataUtil.validityCheck(txtPNum.getText(), "제품번호") || !DataUtil.valLimitCheck(txtPNum.getText(), 6)) {
				System.out.println("1");
				return;
			} else if (!DataUtil.validityCheck(txtPName.getText(), "제품명")
					|| !DataUtil.valLimitCheck(txtPName.getText(), 200)) {
				System.out.println(txtPName.getText().getBytes().length);
				System.out.println("2");
				return;
			} else if (!DataUtil.validityCheck(txtPQty.getText(), "수량")) {
				System.out.println("3");
				return;
			} else if (!DataUtil.validityCheck(txtPPrice.getText(), "단가")) {
				System.out.println("4");
				return;
			} else if (!DataUtil.validityCheck(txtPDate.getText(), "출시일")
					|| !DataUtil.valLimitCheck(txtPDate.getText(), 30)) {
				System.out.println("5");
				return;
			} else if (!DataUtil.validityCheck(txtPImg.getText(), "이미지 파일")
					|| !DataUtil.valLimitCheck(txtPImg.getText(), 150)) {
				System.out.println("6");
				return;
			} else {
				// 이미지 파일 저장
				if (selectedFile != null) {
					imageSave(selectedFile);
				}

				ProductVO pvo = new ProductVO();
				pvo.setP_num(txtPNum.getText());
				pvo.setP_name(txtPName.getText());
				if (!txtPQty.getText().equals(""))
					pvo.setP_qty(Integer.parseInt(txtPQty.getText()));
				pvo.setP_price(Integer.parseInt(txtPPrice.getText()));
				pvo.setP_date(txtPDate.getText());
				pvo.setP_size(txtPSize.getText());
				pvo.setP_grt(txtPGrt.getText());
				pvo.setP_img(txtPImg.getText());

				success = pddao.productInsert(pvo);

				if (success == true) {
					DataUtil.showInfoAlert("제품 등록 결과", "[" + txtPName.getText() + "]의 등록을 성공하였습니다.");
					reset();
				} else {
					DataUtil.showInfoAlert("제품 등록 결과", "제품의 정보 등록에 문제가 있어 완료하지 못하였습니다.");
				}
			}
		} catch (Exception e) {
			System.out.println("btnPInsert() error = " + e.getMessage());
		}
	}

	/**
	 * btnPUpdate() : 제품 업데이트 메소드
	 * 
	 * @param event
	 */
	public void btnPUpdate(ActionEvent event) {
		boolean success = false;

		try {
			if (!DataUtil.validityCheck(txtPNum.getText(), "제품번호")) {
				return;
			} else if (!DataUtil.validityCheck(txtPName.getText(), "제품명")) {
				return;
			} else if (!DataUtil.validityCheck(txtPQty.getText(), "수량")) {
				return;
			} else if (!DataUtil.validityCheck(txtPPrice.getText(), "단가")) {
				return;
			} else if (!DataUtil.validityCheck(txtPDate.getText(), "출시일")) {
				return;
			} else if (!DataUtil.validityCheck(txtPImg.getText(), "이미지 파일")) {
				return;
			} else {
				// 이미지 파일 저장
				if (selectedFile != null) {
					imageSave(selectedFile);
				}

				ProductVO pvo = new ProductVO();
				pvo.setP_num(txtPNum.getText());
				pvo.setP_name(txtPName.getText());
				pvo.setP_qty(Integer.parseInt(txtPQty.getText()));
				pvo.setP_price(Integer.parseInt(txtPPrice.getText()));
				pvo.setP_size(txtPSize.getText());
				pvo.setP_grt(txtPGrt.getText());
				pvo.setP_date(txtPDate.getText());
				pvo.setP_img(txtPImg.getText());

				success = pddao.productUpdate(pvo);

				if (success == true) {
					DataUtil.showInfoAlert("고객 수정 결과", "[" + txtPName.getText() + "]의 수정을 성공하였습니다.");
					reset();
				} else {
					DataUtil.showInfoAlert("고객 수정 결과", "고객의 정보 등록에 문제가 있어 수정을 완료하지 못하였습니다.");
				}
			}
		} catch (Exception e) {
			System.out.println("btnPUpdate() error = " + e.getMessage());
		}
	}

	/**
	 * btnPDelete() : 선택한 상품을 삭제한다.
	 * 
	 * @param event
	 */
	public void btnPDelete(ActionEvent event) {
		boolean success = false;
		try {
			ProductVO pvo = new ProductVO();
			pvo.setP_num(selectedProductIndex);
			imageDelete();
			success = pddao.productDelete(pvo);
		} catch (Exception e) {
			System.out.println("btnPDelete() error = " + e.getMessage());
		}

		if (success == true) {
			DataUtil.showInfoAlert("고객 삭제 결과", "[" + txtPName.getText() + "]의 삭제를 성공하였습니다.");
			reset();
		} else {
			DataUtil.showInfoAlert("고객 삭제 결과", "문제가 있어 삭제를 완료하지 못하였습니다.");
		}
	}

	/**
	 * btnImgChoice() : 이미지 선택 메소드
	 * 
	 * @param event
	 */
	public void btnImgChoice(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("이미지 선택");
			ExtensionFilter imgType = new ExtensionFilter("ImageFile", "*.jpg");
			fc.getExtensionFilters().add(imgType);
			fc.setInitialDirectory(new File("C:/"));

			selectedFile = fc.showOpenDialog(primaryStage);

			// 확장자명 얻기
			String chkStr = selectedFile.getAbsolutePath();
			chkStr = chkStr.substring(chkStr.length() - 4, chkStr.length());

			// 선택한 파일 타입을 체크 후, 이미지 지정
			if (selectedFile != null && chkStr.equals(".jpg")) {
				txtPImg.setText(dicKey.get(cbxPSort.getValue().toString()) + "/" + selectFileName + ".jpg");
			} else {
				selectedFile = null;
				DataUtil.showAlert("이미지 선택 오류", "이미지 파일을 선택해주세요.");
			}

		} catch (Exception e) {
			System.out.println("btnImgChoice() error = " + e.getMessage());
		}
	}

	/**
	 * imageSave() : 이미지 저장 메서드
	 * 
	 * @param file 읽어올 파일 객체
	 * @return 업로드된 파일명 리턴
	 */
	private String imageSave(File file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		String dir = "C:\\COMP\\image\\product\\" + dicKey.get(cbxPSort.getValue().toString());
		dirSave = new File(dir);

		try {
			// 폴더 지정
			File dirMake = new File(dirSave.getAbsolutePath());

			// 폴더가 없을 시, 이미지 저장 폴더 생성
			if (!dirMake.exists()) {
				dirMake.mkdirs();
			}

			// 이미지 파일명 생성
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(
					new FileOutputStream(dirSave.getAbsolutePath() + "\\" + selectFileName + ".jpg"));

			// 선택한 이미지 파일 InputStream의 마지막에 이르렀을 경우는 -1
			while ((data = bis.read()) != -1) {
				bos.write(data);
				bos.flush();
			}
		} catch (Exception e) {
			System.out.println("imageSave() error = " + e.getMessage());
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (bis != null)
					bis.close();
			} catch (IOException e) {
				System.out.println("imageSave() error = " + e.getMessage());
			}
		}

		return fileName;
	}

	/**
	 * imageDelete() : 이미지 파일을 삭제하는 메서드
	 * 
	 * @param fileName 삭제할 경로+파일명을 담은 파일객체
	 * @return 성공여부 반환
	 */
	private boolean imageDelete() {
		boolean result = false;
		try {
			// 삭제 이미지 파일
			// getAbsolutePath() : 절대경로 표시
			File fileDelete = new File(dirSave.getAbsolutePath() + "\\" + selectFileName);

			// exists() : 해당 객체(파일 || 폴더)이 존재하는지 여부를 반환
			if (fileDelete.exists()) {
				result = fileDelete.delete();
			}
		} catch (Exception e) {
			System.out.println("imageDelete() error = " + e.getMessage());
			result = false;
		}
		return result;
	}

	/**
	 * btnPSearch() : 콤보박스의 기준과 검색 텍스트 필드의 값으로 검색을 한다.
	 * 
	 * @param event
	 */
	public void btnPSearch(ActionEvent event) {
		ProductVO pvo = null;
		ArrayList<ProductVO> list = null;

		try {
			if (txtPSearchValue != null && txtPSearchValue.getText().length() != 0) {
				switch (cbxPSearchKey.getValue()) {
				case "제품번호":
					list = pddao.getProductSelected("p_num", txtPSearchValue.getText());
					break;
				case "제품명":
					list = pddao.getProductSelected("p_name", txtPSearchValue.getText());
					break;
				}
			} else {
				cbxPSearchKey.getSelectionModel().clearSelection();
				list = pddao.getProductTotalList();
			}

			productDataList.removeAll(productDataList);

			for (int index = 0; index < list.size(); index++) {
				// 결과 리스트에서 한 행을 가져다가 pvo에 대입
				pvo = list.get(index);
				// 한 행을 추가
				productDataList.add(pvo);
			}
		} catch (NullPointerException npe) {
			DataUtil.showInfoAlert("검색 결과", "검색 구분을 선택해주세요.");
		} catch (Exception e) {
			System.out.println("btnPSearch() error = " + e.getMessage());
		}
	}

	/**
	 * btnWHPopup() : 입고관리 탭을 보여준다.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void btnWHPopup(ActionEvent event) {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle("입고관리");

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/manageStockSub.fxml"));
			Parent parent = loader.load();
			DataUtil.setTheme(parent);

			ManageStockSubController mssController = loader.getController();
			mssController.setPrimaryStage(primaryStage);
			mssController.setMstController(this);
			mssController.setStage(dialog);
			mssController.setPvo(pvo);
			mssController.setWHInfo();

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		} catch (Exception e) {
			System.out.println("btnWHPopup() error = " + e.getMessage());
		}
	}

	/**
	 * productTableView() : 테이블 뷰에서 행을 더블클릭 시, 해당 행의 값을 가져와서 보여준다.
	 * 
	 * @param event
	 */
	public void productTableView(MouseEvent event) {
		if (event.getClickCount() == 2) {
			ProductVO selectProduct = productTableView.getSelectionModel().getSelectedItem();
			if (selectProduct != null) {
				String imgName = selectProduct.getP_img();
				String pSort = imgName.substring(0, imgName.indexOf("/"));

				// 이미지 경로 설정
				dirSave = new File("C:\\COMP\\image\\product\\" + pSort);
				// 이미지 파일 설정
				selectFileName = imgName.substring(imgName.indexOf("/") + 1, imgName.length());

				pvo = selectProduct;
				selectedProductIndex = selectProduct.getP_num();

				txtPNum.setText(selectProduct.getP_num());
				cbxPSort.setValue(dicVal.get(pSort));
				txtPName.setText(selectProduct.getP_name());
				txtPQty.setText(Integer.toString(selectProduct.getP_qty()));
				txtPPrice.setText(Integer.toString(selectProduct.getP_price()));
				txtPSize.setText(selectProduct.getP_size());
				txtPGrt.setText(selectProduct.getP_grt());
				txtPDate.setText(selectProduct.getP_date());
				txtPImg.setText(selectProduct.getP_img());

				cbxPSort.getEditor().setEditable(false);
				setDisable(true);
			}

		}

	}

	/**
	 * productTotalList() : 테이블뷰 레코드 출력(전체 리스트)
	 * 
	 */
	public void productTotalList() {
		productDataList.removeAll(productDataList);
		ProductVO pvo = null;
		ArrayList<ProductVO> list;

		try {
			list = pddao.getProductTotalList();

			for (int index = 0; index < list.size(); index++) {
				// 결과 리스트에서 한 행을 가져다가 pvo에 대입
				pvo = list.get(index);
				// 한 행을 추가
				productDataList.add(pvo);
			}
		} catch (Exception e) {
			System.out.println("productTotalList() error = " + e.getMessage());
		}
	}

	/**
	 * reset() : 각 필드를 초기화, 버튼 제어 초기화, 테이블 뷰에 전체 리스트를 출력
	 * 
	 */
	public void reset() {
		txtPDate.clear();
		cbxPSort.setValue("");
		txtPGrt.clear();
		txtPImg.clear();
		txtPName.clear();
		txtPNum.clear();
		txtPPrice.clear();
		txtPQty.clear();
		txtPSearchValue.clear();
		txtPSize.clear();
		setDisable(false);
		productTotalList();
	}

	/**
	 * setList() : Map을 생성하고 각 txtName의 필드들과 txtPrice의 필드들, spinQty의 필드들을 배열로 만든다.
	 * 
	 */
	private void setList() {
		// Map<key, value> 설정
		String idKey[] = DataUtil.getKey("pSort");
		String idxVal[] = DataUtil.getKey("id");

		for (int i = 0; i < idKey.length; i++) {
			dicKey.put(idKey[i], idxVal[i]);
			dicVal.put(idxVal[i], idKey[i]);
		}
	}

	/**
	 * setCbxList() : 콤보박스에 목록을 설정
	 * 
	 */
	public void setCbxList() {
		cbxPSort.setItems(FXCollections.observableArrayList(DataUtil.getKey("pSort")));
		cbxPSearchKey.setItems(FXCollections.observableArrayList("제품번호", "제품명"));
	}

	/**
	 * setPNum() : 새로운 고객에게 부여될 다음 번호를 가져온다.
	 * 
	 * @param event
	 */
	public void setPNum(MouseEvent event) {
		try {
			if (cbxPSort.getValue().toString() != "") {
				if (selectedProductIndex == null) {
					StringBuffer sb = new StringBuffer();
					String numVal = dicKey.get(cbxPSort.getValue().toString());
					sb.append(numVal + "_");
					sb.append((selectFileName = pddao.getProductCount(numVal)));
					txtPNum.setText(sb.toString());

					btnImgChoice.setDisable(false);
				}
			} else {
				DataUtil.showInfoAlert("제품 선택", "제품 구분을 먼저 선택해주세요.");
			}

		} catch (NullPointerException npe) {
			DataUtil.showInfoAlert("제품 선택", "제품 구분을 먼저 선택해주세요.");
		}
	}

	/**
	 * setDisable() : 각 필드의 수정 여부를 설정
	 * 
	 * @param bool true면 수정가능, false면 수정불가
	 */
	public void setDisable(boolean bool) {
		btnImgChoice.setDisable(!bool);
		btnPInsert.setDisable(bool);
		btnPUpdate.setDisable(!bool);
		btnPDelete.setDisable(!bool);

	}

	/**
	 * btnPClear() : 모든 내용과 필드를 초기화한다.
	 * 
	 * @param event
	 */
	public void btnPClear(ActionEvent event) {
		selectedProductIndex = null;
		reset();
	}
}
