(ns rabbitmq-clj.helpers
  "Tiny helpers."
  (:require [clj-http.client :as http]))

(def api "Atom containing the endpoint."
  (atom "http://guest:guest@127.0.0.1:15672"))

(defn normalize-vhost
  "The API expects HTML URL encoding, so this does that for vhosts."
  [vhost]
  (when vhost
    (clojure.string/replace vhost #"/" "%2f")))

(defn generic-get
  "Helper for GET actions, returns the body of the response."
  [path]
  (let [full-url (str @api path)]
    (-> full-url
        (http/get {:as :json})
        :body)))

(defn generic-delete
  "Helper for DELETE actions, returns the body of the response."
  [path]
  (let [full-url (str @api path)]
    (-> full-url
        http/delete
        :body)))

(defn generic-put
  "Helper for PUT actions, returns the body of the response."
  [path & [body]]
  (let [payload {:form-params body
                 :as :json
                 :coerce :always
                 :content-type :json}
        full-url (str @api path)]
    (-> full-url
        (http/put payload)
        :body)))

(defn generic-post
  "Helper for POST actions, returns the body of the response."
  [path body]
  (let [full-url (str @api path)
        payload {:form-params body
                 :as :json
                 :coerce :always
                 :content-type :json}]
    (-> full-url
        (http/post payload)
        :body)))
