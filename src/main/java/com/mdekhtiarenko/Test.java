package com.mdekhtiarenko;

import com.mdekhtiarenko.models.dao.AssignmentDao;
import com.mdekhtiarenko.models.dao.DaoFactory;
import com.mdekhtiarenko.models.dao.DiagnoseDao;
import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.Patient;
import com.mdekhtiarenko.models.enums.AssignmentType;
import com.mdekhtiarenko.services.PatientService;

import javax.xml.ws.Service;
import java.util.List;
import java.util.Optional;

/**
 * Created by mykola.dekhtiarenko on 07.09.17.
 */
public class Test {
    public static void main(String ... arg){
        PatientService service = PatientService.getInstance();
        Optional<Patient> patient =  service.getFullPatientInfo(9);
        System.out.println(patient);
    }
}
