package game.card.action;

import game.Player;

public class CommunityChest extends Action {
	
	private static final ActionType type = ActionType.COMMUNITY_CHEST;
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
	public ActionType getActionType() {
		return type;
	}
	
	public String getName(){
		return name;
	}

}
