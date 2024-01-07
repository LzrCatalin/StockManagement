package Project;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestClass {
	@Test
	/**
	 * Test that the constructor for Product with 4 parameters works
	 */
	void testProduct() {
		Product product = new Product("1234567890123", "Test Product", 1, 1.99);
		assertEquals("1234567890123", product.getBarcode());
		assertEquals("Test Product", product.getName());
		assertEquals(1, product.getQuantity());
		assertEquals(1.99, product.getPrice());
	}

	@Test
	/**
	 * Test that the constructor for Product with 3 parameters works
	 */
	void testCategory() {
		ArrayList<Product> products = new ArrayList<Product>();
		products.add(new Product("1234567890123", "Test Product", 1, 1.99));
		Category category = new Category("Test Category", products);
		assertEquals("Test Category", category.getCategoryName());
		assertEquals(products, category.getProducts());
	}

	@Test
	/**
	 * Test that the constructor for Category works
	 */
	void isProductInStockByBarcode() {
		Assertions.assertTrue(Main.isProductInStockByBarcode("CPUs", "0155"), "Product should be in stock");
	}

	@Test
	/**
	 * Test that the isProductInStockByBarcode method works
	 */
	void modifyProduct() {
		Product product = Main.getProductDetails("0102");
		Main.modifyProduct("CPUs", product, 1, 10);
		Assertions.assertTrue(Main.isProductInStockByBarcode("CPUs", "0102"), "Product should be in stock");
	}
}
