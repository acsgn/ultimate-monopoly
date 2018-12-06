package game.card;

import game.Player;
import game.TrackType;

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
		}
		else if(name.equals("PARTY TIME")){
			player.reduceMoney(25);
		}
		// ref: cards "Changing Lanes", the alteration with 'below'
		else if (name.equalsIgnoreCase("Changing Lanes - Below")) {

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
	}

	@Override
	public CardType getCardType() {
		return type;
	}

	public String getName(){
		return name;
	}

}
