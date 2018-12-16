package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.Board;
import game.TrackType;

public class BoardTest {

	Board b;
	@Test
	public void testBoardInnerTrack() {
		b = new Board();
		assertEquals(b.getTrack(TrackType.INNER_TRACK).getSquareNumber(), 24);
		assertEquals(b.getTrack(TrackType.MIDDLE_TRACK).getSquareNumber(), 40);
		assertEquals(b.getTrack(TrackType.OUTER_TRACK).getSquareNumber(), 56);
	}

}
