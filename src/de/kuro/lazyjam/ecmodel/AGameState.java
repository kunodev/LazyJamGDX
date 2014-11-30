package de.kuro.lazyjam.ecmodel;

import java.util.ArrayList;
import java.util.List;

import de.kuro.lazyjam.cdiutils.cdihelper.ServiceManager;
import de.kuro.lazyjam.cdiutils.context.GlobalContext;
import de.kuro.lazyjam.settings.Constants;

public abstract class AGameState implements IGameState {

	protected final List<Runnable> updateAbles;
	protected int tick = 0;
	public int TICK_TIME;
//	public Audio bgm; TODO: Audio Support

	protected AGameState() {
		this.TICK_TIME = Constants.DEFAULT_TICK_TIME;
		this.updateAbles = new ArrayList<Runnable>();
	}

	protected AGameState(int TICK_TIME) {
		this.TICK_TIME = TICK_TIME;
		this.updateAbles = new ArrayList<Runnable>();
	}

	protected abstract void update(GlobalContext globalContext);

	@Override
	public void onUpdate(GlobalContext globalContext, int deltaInMilliseconds) {
//		startBGM();
		tick += deltaInMilliseconds;
		if (tick >= TICK_TIME) {
			update(globalContext);
			tick = 0;
		}
	}

//	private void startBGM() {
//		if (bgm != null) {
//			if (!bgm.isPlaying()) {
//				bgm.playAsMusic(1f, 1f, true);
//			}
//		}
//	}

	@Override
	public void onLeaveState() {
//		if (bgm != null) {
//			bgm.stop();
//		}
	}

	@Override
	public int getTickTimer() {
		return TICK_TIME;
	}
}
