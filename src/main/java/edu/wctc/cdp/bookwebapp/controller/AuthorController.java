/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.controller;

import edu.wctc.cdp.bookwebapp.model.Author;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import edu.wctc.cdp.bookwebapp.db.accessor.DBAccessor;
import edu.wctc.cdp.bookwebapp.model.AuthorFacade;
import javax.ejb.EJB;

/**
 *
 * @author Palmer
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {               
        
    private static final String ERR_MSG = "No parameter detected";
    private static final String LIST_PAGE = "/authorList.jsp";
    private static final String ADD_PAGE = "/addAuthor.jsp";
    private static final String UPDATE_PAGE = "/updateAuthor.jsp";
    private static final String DELETE_PAGE = "/deleteAuthor.jsp";
    private static final String HOME_PAGE = "/index.jsp";
    private static final String DELETE_ACTION = "delete";
    private static final String DELETESHOW_ACTION = "deleteShow";
    private static final String LIST_ACTION = "list";
    private static final String UPDATE_ACTION = "update";
    private static final String UPDATESHOW_ACTION = "updateShow";
    private static final String ADD_ACTION = "add";
    private static final String ADDSHOW_ACTION = "addShow";
    private static final String ACTION_PARAM = "action";
    
    @EJB
    private AuthorFacade authorService;
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String destination = HOME_PAGE;
        String action = request.getParameter(ACTION_PARAM);  
                                                                                           
        try {           
           switch (action){
                case LIST_ACTION:
                    Author authors = authorService.find(Integer.parseInt("author_id"));
                    request.setAttribute("authors", authors);
                    destination = LIST_PAGE;
                    break;
                case DELETE_ACTION:                                      
                    authorService.deletebyId(request.getParameter("authorID"));
                    destination = HOME_PAGE;
                    break;
                case UPDATE_ACTION:
                    String authorName = request.getParameter("author_name");                    
                        // WTF DO I DO
                    authorService.update(authorName, request.getParameter("authorID"));
                    
                    destination = HOME_PAGE;
                    break;                
                case ADD_ACTION:                    
                    String name = request.getParameter("author_name");                   
                    authorService.add(name);
                    destination = HOME_PAGE;                    
                    break;
                case ADDSHOW_ACTION:
                    destination = ADD_PAGE;
                    break;
                case UPDATESHOW_ACTION:   
                    List<Author> authorUpdate = authorService.getAllAuthors("author");
                    request.setAttribute("authorUpdate", authorUpdate);                                       
                    destination = UPDATE_PAGE;
                    break;
                case DELETESHOW_ACTION:
                    List<Author> authorDelete = authorService.getAllAuthors("author");
                    request.setAttribute("authorDelete", authorDelete);
                    destination = DELETE_PAGE;
                    break;
                
                          
           }                                       
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }
        
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }
    
    /*
        This helper method just makes the code more modular and readable.
        It's single responsibility principle for a method.
    */

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
                processRequest(request, response);        
    }
    
    @Override
    public void init() throws ServletException {        
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
