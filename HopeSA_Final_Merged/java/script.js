/* Signup FOrm */

document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("loginForm");
  const fullname = document.getElementById("fullname");
  const email = document.getElementById("email");
  const password = document.getElementById("password");
  const showPassword = document.getElementById("showPassword");
  const rememberMe = document.getElementById("rememberMe");

  // Restore saved values
  if (localStorage.getItem("rememberMe") === "true") {
    fullname.value = localStorage.getItem("fullname") || "";
    email.value = localStorage.getItem("email") || "";
    rememberMe.checked = true;
  }

  // Toggle password visibility
  showPassword.addEventListener("change", function () {
    password.type = this.checked ? "text" : "password";
  });

  form.addEventListener("submit", function (e) {
    const nameValue = fullname.value.trim();
    const emailValue = email.value.trim();
    const passwordValue = password.value;

    // Validate full name
    if (!/^[a-zA-Z\s]+$/.test(nameValue)) {
      alert("❌ Full name must contain only letters and spaces.");
      e.preventDefault();
      return;
    }

    // Validate email
    if (!/^\S+@\S+\.\S+$/.test(emailValue)) {
      alert("❌ Please enter a valid email address.");
      e.preventDefault();
      return;
    }

    // Validate password
    if (passwordValue.length < 6) {
      alert("❌ Password must be at least 6 characters long.");
      e.preventDefault();
      return;
    }

    // Store values if Remember Me is checked
    if (rememberMe.checked) {
      localStorage.setItem("rememberMe", "true");
      localStorage.setItem("fullname", nameValue);
      localStorage.setItem("email", emailValue);
    } else {
      localStorage.removeItem("rememberMe");
      localStorage.removeItem("fullname");
      localStorage.removeItem("email");
    }

    // Show success message
    alert("✅ Form submitted successfully!");
    // Form continues to welcome.html
  });
});



/* Donation Form*/

function validateform() {
  const form = document.forms["myform"];
  const fullname = form["fullname"].value.trim();
  const amount = form["amount"].value.trim();

  // Check full name
  const namePattern = /^[a-zA-Z\s]+$/;
  if (!namePattern.test(fullname)) {
    alert("Please enter a valid full name (letters and spaces only).");
    return false;
  }

  // Check amount is a number and > 0
  const amountValue = parseFloat(amount);
  if (isNaN(amountValue) || amountValue <= 0) {
    alert("Please enter a valid donation amount greater than R0.");
    return false;
  }

  // Everything is valid
  alert("Thank you for your donation!");
  return true;
}
