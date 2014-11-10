package de.kuro.lazyjam.ecmodel.concrete.tools;

import java.util.Comparator;

import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.ecmodel.concrete.GameObject;


public class VectorDistanceComparator implements Comparator<GameObject> {

	Vector2 referenceVector;

	public VectorDistanceComparator(GameObject reference) {
		this.referenceVector = reference.getPos();
	}

	public VectorDistanceComparator(Vector2 referenceVector) {
		this.referenceVector = referenceVector;
	}

	@Override
	public int compare(GameObject o1, GameObject o2) {
		return distance(o1.getPos(), o2.getPos(), referenceVector);
	}

	public static int distance(Vector2 o1, Vector2 o2, Vector2 ref) {
		return (int) (ref.dst2(o1) - ref.dst2(o2));
	}

}
