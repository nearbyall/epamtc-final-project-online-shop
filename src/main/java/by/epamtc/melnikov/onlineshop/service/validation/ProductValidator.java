package by.epamtc.melnikov.onlineshop.service.validation;

import org.apache.commons.lang3.StringUtils;

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

	public void validateProductCategory(ProductCategory category) throws ValidatorException {
		
		if (category == null) {
			throw new ValidatorException("service.commonError");
		}
		
		validateProductCategoryName(category.getName());
		
	}
	
	public void validateProductCategoryName(String name) throws ValidatorException {
		if (StringUtils.isBlank(name) || !name.matches(PRODUCT_CATEGORY_NAME_REGEX)) {
			throw new ValidatorException("validation.user.registration.phone");
		}
	}
	
}
