import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Admin extends User{

    //Cobstructor
    public Admin(String firstName, String lastName, String email, String password){
        super(firstName,lastName,email,password);
    }
    
    @Override
    public boolean login(String email, String password){
        try{
            ProcessBuilder pb = new ProcessBuilder("bash","./bash/user-manager.sh","login",email,password);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line=reader.readLine())!=null) {
                output.append(line);
            }
            process.waitFor();
            System.out.println(output.toString());
            return output.toString().trim().equals("success");  
            
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static void initiatePatientRegistration() {
        System.out.println("Enter patient's email:");
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();

        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "../bash/user-manager.sh", "initiate_patient_registration", email);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            process.waitFor();
            System.out.println(output.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in execution");
        }
    }

}