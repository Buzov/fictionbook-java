@echo off

set FB_HOME=%~dp0..

set CLASSPATH=%CLASSPATH%
set CLASSPATH=%FB_HOME%\lib\thirdparty\commons-codec.jar;%CLASSPATH%
set CLASSPATH=%FB_HOME%\lib\thirdparty\commons-logging.jar;%CLASSPATH%
set CLASSPATH=%FB_HOME%\lib\thirdparty\dom4j.jar;%CLASSPATH%
set CLASSPATH=%FB_HOME%\lib\thirdparty\jaxen.jar;%CLASSPATH%
set CLASSPATH=%FB_HOME%\lib\gswing.jar;%CLASSPATH%
set CLASSPATH=%FB_HOME%\lib\fbcommon.jar;%CLASSPATH%
set CLASSPATH=%FB_HOME%\lib\fbreader.jar;%CLASSPATH%

set OPTIONS=
set OPTIONS=%OPTIONS% -cp "%CLASSPATH%"
