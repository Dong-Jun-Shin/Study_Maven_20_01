package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TraderDAO {

	private static TraderDAO instance = null;

	private TraderDAO() {

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
	 * @return TraderDAO
	 */
	public static TraderDAO getInstance() {
		if (instance == null) {
			instance = new TraderDAO();
		}
		return instance;
	}

	/**
	 * getTraderCount() : 다음 부여될 고유번호를 반환한다.
	 * 
	 * @return serialNumber 고유번호 부여를 반환
	 */
	public String getTraderCount() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT NVL(LPAD(MAX(TO_NUMBER(LTRIM(SUBSTR(tr_num, ");
		sql.append("4, 3), '0')))+1, 3, '0'), '001') AS traderCount ");
		sql.append("FROM trader");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String serialNumber = "";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				serialNumber = rs.getString("traderCount");
			}
		} catch (SQLException ie) {
			System.out.println("getTraderCount() error = " + ie.getMessage());
		} catch (Exception e) {
			System.out.println("getTraderCount() error = " + e.getMessage());
		} finally {
			try {
				// 생성의 역순으로 닫기
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				System.out.println("getTraderCount() error = " + e.getMessage());
			}
		}

		return serialNumber;
	}

	/**
	 * getTraderTotalList() : 거래처 전체 조회 메소드
	 * 
	 * @return ArrayList<TraderVO> DB의 조회한 데이터를 리스트로 반환
	 */
	public ArrayList<TraderVO> getTraderTotalList() {
		ArrayList<TraderVO> list = new ArrayList<TraderVO>();
		TraderVO tvo = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tr_num, tr_name,tr_phone, tr_add, tr_bowner, tr_bnum, tr_bname, tr_reg FROM trader ");
		sql.append("ORDER BY tr_num ASC");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tvo = new TraderVO();
				tvo.setTr_num(rs.getString("tr_num"));
				tvo.setTr_name(rs.getString("tr_name"));
				tvo.setTr_phone(rs.getString("tr_phone"));
				tvo.setTr_add(rs.getString("tr_add"));
				tvo.setTr_reg(rs.getDate("tr_reg").toString());
				tvo.setTr_bowner(rs.getString("tr_bowner"));
				tvo.setTr_bnum(rs.getString("tr_bnum"));
				tvo.setTr_bname(rs.getString("tr_bname"));
				list.add(tvo);
			}

		} catch (SQLException sqle) {
			System.out.println("[  getTraderTotalList()  ]    [ SQLException ]");
		} catch (Exception e) {
			System.out.println("[  getTraderTotalList()  ]    [ Unknown Exception ]");
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
				System.out.println("[ getTraderTotalList()  ]    [ Closed Error ]");
			}
		}

		return list;
	}

	/**
	 * getTraderSelected() : 특정 거래처 정보 조회 메소드
	 * 
	 * @param category 검색 구분
	 * @param searchWord 검색 키워드
	 * @returnArrayList<TraderVO> DB의 조회한 데이터를 리스트로 반환
	 */
	public ArrayList<TraderVO> getTraderSelected(String category, String searchWord) {
		ArrayList<TraderVO> list = new ArrayList<TraderVO>();
		TraderVO tvo = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tr_num, tr_name,tr_phone, tr_add, tr_bowner, tr_bnum, tr_bname, tr_reg ");
		sql.append("FROM trader WHERE " + category + " LIKE ?");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + searchWord + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tvo = new TraderVO();
				
				tvo.setTr_num(rs.getString("tr_num"));
				tvo.setTr_name(rs.getString("tr_name"));
				tvo.setTr_phone(rs.getString("tr_phone"));
				tvo.setTr_add(rs.getString("tr_add"));
				tvo.setTr_reg(rs.getDate("tr_reg").toString());
				tvo.setTr_bowner(rs.getString("tr_bowner"));
				tvo.setTr_bnum(rs.getString("tr_bnum"));
				tvo.setTr_bname(rs.getString("tr_bname"));
				list.add(tvo);
			}
		} catch (SQLException sqle) {
			System.out.println("[  getTraderSelected(String category, String searchWord)  ]    [ SQLException ]");
		} catch (Exception e) {
			System.out.println("[  getTraderSelected(String category, String searchWord)  ]    [ Unknown Exception ]");
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
				System.out.println("[ getTraderSelected(String category, String searchWord)  ]    [ Closed Error ]");
			}
		}

		return list;
	}

	/**
	 * traderInsert(TraderVO tvo) : 거래처 정보 등록 메소드
	 * 
	 * @param tvo (TraderVO) : 등록할 거래처
	 * @return boolean
	 */
	public boolean traderInsert(TraderVO tvo) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO trader ");
		sql.append("(tr_num, tr_name, tr_phone, tr_add, tr_bowner, tr_bnum, tr_bname)");
		// 거래처 번호, 거래처명, 전화번호, 주소, 계좌주, 계좌번호, 계좌은행
		sql.append(" VALUES (?, ? , ? , ? , ? , ? , ? )");
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, tvo.getTr_num());
			pstmt.setString(2, tvo.getTr_name());
			pstmt.setString(3, tvo.getTr_phone());
			pstmt.setString(4, tvo.getTr_add());
			pstmt.setString(5, tvo.getTr_bowner());
			pstmt.setString(6, tvo.getTr_bnum());
			pstmt.setString(7, tvo.getTr_bname());
			
			int i = pstmt.executeUpdate();
			if (i == 1) {
				result = true;
			}
		} catch (SQLException sqle) {
			System.out.println("[  traderInsert(TraderVO tvo)  ] [  SQLException  ]");
		} catch (Exception e) {
			System.out.println("[  traderInsert(TraderVO tvo)  ] [  Exception  ]");
		} finally {

			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.out.println("[  traderInsert(TraderVO tvo)  ] [  closed Error  ]");
			}
		}

		return result;
	}

	/**
	 * traderUpdate() : 거래처 정보 수정 메소드
	 * 
	 * @param tvo 수정할 거래처
	 * @return result 수정 결과
	 */
	public boolean traderUpdate(TraderVO tvo) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE trader SET tr_phone = ?, tr_add = ?, ");
		sql.append("tr_bowner = ? ,tr_bnum = ?, tr_bname = ? ");
		sql.append("WHERE tr_num = ?");
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, tvo.getTr_phone());
			pstmt.setString(2, tvo.getTr_add());
			pstmt.setString(3, tvo.getTr_bowner());
			pstmt.setString(4, tvo.getTr_bnum());
			pstmt.setString(5, tvo.getTr_bname());
			pstmt.setString(6, tvo.getTr_num());
			
			int i = pstmt.executeUpdate();
			if (i == 1) {
				result = true;
			}

		} catch (SQLException sqle) {
			System.out.println("[  traderUpdate(TraderVO tvo)  ] [  SQLException  ]");
		} catch (Exception e) {
			System.out.println("[  traderUpdate(TraderVO tvo)  ] [  Exception  ]");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.out.println("[  traderUpdate(TraderVO tvo)  ] [  closed Error  ]");
			}
		}

		return result;
	}

	/**
	 * traderDelete() : 거래처 정보 삭제 메소드
	 * 
	 * @param tvo 삭제할 거래처
	 * @return success 삭제 여부
	 */
	public boolean traderDelete(TraderVO tvo) {
		boolean success = false;
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM trader WHERE tr_num = ? ");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, tvo.getTr_num());
			
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				success = true;
			}
		} catch (SQLException sqle) {
			System.out.println("[   traderDelete(TraderVO cvo)  ] [  SQLException  ]");
			DataUtil.showAlert("삭제 결과", "입고 내역이 있는 거래처는 삭제할 수 없습니다.");
		} catch (Exception e) {
			System.out.println("[   traderDelete(TraderVO cvo)  ] [  Exception  ]");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.out.println("[   traderDelete(TraderVO cvo)  ] [  closed Error  ]");
			}
		}
		
		return success;
	}

}
