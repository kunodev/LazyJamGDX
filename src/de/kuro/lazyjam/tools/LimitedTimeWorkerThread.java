package de.kuro.lazyjam.tools;

/**
 * Crude implementation of a thread
 * @author kuro
 *
 */
public class LimitedTimeWorkerThread extends WorkerThread {

	private int maxTimes;

	public LimitedTimeWorkerThread(int sleepTimeMillis, Runnable event, int maxTimes) {
		super(sleepTimeMillis, event);
		this.maxTimes = maxTimes;
	}

	@Override
	public void run() {
		do {
			super.run();
			this.maxTimes--;
		} while (maxTimes > 0 && !this.kill);
	}

}
