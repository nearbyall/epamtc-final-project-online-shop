package by.epamtc.melnikov.onlineshop.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Java bean class which describes the review.
 * 
 * @author nearbyall
 *
 */
public class Review implements Serializable {

	private static final long serialVersionUID = 8670231347802703228L;
	
	private int id;
	private int productId;
	private int userId;
	private String text;
	private Timestamp createdAt;
	private Timestamp updatedAt;
		
	public Review() {}

	public Review(int id, int productId, int userId, String text, Timestamp createdAt, Timestamp updayedAt) {
		this.id = id;
		this.productId = productId;
		this.userId = userId;
		this.text = text;
		this.createdAt = createdAt;
		this.updatedAt = updayedAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updayedAt) {
		this.updatedAt = updayedAt;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", productId=" + productId + ", userId=" + userId + ", text=" + text
				+ ", createdAt=" + createdAt + ", updayedAt=" + updatedAt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + id;
		result = prime * result + productId;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + userId;
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
		Review other = (Review) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (id != other.id)
			return false;
		if (productId != other.productId)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
}