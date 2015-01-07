(ns rabbitmq-clj.api.vhosts
  "Virtualhost API endpoints:

   GET    /api/vhosts
   GET    /api/vhosts/:name
   PUT    /api/vhosts/:name
   DELETE /api/vhosts/:name"
  (:require [rabbitmq-clj.helpers :refer :all]))

(defn add-vhost
  "Declares virtualhost VHOST."
  [& [vhost]]
  (generic-put (format "/api/vhosts/%s" (normalize-vhost vhost))))

(defn delete-vhost
  "Deletes the virtualhost given by VHOST."
  [& [vhost]]
  (generic-delete (format "/api/vhosts/%s" (normalize-vhost vhost))))

(defn list-vhosts
  "Get a specific virtualhost if VHOST is given, otherwise get all."
  [& [vhost]]
  (if vhost
    (generic-get (format "/api/vhosts/%s" (normalize-vhost vhost)))
    (generic-get "/api/vhosts")))

(defn dispatch
  "Dispatches COMMAND to the correct function."
  [command & args]
  (case (keyword command)
    :list   (apply list-vhosts args)
    :add    (apply add-vhost args)
    :delete (apply delete-vhost args)
    "Invalid command"))
