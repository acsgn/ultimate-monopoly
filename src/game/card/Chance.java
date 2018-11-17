package game.card;

import game.Player;

public class Chance extends Card {

	private static final CardType type = CardType.CHANCE;
	private String name;
	
	public Chance(String name){
		this.name = name;
	}
	@Override
	public void executeAction(Player player) {
		if(name.equals("PARTY TIME")){
			player.reduceMoney(25);
		}
	}

	@Override
	public CardType getCardType() {
		return type;
	}
	
	public String getName(){
		return name;
	}

}
