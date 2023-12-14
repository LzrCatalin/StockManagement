package Project;

import javax.swing.*;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Category {
	private String categoryName;
	private ArrayList<Product> products;

	public Category(String name, ArrayList<Product> products) {
		this.categoryName = name;
		this.products = products;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
			out.append("Category: ").append(getCategoryName());
			for (Product product : products) {
				out.append(product);
			}

		return out.toString();
	}
}
