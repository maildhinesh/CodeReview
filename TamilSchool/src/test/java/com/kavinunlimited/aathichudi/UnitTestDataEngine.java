package com.kavinunlimited.aathichudi;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UnitTestDataEngine {
	
	
	public <T> T getUnitTestData(String fileName, Class<T> type) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ClassLoader classLoader = this.getClass().getClassLoader();
		T object = objectMapper.readValue(new File(classLoader.getResource(fileName).getFile()), type);
		return object;
	}

}
