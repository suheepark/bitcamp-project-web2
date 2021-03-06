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
import bitcamp.java89.ems2.domain.Manager;

@WebServlet("/manager/detail")
public class ManagerDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  ServletConfig config;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    try {
      int memberNo = Integer.parseInt(request.getParameter("memberNo"));
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>매니저관리-상세정보</title>");
      out.println("</head>");
      out.println("<body>");
      
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>매니저 정보</h1>");
      out.println("<form action='update' method='POST'>");
    
      ManagerDao managerDao = (ManagerDao)this.getServletContext().getAttribute("managerDao");
      Manager manager = managerDao.getOne(memberNo);
      
      if (manager == null) {
        throw new Exception("해당 사용자의 정보가 없습니다.");
      }
      
      out.println("<table border='1'>");
      out.printf("<tr><th>이메일</th><td><input name='email' type='text' value='%s'></td></tr>\n", manager.getEmail());
      out.printf("<tr><th>암호</th><td><input name='password' type='text' value='%s' readOnly></td></tr>\n", manager.getPassword());
      out.printf("<tr><th>이름</th><td><input name='name' type='text' value='%s'></td></tr>\n", manager.getName());
      out.printf("<tr><th>전화</th><td><input name='tel' type='text' value='%s'></td></tr>\n", manager.getTel());
      out.printf("<tr><th>직위</th><td><input name='position' type='text' value='%s'></td></tr>\n", manager.getPosition());
      out.printf("<tr><th>팩스</th><td><input name='faxNo' type='text' value='%s'></td></tr>\n", manager.getFaxNo());
      out.println("<tr><th>사진</th><td><input name='photoPath' type='file'></td></tr>");
      out.println("</table>");
      out.println("<button type='submit'>변경</button>");
      out.printf(" <a href='delete?memberNo=%d'>삭제</a>\n", manager.getMemberNo());
      out.printf("<input type='hidden' name='memberNo' value='%d'>\n", manager.getMemberNo());
      
      out.println(" <a href='list'>목록</a>");
      out.println("</form>");
      
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
