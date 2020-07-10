/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.controller;

import hieplh.dto.CareerAndProvinceDTO;
import hieplh.init.InitContextMap;
import hieplh.listener.InitListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class AdvanceServlet extends HttpServlet {

    private final String ADVANCE_PAGE = "advance.jsp";

    private final String CAREER = "career";
    private final String PROVINCE = "province";

    private final String TEXT = "text";
    private final String CHART = "chart";

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

        String url = ADVANCE_PAGE;
        String type = request.getParameter("type");

        String page = request.getParameter("page");
        page = initPage(page);

        String display = request.getParameter("display");
        display = initDisplay(display);

        try {
            ServletContext context = this.getServletContext();

            Map<String, String> mapCareers = (Map<String, String>) context.getAttribute("ALL_CAREERS");
            Map<String, String> mapProvinces = (Map<String, String>) context.getAttribute("ALL_PROVINCES");
            if (mapCareers == null || mapProvinces == null) {
                InitContextMap contextMap = new InitContextMap();
                contextMap.initMap();
                mapCareers = contextMap.getMapCareers();
                mapProvinces = contextMap.getMapProvinces();

                context.setAttribute("ALL_CAREERS", mapCareers);
                context.setAttribute("ALL_PROVINCES", mapProvinces);
            }

            Map<String, List<CareerAndProvinceDTO>> mapCurrentCareersLocateProvince = (Map<String, List<CareerAndProvinceDTO>>) context.getAttribute("MAP_CURRENT_CAREERS_LOCATE_PROVINCE");
            Map<String, List<CareerAndProvinceDTO>> mapCurrentProvincesGotCareer = (Map<String, List<CareerAndProvinceDTO>>) context.getAttribute("MAP_CURRENT_PROVINCES_GOT_CAREER");
            if (mapCurrentCareersLocateProvince == null || mapCurrentProvincesGotCareer == null) {
                InitContextMap contextMap = new InitContextMap();
                contextMap.initMapAdvance();
                mapCurrentCareersLocateProvince = contextMap.getMapCurrentCareersLocateProvince();
                mapCurrentProvincesGotCareer = contextMap.getMapCurrentProvincesGotCareer();
            }

            Map<String, List<CareerAndProvinceDTO>> mapResult = null;

            int pages = 0;
            switch (type) {
                case CAREER:
                    mapResult = processCareer(mapResult, mapCareers, mapCurrentProvincesGotCareer, mapCurrentCareersLocateProvince, page);
                    pages = initPages(mapCareers.size());
                    break;
                case PROVINCE:
                    mapResult = processProvince(mapResult, mapProvinces, mapCurrentCareersLocateProvince, page);
                    pages = initPages(mapProvinces.size());
                    break;
                default:
                    break;
            }

            request.setAttribute("PAGE", page);
            request.setAttribute("DISPLAY", display);
            request.setAttribute("PAGES", pages);
            request.setAttribute("TYPE", type);

            HttpSession session = request.getSession();
            session.setAttribute("ADVANCE_MAP_RESULT", mapResult);
        } catch (NumberFormatException e) {
            log("ADVANCE_SERVLET_NUMBER_FORMAT_EXCEPTION: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String initPage(String page) {
        return page != null ? page : "1";
    }

    private String initDisplay(String display) {
        return display != null ? display : TEXT;
    }

    private int initPages(int records) {
        if (records % 3 == 0) {
            return records / 3;
        }
        return records / 3 + 1;
    }

    private Map<String, List<CareerAndProvinceDTO>> processCareer(Map<String, List<CareerAndProvinceDTO>> mapResult, Map<String, String> mapCareers, Map<String, List<CareerAndProvinceDTO>> mapCurrentProvincesGotCareer, Map<String, List<CareerAndProvinceDTO>> mapCurrentCareersLocateProvince, String page) {
        int index = 0;
        int count = 0;
        boolean flag = false;
        for (Map.Entry<String, String> entry : mapCareers.entrySet()) {
            if ((Integer.parseInt(page) - 1) * 3 == index) {
                if (mapResult == null) {
                    mapResult = new HashMap<>();
                }
                flag = true;
            }

            if (flag) {
                List<CareerAndProvinceDTO> list = mapCurrentProvincesGotCareer.get(entry.getKey());
                for (CareerAndProvinceDTO province : list) {
                    List<CareerAndProvinceDTO> listCurrentCareersLocateProvince = mapCurrentCareersLocateProvince.get(province.getId());
                    double total = 0;
                    for (CareerAndProvinceDTO career : listCurrentCareersLocateProvince) {
                        total += career.getCount();
                    }
                    double percent = Math.round(province.getCount() / total * 1000) / 1000.0;
                    province.setPercent(percent);
                }

                mapResult.put(entry.getKey(), list);
                if (++count == 3) {
                    break;
                }
            }
            index++;
        }

        return mapResult;
    }

    private Map<String, List<CareerAndProvinceDTO>> processProvince(Map<String, List<CareerAndProvinceDTO>> mapResult, Map<String, String> mapProvinces, Map<String, List<CareerAndProvinceDTO>> mapCurrentCareersLocateProvince, String page) {
        int index = 0;
        int count = 0;
        boolean flag = false;
        boolean isCalculateTotal = false;
        for (Map.Entry<String, String> entry : mapProvinces.entrySet()) {
            if ((Integer.parseInt(page) - 1) * 3 == index) {
                if (mapResult == null) {
                    mapResult = new HashMap<>();
                }
                flag = true;
            }

            if (flag) {
                double total = 0;
                List<CareerAndProvinceDTO> list = mapCurrentCareersLocateProvince.get(entry.getKey());
                if (!isCalculateTotal) {
                    for (CareerAndProvinceDTO career : list) {
                        total += career.getCount();
                    }
                } else {
                    isCalculateTotal = true;
                }

                for (CareerAndProvinceDTO career : list) {
                    double percent = Math.round(career.getCount() / total * 10000) / 10000.0;
                    career.setPercent(percent);
                }
                mapResult.put(entry.getKey(), list);
                if (++count == 3) {
                    break;
                }
            }
            index++;
        }

        return mapResult;
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
