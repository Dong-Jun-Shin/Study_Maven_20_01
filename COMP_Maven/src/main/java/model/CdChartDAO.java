package model;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CdChartDAO {
	private static CdChartDAO instance = null;

	private HashMap<String, String> dicKey = new HashMap<String, String>();

	private CdChartDAO() {

	}

	/**
	 * getConnection() : DB 연동 메소드
	 * 
	 * @return Connection
	 * @throws Exception
	 */
	private Connection getConnection() throws Exception {
		Connection con = DBUtill.getConnection();
		return con;
	}

	/**
	 * getInstance() : 인스턴스 생성 메소드
	 * 
	 * @return CD_orderDAO
	 */
	public static CdChartDAO getInstance() {
		if (instance == null) {
			instance = new CdChartDAO();
		}
		return instance;
	}

	/**
	 * getProgressOrderList() : 거래 중인 주문 리스트 조회 메소드
	 * 
	 * @return ArrayList<Order_ChartVO> DB의 조회한 데이터를 리스트로 반환
	 */
	public ArrayList<CdChartVO> getProgressOrderList() {
		ArrayList<CdChartVO> list = new ArrayList<CdChartVO>();
		ArrayList<String> cNum = new ArrayList<String>();

		setDic();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT cd_sort, cd_num, cd_price, c_num FROM cd_order WHERE cd_sort LIKE '거래중' ORDER BY cd_num");

		CdChartVO ccvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 주문 관련 정보 설정
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

//			CdChartVO[] ccvo = new CdChartVO[rs.getFetchSize()];

			while (rs.next()) {
				ccvo = new CdChartVO();
				ccvo.setCd_sort(rs.getString("cd_sort"));
				ccvo.setCd_num(rs.getString("cd_num"));
				ccvo.setCd_price(rs.getInt("cd_price"));
				cNum.add(rs.getString("c_num"));

				list.add(ccvo);
			}

			// list 초기화
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < dicKey.size(); j++) {
					String[] initKey = DataUtil.getKey("method");
					String numMName = "set" + initKey[j] + "_num";
					Method numM = list.get(i).getClass().getMethod(numMName, String.class);

					numM.invoke(list.get(i), "미선택");
				}
			}

			// 고객 관련 정보 설정
			for (int i = 0; i < list.size(); i++) {
				sql = new StringBuffer();
				sql.append("SELECT c_id, c_name, c_phone, c_add, c_email FROM customer WHERE c_num = ?");

				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, cNum.get(i));
				rs = pstmt.executeQuery();

				while (rs.next()) {
					list.get(i).setC_id(rs.getString("c_id"));
					list.get(i).setC_name(rs.getString("c_name"));
					list.get(i).setC_phone(rs.getString("c_phone"));
					list.get(i).setC_add(rs.getString("c_add"));
					list.get(i).setC_email(rs.getString("c_email"));
				}
			}

			for (int i = 0; i < list.size(); i++) {
				sql = new StringBuffer();
				sql.append("SELECT p_num, ch_qty FROM order_chart WHERE cd_num = ?");

				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, list.get(i).getCd_num());
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// 설정할 매개변수
					String num = rs.getString("p_num");
					int qty = rs.getInt("ch_qty");

					// 매개변수에서 Key를 추출
					String numIdx = num.substring(0, num.indexOf("_"));

					// Key를 이용해서 해당 Val을 반환받고 해당하는 메소드명을 설정
					String numMName = "set" + dicKey.get(numIdx) + "_num";
					String qtyMName = "set" + dicKey.get(numIdx) + "_qty";

					// 해당 메소드명으로 메소드 클래스 생성
					Method numM = list.get(i).getClass().getMethod(numMName, String.class);
					Method qtyM = list.get(i).getClass().getMethod(qtyMName, int.class);

					// 메소드명에 해당하는 메소드 호출
					numM.invoke(list.get(i), num);
					qtyM.invoke(list.get(i), qty);
				}
			}
		} catch (SQLException sqle) {
			System.out.println("getProgressOrderList() error = " + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("getProgressOrderList() error = " + e.getMessage());
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				System.out.println("getProgressOrderList() error = " + e.getMessage());
			}
		}

		return list;
	}

	/**
	 * getHistoryOrderList() : 완료된 주문 리스트 조회 메소드
	 * 
	 * @return ArrayList<Order_ChartVO> DB의 조회한 데이터를 리스트로 반환
	 */
	public ArrayList<CdChartVO> getHistoryOrderList() {
		ArrayList<CdChartVO> list = new ArrayList<CdChartVO>();
		ArrayList<String> cNum = new ArrayList<String>();

		setDic();

		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT cd_sort, cd_num, cd_price, c_num FROM cd_order WHERE cd_sort LIKE '거래완료' OR cd_sort LIKE '거래취소' ORDER BY cd_sort, cd_num DESC");

		CdChartVO ccvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 주문 관련 정보 설정
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

//				CdChartVO[] ccvo = new CdChartVO[rs.getFetchSize()];

			while (rs.next()) {
				ccvo = new CdChartVO();
				ccvo.setCd_sort(rs.getString("cd_sort"));
				ccvo.setCd_num(rs.getString("cd_num"));
				ccvo.setCd_price(rs.getInt("cd_price"));
				cNum.add(rs.getString("c_num"));

				list.add(ccvo);
			}

			// list 초기화
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < dicKey.size(); j++) {
					String[] initKey = DataUtil.getKey("method");
					String numMName = "set" + initKey[j] + "_num";
					Method numM = list.get(i).getClass().getMethod(numMName, String.class);

					numM.invoke(list.get(i), "미선택");
				}
			}

			// 고객 관련 정보 설정
			for (int i = 0; i < list.size(); i++) {
				sql = new StringBuffer();
				sql.append("SELECT c_id, c_name, c_phone, c_add, c_email FROM customer WHERE c_num = ?");

				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, cNum.get(i));
				rs = pstmt.executeQuery();

				while (rs.next()) {
					list.get(i).setC_id(rs.getString("c_id"));
					list.get(i).setC_name(rs.getString("c_name"));
					list.get(i).setC_phone(rs.getString("c_phone"));
					list.get(i).setC_add(rs.getString("c_add"));
					list.get(i).setC_email(rs.getString("c_email"));
				}
			}

			for (int i = 0; i < list.size(); i++) {
				sql = new StringBuffer();
				sql.append("SELECT p_num, ch_qty FROM order_chart WHERE cd_num = ?");

				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, list.get(i).getCd_num());
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// 설정할 매개변수
					String num = rs.getString("p_num");
					int qty = rs.getInt("ch_qty");

					// 매개변수에서 Key를 추출
					String numIdx = num.substring(0, num.indexOf("_"));

					// Key를 이용해서 해당 Val을 반환받고 해당하는 메소드명을 설정
					String numMName = "set" + dicKey.get(numIdx) + "_num";
					String qtyMName = "set" + dicKey.get(numIdx) + "_qty";

					// 해당 메소드명으로 메소드 클래스 생성
					Method numM = list.get(i).getClass().getMethod(numMName, String.class);
					Method qtyM = list.get(i).getClass().getMethod(qtyMName, int.class);

					// 메소드명에 해당하는 메소드 호출
					numM.invoke(list.get(i), num);
					qtyM.invoke(list.get(i), qty);
				}
			}
		} catch (SQLException sqle) {
			System.out.println("getHistoryOrderList() error = " + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("getHistoryOrderList() error = " + e.getMessage());
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				System.out.println("getHistoryOrderList() error = " + e.getMessage());
			}
		}

		return list;
	}

	/**
	 * setDic() : 인덱스 id와 메소드명에 들어갈 문자열을 연결한 리스트를 만든다.
	 * 
	 */
	public void setDic() {
		String[] key = DataUtil.getKey("id");
		String[] val = DataUtil.getKey("method");

		for (int i = 0; i < key.length; i++) {
			dicKey.put(key[i], val[i]);
		}

	}
}
