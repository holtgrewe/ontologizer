package de.ontologizer.immutable.graph;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Wrapper class that allows important operations on ontology graphs to be fast.
 *
 * <p>
 * Given the strong assumption of that <code>Vertex</code> can be directly
 * mapped to an integer (which is the case for ontologies), certain
 * precomputations can be performed that allow to speed-up many ontology-related
 * graph queries.
 * </p>
 *
 * <p>
 * Care should be taken to not waste memory here but in general, performance
 * beats memory for the implementation of this interface.
 * </p>
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 * @author Sebastian Bauer
 * @author Sebastian Koehler
 */
public interface FastDirectedGraphView<Vertex> extends Serializable {

	/**
	 * Query for underlying {@link DirectedGraph}.
	 *
	 * @return {@link DirectedGraph} used for precomputation
	 */
	public DirectedGraph<Vertex> getGraph();

	/**
	 * Query for number of vertices in the graph.
	 *
	 * @return number of vertices
	 */
	public int countVertices();

	/**
	 * Query for number of edges in the graph.
	 *
	 * @return number of edges
	 */
	public int countEdges();

	/**
	 * Query <code>Vertex</code> from its numeric <code>index</code>.
	 *
	 * @param index
	 *            of vertex to use for query
	 * @return resulting <code>Vertex</code>
	 */
	public Vertex getVertex(int index);

	/**
	 * Query for vertex index by <code>Vertex</code>
	 *
	 * @param v
	 *            <code>Vertex</code> to obtain the index for
	 * @return numeric index for <code>Vertex</code>
	 */
	public int getVertexIndex(Vertex v);

	/**
	 * Query for index given the collection of <code>Vertex</code> objects.
	 *
	 * @param vertices
	 *            {@link Collection} of <code>Vertex</code> objects to query for
	 * @return resulting list of numeric vertex representation
	 */
	public List<Integer> getVertexIndices(Collection<Vertex> vertices);

	/**
	 * Query for whether one node is the ancestor of another
	 *
	 * @param u
	 *            first node
	 * @param v
	 *            second node
	 * @return whether <code>u</code> is the ancestor of <code>v</code>
	 */
	public boolean isAncestor(Vertex u, Vertex v);

	/**
	 * Query for whether one node is the descendant of another
	 *
	 * @param u
	 *            first node
	 * @param v
	 *            second node
	 * @return whether <code>u</code> is the descendant of <code>v</code>
	 */
	public boolean isDescendant(Vertex u, Vertex v);

	/**
	 * Query for all descendants of <code>v</code>
	 *
	 * @param v
	 *            <code>Vertex</code> to use for the query
	 * @return list of descendant vertices for <code>v</code>
	 */
	public List<Vertex> getDescendants(Vertex v);

	/**
	 * Query for all ancestor of <code>v</code>
	 *
	 * @param v
	 *            <code>Vertex</code> to use for the query
	 * @return list of ancestor vertices for <code>v</code>
	 */
	public List<Vertex> getAncestors(Vertex v);

	/**
	 * Whether or not the underlying graph contains vertex <code>v</code>
	 *
	 * @param v
	 *            <code>Vertex</code> to query with
	 * @return whether or not the underlying graph contains <code>v</code>
	 */
	public boolean containsVertex(Vertex v);

	/**
	 * Parents of <code>v</code> up to the root
	 *
	 * @param v
	 *            <code>Vertex</code> to use for querying
	 * @return {@link List} of <code>Vertex</code> objects of ancestors of
	 *         <code>v</code, up to the root, excluding <code>v</code.
	 */
	public List<Vertex> getParents(Vertex v);

	/**
	 * Children of <code>v</code>.
	 *
	 * @param v
	 *            <code>Vertex</code> to use for querying
	 * @return {@link List} of <code>Vertex</code> objects that are children of
	 *         <code>v</code>, excluding <code>v</code> itself
	 */
	public List<Vertex> getChildren(Vertex v);

}
