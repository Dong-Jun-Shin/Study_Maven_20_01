package model;

public class OrderChartVO {

	private String ch_num;
	private String p_num;
	private int ch_qty;
	private String cd_num;

	public String getCh_num() {
		return ch_num;
	}

	public void setCh_num(String ch_num) {
		this.ch_num = ch_num;
	}

	public String getP_num() {
		return p_num;
	}

	public void setP_num(String p_num) {
		this.p_num = p_num;
	}

	public int getCh_qty() {
		return ch_qty;
	}

	public void setCh_qty(int ch_qty) {
		this.ch_qty = ch_qty;
	}

	public String getCd_num() {
		return cd_num;
	}

	public void setCd_num(String cd_num) {
		this.cd_num = cd_num;
	}

	@Override
	public String toString() {
		return "OrderChartVO [ch_num=" + ch_num + ", p_num=" + p_num + ", ch_qty=" + ch_qty + ", cd_num=" + cd_num
				+ "]";
	}
}
