(ns rabbitmq-clj.api.users
  "Users API endpoints.

   GET    /api/users
   GET    /api/users/:name
   PUT    /api/users/:name
   DELETE /api/users/:name"
  (:require [rabbitmq-clj.helpers :refer :all]))

(defn list-users
  "Lists all users or, if USER is given, a single user."
  [& [user]]
  (if user
    (generic-get (format "/api/users/%s" user))
    (generic-get "/api/users")))

(defn add-user
  "Adds a user."
  [& [user password tags]]
  (generic-put (format "/api/users/%s" user) {:password password
                                              :tags     tags}))

(defn delete-user
  "Deletes a user."
  [& [user]]
  (generic-delete (format "/api/users/%s" user)))

(defn dispatch
  "Dispatches COMMAND to the correct function, passing along ARGS if also
   provided."
  [command & args]
  (case (keyword command)
    :list   (apply list-users args)
    :add    (apply add-user args)
    :delete (apply delete-user args)
    "Invalid command"))
