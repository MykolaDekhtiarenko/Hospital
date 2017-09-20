package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.Patient;
import com.mdekhtiarenko.services.PatientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static com.mdekhtiarenko.views.commands.Constants.*;

/**
 * Created by mykola.dekhtiarenko on 15.09.17.
 */
public class GetHomePage implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        if(session.getAttribute(STAFF_USER)!=null)
            return STAFF_HOMEPAGE;
        else if(session.getAttribute(PATIENT_USER)!=null)
            return prossesPatient(req, session);
        else
            return LOGIN_PAGE;
    }

    private String prossesPatient(HttpServletRequest req, HttpSession session){
        PatientService service = PatientService.getInstance();
        Patient patient = (Patient)session.getAttribute(PATIENT_USER);
        Optional<Patient> fullPatientInfo = service.getFullPatientInfo(patient.getId());
        if(fullPatientInfo.isPresent())
            req.setAttribute("fullPatientInfo", fullPatientInfo.get());
        return PATIENT_HOMEPAGE;
    }
}
