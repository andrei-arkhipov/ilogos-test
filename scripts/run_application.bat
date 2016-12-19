@echo off
setlocal enabledelayedexpansion
for /f %%a in ('where javaw') do (
  set java=%%a
)
java -jar %1