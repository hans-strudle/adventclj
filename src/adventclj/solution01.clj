(ns adventclj.solution01
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(def dirs [:north :east :south :west])

(def compass {:north 0 :east 0 :south 0 :west 0 :dir 0})

(defn next-dir
  [dir next]
    (if (= next \R) (inc dir) (dec dir)))

(defn which-dir
  [dir next] 
    (get dirs (mod (next-dir dir next) 4))) ;; returns a symbol of the direction 

(defn inc-compass
  [compass nxt]
    (merge compass {(which-dir (:dir compass) (get nxt 0))  ;; symbol
                    (+ (get nxt 1) ((which-dir (:dir compass) (get nxt 0)) compass)) ;; value
                    :dir (next-dir (:dir compass) (get nxt 0))})) ;; an index

(defn run-ins
  [instructions]
    (reductions (fn [f s]
              (if (:dir f)
                (inc-compass f s)
                (inc-compass (inc-compass compass f) s)))
          instructions))

(defn calc-distance
  [compass]
    (+ (Math/abs (- (:east compass) (:west compass)))
       (Math/abs (- (:north compass) (:south compass)))))

(defn calc-positions
  [compass-list]
    (map (fn [compass] 
            {:x (- (:east compass) (:west compass)) 
             :y (- (:north compass) (:south compass))}) 
          compass-list))

(defn calc-instersection ;; calc intersection given vertices
  [vertices]
    ())

(defn solve 
  [input & args]
    (let [vertices (->> input
                      (map name)
                      (map (juxt first #(Integer/parseInt (apply str (rest %)))))
                      (run-ins))]
        {:first (->> vertices 
             (last)
             (calc-distance))
         :second (->> vertices 
             (rest)
             (calc-positions)
             (frequencies)
             (filter #(> (val %) 1))
             (first)
             (key)
             (#(+ (Math/abs (:x %)) (Math/abs (:y %)))))})) ;; incorrect
