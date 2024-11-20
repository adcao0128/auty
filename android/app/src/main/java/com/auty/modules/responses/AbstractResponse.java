package com.auty.modules.responses;

public abstract class AbstractResponse {

    String responseName;


    public AbstractResponse(String responseName){
        this.responseName = responseName;

    }

    public String getResponseName() {
        return responseName;
    }

    public abstract void respond(String message);

}
