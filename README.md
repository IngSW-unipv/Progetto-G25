Progetto-G25: ‘Bitebyte’, Sistema di Utilizzo/Gestione Distributore Automatico

Il programma consente di simulare tutto ciò che concerne l’accesso e l’utilizzo di un distributore automatico di cibi e bevande (sia fredde che calde), distinguendo tra Cliente e Amministratore. Il sistema è quindi in grado di simulare entrambe le figure e la loro esperienza, considerando tutte le più comuni interazioni che queste possono eseguire con il sistema stesso.
Di seguito sono riportate le istruzioni per il corretto utilizzo del sistema:
________________________________________
Download di Git

Scaricare e configurare Git seguendo le istruzioni riportate al seguente link:
https://git-scm.com/book/en/v2/Getting-Started-Installing-Git
________________________________________
Clonazione del Repository GitHub

Operazioni preliminari per scaricare il repository Git sul proprio computer:

  -	 Aprire GitHub e accedere al seguente repository: https://github.com/IngSW-unipv/Progetto-G25.git

  -	 Selezionare "Code" e copiare l'URL fornito: https://github.com/IngSW-unipv/Progetto-G25.git

  -	 Aprire Git Bash, un terminale o cmd, e spostarsi dalla directory corrente a quella in cui operare la clonazione.

  -	 Digitare git clone, incollare l'URL precedentemente copiato e premere Invio.

________________________________________
Importazione del Progetto

Importare il progetto nell’IDE desiderato.
________________________________________
Configurazione dell’IDE e delle Librerie

La maggior parte delle interfacce grafiche sono state realizzate tramite JavaFX, quindi è necessario installare la relativa SDK e configurare opportunamente l'IDE. Maggiori informazioni sono disponibili al link:
https://openjfx.io/openjfx-docs/
Inoltre, è necessario importare la libreria ‘MySQLConnector’: Il sistema prevede frequenti operazioni di lettura/scrittura da un database. Maggiori informazioni sono disponibili al link:
https://github.com/mysql/mysql-connector-j
________________________________________
Configurazione del Database

Il sistema basa praticamente la sua intera operatività sull’interazione con un database che deve essere installato localmente. Pertanto, i passaggi da seguire per una corretta configurazione sono:
1.	Scaricare e Installare MySQL Workbench, configurando un’istanza locale.
(Link per il download: https://dev.mysql.com/downloads/workbench/)
2.	Scaricare e importare il dump del database
     -	Scaricare la cartella Dump dal repository: https://github.com/IngSW-unipv/Progetto-G25

     -	Aprire MySQL Workbench e connettersi alla propria istanza locale.

     -	Andare su Data Import/Restore e importare la cartella.

4.	Configurare il file di connessione
     -	Aprire il file di configurazione nel progetto.

     -	Nel progetto caricato all’interno dell’IDE, aggiornare i parametri del file:
        /PROGETTO_G25/properties/dbconfig.properties con quelli della propria istanza locale.


---------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------

Project-G25: ‘Bitebyte’, Vending Machine Usage/Management System

The program allows for the simulation of everything related to accessing and using a vending machine for food and beverages (both hot and cold), distinguishing between Customer and Administrator roles. The system is therefore capable of simulating both roles and their experiences, considering all the most common interactions they can perform with the system itself.
Below are the instructions for the correct use of the system:
________________________________________
Git Download

Download and configure Git using the instructions provided at the following link:
https://git-scm.com/book/en/v2/Getting-Started-Installing-Git
________________________________________
Cloning the GitHub Repository

Preliminary operations to download the Git repository to your computer:

 -	 Open GitHub and access the following repository: https://github.com/IngSW-unipv/Progetto-G25.git

 -	 Select "Code" and copy the provided URL: https://github.com/IngSW-unipv/Progetto-G25.git

 -	 Open Git Bash, a terminal, or cmd, and navigate from the current directory to the one where you want to perform the cloning.

 -	 Type git clone, paste the previously copied URL, and press Enter.

________________________________________
Project Import

Import the project into the desired IDE.
________________________________________
IDE and Library Configuration

Most graphical interfaces have been developed using JavaFX, so it is necessary to install the corresponding SDK and properly configure the IDE. More information is available at:
https://openjfx.io/openjfx-docs/
Additionally, you need to import the ‘MySQLConnector’ library: The system performs frequent read/write operations on a database. More information is available at:
https://github.com/mysql/mysql-connector-j
________________________________________
Database Configuration

The system’s entire functionality is based on interaction with a database that must be installed locally. Therefore, follow these steps for correct setup:
1.	Download and Install MySQL Workbench, configuring a local instance.
(Download link: https://dev.mysql.com/downloads/workbench/)
2.	Download and import the database dump
3.	Download the Dump folder from the repository: https://github.com/IngSW-unipv/Progetto-G25

    -	 Open MySQL Workbench and connect to your local instance.

    -  Go to Data Import/Restore and import the folder.

4.	Configure the connection file
   
    -  Open the configuration file in the project.

    -  In the project loaded within the IDE, update the parameters of the file:
       /PROGETTO_G25/properties/dbconfig.properties with those of your local instance.
