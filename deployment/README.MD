For evaluating performance.

We do not need any docker-compose cache or DB initialization.

Run with:  

```shell
docker-compose rm -f
docker-compose pull
docker-compose up --build --force-recreate --remove-orphans db-service nginx spring-jdbc spring-r2dbc influxdb grafana 
docker-compose run --rm k6 run /k6-scripts/round-test.js
```
