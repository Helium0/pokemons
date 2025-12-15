package com.example.pokemons.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test-containers.yaml")
@SpringBootTest
public class DataBaseIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void databaseConnectionTest() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection);
            assertFalse(connection.isClosed());
            assertTrue(connection.isValid(5));
        }
    }
}
