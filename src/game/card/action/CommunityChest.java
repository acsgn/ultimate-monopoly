package game.card.action;

import game.Player;

public class CommunityChest extends ActionCard {
	
	private static final ActionCardType type = ActionCardType.COMMUNITY_CHEST;
	private String name;
	
	public CommunityChest(String name){
		this.name = name;
	}
	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		if(name.equals("Income Tax Refund")){
			player.increaseMoney(20);
		}
	}

	@Override
	public ActionCardType getActionCardType() {
		return type;
	}
	
	public String getName(){
		return name;
	}

}
