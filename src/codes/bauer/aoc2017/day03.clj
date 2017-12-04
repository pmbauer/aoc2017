(ns codes.bauer.aoc2017.day03)

;; part1
(defn distance [x]
  (letfn [(move [[x y] [dir]]
            (case dir
              :north [x (inc y)]
              :south [x (dec y)]
              :east  [(inc x) y]
              :west  [(dec x) y]))]
    (loop [i 1
           loc [0 0]
           segment-pos 0
           distances (drop 2 (interleave (range) (range)))
           turns (cycle [:south :east :north :west])]
      (cond
        ;; found target, compute distance
        (= i x) (transduce (map #(Math/abs %)) + loc)
        ;; execute turn
        (= 0 segment-pos) (recur i loc (first distances) (rest distances) (rest turns))
        ;; move along segment
        :else (recur (inc i) (move loc turns) (dec segment-pos) distances turns)))))

(distance 1)
;; => 0
(distance 12)
;; => 3
(distance 23)
;; => 2
(distance 1024)
;; => 31
(distance 325489)
;; => 552


;; part2
(defn find-greater [x]
  (letfn [(move [[x y] [dir]]
            (case dir
              :north [x (inc y)]
              :south [x (dec y)]
              :east  [(inc x) y]
              :west  [(dec x) y]))]
    (loop [loc [0 0]
           grid {loc 1}
           segment-pos 0
           distances (drop 2 (interleave (range) (range)))
           turns (cycle [:south :east :north :west])]
      (let [val (grid loc)]
        (cond
          ;; found target, return val
          (> val x) val
          ;; execute turn
          (= 0 segment-pos) (recur loc grid (first distances) (rest distances) (rest turns))
          ;; move along segment, compute next value
          :else (let [[x y :as loc] (move loc turns)
                      val (transduce (map #(get grid % 0)) + [[x       (inc y)]
                                                              [x       (dec y)]
                                                              [(inc x) y]
                                                              [(inc x) (inc y)]
                                                              [(inc x) (dec y)]
                                                              [(dec x) y]
                                                              [(dec x) (inc y)]
                                                              [(dec x) (dec y)]])
                      grid (assoc grid loc val)]
                  (recur loc grid (dec segment-pos) distances turns)))))))

(find-greater 1)
;; => 2
(find-greater 747)
;; => 806
(find-greater 325489)
;; => 330785
