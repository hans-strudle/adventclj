(ns adventclj.solution04
    (:require [adventclj.utils :as utils]
              [clojure.string :as str]))

(defn room-info
  [room]
    (let [parts (str/split room #"-")
         [id csum] (rest (re-find #"(\d+)\[(.+)\]" (last parts)))]
        {:id (utils/parse-int id)
         :checksum csum
         :word (apply str (butlast parts))}))



(defn check-room
  [room]
      (= (:checksum room) (str/join (utils/top-freq 5 (:word room)))))

(defn cypher-char
  [c n]
    (-> (int c)
        (- 97)
        (+ n 26)
        (mod 26)
        (+ 97)
        char))

(defn decrypt
  [data]
    (merge data {:decrypted (apply str (map #(cypher-char % (:id data)) (:word data)))}))

(defn solve
  [input]
    {:first (->> input
      (map room-info)
      (filter check-room)
      (map :id)
      (reduce +))
    :second (->> input
      (map room-info)
      (filter check-room)
      (map decrypt)
      (filter #(str/includes? (:decrypted %) "northpole"))
      (apply :id))})
