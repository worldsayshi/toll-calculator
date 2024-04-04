# Toll fee calculator 1.5

## Assumptions

- There is no customer/stake holders to talk to. (since it is a fictional scenario)
    Having a throughout discussion to verify the actual requirements is not on the table, otherwise that would be the first priority.
    Ideally in such discussion an expert on the tolling law should be consulted. There should also be a discussion with someone responsible for relevant IT infrastructure within the municipality/state as well as the company responsible for the toll gate software to ensure coherent non-functional requirements.
- The Toll fee calculator needs to be available to a number of other services, like toll stations, to be useful.
    For this we will implement it as a Rest service.
- We need to also store the events that occur when a vehicle passes through a toll station somewhere.

## Solution

The current solution includes:

- Kubernetes config for deploying the service
- Spring Boot based Rest API for handling toll calculation and vehicle events
- Swagger GUI for exploring the API
- Toll station vehicle event storage
- Unit tests for Toll fee calculations
- An initial playwright e2e-test solution
- Partially rewritten Toll Calculator.
    Should hopefully be more maintanable this way.
- Semi-automatic semantic versioning, unit testing and build system using `make tag-and-build`
    Only verified to work on Ubuntu (On WSL2)

## Outstanding issues

- Talk to stake holders
    - Toll station suppliers
        We need to figure out how the toll station suppliers want to call this API.
    - Customer
        To verify that the solution is going in the right direction and to drill down into requirements in more detail.
    - Customer tech infrastructure responsible
        To verify that Kubernetes is indeed a suitable platform. Otherwise pivot. Check if they have relevant existing infrastructure conventions.
    - Legal expert?
        We should verify that the solution fits the law directives it is intended to fullfil. 
- Find suitable deployment environment
    The service needs to be deployed somewhere. Azure? Customer infrastructure?
    Configuration for deploying to kubernetes has been prepared and the solution has been verified to work on Kubernetes in Docker Desktop.
- Make sure that dates are handled correctly
    Java date/time API is a bit of a mess. dates are handled a bit inconsistently, could hide more bugs.
    I'm mostly confident in the current solution but for example it should be verified that timezones are handled correctly when server is deployed in a different timezone to where the toll station is situated.
- Some "special case" holidays are not covered.
    Like Midsommarafton. These needs to be handled either through an admin GUI or by overriding library configurations.
- Connect to external vehicle information service
    In order to know what type of vehicle has passed the toll station an external service, such as fordonsfakta.se will need to be used.
    The task of looking up the vehicle information should be split from the task of registring a vehicle event in order to ensure that master data integrity is prioritized.
    The lookup can be done in the /calculateToll endpoint or in a worker that runs periodically.
- Client Authentication
    Presumably the toll stations need to connect to this service over the internet. To ensure safe operation each toll station client software should authenticate itself when connecting to this service. Suggestion: Set up a procedure for generating API keys, preferably enrollment can be done through the Admin GUI.
- Storage backups
    Vehicle events should be safely and automatically backed up with a reasonable frequency.
- Manage Database migrations
    A tool such as Flyway should be used to ensure that we have the evolution of the database schema under control.
- Billing
    A separate solution should be set up to handle billing the vehicle owners. Before doing this additional requirements need to be collected. It could perhaps make sense to keep the Vehicle Events in that service.
- Admin GUI and User authentication
    Some parameters of the requirements could be expected to change as the system is evaluated and/or used. It could be a good idea to allow an administrator to adjust these.
    One important use case is administration of public holidays. Another is the ability to add exceptions for certain unforseen events.
    For this to work in a secure way we also need a way to authenticate users. Preferably this would integrate with existing customer SSO solution. This could be accomplished with oauth2-proxy and Keycloak.
    One additional nice to have use case for such Admin GUI could be a dashboard showing a statistical overview of toll station events. This could be achieved with Grafana.
- Continuous Integration
    A number of steps can be taken to improve CI/CD maturity of the project:
    - Use ArgoCD and image automation to automate deployment to a test environment
    - Run the e2e tests with playwright as an agent in the cluster automatically after the automatic deployment and present the results
- E2E testing of Vehicle Events
    Vehicle Event registration should be covered by Playwright tests.


# Build

To build and push (to a docker registry) all Dockerfile based component in this repo run:
```bash
make tag-and-build
```

# Deploy (to Docker Desktop k8s cluster)

Assumption: docker desktop installed

- Install kubeconfig:
    cp /mnt/c/Users/$(whoami)/.kube/config ~/.kube/docker-k8s.config
    export KUBECONFIG=$HOME/.kube/docker-k8s.config
- Check connection:
    kubectl cluster-info
- Make sure that no other service is running on port 80!
- Deploy:
    kubectl apply -k cluster-config
- Look at Swagger UI:
    http://localhost/swagger-ui/index.html
- Try it with curl (quotes are important):
    Try the below url in the browser or use curl in Powershell. It will likely not work from WSL:
    curl "http://localhost/get-toll-fee?vehicle=Car&dates=2000-10-31T01:30:00.000-05:00"
