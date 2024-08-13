import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class App {
    private static final String RESET = "\033[0m"; // Text Reset
    private static final String BLUE = "\033[34m"; // BLUE
    private static final String GREEN = "\033[32m"; // GREEN
    private static final String RED = "\033[31m"; // RED
    private static final String CYAN = "\033[36m"; // CYAN
    private static final String YELLOW = "\033[33m"; // YELLOW
    private static final String BOLD = "\033[1m"; // BOLD

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(BOLD + BLUE + "***************************************************" + RESET);
            System.out.println(BOLD + CYAN + "  WELCOME TO THE LIFE PROGNOSIS TOOL" + RESET);
            System.out.println(BOLD + BLUE + "***************************************************" + RESET);
            System.out.println(BLUE + "1) " + RESET + "Login");
            System.out.println(BLUE + "2) " + RESET + "Complete your registration (Patient)");
            System.out.println(BLUE + "3) " + RESET + "Exit");

            int choice = getUserChoice(scanner, 1, 3);

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

    private static int getUserChoice(Scanner scanner, int min, int max) {
        int choice;
        while (true) {
            System.out.print(YELLOW + "Please enter your choice: " + RESET);
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println(RED + "Invalid choice. Please enter a number between " + min + " and " + max + "." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Invalid input. Please enter a valid number." + RESET);
            }
        }
    }

    public static void login(Scanner scanner) {
        System.out.println(BOLD + CYAN + "\nLOGIN" + RESET);

        System.out.print(YELLOW + "Enter your email: " + RESET);
        String email = scanner.nextLine();

        String password = getPassword(scanner);

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

                handleUserMenu(scanner, result.split(","));
            } else {
                System.out.println(RED + "Login failed. Please check your credentials and try again." + RESET);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(RED + "Error in execution." + RESET);
        }
    }

    private static String getPassword(Scanner scanner) {
        Console console = System.console();
        String password;

        if (console != null) {
            // Use the Console to read the password without echoing characters to the console
            char[] passwordArray = console.readPassword(YELLOW + "Enter your password: " + RESET);
            password = new String(passwordArray);
        } else {
            // Fallback for environments where Console is not available (e.g., some IDEs)
            System.out.print(YELLOW + "Enter your password: " + RESET);
            password = scanner.nextLine();
        }

        return password;
    }

    private static void handleUserMenu(Scanner scanner, String[] parts) {
        String role = parts[3];
        if ("ADMIN".equals(role)) {
            System.out.println(BOLD + CYAN + "\nADMIN MENU" + RESET);
            Admin newadmin = new Admin(parts[1], parts[2], parts[3], parts[5]);

            while (true) {
                System.out.println(BLUE + "1) " + RESET + "Initiate Patient Registration");
                System.out.println(BLUE + "2) " + RESET + "Download Patient Data");
                System.out.println(BLUE + "3) " + RESET + "Download Statistics");
                System.out.println(BLUE + "4) " + RESET + "Logout");

                int choice = getUserChoice(scanner, 1, 4);

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

            handlePatientMenu(scanner, parts);
        }
    }

    private static void handlePatientMenu(Scanner scanner, String[] parts) {
        String uuidCode = parts[13];
        LocalDate dateOfBirth = LocalDate.parse(parts[6]);
        boolean hasHiv = Boolean.parseBoolean(parts[7]);
        LocalDate dateOfDiagnosis = LocalDate.parse(parts[8]);
        boolean isOnArtDrugs = Boolean.parseBoolean(parts[9]);
        LocalDate dateOfArtDrugs = LocalDate.parse(parts[10]);
        String countryOfResidence = parts[11];

        Patient newpatient = new Patient(parts[1], parts[2], parts[4], parts[5], uuidCode, dateOfBirth, hasHiv,
                dateOfDiagnosis, isOnArtDrugs, dateOfArtDrugs, countryOfResidence, parts[12]);

        while (true) {
            System.out.println(BLUE + "1) " + RESET + "View Profile");
            System.out.println(BLUE + "2) " + RESET + "Update Profile");
            System.out.println(BLUE + "3) " + RESET + "Logout");

            int choice = getUserChoice(scanner, 1, 3);

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

    public static void completeRegistration(Scanner scanner) {
        System.out.println(BOLD + CYAN + "\nREGISTRATION" + RESET);

        String uuidCode = getInput(scanner, "Enter your UUID: ");
        String firstName = getInput(scanner, "Enter your first name: ");
        String lastName = getInput(scanner, "Enter your last name: ");
        String email = getInput(scanner, "Enter your email: ");
        String password = getPassword(scanner);
        String countryOfResidence = getInput(scanner, "Enter your country of residence: ");
        LocalDate dateOfBirth = getDateInput(scanner, "Enter your date of birth (YYYY-MM-DD): ");

        boolean hasHiv = getYesNoInput(scanner, "Do you have HIV? (1 for Yes, 2 for No): ");

        LocalDate dateOfDiagnosis = LocalDate.parse("1970-01-01");
        boolean isOnArtDrugs = false;
        LocalDate dateOfArtDrugs = LocalDate.parse("1970-01-01");

        if (hasHiv) {
            dateOfDiagnosis = getDateInput(scanner, "Enter your date of diagnosis (YYYY-MM-DD): ");
            isOnArtDrugs = getYesNoInput(scanner, "Are you on ART drugs? (1 for Yes, 2 for No): ");
            if (isOnArtDrugs) {
                dateOfArtDrugs = getDateInput(scanner, "Enter your date of ART drugs start (YYYY-MM-DD): ");
            }
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "../bash/user-manager.sh", "complete_registration",
                    uuidCode, firstName, lastName, email, password,
                    dateOfBirth.toString(), Boolean.toString(hasHiv), dateOfDiagnosis.toString(),
                    Boolean.toString(isOnArtDrugs), dateOfArtDrugs.toString(), countryOfResidence);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
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

    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(YELLOW + prompt + RESET);
        return scanner.nextLine().trim();
    }

    private static LocalDate getDateInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(YELLOW + prompt + RESET);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println(RED + "Invalid date format. Please enter the date in YYYY-MM-DD format." + RESET);
            }
        }
    }

    private static boolean getYesNoInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(YELLOW + prompt + RESET);
            String input = scanner.nextLine().trim();
            if (input.equals("1")) {
                return true;
            } else if (input.equals("2")) {
                return false;
            } else {
                System.out.println(RED + "Invalid choice. Please enter 1 for Yes or 2 for No." + RESET);
            }
        }
    }
}
