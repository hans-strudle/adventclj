(ns adventclj.core-test
  (:require [clojure.test :refer :all]
            [adventclj.solution01 :as solution01]
            [adventclj.solution04 :as solution04]
            [adventclj.utils :as utils]
            [adventclj.core :refer :all]))

(deftest test-next-dir
  (testing "solution01 next-dir"
    (is (= (solution01/next-dir 2 \L) -1))
    (is (= (solution01/next-dir 0 \R) 1))))

(deftest test-which-dir
  (testing "solution01 which-dir"
    (is (= (solution01/which-dir solution01/directions 3 \L) :south))
    (is (= (solution01/which-dir solution01/directions 0 \R) :east))))

(deftest test-inc-compass
  (testing "solution01 inc-compass"
    (is (= 
         (solution01/inc-compass solution01/compass [\R 5])
         {:north 0 :east 5 :south 0 :west 0 :dir 1}))))

(deftest test-calc-distance
  (testing "solution01 calc-distance"
    (is (= (solution01/calc-distance solution01/compass) 0))
    (is (= (solution01/calc-distance (assoc solution01/compass :east 5 :south 3)) 8) :east)))

;;(deftest test-extrapolate
;;  (testing "solution01 extrapolate"
;;    (is (= (flatten (solution01/extrapolate 
;;                     '({:x 0 :y 0}) '({:x 2 :y 0})) '({:x 0 :y 0} {:x 1 :y 0} {:x 2 :y 0}))))))

(deftest test-room-info
  (testing "solution04 room-info"
    (is (= (solution04/room-info "xmtjbzidx-ytz-nojmvbz-525[hyzbw]") {:id 525 :checksum "hyzbw" :word "xmtjbzidxytznojmvbz"}))))

(deftest test-cypher-char
  (testing "solution04 cypher-char"
    (is (= (solution04/cypher-char \t 283) \q))
    (is (= (solution04/cypher-char \d 7) \k))))

(deftest test-top-freq
  (testing "utils top-freq"
    (is (= (utils/top-freq 5 "xmtjbzidxytznojmvbz") [\z \b \j \m \t]))))

