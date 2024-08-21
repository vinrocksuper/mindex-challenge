package com.mindex.challenge;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.impl.EmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChallengeApplicationTests {

	@Autowired
	private EmployeeServiceImpl impl;

	@MockBean
	private EmployeeRepository employeeRepository;

	@Test
	public void testDirectReportsCount() {
		Employee employeeOne = new Employee();
		employeeOne.setEmployeeId("E1");
		Employee employeeTwo = new Employee();
		employeeTwo.setEmployeeId("E2");
		Employee employeeThree = new Employee();
		employeeThree.setEmployeeId("E3");
		Employee employeeFour = new Employee();
		employeeFour.setEmployeeId("E4");
		Employee employeeFive = new Employee();
		employeeFive.setEmployeeId("E5");

		employeeOne.setDirectReports(Arrays.asList(employeeTwo, employeeThree));
		employeeThree.setDirectReports(Arrays.asList(employeeFour, employeeFive));

		Mockito.when(employeeRepository.findByEmployeeId("E1")).thenReturn(employeeOne);
		Mockito.when(employeeRepository.findByEmployeeId("E2")).thenReturn(employeeTwo);
		Mockito.when(employeeRepository.findByEmployeeId("E3")).thenReturn(employeeThree);
		Mockito.when(employeeRepository.findByEmployeeId("E4")).thenReturn(employeeFour);
		Mockito.when(employeeRepository.findByEmployeeId("E5")).thenReturn(employeeFive);

		assertEquals(impl.getReportingStructure("E1").getNumberOfReports(), 4);
		assertEquals(impl.getReportingStructure("E3").getNumberOfReports(), 2);
		assertEquals(impl.getReportingStructure("E5").getNumberOfReports(), 0);
	}

	@Test
	public void testEffectiveDateAssignment_NotAssigned() {
		Compensation compensation = new Compensation();
		assertEquals(10000, compensation.getSalary());
		// Will fail after today, set to date of test
		assertEquals(LocalDate.of(2024, 8, 21), compensation.getEffectiveDate());
	}

	@Test
	public void testEffectiveDateAssignment_Assigned() {
		Compensation compensation = new Compensation(100,LocalDate.of(2024, 8, 21));
		assertEquals(100, compensation.getSalary());
		assertEquals(LocalDate.of(2024, 8, 21), compensation.getEffectiveDate());
	}
}
