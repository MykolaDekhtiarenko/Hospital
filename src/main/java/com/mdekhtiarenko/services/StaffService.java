package com.mdekhtiarenko.services;

import com.mdekhtiarenko.models.dao.DaoFactory;
import com.mdekhtiarenko.models.dao.StaffDao;
import com.mdekhtiarenko.models.dao.utils.SecurityUtils;
import com.mdekhtiarenko.models.entities.Staff;
import java.util.Optional;

/**
 * Created by mykola.dekhtiarenko on 13.09.17.
 */
public class StaffService {

    DaoFactory daoFactory;

    StaffService(DaoFactory instance){
        daoFactory = instance;
    }

    public Optional<Staff> login(String email, String password){
            StaffDao dao = daoFactory.createStaffDAO();
            return Optional.ofNullable(dao.getStaffByEmail(email))
                    .filter( staff -> SecurityUtils.decode(password).equals(staff.getPassword()));
    }

    //singelton pattern
    private static class Holder{
        private static StaffService INSTANCE = new StaffService(DaoFactory.getInstance());
    }

    public static StaffService getInstance(){
        return StaffService.Holder.INSTANCE;
    }
}
