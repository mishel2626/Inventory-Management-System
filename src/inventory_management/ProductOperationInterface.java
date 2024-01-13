package inventory_management;

import java.util.List;

public interface ProductOperationInterface {
	
	void createProduct(Product product);
	
	List<Product> getAllProducts();

}
