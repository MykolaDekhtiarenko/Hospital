package com.mdekhtiarenko.models.dao.impl;

import com.mdekhtiarenko.models.dao.DiagnoseDao;
import com.mdekhtiarenko.models.dao.utils.EntityRetriever;
import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.Diagnose;
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
public class DiagnoseDaoImpl implements DiagnoseDao {
    private static final String SELECT_ALL = "SELECT * FROM DIAGNOSE";
    private static final String SELECT_BY_ID = "SELECT * FROM DIAGNOSE WHERE id = ?";
    private static final String CREATE = "INSERT INTO DIAGNOSE (diagnose, TreatmentHistory_id, Creator_id)\n" +
            "VALUES (?, ?, ?);";
    private static final String UPDATE = "UPDATE DIAGNOSE SET " +
            "diagnose = ? " +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM DIAGNOSE WHERE id = ?";

    private static final String SELECT_ASSIGNMENTS = "SELECT * FROM Assignment WHERE Diagnose_id = ?";


    private Connection connection;
    public DiagnoseDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Diagnose> findAll(){
        List<Diagnose> allDiagnoses = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_ALL)){
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                allDiagnoses.add(EntityRetriever.retrieveDiagnose(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allDiagnoses;
    }

    public Diagnose findById(Integer id){
        Diagnose diagnose = null;
        try(PreparedStatement statement
                    = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                diagnose = EntityRetriever.retrieveDiagnose(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diagnose;
    }

    public boolean create(Diagnose diagnose) {
        try (PreparedStatement statement
                     = connection.prepareStatement(CREATE)){
            statement.setString(1, diagnose.getDiagnose());
            statement.setInt(2, diagnose.getTreatmentHistoryId());
            statement.setInt(3, diagnose.getCreatorId());

            statement.execute();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Diagnose update(Diagnose infoForUpdate) {
        Diagnose current = findById(infoForUpdate.getId());
        try (PreparedStatement statement
                     = connection.prepareStatement(UPDATE)){
            if(infoForUpdate.getDiagnose()!=null)
                statement.setString(1, infoForUpdate.getDiagnose());
            else
                statement.setString(1, current.getDiagnose());

            statement.setInt(2, infoForUpdate.getId());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findById(infoForUpdate.getId());
    }

    public Diagnose delete(Integer id) {
        Diagnose deleted = findById(id);
        try (PreparedStatement statement
                     = connection.prepareStatement(DELETE)){
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public List<Assignment> getAssignmentListForDiagnose(Integer diagnoseId) {
        List<Assignment> assignmentList = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_ASSIGNMENTS)) {
            statement.setInt(1, diagnoseId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                assignmentList.add(EntityRetriever.retrieveAssignment(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignmentList;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
