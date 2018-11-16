package game;

import java.util.ArrayList;

import game.card.action.Action;
import game.card.action.Chance;
import game.card.action.CommunityChest;
import game.card.action.RollThree;

public class ActionCards {
	
	private ArrayList<Chance> chance;
	private ArrayList<CommunityChest> communityChest;
	private ArrayList<RollThree> rollThree;

	private static ActionCards actionCards;
	
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
		// should return a card randomly
		return communityChest.get(communityChest.size()-1);
	}
	public Chance getChanceCard(){
		return chance.get(chance.size()-1);
	}
	public void putTheCardBack(Action card) {

	}
	
}
