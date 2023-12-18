package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Project.Main;

public class StockManagement extends JFrame {
	private JButton addProductButton;
	private final JTextField categoryField;
	private final JTextField barcodeField;
	private final JTextField productNameField;
	private final JTextField quantityField;
	private final JTextField priceField;

	public StockManagement() {
		setTitle("Stock Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2)); // Using GridLayout for organized alignment

		categoryField = new JTextField(20);
		barcodeField = new JTextField(20);
		productNameField = new JTextField(20);
		quantityField = new JTextField(20);
		priceField = new JTextField(20);

		addProductButton = new JButton("Add Product");
		addProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String url = "jdbc:sqlite:/home/catalin/workspace/git/StockManagement/identifier.sqlite";

				if (categoryField.getText().isEmpty() || barcodeField.getText().isEmpty() || productNameField.getText().isEmpty() || quantityField.getText().isEmpty() || priceField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill all the spaces with data.");

				} else {
					try {
						Connection connection = DriverManager.getConnection(url);
						System.out.println("Connected successfully to database.");

						// Get inserted data from interface
						String category = categoryField.getText();
						String barcode = barcodeField.getText();
						String name = productNameField.getText();

						// Main function for inserting into stock db
						if (Main.insertIntoStockTable(category, new Product(barcode, name, Integer.parseInt(quantityField.getText()), Double.parseDouble(priceField.getText())))) {
							JOptionPane.showMessageDialog(null, "Product added successfully");
						} else {
							JOptionPane.showMessageDialog(null, "Failed to add product. It may already exist in the stock.");
						}
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			}
		});

		panel.add(new JLabel("Category: "));
		panel.add(categoryField);
		panel.add(new JLabel("Barcode: "));
		panel.add(barcodeField);
		panel.add(new JLabel("Product Name: "));
		panel.add(productNameField);
		panel.add(new JLabel("Quantity: "));
		panel.add(quantityField);
		panel.add(new JLabel("Price: "));
		panel.add(priceField);

		panel.add(addProductButton);

		add(panel);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new StockManagement();
			}
		});
	}
}

