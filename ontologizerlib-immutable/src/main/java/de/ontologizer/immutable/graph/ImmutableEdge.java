package de.ontologizer.immutable.graph;

/**
 * Implementation of an immutable edge with default weight of 1.
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public final class ImmutableEdge<Vertex> implements Edge<Vertex> {

	private static final long serialVersionUID = 1L;

	private final Vertex source;
	private final Vertex dest;

	/**
	 * Construct <code>ImmutableEdge</code> with the given source and destination vertices.
	 *
	 * @param source
	 *            <code>Vertex</code> to use for the edge foot vertex
	 * @param dest
	 *            <code>Vertex</code> to use for the edge tip vertex
	 */
	public ImmutableEdge(Vertex source, Vertex dest) {
		this.source = source;
		this.dest = dest;
	}

	@Override
	public Vertex getSource() {
		return source;
	}

	/**
	 * Not supported.
	 *
	 * <p>
	 * Use {@link MutableEdge} which allows for setting the source vertex.
	 * </p>
	 *
	 * @throws UnsupportedOperationException
	 *             always
	 * @deprecated Use {@link MutableEdge} which allows for setting the source vertex.
	 */
	@Override
	public void setSource(Object source) {
	}

	@Override
	public Vertex getDest() {
		return dest;
	}

	/**
	 * Not supported.
	 *
	 * <p>
	 * Use {@link MutableEdge} which allows for setting the destination vertex.
	 * </p>
	 *
	 * @throws UnsupportedOperationException
	 *             always
	 * @deprecated Use {@link MutableEdge} which allows for setting the source vertex.
	 */
	@Override
	@Deprecated
	public void setDest(Object dest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getWeight() {
		return 1;
	}

	/**
	 * Not supported.
	 *
	 * <p>
	 * Use {@link MutableEdge} which allows for setting the edge weight.
	 * </p>
	 *
	 * @throws UnsupportedOperationException
	 *             always
	 * @deprecated Use {@link MutableEdge} which allows for setting the edge weight.
	 */
	@Override
	public void setWeight(int weight) {
		throw new UnsupportedOperationException();
	}

}
