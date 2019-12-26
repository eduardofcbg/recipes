(ns recipes.generate-test
  (:require [clojure.test :refer :all]
            [recipes.generate :refer [generate]]))

(deftest single-page
  (testing "Single page website, no nesting"
    (let [website {"index.html" "Webpage"}
          expected {"/index.html" "Webpage"}]
      (is (= (generate "" website identity) expected)))))

(deftest nested-page
  (testing "Nested page website"
    (let [website {"recipe" {"cake.html" "Some cake"}}
          expected {"/recipe/cake.html" "Some cake"}]
      (is (= (generate "" website identity) expected)))))

(deftest complete
  (testing "Nested and non nested pages")
  (let [website {"index.html" "Webpage"
                 "recipe" {"cake.html" "Some cake"}}
        expected {"/recipe/cake.html" "Some cake"
                  "/index.html" "Webpage"}]
    (is (= (generate "" website identity) expected))))
