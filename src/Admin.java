import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Admin extends User{

    // Constructor
    public Admin(String firstName, String lastName, String email, String password){
        super(firstName, lastName, email, password,UserRole.ADMIN);
    }

    @Override
    public boolean login(String email, String password){
        try{
            ProcessBuilder pb = new ProcessBuilder("bash", "./bash/user-manager.sh", "login", email, password);
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
            return output.toString().trim().equals("success");
        } catch(Exception e){
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

    public static void downloadAllUsers() {
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "../bash/user-manager.sh", "download_all_users");
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            PrintWriter writer = new PrintWriter("../data/all_users.csv", "UTF-8");
            String line;
            while ((line = reader.readLine()) != null) {
                writer.println(line);
            }
            writer.close();
            process.waitFor();
            System.out.println("All users downloaded successfully to ../data/users.csv");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in execution");
        }
    }

    public static void downloadStatistics() {
        try {
            PrintWriter writer = new PrintWriter("../data/statistics.csv", "UTF-8");
            // Write headers for the statistics CSV
            writer.println("Statistic,Value");
            // Currently empty, to be filled with actual statistics later
            writer.close();
            System.out.println("Statistics downloaded successfully to ../data/statistics.csv");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in execution");
        }
    }
}
