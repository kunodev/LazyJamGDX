package de.kuro.lazyjam.simpleservice;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import de.kuro.lazyjam.cdiutils.annotations.InjectedService;
import de.kuro.lazyjam.cdiutils.annotations.Service;

@Service
public class FontManager {

	@InjectedService
	public Camera cam;
	
	@InjectedService
	public SpriteBatch batch;
	
	/**
	 * Font to use TODO: put into a global config? => when option menu comes
	 */
	private BitmapFont font;

	public FontManager() {
		font = new BitmapFont();
	}
	
	public void init() {
	}

	public void drawTextRelative(int xoffset, int yoffset, String text) {
		drawTextRelative(xoffset, yoffset, text, Color.WHITE);
	}
	
	public void drawTextRelative(int xoffset, int yoffset, String text, Color color) {
		drawTextRelative(xoffset, yoffset, text, color);
	}

	public void drawTextRelative(int xoffset, int yoffset, String text, int stateId, Color color) {
		font.setColor(color);
		font.draw(batch, text, cam.position.x + xoffset, cam.position.y + yoffset); //TODO: batch? Service? 
	}

	public void drawTextAbsolut(int xpos, int ypos, String text) {
		drawTextAbsolut(xpos, ypos, text, Color.WHITE);
	}

	public void drawTextAbsolut(int xpos, int ypos, String text, Color color)
	{	
		font.setColor(color);
		font.draw(batch, text, xpos, ypos); //TODO: batch? Service? 
	}

	public void drawTextAbsolut(float x, float y, String text) {
		this.drawTextAbsolut((int) x, (int) y, text);
	}
	
	public void drawTextAbsolutCentered(int x, int y, String text, Color color) {
		TextBounds bounds = this.getBounds(text);
		drawTextAbsolut(x - bounds.width/2, y - font.getLineHeight()/2, text, color);
	}

	public void drawTextAbsolut(float x, float y, String text, Color color) {
		this.drawTextAbsolut((int) x, (int) y, text, color);
	}

	public void drawAbsoluteWithRectangle(float x, float y, String picture, Rectangle rect) {
		String[] splitPicture = picture.split("\\n");
		int yOffset = 0;
		for(String line : splitPicture) {
			this.drawTextAbsolut(x , y + this.font.getLineHeight() *yOffset, line, Color.GREEN);
			yOffset++;
		}
		
	}
	
	public void drawAbsoluteWithRectangleCentered(float x, float y, String picture, Rectangle rect) {
		String[] splitPicture = picture.split("\\n");
		int yOffset = 0;
		for(String line : splitPicture) {
			this.drawTextAbsolut(x - rect.width/2, y - rect.height/2 + this.font.getLineHeight() *yOffset, line, Color.GREEN);
			yOffset++;
		}
		
	}
	
	public TextBounds getBounds(String text) {
		return font.getBounds(text);
	}

}
