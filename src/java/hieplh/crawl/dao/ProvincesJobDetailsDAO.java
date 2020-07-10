/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.crawl.dao;

import hieplh.sqlquery.SQLStatement;
import hieplh.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProvincesJobDetailsDAO implements Serializable {

    public void insert(String jobId, List<String> listProvince) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                con.setAutoCommit(false);
                stmt = con.prepareStatement(SQLStatement.INSERT_PROVINCES_JOB_DETAILS);

                for (String string : listProvince) {
                    stmt.setString(1, string);
                    stmt.setString(2, jobId);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                con.commit();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
