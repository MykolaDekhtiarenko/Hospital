package com.mdekhtiarenko.models.dao.impl;

import com.mdekhtiarenko.models.dao.DiagnoseDao;
import com.mdekhtiarenko.models.dao.PatientDao;
import com.mdekhtiarenko.models.dao.TreatmentHistoryDao;
import com.mdekhtiarenko.models.dao.utils.EntityRetriever;
import com.mdekhtiarenko.models.dao.utils.SecurityUtils;
import com.mdekhtiarenko.models.entities.Patient;
import com.mdekhtiarenko.models.entities.TreatmentHistory;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mykola.dekhtiarenko on 28.08.17.
 */
public class PatientDaoImpl implements PatientDao {
    private static final String SELECT_ALL = "SELECT * FROM PATIENT";
    private static final String SELECT_BY_ID = "SELECT * FROM PATIENT WHERE id = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM PATIENT WHERE email = ?";
    private static final String CREATE = "INSERT INTO PATIENT" +
            " (email, password, firstName, lastName, phone, birthday)\n" +
            "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE PATIENT SET " +
            "email = ?, password = ?, firstName = ?, lastName = ?, phone = ?, birthday = ?" +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM PATIENT WHERE id = ?";
    private static final String SELECT_TREATMENT_HISTORIES = "SELECT * FROM TREATMENTHISTORY WHERE Patient_id = ?";

    private final Logger logger = Logger.getLogger(AssignmentDaoImpl.class.getName());

    private Connection connection;
    public PatientDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Patient> findAll() {
        List<Patient> all = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                all.add(EntityRetriever.retrievePatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("List size: "+all.size());
        return all;
    }

    public Patient findById(Integer id) {
        Patient patient = null;
        logger.debug("id: "+id);
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
        logger.debug(patient.toString());
        return patient;
    }

    public boolean create(Patient patient) {
        logger.debug(patient.toString());
        try (PreparedStatement statement
                     = connection.prepareStatement(CREATE)) {
            statement.setString(1, patient.getEmail());
            statement.setString(2, SecurityUtils.decode(patient.getPassword()));
            statement.setString(3, patient.getFirstName());
            statement.setString(4, patient.getLastName());
            statement.setString(5, patient.getPhone());
            statement.setDate(6, patient.getBirthday());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Patient update(Patient infoForUpdate) {
        Patient current = findById(infoForUpdate.getId());
        logger.debug( "Current: "+current.toString());
        logger.debug("To update: "+infoForUpdate.toString());
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

            statement.setInt(7, infoForUpdate.getId());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findById(infoForUpdate.getId());
    }

    public Patient delete(Integer id) {
        Patient deleted = findById(id);
        logger.debug( "id: " + id);
        logger.debug(deleted.toString());
        try (PreparedStatement statement
                     = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public List<TreatmentHistory> getTreatmentHistoryListForPatient(Integer patientId,
                                                                    TreatmentHistoryDao treatmentHistoryDao,
                                                                    DiagnoseDao diagnoseDao) {
        logger.debug("patientId: "+patientId);
        List<TreatmentHistory> treatmentHistoryList = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_TREATMENT_HISTORIES)) {
            statement.setInt(1, patientId);
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
        logger.debug( "List size: "+treatmentHistoryList.size());
        return treatmentHistoryList;
    }

    @Override
    public Patient getPatientByEmail(String email) {
        logger.debug("Email: "+email);
        Patient patient = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_EMAIL)){

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                patient = EntityRetriever.retrievePatient(rs);
                logger.debug(patient.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
