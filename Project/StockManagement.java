package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Project.Main;

/**
 * This class is used to manage the stock of the store.
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
		addProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame addFrame = new JFrame("Add Product");
				addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				addFrame.setSize(400, 200);

				JPanel addPanel = new JPanel();
				addPanel.setLayout(new GridLayout(0, 2));

				categoryField = new JTextField(20);
				barcodeField = new JTextField(20);
				productNameField = new JTextField(20);
				quantityField = new JTextField(20);
				priceField = new JTextField(20);

				JButton confirmAddButton = new JButton("Confirm Add");
				confirmAddButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (categoryField.getText().isEmpty() || barcodeField.getText().isEmpty() || productNameField.getText().isEmpty() || quantityField.getText().isEmpty() || priceField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please fill all the spaces with data.");
						} else {
							try {
								String category = categoryField.getText();
								String barcode = barcodeField.getText();
								String name = productNameField.getText();

								if (Main.insertIntoStockTable(category, new Product(barcode, name, Integer.parseInt(quantityField.getText()), Double.parseDouble(priceField.getText())))) {
									JOptionPane.showMessageDialog(null, "Product added successfully");
								} else {
									JOptionPane.showMessageDialog(null, "Failed to add product. It may already exist in the stock.");
								}
							} catch (NumberFormatException | HeadlessException ex) {
								throw new RuntimeException(ex);
							}
						}
					}
				});

				addPanel.add(new JLabel("Category: "));
				addPanel.add(categoryField);
				addPanel.add(new JLabel("Barcode: "));
				addPanel.add(barcodeField);
				addPanel.add(new JLabel("Product Name: "));
				addPanel.add(productNameField);
				addPanel.add(new JLabel("Quantity: "));
				addPanel.add(quantityField);
				addPanel.add(new JLabel("Price: "));
				addPanel.add(priceField);
				addPanel.add(confirmAddButton);

				addFrame.add(addPanel);
				addFrame.setVisible(true);
			}
		});
		return addProductButton;
	}

	/**
	 * This method returns a JButton that modifies a product in the stock.
	 * @return = JButton that modifies a product in the stock.
	 */
	private JButton getModifyButton() {
		JButton modifyProductButton = new JButton("Modify Product");
		modifyProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame modifyFrame = new JFrame("Modify Product");
				modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				modifyFrame.setSize(400, 200);

				JPanel modifyPanel = new JPanel();
				modifyPanel.setLayout(new GridLayout(0, 2));

				categoryField = new JTextField(20);
				barcodeField = new JTextField(20);
				newQuantityField = new JTextField(20);
				newPriceField = new JTextField(20);

				JButton confirmModifyButton = new JButton("Confirm Modify");
				confirmModifyButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (categoryField.getText().isEmpty() || barcodeField.getText().isEmpty() || newQuantityField.getText().isEmpty() || newPriceField.getText().isEmpty()) {
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
					}
				});

				modifyPanel.add(new JLabel("Category: "));
				modifyPanel.add(categoryField);
				modifyPanel.add(new JLabel("Barcode to Modify: "));
				modifyPanel.add(barcodeField);
				modifyPanel.add(new JLabel("New Quantity: "));
				modifyPanel.add(newQuantityField);
				modifyPanel.add(new JLabel("New Price: "));
				modifyPanel.add(newPriceField);
				modifyPanel.add(confirmModifyButton);

				modifyFrame.add(modifyPanel);
				modifyFrame.setVisible(true);
			}
		});
		return modifyProductButton;
	}

	/**
	 * This method returns a JButton that deletes a product from the stock.
	 * @return = JButton that deletes a product from the stock.
	 */
	private JButton getDeleteButton() {
		JButton deleteProductButton = new JButton("Delete Product");
		deleteProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame modifyFrame = new JFrame("Delete Product");
				modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				modifyFrame.setSize(400, 200);

				JPanel modifyPanel = new JPanel();
				modifyPanel.setLayout(new GridLayout(0, 2));

				categoryField = new JTextField(20);
				barcodeField = new JTextField(20);

				JButton confirmDeleteButton = new JButton("Confirm Delete");
				confirmDeleteButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
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
									JOptionPane.showMessageDialog(null, "Product doesn't exists.");
								}
							} catch (Exception ex) {
								throw new RuntimeException(ex);
							}
						}
						modifyFrame.dispose();
					}
				});
				modifyPanel.add(new JLabel("Category: "));
				modifyPanel.add(categoryField);
				modifyPanel.add(new JLabel("Barcode to Delete: "));
				modifyPanel.add(barcodeField);
				modifyPanel.add(confirmDeleteButton);

				modifyFrame.add(modifyPanel);
				modifyFrame.setVisible(true);
			}
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
		addToCartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame addFrame = new JFrame("Add Product To Cart");
				addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				addFrame.setSize(400, 200);

				JPanel addPanel = new JPanel();
				addPanel.setLayout(new GridLayout(0, 2));

				barcodeField = new JTextField(20);
				quantityField = new JTextField(20);

				JButton confirmAddButton = new JButton("Confirm Choice");

				confirmAddButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (barcodeField.getText().isEmpty() || quantityField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please fill all the spaces.");
						} else {
							try {
								String barcode = barcodeField.getText();
								int quantity = Integer.parseInt(quantityField.getText());

								Product product = Main.getProductDetails(barcode);
								if (Main.insertIntoShoppingCart(product, quantity)) {
									JOptionPane.showMessageDialog(null, "Added successfully to shopping cart.");
								} else {
									JOptionPane.showMessageDialog(null, "Failed adding.\n Check again if barcode exists and/or quantity is available.");
								}
							} catch (NumberFormatException | HeadlessException ex) {
								throw new RuntimeException(ex);
							}
						}
					}
				});
				addPanel.add(new JLabel("Barcode: "));
				addPanel.add(barcodeField);
				addPanel.add(new JLabel("Quantity: "));
				addPanel.add(quantityField);
				addPanel.add(confirmAddButton);

				addFrame.add(addPanel);
				addFrame.setVisible(true);
			}
		});
		return addToCartButton;
	}

	/**
	 * This method returns a JButton that deletes a product from the shopping cart.
	 * @return = JButton that deletes a product from the shopping cart.
	 */
	private JButton getDeleteFromCartButton() {
		JButton deleteFromCart = new JButton("Delete Product.");
		deleteFromCart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame addFrame = new JFrame("Delete Product From Cart");
				addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				addFrame.setSize(400, 200);

				JPanel addPanel = new JPanel();
				addPanel.setLayout(new GridLayout(0, 2));

				barcodeField = new JTextField(20);

				JButton confirmAddButton = new JButton("Confirm Delete.");
				confirmAddButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (barcodeField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please fill all the spaces.");
						} else {
							try {
								if (Main.deleteFromShoppingCart(barcodeField.getText())) {
									JOptionPane.showMessageDialog(null, "Product deleted successfully,");
								} else {
									JOptionPane.showMessageDialog(null, "Failed deleting product.\n Check if barcode is valid.");
								}
							} catch (Exception ex) {
								throw new RuntimeException(ex);
							}
						}
					}
				});
				addPanel.add(new JLabel("Barcode: "));
				addPanel.add(barcodeField);
				addPanel.add(confirmAddButton);

				addFrame.add(addPanel);
				addFrame.setVisible(true);
			}
		});
		return deleteFromCart;
	}

	/**
	 * This method returns a JButton that confirms the purchase of the shopping cart.
	 * @return = JButton that confirms the purchase of the shopping cart.
	 */
	private JButton getConfirmPurchase() {
		JButton confirmPurchase = new JButton("Buy cart.");
		confirmPurchase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Main.purchasingShoppingCart()) {
					JOptionPane.showMessageDialog(null, "Purchase completed.");
				} else {
					JOptionPane.showMessageDialog(null, "Failed purchasing.");
				}
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
		stockDetailsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
					System.out.println(totalProducts);

					// Iterate through arraylist(value of hashmap) to be able to get total quantity and price of products
					for (int i = 0; i < entry.getValue().size(); i++) {
						totalQuantity += entry.getValue().get(i).getQuantity();
						totalPrice += entry.getValue().get(i).getPrice() * entry.getValue().get(i).getQuantity();
					}

					for (Product product : entry.getValue()) {
						details.append(product).append("\n");
					}
					System.out.println(totalProducts);
					details.append("\n");
				}
				details.append("Total number of products in stock: ").append(totalProducts);
				details.append("\nTotal quantity of products in stock: ").append(totalQuantity);
				details.append("\nTotal price of products in stock: ").append(totalPrice).append("$\n");

				textArea.setText(details.toString());

				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		return stockDetailsButton;
	}

	/**
	 * This method returns a JButton that displays the cart details.
	 * @return = JButton that displays the cart details.
	 */
	private JButton getCartDetails() {
		JButton cartDetailsButton = new JButton("Cart Details");

		cartDetailsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Product> products = Main.getCartData();

				JFrame frame = new JFrame("Cart Details");
				JTextArea textArea = new JTextArea(20, 40);
				textArea.setEditable(false);

				JScrollPane scrollPane = new JScrollPane(textArea);
				frame.add(scrollPane);

				StringBuilder details = new StringBuilder();
				details.append("====> Cart Details \n\n");
				for (int i = 0; i < products.size(); i++) {
					details.append("==> Product {").append(i+1).append("}\n");
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
			}
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
		panel.setLayout(new GridLayout(0, 3));

		JButton adminButton = getAdminButton();
		JButton userButton = getUserButton();
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

