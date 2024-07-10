package ass.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletUtil {

  // For Int Param
  public static ServletParam<Integer> getIntParam(HttpServletRequest request, String name, Integer defaultValue) {
    ServletParam<Integer> param = new ServletParam<>(name, defaultValue);
    String value = request.getParameter(name);

    if (value == null)
      return param;

    try {
      param.setValue(Integer.parseInt(value));
    } catch (NumberFormatException ex) {
      // NOOP
    }
    return param;
  }

  public static ServletParam<Integer> getIntParam(HttpServletRequest request, String name) {
    return getIntParam(request, name, null);
  }

  // For Boolean Param
  public static ServletParam<Boolean> getBoolParam(HttpServletRequest request, String name, Boolean defaultValue) {
    ServletParam<Boolean> param = new ServletParam<>(name, defaultValue);
    String value = request.getParameter(name);

    if (value == null)
      return param;

    try {
      param.setValue(Boolean.parseBoolean(value));
    } catch (NumberFormatException ex) {
      // NOOP
    }
    return param;
  }

  public static ServletParam<Boolean> getBoolParam(HttpServletRequest request, String name) {
    return getBoolParam(request, name, false);
  }

  // For String Param
  public static ServletParam<String> getStringParam(HttpServletRequest request, String name, String defaultValue) {
    ServletParam<String> param = new ServletParam<>(name, defaultValue);
    String value = request.getParameter(name);

    if (value == null)
      return param;

    param.setValue(value);
    return param;
  }

  public static ServletParam<String> getStringParam(HttpServletRequest request, String name) {
    return getStringParam(request, name, null);
  }

  public static boolean validateParams(HttpServletResponse res, IValidateValue... params) {
    if (IValidateValue.isAllValid(params))
      return true;

    ServletUtil.sendMessage(res, HttpServletResponse.SC_BAD_REQUEST, IValidateValue.getAllErrorMessage(params));
    return false;
  }

  // Validate
  @SuppressWarnings("unchecked")
  public static boolean checkEmptyParams(HttpServletResponse res, ServletParam<String>... params) {
    StringBuilder sb = new StringBuilder();
    boolean isError = false;

    for (ServletParam<String> param : params) {
      if (param.isValid() && !param.getValue().isEmpty())
        continue;

      if (isError) {
        sb.append("\n");
      } else {
        isError = true;
      }

      sb.append(String.format("Invalid value for param '%s'", param.getName()));
    }

    if (isError) {
      ServletUtil.sendMessage(res, HttpServletResponse.SC_BAD_REQUEST, sb.toString());
      return false;
    }

    return true;
  }

  public static void forwardTo(HttpServlet servlet, HttpServletRequest req, HttpServletResponse res, String page) {
    try {
      System.out.println( servlet.getServletContext().getContextPath() + " " + page);
      servlet.getServletContext().getRequestDispatcher(page).forward(req, res);
    } catch (IllegalArgumentException | ServletException | IOException ex) {
      sendMessage(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  public static void redirectTo(HttpServlet servlet, HttpServletResponse res, String dist) {
    try {
      res.sendRedirect(servlet.getServletContext().getContextPath() + "/" + dist);
    } catch (IOException ex) {
      sendMessage(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  public static boolean checkLogin(HttpSession session, String msg) {
    boolean isLogin = true;
    if ( session.getAttribute("user") == null) {
      session.setAttribute("msg", msg);
      isLogin = false;
    }
    return isLogin;
  }

  public static boolean checkLogin(HttpSession session) {
    return ServletUtil.checkLogin(session, "Please login to continue!");
  }

  public static void sendMessage(HttpServletResponse res, int status, String... msgs) {
    try {
      res.setStatus(status);
      res.setContentType("text/html;charset=UTF-8");
      PrintWriter out = res.getWriter();
      out.println("<html lang=\\\"en\\\">");
      if (status != HttpServletResponse.SC_OK) {
        out.println("<head><title>Error</title></head>");
      }
      out.println("<body><a href=\"index.jsp\">go back</a><ul>");
      for (String msg : msgs) {
        out.println("<li>" + msg + "</li>");
      }
      out.println("</ul></body></html>");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private ServletUtil() {
  }
}
