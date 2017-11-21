package de.holisticon.servlet4demo.serverglassfish;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;

@WebServlet(value = {"/http2"})
public class Http2Servlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html;charset=UTF-8");
    PushBuilder pushBuilder = req.newPushBuilder();
    if (pushBuilder != null) {
      pushBuilder
          .path("images/cat.png")
          .addHeader("content-type", "image/jpeg")
          .push();
      pushBuilder
          .path("http2-json")
          .addHeader("content-type", "application/json")
          .push();
    }
    try (PrintWriter respWriter = resp.getWriter()) {
      respWriter.write(new StringBuilder()
                           .append("<html>")
                           .append("<img src='images/cat.jpg'>")
                           .append("<p>Image by <a href=\"https://flic.kr/p/HPf9R1\">")
                           .append("Andy Miccone</a></p>")
                           .append("<p>License: <a href=\"https://creativecommons.org/")
                           .append("publicdomain/zero/1.0/\">")
                           .append("CC0 1.0 Universal (CC0 1.0) \n")
                           .append("Public Domain Dedication</a></p>")
                           .append("</html>")
                           .toString());
    }
  }
}
