(ns rabbitmq-clj.api.connections
  "Generic API endpoints:

   GET    /api/connections
   GET    /api/connections/:name
   DELETE /api/connections/:name"
  (:require [rabbitmq-clj.helpers :refer :all]))

(defn list-connections
  "Returns the /api/connections endpoint, which contains data about connections
   to the RabbitMQ cluster."
  [& [conn]]
  (if conn
    (generic-get (format "/api/connections/%s" conn))
    (generic-get "/api/connections")))

(defn close-connection
  "Closes a connection whose name is given by CONN."
  [conn]
  (generic-delete (format "api/connections/%s" conn)))

(defn dispatch
  [command & args]
  (case command
    :close (close-connection args)
    :list  (list-connections args)))
