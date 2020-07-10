/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.controller;

import hieplh.dao.CareerDAO;
import hieplh.dto.CareerDTO;
import java.io.IOException;
import java.util.List;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class CareerDetailSevlet extends HttpServlet {

    private final String DETAIL_PAGE = "careerdetail.jsp";
    private final String DETAIL_CONTENT_PAGE = "careerdetailcontent.jsp";

    private final String TOP_TYPE = "top";
    private final String LOWEST_TYPE = "lowest";

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

        String url = DETAIL_PAGE;
        String careerId = request.getParameter("careerId");
        String type = request.getParameter("typeCareer");
        String page = request.getParameter("page");
        String ajax = request.getParameter("ajax");
        page = initPage(page);
        careerId = initCareerId(careerId, type, request);

        try {
            if (type != null) {
                CareerDAO dao = new CareerDAO();
                String xml = null;
                int records = 0;
                switch (type) {
                    case TOP_TYPE:
                        xml = dao.getTop10HotCareerDetail(careerId, page);
                        records = dao.getTotalRecordOfHotCareer(careerId);
                        break;
                    case LOWEST_TYPE:
                        xml = dao.getTop10lowestRecruitCareerDetail(careerId, page);
                        if (xml != null) {
                            System.out.println("Length: " + xml.length());
                            System.out.println("XML: " + xml);
                        }
                        records = dao.getTotalRecordOfLowestCareer(careerId);
                        break;
                    default:
                        break;
                }

                int pages = initPages(records);
                request.setAttribute("CAREER_DETAIL", xml);
                request.setAttribute("CAREER_ID", careerId);
                request.setAttribute("TYPE", type);
                request.setAttribute("PAGE", page);
                request.setAttribute("PAGES", pages);
                
                if (ajax != null) {
                    url = DETAIL_CONTENT_PAGE;
                }
            }
        } catch (ClassNotFoundException e) {
            log("CLASS_NOT_FOUND_EXCEPTION: " + e.getMessage());
        } catch (SQLException e) {
            log("SQL_EXCEPTION: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String initPage(String page) {
        return page != null ? page : "1";
    }

    private int initPages(int records) {
        if (records % 30 == 0) {
            return records / 30;
        }
        return records / 30 + 1;
    }

    private String initCareerId(String careerId, String type, HttpServletRequest request) {
        if (careerId != null) {
            return careerId;
        }

        HttpSession session = request.getSession();
        List<CareerDTO> list = null;
        switch (type) {
            case TOP_TYPE:
                list = (List<CareerDTO>) session.getAttribute("HOT_CAREERS");
                break;
            case LOWEST_TYPE:
                list = (List<CareerDTO>) session.getAttribute("LOWEST_RECRUITMENT_CAREERS");
                break;
            default:
                break;
        }
        return list.get(0).getCareerId();
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
