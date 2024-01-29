package org.pytorch.demo.objectdetection;

import java.util.ArrayList;
public class myGroup {
    public ArrayList<String> child;
    public ArrayList<String> child2;
    public ArrayList<String> child3;
    public String groupName;

    public String Subname;
    myGroup(String name, String subname){
        groupName = name;
        Subname = subname;
        child = new ArrayList<String>();
        child2 = new ArrayList<String>();
        child3 = new ArrayList<String>();
    }
}