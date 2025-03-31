document.addEventListener("DOMContentLoaded", function () {
    const interviewerEmail = sessionStorage.getItem("interviewerEmail"); // Get logged-in interviewer's email

    if (!interviewerEmail) {
        alert("Interviewer not logged in!");
        window.location.href = "login.html"; // Redirect to login page
        return;
    }

    fetchAssignedInterviews(interviewerEmail);
    fetchSubmittedFeedback(interviewerEmail);
});

// Function to fetch assigned interviews by interviewer's email
function fetchAssignedInterviews(email) {
    fetch(`http://localhost:8080/api/interviews/assigned?email=${encodeURIComponent(email)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server returned ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (!Array.isArray(data)) {
                throw new Error("Invalid response format: Expected an array");
            }

            const tableBody = document.querySelector("#assigned-interviews-table tbody");
            tableBody.innerHTML = "";

            data.forEach(interview => {
                const row = `<tr>
                    <td>${interview.id}</td>
                    <td>${interview.candidateName}</td>
                    <td>${interview.candidateEmail}</td>
                    <td>${new Date(interview.interviewDate).toLocaleString()}</td>
                    <td>${interview.status}</td>
                    <td>${interview.round}</td>
                </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching assigned interviews:", error));
}



// Submit feedback form
function submitFeedback() {
    const interviewId = document.getElementById("interviewId").value.trim();
    const interviewerId = document.getElementById("interviewerId").value.trim(); 

    if (!interviewId || isNaN(interviewId)) {
        alert("Valid Interview ID is required!");
        return;
    }
    if (!interviewerId || isNaN(interviewerId)) {
        alert("Interviewer ID is required!");
        return;
    }

    const feedbackList = [
        { skill: "ALGORITHM", rating: "AVERAGE", topicsUsed: "Recursion, DP", comments: "Strong problem-solving", interviewerId: parseInt(interviewerId) },
        { skill: "SQL", rating: "AVERAGE", topicsUsed: "Joins, Indexing", comments: "Needs optimization", interviewerId: parseInt(interviewerId) },
        { skill: "GIT", rating: "AVERAGE", topicsUsed: "Version control basics", comments: "Should learn advanced workflows", interviewerId: parseInt(interviewerId) },
        { skill: "DESIGN_PATTERNS", rating: "POOR", topicsUsed: "Singleton, Factory", comments: "Weak understanding", interviewerId: parseInt(interviewerId) },
        { skill: "ATTITUDE", rating: "GOOD", topicsUsed: "Proactive", comments: "Positive approach", interviewerId: parseInt(interviewerId) },
        { skill: "LEARNING_ABILITY", rating: "GOOD", topicsUsed: "Quick adaptability", comments: "Learns fast", interviewerId: parseInt(interviewerId) },
        { skill: "RESUME_EXPLANATION", rating: "GOOD", topicsUsed: "Project explanation", comments: "Knows projects well", interviewerId: parseInt(interviewerId) },
        { skill: "COMMUNICATION", rating: "AVERAGE", topicsUsed: "Clear answers", comments: "Can be more concise", interviewerId: parseInt(interviewerId) },
        { skill: "CODE_SYNTAX", rating: "GOOD", topicsUsed: "Clean code", comments: "Writes structured code", interviewerId: parseInt(interviewerId) }
    ];

    const feedbackData = { interviewId: parseInt(interviewId), feedback: feedbackList };

    console.log("Submitting feedback:", JSON.stringify(feedbackData, null, 2));

    fetch("http://localhost:8080/api/feedback/submit", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(feedbackData)
    })
    .then(response => response.text())  // ✅ Handle plain text response
    .then(data => {
        console.log("Server Response:", data);
        alert(data);  // Show success message
    })
    .catch(error => {
        console.error("Error submitting feedback:", error);
        alert("Error submitting feedback: " + error.message);
    });
}

   

// Fetch submitted feedback from API
function fetchSubmittedFeedback(email) {
    fetch(`http://localhost:8080/api/feedback/interviewer?email=${encodeURIComponent(email)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server returned ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (!Array.isArray(data)) {
                throw new Error("Invalid response format: Expected an array");
            }

            const tableBody = document.querySelector("#feedback-table tbody");
            tableBody.innerHTML = ""; // Clear previous data

            data.forEach(feedback => {
                const row = `<tr>
                    <td>${feedback.interview.id}</td>
                    <td>${feedback.skill}</td>
                    <td>${feedback.rating}</td>
                    <td>${feedback.topicsUsed}</td>
                    <td>${feedback.comments}</td>
                </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching submitted feedback:", error));
}

// Show sections based on selection
function showSection(sectionId) {
    document.querySelectorAll(".section").forEach(section => {
        section.style.display = "none";
    });

    const selectedSection = document.getElementById(sectionId);
    if (selectedSection) {
        selectedSection.style.display = "block";
    } else {
        console.error(`Section with ID "${sectionId}" not found.`);
    }
}

// Logout function
function logout() {
    sessionStorage.clear();
    window.location.href = "login.html"; // Redirect to login page
}
