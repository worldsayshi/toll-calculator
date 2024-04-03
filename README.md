# Toll fee calculator 1.0

## Assumptions

- There is no customer to talk to.
    Having a throughout discussion to verify the actual requirements is not on the table, otherwise that would be the first priority.
- The Toll fee calculator needs to be available to a number of other services, like toll stations, to be useful.
    For this we will implement it as a Rest service.

## Outstanding issues

- Deploy environment
    The service needs to be deployed somewhere. Suggestion: Set it up in kubernetes (or specifically k3s). Here we should consider where the customer is keeping the rest of the infrastructure.
- Storing vehicle events
    Unless we can assume that there is another service built with the purpose of keeping track of when vehicles pass through toll stations we need to track this.
    For this purpose a SQL database should be set up with a table with car registration number and date columns.
- Storage backups
    Vehicle events should be safely and automatically backed up with a reasonable frequency.
- Admin GUI
    Some parameters of the requirements could be expected to change as the system is evaluated. It could be a good idea to allow an administrator to adjust these.

