Refactors Report
=================

1. Singleton pattern for DB connection - dry up entity classes by abstracting DB related operations
   -> add postgres set up and get tests passing (https://github.com/full-of-foo/student-timetable/commit/349f086c95956826558e612dfe101324164613f3)
   -> move entities to 'models' package and implement DB connection as singleton (https://github.com/full-of-foo/student-timetable/commit/c3db86f05269eb0afb4327854c59c85ceca5d7bb)  
   
2. DAO pattern - dry up entity classes by abstracting DB related operations
   -> create 'daos' package and abstract model CRUD behaviour into independent DAO objects (https://github.com/full-of-foo/student-timetable/commit/9c7c4e3cd3cab6f4306fddeb132e78d9baa2dcc7)
   
3. Remove 'Primitive obsession' in DAO layer
   -> pass model objects to 'create' and 'update' rather than primitive attributes (https://github.com/full-of-foo/student-timetable/commit/9c7c4e3cd3cab6f4306fddeb132e78d9baa2dcc7)
   
4. DAO factory pattern - encapsulate the similar DAO behaviour with a factory
   -> create a DAO factory to return instances of DAOs (https://github.com/full-of-foo/student-timetable/commit/b2fb67e3ba94496c43f5bd7242d866e4f7811e68)
   
5. Better organise tests
   -> Create a 'test' source directory to contain tests (https://github.com/full-of-foo/student-timetable/commit/5ec35201e9ce60683bf32c6c73e404ee3a357e89)
   
6. Extract hierarchy for DAO
   -> Create 'BaseDAO' abstract class to dry up DAO crud code (https://github.com/full-of-foo/student-timetable/commit/e8460f7d5d3ebbc25c14c2d9d076f1a2694cbd7a)
   
7. Implement POJO pattern for models and define a ScheduleArrayList
   -> Implement Course, Schedule and Offering as a POJO classes and extract Schedule list logic to an implementation of ArrayList (https://github.com/full-of-foo/student-timetable/commit/a9d9f080a759c24ebbce94b58c3c36cf9cd9519f)
   
8. Extract report to be non-persistent model
  -> Move reporting logic to a report manager and report writer and create model (https://github.com/full-of-foo/student-timetable/commit/cfea2b670e219497db1de3eed5fac105babb29b2)
  
9. Implement MVC pattern for generating reports
  -> Create Report controller and view, add reportLabel and generateReportButton to view and bind touchEvent to button which adds the reportText to the label 
  (https://github.com/full-of-foo/student-timetable/commit/cfea2b670e219497db1de3eed5fac105babb29b2)
  
10. Extract hierarchy for tests
  -> Create 'BaseTest' superclass to encapsulate the creating, tearing of tables and seeding of data ()