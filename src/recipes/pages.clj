(ns recipes.pages
  (:require
   [clojure.set :refer [map-invert]]
   [clojure.string :refer [join ends-with? ends-with?]]
   [environ.core :refer [env]]))

(defmacro for-merge [seq-exprs body-expr]
  `(apply merge (for ~seq-exprs ~body-expr)))

(defn pages
  ([website]
   (pages [] website))
  ([folder website]
   (for-merge [[name content] (seq website)
               :let [path (conj folder name)]]
              (if (map? content)
                (pages path content)
                {path content}))))

(defn ensure-index [full-path extentions]
  (let [name (last full-path)
        path (butlast full-path)]
    (if (some #(ends-with? name %) extentions)
      full-path
      (concat path [name "index.html"]))))

(defn as-url [base-url path]
  (let [relative-path (join "/" path)]
    (str base-url "/" relative-path)))

(defn view->url [website]
  (map-invert (pages website)))

(def memo-view->url (memoize view->url))

(defn path-for [website base-url view]
  (-> website
      (memo-view->url)
      (get view)
      ((partial as-url base-url))))
