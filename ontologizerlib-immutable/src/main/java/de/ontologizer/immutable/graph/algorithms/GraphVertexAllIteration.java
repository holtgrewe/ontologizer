package de.ontologizer.immutable.graph.algorithms;

import de.ontologizer.immutable.graph.DirectedGraph;

/**
 * Interface for iteration of {@link DirectedGraph} vertices using the Visitor
 * pattern, starting from specific vertex.
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
@SuppressWarnings("hiding")
public interface GraphVertexAllIteration<Vertex, DirectedGraph> {

	/**
	 * Iterate all vertices in topological order.
	 *
	 * <p>
	 * {@link VertexVisitor#visit(DirectedGraph, Object)} will be called for
	 * vertices of the graph starting from <code>v</code>
	 * </p>
	 *
	 * @param g
	 *            {@link DirectedGraph} to iterate over
	 * @param visitor
	 *            {@link VertexVisitor} to use for notifying about reaching a
	 *            vertex
	 */
	public void start(DirectedGraph g, VertexVisitor<Vertex> visitor);

}
