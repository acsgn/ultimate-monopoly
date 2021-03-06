package game.strategy;

import java.util.List;

import game.Player;
import game.dice.SingletonDice;
import game.square.Square;
import game.square.estate.Utility;

public class UtilityRentStrategy implements RentStrategy{

	@Override
	public int getRent(Square square) {
		Player owner = ((Utility)square).getOwner();
		List<Utility> ownedUtilities = owner.getUtilitySquares();
		int[] diceRolls = SingletonDice.getInstance().getFaceValues();
		int noOfUtilities = ownedUtilities.size();
		int amountOnDice = diceRolls[0]+ diceRolls[1];
		if(diceRolls[2]!=4 || diceRolls[2]!=5){
			amountOnDice += diceRolls[2];
		}
		switch(noOfUtilities){
			case 1: return 4 * amountOnDice;
			case 2: return 10 * amountOnDice;
			case 3: return 20 * amountOnDice;
			case 4: return 40 * amountOnDice;
			case 5: return 80 * amountOnDice;
			case 6: return 100 * amountOnDice;
			case 7: return 120 * amountOnDice;
			case 8: return 150 * amountOnDice;
		}
		return 0;
	}

}
