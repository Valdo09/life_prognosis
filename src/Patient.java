import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Scanner;
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

    // Getters and setters
    public String getUuidCode() {
        return uuidCode;
    }

    public void setUuidCode(String uuidCode) {
        this.uuidCode = uuidCode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isHasHiv() {
        return hasHiv;
    }

    public void setHasHiv(boolean hasHiv) {
        this.hasHiv = hasHiv;
    }

    public LocalDate getDateOfDiagnosis() {
        return dateOfDiagnosis;
    }

    public void setDateOfDiagnosis(LocalDate dateOfDiagnosis) {
        this.dateOfDiagnosis = dateOfDiagnosis;
    }

    public boolean isOnArtDrugs() {
        return isOnArtDrugs;
    }

    public void setOnArtDrugs(boolean onArtDrugs) {
        isOnArtDrugs = onArtDrugs;
    }

    public LocalDate getDateOfArtDrugs() {
        return dateOfArtDrugs;
    }

    public void setDateOfArtDrugs(LocalDate dateOfArtDrugs) {
        this.dateOfArtDrugs = dateOfArtDrugs;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

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
