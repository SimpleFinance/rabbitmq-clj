(ns rabbitmq-clj.api.bindings
  "Bindings endpoints.

   GET    /api/bindings
   GET    /api/bindings/:vhost
   GET    /api/bindings/:vhost/:queue/:exchange
   POST   /api/bindings/:vhost/:queue/:exchange
   GET    /api/bindings/:vhost/:queue/:exchange/props
   PUT    /api/bindings/:vhost/:queue/:exchange/props
   DELETE /api/bindings/:vhost/:queue/:exchange/props"
  (:require [rabbitmq-clj.helpers :refer :all]))

(defn list-bindings
  "Lists all bindings, OR
   Lists all bindings on virtualhost VHOST, OR
   Lists bindings between QUEUE and EXCHANGE on VHOST"
  [& [vhost queue exchange]]
  (if vhost
    (if (and queue exchange)
      (generic-get (format
                         "/api/bindings/%s/%s/%s"
                         (normalize-vhost vhost)
                         exchange
                         queue))
      (generic-get (format
                         "/api/bindings/%s"
                         (normalize-vhost vhost))))
    (generic-get "/api/bindings")))

(defn declare-binding
  "Declares binding between QUEUE on VHOST and EXCHANGE, accepting optional
   parameters PARAMS."
  [& [vhost queue exchange params]]
  (let [normal (normalize-vhost vhost)
        path (format "/api/bindings/%s/%s/%s" normal exchange queue)
        payload {:routing_key (get params :routing_key "")
                 :arguments (get params :arguments [])}]
    (generic-post path payload)))

(defn delete-binding
  "Deletes binding given by PROPS between QUEUE and EXCHANGE on VHOST. PROPS
   is given by the following description on the RabbitMQ API page:

   The props part of the URI is a name for the binding composed of its
   routing key and properties."
  [& [vhost queue exchange props]]
  (let [normal (normalize-vhost vhost)
        path (format "/api/bindings/%s/%s/%s/%s" normal exchange queue props)]
    (generic-delete path)))

(defn dispatch
  "Dispatches COMMAND to the correct function, passing along ARGS if also
   provided."
  [command & args]
  (case (keyword command)
    :list    (apply list-bindings args)
    :declare (apply declare-binding args)
    :delete  (apply delete-binding args)
    "Invalid command"))
