/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.dto;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class CareerAndProvinceDTO implements Serializable {

    private String id;
    private String name;
    private int count;
    private double percent;

    public CareerAndProvinceDTO() {
    }

    public CareerAndProvinceDTO(String id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }
    
    public CareerAndProvinceDTO(String id, String name, int count, double percent) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.percent = percent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "CareerAndProvinceDTO{" + "id=" + id + ", name=" + name + ", count=" + count + ", percent=" + percent + '}';
    }
}
