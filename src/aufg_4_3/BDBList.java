package aufg_4_3;

public interface BDBList<E> {
	public boolean addBeginning(E e);

	public boolean addEnd(E e);

	public E get(int index);
}
