/**
 * 
 */
package com.inteview.secbi.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inteview.secbi.constant.InputSource;
import com.inteview.secbi.factory.InputFactory;
import com.inteview.secbi.manager.LogManager;

/**
 * @author Odelya Holiday
 *
 *         Apr 3, 2016 11:56:22 PM
 */
@Service
public class HostCounterLogManager implements LogManager {

	@Autowired
	InputFactory inputFactory;

	public void parseLog(InputSource type, String name) throws IOException {
		HashMap<String, MutableInt> hostToCount = inputFactory.obtainInputManager(type).createHostToCountMap(name);

		List<Entry<String, MutableInt>> sortedValues = sortValue(hostToCount);
		for (int i = 0; i < sortedValues.size(); i++) {
			Entry<String, MutableInt> element = sortedValues.get(i);
			System.out.println("Host: " + element.getKey() + ", Count:" + element.getValue());
		}

	}

	/**
	 * Sorting the hashmap by value desc order
	 * 
	 * @param map
	 * @return
	 */
	private static List<Entry<String, MutableInt>> sortValue(HashMap<String, MutableInt> map) {
		List<Entry<String, MutableInt>> entries = new ArrayList<Entry<String, MutableInt>>(map.entrySet());
		Collections.sort(entries, new Comparator<Entry<String, MutableInt>>() {
			public int compare(Entry<String, MutableInt> left, Entry<String, MutableInt> right) {
				return right.getValue().compareTo(left.getValue());
			}
		});
		return entries;
	}

}
