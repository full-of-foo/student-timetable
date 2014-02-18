Refactors Report
=================

1. Singleton pattern for DB connection - dry up entity classes by abstracting DB related operations
   -> add postgres set up and get tests passing (https://github.com/full-of-foo/student-timetable/commit/349f086c95956826558e612dfe101324164613f3)
   -> move entities to 'models' package and implement DB connection as singleton (https://github.com/full-of-foo/student-timetable/commit/c3db86f05269eb0afb4327854c59c85ceca5d7bb)  
   
2. DAO pattern - dry up entity classes by abstracting DB related operations
   -> create 'daos' package and abstract model CRUD behaviour into independent DAO objects ()
   
3. Remove 'Primitive obsession' in DAO layer
   -> pass model objects to 'create' and 'update' rather than primitive attributes ()
    