import java.time.LocalDate;

public class Patient extends User {
    private String uuidCode;
    private LocalDate dateOfBirth;
    private boolean hasHiv;
    private LocalDate dateOfDiagnosis;
    private boolean isOnArtDrugs;
    private LocalDate dateOfArtDrugs;
    private String countryOfResidence;
    private String life_expectacy;

    // Constructor
    public Patient(String firstName, String lastName, String email, String password, String uuidCode,
            LocalDate dateOfBirth, boolean hasHiv, LocalDate dateOfDiagnosis, boolean isOnArtDrugs,
            LocalDate dateOfArtDrugs, String countryOfResidence, String life_expectacy) {
        super(firstName, lastName, email, password, UserRole.PATIENT);
        this.uuidCode = uuidCode;
        this.dateOfBirth = dateOfBirth;
        this.hasHiv = hasHiv;
        this.dateOfDiagnosis = dateOfDiagnosis;
        this.isOnArtDrugs = isOnArtDrugs;
        this.dateOfArtDrugs = dateOfArtDrugs;
        this.countryOfResidence = countryOfResidence;
        this.life_expectacy = life_expectacy;
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

    public boolean hasHiv() {
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

    public String getLifeExpectacy() {
        return life_expectacy;
    }

    public void setLifeExpectacy(String life_expectancy) {
        this.life_expectacy = life_expectancy;
    }

    @Override
    public boolean login(String email, String password) {
        // Implement actual login logic here
        // For demonstration, simply checking if the email and password match the
        // current object's details
        return this.getEmail().equals(email) && this.getPassword().equals(password);
    }

    public void displayProfile() {
        System.out.println("===== Patient Profile =====");
        System.out.println("UUID: " + uuidCode);
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Email: " + getEmail());
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Has HIV: " + (hasHiv ? "Yes" : "No"));
        if (hasHiv) {
            System.out.println("Date of Diagnosis: " + dateOfDiagnosis);
            System.out.println("On ART Drugs: " + (isOnArtDrugs ? "Yes" : "No"));
            if (isOnArtDrugs) {
                System.out.println("Date ART Drugs Started: " + dateOfArtDrugs);
            }
        }
        System.out.println("Country of Residence: " + countryOfResidence);
        System.out.println("Life expectancy:" + life_expectacy);
        System.out.println("===========================\n");
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
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role=" + getRole() +
                '}';
    }
}
