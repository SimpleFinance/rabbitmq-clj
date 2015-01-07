(ns rabbitmq-clj.api.channels
  (:require [rabbitmq-clj.helpers :refer :all]))

(defn dispatch
  "Dispatches COMMAND to the correct function, passing along ARGS if also
   provided."
  [command & args]
  (case (keyword command)
    "Invalid command"))
