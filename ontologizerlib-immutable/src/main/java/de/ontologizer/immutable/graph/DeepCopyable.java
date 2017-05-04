package de.ontologizer.immutable.graph;

/**
 * Interface for classes allowing deep copies.
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public interface DeepCopyable<T> {

	/**
	 * Generate deep copy of this object and return.
	 *
	 * <p>
	 * Deep copies make copies of containers and recursively of all contained objects.
	 * </p>
	 *
	 * @return deep copy of <code>this</code
	 */
	public T deepCopy();

}
