import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class User{

    public enum UserRole{
        ADMIN, PATIENT
    }
    //Attributes
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    // private UserRole role;


    //Constructor
    public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        // this.role=role;
    }

    //Setters
    public void setFirstName(String firstName){
        this.firstName = firstName;
        
    }
    public void setLastNameName(String lastName){
        this.lastName = lastName;
        
    }
    public void setEmail(String email){
        this.email = email;
        
    }
    public void setPassword(String password){
        this.password = password;
        
    }

    //Getters
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
    //login

    public abstract boolean login(String email, String password);
        


}