package by.epamtc.melnikov.onlineshop.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.type.OrderType;

/**
 * Java bean class which describes the order.
 * 
 * @author nearbyall
 *
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 7945020562538111893L;
	
	private int id;
	private double totalPrice;
	private OrderType status;
	private User user;
	private List<OrderItem> orderItems;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public Order() {}

	public Order(int id, double totalPrice, OrderType status, User user, List<OrderItem> orderItems, Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
		this.totalPrice = totalPrice;
		this.status = status;
		this.user = user;
		this.orderItems = orderItems;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public OrderType getStatus() {
		return status;
	}

	public void setStatus(OrderType status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
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

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isConfirmed() {
		if (status == OrderType.ACCEPTED) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", totalPrice=" + totalPrice + ", status=" + status + ", user=" + user
				+ ", orderItems=" + orderItems + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + id;
		result = prime * result + ((orderItems == null) ? 0 : orderItems.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		long temp;
		temp = Double.doubleToLongBits(totalPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Order other = (Order) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (id != other.id)
			return false;
		if (orderItems == null) {
			if (other.orderItems != null)
				return false;
		} else if (!orderItems.equals(other.orderItems))
			return false;
		if (status != other.status)
			return false;
		if (Double.doubleToLongBits(totalPrice) != Double.doubleToLongBits(other.totalPrice))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
