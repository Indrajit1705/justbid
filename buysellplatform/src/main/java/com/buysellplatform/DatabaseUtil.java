package com.buysellplatform;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseUtil {
    private static final Logger logger = Logger.getLogger(DatabaseUtil.class.getName());
    private static HikariDataSource dataSource;

    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find db.properties");
            }

            Properties prop = new Properties();
            prop.load(input);

            String driverClassName = prop.getProperty("db.driver");
            if (driverClassName == null) {
                throw new RuntimeException("Driver class name not found in properties");
            }

            // Load the JDBC driver class
            try {
                Class.forName(driverClassName);
                logger.info("JDBC Driver loaded successfully.");
            } catch (ClassNotFoundException e) {
                logger.severe("JDBC Driver class not found: " + e.getMessage());
                throw new RuntimeException("Failed to load JDBC Driver", e);
            }

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(prop.getProperty("db.url"));
            config.setUsername(prop.getProperty("db.username"));
            config.setPassword(prop.getProperty("db.password"));
            config.setDriverClassName(driverClassName);

            // Optional HikariCP settings
            config.setMaximumPoolSize(10); // Set max pool size
            config.setMinimumIdle(2); // Set min idle connections
            config.setIdleTimeout(30000); // Set idle timeout (30 seconds)
            config.setConnectionTimeout(30000); // Set connection timeout (30 seconds)
            config.setMaxLifetime(1800000); // Set max lifetime (30 minutes)

            dataSource = new HikariDataSource(config);
            logger.info("HikariCP DataSource initialized successfully.");

        } catch (Exception e) {
            logger.severe("Failed to initialize HikariCP DataSource: " + e.getMessage());
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not initialized");
        }
        Connection connection = dataSource.getConnection();
        logger.info("Connection obtained: " + connection);
        return connection;
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
            logger.info("HikariCP DataSource closed.");
        }
    }
}
