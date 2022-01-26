package by.epamtc.melnikov.onlineshop.bean;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Java bean class which describes the product category
 * 
 * @author nearbyall
 *
 */
public class ProductCategory implements Serializable, Comparable<Object> {

	private static final long serialVersionUID = -8130539624873091423L;
	
	private static final Logger logger = LogManager.getLogger();
	
	int id;
	private String name;
	private String imgPath;
	
	public ProductCategory() {}
	
	public ProductCategory(int id) {
		this.id = id;
	}

	public ProductCategory(String name, String imgPath) {
		this.name = name;
		this.imgPath = imgPath;
	}

	public ProductCategory(int id, String name, String imgPath) {
		this.id = id;
		this.name = name;
		this.imgPath = imgPath;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", name=" + name + ", imgPath=" + imgPath + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((imgPath == null) ? 0 : imgPath.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductCategory other = (ProductCategory) obj;
		if (id != other.id)
			return false;
		if (imgPath == null) {
			if (other.imgPath != null)
				return false;
		} else if (!imgPath.equals(other.imgPath))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
        if (o instanceof ProductCategory) {
            return name.compareTo(((ProductCategory) o).name);
        }
        logger.warn(String.format("Try to compareTo incomparable types %s and %s", o.getClass(), ProductCategory.class));
        return 0;
	}
	
}
