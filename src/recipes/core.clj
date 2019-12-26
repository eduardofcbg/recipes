(ns recipes.core
  (:require [recipes.views :refer [format]]
            [recipes.pages :refer [generate-files]]))

(defn -main []
  (print (generate-files format)))
