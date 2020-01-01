(ns recipes.pages
  (:require
   [clojure.set :refer [map-invert]]
   [clojure.string :refer [join]]
   [recipes.generate :refer [generate for-merge]]
   [recipes.read :refer [all-tags]]))

(def static-files {"lib.js" "alert('lixo')"})

(def pages
  {"index.html" [:index-page]
   "tag" (for [tag all-tags]
              [(name tag) [:recipes-page tag]])
   "static" (for [[name _] (seq static-files)]
   														[name [:static-page name]])})

(defn generate
		[website view-formater]
			(generate [] website view-formater)
		[folder website view-formater]
	  (apply merge (for [[name content] (seq website)
	  																		:let [path (conj folder name)]]
								             (if ((or map? vector?) content)
										               (generate path content view-formater)
										               {path (view-formater content)}))))

(defn ensure-index [path]
  (let [name (last path)]
  					(if-not (ends-with? name ".html")
  									    (concat (butlast path) [name "index.html"])
  									    path)))

(defn as-url [path] (str "." (join path "/"))) ;; Url encode

(defn path-for [website view]
  (as-> (generate website identity) url->view ;; TODO memo
        (map-invert)
        (get view)
        (as-url)))
