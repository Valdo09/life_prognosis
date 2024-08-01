import java.time.LocalDate;

public class Patient extends User {
    private String uuidCode;
    private LocalDate dateOfBirth;
    private boolean hasHiv;
    private LocalDate dateOfDiagnosis;
    private boolean isOnArtDrugs;
    private LocalDate dateOfArtDrugs;
    private String countryOfResidence;

    public Patient(String firstName, String lastName, String email, String password, String uuidCode, LocalDate dateOfBirth, boolean hasHiv, LocalDate dateOfDiagnosis, boolean isOnArtDrugs, LocalDate dateOfArtDrugs, String countryOfResidence) {
        super(firstName, lastName, email, password, UserRole.Patient);
        this.uuidCode = uuidCode;
        this.dateOfBirth = dateOfBirth;
        this.hasHiv = hasHiv;
        this.dateOfDiagnosis = dateOfDiagnosis;
        this.isOnArtDrugs = isOnArtDrugs;
        this.dateOfArtDrugs = dateOfArtDrugs;
        this.countryOfResidence = countryOfResidence;
    }

    // Getters and setters can be added here

    @Override
    public String toString() {
        return "Patient{" +
                "uuidCode='" + uuidCode + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", hasHiv=" + hasHiv +
                ", dateOfDiagnosis=" + dateOfDiagnosis +
                ", isOnArtDrugs=" + isOnArtDrugs +
                ", dateOfArtDrugs=" + dateOfArtDrugs +
                ", countryOfResidence='" + countryOfResidence + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
