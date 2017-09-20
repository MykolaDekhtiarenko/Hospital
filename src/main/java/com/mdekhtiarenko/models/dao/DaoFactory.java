package com.mdekhtiarenko.models.dao;


import com.mdekhtiarenko.models.dao.utils.Config;

public abstract class DaoFactory {

    public abstract AssignmentDao createAssignmentDAO();
    public abstract DiagnoseDao createDiagnoseDAO();
    public abstract PatientDao createPatientDAO();
    public abstract StaffDao createStaffDAO();
    public abstract TreatmentHistoryDao createTreatmentHistoryDAO();

    public static DaoFactory getInstance() {
        String className = Config.getInstance().getFactoryClassName();
        DaoFactory factory = null;
        try {
            factory = (DaoFactory) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }
}
