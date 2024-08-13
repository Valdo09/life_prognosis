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
        String reset = "\033[0m"; // Text Reset
        String blue = "\033[34m"; // BLUE
        String green = "\033[32m"; // GREEN
        String red = "\033[31m"; // RED
        String bold = "\033[1m"; // BOLD
        String underline = "\033[4m"; // UNDERLINE

        System.out.println(bold + underline + blue + "===== Patient Profile =====" + reset);
        System.out.println(bold + blue + "UUID: " + reset + uuidCode);
        System.out.println(bold + blue + "Name: " + reset + getFirstName() + " " + getLastName());
        System.out.println(bold + blue + "Email: " + reset + getEmail());
        System.out.println(bold + blue + "Date of Birth: " + reset + dateOfBirth);
        System.out.println(bold + blue + "Has HIV: " + reset + (hasHiv ? green + "Yes" + reset : red + "No" + reset));

        if (hasHiv) {
            System.out.println(bold + blue + "Date of Diagnosis: " + reset + dateOfDiagnosis);
            System.out.println(bold + blue + "On ART Drugs: " + reset
                    + (isOnArtDrugs ? green + "Yes" + reset : red + "No" + reset));

            if (isOnArtDrugs) {
                System.out.println(bold + blue + "Date ART Drugs Started: " + reset + dateOfArtDrugs);
            }
        }

        System.out.println(bold + blue + "Country of Residence: " + reset + countryOfResidence);
        System.out.println(bold + blue + "Life Expectancy: " + reset + life_expectacy);
        System.out.println(bold + underline + blue + "===========================" + reset + "\n");
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
