# BSINFO ABSCHLUSSPROJEKT
GRUPPE 2 - KEVIN KRAUS & ABASSIN SALEH

[![Build Status](https://travis-ci.com/kevin-kraus/bsinfo-ap-project.svg?branch=master)](https://travis-ci.com/kevin-kraus/bsinfo-ap-project)
[![codecov](https://codecov.io/gh/kevin-kraus/bsinfo-ap-project/branch/master/graph/badge.svg?token=QWJFTTFMS6)](https://codecov.io/gh/kevin-kraus/bsinfo-ap-project)

---

Dies ist das Repository, in welchem jegliche Arbeit und Dokumentation zum Abschlussprojekt von Kevin Kraus und Abassin Saleh in AP gemacht werden wird.
### Lokales Starten
#### Backend
1. Im Archiv den Ordner `backend` öffnen.
2. Den Befehl `java -jar bsinfo_gruppe2_be.jar` ausführen.

#### Frontend
1. Im Archiv Hauptverzeichnis den Ordner `frontend` öffnen
2. Den gesamten Inhalt des Ordners `build` in das Root-Verzeichnis eines Webserver bewegen.
3. Webserver aufrufen
#####Alternative:
1. Im Archiv den Ordner `frontend` öffnen.
2. Führe den Befehl `npm i serve -g` aus.
3. Führe den Befehl `serve -s build -p 3000` aus.
4. Das Frontend ist nun unter `localhost:3000` verfügbar.


**ACHTUNG!: ES IST WICHTIG DAS DAS FRONTEND AUF PORT 3000 LÄUFT. ANSONSTEN GIBT ES PROBLEME MIT CORS!**


#### Datenbank
1. Im Archiv den Ordner `database` öffnen
2. Datenbank mit dem `init.sql` aufsetzen

### Ports

#### Datenbank
User: `bsinfo`
Passwort: `bsinfo`
Datenbank: `bsinfo`
Host: `localhost:3306`

#### Backend
Port: `8080`
API-Pfade: `http://localhost:8080/api/v1/...`
Postman: Im Archiv als `postman.json` -> in Postman importieren

#### Frontend
Verfügbare Seiten: 
- Login unter `/login`
- Registrierung unter `/register`
- Benutzerverwaltung unter `/userManagement`
- Bearbeitung eines Benutzers unter `/edit/{username}`
- Logout unter `/logout`

---
<p style="text-align: center;"> © 2021 - Kevin Kraus | Abassin Saleh </p>


