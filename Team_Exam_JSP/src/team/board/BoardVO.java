package team.board;

public class BoardVO {
	private int b_num;
	private String b_title;
	private String b_writer;
	private String b_reg_date;
	private int b_hits;
	private String b_content;
	private String b_file;

	public String getB_content() {
		return b_content;
	}

	public void setB_content(String b_content) {
		this.b_content = b_content;
	}

	public String getB_file() {
		return b_file;
	}

	public void setB_file(String b_file) {
		this.b_file = b_file;
	}

	public int getB_num() {
		return b_num;
	}

	public void setB_num(int b_num) {
		this.b_num = b_num;
	}

	public String getB_title() {
		return b_title;
	}

	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	public String getB_writer() {
		return b_writer;
	}

	public void setB_writer(String b_writer) {
		this.b_writer = b_writer;
	}

	public String getB_reg_date() {
		return b_reg_date;
	}

	public void setB_reg_date(String b_reg_date) {
		this.b_reg_date = b_reg_date;
	}

	public int getB_hits() {
		return b_hits;
	}

	public void setB_hits(int b_hits) {
		this.b_hits = b_hits;
	}

	@Override
	public String toString() {
		return "BoardVO [b_num=" + b_num + ", b_title=" + b_title + ", b_writer=" + b_writer + ", b_reg_date="
				+ b_reg_date + ", b_hits=" + b_hits + ", b_content=" + b_content + ", b_file=" + b_file + "]";
	}
}
