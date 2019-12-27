(ns recipes.read
  (:require [clojure.set :refer [union]]))

(def website {:recipes [{:title "Savory souffle"
                         :description "Works every time"
                         :sources []
                         :tags #{:savory :souffle}}]})

(def all-tags
  (apply union (map :tags (:recipes website))))

(defn recipes-tag [tag]
  (let [recipes (:recipes website)
        has-tag? (fn [recipe] (contains? (:tags recipe) tag))]
    (filter has-tag? recipes)))
