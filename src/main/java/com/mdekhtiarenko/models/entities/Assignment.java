package com.mdekhtiarenko.models.entities;

import com.mdekhtiarenko.models.enums.AssignmentType;
import lombok.*;

import java.sql.Date;

/**
 * Created by mykola.dekhtiarenko on 23.08.17.
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class Assignment {
    private int id;
    private Date dateOfAssignment;
    private Date dateOfExecution;
    private String description;
    private AssignmentType type;
    private int diagnoseId;

}
