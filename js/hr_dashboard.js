document.getElementById('toggle-btn').addEventListener('click', function () {
    let sidebar = document.getElementById('sidebar');
    let mainContent = document.getElementById('main-content');

    if (sidebar.style.width === '0px') {
        sidebar.style.width = '250px';
        mainContent.style.marginLeft = '260px';
    } else {
        sidebar.style.width = '0px';
        mainContent.style.marginLeft = '0';
    }
});

// Backend API Integration
const baseUrl = 'http://localhost:8080/api/hr';

// Fetch all interviews
document.getElementById('view-interviews').addEventListener('click', async () => {
    let response = await fetch(`${baseUrl}/interviews`);
    let data = await response.json();
    displayData(data, 'All Interviews');
});

// Assign an interview
document.getElementById('assign-interview').addEventListener('click', async () => {
    let interviewData = {
        candidateEmail: prompt("Enter Candidate Email:"),
        interviewerName: prompt("Enter Interviewer Name:"),
        position: prompt("Enter Position:"),
        interviewDate: prompt("Enter Interview Date (YYYY-MM-DD HH:mm):")
    };

    let response = await fetch(`${baseUrl}/assign-interview`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(interviewData)
    });

    let result = await response.text();
    displayMessage(result, 'Assign Interview');
});

// View feedback
document.getElementById('view-feedback').addEventListener('click', async () => {
    let interviewId = prompt("Enter Interview ID:");
    let response = await fetch(`${baseUrl}/feedback/${interviewId}`);
    let data = await response.json();
    displayData(data, 'Interview Feedback');
});

// Schedule further rounds
document.getElementById('schedule-round').addEventListener('click', async () => {
    let interviewId = prompt("Enter Interview ID:");
    let scheduleData = { nextRoundDate: prompt("Enter Next Round Date (YYYY-MM-DD HH:mm):") };

    let response = await fetch(`${baseUrl}/schedule-round/${interviewId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(scheduleData)
    });

    let result = await response.text();
    displayMessage(result, 'Schedule Further Rounds');
});

// Make final decision
document.getElementById('final-decision').addEventListener('click', async () => {
    let decisionData = {
        interviewId: prompt("Enter Interview ID:"),
        finalDecision: prompt("Enter Decision (Hired/Rejected):"),
        remarks: prompt("Enter Remarks:")
    };

    let response = await fetch(`${baseUrl}/final-decision`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(decisionData)
    });

    let result = await response.text();
    displayMessage(result, 'Final Decision');
});

// Delete interview
document.getElementById('delete-interview').addEventListener('click', async () => {
    let interviewId = prompt("Enter Interview ID to delete:");

    let response = await fetch(`${baseUrl}/delete-interview/${interviewId}`, {
        method: 'DELETE'
    });

    let result = await response.text();
    displayMessage(result, 'Delete Interview');
});

// Helper Functions
function displayData(data, title) {
    document.getElementById('content-area').innerHTML = `<h2>${title}</h2><pre>${JSON.stringify(data, null, 2)}</pre>`;
}

function displayMessage(message, title) {
    document.getElementById('content-area').innerHTML = `<h2>${title}</h2><p>${message}</p>`;
}
