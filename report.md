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
   -> Create a 'test' source directory to contain tests
    