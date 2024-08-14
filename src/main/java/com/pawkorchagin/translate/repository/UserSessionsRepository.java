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
 * Repository
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserSessionsRepository {
    @Autowired
    private final RepositoryConfig cfg;

    private Connection connection;

    private void init() throws IOException, SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            Class.forName(cfg.getDriverClassName());
            connection = DriverManager.getConnection(cfg.getUrl(), cfg.getUsername(), cfg.getPassword());
            this.executeInitialScript(cfg.getScriptFilePath());
        }
    }

    private void executeInitialScript(String scriptFilePath) throws IOException, SQLException {
        String script = new String(Files.readAllBytes(Paths.get(scriptFilePath)));
        Statement stmt = connection.createStatement();
        for (String command : script.split(";")) {
            if (!command.trim().isEmpty()) {
                stmt.executeUpdate(command + ";");
            }
        }
    }

    public void addUserSession(String ip) throws IOException, SQLException, ClassNotFoundException {
        this.init();
        try (java.sql.PreparedStatement st = connection.prepareStatement("INSERT INTO sessions(ip) VALUES(?)")) {
            st.setString(1, ip);
            st.executeUpdate();
        }
        log.info("add user session: {}", ip);
    }

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
