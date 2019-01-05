package game.square.estate;

public enum PropertyLevel {
	// Houses Levels
	ZERO_HOUSE_LEVEL, ONE_HOUSE_LEVEL, TWO_HOUSE_LEVEL, THREE_HOUSE_LEVEL, FOUR_HOUSE_LEVEL,
	// Hotel Level
	HOTEL_LEVEL,
	// Skyscapper level
	SKYSCRAPER_LEVEL;
	
	private static PropertyLevel[] vals = values();
    public PropertyLevel next()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }
    public PropertyLevel previous(){
    	return vals[(this.ordinal()-1 + vals.length) % vals.length];
    }
	
	
	
}
