package team.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class BoardDAO {
//	private static final String JDBC_URL = "jdbc:oracle:thin:@192.168.0.131:1521:orcl";
//	private static final String USER = "javauser";
//	private static final String PASSWD = "java1234";
	
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
//	private BoardDAO() {
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		}catch(ClassNotFoundException cnfe) {
//			cnfe.printStackTrace();
//		}
//	}
	
	private Connection getConnection() throws Exception {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle11g");
		return ds.getConnection();
	}
	
	// 게시물 목록 전체 조회
	public ArrayList<BoardVO>getBoardList(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		BoardVO vo = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b_num, b_title, b_writer, b_reg_date, b_hits FROM board ORDER BY b_num DESC");
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			// ResultSet의 결과에서 모든 행을 각각의 StudentVO 객체에 저장
			while(rs.next()) {
				// 한 행의 학생정보를 저장할 학생을 위한 빈즈 객체 생성
				vo = new BoardVO();
				
				// 한 행의 학생정보를 자바 빈즈 객체에 저장
				vo.setB_num(rs.getInt("b_num"));
				vo.setB_title(rs.getString("b_title"));
				vo.setB_writer(rs.getString("b_writer"));
				vo.setB_reg_date(rs.getString("b_reg_date"));
				vo.setB_hits(rs.getInt("b_hits"));
				
				list.add(vo);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException sqle) {
				System.out.println(sqle.toString());
				sqle.printStackTrace();
			}
		}
		
		return list;
	}

	// 게시물 검색 조회
	public ArrayList<BoardVO> getSearchBoard(String sort) {
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		
		return list;
	}
	
	// 게시물 내용 목록 조회
	public ArrayList<BoardVO> getSearchDetails(String b_num) {
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();

		return list;
	}
	
	// 게시물 정보 조회
	// DetailsDAO의 내용 조회를 활용해서 해도 되고, JOIN문을 활용해도 됨
	public ArrayList<BoardVO> getSelBoard(HttpServletRequest request) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int b_num = Integer.parseInt(request.getParameter("b_num"));
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		BoardVO bvo = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b.b_num, b.b_title, b.b_writer, b.b_reg_date, b.b_hits, d.b_content, d.b_file ");
		sql.append("FROM board b INNER JOIN details d ON b.b_num = d.b_num WHERE b.b_num=?");
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, b_num);
			rs = pstmt.executeQuery();
			
			// ResultSet의 결과에서 모든 행을 각각의 StudentVO 객체에 저장
			if(rs.next()) {
				// 한 행의 학생정보를 저장할 학생을 위한 빈즈 객체 생성
				bvo = new BoardVO();
				
				// 한 행의 학생정보를 자바 빈즈 객체에 저장
				bvo.setB_num(b_num);
				bvo.setB_file(rs.getString("b_file"));
				bvo.setB_title(rs.getString("b_title"));
				bvo.setB_writer(rs.getString("b_writer"));
				bvo.setB_content(rs.getString("b_content"));
				bvo.setB_hits(rs.getInt("b_hits")+1);
				
				bvo = boardAddHits(bvo);
				list.add(bvo);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException sqle) {
				System.out.println(sqle.toString());
				sqle.printStackTrace();
			}
		}
		
		return list;
	}
	
	// 게시물 등록 
	// SQL문을 작성할 때, 테이블(board, details)에 분리되서 입력하도록 만들기
	// 사용해서 내용 등록도 같이 이루어지도록 하기
	public int boardInsert(BoardVO bvo) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmtB = null;
		PreparedStatement pstmtD = null;
		
		StringBuffer sqlB = new StringBuffer();
		StringBuffer sqlD = new StringBuffer();
		sqlB.append("INSERT INTO board(b_num, b_title, b_writer) VALUES(board_seq.NEXTVAL, ?, ?)");
		sqlD.append("INSERT INTO details(b_num, b_content, b_file) VALUES(board_seq.CURRVAL, ?, ?)");

		try {
			// 트랜젝션
			con = getConnection();
			con.setAutoCommit(false);
			
			pstmtB = con.prepareStatement(sqlB.toString());
			pstmtB.setString(1, bvo.getB_title());
			pstmtB.setString(2, bvo.getB_writer());
			 
			pstmtD = con.prepareStatement(sqlD.toString());
			pstmtD.setString(1, bvo.getB_content());
			pstmtD.setString(2, bvo.getB_file());

			if((result = pstmtB.executeUpdate())==0) throw new SQLException();
			if((result = pstmtD.executeUpdate())==0) throw new SQLException();
			con.commit();
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException sqle_dcl) {
				System.out.println("boardInsert 롤백 error [ " + sqle_dcl + " ]");
				sqle.printStackTrace();
			}
			System.out.println("boardInsert 쿼리 error [ " + sqle + " ]");
			sqle.printStackTrace();
		} catch (Exception e) {
			System.out.println("boardInsert 쿼리 error [ " + e + " ]");
			e.printStackTrace();
		} finally {
			try {
				if(pstmtD != null) pstmtD.close();
				if(pstmtB != null) pstmtB.close();
				if(con != null) con.close();
			}catch (SQLException sqle) {
				System.out.println("디비 연동 해제 error [ " + sqle + " ]");
				sqle.printStackTrace();
			}
		}
		
		return result;
	}
	
	// 조회수 증가
	public BoardVO boardAddHits(BoardVO bvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE board SET b_hits=? WHERE b_num=?");

		try {
			con = getConnection();
			 
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bvo.getB_hits());
			pstmt.setInt(2, bvo.getB_num());
			
			if(pstmt.executeUpdate()==0) throw new SQLException();
		} catch (SQLException sqle) {
			System.out.println("boardAddHits 쿼리 error [ " + sqle + " ]");
			sqle.printStackTrace();
		} catch (Exception e) {
			System.out.println("boardAddHits 쿼리 error [ " + e + " ]");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch (SQLException sqle) {
				System.out.println("디비 연동 해제 error [ " + sqle + " ]");
				sqle.printStackTrace();
			}
		}
		
		return bvo;
	}
}
