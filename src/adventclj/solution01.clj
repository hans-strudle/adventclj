(ns adventclj.solution01
  (:require [adventclj.utils :as utils] 
            [clojure.string :as str]))

(def directions [:north :east :south :west])

(def compass {:north 0 :east 0 :south 0 :west 0 :dir 0})

(defn next-dir
  [dir next]
    ({\L -1 \R 1} next)) ;; left -1, right +1

(defn which-dir
  [dirs current next] 
    (get dirs (mod (+ current (next-dir current next)) 4))) ;; returns a symbol of the direction 

(defn inc-compass
  [compass nxt]
    (merge-with + compass {(which-dir directions (:dir compass) (first nxt)) (last nxt)
                   :dir (next-dir (:dir compass) (first nxt))}))

(defn calc-distance
  [compass]
    (+ (Math/abs (- (:east compass) (:west compass)))
       (Math/abs (- (:north compass) (:south compass)))))

(defn calc-positions
  [compass] 
    {:x (- (:east compass) (:west compass)) 
     :y (- (:north compass) (:south compass))})

(defn extrapolate
  "Takes a collection and a point (in the form {:x N :y N})
   and returns the intermediary points between the last of the first arg, and the second arg
   assumes that either :x or :y hasn't changed (movement only in one direction"
  [f s]
    (let [point (last f)
          diff (merge-with - s point)
          x (:x diff)
          y (:y diff)
          dirmap {true - false +}]
    (if (zero? x) ;; moved in the y direction
        ;; returns n points 1 apart, where n = magnitude in changed direction
        ;; points are (f+1, ..., s) where f is the first point and s is the last (2nd point upwards)
        (rest (take (inc (Math/abs y)) (iterate #(merge-with (dirmap (neg? y)) % {:y 1}) point)))
        (rest (take (inc (Math/abs x)) (iterate #(merge-with (dirmap (neg? x)) % {:x 1}) point))))))

(defn first-repeat ;; find first el in frequencies collection
  [coll el]
    (if (= 2 (coll el)) ;; first block visited twice (frequency of 2)
        (reduced el)
        coll))

(defn solve 
  [input & args]
    (let [vertices (->> input
                      (map name)
                      (map (juxt first #(utils/parse-int (apply str (rest %)))))
                      (reductions inc-compass compass))]
        {:first (->> vertices 
             (last)
             (calc-distance))
         :second (->> vertices 
             (map calc-positions)
             (rest)
             (reductions extrapolate '({:x 0 :y 0})) ;; extrapolate vertices into all blocks visited 
             (flatten)
             (#(reduce first-repeat (frequencies %) %))
             (#(+ (Math/abs (:x %)) (Math/abs (:y %)))))}))
