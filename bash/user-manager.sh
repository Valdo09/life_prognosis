USER_STORE='../data/user-store.txt'
COUNTRIES_CSV='../data/life-expectancy.csv'

login(){
    local email=$1
    local password=$2
    local hashedPassword=$(echo -n $password | openssl dgst -sha256 | awk '{print $2}')
    
    user_data=$(grep "$email,$hashedPassword" "$USER_STORE")
    
    if [ $? -eq 0 ]; then
        role=$(echo "$user_data")
        echo "success,$role"
    else
        echo "failure"
    fi
}

initiate_patient_registration(){
    local email=$1
    local uuid_code=$(uuidgen)
    echo ",,PATIENT,$email,,,,,,,,,$uuid_code" >> "$USER_STORE"
    
    echo "Registration successful. UUID: $uuid_code"
}

complete_registration() {
    local uuid_code=$1
    local first_name=$2
    local last_name=$3
    local email=$4
    local password=$5
    local date_of_birth=$6
    local has_hiv=$7
    local date_of_diagnosis=$8
    local is_on_art_drugs=$9
    local date_of_art_drugs=${10}
    local country_of_residence=${11}

    local hashedPassword=$(echo -n $password | openssl dgst -sha256 | awk '{print $2}')

    # Convert country_of_residence to lowercase for case-insensitive comparison
    local country_lower=$(echo "$country_of_residence" | tr '[:upper:]' '[:lower:]')

    # Find the ISO code for the given country (also convert country names in CSV to lowercase)
    local iso_code=$(awk -F, -v country="$country_lower" 'tolower($1) == country {print $6}' "$COUNTRIES_CSV")

    if [ -z "$iso_code" ]; then
        echo "Error: ISO code for country '$country_of_residence' not found."
        exit 1
    fi

    # Calculate the lifespan based on ART drug status
    local life_expectancy
    if [ "$is_on_art_drugs" == "false" ]; then
        life_expectancy=5
    else
        # Retrieve the average life expectancy for the country
        local avg_life_expectancy=$(awk -F, -v iso="$iso_code" '$6 == iso {print $7}' "$COUNTRIES_CSV")

        if [ -z "$avg_life_expectancy" ]; then
            echo "Error: Life expectancy for country '$country_of_residence' not found."
            exit 1
        fi

        # Convert avg_life_expectancy to an integer
        avg_life_expectancy=$(printf "%.0f" "$avg_life_expectancy")
        echo $avg_life_expectancy
        local birth_year=$(echo $date_of_birth | cut -d'-' -f1)
        local diagnosis_year=$(echo $date_of_diagnosis | cut -d'-' -f1)
        local art_year=$(echo $date_of_art_drugs | cut -d'-' -f1)
        local delay_years=$((art_year - diagnosis_year))
        local age=$((diagnosis_year - birth_year))

        # Apply the delay and ART drug rules
        life_expectancy=$((avg_life_expectancy - age))
        for (( i=0; i<$delay_years; i++ )); do
            life_expectancy=$(echo "scale=2; $life_expectancy * 0.9" | bc)
        done
        life_expectancy=$(echo "scale=2; $life_expectancy * 0.9" | bc) # Adjusted for initial ART year

        # Convert the result to an integer (if needed)
        life_expectancy=$(printf "%.0f" "$life_expectancy")
    fi

    # Remove the existing entry if it exists
    sed -i "/$uuid_code/d" "$USER_STORE"

    # Add the new entry with the calculated lifespan
    echo "$first_name,$last_name,PATIENT,$email,$hashedPassword,$date_of_birth,$has_hiv,$date_of_diagnosis,$is_on_art_drugs,$date_of_art_drugs,$iso_code,$life_expectancy,$uuid_code" >> "$USER_STORE"

    echo "Registration completed successfully for UUID: $uuid_code"
}


download_all_users() {
    # Set the output CSV file path
    local output_file="../data/users.csv"
    
    # Print the header
    echo "firstname,lastname,role,email,password,date_of_birth,has_hiv,date_of_diagnosis,is_on_art_drugs,date_of_art_drugs,iso_code,life_expectancy,uuid_code" > "$output_file"
    
    # Extract the desired columns and append to the output file
    awk -F, 'NR > 1{
        print $1","$2","$3","$4","$5","$6","$7","$8","$9","$10","$11","$12","$13
    }' "$USER_STORE" >> "$output_file"

    echo "Users exported to $output_file successfully."
}

download_statistics() {
    local output_file="../data/statistics.csv"
    local total_patients=0
    local total_survival_rate=0
    local survival_rates=()

    # Read and process data from the user store
    while IFS=, read -r firstname lastname role email password dob has_hiv diagnosis_date art_status art_date iso_code life_expectancy uuid_code; do
        if [[ "$role" == "PATIENT" ]]; then
            total_patients=$((total_patients + 1))
            total_survival_rate=$(echo "$total_survival_rate + $life_expectancy" | bc)
            survival_rates+=("$life_expectancy")
        fi
    done < <(tail -n +2 "$USER_STORE")  # Skip the header

    # Calculate average survival rate
    local average_survival_rate=$(echo "scale=2; $total_survival_rate / $total_patients" | bc)

    # Sort survival rates to calculate median and percentiles
    IFS=$'\n' survival_rates=($(sort -n <<<"${survival_rates[*]}"))
    unset IFS

    # Calculate median survival rate
    local median_survival_rate
    if (( total_patients % 2 == 0 )); then
        local mid_index=$((total_patients / 2))
        local mid_val1=${survival_rates[mid_index-1]}
        local mid_val2=${survival_rates[mid_index]}
        median_survival_rate=$(echo "scale=2; ($mid_val1 + $mid_val2) / 2" | bc)
    else
        local mid_index=$(((total_patients + 1) / 2))
        median_survival_rate=${survival_rates[mid_index-1]}
    fi

    # Calculate percentiles (e.g., 25th, 75th)
    local percentile_25th=${survival_rates[$((total_patients / 4))]}
    local percentile_75th=${survival_rates[$(((3 * total_patients) / 4))]}

    # Write the statistics to the output CSV
    echo "Statistic,Value" > "$output_file"
    echo "Total Patients,$total_patients" >> "$output_file"
    echo "Average Survival Rate,$average_survival_rate" >> "$output_file"
    echo "Median Survival Rate,$median_survival_rate" >> "$output_file"
    echo "25th Percentile Survival Rate,$percentile_25th" >> "$output_file"
    echo "75th Percentile Survival Rate,$percentile_75th" >> "$output_file"

    echo "Statistics downloaded successfully to $output_file"
}

"$@"
