package de.ontologizer.immutable.graph.algorithms;

/**
 * Raised when {@link DirectedGraph} is not a DAG.
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public class GraphNotDagException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GraphNotDagException() {
		super();
	}

	public GraphNotDagException(String message) {
		super(message);
	}

}
