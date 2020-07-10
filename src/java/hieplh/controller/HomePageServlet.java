/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.controller;

import hieplh.dao.CareerDAO;
import hieplh.dto.CareerDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class HomePageServlet extends HttpServlet {

    private final String HOME_PAGE = "homepage.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = HOME_PAGE;

        try {
            CareerDAO dao = new CareerDAO();
            List<CareerDTO> listHotCareers = dao.getTop10HotCareer();
            List<CareerDTO> listLowestRecruitCareers = dao.getTop10lowestRecruitCareer();
            List<CareerDTO> listAvgSalaryOfCareers = dao.getAvgSalaryOfCareers();
            
            HttpSession session = request.getSession();
            session.setAttribute("HOT_CAREERS", listHotCareers);
            session.setAttribute("LOWEST_RECRUITMENT_CAREERS", listLowestRecruitCareers);
            session.setAttribute("AVG_SALARY_OF_CAREERS", listAvgSalaryOfCareers);
            
            String[] colors = initColors();
            session.setAttribute("COLORS", colors);
        } catch (ClassNotFoundException e) {
            log("CLASS_NOT_FOUND_EXCEPTION: " + e.getMessage());
        } catch (SQLException e) {
            log("SQL_EXCEPTION: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    
    private String[] initColors() {
        String[] colors = new String[10];
        
        colors[0] = "#3e95cd";
        colors[1] = "#008000";
        colors[2] = "#8d8d8d";
        colors[3] = "#0400ff";
        colors[4] = "#bf00ff";
        colors[5] = "#00ddff";
        colors[6] = "#00ff94";
        colors[7] = "#756d25";
        colors[8] = "#f6ff00";
        colors[9] = "#ff3700";
        
        return colors;
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
