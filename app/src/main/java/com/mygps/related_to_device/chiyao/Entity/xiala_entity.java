package com.mygps.related_to_device.chiyao.Entity;

/**
 * Created by zy on 2016/3/22.
 */
public class xiala_entity {
    private String time;
    private String medicine;
    public xiala_entity() {
        super();
    }

    public xiala_entity(String medicine, String time) {
        this.medicine = medicine;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    @Override
    public String toString() {
        return "xiala_entity{" +
                "medicine='" + medicine + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
