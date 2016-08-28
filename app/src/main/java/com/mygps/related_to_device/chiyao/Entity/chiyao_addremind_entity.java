package com.mygps.related_to_device.chiyao.Entity;

/**
 * Created by zy on 2016/3/31.
 */
public class chiyao_addremind_entity {
    private int id;
    private String time;
    private String medision_name;
    private String equip_name;
    private String equip_id;
    public chiyao_addremind_entity(){
        super();
    }
    public chiyao_addremind_entity(int id, String time, String medision_name,String equip_name,String equip_id) {
        this.id = id;
        this.medision_name = medision_name;
        this.time = time;
        this.equip_name=equip_name;
        this.equip_id=equip_id;
    }

    public String getEquip_name() {
        return equip_name;
    }

    public void setEquip_name(String equip_name) {
        this.equip_name = equip_name;
    }

    public String getEquip_id() {
        return equip_id;
    }

    public void setEquip_id(String equip_id) {
        this.equip_id = equip_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedision_name() {
        return medision_name;
    }

    public void setMedision_name(String medision_name) {
        this.medision_name = medision_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "chiyao_addremind_entity{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", medision_name='" + medision_name + '\'' +
                ", equip_name='" + equip_name + '\'' +
                ", equip_id='" + equip_id + '\'' +
                '}';
    }
}
