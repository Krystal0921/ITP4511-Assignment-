package ass.servlet.user;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import ass.bean.UserBean;
import ass.db.DataBase;
import ass.db.UserItemDB;
import ass.utils.ServletParam;
import ass.utils.ServletUtil;

@SuppressWarnings("unchecked")
@WebServlet(name = "itemController", urlPatterns = { "/user/item" })
public class ItemController extends HttpServlet {
    private HttpSession session;
    private UserItemDB itemDB;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletParam<String> action = ServletUtil.getStringParam(req, "action");
        ServletParam<String> toWish = ServletUtil.getStringParam(req, "toWish");
        ServletParam<String> toCheckout = ServletUtil.getStringParam(req, "toCheckout");
        session = req.getSession();

        if (!ServletUtil.checkLogin(session)) {
            ServletUtil.redirectTo(this, res, "");
            return;
        }
        if(toWish.getValue()!=null&&!toWish.getValue().isEmpty()){
            doAddWish(req,res);
            return;
        }
        if(toCheckout.getValue()!=null&&!toCheckout.getValue().isEmpty()){
            doCheckOut(req, res);
            return;
        }
        
        if (action.getValue().isEmpty()){
            ServletUtil.redirectTo(this, res, "user/item.jsp?type=all");
            return;
        }

        switch (action.getValue()) {
            case "addCart":
                doAddCart(req, res);
                break;
            case "removeWish":
                doRemoveWish(req, res);
                break;

            case "removeCart":
                doRemoveCart(req, res);
                break;
            case "checkOut":
                doCheckOut(req, res);
                break;
            default:
                return;

        }
    }
    public void doCheckOut(HttpServletRequest req, HttpServletResponse res) {

        if(req.getParameterValues("items")==null){
            session.setAttribute("msg", "No item to checkout");
            ServletUtil.redirectTo(this, res, "user/cart.jsp");
            return;
        }

        UserBean u = (UserBean) session.getAttribute("user");
        List<String>item_ids = Arrays.asList(req.getParameterValues("items"));
        ServletParam<String> pickDate = ServletUtil.getStringParam(req, "pickDate");
        ServletParam<String> returnDate = ServletUtil.getStringParam(req, "returnDate");

        if(!ServletUtil.checkEmptyParams(res, pickDate, returnDate))
            return;

        if (!itemDB.checkOut(item_ids,u.getId(),pickDate.getValue(),returnDate.getValue())) {
            session.setAttribute("msg", "Some item may not be available, try again later");
            ServletUtil.redirectTo(this, res, "user/cart.jsp");
            return;
        }

        session.setAttribute("msg", "Checkout Success");
        ServletUtil.redirectTo(this, res, "user/cart.jsp");
        return;
        
    }
    
    public void doRemoveCart(HttpServletRequest req, HttpServletResponse res) {
        ServletParam<String> item_id = ServletUtil.getStringParam(req, "id");
        if (!ServletUtil.checkEmptyParams(res, item_id))
            return;
        UserBean u = (UserBean) session.getAttribute("user");
        if (!itemDB.removeCartItem(item_id.getValue(), u.getId())) {
            session.setAttribute("msg", "Item cannot be removed from cart");
            ServletUtil.redirectTo(this, res, "user/cart.jsp");
            return;
        }
        session.setAttribute("msg", "Item is successfully removed from cart");
        ServletUtil.redirectTo(this, res, "user/cart.jsp");
        return;
    }

    public void doRemoveWish(HttpServletRequest req, HttpServletResponse res) {
        ServletParam<String> item_id = ServletUtil.getStringParam(req, "id");
        if (!ServletUtil.checkEmptyParams(res, item_id))
            return;
        UserBean u = (UserBean) session.getAttribute("user");
        if (!itemDB.removeWishItem(item_id.getValue(), u.getId())) {
            session.setAttribute("msg", "Item cannot be removed from wish list");
            ServletUtil.redirectTo(this, res, "user/wish.jsp");
            return;
        }
        session.setAttribute("msg", "Item is successfully removed from wish list");
        ServletUtil.redirectTo(this, res, "user/wish.jsp");
        return;

    }
    

    public void doAddWish(HttpServletRequest req, HttpServletResponse res) {
        ServletParam<String> item_id = ServletUtil.getStringParam(req, "toWish");
        UserBean u = (UserBean) session.getAttribute("user");
        if (!ServletUtil.checkEmptyParams(res, item_id))
            return;

        if (!itemDB.checkExist(item_id.getValue(), "item")) {
            session.setAttribute("msg", "Item" + item_id.getValue() + " not found");
            ServletUtil.redirectTo(this, res, "user/item.jsp?type=all");
            return;
        }

        if (itemDB.checkInWish(item_id.getValue(), u.getId())) {
            itemDB.removeWishItem(item_id.getValue(), u.getId());
            session.setAttribute("msg", "Item is removed");
            ServletUtil.redirectTo(this, res, "user/item.jsp?type=all");
            return;
        }

        if(!itemDB.addToWish(item_id.getValue(), u.getId())){
            session.setAttribute("msg", "Item cannot be added to wish list");
            ServletUtil.redirectTo(this, res, "user/item.jsp?type=all");
            return;
        }
        session.setAttribute("msg", "Wish list Item is successfully added ");
        ServletUtil.redirectTo(this, res, "user/item.jsp?type=all");
        return;

    }
    public void doAddCart(HttpServletRequest req, HttpServletResponse res) {
        ServletParam<String> item_id = ServletUtil.getStringParam(req, "id");
        UserBean u = (UserBean) session.getAttribute("user");
        if (!ServletUtil.checkEmptyParams(res, item_id))
            return;

        if (!itemDB.checkExist(item_id.getValue(), "item")) {
            session.setAttribute("msg", "Item" + item_id.getValue() + " not found");
            ServletUtil.redirectTo(this, res, "user/item.jsp?type=all");
            return;
        }

        if (itemDB.checkInCart(item_id.getValue(), u.getId())) {
            session.setAttribute("msg", "Item cannot be repeated!!");
            ServletUtil.redirectTo(this, res, "user/item.jsp?type=all");
            return;
        }

        boolean isAdded = itemDB.addToCart(item_id.getValue(), u.getId());
        if(!isAdded){
            session.setAttribute("msg", "Item cannot be added to cart");
            ServletUtil.redirectTo(this, res, "user/item.jsp?type=all");
            return;
        }
        session.setAttribute("msg", "Item is successfully added to cart");
        ServletUtil.redirectTo(this, res, "user/item.jsp?type=all");
        return;

    }



    public void init() {
        DataBase db = new DataBase();
        itemDB = new UserItemDB(db);
    }
}
