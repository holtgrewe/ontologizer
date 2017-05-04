package de.ontologizer.immutable.graph;

/**
 * Exception raised when list of vertices and edges are incompatible on graph construction.
 * 
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public class VerticesAndEdgesIncompatibleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VerticesAndEdgesIncompatibleException(String message) {
		super(message);
	}

}
