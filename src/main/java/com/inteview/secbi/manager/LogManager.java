/**
 * 
 */
package com.inteview.secbi.manager;

import java.io.IOException;

import com.inteview.secbi.constant.InputSource;

/**
 * @author Odelya Holiday
 *
 *         Apr 3, 2016
 */
public interface LogManager {

	public void parseLog(InputSource type, String name) throws IOException;

}
