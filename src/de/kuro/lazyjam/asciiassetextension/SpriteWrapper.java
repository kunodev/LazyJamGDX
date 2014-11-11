package de.kuro.lazyjam.asciiassetextension;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.cdiutils.annotations.Component;
import de.kuro.lazyjam.cdiutils.annotations.Render;

@Component(name = "PNG")
public class SpriteWrapper implements IRectangleProvider {
	
	public Sprite s;
	
	public SpriteWrapper(Sprite s) {
		this.s = s;
	}

	@Override
	public Rectangle getRectangle() {
		return s.getBoundingRectangle();
	}

	@Render
	public void draw(SpriteBatch sb, Vector2 pos) {
		s.setPosition(pos.x, pos.y);
		s.draw(sb);		
	}

}
