package Project;

import jdk.jshell.spi.SPIResolutionException;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
	/**
	 * Create table stock for my database
	 * @param table_name = database table name
	 */
	public static void creatingTable(String table_name) {
		// SQLite's connection string
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";

		try {
			// Create a connection to the database
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected to SQLite database.");

			// Create a statement
			String sql = "CREATE TABLE IF NOT EXISTS " + table_name +"(\n"
					+ " id integer PRIMARY KEY AUTOINCREMENT,\n"
					+ "barcode text NOT NULL,\n"
					+ " category text NOT NULL,\n"
					+ " product_name text,\n"
					+ "quantity int,\n"
					+ " price real\n"
					+ ");";


			Statement statement = ((Connection) connection).createStatement();
			statement.execute(sql);

			System.out.println("Table created successfully.");
			// Close the connection
			connection.close();

		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static void creatingShoppingCartTable() {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected to SQLite database.");

			// Create statement
			String sql = "CREATE TABLE IF NOT EXISTS shoppingcart (\n"
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
					+ "barcode TEXT,\n"
					+ "quantity INTEGER,\n"
					+ "price REAL,\n"
					+ "FOREIGN KEY(barcode) REFERENCES stock(barcode)\n"
					+ ");";

			Statement statement = connection.createStatement();
			statement.execute(sql);

			System.out.println("Table {shopping cart} created succesffully.");
			// Close connection
			connection.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Here I added a new column to my stock table
	 */
	public static void addColumnToStock() {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected to SQLite database.");

			// Alter table to add the barcode column
			String alterSql = "ALTER TABLE stock ADD COLUMN barcode TEXT";

			Statement statement = connection.createStatement();
			statement.execute(alterSql);

			System.out.println("Column 'barcode' added successfully.");

			connection.close();
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Here I added a new constraint to my barcode column
	 */
	public static void addUniqueConstraintToBarcode() {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected to SQLite database.");

			// Alter table to add a unique constraint on the barcode column
			String alterSql = "CREATE UNIQUE INDEX idx_unique_barcode ON stock (barcode)";

			Statement statement = connection.createStatement();
			statement.execute(alterSql);

			System.out.println("Unique constraint 'idx_unique_barcode' added successfully.");

			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Function for helping us find a product in stock db
	 * @param categoryName = name of category
	 * @param productName = name of product
	 * @return = = true if we found it / false if not
	 */
	public static boolean isProductInStock(String categoryName, String productName) {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";
		boolean exists = false;

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected successfully to database");

			// Query to check if products exists in stock based on category name and product name
			String query = "SELECT * FROM stock WHERE category = ? and product_name = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, categoryName);
			preparedStatement.setString(2, productName);

			// Get result from executing query
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				// Assign the boolean value
				exists = (count > 0);
			}

			// Close connection
			connection.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return exists;
	}

	/**
	 * Function for helping us find a product in stock based by barcode
	 * @param categoryName = category where we do search
	 * @param barcode = barcode we search for
	 * @return = true if we found it / false if not
	 */
	public static boolean isProductInStockByBarcode(String categoryName, String barcode) {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";
		boolean exists = false;

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected successfully to database");

			// Query to check if products exists in stock based on category name and product name
			String query = "SELECT * FROM stock WHERE category = ? and barcode = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, categoryName);
			preparedStatement.setString(2, barcode);

			// Get result from executing query
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				// Assign the boolean value
				exists = (count > 0);
			}

			// Close connection
			connection.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return exists;
	}

	/**
	 * Function to add product in stock db
	 * @param categoryName = name of category where we want to add
	 * @param product = product to add
	 */
	public static void insertIntoStockTable(String categoryName, Product product) {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected to SQLite database.");
			String productName = product.getName();
			String barcode = product.getBarcode();

			//boolean productExists = isProductInStock(categoryName, productName);
			boolean productExists = isProductInStockByBarcode(categoryName, barcode);
			if (!productExists) {
				String sql = "INSERT INTO stock (category, barcode, product_name, quantity, price) VALUES (?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, categoryName);
				preparedStatement.setString(2, product.getBarcode());
				preparedStatement.setString(3, product.getName());
				preparedStatement.setInt(4, product.getQuantity());
				preparedStatement.setDouble(5, product.getPrice());

				preparedStatement.executeUpdate();
				System.out.println("Data added successfully in stock");
			} else {
				System.out.println("Product already exists in stock: " + productName + " on category: " + categoryName);
			}

			// Close connection
			connection.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Function to get product details based on barcode
	 * @param barcode = barcode of the product we want to retrieve
	 * @return = true if we found it/ false if not
	 */
	public static Product getProductDetails(String barcode) {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";
		Product product = null;

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected successfully to database.");

			String query = "SELECT * FROM stock WHERE barcode = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, barcode);

			ResultSet resultSet = preparedStatement.executeQuery();

			// Check if the product exists
			if (resultSet.next()) {
				String productName = resultSet.getString("product_name");
				int quantity = resultSet.getInt("quantity");
				double price = resultSet.getDouble("price");

				// Create a Product object with the retrieved details
				product = new Product(barcode, productName, quantity, price);
			}

			// Close connection
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return product;
	}

	/**
	 * Function for modifying product quantity and price
	 * @param categoryName = category name where to modify product
	 * @param product = product that we want to modify
	 * @param newQuantity = new quantity for the product
	 * @param newPrice = new price for the product
	 */
	public static void modifyProduct(String categoryName, Product product, int newQuantity, double newPrice) {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected to SQLite database.");

			String barcode = product.getBarcode();
			String productName = product.getName();
			int quantity = product.getQuantity();
			double price = product.getPrice();

			if (quantity != newQuantity && price != newPrice) {
				boolean productExists = isProductInStockByBarcode(categoryName, barcode);
				if (productExists) {
					String sql = "UPDATE stock SET quantity = ?, price = ? WHERE category = ? AND product_name = ?";

					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, newQuantity);
					preparedStatement.setDouble(2, newPrice);
					preparedStatement.setString(3, categoryName);
					preparedStatement.setString(4, productName);

					int confirm = preparedStatement.executeUpdate();
					if (confirm > 0) {
						System.out.println("Product " + productName + " in category " + categoryName + " successfully modified");
					} else {
						System.out.println(" <!> Error modifying product <!>");
					}
				} else {
					System.out.println("Product " + productName + " in category " + categoryName + " not in stock");
				}
			} else {
				System.out.println("New values are the same as the old values.");
			}

			// Close connection
			connection.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Function for deleting a row from stock db based on product barcode and category
	 * @param categoryName = category from which we want to delete
	 * @param product = product which we want to delete
	 */
	public static void deleteProductByBarcode(String categoryName, Product product) {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected successfully to database.");

			String barcode = product.getBarcode();

			boolean productExists = isProductInStockByBarcode(categoryName, barcode);
			if (productExists) {
				String sql = "DELETE FROM stock WHERE category = ? AND barcode = ?";

				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, categoryName);
				preparedStatement.setString(2, barcode);

				int result = preparedStatement.executeUpdate();
				if (result > 0) {
					System.out.printf("Successfully deleted product with barcode {%s} from category {%s}\n", barcode, categoryName);
				} else {
					System.out.println("<!> Error while deleting product <!>");
				}
			} else {
				System.out.println("Product with the provided barcode was not found in the stock for the given category");
			}

			// Close connection
			connection.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Function for checking if barcode exists inside shopping cart
	 * @param product = product data
	 * @return true if barcode found/ false if not
	 */
	public static boolean isProductInCartByBarcode(Product product) {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";
		boolean exists = false;

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected successfully to shopping cart db.");

			String query = "SELECT * FROM shoppingcart WHERE barcode = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, product.getBarcode());

			// Get result
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				// Assign the boolean value
				exists = (count > 0);
			}

			// Close connection
			connection.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return exists;
	}

	/**
	 * Function for inserting product into shopping cart
	 * @param product = product data
	 *                TODO: DO IMPLEMENTATION FOR ADDING THE WANTED QUANTITY FOR EACH PRODUCT
	 */
	public static void insertIntoShoppingCart(Product product) {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected successfully to shopping cart db.");

			boolean productExists = isProductInCartByBarcode(product);
			if (!productExists) {
				String query = "INSERT INTO shoppingcart (barcode, quantity, price) VALUES (?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, product.getBarcode());
				preparedStatement.setInt(2, product.getQuantity());
				preparedStatement.setDouble(3, product.getPrice());

				preparedStatement.executeUpdate();
				System.out.println("Successfully added product into shopping cart.");
			}
			// ADD ELSE
			// Close connection
			connection.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Main where we test our functions
	 * @param args = args
	 */
	public static void main(String[] args) {

		//creatingShoppingCartTable();
		//creatingTable("stock");
		//addColumnToStock();
		//addUniqueConstraintToBarcode();

		/**
		 // Creating the hashmap
		 HashMap<String, ArrayList<Product>> categories = new HashMap<>();

		 ArrayList<Product> CPUs = new ArrayList<>();
		 CPUs.add(new Product("0103", "I7-11700HQ", 5, 250));
		 CPUs.add(new Product("0104", "I7-7960K", 10, 175));
		 categories.put("CPUs", CPUs);

		 ArrayList<Product> Graphic_Cards = new ArrayList<>();
		 Graphic_Cards.add(new Product("0201", "RTX 3060", 5, 340));
		 Graphic_Cards.add(new Product("0202", "GTX 1080Ti", 2, 500));
		 Graphic_Cards.add(new Product("0203", "RTX 4080", 4, 650));
		 categories.put("Graphic Cards", Graphic_Cards);

		 // Implementation for adding new items into stock db
		 for (Map.Entry<String, ArrayList<Product>> entry : categories.entrySet()) {
		 String categoryName = entry.getKey();
		 ArrayList<Product> products = entry.getValue();

		 for (Product product : products) {
		 insertIntoStockTable(categoryName, product);
		 }
		 }
		 **/

		//insertIntoStockTable("CPUs", new Product("0101", "I9-11750KF", 5, 650));

		//Product product = getProductDetails("0101");
		//System.out.println(isProductInCartByBarcode(product));
		//insertIntoShoppingCart(product);

		//boolean result = isProductInStockByBarcode("CPUs", "0101");
		//System.out.println(result);

		// Calling modify product function
		/**
		 if (product != null) {
		 	modifyProduct("Graphic Cards", product, 5, 349);
		 } else {
		 	System.out.println("Product not found.");
		 }
		**/


		// Calling delete product function
		/**
		 if (product != null) {
			 deleteProductByBarcode("CPUs", product);
		 } else {
			 System.out.println("Product not found.");
		 }
		 **/
	}
}
