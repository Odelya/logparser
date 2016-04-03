/**
 * 
 */
package com.inteview.secbi.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inteview.secbi.constant.InputSource;
import com.inteview.secbi.manager.InputManager;

/**
 * This factory returns the manager for the input format to handle the input
 * @author Odelya Holiday
 *
 * Apr 3, 2016 
 */
@Component
public class InputFactory {

	@Autowired
	InputManager fileInputManager;
	
	@Autowired
	InputManager httpInputManager;

	public InputManager obtainInputManager(InputSource source) {
		switch (source) {
		case HTTP:
			return httpInputManager;
		case FILE:
			return fileInputManager;
		default:
			throw new IllegalArgumentException(String.format("Input source %s not supported", source));
		}
	}
}
