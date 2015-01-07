# rabbitmq-clj
A RabbitMQ HTTP API client written in Clojure and leaning heavily on clj-http.
Does not implement all endpoints.

## Usage
To install, add the following to your `project.clj`:

`[rabbitmq-clj "0.0.1"]`

API documentation [here](https://github.banksimple.com/pages/ops/rabbitmq-clj/).

This project ships a DSL-like-thing to dispatch the correct commands. The DSL
generally tries to follow the semantics given by
[rabbitmqctl](https://www.rabbitmq.com/man/rabbitmqctl.1.man.html). 

``` clojure
(ns my.project
  (:require [rabbitmq-clj.core :refer :all]))

(configure :port 15000)

(dispatch :users :all)
```

The `(configure)` function accepts any of the following optional keys:

* `:host` - Host of the management API (default: `127.0.0.1`)
* `:port` - Port of the management API (default: `15672`)
* `:username` - User to connect as (default: `guest`)
* `:password` - Password to authenticate with (default: `guest`)

These are maintained in an `api` var in the core namespace which is referred to
by all the API functions. You don't need to call `configure` if you use the
default parameters for the management API.

The DSL itself is as follows:

``` clojure
;; Overview endpoint
(dispatch :overview)

;; Connection endpoints
(dispatch :connections :list)
(dispatch :connections :list "mynamedconnection")
(dispatch :connections :close "mynamedconnection")

;; Virtualhost endpoints
(dispatch :vhosts :add "myvhost")
(dispatch :vhosts :list)
(dispatch :vhosts :list "myvhost")
(dispatch :vhosts :delete "myvhost")

;; Exchange endpoints
(dispatch :exchanges :declare "myvhost" "myexchange" {:auto_delete true})
(dispatch :exchanges :list)
(dispatch :exchanges :list "myvhost")
(dispatch :exchanges :list "myvhost" "myexchange")
(dispatch :exchanges :delete "myvhost" "myexchange")

;; Queue endpoints
(dispatch :queues :declare "myvhost" "myqueue" {:durable false})
(dispatch :queues :list)
(dispatch :queues :list "myvhost")
(dispatch :queues :list "myvhost" "myqueue")
(dispatch :queues :bindings "myvhost" "myqueue")
(dispatch :queues :delete "myvhost" "myqueue")

;; Policy endpoints
(def attributes {:ha-mode "all" :ha-sync-mode "automatic"})
(dispatch :policies :update "myvhost" "policy" ".*" attributes)
(dispatch :policies :list)
(dispatch :policies :list "myvhost" "policy")
(dispatch :policies :clear "myvhost" "policy")

;; User endpoints
(dispatch :users :add "myuser" "mypassword" "mytag")
(dispatch :users :list)
(dispatch :users :list "myuser")
(dispatch :users :delete "myuser")

;; Permissions endpoints
(dispatch :permissions :set "myvhost" "user" [".*" ".*" ".*"])
(dispatch :permissions :list)
(dispatch :permissions :list "myvhost" "user")
(dispatch :permissions :clear "myvhost" "user")
```

Channel and bindings endpoints are presently unimplemented, as are several of
the other auxiliary endpoints. These will probably be added eventually, but
pull requests are also gladly accepted.
