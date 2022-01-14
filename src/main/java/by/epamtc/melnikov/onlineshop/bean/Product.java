package by.epamtc.melnikov.onlineshop.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Product implements Serializable {

	private static final long serialVersionUID = 146249767363491758L;
	
	private int id;
	private int count;
	private double price;
	private String title;
	private String description;
	private Timestamp createdAt;
	private Timestamp updatedt;
	private ProductCategory category;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public Timestamp getUpdatedt() {
		return updatedt;
	}
	
	public void setUpdatedt(Timestamp updatedt) {
		this.updatedt = updatedt;
	}
	
	public ProductCategory getCategory() {
		return category;
	}
	
	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() 
				+ " [id=" + id + ", count=" + count + ", price=" + price + ", title=" + title + ", description="
				+ description + ", createdAt=" + createdAt + ", updatedt=" + updatedt + ", category=" + category + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + count;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((updatedt == null) ? 0 : updatedt.hashCode());
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
		Product other = (Product) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (count != other.count)
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (updatedt == null) {
			if (other.updatedt != null)
				return false;
		} else if (!updatedt.equals(other.updatedt))
			return false;
		return true;
	}
	
}
