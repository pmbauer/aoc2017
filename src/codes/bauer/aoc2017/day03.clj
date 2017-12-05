(ns codes.bauer.aoc2017.day03)

(defn square-spiral
  "lazy seq of square spiral sums and coordinates:
  ({:coord [x0,y0] :val v0} ...)"
  []
  (letfn [(move [[x y] [dir]]
            (case dir
              :north [x (inc y)]
              :south [x (dec y)]
              :east  [(inc x) y]
              :west  [(dec x) y]))
          (adjacents [[x y]]
            [[x       (inc y)]
             [x       (dec y)]
             [(inc x) y]
             [(inc x) (inc y)]
             [(inc x) (dec y)]
             [(dec x) y]
             [(dec x) (inc y)]
             [(dec x) (dec y)]])
          (spiral [coord grid segment-pos distances turns]
            (if (= 0 segment-pos)
              ;; execute turn
              (recur coord grid (first distances) (rest distances) (rest turns))
              (lazy-seq
               (cons {:coord coord, :val (grid coord)}
                     (let [coord (move coord turns)
                           val (transduce (map #(get grid % 0)) +' (adjacents coord))
                           grid (assoc grid coord val)]
                       (spiral coord grid (dec segment-pos) distances turns))))))]
    (let [coord0 [0 0]
          grid {coord0 1}
          distances (drop 2 (interleave (range) (range)))
          turns (cycle [:south :east :north :west])]
      (spiral coord0 grid 0 distances turns))))

;; part1
(defn distance [n]
  (let [[x y] (:coord (nth (square-spiral) (dec n)))]
    (+ (Math/abs x) (Math/abs y))))

(distance 1)
;; => 0
(distance 1024)
;; => 31
(distance 325489)
;; => 552


;; part2
(defn find-greater [x]
  (->> (square-spiral) (drop-while #(<= (:val %) x)) first :val))

(find-greater 1)
;; => 2
(find-greater 747)
;; => 806
(find-greater 325489)
;; => 330785
