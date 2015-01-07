(ns rabbitmq-clj.api.policies
  "Policy endpoints.

   GET    /api/policies
   GET    /api/policies/:vhost
   GET    /api/policies/:vhost/:policy
   PUT    /api/policies/:vhost/:policy
   DELETE /api/policies/:vhost/:policy"
  (:require [rabbitmq-clj.helpers :refer :all]))

(defn list-policies
  "List policies, OR
   List policies for a given virtualhost, OR
   List a specific policy on a given virtualhost."
  [& [vhost policy]]
  (if vhost
    (if policy
      (generic-get (format
                     "/api/policies/%s/%s"
                     (normalize-vhost vhost)
                     policy))
      (generic-get (format "/api/policies/%s" (normalize-vhost vhost))))
    (generic-get "/api/policies")))

(defn update-policy
  "Update POLICY on VHOST with ATTRIBUTES."
  [& [vhost policy pattern definition {:keys [priority apply-to]
                                       :or {priority 0
                                            apply-to "all"}}]]
  (let [path (format "/api/policies/%s/%s" (normalize-vhost vhost) policy)
        payload {:pattern    pattern
                 :definition definition
                 :priority   priority
                 :apply-to   apply-to}]
    (generic-put path payload)))

(defn clear-policy
  "Clears (deletes) POLICY from VHOST."
  [& [vhost policy]]
  (generic-delete (format
                    "/api/policies/%s/%s"
                    (normalize-vhost vhost)
                    policy)))

(defn dispatch
  "Dispatches COMMAND to the correct function, passing along ARGS if also
   provided."
  [command & args]
  (case (keyword command)
    :list   (apply list-policies args)
    :update (apply update-policy args)
    :clear  (apply clear-policy args)
    "Invalid command"))
