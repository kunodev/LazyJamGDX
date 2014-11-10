package de.kuro.lazyjam.componentprefabs;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.cdiutils.annotations.Component;
import de.kuro.lazyjam.cdiutils.annotations.ComponentInit;
import de.kuro.lazyjam.cdiutils.annotations.Render;

@Component(name="PNGSprite")
public class PNGSpriteRenderComponent {
	
	Sprite sprite;
	int maxX;
	int maxY;
	
	
	@Render
	public void render(SpriteBatch b, Vector2 pos) {
		sprite.setCenter(pos.x/2, pos.y/2);
		sprite.draw(b);
	}
	
	@ComponentInit
	public void init(String initString, AssetManager assetMan) {
		String[] initVal = initString.split("\\+");
		Texture preFab = assetMan.get(initVal[0] + ".png");
		this.sprite = new Sprite(preFab);
		
		maxX = Integer.parseInt(initVal[1]);
		maxY = Integer.parseInt(initVal[2]);
	}
		
}
