# jak uruchomić
1. 'vagrant up' aby uruchomic baze
2. stworz bazę 'awsbackupscheduler'
3. wyeksportuj zmienne środowiskowe AWS
4. wystartuj Application.java i wejdz na http://localhost:8080/html/backups dla testowania

# OPCJONALNIE:
uwzględnij:
https://projectlombok.org/features/all

#DEPLOY
build lokalnie i przekopjuj do folderu builded
ssh
git pull
java -jar gs-rest-service-0.1.0.jar
