(ns rabbitmq-clj.api.vhosts-test
  "Tests related to virtualhost endpoints."
  (:require [clojure.test :refer :all]
            [rabbitmq-clj.core :refer [dispatch]]))

(deftest vhosts
  (testing "Virtualhost endpoints"
    (let []
      (is (nil? (dispatch :vhosts :add "myvhost")))
      (is (= ["/" "myvhost"] (map :name (dispatch :vhosts :list))))
      (is (= {:name "myvhost" :tracing false} (dispatch :vhosts :list "myvhost")))
      (is (nil? (dispatch :vhosts :delete "myvhost"))))))
