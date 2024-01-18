package Inventory_Management_System;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InventoryManager implements InventoryManagerInterface {

	private Path path = Paths.get("product.csv");

	@Override
	public void createProduct(Product product) {
		int id = 1000;
		
		try {
			List<String> lines = Files.readAllLines(path);
			String lastId = "0";
			if(lines.size() != 1) {
				lastId = lines.get(lines.size() - 1).split(",")[0].trim();
			}
			id = Integer.parseInt(lastId) + 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String productToString = id + ", " + product.getName() + "," + product.getPrice() + "," + product.getCategory() + ", "
				+ product.getQuantity() + ", " + product.getDescription() + ", " + product.getPublish() + ","
				+ LocalDateTime.now();

		List<String> productLine = List.of(productToString);

		try {
			// Write the lines to the file
			Files.write(path, productLine, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<Product> getAllProducts() {
		try {
			List<String> lines = Files.readAllLines(path);

			int i = 0;
			for (String line : lines) {
				if (i != 0) {
					Product product = this.mapLineToProduct(line);
					System.out.println(product);
				}

				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Product mapLineToProduct(String line) {

		String[] lineArray = line.split(",");

		
		//Convert date to right format
		String[] cleanDate = lineArray[7].trim().split("\\.");
		LocalDateTime createdAt = LocalDateTime.now();
		
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			 createdAt = LocalDateTime.parse(cleanDate[0], formatter);
		}catch(Exception e) {
			e.printStackTrace();
		}
		

		Product product = new Product();
		product.setId(Integer.parseInt(lineArray[0]));
		product.setName(lineArray[1]);
		product.setPrice(Double.valueOf(lineArray[2]));
		product.setCategory(lineArray[3]);
		product.setQuantity(Integer.valueOf(lineArray[4].trim()));
		product.setDescription(lineArray[5]);
		product.setPublish(lineArray[6]);

		product.setCreatedAt(createdAt);

		return product;

	}

	public void generateLowStockNotification(int limit) {
		try {
			List<String> lines = Files.readAllLines(path);

			int i = 0;
			for (String line : lines) {
				if (i != 0) {
					Product product = this.mapLineToProduct(line);
					if (product.getQuantity() < limit) {
						System.out.println("Low stock notification: " + product.getName() + " (Category: "
								+ product.getCategory() + ", " + "Stock: " + product.getQuantity() + ")");
					}

				}

				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateStock(String id, int quantity) {

		try {
			List<String> lines = Files.readAllLines(path);
			int i = 0;
			int lineIndexToModify = 0;
			String modifiedLine = "";
			for (String line : lines) {
				String[] lineArray = line.split(",");
				if (lineArray[0].trim().equals(id)) {
					int stock = Integer.parseInt(lineArray[4].trim()) + quantity;
					lineIndexToModify = i;
					modifiedLine = lineArray[0] + ", " + lineArray[1] + ", " + lineArray[2] + ", " + lineArray[3] + ", "
							+ stock + ", " + lineArray[5] + ", " + lineArray[6] + ", " + lineArray[7];
				}
				i++;
			}

			if (lineIndexToModify != 0) {
				lines.set(lineIndexToModify, modifiedLine);
			}

			Files.write(path, lines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void placeOrder(String id, int order) {
		try {
			List<String> lines = Files.readAllLines(path);
			int i = 0;
			int lineIndexToModify = 0;
			String modifiedLine = "";
			for (String line : lines) {
				String[] lineArray = line.split(",");
				if (lineArray[0].trim().equals(id)) {
					if (order > Integer.parseInt(lineArray[4].trim())) {
						System.out.println("You can not make this order as " + "we have low stock for this product");
						break;
					}
					int stock = Integer.parseInt(lineArray[4].trim()) - order;
					lineIndexToModify = i;
					modifiedLine = lineArray[0] + ", " + lineArray[1] + ", " + lineArray[2] + ", " + lineArray[3] + ", "
							+ stock + ", " + lineArray[5] + ", " + lineArray[6] + ", " + lineArray[7];
				}
				i++;
			}

			if (lineIndexToModify != 0) {
				lines.set(lineIndexToModify, modifiedLine);
			}

			Files.write(path, lines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
