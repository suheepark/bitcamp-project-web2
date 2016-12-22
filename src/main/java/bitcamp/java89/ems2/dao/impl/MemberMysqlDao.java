package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import bitcamp.java89.ems2.dao.MemberDao;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.util.DataSource;

public class MemberMysqlDao implements MemberDao {
  
  DataSource ds;
  
  public void setDataSource(DataSource ds) {
    this.ds = ds;
  }
  
  @Override
  public boolean exist(String email) throws Exception {
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select count(*) cnt from memb where email=?");
        )
    {
      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();
      
      rs.next();
      int count = rs.getInt("cnt");
      rs.close();
      
      if (count > 0) {
        return true;
      } else {
        return false;
      }
    } finally {
      ds.returnConnection(con);
    }
  }

  @Override
  public void insert(Member member) throws Exception {
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "insert into memb(name, pwd, tel, email) values(?,password(?),?,?);", 
            Statement.RETURN_GENERATED_KEYS);
        )
    {
      stmt.setString(1, member.getName());
      stmt.setString(2, member.getPassword());
      stmt.setString(3, member.getTel());
      stmt.setString(4, member.getEmail());
      stmt.executeUpdate();
      ResultSet keyRS = stmt.getGeneratedKeys();
      keyRS.next();
      member.setMemberNo(keyRS.getInt(1));
      keyRS.close();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void update(Member member) throws Exception {
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "update memb set email=?, pwd=password(?), name=?, tel=? where mno=?");
        )
    {
      stmt.setString(1, member.getEmail());
      stmt.setString(2, member.getPassword());
      stmt.setString(3, member.getName());
      stmt.setString(4, member.getTel());
      stmt.setInt(5, member.getMemberNo());
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void delete(int memberNo) throws Exception {
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement("delete from memb where mno=?");
        )
    {
      stmt.setInt(1, memberNo);
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }    
  }
  
  public Member getOne(String email) throws Exception {
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select mno,name,tel,email from memb where email=?");)
    {
      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Member member = new Member();
        member.setMemberNo(rs.getInt("mno"));
        member.setName(rs.getString("name"));
        member.setTel(rs.getString("tel"));
        member.setEmail(rs.getString("email"));
        rs.close();
        return member;
      } else {
        rs.close();
        return null;
      }
    } finally {
      ds.returnConnection(con);
    }
  }

}
