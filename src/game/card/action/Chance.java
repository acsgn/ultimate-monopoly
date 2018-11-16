package game.card.action;

import game.Player;

public class Chance extends ActionCard {

	private static final ActionCardType type = ActionCardType.CHANCE;
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
	public ActionCardType getActionCardType() {
		return type;
	}
	
	public String getName(){
		return name;
	}

}
