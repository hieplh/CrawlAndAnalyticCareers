/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.dao;

import hieplh.sqlquery.SQLStatement;
import hieplh.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class ProvinceDAO implements Serializable{

    public Map<String, String> getAllProvince() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Map<String, String> map = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(SQLStatement.SELECT_ALL_PROVINCE);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    if (map == null) {
                        map = new HashMap<>();
                    }
                    if (!rs.getString(1).isEmpty() && !rs.getString(1).equals("null")) {
                        map.put(rs.getString(1), rs.getString(2));
                    }
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
