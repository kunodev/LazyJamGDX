package de.kuro.lazyjam.asciiassetextension;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.cdiutils.annotations.Component;
import de.kuro.lazyjam.cdiutils.annotations.Render;
import de.kuro.lazyjam.simpleservice.FontManager;

@Component(name="ASCIIPic")
public class ASCIIPicture implements IRectangleProvider {

	public String picture;
	public Rectangle rect;
	
	public ASCIIPicture() {
	}

	public ASCIIPicture(String picture) {
		this.picture = picture;
	}
	
	public void setBoundaries(int width, int height) {
		this.rect = new Rectangle();
		this.rect.width = width;
		this.rect.height = height;
	}
	
	public void setBoundaries(Rectangle rect) {
		this.rect = rect;
	}
 
	@Override
	public Rectangle getRectangle() {
		return rect;
	}
	
	@Render
	public void draw(FontManager fm, Vector2 pos, SpriteBatch b){
		fm.drawAbsoluteWithRectangle(pos.x, pos.y, this.picture, this.rect);
	}
	
}
