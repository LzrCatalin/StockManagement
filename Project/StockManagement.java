package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

				adminPanel.add(addProductButton);
				adminPanel.add(modifyProductButton);
				adminPanel.add(deleteProductButton);

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
		// TODO
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

				userPanel.add(addCart);

				userFrame.add(userPanel);
				userFrame.setVisible(true);
			}
		});
		return userButton;
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

