package game.square.estate;

import game.Player;
import game.card.deed.Deed;
import game.square.Square;
import game.square.SquareType;

public abstract class Estate extends Square {

	private static final SquareType type = SquareType.ESTATE;

	private String name;
	private int price;
	private Deed deed;
	private Player owner;
	private EstateSquareType estateSquareType;

	public Estate(String name, int price, Deed deed, EstateSquareType type) {
		this.name = name;
		this.price = price;
		this.deed = deed;
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

	public void executeAction(Player player) {
		//will ask to pay rent or buy, same for all
	}

	public EstateSquareType getEstateSquareType() {
		return estateSquareType;
	}

	@Override
	public SquareType getType() {
		return type;
	}
}
