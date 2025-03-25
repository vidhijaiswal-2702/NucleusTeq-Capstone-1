const backendUrl = "http://localhost:8080/auth/login"; // Backend login API

async function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const message = document.getElementById("loginMessage");

    if (!email || !password) {
        message.innerHTML = "Please fill all fields!";
        return;
    }

    const loginData = {
        email: email,
        password: password
    };

    try {
        const response = await fetch(backendUrl, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(loginData)
        });

        if (!response.ok) {
            throw new Error("Invalid credentials");
        }

        const user = await response.json();
        localStorage.setItem("user", JSON.stringify(user)); // Store user in local storage

        // Role-based redirection
        if (user.role === "HR_ADMIN") {
            window.location.href = "hr_dashboard.html";
        } else if (user.role === "INTERVIEWER") {
            window.location.href = "interviewer_dashboard.html";
        } else {
            throw new Error("Unknown role. Access denied.");
        }

    } catch (error) {
        message.innerHTML = "Login failed. Check credentials!";
    }
}
