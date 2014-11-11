package de.kuro.lazyjam.asciiassetextension;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import de.kuro.lazyjam.cdiutils.annotations.Service;
import de.kuro.lazyjam.ecmodel.concrete.components.ASCIISpriteAnimation;
import de.kuro.lazyjam.ecmodel.concrete.components.SimpleAbstractAnimationComponent;

@Service
public class ASCIIAssetManager {
	
	public Map<String, ASCIISpriteAnimation> assetBank;
	
	public ASCIIAssetManager() {
		FileHandle assetFolder = Gdx.files.internal("assets");
		for(FileHandle handle : assetFolder.list(".txt")) {
			ASCIISpriteAnimation newAnim = new ASCIISpriteAnimation();
			newAnim.load(handle.reader(128));
			this.assetBank.put(handle.file().getName(), newAnim );
		}
	}

	public SimpleAbstractAnimationComponent getAsset(String val) {
		return assetBank.get(val);
	}
}

