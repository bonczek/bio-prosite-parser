# bio-prosite-parser

#Zbudowanie projektu i uruchomienie testów
mvn clean install

#uruchomienie aplikacji z konsoli
cd target/
java -jar prosite-parser-1.0-SNAPSHOT.jar {$1} {$2}

- {$1} - wzorzec PROSITE do wyszukania, reguły oddzielone '-' np. e(2,3)-e
- {$2} - sekwencja do przeszukania
