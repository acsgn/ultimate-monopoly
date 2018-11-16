package game.square.deed;


public class RailroadDeed extends Deed {

	private static final DeedType type = DeedType.RAILROAD_DEED;

	@Override
	public DeedType getDeedType() {
		return type;
	}

}
