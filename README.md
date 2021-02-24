# Local environment 
## Install docker and docker-compose
- https://docs.docker.com/engine/install/ubuntu/
- https://docs.docker.com/engine/install/linux-postinstall/
- https://docs.docker.com/compose/install/

## Run docker-compose
- on main directory
```shell
docker-compose up
```
## Running tests
```shell
./mvnw clean install
```

## Running application
```shell
./mvnw spring-boot:run
```
## Create Game
```shell
curl --header "Content-Type: application/json" \
--request POST \
http://localhost:8080/games
```
## Make a move
```shell
curl --header "Content-Type: application/json" \
--request PUT \
http://localhost:8080/games/{gameId}/pits/{pitId}
```