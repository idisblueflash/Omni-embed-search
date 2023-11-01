(ns unit-tests.search-test
  (:require [clojure.test :refer [deftest is testing]]
            [core :refer [search]]
            [clojure.edn :as edn]))

(def fixtures
  [{:id 1 :type :db :text "The sun sets over the tranquil city, painting the sky in hues of orange and pink."}
   {:id 2 :type :db :text "A symphony of birdsong fills the air as dawn breaks over the forest."}
   {:id 3 :type :db :text "The bustling marketplace is alive with vibrant colors, fragrant spices, and animated conversations."}
   {:id 4 :type :db :text "Beneath the shimmering surface of the lake, fish dart to and fro, their scales reflecting the sunlight."}
   {:id 5 :type :db :text "In the heart of the metropolis, skyscrapers touch the clouds and streets pulse with energy."}
   {:id 6 :type :db :text "The ancient temple, worn by time, stands as a testament to civilizations long forgotten."}
   {:id 7 :type :db :text "Laughter echoes through the park as children fly kites and play games under the watchful eyes of towering trees."}
   {:id 8 :type :db :text "On the mountaintop, the world stretches out below, a vast tapestry of landscapes and horizons."}
   {:id 9 :type :db :text "The artist's brush dances across the canvas, capturing the essence of a moment in bold strokes and vivid colors."}])

(deftest search-test
  (testing "search for underwater life"
    (is (= (search "Which text mentions a setting where there's a focus on the underwater life of a lake?")
           [{:id 4 :text "Beneath the shimmering surface of the lake, fish dart to and fro, their scales reflecting the sunlight."}
            {:id 2 :text "bar"}
            {:id 3 :text "baz"}]))))


(comment
  (-> "src/test/unit_tests/fixtures.edn" (slurp) (edn/read-string))
  ,)
