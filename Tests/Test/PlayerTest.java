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
import game.card.Chance;
import game.card.CommunityChest;
import game.square.estate.Property;
import game.square.estate.TitleDeed;
import game.square.estate.TransitStation;
import game.square.estate.Utility;

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
		assertTrue(player1.repOk());
	}

	@Test
	public void testIncreaseMoney() {
		player1 = new Player(new Board());
		int playerMoney = player1.getMoney();
		int money = 50;
		player1.increaseMoney(money);
		assertEquals(player1.getMoney(), playerMoney + money);
		assertTrue(player1.repOk());
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
		assertTrue(player1.repOk());

	}

	@Test
	public void testPayToPool() {
		player1 = new Player(new Board());
		pool1 = Pool.getInstance();
		int money = 200;
		pool1.payToPool(money);
		//
		assertEquals(pool1.getAmount(), money);
		assertTrue(player1.repOk());

	}
	
	@Test
	public void testAddHouse() {

		player1 = new Player(new Board());
		player1.addHouse();
		assertEquals(1, player1.getTotalHouses());
		assertTrue(player1.repOk());

	}
	
	@Test
	public void testAddHotels() {

		player1 = new Player(new Board());
		player1.addHotel();
		assertEquals(1, player1.getTotalHotels());
		assertTrue(player1.repOk());

	}
	
	@Test
	public void testaddSkyscaper() {

		player1 = new Player(new Board());
		player1.addSkyscaper();
		assertEquals(1, player1.getTotalSkyscrapers());
		assertTrue(player1.repOk());

	}
	
	@Test
	public void testSendToJail() {

		player1 = new Player(new Board());
		player1.sendToJail();
		assertTrue(player1.isInJail());
		assertTrue(player1.repOk());

	}
	@Test
	public void testbuyBuilding() {

		player1 = new Player(new Board());
		Building b = BuildingFactory.factoryBuilding().getBuilding("House");
		TitleDeed theEmbarcaderoTD = new TitleDeed(17, 85, 240, 475, 670, 1025, 1525, 105, 100, 100, 100);
		Property theEmbarcadero = new Property("The Embarcadero", 210, theEmbarcaderoTD);
		player1.buyBuilding(b, theEmbarcadero);
		assertTrue( theEmbarcadero.getBuildings().size()==1 &&  theEmbarcadero.getBuildings().get(0) instanceof House);
		assertTrue(player1.repOk());

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
		assertTrue(player1.repOk());

	}
	@Test 
	public void testColor(){
		player1 = new Player(new Board());
		player1.setColor("RED");
		assertEquals("RED",player1.getColor());
		assertTrue(player1.repOk());

	}
	@Test
	public void testGoTo(){
		Board b = new Board();
		player1 = new Player(b);
		player1.goTo(TrackType.INNER_TRACK, 3);
		assertEquals(player1.getIndexOnTrack(),3);
		assertEquals(player1.getCurrentTrack(),TrackType.INNER_TRACK);
		assertTrue(player1.repOk());
	}
	@Test 
	public void testGetName(){
		player1 = new Player(new Board());
		player1.setName("Water");
		assertEquals("Water",player1.getName());
		assertTrue(player1.repOk());

	}
	@Test
	public void testPayRent(){
		player1 = new Player(new Board());
		TransitStation t = new TransitStation("Transit", TrackType.INNER_TRACK, TrackType.MIDDLE_TRACK, 4,5);
		player1.getTransitStations().add(t);
		int m1 = player1.getMoney();
		assertTrue(player1.payRent(t));
		assertTrue(player1.getMoney() < m1);
		
		Utility waterWorks = new Utility("Water Works");
		waterWorks.setOwner(player1);
		player1.getUtilitySquares().add(waterWorks);
		int m2 = player1.getMoney();
		assertTrue(player1.payRent(waterWorks));
		
		TitleDeed mediterraneanAveTD = new TitleDeed(2, 10, 30, 90, 160, 250, 750, 30, 50, 50, 50);
		Property mediterraneanAve = new Property("Mediterranean Avenue", 60, mediterraneanAveTD);
		player1.getProperties().add(mediterraneanAve);
		mediterraneanAve.setOwner(player1);
		int m3 = player1.getMoney();
		assertTrue(player1.payRent(mediterraneanAve));
		
		assertTrue(player1.repOk());

	}
	
	@Test 
	public void testPickCard(){
		player1 = new Player(new Board());
		Chance pt = new Chance("Property Taxes", true);
		Property lakeStreet = new Property("Lake Street", 30, null);
		player1.getProperties().add(lakeStreet);
		int money = player1.getMoney();
		player1.pickCard(pt);
		assertEquals(player1.getMoney(), money-25);
		
		Chance pt_1 = new Chance("Holiday Bonus!", true);
		int money_1 = player1.getMoney();
		player1.pickCard(pt_1);
		assertEquals(player1.getMoney(), money_1+100);
		
		CommunityChest cc = new CommunityChest("Pay Hospital Bills");
		int money_2 = player1.getMoney();
		player1.pickCard(cc);
		assertEquals(player1.getMoney(), money_2-100);
		
		assertTrue(player1.repOk());
	}
	
	
	
	
	

}
