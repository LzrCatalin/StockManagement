package Project;

import java.util.ArrayList;

/**
 * This class is used to store the products that the user has added to their shopping cart
 */
public class Category {
	private String categoryName;
	private ArrayList<Product> products;

	/**
	 * Constructor for Category
	 * @param name = name of the category
	 * @param products = products in the category
	 */
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
