/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author ADMIN
 */
public class OrderServlet extends HttpServlet {

    OrderDAO orderdao = new OrderDAO();

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        switch (action) {
            case "getOrder":
                session.setAttribute("listorder", orderdao.getlistOrder());
                session.setAttribute("thebestuser", orderdao.getTheMostUser());
                session.setAttribute("themostselling", orderdao.getbestSelling());
                request.getRequestDispatcher("listOrder.jsp").forward(request, response);
                break;
                case"detail":
                    int id = Integer.parseInt(request.getParameter("id"));
                    session.setAttribute("listdetail", orderdao.getListDetail(id));
                    request.getRequestDispatcher("listOrderDetail.jsp").forward(request, response);

                    break;
            default:
                throw new AssertionError();
        }
    }

  
}
