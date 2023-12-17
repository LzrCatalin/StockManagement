package Project;

public class ShoppingCart {
	private String barcode;
	private int quantity;
	private double price;

	public ShoppingCart(String barcode, int quantity, double price) {
		this.barcode = barcode;
		this.quantity = quantity;
		this.price = price;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
			out.append(getBarcode()).append("; ").append(getQuantity()).append("; ").append(getPrice());
		return out.toString();
	}
}
