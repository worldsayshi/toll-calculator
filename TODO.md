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


## playwright


sudo npx playwright install-deps

Workaround for installing npm using nvm:
sudo ln -s "$NVM_DIR/versions/node/$(nvm version)/bin/node" "/usr/local/bin/node"
sudo ln -s "$NVM_DIR/versions/node/$(nvm version)/bin/npm" "/usr/local/bin/npm"
sudo ln -s "$NVM_DIR/versions/node/$(nvm version)/bin/npx" "/usr/local/bin/npx"

# TODO:s

- [X] Add Spring and Gradle
- [X] Add swagger
- [X] Add initial unit tests
- [ ] Add Dockerfile
- [ ] Fix failing test
- [/] Add more unit tests
    (verify that the requirements are filled with the tests)
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
