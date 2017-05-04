package de.ontologizer.immutable.graph;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import sonumina.collections.IntMapper;

/**
 * Implementation of {@link FastDirectedGraphView} for {@link DirectedGraph}.
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 * @author Sebastian Bauer
 * @author Sebastian Koehler
 */
public final class ImmutableFastDirectedGraphView<Vertex>
		implements
			FastDirectedGraphView<Vertex> {

	private static final long serialVersionUID = 1L;

	/**
	 * Underlying {@link DirectedGraph}.
	 */
	private final DirectedGraph<Vertex> graph;

	/**
	 * {@link IntMapper} used for mapping from <code>Vertex</code> to
	 * <code>int</code>
	 */
	private final IntMapper<Vertex> mapper;

	/**
	 * Contains all the ancestors of the terms (and the terms itself). Note that
	 * the array of ancestors is sorted.
	 */
	public final ImmutableList<ImmutableList<Integer>> vertexAncestors;

	/** Contains the parents of the terms */
	private final ImmutableList<ImmutableList<Integer>> vertexParents;

	/** Contains the children of the term */
	private final ImmutableList<ImmutableList<Integer>> vertexChildren;

	/**
	 * Contains the descendants of the (i.e., children, grand-children, etc. and
	 * the term itself). Note that the array of descendants is sorted.
	 */
	public ImmutableList<ImmutableList<Integer>> vertexDescendants;

	/**
	 * Construct new {@link ImmutableFastDirectedGraphView} from
	 * {@link DirectedGraph}.
	 *
	 * @return {@link ImmutableFastDirectedGraphView} after construction
	 */
	public static <Vertex> ImmutableFastDirectedGraphView<Vertex> construct(
			DirectedGraph<Vertex> g) {
		return null;  // TODO: continue here
	}

	private ImmutableFastDirectedGraphView(DirectedGraph<Vertex> graph,
			Collection<? extends Collection<Integer>> vertexAncestors,
			Collection<? extends Collection<Integer>> vertexParents,
			Collection<? extends Collection<Integer>> vertexChildren,
			Collection<? extends Collection<Integer>> vertexDescendants) {
		this.graph = graph;
		this.mapper = IntMapper.create(graph.getVertices(),
				graph.countVertices());

		ImmutableList.Builder<ImmutableList<Integer>> vertexAncestorBuilder = ImmutableList
				.builder();
		for (Collection<Integer> c : vertexAncestors) {
			vertexAncestorBuilder.add(ImmutableList.copyOf(c));
		}
		this.vertexAncestors = vertexAncestorBuilder.build();

		ImmutableList.Builder<ImmutableList<Integer>> vertexParentBuilder = ImmutableList
				.builder();
		for (Collection<Integer> c : vertexParents) {
			vertexParentBuilder.add(ImmutableList.copyOf(c));
		}
		this.vertexParents = vertexParentBuilder.build();

		ImmutableList.Builder<ImmutableList<Integer>> vertexChildBuilder = ImmutableList
				.builder();
		for (Collection<Integer> c : vertexChildren) {
			vertexChildBuilder.add(ImmutableList.copyOf(c));
		}
		this.vertexChildren = vertexChildBuilder.build();

		ImmutableList.Builder<ImmutableList<Integer>> vertexDescendantBuilder = ImmutableList
				.builder();
		for (Collection<Integer> c : vertexDescendants) {
			vertexDescendantBuilder.add(ImmutableList.copyOf(c));
		}
		this.vertexDescendants = vertexDescendantBuilder.build();
	}

	@Override
	public DirectedGraph<Vertex> getGraph() {
		return graph;
	}

	@Override
	public int countVertices() {
		return graph.countVertices();
	}

	@Override
	public int countEdges() {
		return graph.countEdges();
	}

	@Override
	public Vertex getVertex(int index) {
		return mapper.get(index);
	}

	@Override
	public int getVertexIndex(Vertex v) {
		return mapper.getIndex(v);
	}

	@Override
	public List<Integer> getVertexIndices(Collection<Vertex> vertices) {
		ImmutableList.Builder<Integer> builder = ImmutableList.builder();
		for (Vertex v : vertices) {
			builder.add(mapper.getIndex(v));
		}
		return builder.build();
	}

	@Override
	public boolean isAncestor(Vertex u, Vertex v) {
		if (!containsVertex(u) || !containsVertex(v)) {
			return false;
		}
		ImmutableList<Integer> descs = vertexAncestors.get(getVertexIndex(u));
		return descs.contains(getVertexIndex(v));
	}

	@Override
	public boolean isDescendant(Vertex u, Vertex v) {
		if (!containsVertex(u) || !containsVertex(v)) {
			return false;
		}
		ImmutableList<Integer> descs = vertexDescendants.get(getVertexIndex(u));
		return descs.contains(getVertexIndex(v));
	}

	@Override
	public List<Vertex> getDescendants(Vertex v) {
		getVertexIndex(v);
		ImmutableList.Builder<Vertex> builder = ImmutableList.builder();
		for (int childIndex : vertexDescendants.get(getVertexIndex(v))) {
			builder.add(getVertex(childIndex));
		}
		return builder.build();
	}

	@Override
	public List<Vertex> getAncestors(Vertex v) {
		getVertexIndex(v);
		ImmutableList.Builder<Vertex> builder = ImmutableList.builder();
		for (int childIndex : vertexAncestors.get(getVertexIndex(v))) {
			builder.add(getVertex(childIndex));
		}
		return builder.build();
	}

	@Override
	public boolean containsVertex(Vertex v) {
		return graph.containsVertex(v);
	}

	@Override
	public List<Vertex> getParents(Vertex v) {
		getVertexIndex(v);
		ImmutableList.Builder<Vertex> builder = ImmutableList.builder();
		for (int childIndex : vertexParents.get(getVertexIndex(v))) {
			builder.add(getVertex(childIndex));
		}
		return builder.build();
	}

	@Override
	public List<Vertex> getChildren(Vertex v) {
		ImmutableList.Builder<Vertex> builder = ImmutableList.builder();
		for (int childIndex : vertexChildren.get(getVertexIndex(v))) {
			builder.add(getVertex(childIndex));
		}
		return builder.build();
	}

}
