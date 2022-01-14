package by.epamtc.melnikov.onlineshop.bean.type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum UserType {
	
	USER(1, "user"),
	ADMIN(2, "admin"),
	GUEST(3, "guest");
	
	private static final Logger logger = LogManager.getLogger(UserType.class);
	
	private final int id;
	private final String name;
	
	private UserType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static UserType getTypeById(int id) {
		
		for (UserType userType : UserType.values()) {
			if (userType.id == id) {
				return userType;
			}
		}
		
        logger.warn(String.format("Role with id: %d is not found", id));
        throw new EnumConstantNotPresentException(UserType.class, String.format("Role with id: %d is not found", id));
		
	}
	
}
