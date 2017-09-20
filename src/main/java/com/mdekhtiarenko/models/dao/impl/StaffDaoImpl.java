package com.mdekhtiarenko.models.dao.impl;

import com.mdekhtiarenko.models.dao.StaffDao;
import com.mdekhtiarenko.models.dao.utils.EntityRetriever;
import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.Staff;
import com.mdekhtiarenko.models.dao.utils.SecurityUtils;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mykola.dekhtiarenko on 23.08.17.
 */
public class StaffDaoImpl implements StaffDao {
    private static final String SELECT_ALL = "SELECT * FROM STAFF";
    private static final String SELECT_BY_ID = "SELECT * FROM STAFF WHERE id = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM STAFF WHERE email = ?";

    private static final String CREATE = "INSERT INTO STAFF (email, password, firstName, lastName, role)\n" +
                                                            "VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE STAFF SET " +
            "email = ?, password = ?, firstName = ?, lastName = ?, role = ? " +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM STAFF WHERE id = ?";

    private static final String SELECT_DIAGNOSES = "SELECT * FROM Diagnose WHERE Staff_id = ?";

    private static final String SELECT_ASSIGNMENTS = "SELECT * FROM Assignment WHERE Staff_id = ?";

    private final Logger logger = Logger.getLogger(AssignmentDaoImpl.class.getName());

    private Connection connection;
    public StaffDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Staff> findAll(){
        List<Staff> allStaff = new ArrayList<>();
        try(PreparedStatement statement
                    = connection.prepareStatement(SELECT_ALL)){

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                allStaff.add(EntityRetriever.retrieveStaff(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("List size: "+allStaff.size());
        return allStaff;
    }

    public Staff findById(Integer id){
        logger.debug("id: "+id);
        Staff staff = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_ID)){

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
               staff = EntityRetriever.retrieveStaff(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug(staff.toString());
        return staff;
    }

    public Staff getStaffByEmail(String email){
        logger.debug("Email: "+email);
        Staff staff = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_EMAIL)){

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                staff = EntityRetriever.retrieveStaff(rs);
                logger.debug(staff.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public boolean create(Staff staff) {
        logger.debug(staff.toString());
        try (PreparedStatement statement
                     = connection.prepareStatement(CREATE)) {

            statement.setString(1, staff.getEmail());
            statement.setString(2, SecurityUtils.decode(staff.getPassword()));
            statement.setString(3, staff.getFirstName());
            statement.setString(4, staff.getLastName());
            statement.setString(5, staff.getRole().name());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Staff update(Staff infoForUpdate) {
        Staff current = findById(infoForUpdate.getId());
        logger.debug("Info for update: "+infoForUpdate.toString());
        logger.debug("Current: "+current.toString());
        try (PreparedStatement statement
                     = connection.prepareStatement(UPDATE)){

            if (infoForUpdate.getEmail() != null)
                statement.setString(1, infoForUpdate.getEmail());
            else
                statement.setString(1, current.getEmail());

            if(infoForUpdate.getPassword()!=null)
                statement.setString(2, SecurityUtils.decode(infoForUpdate.getPassword()));
            else
                statement.setString(2, current.getPassword());

            if(infoForUpdate.getFirstName()!=null)
                statement.setString(3, infoForUpdate.getFirstName());
            else
                statement.setString(3, current.getFirstName());

            if(infoForUpdate.getLastName()!=null)
                statement.setString(4, infoForUpdate.getLastName());
            else
                statement.setString(4, current.getLastName());

            if(infoForUpdate.getRole()!=null)
                statement.setString(5, infoForUpdate.getRole().name());
            else
                statement.setString(5, current.getRole().name());

            statement.setInt(6, infoForUpdate.getId());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findById(infoForUpdate.getId());
    }

    public Staff delete(Integer id) {
        Staff deleted = findById(id);
        logger.debug("id: "+id);
        logger.debug(deleted.toString());
        try (PreparedStatement statement
                     = connection.prepareStatement(DELETE)){
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public List<Diagnose> getDiagnoseListForStaff(Integer staffId) {
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
        logger.debug("List size: "+diagnoseList.size());
        return diagnoseList;
    }

    public List<Assignment> getAssigmentListForStaff(Integer staffId) {
        List<Assignment> assignmentList = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_ASSIGNMENTS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                assignmentList.add(EntityRetriever.retrieveAssignment(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("List size: "+assignmentList.size());
        return assignmentList;
    }


    @Override
    public void close() throws Exception {
        connection.close();
    }
}
