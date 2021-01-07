package com.epam.esm.giftcertificatemodule4.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class AbstractIntegrationTest {

    @Autowired
    private DataSource dataSource;

    public Connection executeSqlStartScript() throws SQLException {
        Connection connection = dataSource.getConnection();
        ScriptUtils.executeSqlScript(connection, new ClassPathResource("/create_schema.sql"));
        ScriptUtils.executeSqlScript(connection, new ClassPathResource("/import_data.sql"));
        return connection;
    }

    public void executeSqlEndScript(Connection connection) throws SQLException {
        ScriptUtils.executeSqlScript(connection, new ClassPathResource("/drop_schema.sql"));
        connection.close();
    }
}
