package de.kuro.lazyjam.ecmodel.concrete.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import de.kuro.lazyjam.ecmodel.concrete.GameState;


/**
 * This Class is a "Decorator" to hack ConcurrentModification Exceptions. E.g.
 * the mouseinput update triggers on click, which spawns a new mouselistener =>
 * a new object spawns in the list => ConcurrentModification This type of list
 * is made for update-loops, where it is not important, that an update is
 * executed the same moment something is added or something is deleted. All
 * logics run in single threads anyway.
 * 
 * @author kuro
 *
 * @param <T>
 */

public class QueueSet<T> implements Collection<T>, Runnable {

	private List<T> ingoing;
	private List<T> trash;
	private List<T> data;

	public QueueSet(GameState gs) {
		gs.registerUpdateable(this);
		ingoing = new ArrayList<T>();
		trash = new ArrayList<T>();
		data = new ArrayList<T>();
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public void run() {
		data.removeAll(trash);
		trash.clear();
		data.addAll(ingoing);
		ingoing.clear();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return data.contains(o) || ingoing.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return data.iterator();
	}

	@Override
	public Object[] toArray() {
		return data.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return data.toArray(a);
	}

	@Override
	public boolean add(T e) {
		ingoing.add(e);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		if (o != null) {
			trash.add((T) o);
		}
		return true;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return data.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return ingoing.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return trash.addAll((Collection<? extends T>) c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		trash.addAll(data);
		ingoing.addAll((Collection<? extends T>) c);
		return true;
	}

	@Override
	public void clear() {
		trash.addAll(data);
	}

}
