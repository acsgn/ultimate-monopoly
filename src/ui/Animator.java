package ui;

import java.util.ArrayList;

import javax.swing.JComponent;

public class Animator implements Runnable {

	private static long sleepTime = 25;

	private boolean stop = true;
	private boolean destruct = false;
	private ArrayList<JComponent> components;

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (this) {
					if (destruct)
						break;
					if (stop)
						wait();
				}
				Thread.sleep(sleepTime);
				for (JComponent c : components)
					c.repaint();
			} catch (InterruptedException e) {
			}
		}
	}

	public Animator() {
		components = new ArrayList<JComponent>();
	}

	public void addComponentToAnimate(JComponent component) {
		components.add(component);
	}

	public void startAnimator() {
		stop = false;
		synchronized (this) {
			notify();
		}
	}

	public void stopAnimator() {
		stop = true;
	}

	public void destruct() {
		destruct = true;
		synchronized (this) {
			notify();
		}
	}

	public boolean isStopped() {
		return stop;
	}

}
