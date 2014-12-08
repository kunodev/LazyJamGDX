package de.kuro.lazyjam.asciiassetextension;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.kuro.lazyjam.cdiutils.annotations.Component;
import de.kuro.lazyjam.cdiutils.annotations.Render;
import de.kuro.lazyjam.ecmodel.concrete.GameState;

@Component(name = "PNG_ANI")
/**
 * Wraps regular Sprites into Interface "Object with Rectangles" (which is used for collision)
 * @author kuro
 *
 */
public class AnimationWrapper implements  IRectangleProvider{
	private Rectangle rect;
	private Animation animation;
	private float stateTime;

	/**
	 * Easiest way to deal with sprites is y axis has states and x axis has ongoing animations
	 * Rectangle gets calculated with the getheight and width divided by the columns
	 * @param aniTex 
	 * @param numCols 
	 * @param numRows
	 * @param frameDuration
	 * @param playMode
	 */
	public AnimationWrapper(Texture aniTex, int numCols, int numRows, float frameDuration, Animation.PlayMode playMode) {
		final float width = aniTex.getWidth() / numCols;
		final float height = aniTex.getHeight() / numRows;
		this.rect = new Rectangle();
		rect.width = width;
		rect.height = height;

		this.animation = loadAnimation(aniTex, numCols, numRows, frameDuration, playMode);
	}

	@Override
	public Rectangle getRectangle() {
		return rect;
	}

	/**
	 * Draws the Sprite Centered
	 * @param gs
	 * @param sb
	 * @param pos
	 */
	@Render
	public void draw(GameState gs, SpriteBatch sb, Vector2 pos) {
		stateTime += gs.getTickTimer() / 1000.0f;
		rect.setPosition(pos.x - rect.getWidth() * 0.5f, pos.y - rect.getHeight() * 0.5f);
		sb.draw(animation.getKeyFrame(stateTime), rect.x, rect.y, rect.width, rect.height);
	}

	public static Animation loadAnimation(final Texture aniTex, int numCols, int numRows, float frameDuration, Animation.PlayMode playMode) {
		TextureRegion[][] tmp = TextureRegion.split(aniTex, aniTex.getWidth() / numCols, aniTex.getHeight() / numRows);
		TextureRegion[] keyFrames = new TextureRegion[numCols * numRows];
		int index = 0;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				keyFrames[index++] = tmp[i][j];
			}
		}
		final Animation ani = new Animation(frameDuration, keyFrames);
		ani.setPlayMode(playMode);
		return ani;
	}
}
