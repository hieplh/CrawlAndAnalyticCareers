/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.controller;

import hieplh.crawl.dao.CareerDAO;
import hieplh.crawl.dao.ProvinceDAO;
import hieplh.jaxb.area.Areas;
import hieplh.jaxb.job.Job;
import hieplh.jaxb.job.Jobs;
import hieplh.thread.CallableImp;
import hieplh.thread.CrawlRunableImp;
import hieplh.utils.XMLUtils;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Admin
 */
public class CrawlServlet extends HttpServlet {

    private final String HOME_PAGE = "crawl.html";
    private String xml = "WEB-INF\\Xml\\Careers.xml";
    private String xslDirPath = "WEB-INF\\Xslt\\";
    private String schemaDirPath = "WEB-INF\\Schema\\";

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
            String realPath = this.getServletContext().getRealPath("/");
            xml = realPath + xml;
            xslDirPath = realPath + xslDirPath;
            String link = getParam(xml, "link");
            String paramTinhThanh = getParam(xml, "tinhthanh");
            String paramNganhNghe = getParam(xml, "nganhnghe");

            String xslJobs = "Jobs.xsl";
            String xslAreas = "Areas.xsl";
            String xslJobDetail = "JobDetail.xsl";

            Jobs jobs = null;
            Areas areas = null;

            ExecutorService executorService = Executors.newFixedThreadPool(2);
            List<Future<?>> futures = new ArrayList<>();
            futures.add(executorService.submit(new CallableImp<>(xml, xslDirPath, xslJobs, new Jobs())));
            futures.add(executorService.submit(new CallableImp(xml, xslDirPath, xslAreas, new Areas())));

            for (Future<?> f : futures) {
                try {
                    if (f.get() instanceof Jobs) {
                        jobs = (Jobs) f.get();
                        CareerDAO dao = new CareerDAO();
                        try {
                            int[] size = dao.insert(jobs.getList());
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    if (f.get() instanceof Areas) {
                        areas = (Areas) f.get();
                        ProvinceDAO dao = new ProvinceDAO();
                        try {
                            int[] size = dao.insert(areas.getList());
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    log("CRAWL_SERVLET: " + ex.getMessage());
                }
            }
            executorService.shutdown();

            ExecutorService service = Executors.newFixedThreadPool(4);

            List<Future<?>> futureJobs = new ArrayList<>();
            for (Job job : jobs.getList()) {
                String paramJob = job.getValue();
                String paramArea = areas.getList().get(areas.getList().size() - 1).getValue();
                String linkPage = link
                        + "&" + paramNganhNghe + "=" + paramJob
                        + "&" + paramTinhThanh + "=" + paramArea;
                String dirName = job.getName().replace("/", "-");

                futureJobs.add(service.submit(new CrawlRunableImp(dirName, linkPage, xml, xslDirPath + "\\" + xslJobDetail, realPath + schemaDirPath + "JobDetailsSchema.xsd")));
            }

            for (Future<?> futureJob : futureJobs) {
                try {
                    futureJob.get();
                } catch (ExecutionException | InterruptedException e) {
                    log("FUTURE_ERROR: " + e.getMessage());
                }
            }

            service.shutdown();
            request.setAttribute("CRAWL", "CRAWL");
        } catch (Exception e) {
            log("CRAWL_SERVLET: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String getParam(String filePath, String param) {
        try {
            XMLEventReader reader = XMLUtils.createEventReader(new StreamSource(new File(filePath)));

            boolean found = false;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement element = (StartElement) event;

                    Attribute attr = element.getAttributeByName(new QName(param));
                    if (attr != null) {
                        return attr.getValue();
                    }

                    if (element.getName().getLocalPart().equals(param)) {
                        found = true;
                    }
                }

                if (event.isCharacters()) {
                    Characters characters = (Characters) event;

                    if (found) {
                        return characters.getData();
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
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
