package com.spark.web;

import com.spark.dao.VideoAccessTopNDAO;
import com.spark.domain.VideoAccessTopN;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by kimvra on 2019-04-30
 */
public class VideoAccessTopNServlet extends HttpServlet {

    private VideoAccessTopNDAO videoAccessTopNDAO;

    @Override
    public void init() throws ServletException {
        videoAccessTopNDAO = new VideoAccessTopNDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String day = req.getParameter("day");

        List<VideoAccessTopN> results = videoAccessTopNDAO.query(day);
        JSONArray jsonArray = JSONArray.fromObject(results);

        resp.setContentType("text/html;charset=utf-8");

        PrintWriter printWriter = resp.getWriter();
        printWriter.println(jsonArray);
        printWriter.flush();
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
