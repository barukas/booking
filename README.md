# BOOKING

Two user stories were created:

User Story 1: As a customer, I want to be able to request a booking at this restaurant. In this story, validation is performed considering the table size, date, and time to determine if the time has expired or if the table is still occupied.
The information is stored using H2 (in-memory).

Two endpoints were created: a POST for creating the reservation and a GET for querying reservations by day.

For the point: "We would like you to also think about how you can check the correctness of your code."
You could consider:
- Unit Testing: Create unit tests that cover various scenarios and ensure test coverage.
- Integration Testing
- Static Code Analysis Tools: Use Sonar as a standardization tool (with standards defined within Sonar).
- End-to-End Testing
- Performance Testing
- Good Code Documentation

I also added a Postman collection with evidence of its functionality.

CREATE RESERVATION

![image](https://github.com/user-attachments/assets/9364e2cf-94a0-4d31-8a95-7337c8bcad27)

THERE IS ALREADY A RESERVATION FOR THAT TABLE WITH THAT DATE AND WITH THAT TIME

![image](https://github.com/user-attachments/assets/b7f8e28a-fd69-46ac-9d0e-2d3c012ca7a6)

GET RESERVATION

![image](https://github.com/user-attachments/assets/792d14b9-cdd6-4182-ab46-1c477e78778c)

