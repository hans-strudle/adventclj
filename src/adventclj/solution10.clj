(ns adventclj.solution10
  (:require [clojure.string :as str]))

(def regex #"value\s(\d+).+bot\s(\d+)|bot\s(\d+).+to(.+)\s(\d+).+to(.+)\s(\d+)") ;; spent way too much time on this

(defn build-instruction
  [line]
    (let [instruction (re-find regex line)]))

(defn solve
  [input]
    (->> input
         (map build-instruction)))
