package by.epamtc.melnikov.onlineshop.bean.type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum StatusType {

	VALID(1, "valid"),
	BANNED(2, "banned");
	
	private static final Logger logger = LogManager.getLogger(StatusType.class);
	
	private final int id;
	private final String status;
	
	private StatusType(int id, String status) {
		this.id = id;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public static StatusType getTypeById(int id) {
		
		for (StatusType statusType : StatusType.values()) {
			if (statusType.id == id) {
				return statusType;
			}
		}
		
        logger.warn(String.format("Role with id: %d is not found", id));
        throw new EnumConstantNotPresentException(StatusType.class, String.format("Status with id: %d is not found", id));
		
	}
	
}
