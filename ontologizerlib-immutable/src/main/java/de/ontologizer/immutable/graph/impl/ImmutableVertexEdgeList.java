package de.ontologizer.immutable.graph.impl;

import com.google.common.collect.ImmutableList;
import de.ontologizer.immutable.graph.ImmutableEdge;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation detail for storing in- and out-edge lists.
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public final class ImmutableVertexEdgeList<Vertex> {

	private final ImmutableList<ImmutableEdge<Vertex>> inEdges;
	private final ImmutableList<ImmutableEdge<Vertex>> outEdges;
	
	public static <Vertex> Builder<Vertex> builder() {
		return new Builder<Vertex>();
	}

	public ImmutableVertexEdgeList(Collection<ImmutableEdge<Vertex>> inEdges,
			Collection<ImmutableEdge<Vertex>> outEdges) {
		this.inEdges = ImmutableList.copyOf(inEdges);
		this.outEdges = ImmutableList.copyOf(outEdges);
	}

	public ImmutableList<ImmutableEdge<Vertex>> getInEdges() {
		return inEdges;
	}

	public ImmutableList<ImmutableEdge<Vertex>> getOutEdges() {
		return outEdges;
	}

	/**
	 * Internal helper for construction of VertexEdgeList objects.
	 *
	 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
	 */
	public static final class Builder<Vertex> {
		private final List<ImmutableEdge<Vertex>> inEdges = new ArrayList<ImmutableEdge<Vertex>>();
		private final List<ImmutableEdge<Vertex>> outEdges = new ArrayList<ImmutableEdge<Vertex>>();

		public void addInEdge(ImmutableEdge<Vertex> edge) {
			inEdges.add(edge);
		}

		public void addOutEdge(ImmutableEdge<Vertex> edge) {
			outEdges.add(edge);
		}

		public ImmutableVertexEdgeList<Vertex> build() {
			return new ImmutableVertexEdgeList<Vertex>(inEdges, outEdges);
		}
	}

}
