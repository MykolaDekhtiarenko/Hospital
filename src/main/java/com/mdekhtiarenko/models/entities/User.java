package com.mdekhtiarenko.models.entities;

import com.mdekhtiarenko.models.enums.Role;
import lombok.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by mykola.dekhtiarenko on 23.08.17.
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"id", "password", "treatmentHistoryList", "diagnoseList"})
public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Date birthday;
    private Role role;
    private List<TreatmentHistory> treatmentHistoryList;
    //List of diagnoses diagnosed by doctor
    private List<Diagnose> diagnoseList;


}
