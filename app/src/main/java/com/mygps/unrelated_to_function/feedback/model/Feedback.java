package com.mygps.unrelated_to_function.feedback.model;

/**
 * Created by silen on 16-7-10.
 */

public class Feedback {
    private int AppVersionCode;
    private String MODEL;
    private String SystemVersion;
    private int APIVersion;
    private String ROMInfo;
    private String content;
    private String contact;

    public int getAppVersionCode() {
        return AppVersionCode;
    }

    public void setAppVersionCode(int AppVersionCode) {
        this.AppVersionCode = AppVersionCode;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public String getSystemVersion() {
        return SystemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        SystemVersion = systemVersion;
    }

    public int getAPIVersion() {
        return APIVersion;
    }

    public void setAPIVersion(int APIVersion) {
        this.APIVersion = APIVersion;
    }

    public String getROMInfo() {
        return ROMInfo;
    }

    public void setROMInfo(String ROMInfo) {
        this.ROMInfo = ROMInfo;
    }
}
