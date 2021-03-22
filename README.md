# wcapp

## Docker

Build project:
`mvnw clean install`

Build an image:
`docker build -t wcapp .`

Start container:
`docker run -p 8080:8080 -it wcapp:latest`
