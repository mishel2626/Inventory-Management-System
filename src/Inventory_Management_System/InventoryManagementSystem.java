package Inventory_Management_System;

import java.util.Scanner;

public class InventoryManagementSystem {
	public static void main(String[] args) {

		int choice;
		InventoryManager inventoryManager = new InventoryManager();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to our Inventory Management App ");

		do {
			System.out.println("\n1. Add Product");
			System.out.println("2. Update Stock");
			System.out.println("3. Generate Low Stock Notification");
			System.out.println("4. Display Products");
			System.out.println("5. Place Order");
			System.out.println("Enter your choice: ");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Please insert product name");
				String name = sc.next();

				System.out.println("Please insert product price");
				Double price = sc.nextDouble();

				System.out.println("Please insert product category");
				String category = sc.next();

				System.out.println("Please insert product quantity");
				Integer quantity = sc.nextInt();

				System.out.println("Please insert product description");
				String description = sc.next();

				System.out.println("Please confirm if product is available");
				String publish = sc.next();

				Product product = new Product();
				product.setName(name);
				product.setCategory(category);
				product.setDescription(description);
				product.setQuantity(quantity);
				product.setPublish(publish);
				product.setPrice(price);
				inventoryManager.createProduct(product);
				break;
			case 2:
				System.out.println("Enter product Id to update: ");
				String productId = sc.next();
				System.out.println("Enter quantity to add/remove: ");
				int stock = sc.nextInt();
				inventoryManager.updateStock(productId, stock);
				break;
			case 3:
				System.out.println("Enter low stock threshold: ");
				int threshold = sc.nextInt();
				inventoryManager.generateLowStockNotification(threshold);
				break;
			case 4:
				System.out.println("Generate Raport for all products: ");
				inventoryManager.getAllProducts();
				break;
			case 5:
				System.out.println("Enter product Id to update: ");
				String id = sc.next();
				System.out.println("Enter quantity for order ");
				int order = sc.nextInt();
				inventoryManager.placeOrder(id, order);
				break;

			default:
				System.out.println("Invalid choice. Please enter a number between 1 and 5.");
			}

		} while (choice != 0);

		
	}

}
