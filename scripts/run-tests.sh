@echo off
REM Usage : scripts\run-tests.bat [all^|ui^|api^|postman] [tag]
REM   scripts\run-tests.bat              -> tout
REM   scripts\run-tests.bat ui @smoke    -> UI, scenarios @smoke
setlocal
set SUITE=%~1
if "%SUITE%"=="" set SUITE=all
set TAGS=%~2
cd /d "%~dp0.."

if /i "%SUITE%"=="api"     goto api
if /i "%SUITE%"=="ui"      goto ui
if /i "%SUITE%"=="postman" goto postman
if /i "%SUITE%"=="all"     goto all
echo Suite inconnue : %SUITE% (all^|ui^|api^|postman)
exit /b 1

:all
call :run_api     || exit /b 1
call :run_postman || exit /b 1
call :run_ui      || exit /b 1
goto done

:api
call :run_api || exit /b 1
goto done

:ui
call :run_ui || exit /b 1
goto done

:postman
call :run_postman || exit /b 1
goto done

:run_api
echo ==^> Tests API (RestAssured)
call mvn -B test -Papi
exit /b %errorlevel%

:run_ui
echo ==^> Tests UI (Cucumber + Selenium)
if "%TAGS%"=="" (
  call mvn -B test -Pui -Dheadless=true
) else (
  call mvn -B test -Pui -Dheadless=true "-Dcucumber.filter.tags=%TAGS%"
)
exit /b %errorlevel%

:run_postman
echo ==^> Tests API (Postman / Newman)
if not exist target mkdir target
call newman run postman\restful-booker.postman_collection.json -e postman\env.postman_environment.json --reporters cli,junit,htmlextra --reporter-junit-export target\newman-report.xml --reporter-htmlextra-export target\newman-report.html
exit /b %errorlevel%

:done
echo OK - rapports dans target\
endlocal