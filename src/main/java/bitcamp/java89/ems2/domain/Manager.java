package bitcamp.java89.ems2.domain;

public class Manager extends Member {
  private static final long serialVersionUID = 1L;
  
  protected String position;
  protected String faxNo;
  protected String photoPath;
  
  public String getPosition() {
    return position;
  }
  public void setPosition(String position) {
    this.position = position;
  }
  public String getFaxNo() {
    return faxNo;
  }
  public void setFaxNo(String faxNo) {
    this.faxNo = faxNo;
  }
  public String getPhotoPath() {
    return photoPath;
  }
  public void setPhotoPath(String photoPath) {
    this.photoPath = photoPath;
  }

}
