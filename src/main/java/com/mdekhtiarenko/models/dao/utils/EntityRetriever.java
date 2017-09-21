package com.mdekhtiarenko.models.dao.utils;

import com.mdekhtiarenko.models.entities.*;
import com.mdekhtiarenko.models.enums.AssignmentType;
import com.mdekhtiarenko.models.enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mykola.dekhtiarenko on 09.09.17.
 */
public class EntityRetriever {
    public static Assignment retrieveAssignment(ResultSet rs) throws SQLException {
        return Assignment.builder()
                .id(rs.getInt("id"))
                .dateOfAssignment(rs.getDate("dateOfAssignment"))
                .dateOfExecution(rs.getDate("dateOfExecution"))
                .description(rs.getString("description"))
                .type(AssignmentType.valueOf(rs.getString("type")))
                .diagnoseId(rs.getInt("Diagnose_id"))
                .build();
    }

    public static Diagnose retrieveDiagnose(ResultSet rs) throws SQLException {
        return Diagnose.builder()
                .id(rs.getInt("id"))
                .date(rs.getDate("date"))
                .diagnose(rs.getString("diagnose"))
                .treatmentHistoryId(rs.getInt("TreatmentHistory_id"))
                .creatorId(rs.getInt("Creator_id"))
                .build();
    }

    public static User retrievePatient(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .firstName(rs.getString("firstName"))
                .lastName(rs.getString("lastName"))
                .phone(rs.getString("phone"))
                .birthday(rs.getDate("birthday"))
                .role(Role.valueOf(rs.getString("role")))
                .build();
    }


    public static TreatmentHistory retrieveTreatmentHistory(ResultSet rs) throws SQLException {
        return TreatmentHistory.builder()
                .id(rs.getInt("id"))
                .startDate(rs.getDate("startDate"))
                .endDate(rs.getDate("endDate"))
                .conclusion(rs.getString("conclusion"))
                .userId(rs.getInt("User_id"))
                .build();
    }




}
