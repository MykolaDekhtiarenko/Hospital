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
@EqualsAndHashCode(exclude = {"id", "assignmentList"})
public class Diagnose {
    private int id;
    private Date date;
    private String diagnose;
    private int treatmentHistoryId;
    private int creatorId;
    private List<Assignment> assignmentList;
}
