(ns rabbitmq-clj.api.parameters
  "Parameter endpoints."
  (:require [rabbitmq-clj.helpers :refer :all]))

(defn dispatch
  "Dispatches COMMAND and ARGS to the correct function."
  [url command & args]
  (case (keyword command)
    "Invalid command"))
