package com.mygps.chiyao.Entity;

/**
 * Created by zy on 2016/3/31.
 */
public class chiyao_addremind_entity {
    private Long id;
    private String time;
    private String medision_name;
    public chiyao_addremind_entity(){
        super();
    }
    public chiyao_addremind_entity(Long id, String medision_name, String time) {
        this.id = id;
        this.medision_name = medision_name;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
                '}';
    }
}
