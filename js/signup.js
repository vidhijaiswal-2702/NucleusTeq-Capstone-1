const signupUrl = "http://localhost:8080/auth/register"; // Backend signup API

async function register() {
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const role = document.getElementById("role").value;
    const message = document.getElementById("signupMessage");

    if (!username || !email || !password || !role) {
        message.innerHTML = "Please fill all fields!";
        return;
    }

    const userData = {
        username: username,
        email: email,
        password: password,
        role: role
    };

    try {
        const response = await fetch(signupUrl, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userData)
        });

        if (!response.ok) {
            throw new Error("Signup failed");
        }

        message.innerHTML = "Signup successful! Redirecting...";
        setTimeout(() => {
            window.location.href = "login.html"; // Redirect to login
        }, 1500);

    } catch (error) {
        message.innerHTML = "Signup failed. Try again!";
    }
}
