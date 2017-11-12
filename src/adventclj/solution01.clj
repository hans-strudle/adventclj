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
  [f s]
    (if (:dir f)
      (inc-compass f s)
      (inc-compass (inc-compass compass f) s)))

(defn calc-distance
  [compass]
    (+ (Math/abs (- (:east compass) (:west compass)))
       (Math/abs (- (:north compass) (:south compass)))))

(defn calc-positions
  [compass] 
    {:x (- (:east compass) (:west compass)) 
     :y (- (:north compass) (:south compass))})

(defn extrapolate
  [f s]
    ;;(println (last f))
    ;;(println s)
    ;;(println 44)
    (let [x (- (:x s) (:x (last f)))
          y (- (:y s) (:y (last f)))]
        ;;(println x y)
    (if (zero? x) ;; moved in the y direction
        (rest (take (inc (Math/abs y)) (iterate #(merge-with ({true - false +} (neg? y)) % {:y 1}) (last f))))
        (rest (take (inc (Math/abs x)) (iterate #(merge-with ({true - false +} (neg? x)) % {:x 1}) (last f)))))))

;;(def x (reductions extrapolate '({:x 0 :y 5}) '({:x 3 :y 5} {:x 5 :y 5} {:x 12 :y 5})))

;;(println (flatten x))

(defn first-repeat
  [coll el]
    ;;(println el (coll el))
    (if (= 2 (coll el)) ;; first block
        (reduced el)
        coll))

(defn solve 
  [input & args]
    (let [vertices (->> input
                      (map name)
                      (map (juxt first #(Integer/parseInt (apply str (rest %)))))
                      (reductions run-ins))]
        {:first (->> vertices 
             (last)
             (calc-distance))
         :second (->> vertices 
             (rest)
             (map calc-positions)
             (into '({:x 3 :y 0})) ;; 0,0 starting
             (reverse)
             (reductions extrapolate '({:x 0 :y 0})) ;; extrapolate vertices into all blocks visited
             (flatten)
             ;;(frequencies) ;; calculate visits?
             (#(reduce first-repeat (frequencies %) %))
             ;;(filter #(> (val %) 1))
             (#(+ (Math/abs (:x %)) (Math/abs (:y %)))))})) ;; incorrect
