# CSE 216 Fall 2023
### Instructions on running admin code:
    Use information from the Elephant SQL Database

    From the admin-cli directory in the admin-cli branch:

    
    This will execute tests. The testing file is hardcoded to ensure testing is done on the TestingTable table
        POSTGRES_IP=<> POSTGRES_PORT=<> POSTGRES_USER=<> POSTGRES_PASS=<> POSTGRES_databaseTable=<> mvn package

    This will run App.java and will show the menu for editing the database table specified in the environment variable in the console. 
    Be careful when editing here because we can drop the table by just typing 'D' and then 'Y'
        POSTGRES_IP=<> POSTGRES_PORT=<> POSTGRES_USER=<> POSTGRES_PASS=<> POSTGRES_databaseTable=<> mvn exec:java

    Note: For POSTGRES_databaseTable=<> use either TestTable when testing or proposals for editing. 

    mvn javadoc:javadoc