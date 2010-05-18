@echo off

call %~dp0setenv.bat

java %OPTIONS% ru.gelin.fictionbook.reader.Main %*
