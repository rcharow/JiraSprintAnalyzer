# JiraSprintAnalyzer
Application for analyzing Jira sprint data

## Configuring Jira Connection
1. Add a new ``config.properties`` file to `/ui/src/main/resources`
2. Add Jira connection properties to the file:

```
jira.self=[base url to your jira instance]
jira.username=[username for authenticating requests to jira api]
jira.password=[password for authenticating requests to jira api]

```

## Running the app
1. ``cd /JiraSprintAnalyzer/ui/src/main/resources/static``
2. ``npm install``
3. ``gulp``
    - Gulp will compile the ts to js using webpack, and the sass to css
    - Run `gulp watch` during development
4. ``mvn spring-boot:run``
5. Visit localhost:9000


