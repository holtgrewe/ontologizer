package de.ontologizer.immutable.graph.algorithms;

import de.ontologizer.immutable.graph.DirectedGraph;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Visit all vertices of a {@link DirectedGraph} in topological order.
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public class TopologicalSorting<Vertex, Graph>
		implements
			GraphVertexAllIteration<Vertex, Graph> {

	/**
	 * Implementation of Tarjan's algorithm for topological sorting.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void start(Graph g, VertexVisitor<Vertex> visitor) {
		DirectedGraph<Vertex> graph = (DirectedGraph<Vertex>) g;
		final Set<Vertex> tmpMarked = new HashSet<Vertex>();

		// Collect unmarked vertices
		final Set<Vertex> unmarked = new HashSet<Vertex>();
		final Iterator<Vertex> vertexIterator = graph.vertexIterator();
		while (vertexIterator.hasNext()) {
			unmarked.add(vertexIterator.next());
		}

		// Perform visiting
		while (!unmarked.isEmpty()) {
			final Vertex v = unmarked.iterator().next();
			unmarked.remove(v);
			startFrom(graph, unmarked, tmpMarked, v, visitor);
		}
	}

	/**
	 * Tarjan's <code>visit()</code>.
	 */
	private void startFrom(DirectedGraph<Vertex> graph, Set<Vertex> unmarked,
			Set<Vertex> tmpMarked, Vertex v, VertexVisitor<Vertex> visitor) {
		if (tmpMarked.contains(v)) {
			throw new GraphNotDagException("Graph is not a DAG");
		}
		if (unmarked.contains(v)) {
			tmpMarked.add(v);
			Iterator<Vertex> nextVertices = graph.childVertexIterator(v);
			while (nextVertices.hasNext()) {
				startFrom(graph, unmarked, tmpMarked, nextVertices.next(),
						visitor);
			}
			unmarked.remove(v);
			tmpMarked.remove(v);
			visitor.visit(graph, v);
		}
	}

}
