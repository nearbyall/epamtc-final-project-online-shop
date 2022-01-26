package by.epamtc.melnikov.onlineshop.service.validation;

import org.apache.commons.lang3.StringUtils;

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.bean.ProductCategory;
import by.epamtc.melnikov.onlineshop.service.validation.exception.ValidatorException;

/**
 * The class needed to validate data associated with
 * the {@link Product} using regular expressions.
 * 
 * @author nearbyall
 *
 */
public class ProductValidator {

	private static final String PRODUCT_CATEGORY_NAME_REGEX = "^[^!@#$%^&*()_|+~\\d]{3,40}$";
	private static final String PRODUCT_TITLE_REGEX = "^[^!@#$%^&*()_|+~\\d]{3,40}$";
	private static final String PRODUCT_DESCRIPTION_REGEX = "^(.|\\s)*[a-zA-Z]+(.|\\s)*$";

	public void validateProductCategory(ProductCategory category) throws ValidatorException {
		
		if (category == null) {
			throw new ValidatorException("service.commonError");
		}
		
		validateProductCategoryName(category.getName());
		
	}
	
	public void validateProduct(Product product) throws ValidatorException {
		
		if (product == null) {
			throw new ValidatorException("service.commonError");
		}
		
		validateProductTitle(product.getTitle());
		validateProductDescription(product.getDescription());
		
	}
	
	public void validateProductTitle(String title) throws ValidatorException {
		if (StringUtils.isBlank(title) || !title.matches(PRODUCT_TITLE_REGEX)) {
			throw new ValidatorException("validation.product.title");
		}	
	}
	
	public void validateProductDescription(String description) throws ValidatorException {
		if (StringUtils.isBlank(description) || !description.matches(PRODUCT_DESCRIPTION_REGEX)) {
			throw new ValidatorException("validation.product.description");
		}
	}
	
	public void validateProductCategoryName(String name) throws ValidatorException {
		if (StringUtils.isBlank(name) || !name.matches(PRODUCT_CATEGORY_NAME_REGEX)) {
			throw new ValidatorException("validation.product.category.name");
		}
	}
	
}