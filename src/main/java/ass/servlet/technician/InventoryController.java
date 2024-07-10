package ass.servlet.technician;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.IOException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ass.bean.UserBean;
import ass.db.DataBase;
import ass.db.InventoryDB;
import ass.utils.IDUtil;
import ass.utils.ServletParam;
import ass.utils.ServletUtil;

/**
 *
 * @author LYF00
 */
@WebServlet(name = "InventoryController", urlPatterns = { "/technician/inventory" })
public class InventoryController extends HttpServlet {

    private InventoryDB inventoryDB;
    private DataBase db;

    @Override
    public void init() {
        db = new DataBase();
        inventoryDB = new InventoryDB(db);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletParam<String> itemId = ServletUtil.getStringParam(request, "id");
        ServletParam<Integer> qty = ServletUtil.getIntParam(request, "qty");
        UserBean u = (UserBean) session.getAttribute("user");
        String campusId = IDUtil.getUserCampusId(u.getId(), db);

        if (!ServletUtil.checkEmptyParams(response, itemId)) {
            return;
        }
        if (qty.getValue() <= 0) {
            session.setAttribute("msg", "Quantity Must > 0");
            ServletUtil.redirectTo(this, response, "technician/inventoryList.jsp");
            return;
        }
        if (!inventoryDB.addInventory(campusId, itemId.getValue(), qty.getValue())) {
            session.setAttribute("msg", "Server Busy, Try Again Later");
            ServletUtil.redirectTo(this, response, "technician/inventoryList.jsp");
            return;
        }
        session.setAttribute("msg", "Item(s) added successfully");
        ServletUtil.redirectTo(this, response, "technician/inventoryList.jsp");
        return;

    }

}
