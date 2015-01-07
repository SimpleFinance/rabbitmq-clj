(def VERSION (.trim (slurp "VERSION")))

(defproject rabbitmq-clj VERSION
  :main         rabbitmq-clj.core
  :description  "RabbitMQ API client in Clojure."
  :url          "https://github.com/simplefinance/rabbitmq-clj"
  :license      "All rights reserved."
  :repl-options {:init-ns rabbitmq-clj.core}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.clojure/algo.generic "0.1.2"]
                 [cheshire "5.3.1"]
                 [clj-http "1.0.1"]]
  :repositories
  [["releases" {:url "http://nexus.banksimple.com/content/repositories/releases"
                :username :env/nexus_username
                :password :env/nexus_password
                :sign-releases false}]]
  :min-lein-version "2.0.0"
  :codox {:output-dir "target/doc"
          :src-dir-url "https://github.banksimple.com/ops/rabbitmq-clj/blob/master"}
  :global-vars {*warn-on-reflection* true}
  :profiles {:dev {:plugins [[codox "0.8.10"]
                             [lein-cloverage "1.0.2"]]}})