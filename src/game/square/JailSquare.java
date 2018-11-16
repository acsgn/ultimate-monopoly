package game.square;

public class JailSquare extends Square{

	private static final SquareType type = SquareType.JAIL;
	
	public JailSquare() {
	}

	@Override
	public SquareType getType() {
		return type;
	}

}
