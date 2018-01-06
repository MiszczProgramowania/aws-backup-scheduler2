# jak uruchomić
1. 'vagrant up' aby uruchomic baze
2. stworz bazę 'trading-statistics-service'
3. wyeksportuj zmienne środowiskowe AWS
4. wystartuj Application.java i wejdz na http://localhost:8080/backups dla testowania

# TODO:
wywalic mavena ignore gradlewraper gradlew

# OPCJONALNIE:
uwzględnij:
https://projectlombok.org/features/all


For testing purposes:
INSERT INTO `server` (`last_backup`, `name`, `volume_id`)
 VALUES ("2018-01-04 15:32:48", "test", "vol-0091b36e0d1355fb0");

#DEPLOY
build lokalnie i przekopjuj do folderu builded
ssh
git pull
java -jar gs-rest-service-0.1.0.jar

## TODO
1. HTML FORM adding server