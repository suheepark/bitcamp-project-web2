package bitcamp.java89.ems2.servlet.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.impl.StudentMysqlDao;
import bitcamp.java89.ems2.domain.Student;

@WebServlet("/student/list")
public class StudentListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  ServletConfig config;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    try {
      StudentMysqlDao studentDao = StudentMysqlDao.getInstance();
      ArrayList<Student> list = studentDao.getList();
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>학생관리-목록</title>");
      out.println("</head>");
      out.println("<body>");
      
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      out.println("<h1>학생 정보</h1>");
      out.println("<a href='form.html'>추가</a><br>");
      out.println("<table border='1'>");
      out.println("<tr>");
      out.println("<th>회원번호</th><th>이름</th><th>전화</th>"
          + "<th>이메일</th><th>재직</th><th>최종학력</th><th>학교명</th>");
      out.println("</tr>");
      
      for (Student student : list) {
        out.println("<tr>");
        out.printf("<td>%d</td><td><a href='detail?memberNo=%1$d'>%s</a>"
            + "</td><td>%s</td><td>%s</td>"
            + "<td>%b</td><td>%s</td><td>%s</td>\n",
            student.getMemberNo(), student.getName(),
            student.getTel(), student.getEmail(),
            student.isWorking(), student.getGrade(),
            student.getSchoolName());
        out.println("</tr>");
      }
      
      out.println("</table>");
      
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
