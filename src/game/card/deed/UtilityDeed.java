package game.card.deed;

public class UtilityDeed extends Deed {

	private static final DeedType type = DeedType.UTILITY_DEED;

	@Override
	public DeedType getDeedType() {
		return type;
	}

}
