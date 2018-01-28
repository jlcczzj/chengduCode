package com.faw_qm.jfpublish.receive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ConfirmDocumentContent {
    public static final String STATE_CREATE = "新建";

    public static final String STATE_REVISE = "修订";

    public static final String STATE_EXIST = "已存在";

    private String partNumber;

    private String partName;

    private String versionInfo;

    private String state;

    private Collection docs = new ArrayList();

    private HashMap childAndState = new HashMap();

    private Collection epms = new ArrayList();

    public void setPartNumber(String number) {
        this.partNumber = number;
    }

    public String getPartNumber() {
        return this.partNumber;
    }

    public void setPartName(String name) {
        this.partName = name;
    }

    public String getPartName() {
        return this.partName;
    }

    public void setVersionInfo(String version) {
        this.versionInfo = version;
    }

    public String getVersionInfo() {
        return this.versionInfo;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public int getChildAmount() {
        return this.childAndState.size();
    }

    public int getDocAmount() {
        return this.docs.size();
    }

    public int getEpmAmount() {
        return this.epms.size();
    }

    public HashMap getChildAndState() {
        return this.childAndState;
    }

    public Collection getDocs() {
        return this.docs;
    }

    public Collection getEpms() {
        return this.epms;
    }

    public void addChild(String number,String state) {
        this.childAndState.put(number,state);
    }

    public void addDoc(String number) {
        this.docs.add(number);
    }

    public void addEpm(String number) {
        this.epms.add(number);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
