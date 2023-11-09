(ns core
  (:require [clojure.core.matrix :as m]))

(m/set-current-implementation :vectorz)

(defn fetch-all-contents []
  ;;TBD. need fetch from db of file
  )

(defn embedding-text [text]
  ;;TBD. need call Open AI API
  )

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

(comment
  (require '[next.jdbc :as jdbc])
  (def db {:dbtype "h2" :dbname "example"})
  (def ds (jdbc/get-datasource db))

  (jdbc/execute! ds ["
create table address (
  id int auto_increment primary key,
  name varchar(32),
  email varchar(255)
)"])


  (jdbc/execute! ds ["
insert into address(name,email)
  values('Sean Corfield','sean@corfield.org')"])

  (jdbc/execute! ds ["select * from address"])

  ,)