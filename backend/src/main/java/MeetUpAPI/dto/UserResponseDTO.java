package MeetUpAPI.dto;

public class UserResponseDTO {

  private Integer id;
  private String username;
  private String phoneNumber;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String number) {
    this.phoneNumber = number;
  }


}
