package key;

/**
 * duplas para lista
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vin�cius
 */
public class Pair<T, S> {

	private T first;
	private S second;

	public Pair(T t, S s) {
		this.first = t;
		this.second = s;
	}

	public T getFirst() {
		return first;
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public S getSecond() {
		return second;
	}

	public void setSecond(S second) {
		this.second = second;
	}

}
