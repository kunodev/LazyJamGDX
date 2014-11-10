package de.kuro.lazyjam.simpleservice;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

	public void drawTextAbsolut(float x, float y, String text, Color color) {
		this.drawTextAbsolut((int) x, (int) y, text, color);
	}

}
