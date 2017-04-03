/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.controller;


import edu.wctc.cdp.bookwebapp.model.Author;
import edu.wctc.cdp.bookwebapp.model.AuthorFacade;
import edu.wctc.cdp.bookwebapp.model.Book;
import edu.wctc.cdp.bookwebapp.model.BookFacade;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Palmer
 */
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {   
    
    private static final String ERR_MSG = "No parameter detected";
    private static final String LIST_PAGE = "/bookList.jsp";
    private static final String ADD_PAGE = "/addBook.jsp";
    private static final String UPDATE_PAGE = "/updateBook.jsp";
    private static final String DELETE_PAGE = "/deleteBook.jsp";
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
    private BookFacade bookService;
    
    @EJB
    private AuthorFacade authorService;
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String destination = HOME_PAGE;
        String action = request.getParameter(ACTION_PARAM);  
                                                                                           
        try {           
           switch (action){
                case LIST_ACTION:
                    List<Book> books = bookService.findAll();
                    request.setAttribute("books", books);
                    destination = LIST_PAGE;
                    break;
                case DELETE_ACTION:                                      
                    bookService.deleteById(request.getParameter("bookID"));
                    destination = HOME_PAGE;
                    break;
                case UPDATE_ACTION:
                    String isbn = request.getParameter("isbn");
                    String bookTitle = request.getParameter("title");                    
                    String authorId = request.getParameter("authorId");
                        
                    bookService.update(request.getParameter("bookId"), bookTitle, isbn, authorId);
                    
                    destination = HOME_PAGE;
                    break;                
                case ADD_ACTION:                    
                    String title = request.getParameter("title");
                    String bookIsbn = request.getParameter("isbn");
                    //Author authorEntity = request.getParameter("authorEntity");
                    
                    bookService.addNew(title, bookIsbn);//, authorEntity);
                    destination = HOME_PAGE;                    
                    break;
                case ADDSHOW_ACTION:
                    List<Author> authors = authorService.findAll();
                    request.setAttribute("authors", authors);                    
                    destination = ADD_PAGE;
                    break;
                case UPDATESHOW_ACTION:   
                    List<Book> bookUpdate = bookService.findAll();
                    request.setAttribute("bookUpdate", bookUpdate);                                       
                    destination = UPDATE_PAGE;
                    break;
                case DELETESHOW_ACTION:
                    List<Book> bookDelete = bookService.findAll();
                    request.setAttribute("bookDelete", bookDelete);
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
            throws ServletException, IOException {
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
