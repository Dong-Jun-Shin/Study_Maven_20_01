package model;

public class TraderVO {
	private String tr_num;
	private String tr_name;
	private String tr_phone;
	private String tr_add;
	private String tr_bowner;
	private String tr_bnum;
	private String tr_bname;
	private String tr_reg;

	public String getTr_num() {
		return tr_num;
	}

	public void setTr_num(String tr_num) {
		this.tr_num = tr_num;
	}

	public String getTr_name() {
		return tr_name;
	}

	public void setTr_name(String tr_name) {
		this.tr_name = tr_name;
	}

	public String getTr_phone() {
		return tr_phone;
	}

	public void setTr_phone(String tr_phone) {
		this.tr_phone = tr_phone;
	}

	public String getTr_add() {
		return tr_add;
	}

	public void setTr_add(String tr_add) {
		this.tr_add = tr_add;
	}

	public String getTr_bowner() {
		return tr_bowner;
	}

	public void setTr_bowner(String tr_bowner) {
		this.tr_bowner = tr_bowner;
	}

	public String getTr_bnum() {
		return tr_bnum;
	}

	public void setTr_bnum(String tr_bnum) {
		this.tr_bnum = tr_bnum;
	}

	public String getTr_bname() {
		return tr_bname;
	}

	public void setTr_bname(String tr_bname) {
		this.tr_bname = tr_bname;
	}

	public String getTr_reg() {
		return tr_reg;
	}

	public void setTr_reg(String tr_reg) {
		this.tr_reg = tr_reg;
	}

	@Override
	public String toString() {
		return "TraderVO [tr_num=" + tr_num + ", tr_name=" + tr_name + ", tr_phone=" + tr_phone + ", tr_add=" + tr_add
				+ ", tr_bowner=" + tr_bowner + ", tr_bnum=" + tr_bnum + ", tr_bname=" + tr_bname + ", tr_reg=" + tr_reg
				+ "]";
	}

}
