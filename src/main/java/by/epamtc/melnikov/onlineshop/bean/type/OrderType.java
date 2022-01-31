package by.epamtc.melnikov.onlineshop.bean.type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum OrderType {

	ACCEPTED(1, "ACCEPTED"),
	NOTACCEPTED(2, "NOTACCEPTED");
	
	private static final Logger logger = LogManager.getLogger();
	
	private final int id;
	private final String type;
	
	private OrderType(int id, String type) {
		this.id = id;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public static OrderType getTypeById(int id) {
		
		for (OrderType orderType : OrderType.values()) {
			if (orderType.id == id) {
				return orderType;
			}
		}
		
		logger.warn(String.format("Type with id: %d is not found", id));
		throw new EnumConstantNotPresentException(StatusType.class, String.format("Type with id: %d is not found", id));
		
	}
	
}
