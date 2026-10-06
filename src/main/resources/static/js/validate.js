document.addEventListener("DOMContentLoaded", function () {
    
    let name = document.getElementById("name");
    let email = document.getElementById("email");
    let message = document.getElementById("message");
    let form = document.querySelector("form");

    form.addEventListener("submit", function (e) {
        const errors = [];

        if (name.value.trim() === "") {
            errors.push("Full Name is required");
        }

        if (email.value.trim() === "") {
            errors.push("Email is required");
        }

        if (message.value.trim().length < 10) {
            errors.push("Please enter at least 10 characters in the message");
        }

        if (errors.length > 0) {
            e.preventDefault();
            alert(errors.join("\n")); 
        }
    });
});
