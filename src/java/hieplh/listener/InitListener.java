/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.listener;

import hieplh.dto.CareerAndProvinceDTO;
import hieplh.init.InitContextMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Admin
 */
public class InitListener implements ServletContextListener {

    Map<String, String> mapCareers;
    Map<String, String> mapProvinces;
    Map<String, List<CareerAndProvinceDTO>> mapCurrentCareersLocateProvince;
    Map<String, List<CareerAndProvinceDTO>> mapCurrentProvincesGotCareer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        context.setAttribute("REAL_PATH", context.getRealPath("/"));

        InitContextMap initContextMap = new InitContextMap();
        initContextMap.initMap();
        initContextMap.initMapAdvance();

        mapCareers = initContextMap.getMapCareers();
        mapProvinces = initContextMap.getMapProvinces();
        mapCurrentCareersLocateProvince = initContextMap.getMapCurrentCareersLocateProvince();
        mapCurrentProvincesGotCareer = initContextMap.getMapCurrentProvincesGotCareer();
        
        context.setAttribute("ALL_CAREERS", mapCareers);
        context.setAttribute("ALL_PROVINCES", mapProvinces);

        context.setAttribute("MAP_CURRENT_PROVINCES_GOT_CAREER", mapCurrentProvincesGotCareer);
        context.setAttribute("MAP_CURRENT_CAREERS_LOCATE_PROVINCE", mapCurrentCareersLocateProvince);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
