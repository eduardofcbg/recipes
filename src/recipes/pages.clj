(ns recipes.pages
  (:require
   [clojure.set :refer [map-invert]]
   [recipes.generate :refer [generate for-merge]]
   [recipes.read :refer [all-tags]]))

(defmulti slug class)
(defmethod slug clojure.lang.Keyword [s] (name s)) ; TODO: use protocols

(def pages
  {"index.html" [:index-page]
   "tag" (for-merge [tag all-tags]
                    {(str (slug tag)) [:recipes-page tag]})})

(def generate-files (partial generate "" pages))

(def link->view
	(generate-files identity
																	(fn [folder name] (str folder "/" name))))

(defn make-relative [path] (str "." path))

(defn path-for [& view]
	(-> link->view
					(map-invert)
					(get view)
				 (make-relative)))
