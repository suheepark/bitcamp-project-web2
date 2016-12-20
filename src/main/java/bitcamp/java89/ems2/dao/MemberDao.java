package bitcamp.java89.ems2.dao;

import bitcamp.java89.ems2.domain.Member;

public interface MemberDao {
  void insert(Member member) throws Exception;
  boolean exist(String email) throws Exception;
}
