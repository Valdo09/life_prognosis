import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to life prognosis\n1) Login \n2) Complete your registration (Patient)");
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                //initiatePatientRegistration();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    public static void login() {
        System.out.println("Enter your email:");
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "../bash/user-manager.sh", "login", email, password);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            process.waitFor();
            String result = output.toString().trim();

            System.out.println(result +"\n");

            if (result.startsWith("success")) {
                System.out.println("Login successful");

                String[] parts = result.split(",");
                String role = parts[3];
                if ("ADMIN".equals(role)) {
                    System.out.println("Logged in as Admin");
                    Admin newadmin=new Admin(parts[0],parts[1],parts[2],parts[4]);
                    System.out.println("Welcome to life prognosis\n1) Initiate Patient Registration \n2) Download Patient Data \n3) Download Statistics");
                    
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                    case 1:
                        newadmin.initiatePatientRegistration();
                        break;
                    case 2:
                        
                        break;
                    default:
                        System.out.println("Invalid choice");
                    }       
                } else if ("PATIENT".equals(role)) {
                    System.out.println("Logged in as Patient");
                    // Add patient-specific functionality here
                }
            } else {
                System.out.println("Login failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in execution");
        }
    }

    
}
