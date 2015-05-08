# Maxwell

> Missed it by that much.

I needed to log exceptions in running ClojureScript clients to the
backend. Maxwell uses the Google Closure Library to extract all
available information out of the client and normalize the error. It is
then trivial to send it over the wire.

## Installing

[![Clojars Project](http://clojars.org/maxwell/latest-version.svg)](http://clojars.org/om-routes)

    (:require [maxwell.spy :as spy]
              [maxwell.kaos :as kaos])

Requires `[org.clojure/google-closure-library "0.0-20150505-021ed5b3"]`
or newer, which comes with `[org.clojure/clojurescript "0.0-3255"]`.

[Generated Docs](http://bensu.github.io/maxwell/)

## Examples

To get all available user info we have `spy/all-info`:

```clj
(spy/all-info)
=> {:browser :chrome
    :browser-version "42.0.2311.135"
    :platform :linux
    :platform-version "" ;; Linux has no platform-version
    :engine :webkit
    :engine-version "537.36"
    :screen [1535 789]
    :agent "Mozilla/5.0 (X11; Linux x86_64) ..."}
```
To normalize errors we have `kaos/->map`:

```clj
(kaos/->map (js/Error. "Oh, Max!"))
=> {:message "Oh, Max!"
    :stack ["Error: Oh, Max!" "at .../client.js:419:271)"]
    :file-name "http://localhost:3449/"
    :line-number "Not available"}
```

To catch errors, Maxwell can be used in two ways:

* with try-catch

```clj
(try
  (save-the-world-from-kaos!)
  (catch :default raw-error
    ;; Pardon me while I get my shoe phone.
    (report-to-server (merge (spy/all-info) (kaos/->map raw-error)))))
```

* or with `kaos/watch-errors!`, which takes a
  function, and whenever an error is raised to `window` it will
  normalize it and pass it to said function.

```clj
(kaos/watch-errors! :watch-name
    (fn [normalized-error]
        ;; Chief, would you believe...
        (report-to-server (merge (spy/all-info) normalized-error)))
    {:silence? true})
```

Notice that the registered error-watch has a name and passed the
option to hide the error from the user, `{:silence? true}`. Multiple
watches can be placed and they will not interfere with one another,
except for the `:silence?` value: the last watch to be placed
determines if the error will appear in the console or not.

Maxwell can also be used to do browser checking:

```clj
(println "Don't tell me that they are using IE...")

(when (ie?)
    (println "I asked you not to tell me that!"))
```

## Contributions

Pull requests, issues, and feedback welcome.

## License & Acknowledgments

Special thanks to all ClojureScript contributors and
[David Nolen](https://github.com/swannodette) for all their
outstanding work.

Copyright © 2015 Sebastian Bensusan

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
