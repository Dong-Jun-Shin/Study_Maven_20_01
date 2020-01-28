package model;

public class WarehouseVO {

	private String wh_num;
	private String tr_num;
	private String p_num;	
	private int wh_qty;
	private String wh_reg;

	public String getWh_num() {
		return wh_num;
	}

	public void setWh_num(String wh_num) {
		this.wh_num = wh_num;
	}

	public int getWh_qty() {
		return wh_qty;
	}

	public void setWh_qty(int wh_qty) {
		this.wh_qty = wh_qty;
	}

	public String getTr_num() {
		return tr_num;
	}

	public void setTr_num(String tr_num) {
		this.tr_num = tr_num;
	}

	public String getP_num() {
		return p_num;
	}

	public void setP_num(String p_num) {
		this.p_num = p_num;
	}

	public String getWh_reg() {
		return wh_reg;
	}

	public void setWh_reg(String wh_reg) {
		this.wh_reg = wh_reg;
	}

	@Override
	public String toString() {
		return "WarehouseVO [wh_num=" + wh_num + ", wh_qty=" + wh_qty + ", tr_num=" + tr_num + ", p_num=" + p_num
				+ ", wh_reg=" + wh_reg + "]";
	}

}
