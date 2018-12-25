package game.bot;

import game.Controller;
import game.Player;
import game.square.Square;

public class Bot implements Runnable {

	private Player player;
	private botStrategy strategy;
	private boolean destroy = false;
	private static int botCounter = 0;

	public Bot(Player player) {
		this.player = player;
	}

	public void start() {
		new Thread(this, player.getName()).start();
	}

	public void play() {
		synchronized (this) {
			notify();
		}
	}
	
	public void destroy() {
		destroy = true;
		synchronized (this) {
			notify();
		}
	}
	

	public static void createBot() {
		botCounter++;
		Controller.getInstance().dispatchMessage("BOT/CREATEBOT/Bot_" + botCounter);
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			if(destroy)
				break;
			// Roll the dice
			String message = "BOT/ROLLDICE/" + player.getName();
			Controller.getInstance().dispatchMessage(message);

			// Do some Action,
			Square location = player.getLocation();
			String message_1;
			strategy = botStrategyFactory.getInstance().getbotStrategy(location);
			message_1 = strategy.getActionMessage(location, player);
			Controller.getInstance().dispatchMessage(message_1);

			// End your Turn.
			String message_2 = "BOT/ENDTURN/" + player.getName();
			Controller.getInstance().dispatchMessage(message_2);
		}
	}
}
