# ilogos

An attempt to create an application to parse xml files and store result in Postresql database

## Compilation and Deployment:

Create database in Postgresql: 
'''
psql -U postgres < sql_ddl/create_database.sql 
'''

Create database tables: 
'''
psql -U postgres ilogos < sql_ddl/create_tables.sql
'''

Change application properties values in src/main/resources/ to fit your requrements (directories, pool size, database credentials etc)

Run 'gradle assemble' to build and package an application.

Use scripts from scripts direcoty to run an application: 
'''
./scripts/run_application.sh build/libs/ilogos-test-0.1.0-SNAPSHOT.jar on UNIX/Linux or 

.\scripts\run_application.bat build\libs\ilogos-test-0.1.0-SNAPSHOT.jar on Windows
'''


