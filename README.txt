Maize Game - Educational Maze Game Aplication
EECS 481: Software Engineering

Team Members: Miguel Abreu, Chris Dyer, Erika Jansen, Avi King, Robert Micatka

This repository holds the code for our maze game application. We have a dependency on ASK Scanning Library and
a separate repository holding the code for a website our application requires access to.
 
In order to set up our application:

1. Clone our application from: https://github.com/s1syphus/teammaze

2. If you don't have access to the ASK Scanning Library give Erika your github username (emjansen@umich.edu)
   Then Clone a copy of Chris's ASK Scanning Library from: https://github.com/emjansen/ASKScanningLibrary
   
3. If you don't already have eclipse download the Android SDK bundle from: http://developer.android.com/sdk/index.html

4. Import both projects into eclipse
   File > Import > Existing Android Code Into Workspace 
   Browse to the folder containing the cloned repository > Finish

5. Add the ASK scanning library to the TeamMaize project (if it isn't already) 
   Right click on TeamMaize folder in eclipse
   Properties > Android > 
   Under Library click "Add�" Select ASKScanningLibrary (should show up if has been imported)

6. Run the application: Click on Android Virtual Device Manager Button in eclipse top bar
   Click "New..." and create a device emulator
   Click Run button

The other required part of our project is to run a (local) web server for the questions
In order to run this:

1. Download the local webserver software
-Wamp if you're on a PC
--www.wampserver.com/en
-Mamp if you're on a Mac
--www.mamp.info/en/index.html

2. Get the website and other necessary files from: https://github.com/migabreu/maizemaze 

3. Place a copy of the website_wip (from the cloned repro) into the htdocs folder (mamp only)
-For wamp users, website_wip goes into the www folder found in the wamp folder (/your_installation_path/www)

4. Unzip the mysql file found in website_wip/"sql database" (found in website_wip folder) and place the 
   extracted folder into the db folder
-On a mac, the db folder can be found in Applications/MAMP/
-On a pc, the mysql folder contents go into /Your_installationPath/bin/mysql/mysql-version/data

5. Start Mamp/Wamp

6. Get your apache port number from the preferences option

7. Start the servers
-the servers that should be running are appache and mysql

8. To access the website, open a browser and type in http://localhost:port_number/website_wip/index.html

Connecting the android app to the website

1. Get your ip address
-on a pc, go to start -> type cmd in the input text box -> click cmd and open, then type in ipconfig and locate your local ip address
-on a mac, go to system preferences -> network -> Select your current web device -> The ip address listed in the description under status : connected

2. In the code of MazeGUI.java search for the comment // Change IP Here //
-Edit this line in this file and add the ip address
-If your port number is different than 8888 replace it with your apache Port Number.

3. You're app now connects to the database

After all this is complete you can run the program.

