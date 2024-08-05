USER_STORE='../data/user-store.txt'
COUNTRIES_CSV='../data/countries.csv'

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
    echo "$email,$uuid_code,PATIENT" >> "$USER_STORE"
    
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

    # Find the ISO code for the given country
    local iso_code=$(awk -F, -v country="$country_of_residence" '$1 == country {print $2}' "$COUNTRIES_CSV")

    if [ -z "$iso_code" ]; then
        echo "Error: ISO code for country '$country_of_residence' not found."
        exit 1
    fi
    
    # Remove the existing entry if it exists
    sed -i "/$uuid_code/d" "$USER_STORE"
    
    # Add the new entry
    echo "$first_name,$last_name,PATIENT,$email,$hashedPassword,$date_of_birth,$has_hiv,$date_of_diagnosis,$is_on_art_drugs,$date_of_art_drugs,$iso_code" >> "$USER_STORE"
    
    echo "Registration completed successfully for UUID: $uuid_code"
}

download_all_users() {
    touch "../data/users.csv"
}

"$@"
