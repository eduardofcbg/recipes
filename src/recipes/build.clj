(ns recipes.build
  (:import java.io.File)
  (:require [clojure.java.io :refer [file delete-file make-parents]]
            [clojure.string :refer [join]]
            [recipes.views :refer [view]]
            [recipes.pages :refer [pages ensure-index]]))

(defn delete-build! [folder]
  (let [f (file folder)]
    (when (.exists f)
      (doseq [file (reverse (file-seq f))]
        (delete-file file)))))

(defn- variadic [f] (fn [& args] (f args)))

(defn- spit-page [path content path-for]
  (let [f (variadic path-for)
        render (view content f)]
    (spit path render)))

(defn build! [website folder not-index path-for]
  (println "🏗️  Building...")
  (doseq [[path content] (seq (pages website))
          :let [full-path (into [folder] (ensure-index path not-index))
                filesystem-path (join (java.io.File/separator) full-path)]]
    (make-parents filesystem-path)
    (spit-page filesystem-path content path-for)
    (println "✔️ " filesystem-path))
  (println "🎉🎉🎊 Finished!"))
