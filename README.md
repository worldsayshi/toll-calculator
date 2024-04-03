# Toll fee calculator 1.0

## Assumptions

- There is no customer/stake holders to talk to. (since it is a fictional scenario)
    Having a throughout discussion to verify the actual requirements is not on the table, otherwise that would be the first priority.
    Ideally in such discussion an expert on the tolling law should be consulted. There should also be a discussion with someone responsible for relevant IT infrastructure within the municipality/state as well as the company responsible for the toll gate software to ensure coherent non-functional requirements.
- The Toll fee calculator needs to be available to a number of other services, like toll stations, to be useful.
    For this we will implement it as a Rest service.

## Outstanding issues

- Deploy environment
    The service needs to be deployed somewhere.
    Suggestion: Set it up in kubernetes (or specifically k3s). Here we should consider where the customer is keeping the rest of the infrastructure.
- Storing vehicle events
    Unless we can assume that there is another service built with the purpose of keeping track of when vehicles pass through toll stations we need to track this.
    For this purpose a SQL database should be set up with a table with car registration number and date columns.
- Connect to external vehicle information service
    In order to know what type of vehicle has passed the toll station an external service, such as fordonsfakta.se will need to be used.
    The task of looking up the vehicle information should be split from the task of registring a vehicle event in order to ensure that master data is prioritized. 
    The lookup can be done by a worker that can run before it is time for billing the vehicle owners.
- Client Authentication
    Presumably the toll stations need to connect to this service over the internet. To ensure safe operation each toll station client software should authenticate itself when connecting to this service. Suggestion: Set up a procedure for generating API keys, preferably this procedure can be done through the Admin GUI.
- Storage backups
    Vehicle events should be safely and automatically backed up with a reasonable frequency.
- Billing
    A separate solution should be set up to handle billing the vehicle owners. Before doing this additional requirements need to be collected.
- Admin GUI and User authentication (nice to have)
    Some parameters of the requirements could be expected to change as the system is evaluated. It could be a good idea to allow an administrator to adjust these.
    For this to work in a secure way we also need a way to authenticate users. Preferably this would integrate with existing customer SSO solution. This could be accomplished with oauth2-proxy and Keycloak.
    One additional nice to have use case for such Admin GUI could be a dashboard showing a statistical overview of toll station events. This could be achieved with Grafana.
- Continuous Integration
    A number of steps can be taken to improve CI/CD maturity of the project:
    - Use ArgoCD and image automation to automate deployment to a test environment
    - Run the e2e tests with playwright as an agent in the cluster automatically after the automatic deployment and present the results

# Commands

```bash
# List tags in registry
./scripts/last-build-tag.sh tollCalculator
```

# Run locally

Assumption: docker desktop installed on WSL2

- Install kubeconfig:
    cp /mnt/c/Users/$(whoami)/.kube/config ~/.kube/docker-k8s.config
    export KUBECONFIG=$HOME/.kube/docker-k8s.config
- Check connection:
    kubectl cluster-info
- Deploy:
    kubectl apply -k cluster-config
