insert into faculty (name, description, location, dean, active, registration_date) values ('Engineering', 'Faculty of Engineering', 'Building A', 'Dr. Smith', true, current_timestamp);
insert into faculty (name, description, location, dean, active, registration_date) values ('Arts', 'Faculty of Arts', 'Building B', 'Dr. Johnson', true, current_timestamp);
insert into faculty (name, description, location, dean, active, registration_date) values ('Science', 'Faculty of Science', 'Building C', 'Dr. Lee', true, current_timestamp);

insert into career (faculty_id, name, description, semester_length, degree_awarded, active, registration_date) values (1, 'Computer Science', 'Bachelor of Science in Computer Science', 8, 'BSc', true, current_timestamp);
insert into career (faculty_id, name, description, semester_length, degree_awarded, active, registration_date) values (1, 'Mechanical Engineering', 'Bachelor of Science in Mechanical Engineering', 8, 'BSc', true, current_timestamp);
insert into career (faculty_id, name, description, semester_length, degree_awarded, active, registration_date) values (2, 'History', 'Bachelor of Arts in History', 8, 'BA', true, current_timestamp);
insert into career (faculty_id, name, description, semester_length, degree_awarded, active, registration_date) values (2, 'Literature', 'Bachelor of Arts in Literature', 8, 'BA', true, current_timestamp);
insert into career (faculty_id, name, description, semester_length, degree_awarded, active, registration_date) values (3, 'Biology', 'Bachelor of Science in Biology', 8, 'BSc', true, current_timestamp);
insert into career (faculty_id, name, description, semester_length, degree_awarded, active, registration_date) values (3, 'Chemistry', 'Bachelor of Science in Chemistry', 8, 'BSc', true, current_timestamp);

insert into course (career_id, name, code, description, credits, semester_level, active, registration_date ) values (1, 'Introduction to Programming', 'CS101', 'Basics of programming using Python', 4, 1, true, current_timestamp);
insert into course (career_id, name, code, description, credits, semester_level, active, registration_date ) values (1, 'Data Structures', 'CS201', 'Study of data organization and manipulation', 4, 3, true, current_timestamp);
insert into course (career_id, name, code, description, credits, semester_level, active, registration_date ) values (2, 'Thermodynamics', 'ME101', 'Fundamentals of thermodynamics', 4, 1, true, current_timestamp);
insert into course (career_id, name, code, description, credits, semester_level, active, registration_date ) values (2, 'Fluid Mechanics', 'ME201', 'Study of fluid behavior and properties', 4, 3, true, current_timestamp);
insert into course (career_id, name, code, description, credits, semester_level, active, registration_date ) values (3, 'World History', 'HIS101', 'Overview of global historical events', 4, 1, true, current_timestamp);

insert into student (name, last_name, email, document_number, phone_number, date_of_birth, address, active, created_at) values ('Alice', 'Johnson', 'test@test.com', '123456789', '555-1234', '2000-01-15', '123 Main St', true, current_timestamp);
insert into student (name, last_name, email, document_number, phone_number, date_of_birth, address, active, created_at) values ('Bob', 'Smith', 'bob@gmail.com', '987654321', '555-5678', '1999-05-22', '456 Elm St', true, current_timestamp);
insert into student (name, last_name, email, document_number, phone_number, date_of_birth, address, active, created_at) values ('Charlie', 'Brown', 'charlie@gmail.com', '456789123', '555-8765', '2001-09-10', '789 Oak St', true, current_timestamp);
