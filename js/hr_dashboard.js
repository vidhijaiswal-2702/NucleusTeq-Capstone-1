document.addEventListener("DOMContentLoaded", function () {
    showSection("all-interviews"); // Default section
    fetchAllInterviews();
    fetchFinalDecisions();

     // ✅ Display user details
     const user = JSON.parse(localStorage.getItem("user"));
     if (user) {
         document.getElementById("userDetails").innerHTML = 
             `<p><strong>Email:</strong> ${user.email}</p>
              <p><strong>ID:</strong> ${user.id}</p>`;
     }
});

// Function to show selected section
function showSection(sectionId) {
    document.querySelectorAll(".section").forEach(section => section.classList.remove("active"));

    let activeSection = document.getElementById(sectionId);
    if (activeSection) { // ✅ Check if the element exists
        activeSection.classList.add("active");
    } else {
        console.error(`Section with ID '${sectionId}' not found!`);
        return;
    }

    if (sectionId === "all-interviews") fetchAllInterviews();
    if (sectionId === "view-decisions") fetchFinalDecisions();
}


// 1️⃣ Fetch All Interviews
function fetchAllInterviews() {
    fetch("http://localhost:8080/api/interviews/all")
        .then(response => response.json())
        .then(data => {
            let tableBody = document.querySelector("#interviewsTable tbody");
            tableBody.innerHTML = "";
            data.forEach(interview => {
                let row = `
                    <tr>
                        <td>${interview.id}</td>
                        <td>${interview.candidateName}</td>
                        <td>${interview.candidateEmail}</td>
                        <td>${interview.interviewer.username}</td>
                        <td>${interview.interviewDate}</td>
                        <td>${interview.status}</td>
                        <td>${interview.round}</td>
                    </tr>
                `;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching interviews:", error));
}
// Fetch and display all registered interviewers
function fetchAllInterviewers() {
    fetch("http://localhost:8080/api/users/interviewers")
        .then(response => response.json())
        .then(data => {
            let tableBody = document.querySelector("#interviewersTable tbody");
            tableBody.innerHTML = "";
            data.forEach(interviewer => {
                let row = `
                    <tr>
                        <td>${interviewer.id}</td>
                        <td>${interviewer.username}</td>
                        <td>${interviewer.email}</td>
                        <td>${interviewer.role}</td>
                    </tr>
                `;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching interviewers:", error));
}

// Call function when HR clicks on "View All Interviewers"
document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("[onclick=\"showSection('view-interviewers')\"]").addEventListener("click", fetchAllInterviewers);
});


// 2️⃣ Schedule a New Interview
function scheduleInterview() {
    let interviewData = {
        candidateName: document.getElementById("candidateName").value,
        candidateEmail: document.getElementById("candidateEmail").value,
        interviewer: { id: parseInt(document.getElementById("interviewerId").value) },
        interviewDate: document.getElementById("interviewDate").value,
        round: document.getElementById("round").value,
        status: "SCHEDULED"
    };

    fetch("http://localhost:8080/api/interviews/schedule", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(interviewData)
    })
    .then(response => response.json())
    .then(() => {
        alert("Interview Scheduled Successfully!");
        fetchAllInterviews();
    })
    .catch(error => console.error("Error scheduling interview:", error));
}

// 3️⃣ Update an Interview (Placeholder function)
function updateInterview() {
    let interviewId = document.getElementById("updateInterviewId").value;
    
    let updatedInterviewData = {
        id: parseInt(interviewId),
        candidateName: document.getElementById("updateCandidateName").value,
        candidateEmail: document.getElementById("updateCandidateEmail").value,
        interviewer: {
            id: parseInt(document.getElementById("updateInterviewerId").value),
            username: document.getElementById("updateInterviewerName").value,
            email: document.getElementById("updateInterviewerEmail").value,
            role: "INTERVIEWER"
        },
        interviewDate: document.getElementById("updateInterviewDate").value,
        status: document.getElementById("updateInterviewStatus").value,
        round: document.getElementById("updateInterviewRound").value
    };

    fetch(`http://localhost:8080/api/interviews/update/${interviewId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedInterviewData)
    })
    .then(() => {
        alert("Interview Updated Successfully!");
        fetchAllInterviews();
    })
    .catch(error => console.error("Error updating interview:", error));
}

// 4️⃣ Delete an Interview
function deleteInterview() {
    let interviewId = document.getElementById("updateInterviewId").value;

    fetch(`http://localhost:8080/api/interviews/delete/${interviewId}`, {
        method: "DELETE"
    })
    .then(() => {
        alert("Interview Deleted Successfully!");
        fetchAllInterviews();
    })
    .catch(error => console.error("Error deleting interview:", error));
}

// 5️⃣ View Candidate Feedback
function viewFeedback() {
    let interviewId = document.getElementById("feedbackInterviewId").value;

    fetch(`http://localhost:8080/api/feedback/interview/${interviewId}`)
        .then(response => response.json())
        .then(feedbackArray => {
            if (!Array.isArray(feedbackArray) || feedbackArray.length === 0) {
                document.getElementById("feedbackResult").innerHTML = `<p>No feedback found.</p>`;
                return;
            }

            let finalDecisionDisplay = ''; // To store the final decision display
            let feedbackHtml = feedbackArray.map(feedback => {
                // Get final decision from the first feedback entry
                if (finalDecisionDisplay === '' && feedback.finalDecision) {
                    finalDecisionDisplay = `<p style="font-size: 1.5em; font-weight: bold; color: #007bff;"><strong>Final Decision:</strong> ${feedback.finalDecision}</p>`; // Added styling
                }

                return `
                    <p><strong>Skill:</strong> ${feedback.skill}</p>
                    <p><strong>Rating:</strong> ${feedback.rating}</p>
                    <p><strong>Topics Used:</strong> ${feedback.topicsUsed}</p>
                    <p><strong>Comments:</strong> ${feedback.comments}</p>
                    <hr>
                `;
            }).join('');

            // Combine final decision (if any) and feedback content
            document.getElementById("feedbackResult").innerHTML = finalDecisionDisplay + feedbackHtml;
        })
        .catch(error => console.error("Error fetching feedback:", error));
}

// 6️⃣ Submit Final Hiring Decision
function submitFinalDecision() {
    // ✅ Retrieve HR ID from sessionStorage
    let hrId = sessionStorage.getItem("hrId");

    if (!hrId) {
        console.error("HR ID is missing or invalid:", hrId);
        alert("HR ID is missing. Please log in again.");
        return;
    }

    let decisionData = {
        interviewId: parseInt(document.getElementById("decisionInterviewId").value),
        hrId: parseInt(hrId),
        finalStatus: document.getElementById("finalDecision").value.toUpperCase(), // Corrected line
        comments: document.getElementById("decisionComments").value
    };

    console.log("Submitting Decision:", decisionData);

    fetch("http://localhost:8080/api/decisions/submit", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(decisionData)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw err });
        }
        return response.json();
    })
    .then(data => {
        alert("Final Decision Submitted Successfully!");
        fetchFinalDecisions();
    })
    .catch(error => console.error("Error submitting decision:", error));
}
// 7️⃣ Fetch All Final Decisions
function fetchFinalDecisions() {
    fetch("http://localhost:8080/api/decisions/all")
        .then(response => response.json())
        .then(data => {
            let tableBody = document.querySelector("#decisionsTable tbody");
            tableBody.innerHTML = "";
            data.forEach(decision => {
                let row = `
                    <tr>
                        <td>${decision.interview.id}</td>  <!-- Fix interview ID -->
                        <td>${decision.hr.id}</td>         <!-- Fix HR ID -->
                        <td>${decision.finalStatus}</td>
                        <td>${decision.comments}</td>
                    </tr>
                `;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching decisions:", error));
}



// Logout Function
function logout() {
    alert("Logging out...");
    window.location.href = "login.html"; // Redirect to login page
}
