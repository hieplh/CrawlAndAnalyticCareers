/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.crawl.dao;

import hieplh.jaxb.jobdetail.Details;
import hieplh.jaxb.jobdetail.Job;
import hieplh.sqlquery.SQLStatement;
import hieplh.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author Admin
 */
public class JobDetailDAO implements Serializable {

    public String[] insert(String jobId, List<Job> list, int index) throws ClassNotFoundException, SQLException, ParseException {
        Connection con = null;
        PreparedStatement stmt = null;
        String[] arrId = new String[list.size()];

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                con.setAutoCommit(false);
                stmt = con.prepareStatement(SQLStatement.INSERT_JOB_DETAIL);

                int size = 0;
                for (Job job : list) {
                    ++index;
                    stmt.setString(1, jobId + "-" + Integer.toString(index));
                    stmt.setString(2, job.getJobtitle());
                    stmt.setString(3, job.getCompany());
                    stmt.setDate(13, convertStringToDate(job.getSubmission()));

                    Details detail = job.getDetails();
                    stmt.setString(4, detail.getAddress());
                    stmt.setString(5, detail.getSalary());
                    stmt.setString(6, detail.getExperience());
                    stmt.setString(7, detail.getLevel());
                    stmt.setString(8, detail.getRecruitment());
                    stmt.setString(9, detail.getGender());
                    stmt.setString(10, detail.getNatureOfWork());
                    stmt.setString(11, detail.getFormOfWork());
                    stmt.setString(12, detail.getTry());

                    stmt.addBatch();
                    
                    arrId[size] = jobId + "-" + Integer.toString(index);
                    size++;
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
        return arrId;
    }
    
    private Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-yyyy");
        return new Date(format.parse(dateString).getTime());
    }
}
