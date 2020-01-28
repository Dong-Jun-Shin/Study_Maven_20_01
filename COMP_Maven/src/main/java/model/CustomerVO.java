package model;

public class CustomerVO {

	private String c_num;
	private String c_name;
	private String c_id;
	private String c_pw;
	private String c_phone;
	private String c_add;
	private String c_birth;
	private String c_email;
	private String c_reg;

	public String getC_num() {
		return c_num;
	}

	public void setC_num(String c_num) {
		this.c_num = c_num;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public String getC_id() {
		return c_id;
	}

	public void setC_id(String c_id) {
		this.c_id = c_id;
	}

	public String getC_pw() {
		return c_pw;
	}

	public void setC_pw(String c_pw) {
		this.c_pw = c_pw;
	}

	public String getC_phone() {
		return c_phone;
	}

	public void setC_phone(String c_phone) {
		this.c_phone = c_phone;
	}

	public String getC_add() {
		return c_add;
	}

	public void setC_add(String c_add) {
		this.c_add = c_add;
	}

	public String getC_birth() {
		return c_birth;
	}

	public void setC_birth(String c_birth) {
		this.c_birth = c_birth;
	}

	public String getC_email() {
		return c_email;
	}

	public void setC_email(String c_email) {
		this.c_email = c_email;
	}

	public String getC_reg() {
		return c_reg;
	}

	public void setC_reg(String c_reg) {
		this.c_reg = c_reg;
	}

	@Override
	public String toString() {
		return "CustomerVO [c_num=" + c_num + ", c_name=" + c_name + ", c_id=" + c_id + ", c_pw=" + c_pw + ", c_phone="
				+ c_phone + ", c_add=" + c_add + ", c_birth=" + c_birth + ", c_email=" + c_email + ", c_reg=" + c_reg
				+ "]";
	}
}
