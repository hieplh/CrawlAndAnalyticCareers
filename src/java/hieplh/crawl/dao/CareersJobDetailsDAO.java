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
public class CareersJobDetailsDAO implements Serializable {

    public void insert(String jobId, List<String> listCareer) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                con.setAutoCommit(false);
                stmt = con.prepareStatement(SQLStatement.INSERT_CAREERS_JOB_DETAILS);

                for (String career : listCareer) {
                    stmt.setString(1, career);
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
