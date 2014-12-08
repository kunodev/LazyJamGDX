package de.kuro.lazyjam.ecmodel.concrete.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.asciiassetextension.IRectangleProvider;
import de.kuro.lazyjam.cdiutils.annotations.Update;
import de.kuro.lazyjam.cdiutils.context.GameObjectContext;
import de.kuro.lazyjam.ecmodel.concrete.GameObject;
import de.kuro.lazyjam.ecmodel.concrete.GameState;
/**
 * Collision detection locally in the object, you cann add filters as you like, 
 * tagToSearch should be set, if you need the perfomance
 * @author kuro
 *
 */
public class ExtraSimpleCollisionComponent {
	
	public List<Predicate<GameObject>> filters;
	public String tagToSearch;
	public int activeAfter = 0;
	public long ticks = 0;

	public ExtraSimpleCollisionComponent() {
		filters = new ArrayList<Predicate<GameObject>>();
	}
	
	@Update
	public void collideWith(Vector2 pos, GameState gs, GameObjectContext goc, IRectangleProvider rect) {
		if (ticks++ < activeAfter) {
			return;
		}
		
		Stream<GameObject> gameObjectsStream;
		if(tagToSearch == null) {
			gameObjectsStream = gs.gameObjects.stream();			
		} else {
			Collection<GameObject> goCollection = gs.taggedGameObjects.get(tagToSearch);
			if(goCollection == null) {
				return;
			}
			gameObjectsStream = goCollection.stream();
		}

		Predicate<GameObject> finalFilter = e -> e != goc.go;
		
		for(Predicate<GameObject> filter : this.filters) {
			finalFilter = finalFilter.and(filter);
		}
		finalFilter = finalFilter.and(e -> (e.getComponent(IRectangleProvider.class) != null) && e.getComponent(IRectangleProvider.class).getRectangle().overlaps(rect.getRectangle()));
		gameObjectsStream
			.filter(finalFilter)
			.forEach(e -> e.callCollide(goc));
	}

}
