package model;

public class CdOrderVO {

	private String cd_sort; // Default
	private String cd_num; // 시퀀스 생성
	private String c_num; // 입력 필요한 값(FK)
	private String cd_reg; // Default
	private int cd_price; // 입력 필요한 값

	public String getCd_sort() {
		return cd_sort;
	}

	public void setCd_sort(String cd_sort) {
		this.cd_sort = cd_sort;
	}

	public String getCd_num() {
		return cd_num;
	}

	public void setCd_num(String cd_num) {
		this.cd_num = cd_num;
	}

	public String getC_num() {
		return c_num;
	}

	public void setC_num(String c_num) {
		this.c_num = c_num;
	}

	public String getCd_reg() {
		return cd_reg;
	}

	public void setCd_reg(String cd_reg) {
		this.cd_reg = cd_reg;
	}

	public int getCd_price() {
		return cd_price;
	}

	public void setCd_price(int cd_price) {
		this.cd_price = cd_price;
	}

	@Override
	public String toString() {
		return "CdOrderVO [cd_sort=" + cd_sort + ", cd_num=" + cd_num + ", c_num=" + c_num + ", cd_reg=" + cd_reg
				+ ", cd_price=" + cd_price + "]";
	}

}
