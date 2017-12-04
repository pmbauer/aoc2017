(ns codes.bauer.aoc2017.day04
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

;; part1
(->> (io/resource "day04.in")
    slurp
    str/split-lines
    (map #(str/split % #" "))
    (filter #(apply distinct? %))
    count)
;; => 325

;; part2
(->> (io/resource "day04.in")
     slurp
     str/split-lines
     (map #(str/split % #" "))
     (map #(map frequencies %))
     (filter #(apply distinct? %))
     count)
;; => 119
