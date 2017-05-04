package de.ontologizer.immutable.graph;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Interface for directed graph classes.
 * 
 * <p>
 * All graphs in OntologizerLib are simple, having two edges in the same direction between two
 * vertices are not allowed.
 * </p>
 * 
 * <p>
 * Vertices must implement <code>equals()</code> and <code>hashValue()</code>. Vertices with the
 * same value (by <code>equals</code>) are not allowed.
 * </p>
 * 
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public interface DirectedGraph<Vertex>
		extends Serializable, ShallowCopyable<DirectedGraph<Vertex>> {

	/**
	 * Insert <code>Vertex</code> into graph.
	 * 
	 * @param v
	 *            <code>Vertex</code> to insert
	 */
	public void addVertex(Vertex v);

	/**
	 * Remove <code>Vertex</code> from graph.
	 * 
	 * @param v
	 *            <code>Vertex</code> to remove
	 */
	public void removeVertex(Vertex v);

	/**
	 * Query whether the given vertex is contained in the graph.
	 * 
	 * @param v
	 *            <code>Vertex</code> to query for
	 * @return whether <code>v</code> is contained in the graph.
	 */
	public boolean containsVertex(Vertex v);

	/**
	 * Return number of vertices in the graph.
	 * 
	 * @return Number of vertices in the graph.
	 */
	public int countVertices();

	/**
	 * Build and return iterator over all vertices in the graph.
	 * 
	 * @return {@link Iterator} over all vertices.
	 */
	public Iterator<Vertex> vertexIterator();

	/**
	 * Add {@link Edge} to the graph.
	 * 
	 * @param edge
	 *            {@link Edge} to add to the graph.
	 */
	public void addEdge(Edge<Vertex> edge);

	/**
	 * Remove the edge between <code>source</code> and <code>dest</code>.
	 * 
	 * @param source
	 *            source <code>Vertex</code> of edge to remove
	 * @param dest
	 *            destination <code>Vertex</code> of edge to remove
	 * @return <code>true</code> if the edge could be found and was removed, <code>false</code>
	 *         otherwise
	 */
	public boolean removeEdgeBetween(Vertex source, Vertex dest);

	/**
	 * Query for an edge being in in the graph.
	 * 
	 * @param source
	 *            source <code>Vertex</code> of edge to query for
	 * @param dest
	 *            destination <code>Vertex</code> of edge to query for
	 * @return <code>true</code> if the edge could be found, <code>false</code> otherwise
	 */
	public boolean containsEdge(Vertex source, Vertex dest);

	/**
	 * Query for the edge between <code>source</code> and </code> dest.
	 * 
	 * @param source
	 *            source <code>Vertex</code> of edge to query for
	 * @param dest
	 *            destination <code>Vertex</code> of the edge to query for
	 * @return {@link Edge} between <code>source</code> and <code>dest</code>, <code>null</code> if
	 *         no such edge could be found
	 */
	public Edge<Vertex> getEdge(Vertex source, Vertex dest);

	/**
	 * Query for number of edges.
	 * 
	 * @return number of edges
	 */
	public int countEdges();

	/**
	 * Build and return {@link Iterator} over all edges.
	 * 
	 * @return {@link Iterator} over all edges in the graph.
	 */
	public Iterator<? extends Edge<Vertex>> edgeIterator();

	/**
	 * Query for in-degree of <code>Vertex</code>.
	 * 
	 * @param v
	 *            <code>Vertex</code> to use for the query.
	 * @return number of incoming edges into <code>v</code.
	 */
	public int getInDegree(Vertex v);

	/**
	 * Construct and return in-edge iterator.
	 * 
	 * @return In-edge iterator for <code>v</code>
	 */
	public Iterator<? extends Edge<Vertex>> inEdgeIterator(Vertex v);

	/**
	 * Query for out-degree of <code>Vertex</code>.
	 * 
	 * @param v
	 *            <code>Vertex</code> to use for the query.
	 * @return number of outgoing edges from <code>v</code.
	 */
	public int getOutDegree(Vertex v);

	/**
	 * Construct and return out-edge iterator.
	 * 
	 * @return Out-edge iterator for <code>v</code>
	 */
	public Iterator<? extends Edge<Vertex>> outEdgeIterator(Vertex v);

	/**
	 * Build and return {@link Iterator} of vertices connected by incoming edges.
	 * 
	 * @param v
	 *            <code>Vertex</code> to use for the query.
	 * @return {@link Iterator} over all vertices connected by incoming edges.
	 */
	public Iterator<Vertex> parentVertexIterator(Vertex v);

	/**
	 * Build and return {@link Iterator} of vertices connected by outgoing edges.
	 * 
	 * @param v
	 *            <code>Vertex</code> to use for the query.
	 * @return {@link Iterator} over all vertices connected by outgoing edges.
	 */
	public Iterator<Vertex> childVertexIterator(Vertex v);

	/**
	 * Build and return <code>DirectedGraph</code> by restricting the vertex set to
	 * <code>vertices</code>.
	 * 
	 * @param vertices
	 *            {@link Collection} of <code>Vertex</code> objects to limit to.
	 * @return <code>DirectedGraph</code> induced by restricting the vertex set to
	 *         <code>vertices</code>.
	 */
	public DirectedGraph<Vertex> subGraph(Collection<Vertex> vertices);

}
