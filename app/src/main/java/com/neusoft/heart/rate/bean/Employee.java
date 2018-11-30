package com.neusoft.heart.rate.bean;

public class Employee {
    String EmployeeID;
    String Code;
    String Name;
    String DepartmentID;
    Boolean StopFlag;

    public Employee(){}

    public Employee(String employeeID, String code, String name, String departmentID, Boolean stopFlag) {
        EmployeeID = employeeID;
        Code = code;
        Name = name;
        DepartmentID = departmentID;
        StopFlag = stopFlag;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }

    public Boolean getStopFlag() {
        return StopFlag;
    }

    public void setStopFlag(Boolean stopFlag) {
        StopFlag = stopFlag;
    }
}
