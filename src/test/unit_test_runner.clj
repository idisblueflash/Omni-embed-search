(ns unit-test-runner
  (:require [clojure.test :refer [run-tests]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn find-test-namespaces [test-path base-namespace]
  (let [base-path (-> test-path io/file .getCanonicalPath)]
    (->> base-path
         io/file
         file-seq
         (filter #(re-find #"_test\.clj$" (.getName %)))
         (map #(.getCanonicalPath %))
         (map #(str/replace % (str base-path "/") ""))      ; Remove the base path
         (map #(str/replace % "/" "."))                     ; Replace slashes with dots
         (map #(str/replace % "_" "-"))                     ; Replace underscores with dashes
         (map #(str base-namespace %))                      ; Add the base namespace
         (map #(subs % 0 (- (count %) 4)))                  ; Remove the .clj extension
         (map symbol))))

(defn -main []
  (let [test-namespaces (find-test-namespaces "src/test/unit_tests" "unit-tests.")]
    (doseq [ns-sym test-namespaces]
      (require ns-sym))
    (apply run-tests test-namespaces)))