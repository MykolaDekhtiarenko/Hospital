package com.mdekhtiarenko.models.entities;

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
@EqualsAndHashCode(exclude = {"id", "password", "treatmentHistoryList"})
public class Patient {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Date birthday;
    private List<TreatmentHistory> treatmentHistoryList;
}
