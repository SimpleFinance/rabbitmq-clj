(ns rabbitmq-clj.api.connections-test
  "Tests related to connections endpoints."
  (:require [clojure.test :refer :all]
            [rabbitmq-clj.core :refer [dispatch]]))

(deftest connections
  (testing "Connection endpoints"
    (let [init-conns (dispatch :connections :list)]
      (is (empty? init-conns)))))
