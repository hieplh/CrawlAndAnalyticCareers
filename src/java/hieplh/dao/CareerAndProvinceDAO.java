/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.dao;

import hieplh.dto.CareerAndProvinceDTO;
import hieplh.sqlquery.SQLStatement;
import hieplh.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CareerAndProvinceDAO implements Serializable {

    public List<CareerAndProvinceDTO> getCurrentCareersLocateProvince(String idProvince) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CareerAndProvinceDTO> list = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(SQLStatement.SELECT_CURENT_CAREERS_LOCATE_PROVINCE);
                stmt.setString(1, idProvince);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new CareerAndProvinceDTO(rs.getString("id"), rs.getString("name"), rs.getInt("COUNT")));
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
    
    public List<CareerAndProvinceDTO> getCurrentProvincesGotCareer(String idProvince) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CareerAndProvinceDTO> list = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(SQLStatement.SELECT_CURENT_PROVINCES_GOT_CAREER);
                stmt.setString(1, idProvince);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new CareerAndProvinceDTO(rs.getString("id"), rs.getString("name"), rs.getInt("COUNT")));
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
}
