package com.hospital.oxygen_defect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cylinder")
public class Cylinder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cylinderNo;
    private double pressure;
    private double weight;
    private double temperature;
    private String valveCondition;
    private String status;
    private String defectStatus;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCylinderNo() { return cylinderNo; }
    public void setCylinderNo(String cylinderNo) { this.cylinderNo = cylinderNo; }

    public double getPressure() { return pressure; }
    public void setPressure(double pressure) { this.pressure = pressure; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public String getValveCondition() { return valveCondition; }
    public void setValveCondition(String valveCondition) { this.valveCondition = valveCondition; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDefectStatus() { return defectStatus; }
    public void setDefectStatus(String defectStatus) { this.defectStatus = defectStatus; }
}

