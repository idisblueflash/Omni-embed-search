(ns core
  (:require [clojure.core.matrix :as m]))

(m/set-current-implementation :vectorz)

(defn fetch-all-contents [])

(defn embedding-text [text])

(defn calculate-similarity [embedding-a embedding-b]
  (m/dot embedding-a embedding-b))

(defn convert-and-round [num]
  (->> num
       (* 100)
       (format "%.2f")
       (Double/parseDouble)))

(defn search [embedded-question all-contents]
  (->> all-contents
       (pmap #(assoc % :similarity
                       (calculate-similarity embedded-question (:embedding %))))
       (sort-by (comp - :similarity))
       (map #(select-keys % [:id :text :similarity]))
       (map #(assoc % :similarity (convert-and-round (:similarity %))))
       (take 3)
       ))
