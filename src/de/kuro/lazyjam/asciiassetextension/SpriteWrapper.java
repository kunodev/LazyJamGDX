package de.kuro.lazyjam.asciiassetextension;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.cdiutils.annotations.Component;
import de.kuro.lazyjam.cdiutils.annotations.Render;

@Component(name = "PNG")
public class SpriteWrapper implements IRectangleProvider {
	
	public Sprite s;
	
	public SpriteWrapper(Texture tex) {
		s = new Sprite(tex);
	}
	
	public SpriteWrapper(Sprite s) {
		this.s = s;
	}
	
	public void setRegion(TextureRegion r) {
		s.setRegion(r);
	}

	@Override
	public Rectangle getRectangle() {
		return s.getBoundingRectangle();
	}

	@Render
	public void draw(SpriteBatch sb, Vector2 pos) {
		s.setPosition(pos.x - s.getWidth()/2, pos.y - s.getHeight()/2);
		s.draw(sb);	
	}

}
