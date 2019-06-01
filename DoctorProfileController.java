/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DoctorDao;
import dao.PatientDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Doctor;
import model.Patient;

/**
 *
 * @author sakib
 */
public class DoctorProfileController extends HttpServlet {

    private String viewDirectory = "/WEB-INF/view/doctor/";
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        //session.invalidate();
        
        if (username == null || !role.equals("doctor")){
            request.setAttribute("error", "Please login!");
            response.sendRedirect("doctor-login");
        }
        else{
            DoctorDao doctorDao = new DoctorDao();
            Doctor doctor = doctorDao.getDoctor(username);
            request.setAttribute("doctor", doctor);
            RequestDispatcher rd = request.getRequestDispatcher(viewDirectory + "profile.jsp");
            rd.forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String speciality = request.getParameter("speciality");
        String hospital = request.getParameter("hospital");
        String designation = request.getParameter("designation");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String chamberAddress = request.getParameter("chamberAddress");
        String visitingTime = request.getParameter("visitingTime");
        int patientsPerDay = Integer.valueOf(request.getParameter("patientsPerDay"));
        double fee = Double.valueOf(request.getParameter("fee"));
        
        DoctorDao doctorDao = new DoctorDao();
        int row = doctorDao.update(name, speciality, hospital, designation, username, password, chamberAddress, visitingTime, patientsPerDay, fee);
        response.sendRedirect("doctor-profile");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
