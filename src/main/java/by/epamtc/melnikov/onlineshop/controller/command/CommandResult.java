package by.epamtc.melnikov.onlineshop.controller.command;

/**
 * The class required to store the result of the {@link Command} execution.
 * Includes page {@link CommandResult#page} and direction {@link CommandResult#direction}
 * the {@link Direction}
 * 
 * @author nearbyall
 *
 */
public class CommandResult {
	
	private String page;
	private Direction direction;
	
	public CommandResult() {}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
