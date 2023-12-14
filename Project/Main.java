package Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
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

	public static void addCollumnToStock() {
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

	public static boolean isProductInStock(String categoryName, String productName) {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";
		boolean exists = false;

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected succesfully to database");

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
	public static void insertIntoStockTable(String categoryName, Product product) {
		String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";

		try {
			Connection connection = DriverManager.getConnection(url);
			System.out.println("Connected to SQLite database.");
			String productName = product.getName();

			boolean productExists = isProductInStock(categoryName, productName);
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
	public static void main(String[] args) {
		//creatingTable("stock");
		//addCollumnToStock();
		//addUniqueConstraintToBarcode();


		// Creating the hashmap
		HashMap<String, ArrayList<Product>> categories = new HashMap<>();

		ArrayList<Product> CPUs = new ArrayList<>();
		CPUs.add(new Product("0103", "I7-11700HQ", 5, 250));
		CPUs.add(new Product("0104", "I7-7960K", 10, 175));
		categories.put("CPUs", CPUs);

		ArrayList<Product> Graphic_Cards = new ArrayList<>();
		Graphic_Cards.add(new Product("0202", "RTX 3060", 5, 340));
		Graphic_Cards.add(new Product("0202", "GTX 1080Ti", 2, 500));
		categories.put("Graphic Cards", Graphic_Cards);


		for (Map.Entry<String, ArrayList<Product>> entry : categories.entrySet()) {
			String categoryName = entry.getKey();
			ArrayList<Product> products = entry.getValue();

			for (Product product : products) {
				insertIntoStockTable(categoryName, product);
			}
		}
	}
}
