package com.mdekhtiarenko.models.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Created by mykola.dekhtiarenko on 27.09.17.
 */
@Getter
@Setter
@Builder
public class Response<T> {

    private Optional<T> result;
    private String errorMessage;


    public boolean hasErrors(){
        if(errorMessage==null||errorMessage.equals(""))
            return false;
        else
            return true;
    }

    public void addError(String message){
        if(message==null)
            return;

        if(this.errorMessage==null)
            this.errorMessage = message + " ";
        else
            this.errorMessage += message + " ";
    }

}

