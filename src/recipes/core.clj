(ns recipes.core
  (:require [clojure.java.io :refer [file delete-file make-parents]]
            [recipes.views :refer [format-view]]
            [recipes.pages :refer [generate-files]]))

(def folder "site")

(defn delete-files []
  (doseq [file (reverse (file-seq (file folder)))]
     (delete-file file)))

(defn -main []
  (delete-files)
  (doseq [[path content] (seq (generate-files format-view))
         :let [relative-path (str folder path)]]
         (make-parents relative-path)
         (spit relative-path content)))
