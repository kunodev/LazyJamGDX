package de.kuro.lazyjam.asciiassetextension;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.cdiutils.annotations.Component;
import de.kuro.lazyjam.cdiutils.annotations.Render;
import de.kuro.lazyjam.cdiutils.annotations.Update;
import de.kuro.lazyjam.simpleservice.FontManager;

/**
 * A Single Text to be rendered as Asset
 * @author kuro
 *
 */
@Component(name="ASCIIPic")
public class ASCIIPicture implements IRectangleProvider {

	public String picture;
	public Rectangle rect;
	public BitmapFont f = new BitmapFont();
	
	public static final int DEFAULT_FONTSIZE = 20;
	
	public ASCIIPicture() {
	}

	public ASCIIPicture(String picture) {
		this.picture = picture;
	}
	/**
	 * Overwrite the rectangle if wished
	 * @param width
	 * @param height
	 */
	public void setBoundaries(int width, int height) {
		this.rect = new Rectangle();
		this.rect.width = width;
		this.rect.height = height;
	}
	
	public void setBoundaries(Rectangle rect) {
		this.rect = rect;
	}
	
	@Update
	public void updateRect(Vector2 pos, FontManager fm) {
		this.getRectangle().x = pos.x;
		this.getRectangle().y = pos.y;
	}
 
	/**
	 * Calculates a rectangle from the text (Sure this can be done better, but it works)
	 */
	@Override
	public Rectangle getRectangle() {
		if(rect == null) {
			String[] lines = this.picture.split("\\n");
			float g = f == null ? DEFAULT_FONTSIZE : f.getLineHeight();
			int ySize = (int) (lines.length * g);
			int xSize = 0;
			for(String line : lines) {
				float h = f == null ? DEFAULT_FONTSIZE : f.getSpaceWidth();
				xSize = (int) Math.max(xSize, line.length() * h); 
			}
			rect = new Rectangle();
			rect.setHeight(ySize);
			rect.setWidth(xSize);
		}
		return rect;
	}
	
	@Render
	public void draw(FontManager fm, Vector2 pos, SpriteBatch b){
		fm.drawAbsoluteWithRectangleCentered(pos.x, pos.y, this.picture, getRectangle());
	}
	
}
