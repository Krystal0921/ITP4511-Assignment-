/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ass.servlet.technician;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ass.db.DataBase;
import ass.db.ItemDB;
import ass.utils.ServletParam;
import ass.utils.ServletUtil;

/**
 *
 * @author LYF00
 */
@WebServlet(name = "ItemController", urlPatterns = {"/technician/Item"})
@SuppressWarnings("unchecked")
public class ItemController extends HttpServlet {

    private ItemDB itemDb;
    private DataBase db;
    private HttpSession session;

    @Override
    public void init() {
        db = new DataBase();
        itemDb = new ItemDB(db);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            session = request.getSession();
            ServletParam<String> action = ServletUtil.getStringParam(request, "action");
            if (!ServletUtil.checkEmptyParams(response, action)) {
                return;
            }
            switch (action.getValue()) {
                case "create":
                    doCreateItem(request, response);
                    break;
                case "edit":
                    doEditItem(request, response);
                    break;
                case "delete":
                    doDeleteItem(request, response);
                    break;
                default:
                    session.setAttribute("error", "Invalid action");
                    ServletUtil.redirectTo(this, response, "index.jsp");
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doCreateItem(HttpServletRequest request, HttpServletResponse response) {
        ServletParam<String> equipmentName = ServletUtil.getStringParam(request, "equipmentName");
        ServletParam<String> type = ServletUtil.getStringParam(request, "type");
        ServletParam<String> userType = ServletUtil.getStringParam(request, "userType");
        if (!ServletUtil.checkEmptyParams(response, equipmentName, type, userType)) {
            return;
        }

        String itemId = ass.utils.IDUtil.genID("item", "i", db);
        boolean inserted = itemDb.createItem(itemId, equipmentName.getValue(), type.getValue(), userType.getValue(), "1");

        String successMessage;
        String redirectURL;

        if (inserted) {
            successMessage = "Create item success";
            redirectURL = "itemList.jsp";
        } else {
            successMessage = "Cannot create item, please try again!";
            redirectURL = "itemList.jsp";
        }

        String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL + "';</script>";

        try {
            response.setContentType("text/html");
            response.getWriter().println(script);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private void doEditItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String equipmentName = request.getParameter("equipmentName");
        String campus = request.getParameter("campus");
        String type = request.getParameter("type");
        String userType = request.getParameter("userType");

        String itemId = request.getParameter("itemId");

        boolean edited = itemDb.updateItem(itemId, equipmentName, type, userType);
        if (edited) {
            String successMessage = "Edit item success";
            String redirectURL = "itemList.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        } else {
            String successMessage = "Cannot edit item, please try again!";
            String redirectURL = "editItemForm.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        }
    }

    private void doDeleteItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String itemId = request.getParameter("itemId");

        boolean deleted = itemDb.deleteItem(itemId);
        if (deleted) {
            String successMessage = "Delete item success";
            String redirectURL = "itemList.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        } else {
            String successMessage = "Cannot Delete item, please try again!";
            String redirectURL = "itemList.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        }
    }

}
