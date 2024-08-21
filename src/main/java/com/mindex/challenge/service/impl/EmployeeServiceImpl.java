package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    public ReportingStructure getReportingStructure(String id) {
        Employee employee = employeeRepository.findByEmployeeId(id);
        int numberOfReports = calculateTotalReports(employee);
        return new ReportingStructure(employee, numberOfReports);
    }

    // recursively calculate total number of reports
    private int calculateTotalReports(Employee employee) {
        int reportCount = 0;

        // if the employee has direct reports
        if (employee.getDirectReports() != null && !employee.getDirectReports().isEmpty()) {
            // loop through each directReport, and see if they have any direct reports to add on
            for (Employee directReport : employee.getDirectReports()) {
                Employee fullReport = employeeRepository.findByEmployeeId(directReport.getEmployeeId());
                int directReportCount = calculateTotalReports(fullReport);
                reportCount += 1 + directReportCount;
            }
        }
        return reportCount;
    }

}
