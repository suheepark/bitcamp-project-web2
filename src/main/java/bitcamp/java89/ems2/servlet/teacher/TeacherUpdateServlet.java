package bitcamp.java89.ems2.servlet.teacher;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.impl.MemberMysqlDao;
import bitcamp.java89.ems2.dao.impl.TeacherMysqlDao;
import bitcamp.java89.ems2.domain.Teacher;

@WebServlet("/teacher/update")
public class TeacherUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  ServletConfig config;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    try {
      request.setCharacterEncoding("UTF-8");
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      
      Teacher teacher = new Teacher();
      teacher.setMemberNo(Integer.parseInt(request.getParameter("memberNo")));
      teacher.setEmail(request.getParameter("email"));
      teacher.setPassword(request.getParameter("password"));
      teacher.setName(request.getParameter("name"));
      teacher.setTel(request.getParameter("tel"));
      teacher.setHomepage(request.getParameter("homepage"));
      teacher.setFacebook(request.getParameter("facebook"));
      teacher.setTwitter(request.getParameter("twitter"));
      
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>강사관리-변경</title>");
      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("</head>");
      out.println("<body>");
      
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>변경 결과</h1>");
    
      TeacherMysqlDao teacherDao = TeacherMysqlDao.getInstance();
      
      if (!teacherDao.exist(teacher.getMemberNo())) {
        throw new Exception("회원번호를 찾지 못했습니다.");
      }
      
      MemberMysqlDao memberDao = MemberMysqlDao.getInstance();
      memberDao.update(teacher);
      teacherDao.update(teacher);
      
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
