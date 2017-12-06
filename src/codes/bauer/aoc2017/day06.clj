(ns codes.bauer.aoc2017.day06)

;; part1
(def input [4 10 4 1 8 4 9 14 5 1 14 15 0 15 3 5])

(defn redistribute [slots]
  (let [max-index (.indexOf slots (apply max slots))
        next-i #(mod (inc %) (count slots))]
    (loop [blocks (get slots max-index)
           i (next-i max-index)
           slots (assoc slots max-index 0)]
      (if (<= blocks 0)
        slots
        (recur (dec blocks) (next-i i) (update slots i inc))))))

(defn find-cycle [slots]
  (loop [slots slots
         seen #{}]
    (if (seen slots)
      {:slots slots :iterations (count seen)}
      (recur (redistribute slots) (conj seen slots)))))

;; part1
(:iterations (find-cycle input))
;; => 12841

;; part2
(:iterations (find-cycle (:slots (find-cycle input))))
;; => 8038
