(ns recipes.core
  (:require [environ.core :refer [env]]
            [recipes.read :refer [all-tags styles]]
            [recipes.build :refer [delete-build! build!]]
            [recipes.pages :refer [for-merge path-for]]))

(def not-index #{".html" ".css"})

(defn get-env [prop]
  (or (get env prop)
      (throw (IllegalStateException. (str prop " enviroment variable not provided")))))

(defonce folder (get-env :dist-folder))
(defonce base-url (get-env :base-url))

(def website
  {"index.html" [:index]
   "tag" (for-merge [tag (all-tags)]
                    {(name tag) [:recipes tag]})
   "public" {"styles" (for-merge [[name _] (seq (styles))]
                                 {name [:style name]})}})

(defn -main []
  (delete-build! folder)
  (let [reverse-path-fn (partial path-for website base-url)]
    (build! website folder not-index reverse-path-fn)))
