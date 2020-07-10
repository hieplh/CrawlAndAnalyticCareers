/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.init;

import hieplh.dao.CareerAndProvinceDAO;
import hieplh.dao.CareerDAO;
import hieplh.dao.ProvinceDAO;
import hieplh.dto.CareerAndProvinceDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class InitContextMap {

    Map<String, String> mapCareers;
    Map<String, String> mapProvinces;
    Map<String, List<CareerAndProvinceDTO>> mapCurrentCareersLocateProvince;
    Map<String, List<CareerAndProvinceDTO>> mapCurrentProvincesGotCareer;

    public void initMap() {
        CareerDAO careerDAO = new CareerDAO();
        ProvinceDAO provinceDAO = new ProvinceDAO();

        try {
            mapCareers = careerDAO.getAllCareer();
            mapProvinces = provinceDAO.getAllProvince();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger("ADVANCE_SERVLET_CLASS_NOT_FOUND_EXCEPTION: " + ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger("ADVANCE_SERVLET_SQL_EXCEPTION: " + ex.getMessage());
        }
    }

    public void initMapAdvance() {
        CareerAndProvinceDAO dao = new CareerAndProvinceDAO();
        mapCurrentCareersLocateProvince = new HashMap<>();
        mapCurrentProvincesGotCareer = new HashMap<>();

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<?>> futures = new ArrayList<>();

        futures.add(executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    for (Map.Entry<String, String> entry : mapCareers.entrySet()) {
                        mapCurrentProvincesGotCareer.put(entry.getKey(), dao.getCurrentProvincesGotCareer(entry.getKey()));
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger("ADVANCE_SERVLET_CLASS_NOT_FOUND_EXCEPTION: " + ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger("ADVANCE_SERVLET_SQL_EXCEPTION: " + ex.getMessage());
                }
            }
        }));
        futures.add(executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    for (Map.Entry<String, String> entry : mapProvinces.entrySet()) {
                        mapCurrentCareersLocateProvince.put(entry.getKey(), dao.getCurrentCareersLocateProvince(entry.getKey()));
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger("ADVANCE_SERVLET_CLASS_NOT_FOUND_EXCEPTION: " + ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger("ADVANCE_SERVLET_SQL_EXCEPTION: " + ex.getMessage());
                }
            }
        }));

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException | InterruptedException e) {
                Logger.getLogger("ADVANCE_SERVLET: " + e.getMessage());
            }
        }
        executorService.shutdown();
    }

    public Map<String, String> getMapCareers() {
        return mapCareers;
    }

    public Map<String, String> getMapProvinces() {
        return mapProvinces;
    }

    public Map<String, List<CareerAndProvinceDTO>> getMapCurrentCareersLocateProvince() {
        return mapCurrentCareersLocateProvince;
    }

    public Map<String, List<CareerAndProvinceDTO>> getMapCurrentProvincesGotCareer() {
        return mapCurrentProvincesGotCareer;
    }
}
