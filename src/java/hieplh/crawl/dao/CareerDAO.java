/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.crawl.dao;

import hieplh.jaxb.job.Job;
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
public class CareerDAO implements Serializable {

    public int[] insert(List<Job> list) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        int arr[] = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                con.setAutoCommit(false);
                stmt = con.prepareStatement(SQLStatement.INSERT_CAREER);

                for (Job job : list) {
                    stmt.setString(1, job.getValue());
                    stmt.setString(2, job.getName());
                    stmt.addBatch();
                }
                arr = stmt.executeBatch();
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
        return arr;
    }
}
