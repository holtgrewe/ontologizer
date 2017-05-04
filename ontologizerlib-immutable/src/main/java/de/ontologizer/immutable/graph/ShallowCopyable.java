package de.ontologizer.immutable.graph;

/**
 * Interface for classes allowing shallow copies.
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public interface ShallowCopyable<T> {

	/**
	 * Generate shallow copy of this object and return.
	 *
	 * <p>
	 * Shallow copies make copies of containers but not recursively of the contained elements.
	 * </p>
	 *
	 * @return shallow copy of <code>this</code
	 */
	public T shallowCopy();

}
