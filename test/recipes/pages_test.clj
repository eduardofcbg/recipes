(ns recipes.pages-test
  (:require [clojure.test :refer :all]
            [recipes.pages :refer [pages ensure-index]]))

(deftest generate-single-page
  (testing "Generate single page website, no nesting"
    (let [website {"index.html" "Webpage"}
          expected {["index.html"] "Webpage"}]
      (is (= (pages website) expected)))))

(deftest generate-nested-page
  (testing "Generate nested page website"
    (let [website {"recipe" {"cake.html" "Some cake"}}
          expected {["recipe" "cake.html"] "Some cake"}]
      (is (= (pages website) expected)))))

(deftest generate-complete-page
  (testing "Generate nested and non nested pages")
  (let [website {"index.html" "Webpage"
                 "recipe" {"cake.html" "Some cake"}}
        expected {["recipe" "cake.html"] "Some cake"
                  ["index.html"] "Webpage"}]
    (is (= (pages website) expected))))

(deftest generate-handles-with-view
  (testing "Generate pages with vector inside vector instead of map inside map")
  (let [website {"recipe" {"flavours" {"strawberry" [:strawberry]}}}
        expected {["recipe" "flavours" "strawberry"] [:strawberry]}]
    (is (= (pages website) expected))))

(deftest ensure-index-html
  (testing "Does not need to ensure index folder")
  (let [path ["home" "index.html"]
        expected ["home" "index.html"]]
    (is (= (ensure-index path #{".html"}) expected))))

(deftest ensure-index-success
  (testing "Creates folder for holding index.html")
  (let [path ["home" "recipe"]
        expected ["home" "recipe" "index.html"]]
    (is (= (ensure-index path #{".html"}) expected))))
