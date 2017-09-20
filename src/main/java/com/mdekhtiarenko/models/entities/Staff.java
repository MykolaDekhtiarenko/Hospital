package com.mdekhtiarenko.models.entities;


import com.mdekhtiarenko.models.enums.Role;
import lombok.*;

import java.util.List;

/**
 * Created by mykola.dekhtiarenko on 23.08.17.
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"id", "password", "diagnoseList", "assignmentList"})
public class Staff {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    private List<Diagnose> diagnoseList;
    private List<Assignment> assignmentList;

}
