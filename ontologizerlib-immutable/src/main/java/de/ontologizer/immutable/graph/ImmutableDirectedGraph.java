package de.ontologizer.immutable.graph;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import de.ontologizer.immutable.graph.impl.ImmutableVertexEdgeList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Implementation of an immutable directed graph.
 * 
 * <h3>Construction</h3>
 * 
 * <p>
 * You can use the functions
 * {@link #construct(Collection, Collection, boolean)},
 * {@link #construct(Collection, Collection)},
 * {@link #construct(Collection, boolean)}, or {@link #construct(Collection)}
 * for constructing the graph from a set of edges (and optionally a set of
 * vertices). Alternative, you can use the {@link Builder} class for iterative
 * construction.
 * </p>
 * 
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public final class ImmutableDirectedGraph<Vertex>
		implements
			DirectedGraph<Vertex> {

	private static final long serialVersionUID = 1L;

	private ImmutableMap<Vertex, ImmutableVertexEdgeList<Vertex>> edgeLists;

	/**
	 * Construct and return a {@link #Builder} helper object.
	 * 
	 * @return Freshly constructed {@link #Builder} object.
	 */
	public static <Vertex> Builder<Vertex> builder() {
		return new Builder<Vertex>();
	}

	/**
	 * Construct a new {@link ImmutableDirectedGraph} from a collection of
	 * vertices and edges.
	 * 
	 * @param vertices
	 *            {@link Collection} of <code>Vertex</code> objects to use for
	 *            construction
	 * @param edges
	 *            {@link Collection} of <code>Edge</code> objects to use for
	 *            construction
	 * @param checkCompatibility
	 *            whether or not to check vertex and edge list to be compatible
	 * @return the built {@link ImmutableDirectedGraph}
	 */
	public static <Vertex> ImmutableDirectedGraph<Vertex> construct(
			Collection<Vertex> vertices,
			Collection<? extends Edge<Vertex>> edges,
			boolean checkCompatibility) {
		// Check compatibility if asked for
		if (checkCompatibility) {
			checkCompatibility(vertices, edges);
		}
		// Create copy of immutable edges
		List<ImmutableEdge<Vertex>> immutableEdges = new ArrayList<>();
		for (Edge<Vertex> edge : edges) {
			immutableEdges.add(new ImmutableEdge<Vertex>(edge.getSource(),
					edge.getDest()));
		}
		return new ImmutableDirectedGraph<Vertex>(vertices, immutableEdges);
	}

	/**
	 * Construct a new {@link ImmutableDirectedGraph} from a collection edges.
	 * 
	 * <p>
	 * The edge list is automatically inferred from the edges' vertices.
	 * </p>
	 * 
	 * @param edges
	 *            {@link Collection} of <code>Edge</code> objects to use for
	 *            construction
	 * @param checkCompatibility
	 *            whether or not to check vertex and edge list to be compatible
	 * @return the built {@link ImmutableDirectedGraph}
	 */
	public static <Vertex> ImmutableDirectedGraph<Vertex> construct(
			Collection<? extends Edge<Vertex>> edges,
			boolean checkCompatibility) {
		// Collect the vertices in the same order as in edges
		List<Vertex> vertices = new ArrayList<Vertex>();
		Set<Vertex> vertexSet = new HashSet<Vertex>();
		for (Edge<Vertex> edge : edges) {
			if (!vertexSet.contains(edge.getSource())) {
				vertexSet.add(edge.getSource());
				vertices.add(edge.getSource());
			}
			if (!vertexSet.contains(edge.getDest())) {
				vertexSet.add(edge.getDest());
				vertices.add(edge.getDest());
			}
		}
		// Forward to implementation
		return construct(vertices, edges, checkCompatibility);
	}

	/**
	 * Construct a new {@link ImmutableDirectedGraph} from a collection of
	 * vertices and edges.
	 *
	 * <p>
	 * This is just a forward to <code>construct(vertices, edges, false);
	 * </p>
	 */
	public static <Vertex> ImmutableDirectedGraph<Vertex> construct(
			Collection<Vertex> vertices,
			Collection<? extends Edge<Vertex>> edges) {
		return construct(vertices, edges, false);
	}

	/**
	 * Construct a new {@link ImmutableDirectedGraph} from a collection of
	 * edges.
	 *
	 * <p>
	 * This is just a forward to <code>construct(edges, false);
	 * </p>
	 */
	public static <Vertex> ImmutableDirectedGraph<Vertex> construct(
			Collection<? extends Edge<Vertex>> edges) {
		return construct(edges, false);
	}

	/**
	 * This constructor is used internally for constructing via the static
	 * <code>create</code> functions.
	 * 
	 * @param vertices
	 *            to use for constructing the graph with
	 * @param edges
	 *            to use for constructing the graph with.
	 */
	private ImmutableDirectedGraph(Collection<Vertex> vertices,
			Collection<ImmutableEdge<Vertex>> edges) {
		// Construct mapping from vertex to builder
		Map<Vertex, ImmutableVertexEdgeList.Builder<Vertex>> builders = new HashMap<Vertex, ImmutableVertexEdgeList.Builder<Vertex>>();
		for (Vertex v : vertices) {
			builders.put(v, ImmutableVertexEdgeList.<Vertex>builder());
		}
		// Fill edge builders
		// Fill ImmutableMap builder and construct
		com.google.common.collect.ImmutableMap.Builder<Vertex, ImmutableVertexEdgeList<Vertex>> builder = ImmutableMap
				.<Vertex, ImmutableVertexEdgeList<Vertex>>builder();
		for (Entry<Vertex, ImmutableVertexEdgeList.Builder<Vertex>> e : builders
				.entrySet()) {
			builder.put(e.getKey(), e.getValue().build());
		}
	}

	/**
	 * Check compatibility of <code>vertices</code> and <code>edges</code>,
	 * i.e., there must not be a vertex in <code>edges</code> that is not
	 * present in <code>vertices</code>.
	 * 
	 * @raises VerticesAndEdgesIncompatibleException in case of
	 *         incompatibilities.
	 */
	private static <Vertex> void checkCompatibility(Collection<Vertex> vertices,
			Collection<? extends Edge<Vertex>> edges) {
		Set<Vertex> vertexSet = new HashSet<>(vertices);
		for (Edge<Vertex> edge : edges) {
			if (!vertexSet.contains(edge.getSource())) {
				throw new VerticesAndEdgesIncompatibleException(
						"Unknown source edge in edge " + edge);
			}
			if (!vertexSet.contains(edge.getDest())) {
				throw new VerticesAndEdgesIncompatibleException(
						"Unknown dest edge in edge " + edge);
			}
			if (edge.getSource() == edge.getDest()) {
				throw new VerticesAndEdgesIncompatibleException(
						"Self-loop edge " + edge);
			}
		}

		// TODO: ensure the graph is simple
	}

	@Override
	public DirectedGraph<Vertex> shallowCopy() {
		return subGraph(edgeLists.keySet());
	}

	/**
	 * Not supported.
	 *
	 * <p>
	 * Use {@link MutableDirectedGraph} which allows for adding vertices or use
	 * {@link #Builder}.
	 * </p>
	 * 
	 * @throws UnsupportedOperationException
	 *             always
	 * @deprecated Use {@link MutableDirectedGraph} which allows for adding
	 *             vertices or use {@link #Builder}.
	 */
	@Override
	@Deprecated
	public void addVertex(Vertex v) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Not supported.
	 *
	 * <p>
	 * Use {@link MutableDirectedGraph} which allows for removing vertices.
	 * </p>
	 * 
	 * @throws UnsupportedOperationException
	 *             always
	 * @deprecated Use {@link MutableDirectedGraph} which allows for removing
	 *             vertices.
	 */
	@Override
	@Deprecated
	public void removeVertex(Vertex v) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsVertex(Vertex v) {
		return edgeLists.containsKey(v);
	}

	@Override
	public int countVertices() {
		return edgeLists.size();
	}

	@Override
	public Iterator<Vertex> vertexIterator() {
		return edgeLists.keySet().iterator();
	}

	/**
	 * Not supported.
	 *
	 * <p>
	 * Use {@link MutableDirectedGraph} which allows for adding edges or use
	 * {@link #Builder}.
	 * </p>
	 * 
	 * @throws UnsupportedOperationException
	 *             always
	 * @deprecated Use {@link MutableDirectedGraph} which allows for adding
	 *             edges or use {@link #Builder}.
	 */
	@Override
	@Deprecated
	public void addEdge(Edge<Vertex> edge) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Not supported.
	 *
	 * <p>
	 * Use {@link MutableDirectedGraph} which allows for removing edges or use
	 * {@link #Builder}.
	 * </p>
	 * 
	 * @throws UnsupportedOperationException
	 *             always
	 * @deprecated Use {@link MutableDirectedGraph} which allows for removing
	 *             edges or use {@link #Builder}.
	 */
	@Override
	public boolean removeEdgeBetween(Vertex source, Vertex dest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsEdge(Vertex source, Vertex dest) {
		return getEdge(source, dest) != null;
	}

	@Override
	public Edge<Vertex> getEdge(Vertex source, Vertex dest) {
		final ImmutableVertexEdgeList<Vertex> edgeList = edgeLists.get(source);
		for (Edge<Vertex> e : edgeList.getOutEdges()) {
			if (e.getSource().equals(source) && e.getDest().equals(dest)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public int countEdges() {
		int result = 0;
		for (ImmutableVertexEdgeList<Vertex> lst : edgeLists.values()) {
			result += lst.getInEdges().size();
			result += lst.getOutEdges().size();
		}
		return result;
	}

	@Override
	public Iterator<? extends Edge<Vertex>> edgeIterator() {

		return new Iterator<Edge<Vertex>>() {
			UnmodifiableIterator<ImmutableVertexEdgeList<Vertex>> outerIt = edgeLists
					.values().iterator();
			private UnmodifiableIterator<ImmutableEdge<Vertex>> innerIt = null;

			@Override
			public boolean hasNext() {
				if (innerIt != null && innerIt.hasNext()) {
					return true;
				} else {
					while (innerIt == null || !innerIt.hasNext()) {
						if (!outerIt.hasNext()) {
							return false;
						} else {
							innerIt = outerIt.next().getOutEdges().iterator();
							return innerIt.hasNext();
						}
					}
					return innerIt.hasNext();
				}
			}

			@Override
			public Edge<Vertex> next() {
				return innerIt.next();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public int getInDegree(Vertex v) {
		return edgeLists.get(v).getInEdges().size();
	}

	@Override
	public Iterator<? extends Edge<Vertex>> inEdgeIterator(Vertex v) {
		return edgeLists.get(v).getInEdges().iterator();
	}

	@Override
	public int getOutDegree(Vertex v) {
		return edgeLists.get(v).getOutEdges().size();
	}

	@Override
	public Iterator<? extends Edge<Vertex>> outEdgeIterator(Vertex v) {
		return edgeLists.get(v).getOutEdges().iterator();
	}

	@Override
	public Iterator<Vertex> parentVertexIterator(Vertex v) {
		final Iterator<? extends Edge<Vertex>> iter = outEdgeIterator(v);

		return new Iterator<Vertex>() {
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public Vertex next() {
				return iter.next().getDest();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public Iterator<Vertex> childVertexIterator(Vertex v) {
		final Iterator<? extends Edge<Vertex>> iter = inEdgeIterator(v);

		return new Iterator<Vertex>() {
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public Vertex next() {
				return iter.next().getSource();
			}

			@Override
			public void remove() {
			}
		};
	}

	@Override
	public DirectedGraph<Vertex> subGraph(Collection<Vertex> vs) {
		Set<Vertex> argVertexSet = ImmutableSet.copyOf(vs);

		// Create subset of vertices and edges
		Set<Vertex> vertexSubset = new HashSet<Vertex>();
		Iterator<Vertex> vIt = vertexIterator();
		while (vIt.hasNext()) {
			vertexSubset.add(vIt.next());
		}
		Set<ImmutableEdge<Vertex>> edgeSubset = new HashSet<ImmutableEdge<Vertex>>();
		Iterator<? extends Edge<Vertex>> eIt = edgeIterator();
		while (eIt.hasNext()) {
			Edge<Vertex> e = eIt.next();
			if (argVertexSet.contains(e.getSource())
					&& argVertexSet.contains(e.getDest())) {
				edgeSubset.add(
						new ImmutableEdge<Vertex>(e.getSource(), e.getDest()));
			}
		}

		// Construct sub graph
		return construct(vertexSubset, edgeSubset);
	}

	/**
	 * Helper class for iteratively constructing immutable directed graphs.
	 * 
	 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel
	 *         Holtgrewe</a>
	 */
	public static class Builder<Vertex> {

		private List<Vertex> vertices = new ArrayList<Vertex>();
		private List<ImmutableEdge<Vertex>> edges = new ArrayList<ImmutableEdge<Vertex>>();

		/**
		 * Add <code>Vertex</code> to add to the builder.
		 * 
		 * @param v
		 *            <code>Vertex</code> to add to the builder.
		 */
		public void addVertex(Vertex v) {
			vertices.add(v);
		}

		/**
		 * Add {@link ImmutableEdge} to the builder.
		 * 
		 * @param e
		 *            {@link ImmutableEdge} to add to the builder.
		 */
		public void addEdge(ImmutableEdge<Vertex> e) {
			edges.add(e);
		}

		/**
		 * Construct and add new {@link ImmutableEdge} between
		 * <code>source</code> and <code>dest</code>.
		 * 
		 * @param source
		 *            Source vertex for the directed edge
		 * @param dest
		 *            Destination vertex for the directed edge
		 */
		public void addEdge(Vertex source, Vertex dest) {
			edges.add(new ImmutableEdge<Vertex>(source, dest));
		}

		/**
		 * Build and return new {@link ImmutableDirectedGraph}.
		 * 
		 * @param checkConsistency
		 *            whether or not to check consistency of vertex and edge
		 *            list.
		 * @return freshly built {@link ImmutableDirectedGraph}
		 */
		public ImmutableDirectedGraph<Vertex> build(boolean checkConsistency) {
			return ImmutableDirectedGraph.construct(vertices, edges,
					checkConsistency);
		}

		/**
		 * Build and return new {@link ImmutableDirectedGraph}.
		 *
		 * <p>
		 * This is a forward to <code>build(false)</code>.
		 * </p>
		 */
		public ImmutableDirectedGraph<Vertex> build() {
			return build(false);
		}

	}

}
