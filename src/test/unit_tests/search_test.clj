(ns unit-tests.search-test
  (:require [clojure.test :refer [deftest is testing]]
            [clojure.string :as str]
            [core :refer [search]]
            [clojure.edn :as edn]

            [core :as stub-core]))

(def fixtures
  (-> "src/test/unit_tests/fixtures.edn" (slurp) (edn/read-string)))

(defn mocked-embedding-text [text]
  (->> fixtures
       (filter #(= (:type %) :question))
       (filter #(str/starts-with? (:text %) text))
       first
       :embedding))
(defn all-contents-fixtures [] (->> fixtures (filter #(= (:type %) :db))))

(deftest search-test
  (testing "search for underwater life"
    (is (= (search (mocked-embedding-text "Which text mentions a setting where there's a focus on the underwater life of a lake?")
                   (all-contents-fixtures))
           [{:id 4 :similarity 84.82 :text "Beneath the shimmering surface of the lake, fish dart to and fro, their scales reflecting the sunlight."}
            {:id 8 :similarity 78.4 :text "On the mountaintop, the world stretches out below, a vast tapestry of landscapes and horizons."}
            {:id 1 :similarity 77.59 :text "The sun sets over the tranquil city, painting the sky in hues of orange and pink."}])))
  (testing "search for building and clouds"
    (is (= (search (mocked-embedding-text "In which sample is there a description of a city's tall buildings touching the clouds?")
                   (all-contents-fixtures))
           [{:id 5 :similarity 85.43 :text "In the heart of the metropolis, skyscrapers touch the clouds and streets pulse with energy."}
            {:id 1 :similarity 81.73 :text "The sun sets over the tranquil city, painting the sky in hues of orange and pink."}
            {:id 8 :similarity 79.9 :text "On the mountaintop, the world stretches out below, a vast tapestry of landscapes and horizons."}])))
  (testing "search for break day and birds singing"
    (is (= (search (mocked-embedding-text "Which embedded text captures a scene at the break of day with birds singing?")
                   (all-contents-fixtures))
           [{:id 2 :similarity 88.79 :text "A symphony of birdsong fills the air as dawn breaks over the forest."}
            {:id 1 :similarity 82.28 :text "The sun sets over the tranquil city, painting the sky in hues of orange and pink."}
            {:id 9 :similarity 80.9 :text "The artist's brush dances across the canvas, capturing the essence of a moment in bold strokes and vivid colors."}])))
  )
(comment
  (->> fixtures
       (filter #(= (:type %) :question))
       (map #(select-keys % [:id :text]))
       ),)
