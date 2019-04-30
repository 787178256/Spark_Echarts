package com.spark.dao;

import com.spark.domain.VideoAccessTopN;
import com.spark.utils.MySQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kimvra on 2019-04-30
 */
public class VideoAccessTopNDAO {
    static Map<String, String> courses = new HashMap<>();

    static {
        courses.put("4000", "MySQL优化");
        courses.put("4500", "Crontab");
        courses.put("4600", "Swift");
        courses.put("14540", "SpringData");
        courses.put("14704", "R");
        courses.put("14390", "机器学习");
        courses.put("14322", "redis");
        courses.put("14623", "Docker");
    }


    /**
     * 根据课程编号求课程名称
     */
    public String getCourseName(String id) {
        return courses.get(id);
    }

    /**
     * 根据day查询当天最受欢迎的top5课程
     */
    public List<VideoAccessTopN> query(String day) {
        List<VideoAccessTopN> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySQLUtil.getConnection();
            String sql = "select cms_id, times from day_video_access_topn_stat where day = ? order by times desc limit 5";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, day);

            resultSet = preparedStatement.executeQuery();
            VideoAccessTopN videoAccessTopN = null;
            while (resultSet.next()) {
                videoAccessTopN = new VideoAccessTopN();
                /**
                 * 用map模拟数据库中的课程编号和课程名字
                 */
                videoAccessTopN.setName(getCourseName(resultSet.getLong("cms_id") + ""));
                videoAccessTopN.setValue(resultSet.getLong("times"));
                list.add(videoAccessTopN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MySQLUtil.release(connection, preparedStatement, resultSet);
        }

        return list;
    }

    public static void main(String[] args) {
        VideoAccessTopNDAO videoAccessTopNDAO = new VideoAccessTopNDAO();
        List<VideoAccessTopN> list = videoAccessTopNDAO.query("20170511");
        for (VideoAccessTopN video : list) {
            System.out.println(video.getName() + " , " + video.getValue());
        }
    }

}
