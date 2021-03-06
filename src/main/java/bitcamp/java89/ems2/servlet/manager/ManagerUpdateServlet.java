package bitcamp.java89.ems2.servlet.manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.ManagerDao;
import bitcamp.java89.ems2.dao.MemberDao;
import bitcamp.java89.ems2.domain.Manager;

@WebServlet("/manager/update")
public class ManagerUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  ServletConfig config;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    try {
      request.setCharacterEncoding("UTF-8");
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      
      Manager manager = new Manager();
      manager.setMemberNo(Integer.parseInt(request.getParameter("memberNo")));
      manager.setEmail(request.getParameter("email"));
      manager.setPassword(request.getParameter("password"));
      manager.setName(request.getParameter("name"));
      manager.setTel(request.getParameter("tel"));
      manager.setPosition(request.getParameter("position"));
      manager.setFaxNo(request.getParameter("faxNo"));
      manager.setPhotoPath(request.getParameter("photoPath"));
      
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>매니저관리-변경</title>");
      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("</head>");
      out.println("<body>");
      
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>변경 결과</h1>");
    
      ManagerDao managerDao = (ManagerDao)this.getServletContext().getAttribute("managerDao");
      
      if (!managerDao.exist(manager.getMemberNo())) {
        throw new Exception("회원번호를 찾지 못했습니다.");
      }
      
      MemberDao memberDao = (MemberDao)this.getServletContext().getAttribute("memberDao");
      memberDao.update(manager);
      managerDao.update(manager);
      
      out.println("<p>변경하였습니다.</p>");
      
      rd = request.getRequestDispatcher("/footer");
      rd.include(request, response);
      
      out.println("</body>");
      out.println("</html>");
      
    } catch (Exception e) {
      RequestDispatcher rd = request.getRequestDispatcher("/error");
      rd.forward(request, response);
      return;
    }
  }
  
}
