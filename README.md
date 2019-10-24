Usage
------
* There are two endpoints:
  - `minify`: shortens urls
  - `expand`: returns the "true" long url (if it exists). This will also keep
    count of the number of "clicks" a url has.

* You need to escape the `/` in your url's with the url-encoded character `%2F`.
  This would be done by a client in practice, but since we're just curling
  you'll need to replace it manually.

```shell
# start the server
$ sbt run

# open another shell
$ curl localhost:8080/minify/"www.bing.com"
troops.ai/qG9ncr%

$ curl localhost:8080/expand/"troops.ai%2FqG9ncr" 
www.bing.com%

$ curl localhost:8080/expand/"nonsense"
url not found in db%
```

Design
----------
* I decided to go with `http4s`, and an in-memory "database". In a production
  application, you'd obviously want something persistent. However for the
  purposes of this challenge I think it's fine. 
    - The `Dao` is DI'ed, so it's easy to replace this piece with a real
      implementation in the future. The impurity is contained to the *fake*
      in-memory dao.

* Along with the philosophy of `http4s` I tried to keep this application as
  *pure* as possible. That means all side-effects are wrapped in my `F[_]`
  effect (here I use `cats.effect.IO`), and handled with `unsafeRunAsync` at the
  "*end of the world*".

* I initially used the `Int` db index as the minified id, however I think the
  alphanumeric keys are more secure/harder to guess.
