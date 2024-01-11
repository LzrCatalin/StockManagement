package Project;

 /**
 * This class is used to store the products that the user has added to their shopping cart
 */
public class Product {
	private String barcode;
	private String name;
	private int quantity;
	private double price;

	/**
	 * Constructor for Product
	 * @param barcode = barcode of the product
	 * @param name = name of the product
	 * @param quantity = quantity of the product
	 * @param price = price of the product
	 */
	public Product(String barcode, String name, int quantity, double price) {
		this.barcode = barcode;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	// New constructor used for products stored inside shopping cart
	public Product(String barcode, int quantity, double price) {
		this.barcode = barcode;
		this.quantity = quantity;
		this.price = price;
		// Set default name
		this.name = "";
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
			out.append(getBarcode()).append("; ").append(getName()).append("; ").append(getQuantity()).append("; ").append(getPrice());
		return out.toString();
	}
}
