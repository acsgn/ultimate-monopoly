package game.square.estate;

import game.Controller;
import game.Player;
import game.square.Square;
import game.square.SquareType;

public abstract class Estate extends Square {
	private static final long serialVersionUID = 1L;

	private static final SquareType type = SquareType.ESTATE;

	private String name;
	private int price;
	private Player owner;
	private EstateSquareType estateSquareType;

	public Estate(String name, int price, EstateSquareType type) {
		this.name = name;
		this.price = price;
		this.estateSquareType = type;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public abstract int getRent();

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	@Override
	public void executeWhenLanded(Player player) {
		if (owner != null) {
			if (!owner.getName().equals(player.getName()))
				player.delegateTask("PAYRENT/" + player.getName() + "/" + owner.getName());
		} else {
			Controller.getInstance().publishGameEvent("ESTATE/" + name);
		}
		Controller.getInstance().publishGameEvent(information());
	}

	@Override
	public void executeWhenPassed(Player player) {
	}

	public EstateSquareType getEstateSquareType() {
		return estateSquareType;
	}

	@Override
	public SquareType getType() {
		return type;
	}

	@Override
	public boolean isOwned() {
		return (owner != null);
	}

	public abstract String information();

}
