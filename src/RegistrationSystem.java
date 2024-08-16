import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class RegistrationSystem {
    private String userFile;

    public RegistrationSystem(String userFile) {
        this.userFile = userFile;
    }

    public void saveUser(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.write(user.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        try {
            Admin admin = new Admin("Alice", "Smith", "alice@example.com", "password123");
            RegistrationSystem registrationSystem = new RegistrationSystem("users.txt");

            // Admin generates UUID code for patient
            String patientEmail = "john.doe@example.com";
            String uuidCode = admin.generatePatientCode(patientEmail);
            System.out.println("Generated UUID Code: " + uuidCode);

            // Patient uses the UUID code to complete registration
            Patient patient = new Patient(
                    "John",
                    "Doe",
                    patientEmail,
                    "securepassword",
                    uuidCode,
                    LocalDate.of(1990, 1, 1),
                    true,
                    LocalDate.of(2010, 5, 20),
                    true,
                    LocalDate.of(2010, 6, 1),
                    "CountryX"
            );

            registrationSystem.saveUser(patient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
