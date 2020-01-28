package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderChartDAO {

	private static OrderChartDAO instance = null;

	private OrderChartDAO() {

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
	 * @return Order_chartDAO
	 */
	public static OrderChartDAO getInstance() {
		if (instance == null) {
			instance = new OrderChartDAO();
		}
		return instance;
	}

	/**
	 * getOrder_ChartList() : 주문 내역 조회 메소드
	 * 
	 * @return ArrayList<Order_ChartVO> DB의 조회한 데이터를 리스트로 반환
	 */
	public ArrayList<OrderChartVO> getOrder_ChartList() {
		ArrayList<OrderChartVO> list = new ArrayList<OrderChartVO>();
		OrderChartVO ovo = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ch_num, cd_num, ch_qty, p_num FROM order_chart");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			ovo = new OrderChartVO();
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ovo.setCd_num(rs.getString("cd_num"));
				ovo.setCh_num(rs.getString("ch_num"));
				ovo.setCh_qty(rs.getInt("ch_qty"));
				ovo.setP_num(rs.getString("p_num"));
				list.add(ovo);
			}
		} catch (SQLException sqle) {
			System.out.println("[  getOrder_ChartList()  ]    [ SQLException ]");
		} catch (Exception e) {
			System.out.println("[  getOrder_ChartList()  ]    [ Unknown Exception ]");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.out.println("[  getOrder_ChartList()  ]    [ Closed Error ]");
			}
		}

		return list;
	}

	/**
	 * order_ChartInsert() : 주문 내역 등록 메소드
	 * 
	 * @param ocvo 등록할 주문 내역
	 * @return result 등록 결과
	 */
	public boolean order_ChartInsert(OrderChartVO ocvo) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO order_chart(ch_num, cd_num, ch_qty, p_num)");
		sql.append("VALUES ('O_'||LPAD(TO_CHAR(ch_num_seq.NEXTVAL),4,'0')");
		sql.append(", ?, ?, ?)");
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, ocvo.getCd_num());
			pstmt.setInt(2, ocvo.getCh_qty());
			pstmt.setString(3, ocvo.getP_num());
			
			int i = pstmt.executeUpdate();
			if (i == 1) {
				result = true;
			}

		} catch (SQLException sqle) {
			System.out.println("[  order_ChartInsert(Order_ChartVO ovo)  ] [  SQLException  ]");
		} catch (Exception e) {
			System.out.println("[ order_ChartInsert(Order_ChartVO ovo)  ] [  Exception  ]");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.out.println("[  order_ChartInsert(Order_ChartVO ovo)  ] [  closed Error  ]");
			}
		}

		return result;
	}
}