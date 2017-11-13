(ns adventclj.solution10
  (:require [clojure.string :as str]))

(def bot-values {})

(def regex #"value\s(\d+).+bot\s(\d+)|bot\s(\d+).+to(.+)\s(\d+).+to(.+)\s(\d+)") ;; spent way too much time on this

(defn build-instruction
  [line]
    (let [all (re-find regex line)]
    (apply hash-map (interleave [:chip1 :bot1 :bot2 :lowtype :lownum :hightype :highnum] (rest all)))))

(defn execute-instruction
  [bot-vals instruction]
    (println instruction)
    (merge bot-vals {(:bot1 instruction) (flatten (conj [] (:bot1 bot-vals) (:chip1 instruction)))
                     (:bot2 instruction) []
                     (:highnum instruction) (flatten (conj [] (get bot-vals (:highnum instruction)) (min (:bot2 instruction))))}))

(defn run-instructions
  [f s]
    ;;(if (:steps f) ;; check if its our first el
            (execute-instruction f s))
       ;; (execute-instruction (execute-instruction bot-values f) s)))

(defn solve
  [input]
    (->> input
         (map build-instruction)
         (reduce run-instructions)
         (println)))
