package game.bot;

public class botStrategyFactory {
	
	private static botStrategyFactory factory;
	
	private botStrategyFactory(){
		
	}
	public static botStrategyFactory getInstance(){
		if (factory==null){
			factory = new botStrategyFactory();
		}
		return factory;
	}
	public botStrategy getbotStrategy(BotType type){
		if(type==BotType.DUMMY){
			return new DummyBotStrategy();
		}else if(type==BotType.Greedy){
			return new GreedyBotStrategy();
		}else{
			return new ModerateBotStrategy();
		}
	}
}
