import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("Welcome to life prognosis\n1) Login \n2) Complete your registration (Patient)\n3)Exit");
            Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    completeRegistration(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");
            }

        }
    }

    public static void login(Scanner scanner) {
        System.out.println("Enter your email:");

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

            //System.out.println(result + "\n");

            if (result.startsWith("success")) {
                System.out.println("Login successful");

                String[] parts = result.split(",");
                String role = parts[3];
                if ("ADMIN".equals(role)) {
                    System.out.println("Logged in as Admin");
                    // System.out.println(parts);

                    while (true) {
                        Admin newadmin = new Admin(parts[1], parts[2], parts[3], parts[5]);
                        System.out.println(
                                "1) Initiate Patient Registration \n2) Download Patient Data \n3) Download Statistics \n 4)Logout");

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
                            case 4:
                                return;
                            default:
                                System.out.println("Invalid choice");
                        }

                    }

                } else if ("PATIENT".equals(role)) {

                    String uuidCode = parts[13];
                    LocalDate dateOfBirth = LocalDate.parse(parts[6]);
                    boolean hasHiv = Boolean.parseBoolean(parts[7]);
                    LocalDate dateOfDiagnosis = LocalDate.parse(parts[8]);
                    boolean isOnArtDrugs = Boolean.parseBoolean(parts[9]);
                    LocalDate dateOfArtDrugs = LocalDate.parse(parts[10]);
                    String countryOfResidence = parts[11];

                    System.out.println("Logged in as Patient");
                    //System.out.println(parts);
                    while (true) {

                        Patient newpatient = new Patient(parts[1], parts[2], parts[4], parts[5], uuidCode, dateOfBirth,
                                hasHiv, dateOfDiagnosis, isOnArtDrugs, dateOfArtDrugs, countryOfResidence, parts[12]);
                        System.out.println("1) View Profile \n2) Update Profile \n3)Logout");
                        int choice = Integer.parseInt(scanner.nextLine());
                        switch (choice) {
                            case 1:
                                // display patient profile nicely
                                newpatient.displayProfile();
                                break;
                            case 2:
                                // same method used in user registration
                                completeRegistration(scanner);
                                break;
                            case 3:
                                return;

                            default:
                                System.out.println("Invalid choice");
                        }
                    }

                }
            } else {
                System.out.println("Login failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in execution");
        }
    }

    public static void completeRegistration(Scanner scanner) {

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

        System.out.println("Enter your country of residence:");
        String countryOfResidence = scanner.nextLine();

        System.out.println("Enter your date of birth (YYYY-MM-DD):");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());

        System.out.println("Do you have HIV? (true/false):");
        boolean hasHiv = Boolean.parseBoolean(scanner.nextLine());

        LocalDate dateOfDiagnosis;
        boolean isOnArtDrugs;
        LocalDate dateOfArtDrugs;

        if (hasHiv) {
            System.out.println("Enter your date of diagnosis (YYYY-MM-DD):");
            dateOfDiagnosis = LocalDate.parse(scanner.nextLine());

            System.out.println("Are you on ART drugs? (true/false):");
            isOnArtDrugs = Boolean.parseBoolean(scanner.nextLine());

            if (isOnArtDrugs) {
                System.out.println("Enter your date of ART drugs start (YYYY-MM-DD):");
                dateOfArtDrugs = LocalDate.parse(scanner.nextLine());
            } else {
                dateOfArtDrugs = LocalDate.parse("1970-01-01");
            }
        } else {
            // If the user does not have HIV, assume all subsequent values are false/empty.
            dateOfDiagnosis = LocalDate.parse("1970-01-01");
            isOnArtDrugs = false;
            dateOfArtDrugs = LocalDate.parse("1970-01-01");
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "../bash/user-manager.sh", "complete_registration", uuidCode,
                    firstName, lastName, email, password, dateOfBirth.toString(), Boolean.toString(hasHiv),
                    dateOfDiagnosis.toString(), Boolean.toString(isOnArtDrugs), dateOfArtDrugs.toString(),
                    countryOfResidence);
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
