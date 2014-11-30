package de.kuro.lazyjam.ecmodel.concrete.components;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import de.kuro.lazyjam.asciiassetextension.IRectangleProvider;
import de.kuro.lazyjam.asciiassetextension.SpriteWrapper;
import de.kuro.lazyjam.cdiutils.annotations.Component;
import de.kuro.lazyjam.cdiutils.annotations.ComponentInit;
import de.kuro.lazyjam.cdiutils.annotations.Render;
import de.kuro.lazyjam.cdiutils.annotations.Update;
import de.kuro.lazyjam.cdiutils.cdihelper.CDICallHelper;
import de.kuro.lazyjam.cdiutils.context.GameObjectContext;

@Component(name="PNGSprite")
public class PNGSpriteRenderComponent extends SimpleAbstractAnimationComponent implements IRectangleProvider{	
	
	public int loopTick = 30;
	private int currentTick;
	public SpriteWrapper sprite;
	public boolean play = true;
	public boolean playOnce = false;
	private int maxX;
	private int maxY;
	
	public class MyTexRegion extends TextureRegion implements IRectangleProvider {

		
		public MyTexRegion() {
			super();
			// TODO Auto-generated constructor stub
		}

		public MyTexRegion(Texture texture, float u, float v, float u2, float v2) {
			super(texture, u, v, u2, v2);
			// TODO Auto-generated constructor stub
		}

		public MyTexRegion(Texture texture, int x, int y, int width, int height) {
			super(texture, x, y, width, height);
			// TODO Auto-generated constructor stub
		}

		public MyTexRegion(Texture texture, int width, int height) {
			super(texture, width, height);
			// TODO Auto-generated constructor stub
		}

		public MyTexRegion(Texture texture) {
			super(texture);
			// TODO Auto-generated constructor stub
		}

		public MyTexRegion(TextureRegion region, int x, int y, int width,
				int height) {
			super(region, x, y, width, height);
			// TODO Auto-generated constructor stub
		}

		public MyTexRegion(TextureRegion region) {
			super(region);
			// TODO Auto-generated constructor stub
		}

		
		@Override
		public Rectangle getRectangle() {
			return sprite.s.getBoundingRectangle();
		}
	}
	
	@Update
	public void update() {
		if(play) { 
			currentTick++;
		}
		if(currentTick >= loopTick) {
			this.incrementXOffset();
			currentTick = 0;
			if (playOnce && this.xOffset == maxX - 1) {
				play = false;
			}
		}
	}
	
	public void reset() {
		this.xOffset = 0;
	}
	
	@Render
	public void render(GameObjectContext goc) {
		sprite.setRegion((TextureRegion)getCurrent());
		CDICallHelper.callOnObject(goc, Render.class, sprite);
	}
	
	@ComponentInit
	public void init(String initString, AssetManager assetMan) {
		// Check params
		String[] initVal = initString.split("\\+");
		Texture preFab = assetMan.get(initVal[0] + ".png");
		sprite = new SpriteWrapper(preFab);
		maxX = Integer.parseInt(initVal[1]);
		maxY = Integer.parseInt(initVal[2]);
		// precalc
		int widthOfSprite = preFab.getWidth() / maxX;
		int heightOfSprite = preFab.getHeight() / maxY;
		sprite.s.setSize(widthOfSprite, heightOfSprite);
		int currX = 0;
		int currY = 0;
		// init fields
		if(this.renderableObjects == null) {
			this.renderableObjects = new ArrayList<ArrayList<IRectangleProvider>>();
		}
		for(int i=0; i<maxY; i++) {
			this.renderableObjects.add(new ArrayList<IRectangleProvider>());
			for(int j=0; j<maxX; j++) { 
				MyTexRegion tr = new MyTexRegion(preFab, currX * widthOfSprite, currY * heightOfSprite, widthOfSprite, heightOfSprite);
				this.renderableObjects.get(i).add(tr);
				
				currX++;
			}
			currY++;
		}
	}
	
	@Override
	public Rectangle getRectangle() {
		return sprite.s.getBoundingRectangle();
	}
}
