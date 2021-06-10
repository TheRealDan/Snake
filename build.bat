call gradlew desktop:dist
del Snake.jar
del builds\Snake.jar
rename desktop\build\libs\desktop-latest.jar Snake.jar
robocopy desktop\build\libs\ builds\ Snake.jar