(ns rabbitmq-clj.api.exchanges
  "Exchange endpoints.

   GET    /api/exchanges
   GET    /api/exchanges/:vhost
   GET    /api/exchanges/:vhost/:name
   PUT    /api/exchanges/:vhost/:name
   DELETE /api/exchanges/:vhost/:name"
  (:require [rabbitmq-clj.helpers :refer :all]))

(defn list-exchanges
  "List all exchanges or, if VHOST is given, all exchanges on a virtualhost.
   If EXCHANGE is also given, it will return information on just that exchange
   on the given virtualhost."
  [& [vhost exchange]]
  (let [normal (normalize-vhost vhost)]
    (if vhost
      (if exchange
        (generic-get (format "/api/exchanges/%s/%s" normal exchange))
        (generic-get (format "/api/exchanges/%s" normal)))
      (generic-get "/api/exchanges"))))

(defn declare-exchange
  "Declares EXCHANGE on VHOST with optional parameters PARAMS (hash)."
  [& [vhost exchange params]]
  (let [path (format "/api/exchanges/%s/%s" (normalize-vhost vhost) exchange)
        payload {:type        (get params :type "direct")
                 :auto_delete (get params :auto_delete false)
                 :durable     (get params :durable true)
                 :arguments   (get params :arguments [])}]
    (generic-put path payload)))

(defn delete-exchange
  "Deletes EXCHANGE from VHOST."
  [& [vhost exchange]]
  (generic-delete (format
                    "/api/exchanges/%s/%s"
                    (normalize-vhost vhost)
                    exchange)))

(defn dispatch
  "Dispatches COMMAND to the correct function, passing along ARGS if also
   provided."
  [command & args]
  (case (keyword command)
    :list    (apply list-exchanges args)
    :declare (apply declare-exchange args)
    :delete  (apply delete-exchange args)
    "Invalid command"))
