package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CdOrderDAO;
import model.DataUtil;
import model.RankVO;

public class ManageResultTabController implements Initializable {
	@FXML
	private BarChart<String, Integer> mOrderBarChart;
	@FXML
	private PieChart ySalesPieChart;
	@FXML
	private TableView<RankVO> mComponentRank;
	@FXML
	private BarChart<String, Integer> mSalesBarChart;
	@FXML
	private LineChart<String, Integer> ySalesLineChart;

	private static ObservableList<RankVO> rankDataList = FXCollections.observableArrayList();

	private CdOrderDAO codao = CdOrderDAO.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<String> title = DataUtil.fieldName(new RankVO());

		for (int i = 0; i < title.size(); i++) {
			TableColumn<RankVO, ?> columnName = mComponentRank.getColumns().get(i);
			columnName.setCellValueFactory(new PropertyValueFactory<>(title.get(i)));
		}
		mComponentRank.setItems(rankDataList);
		
		setTheme();
		reset();
	}

	/**
	 * rankTotalList() : '부품별 매출 1위' 테이블을 새로고침한다.
	 * 
	 */
	public void rankTotalList() {
		rankDataList.removeAll(rankDataList);
		RankVO rvo = null;
		ArrayList<RankVO> list;

		// 제품 구분 코드를 제품 구분 명으로 변경
		HashMap<String, String> p_sort = new HashMap<String, String>();
		String[] key = DataUtil.getKey("id");
		String[] val = DataUtil.getKey("pSort");
		for (int i = 0; i < val.length; i++) {
			p_sort.put(key[i], val[i]);
		}

		try {
			list = codao.getCountRank();

			// Key 기준으로 순서 정렬
			list = judgeSort(list);

			for (int i = 0; i < list.size(); i++) {
				rvo = list.get(i);

				// 제품 구분 코드를 제품 구분 명으로 변경
				rvo.setProductSort(p_sort.get(rvo.getProductSort()));

				rankDataList.add(rvo);
			}
		} catch (Exception e) {
			System.out.println("rankTotalList() error = " + e.getMessage());
		}
	}

	/**
	 * setYSalesPieChart() : 주문 데이터로 '연 판매량 분포'를 설정한다.
	 * 
	 */
	public void setYSalesPieChart() {
		Map<String, Integer> resultMap = codao.getChartYearOrder();
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

		for (Map.Entry<String, Integer> result : resultMap.entrySet()) {
			pieChartData.add(new PieChart.Data(result.getKey(), result.getValue()));
		}

		ySalesPieChart.setData(pieChartData);
	}

	/**
	 * setMOrderBarChart() : 주문 데이터로 '월 판매량'를 설정한다.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void setMOrderBarChart() {
		mOrderBarChart.getData().clear();

		Map<String, Integer> resultMap = codao.getChartMonthOrder();
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
			String tmpString = entry.getKey();
			int tmpValue = entry.getValue();
			XYChart.Data<String, Integer> d = new XYChart.Data<String, Integer>(tmpString, tmpValue);
			series.getData().add(d);
		}
		
		mOrderBarChart.getData().addAll(series);
		mOrderBarChart.setLegendVisible(false);
	}

	/**
	 * setMSalesBarChart() : 주문 데이터로 '월 매출액'을 설정한다.
	 * 
	 */
	public void setMSalesBarChart() {
		mSalesBarChart.getData().clear();

		Map<String, Integer> resultMap = codao.getChartMonthPrice();
		XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
		for (Map.Entry<String, Integer> result : resultMap.entrySet()) {
			series.getData().add(new Data<String, Integer>(result.getKey(), result.getValue()));

		}
		
		mSalesBarChart.getData().add(series);
		mSalesBarChart.setLegendVisible(false);
	}

	/**
	 * setYSalesLineChart() : 주문 데이터로 '연 매출액'을 설정한다.
	 * 
	 */
	public void setYSalesLineChart() {
		ySalesLineChart.getData().clear();

		Map<String, Integer> resultMap = codao.getChartYearPrice();
		XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();

		for (Map.Entry<String, Integer> result : resultMap.entrySet()) {
			series.getData().add(new Data<String, Integer>(result.getKey(), result.getValue()));
		}
		
		ySalesLineChart.getData().add(series);
		ySalesLineChart.setLegendVisible(false);
	}

	/**
	 * judgeSort() : 가상으로 정해준 순서를 기준으로 List를 정렬해서 반환해준다.
	 * 
	 * @param before 정렬할 List
	 * @return after 정렬된 List
	 */
	private ArrayList<RankVO> judgeSort(ArrayList<RankVO> before) {
		ArrayList<RankVO> after = new ArrayList<RankVO>();
		String[] judge = DataUtil.getKey("id");

		for (int i = 0; i < judge.length; i++) {
			for (int j = 0; j < before.size(); j++) {
				if (judge[i].equals(before.get(j).getProductSort())) {
					after.add(before.get(j));
				}
			}
		}

		return after;
	}

	/**
	 * reset() : 각 차트와 테이블을 정보를 새로고침한다.
	 * 
	 */
	public void reset() {
		rankTotalList();
		setYSalesPieChart();
		setMOrderBarChart();
		setMSalesBarChart();
		setYSalesLineChart();
	}
	
	/**
	 * setTheme() : 선택한 테마의 색상으로 설정
	 */
	private void setTheme() {
		if(!LoginMainController.isTheme()) {
			ySalesPieChart.setStyle("-fx-pie-fill: white;");
			ySalesLineChart.getXAxis().setStyle("-fx-tick-label-fill : white;");
			ySalesLineChart.getYAxis().setStyle("-fx-tick-label-fill : white;");
			mOrderBarChart.getXAxis().setStyle("-fx-tick-label-fill : white;");
			mOrderBarChart.getYAxis().setStyle("-fx-tick-label-fill : white;");
			mSalesBarChart.getXAxis().setStyle("-fx-tick-label-fill : white;");
			mSalesBarChart.getYAxis().setStyle("-fx-tick-label-fill : white;");
		}
		
	}
}
