@echo off

REM python "./scripts/analytics-frontend-combine_json_files.py"

start "" http://localhost:8001/SaaSAnalyticsFrontEnd/index.html
python -m http.server 8001
