package com.mindex.challenge.data;

import java.time.LocalDate;

public class Compensation {
    private int salary;
    private LocalDate effectiveDate;

    public Compensation(int salary, LocalDate effectiveDate) {
        this.salary = salary;
        this.effectiveDate = effectiveDate != null ? effectiveDate : LocalDate.now();
    }

    public Compensation(){
        this.salary = 10000;
        this.effectiveDate = LocalDate.now();
    }

    public int getSalary() { return salary; }
    public LocalDate getEffectiveDate() { return effectiveDate; }
    public void setSalary(int salary) { this.salary = salary; }
    public void setEffectiveDate(LocalDate date) { this.effectiveDate = date; }
}
