package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import controller.LoginMainController;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class DataUtil extends NumberFormatException {
	private static final long serialVersionUID = -598464327373022988L;
	private static Logger logger = Logger.getLogger(DataUtil.class);

	/**
	 * fieldName() : 파일 유효성 검사 메소드
	 * 
	 * @param obj 검사 대상
	 * @return List<String> 파일 이름을 담은 리스트 반환
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> fieldName(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		List<String> result = new ArrayList();
		for (int i = 0; i < fields.length; i++) {
			try {
				result.add(fields[i].getName());
			} catch (Exception e) {
				System.out.println("[   fieldName(Object obj)  ]");
				return null;
			}
		}
		return result;
	}

	/**
	 * validityCheck() : 값 입력 체크 메소드
	 * 
	 * @param value 입력 대상
	 * @param data  출력문
	 * @return result 검사 결과
	 */
	public static boolean validityCheck(String value, String data) {
		boolean result = true;
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("입력 여부 확인");
		// 입력값 공백 여부 확인
		if (isBlank(value)) {
			alert.setHeaderText(data + " 입력 또는 선택필요.");
			alert.setContentText("공백 입력 불가.");
			alert.showAndWait();
			result = false;
		}
		return result;
	}

	/**
	 * isBlank(String str) : 공백 여부 확인 메소드
	 * 
	 * @param str (String) : 확인 대상
	 * @return boolean
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * valLimitCheck() : 각 자리의 데이터 범위 유효여부를 체크 (name, id, pw, phone, address, email,
	 * bNum)
	 * 
	 * @param value     확인할 데이터 문자열
	 * @param fieldName 데이터 범위의 기준 (이름, id, 전화번호...)
	 * @return result 이상 없으면 true
	 */
	public static boolean valLimitCheck(String value, int size) {
		Alert alert = new Alert(AlertType.WARNING);
		boolean result = true;
		int limit = 0;

		limit = size;

		// 유효하지 않으면 경고
		if (value.getBytes().length > limit) {
			result = false;
			alert.setHeaderText("입력 오류");
			alert.setContentText("입력된 내용의 글자 수가 너무 큽니다.\n좀 더 짧게 해주세요.");
			alert.showAndWait();
		}

		return result;
	}

	/**
	 * dateCheck() : Date형식 체크 및 변환
	 * 
	 * @param date 체크할 날짜를 받는다.
	 * @return sb "YYYY-MM-DD"로 변환된 StringBuffer를 반환
	 */
	public static StringBuffer dateCheck(String date) {
		StringBuffer sb = new StringBuffer();

		try {
			if (DataUtil.validityCheck(date, "생일")) {
				String s_birth = date.trim();
				String s_year = s_birth.substring(0, 4);
				String s_month = s_birth.substring(5, 7);
				String s_day = s_birth.substring(8, 10);

				// 유효한 날짜인지 범위 체크
				if (!(Integer.parseInt(s_year) < 10000 && Integer.parseInt(s_year) > 0)) {
					throw new NumberFormatException();
				} else if (!(Integer.parseInt(s_month) < 13 && Integer.parseInt(s_month) > 0)) {
					throw new NumberFormatException();
				} else if (!(Integer.parseInt(s_day) < 32 && Integer.parseInt(s_day) > 0)) {
					throw new NumberFormatException();
				}

				sb.append(s_year);
				sb.append("-");
				sb.append(s_month);
				sb.append("-");
				sb.append(s_day);
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			DataUtil.showAlert("날짜 입력 오류", "날짜를 'YYYY-MM-DD'의 형식으로 쓰고, 년월일의 범위 안으로 입력해주세요.\n월(1~12), 일(1~31)");
			return null;
		} catch (StringIndexOutOfBoundsException stre) {
			DataUtil.showAlert("날짜 입력 오류", "날짜를 'YYYY-MM-DD'의 형식으로 쓰고, 년월일의 범위 안으로 입력해주세요.\n월(1~12), 일(1~31)");
			return null;
		}

		return sb;
	}

	/**
	 * getKey() : 필요한 문자열 리스트를 반환
	 * 
	 * @param sel 필요한 리스트의 이름
	 * @return key 선택한 문자열 배열 반환
	 */
	public static String[] getKey(String sel) {
		String key[];
		switch (sel) {
		case "method":
			key = new String[] { "Cp", "R", "Mb", "G", "Ss", "H", "Po", "Ca", "Co", "Sw", "K", "Mo", "Sp", "Mn" };
			break;
		case "id":
			key = new String[] { "CP", "R", "MB", "G", "SS", "H", "PO", "CA", "CO", "SW", "K", "MO", "SP", "MN" };
			break;
		case "pSort":
			key = new String[] { "CPU", "RAM", "MB", "GPU", "SSD", "HDD", "파워", "케이스", "쿨러", "SW", "키보드", "마우스", "스피커",
					"모니터" };
			break;
		default:
			key = null;
			break;
		}

		return key;
	}

	/**
	 * getIdxVal() : 인덱스에 해당하는 숫자 리스트를 반환
	 *
	 * @return idxVal 숫자 리스트(0 ~ 13)
	 */
	public static Integer[] getIdxVal() {
		Integer idxVal[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };

		return idxVal;
	}

	/**
	 * showAlert() : 경고창을 보여준다.
	 * 
	 * @param head    제목
	 * @param content 내용
	 */
	public static void showAlert(String head, String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(head);
		alert.setContentText(content);
		alert.showAndWait();
	}

	/**
	 * showInfoAlert() : 알림창을 보여준다.
	 * 
	 * @param head    제목
	 * @param content 내용
	 */
	public static void showInfoAlert(String head, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(head);
		alert.setContentText(content);
		alert.showAndWait();
	}

	/**
	 * send() : 이메일을 전송
	 * 
	 * @param evo 메일 전송에 필요한 데이터
	 * @return result 메일 전송 결과
	 */
	@SuppressWarnings("deprecation")
	public static String send(EmailVO evo) {
		long beginTime = System.currentTimeMillis();
		SimpleEmail email = new SimpleEmail();
		String result = "Failure";

		try {
			// Smtp 서버 연결 설정.(구글 아이디, 비번)
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthentication(evo.getGoogleId(), evo.getGooglePwd());

			// Smtp SSL, TLS 설정 (암호화 단계)
			email.setSSL(true);
			email.setTLS(true);

			// 받는 사람 설정
			email.addTo(evo.getEmailTo(), evo.getEmailToName(), "utf-8");
			// 보내는 사람 설정
			email.setFrom(evo.getEmailFrom(), evo.getEmailFromName(), "utf-8");
			// 제목 설정
			email.setSubject(evo.getEmailSubject());
			// 본문 설정
			email.setMsg(evo.getEmailMsg());
			// 메일 발송
			result = email.send();

			long execTime = System.currentTimeMillis() - beginTime;
			logger.info("exec time = " + execTime + "ms");
			logger.info("RT Msg = " + result);

			if (result.indexOf("JavaMail") > 0) {
				result = "Success";
			}
		} catch (EmailException e) {
			logger.warn("Error", e);
			result = "Failure";
		}
		return result;
	}

	/**
	 * setTheme() : 선택한 테마를 적용해서 반환
	 * 
	 * @param root  적용할 Parent
	 * @param theme LIGHT, DARK
	 * @return root 적용된 Parent
	 */
	public static void setTheme(Parent root) {
		if (LoginMainController.isTheme()) {
			new JMetro(root, Style.LIGHT);
			root.setStyle("-fx-background-color:#EFF8FF;");
		} else {
			new JMetro(root, Style.DARK);
			root.setStyle("-fx-background-color:#555555;");
		}
	}
	
	/**
	 * getImgPath() : 테마에 따른 이미지 경로를 반환한다.
	 * 
	 * @return 이미지 경로와 이름
	 */
	public static String getImgPath() {
		// 이미지 지정
		String localUrl = "file:C:\\COMP\\image\\Theme\\";
		StringBuffer selectFileName = new StringBuffer();
		selectFileName.append("light\\");

		if (LoginMainController.isTheme()) {
			selectFileName = new StringBuffer();
			selectFileName.append("light\\");
		} else {
			selectFileName = new StringBuffer();
			selectFileName.append("dark\\");
		}

		return localUrl + selectFileName.toString();
	}
}
