import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Scanner;

public class App {
    private static final String RESET = "\033[0m";  // Text Reset
    private static final String BLUE = "\033[34m";   // BLUE
    private static final String GREEN = "\033[32m";  // GREEN
    private static final String RED = "\033[31m";    // RED
    private static final String CYAN = "\033[36m";   // CYAN
    private static final String YELLOW = "\033[33m"; // YELLOW
    private static final String BOLD = "\033[1m";    // BOLD
    private static final String UNDERLINE = "\033[4m"; // UNDERLINE

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println(BOLD + BLUE + "***************************************************" + RESET);
            System.out.println(BOLD + CYAN + "  WELCOME TO THE LIFE PROGNOSIS TOOL" + RESET);
            System.out.println(BOLD + BLUE + "***************************************************" + RESET);
            System.out.println(BLUE + "1) " + RESET + "Login");
            System.out.println(BLUE + "2) " + RESET + "Complete your registration (Patient)");
            System.out.println(BLUE + "3) " + RESET + "Exit");

            Scanner scanner = new Scanner(System.in);
            System.out.print(YELLOW + "Please enter your choice: " + RESET);
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    completeRegistration(scanner);
                    break;
                case 3:
                    System.out.println(GREEN + "Thank you for using the Life Prognosis Tool. Goodbye!" + RESET);
                    return;
                default:
                    System.out.println(RED + "Invalid choice. Please try again." + RESET);
            }
        }
    }

    public static void login(Scanner scanner) {
        System.out.println(BOLD + CYAN + "\nLOGIN" + RESET);
        System.out.print(YELLOW + "Enter your email: " + RESET);
        String email = scanner.nextLine();
        System.out.print(YELLOW + "Enter your password: " + RESET);
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

            if (result.startsWith("success")) {
                System.out.println(GREEN + "Login successful!" + RESET);

                String[] parts = result.split(",");
                String role = parts[3];
                if ("ADMIN".equals(role)) {
                    System.out.println(BOLD + CYAN + "\nADMIN MENU" + RESET);
                    Admin newadmin = new Admin(parts[1], parts[2], parts[3], parts[5]);

                    while (true) {
                        System.out.println(BLUE + "1) " + RESET + "Initiate Patient Registration");
                        System.out.println(BLUE + "2) " + RESET + "Download Patient Data");
                        System.out.println(BLUE + "3) " + RESET + "Download Statistics");
                        System.out.println(BLUE + "4) " + RESET + "Logout");

                        System.out.print(YELLOW + "Enter your choice: " + RESET);
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
                                System.out.println(GREEN + "Logging out..." + RESET);
                                return;
                            default:
                                System.out.println(RED + "Invalid choice. Please try again." + RESET);
                        }
                    }

                } else if ("PATIENT".equals(role)) {
                    System.out.println(BOLD + CYAN + "\nPATIENT MENU" + RESET);

                    String uuidCode = parts[13];
                    LocalDate dateOfBirth = LocalDate.parse(parts[6]);
                    boolean hasHiv = Boolean.parseBoolean(parts[7]);
                    LocalDate dateOfDiagnosis = LocalDate.parse(parts[8]);
                    boolean isOnArtDrugs = Boolean.parseBoolean(parts[9]);
                    LocalDate dateOfArtDrugs = LocalDate.parse(parts[10]);
                    String countryOfResidence = parts[11];

                    Patient newpatient = new Patient(parts[1], parts[2], parts[4], parts[5], uuidCode, dateOfBirth,
                            hasHiv, dateOfDiagnosis, isOnArtDrugs, dateOfArtDrugs, countryOfResidence, parts[12]);

                    while (true) {
                        System.out.println(BLUE + "1) " + RESET + "View Profile");
                        System.out.println(BLUE + "2) " + RESET + "Update Profile");
                        System.out.println(BLUE + "3) " + RESET + "Logout");

                        System.out.print(YELLOW + "Enter your choice: " + RESET);
                        int choice = Integer.parseInt(scanner.nextLine());
                        switch (choice) {
                            case 1:
                                newpatient.displayProfile();
                                break;
                            case 2:
                                completeRegistration(scanner);
                                break;
                            case 3:
                                System.out.println(GREEN + "Logging out..." + RESET);
                                return;
                            default:
                                System.out.println(RED + "Invalid choice. Please try again." + RESET);
                        }
                    }
                }
            } else {
                System.out.println(RED + "Login failed. Please check your credentials and try again." + RESET);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(RED + "Error in execution." + RESET);
        }
    }

    public static void completeRegistration(Scanner scanner) {
        System.out.println(BOLD + CYAN + "\nREGISTRATION" + RESET);

        System.out.print(YELLOW + "Enter your UUID: " + RESET);
        String uuidCode = scanner.nextLine();

        System.out.print(YELLOW + "Enter your first name: " + RESET);
        String firstName = scanner.nextLine();

        System.out.print(YELLOW + "Enter your last name: " + RESET);
        String lastName = scanner.nextLine();

        System.out.print(YELLOW + "Enter your email: " + RESET);
        String email = scanner.nextLine();

        System.out.print(YELLOW + "Enter your password: " + RESET);
        String password = scanner.nextLine();

        System.out.print(YELLOW + "Enter your country of residence: " + RESET);
        String countryOfResidence = scanner.nextLine();

        System.out.print(YELLOW + "Enter your date of birth (YYYY-MM-DD): " + RESET);
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());

        System.out.print(YELLOW + "Do you have HIV? (1 for Yes, 2 for No): " + RESET);
        boolean hasHiv = scanner.nextLine().equals("1");

        LocalDate dateOfDiagnosis;
        boolean isOnArtDrugs;
        LocalDate dateOfArtDrugs;

        if (hasHiv) {
            System.out.print(YELLOW + "Enter your date of diagnosis (YYYY-MM-DD): " + RESET);
            dateOfDiagnosis = LocalDate.parse(scanner.nextLine());

            System.out.print(YELLOW + "Are you on ART drugs? (1 for Yes, 2 for No): " + RESET);
            isOnArtDrugs = scanner.nextLine().equals("1");

            if (isOnArtDrugs) {
                System.out.print(YELLOW + "Enter your date of ART drugs start (YYYY-MM-DD): " + RESET);
                dateOfArtDrugs = LocalDate.parse(scanner.nextLine());
            } else {
                dateOfArtDrugs = LocalDate.parse("1970-01-01");
            }
        } else {
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
            System.out.println(GREEN + output.toString() + RESET);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(RED + "Error in execution." + RESET);
        }
    }
}
