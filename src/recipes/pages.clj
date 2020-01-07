(ns recipes.pages
  (:require
   [clojure.set :refer [map-invert]]
   [clojure.string :refer [join ends-with? includes?]]
   [recipes.read :refer :all]))

(defmacro for-merge [seq-exprs body-expr]
  `(apply merge (for ~seq-exprs ~body-expr)))

(def pages
  {"index.html" [:index-page]
   "tag" (for-merge [tag all-tags]
                    {(name tag) [:recipes-page tag]})
   "static" (for-merge [[name _] (seq static-files)]
   														      {name [:static-page name]})})

(defn generate
		([website]
			(generate [] website))
		([folder website]
	  (for-merge [[name content] (seq website)
	              :let [path (conj folder name)]]
	             (if (map? content)
	               (generate path content)
	               {path content}))))

(defn ensure-index [full-path extentions]
  (let [name (last full-path)
  					 path (butlast full-path)]
  					(if (some #(includes? name %) extentions)
							    full-path
							    (concat path [name "index.html"]))))

(defn as-url [path] (str "." (join "/" path))) ;; Url encode

(defn path-for [_ _] :static-page)

; (defn path-for [website view]
;   (-> (generate website) ;; TODO: memo
;       (map-invert)
;       (get view)
;       (as-url)))
