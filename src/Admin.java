import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Admin extends User{

    //Cobstructor
    public Admin(String firstName, String lastName, String email, String password){
        super(firstName,lastName,email,password);
    }
    
    @Override
    public boolean login(String email, String password){
        try{
            ProcessBuilder pb = new ProcessBuilder("bash","./bash/user-manager.sh","login",email,password);
            pb.inheritIO();
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line=reader.readLine())!=null) {
                output.append(line);
            }
            process.waitFor();
            System.out.println(output.toString().trim().equals("success"));
            return output.toString().trim().equals("success");
            
            
            
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // public String initiatePatientRegistration(String email){
    //     try {
    //         ProcessBuilder pb = new ProcessBuilder("bash", "user-manager.sh", "initiate_patient_registration",email);
    //         pb.inheritIO();
    //         Process process = pb.start();
            
    //         process.waitFor();
    //     }
    //     catch(Exception e){
    //         e.printStackTrace();
    //     }
    // }

}