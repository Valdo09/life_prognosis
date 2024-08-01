import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        while (true){
            System.out.println("Welcome to life prognosis\n1) Login \n2) Complete your registration (Patient)");
            Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    completeRegistration();
                    break;
                default:
                    System.out.println("Invalid choice");
            }

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

            System.out.println(result + "\n");

            if (result.startsWith("success")) {
                System.out.println("Login successful");

                String[] parts = result.split(",");
                String role = parts[3];
                if ("ADMIN".equals(role)) {
                    System.out.println("Logged in as Admin");
                    Admin newadmin = new Admin(parts[0], parts[1], parts[2], parts[4]);
                    System.out.println("Welcome to life prognosis\n1) Initiate Patient Registration \n2) Download Patient Data \n3) Download Statistics");

                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            newadmin.initiatePatientRegistration();
                            break;
                        case 2:
                            newadmin.downloadAllUsers();
                            break;
                        case 3:
                            newadmin.downloadStatistics();
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                } else if ("PATIENT".equals(role)) {
                    System.out.println("Logged in as Patient");
                    System.out.println("Welcome to life prognosis\n1) View Profile \n2) Update Profile");
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

    public static void completeRegistration() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your UUID:");
        String uuidCode = scanner.nextLine();

        System.out.println("Enter your first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter your last name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter your email:");
        String email = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        System.out.println("Enter your date of birth (YYYY-MM-DD):");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());

        System.out.println("Do you have HIV? (true/false):");
        boolean hasHiv = Boolean.parseBoolean(scanner.nextLine());

        System.out.println("Enter your date of diagnosis (YYYY-MM-DD):");
        LocalDate dateOfDiagnosis = LocalDate.parse(scanner.nextLine());

        System.out.println("Are you on ART drugs? (true/false):");
        boolean isOnArtDrugs = Boolean.parseBoolean(scanner.nextLine());

        System.out.println("Enter your date of ART drugs start (YYYY-MM-DD):");
        LocalDate dateOfArtDrugs = LocalDate.parse(scanner.nextLine());

        System.out.println("Enter your country of residence:");
        String countryOfResidence = scanner.nextLine();

        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "../bash/user-manager.sh", "complete_registration", uuidCode, firstName, lastName, email, password, dateOfBirth.toString(), Boolean.toString(hasHiv), dateOfDiagnosis.toString(), Boolean.toString(isOnArtDrugs), dateOfArtDrugs.toString(), countryOfResidence);
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
