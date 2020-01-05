/**
 * 
 */
package net.baroi.util.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Aldrin Baroi
 *
 */
public class ExceptionUtil {

	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
