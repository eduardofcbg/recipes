(ns recipes.pages
  (:import java.net.URI)
  (:require
   [clojure.set :refer [map-invert]]
   [clojure.string :refer [join ends-with? includes?]]
   [recipes.read :refer [public all-tags recipes-tag]]))

(defmacro for-merge [seq-exprs body-expr]
  `(apply merge (for ~seq-exprs ~body-expr)))

(def pages
  {"index.html" [:index]
   "tag" (for-merge [tag (all-tags)]
                    {(name tag) [:recipes tag]})
   "public" (for-merge [[name _] (seq (public))]
                       {name [:public name]})})

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

(defn as-url [path]
  (str (URI. (str "/" (join "/" path)))))

(defn path-for [& view]
  (-> (generate pages) ;; TODO: memo
      (map-invert)
      (get view)
      (as-url)))
