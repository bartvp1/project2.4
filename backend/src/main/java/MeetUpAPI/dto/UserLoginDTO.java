package MeetUpAPI.dto;

public class UserLoginDTO {
  @Override
  public String toString() {
    return "{" +
            "username:'" + username + '\'' +
            ", password:'" + password + '\'' +
            '}';
  }

  private String username;
  private String password;

  public String getUsername() {
    return username;
  }
  public String getPassword() {
    return password;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  public void setPassword(String password) {
    this.password = password;
  }

}
