/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.sqlquery;

/**
 *
 * @author Admin
 */
public class SQLStatement {

    public static String INSERT_CAREER = "INSERT INTO CAREER (id, name) VALUES (?, ?)";
    public static String INSERT_PROVINCE = "INSERT INTO PROVINCE (id, name) VALUES (?, ?)";
    public static String INSERT_JOB_DETAIL = "INSERT INTO JOB_DETAIL (id, name, company, address, salary, experience,"
            + " lvl, recruitment, gender, natureOfWork, formOfWork, try, submission) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?)";
    public static String INSERT_CAREERS_JOB_DETAILS = "EXEC INSERT_CAREERS_JOB_DETAILS ?, ?";
    public static String INSERT_PROVINCES_JOB_DETAILS = "EXEC INSERT_PROVINCES_JOB_DETAILS ?, ?";

    public static String SELECT_TOP_10_HOT_CAREER = "SELECT * FROM TOP_10_HOT_CAREER";
    public static String SELECT_TOP_10_HOT_CAREER_DETAIL = "EXEC TOP_10_HOT_CAREER_DETAIL ?, ?";
    public static String SELECT_TOP_10_LOWEST_RECRUIT_CAREER = "SELECT * FROM TOP_10_LOWEST_RECRUIT_CAREER";
    public static String SELECT_TOP_10_LOWEST_RECRUIT_CAREER_DETAIL = "EXEC TOP_10_LOWEST_RECRUIT_CAREER_DETAIL ?, ?";
    public static String SELECT_AVG_SALARY_OF_CAREERS = "SELECT * FROM AVG_SALARY_OF_CAREERS\n"
            + "ORDER BY COUNT_SALARY DESC";
    
    public static String SELECT_TOTAL_RECORD_HOT_CAREER = "EXEC TOTAL_RECORD_HOT_CAREER ?";
    public static String SELECT_TOTAL_RECORD_LOWEST_RECRUITMENT_CAREER = "EXEC TOTAL_RECORD_LOWEST_RECRUITMENT_CAREER ?";
    
    public static String SELECT_ALL_CAREER = "SELECT id, name FROM CAREER";
    public static String SELECT_ALL_PROVINCE = "SELECT id, name FROM PROVINCE";
    
    public static String SELECT_CURENT_PROVINCES_GOT_CAREER = "EXEC CURENT_PROVINCES_GOT_CAREER ?";
    public static String SELECT_CURENT_CAREERS_LOCATE_PROVINCE = "EXEC CURENT_CAREERS_LOCATE_PROVINCE ?";
}
