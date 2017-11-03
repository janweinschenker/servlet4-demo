package de.holisticon.servlet4demo.serverglassfish;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = {"/http2-json"})
public class Http2JsonServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    resp.setContentType("application/json");
    try (PrintWriter respWriter = resp.getWriter()) {
      respWriter.write("{\"id\":39,\"content\":\"Hello, push!\"}");
    }

  }
}
