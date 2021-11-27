For evaluating performance.

We do not need any docker-compose cache or DB initialization.

Run with:  

```shell
docker-compose rm -f
docker-compose pull
docker-compose up --build --force-recreate --remove-orphans
```

Tested working with: 

* Docker-compose: 1.27.4 && 1.28.2
* Docker: 19.03.13 && 20.10.3
* Windows 10 && Kubuntu 20.04