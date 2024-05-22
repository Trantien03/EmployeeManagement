package com.example.controller;

import com.example.dao.EmployeeDAO;
import com.example.dao.EmployeeDAOImpl;
import com.example.entity.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {

    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String fullname = request.getParameter("fullname");
        String birthdayStr = request.getParameter("birthday");
        String address = request.getParameter("address");
        String position = request.getParameter("position");
        String department = request.getParameter("department");

        Date birthday = Date.valueOf(birthdayStr);

        Employee employee = new Employee(fullname, birthday, address, position, department);
        employeeDAO.addEmployee(employee);

        response.sendRedirect("EmployeeServlet?action=list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("list".equals(action)) {
            listEmployees(request, response);
        } else {
            request.getRequestDispatcher("employee.jsp").forward(request, response);
        }
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> employees = employeeDAO.getAllEmployees();
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }
}
