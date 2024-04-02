## How the app was created:
- https://start.spring.io/
- add spring web dependency
- chmod +x gradlew
- find . -name "*Zone.Identifier" -type f -delete
- Add HelloController from https://spring.io/guides/gs/spring-boot
- [-] Add org.springdoc.openapi-gradle-plugin
- Add org.springdoc:springdoc-openapi-starter-webmvc-ui

# Dev env

Install java-21 openjdk

sudo apt install openjdk-21-jre-headless
./gradlew test
./gradlew bootRun

# TODO:s

- [X] Add Spring and Gradle
- [ ] Add swagger
- [ ] Add initial unit tests
- [ ] Add Dockerfile
- Add more unit tests
- (verify that the requirements are filled with the tests)
- Make it deployable to k8s?
- Add playwright tests

- Extensions:
    - Storage use case
        - Add database storage requirements
        - Add database deployment
        - Add backup
        - Add code for database use case
    - ChatGPT API assistant
        - swagger + chatgpt => ... => profit
