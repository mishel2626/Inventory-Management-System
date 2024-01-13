package inventory_management;

import java.util.Scanner;

public class UserInterface {
	public static void main(String[] args) {
			
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Type operation");
		String type = sc.next();
		
		ProductOperation productOperation = new ProductOperation();
		
		switch(type) {
			case "read":
			productOperation.getAllProducts();
			break;
			case "create":
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
				Boolean publish = sc.nextBoolean();
				

				Product product = new Product();
				product.setName(name);
				product.setCategory(category);
				product.setDescription(description);
				product.setQuantity(quantity);
				product.setPublish(publish);
				product.setPrice(price);
				
				
				try {
					productOperation.createProduct(product);
					System.out.println("Product was inserted successfully");
				}catch (Exception e) {
					System.out.println("Product was not inserted successfully because " + e.getLocalizedMessage());
				}
				
				break;
				
		}
		
	}

}
