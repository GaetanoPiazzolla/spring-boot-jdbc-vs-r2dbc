### Input:
- Concurrency: 500 (1) / 250 (2)
- Items retrieved: 6000 / 3000 / 1500
- Containers: 1
- Resources:
    - CPU: 2
    - RAM: 2 gb
    - POOL size: 20
    
### Output:
Simple GET Iterations 

    docker-compose run --rm k6 run /k6-scripts/get-test.js -e USERS=500 -e TYPE=r2dbc

Containerized k6 (1)

- R2DBC: xxxx / xxxx / 10689
- JDBC: xxxx / xxxx / 22765

Containerized k6 (2)

- R2DBC: xxxx / xxxx / xxxx
- JDBC: xxxx / xxxx / xxxx

### Reactions:
R2DBC seems to be better when retrieving 6000 items.
Also in the second case, with 3000 items retrieved, the delta is more or less maintained.
When 1.5k rows are retrieved is where the fun ends, and JDBC starts performing way better; 
almost 100% faster.

![](img.png)

This means that the blocking stack performs better with fewer rows.
In addition, the reactive output can be processed before the call actually ends (which is the whole point of the reactive drivers) 
Resulting in a clear statement: use reactive drivers only when it makes sense to process a STREAM of data and not a TRICKLE :)

Now the question is this: 
Are there lots of applications which needs more than 1500 database rows at once, 
having more than 500 users ? considering also that pagination is an option, the response is NO. 
R2DBC is not a good choice for the overwhelming majority of web application. 

- K6 uses less resources than jmeter - jmeter is way easier to use with that ugly interface - from now on I'll be using only K6 )
- Does passing through the nginx change results ? NO
- Does Jmeter have different results of K6 ? NO