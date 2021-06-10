call gradlew desktop:dist

FOR /F "tokens=* USEBACKQ" %%F IN (`git describe --tags`) DO (SET describe=%%F)
FOR /f "tokens=1 delims=-" %%a in ("%describe%") do (SET tag=%%a)
FOR /f "tokens=2 delims=-" %%a in ("%describe%") do (SET build=%%a)

robocopy desktop\build\libs\ builds\ desktop-latest.jar
rename builds\desktop-latest.jar "Snake %tag%.%build%.jar"