package ontologizer.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Helper class for retrieving version information.
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public class VersionInfo {

	/**
	 * Return the version string.
	 *
	 * <p>
	 * This works both from within IDEs such as Eclipse as well as from the
	 * built JAR file. The used trick is a "project.properties" with the version
	 * information which allows access to the version information through both
	 * ways.
	 * </p>
	 *
	 * @return <code>String</code> with the version information, includes
	 *         <code>-SNAPSHOT</code> suffix on snapshot builds.
	 */
	public static String getVersion() {
		final Properties properties = new Properties();
		try {
			properties.load(VersionInfo.class
					.getResourceAsStream("/project.properties"));
		} catch (IOException e) {
			throw new RuntimeException(
					"Could not load project.properties for obtaining version",
					e);
		}
		return properties.getProperty("version");
	}

}
