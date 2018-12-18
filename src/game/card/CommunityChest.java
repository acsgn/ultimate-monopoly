package game.card;

import game.Player;
import game.Pool;
import game.TrackType;

import java.util.*;

import game.building.*;
import game.dice.SingletonDice;
import game.square.estate.*;

public class CommunityChest extends Card {

	private static final CardType type = CardType.COMMUNITY_CHEST;
	private String name;

	public CommunityChest(String name){
		this.name = name;
	}
	@Override
	public void executeAction(Player player) {
		/* removed
		// ref: card "Income Tax Refund"
		if(name.equalsIgnoreCase("Income Tax Refund")){
			player.increaseMoney(20);
		} 
		// ref: card "You Inherit 100$"
		else if (name.equalsIgnoreCase("You Inherit 100")) {
			player.increaseMoney(100);
		} 
		// ref: card "Insurance Premiums Due"
		else if (name.equalsIgnoreCase("Insurance Premiums Due")) {
			Pool.getInstance().payToPool(50);
			player.reduceMoney(50);
		} 
		// ref: card "Doctor's Fee"
		else if (name.equalsIgnoreCase("Doctor's Fee")) {
			Pool.getInstance().payToPool(50);
			player.reduceMoney(50);
		} */
		
		// ref: card "House Condemned"
		if (name.equalsIgnoreCase("House Condemned")) {

			List<Property> properties = player.getProperties();

			for (Property property : properties) {

				for (Building building : property.getBuildings()) {
					if (building.getBuildingType() == BuildingType.HOUSE) {

						ArrayList<Building> updateBuildings = property.getBuildings();
						updateBuildings.remove(building);
						property.setBuildings(updateBuildings);

						break;
					}
				}
			}

		}
		
		// ref: card "Pay Hospital Bills"
		else if (name.equalsIgnoreCase("Pay Hospital Bills")) {
			Pool.getInstance().payToPool(100);
			player.reduceMoney(100);
		}
		
		// ref: card "Be Kind, Rewind"
		else if (name.equalsIgnoreCase("Be Kind, Rewind")) {
			
			int[] dice = SingletonDice.getInstance().getFaceValues();
			dice[0] = -dice[0];
			dice[1] = -dice[1];
			
			player.move(dice);
			
			// Repeated payRent if the square is owned, because double the 
			// rent should be paid.
			if (player.getLocation().isOwned()) {
				player.payRent(player.getLocation());
			}
			
		}
		
		// ref: card "The Insider's Edge
		else if (name.equalsIgnoreCase("The Insider's Edge")) {
			
			// This card does nothing if player is in the middle track.
			if (player.getCurrentTrack().equals(TrackType.INNER_TRACK)) {
				player.increaseMoney(250);
			} else if (player.getCurrentTrack().equals(TrackType.OUTER_TRACK)) {
				Pool.getInstance().payToPool(50);
				player.reduceMoney(50);
			}
			
		}
		
		// ref: card "Tornado Hits!"
		else if (name.equalsIgnoreCase("Tornado Hits!")) {
			
			// TODO Send message to the player to choose a color among the list
			//		of properties they own.
			// TODO Implement colors to each property to implement this card.
			
		}
		
		// ref: card "A Moving Experience"
		else if (name.equalsIgnoreCase("A Moving Experience")) {

			// TODO To implement this, sending message to the player to choose a travel
			//		square to move into is necessary.
		}
		
		// ref: card "Happy Birthday!"
		else if (name.equalsIgnoreCase("Happy Birthday!")) {
			
			// TODO Get player count, add (playerCount-1)*50 to the current player's
			//		account, and reduce 50 from each of the accounts of other players.
			
		}
		
		// ref: card "Game Night!"
		else if (name.equalsIgnoreCase("Game Night!")) {
			
			// TODO Send players a message to choose someone from rest of the players,
			//		have them both roll dice, give 200 to the player of the higher roll
			//		from the bank.
			
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
