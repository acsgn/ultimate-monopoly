package game.card.action;

import java.util.ArrayList;

public class ActionCards {
	
	private static ActionCards actionCards;
	
	private ArrayList<Chance> chance;
	private ArrayList<CommunityChest> communityChest;
	private ArrayList<RollThree> rollThree;
	
	private ActionCards(){
		constructCommunityChestCards();
		constructChanceCards();
	}
	public static ActionCards getInstance(){
		if(actionCards==null){
			actionCards = new ActionCards();
		}
		return actionCards;
	}
	
	private void constructCommunityChestCards(){
		communityChest = new ArrayList<>();
		CommunityChest incomeTaxrefundCard = new CommunityChest("Income Tax Refund");
		communityChest.add(incomeTaxrefundCard);
	}
	private void constructChanceCards(){
		chance = new ArrayList<>();
		Chance partyTime = new Chance("PARTY TIME");
		chance.add(partyTime);
	}
	public CommunityChest getCommunityChestCard(){
		CommunityChest tmp = communityChest.remove(0);
		communityChest.add(tmp);
		return tmp;
	}
	public Chance getChanceCard(){
		Chance tmp = chance.remove(0);
		chance.add(tmp);
		return tmp;
	}
	
	public void putTheCardBack(ActionCard card) {

	}
	
}
