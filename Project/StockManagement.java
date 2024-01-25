package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to manage the stock of the store interface.
 */
public class StockManagement extends JFrame {
	private JTextField categoryField;
	private JTextField barcodeField;
	private JTextField productNameField;
	private JTextField quantityField;
	private JTextField priceField;
	private JTextField newQuantityField;
	private JTextField newPriceField;

	/**
	 * This method returns a JButton that adds a product to the stock.
	 * @return = JButton that adds a product to the stock.
	 */
	private JButton getAddButton() {
		JButton addProductButton = new JButton("Add Product");
		addProductButton.setFont(new Font("Arial", Font.BOLD, 14));
		addProductButton.setForeground(Color.WHITE);
		addProductButton.setBackground(new Color(0, 102, 204)); // Dark Blue Background
		addProductButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		addProductButton.addActionListener(e -> {
			JFrame addFrame = new JFrame("Add Product");
			addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			addFrame.setSize(400, 200);

			JPanel addPanel = new JPanel();
			addPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 5, 5, 5);

			categoryField = new JTextField(20);
			barcodeField = new JTextField(20);
			productNameField = new JTextField(20);
			quantityField = new JTextField(20);
			priceField = new JTextField(20);

			JButton confirmAddButton = new JButton("Confirm Add");
			confirmAddButton.setFont(new Font("Arial", Font.BOLD, 12));
			confirmAddButton.setForeground(Color.WHITE);
			confirmAddButton.setBackground(new Color(0, 102, 0)); // Dark Green Background
			confirmAddButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

			confirmAddButton.addActionListener(e1 -> {
				if (categoryField.getText().isEmpty() || barcodeField.getText().isEmpty() ||
						productNameField.getText().isEmpty() || quantityField.getText().isEmpty() ||
						priceField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill all the spaces with data.");
				} else {
					try {
						String category = categoryField.getText();
						String barcode = barcodeField.getText();
						String name = productNameField.getText();

						if (Main.insertIntoStockTable(category, new Product(barcode, name,
								Integer.parseInt(quantityField.getText()), Double.parseDouble(priceField.getText())))) {
							JOptionPane.showMessageDialog(null, "Product added successfully");
						} else {
							JOptionPane.showMessageDialog(null, "Failed to add product. It may already exist in the stock.");
						}
					} catch (NumberFormatException | HeadlessException ex) {
						throw new RuntimeException(ex);
					}
				}
			});

			gbc.gridx = 0;
			gbc.gridy = 0;
			addPanel.add(new JLabel("Category:"), gbc);
			gbc.gridx = 1;
			addPanel.add(categoryField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			addPanel.add(new JLabel("Barcode:"), gbc);
			gbc.gridx = 1;
			addPanel.add(barcodeField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			addPanel.add(new JLabel("Product Name:"), gbc);
			gbc.gridx = 1;
			addPanel.add(productNameField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			addPanel.add(new JLabel("Quantity:"), gbc);
			gbc.gridx = 1;
			addPanel.add(quantityField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			addPanel.add(new JLabel("Price:"), gbc);
			gbc.gridx = 1;
			addPanel.add(priceField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.gridwidth = 2;
			addPanel.add(confirmAddButton, gbc);

			addFrame.add(addPanel);
			addFrame.setVisible(true);
		});

		return addProductButton;
	}


	/**
	 * This method returns a JButton that modifies a product in the stock.
	 * @return = JButton that modifies a product in the stock.
	 */
	private JButton getModifyButton() {
		JButton modifyProductButton = new JButton("Modify Product");
		modifyProductButton.setFont(new Font("Verdana", Font.BOLD, 14));
		modifyProductButton.setForeground(Color.WHITE);
		modifyProductButton.setBackground(new Color(6, 65, 243)); // Dark Orange Background
		modifyProductButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		modifyProductButton.addActionListener(e -> {
			JFrame modifyFrame = new JFrame("Modify Product");
			modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			modifyFrame.setSize(400, 200);

			JPanel modifyPanel = new JPanel();
			modifyPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 5, 5, 5);

			categoryField = new JTextField(20);
			barcodeField = new JTextField(20);
			newQuantityField = new JTextField(20);
			newPriceField = new JTextField(20);

			JButton confirmModifyButton = new JButton("Confirm Modify");
			confirmModifyButton.setFont(new Font("Verdana", Font.BOLD, 12));
			confirmModifyButton.setForeground(Color.WHITE);
			confirmModifyButton.setBackground(new Color(34, 139, 34)); // Forest Green Background
			confirmModifyButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

			confirmModifyButton.addActionListener(e1 -> {
				if (categoryField.getText().isEmpty() || barcodeField.getText().isEmpty() ||
						newQuantityField.getText().isEmpty() || newPriceField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill all the spaces with data.");
				} else {
					try {
						String category = categoryField.getText();
						String barcodeToModify = barcodeField.getText();
						int newQuantity = Integer.parseInt(newQuantityField.getText());
						double newPrice = Double.parseDouble(newPriceField.getText());

						Product product = Main.getProductDetails(barcodeToModify);
						if (product != null) {
							if (Main.modifyProduct(category, product, newQuantity, newPrice)) {
								JOptionPane.showMessageDialog(null, "Product modified successfully");
							} else {
								JOptionPane.showMessageDialog(null, "Failed modifying product.");
							}
						} else {
							JOptionPane.showMessageDialog(null,  "Product doesn't exist.");
						}
					} catch (NumberFormatException | HeadlessException ex) {
						throw new RuntimeException(ex);
					}
				}
				modifyFrame.dispose();
			});

			gbc.gridx = 0;
			gbc.gridy = 0;
			modifyPanel.add(new JLabel("Category:"), gbc);
			gbc.gridx = 1;
			modifyPanel.add(categoryField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			modifyPanel.add(new JLabel("Barcode to Modify:"), gbc);
			gbc.gridx = 1;
			modifyPanel.add(barcodeField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			modifyPanel.add(new JLabel("New Quantity:"), gbc);
			gbc.gridx = 1;
			modifyPanel.add(newQuantityField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			modifyPanel.add(new JLabel("New Price:"), gbc);
			gbc.gridx = 1;
			modifyPanel.add(newPriceField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.gridwidth = 2;
			modifyPanel.add(confirmModifyButton, gbc);

			modifyFrame.add(modifyPanel);
			modifyFrame.setVisible(true);
		});

		return modifyProductButton;
	}


	/**
	 * This method returns a JButton that deletes a product from the stock.
	 * @return = JButton that deletes a product from the stock.
	 */
	private JButton getDeleteButton() {
		JButton deleteProductButton = new JButton("Delete Product");
		deleteProductButton.setFont(new Font("Calibri", Font.BOLD, 14));
		deleteProductButton.setForeground(Color.WHITE);
		deleteProductButton.setBackground(new Color(178, 34, 34)); // Firebrick Background
		deleteProductButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		deleteProductButton.addActionListener(e -> {
			JFrame deleteFrame = new JFrame("Delete Product");
			deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			deleteFrame.setSize(400, 200);

			JPanel deletePanel = new JPanel();
			deletePanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 5, 5, 5);

			categoryField = new JTextField(20);
			barcodeField = new JTextField(20);

			JButton confirmDeleteButton = new JButton("Confirm Delete");
			confirmDeleteButton.setFont(new Font("Calibri", Font.BOLD, 12));
			confirmDeleteButton.setForeground(Color.WHITE);
			confirmDeleteButton.setBackground(new Color(139, 0, 0)); // Dark Red Background
			confirmDeleteButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

			confirmDeleteButton.addActionListener(e1 -> {
				if (categoryField.getText().isEmpty() || barcodeField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill all the fields.");
				} else {
					try {
						String category = categoryField.getText();
						String barcode = barcodeField.getText();

						Product product = Main.getProductDetails(barcode);
						if (product != null) {
							if (Main.deleteProduct(category, product)) {
								JOptionPane.showMessageDialog(null, "Product deleted successfully.");
							} else {
								JOptionPane.showMessageDialog(null, "Failed to delete product");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Product doesn't exist.");
						}
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
				deleteFrame.dispose();
			});

			gbc.gridx = 0;
			gbc.gridy = 0;
			deletePanel.add(new JLabel("Category:"), gbc);
			gbc.gridx = 1;
			deletePanel.add(categoryField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			deletePanel.add(new JLabel("Barcode to Delete:"), gbc);
			gbc.gridx = 1;
			deletePanel.add(barcodeField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridwidth = 2;
			deletePanel.add(confirmDeleteButton, gbc);

			deleteFrame.add(deletePanel);
			deleteFrame.setVisible(true);
		});

		return deleteProductButton;
	}


	/**
	 * This method returns a JButton that opens the admin panel.
	 * @return = JButton that opens the admin panel.
	 */
	private JButton getAdminButton() {
		JButton adminButton = new JButton("Admin");
		adminButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame adminFrame = new JFrame("Admin Panel");
				adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				adminFrame.setSize(400, 200);

				JPanel adminPanel = new JPanel();
				adminPanel.setLayout(new GridLayout(0, 1));

				JButton addProductButton = getAddButton();
				JButton modifyProductButton = getModifyButton();
				JButton deleteProductButton = getDeleteButton();
				JButton stockDetailsButton = getStockDetails();

				adminPanel.add(addProductButton);
				adminPanel.add(modifyProductButton);
				adminPanel.add(deleteProductButton);
				adminPanel.add(stockDetailsButton);

				adminFrame.add(adminPanel);
				adminFrame.setVisible(true);
			}
		});
		return adminButton;
	}

	/**
	 * This method returns a JButton that adds a product to the shopping cart.
	 * @return = JButton that adds a product to the shopping cart.
	 */
	private JButton getAddToCartButton() {
		JButton addToCartButton = new JButton("Add To Cart");
		addToCartButton.setFont(new Font("Arial", Font.BOLD, 14));
		addToCartButton.setForeground(Color.WHITE);
		addToCartButton.setBackground(new Color(0, 102, 204)); // Dark Blue Background
		addToCartButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		addToCartButton.addActionListener(e -> {
			JFrame addFrame = new JFrame("Add Product To Cart");
			addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			addFrame.setSize(400, 200);

			JPanel addPanel = new JPanel();
			addPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 5, 5, 5);

			barcodeField = new JTextField(20);
			quantityField = new JTextField(20);

			JButton confirmAddButton = new JButton("Confirm Choice");
			confirmAddButton.setFont(new Font("Arial", Font.BOLD, 12));
			confirmAddButton.setForeground(Color.WHITE);
			confirmAddButton.setBackground(new Color(0, 102, 0)); // Dark Green Background
			confirmAddButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

			confirmAddButton.addActionListener(e1 -> {
				if (barcodeField.getText().isEmpty() || quantityField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill all the spaces.");
				} else {
					try {
						String barcode = barcodeField.getText();
						int quantity = Integer.parseInt(quantityField.getText());

						Product product = Main.getProductDetails(barcode);
						if (Main.insertIntoShoppingCart(product, quantity)) {
							JOptionPane.showMessageDialog(null, "Added successfully to the shopping cart.");
						} else {
							JOptionPane.showMessageDialog(null, "Failed adding.\n Check again if the barcode exists and/or quantity is available.");
						}
					} catch (NumberFormatException | HeadlessException ex) {
						throw new RuntimeException(ex);
					}
				}
			});

			gbc.gridx = 0;
			gbc.gridy = 0;
			addPanel.add(new JLabel("Barcode:"), gbc);
			gbc.gridx = 1;
			addPanel.add(barcodeField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			addPanel.add(new JLabel("Quantity:"), gbc);
			gbc.gridx = 1;
			addPanel.add(quantityField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridwidth = 2;
			addPanel.add(confirmAddButton, gbc);

			addFrame.add(addPanel);
			addFrame.setVisible(true);
		});

		return addToCartButton;
	}


	/**
	 * This method returns a JButton that deletes a product from the shopping cart.
	 * @return = JButton that deletes a product from the shopping cart.
	 */
	private JButton getDeleteFromCartButton() {
		JButton deleteFromCartButton = new JButton("Delete Product");
		deleteFromCartButton.setFont(new Font("Arial", Font.BOLD, 14));
		deleteFromCartButton.setForeground(Color.WHITE);
		deleteFromCartButton.setBackground(new Color(204, 0, 0)); // Dark Red Background
		deleteFromCartButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		deleteFromCartButton.addActionListener(e -> {
			JFrame deleteFrame = new JFrame("Delete Product From Cart");
			deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			deleteFrame.setSize(400, 200);

			JPanel deletePanel = new JPanel();
			deletePanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 5, 5, 5);

			barcodeField = new JTextField(20);

			JButton confirmDeleteButton = new JButton("Confirm Delete");
			confirmDeleteButton.setFont(new Font("Arial", Font.BOLD, 12));
			confirmDeleteButton.setForeground(Color.WHITE);
			confirmDeleteButton.setBackground(new Color(204, 0, 0)); // Dark Red Background
			confirmDeleteButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

			confirmDeleteButton.addActionListener(e1 -> {
				if (barcodeField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill all the spaces.");
				} else {
					try {
						if (Main.deleteFromShoppingCart(barcodeField.getText())) {
							JOptionPane.showMessageDialog(null, "Product deleted successfully");
						} else {
							JOptionPane.showMessageDialog(null, "Failed deleting product.\n Check if the barcode is valid.");
						}
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
				deleteFrame.dispose();
			});

			gbc.gridx = 0;
			gbc.gridy = 0;
			deletePanel.add(new JLabel("Barcode:"), gbc);
			gbc.gridx = 1;
			deletePanel.add(barcodeField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = 2;
			deletePanel.add(confirmDeleteButton, gbc);

			deleteFrame.add(deletePanel);
			deleteFrame.setVisible(true);
		});

		return deleteFromCartButton;
	}


	/**
	 * This method returns a JButton that confirms the purchase of the shopping cart.
	 * @return = JButton that confirms the purchase of the shopping cart.
	 */
	private JButton getConfirmPurchase() {
		JButton confirmPurchase = new JButton("Buy Cart");
		confirmPurchase.setFont(new Font("Arial", Font.BOLD, 14));
		confirmPurchase.setForeground(Color.WHITE);
		confirmPurchase.setBackground(new Color(0, 153, 51)); // Dark Green Background
		confirmPurchase.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		confirmPurchase.addActionListener(e -> {
			if (Main.purchasingShoppingCart()) {
				JOptionPane.showMessageDialog(null, "Purchase completed.");
			} else {
				JOptionPane.showMessageDialog(null, "Failed purchasing.");
			}
		});

		return confirmPurchase;
	}

	/**
	 * This method returns a JButton that opens the user panel.
	 * @return = JButton that opens the user panel.
	 */
	private JButton getUserButton() {
		JButton userButton = new JButton("User");
		userButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame userFrame = new JFrame("User Panel");
				userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				userFrame.setSize(400, 200);

				JPanel userPanel = new JPanel();
				userPanel.setLayout(new GridLayout(0, 1));

				JButton addCart = getAddToCartButton();
				JButton deleteFromCart = getDeleteFromCartButton();
				JButton confirmPurchase = getConfirmPurchase();
				JButton cartDetails = getCartDetails();

				userPanel.add(addCart);
				userPanel.add(deleteFromCart);
				userPanel.add(confirmPurchase);
				userPanel.add(cartDetails);

				userFrame.add(userPanel);
				userFrame.setVisible(true);
			}
		});
		return userButton;
	}

	/**
	 * This method returns a JButton that displays the stock details.
	 * @return = JButton that displays the stock details.
	 */
	private JButton getStockDetails() {
		JButton stockDetailsButton = new JButton("Stock Details");
		stockDetailsButton.setFont(new Font("Arial", Font.BOLD, 14));
		stockDetailsButton.setForeground(Color.WHITE);
		stockDetailsButton.setBackground(new Color(102, 0, 102)); // Dark Purple Background
		stockDetailsButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		stockDetailsButton.addActionListener(e -> {
			// Return the hashmap of products to display
			HashMap<String, ArrayList<Product>> products = Main.getStockData();

			JFrame frame = new JFrame("Stock Details");
			JTextArea textArea = new JTextArea(20, 40);
			textArea.setEditable(false);

			JScrollPane scrollPane = new JScrollPane(textArea);
			frame.add(scrollPane);

			// Get the total amount of products in stock
			int totalProducts = 0;
			int totalQuantity = 0;
			double totalPrice = 0.0;

			StringBuilder details = new StringBuilder();
			for (Map.Entry<String, ArrayList<Product>> entry : products.entrySet()) {
				details.append(entry.getKey()).append(":").append("\n");

				// Add the size of each arraylist(value) for each category (key)
				totalProducts += entry.getValue().size();

				// Iterate through arraylist(value of hashmap) to be able to get total quantity and price of products
				for (int i = 0; i < entry.getValue().size(); i++) {
					totalQuantity += entry.getValue().get(i).getQuantity();
					totalPrice += entry.getValue().get(i).getPrice() * entry.getValue().get(i).getQuantity();
				}

				for (Product product : entry.getValue()) {
					details.append(product).append("\n");
				}

				details.append("\n");
			}
			details.append("Total number of products in stock: ").append(totalProducts);
			details.append("\nTotal quantity of products in stock: ").append(totalQuantity);
			details.append("\nTotal price of products in stock: ").append(totalPrice).append("$\n");

			textArea.setText(details.toString());

			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});

		return stockDetailsButton;
	}

	/**
	 * This method returns a JButton that displays the cart details.
	 * @return = JButton that displays the cart details.
	 */
	private JButton getCartDetails() {
		JButton cartDetailsButton = new JButton("Cart Details");
		cartDetailsButton.setFont(new Font("Arial", Font.BOLD, 14));
		cartDetailsButton.setForeground(Color.WHITE);
		cartDetailsButton.setBackground(new Color(255, 69, 0)); // Red-Orange Background
		cartDetailsButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		cartDetailsButton.addActionListener(e -> {
			ArrayList<Product> products = Main.getCartData();

			JFrame frame = new JFrame("Cart Details");
			JTextArea textArea = new JTextArea(20, 40);
			textArea.setEditable(false);

			JScrollPane scrollPane = new JScrollPane(textArea);
			frame.add(scrollPane);

			StringBuilder details = new StringBuilder();
			details.append("====> Cart Details \n\n");
			for (int i = 0; i < products.size(); i++) {
				details.append("==> Product {").append(i + 1).append("}\n");
				details.append("Barcode: ").append(products.get(i).getBarcode()).append("\n");
				details.append("Quantity: ").append(products.get(i).getQuantity()).append("\n");
				details.append("Price: ").append(products.get(i).getPrice()).append("$\n");
				details.append("\n");
			}
			// Getting number of products stored inside cart
			details.append(" <!> Total number of products in cart: ").append(products.size()).append("\n");

			// Getting total sum of prices of products from cart
			double totalPrice = 0.0;
			for (Product product : products) {
				totalPrice += product.getPrice();
			}
			details.append(" \n<!> Total price of your shopping cart: ").append(totalPrice).append("$\n");
			textArea.setText(details.toString());

			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});

		return cartDetailsButton;
	}

	/**
	 * This is the constructor of the StockManagement class.
	 */
	public StockManagement() {
		setTitle("Stock Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));

		JButton adminButton = getAdminButton();
		JButton userButton = getUserButton();

		// Style the Admin Button
		adminButton.setFont(new Font("Arial", Font.BOLD, 14));
		adminButton.setForeground(Color.WHITE);
		adminButton.setBackground(new Color(0, 102, 204)); // Dark Blue Background
		adminButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		// Style the User Button
		userButton.setFont(new Font("Arial", Font.BOLD, 14));
		userButton.setForeground(Color.WHITE);
		userButton.setBackground(new Color(0, 153, 51)); // Dark Green Background
		userButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		panel.add(adminButton);
		panel.add(userButton);

		add(panel);
		setVisible(true);
	}


	/**
	 * This is the main method of the StockManagement class.
	 * @param args = arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new StockManagement();
			}
		});
	}
}

