# NucleusTeq-Capstone-1
Interview-Feedback-Management-System
Overview
The Interview Feedback Management System (IFMS) is a web-based application designed to streamline the interview evaluation process. It allows HR Managers to schedule interviews, assign interviewers, collect structured feedback, and make hiring decisions efficiently.
Features
HR Manager Features:
✅ Assign interviews to interviewers.
✅ View scheduled interviews in a calendar format.
✅ Review feedback from interviewers.
✅ Schedule L2 interviews if necessary.
✅ Make final hiring decisions.
Interviewer Features:
✅ Receive interview assignments via email notifications.
✅ Log in using company email credentials.
✅ View scheduled interviews in a calendar view.
✅ Submit structured evaluation forms (L1 & L2).
✅ Provide final comments and recommendations.
________________________________________
Technology Stack
Frontend:
•	HTML, CSS, JavaScript – For UI development.
Backend:
•	Spring Boot – REST API development.
•	 Security – Password hashing(jbcrypt hashing)
•	Hibernate & JPA – ORM for database operations.
•	PostgreSQL – Database.
Tools & Libraries:
•	Swagger UI  – API testing.
•	Lombok – Reducing boilerplate code.
________________________________________
System Flow
1️⃣ Interview Assignment: HR Manager assigns an interview to an interviewer.
2️⃣ Interviewer Notifications: Interviewer receives an email with interview details.
3️⃣ Login/Signup: Interviewer logs in via the company email.
4️⃣ Dashboard View: The interviewer accesses interview details from the dashboard.
5️⃣ Calendar View: The interviewer can view the schedule for past, present, and future interviews.
6️⃣ Evaluation Form Submission: The interviewer fills out and submits an evaluation form.
7️⃣ Review & Scheduling: HR Manager reviews feedback and schedules the L2 round if necessary.
8️⃣ Final Decision: The final hiring decision is recorded.
________________________________________
Project Setup
1. Clone the Repository:
sh
CopyEdit
git clone https://github.com/your-repo/interview-feedback-management.git
cd interview-feedback-management
2. Backend Setup
Install Dependencies:
Ensure you have Java 17+, PostgreSQL, and Maven installed.
sh
CopyEdit
mvn clean install
Configure Database:
Update application.properties with your PostgreSQL credentials:
properties
CopyEdit
spring.datasource.url=jdbc:postgresql://localhost:5432/ifms_db
spring.datasource.username=your_username
spring.datasource.password=your_password
Run the Spring Boot Server:
sh
CopyEdit
mvn spring-boot:run
API will be available at: http://localhost:8080
________________________________________
3. Frontend Setup
Run Frontend Locally:
Open index.html in a browser OR use Live Server (VS Code extension).
________________________________________
API Endpoints
Authentication
🔹 POST /auth/register – Register a new user.
🔹 POST /auth/login – Authenticate user.
HR Actions
🔹 POST /api/interviews/create – Schedule a new interview.
🔹 GET /api/interviews/all – View all interviews.
🔹 DELETE /api/interviews/delete/{id} – Remove an interview.
Interviewer Actions
🔹 GET /api/interviews/my – View assigned interviews.
🔹 POST /api/feedback/submit – Submit feedback form.
________________________________________

Future Enhancements
✅ JWT Authentication for better security.
✅ Role-Based Access Control (RBAC).
✅ Enhanced interview analytics dashboard.

