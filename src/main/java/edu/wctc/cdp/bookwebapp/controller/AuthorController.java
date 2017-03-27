/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.controller;

import edu.wctc.cdp.bookwebapp.db.accessor.MySqlDBAccessor;
import edu.wctc.cdp.bookwebapp.model.Author;
import edu.wctc.cdp.bookwebapp.model.AuthorDao;
import edu.wctc.cdp.bookwebapp.model.AuthorDaoInterface;
import edu.wctc.cdp.bookwebapp.model.AuthorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Palmer
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {
    
    
    private String driverClass;
    private String url;
    private String username;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
    private String jndiName;
    
    
    
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
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String destination = HOME_PAGE;
        String action = request.getParameter(ACTION_PARAM);  
        
        
        //AuthorService authorService = new AuthorService(
        //                                    new AuthorDao(new MySqlDBAccessor(),
        //                                            driverClass,
        //                                            url, 
        //                                            username,
        //                                            password));                                          
        try {
           AuthorService authorService = injectDependenciesAndGetAuthorService();
           switch (action){
                case LIST_ACTION:
                    List<Author> authors = authorService.getAllAuthors("author");
                    request.setAttribute("authors", authors);
                    destination = LIST_PAGE;
                    break;
                case DELETE_ACTION:
                    Integer authorID = Integer.parseInt(request.getParameter("authorID"));                   
                    authorService.deleteAuthor(authorID);
                    destination = HOME_PAGE;
                    break;
                case UPDATE_ACTION:
                    String authorName = request.getParameter("author_name");
                    Integer authorID2 = Integer.parseInt(request.getParameter("authorID"));
                        // WTF DO I DO
                    authorService.updateAuthor(authorName, authorID2);
                    
                    destination = HOME_PAGE;
                    break;                
                case ADD_ACTION:                    
                    String name = request.getParameter("author_name");                   
                    authorService.addAuthor(name);
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
    private AuthorService injectDependenciesAndGetAuthorService() throws Exception {
        // Use Liskov Substitution Principle and Java Reflection to
        // instantiate the chosen DBStrategy based on the class name retrieved
        // from web.xml
        Class dbClass = Class.forName(dbStrategyClassName);
        // Use Java reflection to instanntiate the DBStrategy object
        // Note that DBStrategy classes have no constructor params
        DBAccessor db = (DBAccessor) dbClass.newInstance();

        // Use Liskov Substitution Principle and Java Reflection to
        // instantiate the chosen DAO based on the class name retrieved above.
        // This one is trickier because the available DAO classes have
        // different constructor params
        AuthorDaoInterface authorDao = null;
        Class daoClass = Class.forName(daoClassName);
        Constructor constructor = null;
        
        // This will only work for the non-pooled AuthorDao
        try {
            constructor = daoClass.getConstructor(new Class[]{
                DBAccessor.class, String.class, String.class, String.class, String.class
            });
        } catch(NoSuchMethodException nsme) {
            // do nothing, the exception means that there is no such constructor,
            // so code will continue executing below
        }

        // constructor will be null if using connectin pool dao because the
        // constructor has a different number and type of arguments
        
        if (constructor != null) {
            // conn pool NOT used so constructor has these arguments
            Object[] constructorArgs = new Object[]{
                db, driverClass, url, username, password
            };
            authorDao = (AuthorDaoInterface) constructor.newInstance(constructorArgs);

        } else {
            /*
             Here's what the connection pool version looks like. First
             we lookup the JNDI name of the Glassfish connection pool
             and then we use Java Refletion to create the needed
             objects based on the servlet init params
             */
            Context ctx = new InitialContext();
            //Context envCtx = (Context) ctx.lookup("java:comp/env");
            DataSource ds = (DataSource) ctx.lookup(jndiName);
            constructor = daoClass.getConstructor(new Class[]{
                DataSource.class, DBAccessor.class
            });
            Object[] constructorArgs = new Object[]{
                ds, db
            };

            authorDao = (AuthorDaoInterface) constructor
                    .newInstance(constructorArgs);
        }
        
        return new AuthorService(authorDao);
    }

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
        driverClass = getServletContext().getInitParameter("db.driverClass");
        url = getServletContext().getInitParameter("db.url");
        username = getServletContext().getInitParameter("db.username");
        password = getServletContext().getInitParameter("db.password");  
        dbStrategyClassName = getServletContext().getInitParameter("dbStrategy");
        daoClassName = getServletContext().getInitParameter("authorDao");
        jndiName = getServletContext().getInitParameter("connPoolName");

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
