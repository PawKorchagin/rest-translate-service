package com.pawkorchagin.translate.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pawkorchagin.translate.repository.cfg.RepositoryConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Repository for managing user sessions in the database. This class handles 
 * the initialization of the database connection, execution of SQL scripts, 
 * and performing CRUD operations related to user sessions.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserSessionsRepository {

    @Autowired
    private final RepositoryConfig cfg;

    private Connection connection;

    /**
     * Initializes the database connection if it's not already initialized or if 
     * the connection is closed. This method loads the JDBC driver, connects to the 
     * database using the credentials provided in the configuration, and executes 
     * an initial SQL script to set up the database.
     *
     * @throws IOException              if there is an issue reading the initial script file
     * @throws SQLException             if there is an error connecting to the database or executing SQL commands
     * @throws ClassNotFoundException   if the JDBC driver class is not found
     */
    private void init() throws IOException, SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            Class.forName(cfg.getDriverClassName());
            connection = DriverManager.getConnection(cfg.getUrl(), cfg.getUsername(), cfg.getPassword());
            this.executeInitialScript(cfg.getScriptFilePath());
        }
    }

    /**
     * Executes an initial SQL script provided as a file path. The script is expected 
     * to contain multiple SQL commands separated by semicolons. Each command is executed 
     * sequentially to set up the database schema or perform initial data loading.
     *
     * @param scriptFilePath the file path to the SQL script to be executed
     * @throws IOException  if there is an issue reading the script file
     * @throws SQLException if there is an error executing the SQL commands
     */
    private void executeInitialScript(String scriptFilePath) throws IOException, SQLException {
        String script = new String(Files.readAllBytes(Paths.get(scriptFilePath)));
        Statement stmt = connection.createStatement();
        for (String command : script.split(";")) {
            if (!command.trim().isEmpty()) {
                stmt.executeUpdate(command + ";");
            }
        }
    }

    /**
     * Adds a new user session to the database. This method inserts a new record 
     * into the `sessions` table with the provided IP address. The database 
     * connection is initialized if not already active.
     *
     * @param ip the IP address of the user session to be recorded
     * @throws IOException              if there is an issue initializing the database connection
     * @throws SQLException             if there is an error executing the SQL insert command
     * @throws ClassNotFoundException   if the JDBC driver class is not found
     */
    public void addUserSession(String ip) throws IOException, SQLException, ClassNotFoundException {
        this.init();
        try (java.sql.PreparedStatement st = connection.prepareStatement("INSERT INTO sessions(ip) VALUES(?)")) {
            st.setString(1, ip);
            st.executeUpdate();
        }
        log.info("Added user session: {}", ip);
    }

    /**
     * Retrieves all user sessions from the database. This method executes a SQL 
     * `SELECT` query to fetch all records from the `sessions` table and returns 
     * them as a linked list of IP addresses.
     *
     * @return a {@link LinkedList} containing the IP addresses of all recorded user sessions
     * @throws SQLException if there is an error executing the SQL select command
     */
    public LinkedList<String> selectAll() throws SQLException {
        var st = connection.createStatement();
    
        ResultSet rs = st.executeQuery("SELECT * FROM sessions");

        LinkedList<String> list = new LinkedList<>();

        while (rs.next()) {
            list.add(rs.getString("ip"));
        }

        return list;
    }
}
