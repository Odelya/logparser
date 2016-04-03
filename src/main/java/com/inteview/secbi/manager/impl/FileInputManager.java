/**
 * 
 */
package com.inteview.secbi.manager.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.stereotype.Service;

import com.inteview.secbi.manager.InputManager;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

/**
 * This manager handles file input - converts it to hashmap for future sorting
 * @author Odelya Holiday
 *
 *         Apr 3, 2016
 */
@Service
public class FileInputManager implements InputManager {

	private static final String CS_HOST = "cs-host";
	private static final String COMMENT_START = "Fields";

	/**
	 * Returns host to map. The method uses MutableInt and not Int to improve hashmap get and increase performance 
	 */
	public HashMap<String, MutableInt> createHostToCountMap(Object fileName) throws IOException {

		if (!(fileName instanceof String))
			throw new RuntimeException("Input should be string"); // TODO handle
		HashMap<String, MutableInt> hostToCountMap = null;

		hostToCountMap = createHostToCountMap((String) fileName, findCsHostColumnIndexInFile((String) fileName));

		return hostToCountMap;
	}

	/**
	 * Creates the host to count map. It retrieves the cs-host column index to skip unrequired column processing
	 * e.g. 
	 * Host:acemoglusucuklari.com.tr, Count:1
	 * Host:ox-d.knockbook.me, Count:1
     * Host:news.bbcimg.co.uk, Count:1
	 * @param fileName
	 * @param csHostLocation
	 * @return
	 */
	private HashMap<String, MutableInt> createHostToCountMap(String fileName, int csHostLocation) {
		HashMap<String, MutableInt> hostToCount = new HashMap<String, MutableInt>();
		CsvParserSettings settings = new CsvParserSettings();
		settings.selectIndexes(csHostLocation);
		settings.getFormat().setDelimiter(' ');

		CsvParser parser = new CsvParser(settings);
		String[] row;
		parser.beginParsing(InputManager.class.getResourceAsStream(fileName));
		while ((row = parser.parseNext()) != null) {
			if (row.length > 0) {
				MutableInt value = hostToCount.get(row[0]);
				if (value == null) {
					value = new MutableInt();
					value.increment();
					hostToCount.put(row[0], value);
				} else {
					value.increment();
				}
			}
		}
		return hostToCount;
	}

	/**
	 * Finds the cs-host location in the comment. may be different from file to file
	 * @param row
	 * @return
	 */
	private int findCsHostColumnIndexInComment(String[] row) {
		for (int i = 0; i < row.length; i++)
			if (row[i].equals(CS_HOST))
				return i;
		return -1;
	}

	/**
	 * this method finds the index column of cs-host (to skip it in parsing)
	 * @param fileName
	 * @return
	 */
	private int findCsHostColumnIndexInFile(String fileName) {
		int csHostLocation = 0;
		CsvParserSettings settings = new CsvParserSettings();
		settings.setCommentCollectionEnabled(true);
		CsvParser parser = new CsvParser(settings);

		parser.beginParsing(InputManager.class.getResourceAsStream(fileName));
		parser.parseNext();
		
		Collection<String> comments = parser.getContext().comments().values();
		parser.stopParsing();
		
		if (!comments.isEmpty()) {
			for (String comment : comments) {
				if (comment.startsWith(COMMENT_START)) {
					csHostLocation = findCsHostColumnIndexInComment(comment.split(" "));
					if (csHostLocation == -1)
						throw new RuntimeException("Missing cs-host in file"); // TODO
																				// handle
																				// exceptions
					return csHostLocation;
				}
			}

		}
		// didn't find fields element
		throw new RuntimeException("Missing Fields in file"); // TODO handle
																// exceptions
	}
}
