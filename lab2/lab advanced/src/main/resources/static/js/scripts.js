// Custom JavaScript for Student Portal

document.addEventListener("DOMContentLoaded", function () {
    // Auto-hide alerts after 5 seconds
    setTimeout(function () {
        const alerts = document.querySelectorAll(".alert");
        alerts.forEach(function (alert) {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);

    // Confirm delete actions
    const deleteLinks = document.querySelectorAll('a[href*="/delete/"]');
    deleteLinks.forEach(function (link) {
        link.addEventListener("click", function (event) {
            if (!confirm("Are you sure you want to delete this item?")) {
                event.preventDefault();
            }
        });
    });
});
document.addEventListener("DOMContentLoaded", function() {
    const darkModeToggle = document.getElementById("darkModeToggle");
    const darkModeIcon = document.getElementById("darkModeIcon");

    // Vérifie si le mode sombre est activé dans le localStorage
    const isDarkMode = localStorage.getItem("darkMode") === "true";

    // Applique le mode sombre si nécessaire
    if (isDarkMode) {
        document.body.classList.add("dark-mode");
        darkModeIcon.classList.remove("bi-moon-fill");
        darkModeIcon.classList.add("bi-sun-fill");
    }

    // Toggle pour le mode sombre
    darkModeToggle.addEventListener("click", function() {
        document.body.classList.toggle("dark-mode");

        const isDarkModeNow = document.body.classList.contains("dark-mode");
        localStorage.setItem("darkMode", isDarkModeNow);

        if (isDarkModeNow) {
            darkModeIcon.classList.remove("bi-moon-fill");
            darkModeIcon.classList.add("bi-sun-fill");
        } else {
            darkModeIcon.classList.remove("bi-sun-fill");
            darkModeIcon.classList.add("bi-moon-fill");
        }
    });
});
// Validation du formulaire côté client
document.addEventListener("DOMContentLoaded", function() {
    const studentForm = document.getElementById("studentForm");

    if (studentForm) {
        studentForm.addEventListener("submit", function(event) {
            if (!validateForm()) {
                event.preventDefault();
            }
        });

        // Validation en temps réel pour chaque champ
        document.getElementById("name").addEventListener("input", validateName);
        document.getElementById("age").addEventListener("input", validateAge);
        document.getElementById("email").addEventListener("input", validateEmail);
        document.getElementById("course").addEventListener("change", validateCourse);
        document.getElementById("studentId").addEventListener("input", validateStudentId);
    }

    function validateForm() {
        const isNameValid = validateName();
        const isAgeValid = validateAge();
        const isEmailValid = validateEmail();
        const isCourseValid = validateCourse();
        const isStudentIdValid = validateStudentId();

        return isNameValid && isAgeValid && isEmailValid && isCourseValid && isStudentIdValid;
    }

    function validateName() {
        const nameInput = document.getElementById("name");
        const name = nameInput.value.trim();
        const nameError = document.getElementById("nameError");

        if (name.length < 2 || name.length > 50) {
            nameError.textContent = "Name must be between 2 and 50 characters";
            nameError.style.display = "block";
            nameInput.classList.add("is-invalid");
            return false;
        } else {
            nameError.style.display = "none";
            nameInput.classList.remove("is-invalid");
            nameInput.classList.add("is-valid");
            return true;
        }
    }

    function validateAge() {
        const ageInput = document.getElementById("age");
        const age = parseInt(ageInput.value);
        const ageError = document.getElementById("ageError");

        if (isNaN(age) || age < 16) {
            ageError.textContent = "Age must be at least 16";
            ageError.style.display = "block";
            ageInput.classList.add("is-invalid");
            return false;
        } else {
            ageError.style.display = "none";
            ageInput.classList.remove("is-invalid");
            ageInput.classList.add("is-valid");
            return true;
        }
    }

    function validateEmail() {
        const emailInput = document.getElementById("email");
        const email = emailInput.value.trim();
        const emailError = document.getElementById("emailError");
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!emailRegex.test(email)) {
            emailError.textContent = "Please enter a valid email address";
            emailError.style.display = "block";
            emailInput.classList.add("is-invalid");
            return false;
        } else {
            emailError.style.display = "none";
            emailInput.classList.remove("is-invalid");
            emailInput.classList.add("is-valid");
            return true;
        }
    }

    function validateCourse() {
        const courseInput = document.getElementById("course");
        const course = courseInput.value.trim();
        const courseError = document.getElementById("courseError");

        if (course === "") {
            courseError.textContent = "Please select a course";
            courseError.style.display = "block";
            courseInput.classList.add("is-invalid");
            return false;
        } else {
            courseError.style.display = "none";
            courseInput.classList.remove("is-invalid");
            courseInput.classList.add("is-valid");
            return true;
        }
    }

    function validateStudentId() {
        const studentIdInput = document.getElementById("studentId");
        const studentId = studentIdInput.value.trim();
        const studentIdError = document.getElementById("studentIdError");
        const studentIdRegex = /^[A-Z0-9]{8}$/;

        if (!studentIdRegex.test(studentId)) {
            studentIdError.textContent = "Student ID must be 8 characters with uppercase letters and numbers only";
            studentIdError.style.display = "block";
            studentIdInput.classList.add("is-invalid");
            return false;
        } else {
            studentIdError.style.display = "none";
            studentIdInput.classList.remove("is-invalid");
            studentIdInput.classList.add("is-valid");
            return true;
        }
    }
});