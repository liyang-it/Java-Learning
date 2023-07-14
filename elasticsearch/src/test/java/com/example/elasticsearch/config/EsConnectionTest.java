package com.example.elasticsearch.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("EsConnection")
class EsConnectionTest {
	
	@Test
	@DisplayName("Should return null when no address is set")
	void getAddressReturnsNullWhenNoAddressIsSet() {
		EsConnection esConnection = new EsConnection();
		assertNull(esConnection.getAddress());
	}
	
	@Test
	@DisplayName("Should return the correct address when getAddress is called")
	void getAddressReturnsCorrectAddress() {
		EsConnection esConnection = new EsConnection();
		esConnection.setAddress("http://localhost:9200");
		
		assertEquals("http://localhost:9200", esConnection.getAddress());
	}
	
}
