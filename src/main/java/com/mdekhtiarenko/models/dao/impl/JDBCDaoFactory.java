package com.mdekhtiarenko.models.dao.impl;

import com.mdekhtiarenko.models.dao.*;
import com.mdekhtiarenko.models.dao.utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mykola.dekhtiarenko on 09.09.17.
 */
public class JDBCDaoFactory extends DaoFactory {

    Connection getConnection(){
        Config config = Config.getInstance();
        Connection connection;
        try {
            connection = DriverManager.getConnection(config.getUrl() , config.getUser(), config.getPass());
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
