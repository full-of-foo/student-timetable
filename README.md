student-timetable
=================

A Course is a class that could be offered. 
An Offering is a version of a class, taught on some schedule. 
DaysTimes is a comma-separated string of days and times (“M10,T11,F10”).
A Schedule is a particular set of offerings that a student has chosen. 

There are business rules about how schedules can be formed:

•	All students must take at least 12 credits worth of courses.
•	No student may take more than 18 credits, unless explicit permission is given.
•	No two offerings that a student may take can be in the same timeslot.
•	All courses must be unique.

These business rules are not enforced by the database, but by a set of Java classes that interact with the database and check the schedules that students select to ensure conformance with the business rules. 
There are three classes which correspond to the database tables, along with a report class and two test classes

You should examine the code in these classes for possible "bad smells" which might be alleviated by refactoring. A catalogue of bad smells and examples of how to restructure code to eliminate smells can be found at http://sourcemaking.com/refactoring .

Your deliverable should consist of the following:

•	a report describing the series of refactorings you applied to the code. For each refactoring, you should explain why and how you decided to apply the refactoring and present the relevant code before and after the refactoring
•	the final set of refactored classes
•	a repository showing the progress of your work

Apply refactoring to a set of classes interacting with an underlying database, making use of JUnit for unit testing where necessary and Git for version control.

Document the refactorings that you apply to the code, justifying the use of each refactoring.

Submit:

the refactored classes
the report describing the refactorings applied
a repository showing the progress made during refactoring
Deadline: Thursday 20th February, midnight


