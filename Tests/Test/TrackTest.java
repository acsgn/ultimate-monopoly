package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.Track;
import game.TrackType;
import game.square.action.FreeParking;

public class TrackTest {

	Track track1; 
	

	@Test
	public void testAddSquare() {
		track1 = new Track(TrackType.INNER_TRACK);
		track1.addSquare(new FreeParking());		
		assertNotNull(track1.getSquare(0));
	}


}
