(ns recipes.read
	(:require [clojure.set :refer [union]]))

(def website {:recipes [{:title "Savory cheesecake"
																									:description "Works every time"
																									:sources []
																									:tags #{:lixo :merda}}]})

(def all-tags
  (apply union (map :tags (:recipes website))))

(defn recipes-tag [tag]
	(let [recipes (:recipes website)
							has-tag? (fn [recipe] (contains? (:tags recipe) tag))]
							(filter has-tag? recipes)))
