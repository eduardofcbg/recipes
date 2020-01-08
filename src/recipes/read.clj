(ns recipes.read
  (:require
   [clojure.java.io :refer [resource]]
   [clojure.set :refer [union]]))

(defn- read-resource  [path]
  (slurp (resource path)))

(defn public []
  {"script.js" (read-resource  "public/script.js")
   "main.css" (read-resource  "public/main.css")})

; Read from markdown or database for example...

(def website {:recipes [{:title "Sweet souffle"
                         :description "Creme patissiere base"
                         :sources ["https://www.thefrenchcookingacademy.com/recipe/pastry-cream/"]
                         :tags #{:sweet :souffle}}
                        {:title "Savory souffle"
                         :description "Bechamel base"
                         :tags #{:savory :souffle}}]})

(defn all-tags []
  (apply union (map :tags (:recipes website))))

(defn recipes-tag [tag]
  (let [recipes (:recipes website)
        has-tag? (fn [recipe] (contains? (:tags recipe) tag))]
    (filter has-tag? recipes)))

; Functions here should be memoized if called in different views