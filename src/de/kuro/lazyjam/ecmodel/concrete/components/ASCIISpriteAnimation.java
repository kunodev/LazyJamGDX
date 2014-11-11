package de.kuro.lazyjam.ecmodel.concrete.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

import de.kuro.lazyjam.asciiassetextension.ASCIIAssetManager;
import de.kuro.lazyjam.asciiassetextension.ASCIIPicture;
import de.kuro.lazyjam.asciiassetextension.IRectangleProvider;
import de.kuro.lazyjam.cdiutils.annotations.Component;
import de.kuro.lazyjam.cdiutils.annotations.ComponentInit;
import de.kuro.lazyjam.cdiutils.annotations.Render;
import de.kuro.lazyjam.cdiutils.cdihelper.CDICallHelper;
import de.kuro.lazyjam.cdiutils.context.GameObjectContext;

@Component(name="ASCIISprite")
public class ASCIISpriteAnimation extends SimpleAbstractAnimationComponent {


	public ASCIISpriteAnimation() {

	}

	public ASCIISpriteAnimation(ArrayList<ArrayList<IRectangleProvider>> pic) {
		this.renderableObjects = pic;
	}

	@Render
	public void onRender(GameObjectContext goc) {
		CDICallHelper.callOnObject(goc, Render.class, this.getCurrent());
	}

	
	public boolean load(BufferedReader reader) {
		/* Load String from file */
		try {
			// String line = null;
			// boolean states = false;
			// while ((line = reader.readLine()) != null) {
			// if(line.equals("/beginState")) {
			// states = true;
			// }
			// }
			// reader.reset();
			loadWithStates(reader);
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
			return false;
		}
		return true;
	}

	private void loadWithStates(BufferedReader reader) throws IOException {
		String line;
		ArrayList<IRectangleProvider> currentList = new ArrayList<IRectangleProvider>();
		while ((line = reader.readLine()) != null) {
			if (line.equals("=")) {
				this.renderableObjects.add(currentList);
				currentList = new ArrayList<IRectangleProvider>();
				continue;
			} else {
				currentList.add(loadPic(reader, line));
			}
		}
		this.renderableObjects.add(currentList);
	}

	private ASCIIPicture loadPic(BufferedReader reader, String startLine) throws IOException {
		String line = startLine;
		String picture = "";
		ASCIIPicture result = new ASCIIPicture();
		do {
			if (line.equals("-")) {
				result.picture = picture;
				return result;
			} else {
				picture += line + "\n";
			}
		} while ((line = reader.readLine()) != null);
		result.picture = picture;
		return result;
	}

	@ComponentInit
	public void initWithString(String val, ASCIIAssetManager assetMan) {
		SimpleAbstractAnimationComponent preFab = assetMan.getAsset(val);
		if (preFab instanceof ASCIISpriteAnimation) {
			ASCIISpriteAnimation preFabTyped = (ASCIISpriteAnimation) preFab;
			this.overWriteAsset(preFabTyped);
		}
	}

	private void overWriteAsset(ASCIISpriteAnimation preFab) {
		this.renderableObjects = preFab.renderableObjects;
	}
}
