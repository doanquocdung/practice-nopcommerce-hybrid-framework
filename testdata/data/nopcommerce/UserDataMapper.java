package data.nopcommerce;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.GlobalConstants;

import java.io.File;

public class UserDataMapper {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String dob;
    private String mob;
    private String yob;
    private String gender;

    public static UserDataMapper getUserData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new File(GlobalConstants.PROJECT_PATH + "/resources/UserData.json"), UserDataMapper.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @JsonProperty("firstname")
    public String getFirstname() {
        return firstname;
    }

    @JsonProperty("lastname")
    public String getLastname() {
        return lastname;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("dob")
    public String getDob() {
        return dob;
    }

    @JsonProperty("mob")
    public String getMob() {
        return mob;
    }

    @JsonProperty("yob")
    public String getYob() {
        return yob;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }
}
