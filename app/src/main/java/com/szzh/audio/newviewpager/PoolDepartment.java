package com.szzh.audio.newviewpager;

import java.io.Serializable;

/**
 * Created by jzz
 * on 2017/4/14.
 *
 * desc: 医生所在执业部门
 */

public class PoolDepartment implements Serializable {
    private long id;
    private String name;
    private long hospitalId;
    private String hospitalName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public String toString() {
        return "PoolDepartment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hospitalId=" + hospitalId +
                ", hospitalName='" + hospitalName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoolDepartment that = (PoolDepartment) o;

        return id == that.id && hospitalId == that.hospitalId && name.equals(that.name) && hospitalName.equals(that.hospitalName);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + (int) (hospitalId ^ (hospitalId >>> 32));
        result = 31 * result + hospitalName.hashCode();
        return result;
    }
}
