function highlightSelectedPlace(selectId, placeClass){
    const select = document.getElementById(selectId);
    const places = document.querySelectorAll(`.${placeClass}`);

    const updateMapHighlight = () => {
      const selectedValue = select.value;

      places.forEach(place => {
        if(place.id === selectedValue){
          // Highlight the selected place
          place.style.fill = 'black';
        } 
		else{
          // Reset to the original color defined in the SVG
          place.style.fill = '';
        }
      });
    };

    // Attach event listener to the select element
    select.addEventListener('change', updateMapHighlight);
  }


document.addEventListener("DOMContentLoaded", function (){
    document.getElementById("type").addEventListener("change", updateDonationSection);

    function updateDonationSection(){
        document.querySelectorAll('.donation-section').forEach(section => {
            section.style.display = 'none'; // Hide all sections
        });

        const selectedType = document.getElementById("type").value;

        if(selectedType === "MONEY"){
            document.getElementById("moneySection").style.display = "block";
        } 
		else if(selectedType === "ITEMS"){
            document.getElementById("itemsSection").style.display = "block";
        }
    }
});


function updateEmailPreference(){
	const toggle = document.getElementById('emailUpdateToggle');
	const message = document.getElementById('emailPreferenceMessage');
	const hiddenField = document.getElementById('receiveUpdate'); 
    
    if(toggle.checked){
        message.textContent = 'I would like to get (or continue to get) email update';
		message.classList.remove('text-muted');
		message.classList.add('text-success');
		hiddenField.value = true; 
    } 
	else{
        message.textContent = 'I would not like to get email updates.';
		message.classList.remove('text-success');
		message.classList.add('text-muted');
		hiddenField.value = false; 
    }
}



document.addEventListener('DOMContentLoaded', () => {
    const placeSelector = document.getElementById('placeSelector');
    const donationHistoryDiv = document.getElementById('donationHistory');

    placeSelector.addEventListener('change', () => {
        const placeId = placeSelector.value;

        if(placeId){
            fetch(`/donations/history?placeId=${placeId}`)
                .then(response => response.text())
                .then(html => {
                    donationHistoryDiv.innerHTML = html;
                })
                .catch(error => {
                    donationHistoryDiv.innerHTML = '<p class="text-danger">Error loading donation history. Please try again.</p>';
                    console.error('Error fetching donation history:', error);
                });
        } 
		else{
            donationHistoryDiv.innerHTML = '<p class="text-muted">Please select a place to view donation history.</p>';
        }
    });
});


document.addEventListener("DOMContentLoaded", function () {
    const emailInput = document.getElementById("email");
    const confirmEmailInput = document.getElementById("confirmEmail");
    const errorMessage = document.getElementById("emailError");

    // Select all submit buttons and disable them initially
    const submitButtons = document.querySelectorAll("button[type='submit']");
    submitButtons.forEach(button => button.disabled = true);

    function validateEmails() {
        const email = emailInput.value.trim();
        const confirmEmail = confirmEmailInput.value.trim();

        if(email && confirmEmail){
            if(email === confirmEmail){
                errorMessage.style.display = "none"; // Hide error message
                submitButtons.forEach(button => button.disabled = false); // Enable all submit buttons
            } 
			else{
                errorMessage.style.display = "block"; // Show error message
                errorMessage.textContent = "Emails do not match!";
                submitButtons.forEach(button => button.disabled = true); // Disable all submit buttons
            }
        } 
		else{
            errorMessage.style.display = "none"; // Hide error message if either field is empty
            submitButtons.forEach(button => button.disabled = true); // Keep all submit buttons disabled
        }
    }

    // Add event listeners to check emails in real-time
    emailInput.addEventListener("input", validateEmails);
    confirmEmailInput.addEventListener("input", validateEmails);
});

