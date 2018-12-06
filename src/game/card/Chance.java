package game.card;

import game.Player;
import game.TrackType;
import game.square.Square;

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
		} else {
		
		if (name.equals("Go to Jail!")) {
			player.setInJail();
			player.goTo(TrackType.MIDDLE_TRACK, 10);
		}
		
		if (name.equals("Advance to Saint Charles Place")) {
			player.setInJail();
			player.goTo(TrackType.MIDDLE_TRACK, 11);
		}
		
		
		if (name.equals("Make General Repairs to all your properties")) {
			int repairsHouse = 25 * (player.getTotalHouses() + player.getTotalTransitStations());
			int repairsHotel = 100 * (player.getTotalHotels() + player.getTotalSkyscrapers());
			int repairs = repairsHouse + repairsHotel;
			player.reduceMoney(repairs);
		}
		
		if(name.equals("Holiday Bonus!")) {
			player.increaseMoney(100);
		}
		
		if(name.equals("Buyer's Market")) {
			//Move to any Unowned Outer Track property 
			//Buy it from the Bank for 1/2 Price.
			
			//player.goTo(track, index);
			//price  = 
			//player.buySquare();
			//player.increaseMoney();
		}
		
		if(name.equals("Foreclosed Property Sale!")) {
			//Foreclose on any opponent's mortgaged property. 
			//Pay the mortgage value to the bank to claim the property.
		}
		
		if(name.equals("See you in Court")) {
			//Sue any player for unfair business practices. 
			//Take $250 from any player of your choice.
		}
		
	
		if(name.equals("Forward Thinker")) {
			//Advance forward 3 spaces.
			TrackType track = player.getCurrentTrack();
			int index = player.getPosition();
			player.goTo(track, (index+3));
		}
		
		// ref: cards "Changing Lanes", the alteration with 'below'
		 if (name.equalsIgnoreCase("Changing Lanes - Below")) {

			// Player does not move if they are already on the outer track.
			if (player.getCurrentTrack() == TrackType.INNER_TRACK) {

				// there is only one Community Chest square in the inner track (at
				// index 4), so we can proceed without checking anything else. 
				player.goTo(TrackType.MIDDLE_TRACK, 6);

			} else if (player.getCurrentTrack() == TrackType.MIDDLE_TRACK) {

				int curSquareInd = player.getPosition();

				if (curSquareInd == 2) {
					player.goTo(TrackType.OUTER_TRACK, 4);
				} else if (curSquareInd == 17) {
					player.goTo(TrackType.OUTER_TRACK, 23);
				} else if (curSquareInd == 33) {
					player.goTo(TrackType.OUTER_TRACK, 47);
				}

			}

		}
		
		// ref: cards "Changing Lanes", the alteration with 'above'
		else if (name.equalsIgnoreCase("Changing Lanes - Above")) {

			int curSquareInd = player.getPosition();

			// Player does not move if they are already on the inner track.
			if (player.getCurrentTrack() == TrackType.MIDDLE_TRACK) {

				if (curSquareInd == 2) {
					player.goTo(TrackType.INNER_TRACK, 0);
				} else if (curSquareInd == 17) {
					player.goTo(TrackType.INNER_TRACK, 11);
				} else if (curSquareInd == 33) {
					player.goTo(TrackType.INNER_TRACK, 19);
				}

			} else if (player.getCurrentTrack() == TrackType.OUTER_TRACK) {

				if (curSquareInd == 2) {
					player.goTo(TrackType.MIDDLE_TRACK, 0);
				} else if (curSquareInd == 24) {
					player.goTo(TrackType.MIDDLE_TRACK, 18);
				} else if (curSquareInd == 36) {
					player.goTo(TrackType.MIDDLE_TRACK, 26);
				} else if (curSquareInd == 46) {
					player.goTo(TrackType.MIDDLE_TRACK, 32);
				}

			}
		}
		
		// ref: card "Advance to this Track's Pay Corner"
		// !!! Name of this particular card has been shortened in the code.
		else if (name.equalsIgnoreCase("Advance to Pay")) {
			// TODO This code treats the cases as if going to pay corners does NOT
			// 	    automatically pay the player. In case it does, delete the increase
			//      money commands. I shall review the pay corner code tomorrow.
			if (player.getCurrentTrack().equals(TrackType.OUTER_TRACK)) {
				player.goTo(TrackType.OUTER_TRACK, 0);
				player.increaseMoney(400);
			} else if (player.getCurrentTrack().equals(TrackType.MIDDLE_TRACK)) {
				player.goTo(TrackType.MIDDLE_TRACK, 0);
				player.increaseMoney(200);
			} else if (player.getCurrentTrack().equals(TrackType.INNER_TRACK)) {
				player.goTo(TrackType.INNER_TRACK, 0);
				player.increaseMoney(300);
			}
		}
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
