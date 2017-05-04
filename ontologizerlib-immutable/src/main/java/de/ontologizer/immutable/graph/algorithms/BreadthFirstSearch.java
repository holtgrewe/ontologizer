package de.ontologizer.immutable.graph.algorithms;

import de.ontologizer.immutable.graph.DirectedGraph;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

/**
 * Visit vertices of a {@link Graph} in a breadth-first manner (pre-order).
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public class BreadthFirstSearch<Vertex, Graph>
		implements
			GraphVertexStartFromIteration<Vertex, Graph> {

	@SuppressWarnings("unchecked")
	@Override
	public void startFrom(Graph g, Vertex v, VertexVisitor<Vertex> visitor) {
		final DirectedGraph<Vertex> graph = (DirectedGraph<Vertex>) g; // XXX?
		final Set<Vertex> seen = new HashSet<Vertex>();
		final Queue<Vertex> queue = new ArrayDeque<Vertex>();
		queue.add(v);
		while (!queue.isEmpty()) {
			v = queue.poll();
			if (!seen.contains(v)) { // skip seen ones
				seen.add(v);
				visitor.visit(graph, v);
				final Iterator<Vertex> it = graph.childVertexIterator(v);
				while (it.hasNext()) {
					queue.add(it.next());
				}
			}
		}
	}

}
