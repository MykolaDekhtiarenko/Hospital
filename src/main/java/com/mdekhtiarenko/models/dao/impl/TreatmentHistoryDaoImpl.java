package com.mdekhtiarenko.models.dao.impl;

import com.mdekhtiarenko.models.dao.DiagnoseDao;
import com.mdekhtiarenko.models.dao.TreatmentHistoryDao;
import com.mdekhtiarenko.models.dao.utils.EntityRetriever;
import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.TreatmentHistory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mykola.dekhtiarenko on 27.08.17.
 */
public class TreatmentHistoryDaoImpl implements TreatmentHistoryDao {
    private static final String SELECT_ALL = "SELECT * FROM TREATMENTHISTORY";
    private static final String SELECT_BY_ID = "SELECT * FROM TREATMENTHISTORY WHERE id = ?";
    private static final String CREATE = "INSERT INTO TREATMENTHISTORY (Patient_id)\n" +
            "VALUES (?);";
    private static final String UPDATE = "UPDATE TREATMENTHISTORY SET " +
            "endDate = ?, conclusion = ?" +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM TREATMENTHISTORY WHERE id = ?";

    private static final String SELECT_DIAGNOSES = "SELECT * FROM Diagnose WHERE TreatmentHistory_id = ?";

    private final Logger logger = Logger.getLogger(AssignmentDaoImpl.class.getName());

    private Connection connection;
    public TreatmentHistoryDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public List<TreatmentHistory> findAll() {
        List<TreatmentHistory> allTreatmentHistories = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                allTreatmentHistories.add(EntityRetriever.retrieveTreatmentHistory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("List size: "+allTreatmentHistories.size());
        return allTreatmentHistories;
    }

    public TreatmentHistory findById(Integer id) {
        logger.debug("id: "+ id);

        TreatmentHistory treatmentHistory = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                treatmentHistory = EntityRetriever.retrieveTreatmentHistory(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug(treatmentHistory.toString());
        return treatmentHistory;
    }

    public boolean create(TreatmentHistory treatmentHistory) {
        logger.debug(treatmentHistory.toString());
        try (PreparedStatement statement
                     = connection.prepareStatement(CREATE)) {
            statement.setInt(1, treatmentHistory.getPatientId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public TreatmentHistory update(TreatmentHistory infoForUpdate) {
        TreatmentHistory current = findById(infoForUpdate.getId());
        logger.debug("Info for update: "+infoForUpdate.toString());
        logger.debug("Current: "+current.toString());

        try (PreparedStatement statement
                     = connection.prepareStatement(UPDATE)) {

            if (infoForUpdate.getEndDate() != null)
                statement.setDate(1, infoForUpdate.getEndDate());
            else
                statement.setDate(1, current.getEndDate());

            if (infoForUpdate.getConclusion() != null)
                statement.setString(2, infoForUpdate.getConclusion());
            else
                statement.setString(2, current.getConclusion());

            statement.setInt(3, infoForUpdate.getId());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findById(infoForUpdate.getId());
    }

    public TreatmentHistory delete(Integer id) {
        TreatmentHistory deleted = findById(id);
        logger.debug("id: "+id);
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

    public List<Diagnose> getDiagnoseListForTreatmentHistory(Integer treatmentHistoryId, DiagnoseDao diagnoseDao) {
        List<Diagnose> diagnoseList = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_DIAGNOSES)) {
            statement.setInt(1, treatmentHistoryId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Diagnose diagnose = EntityRetriever.retrieveDiagnose(rs);
                diagnose.setAssignmentList(diagnoseDao.getAssignmentListForDiagnose(diagnose.getId()));
                diagnoseList.add(diagnose);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("List size: "+diagnoseList.size());
        return diagnoseList;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
