(ns adventclj.utils)

(defn parse-int
  [n]
    (try 
      (Integer/parseInt n)
      (catch Exception e 0)))

(defn top-freq
  [n coll]
    (->> coll 
      frequencies
      (sort-by (fn [x] [(- (val x)) (int (key x))]))
      (map first)
      (take n)))
