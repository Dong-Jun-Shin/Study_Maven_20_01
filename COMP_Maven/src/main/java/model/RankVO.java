package model;

public class RankVO {

	private String productSort;
	private String p_name;
	private int count;
	private int p_price;
	private int priceResult;

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

	public int getPriceResult() {
		return priceResult;
	}

	public void setPriceResult(int priceResult) {
		this.priceResult = priceResult;
	}

	public String getProductSort() {
		return productSort;
	}

	public void setProductSort(String productSort) {
		this.productSort = productSort;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "RankVO [productSort=" + productSort + ", p_name=" + p_name + ", count=" + count + ", p_price=" + p_price
				+ ", priceResult=" + priceResult + "]";
	}

}
