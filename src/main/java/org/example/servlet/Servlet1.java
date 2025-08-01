package org.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/")
public class Servlet1 implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }
    @Override
    public String getServletInfo() {
        return null;
    }
    @Override
    public void destroy() {

    }
    @Override
    public void service(ServletRequest req, ServletResponse res){
        System.out.println("你好世界");
    }
}
