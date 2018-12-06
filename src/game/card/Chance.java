package game.card;

import game.Player;

public class Chance extends Card {

	private static final CardType type = CardType.CHANCE;
	private String name;
	private Boolean playImmediately;

	
	public Chance(String name, Boolean playImmediately){
		this.name = name;
		this.playImmediately = playImmediately;
	}
	@Override
	public void executeAction(Player player) {
		if(playImmediately.equals(false)) {
			player.addCard(this);
		}
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
