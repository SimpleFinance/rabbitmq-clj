(ns rabbitmq-clj.api.permissions
  "Permissions endpoints.

   GET    /api/permissions
   GET    /api/permissions/:vhost/:user
   PUT    /api/permissions/:vhost/:user
   DELETE /api/permissions/:vhost/:user"
  (:require [rabbitmq-clj.helpers :refer :all]))

(defn list-permissions
  "Lists all permissions, or the permissions for USER on VHOST."
  [& [vhost user]]
  (if (and user vhost)
    (generic-get (format
                   "/api/permissions/%s/%s" (normalize-vhost vhost) user))
    (generic-get "/api/permissions")))

(defn clear-permissions
  "Revokes permissions for USER on VHOST."
  [& [vhost user]]
  (generic-delete (format
                    "/api/permissions/%s/%s" (normalize-vhost vhost) user)))

(defn set-permissions
  "Sets permissions for a user."
  [& [vhost user [conf rd write]]]
  (generic-put (format
                 "/api/permissions/%s/%s"
                 (normalize-vhost vhost) user) {:scope "client"
                                                :configure conf
                                                :read rd
                                                :write write}))

(defn dispatch
  "Dispatches COMMAND to the correct function, passing along ARGS if also
   provided."
  [command & args]
  (case (keyword command)
    :list  (apply list-permissions args)
    :clear (apply clear-permissions args)
    :set   (apply set-permissions args)
    "Invalid command"))
