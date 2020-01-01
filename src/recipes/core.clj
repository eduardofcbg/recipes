(ns recipes.core
  (:require [clojure.string :refer [ends-with?]]
            [clojure.java.io :refer [file delete-file make-parents]]
            [recipes.views :refer [view]]
            [recipes.pages :refer [pages generate ensure-index]]))

(defn delete-generated [folder]
  (let [f (file folder)]
    (when (.exists f)
      (doseq [file (reverse (file-seq f))]
        (delete-file file)))))

(defn -main [folder]
  (delete-generated folder)
  (doseq [[path content] (seq (generate pages view))
          :let [full-path (conj folder (ensure-index path))
          						filesystem-path (join "/" full-path)]] ; TODO: separator
    (make-parents filesystem-path)
    (spit filesystem-path content)))
