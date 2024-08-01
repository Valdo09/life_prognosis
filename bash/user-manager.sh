

USER_STORE='./data/user-store.txt'
 login(){
    local email=$1
    local password=$2
    local hashedPassword=$(echo -n "$password" | openssl dgst -sha256 | awk '{print $2}')

    grep "$email,$hashedPassword" "$USER_STORE" > /dev/null 

    if [ $? -eq 0 ]; then
        echo "success"
    else
        echo "failure"
    fi

}  
login "$@"

function initiate_patient_registration(){
    local email=$1
    local uiid_code= $(uuidgen)
    echo "$email; $uiid_code; PATIENT" $USER_STORE

        
    return uiid_code

}