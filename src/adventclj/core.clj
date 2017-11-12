(ns adventclj.core
  (:require [clojure.string :as str]
            [adventclj.solution01 :as solution01]
            [adventclj.solution04 :as solution04]
            [adventclj.solution10 :as solution10]))

(defn -main
  [& args]
    (println "Day 01")
    (println (solution01/solve (read-string (str "[" (slurp "resources/input01.txt") "]"))))
    (println "Day 04")
    (println (solution04/solve (str/split-lines (slurp "resources/input04.txt"))))
    (println "Day 10"))
   ;; (println (solution10/solve (str/split-lines (slurp "resources/input10.txt")))))
