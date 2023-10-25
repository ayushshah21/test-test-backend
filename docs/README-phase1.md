# Phase 1 Design
### Team Information:
* Team Name: rubber-ducks
* Team Number: 29
* Members:
    * Carl Chang
    * Marco Clark
    * Aaron Huang
    * Brynna Morris
    * Ayush Shah
---
### User Stories
* As a user, I want to post a short idea, so that I can share my thoughts with my colleagues and the company securely.
* As a user, I want to view and scroll through a feed of ideas posted by my colleagues, so that I am able to stay updated on what's being discussed within the company regarding naked mole-rat.
* As a user, I want to like a message or idea, so that I can show my appreciation and support for the idea being proposed in a post.
* As a user, I want to remove a like from a message or idea, so that I am able to make changes if I changed my opinion or accidentally liked the post.
* As a user, I want to have a user-friendly and intuitive interface, so that I can easily navigate and use the app.

### User Story Tests

### Listing of the routes
* Should be a number of routes that do various HTTP requests. We want routes 
* "/" should map to index.html, or whatever the homepage of the application ends up as
* POST route of "/proposals" will add a new proposal/post to the database. Using /post may get a little confusing so I think proposals is nice (it is the point of the app). Also initialize the number of likes to 0.
* PUT of "/proposals/:id/:liking" will be invoked when a message is liked. If liking is the string "true", then the number of likes for the post will be set to 1. If liking is "false", then the number of likes will be set to 0. This is because we do not track users yet, so every user that opens the application will be treated as the same user. 
* DELETE "/proposals/:id" (although not supported in sprint 5) will delete a proposal/post
* GET "/proposals" would return all proposals. My thoughts are we use this when the page is loaded to print all proposal information
* GET "proposals/:id" would return a specific proposal. I don't see use for this in phase 1 but it could be useful later on. 
#### Format of Passed Data:
* Only POST routes need to send a body to the backend and that should be formatted as such:
    ```
    {'proposalText':'Insert text here','numLikes':##}
    ```
* Format of JSON that is **returned** by the backend: 
    ```
    {"mStatus":(status), "mData":[{"propId":(id),"propText":(text),"numLikes":(likes),"dateCreated":(date)},...]}
    ``````
### Listing of the tests
* Backend
    * Created AppTest, which runs all test suites for the application.
    * DataRowTest tests data rows by creating objects and ensuring that the data entered into the object's constructor is unchanged. There is a second copy constructor for DataRow that was also tested here.
    * StructuredResponseTest tested the constructor for StructuredResponse. I generated data and created a DataRow object as the "data" field for the StructuredResponse and tested if the data stayed unchanged after being passed into the constructor. 
    * DatabaseTest will be implemented later on.
* Admin
    * Created Database tests to test the following functions and ensure code is functional after changes or modifications: selectOne, deleteRow, and insertRow.
* Mobile
* https://docs.flutter.dev/cookbook/testing/unit/introduction 
* 
* An introduction to unit testing
* 
* Create unit testing in Flutter, explaining how to set up and execute tests using the test package. It outlines. Add the test dependency which is a test package for writing tests in Dart. Using the website above it will *guide me in creating dedicated test files. I can try runninga test using IntelliJ or VSCode. There are Flutter plugins for IntelliJ and VSCode support running tests. The primary purpose of unit testing is to ensure that my app continues to work as I add more features or make changes to existing functionality.
* 
* Espresso Testing in Android
Espresso can run tests on the user interface on Android which gradle will set up a testing framework for us. The emulator will simulate clicks, inspect UI elements, and eventually Android Studio will report that the tests passed.
* 
* Web
* Listing of the tests
* Created viewIdeas.ts, videoIdeas.test.ts and and same process for postIdeas.ts
* Used jest and React for testing