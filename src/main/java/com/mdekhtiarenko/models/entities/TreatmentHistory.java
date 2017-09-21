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
@EqualsAndHashCode(exclude = {"id", "diagnoseList"})
public class TreatmentHistory {
    private int id;
    private Date startDate;
    private Date endDate;
    private String conclusion;
    private int userId;
    private List<Diagnose> diagnoseList;
}
