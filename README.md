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
2. ``rm -rf node_modules``
3. ``npm install``
4. ``gulp``
    - Gulp will compile the ts to js using webpack, and the sass to css
    - Run `gulp watch` during development
5. ``mvn spring-boot:run``
6. Visit [localhost:900](http://localhost:9000)

## Building app for deployment
1. ``cd /JiraSprintAnalyzer/ui/src/main/resources/static``
2. ``rm -rf node_modules``
3. ``npm install``
4. ``gulp``
5. ``cd /JiraSprintAnalyzer/ui``
6. ``mvn package``
7. ``java -jar target/ui-0.0.1-SNAPSHOT.jar``
6. Visit [localhost:900](http://localhost:9000)
