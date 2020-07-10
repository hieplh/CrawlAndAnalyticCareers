/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.dao;

import hieplh.dto.CareerDTO;
import hieplh.sqlquery.SQLStatement;
import hieplh.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class CareerDAO implements Serializable {

    public List<CareerDTO> getTop10HotCareer() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CareerDTO> list = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(SQLStatement.SELECT_TOP_10_HOT_CAREER);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new CareerDTO(rs.getString("id"), rs.getString("name"), rs.getInt("COUNT")));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return list;
    }

    public String getTop10HotCareerDetail(String id, String page) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(SQLStatement.SELECT_TOP_10_HOT_CAREER_DETAIL);
                stmt.setString(1, id);
                stmt.setString(2, page);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(rs.getString(1));
                    return sb.toString();
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public List<CareerDTO> getTop10lowestRecruitCareer() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CareerDTO> list = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(SQLStatement.SELECT_TOP_10_LOWEST_RECRUIT_CAREER);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new CareerDTO(rs.getString("id"), rs.getString("name"), rs.getInt("COUNT")));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return list;
    }

    public String getTop10lowestRecruitCareerDetail(String id, String page) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(SQLStatement.SELECT_TOP_10_LOWEST_RECRUIT_CAREER_DETAIL);
                stmt.setString(1, id);
                stmt.setString(2, page);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(rs.getString(1));
                    return sb.toString();
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public List<CareerDTO> getAvgSalaryOfCareers() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CareerDTO> list = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(SQLStatement.SELECT_AVG_SALARY_OF_CAREERS);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }

                    CareerDTO dto = new CareerDTO(rs.getString("id"), rs.getString("name"), rs.getString("SALARY"));
                    if (!list.contains(dto)) {
                        list.add(dto);
                    }
                }
                Collections.sort(list);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return list;
    }

    public int getTotalRecordOfHotCareer(String id) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(SQLStatement.SELECT_TOTAL_RECORD_HOT_CAREER);
                stmt.setString(1, id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return 0;
    }

    public int getTotalRecordOfLowestCareer(String id) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(SQLStatement.SELECT_TOTAL_RECORD_LOWEST_RECRUITMENT_CAREER);
                stmt.setString(1, id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return 0;
    }

    public Map<String, String> getAllCareer() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Map<String, String> map = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(SQLStatement.SELECT_ALL_CAREER);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    if (map == null) {
                        map = new HashMap<>();
                    }
                    map.put(rs.getString(1), rs.getString(2));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return map;
    }
}
