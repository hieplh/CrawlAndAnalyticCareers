/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class CareerDTO implements Serializable, Comparable<CareerDTO> {

    private String careerId;
    private String careerName;
    private String jobId;
    private String jobName;
    private String company;
    private int recruitment;
    private String salary;
    private int count;

    public CareerDTO() {
    }

    public CareerDTO(String careerId, String careerName, int count) {
        this.careerId = careerId;
        this.careerName = careerName;
        this.count = count;
    }

    public CareerDTO(String careerId, String careerName, String salary) {
        this.careerId = careerId;
        this.careerName = careerName;
        this.salary = salary;
    }

    public CareerDTO(String careerId, String jobId, String jobName, String company, int recruitment, String salary) {
        this.careerId = careerId;
        this.jobId = jobId;
        this.jobName = jobName;
        this.company = company;
        this.recruitment = recruitment;
        this.salary = salary;
    }

    public String getCareerId() {
        return careerId;
    }

    public void setCareerId(String careerId) {
        this.careerId = careerId;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(int recruitment) {
        this.recruitment = recruitment;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CareerDTO other = (CareerDTO) obj;
        if (!Objects.equals(this.careerId, other.careerId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CareerDTO{" + "careerId=" + careerId + ", careerName=" + careerName + ", jobId=" + jobId + ", jobName=" + jobName + ", company=" + company + ", recruitment=" + recruitment + ", salary=" + salary + ", count=" + count + '}';
    }

    @Override
    public int compareTo(CareerDTO o) {
        return this.careerName.compareTo(o.careerName);
    }

}
