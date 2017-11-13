(ns adventclj.solution10
  (:require [clojure.string :as str]))

(def bot-values {})

(def regex #"value\s(\d+).+bot\s(\d+)|bot\s(\d+).+to\s(.+)\s(\d+).+to\s(.+)\s(\d+)") ;; spent way too much time on this

(defn build-instruction
  [line]
    (let [all (re-find regex line)]
    (apply hash-map (interleave [:chip1 :bot1 :bot2 :lowtype :lownum :hightype :highnum] (rest all)))))

(defn execute-instruction
  [bot-vals instruction]
    ;;(println bot-vals instruction)
    ;;(println (:lowtype instruction))
    ;;(println (reduce min 0 (get bot-vals (:bot2 instruction))))
    ;;(println (reduce max Integer/MAX_VALUE (get bot-vals (:bot2 instruction))))
    (merge bot-vals (if (nil? (:bot1 instruction))
                      {(:bot2 instruction) []}
                      {(:bot1 instruction) (flatten (conj [] (:bot1 bot-vals) (:chip1 instruction)))})
                    (if (= (:lowtype instruction) "bot")
                      {(:lownum instruction) (flatten (conj [] (get bot-vals (:lownum instruction)) (reduce (fnil max 0 0) (get bot-vals (:bot2 instruction)))))} {})
                    (if (= (:hightype instruction) "bot")
                      {(:highnum instruction) (flatten (conj [] (get bot-vals (:highnum instruction)) (reduce (fnil min 0 0) Integer/MAX_VALUE (get bot-vals (:bot2 instruction)))))} {})
))

(defn solve
  [input]
    (->> input
         (map build-instruction)
         (reductions execute-instruction)
         (println)))
