package inventory_management;

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

public class ProductOperation implements ProductOperationInterface {

	private Path path = Paths.get("product.csv");

	@Override
	public void createProduct(Product product) {
		String productToString = product.getName() + "," + product.getPrice() + "," + product.getCategory() + ", "
				+ product.getQuantity() + ", " +product.getDescription() + ", " + product.getPublish() + "," + LocalDateTime.now();

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
		
		 String[] cleanDate = lineArray[6].split("\\.");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime createdAt = LocalDateTime.parse(cleanDate[0], formatter);

		Product product = new Product();
		product.setName(lineArray[0]);
		product.setPrice(Double.valueOf(lineArray[1]));
		product.setCategory(lineArray[2]);
		product.setDescription(lineArray[4]);
		product.setPublish(Boolean.valueOf(lineArray[5]));
		product.setQuantity(Integer.valueOf(lineArray[3].trim()));
		product.setCreatedAt(createdAt);

		return product;

	}

}
