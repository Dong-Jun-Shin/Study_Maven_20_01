package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class DealerVO implements Serializable {
	private static final long serialVersionUID = -1576767060432378767L;
	private String dName;
	private String dId;
	private String dPasswd;
	private String dEId;
	private String dEPw;
	private String dPhone;
	private String dAddress;
	private String dBOwner;
	private String dBNum;
	private String dBName;

	private DealerVO() {
		reset();
	}

	private static DealerVO instance = null;

	public static final DealerVO getInstance() {
		if (instance == null)
			new DealerVO();
		return instance;
	}

	public String getDName() {
		return dName;
	}

	public void setDName(String dName) {
		this.dName = dName;
	}

	public String getDId() {
		return dId;
	}

	public void setDId(String dId) {
		this.dId = dId;
	}

	public String getDPasswd() {
		return dPasswd;
	}

	public void setDPasswd(String dPasswd) {
		this.dPasswd = dPasswd;
	}

	public String getDEId() {
		return dEId;
	}

	public void setDEId(String dEId) {
		this.dEId = dEId;
	}

	public String getDEPw() {
		return dEPw;
	}

	public void setDEPw(String dEPw) {
		this.dEPw = dEPw;
	}

	public String getDPhone() {
		return dPhone;
	}

	public void setDPhone(String dPhone) {
		this.dPhone = dPhone;
	}

	public String getDAddress() {
		return dAddress;
	}

	public void setDAddress(String dAddress) {
		this.dAddress = dAddress;
	}

	public String getDBOwner() {
		return dBOwner;
	}

	public void setDBOwner(String dBOwner) {
		this.dBOwner = dBOwner;
	}

	public String getDBNum() {
		return dBNum;
	}

	public void setDBNum(String dBNum) {
		this.dBNum = dBNum;
	}

	public String getDBName() {
		return dBName;
	}

	public void setDBName(String dBName) {
		this.dBName = dBName;
	}

	public static void setInstance(DealerVO instance) {
		DealerVO.instance = instance;
	}

	@Override
	public int hashCode() {
		return dId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DealerVO) {
			DealerVO other = (DealerVO) obj;
			if (dId.compareTo(other.dId) == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "DealerVO [dName=" + dName + ", dId=" + dId + ", dPasswd=" + dPasswd + ", dPhone=" + dPhone
				+ ", dAddress=" + dAddress + ", dBOwner=" + dBOwner + ", dBNum=" + dBNum + ", dBName=" + dBName + "]";
	}

	/**
	 * reset() : 파일에 저장되어 있는 판매업체의 정보를 읽기
	 * 
	 */
	public void reset() {
		// 판매업체 정보 읽기
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(new File("C:\\COMP\\data\\DealerVO.dat")))) {
			while (true) {
				instance = (DealerVO) ois.readObject();
				// 자료가 들어갔으면 멈춘다.
				if (instance != null) {
					break;
				} else {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			DataUtil.showAlert("정보 읽기 실패", "정보를 읽는 중 문제가 생겼습니다.");
		}
	}
}
