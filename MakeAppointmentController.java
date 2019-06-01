/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AppointmentDao;
import dao.DoctorDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Doctor;

/**
 *
 * @author sakib
 */
public class MakeAppointmentController extends HttpServlet {

    private String viewDirectory = "/WEB-INF/view/patient/";

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
        int id = Integer.valueOf(request.getParameter("id"));
        DoctorDao doctorDao = new DoctorDao();
            Doctor doctor = doctorDao.getDoctor(id);
            request.setAttribute("doctor", doctor);
            RequestDispatcher rd = request.getRequestDispatcher(viewDirectory + "appoint.jsp");
            rd.forward(request, response);
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
        int doctor_id = Integer.valueOf(request.getParameter("doctor_id"));
        int patient_id = (int)request.getSession().getAttribute("userid");
        String name = request.getParameter("name");
        String contactNumber = request.getParameter("contact");
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date date = new Date();
        String visitingDate = dateFormat.format(date);
        AppointmentDao appointmentDao = new AppointmentDao();
        int booked = appointmentDao.getBookedSlot(doctor_id, visitingDate);
        DoctorDao doctorDao = new DoctorDao();
        int limit = doctorDao.getDoctor(doctor_id).getPatientPerDay();
        int serial = 0;
        if(booked < limit){
            serial = booked+1;
        }
        else{
            return;
        }
        appointmentDao.insert(visitingDate, serial, name, contactNumber, patient_id, doctor_id);
        response.sendRedirect("appointment-history");
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
