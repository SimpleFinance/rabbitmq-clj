(ns rabbitmq-clj.api.queues
  "Queue endpoints.

   GET    /api/queues
   GET    /api/queues/:vhost
   GET    /api/queues/:vhost/:queue
   PUT    /api/queues/:vhost/:queue
   DELETE /api/queues/:vhost/:queue
   GET    /api/queues/:vhost/:queue/bindings"
  (:require [rabbitmq-clj.helpers :refer :all]))

(defn list-queues
  "List all queues, OR
   List all queues on a given virtualhost, OR
   List a specific queue on a given virtualhost"
  [& [vhost queue]]
  (if vhost
    (if queue
      (generic-get (format
                     "/api/queues/%s/%s" (normalize-vhost vhost) queue))
      (generic-get (format "/api/queues/%s" (normalize-vhost vhost))))
    (generic-get "/api/queues")))

(defn declare-queue
  "Declares QUEUE on VHOST with optional parameters PARAMS."
  [& [vhost queue params]]
  (let [path (format "/api/queues/%s/%s" (normalize-vhost vhost) queue)
        payload {:auto_delete (get params :auto_delete false)
                 :durable     (get params :durable true)
                 :arguments   (get params :arguments [])}]
    (generic-put path payload)))

(defn delete-queue
  "Deletes QUEUE from VHOST."
  [& [vhost queue]]
  (generic-delete (format
                    "/api/queues/%s/%s" (normalize-vhost vhost) queue)))

(defn queue-bindings
  "List the bindings of QUEUE from VHOST."
  [& [vhost queue]]
  (generic-get (format
                 "/api/queues/%s/%s/bindings"
                 (normalize-vhost vhost)
                 queue)))

(defn dispatch
  "Dispatches COMMAND to the correct function, passing along ARGS if also
   provided."
  [command & args]
  (case (keyword command)
    :list     (apply list-queues args)
    :declare  (apply declare-queue args)
    :delete   (apply delete-queue args)
    :bindings (apply queue-bindings args)
    "Invalid command"))
