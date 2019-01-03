package game.bot;

import java.awt.Color;
import java.io.Serializable;
import java.util.Random;

import game.Controller;
import game.Player;
import game.square.Square;

public class Bot implements Runnable, Serializable {
	private static final long serialVersionUID = 1L;

	private Player player;
	private botStrategy strategy;
	private boolean destroy = false;

	private static int botCounter = 0;
	private static Random random = new Random();
	private static final float saturation = 0.9f;
	private static final float luminance = 0.9f;

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
		int color = Color.getHSBColor(random.nextFloat(), saturation, luminance).getRGB();
		Controller.getInstance().dispatchMessage("BOT/CREATEBOT/Bot_" + botCounter + "/" + color);
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
			if (destroy)
				break;

			// Roll the dice
			String message = "BOT/ROLLDICE/" + player.getName();
			Controller.getInstance().dispatchMessage(message);

			// Do some Action
			Square location = player.getLocation();
			strategy = botStrategyFactory.getInstance().getbotStrategy(location);
			String message_1 = strategy.getActionMessage(location, player);
			Controller.getInstance().dispatchMessage(message_1);

			// End your Turn.
			String message_2 = "BOT/ENDTURN/" + player.getName();
			Controller.getInstance().dispatchMessage(message_2);
		}
	}
}
