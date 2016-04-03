package com.inteview.secbi.manager;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang3.mutable.MutableInt;

/**
 * Manager interface to handle different input sources
 * @author Odelya Holiday
 *
 * Apr 3, 2016 6:18:00 PM
 */
public interface InputManager {

	public HashMap<String, MutableInt> createHostToCountMap(Object input) throws IOException;

}
