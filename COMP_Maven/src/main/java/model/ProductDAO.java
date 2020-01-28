package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {

	private static ProductDAO instance = null;

	private ProductDAO() {

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
	 * @return ProductDAO
	 */
	public static ProductDAO getInstance() {
		if (instance == null) {
			instance = new ProductDAO();
		}
		return instance;
	}

	/**
	 * getProductCount() : 다음 부여될 고유번호를 반환한다.
	 * 
	 * @return serialNumber 고유번호 부여를 반환
	 */
	public String getProductCount(String valNum) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT NVL(LPAD(MAX(TO_NUMBER(LTRIM(SUBSTR(p_num, ");
		sql.append("4, 3), '0')))+1, 3, '0'), '001') AS productCount ");
		sql.append("FROM product ");
		sql.append("WHERE p_num LIKE ?");
		sql.append("ORDER BY p_num ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String serialNumber = "";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + valNum + "%");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				serialNumber = rs.getString("productCount");
			}
		} catch (SQLException sqle) {
			System.out.println("getProductCount() error = " + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("getProductCount() error = " + e.getMessage());
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
				System.out.println("getProductCount() error = " + e.getMessage());
			}
		}

		return serialNumber;
	}

	/**
	 * getProductTotalList() : 전체 제품 리스트 조회 메소드
	 * 
	 * @return ArrayList<ProductVO> DB의 조회한 데이터를 리스트로 반환
	 */
	public ArrayList<ProductVO> getProductTotalList() {
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO pvo = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p_name ,p_price ,p_size ,p_grt ,p_date ,p_img ,p_qty ,p_num ,p_reg ");
		sql.append("FROM product ");
		sql.append("ORDER BY p_num");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pvo = new ProductVO();

				pvo.setP_name(rs.getString("p_name"));
				pvo.setP_price(rs.getInt("p_price"));
				pvo.setP_size(rs.getString("p_size"));
				pvo.setP_grt(rs.getString("p_grt"));
				pvo.setP_date(rs.getString("p_date"));
				pvo.setP_img(rs.getString("p_img"));
				pvo.setP_qty(rs.getInt("p_qty"));
				pvo.setP_num(rs.getString("p_num"));
				pvo.setP_reg(rs.getDate("p_reg").toString());

				list.add(pvo);
			}
		} catch (SQLException sqle) {
			System.out.println("[  public ArrayList<ProductVO> getProductTotalList()  ]    [ SQLException ]");
		} catch (Exception e) {
			System.out.println("[  public ArrayList<ProductVO> getProductTotalList()  ]    [ Unknown Exception ]");
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
				System.out.println(
						"[  public ArrayList<ProductVO> getProductTotalList()  ]    [ Connect Closed Exception ]");
			}
		}

		return list;
	}

	/**
	 * getProductSelected() : 선택한 제품 조회 메소드
	 * 
	 * @param category 조회할 검색 구분
	 * @param searchWord 조회할 키워드
	 * @return ArrayList<ProductVO> DB의 조회한 데이터를 리스트로 반환
	 */
	public ArrayList<ProductVO> getProductSelected(String category, String searchWord) {
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO pvo = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p_name, p_price, p_size, p_grt, p_date, p_img, p_qty, p_num, p_reg ");
		sql.append("FROM product ");
		sql.append("WHERE " + category + " LIKE ? ");
		sql.append("ORDER BY p_num");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + searchWord + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pvo = new ProductVO();
				pvo.setP_name(rs.getString("p_name"));
				pvo.setP_price(rs.getInt("p_price"));
				pvo.setP_size(rs.getString("p_size"));
				pvo.setP_grt(rs.getString("p_grt"));
				pvo.setP_date(rs.getString("p_date"));
				pvo.setP_img(rs.getString("p_img"));
				pvo.setP_qty(rs.getInt("p_qty"));
				pvo.setP_num(rs.getString("p_num"));
				pvo.setP_reg(rs.getDate("p_reg").toString());

				list.add(pvo);
			}
		} catch (SQLException sqle) {
			System.out
					.println("[  public ArrayList<ProductVO> getProductSelected()  ]    [ SQLException ]");
		} catch (Exception e) {
			System.out.println(
					"[  public ArrayList<ProductVO> getProductSelected()  ]    [ Unknown Exception ]");
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
				System.out.println(
						"[  public ArrayList<ProductVO> getProductSelected()  ]    [ Connect Closed Exception ]");
			}
		}

		return list;
	}

	/**
	 * productInsert() : 제품 등록 메소드
	 * 
	 * @param pvo 등록할 제품
	 * @return result 등록 결과
	 */
	public boolean productInsert(ProductVO pvo) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO product ");
		// 제품번호, 제품명, 값, 크기, 보증기간, 출시일, 이미지명, 제품갯수
		sql.append("(p_num, p_name ,p_price ,p_size ,p_grt ,p_date ,p_img ,p_qty) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pvo.getP_num());
			pstmt.setString(2, pvo.getP_name());
			pstmt.setInt(3, pvo.getP_price());
			pstmt.setString(4, pvo.getP_size());
			pstmt.setString(5, pvo.getP_grt());
			pstmt.setString(6, pvo.getP_date());
			pstmt.setString(7, pvo.getP_img());
			pstmt.setInt(8, pvo.getP_qty());

			int i = pstmt.executeUpdate();
			if (i == 1) {
				result = true;
			}
		} catch (SQLException sqle) {
			System.out.println("[   productInsert(ProductVO pvo)  ] [  SQLException  ]");
		} catch (Exception e) {
			System.out.println("[   productInsert(ProductVO pvo)  ] [  Exception  ]");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.out.println("[   productInsert(ProductVO pvo)  ] [  closed Error  ]");
			}
		}

		return result;
	}

	/**
	 * productUpdate() : 제품 수정 메소드
	 * 
	 * @param pvo 수정할 제품
	 * @return result 수정 결과
	 */
	public boolean productUpdate(ProductVO pvo) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE product SET ");
		sql.append("p_name = ?, "); // 제품명
		sql.append("p_price = ? , "); // 가격
		sql.append("p_size = ?, "); // 크기
		sql.append("p_grt = ?, "); // 보증기간
		sql.append("p_date = ?, "); // 출시일
		sql.append("p_img = ?, "); // 이미지명
		sql.append("p_qty = ? "); // 개수
		sql.append("WHERE p_num = ? "); // 제품번호 구분

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pvo.getP_name());
			pstmt.setInt(2, pvo.getP_price());
			pstmt.setString(3, pvo.getP_size());
			pstmt.setString(4, pvo.getP_grt());
			pstmt.setString(5, pvo.getP_date());
			pstmt.setString(6, pvo.getP_img());
			pstmt.setInt(7, pvo.getP_qty());
			pstmt.setString(8, pvo.getP_num());

			int i = pstmt.executeUpdate();
			if (i == 1) {
				result = true;
			}
		} catch (SQLException sqle) {
			System.out.println("[  productUpdate(ProductVO pvo)  ] [  SQLException  ]");
		} catch (Exception e) {
			System.out.println("[  productUpdate(ProductVO pvo)  ] [  Exception  ]");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.out.println("[  productUpdate(ProductVO pvo)  ] [  closed Error  ]");
			}
		}

		return result;
	}

	/**
	 * productDelete() : 제품 삭제 메소드
	 * 
	 * @param pvo 삭제할 제품
	 * @return result 삭제 결과
	 */
	public boolean productDelete(ProductVO pvo) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM product WHERE p_num = ?");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pvo.getP_num());

			int i = pstmt.executeUpdate();
			if (i == 1) {
				result = true;
			}
		} catch (SQLException sqle) {
			System.out.println("[  productDelete(ProductVO pvo)  ] [  SQLException  ]");
			DataUtil.showAlert("삭제 결과", "주문 내역이나 입고 내역이 있는 제품은 삭제할 수 없습니다.");
		} catch (Exception e) {
			System.out.println("[  productDelete(ProductVO pvo)  ] [  Exception  ]");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.out.println("[  productDelete(ProductVO pvo)  ] [  closed Error  ]");
			}
		}

		return result;
	}
}
