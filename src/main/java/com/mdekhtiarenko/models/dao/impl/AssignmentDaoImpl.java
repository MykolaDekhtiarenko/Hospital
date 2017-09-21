package com.mdekhtiarenko.models.dao.impl;

import com.mdekhtiarenko.models.dao.AssignmentDao;
import com.mdekhtiarenko.models.dao.utils.EntityRetriever;
import com.mdekhtiarenko.models.entities.Assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Created by mykola.dekhtiarenko on 27.08.17.
 */
public class AssignmentDaoImpl implements AssignmentDao {
    private static final String SELECT_ALL = "SELECT * FROM ASSIGNMENT";
    private static final String SELECT_BY_ID = "SELECT * FROM ASSIGNMENT WHERE id = ?";
    private static final String CREATE = "INSERT INTO ASSIGNMENT (description, type, Diagnose_id)\n" +
            "VALUES (?, ?, ?);";
    private static final String UPDATE = "UPDATE ASSIGNMENT SET " +
            "dateOfExecution = ?, description = ?, type = ? " +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM ASSIGNMENT WHERE id = ?";

    private Connection connection;
    public AssignmentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Assignment> findAll(){
        List<Assignment> allAssigment = new ArrayList<>();
        try(PreparedStatement statement
                    = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                allAssigment.add(EntityRetriever.retrieveAssignment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAssigment;
    }

    public Assignment findById(Integer id){
        Assignment assignment = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_ID)){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                assignment = EntityRetriever.retrieveAssignment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignment;
    }

    public boolean create(Assignment assignment){
        try(PreparedStatement statement
                    = connection.prepareStatement(CREATE)) {

            statement.setString(1, assignment.getDescription());
            statement.setString(2, assignment.getType().name());
            statement.setInt(3, assignment.getDiagnoseId());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public  Assignment update(Assignment infoForUpdate){
        Assignment current = findById(infoForUpdate.getId());

        try(PreparedStatement statement
                    = connection.prepareStatement(UPDATE)) {

            if(infoForUpdate.getDateOfExecution()!=null)
                statement.setDate(1,infoForUpdate.getDateOfExecution());
            else
                statement.setDate(1, current.getDateOfExecution());

            if(infoForUpdate.getDescription()!=null)
                statement.setString(2, infoForUpdate.getDescription());
            else
                statement.setString(2, current.getDescription());

            if(infoForUpdate.getType()!=null)
                statement.setString(3, infoForUpdate.getType().name());
            else
                statement.setString(3, current.getType().name());

            statement.setInt(4, infoForUpdate.getId());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findById(infoForUpdate.getId());
    }

    public Assignment delete(Integer id) {
        Assignment deleted = findById(id);
        try (PreparedStatement statement
                     = connection.prepareStatement(DELETE)){
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
