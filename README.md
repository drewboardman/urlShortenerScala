A url shortener project implemented in scala.

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
drew.io/qG9ncr%

$ curl localhost:8080/expand/"drew.io%2FqG9ncr"  # replace / with %2F
www.bing.com%

$ curl localhost:8080/expand/"nonsense"
url not found in db%
```
