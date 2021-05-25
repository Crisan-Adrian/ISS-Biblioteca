package controller;

import service.EmployeeService;

public class EmployeeWindowController {
    private EmployeeService service;

    public void setup(EmployeeService service){
        this.service = service;
    }
}
