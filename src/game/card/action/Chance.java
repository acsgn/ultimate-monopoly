package game.card.action;

import game.Player;

public class Chance extends Action {

	private static final ActionType type = ActionType.CHANCE;
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
	public ActionType getActionType() {
		return type;
	}
	
	public String getName(){
		return name;
	}

}
