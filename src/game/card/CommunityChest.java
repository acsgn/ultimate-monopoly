package game.card;

import game.Player;

public class CommunityChest extends Card {
	
	private static final CardType type = CardType.COMMUNITY_CHEST;
	private String name;
	
	public CommunityChest(String name){
		this.name = name;
	}
	@Override
	public void executeAction(Player player) {
		if(name.equals("Income Tax Refund")){
			player.increaseMoney(20);
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
