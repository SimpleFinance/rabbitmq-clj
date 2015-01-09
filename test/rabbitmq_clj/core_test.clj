(ns rabbitmq-clj.core-test
  (:require [clojure.test :refer :all]
            [rabbitmq-clj.core :refer [configure dispatch]]))

(deftest core
  (testing "All endpoints"
    (let [overview (dispatch :overview)
          nodes (dispatch :arbitrary :get "/api/nodes")]
      (is (map? overview))
      (is (coll? nodes)))))
