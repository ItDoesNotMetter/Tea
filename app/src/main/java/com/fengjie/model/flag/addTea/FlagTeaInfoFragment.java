package com.fengjie.model.flag.addTea;


public class FlagTeaInfoFragment
{
    private  boolean UPDATE_FALG = false ;
    public  final static boolean UPDATE = true ;

    public FlagTeaInfoFragment ( boolean UPDATE_FALG) {
        this.UPDATE_FALG = UPDATE_FALG;
    }

    public boolean isUPDATE_FALG() {
        return UPDATE_FALG;
    }

}
