package Inventory_Management_System;
import java.util.List;

public interface InventoryManagerInterface {
	
	void createProduct(Product product);
	
	List<Product> getAllProducts();
	
	void generateLowStockNotification(int limit);
	
	void updateStock(String id, int order);
	
	void placeOrder(String id, int order);

}
