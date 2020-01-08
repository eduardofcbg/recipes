(ns recipes.core
  (:import java.io.File)
  (:require [clojure.java.io :refer [file delete-file make-parents]]
            [clojure.string :refer [join]]
            [recipes.views :refer [view]]
            [recipes.pages :refer [pages generate ensure-index]]))

(defn delete-generated! [folder]
  (let [f (file folder)]
    (when (.exists f)
      (doseq [file (reverse (file-seq f))]
        (delete-file file)))))

(def not-index #{".html" ".js" ".css"})

(defn -main [folder]
  (delete-generated! folder)
  (doseq [[path content] (seq (generate pages))
          :let [full-path (into [folder] (ensure-index path not-index))
                filesystem-path (join (java.io.File/separator) full-path)]]
    (make-parents filesystem-path)
    (spit filesystem-path (view content))))
