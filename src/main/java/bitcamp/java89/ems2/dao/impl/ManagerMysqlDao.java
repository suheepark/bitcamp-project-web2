package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bitcamp.java89.ems2.dao.ManagerDao;
import bitcamp.java89.ems2.domain.Manager;
import bitcamp.java89.ems2.util.DataSource;

public class ManagerMysqlDao implements ManagerDao {
  
  DataSource ds;
  
  public void setDataSource(DataSource ds) {
    this.ds = ds;
  }
  
  public ArrayList<Manager> getList() throws Exception {
    ArrayList<Manager> list = new ArrayList<>();
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select mno,name,tel,email,posi,fax,path "
            + "from mgr left outer join memb on mgr.mrno=memb.mno");
        ResultSet rs = stmt.executeQuery();
        )
    {
      while (rs.next()) {
        Manager manager = new Manager();
        manager.setMemberNo(rs.getInt("mno"));
        manager.setName(rs.getString("name"));
        manager.setTel(rs.getString("tel"));
        manager.setEmail(rs.getString("email"));
        manager.setPosition(rs.getString("posi"));
        manager.setFaxNo(rs.getString("fax"));
        manager.setPhotoPath(rs.getString("path"));
        list.add(manager);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }
  
  public boolean exist(String email) throws Exception {
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select count(*) cnt from mgr left outer join memb "
            + "on mgr.mrno=memb.mno where email=?");
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
  
  public boolean exist(int memberNo) throws Exception {
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select count(*) cnt from mgr left outer join memb on mgr.mrno=memb.mno where mrno=?");
        )
    {
      stmt.setInt(1, memberNo);
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
  
  public Manager getOne(int memberNo) throws Exception {
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select name,tel,email,posi,fax,path "
          + "from mgr left outer join memb on mgr.mrno=memb.mno "
          + "where mno=?");)
    {
      stmt.setInt(1, memberNo);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Manager manager = new Manager();
        manager.setMemberNo(memberNo);
        manager.setName(rs.getString("name"));
        manager.setTel(rs.getString("tel"));
        manager.setEmail(rs.getString("email"));
        manager.setPosition(rs.getString("posi"));
        manager.setFaxNo(rs.getString("fax"));
        manager.setPhotoPath(rs.getString("path"));
        rs.close();
        return manager;
        
      } else {
        rs.close();
        return null;
      }
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void insert(Manager manager) throws Exception {
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "insert into mgr(mrno,posi,fax,path) values(?,?,?,?)");
        )
    {
      stmt.setInt(1, manager.getMemberNo());
      stmt.setString(2, manager.getPosition());
      stmt.setString(3, manager.getFaxNo());
      stmt.setString(4, manager.getPhotoPath());
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void update(Manager manager) throws Exception {
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "update mgr set posi=?, fax=?, path=? where mrno=?");
        )
    {
      stmt.setString(1, manager.getPosition());
      stmt.setString(2, manager.getFaxNo());
      stmt.setString(3, manager.getPhotoPath());
      stmt.setInt(4, manager.getMemberNo());
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void delete(int memberNo) throws Exception {
    Connection con = ds.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement("delete from mgr where mrno=?");
        )
    {
      stmt.setInt(1, memberNo);
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }    
  }
  
}
