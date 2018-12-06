package game.card;

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
		CommunityChest happyBirthday = new CommunityChest("Happy Birthday");
		CommunityChest gameNight = new CommunityChest("Game Night");
		CommunityChest aMovingExperience = new CommunityChest("A Moving Experience");
		CommunityChest houseCondemned = new CommunityChest("House Condemned");
		
		CommunityChest beKindRewind = new CommunityChest("Be Kind, Rewind");
		CommunityChest payHospitalBills = new CommunityChest("Pay Hospital Bills");
		CommunityChest tornadoHits = new CommunityChest("Tornado Hits!");
		CommunityChest theInsidersEdge = new CommunityChest("The Insider's Edge");
		
		communityChest.add(incomeTaxrefundCard);
		communityChest.add(happyBirthday);
		communityChest.add(gameNight);
		communityChest.add(aMovingExperience);
		communityChest.add(houseCondemned);
		
		communityChest.add(beKindRewind);
		communityChest.add(payHospitalBills);
		communityChest.add(tornadoHits);
		communityChest.add(theInsidersEdge);
	
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
	
	public void putTheCardBack(Card card) {

	}
	
}
