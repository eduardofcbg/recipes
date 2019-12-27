(ns recipes.core
  (:require [clojure.string :refer [ends-with?]]
            [clojure.java.io :refer [file delete-file make-parents]]
            [recipes.views :refer [format-view]]
            [recipes.pages :refer [generate-files]]))

(def folder "site")

(defn delete-files []
  (let [f (file folder)]
       (when (.exists f)
             (doseq [file (reverse (file-seq f))]
                    (delete-file file)))))

(defn format-path [folder name]
  (if-not (ends-with? name ".html")
          (str folder "/" name "/index.html")
          (str folder "/" name)))

(defn -main []
  (delete-files)
  (doseq [[path content] (seq (generate-files format-view format-path))
         :let [relative-path (str folder path)]]
         (make-parents relative-path)
         (spit relative-path content)))
