package de.kuro.lazyjam.tools;
/**
 * Crude implementation of a thread
 * @author kuro
 *
 */
public class WorkerThread extends Thread {

	private int sleepTimeMillis;
	private Runnable task;
	public boolean running = true;
	public boolean kill = false;

	public WorkerThread(int sleepTimeMillis, Runnable event) {
		this.task = event;
		this.sleepTimeMillis = sleepTimeMillis;
	}

	@Override
	public void run() {
		if (running) {
			task.run();
			try {
				sleep(sleepTimeMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
