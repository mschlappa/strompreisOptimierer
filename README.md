# strompreisOptimierer

Dieses Java Steuerungs-Tool hilft in Verbindung mit dem variablen Stromtarif von aWATTar und einem stationären Stromspeicher (hier:E3DC) den Strompreis für Netzbezug zu minimieren. 
Dafür wird in günstigen PreisIntervallen die Entladung des Stromspeichers gesperrt, um teure Preisintervalle mit dem Speicher zu überbrücken.

Zur Steuerung des Stromspeichers verwende ich zusätzlich mein Tool [e3dcset](https://github.com/mschlappa/e3dcset) aus meinem anderen Repo.
