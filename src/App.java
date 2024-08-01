import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to life prognosis\n 1)Login \n 2)Complete your registration (Patient)");
        Scanner scanner=new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());

        switch(choice){
            case 1 :
                Login();
            case 2 :
                // initiatePatientRegistration();
        }

    }
    public static void Login(){
        System.out.println("Enter your email");
        Scanner scanner=new Scanner(System.in);
        String email;
        email=scanner.nextLine();
        System.out.println("Enter your password");
        String password;
        password=scanner.nextLine();
        try{
            ProcessBuilder pb = new ProcessBuilder("bash","../bash/user-manager.sh","login",email,password);
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
            String result=output.toString();
            if (result=="success"){
                System.out.println(result);
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("error in execution");
        }

        // Admin admin = new Admin("","",email,password);
        // Login(email, password);
    }
}
