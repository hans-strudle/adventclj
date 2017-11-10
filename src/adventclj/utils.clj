(ns adventclj.utils)

(defn parse-int
  [n]
    (Integer/parseInt n))

(defn top-freq
  [n word]
    (->> word
      frequencies
      (sort-by (fn [x] [(- (val x)) (int (key x))]))
      (map first)
      (take n)))
