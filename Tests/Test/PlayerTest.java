package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.Board;
import game.Player;
import game.Pool;
import game.TrackType;
import game.building.Building;
import game.building.BuildingFactory;
import game.building.House;
import game.square.estate.Property;
import game.square.estate.TitleDeed;
import game.square.estate.TransitStation;

public class PlayerTest {

	Player player1;
	Pool pool1;


	@Test
	public void testReduceMoney() {
		player1 = new Player(new Board());
		int playerMoney = player1.getMoney();
		int money = 50;
		assertTrue(player1.reduceMoney(money));
		assertEquals(player1.getMoney(), playerMoney - money);	
		int m = 5000;
		assertFalse(player1.reduceMoney(m));
	}

	@Test
	public void testIncreaseMoney() {
		player1 = new Player(new Board());
		int playerMoney = player1.getMoney();
		int money = 50;
		player1.increaseMoney(money);
		assertEquals(player1.getMoney(), playerMoney + money);
	}

	@Test
	public void testPayBail() {
		player1 = new Player(new Board());
		pool1 = Pool.getInstance();

		int playerMoney = player1.getMoney();
		int money = 100;
		player1.payBail(money);
		assertEquals(player1.getMoney(), playerMoney - money);
		assertEquals(pool1.getAmount(), money);
	}

	@Test
	public void testPayToPool() {

		pool1 = Pool.getInstance();
		int money = 200;
		pool1.payToPool(money);
		assertEquals(pool1.getAmount(), money);
	}
	
	@Test
	public void testAddHouse() {

		player1 = new Player(new Board());
		player1.addHouse();
		assertEquals(1, player1.getTotalHouses());
		
	}
	
	@Test
	public void testAddHotels() {

		player1 = new Player(new Board());
		player1.addHotel();
		assertEquals(1, player1.getTotalHotels());
		
	}
	
	@Test
	public void testaddSkyscaper() {

		player1 = new Player(new Board());
		player1.addSkyscaper();
		assertEquals(1, player1.getTotalSkyscrapers());
		
	}
	
	@Test
	public void testSendToJail() {

		player1 = new Player(new Board());
		player1.sendToJail();
		assertTrue(player1.isInJail());
		
	}
	@Test
	public void testbuyBuilding() {

		player1 = new Player(new Board());
		Building b = BuildingFactory.factoryBuilding().getBuilding("House");
		TitleDeed theEmbarcaderoTD = new TitleDeed(17, 85, 240, 475, 670, 1025, 1525, 105, 100, 100, 100);
		Property theEmbarcadero = new Property("The Embarcadero", 210, theEmbarcaderoTD);
		player1.buyBuilding(b, theEmbarcadero);
		assertTrue( theEmbarcadero.getBuildings().size()==1 &&  theEmbarcadero.getBuildings().get(0) instanceof House);
		
	}
	
	@Test
	public void testSellBuilding() {
		player1 = new Player(new Board());
		Building b = BuildingFactory.factoryBuilding().getBuilding("House");
		TitleDeed theEmbarcaderoTD = new TitleDeed(17, 85, 240, 475, 670, 1025, 1525, 105, 100, 100, 100);
		Property theEmbarcadero = new Property("The Embarcadero", 210, theEmbarcaderoTD);
		player1.buyBuilding(b, theEmbarcadero);
		int s = theEmbarcadero.getBuildings().size();
		player1.sellBuilding(b, theEmbarcadero);
		assertTrue(s == theEmbarcadero.getBuildings().size()+1);
		
	}
	@Test 
	public void testColor(){
		player1 = new Player(new Board());
		player1.setColor("RED");
		assertEquals("RED",player1.getColor());
	}
	@Test
	public void testGoTo(){
		Board b = new Board();
		player1 = new Player(b);
		player1.goTo(TrackType.INNER_TRACK, 3);
		assertEquals(player1.getIndexOnTrack(),3);
		assertEquals(player1.getCurrentTrack(),TrackType.INNER_TRACK);
	}
	@Test 
	public void testGetName(){
		player1 = new Player(new Board());
		player1.setName("Water");
		assertEquals("Water",player1.getName());
	}
	@Test
	public void testPayRent(){
		player1 = new Player(new Board());
		TransitStation t = new TransitStation("Transit", TrackType.INNER_TRACK, TrackType.MIDDLE_TRACK, 4,5);
		player1.getTransitStations().add(t);
		int m1 = player1.getMoney();
		assertTrue(player1.payRent(t));
		assertTrue(player1.getMoney() < m1);
	}
	
	
	
	
	

}
