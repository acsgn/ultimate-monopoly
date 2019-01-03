package game.square.action;

import game.Player;
import game.TrackType;

public class HollandTunnel extends Action {
	private static final long serialVersionUID = 1L;

	private static final TrackType track1 = TrackType.INNER_TRACK;
	private static final TrackType track2 = TrackType.OUTER_TRACK;

	private static final int index1 = 18;
	private static final int index2 = 14;

	public HollandTunnel() {
	}

	@Override
	public void executeWhenPassed(Player player) {
	}

	@Override
	public void executeWhenLanded(Player player) {
		if (player.getCurrentTrack() == track1)
			player.goTo(track2, index2);
		else
			player.goTo(track1, index1);
	}
	
}
