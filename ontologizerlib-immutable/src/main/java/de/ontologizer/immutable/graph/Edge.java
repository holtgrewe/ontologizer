package de.ontologizer.immutable.graph;

import java.io.Serializable;

/**
 * Interface that graph edge classes should implement.
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public interface Edge<Vertex> extends Serializable {

	/**
	 * Query for the destination vertex.
	 *
	 * @return <code>Vertex</code> at the tip of the edge (if directed).
	 */
	public Vertex getDest();

	/**
	 * Set the destination vertex.
	 *
	 * @param dest
	 *            <code>Vertex</code> to set the vertex at the tip of the edge to.
	 */
	public void setDest(Vertex dest);

	/**
	 * Query for the source vertex.
	 *
	 * @return <code>Vertex</code> at the foot of the edge (if directed).
	 */
	public Vertex getSource();

	/**
	 * Set the source vertex.
	 *
	 * @param source
	 *            <code>Vertex</code> to set the vertex at the foot of the edge to.
	 */
	public void setSource(Vertex source);

	/**
	 * Query for the edge weight.
	 * 
	 * @return weight of the edge
	 */
	public int getWeight();

	/**
	 * Set the edge weight
	 * 
	 * @param weight
	 *            of the edge to set.
	 */
	public void setWeight(int weight);

}
