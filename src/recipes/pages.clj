(ns recipes.pages
  (:require
   [clojure.set :refer [map-invert]]
   [recipes.generate :refer [generate for-merge]]
   [recipes.read :refer [all-tags]]))

(defmulti slug class)
(defmethod slug clojure.lang.Keyword [s] (name s)) ; TODO: use protocols

(def pages
  {"index" [:index-page]
   "tag" (for-merge [tag all-tags]
                    {(slug tag) [:recipes-page tag]})})

(def generate-files (partial generate "" pages))

(defn path-for [& view]
  (get (clojure.set/map-invert (generate-files identity))
       view))
