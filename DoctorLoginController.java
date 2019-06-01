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
public class DoctorLoginController extends HttpServlet {

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
        request.getRequestDispatcher(viewDirectory+"login.jsp")
                .forward(request, response);
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        DoctorDao doctorDao = new DoctorDao();
        Doctor doctor = doctorDao.getDoctor(username, password);
        if (doctor == null){
            request.setAttribute("error", "username and password didn't match");
            request.getRequestDispatcher(viewDirectory+"login.jsp")
                .forward(request, response);
        }
        else{
            HttpSession session = request.getSession();
            session.setAttribute("username", doctor.getUsername());
            session.setAttribute("userid", doctor.getId());
            session.setAttribute("role", "doctor");
//            request.setAttribute("patient", patient);
//            RequestDispatcher rd = request.getRequestDispatcher("patient-profile");
//            rd.forward(request, response);
//            System.out.println("login controller");
            response.sendRedirect("doctor-profile");
        }
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
