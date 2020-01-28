package model;

public class ProductVO {

	private String p_num;
	private String p_name;
	private int p_qty;
	private int p_price;
	private String p_size;
	private String p_grt;
	private String p_date;
	private String p_img;
	private String p_reg;

	public String getP_num() {
		return p_num;
	}

	public void setP_num(String p_num) {
		this.p_num = p_num;
	}

	public int getP_qty() {
		return p_qty;
	}

	public void setP_qty(int p_qty) {
		this.p_qty = p_qty;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public int getP_price() {
		return p_price;
	}

	public void setP_price(int p_price) {
		this.p_price = p_price;
	}

	public String getP_size() {
		return p_size;
	}

	public void setP_size(String p_size) {
		this.p_size = p_size;
	}

	public String getP_grt() {
		return p_grt;
	}

	public void setP_grt(String p_grt) {
		this.p_grt = p_grt;
	}

	public String getP_date() {
		return p_date;
	}

	public void setP_date(String p_date) {
		this.p_date = p_date;
	}

	public String getP_img() {
		return p_img;
	}

	public void setP_img(String p_img) {
		this.p_img = p_img;
	}

	public String getP_reg() {
		return p_reg;
	}

	public void setP_reg(String p_reg) {
		this.p_reg = p_reg;
	}

	@Override
	public String toString() {
		return "ProductVO [p_num=" + p_num + ", p_qty=" + p_qty + ", p_name=" + p_name + ", p_price=" + p_price
				+ ", p_size=" + p_size + ", p_grt=" + p_grt + ", p_date=" + p_date + ", p_img=" + p_img + ", p_reg="
				+ p_reg + "]";
	}

}
