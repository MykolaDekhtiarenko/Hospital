package com.mdekhtiarenko.models.dao.impl;

import com.mdekhtiarenko.models.dao.*;
import com.mdekhtiarenko.models.dao.utils.Config;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mykola.dekhtiarenko on 09.09.17.
 */
public class JDBCDaoFactory extends DaoFactory {

    Connection getConnection(){
        Config config = Config.getInstance();

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(config.getUrl());
        ds.setPassword(config.getPass());
        ds.setUsername(config.getUser());

        Connection connection;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    @Override
    public AssignmentDao createAssignmentDAO() {
        return new AssignmentDaoImpl(getConnection());
    }

    @Override
    public DiagnoseDao createDiagnoseDAO() {
        return new DiagnoseDaoImpl(getConnection());
    }

    @Override
    public UserDao createUserDAO() {
        return new UserDaoImpl(getConnection());
    }

    @Override
    public TreatmentHistoryDao createTreatmentHistoryDAO() {
        return new TreatmentHistoryDaoImpl(getConnection());
    }
}
