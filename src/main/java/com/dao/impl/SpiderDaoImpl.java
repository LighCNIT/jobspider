package com.dao.impl;

import com.dao.SpiderDao;
import com.model.SpiderInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.*;

/**
 * @Auther: Ligh
 * @Date: 2018/12/9 15:50
 * @Description:
 */
@Repository
public class SpiderDaoImpl implements SpiderDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private Connection conn = null;
    private Statement stmt = null;

    public SpiderDaoImpl() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3309/spider?"
                    + "user=root&password=admin";
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int addSpider(SpiderInfo spider) {
        try {
            String sql = " INSERT INTO spider (url,post,salary,company,JD,jobAddress) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, spider.getUrl());
            ps.setString(2, spider.getPost());
            ps.setString(3, spider.getSalary());
            ps.setString(4, spider.getCompany());
            ps.setString(5, spider.getJD());
            ps.setString(6, spider.getJobAddress());
            return ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
}
