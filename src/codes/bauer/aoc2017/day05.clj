(ns codes.bauer.aoc2017.day05
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

;; part1
(def input (->> (io/resource "day05.in")
       slurp
       str/split-lines
       (mapv #(Long/parseLong %))))

(def sample [0 3  0  1  -3])

(defn run1 [maze]
  (loop [step 0
         i 0
         maze maze]
    (if (or (< i 0) (>= i (count maze)))
      step
      (let [jv (nth maze i)]
        (recur (inc step) (+ i jv) (assoc maze i (inc jv)))))))

(run1 sample)
;; => 5

(run1 input)
;; => 339351


;; part2
(defn run2 [maze]
  (loop [step 0
         i 0
         maze maze]
    (if (or (< i 0) (>= i (count maze)))
      step
      (let [jv (nth maze i)
            maze (assoc maze i (if (>= jv 3) (dec jv) (inc jv)))]
        (recur (inc step) (+ i jv) maze)))))

(run2 sample)
;; => 10

(run2 input)
;; => 24315397
