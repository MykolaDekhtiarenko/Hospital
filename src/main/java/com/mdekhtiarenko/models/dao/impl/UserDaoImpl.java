package com.mdekhtiarenko.models.dao.impl;

import com.mdekhtiarenko.models.dao.DiagnoseDao;
import com.mdekhtiarenko.models.dao.UserDao;
import com.mdekhtiarenko.models.dao.TreatmentHistoryDao;
import com.mdekhtiarenko.models.dao.utils.EntityRetriever;
import com.mdekhtiarenko.models.dao.utils.SecurityUtils;
import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.entities.TreatmentHistory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mykola.dekhtiarenko on 28.08.17.
 */
public class UserDaoImpl implements UserDao {
    private static final String SELECT_ALL = "SELECT * FROM USER";
    private static final String SELECT_BY_ID = "SELECT * FROM USER WHERE id = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM USER WHERE email = ?";
    private static final String CREATE = "INSERT INTO USER" +
            " (email, password, firstName, lastName, phone, birthday, role)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE USER SET " +
            "email = ?, password = ?, firstName = ?, lastName = ?, phone = ?, birthday = ?, role = ?" +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM USER WHERE id = ?";
    private static final String SELECT_TREATMENT_HISTORIES = "SELECT * FROM TREATMENTHISTORY WHERE User_id = ?";
    private static final String SELECT_DIAGNOSES = "SELECT * FROM Diagnose WHERE Creator_id = ?";
    private static final String SELECT_SICK = "SELECT DISTINCT User.id, email, password, firstName, lastName, phone, birthday, role\n" +
            "FROM USER JOIN TreatmentHistory ON User.id = TreatmentHistory.User_id\n" +
            "AND TreatmentHistory.conclusion IS NULL";



    private Connection connection;
    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public List<User> findAll() {
        List<User> all = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                all.add(EntityRetriever.retrievePatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }

    public User findById(Integer id) {
        User patient = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                patient = EntityRetriever.retrievePatient(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    public boolean create(User patient) {
        try (PreparedStatement statement
                     = connection.prepareStatement(CREATE)) {
            statement.setString(1, patient.getEmail());
            statement.setString(2, SecurityUtils.decode(patient.getPassword()));
            statement.setString(3, patient.getFirstName());
            statement.setString(4, patient.getLastName());
            statement.setString(5, patient.getPhone());
            statement.setDate(6, patient.getBirthday());
            statement.setString(7, patient.getRole().name());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public User update(User infoForUpdate) {
        User current = findById(infoForUpdate.getId());
        try (PreparedStatement statement
                     = connection.prepareStatement(UPDATE)) {

            if (infoForUpdate.getEmail() != null)
                statement.setString(1, infoForUpdate.getEmail());
            else
                statement.setString(1, current.getEmail());

            if (infoForUpdate.getPassword() != null)
                statement.setString(2, SecurityUtils.decode(infoForUpdate.getPassword()));
            else
                statement.setString(2, current.getPassword());

            if (infoForUpdate.getFirstName() != null)
                statement.setString(3, infoForUpdate.getFirstName());
            else
                statement.setString(3, current.getFirstName());

            if (infoForUpdate.getLastName() != null)
                statement.setString(4, infoForUpdate.getLastName());
            else
                statement.setString(4, current.getLastName());

            if (infoForUpdate.getPhone() != null)
                statement.setString(5, infoForUpdate.getPhone());
            else
                statement.setString(5, current.getPhone());

            if (infoForUpdate.getBirthday() != null)
                statement.setDate(6, infoForUpdate.getBirthday());
            else
                statement.setDate(6, current.getBirthday());

            if(infoForUpdate.getRole()!=null)
                statement.setString(7, infoForUpdate.getRole().name());
            else
                statement.setString(7, current.getRole().name());

            statement.setInt(8, infoForUpdate.getId());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findById(infoForUpdate.getId());
    }

    public User delete(Integer id) {
        User deleted = findById(id);
        try (PreparedStatement statement
                     = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public List<TreatmentHistory> getTreatmentHistoryList(Integer userId,
                                                          TreatmentHistoryDao treatmentHistoryDao,
                                                          DiagnoseDao diagnoseDao) {
        List<TreatmentHistory> treatmentHistoryList = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_TREATMENT_HISTORIES)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                TreatmentHistory treatmentHistory = EntityRetriever.retrieveTreatmentHistory(rs);
                treatmentHistory.setDiagnoseList(
                        treatmentHistoryDao.getDiagnoseListForTreatmentHistory(treatmentHistory.getId(), diagnoseDao));
                treatmentHistoryList.add(treatmentHistory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treatmentHistoryList;
    }

    @Override
    public List<Diagnose> getDiagnoseList(Integer userId) {
        List<Diagnose> diagnoseList = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_DIAGNOSES)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                diagnoseList.add(EntityRetriever.retrieveDiagnose(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diagnoseList;
    }

    @Override
    public User findByEmail(String email) {
        User patient = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_EMAIL)){

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                patient = EntityRetriever.retrievePatient(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    @Override
    public List<User> getSick() {
        List<User> sick = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_SICK)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                sick.add(EntityRetriever.retrievePatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sick;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
