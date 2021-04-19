package com.practica.userJava.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.practica.userJava.entity.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ResponseLogin
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-04-15T20:17:04.861Z[GMT]")


public class ResponseLogin   {
  @JsonProperty("user")
  private User user = null;

  @JsonProperty("authToken")
  private String authToken = null;

  public ResponseLogin user(User user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   * @return user
   **/
  
    @Valid
    public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public ResponseLogin authToken(String authToken) {
    this.authToken = authToken;
    return this;
  }

  /**
   * Get authToken
   * @return authToken
   **/
  
    public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseLogin responseLogin = (ResponseLogin) o;
    return Objects.equals(this.user, responseLogin.user) &&
        Objects.equals(this.authToken, responseLogin.authToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, authToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseLogin {\n");
    
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    authToken: ").append(toIndentedString(authToken)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
