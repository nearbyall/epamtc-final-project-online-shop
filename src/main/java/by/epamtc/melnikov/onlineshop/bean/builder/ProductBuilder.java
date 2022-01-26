package by.epamtc.melnikov.onlineshop.bean.builder;

import java.sql.Timestamp;

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.bean.ProductCategory;

/**
 * Realization of the pattern Builder. 
 * Builds a {@link Product} from the received parameters.
 * 
 * @author nearbyall
 *
 */
public class ProductBuilder {

	private Product product;
	
	public ProductBuilder() {
		product = new Product();
	}
	
	public ProductBuilder withId(int id) {
		product.setId(id);
		return this;
	}
	
	public ProductBuilder withCount(int count) {
		product.setCount(count);
		return this;
	}
	
	public ProductBuilder withPrice(double price) {
		product.setPrice(price);
		return this;
	}
	
	public ProductBuilder withTitle(String title) {
		product.setTitle(title);
		return this;
	}
	
	public ProductBuilder withDescription(String description) {
		product.setDescription(description);
		return this;
	}
	
	public ProductBuilder withImgPath(String imgPath) {
		product.setImgPath(imgPath);
		return this;
	}
	
	public ProductBuilder withCreatedAt(Timestamp createdAt) {
		product.setCreatedAt(createdAt);
		return this;
	}
	
	public ProductBuilder withUpdatedAt(Timestamp updatedAt) {
		product.setUpdatedt(updatedAt);
		return this;
	}
	
	public ProductBuilder withCategory(ProductCategory category) {
		product.setCategory(category);
		return this;
	}
	
	public Product build() {
		return product;
	}
	
}
