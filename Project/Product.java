package Project;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.Collection;

public class Product {
	private String barcode;
	private String name;
	private int quantity;
	private double price;

	public Product(String barcode, String name, int quantity, double price) {
		this.barcode = barcode;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
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
